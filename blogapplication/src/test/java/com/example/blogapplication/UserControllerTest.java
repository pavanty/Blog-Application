package com.example.blogapplication;

import com.example.blogapplication.config.UserAuthenticationProvider;
import com.example.blogapplication.controller.UserController;
import com.example.blogapplication.dtos.CredentialsDto;
import com.example.blogapplication.dtos.SignUpDto;
import com.example.blogapplication.dtos.UserDto;
import com.example.blogapplication.entity.User;
import com.example.blogapplication.repository.UserRepository;
import com.example.blogapplication.service.IuserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private IuserService userService;

    @Mock
    private UserAuthenticationProvider userAuthenticationProvider;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setName("Test User");
        signUpDto.setEmail("test@example.com");
        signUpDto.setPassword("password");


        UserDto userDto = new UserDto();
        userDto.setReg_id(1);
        userDto.setName("Test User");
        userDto.setEmail("test@example.com");
        userDto.setToken("mocked_token");


        when(userService.register(signUpDto)).thenReturn(userDto);
        when(userAuthenticationProvider.createToken(signUpDto.getEmail())).thenReturn("mocked_token");


        ResponseEntity<UserDto> responseEntity = userController.register(signUpDto);


        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(userDto, responseEntity.getBody());
        assertEquals("mocked_token", userDto.getToken());


        verify(userService, times(1)).register(signUpDto);
        verify(userAuthenticationProvider, times(1)).createToken(signUpDto.getEmail());
    }

    @Test
    void testLogin() {

        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setEmail("test@example.com");
        credentialsDto.setPassword("password");
        UserDto userDto = new UserDto();
        userDto.setReg_id(1);
        userDto.setName("Test User");
        userDto.setEmail("test@example.com");
        userDto.setToken("mocked_token");
        when(userService.login(credentialsDto)).thenReturn(userDto);
        when(userAuthenticationProvider.createToken(userDto.getEmail())).thenReturn("mocked_token");

        ResponseEntity<UserDto> responseEntity = userController.login(credentialsDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(userDto, responseEntity.getBody());
        assertEquals("mocked_token", userDto.getToken());

        verify(userService, times(1)).login(credentialsDto);
        verify(userAuthenticationProvider, times(1)).createToken(userDto.getEmail());
    }

    @Test
    void testShowAllUsers() {

        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "User 1", "user1@example.com", "123456", "123456"));
        userList.add(new User(2, "User 2", "user2@example.com", "654321", "1234567"));


        when(userService.showallregistration()).thenReturn(userList);


        ResponseEntity<List<User>> responseEntity = userController.showallregistration();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(userList, responseEntity.getBody());
        verify(userService, times(1)).showallregistration();
    }


}