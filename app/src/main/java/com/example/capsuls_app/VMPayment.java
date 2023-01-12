package com.example.capsuls_app;

public class VMPayment {
    public String cardNumber;
    public String ccv2;
    public String holder;
    public  double balance;
    public VMPayment() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public VMPayment(String cardNumber, String ccv2,String holder,double balance) {
        this.cardNumber = cardNumber;
        this.ccv2 = ccv2;
        this.holder=holder;
        this.balance=balance;


    }
}
