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
        List<User> users = repository.findAll();
        assertNotNull(users);
        assertEquals(6, users.size());
    }

    @Test
    void shouldFindEachId() {
        User kevin = repository.findById(1);
        assertEquals("Kevin", kevin.getUsername());
        assertEquals("kevin", kevin.getPassword());
        assertEquals("kevin@kevin.com", kevin.getEmail());
        assertEquals(Role.ADMIN, kevin.getRole());

        User miguel = repository.findById(2);
        assertEquals("Miguel", miguel.getUsername());
        assertEquals("miguel", miguel.getPassword());
        assertEquals("miguel@miguel.com", miguel.getEmail());
        assertEquals(Role.ADMIN, miguel.getRole());

        User headley = repository.findById(3);
        assertEquals("Headly", headley.getUsername());
        assertEquals("headley", headley.getPassword());
        assertEquals("headley@headley.com", headley.getEmail());
        assertEquals(Role.ADMIN, headley.getRole());
    }
}