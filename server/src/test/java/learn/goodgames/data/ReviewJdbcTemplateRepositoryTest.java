package learn.goodgames.data;

import learn.goodgames.models.Game;
import learn.goodgames.models.Review;
import learn.goodgames.models.Role;
import learn.goodgames.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReviewJdbcTemplateRepositoryTest {

    @Autowired
    ReviewJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Review> all = repository.findAllReviews();
        assertNotNull(all);
        assertEquals(7, all.size());
    }

    @Test
    void shouldFindEachReview() {
        Review review = repository.findReviewById(1);
        assertNotNull(review);
        assertEquals("I loved this game!", review.getText());
        assertEquals(10, review.getRating());
        assertEquals(1, review.getUserId());
        assertEquals(1, review.getGameId());

        Review reviewTwo = repository.findReviewById(2);
        assertNotNull(reviewTwo);
        assertEquals("Nope, not for me, don't play it.", reviewTwo.getText());
        assertEquals(0, reviewTwo.getRating());
        assertEquals(6, reviewTwo.getUserId());
        assertEquals(1, reviewTwo.getGameId());
    }

    @Test
    void shouldAddNewReview() {
        Review review = makeReview();
        User user = makeUser();
        Game game = makeGame();

        Review actual = repository.addReview(review, user, game);
        assertNotNull(actual);
        assertEquals(8, review.getReviewId());
        assertEquals(3, review.getUser().getUserId());
        assertEquals(2, review.getGame().getGameId());
    }

    @Test
    void shouldUpdate() {
        Review review = makeReview();
        review.setReviewId(5);
        assertTrue(repository.updateReview(review));
        Review actual = repository.findReviewById(5);
        assertEquals("Testing Text", actual.getText());
        assertEquals(5, actual.getRating());
    }

    // This test includes dependencies on other tests
    // deleteReviewUser() passes the test but makes the shouldFindGameAverageRating() fail because it deletes a review
    // Have to comment out deleteFromUser() from repo, jdbc and refresh the DB
    // And then uncomment it out so that other test works

//    @Test
//    void shouldDeleteForUser() {
//        // Deleting review 2
//        Review review = makeDuplicateReview();
//        User user = makeDuplicateUser();
//        assertTrue(repository.deleteReviewUser(2, review, user));
//    }

    // This test uses data with dependencies
    // Same as above refresh the code from the steps above

//    @Test
//    void shouldDeleteOtherReviewsForAdmin() {
//        Review review = makeDuplicateReview();
//        User admin = makeDuplicateAdmin();
//        assertTrue(repository.deleteReviewAdmin(6, review, admin));
//    }

    // Helper Methods

    private Review makeReview() {
        Review review = new Review();
        review.setText("Testing Text");
        review.setRating(5);
        return review;
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

    private User makeUser() {
        User user = new User();
        user.setUserId(3);
        user.setUsername("test-user");
        user.setPassword("test-password");
        user.setEmail("test@email.com");
        user.setRole(Role.USER);
        return user;
    }

    // Makes identical DB user for user id = 2
    private User makeDuplicateUser() {
        User user = new User();
        user.setUserId(6);
        user.setUsername("Dias");
        user.setPassword("dias");
        user.setEmail("dias@dias.com");
        user.setRole(Role.USER);
        return user;
    }
    // ("Kevin", "kevin@kevin.com", "kevin", "ADMIN")
    private User makeDuplicateAdmin() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("Kevin");
        user.setPassword("kevin");
        user.setEmail("kevin@kevin.com");
        user.setRole(Role.ADMIN);
        return user;
    }

    private Game makeGame() {
        Game game = new Game();
        game.setGameId(2);
        game.setBggId(6);
        game.setName("Test Game");
        return game;
    }

}