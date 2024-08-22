package com.accenture.library.factory.impl;

import com.accenture.library.dto.*;
import com.accenture.library.exceptions.*;
import com.accenture.library.repository.entity.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.*;

import static com.accenture.library.repository.entity.Genre.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookFactoryImplTest {


    @Test
    void createBookCreatesChildrenBookWhenGenreIsChildren() throws IllegalGenreException {
        BookDto bookDto = new BookDto();
        bookDto.setGenre("CHILDREN");
        Book book = new BookFactoryImpl().createBook(bookDto);
        assertTrue(book instanceof ChildrenBook);
        assertEquals(CHILDREN, book.getGenre());
    }

    @Test
    void createBookCreatesFictionBookWhenGenreIsFiction() throws IllegalGenreException {
        BookDto bookDto = new BookDto();
        bookDto.setGenre("FICTION");
        Book book = new BookFactoryImpl().createBook(bookDto);
        assertTrue(book instanceof FictionBook);
        assertEquals(FICTION, book.getGenre());
    }

    @Test
    void createBookCreatesHistoricalBookWhenGenreIsHistorical() throws IllegalGenreException {
        BookDto bookDto = new BookDto();
        bookDto.setGenre("HISTORICAL");
        Book book = new BookFactoryImpl().createBook(bookDto);
        assertTrue(book instanceof HistoricalBook);
        assertEquals(HISTORICAL, book.getGenre());
    }

    @Test
    void createBookCreatesScientificBookWhenGenreIsScientific() throws IllegalGenreException {
        BookDto bookDto = new BookDto();
        bookDto.setGenre("SCIENTIFIC");
        Book book = new BookFactoryImpl().createBook(bookDto);
        assertTrue(book instanceof ScientificBook);
        assertEquals(SCIENTIFIC, book.getGenre());
    }

    @Test
    void createBookThrowsIllegalGenreExceptionWhenGenreIsInvalid() {
        BookDto bookDto = new BookDto();
        bookDto.setGenre("INVALID");
        assertThrows(IllegalGenreException.class, () -> new BookFactoryImpl().createBook(bookDto));
    }

}