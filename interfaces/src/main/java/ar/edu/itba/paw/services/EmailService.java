package ar.edu.itba.paw.services;

import java.io.IOException;
import java.util.Locale;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;

public interface EmailService {

    void sendVerificationEmail(User user, String baseUrl, Locale locale) throws IOException;

    void sendContentNotification(User user, Content content,String baseUrl, Locale locale) throws IOException;

}