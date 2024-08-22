package com.accenture.library.mapper;

import com.accenture.library.dao.*;
import com.accenture.library.dto.UserDto;
import com.accenture.library.repository.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void userDtoToUser_withValidUserDto_returnsUser() {
        UserDto userDto = new UserDto();
        userDto.setName("Test User");
        userDto.setEmail("email");

        User user = userMapper.userDtoToUser(userDto);

        assertEquals("Test User", user.getUsername());
        assertEquals("email", user.getEmail());
        assertEquals(new HashSet<>(), user.getBooks());
        assertEquals(new ArrayList<>(), user.getOrders());
    }

    @Test
    void userDtoToUser_withEmptyNameInUserDto_returnsUserWithEmptyUsername() {
        UserDto userDto = new UserDto();
        userDto.setName("");
        userDto.setEmail("");

        User user = userMapper.userDtoToUser(userDto);

        assertEquals("", user.getUsername());
        assertEquals("", user.getEmail());
    }

    @Test
    void userToUserDao_withValidUser_returnsUserDao() {
        User user = new User();
        user.setUsername("Test User");
        Book book1 = new Book();
        book1.setIsbn("ISBN1");
        Book book2 = new Book();
        book2.setIsbn("ISBN2");
        user.setBooks(new HashSet<>(Arrays.asList(book1, book2)));

        UserDao userDao = userMapper.userToUserDao(user);

        assertEquals("Test User", userDao.getName());
        assertTrue(userDao.getBooks().contains("ISBN1"));
        assertTrue(userDao.getBooks().contains("ISBN2"));
    }

    @Test
    void userToUserDao_withUserHavingNoBooks_returnsUserDaoWithEmptyBookSet() {
        User user = new User();
        user.setUsername("Test User");

        UserDao userDao = userMapper.userToUserDao(user);

        assertEquals("Test User", userDao.getName());
        assertTrue(userDao.getBooks().isEmpty());
    }
}
