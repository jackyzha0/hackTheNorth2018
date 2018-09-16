package com.example.jacky.htntappit;

public class Transaction {
    String sender;
    String recipient;
    double amount;
    public Transaction(String s,String r,double a) {
        sender = s;
        recipient = r;
        amount = a;
    }
}
