package com.example.UserService.externalServices;

import com.example.UserService.model.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="RATINGSERVICE")
public interface RatingService {

    @PostMapping("/ratings")
    public Rating createRating(Rating rating);

    @PutMapping("/ratings/{ratingId}")
    public Rating updateRating(@PathVariable Integer ratingId, Rating rating);

    @DeleteMapping("/ratings/{ratingId}")
    public void deleteRating(@PathVariable Integer ratingId);
}
