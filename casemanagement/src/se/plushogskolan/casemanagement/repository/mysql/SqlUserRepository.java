package se.plushogskolan.casemanagement.repository.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.User;
import se.plushogskolan.casemanagement.model.User.UserBuilder;
import se.plushogskolan.casemanagement.repository.UserRepository;

public class SqlUserRepository implements UserRepository {

    private final String url = "jdbc:mysql://localhost:3306/case_db?user=root&password=root&useSSL=false";

    private ResultMapper<User> userMapper = (u -> new UserBuilder().setFirstName(u.getString("first_name"))
            .setLastName(u.getString("last_name")).setActive(u.getBoolean("active")).setTeamId(u.getInt("idteam"))
            .build(u.getInt("iduser_table"), u.getString("username")));

    private boolean nullCheck(String checkedString) {

        if (checkedString.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public void saveUser(User user) throws RepositoryException {

        String query = "insert into user_table set first_name=?, last_name=?, username=?, active=?, "
                + "idteam = (select idteam_table from team_table where idteam_table = ?)";

        try {
            new SqlHelper(url).query(query).parameter(user.getFirstName()).parameter(user.getLastName())
                    .parameter(user.getUsername()).parameter(user.isActive()).parameter(user.getTeamId()).update();
        } catch (SQLException e) {
            throw new RepositoryException("Coulnt insert user with id: " + user.getId(), e);
        }
    }

    @Override
    public void updateUser(User newValues) throws RepositoryException {

        String query = "update user_table set first_name=?, last_name=?, username=?, active=?, "
                + "idteam = (select idteam_table from team_table where idteam_table=?)" + "where iduser_table = ?";

        try {
            new SqlHelper(url).query(query).parameter(newValues.getFirstName()).parameter(newValues.getLastName())
                    .parameter(newValues.getUsername()).parameter(newValues.isActive()).parameter(newValues.getTeamId())
                    .parameter(newValues.getId()).update();

        } catch (SQLException e) {
            throw new RepositoryException("Couldnt update user with id: " + newValues.getId(), e);
        }

    }

    @Override
    public void inactivateUserById(int userId) throws RepositoryException {

        String query = "update user_table set active=? where iduser_table=?";

        try {
            new SqlHelper(url).query(query).parameter(false).parameter(userId).update();

        } catch (SQLException e) {
            throw new RepositoryException("Couldnt set user " + userId + " inactive", e);
        }

    }

    @Override
    public User getUserById(int userId) throws RepositoryException {

        String query = "select iduser_table, first_name, last_name, username, active, idteam " + "from user_table "
                + "where iduser_table = ?";

        try {
            return new SqlHelper(url).query(query).parameter(userId).single(userMapper);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RepositoryException("Couldnt get user with userId: " + userId, e);
        }
    }

    @Override
    public List<User> getUserBy(String firstName, String lastName, String username) throws RepositoryException {

        String query = "select * from user_table where instr(first_name, ?) > 0 and instr(last_name, ?) > 0 "
                + "and instr(username, ?) > 0;";

        try {
            return new SqlHelper(url).query(query).parameter(firstName).parameter(lastName).parameter(username)
                    .many(userMapper);
        } catch (SQLException e) {
            String exceptionMessage = String.format("Couldnt fetch Users from %s , %s , %s", firstName, lastName,
                    username);
            throw new RepositoryException(exceptionMessage, e);
        }
    }

    @Override
    public List<User> getUsersByTeamId(int teamId) throws RepositoryException {

        String query = "select * from user_table" + "where idteam = ?";

        try {
            return new SqlHelper(url).query(query).parameter(teamId).many(userMapper);
        } catch (SQLException e) {
            throw new RepositoryException("Couldnt get users by team id: " + teamId, e);
        }
    }

}
