package se.plushogskolan.casemanagement.repository;

public interface UserRepository {

    void saveUser(User user);

    void updateUser(User newValues);
    
    void inactivateUserById(int userId);
    
    User getUserById(int userId);
    
    User getUserBy(String firstName, String lastName, String username);
    
    List<User> getUsersByTeamId(int teamId);
    
}
