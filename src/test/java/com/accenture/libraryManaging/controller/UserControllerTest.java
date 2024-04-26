package com.accenture.libraryManaging.controller;

import com.accenture.libraryManaging.dao.*;
import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.repository.entity.*;
import com.accenture.libraryManaging.service.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserController userController;
    private UserService userService;
    private UserDto userDto;
    private User user;
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        userDto = Mockito.mock(UserDto.class);
        userController = new UserController(userService);
        user = Mockito.mock(User.class);
        userDao = Mockito.mock(UserDao.class);
    }


    @Test
    void addUserReturnsHttpStatusOkWhenUserIsAddedSuccessfully() throws UserAlreadyExistsException {
        UserDto userDto = new UserDto();
        userDto.setName("username");
        when(userService.addUser(userDto)).thenReturn(user);
        ResponseEntity<?> response = userController.addUser(userDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void addUserThrowsUserAlreadyExistsExceptionWhenUsernameAlreadyExists() throws UserAlreadyExistsException {
        UserDto userDto = new UserDto();
        userDto.setName("existingUsername");
        when(userService.addUser(userDto)).thenThrow(UserAlreadyExistsException.class);
        assertThrows(UserAlreadyExistsException.class, () -> userController.addUser(userDto));
    }

    @Test
    void addUserReturnsAddedUserWhenUserIsAddedSuccessfully() throws UserAlreadyExistsException {
        UserDto userDto = new UserDto();
        userDto.setName("username");
        User user = new User();
        user.setUsername("username");
        when(userService.addUser(userDto)).thenReturn(user);
        ResponseEntity<?> response = userController.addUser(userDto);
        assertEquals(user, response.getBody());
    }

    @Test
    void getUserInfoThrowsUserNotFoundExceptionWhenUsernameDoesNotExist() throws UserNotFoundException {
        String username = "nonExistentUsername";
        when(userService.getUserInfo(username)).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> userController.getUserInfo(username));
    }

    @Test
    void getUserInfoReturnsUserInfoWhenUserExists() throws UserNotFoundException {
        String username = "existingUsername";
        UserDao user = new UserDao();
        user.setName(username);
        when(userService.getUserInfo(username)).thenReturn(user);
        ResponseEntity<?> response = userController.getUserInfo(username);
        assertEquals(user, response.getBody());
    }

}