package com.ritesh.lld.ATM;

import java.util.Random;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Transaction {
    String transactionId;
    TransactionStatus status;

    public Transaction(){
        this.transactionId = UUID.randomUUID().toString();
        this.status = TransactionStatus.INITIATED;
    }
    abstract void execute();
}

