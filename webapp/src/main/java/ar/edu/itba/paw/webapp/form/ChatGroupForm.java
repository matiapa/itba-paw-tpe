package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class ChatGroupForm {

    @NotNull
    @Size(min = 5, max = 50)
    private String name;

    @NotNull
    private String careerCode;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date creationDate;

    @NotNull
    private ChatPlatform platform;

    @NotNull
    @Size(min = 5, max = 100)
    private String link;


    public String getName() {
        return name;
    }

    public String getCareerCode() {
        return careerCode;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public ChatPlatform getPlatform() {
        return platform;
    }

    public String getLink() {
        return link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCareerCode(String careerCode) {
        this.careerCode = careerCode;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setPlatform(ChatPlatform platform) {
        this.platform = platform;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
