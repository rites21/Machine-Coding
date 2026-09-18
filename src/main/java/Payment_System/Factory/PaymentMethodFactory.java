package Payment_System.Factory;

import Payment_System.Entity.PaymentMethodType;
import Payment_System.PaymentMethod.CardPaymentMethod;
import Payment_System.PaymentMethod.NetBankingPaymentMethod;
import Payment_System.PaymentMethod.PaymentMethod;
import Payment_System.PaymentMethod.UpiPaymentMethod;

public class PaymentMethodFactory {

    public static PaymentMethod getPaymentMethod(PaymentMethodType type) {

        return switch (type) {

            case CARD -> new CardPaymentMethod();

            case UPI -> new UpiPaymentMethod();

            case NET_BANKING -> new NetBankingPaymentMethod();
        };
    }
}