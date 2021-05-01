package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class UserForm {


    @NotNull
    @Size(min = 5, max = 20)
    private String name;

    @NotNull
    @Size(min = 5, max = 20)
    private String surname;

    @NotNull
    @Size(min = 5, max = 50)
    private String email;

    @NotNull
    private int id;

    @NotNull
    private String careerCode;

    @NotNull
    @NotEmpty
    private ArrayList<String> courses;



    public String getName() {
        return name;
    }

    public String getCareerCode() {
        return careerCode;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCareerCode(String careerCode) {
        this.careerCode = careerCode;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }
}
