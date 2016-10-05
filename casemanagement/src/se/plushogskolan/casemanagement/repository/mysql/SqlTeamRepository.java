package se.plushogskolan.casemanagement.repository.mysql;

import java.sql.SQLException;
import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.mapper.ResultMapper;
import se.plushogskolan.casemanagement.model.Team;
import se.plushogskolan.casemanagement.repository.TeamRepository;

public final class SqlTeamRepository implements TeamRepository {
    private final String databaseUrl = "jdbc:mysql://localhost:3306/case_db?user=root&password=root&useSSL=false";
    private final ResultMapper<Team> teamMapper = (r -> Team.builder().setId(r.getInt("idteam_table"))
            .setActive(r.getBoolean("active")).build(r.getString("name")));

    @Override
    public void saveTeam(Team team) throws RepositoryException {
        final String query = "INSERT INTO team_table (name, active) VALUES (?, ?);";
        SqlHelper helper = new SqlHelper(databaseUrl);
        try {
            helper.query(query).parameter(team.getName()).parameter(team.isActive()).update();
        } catch (SQLException e) {
            throw new RepositoryException("Could not save team: " + team.toString(), e);
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
            throw new RepositoryException("Could not update team with id: " + newValues.getId(), e);
        }
    }

    @Override
    public void inactivateTeam(int teamId) throws RepositoryException {
        setTeamActiveStatus(teamId, false);
    }

    @Override
    public void activateTeam(int teamId) throws RepositoryException {
        setTeamActiveStatus(teamId, true);
    }
    
    private void setTeamActiveStatus(int teamId, boolean isActive) throws RepositoryException {
        final String query = "UPDATE team_table SET active = ? WHERE idteam_table = ?;";
        SqlHelper helper = new SqlHelper(databaseUrl);
        try {
            helper.query(query).parameter(isActive).parameter(teamId).update();
        } catch (SQLException e) {
            throw new RepositoryException("Could change active status on team with id: " + teamId, e);
        }
    }

    @Override
    public List<Team> getAllTeams() throws RepositoryException {
        final String query = "SELECT * FROM team_table";
        SqlHelper helper = new SqlHelper(databaseUrl);
        List<Team> teams;
        try {
            teams = helper.query(query).many(teamMapper);
        } catch (SQLException e) {
            throw new RepositoryException("Could not get all teams.", e);
        }
        return teams;
    }

    @Override
    public void addUserToTeam(int userId, int teamId) throws RepositoryException {
        final String query = "UPDATE user_table SET idteam = ? WHERE iduser_table = ?;";
        SqlHelper helper = new SqlHelper(databaseUrl);
        try {
            helper.query(query).parameter(teamId).parameter(userId).update();
        } catch (SQLException e) {
            throw new RepositoryException("Could not add User with id " + userId + " to Team with id " + teamId, e);
        }
    }

    public void deleteFromDatabaseTeamWithNameAndActiveStatus(String teamName, boolean teamActiveStatus)
            throws RepositoryException {
        final String query = "DELETE FROM team_table WHERE name = ? AND active = ? LIMIT 1;";
        SqlHelper helper = new SqlHelper(databaseUrl);
        try {
            helper.query(query).parameter(teamName).parameter(teamActiveStatus).update();
        } catch (SQLException e) {
            throw new RepositoryException("Could delete row with param matching teamName: \"" + teamName
                    + ", activeStatus: \"" + teamActiveStatus + "\". from in team_table in database.", e);
        }
    }
    
    public Team getTeamWithId(int teamId) throws RepositoryException {
        final String query = "SELECT * FROM team_table WHERE idteam_table = ?;";
        SqlHelper helper = new SqlHelper(databaseUrl);
        try {
            return helper.query(query).parameter(teamId).single(teamMapper);
        } catch (SQLException e) {
            throw new RepositoryException("Could get Team with id " + teamId, e);
        }
    }
    
    public boolean isTeamWithIdActive(int teamId) throws RepositoryException {
        final String query = "SELECT * FROM team_table WHERE idteam_table = ?;";
        SqlHelper helper = new SqlHelper(databaseUrl);
        try {
            Team team = helper.query(query).parameter(teamId).single(teamMapper);
            return team.isActive();
        } catch (SQLException e) {
            throw new RepositoryException("Could find Team with id " + teamId, e);
        }
    }

    public void removeTeamFromUserWithId(int userId) throws RepositoryException {
        final String query = "UPDATE user_table SET idteam = null WHERE iduser_table = ?;";
        SqlHelper helper = new SqlHelper(databaseUrl);
        try {
            helper.query(query).parameter(userId).update();
        } catch (SQLException e) {
            throw new RepositoryException("Could not remove User with id " + userId, e);
        }
    }
}
