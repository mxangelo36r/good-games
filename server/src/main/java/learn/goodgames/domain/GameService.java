package learn.goodgames.domain;

import learn.goodgames.data.GameRepository;
import learn.goodgames.models.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public List<Game> findAllGames() { return repository.findAllGames(); }

    public Game findGameById(int gameId) { return repository.findGameById(gameId); }

    public List<Game> findGamesByName(String gameName) { return repository.findGamesByName(gameName); }

    public Result<Game> addGame(Game game) {
        Result<Game> result = validate(game);

        if (!result.isSuccess()) {
            return result;
        }

        if (game.getGameId() != 0) {
            result.addMessage("gameId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        game = repository.addGame(game);
        result.setPayload(game);
        return result;
    }

    // Need review to test this service
    public Result<Game> updateGameAverageRating(int gameId) {
        return null;
    }

    private Result<Game> validate(Game game) {
        Result<Game> result = new Result<>();
        List<Game> games = findAllGames();

        if (game == null) {
            result.addMessage("game cannot be null", ResultType.INVALID);
            return result;
        }

        if(Validations.isNullOrBlank(game.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        if(Validations.isNullOrBlank(String.valueOf(game.getBggId()))) {
            result.addMessage("bggId is required", ResultType.INVALID);
        }

        for(Game g : games) {
            if(g.getBggId() == game.getBggId()) {
                result.addMessage("bggId cannot be duplicated", ResultType.INVALID);
            }
        }

        return result;
    }
}
