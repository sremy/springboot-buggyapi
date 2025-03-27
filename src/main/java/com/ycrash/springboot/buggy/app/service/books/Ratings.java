package com.ycrash.springboot.buggy.app.service.books;

import java.util.List;


public class Ratings {

    public Ratings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    List<Rating> ratings;

    float averageRating = -1;

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public synchronized float getAverageRating() {
        if (averageRating == -1 && ratings != null) {
            averageRating = computeAverageRating();
        }
        return averageRating;
    }

    private float computeAverageRating() {
        return (float) ratings.stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElse(-1.0);
    }

    public int getRatingsSize() {
        return ratings != null ? ratings.size() : 0;
    }

}
