package com.order.management.bank;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.order.management.utils.JsonUtil;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class BankStub {

    private JsonUtil jsonUtil;
    public WireMockServer wireMockServer;

    public BankStub setUp() {
        wireMockServer = new WireMockServer(9090);
        wireMockServer.start();
        jsonUtil = new JsonUtil();
        return this;
    }

    public BankStub resetServer() {
        wireMockServer.resetAll();
        return this;
    }

    public BankStub stubForGetDebitCard(String responseFileName) {
        wireMockServer
                .stubFor(WireMock.get("/cards?debitCard=123456789012")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("json/" + responseFileName)));
        return this;
    }

    public BankStub status() {
        System.out.println("Bank Stub Started!");
        return this;
    }

}
