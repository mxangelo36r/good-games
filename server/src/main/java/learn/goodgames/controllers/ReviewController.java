package learn.goodgames.controllers;

import learn.goodgames.domain.ReviewService;
import learn.goodgames.models.Review;
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


    @GetMapping("/review/{reviewId}")
    public ResponseEntity<Object> findById(@PathVariable int reviewId) {
        Review review = service.findReviewById(reviewId);
        if (review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(review);
    }
}
