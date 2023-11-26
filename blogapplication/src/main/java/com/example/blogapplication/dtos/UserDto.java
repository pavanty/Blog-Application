package com.example.blogapplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Integer reg_id;
    private String name;

    private String email;
    private String mobilenumber;
    private String password;
    private String token;

}
