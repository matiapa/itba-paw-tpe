package ar.edu.itba.paw.models;

import java.util.Date;

public class ChatGroup {

    private final Integer id;
    private final String careerId;
    private final String name, link;
    private final Date creationDate;
    private final int submitted_by;

    public ChatGroup(Integer id, String careerId, String name, String link, int user, Date creationDate){
        this.id = id;
        this.careerId = careerId;
        this.name = name;
        this.link = link;
        this.creationDate = creationDate;
        this.submitted_by = user;
    }

    public Integer getId() {return id;}

    public String getCareerId() {
        return careerId;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public Integer getSubmitted_by() {
        return submitted_by;
    }

    public Date getCreationDate() {
        return creationDate;
    }

}