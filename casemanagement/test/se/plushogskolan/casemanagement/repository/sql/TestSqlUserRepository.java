package se.plushogskolan.casemanagement.repository.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
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

        List<User> userList = sqlUserRepository.getUserBy("juan", "deag", "juandeagle");

        assertEquals(user.getUsername(), userList.get(0).getUsername());
        assertEquals(user.getFirstName(), userList.get(0).getFirstName());
        assertEquals(user.getLastName(), userList.get(0).getLastName());
    }

    @Test
    public void updateUserTest() throws RepositoryException {

        List<User> userList = sqlUserRepository.getUserBy("juan", "deag", "juandeagle");
        user = userList.get(0);
        user = User.builder().setFirstName("joakim").setLastName(user.getLastName()).setId(user.getId())
                .build(user.getUsername());

        sqlUserRepository.updateUser(user);

        assertEquals(user.getFirstName(), sqlUserRepository.getUserById(user.getId()).getFirstName());

    }

    @Test
    public void setUserActiveOrInactiveTest() throws RepositoryException {

        User user = sqlUserRepository.getUserBy("", "", "joakimlandstrom").get(0);

        sqlUserRepository.activateUserById(user.getId());

        assertTrue(sqlUserRepository.getUserById(user.getId()).isActive());

        sqlUserRepository.inactivateUserById(user.getId());

        assertFalse(sqlUserRepository.getUserById(user.getId()).isActive());

    }

    @Test
    public void getUserByIdTest() throws RepositoryException {

        int idTest = 1;

        assertEquals(1, sqlUserRepository.getUserById(1).getId());

    }

}
