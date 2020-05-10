import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


public class TestMethods {

    protected SoftAssert softAssert;
    private int passed = 0;
    private int failed = 0;
    private int skipped = 0;
    private ExtentTest extentTest;
    private ExtentHtmlReporter htmlReporter;
    private ExtentReports extentReports;

    private long suiteStartTime = 0;
    private long suiteEndTime = 0;
    private String automationFileName;


    @BeforeSuite(alwaysRun = true)
    public void setExtent() {
        suiteStartTime = System.currentTimeMillis();
        String fileTime = new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date());
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/test/resources/reports/automation_report_" + fileTime + ".html");
        automationFileName = "automation_report_" + fileTime + ".html";
        htmlReporter.config().setDocumentTitle("ALM API Automation");
        htmlReporter.config().setReportName("ALM API Automation Report");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
        extentReports.setSystemInfo("QA", "Env");

    }

    int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    @Test
    public void test1() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("a", "a");
        softAssert.assertAll();
        System.out.println("Test 01");
    }

    @Test
    public void test2() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("a", "a");

        softAssert.assertAll();
        System.out.println("Test 02");
    }

    @Test
    public void test3() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("a", "a");
        softAssert.assertAll();
        System.out.println("Test 03");
    }


    @Test
    public void test4() {
        SoftAssert softAssert = new SoftAssert();

        int x = randomWithRange(1, 2);
        System.out.println("test--------------" + x);

        softAssert.assertEquals(x, 1);
        softAssert.assertAll();
        System.out.println("Test 04");
    }

    @Test
    public void test5() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("a", "a");
        softAssert.assertAll();
        System.out.println("Test 05");

    }

    private void printAllResults(Collection<ITestResult> results) {
        for (ITestResult result : results) {

            if (result.getStatus() == ITestResult.FAILURE) {
                extentTest = extentReports.createTest(result.getName(), result.getMethod().getDescription());
                extentTest.log(Status.FAIL, "Test Case FAILED is " + result.getMethod().getDescription());
                extentTest.fail("Test Failure Reason is     " + result.getThrowable());
                failed++;


            } else if (result.getStatus() == ITestResult.SKIP) {
                extentTest = extentReports.createTest(result.getName(), result.getMethod().getDescription());
                extentTest.log(Status.SKIP, "Test Case SKIPPED is " + result.getMethod().getDescription());
                extentTest.info("Test Method  is       " + result.getMethod().getMethodName());
                skipped++;

            } else if (result.getStatus() == ITestResult.SUCCESS) {

                extentTest = extentReports.createTest(result.getName(), result.getMethod().getDescription());
                extentTest.log(Status.PASS, "Test Case Passed is " + result.getMethod().getDescription());
                extentTest.info("Test Method  is       " + result.getMethod().getMethodName());
                passed++;

            }
        }
    }

    private void printAllResults1(ITestContext context) {
        printAllResults(context.getPassedTests().getAllResults());
        printAllResults(context.getFailedTests().getAllResults());
        printAllResults(context.getSkippedTests().getAllResults());
    }


    private void printSuiteResults(ISuite suite) {
        Collection<ISuiteResult> suiteResults = suite.getResults().values();
        for (ISuiteResult suiteResult : suiteResults) {
            printAllResults1(suiteResult.getTestContext());
        }
    }

    @AfterSuite
    public void afterSuite(ITestContext context) throws IOException {
        suiteEndTime = System.currentTimeMillis();


        /* Generating the report */
        printSuiteResults(context.getSuite());
        /* Writing test results to the mail */

        /* Generating the Extent test report */
        extentReports.flush();

        //Pie Chart Generation For the Mail
        //   int passed = Integer.parseInt(PropertyFileReader.propertyReader("src/test/resources/utility/email/testResults.properties").getProperty("passed"));
        //   int failed = Integer.parseInt(PropertyFileReader.propertyReader("src/test/resources/utility/email/testResults.properties").getProperty("failed"));
        //  int skipped = Integer.parseInt(PropertyFileReader.propertyReader("src/test/resources/utility/email/testResults.properties").getProperty("skipped"));

        //   PieChartGeneration.createPieChart(passed, failed, skipped);

        //Sending the Mail
        //  MailUtil.sendMail();
    }


}

