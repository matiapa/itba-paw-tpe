package ar.edu.itba.paw.models;

import java.util.Date;

public class ChatGroup {

    private final String id, careerId;
    private final String name, link;
    private final Date creationDate;

    public ChatGroup(String id, String careerId, String name, String link, Date creationDate){
        this.id = id;
        this.careerId = careerId;
        this.name = name;
        this.link = link;
        this.creationDate = creationDate;
    }

    public String getId() {return id;}

    public String getCareerId() {
        return careerId;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

}