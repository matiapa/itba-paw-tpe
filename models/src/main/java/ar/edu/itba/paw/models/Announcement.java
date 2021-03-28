package ar.edu.itba.paw.models;

import java.util.Date;

public class Announcement {

    private final String title, content;
    private final Date uploadDate;

    public Announcement(String name, String description, Date uploadDate) {
        this.title = name;
        this.content = description;
        this.uploadDate = uploadDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getUploadDate() {
        return uploadDate;
    }
}
