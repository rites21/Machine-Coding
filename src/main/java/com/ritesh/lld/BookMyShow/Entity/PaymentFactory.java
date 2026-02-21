package com.ritesh.lld.BookMyShow.Entity;

public class PaymentFactory {
    public static Payment newPayment(Payment payment){
        if(payment.getPaymentType() == PaymentType.CARD){
            return new Payment();
            //will make card obj
        }
        return payment;
    }
}
