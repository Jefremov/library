package com.accenture.library.controller;

import com.accenture.library.dao.*;
import com.accenture.library.dto.*;
import com.accenture.library.exceptions.*;
import com.accenture.library.repository.entity.*;
import com.accenture.library.service.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserController userController;
    private UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
        user = Mockito.mock(User.class);
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
        User testUser = new User();
        testUser.setUsername("username");
        when(userService.addUser(userDto)).thenReturn(testUser);
        ResponseEntity<?> response = userController.addUser(userDto);
        assertEquals(testUser, response.getBody());
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
        UserDao userDao = new UserDao();
        userDao.setName(username);
        when(userService.getUserInfo(username)).thenReturn(userDao);
        ResponseEntity<?> response = userController.getUserInfo(username);
        assertEquals(userDao, response.getBody());
    }

}