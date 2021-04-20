package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/registerUser", method = RequestMethod.GET)
    public ModelAndView createForm(@ModelAttribute("UserForm") final UserForm form) {
        return new ModelAndView("register/register");
    }

    @RequestMapping(path = "/registerUser", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("UserForm") final UserForm form){
        final User user= userService.registerUser(form.getId(), form.getName(), form.getSurname(), form.getEmail(), form.getCareer_id());
        return new ModelAndView("index");
    }
}
