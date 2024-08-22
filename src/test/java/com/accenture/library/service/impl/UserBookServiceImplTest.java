package com.accenture.library.service.impl;

import com.accenture.library.exceptions.*;
import com.accenture.library.repository.*;
import com.accenture.library.repository.entity.*;
import com.accenture.library.repository.entity.Order;
import com.accenture.library.template.*;
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
    private OrderRepository orderRepository;
    @Mock
    private BorrowBookOperation borrowBookOperation;
    @Mock
    private ReturnBookOperation returnBookOperation;


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
    void getBookThrowsBookNotAvailableExceptionWhenBookIsNotAvailable() throws BookAlreadyTakenException, BookNotAvailableException {
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
        doThrow(new BookNotAvailableException("Book with isbn: " + isbn + " has not been borrowed by user " + username)).when(borrowBookOperation).perform(book, user, new HashSet<>());

        assertThrows(BookNotAvailableException.class, () -> userBookService.getBook(username, isbn));
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
    void returnBookThrowsBookNotFoundExceptionWhenBookIsNotBorrowedByUser() throws BookNotFoundException {
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
        doThrow(new BookNotFoundException("Book with isbn: " + isbn + " has not been borrowed by user " + username)).when(returnBookOperation).perform(book, user, new HashSet<>());

        assertThrows(BookNotFoundException.class, () -> userBookService.returnBook(username, isbn));
    }

    @Test
    void returnBookThrowsBookNotFoundExceptionWhenBookIsReturnedThatWasNotBorrowed() throws BookNotFoundException {
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
        doThrow(new BookNotFoundException("Book with isbn: " + isbn + " has not been borrowed by user " + username)).when(returnBookOperation).perform(book, user, new HashSet<>());

        assertThrows(BookNotFoundException.class, () -> userBookService.returnBook(username, isbn));
    }


    @Test
    void orderBookThrowsBookNotFoundExceptionWhenBookIsbnDoesNotExist() {
        String username = "username";
        String isbn = "nonExistentIsbn";
        when(bookRepository.existsByIsbn(isbn)).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> userBookService.orderBook(username, isbn));
    }

    @Test
    void orderBookThrowsUserNotFoundExceptionWhenUsernameDoesNotExist() {
        String username = "nonExistentUsername";
        String isbn = "isbn";
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
        when(userRepository.existsByUsername(username)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userBookService.orderBook(username, isbn));
    }

    @Test
    void orderBookThrowsBookAlreadyTakenExceptionWhenBookIsAlreadyTakenByUser() {
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

        assertThrows(BookAlreadyTakenException.class, () -> userBookService.orderBook(username, isbn));
    }

    @Test
    void orderBookThrowsBookAlreadyTakenExceptionWhenBookIsAvailable() {
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

        assertThrows(BookAlreadyTakenException.class, () -> userBookService.orderBook(username, isbn));
    }

    @Test
    void orderBookThrowsBookAlreadyTakenExceptionWhenUserHaveAlreadyOrderedBook() {
        String username = "username";
        String isbn = "isbn";
        User user = new User();
        user.setUsername(username);
        Book book = new Book();
        book.setIsbn(isbn);
        book.setAvailable(0);
        Order order = new Order();
        order.setUserId(user.getId());
        order.setBookId(book.getId());
        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(bookRepository.findByIsbn(isbn)).thenReturn(book);
        when(orderRepository.existsByBookIdAndUserId(book.getId(), user.getId())).thenReturn(true);

        assertThrows(BookAlreadyTakenException.class, () -> userBookService.orderBook(username, isbn));
    }

    @Test
    void orderBookReturnsSuccessMessageWhenBookIsNotAvailableAndUserIsValidAndBookIsNotAlreadyTakenByUser()
            throws UserNotFoundException, BookAlreadyTakenException, BookNotFoundException {
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
        when(orderRepository.existsByBookIdAndUserId(book.getId(), user.getId())).thenReturn(false);

        String result = userBookService.orderBook(username, isbn);

        assertEquals("Book with isbn: isbn has been ordered by user username", result);
    }

}