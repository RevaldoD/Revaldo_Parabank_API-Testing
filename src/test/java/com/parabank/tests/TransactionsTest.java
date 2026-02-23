package com.parabank.tests;

import com.parabank.api.TransactionApi;
import com.parabank.base.BaseTest;
import com.parabank.config.Config;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class TransactionsTest extends BaseTest {

    private TransactionApi transactionApi;

    @BeforeClass
    public void setup() {
        transactionApi = new TransactionApi();
    }

    @Test
    public void shouldFindTransactionListsWithTransactionId() {
        int transactionID = 14476;
        test.get().info("Sending GET request for transaction ID: " + transactionID + " (/transactions/{transactionId})");
        Response response = transactionApi.getTransactionWithId(transactionID);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body("id", equalTo(transactionID))
                .body("accountId", notNullValue())
                .body("type", notNullValue())
                .body("date", notNullValue())
                .body("amount", notNullValue())
                .body("description", notNullValue());
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }

    @Test
    public void shouldFindTransactionListsWithExactDate() throws Exception {
        String onDate = "02-08-2026";

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        long onEpoch = sdf.parse(onDate).getTime();

        test.get().info("Sending GET request for transactions on date: " + onDate + " for account ID: " + Config.VALID_ACCOUNT_ID + " (/accounts/{accountId}/transactions/onDate/{onDate})");
        Response response = transactionApi.getTransactionWithExactDate(Config.VALID_ACCOUNT_ID,onDate);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body("id", everyItem(notNullValue()))
                .body("accountId", everyItem(equalTo(Config.VALID_ACCOUNT_ID)))
                .body("type", everyItem(notNullValue()))
                .body("date", everyItem(anyOf(equalTo(onEpoch))))
                .body("amount", everyItem(notNullValue()))
                .body("description", everyItem(notNullValue()));
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }

    @Test
    public void shouldFindTransactionListsWithRangeDate() throws Exception {
        String fromDate = "02-08-2026";
        String toDate = "02-08-2026";

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        long fromEpoch = sdf.parse(fromDate).getTime();
        long toEpoch = sdf.parse(toDate).getTime();

        test.get().info("Sending GET request for transactions from " + fromDate + " to " + toDate + " for account ID: " + Config.VALID_ACCOUNT_ID + " (/accounts/{accountId}/transactions/fromDate/{fromDate}/toDate/{toDate})");
        Response response = transactionApi.getTransactionWithRangeDate(Config.VALID_ACCOUNT_ID,fromDate,toDate);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body("id", everyItem(notNullValue()))
                .body("accountId", everyItem(equalTo(Config.VALID_ACCOUNT_ID)))
                .body("type", everyItem(notNullValue()))
                .body("date", everyItem(anyOf(equalTo(fromEpoch), equalTo(toEpoch))))
                .body("amount", everyItem(notNullValue()))
                .body("description", everyItem(notNullValue()));
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }


    @Test
    public void shouldFindTransactionListsWithAmount() throws Exception {
        int amount = 1000;

        test.get().info("Sending GET request for transactions with amount: $" + amount + " for account ID: " + Config.VALID_ACCOUNT_ID + " (/accounts/{accountId}/transactions/amount/{amount})");
        Response response = transactionApi.getTransactionWithAmount(Config.VALID_ACCOUNT_ID,amount);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body("id", everyItem(notNullValue()))
                .body("accountId", everyItem(equalTo(Config.VALID_ACCOUNT_ID)))
                .body("type", everyItem(notNullValue()))
                .body("date", everyItem(notNullValue()))
                .body("amount", everyItem(equalTo((float) amount)))
                .body("description", everyItem(notNullValue()));
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }
//    negative test case
    @Test
    public void shouldFindTransactionListsWithInvalidId() {
        int transactionID = 14477;
        test.get().info("Sending GET request for invalid transaction ID: " + transactionID + " (/transactions/{transactionId})");
        Response response = transactionApi.getTransactionWithId(transactionID);

        test.get().info("Validating response status code is 400");
        response.then()
                .statusCode(400)
                .body(containsString("Could not find transaction #"+ transactionID));
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }
}
