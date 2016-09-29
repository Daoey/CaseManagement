package se.plushogskolan.casemanagement.repository.mysql;

import java.sql.SQLException;
import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.Team;
import se.plushogskolan.casemanagement.repository.TeamRepository;

public final class SqlTeamRepository implements TeamRepository {
    private final String databaseUrl = "jdbc:mysql://localhost:3306/case_db?user=root&password=root&useSSL=false";
    private final ResultMapper<Team> teamMapper = (r -> new Team.TeamBuilder().setId(r.getInt("idteam_table"))
            .setActive(r.getBoolean("active")).build(r.getString("name")));

    @Override
    public void saveTeam(Team team) throws RepositoryException {
        final String query = "insert into team_table (name, active) values (?, ?);";
        SqlHelper helper = new SqlHelper(databaseUrl);
        try {
            helper.query(query).parameter(team.getName()).parameter(team.isActive()).update();
        } catch (SQLException e) {
            throw new RepositoryException("Could not save team: \"" + team.toString() + "\"", e);
        }
    }

    @Override
    public void updateTeam(Team newValues) throws RepositoryException {
        final String query = "UPDATE team_table SET name = ?, active = ? WHERE idteam_table = ?;";
        SqlHelper helper = new SqlHelper(databaseUrl);
        try {
            helper.query(query).parameter(newValues.getName()).parameter(newValues.isActive())
                    .parameter(newValues.getId()).update();
        } catch (SQLException e) {
            throw new RepositoryException("Could not update team with id: \"" + newValues.getId() + "\"", e);
        }
    }

    @Override
    public void inactivateTeam(int teamId) throws RepositoryException {
        final String query = "UPDATE team_table SET active = ? WHERE idteam_table = ?;";
        SqlHelper helper = new SqlHelper(databaseUrl);
        try {
            helper.query(query).parameter(false).parameter(teamId).update();
        } catch (SQLException e) {
            throw new RepositoryException("Could not inactivate team with id: \"" + teamId + "\"", e);
        }
    }

    @Override
    public List<Team> getAllTeams() throws RepositoryException {
        final String query = "select * from team_table";
        SqlHelper helper = new SqlHelper(databaseUrl);
        List<Team> teams;
        try {
            teams = helper.query(query).many(teamMapper);
        } catch (SQLException e) {
            throw new RepositoryException("Could get all teams.", e);
        }
        return teams;
    }

    @Override
    public void addUserToTeam(int userId, int teamId) {
        // TODO Auto-generated method stub

    }
}
