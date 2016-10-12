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

        try {
            if (userFillsRequirements(user)) {
                userRepository.saveUser(user);
            } else {
                throw new ServiceException("Username too short or team is full" + user.toString());
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Could not save User: " + user.toString(), e);
        }
    }

    public void updateUserFirstName(int userId, String firstName) {

        try {
            User userToUpdate = userRepository.getUserById(userId);
            User updatedUser = User.builder().setFirstName(firstName).setLastName(userToUpdate.getLastName())
                    .setTeamId(userToUpdate.getTeamId()).setActive(userToUpdate.isActive()).setId(userToUpdate.getId())
                    .build(userToUpdate.getUsername());

            userRepository.updateUser(updatedUser);

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

            userRepository.updateUser(updatedUser);

        } catch (RepositoryException e) {
            throw new ServiceException("Could not update user with id: " + userId + ", new last name: " + lastName, e);
        }
    }

    public void updateUserUsername(int userId, String username) {

        try {
            User userToUpdate = userRepository.getUserById(userId);
            User updatedUser = User.builder().setFirstName(userToUpdate.getFirstName())
                    .setLastName(userToUpdate.getLastName()).setTeamId(userToUpdate.getTeamId())
                    .setActive(userToUpdate.isActive()).setId(userToUpdate.getId()).build(username);

            if (usernameLongEnough(username)) {
                userRepository.updateUser(updatedUser);
            } else {
                throw new ServiceException("Username not long enough. Username was " + username);
            }

        } catch (RepositoryException e) {
            throw new ServiceException("Could not update user with id: " + userId + ", new username: " + username, e);
        }
    }

    public void inactivateUserById(int userId) {

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

    public List<User> searchUsersBy(String firstName, String lastName, String username) {
        try {
            return userRepository.searchUsersBy(firstName, lastName, username);
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
        try {
            if (teamHasSpaceForUser(teamId, userId)) {
                teamRepository.addUserToTeam(userId, teamId);
            } else {
                throw new ServiceException("No space in team for user. userId = " + userId + "teamId = " + teamId);
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

    public void deleteWorkItem(int workItemId) {

        try {
            workItemRepository.deleteWorkItemById(workItemId);

            cleanRelatedDataOnWorkItemDelete(workItemId);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not delete WorkItem with id: " + workItemId, e);
        }
    }

    public void addWorkItemToUser(int workItemId, int userId) {

        try {
            if (userIsActive(userId) && userHasSpaceForAdditionalWorkItem(workItemId, userId)) {
                workItemRepository.addWorkItemToUser(workItemId, userId);
            } else {
                throw new ServiceException("Could not add work item to user, "
                        + "either user is inactive or there is no space for additional work items");
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
        try {
            if (workItemIsDone(issue.getWorkItemId())) {
                issueRepository.saveIssue(issue);
                workItemRepository.updateStatusById(issue.getWorkItemId(), WorkItem.Status.UNSTARTED);
            } else {
                throw new ServiceException("WorkItem does not have status done");
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Could not save Issue " + issue, e);
        }
    }

    public void updateIssueDescription(int issueId, String description) {
        try {
            Issue issueToUpdate = issueRepository.getIssueById(issueId);
            Issue updatedIssue = Issue.builder(issueToUpdate.getWorkItemId()).setId(issueId).setDescription(description)
                    .build();
            issueRepository.updateIssue(updatedIssue);
        } catch (RepositoryException e) {
            throw new ServiceException("Could not change description of issue with id: " + issueId, e);
        }
    }

    public void assignIssueToWorkItem(int issueId, int workItemId) {

        try {
            if (workItemIsDone(workItemId)) {
                Issue issueToUpdate = issueRepository.getIssueById(issueId);
                Issue updatedIssue = Issue.builder(workItemId).setId(issueId)
                        .setDescription(issueToUpdate.getDescription()).build();
                issueRepository.updateIssue(updatedIssue);
                workItemRepository.updateStatusById(workItemId, WorkItem.Status.UNSTARTED);
            } else {
                throw new ServiceException("WorkItem does not have status done");
            }
        } catch (RepositoryException e) {
            throw new ServiceException(
                    "Could not assign new work item to Issue with id " + issueId + " and work item id " + workItemId,
                    e);
        }
    }

    private boolean userFillsRequirements(User user) throws RepositoryException {
        if (!usernameLongEnough(user.getUsername())) {
            return false;
        }
        if (!teamHasSpaceForUser(user.getTeamId(), user.getId())) {
            return false;
        }
        return true;
    }

    private boolean usernameLongEnough(String username) {

        return username.length() >= 10;
    }

    private boolean teamHasSpaceForUser(int teamId, int userId) throws RepositoryException {

        if (teamId == 0) {
            return true;
        }
        return numberOfUsersInTeamLessThanTen(teamId);
    }

    private boolean numberOfUsersInTeamLessThanTen(int teamId) throws RepositoryException {
        List<User> users = userRepository.getUsersByTeamId(teamId);
        return users.size() < 10;
    }

    private void setStatusOfAllWorkItemsOfUserToUnstarted(int userId) throws RepositoryException {

        List<WorkItem> workItems = workItemRepository.getWorkItemsByUserId(userId);
        for (WorkItem workItem : workItems) {
            workItemRepository.updateStatusById(workItem.getId(), WorkItem.Status.UNSTARTED);
        }
    }

    private boolean userIsActive(int userId) throws RepositoryException {

        User user = userRepository.getUserById(userId);
        return user.isActive();
    }

    private boolean userHasSpaceForAdditionalWorkItem(int workItemId, int userId) throws RepositoryException {

        List<WorkItem> workItems = workItemRepository.getWorkItemsByUserId(userId);

        if (workItems == null) {
            return true;
        }
        for (WorkItem workItem : workItems) {
            if (workItem.getId() == workItemId) {
                return true;
            }
        }
        return workItems.size() < 5;
    }

    private boolean workItemIsDone(int workItemId) throws RepositoryException {
        WorkItem workItem = workItemRepository.getWorkItemById(workItemId);
        return WorkItem.Status.DONE.equals(workItem.getStatus());
    }

    private void cleanRelatedDataOnWorkItemDelete(int workItemId) throws RepositoryException {
        for (Issue issue : issueRepository.getIssuesByWorkItemId(workItemId))
            issueRepository.deleteIssue(issue.getId());
    }

}
