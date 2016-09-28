package se.plushogskolan.casemanagement.repository;

public interface IssueRepository {

    void saveIssue(Issue issue);
    
    void updateIssue(Issue newValues);
    
}
