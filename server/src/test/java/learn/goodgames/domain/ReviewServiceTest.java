package learn.goodgames.domain;

import learn.goodgames.data.GameRepository;
import learn.goodgames.data.ReviewRepository;

import learn.goodgames.data.mappers.GameMapper;
import learn.goodgames.models.*;

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
    GameRepository gameRepository;
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


    // Add Service Tests
//    Validations for creating a review:
//    X Can’t empty an empty review (empty string / null)
//    X Rating has to be between 1-10
//    X Game has to have an existing game_id via board game geek api (Can’t be null)
//    X Cannot have the following duplicate combination: user_id, review_id, game_id - when adding
    
    @Test
    void shouldNotAddWhenGameDoesNotExistIs() {
        Game game = makeDuplicateGame();

        Review arg = makeReview();
        List<Review> all = List.of(arg);
        List<Game> games = List.of(game);
        arg.setReviewId(10);
        arg.setUserId(4);
        arg.setGameId(12098);

        when(repository.findAllReviews()).thenReturn(all);
        when(gameRepository.findAllGames()).thenReturn(games);
        Review review = makeReview();
        review.setReviewId(11);
        review.setUserId(3);
        review.setGameId(12098);
        Result<Review> result = service.addReview(arg);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Unable to find valid game", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenUserDoesNotExistIs() {
        Game game = makeDuplicateGame();

        Review arg = makeReview();
        List<Review> all = List.of(arg);
        List<Game> games = List.of(game);
        arg.setReviewId(10);
        arg.setUserId(323);
        arg.setGameId(1);

        when(repository.findAllReviews()).thenReturn(all);
        when(gameRepository.findAllGames()).thenReturn(games);
        Review review = makeReview();
        review.setReviewId(11);
        review.setUserId(2123);
        review.setGameId(2);
        Result<Review> result = service.addReview(review);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Unable to find valid game", result.getMessages().get(0));
    }

    @Test
    void shouldAddDuplicateReviewCombination() {
        Game game = makeDuplicateGame();
        User user = makeDuplicateUser();

        Review arg = makeReview();
        List<Review> all = List.of(arg);
        arg.setUserId(user.getUserId());
        arg.setGameId(game.getGameId());

        when(repository.findAllReviews()).thenReturn(all);
        Review review = makeReview();
        review.setUserId(user.getUserId());
        review.setGameId(game.getGameId());
        Result<Review> result = service.addReview(arg);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Cannot add a review with the following combination: reviewId, gameId and userId", result.getMessages().get(0));
    }


    @Test
    void shouldNotAddReviewWithNullEmptyText() {
        Review review = makeReview();
        review.setText(null);
        Result<Review> result = service.addReview(review);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Text cannot be empty", result.getMessages().get(0));

        Review reviewTwo = makeReview();
        reviewTwo.setText("");
        Result<Review> resultTwo = service.addReview(reviewTwo);
        assertEquals(ResultType.INVALID, resultTwo.getType());
        assertEquals("Text cannot be empty", resultTwo.getMessages().get(0));
    }

    @Test
    void shouldNotAddReviewWithOutOfBoundRating() {
        Review review = makeReview();
        review.setRating(11);
        Result<Review> result = service.addReview(review);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Rating has to be between 1-10", result.getMessages().get(0));

        Review reviewTwo = makeReview();
        reviewTwo.setRating(-4);
        Result<Review> resultTwo = service.addReview(reviewTwo);
        assertEquals(ResultType.INVALID, resultTwo.getType());
        assertEquals("Rating has to be between 1-10", resultTwo.getMessages().get(0));
    }
    
    // Makes identical DB review for review id = 2
    private Review makeDuplicateReview() {
              Review review = new Review();
        review.setReviewId(2);
        review.setText("Nope, not for me, don't play it.");
        review.setRating(0);
        review.setUserId(1);
        review.setGameId(1);
        return review;
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


    // Makes identical DB review for user id = 6
    private User makeDuplicateUser() {
        User user = new User();
        user.setUserId(6);
        user.setUsername("Dias");
        user.setPassword("dias");
        user.setEmail("dias@dias.com");
        user.setRole(Role.USER);
        return user;
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


    // Makes identical DB review for game id = 1
    private Game makeDuplicateGame() {
        Game game = new Game();
        game.setGameId(1);
        game.setBggId(110308);
        game.setName("7 Wonders: Catan");
        return game;
    }

    private Review makeReview() {
        Review review = new Review();
        review.setText("Testing Text");
        review.setRating(5);
        return review;
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