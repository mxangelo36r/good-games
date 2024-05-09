package learn.goodgames.domain;

import learn.goodgames.data.UserRepository;
import learn.goodgames.models.Role;
import learn.goodgames.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public User findById(int userId) {
        return userRepository.findUserById(userId);
    }

    public Result<User> verify(User user) {
        Result<User> result = new Result<>();

        List<User> all = userRepository.findAllUsers();
        User verfiedUser =  all.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(user.getEmail()) && u.getPassword().equalsIgnoreCase(user.getPassword()))
                .findFirst()
                .orElse(null);

        if (verfiedUser == null) {
            result.addMessage("Username or password is incorrect", ResultType.NOT_FOUND);
            return result;
        }

        result.setPayload(verfiedUser);
        return result;
    }

    public Result<User> add(User user) {
        Result<User> result = validate(user);

        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != 0) {
            result.addMessage("User ID cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        user = userRepository.addUser(user);
        result.setPayload(user);
        return result;
    }

    public Result<User> update(User user) {
        Result<User> result = validate(user);

        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() <= 0) {
            result.addMessage("User ID must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!userRepository.updateUser(user)) {
            String msg = String.format("User ID: %s, not found", user.getUserId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    // Validations:

    // User cannot be null
    // All fields cannot be null
    // Email should contain domain (.com, .co... etc.)
    // Email should be unique
    // Username should be unique
    // Role has to be either ADMIN or USER

    private Result<User> validate(User user) {
        Result<User> result = new Result<>();
        List<User> all = findAllUsers();

        if (user == null) {
            result.addMessage("User cannot be null", ResultType.INVALID);
            return result;
        }

        if (user.getEmail().isBlank() && user.getUsername().isBlank() && user.getPassword().isBlank()) {
            result.addMessage("Username, Email and Password cannot be empty", ResultType.INVALID);
            return result;
        }

        if (user.getEmail().isBlank() && user.getUsername().isBlank()) {
            result.addMessage("Username and Email cannot be empty", ResultType.INVALID);
            return result;
        }

        if (user.getEmail().isBlank() && user.getPassword().isBlank()) {
            result.addMessage("Username and Email cannot be empty", ResultType.INVALID);
            return result;
        }

        if (user.getUsername().isBlank() && user.getPassword().isBlank()) {
            result.addMessage("Username and Password cannot be empty", ResultType.INVALID);
            return result;
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            result.addMessage("Username cannot be empty", ResultType.INVALID);
            return result;
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            result.addMessage("Email cannot be empty", ResultType.INVALID);
            return result;
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            result.addMessage("Password cannot be empty", ResultType.INVALID);
            return result;
        }

        if (user.getRole() == null) {
            result.addMessage("Role cannot be empty", ResultType.INVALID);
            return result;
        }


        if (!distinctUsername(all, user)) {
            result.addMessage("Username taken. Please choose another username", ResultType.INVALID);
            return result;
        }

        if (!distinctEmail(all, user)) {
            result.addMessage("Email taken. Please choose another email", ResultType.INVALID);
            return result;
        }

        if (!containsEmail(user)) {
            result.addMessage("Enter a valid email", ResultType.INVALID);
            return result;
        }

        if (!isRoleValid(user.getRole())) {
            result.addMessage("Role has to be either Admin or User", ResultType.INVALID);
            return result;
        }

        if (!hasNoSpaces(user.getEmail())) {
            result.addMessage("Email cannot contain spaces", ResultType.INVALID);
            return result;
        }

        if (!hasNoSpaces(user.getUsername())) {
            result.addMessage("Username cannot contain spaces", ResultType.INVALID);
            return result;
        }

        return result;
    }

    // Helper Methods

    private boolean containsEmail(User user) {
        return user.getEmail().contains("@") && user.getEmail().contains(".com") ||
                user.getEmail().contains("@") && user.getEmail().contains(".co.") ||
                user.getEmail().contains("@") && user.getEmail().contains(".net") ||
                user.getEmail().contains("@") && user.getEmail().contains(".org");
    }

    private static boolean distinctUsername(List<User> users, User user) {
        return users.stream()
                .noneMatch(u -> u.getUsername().equals(user.getUsername()));
    }

    private static boolean distinctEmail(List<User> users, User user) {
        return users.stream()
                .noneMatch(u -> u.getEmail().equals(user.getEmail()));
    }

    private static boolean isRoleValid(Role role) {
        return role == Role.USER || role == Role.ADMIN;
    }

    private static boolean hasNoSpaces(String string) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(string);
        return !matcher.find();
    }

}
