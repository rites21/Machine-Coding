package Payment_System.PaymentMethod;

import Payment_System.Entity.Payment;
import Payment_System.Entity.PaymentResult;

public interface PaymentMethod {

    PaymentResult pay(Payment payment);
}