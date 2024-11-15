package com.nk.clothify_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nk.clothify_backend.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {


    private Long id;


    private String firstName;


    private String lastName;


    private String streetAddress;


    private String city;


    private String state;


    private String zipCode;


    private UserEntity userEntity;

    private String phoneNumber;


}
