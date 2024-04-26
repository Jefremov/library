package com.accenture.libraryManaging.mapper;

import com.accenture.libraryManaging.dao.*;
import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.repository.entity.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class UserMapper {
    public User userDtoToUser(UserDto userDto) {

        User user = new User();
        user.setUsername(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setOrders(new ArrayList<Order>());
        return user;
    }

    public UserDao userToUserDao(User user) {
        UserDao userDao = new UserDao();
        userDao.setName(user.getUsername());
        Set<String> books = new HashSet<>();
        for (Book book : user.getBooks()) {
            books.add(book.getIsbn());
        }
        userDao.setBooks(books);
        return userDao;
    }
}
