package learn.goodgames.controllers;

import learn.goodgames.domain.GameService;
import learn.goodgames.domain.Result;
import learn.goodgames.models.Game;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/game")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping
    public List<Game> findAllGames() { return service.findAllGames(); }

    @GetMapping("/id/{gameId}")
    public ResponseEntity<Game> findGameById(@PathVariable int gameId) {
        Game game = service.findGameById(gameId);

        if (game == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(game);
    }

    @GetMapping("/bggId/{bggId}")
    public ResponseEntity<Game> findGameByBggId(@PathVariable int bggId) {
        Game game = service.findGameByBggId(bggId);

        if (game == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(game);
    }

    @GetMapping("/name/{gameName}")
    public List<Game> findGamesByName(@PathVariable String gameName) {
        return service.findGamesByName(gameName);
    }

    @GetMapping("/rating/{gameId}")
    public double getGameAvgRating(@PathVariable int gameId) {
        return service.getGameAvgRating(gameId);
    }

    @GetMapping("/topfive")
    public List<Game> getTop5ReviewedGames() { return service.getTop5ReviewedGames(); }

    @PostMapping()
    public ResponseEntity<Object> add(@RequestBody Game game) {
        Result<Game> result = service.addGame(game);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }
}
