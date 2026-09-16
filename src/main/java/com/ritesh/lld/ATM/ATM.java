package com.ritesh.lld.ATM;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ATM {
    String atmId;
    BankType ownerBank;
    double availableCash;
}



