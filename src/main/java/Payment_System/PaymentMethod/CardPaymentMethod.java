package Payment_System.PaymentMethod;

import Payment_System.Entity.Payment;
import Payment_System.Entity.PaymentResult;

public class CardPaymentMethod implements PaymentMethod {

    @Override
    public PaymentResult pay(Payment payment) {

        // Call external card/payment provider here

        return new PaymentResult(payment.getPaymentId(), "SUCCESS");
    }
}