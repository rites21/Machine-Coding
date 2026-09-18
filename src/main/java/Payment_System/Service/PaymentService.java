package Payment_System.Service;

import Payment_System.Entity.Payment;
import Payment_System.Entity.PaymentResult;
import Payment_System.Factory.PaymentMethodFactory;
import Payment_System.PaymentMethod.PaymentMethod;

public class PaymentService {


    public PaymentResult processPayment(Payment payment) {

        PaymentMethod paymentMethod = PaymentMethodFactory.getPaymentMethod(payment.getPaymentMethodType());

        return paymentMethod.pay(payment);
    }
}