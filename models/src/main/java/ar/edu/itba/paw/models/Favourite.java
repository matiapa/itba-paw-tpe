package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;


@Entity
@IdClass(FavouriteId.class)
@Table(name = "fav_course")
public class Favourite {

    @Id
    @Column(name = "course_id")
    private String course_id;

    @Id
    @Column(name = "user_id")
    private Integer user_id;

    public Favourite(){}

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}

class FavouriteId implements Serializable {
    private String course_id;

    private Integer user_id;

    // default constructor


    public FavouriteId(String course_id, Integer user_id) {
        this.course_id = course_id;
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavouriteId that = (FavouriteId) o;
        return course_id.equals(that.course_id) && user_id.equals(that.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course_id, user_id);
    }
    // equals() and hashCode()
}