package se.plushogskolan.casemanagement.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public final class TestUser {
    private int defaultId = 1001;
    private String defaultUsername = "Mr. Youre Face";
    private User twinUser1;
    private User twinUser2;
    private User brokenUser;

    @Before
    public void setUp() throws Exception {
        twinUser1 = User.builder().setId(defaultId).build(defaultUsername);
        twinUser2 = User.builder().setId(defaultId).build(defaultUsername);
    }

    @Test
    public void testUserBuilder() {
        User.builder().setActive(false).setFirstName("Per-Erik").setLastName("Ferb").setTeamId(666).setId(defaultId)
                .build(defaultUsername);
    }

    @Test
    public void testUserEquals() {
        brokenUser = User.builder().setId(defaultId).build("Another username");

        assertEquals(twinUser1, twinUser2);

        assertNotEquals(twinUser1, brokenUser);

        assertNotEquals(twinUser1, Team.builder().build("Not a User"));
    }

    @Test
    public void testUserHashCode() {
        int anotherId = 1002;
        brokenUser = User.builder().setId(anotherId).build(defaultUsername);

        assertEquals(twinUser1.hashCode(), twinUser2.hashCode());

        assertNotEquals(twinUser1.hashCode(), brokenUser.hashCode());
    }
}
