package se.plushogskolan.casemanagement.repository.mysql;

import java.sql.SQLException;
import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.WorkItem;
import se.plushogskolan.casemanagement.model.WorkItem.Status;
import se.plushogskolan.casemanagement.repository.WorkItemRepository;

public class SqlWorkItemRepository implements WorkItemRepository {

    private final String url = "jdbc:mysql://localhost:3306/case_db?user=root&password=root&useSSL=false";

    @Override
    public void saveWorkItem(WorkItem workItem) throws RepositoryException {
        try {
            new SqlHelper(url).query("insert into work_item_table (description, idstatus, iduser) values (?,?,?);")
                .parameter(workItem.getDescription())
                .parameter(workItem.getStatus().toString())
                .parameter(workItem.getUserId())
                .update();
        } catch (SQLException e) {
            throw new RepositoryException("Work Item could not be saved.", e);
        }
    }

    @Override
    public void updateStatusById(int workItemId, Status workItemStatus) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteWorkItem(int workItemId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addItemToUser(int workItemId, int userId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<WorkItem> getWorkItemsByStatus(Status workItemStatus) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<WorkItem> getWorkItemsByTeamId(int teamId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<WorkItem> getWorkItemsByUserId(int userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<WorkItem> getWorkItemsWithIssue() {
        // TODO Auto-generated method stub
        return null;
    }

}
