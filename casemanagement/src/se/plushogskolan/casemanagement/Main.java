package se.plushogskolan.casemanagement;

import se.plushogskolan.casemanagement.model.User;
import se.plushogskolan.casemanagement.repository.mysql.SqlIssueRepository;
import se.plushogskolan.casemanagement.repository.mysql.SqlTeamRepository;
import se.plushogskolan.casemanagement.repository.mysql.SqlUserRepository;
import se.plushogskolan.casemanagement.repository.mysql.SqlWorkItemRepository;
import se.plushogskolan.casemanagement.service.CaseService;

public final class Main {
    private static final CaseService CASE_SERVICE = createSqlCaseService();
    private static final int ID = 0;
    private static final String USERNAME = "Billy_the_Tester";
    private static final String FIRST_NAME = "Test";
    private static final String lAST_NAME = "Testsson";
    private static final String NEW_USERNAME = "Updated_Tester";

    public static void main(String[] args) {

        // USER CRITERIAS
        // Create User
        User user = createUser(ID, USERNAME, FIRST_NAME, lAST_NAME);

        // Update User
        updateUser(user, NEW_USERNAME);
        updateUser(user, USERNAME); // reset update

        // Inactivate User
        inactivateUser();
        
        // Get User by id
        getUserById();
    }

    private static void getUserById() {
        User userToGet = CASE_SERVICE.getUserBy(FIRST_NAME, lAST_NAME, USERNAME);
        int id = userToGet.getId();
        User userById = CASE_SERVICE.getUserById(id);
    }

    private static void inactivateUser() {
        User userToInactivate = CASE_SERVICE.getUserBy(FIRST_NAME, lAST_NAME, USERNAME);
        CASE_SERVICE.inactivateUserById(userToInactivate.getId());
    }

    private static User createUser(int id, String username, String firstName, String lastName) {
        return new User.UserBuilder().setActive(true).setFirstName(firstName).setLastName(lastName).build(id, username);
    }

    private static void updateUser(User user, String newUsername) {
        // Save a user we can update later
        CASE_SERVICE.saveUser(user);

        // Create a User with new values
        User userToEdit = CASE_SERVICE.getUserBy(user.getFirstName(), user.getLastName(), null);
        User userWithNewValues = createUser(userToEdit.getId(), newUsername, userToEdit.getFirstName(),
                userToEdit.getLastName());

        // Update User
        CASE_SERVICE.updateUser(userWithNewValues);
    }

    private static CaseService createSqlCaseService() {
        SqlUserRepository sqlUserRepository = new SqlUserRepository();
        SqlWorkItemRepository sqlWorkItemRepository = new SqlWorkItemRepository();
        SqlTeamRepository sqlTeamRepository = new SqlTeamRepository();
        SqlIssueRepository sqlIssueRepository = new SqlIssueRepository();
        return new CaseService(sqlUserRepository, sqlTeamRepository, sqlWorkItemRepository, sqlIssueRepository);
    }
}
