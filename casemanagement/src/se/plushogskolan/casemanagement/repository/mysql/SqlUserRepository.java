package se.plushogskolan.casemanagement.repository.mysql;

import java.util.List;

import se.plushogskolan.casemanagement.model.User;
import se.plushogskolan.casemanagement.repository.UserRepository;

public class SqlUserRepository implements UserRepository{

    private final String url = "jdbc:mysql://localhost:3306/case_db?user=root&password=root&useSSL=false";

    @Override
    public void saveUser(User user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateUser(User newValues) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void inactivateUserById(int userId) {
        // TODO Auto-generated method stub
        
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
