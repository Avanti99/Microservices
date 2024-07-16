package com.example.UserService;

import com.example.UserService.externalServices.RatingService;
import com.example.UserService.model.Rating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	RatingService ratingService;

	@Test
	void contextLoads() {
	}

	@Test
	void createRating() {
		Rating rating = Rating.builder().ratingId(3).userId(2).hotelId(2).rating(4).feedback("Food is not good").build();
		Rating savedRating = ratingService.createRating(rating);
	}

	@Test
	void updateRating() {
		Rating rating = Rating.builder().ratingId(2).userId(2).hotelId(2).rating(9).feedback("Food is very tasty").build();
		Rating updatedRating = ratingService.updateRating(rating.getRatingId(),rating);
	}

	@Test
	void deleteRating() {
		ratingService.deleteRating(3);
	}
}
