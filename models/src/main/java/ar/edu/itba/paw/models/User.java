package ar.edu.itba.paw.models;

import java.util.List;
import java.util.Date;

public class User {

    private final int id;
    private final String name;
    private final String surname;
    private final String email;
    protected String password;
    private final String profileImgB64;
    private final Date signupDate;

    private final List<Permission> permissions;

    private final String careerCode;

    public User(int id, String name, String surname, String email, String password, String profileImgB64,
                Date signupDate, List<Permission> permissions, String careerCode) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.profileImgB64 = profileImgB64;
        this.signupDate = signupDate;
        this.permissions = permissions;
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

    public String getPassword() {
        return password;
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

    public List<Permission> getPermissions() {
        return permissions;
    }
}
