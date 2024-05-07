package learn.goodgames.domain;

import learn.goodgames.data.UserRepository;
import learn.goodgames.models.Role;
import learn.goodgames.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    @Test
    void shouldFindMockUser() {
        User expected = makeUser();
        when(repository.findById(1)).thenReturn(expected);
        User actual = service.findById(1);
        assertEquals(expected, actual);
    }


    // Mock Data

    User makeUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("userTest");
        user.setPassword("passwordTest");
        user.setEmail("test@user.com");
        user.setRole(Role.USER);
        return user;
    }

}