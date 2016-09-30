package se.plushogskolan.casemanagement;

import java.util.List;

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
    private static final String USERNAME_WITH_9_CHARS = "123456789";
    private static final String FIRST_NAME = "Test";
    private static final String LAST_NAME = "Testsson";
    private static final String NEW_USERNAME = "Updated_Tester";

    public static void main(String[] args) {

        // USER CRITERIAS
        User user = createUser(ID, USERNAME, FIRST_NAME, LAST_NAME);
        CASE_SERVICE.saveUser(user);

        updateUser(user, NEW_USERNAME);
        updateUser(user, USERNAME); // reset update

        inactivateUser();

        getUserById();

        searchForUser();

        List<User> users = CASE_SERVICE.getUsersByTeamId(1);

        usernameMustBeTenCharsLong();
    }

    private static void usernameMustBeTenCharsLong() {
        try {
            User badUser = createUser(ID, USERNAME_WITH_9_CHARS, FIRST_NAME, LAST_NAME);
            CASE_SERVICE.saveUser(badUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void searchForUser() {
        User searchedUser;
        searchedUser = CASE_SERVICE.getUserBy(FIRST_NAME, LAST_NAME, USERNAME);
        searchedUser = CASE_SERVICE.getUserBy(FIRST_NAME, LAST_NAME, null);
        searchedUser = CASE_SERVICE.getUserBy(FIRST_NAME, null, null);
        searchedUser = CASE_SERVICE.getUserBy(null, LAST_NAME, USERNAME);
        searchedUser = CASE_SERVICE.getUserBy(null, null, USERNAME);
        searchedUser = CASE_SERVICE.getUserBy(null, LAST_NAME, null);
    }

    private static void getUserById() {
        User userToGet = CASE_SERVICE.getUserBy(FIRST_NAME, LAST_NAME, USERNAME);
        int id = userToGet.getId();
        User userById = CASE_SERVICE.getUserById(id);
    }

    private static void inactivateUser() {
        User userToInactivate = CASE_SERVICE.getUserBy(FIRST_NAME, LAST_NAME, USERNAME);
        CASE_SERVICE.inactivateUserById(userToInactivate.getId());
    }

    private static User createUser(int id, String username, String firstName, String lastName) {
        return new User.UserBuilder().setActive(true).setFirstName(firstName).setLastName(lastName).build(id, username);
    }

    private static void updateUser(User user, String newUsername) {

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
