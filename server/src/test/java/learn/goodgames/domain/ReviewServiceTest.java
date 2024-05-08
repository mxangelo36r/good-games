package learn.goodgames.domain;

import learn.goodgames.data.ReviewRepository;
import learn.goodgames.data.UserRepository;
import learn.goodgames.models.Review;
import learn.goodgames.models.Role;
import learn.goodgames.models.User;
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

    @MockBean
    UserRepository userRepository;

    @Test
    void shouldFindAll() {
        List<Review> expected = List.of(makeReview1());
        when(repository.findAllReviews()).thenReturn(expected);
        List<Review> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllReviewsByGameId() {
        List<Review> expected = List.of(makeReview1());
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
        Review expected = makeReview1();
        when(repository.findReviewById(2)).thenReturn(expected);
        Review actual = service.findReviewById(2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindReviewIfInvalidReviewId() {
        Review actual = service.findReviewById(151515);
        assertNull(actual);
    }

    @Test
    void shouldDeleteReviewIfIWroteReview() {
//        when(repository.deleteReviewById(1)).thenReturn(true);
//        Result<Review> result = service.deleteReviewById(1, 1);
//
//        assertEquals(ResultType.SUCCESS, result.getType());

    }

    @Test
    void shouldDeleteAnyReviewIfAdmin() {

    }

    @Test
    void shouldNotDeleteWhenNotOwnUserReview() {

    }

    Review makeReview1() {
        Review review = new Review();
        review.setReviewId(2);
        review.setText("Nope, not for me, don't play it.");
        review.setRating(0);
        review.setUserId(1);
        review.setGameId(1);
        return review;
    }

    User makeUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("userTest");
        user.setPassword("passwordTest");
        user.setEmail("test@user.com");
        user.setRole(Role.USER);
        return user;
    }

    User makeAdmin() {
        User user = new User();
        user.setUserId(2);
        user.setUsername("adminTest");
        user.setPassword("passwordTest");
        user.setEmail("test@admin.com");
        user.setRole(Role.ADMIN);
        return user;
    }
}