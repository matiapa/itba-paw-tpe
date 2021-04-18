package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ChatGroupForm {

    @Size(min = 6, max = 100)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String groupName;

    @Size(min = 6, max = 100)
    @Pattern(regexp = "[a-zA-z]+ [a-zA-z]+")
    private String groupCareer;
    @Size(min = 6, max = 100)
    private String link;

    public String getGroupName() {
        return groupName;
    }

    public String getGroupCareer() {
        return groupCareer;
    }

    public String getLink() {
        return link;
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
