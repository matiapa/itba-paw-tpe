package ar.edu.itba.paw.models;

public class Career {

    private final String name;
    private final String code;

    public Career(String name, String code){
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

}
