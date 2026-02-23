package com.parabank.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoanApi {

    private static final String REQUEST_LOAN = "/requestLoan";

    public Response requestLoan(int accountId, int amount, int downPayment, int fromAccountId) {
        return given()
                .queryParam("customerId", accountId)
                .queryParam("amount", amount)
                .queryParam("downPayment", downPayment)
                .queryParam("fromAccountId", fromAccountId)
                .when()
                .post(REQUEST_LOAN);
    }


}
