package com.example.blogapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
@Entity(name = "register")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reg_id;
    private String name;
    @Column(unique = true)
    private String email;
    private String mobilenumber;
    private String password;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BlogCategory> blogCategories;
    public Integer getReg_id() {
        return reg_id;
    }

    public List<BlogCategory> getBlogCategories() {
        return blogCategories;
    }

    public void setBlogCategories(List<BlogCategory> blogCategories) {
        this.blogCategories = blogCategories;
    }

    public User(Integer reg_id, String name, String email, String mobilenumber, String password) {
        this.reg_id = reg_id;
        this.name = name;
        this.email = email;
        this.mobilenumber = mobilenumber;
        this.password = password;
        this.blogCategories = blogCategories;
    }

    public User() {
    }

    public void setReg_id(Integer reg_id) {
        this.reg_id = reg_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}