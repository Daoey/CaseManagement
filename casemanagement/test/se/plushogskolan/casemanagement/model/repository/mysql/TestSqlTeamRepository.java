package se.plushogskolan.casemanagement.model.repository.mysql;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.Team;
import se.plushogskolan.casemanagement.repository.mysql.SqlTeamRepository;

public final class TestSqlTeamRepository {
    private int defaultId = 1001;
    private String defaultUsername = "Really long username";
    private Team defaultTeam;
    private SqlTeamRepository sqlTeamRepository;

    @Before
    public void setUp() throws Exception {
        defaultTeam = new Team.TeamBuilder().build(defaultId, defaultUsername);
        sqlTeamRepository = new SqlTeamRepository();
    }

    @Test
    public void testToSaveAndGetATeam() throws RepositoryException {
        sqlTeamRepository.saveTeam(defaultTeam);

        List<Team> teams = sqlTeamRepository.getAllTeams();

        boolean teamFound = false;
        for (Team teamFromRepo : teams) {
            if (defaultTeam.equals(teamFromRepo)) {
                teamFound = true;
            }
        }
        assert (teamFound);
    }
}
