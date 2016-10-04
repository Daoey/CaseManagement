package se.plushogskolan.casemanagement.repository;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.Issue;

public interface IssueRepository {

    void saveIssue(Issue issue) throws RepositoryException;
        
    void assignIssueToWorkItem(int issueId, int workItemId) throws RepositoryException;
    
    void changeIssueDescription(int issueId, String description) throws RepositoryException;
    
}
