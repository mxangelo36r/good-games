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

    @Test
    void shouldDeleteForUser() {

    }

    @Test
    void shouldDeleteForAdmin() {

    }

    // Helper Methods

    private Review makeReview() {
        Review review = new Review();
        review.setText("Testing Text");
        review.setRating(5);
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

    private Game makeGame() {
        Game game = new Game();
        game.setGameId(2);
        game.setBggId(6);
        game.setName("Test Game");
        return game;
    }

}