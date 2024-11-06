package com.nk.clothify_backend.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PaymentInfomation {

    @Column(name = "cardholder_name")
    private String cardholderName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "cvv")
    private String cvv;
}
