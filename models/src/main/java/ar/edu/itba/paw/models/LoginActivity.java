package ar.edu.itba.paw.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "login_activity")
@IdClass(LoginActivity.LoginActivityId.class)
public class LoginActivity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @Column(nullable = false)
    private final Date date = new Date();

    @Id
    @Column(name = "user_id", insertable = false, updatable = false)
    private int userId;


    public LoginActivity() { }

    public LoginActivity(User user) {
        this.user = user;
    }


    public static class LoginActivityId implements Serializable {

        private int userId;
        private Date date;

        public LoginActivityId(){}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LoginActivityId that = (LoginActivityId) o;
            return userId == that.userId && Objects.equals(date, that.date);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, date);
        }
    }

}