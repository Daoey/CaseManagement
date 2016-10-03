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
    public void updateIssue(Issue newValues) throws RepositoryException {

        String update = "UPDATE issue_table SET idwork_item=?, description=? WHERE idissue_table=?";

        try {
            new SqlHelper(url).query(update).parameter(newValues.getWorkItemId()).parameter(newValues.getDescription())
                    .parameter(newValues.getId()).update();
        } catch (SQLException e) {
            throw new RepositoryException("Could not update issue " + newValues, e);
        }
    }

}
