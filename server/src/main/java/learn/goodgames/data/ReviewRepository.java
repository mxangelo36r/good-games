package learn.goodgames.data;

import learn.goodgames.models.Game;
import learn.goodgames.models.Review;
import learn.goodgames.models.User;

import java.util.List;

public interface ReviewRepository {

    List<Review> findAllReviews();

    Review findReviewById(int reviewId);

    Review addReview(Review review, User user, Game game);

    boolean updateReview(Review review);

    boolean deleteReviewUser(Review review, User user);

    boolean deleteReviewAdmin(Review review, User user);

}
