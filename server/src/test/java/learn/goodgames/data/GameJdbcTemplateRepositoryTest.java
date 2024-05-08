package learn.goodgames.data;

import learn.goodgames.models.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GameJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 5;

    @Autowired
    GameJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Game> games = repository.findAllGames();
        assertNotNull(games);

        assertTrue(!games.isEmpty() && games.size() <= 5);
    }

    @Test
    void shouldFindById() {
        Game expected = makeGame();

        Game actual = repository.findGameById(1);
        assertNotNull(actual);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByBggId() {
        Game expected = makeGame();
        Game actual = repository.findGameByBggId(2536);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindTwoCatansByName() {
        List<Game> twoCatans = repository.findGamesByName("catan");
        assertNotNull(twoCatans);
        assertEquals(2, twoCatans.size());

        Game test = twoCatans.get(0);
        assertEquals(3, test.getGameId());
        assertEquals(13, test.getBggId());
        assertEquals("CATAN", test.getName());
    }

    @Test
    void shouldAddGame() {
        Game game = makeGame();
        game.setGameId(0);
        Game actual = repository.addGame(game);
        assertNotNull(actual);

        assertEquals(NEXT_ID, actual.getGameId());
    }

    @Test
    void shouldFindGameAverageRating() {
        double avgRating = repository.getGameAvgRating(1);
        assertEquals(5, avgRating);
    }

    Game makeGame() {
        Game game = new Game();
        game.setGameId(1);
        game.setBggId(2536);
        game.setName("Vabanque");
        return game;
    }
}