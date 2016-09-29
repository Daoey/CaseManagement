package se.plushogskolan.casemanagement.model;

import java.util.ArrayList;
import java.util.List;

public final class Team {
    // Required
    private int id;
    private String name;
    // Optional
    private boolean active;
    private List<User> users;

    private Team(int id, boolean active, String name, List<User> users) {
        this.id = id;
        this.active = active;
        this.name = name;
        this.users = users;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object other) {

        if (this == other) {
            return true;
        }

        if (null == other) {
            return false;
        }

        if (other instanceof Team) {
            Team otherTeam = (Team) other;
            return id == otherTeam.getId() && name.equals(otherTeam.getName());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Team [id=" + id + ", name=" + name + ", active=" + active + ", users=" + users + "]";
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public static final class TeamBuilder {
        // Required
        private int id;
        private String name;
        // Optional
        private boolean active = true;
        private List<User> users = new ArrayList<>();

        public Team build(int id, String name) {
            return new Team(id, active, name, users);
        }

        public TeamBuilder setActive(boolean active) {
            this.active = active;
            return this;
        }

        public TeamBuilder setUsers(List<User> users) {
            if (users.size() > 10) {
                throw new IllegalArgumentException("Teams are not allowed to have more than 10 Users");
            }
            this.users = users;
            return this;
        }
    }
}
