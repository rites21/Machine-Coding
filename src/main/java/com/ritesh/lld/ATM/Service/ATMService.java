package com.ritesh.lld.ATM.Service;

import com.ritesh.lld.ATM.ATM;
import com.ritesh.lld.ATM.Account;
import com.ritesh.lld.ATM.Transaction;
import com.ritesh.lld.ATM.WithdrawTransaction;

public class ATMService {
    private ATM atm;

    public ATMService(ATM atm){
        this.atm = atm;
    }
    public Transaction withdrawMoney(Account account, Double requestMoney){
        if(requestMoney > atm.getAvailableCash()){
            throw new RuntimeException("No cash avilable");
        }
        WithdrawTransaction withdrawTransaction  = new WithdrawTransaction(atm, account, requestMoney);
        synchronized (atm){
            withdrawTransaction.execute();
        }
        return withdrawTransaction;

    }

//    pinchane,balanceInquiry
    public void pinChange(Account account, String PIN){
        atm.setAtmId(PIN);
    }


}
