package Payment_System.PaymentMethod;

import Payment_System.Entity.Payment;
import Payment_System.Entity.PaymentResult;

public class UpiPaymentMethod implements PaymentMethod {

    @Override
    public PaymentResult pay(Payment payment) {

        // Call UPI provider

        return new PaymentResult(payment.getPaymentId(), "SUCCESS");
    }
}