package com.example.blogapplication.service;

import com.example.blogapplication.dtos.CredentialsDto;
import com.example.blogapplication.dtos.SignUpDto;
import com.example.blogapplication.dtos.UserDto;
import com.example.blogapplication.entity.User;

import java.util.List;

public interface IuserService {
    User saveUser(User register);
//    public boolean login(String email, String password);

    List<User> showallregistration();

    UserDto register(SignUpDto user);

    UserDto login(CredentialsDto credentialsDto);


    String getUserNameById(int regId);

   UserDto findByEmail(String currentUserName);




    UserDto loadUserByUsername(String testuser);
}
