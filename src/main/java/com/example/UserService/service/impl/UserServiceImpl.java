package com.example.UserService.service.impl;

import com.example.UserService.dao.UserDao;
import com.example.UserService.exception.ResourceNotFoundException;
import com.example.UserService.model.User;
import com.example.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUser(Integer userId) {
        return userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));
    }

    @Override
    public User deleteUser(User user) {
        userDao.delete(user);
        return user;
    }

    @Override
    public User updateUser(Integer userId, User user) {
        Optional<User> updatedUser = userDao.findById(userId);
        if(updatedUser.isPresent()) {
            User existingUser = updatedUser.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setAbout(user.getAbout());
            return userDao.save(existingUser);
        } else {
            throw new ResourceNotFoundException("User with given id is not found on server !! : " + userId);
        }
    }
}
