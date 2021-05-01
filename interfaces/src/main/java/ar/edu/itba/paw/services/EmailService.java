package ar.edu.itba.paw.services;




import java.util.Map;

public interface EmailService {

    void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel);

    void sendHTMLMessage(String to, String subject, String text);

    int getVerificationCode(String email);

    void sendVerificationEmail(String email);
}
