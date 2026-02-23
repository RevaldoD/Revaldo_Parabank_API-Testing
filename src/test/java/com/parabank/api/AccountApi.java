package com.parabank.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AccountApi {

    public enum AccountType {
        CHECKING(0),
        SAVINGS(1);

        private final int code;

        AccountType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    private static final String CREATE_ACCOUNT = "/createAccount";
    private static final String GET_CUSTOMER = "/customers/{customerId}/accounts";
    private static final String GET_ACCOUNT = "/accounts/{accountId}";
    private static final String TRANSFER_FUNDS = "/transfer";
    private static final String BILL_PAY = "/billpay";
    private static final String UPDATE_CUSTOMER_INFO= "/customers/update/{customerId}";

    public Response createAccount(int customerId, AccountType accountType, int fromAccountId) {
        return given()
                .queryParam("customerId", customerId)
                .queryParam("newAccountType", accountType.getCode())
                .queryParam("fromAccountId", fromAccountId)
            .when()
                .post(CREATE_ACCOUNT);
    }

    public Response getCustomer(int customerId) {
        return given()
                .pathParam("customerId", customerId)
            .when()
                .get(GET_CUSTOMER);
    }

    public Response getAccount(int accountId) {
        return given()
                .pathParam("accountId", accountId)
                .when()
                .get(GET_ACCOUNT);
    }

    public Response transferFunds(int accountId, int anotherAccount, int amount) {
        return given()
                .queryParam("fromAccountId", accountId)
                .queryParam("toAccountId", anotherAccount)
                .queryParam("amount", amount)
                .when()
                .post(TRANSFER_FUNDS);
    }


    public Response billPayment(int accountId, int amount, Object pay) {
        return given()
                .contentType(ContentType.JSON)
                .queryParam("accountId", accountId)
                .queryParam("amount", amount)
                .body(pay)
                .when()
                .post(BILL_PAY);
    }

    public Response updateCustomerInfo(int customerId, String firstName, String lastName, String street, String city, String state, String zip, String phoneNumber, String ssn, String username, String password) {
        return given()
                .contentType(ContentType.JSON)
                .pathParam("customerId", customerId)
                .queryParam("firstName", firstName)
                .queryParam("lastName", lastName)
                .queryParam("street", street)
                .queryParam("city", city)
                .queryParam("state", state)
                .queryParam("zipCode", zip)
                .queryParam("phoneNumber", street)
                .queryParam("ssn", ssn)
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .post(UPDATE_CUSTOMER_INFO);
    }
}
