package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.UserData;

public interface SgaService {

    UserData fetchFromEmail(String email);

}
