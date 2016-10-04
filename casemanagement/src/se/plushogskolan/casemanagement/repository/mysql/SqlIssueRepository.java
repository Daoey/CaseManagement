package se.plushogskolan.casemanagement.repository.mysql;

import java.sql.SQLException;
import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.mapper.ResultMapper;
import se.plushogskolan.casemanagement.model.Issue;
import se.plushogskolan.casemanagement.repository.IssueRepository;

public final class SqlIssueRepository implements IssueRepository {

    private static final ResultMapper<Issue> ISSUE_MAPPER = (r -> Issue
            .builder(Integer.parseInt(r.getString("idwork_item"))).setId(Integer.parseInt(r.getString("idissue_table")))
            .setDescription(r.getString("description")).build());
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
    public void updateIssueDescription(int issueId, String description) throws RepositoryException {
        String update = "UPDATE issue_table SET description=? WHERE idissue_table=?";
        try {
            new SqlHelper(url).query(update).parameter(description).parameter(issueId).update();
        } catch (SQLException e) {
            throw new RepositoryException("Could not change issue description. issueId = " + issueId, e);
        }
    }

    @Override
    public void deleteIssue(int issueId) throws RepositoryException {
        String delete = "DELETE FROM issue_table WHERE idissue_table=?";
        try {
            new SqlHelper(url).query(delete).parameter(issueId).update();
        } catch (SQLException e) {
            throw new RepositoryException("Could not delete issue. issueId = " + issueId, e);
        }

    }

    @Override
    public List<Issue> getIssuesByWorkItemId(int workItemId) throws RepositoryException {
        try {
            return new SqlHelper(url).query("SELECT * FROM issue_table WHERE idwork_item = ?;").parameter(workItemId)
                    .many(ISSUE_MAPPER);
        } catch (SQLException e) {
            throw new RepositoryException("Could not get Issues by WorkItemId. id = " + workItemId, e);
        }
    }

}
