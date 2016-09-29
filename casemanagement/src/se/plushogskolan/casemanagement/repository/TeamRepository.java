package se.plushogskolan.casemanagement.repository;

import java.util.List;

import se.plushogskolan.casemanagement.model.Team;

public interface TeamRepository {

    void saveTeam(Team team);
    
    void updateTeam(Team newValues);

    void inactivateTeam(int teamId);
    
    List<Team> getAllTeams();
    
    void addUserToTeam(int userId, int teamId);
    
}
