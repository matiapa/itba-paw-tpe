package ar.edu.itba.paw.models;

public class Course {

    private final String id, name;
    private final int careerId;

    public Course(String id, String name, int career_id) {
        this.id = id;
        this.name = name;
        this.careerId = career_id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCareerId() {
        return careerId;
    }

}
