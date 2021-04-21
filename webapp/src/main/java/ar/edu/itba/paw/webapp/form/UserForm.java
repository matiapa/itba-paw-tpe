package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class UserForm {


    private String name;

    private String surname;

    private String email;


    private int id;

    private int career_id;


    private ArrayList<String> courses;



    public String getName() {
        return name;
    }

    public int getCareer_id() {
        return career_id;
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

    public void setCareer_id(int career_id) {
        this.career_id = career_id;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }
}
