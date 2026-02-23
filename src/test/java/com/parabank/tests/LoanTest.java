package com.parabank.tests;

import com.parabank.api.LoanApi;
import com.parabank.base.BaseTest;
import com.parabank.config.Config;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.hamcrest.Matchers.*;

public class LoanTest extends BaseTest {

    private LoanApi loanApi;

    @BeforeClass
    public void setup() {
        loanApi = new LoanApi();
    }

    @Test
    public void shouldAbleToRequestLoan() {
        int amount = 1000;
        int downPayment = 10;
        test.get().info("Sending POST loan request: amount=$" + amount + ", downPayment=$" + downPayment + " for customer ID: " + Config.CUSTOMER_ID + " (/requestLoan)");
        Response response = loanApi.requestLoan(Config.CUSTOMER_ID,amount,downPayment,Config.VALID_ACCOUNT_ID);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body("responseDate", allOf(
                        greaterThanOrEqualTo(LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()),
                        lessThan(LocalDate.now().plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())))
                .body("loanProviderName", notNullValue())
                .body("approved", equalTo(true))
                .body("accountId", notNullValue());
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }


    public void shouldUnableToRequestLoan() {
        int amount = 1000;
        int downPayment = 10;
        test.get().info("Sending POST loan request: amount=$" + amount + ", downPayment=$" + downPayment + " for customer ID: " + Config.CUSTOMER_ID + " (/requestLoan)");
        Response response = loanApi.requestLoan(Config.CUSTOMER_ID,amount,downPayment,Config.VALID_ACCOUNT_ID);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body("responseDate", allOf(
                        greaterThanOrEqualTo(LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()),
                        lessThan(LocalDate.now().plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())))
                .body("loanProviderName", notNullValue())
                .body("approved", equalTo(true))
                .body("accountId", notNullValue());
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }


}
