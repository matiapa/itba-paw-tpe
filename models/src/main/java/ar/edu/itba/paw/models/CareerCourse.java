package ar.edu.itba.paw.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "career_course")
public class CareerCourse extends Course{


    private int year;
    private int semester;

    CareerCourse(){

    }
    public CareerCourse(String id, String name,int credits,int semester) {
        super(id, name,credits);
        this.semester=semester%2 +1 ;
        this.year=(semester+1)/2;

    }

    public int getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }
}
