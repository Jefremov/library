package com.accenture.library.service.impl;

import com.accenture.library.dao.*;
import com.accenture.library.dto.UserDto;
import com.accenture.library.exceptions.*;
import com.accenture.library.mapper.UserMapper;
import com.accenture.library.repository.UserRepository;
import com.accenture.library.repository.entity.User;
import com.accenture.library.service.UserBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserBookService userBookService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUser_whenUserAlreadyExists_throwsException() {
        UserDto userDto = new UserDto();
        userDto.setName("Test User");

        User user = new User();
        user.setUsername("Test User");

        when(userMapper.userDtoToUser(userDto)).thenReturn(user);
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.addUser(userDto));
    }

    @Test
    void addUser_whenUserDoesNotExist_savesUser() throws UserAlreadyExistsException {
        UserDto userDto = new UserDto();
        userDto.setName("New User");

        User user = new User();
        user.setUsername("New User");

        when(userMapper.userDtoToUser(userDto)).thenReturn(user);
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.addUser(userDto);

        assertEquals("New User", savedUser.getUsername());
    }

    @Test
    void getUserInfo_whenUserNotFound_throwsException() {
        String username = "NonExistentUser";

        when(userRepository.existsByUsername(username)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.getUserInfo(username));
    }

    @Test
    void getUserInfoReturnsCorrectInfoWhenUserExists() throws UserNotFoundException {
        String username = "ExistingUser";
        User user = new User();
        user.setUsername(username);
        UserDao userDaoExpected = new UserDao();
        userDaoExpected.setName(username);
        userDaoExpected.setBooks(Collections.emptySet());

        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(userBookService.getBooksByUser(user)).thenReturn(Collections.emptySet());
        when(userMapper.userToUserDao(user)).thenReturn(userDaoExpected);

        UserDao userDaoActual = userService.getUserInfo(username);

        assertEquals(userDaoExpected.getName(), userDaoActual.getName());
        assertEquals(userDaoExpected.getBooks(), userDaoActual.getBooks());
    }
}
