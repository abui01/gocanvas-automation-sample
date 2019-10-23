package com.canvas.qa.test.BETAQRT;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.SearchUsersPage;
import com.canvas.qa.pages.profile.DisableUserPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.profile.UsersPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

@Test
public class DisableUserTest extends BrowserLaunchTest

{

	@Parameters({ "step", "testdescription" })
	public void clickDisable(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException, AWTException {
		CanvasLoginTest loginTest = new CanvasLoginTest();
		DisableUserPage disableUser = new DisableUserPage(driver);
		int i = 3;
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		boolean userDisabled = disableUser.disableUserClick(parameters.get("firstname"), parameters.get("lastname"),
				parameters.get("user"));
		if (userDisabled) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": User Disabled.");
		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ":  User Disabled.");
		}

		org.testng.Assert.assertTrue(userDisabled, "User Disabled.");

		disableUser.logout();
		/*
		 * try {
		 */
		Thread.sleep(5000);
		loginTest.verifyInValidLogin(step, testdescription, parameters.get("disableduseremail"),
				parameters.get("disableduserpassword"), testContext);
		Thread.sleep(3000);
		/*
		 * } catch (Exception e) { e.printStackTrace(); LOGGER.
		 * info("DisableUser.clickDisableCancel : Login has encountered a problem."
		 * ); }
		 */
		String toast = disableUser.toast();
		if (toast.contains("Your account has been disabled, please contact your company admin.")) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Account Disabled.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Account Disabled.");
		}

		org.testng.Assert.assertTrue(
				toast.contains("Your account has been disabled, please contact your company admin."),
				"Account Disabled.");

	}

	@Parameters({ "step", "testdescription" })
	public void clickDisableCancel(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException, AWTException {
		Map<String, String> parameters1 = FileReaderUtil.getTestParameters(testContext);
		LoginPage login = new LoginPage(driver);
		login.gotoLogin();
		login.typeusername(parameters1.get("admin"));
		login.typepassword(parameters1.get("adminpwd"));
		login.Clickonloginbutton();
		DashboardPage dashboardPage = new DashboardPage(driver);
		SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
		searchUsersPage.enterEmail(parameters1.get("user"));
		searchUsersPage.clickSearch();
		searchUsersPage.clickOnMore("taukeer.ahmad+1@3pillarglob...");
		if (searchUsersPage.isEnableDisplayed()) {
			UsersPage usersPage = searchUsersPage.clickEnable();
			usersPage.purchaseClick();
		}

		searchUsersPage = dashboardPage.clickSearchUsers();
		searchUsersPage.enterEmail(parameters1.get("disableduseremail"));
		searchUsersPage.clickSearch();
		searchUsersPage.clickOnMore("taukeer.ahmad+2@3pillarglob...");
		if (searchUsersPage.isEnableDisplayed()) {
			UsersPage usersPage = searchUsersPage.clickEnable();
			usersPage.purchaseClick();
		}

		searchUsersPage.logout();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 3;
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		/*
		 * try {
		 */
		loginTest.verifyValidLogin1(step, testdescription, parameters.get("user"), parameters.get("password"),
				testContext);
		/*
		 * } catch (Exception e) { e.printStackTrace(); LOGGER.
		 * info("DisableUser.clickDisableCancel : Login has encountered a problem."
		 * ); }
		 */
		DisableUserPage disableUser = new DisableUserPage(driver);
		/*
		 * try {
		 */
		disableUser.clickAccountUserBtn();
		disableUser.disableCancelUserClick(parameters.get("firstname"), parameters.get("lastname"),
				parameters.get("user"));

		ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
				"Step " + step + "." + (++i) + ": Search User.");
		/*
		 * } catch (Exception e) { ReportManager.lognew(testContext.getName(),
		 * testdescription, LogStatus.PASS, "Step " + step + "." + (++i) +
		 * ": Search User."); e.printStackTrace(); }
		 */

	}

	@Parameters({ "step", "testdescription" })
	public void enableCompanyUser(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		CanvasLoginTest loginTest = new CanvasLoginTest();
		DisableUserPage disableUser = new DisableUserPage(driver);
		int i = 3;
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		/*
		 * try {
		 */
		Thread.sleep(2000);
		loginTest.verifyValidLoginnew(step, testdescription, parameters.get("user"), parameters.get("password"),
				testContext);
		/*
		 * } catch (Exception e) { e.printStackTrace(); LOGGER.
		 * info("DisableUser.clickDisableCancel : Login has encountered a problem."
		 * ); }
		 */
		disableUser.clickAccountUserBtn();

		String toastMessage = disableUser.enableCompanyUser(parameters.get("firstname"), parameters.get("lastname"),
				parameters.get("user"));
		if (toastMessage.equalsIgnoreCase(
				"The account for Company User is now enabled. Company User can now access the Canvas system.")) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Search User.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Search User.");
		}

		org.testng.Assert.assertTrue(
				toastMessage.equalsIgnoreCase(
						"The account for Company User is now enabled. Company User can now access the Canvas system."),
				"Search User.");

		/*
		 * boolean reenabled =
		 * disableUser.reabledUserAppears(parameters.get("firstname"),
		 * parameters.get("lastname"), parameters.get("useremail")); if
		 * (reenabled) { ReportManager.lognew(testContext.getName(),
		 * testdescription, LogStatus.PASS, "Step " + step + "." + (++i) +
		 * ": User Re-enabled."); } else { LOGGER.
		 * info("UploadDispatchFile.secondDepartment : Upload CSV File Contains Error."
		 * ); ReportManager.lognew(testContext.getName(), testdescription,
		 * LogStatus.FAIL, "Step " + step + "." + (++i) +
		 * ":  User Not Re-enabled."); }
		 * 
		 * org.testng.Assert.assertTrue(reenabled, "User Not Re-enabled.");
		 */
		disableUser.logout();

	}

	@Parameters({ "step", "testdescription" })
	public void verifyUserEnabled(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		CanvasLoginTest loginTest = new CanvasLoginTest();
		DisableUserPage disableUser = new DisableUserPage(driver);
		int i = 3;
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		/*
		 * try {
		 */
		loginTest.verifyValidLoginnew(step, testdescription, parameters.get("disableduseremail"),
				parameters.get("password"), testContext);
		ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
				"Step " + step + "." + (++i) + ": Re-login.");
		/*
		 * } catch (Exception e) { e.printStackTrace(); LOGGER.
		 * info("DisableUser.clickDisableCancel : Login has encountered a problem."
		 * ); ReportManager.lognew(testContext.getName(), testdescription,
		 * LogStatus.FAIL, "Step " + step + "." + (++i) + ": Re-login."); }
		 */
		disableUser.logout();
	}
}
