package se.plushogskolan.casemanagement.repository.sql;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.Team;
import se.plushogskolan.casemanagement.repository.mysql.SqlTeamRepository;

public final class TestSqlTeamRepository {
    private Team testTeam;
    private List<Team> teams;
    private SqlTeamRepository sqlTeamRepository;

    @Before
    public void setUp() throws Exception {
        testTeam = Team.builder().setActive(false).build("The Testing Tests Team");
        sqlTeamRepository = new SqlTeamRepository();
    }

    @After
    public void clean() throws RepositoryException {
        sqlTeamRepository.deleteFromDatabaseTeamWithNameAndActiveStatus(testTeam.getName(), testTeam.isActive());
        teams = null;
    }

    @Test
    public void saveTeam() throws RepositoryException {
        sqlTeamRepository.saveTeam(testTeam);

        teams = sqlTeamRepository.getAllTeams();

        boolean ourTeamFoundInDatabase = false;
        for (Team team : teams) {
            if (testTeam.getName().equals(team.getName())) ourTeamFoundInDatabase = true;
        }
        assertEquals(true, ourTeamFoundInDatabase);
    }

    @Test
    public void updateTeam() throws RepositoryException {
    }

    @Test
    public void inactivateTeam() throws RepositoryException {
    }

    @Test
    public void getAllTeams() throws RepositoryException {
    }

    @Test
    public void addUserToTeam() throws RepositoryException {
    }
}
