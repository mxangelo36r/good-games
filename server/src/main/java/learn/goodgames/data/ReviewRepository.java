package learn.goodgames.data;

import learn.goodgames.models.Game;
import learn.goodgames.models.Review;
import learn.goodgames.models.User;

import java.util.List;

public interface ReviewRepository {

    List<Review> findAll();

    Review findById(int reviewId);

    Review add(Review review, User user, Game game);

    boolean update(Review review, User user, Game game);
}
