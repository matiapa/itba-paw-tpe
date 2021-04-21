package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;

public interface SgaService {
    User fetchFromEmail(String email);
}
