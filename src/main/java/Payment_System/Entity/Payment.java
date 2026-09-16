package Payment_System.Entity;

public class Payment {
    private String paymentId;
    private String customerId;
    private String merchantId;
    private double amount;
    private PaymentStatus status;
    private PaymentMethodType paymentMethodType;
}
