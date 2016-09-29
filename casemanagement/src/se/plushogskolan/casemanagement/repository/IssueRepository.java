package se.plushogskolan.casemanagement.repository;

import se.plushogskolan.casemanagement.model.Issue;

public interface IssueRepository {

    void saveIssue(Issue issue);
    
    void updateIssue(Issue newValues);
    
}
