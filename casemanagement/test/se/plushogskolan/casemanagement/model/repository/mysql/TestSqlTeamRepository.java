package se.plushogskolan.casemanagement.model.repository.mysql;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.Team;
import se.plushogskolan.casemanagement.repository.mysql.SqlTeamRepository;

/**
 * 
 * @deprecated because not done. Test results must now be checked manually in database
 *
 */
@Deprecated
public final class TestSqlTeamRepository {
    private String defaultUsername = "Really long username";
    private Team defaultTeam;
    private SqlTeamRepository sqlTeamRepository;

    @Before
    public void setUp() throws Exception {
        defaultTeam = new Team.TeamBuilder().setActive(true).build(defaultUsername);
        sqlTeamRepository = new SqlTeamRepository();
    }

    @Test
    public void testSaveTeam() throws RepositoryException {
        // TODO assert before and after
        sqlTeamRepository.saveTeam(defaultTeam);
    }

    @Test
    public void testUpdateTeam() throws RepositoryException {
        // TODO make this test runnable many times by resetting values @after or
        // maybe mock db
        // TODO check values before
        Team newValues = new Team.TeamBuilder().setActive(true).setId(3).build("Alles neus name again");
        // TODO assert values after update
        // TODO reset values in @after
        sqlTeamRepository.updateTeam(newValues);
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
