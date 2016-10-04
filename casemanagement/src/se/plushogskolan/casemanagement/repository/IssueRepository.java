package se.plushogskolan.casemanagement.repository;

import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.Issue;

public interface IssueRepository {

    void saveIssue(Issue issue) throws RepositoryException;

    void assignIssueToWorkItem(int issueId, int workItemId) throws RepositoryException;

    void updateIssueDescription(int issueId, String description) throws RepositoryException;

    void deleteIssue(int issueId) throws RepositoryException;

    List<Issue> getIssuesByWorkItemId(int workItemId) throws RepositoryException;

}
