package ar.edu.itba.paw.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "permission")
@IdClass(Permission.PermissionId.class)
public class Permission {

    public enum Action {create, read, update, delete}

    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Action action;

    @Id
    @Enumerated(EnumType.STRING)
    @Column
    private Entity entity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @Column(name = "user_id", insertable = false, updatable = false)
    private int userId;


    public Permission(Action action, Entity entity) {
        this.action = action;
        this.entity = entity;
    }

    public Permission() { }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return action == that.action && entity == that.entity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, entity);
    }

    @Override
    public String toString() {
        return (entity+"."+ action).toUpperCase();
    }


    public static class PermissionId implements Serializable {

        private int userId;
        private Action action;
        private Entity entity;

        public PermissionId(){}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PermissionId that = (PermissionId) o;
            return userId == that.userId && action == that.action && entity == that.entity;
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, action, entity);
        }
    }

}