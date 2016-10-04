package se.plushogskolan.casemanagement.repository.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.Team;
import se.plushogskolan.casemanagement.repository.mysql.SqlTeamRepository;

public final class TestSqlTeamRepository {
    private String teamNameForTesting = "Team for testing SqlTeamRepo";
    private boolean statusForTesting = true;
    private Team defaultTeam;
    private SqlTeamRepository sqlTeamRepository;

    @Before
    public void setUp() throws Exception {
        defaultTeam = Team.builder().setActive(statusForTesting).build(teamNameForTesting);
        sqlTeamRepository = new SqlTeamRepository();

        checkThatOurTeamNotAlreadyInDatabase();
        sqlTeamRepository.saveTeam(defaultTeam);

        defaultTeam = getTestTeamFromDatabase();
    }

    private void checkThatOurTeamNotAlreadyInDatabase() throws RepositoryException {
        List<Team> teams = sqlTeamRepository.getAllTeams();
        for (Team team : teams) {
            if (teamNameForTesting.equals(team.getName())) {
                fail();
            }
        }
    }

    @After
    public void cleanDatabase() throws RepositoryException {
        sqlTeamRepository.updateTeam(Team.builder().setId(defaultTeam.getId()).setActive(statusForTesting)
                .build(teamNameForTesting));
        sqlTeamRepository.deleteFromDatabaseTeamWithNameAndStatus(teamNameForTesting, statusForTesting);
    }

    @Test
    public void testUpdateTeam() throws RepositoryException {
        String newTeamNameForTesting = "New Team for testing SqlTeamRepo.updateTeam()";
        Team testTeamBeforeUpdate = getTestTeamFromDatabase();

        Team newValues = Team.builder().setActive(false).setId(testTeamBeforeUpdate.getId())
                .build(newTeamNameForTesting);

        sqlTeamRepository.updateTeam(newValues);

        Team testTeamAfterUpdate = getTestTeamFromDatabaseWithNewName(newTeamNameForTesting);

        assertEquals(newValues, testTeamAfterUpdate);
        assertNotEquals(testTeamBeforeUpdate, testTeamAfterUpdate);
    }

    private Team getTestTeamFromDatabase() throws RepositoryException {
        List<Team> teams = sqlTeamRepository.getAllTeams();
        for (Team team : teams) {
            if (teamNameForTesting.equals(team.getName())) {
                return team;
            }
        }
        return null;
    }

    private Team getTestTeamFromDatabaseWithNewName(String newName) throws RepositoryException {
        List<Team> teams = sqlTeamRepository.getAllTeams();
        for (Team team : teams) {
            if (newName.equals(team.getName())) {
                return team;
            }
        }
        return null;
    }

    @Test
    public void testInactivateTeam() throws RepositoryException {
        // TODO assert before and after
        sqlTeamRepository.inactivateTeam(4);
    }

    @Test
    public void testGetAllTeams() throws RepositoryException {
        // TODO assert before and after
        List<Team> teams = sqlTeamRepository.getAllTeams();
        if (teams.size() < 1) {
            fail();
        }
    }

    @Test
    public void testAddUserToTeam() throws RepositoryException {
        // TODO assert before and after
        sqlTeamRepository.addUserToTeam(2, 1);
    }
}
