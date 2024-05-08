package learn.goodgames.domain;

import learn.goodgames.data.ReviewRepository;
import learn.goodgames.data.UserRepository;
import learn.goodgames.models.Review;
import learn.goodgames.models.Role;
import learn.goodgames.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository repository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Review> findAll() { return repository.findAllReviews(); }

    public List<Review> findReviewsByGameId(int gameId) {return repository.findReviewsByGameId(gameId); }

    public Review findReviewById(int reviewId) { return repository.findReviewById(reviewId); }

    public Result<Review> addReview(Review review) {
        Result<Review> result = new Result<>();
        return result;
    }

    public Result<Review> updateReview(Review review) {
        Result<Review> result = new Result<>();
        return result;
    }

    public Result<Review> deleteReviewById(int reviewId, int userId) {
        Review review = repository.findReviewById(reviewId);
        User user = userRepository.findUserById(userId);
        // Validate both review and user are owned by the same person OR that the user is an admin
        Result<Review> result = validateDelete(review, user);

        if(!repository.deleteReviewById(reviewId)) {
            result.addMessage("review not found", ResultType.NOT_FOUND);
        }
        return result;
    }

    private Result<Review> validateDelete(Review review, User user) {
        Result<Review> result = new Result<>();

        if(review == null) {
            result.addMessage("review not found", ResultType.INVALID);
            return result;
        }

        if (user == null) {
            result.addMessage("user not found", ResultType.INVALID);
            return result;
        }

        if(user.getRole() != Role.ADMIN && review.getUserId() != user.getUserId()) {
            result.addMessage("you can only delete reviews that you have written", ResultType.INVALID);
        }

        return result;
    }
}
