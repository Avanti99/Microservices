package com.example.UserService.controller;

import com.example.UserService.model.User;
import com.example.UserService.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    int retryCount = 1;

    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
//    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable Integer userId) {
        retryCount++;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> ratingHotelFallback(Integer userId, Exception ex) {
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created dummy because some service is down")
                .userId(3)
                .build();
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer userId) {
        User user = userService.getUser(userId);
        User deletedUser =  userService.deleteUser(user);
        return ResponseEntity.ok(deletedUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }
}
