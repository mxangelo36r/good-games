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

}
