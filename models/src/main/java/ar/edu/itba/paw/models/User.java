package ar.edu.itba.paw.models;

import javax.persistence.ManyToMany;
import java.util.List;
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private final int id;
    private final String name;
    private final String surname;
    private final String email;
    protected String password;
    private String profileImgB64;
    private final Date signupDate;

    private final List<Permission> permissions;

    @ManyToMany(mappedBy = "seenBy")
    List<Announcement> seenAnnouncements;

    @ManyToMany(mappedBy = "favedBy")
    List<Course> favedCourses;

    private final String careerCode;

    public User(int id, String name, String surname, String email, String password, String profileImgB64,
                Date signupDate, List<Permission> permissions, String careerCode) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.profileImgB64 = profileImgB64;
        this.signupDate = signupDate;
        this.permissions = permissions;
        this.careerCode=careerCode;
    }

    public int getId() {
        return id;
    }

    public String getFullName() { return getName() + " " + getSurname(); }

    public String getName() {
        return name != null ? normalizeCase(name) : "";
    }

    public String getSurname() {
        return surname != null ? normalizeCase(surname) : "";
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCareerCode() {
        return careerCode;
    }

    public String getProfileImgB64() {
        return profileImgB64;
    }

    public Date getSignupDate() {
        return signupDate;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public List<Course> getFavedCourses() {
        return favedCourses;
    }

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

    public void setProfileImage(String base64Image){
        this.profileImgB64 = base64Image;
    }

    public List<Announcement> getSeenAnnouncements() {
        return seenAnnouncements;
    }
}