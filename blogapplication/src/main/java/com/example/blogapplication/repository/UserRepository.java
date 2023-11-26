package com.example.blogapplication.repository;

import com.example.blogapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public Optional<User> findByemailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);



}
