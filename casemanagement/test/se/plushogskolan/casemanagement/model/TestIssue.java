package se.plushogskolan.casemanagement.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class TestIssue {

    private final Issue issue = Issue.builder(2).setDescription("Store out of milk").setId(1).build();
    private final Issue issueCopy = Issue.builder(2).setDescription("Store out of milk").setId(1).build();
    private final Issue issueDifferentId = Issue.builder(2).setDescription("Store out of milk").setId(4).build();
    private final Issue issueDifferentWorkItemId = Issue.builder(8).setDescription("Store out of milk").setId(1)
            .build();
    private final Issue issueDifferentDescription = Issue.builder(2).setDescription("Store too far away").setId(1)
            .build();

    @Test
    public void toStringAsExpected() {
        assertEquals("Issue [id = 1, workItemId = 2, description = Store out of milk]", issue.toString());
    }

    @Test
    public void equalsIfSame() {
        assertEquals(issue, issue);
    }

    @Test
    public void equalsIfSameValues() {
        assertEquals(issue, issueCopy);
    }

    @Test
    public void sameHashCodeIfEquals() {
        assertEquals(issue.hashCode(), issueCopy.hashCode());
    }

    @Test
    public void differentIfDifferentId() {
        assertNotEquals(issue, issueDifferentId);
    }

    @Test
    public void differentIfDifferentWorkItemId() {
        assertNotEquals(issue, issueDifferentWorkItemId);
    }

    @Test
    public void differentIfDifferentDescription() {
        assertNotEquals(issue, issueDifferentDescription);
    }
    
    @Test
    public void differentIfDifferentType() {
        assertNotEquals(issue, "issue");
    }
    
    @Test
    public void gettersAndSetters() {
        final Issue tvProblem = Issue.builder(4).setDescription("Remote not working").setId(3).build();
        assertEquals(3, tvProblem.getId());
        assertEquals(4, tvProblem.getWorkItemId());
        assertEquals("Remote not working", tvProblem.getDescription());
    }

}
