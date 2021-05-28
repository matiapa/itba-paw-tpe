package ar.edu.itba.paw.models;

import java.util.Date;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "login_activity")
public class LoginActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "login_activity_id_seq")
    @SequenceGenerator(sequenceName = "login_activity_id_seq", name = "login_activity_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "timestamp default now() not null")
    private Date date;

    public LoginActivity() { }

    public LoginActivity(User user) {
        this.user = user;
    }

}