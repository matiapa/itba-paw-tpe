package ar.edu.itba.paw.models;

public class ChatGroup {
    private final String id, career_id;
    private final String link;

    public ChatGroup(String id, String career_id, String link){
        this.id = id;
        this.career_id = career_id;
        this.link = link;
    }

    public String getId() {return id;}

    public String getCareer_id() {
        return career_id;
    }

    public String getLink() {
        return link;
    }
}
