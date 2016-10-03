package se.plushogskolan.casemanagement.model;

public final class User {
    // Required
    private int id;
    private String username;
    // Optional
    private int teamId;
    private boolean isActive;
    private String firstName;
    private String lastName;

    private User(int id, boolean isActive, int teamId, String username, String firstName, String lastName) {
        this.id = id;
        this.isActive = isActive;
        this.teamId = teamId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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

        if (other instanceof User) {
            User otherUser = (User) other;
            return id == otherUser.getId() && username.equals(otherUser.getUsername());
        }
        return false;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", teamId=" + teamId + ", isActive=" + isActive
                + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getTeamId() {
        return teamId;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public static final class UserBuilder {
        // Required
        private int id;
        private String username;
        // Optional
        private int teamId = 0;
        private boolean isActive = true;
        private String firstName = "";
        private String lastName = "";
        
        private UserBuilder() {
            super();
        }

        public User build(int id, String username) {
            this.id = id;
            this.username = username;
            return new User(id, isActive, teamId, username, firstName, lastName);
        }

        public UserBuilder setTeamId(int teamId) {
            this.teamId = teamId;
            return this;
        }

        public UserBuilder setActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
    }
}
