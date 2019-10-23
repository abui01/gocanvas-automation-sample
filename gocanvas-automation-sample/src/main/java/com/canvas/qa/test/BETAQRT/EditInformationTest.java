package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.SearchUsersPage;
import com.canvas.qa.pages.profile.EditInformationPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author taukeer.ahmad
 *
 */
@Test
public class EditInformationTest extends BrowserLaunchTest {
	@Parameters({ "step", "testdescription" })
	public void loginChangeCountry(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;
		/*
		 * try {
		 */
		loginTest.verifyValidLogin1(step, testdescription, parameters.get("user_username"),
				parameters.get("user_password"), testContext);
		i += 3;
		EditInformationPage editInformation = new EditInformationPage(driver);
		editInformation.profileBtnClick();
		editInformation.editBtnClick();

		/*
		 * try {
		 */
		editInformation.updateUserCountry(parameters.get("country"));
		editInformation.commitClick();

		editInformation.logout();
		loginTest.verifyValidLogin1(step, testdescription, parameters.get("user_username"),
				parameters.get("user_password"), testContext);

		editInformation.profileBtnClick();
		editInformation.editBtnClick();

		boolean verified = editInformation.verifyCountryUpdated(parameters.get("country"));
		if (verified) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + (step) + "." + (++i) + ": Country Change.");

			editInformation.updateUserCountry(parameters.get("originalcountry"));
			editInformation.commitClick();
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + (step) + "." + (++i) + ": Country Change.");
		}

		org.testng.Assert.assertTrue(verified, "Country Updation has encountered a problem");

		/*
		 * } catch (Exception e) { e.printStackTrace();
		 * ReportManager.lognew(testContext.getName(), testdescription,
		 * LogStatus.FAIL, "Step " + (step) + "." + (++i) +
		 * ": Country Change."); LOGGER.
		 * info("EditInformation.loginChangeCountry : Country Updation has encountered a problem."
		 * ); } } catch (Exception e) { e.printStackTrace(); LOGGER.
		 * info("EditInformation.loginChangeCountry : loginChangeCountry method has encountered a problem."
		 * ); }
		 */
	}

	@Parameters({ "step", "testdescription" })
	public void loginChangeEmail(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters1 = FileReaderUtil.getTestParameters(testContext);
		DashboardPage dashboardPage = new DashboardPage(driver);
		LoginPage login = new LoginPage(driver);
		login.gotoLogin();
		login.typeusername(parameters1.get("admin"));
		login.typepassword(parameters1.get("adminpwd"));
		login.Clickonloginbutton();

		SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
		searchUsersPage.enterEmail(parameters1.get("admin_username"));
		searchUsersPage.clickSearch();
		dashboardPage = searchUsersPage.clickOnManage(parameters1.get("admin_username"));
		dashboardPage.clickProfile();
		dashboardPage.clickChangePassword();
		dashboardPage.changeUserPassword(parameters1.get("admin_password"));
		searchUsersPage = dashboardPage.clickResumeYourSession();
		searchUsersPage.logout();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 3;
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String email = parameters.get("newemail");
		String emailold = parameters.get("admin_username");

		/*
		 * try {
		 */
		loginTest.verifyValidLogin1(step, testdescription, parameters.get("admin_username"),
				parameters.get("admin_password"), testContext);
		/*
		 * } catch (Exception e) { e.printStackTrace(); LOGGER.
		 * info("EditInformation.loginChangeEmail : Login has encountered a problem."
		 * ); }
		 */
		EditInformationPage editInformation = new EditInformationPage(driver);
		editInformation.profileBtnClick();
		editInformation.editBtnClick();

		/*
		 * try {
		 */
		editInformation.updateEmail(email);
		editInformation.commitClick();

		editInformation.logout();
		loginTest.verifyValidLogin1(step, testdescription, parameters.get("newemail"), parameters.get("admin_password"),
				testContext);

		editInformation.profileBtnClick();
		boolean verified = editInformation.verifyEmailUpdated(email);
		if (verified) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Email Updated.");

			editInformation.editBtnClick();
			editInformation.updateEmail(emailold);
			editInformation.commitClick();
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Email Not Updated.");
		}

		org.testng.Assert.assertTrue(verified, "Email Not Updated");

		/*
		 * } catch (Exception e) { e.printStackTrace(); LOGGER.
		 * info("EditInformation.loginChangeEmail : Email Updation has encountered a problem."
		 * ); }
		 */
	}

	@Parameters({ "step", "testdescription" })
	public void loginChangeFirstName(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;
		EditInformationPage editInformation = new EditInformationPage(driver);

		/*
		 * try {
		 */
		editInformation.editBtnClick();
		editInformation.updateFirstName(parameters.get("firstname"));
		editInformation.commitClick();

		editInformation.logout();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		/*
		 * try {
		 */
		loginTest.verifyValidLogin1(step, testdescription, parameters.get("user_username"),
				parameters.get("user_password"), testContext);
		/*
		 * } catch (Exception e) { e.printStackTrace(); LOGGER.
		 * info("EditInformation.loginChangeFirstName : loginChangeFirstName method has encountered a problem."
		 * ); }
		 */
		editInformation.profileBtnClick();

		boolean verified = editInformation.verifyFirstNameUpdated(parameters.get("firstname"));
		if (verified) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + (step) + "." + (++i) + ": First Name Change.");

			editInformation.editBtnClick();
			editInformation.updateFirstName(parameters.get("originalname"));
			editInformation.commitClick();
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + (step) + "." + (++i) + ": First Name Change.");
		}

		org.testng.Assert.assertTrue(verified, "First Name Change");

		/*
		 * } catch (Exception e) { e.printStackTrace();
		 * ReportManager.lognew(testContext.getName(), testdescription,
		 * LogStatus.FAIL, "Step " + (step) + "." + (++i) +
		 * ": First Name Change."); LOGGER.
		 * info("EditInformation.loginChangeCountry : First Name Updation has encountered a problem."
		 * ); }
		 */
	}

	@Parameters({ "step", "testdescription" })
	public void loginChangeLocation(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		CanvasLoginTest loginTest = new CanvasLoginTest();
		EditInformationPage editInformation = new EditInformationPage(driver);
		editInformation.profileBtnClick();
		editInformation.editBtnClick();

		/*
		 * try {
		 */
		editInformation.updateDefaultLocation((parameters.get("defaultlocation")));
		editInformation.commitClick();

		editInformation.logout();
		/*
		 * try {
		 */
		loginTest.verifyValidLogin1(step, testdescription, parameters.get("admin_username"),
				parameters.get("admin_password"), testContext);
		/*
		 * } catch (Exception e) { e.printStackTrace(); LOGGER.
		 * info("EditInformation.loginChangeLocation : loginChangeLocation method has encountered a problem."
		 * ); }
		 */

		boolean verified = editInformation.verifyDefaultLocationUpdated(parameters.get("defaultlocation"));
		if (verified) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + (step) + "." + (++i) + ": Default Location Change.");

			editInformation.profileBtnClick();
			editInformation.editBtnClick();
			editInformation.updateDefaultLocation((parameters.get("originallocation")));
			editInformation.commitClick();
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + (step) + "." + (++i) + ": Default Location Change.");
		}
		org.testng.Assert.assertTrue(verified, "Default Location Change.");

		/*
		 * } catch (Exception e) { e.printStackTrace();
		 * ReportManager.lognew(testContext.getName(), testdescription,
		 * LogStatus.FAIL, "Step " + (step) + "." + (++i) +
		 * ": Default Location Change."); LOGGER.info(
		 * "EditInformation.loginChangeTimeZone : Default Location Updation has encountered a problem."
		 * ); }
		 */

		editInformation.logout();
	}

	@Parameters({ "step", "testdescription" })
	public void loginChangeTimeZone(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;

		/*
		 * try {
		 */
		EditInformationPage editInformation = new EditInformationPage(driver);
		editInformation.profileBtnClick();
		editInformation.editBtnClick();

		/*
		 * try {
		 */
		editInformation.updateTimeZone(parameters.get("timezone"));
		editInformation.commitClick();

		editInformation.logout();
		loginTest.verifyValidLogin1(step, testdescription, parameters.get("admin_username"),
				parameters.get("admin_password"), testContext);

		editInformation.profileBtnClick();
		boolean verified = editInformation.verifyTimeZone(parameters.get("timezone"));
		if (verified) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + (step) + "." + (++i) + ": Time Zone Change.");
			editInformation.editBtnClick();
			editInformation.updateTimeZone(parameters.get("originaltimezone"));
			editInformation.commitClick();
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + (step) + "." + (++i) + ": Time Zone Change.");
		}

		org.testng.Assert.assertTrue(verified, "Time Zone Change");

		/*
		 * } catch (Exception e) { e.printStackTrace();
		 * ReportManager.lognew(testContext.getName(), testdescription,
		 * LogStatus.FAIL, "Step " + (step) + "." + (++i) +
		 * ": Time Zone Change."); LOGGER.
		 * info("EditInformation.loginChangeTimeZone : Time Zone Updation has encountered a problem."
		 * ); } } catch (Exception e) { e.printStackTrace(); LOGGER.
		 * info("EditInformation.loginChangeTimeZone : loginChangeTimeZone method has encountered a problem."
		 * ); }
		 */
	}
}
