package Payment_System.PaymentMethod;

import Payment_System.Entity.Payment;
import Payment_System.Entity.PaymentResult;

public class NetBankingPaymentMethod implements PaymentMethod {

    @Override
    public PaymentResult pay(Payment payment) {

        // Call net banking provider

        return new PaymentResult(payment.getPaymentId(), "SUCCESS");
    }
}