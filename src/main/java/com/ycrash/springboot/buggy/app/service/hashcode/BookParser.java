package com.ycrash.springboot.buggy.app.service.hashcode;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class BookParser {

    public static void main(String[] args) {
        String filePath = "books-10k_reduced.csv";

        List<Book> books = parseBooks(filePath);


        // Print the list to verify
        for (Book book : books) {
            System.out.println(book);
        }

    }


    public static List<Book> parseBooks(String filePath) {
        List<Book> books = new ArrayList<>();
        InputStream resourceAsStream = BookParser.class.getClassLoader().getResourceAsStream(filePath);
        try (Reader reader = new InputStreamReader(resourceAsStream)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .builder().setSkipHeaderRecord(true)
                    .setHeader("book_id", "isbn", "authors", "original_publication_year", "original_title", "language_code", "image_url")
                    .setQuote('"')            // Handle double quotes
                    .build()
                    .parse(reader);

            int id = 1;
            for (CSVRecord record : records) {
                // book_id,isbn,authors,original_publication_year,original_title,language_code,image_url
//                System.out.println(record.get(0)); // Access column by index
                String publicationYear = record.get("original_publication_year").split("\\.")[0];
                Book book = new Book(Integer.valueOf(record.get("book_id")),
                        record.get("original_title"),
                        record.get("isbn"),
                        record.get("authors"),
                        "",
                        record.get("language_code"),
                        !StringUtils.isBlank(publicationYear) ? Integer.valueOf(publicationYear) : null,
                        null,
                        record.get("image_url")
                );
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    protected static Book lineToBook(String line) {
        // Split the line by commas (CSV fields)
        String[] fields = line.split(",");

        // Extract and map the fields to the Product object
        Integer id = Integer.parseInt(fields[0]);
        String isbn = fields[1];
        String authors = fields[2];

        Integer publicationYear = fields[3].isEmpty() ? null : Integer.parseInt(fields[3].split("\\.")[0]);
        String title = fields[4]; // Assuming `original_title` as the title
        String languageCode = fields[5];
        String imageUrl = fields[6];

        // Add placeholders for description, price, and CodeTVA
//                String description = "Published in " + publicationYear + " (" + languageCode + ")";
        String description = "";
        Integer price = 0; // Placeholder value

        // Create a Product object
        Book book = new Book(id, title, isbn, authors, description, languageCode, publicationYear, price, imageUrl);
        return book;
    }
}
