package ar.edu.itba.paw.models;

public class User {

    private final int id;
    private final String name;
    private final String surname;
    private final String email;

    private final String careerCode;

    public User(int id, String name, String surname, String email, String careerCode) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
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
}
