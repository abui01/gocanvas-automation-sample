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
import com.canvas.qa.pages.apps.EditAndViewPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class EditAndViewTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void verifyEditAndView(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {

		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the Company designer: " + parameters.get("username"), testContext, testDescription);

			EditAndViewPage enableDisable = new EditAndViewPage(driver);
			DownloadAndPdfViewPage clickOnLogoutLink = new DownloadAndPdfViewPage(driver);
			CreateAppPage createapp = new CreateAppPage(driver);
			LoginPage login = new LoginPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			AppBuilderPage appBuilder = new AppBuilderPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			String appName = randomAlphaNumeric(10);
			dashboardPage.clickApp();
			createapp.clickAppCreateButton();
			appBuilder.createAppWithScreen(appName);
			appBuilder.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));
			appBuilder.clickPublishToDeviceButton();

			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilder.clickCloseButton();
			createapp.clickAppName(appName);

			boolean defaultEditViewLink = enableDisable.verifyEditAndView();
			reportLog(!defaultEditViewLink, testContext.getName(),
					"Verify Edit & View icon displays under Features on my apps details page ", "1.2",
					"Edit & View icon does not display to a user on a start up plan");
			org.testng.Assert.assertFalse(defaultEditViewLink);

			dashboardPage.clickApp();
			createapp.clickAppName(appName).destroyApp();
			customWait(5);

			clickOnLogoutLink.logout();
			boolean logoutMessage1 = createapp.afterlogout(parameters.get("logOut_msg"));
			reportLog(logoutMessage1, testContext.getName(), "Click on logout button in create app page", "2.1",
					"User Logout from the Application");
			org.testng.Assert.assertTrue(logoutMessage1);

			login.typeusername(parameters.get("username1"));
			login.typepassword(parameters.get("password1"));
			login.Clickonloginbutton();
			String appName2 = randomAlphaNumeric(10);
			dashboardPage.clickApp();
			createapp.clickAppCreateButton();
			appBuilder.createApp(appName2, testContext, "2.1.1");
			appBuilder.clickSaveButton(appName2,parameters.get("username1"),parameters.get("password1"));
			appBuilder.clickPublishToDeviceButton();

			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilder.clickCloseButton();

			boolean appNameText_2 = enableDisable.pageNameAfterlogin(parameters.get("app_name"));
			reportLog(appNameText_2, testContext.getName(), "Login as the company designer :", "2.2",
					"SignIn successfull");
			org.testng.Assert.assertTrue(appNameText_2, "SignIn successfull");

			createapp.clickAppName(appName2);

			boolean enableDispatchLink = enableDisable.verifyEditReviewLinkInBottom();
			reportLog(enableDispatchLink, testContext.getName(), "Verify Edit & View Link in the bottom  ", "2.3",
					"Edit and View icon does displays under Features on my apps details page.");
			org.testng.Assert.assertTrue(enableDispatchLink);

			enableDisable.clickOnEditAndViewLink();

			boolean checkBoxValue = enableDisable.verifyAllowedADRToDeleteCheckBox();
			reportLog(checkBoxValue, testContext.getName(),
					"Verify checkmarks for Web Use under Allowed to delete by default for Admins, Designers, Reporters.",
					"3.1",
					"By default there are check marks besides for 'Allowed to delete' for Admins, Designers, Reporters.");
			org.testng.Assert.assertTrue(checkBoxValue);

			boolean checkBoxValue1 = enableDisable.verifyEditedOnMobile();
			reportLog(checkBoxValue1, testContext.getName(),
					"Verify by default there is a checkmark under Submission Date for 'When edited on mobile'", "3.2",
					"By default there is a check mark besides, 'When edited on mobile'..");
			org.testng.Assert.assertTrue(checkBoxValue1);

			enableDisable.selectCheckBox();
			boolean selectcheckBox = enableDisable.saveSelectedCheckBox(parameters.get("saveMsg"));
			reportLog(selectcheckBox, testContext.getName(), "Click on save button after selecting checkboxs", "4.1",
					"Edit options have been successfully updated after selecting the check box than saving the data");
			org.testng.Assert.assertTrue(selectcheckBox);

			enableDisable.clickOnEditAndViewLink();
			enableDisable.deselectCheckBox();

			boolean deselectcheckBox = enableDisable.saveDeselectCheckBox(parameters.get("saveMsg"));
			reportLog(deselectcheckBox, testContext.getName(), "Click on save button after un-selecting the checkboxs",
					"5.1",
					"Edit options have been successfully updated after de-selecting the check box than saving the data.");
			org.testng.Assert.assertTrue(deselectcheckBox);

			dashboardPage.clickApp();
			createapp.clickAppName(appName2).destroyApp();
			customWait(5);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
