package learn.goodgames.models;

import java.util.Objects;

public class Game {
    private int gameId;
    private int bggId;
    private String name;

    public Game() {
    }

    public Game(int gameId, int bggId, String name) {
        this.gameId = gameId;
        this.bggId = bggId;
        this.name = name;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getBggId() {
        return bggId;
    }

    public void setBggId(int bggId) {
        this.bggId = bggId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return bggId == game.bggId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bggId);
    }
}
