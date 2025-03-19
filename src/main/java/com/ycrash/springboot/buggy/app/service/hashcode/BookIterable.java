package com.ycrash.springboot.buggy.app.service.hashcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

public class BookIterable implements Iterable<Book> {

    private final String resourcePath;

    public BookIterable(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public Iterator<Book> iterator() {
        return new BookIterator(resourcePath);
    }

    private static class BookIterator implements Iterator<Book> {
        private BufferedReader br;
        private String nextLine;

        public BookIterator(String resourcePath) {
            try {
                InputStream resourceAsStream = BookIterable.class.getClassLoader().getResourceAsStream(resourcePath);
                this.br = new BufferedReader(new InputStreamReader(resourceAsStream)
                );
                // Skip the header line
                this.nextLine = br.readLine();
                this.nextLine = br.readLine(); // Move to the first data line
            } catch (IOException e) {
                e.printStackTrace();
                closeReader();
            }
        }



        @Override
        public boolean hasNext() {
            return nextLine != null;
        }

        @Override
        public Book next() {
            if (nextLine == null) {
                throw new IllegalStateException("No more lines to read");
            }

            Book book = BookParser.lineToBook(nextLine);

            try {
                // Prepare for the next line
                nextLine = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                closeReader();
            }

            return book;
        }

        private void closeReader() {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
