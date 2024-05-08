package learn.goodgames.data;

import learn.goodgames.models.Game;
import learn.goodgames.models.Review;
import learn.goodgames.models.User;

import java.util.List;

public interface ReviewRepository {

    List<Review> findAllReviews();

    List<Review> findReviewsByGameId(int gameId);

    Review findReviewById(int reviewId);

    Review addReview(Review review, User user, Game game);

    boolean updateReview(Review review);

    boolean deleteReviewById(int reviewId);
}
