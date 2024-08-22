package com.accenture.library.controller;

import com.accenture.library.dto.*;
import com.accenture.library.exceptions.*;
import com.accenture.library.service.*;
import io.swagger.v3.oas.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Add a new user. Unique username is required.")
    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) throws UserAlreadyExistsException {
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.OK);
    }
    @Operation(summary = "Get user information by username")
    @GetMapping("/getInfo/{username}")
    public ResponseEntity<Object> getUserInfo(@PathVariable String username) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getUserInfo(username), HttpStatus.OK);
    }

}
