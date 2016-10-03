package se.plushogskolan.casemanagement.presentation;

import java.util.List;

import se.plushogskolan.casemanagement.model.User;
import se.plushogskolan.casemanagement.repository.mysql.SqlIssueRepository;
import se.plushogskolan.casemanagement.repository.mysql.SqlTeamRepository;
import se.plushogskolan.casemanagement.repository.mysql.SqlUserRepository;
import se.plushogskolan.casemanagement.repository.mysql.SqlWorkItemRepository;
import se.plushogskolan.casemanagement.service.CaseService;

public final class UserCriteriasExample {
    // TODO clean UserCriteriasExample

    private final CaseService caseService = createSqlCaseService();
    private final int id = 0;
    private final String username = "Billy_the_Tester1";
    private final String userNameWith9Chars = "123456789";
    private final String firstName = "Test";
    private final String lastName = "Testsson";
    private final String newUsername = "Updated_Tester";
    private User user;
    private final String newLine = "\n";

    public void createUser() {
        System.out.println(newLine + "createUser()");
        
        user = createUser(id, username, firstName, lastName);
        System.out.println("User created: " + user.toString());
        
        caseService.saveUser(user);
        System.out.println("User saved");
    }

    private User createUser(int id, String username, String firstName, String lastName) {
        return User.builder().setActive(true).setFirstName(firstName).setLastName(lastName).build(id, username);
    }

    public void updateUser() {
        // Create a User with new values
        List<User> searchResult = caseService.getUserBy(user.getFirstName(), user.getLastName(), null);
        User userToEdit = getUser(searchResult);
        User userWithNewValues = createUser(userToEdit.getId(), newUsername, userToEdit.getFirstName(),
                userToEdit.getLastName());

        // Update User
        caseService.updateUser(userWithNewValues);
    }

    public void setUserInactive() {
        List<User> searchResult = caseService.getUserBy(firstName, lastName, username);
        int userId = getUserId(searchResult);
        caseService.inactivateUserById(userId);

    }

    private int getUserId(List<User> searchResult) {
        if (searchResult.size() == 1) {
            return searchResult.get(0).getId();
        } else {
            throw new IllegalArgumentException("Ambiguos searchresult");
        }
    }

    private User getUser(List<User> searchResult) {
        if (searchResult.size() == 1) {
            return searchResult.get(0);
        } else {
            throw new IllegalArgumentException("Ambiguos searchresult");
        }
    }

    public void getUserById() {
        List<User> searchResult = caseService.getUserBy(firstName, lastName, username);
        int userToGetId = getUserId(searchResult);
        User userById = caseService.getUserById(userToGetId);
    }

    public void searchUser() {
        List<User> searchedUser;
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
        List<User> searchResult = caseService.getUserBy(firstName, lastName, username);
        int idToUserToInactivate = getUserId(searchResult);
        caseService.activateUserById(idToUserToInactivate);

        // WorkItem workItem = WorkItem.builder().setDescription("Test
        // that making User inactive sets Users WorkItems
        // unstarted").setStatus(Status.)
        // caseService.addWorkItemToUser(workItem.getId(),
        // userToInactivate.getId());

        caseService.inactivateUserById(idToUserToInactivate);
    }

    private CaseService createSqlCaseService() {
        SqlUserRepository sqlUserRepository = new SqlUserRepository();
        SqlWorkItemRepository sqlWorkItemRepository = new SqlWorkItemRepository();
        SqlTeamRepository sqlTeamRepository = new SqlTeamRepository();
        SqlIssueRepository sqlIssueRepository = new SqlIssueRepository();
        return new CaseService(sqlUserRepository, sqlTeamRepository, sqlWorkItemRepository, sqlIssueRepository);
    }
}