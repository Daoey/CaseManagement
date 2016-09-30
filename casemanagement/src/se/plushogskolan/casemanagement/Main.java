package se.plushogskolan.casemanagement;

import se.plushogskolan.casemanagement.model.User;
import se.plushogskolan.casemanagement.repository.mysql.SqlIssueRepository;
import se.plushogskolan.casemanagement.repository.mysql.SqlTeamRepository;
import se.plushogskolan.casemanagement.repository.mysql.SqlUserRepository;
import se.plushogskolan.casemanagement.repository.mysql.SqlWorkItemRepository;
import se.plushogskolan.casemanagement.service.CaseService;

public final class Main {
    private static final CaseService CASE_SERVICE = createSqlCaseService();

    public static void main(String[] args) {
        int defaultId = 0;
        String defaultUsername = "Billy_the_Tester";
        String defaultFirstName = "Test";
        String defaultLastName = "Testsson";
        String newUserName = "Updated_Tester";

        // USER CRITERIAS
        // Create User
        User user = createUser(defaultId, defaultUsername, defaultFirstName, defaultLastName);

        // Update User
        updateUser(user, newUserName);
        updateUser(user, defaultUsername); // reset update

        // Inactivate User
        inactivateUser();
    }

    private static void inactivateUser() {
        // TODO Auto-generated method stub

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
