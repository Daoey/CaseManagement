package se.plushogskolan.casemanagement.repository;

import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.User;

public interface UserRepository {

    void saveUser(User user) throws RepositoryException;

    void updateUser(User newValues) throws RepositoryException;

    void inactivateUserById(int userId) throws RepositoryException;

    void activateUserById(int userId) throws RepositoryException;

    User getUserById(int userId) throws RepositoryException;

    List<User> searchUsersBy(String firstName, String lastName, String username) throws RepositoryException;

    List<User> getUsersByTeamId(int teamId) throws RepositoryException;

}
