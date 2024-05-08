package learn.goodgames.domain;

import learn.goodgames.data.GameRepository;
import learn.goodgames.models.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GameServiceTest {

    @MockBean
    GameRepository repository;

    @Autowired
    GameService service;

    @Test
    void shouldFindAll() {
        List<Game> all = new ArrayList<>();
        all.add(makeGame1());
        when(repository.findAllGames()).thenReturn(all);
        List<Game> result = service.findAllGames();
        assertNotNull(all);
    }

    @Test
    void shouldFindById() {
        Game expected = makeGame1();
        when(repository.findGameById(1)).thenReturn(expected);
        Game actual = service.findGameById(1);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddWhenInvalid() {
        Game game = makeGame0();

        // Should not add game when name null
        game.setName(null);
        Result<Game> result = service.addGame(game);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add game when name empty
        game.setName("");
        result = service.addGame(game);
        assertEquals(ResultType.INVALID, result.getType());

        // should not add when game null
        result = service.addGame(null);
        assertEquals(ResultType.INVALID, result.getType());

        // should not add when duplicate bggId
        game = makeGame1();
        game.setGameId(0);
        List<Game> all = List.of(makeGame1());
        when(repository.findAllGames()).thenReturn(all);
        result = service.addGame(game);
        assertEquals(ResultType.INVALID, result.getType());

        // should not add when gameId > 0
        game = makeGame1();
        result = service.addGame(game);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldReturnGameAvgRating() {
        when(repository.getGameAvgRating(1)).thenReturn(5.0);
        double rating = service.getGameAvgRating(1);
        assertEquals(5.0, rating);
    }

    @Test
    void shouldNotReturnRatingWhenInvalid() {
        double rating = service.getGameAvgRating(15);
        assertEquals(0.0, rating);
    }

    Game makeGame1() {
        Game game = new Game();
        game.setGameId(1);
        game.setBggId(2536);
        game.setName("Vabanque");
        return game;
    }

    Game makeGame0() {
        Game game = new Game();
        game.setGameId(0);
        game.setBggId(1111);
        game.setName("Test");
        return game;
    }
}