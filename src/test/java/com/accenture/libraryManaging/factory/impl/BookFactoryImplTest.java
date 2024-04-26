package com.accenture.libraryManaging.factory.impl;

import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.mapper.*;
import com.accenture.libraryManaging.repository.*;
import com.accenture.libraryManaging.repository.entity.*;
import org.aspectj.lang.annotation.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static com.accenture.libraryManaging.repository.entity.Genre.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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