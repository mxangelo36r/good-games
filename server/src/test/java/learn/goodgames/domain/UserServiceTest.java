package learn.goodgames.domain;

import learn.goodgames.data.UserRepository;
import learn.goodgames.models.Role;
import learn.goodgames.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

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

    @Test
    void shouldNotAddNullUsername() {
        User user = makeUser();
        user.setUserId(0);
        user.setUsername(null);
        Result<User> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Username cannot be empty", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddNullPassword() {
        User user = makeUser();
        user.setUserId(0);
        user.setPassword(null);
        Result<User> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Password cannot be empty", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddNullEmail() {
        User user = makeUser();
        user.setUserId(0);
        user.setEmail(null);
        Result<User> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Email cannot be empty", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddNullRole() {
        User user = makeUser();
        user.setUserId(0);
        user.setRole(null);
        Result<User> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Role cannot be empty", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddDuplicateEmail() {
        List<User> all = List.of(makeUser());
        when(repository.findAll()).thenReturn(all);
        User duplicateEmail = makeUser();
        duplicateEmail.setUsername("Kevin");
        duplicateEmail.setUserId(0);

        Result<User> resultEmail = service.add(duplicateEmail);
        assertEquals(ResultType.INVALID, resultEmail.getType());
        assertEquals("Username taken. Please choose another username", resultEmail.getMessages().get(0));
    }

    @Test
    void shouldNotAddDuplicateUsername() {
        List<User> all = List.of(makeUser());
        when(repository.findAll()).thenReturn(all);
        User duplicateUsername = makeUser();
        duplicateUsername.setEmail("brissett@brissett@gmail.com");
        duplicateUsername.setUserId(0);

        Result<User> resultUsername = service.add(duplicateUsername);
        assertEquals(ResultType.INVALID, resultUsername.getType());
        assertEquals("Email taken. Please choose another email", resultUsername.getMessages().get(0));
    }

    @Test
    void shouldNotAddInvalidRole() {
        User user = makeUser();
        user.setRole(Role.OTHER);
        Result<User> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Role has to be either Admin or User", result.getMessages().get(0));
    }

    @Test
    void  shouldNotAddInvalidEmail() {
        User user = makeUser();
        user.setEmail("Inv al id@email.com");
        Result<User> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Email cannot contain spaces", result.getMessages().get(0));
    }

    @Test
    void  shouldNotAddInvalidUsername() {
        User user = makeUser();
        user.setUsername("In vali dUser name");
        Result<User> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Username cannot contain spaces", result.getMessages().get(0));
    }

    // Validating Add

    //  X User cannot be null
    //  X All fields cannot be null
    //  X Email should contain domain (.com, .co... etc.)
    //  X Email should be unique
    //  X Username should be unique
    //  X Role has to be either ADMIN or USER
    //  X Username and Email cannot contain spaces

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