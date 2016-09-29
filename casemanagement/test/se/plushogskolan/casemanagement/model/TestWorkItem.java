package se.plushogskolan.casemanagement.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import se.plushogskolan.casemanagement.model.WorkItem.Status;

public class TestWorkItem {

    private WorkItem workItem = WorkItem.builder(10, "Buy milk", WorkItem.Status.UNSTARTED).setId(2).build();
    private WorkItem workItemCopy = WorkItem.builder(10, "Buy milk", WorkItem.Status.UNSTARTED).setId(2).build();
    private WorkItem workItemDifferentId = WorkItem.builder(10, "Buy milk", WorkItem.Status.UNSTARTED).setId(5).build();
    private WorkItem workItemDifferentUserId = WorkItem.builder(7, "Buy milk", WorkItem.Status.UNSTARTED).setId(2)
            .build();
    private WorkItem workItemDifferentDescription = WorkItem.builder(10, "Buy cereal", WorkItem.Status.UNSTARTED)
            .setId(2).build();
    private WorkItem workItemDifferentStatus = WorkItem.builder(10, "Buy milk", WorkItem.Status.DONE).setId(2).build();

    @Test
    public void toStringAsExpected() {
        assertEquals("[WorkItem id = 2, userId = 10, description = Buy milk, Status = UNSTARTED]", workItem.toString());
    }

    @Test
    public void equalsIfSame() {
        assertEquals(workItem, workItem);
    }

    @Test
    public void equalsIfSameValues() {
        assertEquals(workItem, workItemCopy);
    }

    @Test
    public void sameHashCodeIfEquals() {
        assertEquals(workItem.hashCode(), workItemCopy.hashCode());
    }

    @Test
    public void differentIfDifferentId() {
        assertNotEquals(workItem, workItemDifferentId);
    }

    @Test
    public void differentIfDifferentUserId() {
        assertNotEquals(workItem, workItemDifferentUserId);
    }

    @Test
    public void differentIfDifferentDescription() {
        assertNotEquals(workItem, workItemDifferentDescription);
    }

    @Test
    public void differentIfDifferentStatus() {
        assertNotEquals(workItem, workItemDifferentStatus);
    }

    @Test
    public void differentIfDifferentType() {
        assertNotEquals(workItem, "workItem");
    }

    @Test
    public void gettersAndSetters() {
        WorkItem testGettersAndSetters = WorkItem
                .builder(5, "100% coverage by testing getters and setters too", Status.STARTED).setId(8).build();
        assertEquals(8, testGettersAndSetters.getId());
        assertEquals(5, testGettersAndSetters.getUserId());
        assertEquals("100% coverage by testing getters and setters too", testGettersAndSetters.getDescription());
        assertEquals(Status.STARTED, testGettersAndSetters.getStatus());
    }
}
