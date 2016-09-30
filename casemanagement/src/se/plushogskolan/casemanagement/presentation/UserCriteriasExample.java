package se.plushogskolan.casemanagement.presentation;

import java.util.List;

import se.plushogskolan.casemanagement.model.User;
import se.plushogskolan.casemanagement.repository.mysql.SqlIssueRepository;
import se.plushogskolan.casemanagement.repository.mysql.SqlTeamRepository;
import se.plushogskolan.casemanagement.repository.mysql.SqlUserRepository;
import se.plushogskolan.casemanagement.repository.mysql.SqlWorkItemRepository;
import se.plushogskolan.casemanagement.service.CaseService;

public final class UserCriteriasExample {

    private final CaseService caseService = createSqlCaseService();
    private final int id = 0;
    private final String username = "Billy_the_Tester";
    private final String userNameWith9Chars = "123456789";
    private final String firstName = "Test";
    private final String lastName = "Testsson";
    private final String newUsername = "Updated_Tester";
    private User user;

    public void createUser() {
        user = createUser(id, username, firstName, lastName);
        caseService.saveUser(user);
    }

    private User createUser(int id, String username, String firstName, String lastName) {
        return new User.UserBuilder().setActive(true).setFirstName(firstName).setLastName(lastName).build(id, username);
    }

    public void updateUser() {
        // Create a User with new values
        User userToEdit = caseService.getUserBy(user.getFirstName(), user.getLastName(), null);
        User userWithNewValues = createUser(userToEdit.getId(), newUsername, userToEdit.getFirstName(),
                userToEdit.getLastName());

        // Update User
        caseService.updateUser(userWithNewValues);
    }

    public void setUserInactive() {
        User userToInactivate = caseService.getUserBy(firstName, lastName, username);
        caseService.inactivateUserById(userToInactivate.getId());
    }

    public void getUserById() {
        User userToGet = caseService.getUserBy(firstName, lastName, username);
        int id = userToGet.getId();
        User userById = caseService.getUserById(id);
    }

    public void searchUser() {
        User searchedUser;
        searchedUser = caseService.getUserBy(firstName, lastName, username);
        searchedUser = caseService.getUserBy(firstName, lastName, null);
        searchedUser = caseService.getUserBy(firstName, null, null);
        searchedUser = caseService.getUserBy(null, lastName, username);
        searchedUser = caseService.getUserBy(null, null, username);
        searchedUser = caseService.getUserBy(null, lastName, null);
    }

    public void getUserFromSpecificTeam() {
        List<User> users = caseService.getUsersByTeamId(1);
    }

    public void usernameLengthRestriction() {
        try {
            User badUser = createUser(id, userNameWith9Chars, firstName, lastName);
            caseService.saveUser(badUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUserInactiveSetsUserWorkitemsUnstarted() {
        User userToInactivate = caseService.getUserBy(firstName, lastName, username);
        caseService.activateUserById(userToInactivate.getId());

        // WorkItem workItem = WorkItem.builder().setDescription("Test
        // that making User inactive sets Users WorkItems
        // unstarted").setStatus(Status.)
        // caseService.addWorkItemToUser(workItem.getId(),
        // userToInactivate.getId());

        caseService.inactivateUserById(userToInactivate.getId());
    }

    private CaseService createSqlCaseService() {
        SqlUserRepository sqlUserRepository = new SqlUserRepository();
        SqlWorkItemRepository sqlWorkItemRepository = new SqlWorkItemRepository();
        SqlTeamRepository sqlTeamRepository = new SqlTeamRepository();
        SqlIssueRepository sqlIssueRepository = new SqlIssueRepository();
        return new CaseService(sqlUserRepository, sqlTeamRepository, sqlWorkItemRepository, sqlIssueRepository);
    }
}