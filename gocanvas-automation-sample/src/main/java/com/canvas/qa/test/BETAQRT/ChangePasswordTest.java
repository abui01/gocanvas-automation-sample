package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.SearchUsersPage;
import com.canvas.qa.pages.profile.ChangePasswordPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author taukeer.ahmad
 *
 */
@Test
public class ChangePasswordTest extends BrowserLaunchTest

{
	@Parameters({ "step", "testdescription" })
	public void changePassword(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		LoginPage login = new LoginPage(driver);
		login.gotoLogin();
		login.typeusername(parameters.get("admin"));
		login.typepassword(parameters.get("adminpwd"));
		login.Clickonloginbutton();
		DashboardPage dashboardPage = new DashboardPage(driver);
		SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
		searchUsersPage.enterEmail(parameters.get("username"));
		searchUsersPage.clickSearch();
		dashboardPage = searchUsersPage.clickOnManage(parameters.get("username"));
		dashboardPage.clickProfile();
		dashboardPage.clickChangePassword();
		dashboardPage.changeUserPassword(parameters.get("password"));
		searchUsersPage = dashboardPage.clickResumeYourSession();
		searchUsersPage.logout();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;

		try {
			loginTest.verifyValidLogin1(step, testdescription, null, null, testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}

		ChangePasswordPage changePasswordLink = new ChangePasswordPage(driver);
		changePasswordLink.myProfileButtonClick();

		String oldPass = changePasswordLink.verifyOldPassLabel();
		if (oldPass != null && !oldPass.isEmpty() && oldPass.toLowerCase().contains("old password *")) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Old Password Label.");
		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Old Password Label.");
		}
		org.testng.Assert.assertTrue(
				oldPass != null && !oldPass.isEmpty() && oldPass.toLowerCase().contains("old password *"),
				"old password label");
		String newPass = changePasswordLink.verifyNewPassLabel();
		if (newPass != null && !newPass.isEmpty() && newPass.toLowerCase().contains("new password *")) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": New Password Label.");
		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": New Password Label.");
		}
		org.testng.Assert.assertTrue(
				newPass != null && !newPass.isEmpty() && newPass.toLowerCase().contains("new password *"),
				"new password label");
		String confirmPass = changePasswordLink.verifyConfirmPassLabel();
		if (confirmPass != null && !confirmPass.isEmpty()
				&& confirmPass.toLowerCase().contains("retype new password *")) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Confirm Password Label.");
		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Confirm Password Label.");
		}
		org.testng.Assert.assertTrue(confirmPass != null && !confirmPass.isEmpty()
				&& confirmPass.toLowerCase().contains("retype new password *"), "confirm password label");
	}

	@Parameters({ "step", "testdescription" })
	public void changePasswordAndVerify(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {

		ChangePasswordPage changePasswordLink = new ChangePasswordPage(driver);

		int i = 0;
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String oldPassword = parameters.get("password");
		String newPassword = parameters.get("newpassword");
		String confirmPassword = parameters.get("newpassword");
		try {
			changePasswordLink.enterOldPassword(oldPassword);
			changePasswordLink.userPassword(newPassword);
			changePasswordLink.confirmPassword(confirmPassword);
		} catch (IOException e) {
			e.printStackTrace();

		}
		changePasswordLink.changePasswordClick();
		String message = changePasswordLink.verifySuccessfulMsg();
		if (message != null && !message.isEmpty() && message.contains("Your password has been updated.")) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Password Udpated.");
		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Password Updated. " + message);
		}
		org.testng.Assert.assertTrue(
				message != null && !message.isEmpty() && message.contains("Your password has been updated."),
				"password updated");
		changePasswordLink.logout();
	}

	@Parameters({ "step", "testdescription" })
	public void postCondition(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		try {
			ChangePasswordPage changePasswordLink = new ChangePasswordPage(driver);

			int i = 0;

			String oldPassword = parameters.get("password");
			String newPassword = "";
			String confirmPassword = "";
			try {
				changePasswordLink.myProfileButtonClick();
				changePasswordLink.enterOldPassword(oldPassword);
				changePasswordLink.userPassword(newPassword);
				changePasswordLink.confirmPassword(confirmPassword);
			} catch (IOException e) {
				e.printStackTrace();
			}
			changePasswordLink.changePasswordClick();

			oldPassword = parameters.get("newpassword");
			newPassword = parameters.get("password");
			confirmPassword = parameters.get("password");
			try {
				changePasswordLink.enterOldPassword(oldPassword);
				changePasswordLink.userPassword(newPassword);
				changePasswordLink.confirmPassword(confirmPassword);
			} catch (IOException e) {
				e.printStackTrace();
			}
			changePasswordLink.changePasswordClick();
			changePasswordLink.logout();

		} catch (Exception e) {
			e.printStackTrace();
		}

		ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
				"Step " + step + ": Post Conditions");
	}

	@Parameters({ "step", "testdescription" })
	public void reverifyPasswordChange(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		Thread.sleep(5000);
		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 3;

		ChangePasswordPage changePasswordLink = new ChangePasswordPage(driver);
		loginTest.verifyValidLoginnew(step, testdescription, parameters.get("username"), parameters.get("newpassword"),
				testContext);

		boolean success = changePasswordLink.verifyLogin();
		if (success)
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + (step) + "." + (++i) + ": Verify Login.");
		else
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + (step) + "." + (++i) + ": Verify Login.");

		org.testng.Assert.assertTrue(success, "verify login");
	}

	@Parameters({ "step", "testdescription" })
	public void verifyBlankPassword(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {

		ChangePasswordPage changePasswordLink = new ChangePasswordPage(driver);

		int i = 0;
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String oldPassword = parameters.get("password");
		String newPassword = "";
		String confirmPassword = "";
		try {
			changePasswordLink.myProfileButtonClick();
			changePasswordLink.enterOldPassword(oldPassword);
			changePasswordLink.userPassword(newPassword);
			changePasswordLink.confirmPassword(confirmPassword);
		} catch (IOException e) {
			e.printStackTrace();
		}
		changePasswordLink.changePasswordClick();
		/*
		 * String message = changePasswordLink.verifyInvalidMsg(); if (message
		 * != null && !message.isEmpty() &&
		 * message.contains("The password is invalid.")) {
		 * ReportManager.lognew(testContext.getName(), testdescription,
		 * LogStatus.PASS, "Step " + step + "." + (++i) +
		 * ": Blank Password Udpated."); } else {
		 * 
		 * ReportManager.lognew(testContext.getName(), testdescription,
		 * LogStatus.FAIL, "Step " + step + "." + (++i) +
		 * ": Blank Password Udpated. " + message); }
		 * org.testng.Assert.assertTrue(message != null && !message.isEmpty() &&
		 * message.contains("The password is invalid."),"blank password updated"
		 * );
		 */
	}
}
