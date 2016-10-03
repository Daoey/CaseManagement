package se.plushogskolan.casemanagement.model;

public final class Team {
    // Required
    private String name;
    // Optional
    private int id;
    private boolean active;

    private Team(int id, boolean active, String name) {
        this.id = id;
        this.active = active;
        this.name = name;
    }

    public static TeamBuilder builder() {
        return new TeamBuilder();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + name.hashCode();
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
        return "Team [id=" + id + ", name=" + name + ", active=" + active + "]";
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

    public static final class TeamBuilder {
        // Optional
        private int id = 0;
        private boolean active = true;

        private TeamBuilder() {
            super();
        }

        public Team build(String name) {
            // Required name
            return new Team(id, active, name);
        }

        public TeamBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public TeamBuilder setActive(boolean active) {
            this.active = active;
            return this;
        }
    }
}
