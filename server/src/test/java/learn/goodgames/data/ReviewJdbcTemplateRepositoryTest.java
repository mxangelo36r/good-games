package learn.goodgames.data;

import learn.goodgames.models.Review;
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
        List<Review> all = repository.findAll();
        assertNotNull(all);
        assertEquals(7, all.size());
    }

    @Test
    void shouldFindEachReview() {
        Review review = repository.findById(1);
        assertNotNull(review);
        assertEquals("I loved this game!", review.getText());
        assertEquals(10, review.getRating());
        assertEquals(1, review.getUserId());
        assertEquals(1, review.getGameId());

        Review reviewTwo = repository.findById(2);
        assertNotNull(reviewTwo);
        assertEquals("Nope, not for me, don't play it.", reviewTwo.getText());
        assertEquals(0, reviewTwo.getRating());
        assertEquals(6, reviewTwo.getUserId());
        assertEquals(1, reviewTwo.getGameId());
    }

}