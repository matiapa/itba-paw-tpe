package ar.edu.itba.paw.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@javax.persistence.Entity(name = "User")
@Table(name = "users")
public class User implements Serializable, UserData {

    @Id
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column()
    private String surname;

    @Column(nullable = false)
    private String email;

    @Column
    protected String password;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "signup_date", nullable = false)
    private Date signupDate = new Date();
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "career_code")
    private Career career;
    
    @Column(nullable = false)
    private boolean verified = false;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "user")
    private List<Permission> permissions;
    
    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserVerification verification;

    @ManyToMany(mappedBy = "seenBy")
    private List<Announcement> seenAnnouncements;

    @ManyToMany(mappedBy = "favedBy")
    private Set<Course> favoriteCourses;

    @OneToMany(mappedBy = "rated", fetch = FetchType.LAZY)
    private List<UserWorkRate> rates;


    public User(int id, String name, String surname, String email, String password, Career career) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.career=career;
    }

    public User(){}


    public boolean can(String action, String entity){
        return getPermissions().contains(new Permission(
            Permission.Action.valueOf(action), Entity.valueOf(entity)
        ));
    }

    public boolean can(Permission.Action action, Entity entity){
        return getPermissions().contains(new Permission(
            action, entity
        ));
    }

    // TODO: Move this method

    private String normalizeCase(String str) {
        str = str.toLowerCase();
        String[] arr = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for(String s : arr) sb.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).append(" ");
        return sb.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getFullName() { return getName() + " " + getSurname(); }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return normalizeCase(name);
    }

    @Override
    public String getSurname() {
        return normalizeCase(surname);
    }

    @Override
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Date getSignupDate() {
        return signupDate;
    }

    @Override
    public Career getCareer() {
        return career;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public UserVerification getVerification() {
        return verification;
    }

    public Set<Course> getFavoriteCourses() {
        return favoriteCourses;
    }

    public List<Announcement> getSeenAnnouncements() {
        return seenAnnouncements;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setFavoriteCourses(Set<Course> favoriteCourses) {
        this.favoriteCourses = favoriteCourses;
    }

    public void setVerification(UserVerification verification) {
        this.verification = verification;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

}