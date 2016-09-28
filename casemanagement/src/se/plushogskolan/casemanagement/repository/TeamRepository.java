package se.plushogskolan.casemanagement.repository;

public interface TeamRepository {

    void saveTeam(Team team);
    
    void updateTeam(Team newValues);

    void inactivateTeam(int teamId);
    
    List<Team> getAllTeams();
    
    void addUserToTeam(int userId, int teamId);
    
}
