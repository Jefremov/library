package com.accenture.libraryManaging.iterator;

import com.accenture.libraryManaging.repository.entity.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BookListIteratorTest {

    @Test
    void hasNextReturnsTrueWhenBooksExist() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        BookListIterator iterator = new BookListIterator(books);
        assertTrue(iterator.hasNext());
    }

    @Test
    void hasNextReturnsFalseWhenNoBooksExist() {
        List<Book> books = Collections.emptyList();
        BookListIterator iterator = new BookListIterator(books);
        assertFalse(iterator.hasNext());
    }

    @Test
    void nextReturnsCorrectBookWhenBooksExist() {
        Book book1 = new Book();
        Book book2 = new Book();
        List<Book> books = Arrays.asList(book1, book2);
        BookListIterator iterator = new BookListIterator(books);
        assertEquals(book1, iterator.next());
        assertEquals(book2, iterator.next());
    }

    @Test
    void nextThrowsExceptionWhenNoBooksExist() {
        List<Book> books = Collections.emptyList();
        BookListIterator iterator = new BookListIterator(books);
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void hasNext() {
    }
}