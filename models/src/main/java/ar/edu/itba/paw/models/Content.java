package ar.edu.itba.paw.models;

import java.util.Date;

public class Content {

    private final int id;
    private final String name, link, description;
    private final User submitter;
    private final Date uploadDate;

    public Content(int id, String name, String link, String description, User submitter,Date uploadDate) {
        this.id = id;
        this.name = name;
        this.link=link;
        this.description=description;
        this.submitter = submitter;
        this.uploadDate = uploadDate;
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

    public Date getUploadDate() {
        return uploadDate;
    }
}