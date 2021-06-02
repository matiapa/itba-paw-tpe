package ar.edu.itba.paw.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "login_activity", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"}, name = "login_activity_unique")})
public class LoginActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "login_activity_id_seq")
    @SequenceGenerator(sequenceName = "login_activity_id_seq", name = "login_activity_id_seq", allocationSize = 1)
    @Column
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private final Date date = new Date();

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