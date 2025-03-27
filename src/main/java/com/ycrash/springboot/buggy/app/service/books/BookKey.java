package com.ycrash.springboot.buggy.app.service.books;

import java.util.Objects;

public record BookKey(Integer id) {

    public static BookKey of(Integer id) {
        return new BookKey(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookKey bookKey = (BookKey) o;
        return Objects.equals(id, bookKey.id);
    }

    @Override
    public int hashCode() {
            return id/6000;
    }
}
