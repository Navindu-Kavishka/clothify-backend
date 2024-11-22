package com.nk.clothify_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nk.clothify_backend.model.PaymentInfomation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;
    private String phoneNumber;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<AddressEntity> addressEntities = new ArrayList<>();

    @Embedded
    @ElementCollection
    @CollectionTable(name = "payment_infomation",joinColumns = @JoinColumn(name = "user_id"))
    private List<PaymentInfomation> paymentInfomation = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RatingEntity> ratingEntities = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewEntity> reviewEntities = new ArrayList<>();

    private LocalDateTime createdAt;

}
