package se.plushogskolan.casemanagement.service;

import java.util.List;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.exception.ServiceException;
import se.plushogskolan.casemanagement.model.Issue;
import se.plushogskolan.casemanagement.model.Team;
import se.plushogskolan.casemanagement.model.User;
import se.plushogskolan.casemanagement.model.WorkItem;
import se.plushogskolan.casemanagement.repository.IssueRepository;
import se.plushogskolan.casemanagement.repository.TeamRepository;
import se.plushogskolan.casemanagement.repository.UserRepository;
import se.plushogskolan.casemanagement.repository.WorkItemRepository;

/**
 * - En User måste ha ett användarnamn som är minst 10 tecken långt. - När en
 * User inaktivares ändras status på alla dennes WorkItem till Unstarted - Det
 * får max vara 10 users i ett team - En User kan bara ingå i ett team åt gången
 * - En WorkItem kan inte tilldelas en User som är inaktiv - En User kan max ha
 * 5 WorkItems samtidigt - Ett Issue ska bara kunna läggas till work item som
 * har status Done - När en Issue läggs till en work item ändras status för
 * workitem till Unstarted
 */

public final class CaseService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final WorkItemRepository workItemRepository;
    private final IssueRepository issueRepository;

    public CaseService(UserRepository userRepository, TeamRepository teamRepository,
            WorkItemRepository workItemRepository, IssueRepository issueRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.workItemRepository = workItemRepository;
        this.issueRepository = issueRepository;
    }

    public void saveUser(User user) {
        // En User måste ha ett användarnamn som är minst 10 tecken långt
        // Det får max vara 10 users i ett team
        try {
            if (userFillsRequirements(user)) {
                userRepository.saveUser(user);
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Could not save User: " + user.toString(), e);
        }
    }

    @Deprecated
    public void updateUser(User newValues) {
        // En User måste ha ett användarnamn som är minst 10 tecken långt
        // Det får max vara 10 users i ett team
        try {
            if (userFillsRequirements(newValues)) {
                userRepository.updateUser(newValues);
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Could not update user " + newValues, e);
        }

    }

    // Uppstyckning av ovannämda funktion
    public void updateUserFirstName(int userId, String firstName) {

        try {
            User userToUpdate = userRepository.getUserById(userId);
            User updatedUser = User.builder().setFirstName(firstName).setLastName(userToUpdate.getLastName())
                    .setTeamId(userToUpdate.getTeamId()).setActive(userToUpdate.isActive()).setId(userToUpdate.getId())
                    .build(userToUpdate.getUsername());

            if (userFillsRequirements(updatedUser)) {
                userRepository.updateUser(updatedUser);
            }

        } catch (RepositoryException e) {
            throw new ServiceException("Could not update user with id: " + userId + ", new first name: " + firstName,
                    e);
        }
    }

    public void updateUserLastName(int userId, String lastName) {

        try {
            User userToUpdate = userRepository.getUserById(userId);
            User updatedUser = User.builder().setFirstName(userToUpdate.getFirstName()).setLastName(lastName)
                    .setTeamId(userToUpdate.getTeamId()).setActive(userToUpdate.isActive()).setId(userToUpdate.getId())
                    .build(userToUpdate.getUsername());

            if (userFillsRequirements(updatedUser)) {
                userRepository.updateUser(updatedUser);
            }

        } catch (RepositoryException e) {
            throw new ServiceException("Could not update user with id: " + userId + ", new last name: " + lastName,
                    e);
        }
    }

    public void updateUserUsername(int userId, String username) {

        try {
            User userToUpdate = userRepository.getUserById(userId);
            User updatedUser = User.builder().setFirstName(userToUpdate.getFirstName())
                    .setLastName(userToUpdate.getLastName()).setTeamId(userToUpdate.getTeamId())
                    .setActive(userToUpdate.isActive()).setId(userToUpdate.getId()).build(username);

            if (userFillsRequirements(updatedUser)) {
                userRepository.updateUser(updatedUser);
            }

        } catch (RepositoryException e) {
            throw new ServiceException("Could not update user with id: " + userId + ", new username: " + username,
                    e);
        }
    }

    public void inactivateUserById(int userId) { // TODO should be done with
        // db-commit?
        // När en User inaktiveras ändras status på alla dennes WorkItem
        // till Unstarted
        try {
            userRepository.inactivateUserById(userId);
            setStatusOfAllWorkItemsOfUserToUnstarted(userId);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not inactivate User with id " + userId, e);
        }
    }

    public void activateUserById(int userId) {
        try {
            userRepository.activateUserById(userId);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not activate User with id " + userId, e);
        }
    }

    public User getUserById(int userId) {
        try {
            return userRepository.getUserById(userId);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not get User by id " + userId, e);
        }
    }

    public List<User> getUserBy(String firstName, String lastName, String username) {
        try {
            return userRepository.getUserBy(firstName, lastName, username);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not get User by first name, last name, username.", e);
        }
    }

    public List<User> getUsersByTeamId(int teamId) {
        try {
            return userRepository.getUsersByTeamId(teamId);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not get User by TeamId, teamId=" + teamId, e);
        }
    }

    public void saveTeam(Team team) {
        try {
            teamRepository.saveTeam(team);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not save Team: " + team.toString(), e);
        }
    }

    public void updateTeam(Team newValues) {
        try {
            teamRepository.updateTeam(newValues);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not update Team with id " + newValues.getId(), e);
        }
    }

    public void inactivateTeam(int teamId) {
        try {
            teamRepository.inactivateTeam(teamId);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not inactivate Team with id \"" + teamId, e);
        }
    }

    public void activateTeam(int teamId) {
        try {
            teamRepository.activateTeam(teamId);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not activate Team with id \"" + teamId, e);
        }
    }

    public List<Team> getAllTeams() {
        try {
            return teamRepository.getAllTeams();
        } catch (RepositoryException e) {
            throw new ServiceException("Could not get all Teams", e);
        }
    }

    public void addUserToTeam(int userId, int teamId) {
        // Det får max vara 10 users i ett team
        try {
            if (teamHasSpace(userId, teamId)) {
                teamRepository.addUserToTeam(userId, teamId);
            }
        } catch (RepositoryException e) {
            throw new ServiceException(
                    "Could not add User with id \"" + userId + "\" to Team with id \"" + teamId + "\".", e);
        }
    }

    public void saveWorkItem(WorkItem workItem) {
        try {
            workItemRepository.saveWorkItem(workItem);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not save workItem: " + workItem.toString(), e);
        }
    }

    public void updateStatusById(int workItemId, WorkItem.Status workItemStatus) {
        try {
            workItemRepository.updateStatusById(workItemId, workItemStatus);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not update status to: \"" + workItemStatus.toString()
                    + "\" on WorkItem with id: " + workItemId, e);
        }
    }

    // Save for last
    public void deleteWorkItem(int workItemId) {
        // När ni tar bort något (exempelvis en WorkItem) behöver ni fundera på
        // hur detta ska påverka
        // eventuellt relaterad data
        try {
            workItemRepository.deleteWorkItemById(workItemId);
            // Clean before or after delete?
            cleanRelatedDataOnWorkItemDelete(workItemId);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not delete WorkItem with id: " + workItemId, e);
        }
    }

    public void addWorkItemToUser(int workItemId, int userId) {
        // En WorkItem kan inte tilldelas en User som är inaktiv
        // En User kan max ha 5 WorkItems samtidigt
        try {
            if (userIsActive(userId) && userHasSpaceForAdditionalWorkItem(workItemId, userId)) {
                workItemRepository.addWorkItemToUser(workItemId, userId);
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Could not add WorkItem " + workItemId + " to User " + userId, e);
        }

    }

    public List<WorkItem> getWorkItemsByStatus(WorkItem.Status workItemStatus) {
        try {
            return workItemRepository.getWorkItemsByStatus(workItemStatus);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not WorkItems with status " + workItemStatus, e);
        }
    }

    public List<WorkItem> getWorkItemsByTeamId(int teamId) {
        try {
            return workItemRepository.getWorkItemsByTeamId(teamId);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not get WorkItem connected to Team id " + teamId, e);
        }
    }

    public List<WorkItem> getWorkItemsByUserId(int userId) {
        try {
            return workItemRepository.getWorkItemsByUserId(userId);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not WorkItem connected to User id " + userId, e);
        }
    }

    public List<WorkItem> getWorkItemsWithIssue() {
        try {
            return workItemRepository.getWorkItemsWithIssue();
        } catch (RepositoryException e) {
            throw new ServiceException("Could not WorkItems with Issues", e);
        }
    }

    public void saveIssue(Issue issue) {
        if (workItemIsDone(issue.getWorkItemId())) {
            try {
                issueRepository.saveIssue(issue);
                workItemRepository.updateStatusById(issue.getWorkItemId(), WorkItem.Status.UNSTARTED);
            } catch (RepositoryException e) {
                throw new ServiceException("Could not save Issue " + issue, e);
            }
        }
    }

    public void updateIssueDescription(int issueId, String description) {
        try {
            issueRepository.updateIssueDescription(issueId, description);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not change description of issue with id: " + issueId, e);
        }
    }

    public void assignIssueToWorkItem(int issueId, int workItemId) {
        // Ett Issue ska bara kunna läggas till work item som har status Done
        // När en Issue läggs till en work item ändras status för workitem till
        // Unstarted
        if (workItemIsDone(workItemId)) {
            try {
                issueRepository.assignIssueToWorkItem(issueId, workItemId);
                workItemRepository.updateStatusById(workItemId, WorkItem.Status.UNSTARTED);
            } catch (RepositoryException e) {
                throw new ServiceException("Could not assign new work item to Issue with id " + issueId
                        + " and work item id " + workItemId, e);
            }
        }
    }

    private boolean userFillsRequirements(User user) throws RepositoryException {
        if (!usernameLongEnough(user.getUsername())) {
            throw new ServiceException(
                    "Username must be at least 10 characters long. Username was " + user.getUsername());
        }
        if (user.getId() < 1) {
            throw new ServiceException("User id must be positive. User id was " + user.getId());
        }
        if (!teamHasSpace(user.getId(), user.getTeamId())) {
            throw new ServiceException("User team is full. Team id " + user.getTeamId());
        }
        return true;
    }

    private boolean usernameLongEnough(String username) {
        // En User måste ha ett användarnamn som är minst 10 tecken långt
        return username.length() >= 10;
    }

    private boolean teamHasSpace(int userId, int teamId) throws RepositoryException {
        // Det får max vara 10 users i ett team
        if (teamId == 0) { // teamId = 0 means no specific team is set to User
            return true;
        }
        User user = userRepository.getUserById(userId);
        if (user.getTeamId() == teamId) {
            return true;
        }
        return numberOfUsersInTeamLessThanTen(teamId);
    }

    private boolean numberOfUsersInTeamLessThanTen(int teamId) throws RepositoryException {
        List<User> users;
        users = userRepository.getUsersByTeamId(teamId);
        return users.size() < 10;
    }

    private void setStatusOfAllWorkItemsOfUserToUnstarted(int userId) throws RepositoryException {
        // När en User inaktiveras ändras status på alla dennes WorkItem
        List<WorkItem> workItems;
        workItems = workItemRepository.getWorkItemsByUserId(userId);
        for (WorkItem workItem : workItems) {
            workItemRepository.updateStatusById(workItem.getId(), WorkItem.Status.UNSTARTED);
        }
    }

    private boolean userIsActive(int userId) {
        // En WorkItem kan inte tilldelas en User som är inaktiv
        User user;
        try {
            user = userRepository.getUserById(userId);
        } catch (RepositoryException e) {
            throw new ServiceException("Can not get user with id " + userId, e);
        }
        return user.isActive();
    }

    private boolean userHasSpaceForAdditionalWorkItem(int workItemId, int userId) throws RepositoryException {
        // En User kan max ha 5 WorkItems samtidigt
        List<WorkItem> workItems = workItemRepository.getWorkItemsByUserId(userId);
        for (WorkItem workItem : workItems) {
            if (workItem.getId() == workItemId) {
                return true;
            }
        }
        return workItems.size() < 5;
    }

    private boolean workItemIsDone(int workItemId) {

        WorkItem workItem;
        try {
            workItem = workItemRepository.getWorkItemById(workItemId);
            return WorkItem.Status.DONE.equals(workItem.getStatus());
        } catch (RepositoryException e) {
            throw new ServiceException("Could not get WorkItem with id " + workItemId, e);

        }
    }

    private void cleanRelatedDataOnWorkItemDelete(int workItemId) {
        // TODO Implement me
        try {
            for (Issue issue : issueRepository.getIssuesByWorkItemId(workItemId))
                issueRepository.deleteIssue(issue.getId());
            workItemRepository.deleteWorkItemById(workItemId);
        } catch (RepositoryException e) {
            throw new ServiceException(
                    "Could not clean data related to WorkItem " + workItemId + "when deleting WorkItem", e);
        }

    }

}
