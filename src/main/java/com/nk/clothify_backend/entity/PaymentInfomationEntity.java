package com.nk.clothify_backend.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//checked

@Getter
@Setter
@NoArgsConstructor
public class PaymentInfomationEntity {

    @Column(name = "cardholder_name")
    private String cardholderName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "cvv")
    private String cvv;
}
