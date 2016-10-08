package se.plushogskolan.casemanagement.repository;

import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.WorkItem;

public interface WorkItemRepository {

    void saveWorkItem(WorkItem workItem) throws RepositoryException;
    
    void updateStatusById(int workItemId, WorkItem.Status workItemStatus) throws RepositoryException;
    
    void deleteWorkItemById(int workItemId) throws RepositoryException;
    
    void addWorkItemToUser(int workItemId, int userId) throws RepositoryException;
    
    List<WorkItem> getWorkItemsByStatus(WorkItem.Status workItemStatus) throws RepositoryException;
    
    List<WorkItem> getWorkItemsByTeamId(int teamId) throws RepositoryException;
    
    List<WorkItem> getWorkItemsByUserId(int userId) throws RepositoryException;
 
    List<WorkItem> getWorkItemsWithIssue() throws RepositoryException;
    
    WorkItem getWorkItemById(int workItemId) throws RepositoryException;
}
