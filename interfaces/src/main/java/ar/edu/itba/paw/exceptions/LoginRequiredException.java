package ar.edu.itba.paw.exceptions;

public class LoginRequiredException extends Exception{

    public LoginRequiredException(){
        super("User login is required");
    }

}
