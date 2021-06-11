package ar.edu.itba.paw.services;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import ar.edu.itba.paw.models.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserDao;


@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;
    
    @Autowired
    private UserDao userDao;

    @Resource
    private MessageSource messageSource;


    @Async
    @Override
    public void sendVerificationEmail(User user, String baseURL,Locale locale) throws IOException {

        if(user.getVerification().getCode()==null)
            throw new RuntimeException("Missing verification code");

        Map<String,Object> model = new HashMap<>();

        model.put("title",messageSource.getMessage("register.verification_mail.title",null,locale));
        model.put("text", messageSource.getMessage("register.verification_mail.body", null, locale));
        model.put("buttonText", messageSource.getMessage("register.verification_mail.buttonText", null, locale));
        model.put("buttonLink", String.format("%s/register/verification?verificationCode=%d&email=%s", baseURL, user.getVerification().getCode(), user.getEmail()));
        model.put("baseUrl",baseURL);

        String subject = messageSource.getMessage("register.verification_mail.subject", null, locale);

        sendMessageUsingThymeleafTemplate(user.getEmail(), subject, model,"register-verification.html");

    }
    @Async
    @Override
    public void sendContentNotification(User user, Content content,String baseURL, Locale locale) throws IOException {

        Map<String,Object> model = new HashMap<>();

        model.put("title",messageSource.getMessage("content.fav_notification_mail.title",null,locale));
        model.put("text", messageSource.getMessage("content.fav_notification_mail.body", null, locale));
        model.put("buttonText", messageSource.getMessage("content.fav_notification_mail.buttonText", null, locale));
        model.put("buttonLink", String.format("%s/contents/%d", baseURL, content.getId()));
        String subject = messageSource.getMessage("content.fav_notification_mail.subject", null, locale);

        sendMessageUsingThymeleafTemplate(user.getEmail(), subject, model,"new-content-notification.html");

    }

    private void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel,String template) throws IOException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process(template,thymeleafContext);

        sendHTMLMessage(to,subject,htmlBody);
    }

    private void sendHTMLMessage(String to, String subject, String text) throws IOException {
        MimeMessage message= mailSender.createMimeMessage();

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "auth.properties";

        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        try{
            MimeMessageHelper helper= new MimeMessageHelper(message,true);
            helper.setFrom(appProps.getProperty("itbahub.mailer.email"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text,true);
        }
        catch (MessagingException messagingException){
            throw new RuntimeException("Mailer failed");
        }

        mailSender.send(message);

    }

}
