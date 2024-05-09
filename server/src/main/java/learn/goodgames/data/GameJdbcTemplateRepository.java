package learn.goodgames.data;

import learn.goodgames.data.mappers.GameMapper;
import learn.goodgames.models.Game;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GameJdbcTemplateRepository implements GameRepository {

    private final JdbcTemplate jdbcTemplate;

    public GameJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Game> findAllGames() {
        final String sql = "select game_id, `name`, bgg_id " +
                "from game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    public Game findGameById(int gameId) {
        final String sql = "select game_id, bgg_id, name " +
                "from game " +
                "where game_id = ?;";
        return jdbcTemplate.query(sql, new GameMapper(), gameId).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Game findGameByBggId(int bggId) {
        final String sql = "select game_id, bgg_id, name " +
                "from game " +
                "where bgg_id = ?;";
        return jdbcTemplate.query(sql, new GameMapper(), bggId).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Game> findGamesByName(String gameName) {
        final String sql = "select game_id, bgg_id, name " +
                "from game " +
                "where name like ?;";
        return jdbcTemplate.query(sql, new GameMapper(), "%" + gameName + "%").stream()
                .filter(game -> game.getName().toLowerCase().contains(gameName.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Game addGame(Game game) {
        final String sql = "insert into game (bgg_id, `name`) " +
                "values (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, game.getBggId());
            ps.setString(2, game.getName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        game.setGameId(keyHolder.getKey().intValue());
        return game;
    }

    @Override
    public double getGameAvgRating(int gameId) {
        final String sql = "select avg(rating) as avg_rating " +
                "from review r " +
                "where game_id = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, gameId);
    }

    @Override
    public List<Game> getTop5ReviewedGames() {
        final String sql = "select g.game_id, g.`name`, g.bgg_id, avg(r.rating) as rating " +
                "from review r " +
                "inner join game g on g.game_id = r.game_id " +
                "group by r.game_id " +
                "order by rating desc " +
                "limit 5;";

        return jdbcTemplate.query(sql, new GameMapper());
    }
}
