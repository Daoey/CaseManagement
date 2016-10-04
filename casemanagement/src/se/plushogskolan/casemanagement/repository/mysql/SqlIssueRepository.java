package se.plushogskolan.casemanagement.repository.mysql;

import java.sql.SQLException;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.Issue;
import se.plushogskolan.casemanagement.repository.IssueRepository;

public final class SqlIssueRepository implements IssueRepository {

    private final String url = "jdbc:mysql://localhost:3306/case_db?user=root&password=root&useSSL=false";

    @Override
    public void saveIssue(Issue issue) throws RepositoryException {

        String insert = "INSERT INTO issue_table (idwork_item, description) VALUES (?,?)";

        try {
            new SqlHelper(url).query(insert).parameter(issue.getWorkItemId()).parameter(issue.getDescription())
                    .update();
        } catch (SQLException e) {
            throw new RepositoryException("Could not save issue " + issue, e);
        }

    }

    @Override
    public void assignIssueToWorkItem(int issueId, int workItemId) throws RepositoryException {
        String update = "UPDATE issue_table SET idwork_item=? WHERE idissue_table=?";
        try {
            new SqlHelper(url).query(update).parameter(workItemId).parameter(issueId).update();
        } catch (SQLException e) {
            throw new RepositoryException(
                    "Could not assign new work item id to issue. issueId = " + issueId + ", workItemId = " + workItemId,
                    e);
        }
    }

    @Override
    public void changeIssueDescription(int issueId, String description) throws RepositoryException {
        String update = "UPDATE issue_table SET description=? WHERE idissue_table=?";
        try {
            new SqlHelper(url).query(update).parameter(description).parameter(issueId).update();
        } catch (SQLException e) {
            throw new RepositoryException("Could not change issue description. issueId = " + issueId, e);
        }
    }

}
