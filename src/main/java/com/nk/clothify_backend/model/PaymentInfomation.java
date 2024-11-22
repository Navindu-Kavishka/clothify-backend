package com.nk.clothify_backend.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//checked

@Getter
@Setter
@NoArgsConstructor
public class PaymentInfomation {


    private String cardholderName;


    private String cardNumber;


    private String expirationDate;


    private String cvv;
}
