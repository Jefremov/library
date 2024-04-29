package com.accenture.libraryManaging.Randomizer;

import com.accenture.libraryManaging.dto.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.*;
import java.util.*;

public class AddingUserRandomizer {


    private static final URI ADD_USER_URL = URI.create("http://localhost:8080/users/add");

    public static void addUsers() {
        for (int i = 0; i < 20; i++) {
            addRandomUser();
        }
    }

    private static void addRandomUser() {
        RestTemplate restTemplate = new RestTemplate();

        UserDto userDto = generateRandomUserDto();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDto> requestEntity = new HttpEntity<>(userDto, headers);

        ResponseEntity<?> responseEntity = restTemplate.postForEntity(ADD_USER_URL, requestEntity, String.class);
    }

    private static UserDto generateRandomUserDto() {
        Random random = new Random();
        UserDto userDto = new UserDto();
        String prefix = String.valueOf(random.nextInt(12));
        userDto.setName("penguin" + prefix);
        userDto.setEmail("penguin" + prefix + "@hotmail.aq");
        return userDto;
    }
}
