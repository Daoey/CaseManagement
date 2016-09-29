package se.plushogskolan.casemanagement.repository.mysql;

import java.sql.SQLException;

import se.plushogskolan.casemanagement.model.Issue;
import se.plushogskolan.casemanagement.repository.IssueRepository;

public class SqlIssueRepository implements IssueRepository {

    private final String url = "jdbc:mysql://localhost:3306/case_db?user=root&password=root&useSSL=false";

    @Override
    public void saveIssue(Issue issue) {

        String insert = "INSERT INTO issue_table (idwork_item, description) VALUES (?,?)";

        try {
            new SqlHelper(url).query(insert).parameter(issue.getWorkItemId()).parameter(issue.getDescription())
                    .update();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void updateIssue(Issue newValues) {

        String update = "UPDATE issue_table SET idwork_item=?, description=? WHERE idissue_table=?";

        try {
            new SqlHelper(url).query(update).parameter(newValues.getWorkItemId()).parameter(newValues.getDescription())
                    .parameter(newValues.getId()).update();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
