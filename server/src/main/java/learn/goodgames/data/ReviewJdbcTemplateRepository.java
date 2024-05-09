package learn.goodgames.data;

import learn.goodgames.data.mappers.ReviewMapper;
import learn.goodgames.models.Game;
import learn.goodgames.models.Review;
import learn.goodgames.models.Role;
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
    public List<Review> findAllReviews() {
        final String sql = "" +
                "select r.review_id, r.`text`, r.rating, r.user_id, r.game_id, u.`name` as user_name, g.`name` as game_name, g.bgg_id " +
                    "from review r " +
                    "inner join `user` u on u.user_id = r.user_id " +
                    "inner join game g on g.game_id = r.user_id;";
        return jdbcTemplate.query(sql, new ReviewMapper());
    }

    @Override
    @Transactional
    public Review findReviewById(int reviewId) {
        final String sql = "SELECT r.review_id, r.`text`, r.rating, r.user_id, r.game_id, u.`name` as user_name, g.`name` as game_name " +
                "FROM review r " +
                "INNER JOIN `user` u ON u.user_id = r.review_id " +
                "INNER JOIN game g ON g.game_id = g.game_id " +
                "WHERE r.review_id = ?;";

        return jdbcTemplate.query(sql, new ReviewMapper(), reviewId).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Review>findReviewsByGameId(int gameId) {
        final String sql = "select r.review_id, r.`text`, r.rating, r.user_id, r.game_id, g.game_id, g.bgg_id, g.`name` as game_name, u.`name` as user_name " +
                "from review r " +
                "inner join game g on g.game_id = r.game_id " +
                "inner join user u on u.user_id = r.user_id " +
                "where r.game_id = ?;";

        return jdbcTemplate.query(sql, new ReviewMapper(), gameId);
    }

    // Probably needs try/catch for DataIntegrityViolationException
    // Maybe in GlobalExceptionHandler?
    // Is adding but new review id is +1 the expected
    @Override
    public Review addReview(Review review) {
        // Adds review from an existing User and Game
        final String sql = "INSERT INTO review (`text`, rating, user_id, game_id) " +
                "VALUES (?, ?, ?, ?);";

        // Original sql statement with User and Game parameters

//        final String sql = "INSERT INTO review (`text`, rating, user_id, game_id) " +
//                "SELECT ?, ?, ?, ? " +
//                // Dual = Dummy table to select & insert data into review (gets user and game info)
//                "FROM dual " +
//                "WHERE NOT EXISTS (SELECT 1 FROM review " +
//                "WHERE user_id = ? AND game_id = ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, review.getText());
            ps.setInt(2, review.getRating());
            ps.setInt(3, review.getUserId());
            ps.setInt(4, review.getGameId());
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

        // Setting User and Game to review:
        // Might have to get rid of User and Game parameter if it breaks
//        review.setUser(user);
//        review.setGame(game);
        review.setReviewId(keyHolder.getKey().intValue());
        return review;
    }

    // Might need to add a statement saying only User can update (if) - need to double check
    @Override
    public boolean updateReview(Review review) {
        // Can only edit their own reviews for a specific game
        final String sql = "UPDATE review SET " +
                "`text` = ?, " +
                "rating = ? " +
                "WHERE review_id = ?;";

        return jdbcTemplate.update(sql,
                review.getText(),
                review.getRating(),
                review.getReviewId()) > 0;
    }

//     Delete:
    @Override
    public boolean deleteReviewById(int reviewId) {
        return jdbcTemplate.update("delete from review where review_id = ?;", reviewId) > 0;
    }
}
