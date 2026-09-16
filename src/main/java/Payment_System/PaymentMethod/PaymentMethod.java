package Payment_System.PaymentMethod;

import Payment_System.Entity.Payment;

public interface PaymentMethod {
    void pay(Payment payment);
}
