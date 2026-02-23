package com.parabank.request;

public class PayeeModel {

    private String name;
    private Address address;
    private String phoneNumber;
    private int accountNumber;

    public PayeeModel(String name, Address address, String phoneNumber, int accountNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
}
