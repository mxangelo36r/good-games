package learn.goodgames.domain;

import learn.goodgames.data.ReviewRepository;
import learn.goodgames.models.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository repository;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public List<Review> findAll() { return repository.findAllReviews(); }

    public List<Review> findReviewsByGameId(int gameId) {return repository.findReviewsByGameId(gameId); }

    public Review findReviewById(int reviewId) { return repository.findReviewById(reviewId); }

}
