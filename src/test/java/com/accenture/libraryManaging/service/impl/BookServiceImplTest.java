package com.accenture.libraryManaging.service.impl;

import com.accenture.libraryManaging.dao.*;
import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.factory.*;
import com.accenture.libraryManaging.mapper.*;
import com.accenture.libraryManaging.repository.*;
import com.accenture.libraryManaging.repository.entity.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserBookServiceImpl userBookService;
    @Mock
    private BookFactory bookFactory;

    @Mock
    private BookMapper bookMapper;


    @Test
    void addBookIncreasesAvailabilityWhenBookAlreadyExists() throws IllegalGenreException {
        BookDto bookDto = new BookDto();
        bookDto.setIsbn("1234567890");
        Book book = new Book();
        book.setIsbn("1234567890");
        book.setAvailable(1);
        when(bookFactory.createBook(bookDto)).thenReturn(book);
        when(bookMapper.mapToEntity(bookDto, book)).thenReturn(book);
        when(bookRepository.existsByIsbn(book.getIsbn())).thenReturn(true);
        when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);

        Book returnedBook = bookService.addBook(bookDto);

        assertEquals(2, returnedBook.getAvailable());
    }

    @Test
    void addBookSetsAvailabilityToOneWhenBookDoesNotExist() throws IllegalGenreException {
        BookDto bookDto = new BookDto();
        bookDto.setIsbn("1234567890");
        Book book = new Book();
        book.setIsbn("1234567890");
        when(bookFactory.createBook(bookDto)).thenReturn(book);
        when(bookMapper.mapToEntity(bookDto, book)).thenReturn(book);
        when(bookRepository.existsByIsbn(book.getIsbn())).thenReturn(false);
        when(bookRepository.save(book)).thenReturn(book);

        Book returnedBook = bookService.addBook(bookDto);

        assertEquals(1, returnedBook.getAvailable());
    }

    @Test
    void addBookThrowsIllegalGenreExceptionWhenGenreIsInvalid() throws IllegalGenreException {
        BookDto bookDto = new BookDto();
        bookDto.setGenre("INVALID");
        when(bookFactory.createBook(bookDto)).thenThrow(new IllegalGenreException("Invalid genre"));

        assertThrows(IllegalGenreException.class, () -> bookService.addBook(bookDto));
    }



    @Test
    void getBooksInfoByAuthorThrowsBookNotFoundExceptionWhenBooksDoNotExist() {
        String author = "Author";
        when(bookRepository.findByAuthor(author)).thenReturn(Collections.emptyList());

        assertThrows(BookNotFoundException.class, () -> bookService.getBooksInfoByAuthor(author));
    }

    @Test
    void getBookInfoByTitleReturnsCorrectBookWhenBookExists() throws BookNotFoundException {
        String title = "Title";
        Book book = new Book();
        book.setTitle(title);
        BookDao expectedBook = new BookDao();
        expectedBook.setTitle(title);
        expectedBook.setUsers(Collections.emptySet());
        when(bookRepository.findByTitle(title)).thenReturn(book);
        when(bookMapper.mapToDao(book)).thenReturn(expectedBook);
        when(userBookService.getUsersByBook(book)).thenReturn(Collections.emptySet());

        BookDao returnedBook = bookService.getBookInfoByTitle(title);

        assertEquals(expectedBook, returnedBook);
    }

    @Test
    void getBooksInfoByAuthorReturnsCorrectBooksWhenBooksExist() throws BookNotFoundException {
        String author = "Author";
        Book book1 = new Book();
        book1.setTitle("Title1");
        book1.setAuthor(author);
        Book book2 = new Book();
        book2.setTitle("Title2");
        book2.setAuthor(author);
        List<Book> books = Arrays.asList(book1, book2);
        BookDao expectedBookDao1 = new BookDao();
        expectedBookDao1.setTitle("Title1");
        expectedBookDao1.setUsers(Collections.emptySet());
        BookDao expectedBookDao2 = new BookDao();
        expectedBookDao2.setTitle("Title2");
        expectedBookDao2.setUsers(Collections.emptySet());
        when(bookRepository.findByAuthor(author)).thenReturn(books);
        when(bookMapper.mapToDao(book1)).thenReturn(expectedBookDao1);
        when(bookMapper.mapToDao(book2)).thenReturn(expectedBookDao2);
        when(userBookService.getUsersByBook(book1)).thenReturn(Collections.emptySet());
        when(userBookService.getUsersByBook(book2)).thenReturn(Collections.emptySet());

        List<BookDao> returnedBooks = bookService.getBooksInfoByAuthor(author);

        assertEquals(2, returnedBooks.size());
        assertTrue(returnedBooks.contains(expectedBookDao1));
        assertTrue(returnedBooks.contains(expectedBookDao2));
    }

    @Test
    void getBookInfoByIsbnReturnsCorrectBookWhenBookExists() throws BookNotFoundException {
        String isbn = "1234567890";
        Book book = new Book();
        book.setIsbn(isbn);
        BookDao expectedBook = new BookDao();
        expectedBook.setIsbn(isbn);
        expectedBook.setUsers(Collections.emptySet());
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
        when(bookRepository.findByIsbn(isbn)).thenReturn(book);
        when(bookMapper.mapToDao(book)).thenReturn(expectedBook);
        when(userBookService.getUsersByBook(book)).thenReturn(Collections.emptySet());

        BookDao returnedBook = bookService.getBookInfoByIsbn(isbn);

        assertEquals(expectedBook, returnedBook);
    }

    @Test
    void getBookInfoByIsbnThrowsBookNotFoundExceptionWhenBookDoesNotExist() {
        String isbn = "1234567890";
        when(bookRepository.existsByIsbn(isbn)).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> bookService.getBookInfoByIsbn(isbn));
    }

    @Test
    void getBooksInfoByTitleThrowsBookNotFoundExceptionWhenBookDoesNotExist() {
        String title = "Title";
        Mockito.when(bookRepository.findByTitle(title)).thenReturn(null);

        assertThrows(BookNotFoundException.class, () -> bookService.getBookInfoByTitle(title));
    }
}