package ar.edu.itba.paw.models;

import java.io.Serializable;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "poll")
public class Poll {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poll_id_seq")
    @SequenceGenerator(sequenceName = "poll_id_seq", name = "poll_id_seq", allocationSize = 1)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PollFormat format;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "creation_date", nullable = false)
    private final Date creationDate = new Date();
    
    @Column(name = "expiry_date")
    private Date expiryDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submitted_by", nullable = false)
    private User submittedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_code")
    private Career career;

    @Column
    private String link;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "poll")
    private List<PollOption> options;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "poll_vote_registry",
        joinColumns = @JoinColumn(name = "poll_id"), 
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> votedBy;

    public Poll() {}
    
    public Poll(String name, String description, PollFormat format, Career career, Course course, Date expiryDate, User submittedBy) {
        this.name = name;
        this.description = description;
        this.format = format;
        this.career = career;
        this.course = course;
        this.expiryDate = expiryDate;
        this.submittedBy = submittedBy;
    }

    public Poll(String name, String description, PollFormat format, Career career, Date expiryDate, User submittedBy) {
        this(name, description, format, career, null, expiryDate, submittedBy);
    }

    public Poll(String name, String description, PollFormat format, Course course, Date expiryDate, User submittedBy) {
        this(name, description, format, null, course, expiryDate, submittedBy);
    }

    public Poll(String name, String description, PollFormat format, Date expiryDate, User submittedBy) {
        this(name, description, format, null, null, expiryDate, submittedBy);
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

    public void setOptions(List<PollOption> options) {
        this.options = options;
    }

    public boolean getIsExpired(){
        return expiryDate != null && expiryDate.before(new Date());
    }

    public Course getCourse() {
        return course;
    }

    public Career getCareer() {
        return career;
    }

    public String getLink() {
        return link;
    }

    public List<User> getVotedBy() {
        return votedBy;
    }

    @Entity
    @Table(name = "poll_option")
    @IdClass(Poll.PollOptionId.class)
    public static class PollOption {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poll_option_id_seq")
        @SequenceGenerator(sequenceName = "poll_option_id_seq", name = "poll_option_id_seq", allocationSize = 1)
        @Column(name = "option_id", nullable = false)
        private int optionId;

        @Column(name = "option_value")
        private String value;

        @Id
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "poll_id", nullable = false)
        private Poll poll;

        @OneToOne(mappedBy = "option", fetch = FetchType.EAGER)
        private PollVoteCount votes;
        
        private PollOption() {}

        public PollOption(Poll poll, String value) {
            this.poll = poll;
            this.value = value;
        }
    
        public int getId() {
            return optionId;
        }
    
        public String getValue() {
            return value;
        }

        public PollVoteCount getVotes() {
            return votes;
        }
    }
    
    @Entity
    @Table(name = "poll_submission")
    public static class PollVote {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poll_submission_id_seq")
        @SequenceGenerator(sequenceName = "poll_submission_id_seq", name = "poll_submission_id_seq", allocationSize = 1)
        @Column(nullable = false)
        private int id;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "poll_id", nullable = false, insertable = false, updatable = false)
        private Poll poll;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumns({
            @JoinColumn(name = "poll_id", referencedColumnName = "poll_id"),
            @JoinColumn(name = "option_id", referencedColumnName = "option_id")
        })
        private PollOption option;

        @Column
        private String value;

        @Column(nullable = false)
        private Date date = new Date();

        private PollVote() {}

        public PollVote(PollOption option) {
            this.option = option;
        }
    }
    
    @Entity
    @Table(name = "poll_vote_count")
    @IdClass(Poll.PollOptionId.class)
    public static class PollVoteCount {
        @Id
        @Column(name = "option_id", insertable = false, updatable = false)
        private int optionId;
        
        @Column
        private int votes;
        
        @Id
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "poll_id", nullable = false, insertable = false, updatable = false)
        private Poll poll;
        
        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumns({
            @JoinColumn(name = "poll_id", referencedColumnName = "poll_id", insertable = false, updatable = false),
            @JoinColumn(name = "option_id", referencedColumnName = "option_id", insertable = false, updatable = false)
        })
        private PollOption option;
        
        public PollVoteCount() {}
        
        public int getVotes() {
            return votes;
        }
        
        public Poll getPoll() {
            return poll;
        }
        
        public PollOption getOption() {
            return option;
        }
    }

    @Entity(name = "PollControversy")
    @Table(name = "poll_controversy")
    public static class PollControversy {
        @Id
        @Column(name = "id", insertable = false, updatable = false)
        private int pollId;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id", nullable = false)
        private Poll poll;

        @Column
        private double points;

        private PollControversy() {}
    }

    public static class PollOptionId implements Serializable {
        private int poll;
        private int optionId;
    }
    
    public enum PollState {
        open, closed
    }

    public enum PollFormat {
        multiple_choice, text
    }
    
}