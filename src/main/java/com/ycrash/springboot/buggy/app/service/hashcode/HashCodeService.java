package com.ycrash.springboot.buggy.app.service.hashcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class HashCodeService {

    private static final Logger log = LoggerFactory.getLogger(HashCodeService.class);

    Map<Book, Integer> catalog = new HashMap<>();
    private Book javaBook;
    private List<Book> books;
    private Random random = new Random();


    public String start() {
        log.info("HashCodeService started");

        javaBook = new Book(0, "Book The Well-Grounded Java Developer",
                "9781617298875",
                "Benjamin Evans, Jason Clark and Martijn Verburg",
                "Understanding Java from the JVM up gives you a solid foundation to grow your expertise and take on advanced techniques for performance, concurrency, containerization, and more",
                "en",
                2022,
                4199,
                "https://images.manning.com/264/352/resize/book/8/211404c-5150-4359-9596-e9026710d5df/Evans2ed-HI.png");


        catalog.put(javaBook, 1);

        books = BookParser.parseBooks("books-10k_reduced.csv");
        for (Book book: books) {
            catalog.put(book, 1);
        }

        Double d = 456d;
        d.compareTo(Double.MIN_VALUE);
        Double.valueOf(0d);


        log.info("Catalog size: " + catalog.size());
//        log.info(catalog);


        return "Catalog " + catalog.size() + " books inserted";

    }

    public List<Book> search() {
        log.info("Search...");

        Book randomBook = books.get(random.nextInt(books.size()));

        Integer count = null;
        for (int i = 0; i < 10000; i++) {
            count = catalog.get(books.get(i));
        }

        log.info("javabook " + randomBook.title + ": " + count);
//        count = catalog.get(books.get(books.size()-1));
//        log.info("lastbook" + count);
        return List.of(randomBook);
    }
}
