package com.ycrash.springboot.buggy.app.service.hashcode;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.StringJoiner;

public class Book {

    Integer id;
    String title;
    String isbn;
    String authors;
    String description;
    String languageCode;
    Integer publicationYear;
    Integer price;
    String imageUrl;

    @Getter
    @Setter
    Ratings ratings;

    public Book(Integer id, String title, String isbn, String authors, String description, String languageCode, Integer publicationYear, Integer price, String imageUrl) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.authors = authors;
        this.description = description;
        this.languageCode = languageCode;
        this.publicationYear = publicationYear;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(isbn, book.isbn) && Objects.equals(authors, book.authors) && Objects.equals(description, book.description) && Objects.equals(languageCode, book.languageCode) && Objects.equals(publicationYear, book.publicationYear) && Objects.equals(price, book.price) && Objects.equals(imageUrl, book.imageUrl);
    }

    @Override
    public int hashCode() {
        return id;
//        return id/10000;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("title='" + title + "'")
                .add("isbn='" + isbn + "'")
                .add("authors='" + authors + "'")
                .add("description='" + description + "'")
                .add("languageCode='" + languageCode + "'")
                .add("publicationYear=" + publicationYear)
                .add("price=" + price)
                .add("imageUrl='" + imageUrl + "'")
                .add("ratings='" + (ratings == null ? "null" : ratings.getAverageRating() + " by " + ratings.getRatingsSize() + " readers'"))
                .toString();
    }
}
