package ar.edu.itba.paw.models;

public class Content {

    private final int id;
    private final String course_id,name,author,link,description;

    public Content(int id, String course_id, String name, String author,String link,String description) {
        this.id = id;
//        this.career_id = career_id;
        this.course_id = course_id;
        this.name = name;
        this.author = author;
        this.link=link;
        this.description=description;
    }

    public String getLink() {
        return link;
    }

    public int getId() {
        return id;
    }

//    public String getCareer_id() {
//        return career_id;
//    }

    public String getCourse_id() {
        return course_id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }
}
