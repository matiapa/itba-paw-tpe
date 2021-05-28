package ar.edu.itba.paw.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "login_activity")
public class LoginActivity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "timestamp default now() not null")
    private Date date;

    public LoginActivity() {
    }

    public LoginActivity(User user) {
        this.user = user;
    }
}
