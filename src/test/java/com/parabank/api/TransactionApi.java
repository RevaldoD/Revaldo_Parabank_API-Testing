package com.parabank.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TransactionApi {

    private static final String TRANSACTION_LISTS_TRANSACTIONID = "/transactions/{transactionId}";
    private static final String TRANSACTION_LISTS_RANGE_DATE = "/accounts/{accountId}/transactions/fromDate/{fromDate}/toDate/{toDate}";
    private static final String TRANSACTION_LISTS_EXACT_DATE = "/accounts/{accountId}/transactions/onDate/{onDate}";
    private static final String TRANSACTION_LISTS_AMOUNT = "/accounts/{accountId}/transactions/amount/{amount}";

    public Response getTransactionWithId(int transactionId) {
        return given()
                .pathParam("transactionId", transactionId)
                .when()
                .get(TRANSACTION_LISTS_TRANSACTIONID);
    }

    public Response getTransactionWithRangeDate(int accountId, String fromDate, String toDate) {
        return given()
                .pathParam("accountId", accountId)
                .pathParam("fromDate", fromDate)
                .pathParam("toDate", toDate)
                .when()
                .get(TRANSACTION_LISTS_RANGE_DATE);
    }

    public Response getTransactionWithExactDate(int accountId, String Date) {
        return given()
                .pathParam("accountId", accountId)
                .pathParam("onDate", Date)
                .when()
                .get(TRANSACTION_LISTS_EXACT_DATE);
    }

    public Response getTransactionWithAmount(int accountId, int amount) {
        return given()
                .pathParam("accountId", accountId)
                .pathParam("amount", amount)
                .when()
                .get(TRANSACTION_LISTS_AMOUNT);
    }

}
