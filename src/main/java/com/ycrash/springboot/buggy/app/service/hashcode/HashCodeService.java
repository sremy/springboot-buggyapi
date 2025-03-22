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

    private final Random random = new Random();

    private List<Book> books;


    record BookKey(String title) {
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            BookKey bookKey = (BookKey) o;
            return Objects.equals(title, bookKey.title);
        }

        @Override
        public int hashCode() {
            return 1;
//             return Objects.hashCode(title);
        }
    }


    public String start() {
        log.info("HashCodeService started");

        books = BookParser.parseBooks("books-10k_reduced.csv");
        for (Book book : books) {
            catalog.put(new BookKey(book.title), book);
        }

        log.info("Catalog size: {}", catalog.size());

        return "Catalog " + catalog.size() + " books inserted";

    }

    public List<Book> search() {
        log.info("Search for a random book...");

        var randomBook = books.get(random.nextInt(books.size()));

        Stopwatch stopwatch = Stopwatch.createStarted();

        var c = catalog.get(new BookKey(randomBook.title));

        log.info("Book found: {}", c);
        log.info("Search completed in {} ns", stopwatch.elapsed(TimeUnit.NANOSECONDS));

        return List.of(randomBook);
    }
}
