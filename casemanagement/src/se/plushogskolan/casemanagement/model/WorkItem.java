package se.plushogskolan.casemanagement.model;

public final class WorkItem {

    private final int id;
    private final int userId;
    private final String description;

    private WorkItem(int id, int userId, String description) {
        this.id = id;
        this.userId = userId;
        this.description = description;
    }

    public static WorkItemBuilder builder(int userId, String description) {
        return new WorkItemBuilder(userId, description);
    }

    @Override
    public String toString() {
        return "[id = " + id + ", userId = " + userId + ", description = " + description + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof WorkItem) {
            WorkItem otherWorkItem = (WorkItem) obj;
            return id == otherWorkItem.id && userId == otherWorkItem.userId
                    && description.equals(otherWorkItem.description);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result += 31 * id;
        result += 31 * userId;
        result += 31 * description.hashCode();
        return result;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public static class WorkItemBuilder {
        private int id = 0;
        private int userId;
        private String description;

        private WorkItemBuilder(int userId, String description) {
            this.userId = userId;
            this.description = description;
        }

        public WorkItemBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public WorkItem build() {
            return new WorkItem(id, userId, description);
        }

    }
}
