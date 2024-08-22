package com.accenture.library.service;

import com.accenture.library.dao.*;
import com.accenture.library.dto.*;
import com.accenture.library.exceptions.*;
import com.accenture.library.repository.entity.*;

public interface UserService {
    User addUser(UserDto userDto) throws UserAlreadyExistsException;

    UserDao getUserInfo(String username) throws UserNotFoundException;
}
