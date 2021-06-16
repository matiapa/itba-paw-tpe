package ar.edu.itba.paw.webapp.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.exceptions.BadRequestException;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;
import ar.edu.itba.paw.webapp.form.AnnounceForm;
import ar.edu.itba.paw.webapp.form.ContentReviewForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.webapp.controller.common.Pager;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.SgaService;
import ar.edu.itba.paw.webapp.controller.common.CommonFilters;
import ar.edu.itba.paw.webapp.form.ContentForm;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Controller
public class ContentController {

    @Autowired private ContentService contentService;

    @Autowired private SgaService sgaService;

    @Autowired private CourseService courseService;

    @Autowired private CommonFilters commonFilters;

    private static final Logger LOGGER= LoggerFactory.getLogger(ContentController.class);


    @RequestMapping(value = "/contents", method = GET)
    public ModelAndView list(
        @RequestParam(name = "courseId", required = false) String courseId,
        @RequestParam(name = "contentType", required = false) String contentType,
        @RequestParam(name = "minDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date minDate,
        @RequestParam(name = "maxDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date maxDate,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(name = "showCreateForm", required = false, defaultValue="false") Boolean showCreateForm,
        @ModelAttribute("createForm") final ContentForm contentForm,
        @ModelAttribute("user") User loggedUser
    ) {
        final ModelAndView mav = new ModelAndView("contents/content_list");

        // Add filters options
        Course selectedCourse = commonFilters.addCourses(mav, courseId);

        // -- By type
        Content.ContentType selectedType = contentType != null && !contentType.isEmpty()
                ? Content.ContentType.valueOf(contentType) : null;
        mav.addObject("selectedType", selectedType);

        // Add filtered content
        List<Content> contents;
        Map<Integer, UserData> contentOwners = new HashMap<>();

        if(courseId != null){
            mav.addObject("courseId", courseId);

            Pager pager = new Pager(contentService.getSize(selectedCourse, selectedType, minDate, maxDate), page);
            mav.addObject("pager", pager);

            contents = contentService.findByCourse(selectedCourse, selectedType, minDate, maxDate, pager.getCurrPage(), pager.getLimit());
            contents.sort(Comparator.comparing(Content::getUploadDate).reversed());
            mav.addObject("contents", contents);

            contents
                .stream()
                .filter(c -> c.getOwnerMail() != null)
                .forEach(c -> {
                    UserData user = sgaService.fetchFromEmail(c.getOwnerMail());
                    if(user != null) contentOwners.put(c.getId(), user);
            });

            mav.addObject("contentOwners", contentOwners);
        }

        // Add other parameters

        mav.addObject("showCreateForm", showCreateForm);

        mav.addObject("canDeleteContent", loggedUser.can(Permission.Action.delete, Entity.course_content));

        return mav;
    }

    @RequestMapping(value = "/contents", method = POST)
    public ModelAndView create(
        HttpServletRequest request,
        @Valid @ModelAttribute("createForm") final ContentForm form, final BindingResult errors,
        @ModelAttribute("user") User loggedUser
    ) throws URISyntaxException, IOException {


        if(errors.hasErrors()){
            LOGGER.debug("User failed to create content with form {}  and errors {}",form,errors);
            return list(null, null, null, null, 0,
                true, form, loggedUser);
        }

        String websiteUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build()
                .toString() + request.getContextPath();

        contentService.createContent(
            form.getName(),
            form.getLink(),
            courseService.findById(form.getCourseId()).orElseThrow(NoSuchElementException::new),
            form.getDescription(),
            Content.ContentType.valueOf(form.getContentType()),
            form.getContentDate(),
            loggedUser,
            form.getOwnerMail().isEmpty()?null: form.getOwnerMail(),
            websiteUrl,
            LocaleContextHolder.getLocale()
        );



        LOGGER.debug("User created content with form {} ",form);

        return list(form.getCourseId(), null, null, null, 0,
                false, form, loggedUser);
    }

    @RequestMapping(value = "/contents/{id}", method = GET)
    public ModelAndView get(
            @PathVariable(value = "id") Integer id,
            @ModelAttribute("ContentReviewForm") final ContentReviewForm form,
            @ModelAttribute("user") User loggedUser,
            @RequestParam(name="page", required = false, defaultValue = "0") int page
    ){

        final ModelAndView mav = new ModelAndView("contents/content_detail");



        Optional<Content> optionalContent = contentService.findById(id);
        if (! optionalContent.isPresent()){
            LOGGER.debug("user {} tried accessing content with id {} but didnt exist",loggedUser,id);
            throw new ResourceNotFoundException();
        }

        Pager pager = new Pager(contentService.getReviewsSize(optionalContent.get()),page);

        mav.addObject("pager",pager);

        mav.addObject("content", optionalContent.get());
        mav.addObject("reviews",contentService.getReviews(optionalContent.get(),page,10));

        return mav;

    }


    @RequestMapping(value = "/contents/{id}", method = POST)
    public ModelAndView postReview(
            @PathVariable(value = "id") Integer id,
            @Valid @ModelAttribute("ContentReviewForm") final ContentReviewForm form,final BindingResult errors,
            @ModelAttribute("user") User loggedUser,
            @RequestParam(name="page", required = false, defaultValue = "0") int page
    ){


        if (errors.hasErrors()){
            LOGGER.debug("user {} tried to post a review on content with id {} with errors {}",loggedUser,id,errors);
            return get(id,form,loggedUser,page);
        }
        Optional<Content> optionalContent=contentService.findById(id);
        if (!optionalContent.isPresent()){
            LOGGER.debug("user {} tried to review content with id {} but didnt exist",loggedUser,id);
            throw new ResourceNotFoundException();
        }
        contentService.createContentReview(optionalContent.get(), form.getReviewBody(),loggedUser);

        return get(id, form, loggedUser, page);

    }

    @Secured("ROLE_COURSE_CONTENT.DELETE")
    @RequestMapping(value = "/contents/{id}", method = DELETE)
    public String delete(
            @PathVariable(value="id") int id, HttpServletRequest request
    ) {
        contentService.delete(contentService.findById(id).orElseThrow(NoSuchElementException::new));
        LOGGER.debug("User deleted content with id {}",id);

        return "redirect:"+request.getHeader("Referer");
    }

    @Secured("ROLE_COURSE_CONTENT.DELETE")
    @RequestMapping(value = "/contents/{id}/delete", method = POST)
    public String deleteWithPost(
            @PathVariable(value="id") int id, HttpServletRequest request
    ) {
        return delete(id, request);
    }

}