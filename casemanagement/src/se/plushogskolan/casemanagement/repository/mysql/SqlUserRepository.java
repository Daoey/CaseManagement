package se.plushogskolan.casemanagement.repository.mysql;

import java.sql.SQLException;
import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.User;
import se.plushogskolan.casemanagement.repository.UserRepository;

public class SqlUserRepository implements UserRepository {

    private final String url = "jdbc:mysql://localhost:3306/case_db?user=root&password=root&useSSL=false";

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
                + "idteam = (select idteam_table from team_table where idteam_table = ?)"
                + "where iduser_table = ?";

        try {
            new SqlHelper(url).query(query).parameter(newValues.getFirstName()).parameter(newValues.getLastName())
                    .parameter(newValues.getUsername()).parameter(newValues.isActive()).parameter(newValues.getTeamId())
                    .parameter(newValues.getId()).update();

        } catch (SQLException e) {
            throw new RepositoryException("Couldnt update user with id: " + newValues.getId(), e);
        }

    }

    @Override
    public void inactivateUserById(int userId) {

        String query = "";
        
    }

    @Override
    public User getUserById(int userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User getUserBy(String firstName, String lastName, String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> getUsersByTeamId(int teamId) {
        // TODO Auto-generated method stub
        return null;
    }

}
