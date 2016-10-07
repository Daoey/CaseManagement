package se.plushogskolan.casemanagement.repository.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import se.plushogskolan.casemanagement.exception.RepositoryException;
import se.plushogskolan.casemanagement.model.User;
import se.plushogskolan.casemanagement.repository.mysql.SqlUserRepository;

public class TestSqlUserRepository {

    private final SqlUserRepository sqlUserRepository = new SqlUserRepository();
    private static User user;

    @BeforeClass
    public static void init() {
        user = User.builder().setFirstName("juan").setLastName("deag").build("juandeagle");

    }

    @Test
    public void saveUserTest() throws RepositoryException {

        sqlUserRepository.saveUser(user);

        List<User> userList = sqlUserRepository.searchUsersBy("juan", "deag", "juandeagle");

        assertEquals(user.getUsername(), userList.get(0).getUsername());
        assertEquals(user.getFirstName(), userList.get(0).getFirstName());
        assertEquals(user.getLastName(), userList.get(0).getLastName());
    }

    @Test
    public void updateUserTest() throws RepositoryException {

        List<User> userList = sqlUserRepository.searchUsersBy("juan", "deag", "juandeagle");
        user = userList.get(0);
        user = User.builder().setFirstName("Joakim").setLastName(user.getLastName()).setId(user.getId())
                .build(user.getUsername());

        sqlUserRepository.updateUser(user);

        assertEquals(user.getFirstName(), sqlUserRepository.getUserById(user.getId()).getFirstName());

    }

    @Test
    public void setUserActiveOrInactiveTest() throws RepositoryException {

        User user = sqlUserRepository.searchUsersBy("", "", "joakimlandstrom").get(0);

        sqlUserRepository.activateUserById(user.getId());

        assertTrue(sqlUserRepository.getUserById(user.getId()).isActive());

        sqlUserRepository.inactivateUserById(user.getId());

        assertFalse(sqlUserRepository.getUserById(user.getId()).isActive());

    }

    @Test
    public void getUserByIdTest() throws RepositoryException {

        int idTest = 1;

        assertEquals(idTest, sqlUserRepository.getUserById(1).getId());

    }

    @Test
    public void getUserByFirstNameLastNameUsernameTest() throws RepositoryException {

        List<User> list = sqlUserRepository.searchUsersBy("", "", "");

        assertEquals(5, list.size());

        list = sqlUserRepository.searchUsersBy("Joak", "La", "");

        assertEquals(1, list.size());

    }

    @Test
    public void getUserByTeamIdTest() throws RepositoryException {

        List<User> teamList1 = sqlUserRepository.getUsersByTeamId(1);
        List<User> teamList2 = sqlUserRepository.getUsersByTeamId(2);

        assertEquals(2, teamList1.size());
        assertEquals(1, teamList2.size());

    }

}
