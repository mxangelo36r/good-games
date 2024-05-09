package learn.goodgames.models;

import java.util.Objects;

public class Review {

    // Fields
    private int reviewId;
    private User user;
    private int userId;
    private Game game;
    private int gameId;
    private String text;
    private int rating;

    // Getters & Setters

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    // Cannot have the following duplicate combination: user_id, review_id, game_id - when adding
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(userId, review.userId) &&
                Objects.equals(reviewId, review.reviewId) &&
                Objects.equals(gameId, review.gameId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(userId, reviewId, gameId);
    }

}
