package ar.edu.itba.paw.models;

import java.util.Date;

public class User {

    private final int id;
    private final String name;
    private final String surname;
    private final String email;
    private final String profileImgB64;
    private final Date signupDate;

    private final String careerCode;

    public User(int id, String name, String surname, String email, String profileImgB64, Date signupDate, String careerCode) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.profileImgB64 = profileImgB64;
        this.signupDate = signupDate;
        this.careerCode=careerCode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getCareerCode() {
        return careerCode;
    }

    public String getProfileImgB64() {
        return profileImgB64;
    }

    public Date getSignupDate() {
        return signupDate;
    }
}
