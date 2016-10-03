package se.plushogskolan.casemanagement.model;

public final class Issue {

    private final int id;
    private final int workItemId;
    private final String description;

    private Issue(int id, int workItemId, String description) {
        this.id = id;
        this.workItemId = workItemId;
        this.description = description;
    }

    public static IssueBuilder builder(int workItemId) {
        return new IssueBuilder(workItemId);
    }

    @Override
    public String toString() {
        return "Issue [id = " + id + ", workItemId = " + workItemId + ", description = " + description + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Issue) {
            Issue otherIssue = (Issue) obj;
            return id == otherIssue.id && workItemId == otherIssue.workItemId
                    && description.equals(otherIssue.description);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result += 31 * id;
        result += 31 * workItemId;
        result += 31 * description.hashCode();
        return result;
    }
    
    public int getId() {
        return id;
    }
    
    public int getWorkItemId() {
        return workItemId;
    }
    
    public String getDescription() {
        return description;
    }

    public static final class IssueBuilder {
        private int id = 0;
        private int workItemId;
        private String description = "";

        private IssueBuilder(int workItemId) {
            this.workItemId = workItemId;
        }

        public IssueBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public IssueBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Issue build() {
            return new Issue(id, workItemId, description);
        }
    }
}
