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

    public Game findGameByBggId(int bggId) { return repository.findGameByBggId(bggId); }

    public List<Game> findGamesByName(String gameName) { return repository.findGamesByName(gameName); }

    public List<Game> getTop5ReviewedGames() { return repository.getTop4ReviewedGames(); }

    public int getNextGameId() { return repository.getNextGameId(); }

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

    public double getGameAvgRating(int gameId) {
        Game game = findAllGames().stream()
                .filter(g -> g.getGameId() == gameId)
                .findFirst()
                .orElse(null);
        if(game == null) {
            return -1;
        } else {
            return repository.getGameAvgRating(game.getGameId());
        }
    }

    public int getTotalGameReviews(int gameId) {
        Game game = findAllGames().stream()
                .filter(g -> g.getGameId() == gameId)
                .findFirst()
                .orElse(null);
        if(game == null) {
            return 0;
        } else {
            return repository.getTotalGameReviews(game.getGameId());
        }
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
