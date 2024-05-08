package learn.goodgames.data;

import learn.goodgames.models.User;

import java.util.List;

public interface UserRepository {

    List<User> findAllUsers();

    User findUserById(int userId);

    User addUser(User user);

    boolean updateUser(User user);

}
