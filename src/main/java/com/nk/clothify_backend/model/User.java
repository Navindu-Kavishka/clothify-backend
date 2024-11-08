package com.nk.clothify_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nk.clothify_backend.entity.AddressEntity;
import com.nk.clothify_backend.entity.RatingEntity;
import com.nk.clothify_backend.entity.ReviewEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {


    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;
    private String phoneNumber;


    private List<AddressEntity> addressEntities = new ArrayList<>();

    @Embedded
    @ElementCollection
    private List<PaymentInfomation> paymentInfomation = new ArrayList<>();


    @JsonIgnore
    private List<RatingEntity> ratingEntities = new ArrayList<>();


    @JsonIgnore
    private List<ReviewEntity> reviewEntities = new ArrayList<>();

    private LocalDateTime createdAt;

}
