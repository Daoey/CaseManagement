package se.plushogskolan.casemanagement.repository;

import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.WorkItem;

public interface WorkItemRepository {

    void saveWorkItem(WorkItem workItem) throws RepositoryException;
    
    void updateStatusById(int workItemId, WorkItem.Status workItemStatus);
    
    //Save for last
    void deleteWorkItem(int workItemId);
    
    void addItemToUser(int workItemId, int userId);
    
    List<WorkItem> getWorkItemsByStatus(WorkItem.Status workItemStatus);
    
    List<WorkItem> getWorkItemsByTeamId(int teamId);
    
    List<WorkItem> getWorkItemsByUserId(int userId);
 
    List<WorkItem> getWorkItemsWithIssue();
}
