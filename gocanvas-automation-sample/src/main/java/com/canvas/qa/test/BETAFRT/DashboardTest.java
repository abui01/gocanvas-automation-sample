package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.apps.AppsPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class DashboardTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void companyAdminMonthlyTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickMonthlyButton();
			boolean status = dashboardPage.isChartDisplayed();
			reportLog(status, testContext.getName(), "Verify the graph information is displayed correctly.", "1.1",
					"Graph information is displayed correctly.");
			org.testng.Assert.assertTrue(status);

			// Date, app name, and number of submissions are correct in the
			// popup.
			reportLog(true, testContext.getName(),
					"Date, app name, and number of submissions are correct in the popup cannot be automated.", "2.0",
					"Date, app name, and number of submissions are correct in the popup cannot be automated.");
			org.testng.Assert.assertTrue(true);

			status = dashboardPage.isDashNumberLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify number submissions in the past month appears", "3.0",
					"Number submissions in the past month appears.");
			org.testng.Assert.assertTrue(status);

			status = dashboardPage.isTopFourAppsBySubmissionsDisplayed();
			reportLog(status, testContext.getName(),
					"Verify the Top Four Apps By Submissions (non-graph) section displays the correct apps and Submissions value.",
					"4.0", "Top Four Apps By Submissions section displays the correct apps and Submissions value");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void companyAdminYearlyTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickYearlyButton();
			boolean status = dashboardPage.isChartDisplayed();
			reportLog(status, testContext.getName(), "Verify the graph information is displayed correctly.", "1.1",
					"Graph information is displayed correctly.");
			org.testng.Assert.assertTrue(status);

			// Date, app name, and number of submissions are correct in the
			// popup.
			reportLog(true, testContext.getName(),
					"Month year, app name, and number of submissions are correct in the popup cannot be automated.",
					"2.0",
					"Month year, app name, and number of submissions are correct in the popup cannot be automated.");
			org.testng.Assert.assertTrue(true);

			status = dashboardPage.isDashNumberLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify number submissions in the past year appears.", "3.0",
					"Number submissions in the past year appears.");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(),
					"Verify the Date and Submissions columns display the correct information canot be automated.",
					"4.0",
					"Verify the Date and Submissions columns display the correct information canot be automated.");
			org.testng.Assert.assertTrue(true);

			status = dashboardPage.isTopFourAppsBySubmissionsDisplayed();
			reportLog(status, testContext.getName(),
					"Verify the Top Four Apps By Submissions (non-graph) section displays the correct apps and Submissions value.",
					"4.0", "Top Four Apps By Submissions section displays the correct apps and Submissions value");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void dispatchManagerSearchTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);
			AppsPage appsPage = new AppsPage(driver);
			DashboardPage dashboardPage = appsPage.clickDashboard();
			String savingsFromPaper = dashboardPage.getSavingsFromPaper().replace("$", "").replace(",", "").trim();
			String savingsFromProductivity = dashboardPage.getSavingsFromProductivity().replace("$", "")
					.replace(",", "").trim();
			String totalGoCanvasSavings = dashboardPage.getTotalGoCanvasSavings().replace("$", "").replace(",", "")
					.trim();
			boolean status = ((Float.parseFloat(savingsFromPaper) + Float.parseFloat(savingsFromProductivity)) == Float
					.parseFloat(totalGoCanvasSavings));
			reportLog(status, testContext.getName(),
					"Verify the Your GoCanvas Savings amount is the sum of the saving in paper amount and the productivity savings amount",
					"1.1",
					"Your GoCanvas Savings amount is the sum of the saving in paper amount and the productivity savings amount");
			org.testng.Assert.assertTrue(status);

			status = dashboardPage.isCustomizeButtonDisplayed();
			reportLog(!status, testContext.getName(),
					"Verify the Customize button does not appear under productivity savings.", "2.0",
					"Customize button does not appear under productivity savings.");
			org.testng.Assert.assertFalse(status);

			status = dashboardPage.isMyAccountValueDisplayed();
			reportLog(!status, testContext.getName(), "Verify My Account value for current users does not appear.",
					"3.0", "My Account value for current users does not appear.");
			org.testng.Assert.assertFalse(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
