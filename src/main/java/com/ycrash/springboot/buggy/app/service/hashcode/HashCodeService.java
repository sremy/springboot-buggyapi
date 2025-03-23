package com.ycrash.springboot.buggy.app.service.hashcode;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class HashCodeService {

    private static final Logger log = LoggerFactory.getLogger(HashCodeService.class);

    private final Map<BookKey, Book> catalog = new HashMap<>();
    private List<Book> books;

    private Map<BookKey, List<Rating>> ratingsMap;


    private final Random random = new Random();


    public String start() {
        log.info("HashCodeService started");

        loadBooks("books-10k_reduced.csv");
        log.info("Catalog size: {} books", catalog.size());

        // Load ratings
        List<Rating> ratingsList = loadRatings("ratings.csv.gz");

        return "Catalog " + catalog.size() + " books inserted with " + ratingsList.size() + " ratings";
    }

    private void loadBooks(String filename) {
        books = BookParser.parseBooks(filename);
        for (Book book : books) {
            catalog.put(new BookKey(book.id), book);
        }
    }


    public List<Rating> loadRatings(String filename) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<Rating> ratings = RatingParser.parseRatings(filename);
        this.ratingsMap = RatingParser.indexByBookId(ratings);
        log.info("Ratings loaded and indexed in {}s", stopwatch.elapsed(TimeUnit.SECONDS));
        return ratings;
    }


    public Book randomBook() {
        log.info("Search for a random book...");

        var randomBook = books.get(random.nextInt(books.size()));

        Stopwatch stopwatch = Stopwatch.createStarted();

        List<Rating> ratingList = ratingsMap.get(BookKey.of(randomBook.id));
        randomBook.setRatings(new Ratings(ratingList));

        log.info("Book found: {}", randomBook);
        log.debug("Rating associated: {}", ratingList);
        log.info("Search ratings completed in {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        return randomBook;
    }
}
