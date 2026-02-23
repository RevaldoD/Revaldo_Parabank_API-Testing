package com.parabank.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.parabank.config.Config;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.testng.ITestResult;
import org.testng.annotations.*;

// Centralizes Rest Assured configuration. All test classes extend this.
public abstract class BaseTest {

    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void globalSetup() {
        RestAssured.baseURI = Config.BASE_URI;
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("reports/APItestsreport.html");
        spark.config().setDocumentTitle("API test Report for user module");
        spark.config().setReportName("API Test report user module");
        extent.attachReporter(spark);
        extent.setSystemInfo("Environment", "Demo");
        extent.setSystemInfo("Tester name", "Revaldo");
    }

    @BeforeMethod
    public void beforeMethod(java.lang.reflect.Method method) {
        test.set(extent.createTest(method.getName()));
    } //test report method to log every get.info()

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE){
            test.get().fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP){
            test.get().skip("test skipped" + result.getThrowable());
        } else {
            test.get().pass("Test case Passed");
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        extent.flush();
    }
}

