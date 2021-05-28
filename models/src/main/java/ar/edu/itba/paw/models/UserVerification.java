package ar.edu.itba.paw.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_verification")
public class UserVerification {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "verification_code", nullable = false)
    private Integer code;
    
    public UserVerification() { }

    public UserVerification(User user, Integer code) {
        this.user = user;
        this.code = code;
    }
    
    public Integer getCode() {
        return code;
    }
}
