package learn.goodgames.data.mappers;

import learn.goodgames.models.Game;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameMapper implements RowMapper<Game> {

    @Override
    public Game mapRow(ResultSet resultSet, int i) throws SQLException {
        Game game = new Game();
        game.setGameId(resultSet.getInt("game_id"));
        game.setBggId(resultSet.getInt("bgg_id"));
        game.setName(resultSet.getString("name"));
        return game;
    }

}
