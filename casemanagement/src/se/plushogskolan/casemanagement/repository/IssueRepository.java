package se.plushogskolan.casemanagement.repository;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.Issue;

public interface IssueRepository {

    void saveIssue(Issue issue) throws RepositoryException;
    
    void updateIssue(Issue newValues) throws RepositoryException;
    
}
