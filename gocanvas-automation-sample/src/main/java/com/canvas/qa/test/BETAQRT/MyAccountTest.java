package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.profile.MyAccountPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author taukeer.ahmad
 *
 */
@Test
public class MyAccountTest extends BrowserLaunchTest

{

	@Parameters({ "step" })
	public void adminAccountAccess(String step, ITestContext testContext) throws InterruptedException, IOException {

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 3;
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		loginTest.verifyValidLogin1(step, parameters.get("admin_username"), parameters.get("admin_password"),
				testContext);

		MyAccountPage myAccount = new MyAccountPage(driver);

		boolean isDisplayed = myAccount.accountIsDisplayed();
		if (isDisplayed)
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Account Dispalyed.");
		else

			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Account Dispalyed.");
		org.testng.Assert.assertTrue(isDisplayed, "Account Dispalyed");
	}

	@Parameters({ "step" })
	public void adminManageUsersAccess(String step, ITestContext testContext) throws InterruptedException {

		int i = 0;

		MyAccountPage myAccount = new MyAccountPage(driver);

		myAccount.clickAccount();

		boolean manageUserIsDisplayed = myAccount.manageUserIsDisplayed();
		if (manageUserIsDisplayed)
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Manage User Dispalyed.");
		else
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Manage User Dispalyed.");

		org.testng.Assert.assertTrue(manageUserIsDisplayed, "Manage User Dispalyed.");

		myAccount.logout();

	}

	@Parameters({ "step" })
	public void loginNonAdmin(String step, ITestContext testContext) throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;
		Thread.sleep(5000);
		CanvasLoginTest loginTest = new CanvasLoginTest();

		loginTest.verifyValidLoginnew(step, parameters.get("designer_username"), parameters.get("designer_password"),
				testContext);

		i += 3;

		MyAccountPage myAccount = new MyAccountPage(driver);

		boolean isDisplayed = myAccount.accountIsDisplayed();
		if (isDisplayed) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Account Dispalyed.");
		} else {

			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Account Dispalyed.");
		}
		org.testng.Assert.assertFalse(isDisplayed, "Account Displayed");
		myAccount.logout();

		loginTest.verifyValidLoginnew(step, parameters.get("reporter_username"), parameters.get("reporter_password"),
				testContext);

		i += 3;

		myAccount.profileBtnClick();

		isDisplayed = myAccount.accountIsDisplayed();
		if (isDisplayed) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Account Dispalyed.");
		} else {

			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Account Dispalyed.");
		}
		org.testng.Assert.assertFalse(isDisplayed, "Account Displayed");
		myAccount.logout();

		loginTest.verifyValidLoginnew(step, parameters.get("user_username"), parameters.get("user_password"),
				testContext);

		i += 3;

		isDisplayed = myAccount.accountIsDisplayed();
		if (isDisplayed) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Account Dispalyed.");
		} else {

			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Account Dispalyed.");
		}
		org.testng.Assert.assertFalse(isDisplayed, "Account Displayed");
	}

}
