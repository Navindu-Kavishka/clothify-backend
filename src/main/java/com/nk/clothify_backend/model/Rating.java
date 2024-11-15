package com.nk.clothify_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nk.clothify_backend.entity.ProductEntity;
import com.nk.clothify_backend.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Rating {


    private Long id;

    private UserEntity userEntity;

    private ProductEntity productEntity;

    private double rating;

    private LocalDateTime createdAt;

}
