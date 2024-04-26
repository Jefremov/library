package com.accenture.libraryManaging.service.impl;

import com.accenture.libraryManaging.dao.*;
import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.mapper.*;
import com.accenture.libraryManaging.repository.*;
import com.accenture.libraryManaging.repository.entity.*;
import com.accenture.libraryManaging.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private UserRepository userRepository;
    private UserBookService userBookService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, UserBookService userBookService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.userBookService = userBookService;
    }


    @Override
    public User addUser(UserDto userDto) throws UserAlreadyExistsException {
        User user = userMapper.userDtoToUser(userDto);
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("User " + user.getUsername() + " already exists");
        }
        Set<Book> books = new HashSet<>();
        user.setBooks(books);
        return userRepository.save(user);
    }

    @Override
    public UserDao getUserInfo(String username) throws UserNotFoundException {
        if(!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException("User with username: " + username + " not found");
        }
        User user = userRepository.findByUsername(username);
        UserDao userDao = userMapper.userToUserDao(user);
        userDao.setBooks(userBookService.getBooksByUser(user));
        return userDao;
    }

}
