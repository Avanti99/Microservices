package com.example.UserService.service.impl;

import com.example.UserService.dao.UserDao;
import com.example.UserService.exception.ResourceNotFoundException;
import com.example.UserService.externalServices.HotelService;
import com.example.UserService.model.Hotel;
import com.example.UserService.model.Rating;
import com.example.UserService.model.User;
import com.example.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RestTemplate restTemplate;

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
        User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));

        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/" + userId, Rating[].class);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);

        return user;
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
