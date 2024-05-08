package learn.goodgames.data;

import learn.goodgames.models.Game;

import java.util.List;

public interface GameRepository {
    List<Game> findAllGames();

    Game findGameById(int gameId);

    List<Game> findGamesByName(String gameName);

    Game addGame(Game game);

    double getGameAvgRating(int gameId);

//    boolean updateGameAverageRating(int gameId);

//    boolean delete (int gameId);
}
