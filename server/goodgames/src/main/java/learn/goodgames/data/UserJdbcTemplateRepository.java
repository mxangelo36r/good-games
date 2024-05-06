package learn.goodgames.data;

import learn.goodgames.data.mappers.UserMapper;
import learn.goodgames.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class UserJdbcTemplateRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<User> findAll() {
        final String sql = "SELECT user_id, username, password, email FROM user;";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    @Transactional
    public User findById(int userId) {
        final String sql = "SELECT user_id, username, password, email FROM user " +
                "WHERE user_id = ?;";

        return jdbcTemplate.query(sql, new UserMapper(), userId).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public User add(User user) {
        final String sql = "INSERT into user (username, password, email) " +
                "values (?,?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public boolean update(User user) {

        final String sql = "UPDATE user SET " +
                "username = ?, " +
                "password = ?, " +
                "email = ? " +
                "WHERE user_id = ?;";

        return jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getUserId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int userId) {
        return jdbcTemplate.update("DELETE FROM user WHERE user_id = ?;", userId) > 0;
    }
}
