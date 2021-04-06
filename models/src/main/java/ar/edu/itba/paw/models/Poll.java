package ar.edu.itba.paw.models;

import java.util.Date;

public class Poll {
    
    private final int id;
    private final String name, description;
    private final Date creationDate, expiryDate;
    private final User submittedBy;
    
    public Poll(int id, String name, String description, Date creationDate, Date expiryDate, User submittedBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.submittedBy = submittedBy;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public User getSubmittedBy() {
        return submittedBy;
    }
}
