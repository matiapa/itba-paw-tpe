package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class ChatGroupForm {

    @NotNull
    @Size(min = 6, max = 100)
    private String name;

    @NotNull
    private Integer careerId;

    @NotNull
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date creationDate;

    @NotNull
    private ChatPlatform platform;

    @NotNull
    @Size(min = 6, max = 100)
    private String link;


    public String getName() {
        return name;
    }

    public Integer getCareerId() {
        return careerId;
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

    public void setCareerId(Integer careerId) {
        this.careerId = careerId;
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
