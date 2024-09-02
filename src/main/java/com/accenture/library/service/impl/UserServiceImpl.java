package com.accenture.library.service.impl;

import com.accenture.library.dao.*;
import com.accenture.library.dto.*;
import com.accenture.library.exceptions.*;
import com.accenture.library.mapper.*;
import com.accenture.library.repository.*;
import com.accenture.library.repository.entity.*;
import com.accenture.library.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserBookService userBookService;

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
        HashSet<String> books = new HashSet<>();
        user.setBookIsbns(books);
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
