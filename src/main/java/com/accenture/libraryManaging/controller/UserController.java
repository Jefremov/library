package com.accenture.libraryManaging.controller;

import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.service.*;
import io.swagger.v3.oas.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Add a new user. Unique username is required.")
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto) throws UserAlreadyExistsException {

        try {
            ResponseEntity<?> responseEntity = new ResponseEntity<>(userService.addUser(userDto), HttpStatus.OK);
            System.out.println(responseEntity.getBody());
            return new ResponseEntity<>(responseEntity.getStatusCode());
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @Operation(summary = "Get user information by username")
    @GetMapping("/getInfo/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username) throws UserNotFoundException {
        try {
            ResponseEntity<?> responseEntity = new ResponseEntity<>(userService.getUserInfo(username), HttpStatus.OK);
            System.out.println(responseEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

}
