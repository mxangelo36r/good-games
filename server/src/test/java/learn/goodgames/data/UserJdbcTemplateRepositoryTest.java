package learn.goodgames.data;

import learn.goodgames.models.User;
import learn.goodgames.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcTemplateRepositoryTest {

    @Autowired
    UserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<User> users = repository.findAllUsers();
        assertNotNull(users);
        assertTrue(users.size() >= 6);
    }

    @Test
    void shouldFindEachId() {
        User kevin = repository.findUserById(1);
        assertEquals("Kevin", kevin.getUsername());
        assertEquals("kevin", kevin.getPassword());
        assertEquals("kevin@kevin.com", kevin.getEmail());
        assertEquals(Role.ADMIN, kevin.getRole());

        User miguel = repository.findUserById(2);
        assertEquals("Miguel", miguel.getUsername());
        assertEquals("miguel", miguel.getPassword());
        assertEquals("miguel@miguel.com", miguel.getEmail());
        assertEquals(Role.ADMIN, miguel.getRole());

        User headley = repository.findUserById(3);
        assertEquals("Headly", headley.getUsername());
        assertEquals("headley", headley.getPassword());
        assertEquals("headley@headley.com", headley.getEmail());
        assertEquals(Role.ADMIN, headley.getRole());
    }

    @Test
    void shouldAddNewUser() {
        User user = makeUser();
        User actual = repository.addUser(user);
        assertNotNull(actual);
        assertEquals(7, actual.getUserId());
    }

    @Test
    void shouldUpdateExistingUser() {
        User user = makeUser();
        user.setUserId(1);
        assertTrue(repository.updateUser(user));
    }

    // Helper Methods

    User makeUser() {
        User user = new User();
        user.setUsername("userTest");
        user.setPassword("passwordTest");
        user.setEmail("test@user.com");
        user.setRole(Role.USER);
        return user;
    }

}