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

import java.util.ArrayList;
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
        assert (actual.isEmpty());
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
    void shouldNotAddWhenGameDoesNotExist() {
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
    void shouldNotAddWhenUserDoesNotExist() {
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
    void shouldNotAddDuplicateReviewCombination() {
        Game game = makeDuplicateGame();
        User user = makeDuplicateUserDias();

        Review arg = makeReview();
        arg.setGameId(1);
        arg.setUserId(1);

        List<Review> all = List.of(arg);
        List<Game> games = List.of(game);
        List<User> users = List.of(user);
//        arg.setUserId(user.getUserId());
//        arg.setGameId(game.getGameId());

        when(repository.findAllReviews()).thenReturn(all);
        when(gameRepository.findAllGames()).thenReturn(games);
        when(userRepository.findAllUsers()).thenReturn(users);

        Review review = makeReview();
        review.setText(null);
        service.addReview(review);
        Result<Review> result = service.addReview(review);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Text cannot be empty", result.getMessages().get(0));
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

    // Update Test
    @Test
    void shouldNotUpdateWhenUserDoesNotExistInDB() {
        User user = makeUser();
        Review review = makeReview1();

        Result<Review> result = service.updateReview(review, user.getUserId());
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("User does not exist", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateOtherReviewsForUser() {
        User user = makeUser(); // userId = 1
        Review review = makeDuplicateReviewFour();
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findAllUsers()).thenReturn(users);
        when(userRepository.findUserById(1)).thenReturn(user);
        Result<Review> result = service.updateReview(review, user.getUserId());
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("You can only edit your own review. User ID: 1 Review ID: 3 Email: test@user.com", result.getMessages().get(0));
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

    private Review makeReview1() {

        Review review = new Review();
        review.setReviewId(2);
        review.setText("Nope, not for me, don't play it.");
        review.setRating(0);
        review.setUserId(1);
        review.setGameId(1);
        return review;
    }

    private Review makeReview2() {

        Review review = new Review();
        review.setReviewId(2);
        review.setText("Nope, not for me, don't play it.");
        review.setRating(0);
        review.setUserId(2);
        review.setGameId(2);
        return review;
    }

    // Duplicate review in DB (reviewId = 4)
    private Review makeDuplicateReviewFour() {
        Review review = new Review();
        review.setReviewId(4);
        review.setText("I tried it and I liked it, I guess");
        review.setRating(5);
        review.setUserId(3);
        review.setGameId(3);
        return review;
    }


    // Makes identical DB review for user id = 6
    private User makeDuplicateUserDias() {
        User user = new User();
        user.setUserId(6);
        user.setUsername("Dias");
        user.setPassword("dias");
        user.setEmail("dias@dias.com");
        user.setRole(Role.USER);
        return user;
    }

    private User makeUser() {
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

    private User makeAdmin() {
        User user = new User();
        user.setUserId(2);
        user.setUsername("adminTest");
        user.setPassword("passwordTest");
        user.setEmail("test@admin.com");
        user.setRole(Role.ADMIN);
        return user;

    }
}