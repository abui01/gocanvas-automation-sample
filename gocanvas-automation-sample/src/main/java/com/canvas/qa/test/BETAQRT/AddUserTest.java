package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.DeliveryStatusPage;
import com.canvas.qa.pages.profile.AddUsersPage;
import com.canvas.qa.pages.profile.EditInformationPage;
import com.canvas.qa.pages.profile.UsersPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

@Test
public class AddUserTest extends BrowserLaunchTest {
	private String firstEmailID;

	@Parameters({ "step" })
	public void add20Users(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		CanvasLoginTest loginTest = new CanvasLoginTest();
		try {
			loginTest.verifyValidLoginnew(step, parameters.get("business_username"),
					parameters.get("business_password"), testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}
		i += 3;

		AddUsersPage addUsersLink = new AddUsersPage(driver);
		addUsersLink.usersButtonClick();
		addUsersLink.addUsersButtonClick();

		addUsersLink.setNumUsers(parameters.get("business_numUsersMore"));
		addUsersLink.setNumUsersText(parameters.get("business_numUsers"));
		addUsersLink.purchaseClick();

		UsersPage usersLink = new UsersPage(driver);
		if (usersLink.getNumOpen() == Integer.parseInt(parameters.get("business_numUsers"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Users Added to Business Account.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Users Added to Business Account.");
		}
		org.testng.Assert.assertTrue(usersLink.getNumOpen() == Integer.parseInt(parameters.get("business_numUsers")));
		usersLink.logout();
	}

	@Parameters({ "step" })
	public void add3Users(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		CanvasLoginTest loginTest = new CanvasLoginTest();
		try {
			loginTest.verifyValidLoginnew(step, parameters.get("trial_email"), parameters.get("trial_password"),
					testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}
		i += 3;

		AddUsersPage addUsersLink = new AddUsersPage(driver);
		addUsersLink.usersButtonClick();
		addUsersLink.addUsersButtonClick();

		addUsersLink.setNumUsers(parameters.get("trial_numUsers"));
		addUsersLink.addButtonClick();

		UsersPage usersLink = new UsersPage(driver);
		if (usersLink.getNumOpen() == Integer.parseInt(parameters.get("trial_numUsers"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Users Added to Trial Account.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Users Added to Trial Account.");
		}
		org.testng.Assert.assertTrue(usersLink.getNumOpen() == Integer.parseInt(parameters.get("trial_numUsers")));
		addUsersLink.logout();
	}

	@Parameters({ "step" })
	public void addUserCancel(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		AddUsersPage addUsersLink = new AddUsersPage(driver);
		addUsersLink.setFirstName(parameters.get("firstName"));
		addUsersLink.setLastName(parameters.get("lastName"));
		addUsersLink.setEmail(parameters.get("firstValidEmail"));
		addUsersLink.setRole(parameters.get("secondUserRole"));

		addUsersLink.cancelClick();
		addUsersLink.addUsersButtonClick();

		if (addUsersLink.getNumUsersDDLValue().equals(parameters.get("default_userCount"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Default User Count.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Default User Count.");
		}
		org.testng.Assert.assertTrue(addUsersLink.getNumUsersDDLValue().equals(parameters.get("default_userCount")));

		if (addUsersLink.getFirstName().equals(parameters.get("default_firstName"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Default User First Name.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Default User First Name.");
		}
		org.testng.Assert.assertTrue(addUsersLink.getFirstName().equals(parameters.get("default_firstName")));
		if (addUsersLink.getLastName().equals(parameters.get("default_lastName"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Default User Last Name.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Default User Last Name.");
		}
		org.testng.Assert.assertTrue(addUsersLink.getLastName().equals(parameters.get("default_lastName")));
		if (addUsersLink.getEmail().equals(parameters.get("default_email"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Default User Email.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Default User Email.");
		}
		org.testng.Assert.assertTrue(addUsersLink.getEmail().equals(parameters.get("default_email")));
		if (addUsersLink.getRole().equals(parameters.get("default_role"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Default User Role.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Default User Role.");
		}
		org.testng.Assert.assertTrue(addUsersLink.getRole().equals(parameters.get("default_role")));
		if (addUsersLink.areAddOptionsChecked()) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Default Send Instruction and Set Password.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Default Send Instruction and Set Password.");
		}
		org.testng.Assert.assertTrue(addUsersLink.areAddOptionsChecked());
	}

	@Parameters({ "step" })
	public void addUserDefaults(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;
		try {
			loginTest.verifyValidLogin1(step, parameters.get("trial_email"), parameters.get("trial_password"),
					testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}
		i += 3;

		AddUsersPage addUsersLink = new AddUsersPage(driver);
		addUsersLink.usersButtonClick();
		addUsersLink.addUsersButtonClick();

		if (addUsersLink.getNumUsersDDLValue().equals(parameters.get("default_userCount"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Default User Count.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Default User Count.");
		}
		org.testng.Assert.assertTrue(addUsersLink.getNumUsersDDLValue().equals(parameters.get("default_userCount")));
		if (addUsersLink.getFirstName().equals(parameters.get("default_firstName"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Default User First Name.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Default User First Name.");
		}
		org.testng.Assert.assertTrue(addUsersLink.getFirstName().equals(parameters.get("default_firstName")));
		if (addUsersLink.getLastName().equals(parameters.get("default_lastName"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Default User Last Name.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Default User Last Name.");
		}
		org.testng.Assert.assertTrue(addUsersLink.getLastName().equals(parameters.get("default_lastName")));
		if (addUsersLink.getEmail().equals(parameters.get("default_email"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Default User Email.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Default User Email.");
		}
		org.testng.Assert.assertTrue(addUsersLink.getEmail().equals(parameters.get("default_email")));
		if (addUsersLink.getRole().equals(parameters.get("default_role"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Default User Role.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Default User Role.");
		}
		org.testng.Assert.assertTrue(addUsersLink.getRole().equals(parameters.get("default_role")));
		if (addUsersLink.areAddOptionsChecked()) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Default Send Instruction and Set Password.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Default Send Instruction and Set Password.");
		}
		org.testng.Assert.assertTrue(addUsersLink.areAddOptionsChecked());
	}

	@Parameters({ "step" })
	public void blankError(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		AddUsersPage addUsersLink = new AddUsersPage(driver);

		addUsersLink.addButtonClick();
		boolean status = addUsersLink.isErrorMessageDisplayed("First Name is required")
				&& addUsersLink.isErrorMessageDisplayed("Last Name is required")
				&& addUsersLink.isErrorMessageDisplayed("Email is required");
		if (status) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Invalid Email error message.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Invalid Email error message.");
		}
		org.testng.Assert.assertTrue(status);
	}

	@Parameters({ "step" })
	public void emailsSent(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		AddUsersPage addUsersLink = new AddUsersPage(driver);
		addUsersLink.setPassword(parameters.get("firstPassword"));
		addUsersLink.addButtonClick();
		addUsersLink.saveButtonClick();

		addUsersLink.addUsersButtonClick();

		addUsersLink.setFirstName(parameters.get("firstName"));
		addUsersLink.setLastName(parameters.get("lastName"));
		addUsersLink.setEmail(parameters.get("secondValidEmail"));
		addUsersLink.setRole(parameters.get("secondUserRole"));
		addUsersLink.addButtonClick();
		addUsersLink.cancelClick();

		addUsersLink.logout();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		try {
			loginTest.verifyValidLogin1(step, parameters.get("user"), parameters.get("password"), testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}
		i += 3;

		DeliveryStatusPage deliveryStatusLink = new DeliveryStatusPage(driver);
		deliveryStatusLink.gotoUser(parameters.get("trial_email"));
		deliveryStatusLink.deliveryStatClick();

		if (deliveryStatusLink.installEmailSent(parameters.get("secondValidEmail"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Installation Email sent to new user.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Installation Email sent to new user.");
		}
		org.testng.Assert.assertTrue(deliveryStatusLink.installEmailSent(parameters.get("secondValidEmail")));
		if (deliveryStatusLink.setPassEmailSent(parameters.get("secondValidEmail"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Set Password Email sent to new user.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Set Password Email sent to new user.");
		}
		org.testng.Assert.assertTrue(deliveryStatusLink.setPassEmailSent(parameters.get("secondValidEmail")));
		addUsersLink.logout();
	}

	@Parameters({ "step" })
	public void invalidEmailError(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		AddUsersPage addUsersLink = new AddUsersPage(driver);

		addUsersLink.setFirstName(parameters.get("firstName"));
		addUsersLink.setLastName(parameters.get("lastName"));
		addUsersLink.setEmail(parameters.get("invalidEmail"));

		addUsersLink.addButtonClick();
		boolean status = addUsersLink.isErrorMessageDisplayed("Please enter a valid email");
		if (status) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Invalid Email error message.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Invalid Email error message.");
		}
		org.testng.Assert.assertTrue(status);
	}

	@Parameters({ "step" })
	public void invalidPasswordError(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;
		Random rand = new Random();
		firstEmailID = rand.nextInt(5000) + parameters.get("firstValidEmail");
		AddUsersPage addUsersLink = new AddUsersPage(driver);
		addUsersLink.setEmail(firstEmailID);
		addUsersLink.uncheckAddOptions();
		addUsersLink.setPassword(parameters.get("invalidPassword"));
		addUsersLink.addButtonClick();
		boolean status = addUsersLink.isErrorMessageDisplayed("Minimum of 6 characters");
		if (status) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Invalid Password error message.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Invalid Email error message.");
		}
		org.testng.Assert.assertTrue(status);
	}

	@Parameters({ "step" })
	public void postConditions(String step, ITestContext testContext) throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		CanvasLoginTest loginTest = new CanvasLoginTest();

		loginTest.verifyValidLoginnew(step, parameters.get("business_username"), parameters.get("business_password"),
				testContext);

		i += 3;

		UsersPage usersLink = new UsersPage(driver);
		usersLink.usersButtonClick();

		usersLink.disableOpenUsers();

		usersLink.logout();

		loginTest.verifyValidLogin1(step, parameters.get("trial_email"), parameters.get("trial_password"), testContext);

		i += 3;

		String milli = Long.toString(System.currentTimeMillis());
		usersLink.usersButtonClick();
		usersLink.goToUser(firstEmailID);

		EditInformationPage editInfoLink = new EditInformationPage(driver);
		editInfoLink.editBtnClick();
		editInfoLink.updateEmail(firstEmailID + milli);
		editInfoLink.commitClick();

		usersLink.usersButtonClick();
		usersLink.goToUser(parameters.get("secondValidEmail"));

		editInfoLink.editBtnClick();
		editInfoLink.updateEmail(parameters.get("secondValidEmail") + milli);
		editInfoLink.commitClick();

		usersLink.usersButtonClick();
		usersLink.disableUser(firstEmailID);
		usersLink.disableUser(parameters.get("secondValidEmail"));
		usersLink.disableOpenUsers();
	}
}
