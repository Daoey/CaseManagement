package se.plushogskolan.casemanagement.repository;

public interface WorkItemRepository {

    void saveWorkItem(WorkItemRepository workItem);
    
    void updateStatus(WorkItemStatus workItemStatus);
    
    //Save for last
    void deleteWorkItem(int workItemId);
    
    void addItemToUser(int workItemId, int userId);
    
    List<WorkItem> getWorkItemsByStatus(WorkItemStatus workItemStatus);
    
    List<WorkItem> getWorkItemsByTeamId(int teamId);
    
    List<WorkItem> getWorkItemsByUserId(int userId);
 
    List<WorkItem> getWorkItemsWithIssue();
}
