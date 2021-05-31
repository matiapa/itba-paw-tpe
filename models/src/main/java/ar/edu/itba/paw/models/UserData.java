package ar.edu.itba.paw.models;

import java.util.Date;

public interface UserData {

    int getId();

    String getName();

    String getSurname();

    String getFullName();

    String getEmail();

    Career getCareer();

    byte[] getPicture();

    Date getSignupDate();

}
