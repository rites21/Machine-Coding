package com.ritesh.lld.ATM;

public class WithdrawTransaction extends Transaction {
    private final ATM atm;
    private final Account account;
    private final double amount;

    public WithdrawTransaction(ATM atm, Account account1, double amount) {
        this.atm = atm;
        this.account = account1;
        this.amount = amount;
    }


    @Override
    public void execute() {
        // 1. Validate ATM cash
        if (amount > atm.getAvailableCash()) {
            status = TransactionStatus.CANCEL;
            throw new RuntimeException("ATM has insufficient cash");
        }

        // 2. Validate account balance
        if (amount > account.getBalance()) {
            status = TransactionStatus.CANCEL;
            throw new RuntimeException("Insufficient account balance");
        }

        // 3. Debit account
        account.setBalance(account.getBalance() - amount);

        // 4. Debit ATM cash
        atm.setAvailableCash(atm.getAvailableCash() - amount);

        // 5. Mark success
        status = TransactionStatus.COMPLETED;

        System.out.println("Please collect cash: " + amount);
    }
}
