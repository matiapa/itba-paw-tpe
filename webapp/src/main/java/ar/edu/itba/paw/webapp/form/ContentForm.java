package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.Content;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class ContentForm {

    @NotNull
    @Size(min = 5, max = 50)
    private String name;

    @NotNull
    @Size(min = 5, max = 100)
    private String link;

    @NotNull
    @Size(min = 5, max = 500)
    private String description;

    @NotNull
    private String courseId;

    private Date content_Date;

    @NotNull
    private Content.ContentType contentType;

    public ContentForm(String name, String link, String description, String courseId, Date content_Date, Content.ContentType contentType) {
        this.name = name;
        this.link = link;
        this.description = description;
        this.courseId = courseId;
        this.content_Date = content_Date;
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Date getContent_Date() {
        return content_Date;
    }

    public void setContent_Date(Date content_Date) {
        this.content_Date = content_Date;
    }

    public Content.ContentType getContentType() {
        return contentType;
    }

    public void setContentType(Content.ContentType contentType) {
        this.contentType = contentType;
    }
}
