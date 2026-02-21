package com.ritesh.lld.BookMyShow.Services;

import com.ritesh.lld.BookMyShow.Entity.Payment;
import com.ritesh.lld.BookMyShow.Entity.User;
import com.ritesh.lld.BookMyShow.Strategies.PaymentSTratiges;
import com.ritesh.lld.BookMyShow.Strategies.UpiPayment;

public class PaymentService {
    private Payment payment;
    private PaymentSTratiges paymentSTratiges;
    private User user;

    public PaymentService(Payment payment, PaymentSTratiges paymentSTratiges, User user) {
        this.payment = payment;
        this.paymentSTratiges = paymentSTratiges;
        this.user = user;
    }


    public String makePayment() {
        //create a payment obj
        Payment currPayment = new Payment();
        //create good obj
        payment.pay(new UpiPayment());
        //confirm from razor pay status
        return "SUCESS";
    }
}
