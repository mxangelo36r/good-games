package learn.goodgames.domain;

import learn.goodgames.data.UserRepository;
import learn.goodgames.models.Role;
import learn.goodgames.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int userId) {
        return userRepository.findById(userId);
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

        user = userRepository.add(user);
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

        if (!userRepository.update(user)) {
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

        if (user == null) {
            result.addMessage("User cannot be null", ResultType.INVALID);
            return result;
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            result.addMessage("Username cannot be empty", ResultType.INVALID);
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

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            result.addMessage("Email cannot be empty", ResultType.INVALID);
            return result;
        }

        if (!distinctUsername(userRepository.findAll())) {
            result.addMessage("Email taken. Please choose another email.", ResultType.INVALID);
            return result;
        }

        if (!distinctEmail(userRepository.findAll())) {
            result.addMessage("Username take. Please choose another email", ResultType.INVALID);
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

        return result;
    }

    // Helper Methods

    public boolean containsEmail(User user) {
        return user.getEmail().contains("@") && user.getEmail().contains(".com") ||
                user.getEmail().contains("@") && user.getEmail().contains(".co.") ||
                user.getEmail().contains("@") && user.getEmail().contains(".net") ||
                user.getEmail().contains("@") && user.getEmail().contains(".org");
    }

    public static boolean distinctUsername(List<User> users) {
        return users.stream()
                .allMatch(user -> users.stream()
                        .filter(u -> u.getUsername().equals(user.getUsername())).count() == 1);
    }

    public static boolean distinctEmail(List<User> users) {
        return users.stream()
                .allMatch(user -> users.stream()
                        .filter(u -> u.getEmail().equals(user.getEmail())).count() == 1);
    }

    public static boolean isRoleValid(Role role) {
        return role == Role.USER || role == Role.ADMIN;
    }




}
