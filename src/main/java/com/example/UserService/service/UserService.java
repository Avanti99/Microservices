package com.example.UserService.service;

import com.example.UserService.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    List<User> getAllUsers();

    User getUser(Integer userId);

    User deleteUser(User user);

    User updateUser(Integer userId, User user);
}
