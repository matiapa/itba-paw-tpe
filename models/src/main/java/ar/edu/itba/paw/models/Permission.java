package ar.edu.itba.paw.models;

import java.util.Objects;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "permission")
public class Permission {

    public enum Action {create, read, update, delete}

    // TODO: Put a composed key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_id_seq")
    @SequenceGenerator(sequenceName = "permission_id_seq", name = "permission_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
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