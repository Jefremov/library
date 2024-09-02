package com.accenture.library.mapper;

import com.accenture.library.dao.*;
import com.accenture.library.dto.*;
import com.accenture.library.repository.entity.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class UserMapper {
    public User userDtoToUser(UserDto userDto) {

        User user = new User();
        user.setUsername(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setOrders(new ArrayList<>());
        return user;
    }

    public UserDao userToUserDao(User user) {
        UserDao userDao = new UserDao();
        userDao.setName(user.getUsername());
        Set<String> books = new HashSet<>(user.getBookIsbns());
        userDao.setBooks(books);
        return userDao;
    }
}
