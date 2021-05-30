package ar.edu.itba.paw.services;

import java.io.IOException;

import ar.edu.itba.paw.models.User;

public interface EmailService {

    void sendVerificationEmail(User user, String baseUrl) throws IOException;

}