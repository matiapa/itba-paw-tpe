package ar.edu.itba.paw.services;

import java.io.IOException;
import java.util.Map;

public interface EmailService {

    void sendVerificationEmail(String email,String baseUrl) throws IOException;

}