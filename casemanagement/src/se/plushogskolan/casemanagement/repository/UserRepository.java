package se.plushogskolan.casemanagement.repository;

import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.User;

public interface UserRepository {

    void saveUser(User user);

    void updateUser(User newValues);
    
    void inactivateUserById(int userId);
    
    User getUserById(int userId) throws RepositoryException;
    
    User getUserBy(String firstName, String lastName, String username);
    
    List<User> getUsersByTeamId(int teamId);
    
}
