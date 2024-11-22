package com.nk.clothify_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "rating")
@Data
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity userEntity;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private ProductEntity productEntity;

    @Column(name = "rating")
    private int rating;

    private LocalDateTime createdAt;

}
