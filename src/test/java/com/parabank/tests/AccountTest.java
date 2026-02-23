package com.parabank.tests;

import com.parabank.api.AccountApi;
import com.parabank.base.BaseTest;
import com.parabank.config.Config;
import com.parabank.api.AccountApi.AccountType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

public class AccountTest extends BaseTest {

    private AccountApi accountApi;

    @BeforeClass
    public void setup() {
        accountApi = new AccountApi();
    }


    @Test
    public void shouldRetrieveAccountById() {
        test.get().info("Sending GET request for account ID: " + Config.VALID_ACCOUNT_ID + " (/accounts/{accountId})");
        Response response = accountApi.getAccount(Config.VALID_ACCOUNT_ID);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body("id", equalTo(Config.VALID_ACCOUNT_ID))
                .body("customerId", notNullValue())
                .body("type", isOneOf(AccountType.CHECKING.name(), AccountType.SAVINGS.name()))
                .body("balance", notNullValue());
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }

    @Test
    public void shouldOpenSavingsAccount() {
        test.get().info("Sending POST request to create SAVINGS account for customer ID: " + Config.CUSTOMER_ID + " (/createAccount)");
        Response response = accountApi.createAccount(
                Config.CUSTOMER_ID, AccountType.SAVINGS, Config.VALID_ACCOUNT_ID);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("type", equalTo(AccountType.SAVINGS.name()));
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }

    @Test
    public void shouldOpenCheckingAccount() {
        test.get().info("Sending POST request to create CHECKING account for customer ID: " + Config.CUSTOMER_ID + " (/createAccount)");
        Response response = accountApi.createAccount(
                Config.CUSTOMER_ID, AccountType.CHECKING, Config.VALID_ACCOUNT_ID);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("type", equalTo(AccountType.CHECKING.name()));
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }


    @Test
    public void shouldRetrieveCustomerAccounts() {
        test.get().info("Sending GET request for all accounts of customer ID: " + Config.CUSTOMER_ID + " (/customers/{customerId}/accounts)");
        Response response = accountApi.getCustomer(Config.CUSTOMER_ID);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("customerId", everyItem(equalTo(Config.CUSTOMER_ID)))
                .body("id", everyItem(notNullValue()))
                .body("type", everyItem(notNullValue()));
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }

    @Test
    public void shouldFailToOpenAccountWithNonExistingAccountId() {
        test.get().info("Sending POST request to create account with non-existing account ID: " + Config.NON_EXISTING_ACCOUNT_ID + " (/createAccount)");
        Response response = accountApi.createAccount(
                Config.CUSTOMER_ID, AccountType.SAVINGS, Config.NON_EXISTING_ACCOUNT_ID);

        test.get().info("Validating response status code is NOT 200");
        response.then()
                .statusCode(not(equalTo(200)))
                .body(containsString("Could not create new account for customer " + Config.CUSTOMER_ID + " from account " + Config.NON_EXISTING_ACCOUNT_ID));
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }

    @Test
    public void shouldUpdateCustomerProfile() {
        String firstName = "UpdatedFirst";
        String lastName = "UpdatedLast";
        String street = "456 Oak Ave";
        String city = "Los Angeles";
        String state = "CA";
        String zip = "90001";
        String phoneNumber = "987-654-3210";
        String ssn = "987-65-4321";
        String username = "updateduser";
        String password = "updatedpass123";

        test.get().info("Sending POST request to update profile for customer ID: " + Config.CUSTOMER_ID + " (/customers/update/{customerId})");
        Response response = accountApi.updateCustomerInfo(Config.CUSTOMER_ID, firstName, lastName, street, city, state, zip, phoneNumber, ssn, username, password);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body(containsString("Successfully updated customer profile"));
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }
}
