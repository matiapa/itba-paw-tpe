package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "career")
public class Career {

    @Column(length = 100, nullable = false)
    private String name;
    @Id
    @Column(name = "code")
    private String code;

    Career(){
        //hibernate
    }
/*
    public Career(String name, String code){
        this.name = name;
        this.code = code;
    }

*/
    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

}
