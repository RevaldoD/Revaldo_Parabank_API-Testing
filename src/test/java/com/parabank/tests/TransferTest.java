package com.parabank.tests;

import com.parabank.api.AccountApi;
import com.parabank.base.BaseTest;
import com.parabank.config.Config;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;

public class TransferTest extends BaseTest {

    private AccountApi accountApi;

    @BeforeClass
    public void setup() {
        accountApi = new AccountApi();
    }

    @Test
    public void shouldTransferFundsWithSameAccount() {
        int amount = 100;
        test.get().info("Sending POST transfer of $" + amount + " from account " + Config.VALID_ACCOUNT_ID + " to same account " + Config.VALID_ACCOUNT_ID + " (/transfer)");
        Response response = accountApi.transferFunds(Config.VALID_ACCOUNT_ID, Config.VALID_ACCOUNT_ID, amount);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body(containsString("Successfully transferred $" + amount + " from account #"+ Config.VALID_ACCOUNT_ID + " to account #" + Config.VALID_ACCOUNT_ID));
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }

    @Test
    public void shouldTransferFundsWithDifferentAccount() {
        int amount = 100;
        int account = 13455;
        test.get().info("Sending POST transfer of $" + amount + " from account " + Config.VALID_ACCOUNT_ID + " to account " + account + " (/transfer)");
        Response response = accountApi.transferFunds(Config.VALID_ACCOUNT_ID, account, amount);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body(containsString("Successfully transferred $" + amount + " from account #" + Config.VALID_ACCOUNT_ID + " to account #" +account));
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }

    @Test
    public void shouldTransferFundsWithZeroAmount() {
        int amount = 0;
        int account = 13455;
        test.get().info("Sending POST transfer of $" + amount + " (zero) from account " + Config.VALID_ACCOUNT_ID + " to account " + account + " (/transfer)");
        Response response = accountApi.transferFunds(Config.VALID_ACCOUNT_ID, account, amount);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body(containsString("Successfully transferred $" + amount + " from account #" + Config.VALID_ACCOUNT_ID + " to account #" +account));
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }

    @Test
    public void shouldTransferFundsWithNonExistsAccount() {
        int amount = 100;
        int account = 13456;
        test.get().info("Sending POST transfer of $" + amount + " from account " + Config.VALID_ACCOUNT_ID + " to non-existing account " + account + " (/transfer)");
        Response response = accountApi.transferFunds(Config.VALID_ACCOUNT_ID, account, amount);

        test.get().info("Validating response status code is 400");
        response.then()
                .statusCode(400)
                .body(containsString("Could not find account number "+ Config.VALID_ACCOUNT_ID + " and/or " +account));
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }

}
