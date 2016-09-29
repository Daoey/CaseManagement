package se.plushogskolan.casemanagement.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import se.plushogskolan.casemanagement.model.WorkItem.Status;

public class TestWorkItem {
    private WorkItem workItem = WorkItem.builder().setId(2).setUserId(10).setDescription("Buy milk")
            .setStatus(WorkItem.Status.UNSTARTED).build();
    private WorkItem workItemCopy = WorkItem.builder().setId(2).setUserId(10).setDescription("Buy milk")
            .setStatus(WorkItem.Status.UNSTARTED).build();
    private WorkItem workItemDifferentId = WorkItem.builder().setId(5).setUserId(10).setDescription("Buy milk")
            .setStatus(WorkItem.Status.UNSTARTED).build();
    private WorkItem workItemDifferentUserId = WorkItem.builder().setId(2).setUserId(7).setDescription("Buy milk")
            .setStatus(WorkItem.Status.UNSTARTED).build();
    private WorkItem workItemDifferentDescription = WorkItem.builder().setId(2).setUserId(10)
            .setDescription("Buy cereal").setStatus(WorkItem.Status.UNSTARTED).build();
    private WorkItem workItemDifferentStatus = WorkItem.builder().setId(2).setUserId(10).setDescription("Buy milk")
            .setStatus(WorkItem.Status.DONE).build();

    @Test
    public void toStringAsExpected() {
        assertEquals("WorkItem [id = 2, userId = 10, description = Buy milk, Status = UNSTARTED]", workItem.toString());
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
        WorkItem testGettersAndSetters = WorkItem.builder().setId(8).setUserId(5)
                .setDescription("increase code coverage by testing getters and setters too").setStatus(Status.STARTED)
                .build();
        assertEquals(8, testGettersAndSetters.getId());
        assertEquals(5, testGettersAndSetters.getUserId());
        assertEquals("increase code coverage by testing getters and setters too",
                testGettersAndSetters.getDescription());
        assertEquals(Status.STARTED, testGettersAndSetters.getStatus());
        //Superficial just to cover enum code coverage in Jacoco
        Status.valueOf(Status.STARTED.toString());
    }
}
