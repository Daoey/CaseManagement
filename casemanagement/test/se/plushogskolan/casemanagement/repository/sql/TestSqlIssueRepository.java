package se.plushogskolan.casemanagement.repository.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.Issue;
import se.plushogskolan.casemanagement.repository.mysql.SqlIssueRepository;

public class TestSqlIssueRepository {

    private final int workItemId = 1;
    private final String testDescription = "This string serves as a unique description";
    private final String tooLongDescription = "This string is too long for inserting in the database. "
            + "It will throw a repository exception upon insertion.";
    private final SqlIssueRepository issueRepository = new SqlIssueRepository();
    
    @Test
    public void saveAndDeleteIssue() throws RepositoryException {

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
    public void updateIssueDescription() throws RepositoryException {
        String newDescription = "New issue description";

        Issue tempIssue = Issue.builder(workItemId).setDescription(testDescription).build();
        issueRepository.saveIssue(tempIssue);

        List<Issue> issuesBeforeUpdatedDescription = issueRepository.getIssuesByWorkItemId(workItemId);
        for (Issue issue : issuesBeforeUpdatedDescription) {
            if (testDescription.equals(issue.getDescription())) {
                issueRepository.updateIssueDescription(issue.getId(), newDescription);
            }
        }

        List<Issue> issuesAfterUpdatedDescription = issueRepository.getIssuesByWorkItemId(workItemId);
        boolean newDescriptionSetAndFound = false;
        for (Issue issue : issuesAfterUpdatedDescription) {
            if (newDescription.equals(issue.getDescription())) {
                newDescriptionSetAndFound = true;
                issueRepository.deleteIssue(issue.getId());
            }
        }
        assertTrue(newDescriptionSetAndFound);
    }

    @Test
    public void assignNewWorkItemId() throws RepositoryException {
        Issue tempIssue = Issue.builder(workItemId).setDescription(testDescription).build();
        issueRepository.saveIssue(tempIssue);

        int newWorkItemId = 5;
        List<Issue> issuesWithStandardWorkItemId = issueRepository.getIssuesByWorkItemId(workItemId);
        List<Issue> issuesWithNewWorkItemId = issueRepository.getIssuesByWorkItemId(newWorkItemId);

        Issue afterSave = null;
        for (Issue issue : issuesWithStandardWorkItemId) {
            if (testDescription.equals(issue.getDescription())) {
                afterSave = Issue.builder(issue.getWorkItemId()).setDescription(issue.getDescription())
                        .setId(issue.getId()).build();
                issueRepository.assignIssueToWorkItem(issue.getId(), newWorkItemId);
            }
        }

        List<Issue> issuesWithStandardWorkItemIdAfterAssignment = issueRepository.getIssuesByWorkItemId(workItemId);
        List<Issue> issuesWithNewWorkItemIdAfterAssignment = issueRepository.getIssuesByWorkItemId(newWorkItemId);

        assertEquals(issuesWithStandardWorkItemIdAfterAssignment.size(), issuesWithStandardWorkItemId.size() - 1);
        assertEquals(issuesWithNewWorkItemIdAfterAssignment.size(), issuesWithNewWorkItemId.size() + 1);

        Issue tempIssueAfterReAssignment = null;
        for (Issue issue : issuesWithNewWorkItemIdAfterAssignment) {
            if (testDescription.equals(issue.getDescription())) {
                tempIssueAfterReAssignment = issue;
                issueRepository.deleteIssue(issue.getId());
            }
        }

        assertEquals(afterSave.getId(), tempIssueAfterReAssignment.getId());
        assertEquals(afterSave.getDescription(), tempIssueAfterReAssignment.getDescription());
        assertNotEquals(afterSave.getWorkItemId(), tempIssueAfterReAssignment.getWorkItemId());
    }

    @Test(expected = RepositoryException.class)
    public void saveWayTooLongDescription() throws RepositoryException {
        issueRepository.saveIssue(Issue.builder(workItemId).setDescription(tooLongDescription).build());
    }

    @Test(expected = RepositoryException.class)
    public void getIssuesByWorkItemInvalidId() throws RepositoryException {
        int invalidWorkItemId = -1;
        issueRepository.getIssuesByWorkItemId(invalidWorkItemId);
    }

    @Test(expected = RepositoryException.class)
    public void assignInvalidWorkItemId() throws RepositoryException {
        int invalidWorkItemId = -1;
        issueRepository.assignIssueToWorkItem(1, invalidWorkItemId);
    }

    @Test(expected = RepositoryException.class)
    public void updateIssueDescriptionTooLongDescription() throws RepositoryException {
        issueRepository.updateIssueDescription(1, tooLongDescription);
    }
}
