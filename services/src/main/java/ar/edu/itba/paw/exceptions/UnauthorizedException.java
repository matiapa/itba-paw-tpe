package ar.edu.itba.paw.exceptions;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(){
        super("Operation not authorized");
    }

}