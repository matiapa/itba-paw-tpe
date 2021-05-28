package ar.edu.itba.paw.models;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "users")
public class User implements Serializable {

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
    
    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private UserVerification verification;

    @ManyToMany(mappedBy = "seenBy")
    private List<Announcement> seenAnnouncements;

    @OneToMany(mappedBy = "uploader")
    private List<Announcement> uploadedAnnouncements;

    @ManyToMany(mappedBy = "favedBy")
    private List<Course> favedCourses;

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

    public void setId(int id) {
        this.id = id;
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

    public void setEmail(String email) {
        this.email = email;
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

    public void setProfileImgB64(String profileImgB64) {
        this.profileImgB64 = profileImgB64;
    }

    public Date getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(Date signupDate) {
        this.signupDate = signupDate;
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

    public void setUploadedAnnouncements(List<Announcement> uploadedAnnouncements) {
        this.uploadedAnnouncements = uploadedAnnouncements;
    }

    public List<Course> getFavedCourses() {
        return favedCourses;
    }

    public void setFavedCourses(List<Course> favedCourses) {
        this.favedCourses = favedCourses;
    }

    public List<Content> getUploadedContent() {
        return uploadedContent;
    }

    public void setUploadedContent(List<Content> uploadedContent) {
        this.uploadedContent = uploadedContent;
    }
}