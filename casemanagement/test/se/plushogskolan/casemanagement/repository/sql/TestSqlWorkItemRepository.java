package se.plushogskolan.casemanagement.repository.sql;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.WorkItem;
import se.plushogskolan.casemanagement.repository.mysql.SqlWorkItemRepository;

public class TestSqlWorkItemRepository {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SqlWorkItemRepository repository;
    private WorkItem workItem1;
    private WorkItem workItem2;
    private WorkItem workItem3;
    private WorkItem workItem4;
    private WorkItem workItemWithoutUser1;
    private WorkItem workItemWithoutUser2;
    private WorkItem workItemWithoutUser3;
    private WorkItem workItemWithoutUser4;
    private String description1 = "Clean room A1";
    private String description2 = "Clean room B2";
    private String description3 = "Clean room C3";
    private String description4 = "Clean room D4";
    private int userId1 = 2;
    private int userId2 = 4;
    private int userId3 = 6;
    private int userId4 = 8;

    @Before
    public void testSetUp() {
        repository = new SqlWorkItemRepository();
        workItem1 = WorkItem.builder().setDescription(description1).setStatus(WorkItem.Status.STARTED)
                .setUserId(userId1).build();
        workItem2 = WorkItem.builder().setDescription(description2).setStatus(WorkItem.Status.STARTED)
                .setUserId(userId2).build();
        workItem3 = WorkItem.builder().setDescription(description3).setStatus(WorkItem.Status.DONE).setUserId(userId3)
                .build();
        workItem4 = WorkItem.builder().setDescription(description4).setStatus(WorkItem.Status.UNSTARTED)
                .setUserId(userId4).build();

        workItemWithoutUser1 = WorkItem.builder().setDescription("Lava la ropa").setStatus(WorkItem.Status.UNSTARTED)
                .build();
        workItemWithoutUser2 = WorkItem.builder().setDescription("Hace la comida").setStatus(WorkItem.Status.UNSTARTED)
                .build();
        workItemWithoutUser3 = WorkItem.builder().setDescription("Ordena el living")
                .setStatus(WorkItem.Status.UNSTARTED).build();
        workItemWithoutUser4 = WorkItem.builder().setDescription("Limpia los baños")
                .setStatus(WorkItem.Status.UNSTARTED).build();
        try {
            repository.saveWorkItem(workItemWithoutUser1);
            repository.saveWorkItem(workItemWithoutUser2);
            repository.saveWorkItem(workItemWithoutUser3);
            repository.saveWorkItem(workItemWithoutUser4);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void canSaveWorkItem() {
        try {
            repository.saveWorkItem(workItem1);
            repository.saveWorkItem(workItem2);
            repository.saveWorkItem(workItem3);
            repository.saveWorkItem(workItem4);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void canUpdateStatusById() {
        WorkItem.Status workItemWithId1Status = WorkItem.Status.UNSTARTED;
        WorkItem.Status workItemWithId2Status = WorkItem.Status.STARTED;
        WorkItem.Status workItemWithId3Status = WorkItem.Status.STARTED;
        WorkItem.Status workItemWithId4Status = WorkItem.Status.DONE;
        try {
            repository.updateStatusById(1, workItemWithId1Status);
            repository.updateStatusById(2, workItemWithId2Status);
            repository.updateStatusById(3, workItemWithId3Status);
            repository.updateStatusById(4, workItemWithId4Status);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(repository.getWorkItemById(1).getStatus(), workItemWithId1Status);
            assertEquals(repository.getWorkItemById(2).getStatus(), workItemWithId2Status);
            assertEquals(repository.getWorkItemById(3).getStatus(), workItemWithId3Status);
            assertEquals(repository.getWorkItemById(4).getStatus(), workItemWithId4Status);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void canDeleteWorkItemById() {
        try {
            // workItem1 = repository.getWorkItemById(9);
            // workItem2 = repository.getWorkItemById(10);
            // workItem3 = repository.getWorkItemById(11);
            // workItem4 = repository.getWorkItemById(12);
            repository.deleteWorkItemById(9);
            repository.deleteWorkItemById(10);
            repository.deleteWorkItemById(11);
            repository.deleteWorkItemById(12);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void canAddWorkItemToUser() {
        try {
            repository.addWorkItemToUser(13, 1);
            repository.addWorkItemToUser(14, 2);
            repository.addWorkItemToUser(15, 3);
            repository.addWorkItemToUser(16, 4);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

    }

    // @Test
    public void canGetWorkItemsByStatus() {
        try {
            List<WorkItem> workItemList = repository.getWorkItemsByStatus(WorkItem.Status.DONE);
            for (WorkItem workItem : workItemList) {
                System.out.println(workItem.toString());
            }
            System.out.println();
            workItemList = repository.getWorkItemsByStatus(WorkItem.Status.STARTED);
            for (WorkItem workItem : workItemList) {
                System.out.println(workItem.toString());
            }
            System.out.println();
            workItemList = repository.getWorkItemsByStatus(WorkItem.Status.UNSTARTED);
            for (WorkItem workItem : workItemList) {
                System.out.println(workItem.toString());
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    // @Test
    public void shouldGetWorkItemsByTeamId() {
        try {
            List<WorkItem> workItemList;
            for (int i = 1; i <= 3; i++) {
                workItemList = repository.getWorkItemsByTeamId(i);
                for (WorkItem workItem : workItemList)
                    System.out.println(workItem.toString());
                System.out.println();
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    // @Test
    public void shouldGetWorkItemsByUserId() {
        try {
            List<WorkItem> workItems;
            for (int i = 1; i <= 4; i++) {
                workItems = repository.getWorkItemsByUserId(i);
                for (WorkItem workItem : workItems)
                    System.out.println(workItem.toString());
                System.out.println();
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    // @Test
    public void shouldGetWorkItemsWIthIssue() {
        List<WorkItem> workItems;
        try {
            workItems = repository.getWorkItemsWithIssue();
            for (WorkItem workItem : workItems)
                System.out.println(workItem.toString());
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetWorkItemById() {

    }

    @After
    public void resetDatabase() {
        try {
            for (int i = 9; i <= 32; i++)
                repository.deleteWorkItemById(i);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
}
