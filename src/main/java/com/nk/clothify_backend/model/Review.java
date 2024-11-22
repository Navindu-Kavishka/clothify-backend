package com.nk.clothify_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nk.clothify_backend.entity.ProductEntity;
import com.nk.clothify_backend.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {


    private Long id;

    private String review;

    private ProductEntity productEntity;

    private UserEntity userEntity;

    private LocalDateTime createdAt;

}
