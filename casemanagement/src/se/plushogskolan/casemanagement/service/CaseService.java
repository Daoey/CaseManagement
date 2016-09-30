package se.plushogskolan.casemanagement.service;

import java.util.List;

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
        // TODO
    }

    public void updateUser(User newValues) {
        // TODO
    }

    public void inactivateUserById(int userId) {
        // TODO
    }

    public User getUserById(int userId) {
        // TODO
        return null;
    }

    public User getUserBy(String firstName, String lastName, String username) {
        // TODO
        return null;
    }

    public List<User> getUsersByTeamId(int teamId) {
        // TODO
        return null;
    }

    public void saveTeam(Team team) {
        // TODO
    }

    public void updateTeam(Team newValues) {
        // TODO
    }

    public void inactivateTeam(int teamId) {
        // TODO
    }

    public List<Team> getAllTeams() {
        // TODO
        return null;
    }

    public void addUserToTeam(int userId, int teamId) {
        // TODO
    }

    public void saveWorkItem(WorkItem workItem) {
        // TODO
    }

    public void updateStatusById(int workItemId, WorkItem.Status workItemStatus) {
        // TODO
    }

    // Save for last
    public void deleteWorkItem(int workItemId) {
        // TODO
    }

    public void addWorkItemToUser(int workItemId, int userId) {
        // TODO
    }

    public List<WorkItem> getWorkItemsByStatus(WorkItem.Status workItemStatus) {
        // TODO
        return null;
    }

    public List<WorkItem> getWorkItemsByTeamId(int teamId) {
        // TODO
        return null;
    }

    public List<WorkItem> getWorkItemsByUserId(int userId) {
        // TODO
        return null;
    }

    public List<WorkItem> getWorkItemsWithIssue() {
        // TODO
        return null;
    }

    public void saveIssue(Issue issue) {
        // TODO
    }

    public void updateIssue(Issue newValues) {
        // TODO
    }

}
