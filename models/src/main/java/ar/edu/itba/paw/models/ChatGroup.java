package ar.edu.itba.paw.models;

public class ChatGroup {
    private final String id, careerId;
    private final String name, link;

    public ChatGroup(String id, String career_id, String name, String link){
        this.id = id;
        this.careerId = career_id;
        this.name = name;
        this.link = link;
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
}
