package learn.goodgames.data;

import learn.goodgames.models.Game;

import java.util.List;

public interface GameRepository {
    List<Game> findAllGames();

    Game findGameById(int gameId);

    Game findGameByBggId(int bggId);

    List<Game> findGamesByName(String gameName);

    int getTotalGameReviews(int gameId);

    int getNextGameId();

    List<Game> getTop4ReviewedGames();

    Game addGame(Game game);

    double getGameAvgRating(int gameId);
}
