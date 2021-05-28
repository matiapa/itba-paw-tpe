package ar.edu.itba.paw.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "permission")
public class Permission {

    public enum Action {create, read, update, delete}

    @Column(nullable = false)
    private Action action;

    @Enumerated(EnumType.STRING)
    @Column
    private Entity entity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

}