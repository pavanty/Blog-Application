package com.example.blogapplication.controller;

import com.example.blogapplication.config.UserAuthenticationProvider;
import com.example.blogapplication.dtos.CredentialsDto;
import com.example.blogapplication.dtos.SignUpDto;
import com.example.blogapplication.entity.User;
import com.example.blogapplication.repository.UserRepository;
import com.example.blogapplication.service.IuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.blogapplication.dtos.UserDto;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
//@RequestMapping(value = "/user")
@CrossOrigin(value = "*")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IuserService userService;
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;


    @PostMapping(value = "/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        UserDto createdUser = userService.register(user);

        createdUser.setToken(userAuthenticationProvider.createToken(user.getEmail()));


        return ResponseEntity.created(URI.create("/users/" + createdUser.getReg_id())).body(createdUser);
    }


    @PostMapping(value="/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto.getEmail()));
        return ResponseEntity.ok(userDto);
    }


    @GetMapping(value="/showallusers")
    public ResponseEntity<List<User>> showallregistration(){
        List<User> allregister=userService.showallregistration();
        return  new ResponseEntity<List<User>>(allregister,HttpStatus.OK);
    }


}
