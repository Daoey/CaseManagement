package se.plushogskolan.casemanagement.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public final class TestTeam {
    private final int defaultId = 1;
    private final String defaultName = "Regular ordinary team";
    private final int anotherId = 13376;
    private final String anotherName = "We are unique!";
    private Team teamTwin1;
    private Team teamTwin2;
    private Team anotherTeam;

    @Before
    public void setup() {
        teamTwin1 = new Team.TeamBuilder().setId(defaultId).build(defaultName);
        teamTwin2 = new Team.TeamBuilder().setId(defaultId).build(defaultName);
    }

    @Test
    public void testTeamEqual() {

        assertEquals(teamTwin1, teamTwin2);

        anotherTeam = new Team.TeamBuilder().build(defaultName);
        assertNotEquals(teamTwin1, anotherTeam);

        anotherTeam = new Team.TeamBuilder().build(anotherName);
        assertNotEquals(teamTwin1, anotherTeam);
    }

    @Test
    public void testTeamHashCode() {
        assertEquals(teamTwin1.hashCode(), teamTwin2.hashCode());

        anotherTeam = new Team.TeamBuilder().build(defaultName);
        assertNotEquals(teamTwin1.hashCode(), anotherTeam.hashCode());

        anotherTeam = new Team.TeamBuilder().build(anotherName);
        assertNotEquals(teamTwin1.hashCode(), anotherTeam.hashCode());
    }

    @Test
    public void testTeamBuilder() {
        List<User> users = new ArrayList<>();
        users.add(new User.UserBuilder().build(1001, "long username"));
        new Team.TeamBuilder().setActive(false).build(defaultName);
    }
}
