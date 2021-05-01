package ar.edu.itba.paw.services;


import ar.edu.itba.paw.persistence.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.HashMap;
import java.util.Map;


@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;
    
    @Autowired
    private UserDao userDao;

    private String email="noreplyitbahub@gmail.com";

    private String BaseURL="http://localhost:8080/webapp";

    @Override
    public void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("template-thymeleaf.html",thymeleafContext);

        sendHTMLMessage(to,subject,htmlBody);
    }

    @Override
    public void sendHTMLMessage(String to, String subject, String text)  {
        MimeMessage message= mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper= new MimeMessageHelper(message,true);
            helper.setFrom(email);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text,true);
        }
        catch (MessagingException messagingException){
            throw new RuntimeException("fallo mailer");
        }




        mailSender.send(message);
    }

    

    @Override
    public int getVerificationCode(String email) {
        return userDao.getVerificationCode(email);
    }

    @Override
    public void sendVerificationEmail(String email) {


        Map<String,Object> model = new HashMap<>();
        model.put("buttonLink",BaseURL+"/register/verification?verificationCode="+getVerificationCode(email)+"&email="+email);
        model.put("text","Esta cuenta ha sido utilizada para registrar un usuario en ITBAHub, si no fuiste vos ignora este mensaje.");
        String subject="Verificar cuenta";
        sendMessageUsingThymeleafTemplate(email,subject,model);


    }

}
