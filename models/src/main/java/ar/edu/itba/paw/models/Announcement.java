package ar.edu.itba.paw.models;

import java.util.Date;

public class Announcement {

    private final int id;
    private final User uploader;
    private final String title, summary, content;
    private final Date uploadDate, expiryDate;
    private final Boolean seen;

    public Announcement(int id, User uploader, String name, String summary, String description,
                        Date uploadDate, Date expiryDate, Boolean seen) {
        this.id = id;
        this.uploader = uploader;
        this.title = name;
        this.summary = summary;
        this.content = description;
        this.uploadDate = uploadDate;
        this.expiryDate = expiryDate;
        this.seen = seen;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getUploader() {
        return uploader;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public Boolean getSeen() {
        return seen;
    }
}