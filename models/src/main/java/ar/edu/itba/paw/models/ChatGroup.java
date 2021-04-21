package ar.edu.itba.paw.models;

import java.util.Date;

public class ChatGroup {

    private final Integer id;
    private final Integer careerId;
    private final String name, link;
    private final Date creationDate;
    private final int submitted_by;
    private final ChatPlatform platform;

    public ChatGroup(Integer id, Integer careerId, String name, String link, int user, Date creationDate, ChatPlatform platform){
        this.id = id;
        this.careerId = careerId;
        this.name = name;
        this.link = link;
        this.creationDate = creationDate;
        this.submitted_by = user;
        this.platform = platform;
    }

    public Integer getId() {return id;}

    public Integer getCareerId() {
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