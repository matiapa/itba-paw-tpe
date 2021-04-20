package ar.edu.itba.paw.webapp.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class ChatGroupForm {

    @Size(min = 6, max = 100)
    @NotNull
    private String groupName;

    @Size(min = 6, max = 100)
    private String groupCareer;

    @Size(min = 6, max = 100)
    @NotNull
    private String link;

    @NotNull
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date groupDate;

    public String getGroupName() {
        return groupName;
    }

    public String getGroupCareer() {
        return groupCareer;
    }

    public String getLink() {
        return link;
    }

    public Date getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(Date groupDate) {
        this.groupDate = groupDate;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupCareer(String groupCareer) {
        this.groupCareer = groupCareer;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
