package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.PublishAppPage;
import com.canvas.qa.pages.SubmissionNumbering;
import com.canvas.qa.pages.SubmissionStatusesPage;
import com.canvas.qa.pages.apps.AppBuilderPage;
import com.canvas.qa.pages.apps.EditAppPage;
import com.canvas.qa.pages.apps.PDFDesignerLayoutPage;
import com.canvas.qa.pages.apps.PDFDesignerPage;
import com.canvas.qa.pages.dispatch.DispatchCalendarPage;
import com.canvas.qa.pages.dispatch.DispatchCalendarViewPage;
import com.canvas.qa.pages.dispatch.DispatchPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.qa.test.UtilityFunctions;
import com.canvas.util.FileReaderUtil;

import org.testng.Assert;

/**
 * @param testContext
 * @throws IOException
 * @throws InterruptedException
 */

/** TC10428 Create App With MultipleScreens **/

public class AppBuilderTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void appWithGridScreenTest(String testDescription, String rallyLink, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription,
					rallyLink);
			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = dashboardPage.clickApp();
			AppBuilderPage appBuilderPage = createAppPage.clickAppCreateButton();
			String appName = randomAlphaNumeric(10);
			appBuilderPage.clickBlankTemplate();
			appBuilderPage.clickStartButton();
			boolean status = appBuilderPage.isPublishToDeviceButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify App builder should open.", "1.2",
					"App builder opened successfully.");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.enterAppName(appName);
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));
			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Succesfull message",
					"2.0", "Verify Save successful appears: IS NOT AUTOMATED");

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLoopIcon();
			appBuilderPage.clickUseExistingField();
			appBuilderPage.clickExistingFieldDropDown();
			appBuilderPage.selectExistingFiedValue(parameters.get("loop_field"));
			appBuilderPage.clickDoneButton();
			appBuilderPage.clickSaveButton();

			appBuilderPage.clickScreen(parameters.get("loop_field"));
			appBuilderPage.clickSettingsButton();
			appBuilderPage.selectGridStyle(parameters.get("style"));
			status = appBuilderPage.getGridEnabledMessage().contains(parameters.get("message"));
			reportLog(status, testContext.getName(), "Verify message Grid Enabled for this screen is displayed.", "3.1",
					"Message Grid Enabled for this screen is displayed.");
			org.testng.Assert.assertTrue(status);

			status = appBuilderPage.isPreviewGridButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify button with Name Preview Grid is displayed.", "3.2",
					"Button with Name Preview Grid is displayed.");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.clickSaveButton();
			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			status = publishAppPage.isPublishButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify User re-direct to Publish App Page.", "4.1",
					"User re-directed to Publish App Page successfully.");
			org.testng.Assert.assertTrue(status);

			appBuilderPage = publishAppPage.verifyCreatedApp(testContext, parameters.get("name"),
					parameters.get("username"), appName, parameters.get("app_status"), "4.2", "4.3", "4.4");
			createAppPage = appBuilderPage.clickCloseButton();
			createAppPage.verifyPublishedApp(testContext, "Version 1", appName, "5.");

			status = createAppPage.isAppNameDisplayed(appName);
			reportLog(status, testContext.getName(), "Verify Created App in Apps page after publishing the app", "6.0",
					"Created App with name: " + appName + " displayed in the list in Apps page");
			org.testng.Assert.assertTrue(status);

			appBuilderPage = createAppPage.clickAppName(appName).clickEditAppButton();
			appBuilderPage.clickScreen(parameters.get("loop_field"));
			status = appBuilderPage.isFieldDisplayed(parameters.get("loop_field"));
			reportLog(status, testContext.getName(),
					"Verify field with label Age should display in parent screen (Age)", "7.0",
					"Field with label Age displayed in parent screen (Age).");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.verifyScreenLabels("7.1.", parameters.get("first_screen_name"), testContext,
					"Verify First screen labels", parameters.get("final_first_screen_fields").split(";"),
					parameters.get("final_first_screen_labels").split(";"));

			status = appBuilderPage.getGridEnabledMessage().contains(parameters.get("message"));
			reportLog(status, testContext.getName(), "Verify message Grid Enabled for this screen is displayed.", "7.2",
					"Message Grid Enabled for this screen is displayed.");
			org.testng.Assert.assertTrue(status);

			status = appBuilderPage.isPreviewGridButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify button with Name Preview Grid is displayed.", "7.3",
					"Button with Name Preview Grid is displayed.");
			org.testng.Assert.assertTrue(status);

			createAppPage = appBuilderPage.clickCloseButton();
			createAppPage.clickAppName(appName).destroyApp();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void companyReporterCompanyUser(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createApp = new CreateAppPage(driver);
			LoginPage loginPage = new LoginPage(driver);

			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company reporter : " + parameters.get("username"), testContext, testDescription);

			// dashboardPage.clickOnToastMessageCrossSign();
			dashboardPage.isDashBoardLinkDispaly();

			boolean dashBoardLinkCompanyReporter = dashboardPage.isDashBoardLinkDispaly();
			reportLog(!dashBoardLinkCompanyReporter, testContext.getName(), "Verify dashbaord link after login ", "1.2",
					"Dashboard does not appear.");
			org.testng.Assert.assertFalse(dashBoardLinkCompanyReporter);
			//
			// <<<<<<< HEAD
			// boolean toastMessageAfterEnteringUrlCompanyReporter =
			// dashboardPage.enterUrlInBrowser(
			// parameters.get("toastmsg"), PropertyUtils.getProperty("app.url") +
			// "/dashboards");
			// reportLog(toastMessageAfterEnteringUrlCompanyReporter, testContext.getName(),
			// "Verify the message after entering url in the browser ", "2.1",
			// =======

			/**
			 * this toast message no longer appears dashboardPage.isToastMessageDisplayed();
			 * 
			 * boolean toastMessageCompanyReporter =
			 * dashboardPage.isToastMessageDisplayed();
			 * reportLog(!toastMessageCompanyReporter, testContext.getName(), "Verify toast
			 * message after login ", "2.1", "This appears: You don't have access to this
			 * section of the site.");
			 * org.testng.Assert.assertFalse(toastMessageCompanyReporter);
			 */

			createApp.logout();
			loginPage.typeusername(parameters.get("username1"));
			loginPage.typepassword(parameters.get("password1"));
			loginPage.Clickonloginbuttonforinvalidlogin();
			if (loginPage.ifTermsOfUseDispalyed()) {
				loginPage.clickTermsOfUseCheckbox();
				loginPage.typepassword(parameters.get("password1"));
				loginPage.Clickonloginbuttonforinvalidlogin();
			}

			boolean dashBoardLinkCompanyUser = dashboardPage.isDashBoardLinkDispaly();
			reportLog(!dashBoardLinkCompanyUser, testContext.getName(), "Verify dashbaord link after login ", "3.1",
					"Dashboard does not appear.");
			org.testng.Assert.assertFalse(dashBoardLinkCompanyUser);

			/**
			 * toast message no longer displays dashboardPage.isToastMessageDisplayed();
			 * 
			 * boolean toastMessageCompanyUser = dashboardPage.isToastMessageDisplayed();
			 * reportLog(!toastMessageCompanyUser, testContext.getName(), "Verify toast
			 * message after login ", "4.1", "This appears: You don't have access to this
			 * section of the site.");
			 * org.testng.Assert.assertFalse(toastMessageCompanyUser);
			 */

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	@Test
	@Parameters({ "testdescription" })
	public void copyApp(String testDescription, ITestContext testContext) throws IOException, InterruptedException

	{
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {

			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createApp = new CreateAppPage(driver);
			LoginPage loginPage = new LoginPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			String duplicateAppName = appName + " - Copy";
			AppBuilderPage appBuilder = new AppBuilderPage(driver);
			EditAppPage editAppPage = new EditAppPage(driver);
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer : " + parameters.get("username"), testContext, testDescription);

			dashboardPage.clickApp();
			createApp.clickAppName("App with no custom logo");
			// appBuilder.clickAppName();

			boolean copyAppIcon = appBuilder.verifyCopyAppIcon();
			reportLog(copyAppIcon, testContext.getName(),
					"Verify copy icon under Actions section after clicking on App ", "1.2",
					"Copy app icon displays under Actions section");
			org.testng.Assert.assertTrue(copyAppIcon);

			boolean toolTipText = appBuilder.verifytoolTipText(parameters.get("tool_tip_text"));
			reportLog(toolTipText, testContext.getName(), "Verify tag tip says, 'Creates a copy of this app'", "2.1",
					"Creates a copy of this app displays correctly while hovering over Copy App icon.");
			org.testng.Assert.assertTrue(toolTipText);

			dashboardPage.clickApp();
			createApp.clickAppCreateButton();
			appBuilder.createApp(appName, testContext, "3.1");

			appBuilder.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));

			PublishAppPage publishAppPage = appBuilder.clickPublishToDeviceButton();
			publishAppPage.clickNextPublishButton();
			publishAppPage.clickPublishButton();
			appBuilder.clickCloseButton();
			createApp.clickAppName(appName);
			createApp.uploadAppLogo();

			String message = "An app has been created from your '" + appName + "' app.";
			boolean copyMessageText = appBuilder.verifyCopyText(message);
			reportLog(copyMessageText, testContext.getName(), "Verify message after clicking oncopy icon", "3.2",
					"Message 'An app has been created from your 'Try This App First' app.' display.");
			org.testng.Assert.assertTrue(copyMessageText);

			dashboardPage.clickApp();
			boolean dupAppName = createApp.isAppNameDisplayed(duplicateAppName);
			reportLog(dupAppName, testContext.getName(), "Verify duplicate App name", "3.3",
					"New Copied app with name 'Try This App First - Copy' is verified");
			org.testng.Assert.assertTrue(dupAppName);

			dashboardPage.clickApp();
			boolean appStatus = createApp.getAppStatus(duplicateAppName).contains(parameters.get("app_status"));
			reportLog(appStatus, testContext.getName(), "Verify status of App after copied", "4.1",
					"New copied app, status is set to 'New' verified ");
			org.testng.Assert.assertTrue(appStatus);

			dashboardPage.clickApp();
			boolean appVersion = createApp.getAppVersion(duplicateAppName).contains(parameters.get("app_version"));
			reportLog(appVersion, testContext.getName(), "Verify version of App ", "4.2",
					"New copied app, version set to 'version 1' verified");
			org.testng.Assert.assertTrue(appVersion);

			createApp.logout();
			loginPage.typeusername(parameters.get("username1"));
			loginPage.typepassword(parameters.get("password1"));
			loginPage.Clickonloginbutton();

			dashboardPage.clickApp();
			createApp.deleteApp(duplicateAppName);

			String message2 = "A pending version of your '" + duplicateAppName + "' app has been removed.";
			boolean appDeletedStatus = appBuilder.isCopiedAppDelete(message2);
			reportLog(appDeletedStatus, testContext.getName(), "Verify user successfully delete the copied app ", "5.1",
					"Copied App deleted is verified");
			org.testng.Assert.assertTrue(appDeletedStatus);

			dashboardPage.clickApp();
			createApp.clickAppName(appName);
			editAppPage.destroyApp();

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void copyScreenAndFieldsTest(String testDescription, String rallyLink, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription,
					rallyLink);
			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = dashboardPage.clickApp();
			AppBuilderPage appBuilderPage = createAppPage.clickAppCreateButton();
			String appName = randomAlphaNumeric(10);
			appBuilderPage.clickBlankTemplate();
			appBuilderPage.clickStartButton();
			boolean status = appBuilderPage.isPublishToDeviceButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify App builder should open.", "1.2",
					"App builder opened successfully.");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.enterAppName(appName);
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));
			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Succesfull message",
					"2.0", "Verify Save successful appears: IS NOT AUTOMATED");

			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			status = publishAppPage.isPublishButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify User re-direct to Publish App Page.", "3.1",
					"User re-directed to Publish App Page successfully.");
			org.testng.Assert.assertTrue(status);

			appBuilderPage = publishAppPage.verifyCreatedApp(testContext, parameters.get("name"),
					parameters.get("username"), appName, parameters.get("app_status"), "3.2", "3.3", "3.4");
			createAppPage = appBuilderPage.clickCloseButton();
			createAppPage.verifyPublishedApp(testContext, "Version 1", appName, "4.");

			status = createAppPage.isAppNameDisplayed(appName);
			reportLog(status, testContext.getName(), "Verify Created App in Apps page after publishing the app", "5.0",
					"Created App with name: " + appName + " displayed in the list in Apps page");
			org.testng.Assert.assertTrue(status);

			appBuilderPage = createAppPage.clickAppName(appName).clickEditAppButton();
			appBuilderPage.verifyScreenLabels("6.1.", parameters.get("first_screen_name"), testContext,
					"Verify First screen labels", parameters.get("first_screen_fields").split(";"),
					parameters.get("first_screen_labels").split(";"));

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickCopyScreenIcon();
			String copiedScreenName = parameters.get("first_screen_name") + "- copy";
			appBuilderPage.isScreenDisplayed(copiedScreenName);
			reportLog(status, testContext.getName(), "Verify copied screen created.", "7.0",
					copiedScreenName + " : screen created successfully.");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.copyScreenField(parameters.get("copy_field"));
			int integer = appBuilderPage.getFieldCount(parameters.get("copy_field"));

			// status = (integer == 2); //sometimes prints 4, flaky

			status = (integer > 1);
			// System.out.println(integer); // test to ensure it equates to 2 due to flaky
			// nature
			// status = (appBuilderPage.getFieldCount(parameters.get("copy_field")) == 2);
			reportLog(status, testContext.getName(), "Verify copied field created.", "8.0",
					parameters.get("copy_field") + " : new field created successfully.");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.clickSaveButton();
			createAppPage = appBuilderPage.clickCloseButton();
			createAppPage.clickAppName(appName).destroyApp();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/**
	 * TC10582 Create App with selecting existing Template with publishing the App
	 **/

	@Test
	@Parameters({ "testdescription" })
	public void createAppexistingTemplate(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createapp = new CreateAppPage(driver);
			AppBuilderPage appBuilder = new AppBuilderPage(driver);
			EditAppPage editapp = new EditAppPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			String appName = parameters.get("app_name");
			dashboardPage.clickApp();
			createapp.clickAppCreateButton();
			boolean status = appBuilder.createAppWithTemplate();
			reportLog(status, testContext.getName(), "User clicked on Create App & then Start button", "1.2",
					"App builder should open");
			org.testng.Assert.assertTrue(status, "App builder should open");

			// boolean appName1
			// =appBuilder.verifyAppNameText(parameters.get("app_name"));
			// reportLog(appName1, testContext.getName(), "Verify app name after
			// login",
			// "1.3." + "","App name is verified");
			// org.testng.Assert.assertTrue(appName1, "App name is verfied");

			appName = parameters.get("app_name") + randomAlphaNumeric(10);
			appBuilder.clearAppName();
			appBuilder.enterAppName(appName);
			appBuilder.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));

			// boolean appName1
			// =appBuilder.verifyAppNameText(parameters.get("app_name"));
			// reportLog(appName1, testContext.getName(), "Verify app name after
			// login", "1.3." + "","App name is verified");
			// org.testng.Assert.assertTrue(appName1, "App name is verfied");

			boolean screenName1 = appBuilder.verifyFirstScreenName(parameters.get("ScreenName1"));
			reportLog(screenName1, testContext.getName(), "Verify first Screen Name", "2.1." + "",
					"Screen with name 'Customer Contact Information' verified");

			org.testng.Assert.assertTrue(screenName1, "First screen name verfied");

			boolean screenName2 = appBuilder.verifySecondScreenName(parameters.get("ScreenName2"));
			reportLog(screenName2, testContext.getName(), "Verify second Screen Name", "2.2." + "",
					"Screen with name 'Waiver Language' verified");
			org.testng.Assert.assertTrue(screenName2, "First screen name verfied");

			boolean screenName3 = appBuilder.verifyThirdScreenName(parameters.get("ScreenName3"));
			reportLog(screenName3, testContext.getName(), "Verify third Screen Name", "2.3." + "",
					"Screen with name 'Client Signature' verified");
			org.testng.Assert.assertTrue(screenName3, "First screen name verfied");

			appBuilder.verifyScreenLabels("3.1.", parameters.get("first_screen_name"), testContext,
					"Verify 'Customer Contact Information' screen labels",
					parameters.get("first_screen_fields").split(";"), parameters.get("first_screen_labels").split(";"));
			appBuilder.verifyScreenLabels("4.1.", parameters.get("second_screen_name"), testContext,
					"Verify 'Waiver Language' screen labels", parameters.get("second_screen_fields").split(";"),
					parameters.get("second_screen_labels").split(";"));
			appBuilder.verifyScreenLabels("5.1.", parameters.get("third_screen_name"), testContext,
					"Verify 'Client Signature' screen labels", parameters.get("third_screen_fields").split(";"),
					parameters.get("third_screen_labels").split(";"));

			appBuilder.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			publishAppPage.verifyCreatedApp(testContext, parameters.get("name"), parameters.get("username"), appName,
					parameters.get("app_status"), "5.1", "5.2", "5.3");
			appBuilder.clickCloseButton();
			createapp.verifyPublishedApp(testContext, parameters.get("name"), parameters.get("username"), appName,
					parameters.get("app_status"), "6.1.", "7.1");
			createapp.clickAppName(appName);
			editapp.destroyApp();
			customWait(5);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/**
	 * TC10941 Verify Default screen when user create new app when some app with *
	 * version 2 already exist in the account
	 **/

	@Test
	@Parameters({ "testdescription" })
	public void createAppToVerifyDefaultScreen(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {

			/** Login to verify Default Screen exist **/

			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createapp = new CreateAppPage(driver);
			LoginPage login = new LoginPage(driver);

			CreateAppPage createappPage = dashboardPage.clickApp();

			boolean pageNameVerfied = createapp.verifyAppPageText(parameters.get("page_name"));
			reportLog(pageNameVerfied, testContext.getName(), "Verify page name after login", "1.2." + "",
					"Page name 'Apps' verified");
			org.testng.Assert.assertTrue(pageNameVerfied, "Page name 'Apps' verfied");

			boolean appStatusVersion = createappPage.verifyVersion(parameters.get("app_version2"));
			reportLog(appStatusVersion, testContext.getName(), "Verify version of the App ", "2.1",
					"Version '2' of the app is verfied");
			org.testng.Assert.assertTrue(appStatusVersion, "Version '2' of the app is verfied");

			AppBuilderPage appBuilder = createappPage.clickAppCreateButton();
			boolean isAppBuilderOpen = appBuilder.createDefaultApp();
			reportLog(isAppBuilderOpen, testContext.getName(), "User has clicked on Create App & then Start button",
					"2.2", "App builder is open");
			org.testng.Assert.assertTrue(isAppBuilderOpen, "App builder is open");

			boolean appStatusVersion2 = appBuilder.verifyDefaultScreen();
			reportLog(appStatusVersion2, testContext.getName(),
					"Account have one app with version 2.Verify default screen in app outline ", "3.1",
					"One default normal screen is created  itself in App outline");
			org.testng.Assert.assertTrue(appStatusVersion2, "Default screen is created  itself ");

			boolean pageName = appBuilder.clickCloseWithoutSave(parameters.get("page_name"));
			reportLog(pageName, testContext.getName(), "User Click on close button in app builder without save", "4.1",
					"User re-direct to App Page");
			org.testng.Assert.assertTrue(pageName, "User re-direct to App Page");

			createapp.logout();
			boolean logoutMessage = createappPage.afterlogout(parameters.get("logOut_msg"));
			reportLog(logoutMessage, testContext.getName(), "Click on logout button in create app page", "5.1",
					"User Logout from the Application");
			org.testng.Assert.assertTrue(logoutMessage, "User Logout from the Application ");

			/** Login again to verify Default Screen not exist **/

			login.typeusername(parameters.get("username1"));
			login.typepassword(parameters.get("password1"));
			login.Clickonloginbutton();

			boolean appNameText_2 = createapp.afterlogin(parameters.get("page_name_1"));
			reportLog(appNameText_2, testContext.getName(), "Login as the company admin:", "6.1.",
					"SignIn successfull");
			org.testng.Assert.assertTrue(appNameText_2, "SignIn successfull");
			dashboardPage.clickApp();

			boolean pageNameVerfied_1 = createapp.verifyAppPageText(parameters.get("page_name"));
			reportLog(pageNameVerfied_1, testContext.getName(), "Verify page name after login", "6.2." + "",
					"Page name 'Apps' verified");
			org.testng.Assert.assertTrue(pageNameVerfied_1, "Page name 'Apps' verfied");

			dashboardPage.clickApp();
			boolean appStatusVersion1 = createappPage.verifyVersion(parameters.get("app_version1"));
			reportLog(appStatusVersion1, testContext.getName(), "Verify version of the App ", "7.1",
					"Version '1' of the app is verfied");
			org.testng.Assert.assertTrue(appStatusVersion1, "Version '1' of the app is verfied");

			createappPage.clickAppCreateButton();
			boolean appNameText1 = appBuilder.createDefaultApp();
			reportLog(appNameText1, testContext.getName(), "User has clicked on Create App & then Start button", "7.2",
					"App builder is open");
			org.testng.Assert.assertTrue(appNameText1, "App builder is open");

			boolean defaultScreen = appBuilder.verifyDefaultScreen();
			reportLog(defaultScreen, testContext.getName(),
					"Account have one app with version less than 2. Verify default screen in app outline  ", "8.1",
					"Default Screen should be created in app outline");
			org.testng.Assert.assertTrue(defaultScreen);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/**
	 * TC10430
	 * 
	 * @param testDescription
	 * @param testContext
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	@Parameters({ "testdescription" })
	public void createAppWithAdvancedScreen(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "1.2");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			boolean status = appBuilderPage.isLabelDisplayed("New name");
			reportLog(status, testContext.getName(), "User created first screen with labels", "2.0",
					"First screen created successfully");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLoopIcon();
			appBuilderPage.clickUseExistingField();
			appBuilderPage.clickDoneButton();
			status = appBuilderPage.isLabelDisplayed("New name");
			reportLog(status, testContext.getName(), "User created parent screen with loop", "2.1",
					"Parent screen created successfully");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.clickScreen(parameters.get("second_parent_screen_name"));
			appBuilderPage.clickLoopIcon();
			appBuilderPage.enterFieldName(parameters.get("first_parent_screen_name"));
			appBuilderPage.clickDoneButton();
			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));

			status = appBuilderPage.isLabelDisplayed("Advanced List");
			reportLog(status, testContext.getName(), "User created second screen with loop", "2.2",
					"Second Parent screen created successfully");
			org.testng.Assert.assertTrue(status);

			/*
			 * boolean status =
			 * appBuilder.getFtuxMessage().equals(parameters.get("save_message") );
			 * reportLog(status, testContext.getName()
			 * ,"User clicked on Save Button after entering label names", "2.1",
			 * "Verify Save successful appears" ); org.testng.Assert.assertTrue(
			 * status,"Verify Save successful appear");
			 */

			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Succesfull message",
					"2.3", "Verify Save successful appears: IS NOT AUTOMATED");
			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();

			appBuilderPage = publishAppPage.verifyCreatedApp(testContext, parameters.get("name"),
					parameters.get("username"), appName, parameters.get("app_status"), "3.1", "3.2", "3.3");
			CreateAppPage createAppPage = appBuilderPage.clickCloseButton();
			createAppPage.verifyPublishedApp(testContext, parameters.get("name"), parameters.get("username"), appName,
					parameters.get("app_status"), "4.1.", "5.1");
			appBuilderPage = createAppPage.editApp(appName);

			appBuilderPage = appBuilderPage.verifyScreenLabels("6.1.", parameters.get("first_parent_screen_name"),
					testContext, "Verify First Parent screen labels",
					parameters.get("first_parent_screen_fields").split(";"),
					parameters.get("first_parent_screen_labels").split(";"));
			appBuilderPage = appBuilderPage.verifyScreenLabels("6.2.", parameters.get("second_parent_screen_name"),
					testContext, "Verify Second Parent labels",
					parameters.get("second_parent_screen_fields").split(";"),
					parameters.get("second_parent_screen_labels").split(";"));
			appBuilderPage.verifyScreenLabels("6.3.", parameters.get("first_screen_name"), testContext,
					"Verify Child screen labels", parameters.get("loop_screen_fields").split(";"),
					parameters.get("loop_screen_labels").split(";"));

			appBuilderPage.clickCloseButton().clickAppName(appName).destroyApp();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void createAppWithFieldAndScreenConditions(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "1.2");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			boolean status = appBuilderPage.isLabelDisplayed("First name");
			reportLog(status, testContext.getName(), "User created first screen with labels", "2.0",
					"First screen created successfully");
			org.testng.Assert.assertTrue(status);

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("second_screen_place_holder"),
					parameters.get("second_screen_name"), parameters.get("second_screen_labels").split(";"),
					parameters.get("second_screen_fields").split(";"));
			status = appBuilderPage.isLabelDisplayed("First Name");
			reportLog(status, testContext.getName(), "User created second screen with labels", "2.1",
					"Second screen created successfully");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));

			int screenIndex = 1;
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLabel(parameters.get("field_condition_field")).clickFieldConditions()
					.clickNewFieldConditionButton().selectConditionScreen(screenIndex)
					.selectConditionField(parameters.get("conditional_field"))
					.selectCondition(parameters.get("condition")).enterConditionValue(parameters.get("condition_value"))
					.clickSaveButton();
			status = appBuilderPage.getSelectedConditionScreen().contains(parameters.get("first_screen_name"));
			reportLog(status, testContext.getName(), "Applying Field Condition on First screen", "2.2",
					"Field condition applied successfully");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.clickScreen(parameters.get("second_screen_name")).clickSettingsButton()
					.clickScreenConditions().clickNewScreenConditionButton().selectConditionScreen(1)
					.selectScreenConditionField(parameters.get("screen_conditional_field"))
					.selectCondition(parameters.get("screen_condition")).enterConditionValue("15").clickSaveButton();
			status = appBuilderPage.getSelectedConditionScreen().contains(parameters.get("first_screen_name"));
			reportLog(status, testContext.getName(), "Applying Screen Condition on second screen", "2.3",
					"Screen condition applied successfully");
			org.testng.Assert.assertTrue(status);

			/*
			 * boolean status =
			 * appBuilder.getFtuxMessage().equals(parameters.get("save_message") );
			 * reportLog(status, testContext.getName()
			 * ,"User clicked on Save Button after entering label names", "2.1",
			 * "Verify Save successful appears" ); org.testng.Assert.assertTrue(
			 * status,"Verify Save successful appear");
			 */

			reportLog(true, testContext.getName(), "User clicked on Save Button after entering label names", "2.4",
					"Verify Save successful appears: IS NOT AUTOMATED");

			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			appBuilderPage = publishAppPage.verifyCreatedApp(testContext, parameters.get("name"),
					parameters.get("username"), appName, parameters.get("app_status"), "3.1", "3.2", "3.3");
			CreateAppPage createAppPage = appBuilderPage.clickCloseButton();
			createAppPage.verifyPublishedApp(testContext, parameters.get("name"), parameters.get("username"), appName,
					parameters.get("app_status"), "4.1.", "5.1");

			appBuilderPage = createAppPage.editApp(appName);

			appBuilderPage.clickScreen(parameters.get("first_screen_name"))
					.clickLabel(parameters.get("field_condition_field"));
			appBuilderPage = appBuilderPage.validateConditions(parameters.get("first_screen_name"),
					parameters.get("conditional_field"), parameters.get("condition"), parameters.get("condition_value"),
					testContext, "6.1.",
					"User Clicked on " + parameters.get("first_screen_name") + " after clicking on Edit App");

			appBuilderPage.clickScreen(parameters.get("second_screen_name")).clickSettingsButton();

			appBuilderPage = appBuilderPage.validateConditions(parameters.get("first_screen_name"),
					parameters.get("screen_conditional_field"), parameters.get("screen_condition"), "15", testContext,
					"6.2.", "User Clicked on " + parameters.get("second_screen_name") + " after clicking on Edit App");

			appBuilderPage.clickCloseButton().clickAppName(appName).destroyApp();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void createAppWithLoopScreen(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);

			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "1.2");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			boolean status = appBuilderPage.isLabelDisplayed("First name");
			reportLog(status, testContext.getName(), "User created first screen with labels", "2.0",
					"First screen created successfully");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLoopIcon();
			appBuilderPage.clickUseExistingField();
			appBuilderPage.clickDoneButton();

			status = appBuilderPage.isLabelDisplayed("First name");
			reportLog(status, testContext.getName(), "User created parent screen with loop", "2.1",
					"Parent screen created successfully");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));

			/*
			 * status = appBuilder.getFtuxMessage().equals(parameters.get("save_message") );
			 * reportLog(status, testContext.getName()
			 * ,"User clicked on Save Button after entering label names", "2.1",
			 * "Verify Save successful appears" ); org.testng.Assert.assertTrue(
			 * status,"Verify Save successful appear");
			 */

			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Succesfull message",
					"2.2", "Verify Save successful appears: IS NOT AUTOMATED");
			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();

			appBuilderPage = publishAppPage.verifyCreatedApp(testContext, parameters.get("name"),
					parameters.get("username"), appName, parameters.get("app_status"), "3.1", "3.2", "3.3");

			CreateAppPage createAppPage = appBuilderPage.clickCloseButton();
			createAppPage.verifyPublishedApp(testContext, parameters.get("name"), parameters.get("username"), appName,
					parameters.get("app_status"), "4.1.", "5.1");

			appBuilderPage = createAppPage.editApp(appName);
			appBuilderPage = appBuilderPage.verifyScreenLabels("6.1.", parameters.get("parent_screen_name"),
					testContext, "Verify Parent screen labels", parameters.get("parent_screen_fields").split(";"),
					parameters.get("parent_screen_labels").split(";"));

			appBuilderPage = appBuilderPage.verifyScreenLabels("6.2.", parameters.get("first_screen_name"), testContext,
					"Verify Child screen labels", parameters.get("loop_screen_fields").split(";"),
					parameters.get("loop_screen_labels").split(";"));
			appBuilderPage.clickCloseButton().clickAppName(appName).destroyApp();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// TC10428
	@Test
	@Parameters({ "testdescription" })
	public void createAppWithMultipleScreens(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);

			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "1.2");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			boolean status = appBuilderPage.isLabelDisplayed("First name");
			reportLog(status, testContext.getName(), "User created first screen with labels", "2.0",
					"First screen created successfully");
			org.testng.Assert.assertTrue(status);

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("second_screen_place_holder"),
					parameters.get("second_screen_name"), parameters.get("second_screen_labels").split(";"),
					parameters.get("second_screen_fields").split(";"));
			status = appBuilderPage.isLabelDisplayed("First Name");
			reportLog(status, testContext.getName(), "User created second screen with labels", "2.1",
					"Second screen created successfully");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));

			/*
			 * boolean status =
			 * appBuilder.getFtuxMessage().equals(parameters.get("save_message") );
			 * reportLog(status, testContext.getName()
			 * ,"User clicked on Save Button after entering label names", "2.1",
			 * "Verify Save successful appears" ); org.testng.Assert.assertTrue(
			 * status,"Verify Save successful appear");
			 */
			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Succesfull message",
					"2.2", "Verify Save successful appears: IS NOT AUTOMATED");

			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			appBuilderPage = publishAppPage.verifyCreatedApp(testContext, parameters.get("name"),
					parameters.get("username"), appName, parameters.get("app_status"), "3.1", "3.2", "3.3");

			CreateAppPage createAppPage = appBuilderPage.clickCloseButton();
			createAppPage.verifyPublishedApp(testContext, parameters.get("name"), parameters.get("username"), appName,
					parameters.get("app_status"), "4.1.", "5.1");
			appBuilderPage = createAppPage.editApp(appName);
			appBuilderPage.verifyScreenLabels("6.1.", parameters.get("first_screen_name"), testContext,
					"Verify First screen labels", parameters.get("first_screen_fields").split(";"),
					parameters.get("first_screen_labels").split(";"));
			appBuilderPage.verifyScreenLabels("6.2.", parameters.get("second_screen_name"), testContext,
					"Verify Second screen labels", parameters.get("second_screen_fields").split(";"),
					parameters.get("second_screen_labels").split(";"));
			appBuilderPage.clickCloseButton().clickAppName(appName).destroyApp();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/*
	 * kp ID: TC7711 Name:Copy App
	 */

	@Test
	@Parameters({ "testdescription" })
	public void createAppWithReferenceData(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);

			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "1.2");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));
			String workingDir = System.getProperty("user.dir");
			String filepath = workingDir + parameters.get("reference_file_path");
			String referenceDataName = parameters.get("reference_data_name") + randomAlphaNumeric(10);
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.addReferenceDataFromCSV(parameters.get("reference_data_field"), referenceDataName,
					parameters.get("reference_data_description"), filepath);

			boolean status = appBuilderPage.isIconDisplayed(parameters.get("reference_data_field"));
			reportLog(status, testContext.getName(),
					"User created first screen and applied reference data on first screen label city", "2.1",
					"Applied reference data on city label");
			org.testng.Assert.assertTrue(status);
			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Succesfull message",
					"2.2", "Verify Save successful appears: IS NOT AUTOMATED");

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("reference_field2"));
			appBuilderPage.addReferenceData(referenceDataName, parameters.get("reference_value2"), 1, null);

			status = appBuilderPage.isIconDisplayed(parameters.get("reference_data_field"));
			reportLog(status, testContext.getName(),
					"User applied reference data on first screen label Data on the basis ....", "3.1",
					"Applied reference data on Data on the basis of the Selection of City label");
			org.testng.Assert.assertTrue(status);

			status = appBuilderPage.isReferenceFieldDisplayed(parameters.get("reference_data_field"));
			reportLog(status, testContext.getName(), "Verify City displayed on reference field", "3.2",
					"City displayed on reference field");
			org.testng.Assert.assertTrue(status);

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("second_screen_place_holder"),
					parameters.get("second_screen_name"), parameters.get("second_screen_labels").split(";"),
					parameters.get("second_screen_fields").split(";"));
			appBuilderPage.clickSaveButton();
			// Kp add
			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("second_screen_labels"));
			// end KP add
			appBuilderPage.addReferenceData(referenceDataName, parameters.get("reference_value3"), 1,
					parameters.get("reference_data_field"));

			status = appBuilderPage.isIconDisplayed(parameters.get("second_reference_data_field"));
			reportLog(status, testContext.getName(),
					"User applied reference data on second screen label Data on the basis ....", "4.0",
					"Applied reference data on Data on the basis of the Selection of City label...");
			org.testng.Assert.assertTrue(status);

			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			appBuilderPage = publishAppPage.verifyCreatedApp(testContext, parameters.get("name"),
					parameters.get("username"), appName, parameters.get("app_status"), "4.1", "4.2", "4.3");
			CreateAppPage createAppPage = appBuilderPage.clickCloseButton();

			createAppPage.verifyPublishedApp(testContext, parameters.get("name"), parameters.get("username"), appName,
					parameters.get("app_status"), "5.1.", "6.1");
			appBuilderPage = createAppPage.editApp(appName);
			appBuilderPage.verifyScreenLabels("7.1.", parameters.get("first_screen_name"), testContext,
					"Verify First screen labels", parameters.get("first_screen_fields").split(";"),
					parameters.get("first_screen_labels").split(";"));
			appBuilderPage.verifyScreenLabels("7.2.", parameters.get("second_screen_name"), testContext,
					"Verify Second screen labels", parameters.get("second_screen_fields").split(";"),
					parameters.get("second_screen_labels").split(";"));

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("reference_data_field"));
			appBuilderPage.verifyReferenceData(referenceDataName, parameters.get("reference_value1"),
					parameters.get("screen_value1"), null, testContext, "8.1.", "Clicked on first screen city label");

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("reference_field2"));
			appBuilderPage.verifyReferenceData(referenceDataName, parameters.get("reference_value2"),
					parameters.get("first_screen_name"), parameters.get("reference_data_field"), testContext, "8.2.",
					"Clicked on first screen Data on the basis .. label");

			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("second_reference_data_field"));
			appBuilderPage.verifyReferenceData(referenceDataName, parameters.get("reference_value3"),
					parameters.get("first_screen_name"), parameters.get("reference_data_field"), testContext, "8.3.",
					"Clicked on second screen Data on the basis .. label");

			appBuilderPage.clickSaveButton();
			appBuilderPage.clickCloseButton().clickAppName(appName).deleteApp();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/*
	 * kp ID: TC7487 Name:Company Reporter, Company User
	 */

	/**
	 * kp Test case ID:TC3891 Summary:delete pending version
	 **/
	@Test
	@Parameters({ "testdescription" })

	public void deletePendingVersion(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {

			/** Login and Add status **/

			performLogin(1, parameters.get("username1"), parameters.get("password1"),
					"Login as the sales user plus " + parameters.get("username1"), testContext, testDescription);
			CreateAppPage createapp = new CreateAppPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			AppBuilderPage appBuilder = new AppBuilderPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);

			LoginPage login = new LoginPage(driver);
			String appNameRandom1 = parameters.get("appName1") + randomAlphaNumeric(10);
			String deleteAppMsg = "A pending version of your '" + appNameRandom1 + "' app has been removed.";
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilder.createApp(appNameRandom1, testContext, "1.1");
			appBuilder.clickSaveButton(appNameRandom1, parameters.get("username1"), parameters.get("password1"));

			appBuilder.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			CreateAppPage createAppPage = appBuilderPage.clickCloseButton();
			appBuilderPage = createAppPage.editApp(appNameRandom1);

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilder.clickSaveButton();
			appBuilderPage.clickCloseButton();
			// customWait(5);

			appBuilder.verifyAppExistInPage(appNameRandom1);

			boolean app_status = appBuilderPage.verifyAppStatus(appNameRandom1, parameters.get("app_status"));
			reportLog(app_status, testContext.getName(), "Verify App Status ", "1.2.",
					"Verify the status of our newly created app is now 'Pending' from 'Published' Is Verfied");
			org.testng.Assert.assertTrue(app_status, "Second Column 'version' is verfied");

			createapp.logout();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbutton();

			appBuilder.verifyAppExistInPage(appNameRandom1);

			boolean pop_up_msg = appBuilderPage.verifyPopUpMsg(appNameRandom1, parameters.get("delete_msg"));
			reportLog(pop_up_msg, testContext.getName(), "Verify message in Pop Up ", "2.1.",
					"The following pop up message appears: 'Delete version 2 of this app?' ");
			org.testng.Assert.assertTrue(pop_up_msg,
					"The following pop up message appears: 'Delete version 2 of this app?' ");

			appBuilder.verifyAppExistInPage(appNameRandom1);

			boolean delete_app_msg = appBuilderPage.deletePendingApp(appNameRandom1, deleteAppMsg);
			reportLog(delete_app_msg, testContext.getName(), "Verify delete pending App message ", "3.1.",
					"A pending version of your 'Pending App' app has been removed.");
			org.testng.Assert.assertTrue(delete_app_msg,
					"A pending version of your 'Pending App' app has been removed.");
			// customWait(6);

			String appNameRandom2 = parameters.get("appName2") + randomAlphaNumeric(10);
			createapp.clickAppCreateButton();
			appBuilder.createApp(appNameRandom2, testContext, "4.1");
			appBuilder.clickSaveButton(appNameRandom2, parameters.get("username2"), parameters.get("password2"));
			appBuilder.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilderPage.clickCloseButton();
			createAppPage.editApp(appNameRandom2);

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilder.clickSaveButton();
			appBuilderPage.clickCloseButton();
			// customWait(5);

			createAppPage.isAppNameDisplayed(appNameRandom2);
			boolean app_status_App2 = appBuilderPage.verifyAppStatus(appNameRandom2, parameters.get("app_status"));
			reportLog(app_status_App2, testContext.getName(), "Verify App Status ", "4.2",
					"Verify the status of our newly created app is now 'Pending' from 'Published' Is Verfied");
			org.testng.Assert.assertTrue(app_status_App2,
					"Verify the status of our newly created app is now 'Pending' from 'Published'");

			createAppPage.isAppNameDisplayed(appNameRandom2);
			boolean app_version_App2 = appBuilderPage.verifyAppVersion(appNameRandom2, parameters.get("app_version"));
			reportLog(app_version_App2, testContext.getName(), "Verify version of app after editing publish App ",
					"4.3", "Version of the app set to 'version 2' Is Verfied");
			org.testng.Assert.assertTrue(app_version_App2, "Version of the app set to 'version 2'");

			createAppPage.isAppNameDisplayed(appNameRandom2);
			appBuilderPage.deletePendingApp(appNameRandom2);

			createAppPage.isAppNameDisplayed(appNameRandom1);
			boolean delete_pending_ver = appBuilderPage.verfiyVersionAfterDelete(appNameRandom1,
					parameters.get("app_version_updated"));
			reportLog(delete_pending_ver, testContext.getName(), "Verify app version reverted back to Version 1 ",
					"5.1.", "App version reverted back to Version 1 is Verfied");
			org.testng.Assert.assertTrue(delete_pending_ver, "App version reverted back to Version 1 is Verfied");

			createAppPage.isAppNameDisplayed(appNameRandom1);
			boolean app_status_updated = appBuilderPage.verfiyAppStatusAfterDelete(appNameRandom1,
					parameters.get("app_status_updated"));
			reportLog(app_status_updated, testContext.getName(), "Verify app status reverted back to Publish ", "5.2.",
					"App status reverted back to Publish from pending");
			org.testng.Assert.assertTrue(app_status_updated, "App status reverted back to Publish from pending");

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void deleteScreenAndFieldsTest(String testDescription, String rallyLink, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription,
					rallyLink);
			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = dashboardPage.clickApp();
			AppBuilderPage appBuilderPage = createAppPage.clickAppCreateButton();
			String appName = randomAlphaNumeric(10);
			appBuilderPage.clickBlankTemplate();
			appBuilderPage.clickStartButton();
			boolean status = appBuilderPage.isPublishToDeviceButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify App builder should open.", "1.2",
					"App builder opened successfully.");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.enterAppName(appName);
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("second_screen_place_holder"),
					parameters.get("second_screen_name"), parameters.get("second_screen_labels").split(";"),
					parameters.get("second_screen_fields").split(";"));
			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));
			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Succesfull message",
					"2.0", "Verify Save successful appears: IS NOT AUTOMATED");

			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			status = publishAppPage.isPublishButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify User re-direct to Publish App Page.", "3.0",
					"User re-directed to Publish App Page successfully.");
			org.testng.Assert.assertTrue(status);

			appBuilderPage = publishAppPage.verifyCreatedApp(testContext, parameters.get("name"),
					parameters.get("username"), appName, parameters.get("app_status"), "3.1", "3.2", "3.3");
			createAppPage = appBuilderPage.clickCloseButton();

			createAppPage.verifyPublishedApp(testContext, parameters.get("name"), parameters.get("username"), appName,
					parameters.get("app_status"), "4.1.", "5.1");

			appBuilderPage = createAppPage.editApp(appName);
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickDeleteIcon();
			appBuilderPage.clickDeleteScreenButton();
			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			appBuilderPage.deleteScreenField(parameters.get("field_delete1"));
			appBuilderPage.clickDeleteFieldButton();
			appBuilderPage.deleteScreenField(parameters.get("field_delete2"));
			appBuilderPage.clickDeleteFieldButton();
			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));

			publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			appBuilderPage = publishAppPage.clickPublishButton();
			createAppPage = appBuilderPage.clickCloseButton();
			createAppPage.verifyPublishedApp(testContext, "Version 2", appName, parameters.get("app_status"), "6.");

			appBuilderPage = createAppPage.clickAppName(appName).clickEditAppButton();
			status = appBuilderPage.isPublishToDeviceButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify App builder should open.", "7.0",
					"App builder opened successfully.");
			org.testng.Assert.assertTrue(status);

			status = appBuilderPage.isScreenDisplayed(parameters.get("first_screen_name"));
			reportLog(!status, testContext.getName(), "Verify First Screen App fields screen is deleted.", "8.1",
					"First Screen App fields screen is deleted.");
			org.testng.Assert.assertFalse(status);

			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			status = appBuilderPage.isFieldDisplayed(parameters.get("field_delete1"));
			reportLog(!status, testContext.getName(), "Verify First Name field is deleted from second screen.", "8.1",
					"First Name field is deleted from second screen.");
			org.testng.Assert.assertFalse(status);

			status = appBuilderPage.isFieldDisplayed(parameters.get("field_delete2"));
			reportLog(!status, testContext.getName(), "Verify Drawing field is deleted from second screen.", "8.2",
					"Drawing field is deleted from second screen.");
			org.testng.Assert.assertFalse(status);

			createAppPage = appBuilderPage.clickCloseButton();
			createAppPage.clickAppName(appName).destroyApp();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// TC10535
	@Test
	@Parameters({ "testdescription" })
	public void editPublishAppWithCreatingNewVersion(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			AppBuilderPage appBuilder = dashboardPage.clickApp().clickAppCreateButton();
			appBuilder.createApp(appName, testContext, "1.2");

			appBuilder = appBuilder.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			boolean status = appBuilder.isLabelDisplayed("First name");
			reportLog(status, testContext.getName(), "User created first screen with labels", "2.0",
					"First screen created successfully");
			org.testng.Assert.assertTrue(status);

			appBuilder = appBuilder.addScreenAndAddLabel(parameters.get("second_screen_place_holder"),
					parameters.get("second_screen_name"), parameters.get("second_screen_labels").split(";"),
					parameters.get("second_screen_fields").split(";"));
			status = appBuilder.isLabelDisplayed("First Name");
			reportLog(status, testContext.getName(), "User created second screen with labels", "2.1",
					"Second screen created successfully");
			org.testng.Assert.assertTrue(status);

			appBuilder.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));

			reportLog(true, testContext.getName(), "User clicked on Save Button after entering label names", "2.2",
					"Verify Save successful appears: IS NOT AUTOMATED");

			PublishAppPage publishAppPage = appBuilder.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			appBuilder = publishAppPage.verifyCreatedApp(testContext, parameters.get("name"),
					parameters.get("username"), appName, parameters.get("app_status"), "3.1", "3.2", "3.3");
			CreateAppPage createAppPage = appBuilder.clickCloseButton();
			createAppPage.verifyPublishedApp(testContext, parameters.get("name"), parameters.get("username"), appName,
					parameters.get("app_status"), "4.1.", "4.2");

			boolean status1 = createAppPage.isAppNameDisplayed(appName);
			reportLog(status1, testContext.getName(), "App is searched", "5.1", "App is searched");
			org.testng.Assert.assertTrue(status1, "App gets searched");

			EditAppPage editAppPage = createAppPage.clickAppName(appName);
			String appVersion = editAppPage.getVersion();
			status = appVersion.contains(parameters.get("app_version"));
			reportLog(status, testContext.getName(), "Verifying version number", "5.2",
					"Version number displayed correctly");
			org.testng.Assert.assertTrue(status, "Version number displayed correctly");

			appBuilder = editAppPage.clickEditAppButton();
			status = appBuilder.isAppBuilderEditMode(parameters.get("app_builder_edit_mode_indicator"));
			reportLog(status, testContext.getName(), "Verify App Builder opened in edit mode", "6.1",
					"App builder opened in edit mode");
			org.testng.Assert.assertTrue(status, "App builder opened in edit more");

			appBuilder.verifyScreenLabels("6.2.", parameters.get("first_screen_name"), testContext,
					"Verify First screen labels", parameters.get("first_screen_fields").split(";"),
					parameters.get("first_screen_labels").split(";"));
			appBuilder.verifyScreenLabels("6.3.", parameters.get("second_screen_name"), testContext,
					"Verify Second screen labels", parameters.get("second_screen_fields").split(";"),
					parameters.get("second_screen_labels").split(";"));

			// Add Some more fields after Edit
			appBuilder.addLabelToScreen(parameters.get("first_screen_name"),
					parameters.get("extra_first_screen_labels").split(";"),
					parameters.get("extra_first_screen_fields").split(";"));
			appBuilder.addLabelToScreen(parameters.get("second_screen_name"),
					parameters.get("extra_second_screen_labels").split(";"),
					parameters.get("extra_second_screen_fields").split(";"));

			// Verify Newly added fields
			appBuilder.verifyupdatedScreenLabels("7.1.", parameters.get("first_screen_name"), testContext,
					"Verify First screen labels", parameters.get("extra_first_screen_fields").split(";"),
					parameters.get("extra_first_screen_labels").split(";"));
			appBuilder.verifyupdatedScreenLabels("7.2.", parameters.get("second_screen_name"), testContext,
					"Verify Second screen labels", parameters.get("extra_second_screen_fields").split(";"),
					parameters.get("extra_second_screen_labels").split(";"));

			publishAppPage = appBuilder.clickPublishToDeviceButton();
			publishAppPage.clickPublishButton();

			appBuilder = publishAppPage.verifyCreatedApp(testContext, parameters.get("name"),
					parameters.get("username"), appName, parameters.get("app_status"), "7.3", "7.4", "7.5");
			createAppPage = appBuilder.clickCloseButton();
			status = createAppPage.isAppNameDisplayed(appName);
			reportLog(status, testContext.getName(), "Verifying app displayed on Apps Page", "8.1",
					"App displayed on Apps Page");
			org.testng.Assert.assertTrue(status, "App displayed on App Page");

			editAppPage = createAppPage.clickAppName(appName);
			appVersion = editAppPage.getVersion();
			status = appVersion.equals(parameters.get("second_app_version"));
			reportLog(status, testContext.getName(), "Verifying version number", "8.2",
					"Version number displayed correctly");
			org.testng.Assert.assertTrue(status, "Version number displayed correctly");

			editAppPage.destroyApp();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void fieldSettingsTest(String testDescription, String rallyLink, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		// try {
		performLogin(1, parameters.get("username"), parameters.get("password"),
				"Login as: " + parameters.get("username"), testContext, testDescription, rallyLink);
		DashboardPage dashboardPage = new DashboardPage(driver);
		CreateAppPage createAppPage = dashboardPage.clickApp();
		AppBuilderPage appBuilderPage = createAppPage.clickAppCreateButton();
		String appName = randomAlphaNumeric(10);
		appBuilderPage.clickBlankTemplate();
		appBuilderPage.clickStartButton();
		boolean status = appBuilderPage.isPublishToDeviceButtonDisplayed();
		reportLog(status, testContext.getName(), "Verify App builder should open.", "1.2",
				"App builder opened successfully.");
		org.testng.Assert.assertTrue(status);

		appBuilderPage.enterAppName(appName);
		appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
				parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
				parameters.get("first_screen_fields").split(";"));
		appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));
		appBuilderPage.clickLabelField(parameters.get("first_screen_labels").split(";")[0]);
		// appBuilderPage.clickLabel(parameters.get("first_screen_labels").split(";")[0]);
		status = appBuilderPage.isFieldSettingsDisplayed();
		reportLog(status, testContext.getName(), "Verify field settings displayed on clicking first name.", "2.1",
				"Field settings displayed on clicking first name.");
		org.testng.Assert.assertTrue(status);
		appBuilderPage.clickFieldsButton();

		appBuilderPage.clickLabel(parameters.get("first_screen_labels").split(";")[1]);
		status = appBuilderPage.isFieldSettingsDisplayed();
		reportLog(status, testContext.getName(), "Verify field settings displayed on clicking number field.", "2.2",
				"Field settings displayed on clicking number field.");
		org.testng.Assert.assertTrue(status);
		appBuilderPage.clickFieldsButton();

		appBuilderPage.clickScreen(parameters.get("first_screen_name"));
		appBuilderPage.clickLabel("First Name");
		appBuilderPage.selectFieldStyle("Number");
		appBuilderPage.enterDefaultValue("1234");
		appBuilderPage.clickRequiredCheckBox();
		appBuilderPage.clickMoreLink();
		appBuilderPage.clickPageBreakAfterCheckBox();
		appBuilderPage.enterMaxCharacterLength("4");
		appBuilderPage.enterFieldMask("0aA?");
		appBuilderPage.selectPdfVisible("Never");
		appBuilderPage.selectWebVisible("Never");
		appBuilderPage.clickEmailSubjectCheckBox();
		appBuilderPage.clickPDFFileNameCheckBox();
		appBuilderPage.clickSubmissionNameCheckBox();
		appBuilderPage.enterExportLabel("Test 1");
		appBuilderPage.enterPDFWebLabel("Test 2");
		appBuilderPage.enterReceiptLabel("Test 3");
		appBuilderPage.clickSaveButton();
		reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Succesfull message", "3.0",
				"Verify Save successful appears: IS NOT AUTOMATED");
		appBuilderPage.clickFieldsButton();

		appBuilderPage.clickScreen(parameters.get("first_screen_name"));
		appBuilderPage.clickLabel("No of employee");
		appBuilderPage.selectFieldStyle("Number");
		appBuilderPage.enterDefaultValue("4");
		appBuilderPage.clickRequiredCheckBox();
		appBuilderPage.enterMinimum("1");
		appBuilderPage.enterMaximum("10");
		appBuilderPage.clickMoreLink();
		appBuilderPage.clickPageBreakAfterCheckBox();
		appBuilderPage.selectDecimalPlaces("3");
		appBuilderPage.selectPdfVisible("Never");
		appBuilderPage.selectWebVisible("Never");
		appBuilderPage.clickEmailSubjectCheckBox();
		appBuilderPage.clickPDFFileNameCheckBox();
		appBuilderPage.clickSubmissionNameCheckBox();
		appBuilderPage.enterExportLabel("X1");
		appBuilderPage.enterPDFWebLabel("X2");
		appBuilderPage.enterReceiptLabel("X3");
		appBuilderPage.clickSaveButton();
		reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Succesfull message", "4.0",
				"Verify Save successful appears: IS NOT AUTOMATED");
		appBuilderPage.clickFieldsButton();
		driver.navigate().refresh();

		appBuilderPage.clickScreen(parameters.get("first_screen_name"));
		appBuilderPage.clickLabel("First Name");
		status = appBuilderPage.getSelectedFieldStyle().contains("Number");
		reportLog(status, testContext.getName(), "Verify style for First Name", "5.1.1",
				"Verified style for First Name");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getDefaultValue().contains("1234");
		reportLog(status, testContext.getName(), "Verify default value for First Name", "5.1.2",
				"Verified default value for First Name");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.isRequiredCheckBoxSelected();
		reportLog(status, testContext.getName(), "Verify Required CheckBox for First Name", "5.1.3",
				"Verified Required CheckBox for First Name");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.isReadOnlyCheckBoxSelected();
		reportLog(!status, testContext.getName(), "Verify Read Only CheckBox for First Name", "5.1.4",
				"Verified Read Only CheckBox for First Name");
		org.testng.Assert.assertTrue(!status);
		appBuilderPage.clickMoreLink();
		status = appBuilderPage.isPageBreakAfterCheckBoxSelected();
		reportLog(status, testContext.getName(), "Verify Page Break After CheckBox for First Name", "5.1.5",
				"Verified Page Break After CheckBox for First Name");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getMaximumLength().contains("4");
		reportLog(status, testContext.getName(), "Verify Maximum Length for First Name", "5.1.6",
				"Verified Maximum Length for First Name");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getFieldMask().contains("0aA?");
		reportLog(status, testContext.getName(), "Verify Field Mask for First Name", "5.1.7",
				"Verified  Field Mask for First Name");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.isMobileVisibleCheckBoxSelected();
		reportLog(status, testContext.getName(), "Verify Mobile Visible for First Name", "5.1.8",
				"Verified Mobile Visible for First Name");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getSelectedPDFVisible().contains("Never");
		reportLog(status, testContext.getName(), "Verify PDF Visible for First Name", "5.1.9",
				"Verified PDF Visible for First Name");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getSelectedWebVisible().contains("Never");
		reportLog(status, testContext.getName(), "Verify Web Visible for First Name", "5.1.10",
				"Verified Web Visible for First Name");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.isEmailSubjectCheckBoxSelected();
		reportLog(status, testContext.getName(), "Verify Email Subject for First Name", "5.1.11",
				"Verified Email Subject for First Name");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.isEmailBodyCheckBoxSelected();
		reportLog(!status, testContext.getName(), "Verify Email Body for First Name", "5.1.12",
				"Verified Email Body for First Name");
		org.testng.Assert.assertTrue(!status);
		status = appBuilderPage.isPdfFileNameCheckBoxSelected();
		reportLog(status, testContext.getName(), "Verify PDF File for First Name", "5.1.13",
				"Verified PDF File for First Name");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.isSubmissionNameCheckBoxSelected();
		reportLog(status, testContext.getName(), "Verify Submission Name for First Name", "5.1.14",
				"Verified Submission Name for First Name");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getExportLabel().contains("Test 1");
		reportLog(status, testContext.getName(), "Verify Export Label for First Name", "5.1.15",
				"Verified Export Label for First Name");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getPdfWebLabel().contains("Test 2");
		reportLog(status, testContext.getName(), "Verify PDFWeb Label for First Name", "5.1.16",
				"Verified PDFWeb Label for First Name");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getReceiptLabel().contains("Test 3");
		reportLog(status, testContext.getName(), "Verify Receipt Label for First Name", "5.1.17",
				"Verified Receipt Label for First Name");
		org.testng.Assert.assertTrue(status);

		appBuilderPage.clickFieldsButton();
		appBuilderPage.clickScreen(parameters.get("first_screen_name"));
		appBuilderPage.clickLabel("No of employee");
		status = appBuilderPage.getSelectedFieldStyle().contains("Number");
		reportLog(status, testContext.getName(), "Verify style for No of employee", "5.2.1",
				"Verified style for No of employee");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getDefaultValue().contains("4");
		reportLog(status, testContext.getName(), "Verify default value for No of employee", "5.2.2",
				"Verified default value for No of employee");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.isRequiredCheckBoxSelected();
		reportLog(status, testContext.getName(), "Verify Required CheckBox for No of employee", "5.2.3",
				"Verified Required CheckBox for No of employee");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.isReadOnlyCheckBoxSelected();
		reportLog(!status, testContext.getName(), "Verify Read Only CheckBox for No of employee", "5.2.4",
				"Verified Read Only CheckBox for No of employee");
		org.testng.Assert.assertTrue(!status);
		status = appBuilderPage.getMinimum().contains("1");
		reportLog(status, testContext.getName(), "Verify Minimum for No of employee", "5.2.5",
				"Verified Minimum for No of employee");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getMaximum().contains("10");
		reportLog(status, testContext.getName(), "Verify Maximum for No of employee", "5.2.6",
				"Verified Maximum for No of employee");
		org.testng.Assert.assertTrue(status);
		appBuilderPage.clickMoreLink();
		status = appBuilderPage.isPageBreakAfterCheckBoxSelected();
		reportLog(status, testContext.getName(), "Verify Page Break After CheckBox for No of employee", "5.2.7",
				"Verified Page Break After CheckBox for No of employee");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getSelectedDecimalPlace().contains("3");
		reportLog(status, testContext.getName(), "Verify Decimal Places for No of employee", "5.2.8",
				"Verified Decimal Places for No of employee");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.isMobileVisibleCheckBoxSelected();
		reportLog(status, testContext.getName(), "Verify Mobile Visible for No of employee", "5.2.9",
				"Verified Mobile Visible for No of employee");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getSelectedPDFVisible().contains("Never");
		reportLog(status, testContext.getName(), "Verify PDF Visible for No of employee", "5.2.10",
				"Verified PDF Visible for No of employee");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getSelectedWebVisible().contains("Never");
		reportLog(status, testContext.getName(), "Verify Web Visible for No of employee", "5.2.11",
				"Verified Web Visible for No of employee");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.isEmailSubjectCheckBoxSelected();
		reportLog(status, testContext.getName(), "Verify Email Subject for No of employee", "5.2.13",
				"Verified Email Subject for No of employee");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.isEmailBodyCheckBoxSelected();
		reportLog(!status, testContext.getName(), "Verify Email Body for No of employee", "5.2.14",
				"Verified Email Body for No of employee");
		org.testng.Assert.assertTrue(!status);
		status = appBuilderPage.isPdfFileNameCheckBoxSelected();
		reportLog(status, testContext.getName(), "Verify PDF File for No of employee", "5.2.15",
				"Verified PDF File for No of employee");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.isSubmissionNameCheckBoxSelected();
		reportLog(status, testContext.getName(), "Verify Submission Name for No of employee", "5.2.16",
				"Verified Submission Name for No of employee");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getExportLabel().contains("X1");
		reportLog(status, testContext.getName(), "Verify Export Label for No of employee", "5.2.17",
				"Verified Export Label for No of employee");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getPdfWebLabel().contains("X2");
		reportLog(status, testContext.getName(), "Verify PDFWeb Label for No of employee", "5.2.18",
				"Verified PDFWeb Label for No of employee");
		org.testng.Assert.assertTrue(status);
		status = appBuilderPage.getReceiptLabel().contains("X3");
		reportLog(status, testContext.getName(), "Verify Receipt Label for No of employee", "5.2.19",
				"Verified Receipt Label for No of employee");
		org.testng.Assert.assertTrue(status);

		createAppPage = appBuilderPage.clickCloseButton();
		createAppPage.clickAppName(appName).destroyApp();

		reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
//		} catch (Exception e) {
//			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
//			org.testng.Assert.assertTrue(false, e.getMessage());
//		}
	}

	/**
	 * kp Test case ID:TC8069 Summary:Un-destroy for apps
	 * 
	 * @throws ParseException
	 **/

	@Test
	@Parameters({ "testdescription" })

	public void unDestroyforApps(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException, ParseException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {

			/** Login and Add status **/

			performLogin(1, parameters.get("username1"), parameters.get("password1"),
					"Login as the sales user plus " + parameters.get("username1"), testContext, testDescription);
			CreateAppPage createapp = new CreateAppPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			AppBuilderPage appBuilder = new AppBuilderPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			EditAppPage editapp = new EditAppPage(driver);
			SubmissionNumbering appUndestroy = new SubmissionNumbering(driver);

			String appNameRandom = parameters.get("appName") + randomAlphaNumeric(10);
			dashboardPage.clickApp();
			createapp.clickAppCreateButton();
			// appBuilder.createApp(appNameRandom, testContext, "1.1");
			appBuilder.clickBlankTemplate();
			appBuilder.clickStartButton();
			appBuilder.clickAddScreenButton();
			appBuilder.enterAppName(appNameRandom);
			appBuilder.clickSaveButton(appNameRandom, parameters.get("username1"), parameters.get("password1"));

			appBuilder.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			publishAppPage.clickCloseButton();
			createapp.isAppNameDisplayed(appNameRandom);

			String appName = appUndestroy.getunDestroyAppName(appNameRandom);
			String version = appUndestroy.getunDestroyVersion(appNameRandom);
			String appStatus = appUndestroy.getunDestroyAppStatus(appNameRandom);

			createapp.clickAppName(appName);
			editapp.destroyApp();
			appBuilder.goCanvasOnly();
			appBuilder.destroyedApp();

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm z");

			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			String actualDateTime11 = df.format(cal.getTime());
			Date sysDate = new SimpleDateFormat("MM/dd/yyyy hh:mm z").parse(actualDateTime11);

			cal.add(Calendar.MINUTE, -2);
			String startDateTime11 = df.format(cal.getTime());
			Date startDateTime = df.parse(startDateTime11);

			cal.add(Calendar.MINUTE, 4);
			String endDateTime11 = df.format(cal.getTime());
			Date endDateTime = df.parse(endDateTime11);

			boolean col1 = appUndestroy.isAppNameDisplayInTable(appName);
			reportLog(col1, testContext.getName(), "Verify App was visible in Destroyed Apps Pages with correct Name ",
					"1.2.", "Correct 'App Name' is verfied");
			org.testng.Assert.assertTrue(col1, "First Column 'App Name' is verfied");

			boolean col2 = appUndestroy.isAppVersioneDisplayInTable(appName, version);
			reportLog(col2, testContext.getName(), "Verify App was visible in Destroyed Apps Page with version of app",
					"1.3.", "Correct App 'version' is verfied");
			org.testng.Assert.assertTrue(col2, "Second Column 'version' is verfied");

			boolean dateTimeVal = appUndestroy.appWithinInRange(sysDate, startDateTime, endDateTime);
			reportLog(dateTimeVal, testContext.getName(), "Verify Fourth column value display ", "1.4.",
					"Date verified");
			org.testng.Assert.assertTrue(dateTimeVal, "Date verfied");

			appUndestroy.restoreApps();
			boolean deletedSubmisson = appUndestroy.isAppDisplayInDestroyedAppsPageAfterRestore(appName);
			reportLog(!deletedSubmisson, testContext.getName(), "Verify app in Destoryed App page after Restore.  ",
					"2.1.", "App is no longer placed in Destroyed Apps List is verified");
			org.testng.Assert.assertFalse(deletedSubmisson,
					"Submissoin not display in Deleted Submissions page is verified");

			dashboardPage.clickApp();
			appBuilder.verifyAppExistInPage(appName);

			boolean appNameVerify = appUndestroy.verifyRestoreAppInAppsPage(appName);
			reportLog(appNameVerify, testContext.getName(), "Verify restore App in Apps Page.", "2.2.",
					"App name in 'App Page' is verified after restore ");
			org.testng.Assert.assertTrue(appNameVerify, "Restored App display in Apps Page");

			boolean appVersionVerify = appUndestroy.verifyAppVersionAfterRestore(appName, version);
			reportLog(appVersionVerify, testContext.getName(), "Verify restore App version in Apps Page.", "2.3.",
					"Version number of App in 'App Page' is verified after restore");
			org.testng.Assert.assertTrue(appVersionVerify, "Restored App display in Apps Page");

			boolean appStatusVerify = appUndestroy.verifyAppStatusAfterRestore(appName, appStatus);
			reportLog(appStatusVerify, testContext.getName(), "Verify restore App status in Apps Page.", "2.4.",
					"Status of app in 'App Page' is verified after restore");
			org.testng.Assert.assertTrue(appStatusVerify, "Restored App display in Apps Page");

			createapp.clickAppName(appName);
			createapp.clickRetireButton();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {

			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	/** TC7708 Verify Submission Status **/

	@Test
	@Parameters({ "testdescription" })

	public void verifySubmission(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			/** Login and Add status **/
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer " + parameters.get("username"), testContext, testDescription);
			SubmissionStatusesPage submissionStatuses = new SubmissionStatusesPage(driver);
			CreateAppPage createapp = new CreateAppPage(driver);
			LoginPage login = new LoginPage(driver);
			// EditAppPage clickSubmissionLink = new EditAppPage(driver);

			boolean submissionStatusLinkText = submissionStatuses.clickAppLink(parameters.get("submission_link"));
			reportLog(submissionStatusLinkText, testContext.getName(), "Click on App Link", "1.2.",
					"App Link Is clicked User re-direct to App detail Page");
			org.testng.Assert.assertTrue(submissionStatusLinkText, "App Link Is clicked");

			boolean submissionStatusText = submissionStatuses
					.verifySubmissionLinkText(parameters.get("submission_status_text"));
			reportLog(submissionStatusText, testContext.getName(),
					"Verify Submission status link text in Apps details page ", "1.3." + "",
					"Submission Status text is verfied");
			org.testng.Assert.assertTrue(submissionStatusText, "Submission Status text is verfied");

			boolean SubmissionText = submissionStatuses.clickSubmissionStatus(parameters.get("submission_status_text"));
			reportLog(SubmissionText, testContext.getName(), "Click on Submission Status Link in App detail page ",
					"2.1.", "Submission Status link is clicked and User re-direct to Edit Submission Status Page");
			org.testng.Assert.assertTrue(SubmissionText,
					"Submission Status icon is clicked and User re-direct to Edit Submission Status Page");

			boolean DefaultCheckBoxValue = submissionStatuses.verifyDefaultValueofCheckBox();
			if (DefaultCheckBoxValue) {
				submissionStatuses.deleteAllStatus(parameters.get("delete_msg"), parameters.get("Status").split(";"));
				submissionStatuses.clickOnSubmissiOnStatus();
				submissionStatuses.clickEnableCheckBox();
				submissionStatuses.saveStatus();
				submissionStatuses.clickOnSubmissiOnStatus();
			}

			DefaultCheckBoxValue = submissionStatuses.verifyDefaultValueofCheckBox();
			reportLog(!DefaultCheckBoxValue, testContext.getName(), "Verify Default Check Box value ", "2.2.",
					"By default Check box is not selected");
			org.testng.Assert.assertFalse(DefaultCheckBoxValue, "By default Check box is not selected");

			boolean checkBoxDefaultValue = submissionStatuses.selectCheckBox();
			reportLog(checkBoxDefaultValue, testContext.getName(), "Select the check box", "3.1.",
					"Enable Submission Status Check box is selected");
			org.testng.Assert.assertTrue(checkBoxDefaultValue, "Enable Submission Status Check box is selected");

			boolean submissionStatus = submissionStatuses.addStatus(parameters.get("org_msg"),
					parameters.get("Status").split(";"));
			reportLog(submissionStatus, testContext.getName(), "Add the status 'Test1','Test 2','Test 3' ", "3.2." + "",
					"Submission Status Options for form 'Submission Status' was successfully addded.");
			org.testng.Assert.assertTrue(submissionStatus, "Status is added");

			boolean statusAfterMove = submissionStatuses.moveDownStatus(parameters.get("columnText1"));
			reportLog(statusAfterMove, testContext.getName(), "Text before moving ", "3.3." + "",
					"Test 1 Move to second  row in the column");
			org.testng.Assert.assertTrue(statusAfterMove, "Test 1 Move to first row in the column");

			boolean statusBeforeMove = submissionStatuses.moveUpStatus(parameters.get("columnText2"));
			reportLog(statusBeforeMove, testContext.getName(), "Text after moving ", "3.4." + "",
					"Test 1 Move to first row in the column");
			org.testng.Assert.assertTrue(statusBeforeMove, "Test 1 Move to second row in the column");
			createapp.logout();
			boolean logoutMessage = createapp.afterlogout(parameters.get("logOut_msg"));
			reportLog(logoutMessage, testContext.getName(), "Click on logout button in create app page", "4.1.",
					"User Logout from the Application");
			org.testng.Assert.assertTrue(logoutMessage, "User Logout from the Application ");

			// ** Login with different user to delete all added status **//*
			login.typeusername(parameters.get("username1"));
			login.typepassword(parameters.get("password1"));
			login.Clickonloginbutton();

			boolean appNameText_2 = createapp.afterlogin(parameters.get("app_name"));
			reportLog(appNameText_2, testContext.getName(), "Login as the company admin:", "4.2.",
					"SignIn successfull");
			org.testng.Assert.assertTrue(appNameText_2, "SignIn successfull");

			// reportLog(true, testContext.getName(),"User Login again
			// ","4.2.","SignIn
			// successfull");
			// org.testng.Assert.assertTrue(true, "SignIn successfull");

			boolean submissionLinkText = submissionStatuses.clickAppLink(parameters.get("submission_link"));
			reportLog(submissionLinkText, testContext.getName(), "Click on App Link", "4.3.",
					"App Link Is clicked User re-direct to App detail Page");
			org.testng.Assert.assertTrue(submissionLinkText, "App Link Is clicked");

			boolean SubmissionText1 = submissionStatuses
					.clickSubmissionStatus(parameters.get("submission_status_text"));
			reportLog(SubmissionText1, testContext.getName(), "Click on Submission Status Link", "4.4.",
					"Submission Status icon is clicked and User re-direct to Edit Submission Status Page");
			org.testng.Assert.assertTrue(SubmissionText1, "Submission Status icon is clicked ");

			boolean replacementOptionWindowText = submissionStatuses
					.replacementOptionWindow(parameters.get("window_msg"), parameters.get("Status").split(";"));
			reportLog(replacementOptionWindowText, testContext.getName(), "Verify 'Assign Replacement Option' window ",
					"4.5." + "",
					"'Assign Replacement Option window' opens successfully when user clicked on trash icon");
			org.testng.Assert.assertTrue(replacementOptionWindowText,
					"Assign Replacement Option window opens successfully");

			boolean deletemsg = submissionStatuses.deleteAllStatus(parameters.get("delete_msg"),
					parameters.get("Status").split(";"));
			reportLog(deletemsg, testContext.getName(), "Delete all submission and verify deleted message ", "4.6.",
					"User can successfully delete each submission status by clicking on trash icon ");
			org.testng.Assert.assertTrue(deletemsg, "User can successfully delete each submission");

			boolean alertmsg = submissionStatuses.editStatus(parameters.get("Status1"), (parameters.get("alert_msg")));
			reportLog(alertmsg, testContext.getName(), "Verify the alert message after clicking on Edit button ",
					"5.1" + "",
					"Alert message 'Are you sure you want to make your changes?' display properly when user click on Edit button");
			org.testng.Assert.assertTrue(alertmsg, "Alert message display properly");

			boolean msgAfterSave = submissionStatuses.updateStatus(parameters.get("Status2"),
					parameters.get("submission_link"));
			reportLog(msgAfterSave, testContext.getName(), "Status edited and updated with new name ", "5.2" + "",
					"User was successfully able to the edit the submission status From Redskins Love To Redskins Suck.");
			org.testng.Assert.assertTrue(msgAfterSave, "Status edited and updated with New Name");

			boolean deleteIndividual = submissionStatuses.deleteIndividualStatus(parameters.get("delete_indv_msg"));
			reportLog(deleteIndividual, testContext.getName(), "Delete added Status 'Redskins Suck' ", "5.3" + "",
					"Added status 'Redskins Suck' is deleted");
			org.testng.Assert.assertTrue(deleteIndividual, "Added individual submission status is deleted");

			DashboardPage dashboardPage = new DashboardPage(driver);
			createapp.logout();
			login.typeusername(parameters.get("username"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			createapp = dashboardPage.clickApp();
			createapp.clickAppName(parameters.get("app")).clickSubmissionStatusLink();
			if (submissionStatuses.verifyDefaultValueofCheckBox()) {
				submissionStatuses.selectCheckBox();
				submissionStatuses.saveStatus();
			}
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	/**
	 * kp Test case ID:TC7702 Summary:Submission No.
	 **/

	@Test
	@Parameters({ "testdescription" })

	public void verifySubmissionNo(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {

			/** Login and Add status **/
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer " + parameters.get("username"), testContext, testDescription);
			SubmissionNumbering submissionNumbering = new SubmissionNumbering(driver);
			CreateAppPage createapp = new CreateAppPage(driver);
			LoginPage login = new LoginPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			AppBuilderPage appBuilder = new AppBuilderPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			String appName = randomAlphaNumeric(10);
			dashboardPage.clickApp();
			createapp.clickAppCreateButton();
			appBuilder.createApp(appName, testContext, "1.2");
			appBuilder.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));
			appBuilder.clickPublishToDeviceButton();

			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilder.clickCloseButton();
			createapp.clickAppName(appName);

			boolean submissionStatusText = submissionNumbering.isSubmissionNoIConDisplay();
			reportLog(submissionStatusText, testContext.getName(),
					"Verify Submission No icon Display in Apps details page ", "1.3." + "",
					"Submission No icon Display in App detail page is verfied");
			org.testng.Assert.assertTrue(submissionStatusText,
					"Submission No icon Display in Apps details page is verfied");
			submissionNumbering.clickSubmissionNumber();

			boolean defaultCheckBoxValue = submissionNumbering.verifyDefaultValueofCheckBox();
			reportLog(!defaultCheckBoxValue, testContext.getName(),
					"Verify Enable Submission Numbering Check Box value ", "2.1.",
					"By default 'Enable Submission Numbering' check box is selected");
			org.testng.Assert.assertFalse(defaultCheckBoxValue, "By default Check box is not selected");

			boolean defaultRadioButtonValue = submissionNumbering.verifyDefaultValueofRadio();
			reportLog(!defaultRadioButtonValue, testContext.getName(),
					"Verify Current GoCanvas App radio button value ", "2.2.",
					"By default Radio button 'Current GoCanvas App' is selected");
			org.testng.Assert.assertFalse(defaultRadioButtonValue,
					"By default Current GoCanvas App radio button is selected");

			boolean labelEnteredValue = submissionNumbering.verifyLabelValue(parameters.get("label_value"));
			reportLog(labelEnteredValue, testContext.getName(), "Verify value in 'Label' field ", "2.3.",
					"Default value in Label field is 'No' is verfied");
			org.testng.Assert.assertTrue(labelEnteredValue, "By default value in Label field is 'No' is verfied");

			boolean submissionNumberValue = submissionNumbering
					.verifySubmissionValue(parameters.get("submisson_value"));
			reportLog(submissionNumberValue, testContext.getName(), "Verify value in 'Submission Number' field ",
					"2.4.", "Default value in Submission Number field is '5' is verfied");
			org.testng.Assert.assertTrue(submissionNumberValue,
					"By default value in Submission Number field is '5' is verfied");

			boolean errorMessage = submissionNumbering.verifyErrorMessage(parameters.get("enter_label_value"),
					parameters.get("enter_submisson_value"), parameters.get("enter_other_prefix"),
					parameters.get("enter_suffix"), parameters.get("error_msg"));
			reportLog(errorMessage, testContext.getName(), "Verify error message when user enter invalid data ", "3.1.",
					"In-line error message display properly is verified");
			org.testng.Assert.assertTrue(errorMessage, "Error message is verfied");

			createapp.logout();
			login.typeusername(parameters.get("username1"));
			login.typepassword(parameters.get("password1"));
			login.Clickonloginbutton();
			dashboardPage.clickApp();
			createapp.clickAppName(appName);
			submissionNumbering.clickSubmissionNumber();
			String message = "Submission Numbering for form '" + appName + "' was successfully updated.";
			boolean submissionNumberingUpdateMsg = submissionNumbering.verifySubmissonNumberingUpdate(
					parameters.get("update_label"), parameters.get("update_number"), message);
			reportLog(submissionNumberingUpdateMsg, testContext.getName(),
					"Verify successful message after updating value", "4.1.",
					"Message 'Submission Numbering' was successfully updated  is  verified ");
			org.testng.Assert.assertTrue(submissionNumberingUpdateMsg, "Successful Message is verfied");

			submissionNumbering.clickSubmissionNumber();
			submissionNumbering.verifyResetSubmissonNumberingUpdate(parameters.get("label_value"),
					parameters.get("submisson_value"));

			String message2 = "Submission Numbering for form '" + appName + "' was disabled.";
			submissionNumbering.clickSubmissionNumber();
			boolean submissionNumberingdisabled = submissionNumbering.verifySubmissonNumberingDisable(message2);
			reportLog(submissionNumberingdisabled, testContext.getName(),
					"Verify successful message after disabling the user", "5.1.",
					"Successful message display after disabling the Submission Numbering is disabled verified");
			org.testng.Assert.assertTrue(submissionNumberingdisabled, "Successful Message is verfied");

			submissionNumbering.clickSubmissionNumber();
			boolean submissionNumberingCheckBox = submissionNumbering.verifySubmissonNumberingCheckBox();
			reportLog(!submissionNumberingCheckBox, testContext.getName(),
					"Verify Checkbox for 'Enable Submission Numbering' ", "6.1.",
					"Enable Submission Numbering check box is un-checked is verified ");
			org.testng.Assert.assertFalse(submissionNumberingCheckBox,
					"CheckBox 'Enable Submission Numbering ' is un-selected");

			submissionNumbering.verifyReenableSubmissionNumbering();
			dashboardPage.clickApp();
			createapp.clickAppName(appName).destroyApp();
			customWait(5);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void appBuilderLoadingTest(String testDescription, String rallyLink, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription,
					rallyLink);
			String appName = parameters.get("app_name");
			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = dashboardPage.clickApp();
			boolean status = createAppPage.isAppNameDisplayed(appName);
			reportLog(status, testContext.getName(), "Verify app name displayed.", "1.1",
					"App name displayed successfully.");
			Assert.assertTrue(status);

			PDFDesignerPage pdfDesignerPage = createAppPage.clickEditPDFPublishedApp(appName);
			pdfDesignerPage.clickCreateALayout();
			PDFDesignerLayoutPage pdfDesignerLayoutPage = pdfDesignerPage.clickStartButton();

			status = pdfDesignerLayoutPage.isAddSectionDisplayed();
			reportLog(status, testContext.getName(), "Verify pdf designer loads.", "2.1",
					"PDF designer loaded successfully.");
			Assert.assertTrue(status);

			AppBuilderPage appBuilderPage = pdfDesignerLayoutPage.clickAppLink();
			status = appBuilderPage.isAddScreenDisplayed();
			reportLog(status, testContext.getName(), "Verify app builder should open.", "3.1",
					"App builder opened successfully.");
			Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			Assert.assertTrue(false, e.getMessage());
		}
	}

	// kp QA-52

	// Automation Reference data with Normal Screen with Reference Data in the
	// Dispatch Manager (*In Web)

	@Test
	@Parameters({ "testdescription" })
	public void createReferenceDataWithNormalScreenFromDispatchManager(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {

			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			String dispatchName = randomAlphaNumeric(10);
			int indexOfAssignTo = 1;
			DashboardPage dashboardPage = new DashboardPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			DispatchCalendarPage dispatchCalendarPage = new DispatchCalendarPage(driver);
			DispatchPage dispatchPage = new DispatchPage(driver);
			DispatchCalendarViewPage dispatchItem = new DispatchCalendarViewPage(driver);
			CreateAppPage createAppLink = new CreateAppPage(driver);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "1.2");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));
			String workingDir = System.getProperty("user.dir");
			String filepath = workingDir + parameters.get("reference_file_path");
			String referenceDataName = parameters.get("reference_data_name") + randomAlphaNumeric(10);
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.addReferenceDataFromCSV(parameters.get("reference_data_field"), referenceDataName,
					parameters.get("reference_data_description"), filepath);

			boolean status = appBuilderPage.isIconDisplayed(parameters.get("reference_data_field"));
			reportLog(status, testContext.getName(),
					"User created first screen and applied reference data on first screen label city", "2.1",
					"Applied reference data on city label");
			org.testng.Assert.assertTrue(status);
			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Succesfull message",
					"2.2", "Verify Save successful appears: IS NOT AUTOMATED");

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("reference_field2"));
			appBuilderPage.addReferenceData(referenceDataName, parameters.get("reference_value2"), 1, null);

			status = appBuilderPage.isIconDisplayed(parameters.get("reference_data_field"));
			reportLog(status, testContext.getName(),
					"User applied reference data on first screen label Data on the basis ....", "3.1",
					"Applied reference data on Data on the basis of the Selection of City label");
			org.testng.Assert.assertTrue(status);

			status = appBuilderPage.isReferenceFieldDisplayed(parameters.get("reference_data_field"));
			reportLog(status, testContext.getName(), "Verify City displayed on reference field", "3.2",
					"City displayed on reference field");
			org.testng.Assert.assertTrue(status);

			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();

			publishAppPage.selectDispatchToggleButton();
			publishAppPage.clickNextButton();

			appBuilderPage = publishAppPage.verifyCreatedApp(testContext, parameters.get("name"),
					parameters.get("username"), appName, parameters.get("app_status"), "4.1", "4.2", "4.3");

			appBuilderPage.clickCloseButton();
			dashboardPage.clickDispatch();
			dispatchCalendarPage.clickCreateDispatchLink();
			DispatchCalendarViewPage createDispatchRefData = new DispatchCalendarViewPage(driver);
			createDispatchRefData.selectApp(appName);
			dispatchCalendarPage.clickYesButtonPopUp();
			createDispatchRefData.enterDispatchName(dispatchName);
			createDispatchRefData.enterItemDescription(dispatchName);
			createDispatchRefData.selectAssignTo(indexOfAssignTo);

			String[] cityValueList = parameters.get("cityValueList").split(";");
			String[] populatedValue = parameters.get("populatedValue").split(";");

			createAppLink.verifyReferenceDataPopulation(testContext, populatedValue, cityValueList, "5.1");
			dispatchItem.clickSaveButton();
			status = dispatchItem.getToastMessage().equals(parameters.get("toast_message"));
			reportLog(status, testContext.getName(), "Verify Your dispatch was successfully created appears.", "6.1",
					"Verified Your dispatch was successfully created appears.");
			dashboardPage.clickDispatch();
			dispatchPage.searchDispatch(dispatchName);
			dispatchPage.deleteDispatch();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-77 : Automation Reference data with Allow duplicate Entries with Reference
	// Data in the Dispatch Manager

	@Test
	@Parameters({ "testdescription" })
	public void referenceDataWithAllowDuplicate(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			String dispatchName = randomAlphaNumeric(10);
			int indexOfAssignTo = 1;
			DashboardPage dashboardPage = new DashboardPage(driver);
			DispatchCalendarViewPage allowDuplicate = new DispatchCalendarViewPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			CreateAppPage createAppLink = new CreateAppPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);

			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			DispatchCalendarPage dispatchCalendarPage = new DispatchCalendarPage(driver);
			DispatchCalendarViewPage clickOSaveButton = new DispatchCalendarViewPage(driver);
			DispatchPage dispatchPage = new DispatchPage(driver);
			appBuilderPage.createApp(appName, testContext, "1.2");
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));
			String workingDir = System.getProperty("user.dir");
			String filepath = workingDir + parameters.get("reference_file_path");
			String referenceDataName = parameters.get("reference_data_name") + randomAlphaNumeric(10);
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.addReferenceDataFromCSV(parameters.get("reference_data_field"), referenceDataName,
					parameters.get("reference_data_description"), filepath);

			boolean status = appBuilderPage.isIconDisplayed(parameters.get("reference_data_field"));
			reportLog(status, testContext.getName(),
					"User created first screen and applied reference data on first screen label city", "2.1",
					"Applied reference data on city label");
			org.testng.Assert.assertTrue(status);
			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Succesfull message",
					"2.2", "Verify Save successful appears: IS NOT AUTOMATED");

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("reference_field2"));
			appBuilderPage.addReferenceData(referenceDataName, parameters.get("reference_value2"), 1, null);

			status = appBuilderPage.isIconDisplayed(parameters.get("reference_data_field"));
			reportLog(status, testContext.getName(),
					"User applied reference data on first screen label Data on the basis ....", "3.1",
					"Applied reference data on Data on the basis of the Selection of City label");
			org.testng.Assert.assertTrue(status);

			status = appBuilderPage.isReferenceFieldDisplayed(parameters.get("reference_data_field"));
			reportLog(status, testContext.getName(), "Verify City displayed on reference field", "3.2",
					"City displayed on reference field");
			org.testng.Assert.assertTrue(status);
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLoopIcon();
			appBuilderPage.clickUseExistingField();
			appBuilderPage.clickDoneButton();
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickAllowDuplicate();
			appBuilderPage.clickSaveButton();
			appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.selectDispatchToggleButton();
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilderPage.clickCloseButton();
			dashboardPage.clickDispatch();
			dispatchCalendarPage.clickCreateDispatchLink();
			allowDuplicate.selectApp(appName);
			dispatchCalendarPage.clickYesButtonPopUp();
			allowDuplicate.enterDispatchName(dispatchName);
			allowDuplicate.enterItemDescription(dispatchName);
			allowDuplicate.selectAssignTo(indexOfAssignTo);
			String[] cityValueList = parameters.get("cityValueList").split(";");
			String[] populatedValue = parameters.get("populatedValue").split(";");
			createAppLink.allowDuplicateData(testContext, populatedValue, cityValueList, "4.1");
			clickOSaveButton.clickSaveButton();
			dashboardPage.clickDispatch();
			dispatchPage.searchDispatch(dispatchName);
			dispatchPage.deleteDispatch();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	// QA-78 : Automation Reference data with Allow duplicate Entries with Reference
	// Data in the Dispatch Manager

	@Test
	@Parameters({ "testdescription" })
	public void editablevalueswithreferencedata(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			String dispatchName = randomAlphaNumeric(10);
			int indexOfAssignTo = 1;
			DashboardPage dashboardPage = new DashboardPage(driver);
			DispatchCalendarViewPage editableDropDown = new DispatchCalendarViewPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			CreateAppPage createAppLink = new CreateAppPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);

			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			DispatchCalendarPage dispatchCalendarPage = new DispatchCalendarPage(driver);
			DispatchCalendarViewPage clickOSaveButton = new DispatchCalendarViewPage(driver);
			DispatchPage dispatchPage = new DispatchPage(driver);
			appBuilderPage.createApp(appName, testContext, "1.2");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));
			String workingDir = System.getProperty("user.dir");
			String filepath = workingDir + parameters.get("reference_file_path");
			String referenceDataName = parameters.get("reference_data_name") + randomAlphaNumeric(10);
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.addReferenceDataFromCSV(parameters.get("reference_data_field"), referenceDataName,
					parameters.get("reference_data_description"), filepath);

			boolean status = appBuilderPage.isIconDisplayed(parameters.get("reference_data_field"));
			reportLog(status, testContext.getName(),
					"User created first screen and applied reference data on first screen label", "2.1",
					"Reference data Applied on field: City");
			org.testng.Assert.assertTrue(status);
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("reference_field2"));
			appBuilderPage.addReferenceData(referenceDataName, parameters.get("reference_value2"), 1, null);

			status = appBuilderPage.isIconDisplayed(parameters.get("reference_data_field"));
			reportLog(status, testContext.getName(),
					"User applied reference data on first screen label Data on the basis ....", "3.1",
					"Reference data Applied on field: Data on the basis of the Selection of City");
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("reference_data_field"));
			appBuilderPage.selectStyle(parameters.get("style"));
			appBuilderPage.clickSaveButton();
			appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.selectDispatchToggleButton();
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilderPage.clickCloseButton();
			dashboardPage.clickDispatch();
			dispatchCalendarPage.clickCreateDispatchLink();
			editableDropDown.selectApp(appName);
			dispatchCalendarPage.clickYesButtonPopUp();
			editableDropDown.enterDispatchName(dispatchName);
			editableDropDown.enterItemDescription(dispatchName);
			editableDropDown.selectAssignTo(indexOfAssignTo);
			createAppLink.verifyEditableField(testContext, parameters.get("city"), parameters.get("populatedValue"),
					"4.");
			clickOSaveButton.clickSaveButton();
			status = clickOSaveButton.getToastMessage().equals(parameters.get("toast_message"));
			reportLog(status, testContext.getName(), "Verify dispatch successfully created", "5.1",
					"Dispatch successfully created.");
			dashboardPage.clickDispatch();
			dispatchPage.searchDispatch(dispatchName);
			dispatchPage.deleteDispatch();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-98 : Create App with adding new field and some constant value
	@Test
	@Parameters({ "testdescription" })
	public void appWithCalculationWithNumberAndConstant(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			AppBuilderPage appBuilder = new AppBuilderPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			CreateAppPage applist = new CreateAppPage(driver);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "1.2");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			boolean status = appBuilderPage.isLabelDisplayed(parameters.get("number1"));
			reportLog(status, testContext.getName(), "User created first screen with labels", "2.1",
					"First screen created successfully");
			org.testng.Assert.assertTrue(status);
			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));
			appBuilderPage.clickLabelField(parameters.get("number2"));
			appBuilderPage.clickLabelField(parameters.get("calculation"));

			boolean popUptext = appBuilderPage.openCalculationPopUp(parameters.get("calpopuptext"));
			reportLog(popUptext, testContext.getName(), "Verfied Calculation pop up is open", "3.1",
					"Calculation pop up opened in the center after clicking on edit Calculation button Is Verified");

			String[] operatorDropDownValue = parameters.get("operatordropdown").split(";");
			applist.verifyValuesInDropDownFieldOperator1(testContext, operatorDropDownValue, "4.1");
			applist.verifyValuesInDropDownFieldOperator2(testContext, operatorDropDownValue, "5.1");

			boolean fiedlAdded = applist.addNewField(testContext, operatorDropDownValue);
			reportLog(fiedlAdded, testContext.getName(), "Verfied field selected from drop down", "6.1",
					"New Number selected from drop down in opened Calculation pop up Is Verified");

			applist.addConstant(testContext, operatorDropDownValue, parameters.get("numberDecimal"));
			reportLog(true, testContext.getName(), "Verfied user able to enter data for constant field", "7.1",
					"Decimal Value is entered in constant field Is Verified");

			appBuilderPage.clickSaveButton();
			appBuilder.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();

			CreateAppPage createAppPage = appBuilderPage.clickCloseButton();
			appBuilderPage = createAppPage.clickAppName(appName).clickEditAppButton();

			appBuilderPage.clickLabelField(parameters.get("calculation"));
			appBuilderPage.clickEditCalculation();

			createAppPage = createAppPage.verifyCalculationSavedValues(testContext, parameters.get("Operator 1"),
					parameters.get("Operator 2"), parameters.get("Operator 3"), parameters.get("numberDecimal"), "8.1",
					"8.2", "8.3", "8.4", appName);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-276 : Automation Reference data with Non-Editable values with Reference
	// Data in the Dispatch Manager

	@Test
	@Parameters({ "testdescription" })
	public void noneditablevalueswithreferencedata(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			String dispatchName = randomAlphaNumeric(10);
			int indexOfAssignTo = 1;
			DashboardPage dashboardPage = new DashboardPage(driver);
			DispatchCalendarViewPage editableDropDown = new DispatchCalendarViewPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			CreateAppPage createAppLink = new CreateAppPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);

			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			DispatchCalendarPage dispatchCalendarPage = new DispatchCalendarPage(driver);
			DispatchCalendarViewPage clickOSaveButton = new DispatchCalendarViewPage(driver);
			DispatchPage dispatchPage = new DispatchPage(driver);
			appBuilderPage.createApp(appName, testContext, "1.2");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));
			String workingDir = System.getProperty("user.dir");
			String filepath = workingDir + parameters.get("reference_file_path");
			String referenceDataName = parameters.get("reference_data_name") + randomAlphaNumeric(10);
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.addReferenceDataFromCSV(parameters.get("reference_data_field"), referenceDataName,
					parameters.get("reference_data_description"), filepath);

			boolean status = appBuilderPage.isIconDisplayed(parameters.get("reference_data_field"));
			reportLog(status, testContext.getName(),
					"User created first screen and applied reference data on first screen label", "2.1",
					"Reference data Applied on field: City");
			org.testng.Assert.assertTrue(status);
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("reference_field2"));
			appBuilderPage.addReferenceData(referenceDataName, parameters.get("reference_value2"), 1, null);

			status = appBuilderPage.isIconDisplayed(parameters.get("reference_data_field"));
			reportLog(status, testContext.getName(),
					"User applied reference data on first screen label Data on the basis ....", "3.1",
					"Reference data Applied on field: Data on the basis of the Selection of City");
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("reference_data_field"));
			appBuilderPage.clickSaveButton();
			appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.selectDispatchToggleButton();
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilderPage.clickCloseButton();
			dashboardPage.clickDispatch();
			dispatchCalendarPage.clickCreateDispatchLink();
			editableDropDown.selectApp(appName);
			dispatchCalendarPage.clickYesButtonPopUp();
			editableDropDown.enterDispatchName(dispatchName);
			editableDropDown.enterItemDescription(dispatchName);
			editableDropDown.selectAssignTo(indexOfAssignTo);
			boolean editabletextstatus = createAppLink
					.verifyNonEditableField(parameters.get("noneditablevalue"), parameters.get("enterdatasecondfield"))
					.equals(parameters.get("noneditablemessage"));
			reportLog(editabletextstatus, testContext.getName(),
					"Verify message when user enter data in non editable field", "4.1",
					"'No Record Found'" + " message is display when user enter data in Non editable field ");
			clickOSaveButton.clickSaveButton();
			status = clickOSaveButton.getToastMessage().equals(parameters.get("toast_message"));
			reportLog(status, testContext.getName(), "Verify dispatch successfully created", "5.1",
					"Dispatch successfully created.");
			dashboardPage.clickDispatch();
			dispatchPage.searchDispatch(dispatchName);
			dispatchPage.deleteDispatch();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	// QA-101 : Create App with adding new field and apply operator on fields

	@Test
	@Parameters({ "testdescription" })
	public void appWithCalculationWithNumberAndOperator(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

//		try {
		performLogin(1, parameters.get("username"), parameters.get("password"),
				"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
		DashboardPage dashboardPage = new DashboardPage(driver);
		AppBuilderPage appBuilder = new AppBuilderPage(driver);
		PublishAppPage publishAppPage = new PublishAppPage(driver);
		String appName = parameters.get("app_name") + randomAlphaNumeric(10);
		CreateAppPage applist = new CreateAppPage(driver);
		AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
		appBuilderPage.createApp(appName, testContext, "1.2");

		appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
				parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
				parameters.get("first_screen_fields").split(";"));
		boolean status = appBuilderPage.isLabelDisplayed(parameters.get("number1"));
		reportLog(status, testContext.getName(), "User created first screen with labels", "2.1",
				"First screen created successfully");
		org.testng.Assert.assertTrue(status);
		appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));
		appBuilderPage.clickLabelField(parameters.get("number2"));
		appBuilderPage.clickLabelField(parameters.get("calculation"));

		boolean popUptext = appBuilderPage.openCalculationPopUp(parameters.get("calpopuptext"));
		reportLog(popUptext, testContext.getName(), "Verfied Calculation pop up is open", "3.1",
				"Calculation pop up opened in the center after clicking on edit Calculation button Is Verified");
		org.testng.Assert.assertTrue(popUptext);

		String[] operatorDropDownValue = parameters.get("operatordropdownvalues").split(";");
		String[] operatorselectedDropDownValue = parameters.get("operatordropdownselectedvalues").split(";");
		applist.verifyValuesInDropDownFieldOperator1(testContext, operatorDropDownValue, "4.1");
		applist.verifyValuesInDropDownFieldOperator2(testContext, operatorDropDownValue, "5.1");

		applist.selectFieldAndApplyOperators(testContext, operatorDropDownValue, operatorselectedDropDownValue);
		applist.addConstants(testContext, operatorDropDownValue, parameters.get("numberDecimal"));
		reportLog(true, testContext.getName(), "Verfied user able to enter data for constant field", "7.1",
				"Decimal Value is entered in constant field Is Verified");
		org.testng.Assert.assertTrue(status);

		appBuilderPage.clickSaveButton();
		appBuilder.clickPublishToDeviceButton();
		publishAppPage.clickNextButton();
		publishAppPage.clickPublishButton();

		CreateAppPage createAppPage = appBuilderPage.clickCloseButton();
		appBuilderPage = createAppPage.clickAppName(appName).clickEditAppButton();

		appBuilderPage.clickLabelField(parameters.get("calculation"));
		appBuilderPage.clickEditCalculation();
		String symbolArray[] = new String[] { parameters.get("Symbol1"), parameters.get("Symbol2"),
				parameters.get("Symbol3"), parameters.get("Symbol4"), parameters.get("Symbol5"),
				parameters.get("Symbol6") };
		String stepsArry[] = new String[] { "9.1", "9.2", "9.3", "9.4", "9.5", "9.6", "9.7" };
		createAppPage.verifyCalculationFieldSavedValues(testContext, parameters.get("Operator 1"),
				parameters.get("Operator 2"), parameters.get("Operator 3"), parameters.get("Operator 4"),
				parameters.get("Operator 5"), parameters.get("Operator 6"), parameters.get("numberDecimal"), "8.1",
				"8.2", "8.3", "8.4", "8.5", "8.6", "8.7", appName);
		createAppPage = createAppPage.verifySymbolOfOperator(testContext, symbolArray, stepsArry, appName);
		reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
//		} catch (Exception e) {
//			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
//			org.testng.Assert.assertTrue(false, e.getMessage());
//		}
	}

	// QA-307 : Upload Photo OR Select existing Reference Image
	@Test
	@Parameters({ "testdescription" })
	public void uploadStaticPhoto(String testDescription, ITestContext testContext) throws Exception {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {

			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);

			// Delete existing ref image
			boolean refImagesDeleted = dashboardPage.deleteExistingStaticPhoto();
			reportLog(refImagesDeleted, testContext.getName(), "Verify Ref-Image is uploaded or Not", "2.0",
					"Existing Reference Image deleted sucessfully");

			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "3.1");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels1").split(";"),
					parameters.get("first_screen_field1").split(";"));

			// Add Static Image Field in FIRST Screen
			appBuilderPage = appBuilderPage.clickOnAddStaticImage(parameters.get("first_screen_labels2").split(";"),
					parameters.get("first_screen_field2").split(";"));

			String workingDir = System.getProperty("user.dir");
			String filepath = workingDir + parameters.get("staticImage_file_path");
			String staticPhotoName = parameters.get("static_photo_name") + randomAlphaNumeric(10);

			// Upload Static Image in FIRST Screen
			appBuilderPage.uploadStaticPhoto(staticPhotoName, filepath);

			appBuilderPage = appBuilderPage.enterStaticPhotoLabel(parameters.get("first_screen_labels2").split(";"),
					parameters.get("first_screen_field2").split(";"), parameters.get("first_screen_field3").split(";"));

			// Verify Image uploaded in work-space or Not
			boolean firstScreenDisplay = appBuilderPage.isLabelDisplayed("First Name");
			reportLog(firstScreenDisplay, testContext.getName(), "User created first screen with labels", "4.0",
					"First screen created successfully");
			org.testng.Assert.assertTrue(firstScreenDisplay);

			boolean imagePresentOrNot = appBuilderPage.imageuploadedsuccessfully();
			reportLog(imagePresentOrNot, testContext.getName(), "Verify Image is uploaded or Not", "5.0",
					"Photo is uploaded sucessfully from system is verified in First Screen");

			// Add second screen
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("second_screen_place_holder"),
					parameters.get("second_screen_name"), parameters.get("second_screen_labels").split(";"),
					parameters.get("second_screen_field").split(";"));

			appBuilderPage = appBuilderPage.clickOnAddStaticImage(parameters.get("second_screen_labels2").split(";"),
					parameters.get("first_screen_field2").split(";"));

			// Select Existing Image
			appBuilderPage.selectExistingRefImage();

			boolean secondScreenDisplay = appBuilderPage.isLabelDisplayed("Last Name");
			reportLog(secondScreenDisplay, testContext.getName(), "User created first screen with labels", "6.0",
					"Second screen created successfully");

			org.testng.Assert.assertTrue(secondScreenDisplay);

			boolean existingImagePresentOrNot = appBuilderPage.imageuploadedsuccessfully();
			reportLog(existingImagePresentOrNot, testContext.getName(), "Verify existing ref-Image is selected or Not",
					"7.0", "Existing ref-image selected is verifed in Second screen");

			appBuilderPage = appBuilderPage.enterStaticPhotoLabel(parameters.get("second_screen_labels2").split(";"),
					parameters.get("first_screen_field2").split(";"), parameters.get("first_screen_field3").split(";"));

			appBuilderPage.clickSaveButton();
			appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();

			CreateAppPage createAppPage = appBuilderPage.clickCloseButton();
			appBuilderPage = createAppPage.clickAppName(appName).clickEditAppButton();

			// Edit flow
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			boolean firstScreenFieldLabel_1 = appBuilderPage.isFieldDisplayed(parameters.get("first_screen_labels1"));
			reportLog(firstScreenFieldLabel_1, testContext.getName(), "Verify First Screen Label Name 'First Name'",
					"8.0", "Field with label 'First Name' displayed in edit mode in first screen");

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			boolean firstScreenFieldLabel_2 = appBuilderPage.isFieldDisplayed(parameters.get("first_screen_labels2"));
			reportLog(firstScreenFieldLabel_2, testContext.getName(),
					"Verify First Screen Label Name 'Upload Image From System'", "9.0",
					"Field with label 'Upload Image From System' display in edit mode in first screen");

			boolean imagePresentOrNotInEditModeInFirstScreen = appBuilderPage.imageuploadedsuccessfully();
			reportLog(imagePresentOrNotInEditModeInFirstScreen, testContext.getName(),
					"Verify Image display in edit mode or not", "10.0",
					"'Uploaded Static Image' is displaying in first screen when user verify after editing the App ");

			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			boolean secondScreenFieldLabel_1 = appBuilderPage.isFieldDisplayed(parameters.get("second_screen_labels1"));
			reportLog(secondScreenFieldLabel_1, testContext.getName(), "Verify First Screen Label Name 'Last Name'",
					"11.0", "Field with label 'Last Name' displayed in edit mode in second screen");

			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			boolean secondScreenFieldLabel_2 = appBuilderPage.isFieldDisplayed(parameters.get("second_screen_labels2"));
			reportLog(secondScreenFieldLabel_2, testContext.getName(),
					"Verify First Screen Label Name 'Select Existing Ref-Imge'", "12.0",
					"Field with label 'Select Existing Ref-Imge' display in edit mode in second screen");

			boolean imagePresentOrNotInEditModeInSecondScreen = appBuilderPage.imageuploadedsuccessfully();
			reportLog(imagePresentOrNotInEditModeInSecondScreen, testContext.getName(),
					"Verify existing ref-Image display in edit mode or not", "13.0",
					"'Selected ref image' is displaying in second screen when user verify after editing the App");

			appBuilderPage.clickCloseButton();
			createAppPage.clickAppName(appName).destroyApp();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-396 upload Static Image with non supported file
	@Test
	@Parameters({ "testdescription" })
	public void verifyStaticPhotoNonSupportedFile(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);

			// create app
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			CreateAppPage applist = new CreateAppPage(driver);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "2.2");

			// Add first screen and provide name
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels1").split(";"),
					parameters.get("first_screen_field1").split(";"));

			// Add Static Image Field in FIRST Screen
			appBuilderPage = appBuilderPage.clickOnAddStaticImage(parameters.get("first_screen_labels2").split(";"),
					parameters.get("first_screen_field2").split(";"));

			String workingDir = System.getProperty("user.dir");
			// Upload Static Image in FIRST Screen
			appBuilderPage.selectExistingRefImage();

			// Verify Short text should be dropped and Verify Label Name are displayed
			// FirstName
			boolean firstScreenDisplay_1 = appBuilderPage.isLabelDisplayed("First Name");
			reportLog(firstScreenDisplay_1, testContext.getName(), "User enter short text lable name", "4.0",
					"User enter short text lable name sucessfully");
			org.testng.Assert.assertTrue(firstScreenDisplay_1);

			appBuilderPage = appBuilderPage.enterStaticPhotoLabel(parameters.get("first_screen_labels2").split(";"),
					parameters.get("first_screen_field2").split(";"), parameters.get("first_screen_field3").split(";"));

			int screenIndex = 1;
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLabel(parameters.get("first_screen_labels2")).clickFieldConditions()
					.clickNewFieldConditionButton().selectConditionScreen(screenIndex)
					.selectConditionField(parameters.get("conditional_field"))
					.selectCondition(parameters.get("condition_equal"))
					.enterConditionValue(parameters.get("condition_value")).clickSaveButton();

			// Now Drag and Drop Static Image and upload some Non Jpeg,PNG file
			appBuilderPage = appBuilderPage.clickOnAddStaticImage(parameters.get("first_screen_labels2").split(";"),
					parameters.get("first_screen_field2").split(";"));
			String filepath2 = workingDir + parameters.get("staticImage_file_path_PDF");
			appBuilderPage.uploadPhoto(filepath2);
			boolean invaildImage = appBuilderPage.verifyInvalidImageFormat(parameters.get("errorMessage"));
			reportLog(invaildImage, testContext.getName(), "Invalid image format text is displayed", "9.0",
					"nvalid image format text is displayed sucessfully");
			appBuilderPage.clickCancelButton();
			appBuilderPage.clickPublishToDevice();
			publishAppPage.clickPublishToDeviceButton();
			appBuilderPage.clickNextButton();
			publishAppPage.clickPublishButton();
			publishAppPage.clickCloseAppBuilderButton();
			applist.clickAppName(appName).destroyApp();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

// QA-414 verify Calculation Validations Message 
	@Test
	@Parameters({ "testdescription" })
	public void verifyCalculationValidationsMessage(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			CreateAppPage createAppPage = new CreateAppPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "1.2");

			// input fields in three different screen
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_label").split(";"),
					parameters.get("first_screen_field").split(";"));
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("second_screen_place_holder"),
					parameters.get("second_screen_name"), parameters.get("second_screen_label").split(";"),
					parameters.get("second_screen_field").split(";"));
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("third_screen_place_holder"),
					parameters.get("third_screen_name"), parameters.get("third_screen_label").split(";"),
					parameters.get("third_screen_field").split(";"));
			// publish device
			appBuilderPage.clickPublishToDevice();
			publishAppPage.clickPublishToDeviceButton();
			appBuilderPage.clickNextButton();
			publishAppPage.clickPublishButton();
			publishAppPage.clickCloseAppBuilderButton();

			// reopened app using app name
			createAppPage.clickAppName(appName).clickEditAppButton();

			// verify all fields created successfully
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			boolean status = appBuilderPage.isLabelDisplayed(parameters.get("number1"));
			reportLog(status, testContext.getName(), "User created first screen with labels", "7.1",
					"First screen created successfully");
			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			boolean status2 = appBuilderPage.isLabelDisplayed(parameters.get("number2"));
			reportLog(status2, testContext.getName(), "User created first screen with labels", "7.2",
					"Second screen created successfully");
			appBuilderPage.clickScreen(parameters.get("third_screen_name"));
			boolean status3 = appBuilderPage.isLabelDisplayed(parameters.get("number3"));
			reportLog(status3, testContext.getName(), "User created three screen with labels", "7.3",
					"Third screens created successfully");

			org.testng.Assert.assertTrue(status);

			appBuilderPage.clickLabelField(parameters.get("third_screen_label"));
			appBuilderPage.clickEditCalculation();
			// verify operator default selected value
			boolean defaultTextOp1 = createAppPage.verifyOperatorDefaultValue(parameters.get("Operator_1"),
					parameters.get("number1"));
			boolean defaultTextOp2 = createAppPage.verifyOperatorDefaultValue(parameters.get("Operator_2"),
					parameters.get("number2"));
			reportLog(defaultTextOp1 && defaultTextOp2, testContext.getName(), "Verify operator default value", "8.0",
					"Operator default value was displayed correct");
			createAppPage.clickCancelCalculation();

			// delete second screen by screen name
			customWait(2);// wait for driver move to Screen 2 click delete icon
			appBuilderPage.moveToScreenDelete(parameters.get("second_screen_name"));
			// verify error message
			appBuilderPage.clickScreen(parameters.get("third_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("third_screen_label"));
			appBuilderPage.clickEditCalculation();
			boolean displayed = appBuilderPage.verifyErrorMsgOnCalculation(parameters.get("error_message"));
			Assert.assertTrue(displayed, "Error Message not displayed");
			reportLog(displayed, testContext.getName(), "Verify error meesage get displayed", "10.0",
					"Error message displayed successfully");
			boolean empty = createAppPage.verifyEmptyOperator2();
			reportLog(empty, testContext.getName(), "Verify Operator 2 dropdown field not displayed", "11",
					"Operator 2 have no field can be selected which is expected");
			createAppPage.deleteOperators(parameters.get("Operator_2"));
			createAppPage.confirmDeleteOperator();
			createAppPage.clickDoneOnCalculationField();

			appBuilderPage.clickPublishToDevice();
			publishAppPage.clickPublishToDeviceButton();
			publishAppPage.clickPublishAssignUser();
			publishAppPage.clickCloseAppBuilderButton();
			createAppPage.clickAppName(appName).destroyApp();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-427 Verify Summary/Payment Validation Error Message
	@Test
	@Parameters({ "testdescription" })
	public void veriySummaryPaymentValidationErrorMessage(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);

			// create app
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			CreateAppPage createAppPage = new CreateAppPage(driver);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "2");

			// Add first screen and provide name adding the Number and Calculation fields.
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_lables").split(";"),
					parameters.get("first_screen_fields").split(";"));

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("second_screen_place_holder"),
					parameters.get("second_screen_name"), parameters.get("second_screen_labels").split(";"),
					parameters.get("second_screen_fields").split(";"));

			appBuilderPage.clickLabelField(parameters.get("summary"));
			appBuilderPage.selectSummaryScreen(parameters.get("first_screen_name"));
			appBuilderPage.selectSummaryField(parameters.get("calculation"));

			// publish to devices
			appBuilderPage.clickPublishToDevice();
			publishAppPage.clickPublishToDeviceButton();
			appBuilderPage.clickNextButton();
			publishAppPage.clickPublishButton();
			publishAppPage.clickCloseAppBuilderButton();
			// reopened app using app name and verify all the fields
			createAppPage.clickAppName(appName).clickEditAppButton();
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			boolean status = appBuilderPage.isLabelDisplayed(parameters.get("numerb1"))
					&& appBuilderPage.isLabelDisplayed(parameters.get("number2"))
					&& appBuilderPage.isLabelDisplayed(parameters.get("calculation"));
			reportLog(status, testContext.getName(), "Verify Screen 1 all labels", "10.1",
					"First screen label displayed as expected");
			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			boolean status2 = appBuilderPage.isLabelDisplayed(parameters.get("summary"))
					&& appBuilderPage.isLabelDisplayed(parameters.get("payment"));
			reportLog(status2, testContext.getName(), "Verify Screen 2 all labels", "10.2",
					"Second screen label displayed as expected");

			// delete calculation field on Screen 1
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.deleteScreenField(parameters.get("calculation"));
			appBuilderPage.clickDeleteFieldButton();
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			boolean checkDeleted = appBuilderPage.isLabelDeleted(parameters.get("calculation"));
			reportLog(checkDeleted, testContext.getName(), "Verify deleted calculation field", "11",
					"Calculation field has beed deleted successfully");

			// Check the error icon are displayed on Screen 2
			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("summary"));
			boolean checkError1 = appBuilderPage.isErrorIconDisplayed(parameters.get("summary"));
			reportLog(checkError1, testContext.getName(), "Check Error icon display on Summary field", "12.1",
					"The Error Icon displayed in Summary field successfully");
			appBuilderPage.clickLabelField(parameters.get("payment"));
			boolean checkError2 = appBuilderPage.isErrorIconDisplayed(parameters.get("payment"));
			reportLog(checkError2, testContext.getName(), "Check Error icon display on Payment field", "12.2",
					"The Error Icon displayed in Payment field successfully");

			// close the App builder don't save and exit
			boolean pageName = appBuilderPage.clickCloseWithoutSave(parameters.get("page_name"));
			reportLog(pageName, testContext.getName(), "User Click on close button in app builder without save", "13",
					"User re-direct to App Page");

			// Destroy The App
			createAppPage.clickAppName(appName).destroyApp();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-453 Verify Payment field allow additional data to be sent on Payment
	// Fields
	@Test
	@Parameters({ "testdescription" })
	public void veriyAllowAdditionalDataOnPayment(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);

			// create app
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			CreateAppPage createAppPage = new CreateAppPage(driver);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "2");

			// Add first screen and provide name adding the Number and Calculation fields.
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			// Second Screen
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("second_screen_place_holder"),
					parameters.get("second_screen_name"), parameters.get("second_screen_labels").split(";"),
					parameters.get("second_screen_fields").split(";"));
			// Third Screen
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("third_screen_place_holder"),
					parameters.get("third_screen_name"), parameters.get("third_screen_labels").split(";"),
					parameters.get("third_screen_fields").split(";"));

			// verify all the fields before the payment field are displayed in dropDwon
			boolean verifyDropdown = appBuilderPage.verifyDataIncludePaymentField(parameters.get("paymentFields"),
					parameters.get("second_screen_name"), parameters.get("unExceptFieldlabels"));
			reportLog(verifyDropdown, testContext.getName(),
					"verify all the fields before the payment field are displayed in dropDwon", "12.0 ",
					"All options in dropDown as expected in the Payment dropDown");

			// Select one option in dropdown and verify it
			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			appBuilderPage.clickLabel(parameters.get("paymentFields"));
			reportLog(appBuilderPage.selectDataIncPayment(), testContext.getName(),
					"Select one random option from dropDown", "13.0", "Successfully select option from dropDown");

			// move the payment field in same screen
			appBuilderPage.switchTwoFields(parameters.get("paymentFields"), parameters.get("moveToTargetField"));
			verifyDropdown = appBuilderPage.verifyDataIncludePaymentField(parameters.get("paymentFields"),
					parameters.get("second_screen_name"), parameters.get("unExceptFieldlabels"));
			reportLog(verifyDropdown, testContext.getName(),
					"verify all the fields before the payment field are displayed in dropDwon", "14.0 ",
					"All options in dropDown as expected in the Payment dropDown after Switch Fields");
			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			appBuilderPage.clickLabel(parameters.get("paymentFields"));
			reportLog(appBuilderPage.selectDataIncPayment(), testContext.getName(),
					"Select one random option from dropDown", "14.1", "Successfully select option from dropDown");

			// Move the Screen into different order and verify Payment DropDown
			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			appBuilderPage.switchTwoScreens(parameters.get("second_screen_name"), parameters.get("third_screen_name"));

			verifyDropdown = appBuilderPage.verifyDataIncludePaymentField(parameters.get("paymentFields"),
					parameters.get("second_screen_name"), parameters.get("unExceptFieldlabels"));
			reportLog(verifyDropdown, testContext.getName(),
					"verify all the fields before the payment field are displayed in dropDwon", "15.0 ",
					"All options in dropDown as expected in the Payment dropDown After Switch Screen");
			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			appBuilderPage.clickLabel(parameters.get("paymentFields"));
			reportLog(appBuilderPage.selectDataIncPayment(), testContext.getName(),
					"Select one random option from dropDown", "16.0", "Successfully select option from dropDown");

			// publish to devices
			appBuilderPage.clickPublishToDevice();
			publishAppPage.clickPublishToDeviceButton();
			appBuilderPage.clickNextButton();
			publishAppPage.clickPublishButton();
			publishAppPage.clickCloseAppBuilderButton();

			// Destroy The App
			createAppPage.clickAppName(appName).destroyApp();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/**
	 * GOC-5781
	 * 
	 * @param testDescription
	 * @param testContext
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	@Parameters({ "testdescription" })
	public void editsReferenceDataTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);

			// create app
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			CreateAppPage createAppPage = new CreateAppPage(driver);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "2");

			// Add first screen and provide name adding the Number and Calculation fields.
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			boolean status = appBuilderPage.isLabelDisplayed(parameters.get("first_screen_labels").split(";")[0])
					&& appBuilderPage.isLabelDisplayed(parameters.get("first_screen_labels").split(";")[1])
					&& appBuilderPage.isLabelDisplayed(parameters.get("first_screen_labels").split(";")[2])
					&& appBuilderPage.isLabelDisplayed(parameters.get("first_screen_labels").split(";")[3]);
			reportLog(status, testContext.getName(), "User created first screen with labels", "3.0",
					"First screen created successfully");
			Assert.assertTrue(status);
			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));

			// add reference data
			String workingDir = System.getProperty("user.dir");
			String filepath = workingDir + parameters.get("reference_file_path");
			String referenceDataName = parameters.get("reference_data_name") + randomAlphaNumeric(10);
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.addReferenceDataFromCSV(parameters.get("reference_data_field"), referenceDataName,
					parameters.get("reference_data_description"), filepath);
			Map<String, List<String>> beforeEditData = appBuilderPage.viewReferenceDataTable();
			Map<String, List<String>> dataFromCSV = UtilityFunctions.getDataFromCSVFile(filepath);
			status = appBuilderPage.isIconDisplayed(parameters.get("reference_data_field"));
			reportLog(status, testContext.getName(),
					"User created first screen and applied reference data on first screen label city", "4,0",
					"Applied reference data on city label");
			org.testng.Assert.assertTrue(status);
			appBuilderPage.clickSaveButton();
			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Succesfull message",
					"4.1", "Verify Save successful appears: IS NOT AUTOMATED");
			status = appBuilderPage.verifTwoReferenceData(beforeEditData, dataFromCSV);
			reportLog(status, testContext.getName(), "Compare reference data from data table with CSV file", "4.2",
					"Data from CSV file is matching WebData table");

			// edit reference data
			String editFilepath = workingDir + parameters.get("edit_reference_file_path");
			String editRefDataName = parameters.get("reference_data_name") + randomAlphaNumeric(10);
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.editReferenceData(parameters.get("reference_data_field"), editRefDataName, editFilepath);
			status = appBuilderPage.verifyRefDataSaved();
			reportLog(status, testContext.getName(), "Verify reference data saved ", "5.0",
					"Reference saved successfully");
			appBuilderPage.clickLabel(parameters.get("reference_data_field"));
			Map<String, List<String>> afterEditDdata = appBuilderPage.viewReferenceDataTable();

			// compare before edit data and after edit data, expacted false, So put '!' for
			// condition
			status = !appBuilderPage.verifTwoReferenceData(beforeEditData, afterEditDdata);
			reportLog(status, testContext.getName(), "Verify reference data difference after edited", "5.1",
					"Reference data edited successfully");
			Assert.assertTrue(status);

			// copy field and check reference data
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.copyScreenField(parameters.get("copy_field"));
			status = appBuilderPage.compareCopyFielRefDataSame(parameters.get("copy_field"));
			reportLog(status, testContext.getName(), "Compare two screen reference data are same", "6.0",
					"Copy field are expected");

			// copy screen and check reference data
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickCopyScreenIcon();
			String copiedScreenName = parameters.get("first_screen_name") + " - copy";
			status = appBuilderPage.isScreenDisplayed(copiedScreenName);
			reportLog(status, testContext.getName(), "Verify copied screen created.", "7.0",
					copiedScreenName + " : screen created successfully.");

			status = appBuilderPage.compareCopyScreenRefData(parameters.get("copy_field"),
					parameters.get("first_screen_name"), copiedScreenName);
			reportLog(status, testContext.getName(), "Verify copied screen reference field data created", "8.0",
					copiedScreenName + " : screen reference data fields created successfully.");

			// publish to devices
			appBuilderPage.clickPublishToDevice();
			publishAppPage.clickPublishToDeviceButton();
			appBuilderPage.clickNextButton();
			publishAppPage.clickPublishButton();
			publishAppPage.clickCloseAppBuilderButton();

			// Destroy The App
			createAppPage.clickAppName(appName).destroyApp();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/**
	 * GOC-5870
	 * 
	 * @param testDescription
	 * @param testContext
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	@Parameters({ "testdescription" })
	public void multiChoiceDropDownFieldsTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);

			// create app
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			CreateAppPage createAppPage = new CreateAppPage(driver);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "2");

			// Add first screen and provide name adding the Number and Calculation fields.
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_field_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			boolean status = appBuilderPage
					.islabelArrayDisplayed(parameters.get("first_screen_field_labels").split(";"));
			reportLog(status, testContext.getName(), "User created first screen with labels", "3.0",
					"First screen created successfully");
			Assert.assertTrue(status);

			//Type Own options
			appBuilderPage.clickLabel(parameters.get("dropDown_TypeOwn"));
			status = appBuilderPage
					.typeOwnChoicesVerifyInDefaultValue(parameters.get("dropDown_type_value").split(","));
			reportLog(status, testContext.getName(), "Validate typing own choices showing in default value", "4.0",
					"All options typing by own displayed in defalut drop down.");
			Assert.assertTrue(status);
			// Common options
			appBuilderPage.clickLabel(parameters.get("dropDown_CommonList"));
			status = appBuilderPage.commonListDropDown();
			reportLog(status, testContext.getName(),
					"Select random Common List and Verify Common list value showing in Defalue dropdown", "5.0",
					"All options in common list displayed in default drop down.");
			Assert.assertTrue(status);
			// Multi Choice Type Own
			appBuilderPage.clickLabel(parameters.get("multiChoice_TypeOwn"));
			appBuilderPage.typeChoices(parameters.get("multiChoice_type_value").split(","));
			status = appBuilderPage.multiChoiceValidation(parameters.get("multiChoice_type_value").split(","));
			reportLog(status, testContext.getName(), "Validate typing own choice shoing in default value", "6.0",
					"All options typing by own displayed in default drop down");
			Assert.assertTrue(status);
			// Default Choice in DropDown/MultiChoice
			appBuilderPage.clickSaveButton();
			appBuilderPage.clickLabel(parameters.get("dropDown_default"));
			status = appBuilderPage.validateDefaultChoiceWithDefaultValue();
			reportLog(status, testContext.getName(), "Verify DropDown Default Choice", "7.0",
					"Default choice displayed in Default value drop down");
			Assert.assertTrue(status);
			appBuilderPage.clickLabel(parameters.get("multiChoice_default"));
			status = appBuilderPage.validateDefaultChoiceWithDefaultValue();
			reportLog(status, testContext.getName(), "Verify Multi Choice Default Choice", "8.0",
					"Default choice displayed in Default value drop down");
			Assert.assertTrue(status);
			
			// publish to devices
			appBuilderPage.clickPublishToDevice();
			publishAppPage.clickPublishToDeviceButton();
			appBuilderPage.clickNextButton();
			publishAppPage.clickPublishButton();
			publishAppPage.clickCloseAppBuilderButton();

			// Destroy The App
			createAppPage.clickAppName(appName).destroyApp();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/**
	 * GOC-6129 Create advanced list with 4 level loop. then drag-drop around
	 * screen.
	 * 
	 * @param testDescription
	 * @param testContext
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	@Parameters({ "testdescription" })
	public void veriyAppWithMultiLoops(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "2.0");
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_field_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			boolean status = appBuilderPage
					.islabelArrayDisplayed(parameters.get("first_screen_field_labels").split(";"));
			reportLog(status, testContext.getName(), "User created first screen with labels", "3.0",
					"First screen created successfully");
			Assert.assertTrue(status);

			// First Level
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLoopIcon();
			appBuilderPage.clickUseExistingField();
			appBuilderPage.existingFieldDropDown(parameters.get("loop_Field1"));
			appBuilderPage.clickDoneButton();
			status = appBuilderPage.isLabelDisplayed(parameters.get("loop_Field1"));
			reportLog(status, testContext.getName(), "User created first loop screen", "4.0",
					" First level Loop screen name " + parameters.get("loop_Field1") + " created successfully");
			org.testng.Assert.assertTrue(status);

			// Second Level
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLoopIcon();
			appBuilderPage.enterFieldName(parameters.get("loop_Field2"));
			appBuilderPage.clickDoneButton();
			status = appBuilderPage.isLabelDisplayed(parameters.get("loop_Field2"));
			reportLog(status, testContext.getName(), "Created Second Loop screen with loop", "5.0",
					"Second level loop screen name " + parameters.get("loop_Field2") + " created successfully");
			org.testng.Assert.assertTrue(status);

			// Third level
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLoopIcon();
			appBuilderPage.clickUseExistingField();
			appBuilderPage.existingFieldDropDown(parameters.get("loop_Field3"));
			appBuilderPage.clickDoneButton();
			status = appBuilderPage.isLabelDisplayed(parameters.get("loop_Field3"));
			reportLog(status, testContext.getName(), "Created Third Loop screen with loop", "6.0",
					"Third screen name " + parameters.get("loop_Field3") + " created successfully");
			org.testng.Assert.assertTrue(status);

			// fourth Level
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLoopIcon();
			appBuilderPage.clickUseExistingField();
			appBuilderPage.existingFieldDropDown(parameters.get("loop_Field4"));
			appBuilderPage.clickDoneButton();
			status = appBuilderPage.isLabelDisplayed(parameters.get("loop_Field4"));
			reportLog(status, testContext.getName(), "Created Fourth Loop screen with loop", "7.0",
					"Fourth Loop screen name " + parameters.get("loop_Field4") + " created successfully");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickCopyScreenIcon();
			String copiedScreenName = parameters.get("first_screen_name") + " -…";
			status = appBuilderPage.isScreenDisplayed(copiedScreenName);
			reportLog(status, testContext.getName(), "Verify copied screen created.", "8.0",
					copiedScreenName + " : screen created successfully.");

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			//
			appBuilderPage.switchTwoScreens(parameters.get("first_screen_name"), parameters.get("loop_Field1"));
			status = appBuilderPage.isLoopMovedSuccessfulluy("1");
			reportLog(status, testContext.getName(), "Verify Screen moved to different loop ", "9.0",
					" Screen moved to Under " + parameters.get("loop_Field1"));

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.switchTwoScreens(parameters.get("first_screen_name"), parameters.get("loop_Field4_Display"));
			status = appBuilderPage.isLoopMovedSuccessfulluy("4");
			reportLog(status, testContext.getName(), "Verify Screen moved to different loop ", "10.0",
					" Screen moved to Under " + parameters.get("loop_Field3"));

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.switchTwoScreens(parameters.get("first_screen_name"), parameters.get("loop_Field3"));
			status = appBuilderPage.isLoopMovedSuccessfulluy("3");
			reportLog(status, testContext.getName(), "Verify Screen moved to different loop ", "11.0",
					" Screen moved to Under " + parameters.get("loop_Field2"));

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.switchTwoScreens(parameters.get("first_screen_name"), parameters.get("loop_Field2"));
			status = appBuilderPage.isLoopMovedSuccessfulluy("2");
			reportLog(status, testContext.getName(), "Verify Screen moved to different loop ", "12.0",
					" Screen moved to Under " + parameters.get("loop_Field1"));

			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.switchTwoScreens(parameters.get("first_screen_name"), parameters.get("loop_Field1"));
			status = appBuilderPage.isLoopMovedSuccessfulluy("1");
			reportLog(status, testContext.getName(), "Verify Screen moved to different loop ", "13.0",
					" Screen moved to outSide loop " + parameters.get("loop_Field1"));

			// publish to devices
			appBuilderPage.clickPublishToDevice();
			publishAppPage.clickPublishToDeviceButton();
			appBuilderPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilderPage.clickCloseButton().clickAppName(appName).destroyApp();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	/**
	 * GOC-6728 Create loop with Calculation test, Verify calculation operator
	 * editable/save/deletion
	 * 
	 * @param testDescription
	 * @param testContext
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	@Parameters({ "testdescription" })
	public void createLoopWithCalculationTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashBoardPage = new DashboardPage(driver);
			AppBuilderPage appBuilder = new AppBuilderPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			CreateAppPage createAppPage = new CreateAppPage(driver);
			AppBuilderPage appBuilderPage = dashBoardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "2");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			boolean status = appBuilderPage
					.islabelArrayDisplayed(parameters.get("first_screen_labels").split(";"));
			reportLog(status, testContext.getName(), "User created first screen with labels", "3.0",
					"First screen created successfully");
			System.out.println("123123");
			Assert.assertTrue(status);
			System.out.println("1231234");
			appBuilderPage.clickSaveButton(appName, parameters.get("username"), parameters.get("password"));
			//create loop screen
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLoopIcon();
			appBuilderPage.enterFieldName(parameters.get("loop_Field"));
			appBuilderPage.clickDoneButton();
			status = appBuilderPage.isLabelDisplayed(parameters.get("loop_Field"));
			reportLog(status, testContext.getName(), "Created Second Loop screen with loop", "4.0",
					"Second level loop screen name " + parameters.get("loop_Field") + " created successfully");
			org.testng.Assert.assertTrue(status);
			
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("calculation"));
			//div[@class = 'field-label']//div[contains(.,'Total Sum')]
			status = appBuilderPage.verifytCalculationOperation(parameters.get("defaultOperator"));
			reportLog(status, testContext.getName(), "Verify default operator displayed", "5.0",
					"The default operator: " + parameters.get("defaultOperator") + " displayed as expected");
			org.testng.Assert.assertTrue(status);
			
			 status = appBuilderPage.openCalculationPopUp(parameters.get("calpopuptext"));
			reportLog(status, testContext.getName(), "Verfied Calculation pop up is open", "6.0",
					"Calculation pop up opened in the center after clicking on edit Calculation button Is Verified");
			org.testng.Assert.assertTrue(status);
			
			String[] operatorDropDownValue = parameters.get("operatorDropdownValues").split(";");
			String[] operatorselectedDropDownValue = parameters.get("operatorDropdownSelectedValues").split(";");
			createAppPage.verifyValuesInDropDownFieldOperator1(testContext, operatorDropDownValue, "6.0");
			createAppPage.verifyValuesInDropDownFieldOperator2(testContext, operatorDropDownValue, "6.0.1");

			createAppPage.selectFieldAndApplyOperators(testContext, operatorDropDownValue, operatorselectedDropDownValue);
			createAppPage.addConstants(testContext, operatorDropDownValue, parameters.get("numberDecimal"));
			reportLog(true, testContext.getName(), "Verfied user able to enter data for constant field", "6.2",
					"Decimal Value is entered in constant field Is Verified");
			org.testng.Assert.assertTrue(status);
			
			status = appBuilderPage.verifytCalculationOperation(parameters.get("changedOperator"));
			reportLog(status, testContext.getName(), "Verify default operator displayed", "6.3",
					"The changed operator: " + parameters.get("changedOperator") + " displayed as expected");
			org.testng.Assert.assertTrue(status);
			
			//verify X delection 
			appBuilderPage.clickEditCalculation();
			createAppPage.xToDeleteOperator(testContext,"7.0");
			createAppPage.clickDoneOnCalculationField();
			
			//Verify Cancel delection
			appBuilderPage.clickEditCalculation();
			createAppPage.noButtonCancelDeleOperator(testContext,"8.0");
			createAppPage.clickDoneOnCalculationField();
			
			//verify confirm delection
			appBuilderPage.clickEditCalculation();
			createAppPage.confirmDeleOperator(testContext,"9.0");
			createAppPage.clickDoneOnCalculationField();
			

			appBuilderPage.clickSaveButton();
			appBuilder.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();

			appBuilderPage.clickCloseButton().clickAppName(appName).destroyApp();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

}
