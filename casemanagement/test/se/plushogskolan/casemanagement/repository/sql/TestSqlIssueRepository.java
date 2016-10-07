package se.plushogskolan.casemanagement.repository.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.Issue;
import se.plushogskolan.casemanagement.repository.mysql.SqlIssueRepository;

public class TestSqlIssueRepository {

    private final int workItemId = 1;
    private final String tooLongDescription = "This string is too long for inserting in the database. "
            + "It will throw a repository exception upon insertion.";
    private final SqlIssueRepository issueRepository = new SqlIssueRepository();

    @Test
    public void saveDeleteAndGetIssueByWorkItemId() throws RepositoryException {
        
        String testDescription = "This string serves as a unique description";

        List<Issue> issuesBeforeSave = issueRepository.getIssuesByWorkItemId(workItemId);

        Issue tempIssue = Issue.builder(workItemId).setDescription(testDescription).build();
        issueRepository.saveIssue(tempIssue);

        List<Issue> issuesAfterSave = issueRepository.getIssuesByWorkItemId(workItemId);
        assertEquals(issuesAfterSave.size(), issuesBeforeSave.size() + 1);

        for (Issue issue : issuesAfterSave) {
            if (testDescription.equals(issue.getDescription())) {
                assertEquals(workItemId, issue.getWorkItemId());
                issueRepository.deleteIssue(issue.getId());
            }
        }

        List<Issue> issuesAfterDelete = issueRepository.getIssuesByWorkItemId(workItemId);
        assertEquals(issuesBeforeSave.size(), issuesAfterDelete.size());
    }

    @Test
    public void updateAndGetIssueById() throws RepositoryException {
        int issueId = 1;
        Issue issueBeforeUpdate = issueRepository.getIssueById(issueId);
        Issue issueToBeUpdated = Issue.builder(2).setDescription("New description").setId(issueBeforeUpdate.getId())
                .build();
        issueRepository.updateIssue(issueToBeUpdated);
        Issue issueAfterUpdate = issueRepository.getIssueById(issueId);
        issueRepository.updateIssue(issueBeforeUpdate);
        assertEquals(issueBeforeUpdate, issueRepository.getIssueById(issueId));
        assertNotEquals(issueBeforeUpdate, issueAfterUpdate);
        assertEquals("New description", issueAfterUpdate.getDescription());
        assertEquals(1, issueAfterUpdate.getId());
        assertEquals(2, issueAfterUpdate.getWorkItemId());
    }

    @Test(expected = RepositoryException.class)
    public void updateWayTooLongDescription() throws RepositoryException {
        int issueId = 1;
        issueRepository
                .updateIssue(Issue.builder(workItemId).setDescription(tooLongDescription).setId(issueId).build());
    }

    @Test(expected = RepositoryException.class)
    public void saveWayTooLongDescription() throws RepositoryException {
        issueRepository.saveIssue(Issue.builder(workItemId).setDescription(tooLongDescription).build());
    }
}
