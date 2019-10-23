package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.PublishAppPage;
import com.canvas.qa.pages.apps.AppBuilderPage;
import com.canvas.qa.pages.apps.AppsPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class DestroyAppTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void createAndDestroyAppTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = new CreateAppPage(driver);
			AppBuilderPage appBuilder = new AppBuilderPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			AppsPage appsPage = new AppsPage(driver);
			LoginPage loginPage = new LoginPage(driver);

			String appName = parameters.get("app_name") + randomAlphaNumeric(10);

			dashboardPage.clickApp();
			createAppPage.clickAppCreateButton();
			appBuilder.createApp(appName, testContext, "1.2");
			appBuilder.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));

			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Succesfull message",
					"2.2", "Verify Save successful appears: IS NOT AUTOMATED");

			appBuilder.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();

			appsPage.appClick(appName);
			reportLog(true, testContext.getName(), "User clicked on newly created app " + appName, "3.0",
					"User lands on details page of " + appName + " app");

			boolean status = appsPage.verifyDestroyAppButtonIsActive();
			reportLog(status, testContext.getName(), "User is in app screen, verify Destroy App button is clickable",
					"4.0", "Destroy App Button Link exists and is clickable");
			org.testng.Assert.assertTrue(status);

			appsPage.moveMouseToDestroyButton();
			boolean textpresent = appsPage.verifyDestroyAppModalText(
					"Deletes a Published version of an app. THIS ALSO DELETES ALL SUBMISSION DATA");
			reportLog(textpresent, testContext.getName(),
					"User hovers over Destroy App Button to verify text modal appears", "4.0",
					"Text: 'Deletes a Published version of an app. THIS ALSO DELETES ALL SUBMISSION DATA' exists.");
			org.testng.Assert.assertTrue(textpresent);

			appsPage.clickRetireAppLink();

			boolean appExists = appsPage.verifyAppNoLongerExists(appName);
			reportLog(appExists, testContext.getName(),
					"User verifies if " + appName + " exists on app page after retiring app.", "5.0",
					appName + " app no longer appears on the page");
			org.testng.Assert.assertTrue(appExists);

			// Log out and Login as new user
			dashboardPage.clickLogOutButton();

			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password2"));
			loginPage.Clickonloginbutton();

			String pendingAppName = parameters.get("pending_app_name");
			String newStatusApp = parameters.get("new_status_app");

			dashboardPage.clickApp();
			appsPage.appClick(pendingAppName);

			boolean destroyAppButtonInactive = appsPage.verifyDestroyAppButtonInactive();
			reportLog(destroyAppButtonInactive, testContext.getName(),
					"Verify user unable to Destroy App when app set to 'Pending'", "6.0",
					"Destroy App button is disabled on " + pendingAppName);
			org.testng.Assert.assertTrue(destroyAppButtonInactive);

			dashboardPage.clickApp();
			appsPage.appClick(newStatusApp);

			boolean destroyAppButtonInactive2 = appsPage.verifyDestroyAppButtonInactive();
			reportLog(destroyAppButtonInactive2, testContext.getName(),
					"Verify user unable to Destroy App when app set to 'New'", "7.0",
					"Destroy App button is disabled on " + newStatusApp);
			org.testng.Assert.assertTrue(destroyAppButtonInactive2);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

}
