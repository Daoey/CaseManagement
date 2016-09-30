package se.plushogskolan.casemanagement;

import se.plushogskolan.casemanagement.presentation.UserCriteriasExample;

public final class Main {

    public static void main(String[] args) {
        
        UserCriteriasExample userExample = new UserCriteriasExample();
        
        userExample.createUser();
        
        userExample.updateUser();
        
        userExample.setUserInactive();
        
        userExample.getUserById();
        
        userExample.getUserFromSpecificTeam();
        
        userExample.usernameLengthRestriction();
        
        userExample.setUserInactiveSetsUserWorkitemsUnstarted();
    }
}
