package com.parabank.tests;

import com.parabank.api.AccountApi;
import com.parabank.base.BaseTest;
import com.parabank.config.Config;
import com.parabank.request.Address;
import com.parabank.request.PayeeModel;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class BillPaymentTest extends BaseTest {

    private AccountApi accountApi;

    @BeforeClass
    public void setup() {
        accountApi = new AccountApi();
    }

    @Test
    public void shouldBillPayment() {
        int amount = 100;
        Address address = new Address("123 Main St", "New York", "NY", "10001");
        PayeeModel payee = new PayeeModel("John Doe", address, "123-456-7890", 13345);

        test.get().info("Sending POST bill payment of $" + amount + " from account ID: " + Config.VALID_ACCOUNT_ID + " (/billpay)");
        Response response = accountApi.billPayment(Config.VALID_ACCOUNT_ID, amount, payee);

        test.get().info("Validating response status code is 200");
        response.then()
                .statusCode(200)
                .body("payeeName", equalTo(payee.getName()))
                .body("amount", equalTo(amount))
                .body("accountId", equalTo(Config.VALID_ACCOUNT_ID));
        test.get().info("Response body: " + response.getBody().asPrettyString());
    }
}
