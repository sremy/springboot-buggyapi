package com.ycrash.springboot.buggy.app.service.books;

import com.google.common.base.Stopwatch;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

public class RatingParser {

    private static final Logger log = LoggerFactory.getLogger(RatingParser.class);

    public static List<Rating> parseRatings(String filePath) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<Rating> ratings = new ArrayList<>();
        try (InputStream fileStream = RatingParser.class.getClassLoader().getResourceAsStream(filePath);
             InputStream gzipStream = new GZIPInputStream(fileStream);
             BufferedReader reader = new BufferedReader(new InputStreamReader(gzipStream))) {

            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("user_id", "book_id", "rating")
                    .withSkipHeaderRecord()
                    .parse(reader);

            int loggingStep = 500_000;
            int i = 0;
            for (CSVRecord record : records) {
                int userId = Integer.parseInt(record.get("user_id"));
                int bookId = Integer.parseInt(record.get("book_id"));
                int rating = Integer.parseInt(record.get("rating"));
                ratings.add(new Rating(userId, bookId, rating));

                if (++i % loggingStep == 0) {
                    log.info("Parsing {} ratings done", i);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Parsing of {} ratings 100% done in {}s", ratings.size(), stopwatch.elapsed(TimeUnit.SECONDS));
        return ratings;
    }

    public static Map<BookKey, List<Rating>> indexByBookId(List<Rating> ratings) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Map<BookKey, List<Rating>> ratingsByBookId = new HashMap<>();
        int ratingsSize = ratings.size();
        int tenthStep = ratingsSize / 10;
        for (int i = 0; i < ratingsSize; i++) {
            Rating rating = ratings.get(i);
            ratingsByBookId.computeIfAbsent(new BookKey(rating.getBookId()), k -> new ArrayList<>()).add(rating);
            if (i % tenthStep == 0 && i != 0) {
                log.info("Indexing {}% done", i / tenthStep * 10);
            }
        }
        log.info("Ratings indexed: {} in {}s", ratings.size(), stopwatch.elapsed(TimeUnit.SECONDS));
        return ratingsByBookId;
    }
}