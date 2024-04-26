package com.accenture.libraryManaging.service;

import com.accenture.libraryManaging.dao.*;
import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.repository.entity.*;

public interface UserService {
    User addUser(UserDto userDto) throws UserAlreadyExistsException;

    UserDao getUserInfo(String username) throws UserNotFoundException;
}
