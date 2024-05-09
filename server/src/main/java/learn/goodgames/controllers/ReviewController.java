package learn.goodgames.controllers;

import learn.goodgames.domain.Result;
import learn.goodgames.domain.ReviewService;
import learn.goodgames.models.Review;
import learn.goodgames.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping
    public List<Review> findAll() { return service.findAll(); }

    @GetMapping("/game/{gameId}")
    public List<Review> findReviewsByGameId(@PathVariable int gameId) { return service.findReviewsByGameId(gameId); }

    @PostMapping("/review")
    public ResponseEntity<Object> add(@RequestBody Review review) {
        Result<Review> result = service.addReview(review);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Object> update(@PathVariable int reviewId, @RequestBody Review review) {
        if (reviewId != review.getReviewId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Review> result = service.addReview(review);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }


    @GetMapping("/review/{reviewId}")
    public ResponseEntity<Object> findById(@PathVariable int reviewId) {
        Review review = service.findReviewById(reviewId);
        if (review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{reviewId}/{userId}")
    public ResponseEntity<Object> deleteById(@PathVariable int reviewId, @PathVariable int userId) {
        Result<Review> result = service.deleteReviewById(reviewId, userId);

        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

}
