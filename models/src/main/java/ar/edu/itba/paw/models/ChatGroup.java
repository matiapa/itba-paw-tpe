package ar.edu.itba.paw.models;

import java.util.Date;

public class ChatGroup {

    private final String id, careerId;
    private final String name, link;
    private final Date creationDate;

    public ChatGroup(String id, String career_id, String name, String link, Date creationDate){
        this.id = id;
        this.careerId = career_id;
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
