package com.example.jacky.htntappit;

public class VerifyBoolean {
    public static boolean verify = true; //fingerprint has been verified
    public static boolean balance = true; //you can send money

    public static boolean getVerify() {
        return verify;
    }

    public static boolean getBalance() {
        return balance;
    }

    public static void setBalance(boolean b) {
        balance = b;
    }

    public static void setVerify(boolean b) {
        verify = b;
    }

}