package se.plushogskolan.casemanagement.repository.sql;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.Team;
import se.plushogskolan.casemanagement.repository.mysql.SqlTeamRepository;

public final class TestSqlTeamRepository {
    private Team testTeam;
    private SqlTeamRepository sqlTeamRepository;

    @Before
    public void setUp() throws Exception {
        testTeam = Team.builder().setActive(false).build("The Testing Tests Team");
        sqlTeamRepository = new SqlTeamRepository();
    }

    @After
    public void cleanDatabase() throws RepositoryException {
        sqlTeamRepository.deleteFromDatabaseTeamWithNameAndActiveStatus(testTeam.getName(), testTeam.isActive());
    }

    @Test
    public void saveTeam() throws RepositoryException {
        sqlTeamRepository.saveTeam(testTeam);
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
