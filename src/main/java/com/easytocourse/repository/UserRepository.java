package com.easytocourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.easytocourse.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);
     
}