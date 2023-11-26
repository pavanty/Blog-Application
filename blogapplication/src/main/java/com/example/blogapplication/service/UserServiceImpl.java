package com.example.blogapplication.service;

import com.example.blogapplication.dtos.CredentialsDto;
import com.example.blogapplication.dtos.SignUpDto;
import com.example.blogapplication.dtos.UserDto;
import com.example.blogapplication.entity.User;
import com.example.blogapplication.exception.AppException;
import com.example.blogapplication.exception.UserNotFoundException;
import com.example.blogapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl  implements  IuserService{

    @Autowired
    UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;
    @Override
    public User saveUser(User register) {
        return userRepository.save(register);
    }



    @Override
    public List<User> showallregistration() {
        return userRepository.findAll();
    }

    @Override
    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new UserNotFoundException("Login already exists");
        }

    User user1 = userMapper.signUpToUser(userDto);
        user1.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        User savedUser = userRepository.save(user1);

        return userMapper.toUserDto(savedUser);
    }

    @Override
    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Override
    public String getUserNameById(int reg_id) {
        Optional<User> userOptional = userRepository.findById(reg_id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getName();
        } else {

            throw new UserNotFoundException("User not found with ID: " + reg_id);
        }
    }


    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto loadUserByUsername(String testuser) {
        return null;
    }




}
