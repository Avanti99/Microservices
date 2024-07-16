package com.example.UserService.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rating {
    private Integer ratingId;
    private Integer userId;
    private Integer hotelId;
    private Integer rating;
    private String feedback;
    private Hotel hotel;
}
