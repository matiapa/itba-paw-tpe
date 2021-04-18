package ar.edu.itba.paw.models;

public class Course {

    private final String id, name;
    private final int credits;

    public Course(String id, String name,int credits) {
        this.id = id;
        this.name = name;
        this.credits=credits;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }
}