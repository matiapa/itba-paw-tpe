package ar.edu.itba.paw.models;

public class CareerCourse extends Course{


    private final int year;
    private final int semester;

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
