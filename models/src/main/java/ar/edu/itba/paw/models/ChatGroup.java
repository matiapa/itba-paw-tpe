package ar.edu.itba.paw.models;

import java.util.Date;

public class ChatGroup {

    private final int id;
    private final int careerId;
    private final String name, link;
    private final Date creationDate;
    private final int submittedBy;
    private final ChatPlatform platform;

    public ChatGroup(int id, int careerId, String name, String link, int user, Date creationDate, ChatPlatform platform){
        this.id = id;
        this.careerId = careerId;
        this.name = name;
        this.link = link;
        this.creationDate = creationDate;
        this.submittedBy = user;
        this.platform = platform;
    }

    public int getId() {return id;}

    public int getCareerId() {
        return careerId;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public int getSubmittedBy() {
        return submittedBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Integer getCreationYear() {
        return creationDate.getYear() + 1900;
    }

    public Integer getCreationQuarter() {
        return creationDate.getMonth() < 7 ? 1 : 2;
    }

    public ChatPlatform getPlatform() {
        return platform;
    }

    public enum ChatPlatform {
        whatsapp, discord
    }

}