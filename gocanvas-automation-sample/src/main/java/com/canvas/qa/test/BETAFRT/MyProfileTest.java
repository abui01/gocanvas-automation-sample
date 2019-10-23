package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.SearchUsersPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.profile.ProfilePage;
import com.canvas.qa.pages.profile.UsersPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class MyProfileTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void getCanvasClientTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company user: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickProfile();
			boolean status = dashboardPage.isDownloadGoCanvasClientDisplayed();
			reportLog(status, testContext.getName(),
					"Under Actions, verify the user sees Download GoCanvas Client button", "1.1",
					"User has access to Get Canvas Client button.");
			org.testng.Assert.assertTrue(status);

			dashboardPage.clickDownloadGoCanvasClient();
			status = dashboardPage.getToastMessage().contains(parameters.get("message1"));
			reportLog(status, testContext.getName(),
					"User receives this message An email was sent to 12@4321.cc. On your mobile device, open the email and follow the link to install the app",
					"2.0",
					"An email was sent to 12@4321.cc. On your mobile device,open the email and follow the link to install the app: message appears.");
			org.testng.Assert.assertTrue(status);

			dashboardPage.clickLogOut();
			LoginPage login = new LoginPage(driver);
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbutton();
			SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
			searchUsersPage.enterEmail(parameters.get("email"));
			searchUsersPage.clickSearch();
			dashboardPage = searchUsersPage.clickOnManage(parameters.get("email"));
			dashboardPage.clickGoCanvasOnly();
			dashboardPage.clickDeliveryStatus();
			status = dashboardPage.isEmailTypeDisplayed(parameters.get("Email_Type"));
			reportLog(status, testContext.getName(), "Email type displayed: " + parameters.get("Email_Type"), "3.0",
					"Email type displayed: " + parameters.get("Email_Type"));
			org.testng.Assert.assertTrue(status);

			status = dashboardPage.isRecipientDisplayed(parameters.get("username"));
			reportLog(status, testContext.getName(), "Receipent displayed: " + parameters.get("username"), "3.1",
					"Receipent displayed: " + parameters.get("username"));
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void myAccountTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickAccount();
			UsersPage usersPage = dashboardPage.clickUsers();
			usersPage.searchUser(parameters.get("username"));
			if (!usersPage.isNameDisplayed(parameters.get("name"))) {
				ProfilePage profilePage = usersPage.clickNameByEmail(parameters.get("username"));
				profilePage.clickEdit();
				profilePage.updateFirstName(parameters.get("first_name"));
				usersPage = dashboardPage.clickUsers();
				usersPage.searchUser(parameters.get("username"));
			}

			ProfilePage profilePage = usersPage.clickName(parameters.get("name"));
			boolean status = profilePage.isProfileDisplayed();
			reportLog(status, testContext.getName(), "Verify user is directed to the users profile page.", "1.1",
					"User is directed to the Profile page successfully.");
			org.testng.Assert.assertTrue(status);

			dashboardPage.clickAccount();
			usersPage = dashboardPage.clickUsers();
			usersPage.searchUser(parameters.get("username2"));
			profilePage = usersPage.clickName(parameters.get("name2"));
			status = profilePage.isDownloadGoCanvasClientDisplayed();
			reportLog(status, testContext.getName(), "Download GoCanvas Client displayed", "2.0",
					"Download GoCanvas Client displayed");
			org.testng.Assert.assertTrue(status);

			status = profilePage.isAppAssignmentsDisplayed();
			reportLog(status, testContext.getName(), "App Assignments displayed", "2.1", "App Assignments displayed");
			org.testng.Assert.assertTrue(status);

			status = profilePage.isChangePasswordDisplayed();
			reportLog(status, testContext.getName(), "Change Password displayed", "2.2", "Change Password displayed");
			org.testng.Assert.assertTrue(status);

			usersPage = profilePage.clickUsers();
			status = usersPage.isUsersDisplayed();
			reportLog(status, testContext.getName(), "Verify user is directed to the users page.", "3.0",
					"User is directed to the Users page successfully.");
			org.testng.Assert.assertTrue(status);

			usersPage.searchUser(parameters.get("username"));
			profilePage = usersPage.clickName(parameters.get("name"));
			profilePage.clickEdit();
			profilePage.updateFirstName(parameters.get("new_name"));
			status = profilePage.getToastMessage().contains(parameters.get("message"));
			//System.out.println(profilePage.getToastMessage()+"/n"+parameters.get("message"));
			reportLog(status, testContext.getName(),
					"Verify you see the success message: Your profile has been updated", "4.0",
					"Your profile has been updated: message appears");
			org.testng.Assert.assertTrue(status);

			profilePage.clickEdit();
			profilePage.updateFirstName(parameters.get("first_name"));

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", 
					e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
