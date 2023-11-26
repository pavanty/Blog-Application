package com.example.blogapplication.service;


import com.example.blogapplication.dtos.SignUpDto;
import com.example.blogapplication.dtos.UserDto;
import com.example.blogapplication.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
