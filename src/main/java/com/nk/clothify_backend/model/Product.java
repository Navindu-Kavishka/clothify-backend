package com.nk.clothify_backend.model;

import com.nk.clothify_backend.entity.CategoryEntity;
import com.nk.clothify_backend.entity.RatingEntity;
import com.nk.clothify_backend.entity.ReviewEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {


    private Long id;

    private String title;

    private String description;

    private double price;

    private double discountedPrice;

    private double discountPercentage;

    private int quantity;

    private String brand;
    private String color;

    @ElementCollection
    private Set<Size> sizes = new HashSet<>();


    private String imageUrl;


    private List<RatingEntity> ratingEntities = new ArrayList<>();

    private List<ReviewEntity> reviewEntities = new ArrayList<>();

    private int numRatings;

    private CategoryEntity categoryEntity;

    private LocalDateTime createdAt;

}
