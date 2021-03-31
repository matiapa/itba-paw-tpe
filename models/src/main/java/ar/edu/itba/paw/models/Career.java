package ar.edu.itba.paw.models;

public class Career {
    private final int id;
    private final String name;

    public Career(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
