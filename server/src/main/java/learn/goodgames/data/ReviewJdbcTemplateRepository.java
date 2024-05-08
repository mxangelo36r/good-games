package learn.goodgames.data;

import learn.goodgames.data.mappers.ReviewMapper;
import learn.goodgames.data.mappers.UserMapper;
import learn.goodgames.models.Game;
import learn.goodgames.models.Review;
import learn.goodgames.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReviewJdbcTemplateRepository implements ReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReviewJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Review> findAll() {
        final String sql = "SELECT review_id, `text`, rating, user_id, game_id FROM review;";
        return jdbcTemplate.query(sql, new ReviewMapper());
    }

    @Override
    @Transactional
    public Review findById(int reviewId) {
        final String sql = "SELECT review_id, `text`, rating, user_id, game_id FROM review " +
                "WHERE review_id = ?;";

        return jdbcTemplate.query(sql, new ReviewMapper(), reviewId).stream()
                .findFirst()
                .orElse(null);
    }

    // Probably needs try/catch for DataIntegrityViolationException
    // Maybe in GlobalExceptionHandler?
    // Is adding but new review id is +1 the expected
    @Override
    public Review add(Review review, User user, Game game) {
        // Adds review from an existing User and Game
        final String sql = "INSERT INTO review (`text`, rating, user_id, game_id) " +
                "SELECT ?, ?, ?, ? " +
                // Dual = Dummy table to select & insert data into review (gets user and game info)
                "FROM dual " +
                "WHERE NOT EXISTS (SELECT 1 FROM review " +
                "WHERE user_id = ? AND game_id = ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, review.getText());
            ps.setInt(2, review.getRating());
            ps.setInt(3, user.getUserId());
            ps.setInt(4, game.getGameId());
            ps.setInt(5, user.getUserId());
            ps.setInt(6, game.getGameId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        // Sets the key +2 from the last so added -1
        // Need to check for the following in DB via SQL:
        // - Primary key constraints
        // - Concurrent inserts happening simultaneously?
        // - Possible Triggers
        review.setReviewId(keyHolder.getKey().intValue() - 1);
        return review;
    }

    // Need edit for User & Admin
    // User: can only edit their own reviews
    // Admin: can edit all reviews

    @Override
    public boolean update(Review review, User user, Game game) {
        return false;
    }

    // Helper Methods


}
