package se.plushogskolan.casemanagement.repository.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.Team;
import se.plushogskolan.casemanagement.repository.TeamRepository;

public final class SqlTeamRepository implements TeamRepository {
    private final String databaseUrl = "jdbc:mysql://localhost:3306/case_db?user=root&password=root&useSSL=false";

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
    public void inactivateTeam(int teamId) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Team> getAllTeams() {
        // TODO Auto-generated method stub
        return new ArrayList<>();
    }

    @Override
    public void addUserToTeam(int userId, int teamId) {
        // TODO Auto-generated method stub

    }

}
