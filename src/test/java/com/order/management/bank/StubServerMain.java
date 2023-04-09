package com.order.management.bank;

public class StubServerMain {

    public static BankStub bankStub = new BankStub();

    public static void main(String[] args) {
        bankStub.setUp()
                .stubForGetDebitCard("SampleDebitCard.json")
                .status();
    }
}
