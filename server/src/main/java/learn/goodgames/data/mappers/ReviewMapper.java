package learn.goodgames.data.mappers;

import learn.goodgames.models.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewMapper implements RowMapper<Review> {


    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        Review review = new Review();
        review.setReviewId(resultSet.getInt("review_id"));
        review.setText(resultSet.getString("text"));
        review.setRating(resultSet.getInt("rating"));
        review.setUserId(resultSet.getInt("user_id"));
        review.setGameId(resultSet.getInt("game_id"));
        review.setGameName(resultSet.getString("game_name"));
        review.setUserName(resultSet.getString("user_name"));
        return review;
    }
    // make another mapRow: text, rating,
}
