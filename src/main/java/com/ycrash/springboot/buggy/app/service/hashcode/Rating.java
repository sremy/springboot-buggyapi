package com.ycrash.springboot.buggy.app.service.hashcode;

public class Rating {
    private final int userId;
    private final int bookId;
    private final int rating;

    public Rating(int userId, int bookId, int rating) {
        this.userId = userId;
        this.bookId = bookId;
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "userId=" + userId +
                ", bookId=" + bookId +
                ", rating=" + rating +
                '}';
    }

    public String toStars() {
        return "★".repeat(this.rating);
    }
}