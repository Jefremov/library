package com.accenture.libraryManaging.service.impl;

import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.observer.*;
import com.accenture.libraryManaging.repository.*;
import com.accenture.libraryManaging.repository.entity.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserBookServiceImplTest {

    @InjectMocks
    private UserBookServiceImpl userBookService;

    @Mock
    private UserBookRepository userBookRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private BookPublisher bookPublisher;
    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getBooksByUserReturnsCorrectBooksWhenUserHasBooks() {
        User user = new User();
        user.setId(1L);
        Book book1 = new Book();
        book1.setId(1L);
        book1.setIsbn("isbn1");
        Book book2 = new Book();
        book2.setId(2L);
        book2.setIsbn("isbn2");
        UserBook userBook1 = new UserBook();
        userBook1.setId(1L);
        userBook1.setBook(book1);
        userBook1.setUser(user);
        UserBook userBook2 = new UserBook();
        userBook2.setId(2L);
        userBook2.setBook(book2);
        userBook2.setUser(user);
        when(userBookRepository.findByUserId(user.getId())).thenReturn(Arrays.asList(userBook1, userBook2));
        when(bookRepository.findById(book1.getId())).thenReturn(Optional.of(book1));
        when(bookRepository.findById(book2.getId())).thenReturn(Optional.of(book2));
        when(bookRepository.findById(book1.getId())).thenReturn(Optional.of(book1));
        when(bookRepository.findById(book2.getId())).thenReturn(Optional.of(book2));

        Set<String> books = userBookService.getBooksByUser(user);

        assertEquals(2, books.size());
        assertTrue(books.contains(book1.getIsbn()));
        assertTrue(books.contains(book2.getIsbn()));
    }

    @Test
    void getBooksByUserReturnsEmptySetWhenUserHasNoBooks() {
        User user = new User();
        user.setId(1L);
        when(userBookRepository.findByUserId(user.getId())).thenReturn(Collections.emptyList());

        Set<String> books = userBookService.getBooksByUser(user);

        assertTrue(books.isEmpty());
    }

    @Test
    void getUsersByBookReturnsCorrectUsersWhenBookIsBorrowedByUsers() {
        Book book = new Book();
        book.setId(1L);
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("username1");
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("username2");
        UserBook userBook1 = new UserBook();
        userBook1.setId(1L);
        userBook1.setBook(book);
        userBook1.setUser(user1);
        UserBook userBook2 = new UserBook();
        userBook2.setId(2L);
        userBook2.setBook(book);
        userBook2.setUser(user2);
        when(userBookRepository.findByBookId(book.getId())).thenReturn(Arrays.asList(userBook1, userBook2));
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.findById(user2.getId())).thenReturn(Optional.of(user2));

        Set<String> users = userBookService.getUsersByBook(book);

        assertEquals(2, users.size());
        assertTrue(users.contains(user1.getUsername()));
        assertTrue(users.contains(user2.getUsername()));
    }

    @Test
    void getUsersByBookReturnsEmptySetWhenBookIsNotBorrowedByAnyUser() {
        Book book = new Book();
        book.setId(1L);
        when(userBookRepository.findByBookId(book.getId())).thenReturn(Collections.emptyList());

        Set<String> users = userBookService.getUsersByBook(book);

        assertTrue(users.isEmpty());
    }

    @Test
    void getBookThrowsBookNotFoundExceptionWhenBookIsbnDoesNotExist() {
        String username = "username";
        String isbn = "nonExistentIsbn";
        when(bookRepository.existsByIsbn(isbn)).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> userBookService.getBook(username, isbn));
    }

    @Test
    void getBookThrowsUserNotFoundExceptionWhenUsernameDoesNotExist() {
        String username = "nonExistentUsername";
        String isbn = "isbn";
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
        when(userRepository.existsByUsername(username)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userBookService.getBook(username, isbn));
    }

    @Test
    void getBookThrowsBookAlreadyTakenExceptionWhenBookIsAlreadyBorrowedByUser() {
        String username = "username";
        String isbn = "isbn";
        User user = new User();
        user.setUsername(username);
        user.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setIsbn(isbn);
        user.setBooks(new HashSet<>(Collections.singletonList(book)));

        UserBook userBook = new UserBook();
        userBook.setBook(book);
        userBook.setUser(user);
        List<UserBook> userBooks = new ArrayList<>();
        userBooks.add(userBook);

        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(bookRepository.findByIsbn(isbn)).thenReturn(book);
        when(userBookRepository.findByUserId(user.getId())).thenReturn(userBooks);

        book.setAvailable(3);


        assertThrows(BookAlreadyTakenException.class, () -> userBookService.getBook(username, isbn));
    }

    @Test
    void getBookThrowsBookNotAvailableExceptionWhenBookIsNotAvailable() {
        String username = "username";
        String isbn = "isbn";
        User user = new User();
        user.setUsername(username);
        Book book = new Book();
        book.setIsbn(isbn);
        book.setAvailable(0);
        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(bookRepository.findByIsbn(isbn)).thenReturn(book);

        assertThrows(BookNotAvailableException.class, () -> userBookService.getBook(username, isbn));
    }

    @Test
    void getBookReturnsBookWhenBookIsAvailableAndUserIsValidAndBookIsNotAlreadyTakenByUser()
            throws UserNotFoundException, BookAlreadyTakenException, BookNotFoundException, BookNotAvailableException {
        String username = "username";
        String isbn = "isbn";
        User user = new User();
        user.setUsername(username);
        Book book = new Book();
        book.setIsbn(isbn);
        book.setAvailable(1);
        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(bookRepository.findByIsbn(isbn)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);

        Book result = userBookService.getBook(username, isbn);

        assertEquals(book, result);
        assertEquals(0, book.getAvailable());
        assertTrue(book.getUsers().contains(user));
    }

    @Test
    void returnBookThrowsBookNotFoundExceptionWhenBookIsbnDoesNotExist() {
        String username = "username";
        String isbn = "nonExistentIsbn";
        when(bookRepository.existsByIsbn(isbn)).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> userBookService.returnBook(username, isbn));
    }

    @Test
    void returnBookThrowsUserNotFoundExceptionWhenUsernameDoesNotExist() {
        String username = "nonExistentUsername";
        String isbn = "isbn";
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
        when(userRepository.existsByUsername(username)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userBookService.returnBook(username, isbn));
    }

    @Test
    void returnBookThrowsBookNotFoundExceptionWhenBookIsNotBorrowedByUser() {
        String username = "username";
        String isbn = "isbn";
        User user = new User();
        user.setUsername(username);
        Book book = new Book();
        book.setIsbn(isbn);
        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(bookRepository.findByIsbn(isbn)).thenReturn(book);

        assertThrows(BookNotFoundException.class, () -> userBookService.returnBook(username, isbn));
    }

    @Test
    void returnBookReturnsBookWhenBookIsReturnedSuccessfully() throws UserNotFoundException, BookNotFoundException {
        String username = "username";
        String isbn = "isbn";
        User user = new User();
        user.setUsername(username);
        Book book = new Book();
        book.setIsbn(isbn);
        book.setAvailable(0);
        user.setBooks(new HashSet<>(Collections.singletonList(book)));
        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(bookRepository.findByIsbn(isbn)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);

        Book returnedBook = userBookService.returnBook(username, isbn);

        assertEquals(book, returnedBook);
        assertEquals(1, book.getAvailable());
        assertFalse(book.getUsers().contains(user));
    }

    @Test
    void returnBookThrowsBookNotFoundExceptionWhenBookIsReturnedThatWasNotBorrowed() throws UserNotFoundException, BookNotFoundException {
        String username = "username";
        String isbn = "isbn";
        User user = new User();
        user.setUsername(username);
        Book book = new Book();
        book.setIsbn(isbn);
        book.setAvailable(0);
        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(bookRepository.findByIsbn(isbn)).thenReturn(book);

        assertThrows(BookNotFoundException.class, () -> userBookService.returnBook(username, isbn));
    }
}