package learn.goodgames.domain;

import learn.goodgames.data.ReviewRepository;
import learn.goodgames.models.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReviewServiceTest {

    @Autowired
    ReviewService service;

    @MockBean
    ReviewRepository repository;

    @Test
    void shouldFindAll() {
        List<Review> expected = List.of(makeDuplicateReview());
        when(repository.findAllReviews()).thenReturn(expected);
        List<Review> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllReviewsByGameId() {
        List<Review> expected = List.of(makeDuplicateReview());
        when(repository.findReviewsByGameId(1)).thenReturn(expected);
        List<Review> actual = service.findReviewsByGameId(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyArrayIfInvalidGameId() {
        List<Review> actual = service.findReviewsByGameId(19999);
        assert(actual.isEmpty());
    }

    @Test
    void shouldFindReviewByReviewId() {
        Review expected = makeDuplicateReview();
        when(repository.findReviewById(2)).thenReturn(expected);
        Review actual = service.findReviewById(2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindReviewIfInvalidReviewId() {
        Review actual = service.findReviewById(151515);
        assertNull(actual);
    }

    // Makes identical DB review for review id = 2
    private Review makeDuplicateReview() {
        Review review = new Review();
        review.setReviewId(2);
        review.setText("Nope, not for me, don't play it.");
        review.setRating(0);
        review.setUserId(6);
        review.setGameId(1);
        return review;
    }
}