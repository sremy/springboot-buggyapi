package com.ycrash.springboot.buggy.app.service.hashcode;

import java.util.Objects;

public class Product {

    Integer id;
    String name;
    String isbn;
    String authors;
    String description;
    Integer price;
    CodeTVA codeTVA;
    String imageUrl;

    public Product(Integer id, String name, String isbn, String authors, String description, Integer price, CodeTVA codeTVA, String imageUrl) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.authors = authors;
        this.description = description;
        this.price = price;
        this.codeTVA = codeTVA;
        this.imageUrl = imageUrl;
    }

    public Product(String name, String isbn, String authors, String description, Integer price, CodeTVA codeTVA, String imageUrl) {
        this.name = name;
        this.isbn = isbn;
        this.authors = authors;
        this.description = description;
        this.price = price;
        this.codeTVA = codeTVA;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product book = (Product) o;
        return Objects.equals(name, book.name) && Objects.equals(description, book.description) && Objects.equals(price, book.price) && codeTVA == book.codeTVA;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, codeTVA);
    }

}
