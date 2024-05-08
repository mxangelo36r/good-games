package learn.goodgames.data;

import learn.goodgames.models.Review;

import java.util.List;

public interface ReviewRepository {

    List<Review> findAll();
}
