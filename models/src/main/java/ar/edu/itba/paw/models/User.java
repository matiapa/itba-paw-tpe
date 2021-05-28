package ar.edu.itba.paw.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    @Column(name = "profile_picture")
    private String profileImgB64;

    @Column(name = "signup_date", columnDefinition = "date default now() not null")
    private Date signupDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_code")
    private Career career;
    
    @Column(columnDefinition = "boolean default false not null")
    private boolean verified;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "user")
    private List<Permission> permissions;
    
    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private UserVerification verification;

    @ManyToMany(mappedBy = "seenBy")
    private List<Announcement> seenAnnouncements;

    @OneToMany(mappedBy = "uploader")
    private List<Announcement> uploadedAnnouncements;

    @ManyToMany(mappedBy = "favedBy")
    private Set<Course> favedCourses;

    @OneToMany(mappedBy = "uploader")
    private List<Content> uploadedContent;


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

    private String normalizeCase(String str) {
        str = str.toLowerCase();
        String[] arr = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for(String s : arr) sb.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).append(" ");
        return sb.toString().trim();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImgB64() {
        return profileImgB64;
    }

    public void setProfilePicture(String profileImgB64) {
        this.profileImgB64 = profileImgB64;
    }

    public Date getSignupDate() {
        return signupDate;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public UserVerification getVerification() {
        return verification;
    }

    public void setVerification(UserVerification verification) {
        this.verification = verification;
    }

    public List<Announcement> getSeenAnnouncements() {
        return seenAnnouncements;
    }

    public void setSeenAnnouncements(List<Announcement> seenAnnouncements) {
        this.seenAnnouncements = seenAnnouncements;
    }

    public List<Announcement> getUploadedAnnouncements() {
        return uploadedAnnouncements;
    }

    public Set<Course> getFavoriteCourses() {
        return favedCourses;
    }

    public void setFavoriteCourses(Set<Course> favedCourses) {
        this.favedCourses = favedCourses;
    }

    public List<Content> getUploadedContent() {
        return uploadedContent;
    }
}