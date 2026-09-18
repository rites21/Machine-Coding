package Payment_System.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {
    private String paymentId;
    private String customerId;
    private String merchantId;
    private double amount;
    private PaymentStatus status;
    private PaymentMethodType paymentMethodType;
}
