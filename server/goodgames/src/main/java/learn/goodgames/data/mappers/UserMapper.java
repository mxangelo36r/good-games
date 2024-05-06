package learn.goodgames.data.mappers;

import learn.goodgames.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}
