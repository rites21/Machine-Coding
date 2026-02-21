package com.ritesh.lld.BookMyShow.Entity;

import java.util.Random;
import java.util.UUID;

import com.ritesh.lld.BookMyShow.Strategies.PaymentSTratiges;

import lombok.Data;

@Data
public class Payment {
    private String paymentId;
    private PaymentType paymentType;
    private Double amount;
    private UUID transactionId;
    private String userId;


    public void pay(PaymentSTratiges paymentSTratiges){

    }
}
