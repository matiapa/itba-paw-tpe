package ar.edu.itba.paw.models;

import java.util.Objects;

public class Permission {

    public enum Action { CREATE, READ, UPDATE, DELETE }

    private final Action action;
    private final Entity entity;

    public Permission(Action action, Entity entity) {
        this.action = action;
        this.entity = entity;
    }

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
        return entity+"."+ action;
    }

}