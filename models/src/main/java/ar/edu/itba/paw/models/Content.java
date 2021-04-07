package ar.edu.itba.paw.models;

public class Content {

    private final int id;
    private final String name, link, description;
    private final User submitter;

    public Content(int id, String name, String link, String description, User submitter) {
        this.id = id;
        this.name = name;
        this.link=link;
        this.description=description;
        this.submitter = submitter;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public User getSubmitter() {
        return submitter;
    }
}