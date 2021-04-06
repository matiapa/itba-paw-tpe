package ar.edu.itba.paw.models;

import java.util.Objects;

public class Course {

    private final String id, name;

    public Course(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}