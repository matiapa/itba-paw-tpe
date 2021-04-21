package ar.edu.itba.paw.models;

import java.util.Date;
import java.util.List;

public class Poll {
    
    private final int id;
    private final String name, description;
    private final PollFormat pollFormat;
    private final Date creationDate, expiryDate;
    private final User submittedBy;
    private final List<PollOption> options;
    
    public Poll(int id, String name, String description, PollFormat format, Integer careerId, String courseId, Date creationDate, Date expiryDate, User submittedBy, List<PollOption> options) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pollFormat = format;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.submittedBy = submittedBy;
        this.options = options;
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

    public List<PollOption> getOptions() {
        return options;
    }

    public static class PollOption {
        private final int id;
        private final String value;

        
        public PollOption(int id, String value) {
            this.id = id;
            this.value = value;
        }

        public PollOption(String value){
            this(0, value);
        }
    
        public int getId() {
            return id;
        }
    
        public String getValue() {
            return value;
        }
    }

    public static class PollVoteOption {
        private final int id;
        private final String value;
        private final int vote;

        public PollVoteOption(int id, String value,int vote) {
            this.id = id;
            this.value = value;
            this.vote=vote;
        }

        public int getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        public int getVote(){return vote;}
    }

}