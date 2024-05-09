package learn.goodgames.data;

import learn.goodgames.models.Game;

import java.util.List;

public interface GameRepository {
    List<Game> findAllGames();

    Game findGameById(int gameId);

    Game findGameByBggId(int bggId);

    List<Game> findGamesByName(String gameName);

    List<Game> getTop5ReviewedGames();

    Game addGame(Game game);
}
