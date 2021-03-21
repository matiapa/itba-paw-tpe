package ar.edu.itba.paw.models;

public class User {

    private final String email;
    private final String password;

    public User(String name, String password) {
        this.email = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
