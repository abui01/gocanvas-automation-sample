package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.DownloadAndPdfViewPage;
import com.canvas.qa.pages.PublishAppPage;
import com.canvas.qa.pages.apps.AppBuilderPage;
import com.canvas.qa.pages.apps.EditAppPage;
import com.canvas.qa.pages.apps.RememberAndRecallPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class RememberAndRecallTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void verifyRememberRecall(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {

		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the Company designer: " + parameters.get("username"), testContext, testDescription);

			RememberAndRecallPage enableDisable = new RememberAndRecallPage(driver);
			DownloadAndPdfViewPage clickOnLogoutLink = new DownloadAndPdfViewPage(driver);
			CreateAppPage createapp = new CreateAppPage(driver);
			LoginPage login = new LoginPage(driver);
			EditAppPage destroy = new EditAppPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			AppBuilderPage appBuilder = new AppBuilderPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			String appName = randomAlphaNumeric(10);
			dashboardPage.clickApp();
			createapp.clickAppCreateButton();
			appBuilder.createAppWithScreen(appName);
			appBuilder.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));
			appBuilder.clickPublishToDeviceButton();
			if (appBuilder.isRenameYourAppDisplayed()) {
				appBuilder.clearAppName();
				appBuilder.enterAppName(appName);
				appBuilder.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));
				appBuilder.clickPublishToDeviceButton();
			}
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilder.clickCloseButton();
			createapp.clickAppName(appName);

			boolean defaultRememberRecallLink = enableDisable.verifyRememberAndRecall();
			reportLog(defaultRememberRecallLink, testContext.getName(),
					"Verify Remember & Recall icon displays under Features on my apps details page  ", "1.2",
					"Remember & Recall icon displays under Features.");
			org.testng.Assert.assertTrue(defaultRememberRecallLink);

			dashboardPage.clickApp();
			createapp.clickAppName(appName);
			destroy.destroyApp();
			customWait(5);

			clickOnLogoutLink.logout();
			boolean logoutMessage1 = createapp.afterlogout(parameters.get("logOut_msg"));
			reportLog(logoutMessage1, testContext.getName(), "Click on logout button in create app page", "2.1",
					"User Logout from the Application");
			org.testng.Assert.assertTrue(logoutMessage1);

			login.typeusername(parameters.get("username1"));
			login.typepassword(parameters.get("password1"));
			login.Clickonloginbutton();

			boolean appNameText_2 = enableDisable.pageNameAfterlogin(parameters.get("app_name"));
			reportLog(appNameText_2, testContext.getName(), "Login as the company Designer:", "2.2",
					"SignIn successfull");
			org.testng.Assert.assertTrue(appNameText_2, "SignIn successfull");

			String appName2 = randomAlphaNumeric(10);
			dashboardPage.clickApp();
			createapp.clickAppCreateButton();
			appBuilder.createApp(appName2);
			appBuilder.clickSaveButton(appName2,parameters.get("username1"),parameters.get("password1"));
			appBuilder.clickPublishToDeviceButton();
			if (appBuilder.isRenameYourAppDisplayed()) {
				appBuilder.clearAppName();
				appBuilder.enterAppName(appName2);
				appBuilder.clickSaveButton(appName2,parameters.get("username1"),parameters.get("password1"));
				appBuilder.clickPublishToDeviceButton();
			}
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilder.clickCloseButton();
			createapp.clickAppName(appName2);

			boolean enableDispatchLink = enableDisable.verifyRememberAndRecall();
			reportLog(enableDispatchLink, testContext.getName(), "Verify Remember & Recall Link in the bottom  ", "2.3",
					"Remember & recall button is avilable in the bootom of app detail page");
			org.testng.Assert.assertTrue(enableDispatchLink);

			boolean disableRememberRecll = enableDisable.verifyRememberRecallCheckBox();
			reportLog(disableRememberRecll, testContext.getName(),
					"Verify  Remember/Recall Tooggle defaut selected or Not disable ' ", "2.4",
					"Remember Recall Option enable by default.");
			org.testng.Assert.assertTrue(disableRememberRecll);

			// enableDisable.verifyClickRemAndRecallLink1();
			boolean disableRemAndRecallLink1 = enableDisable.clickOnRememberAndRecallLink(parameters.get("disableMsg"));
			reportLog(disableRemAndRecallLink1, testContext.getName(),
					"Verify user sees Remember/Recall has been successfully disabled ", "3.1",
					"Remember/Recall has been successfully disabled");
			org.testng.Assert.assertTrue(disableRemAndRecallLink1);

			clickOnLogoutLink.logout();
			boolean logoutMessage2 = createapp.afterlogout(parameters.get("logOut_msg"));
			reportLog(logoutMessage2, testContext.getName(), "Click on logout button in create app page", "4.1",
					"User Logout from the Application");
			org.testng.Assert.assertTrue(logoutMessage2);

			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbutton();

			boolean appNameText_3 = enableDisable.pageNameAfterlogin2(parameters.get("app_name1"));
			reportLog(appNameText_3, testContext.getName(), "Login as the company Designer:", "4.2",
					"SignIn successfull");
			org.testng.Assert.assertTrue(appNameText_3, "SignIn successfull");

			dashboardPage.clickApp();
			createapp.clickAppName(appName2);

			boolean enableRememberRecall = enableDisable.clickOnRememberAndRecallLink(parameters.get("enableMsg"));
			reportLog(enableRememberRecall, testContext.getName(),
					"Verify user sees Remember/Recall has been successfully enabled  ' ", "4.1",
					"Remember/Recall has been successfully enabled");
			org.testng.Assert.assertTrue(enableRememberRecall);

			// enableDisable.verifyClickRemAndRecallLink2();
			// boolean disableRemANdRecallLink2
			// =enableDisable.verifyDisableRemAndRecallLink2(parameters.get("enableMsg"));
			// reportLog(disableRemANdRecallLink2, testContext.getName(),
			// "Verify users sees Remember/Recall has been successfully
			// disabled' ","4.3","Remember/Recall has been successfully
			// enabled");
			// org.testng.Assert.assertTrue(disableRemANdRecallLink2);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
