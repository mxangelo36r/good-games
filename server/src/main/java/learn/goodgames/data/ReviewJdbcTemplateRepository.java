package learn.goodgames.data;

import learn.goodgames.data.mappers.ReviewMapper;
import learn.goodgames.data.mappers.UserMapper;
import learn.goodgames.models.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

}
