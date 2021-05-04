package ar.edu.itba.paw.models;

import java.util.Date;

public class Content {

    private final int id;
    private final String name, link, description;

    private final User submitter;
    private final String ownerMail;

    private final Date uploadDate;
    private final Date contentDate;

    private final ContentType contentType;

    public Content(int id, String name, String link, String description, User submitter, String ownerMail,
           Date uploadDate, Date contentDate, ContentType contenttype) {
        this.id = id;
        this.name = name;
        this.link=link;
        this.description=description;
        this.ownerMail = ownerMail;
        this.submitter = submitter;
        this.uploadDate = uploadDate;
        this.contentDate = contentDate;
        this.contentType = contenttype;
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

    public String getOwnerMail() {
        return ownerMail;
    }

    public User getSubmitter() {
        return submitter;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public Date getContentDate() {
        return contentDate;
    }

    public ContentType getContentType() {
        return contentType;
    }


    public enum ContentType{
        exam, guide, exercise, project, theory, summary, bibliography, solution, code, miscellaneous
    }

}