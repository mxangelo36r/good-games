package learn.goodgames.domain;

import learn.goodgames.data.GameRepository;
import learn.goodgames.data.ReviewRepository;
import learn.goodgames.data.UserRepository;
import learn.goodgames.models.Game;
import learn.goodgames.models.Review;
import learn.goodgames.models.Role;
import learn.goodgames.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository repository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public ReviewService(ReviewRepository repository, UserRepository userRepository, GameRepository gameRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public List<Review> findAll() { return repository.findAllReviews(); }

    public List<Review> findReviewsByGameId(int gameId) {return repository.findReviewsByGameId(gameId); }

    public Review findReviewById(int reviewId) { return repository.findReviewById(reviewId); }

    public Result<Review> addReview(Review review) {
        Result<Review> result = validateAdd(review);

        if (!result.isSuccess()) {
            return result;
        }

        if (review.getReviewId() != 0) {
            result.addMessage("Review ID cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        // Might have to change this parameter if it breaks (jdbc as well)
        review = repository.addReview(review);
        result.setPayload(review);
        return result;
    }

    public Result<Review> updateReview(Review review, int userId) {
        User user = userRepository.findUserById(userId);

        Result<Review> result = validateUpdate(review, user);

        if (!result.isSuccess()) {
            return result;
        }

        if (review.getReviewId() <= 0) {
            result.addMessage("Review ID must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.updateReview(review)) {
            String msg = String.format("User ID: %s, not found", user.getUserId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

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
            return result;
        }
        return result;
    }

    private Result<Review> validateAdd(Review review) {
        Result<Review> result = new Result<>();
        List<Game> games = gameRepository.findAllGames();
        List<Review> reviews = findAll();

        if (review == null) {
            result.addMessage("Review cannot be found", ResultType.INVALID);
            return result;
        }

        if (review.getText() == null || review.getText().isBlank()) {
            result.addMessage("Text cannot be empty", ResultType.INVALID);
            return result;
        }

        if (String.valueOf(review.getGameId()).isBlank()) {
            result.addMessage("Game ID cannot be empty", ResultType.INVALID);
            return result;
        }

        if (String.valueOf(review.getUserId()).isBlank()) {
            result.addMessage("User ID cannot be empty", ResultType.INVALID);
            return result;
        }

        if (review.getRating() < 0 || review.getRating() > 10) {
            result.addMessage("Rating has to be between 1-10", ResultType.INVALID);
            return result;
        }

        Game g = games.stream()
                .filter(game -> game.getGameId() == review.getGameId())
                .findFirst()
                .orElse(null);

        if (g == null) {
            result.addMessage("Unable to find valid game", ResultType.INVALID);
            return result;
        }

        // Checks for duplicate reviews: reviewId, userId, gameId
        for (Review r : reviews) {
            if (r.equals(review)) {
                result.addMessage("Cannot add a review with the following combination: " +
                        "gameId and userId", ResultType.INVALID);
                return result;
            }
        }

        return result;
    }

    private Result<Review> validateUpdate(Review review, User user) {
        Result<Review> result = new Result<>();
        List<User> users = userRepository.findAllUsers();

        if(review == null) {
            result.addMessage("Review does not exist", ResultType.INVALID);
            return result;
        }

        if (user == null) {
            result.addMessage("User does not exist", ResultType.INVALID);
            return result;
        }

        // Checks if userId in review is the same as userId found in stream
        if (review.getUserId() != user.getUserId()) {
            result.addMessage("You can only edit your own review", ResultType.INVALID);
            return result;
        }

        if (user.getRole() != Role.ADMIN && review.getUserId() != user.getUserId()) {
            result.addMessage("You can only edit other reviews if you're ADMIN", ResultType.INVALID);
            return result;
        }

        if (review.getRating() < 0 || review.getRating() > 10) {
            result.addMessage("Rating has to be between 1-10", ResultType.INVALID);
            return result;
        }

        if (review.getText() == null || review.getText().isBlank()) {
            result.addMessage("Text cannot be empty", ResultType.INVALID);
            return result;
        }

        return result;
    }

}
