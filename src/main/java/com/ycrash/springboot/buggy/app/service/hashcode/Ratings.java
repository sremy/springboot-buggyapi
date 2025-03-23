package com.ycrash.springboot.buggy.app.service.hashcode;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class Ratings {

    public Ratings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    @Getter @Setter
    List<Rating> ratings;

    float averageRating = -1;


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
