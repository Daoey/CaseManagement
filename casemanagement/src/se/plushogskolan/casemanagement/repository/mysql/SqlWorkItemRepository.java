package se.plushogskolan.casemanagement.repository.mysql;

import java.sql.SQLException;
import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.mapper.ResultMapper;
import se.plushogskolan.casemanagement.model.WorkItem;
import se.plushogskolan.casemanagement.model.WorkItem.Status;
import se.plushogskolan.casemanagement.repository.WorkItemRepository;

public final class SqlWorkItemRepository implements WorkItemRepository {

    private static final ResultMapper<WorkItem> WORK_ITEM_MAPPER = (r -> WorkItem.builder()
            .setId(Integer.parseInt(r.getString("idwork_item_table")))
            .setUserId(Integer.parseInt(r.getString("iduser"))).setStatus(Integer.parseInt(r.getString("idstatus")))
            .setDescription(r.getString("description")).build());
    private final String url = "jdbc:mysql://localhost:3306/case_db?user=root&password=root&useSSL=false";

    @Override
    public void saveWorkItem(WorkItem workItem) throws RepositoryException {
        try {
            new SqlHelper(url).query("INSERT INTO work_item_table (description, idstatus, iduser) VALUES (?,?,?);")
                    .parameter(workItem.getDescription()).parameter(workItem.getStatus().ordinal() + 1)
                    .parameter(workItem.getUserId()).update();
        } catch (SQLException e) {
            throw new RepositoryException("Could not save WorkItem: " + workItem.toString(), e);
        }
    }

    @Override
    public void updateStatusById(int workItemId, Status workItemStatus) throws RepositoryException {
        int sqlIndex = workItemStatus.ordinal() + 1;
        try {
            new SqlHelper(url).query("UPDATE work_item_table SET idstatus = ? WHERE idwork_item_table = ?;")
                    .parameter(sqlIndex).parameter(workItemId).update();
        } catch (SQLException e) {
            throw new RepositoryException(
                    "Could not update status on workItem with id " + workItemId + " to " + workItemStatus.toString(),
                    e);
        }
    }

    @Override
    public void deleteWorkItemById(int workItemId) throws RepositoryException {
        try {
            new SqlHelper(url).query("DELETE FROM work_item_table WHERE idwork_item_table = ?;").parameter(workItemId)
                    .update();
        } catch (SQLException e) {
            throw new RepositoryException("Could not delete Work Item with id " + workItemId, e);
        }
    }

    @Override
    public void addWorkItemToUser(int workItemId, int userId) throws RepositoryException {
        try {
            new SqlHelper(url).query("UPDATE work_item_table SET iduser = ? WHERE idwork_item_table = ?;")
                    .parameter(userId).parameter(workItemId).update();
        } catch (SQLException e) {
            throw new RepositoryException("Could not add WorkItem with id " + workItemId + " to User with id " + userId,
                    e);
        }
    }

    @Override
    public List<WorkItem> getWorkItemsByStatus(Status workItemStatus) throws RepositoryException {
        int sqlIndex = workItemStatus.ordinal() + 1;
        try {
            return new SqlHelper(url).query("SELECT * FROM work_item_table WHERE idstatus = ?;").parameter(sqlIndex)
                    .many(WORK_ITEM_MAPPER);
        } catch (SQLException e) {
            throw new RepositoryException(
                    "Could not get Work Items by Status. Status asked for: " + workItemStatus.toString(), e);
        }
    }

    @Override
    public List<WorkItem> getWorkItemsByTeamId(int teamId) throws RepositoryException {
        try {
            return new SqlHelper(url)
                    .query("SELECT * FROM work_item_table WHERE iduser IN "
                            + "(SELECT iduser_table FROM user_table WHERE idteam = ?);")
                    .parameter(teamId).many(WORK_ITEM_MAPPER);
        } catch (SQLException e) {
            throw new RepositoryException("Could not get Work Item by TeamId. Team id: " + teamId, e);
        }
    }

    @Override
    public List<WorkItem> getWorkItemsByUserId(int userId) throws RepositoryException {
        try {
            return new SqlHelper(url).query("SELECT * FROM work_item_table WHERE iduser = ?;").parameter(userId)
                    .many(WORK_ITEM_MAPPER);
        } catch (SQLException e) {
            throw new RepositoryException("Could not get Work Items by User id " + userId, e);
        }
    }

    @Override
    public List<WorkItem> getWorkItemsWithIssue() throws RepositoryException {
        try {
            return new SqlHelper(url).query("SELECT * FROM work_item_table WHERE idwork_item_table IN "
                    + "(SELECT idwork_item FROM issue_table);").many(WORK_ITEM_MAPPER);
        } catch (SQLException e) {
            throw new RepositoryException("Could not get Work Items with Issues.", e);
        }
    }

    @Override
    public WorkItem getWorkItemById(int workItemId) throws RepositoryException {
        try {
            return new SqlHelper(url).query("SELECT * FROM work_item_table WHERE idwork_item_table = ?;")
                    .parameter(workItemId).single(WORK_ITEM_MAPPER);
        } catch (SQLException e) {
            throw new RepositoryException("Could not get WorkItem by id " + workItemId, e);
        }
    }
}