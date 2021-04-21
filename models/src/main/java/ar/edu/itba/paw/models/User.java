package ar.edu.itba.paw.models;

public class User {

    private final int id;
    private final String name;
    private final String surname;
    private final String email;

    private final int career_id;

    public User(int id, String name, String surname, String email,int career_id) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.career_id=career_id;
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

    public int getCareerId() {
        return career_id;
    }
}
