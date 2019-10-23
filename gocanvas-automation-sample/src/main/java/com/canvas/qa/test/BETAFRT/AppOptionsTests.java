package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.ApplicationStorePage;
import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.PublishAppPage;
import com.canvas.qa.pages.apps.AppAssignmentsPage;
import com.canvas.qa.pages.apps.AppBuilderPage;
import com.canvas.qa.pages.apps.AppsPage;
import com.canvas.qa.pages.apps.EditAppPage;
import com.canvas.qa.pages.apps.EditTableOfContentsPage;
import com.canvas.qa.pages.apps.ShowEmailOptionsPage;
import com.canvas.qa.pages.apps.ShowIntegrationOptionsPage;
import com.canvas.qa.pages.apps.ShowPDFOptionsPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class AppOptionsTests extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void shareAppBetweenDepartmentsTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = new CreateAppPage(driver);
			AppsPage appsPage = new AppsPage(driver);
			AppAssignmentsPage appAssignmentPage = new AppAssignmentsPage(driver);

			//nav to First Department
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department1"));
			
			//nav to apps list and find "poo" app
			dashboardPage.clickApp();
			createAppPage.clickAppName(parameters.get("app_name"));
			appsPage.clickDepartmentShareButton();
			appsPage.click2ndDepartmentCheckbox(true);
			appAssignmentPage.saveButtonClick();
			
			//nav to Second Department
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department2"));
			dashboardPage.clickApp();
			
			//ensure shared app displays in second department
			boolean sharedStatus = createAppPage.isAppNameShared(parameters.get("app_name"), true); //"poo" app
			reportLog(sharedStatus, testContext.getName(), "Verify the application was shared in 2nd Department", "2.0",
					parameters.get("app_name") + " :was successfully shared and appears on the Apps list within the Second Department");
			org.testng.Assert.assertTrue(sharedStatus);
			
			//ensure nav back to First Department
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department1"));
			
			//nav back to department 1 and unshare
			dashboardPage.clickApp();
			createAppPage.clickAppName(parameters.get("app_name"));
			appsPage.clickDepartmentShareButton();
			appsPage.click2ndDepartmentCheckbox(false);
			appAssignmentPage.saveButtonClick();
			
			//nav back to Second Department
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department2"));
			dashboardPage.clickApp();
			
			//ensure app no longer appears
			boolean shareStatus2 = createAppPage.isAppNameShared(parameters.get("app_name"), false); //"poo" app
			reportLog(shareStatus2, testContext.getName(), "Verify the application does not appear on Apps in 2nd Department", "3.0",
					parameters.get("app_name") + " :was successfully unshared and no longer appears within the Apps list in the Second Department");
			org.testng.Assert.assertTrue(shareStatus2);
			
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
	
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
			EditAppPage editAppPage = new EditAppPage(driver);
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
			appBuilder.clickCloseButton();
			createAppPage.clickAppName(appName);
			reportLog(true, testContext.getName(), "User clicked on newly created app " + appName, "3.0",
					"User lands on details page of " + appName + " app");

			boolean status = appsPage.verifyDestroyAppButtonIsActive();
			reportLog(status, testContext.getName(), "User is in app screen, verify Destroy App button is clickable",
					"4.0", "Destroy App Button Link exists and is clickable");
			org.testng.Assert.assertTrue(status);

			appsPage.moveMouseToDestroyButton();
			boolean textpresent = editAppPage
					.verifyAppModalText("Deletes a Published version of an app. THIS ALSO DELETES ALL SUBMISSION DATA");
			reportLog(textpresent, testContext.getName(),
					"User hovers over Destroy App Button to verify text modal appears", "4.0",
					"Text: 'Deletes a Published version of an app. THIS ALSO DELETES ALL SUBMISSION DATA' exists.");
			org.testng.Assert.assertTrue(textpresent);

			appsPage.clickRetireAppLink();

			boolean appExists = createAppPage.getNumberOfAppInstances(appName) < 1;
			reportLog(appExists, testContext.getName(),
					"User verifies if " + appName + " exists on app page after retiring app.", "5.0",
					appName + " app no longer appears on the page");
			org.testng.Assert.assertTrue(appExists);

			// clean up code
			dashboardPage.clickApp();
			createAppPage.clickFilterDDL();
			createAppPage.selectRetiredStatus();
			createAppPage.clickQuickLink(appName);
			createAppPage.clickQuickLinkMenu(appName, "Un-Retire");
			// createAppPage.clickQuickLinksButton();
			// createAppPage.clickUnRetireButton();
			dashboardPage.clickApp();
			createAppPage.clickAppName(appName);
			// createAppPage.selectPublishedStatus();
			// appsPage.appClick(appName);
			editAppPage.destroyApp();

			// Log out and Login as new user
			dashboardPage.clickLogOutButton();

			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password2"));
			loginPage.Clickonloginbutton();

			String pendingAppName = parameters.get("pending_app_name") + randomAlphaNumeric(10);

			dashboardPage.clickApp();
			createAppPage.clickAppCreateButton();
			appBuilder.createApp(pendingAppName, testContext, "7.0");
			appBuilder.clickSaveButton(pendingAppName,parameters.get("username2"),parameters.get("password2"));
			appBuilder.clickPublishToDeviceButton();

			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilder.clickCloseButton();
			createAppPage.clickAppName(pendingAppName).clickEditAppButton().clickAddScreenButton().clickSaveButton()
					.clickCloseButton();

			dashboardPage.clickApp();
			createAppPage.clickAppName(pendingAppName);
			boolean destroyAppButtonInactive = appsPage.verifyDestroyAppButtonInactive();
			reportLog(destroyAppButtonInactive, testContext.getName(),
					"Verify user unable to Destroy App when app set to 'Pending'", "6.0",
					"Destroy App button is disabled on " + pendingAppName);
			org.testng.Assert.assertTrue(destroyAppButtonInactive);

			String newStatusApp = parameters.get("new_status_app") + randomAlphaNumeric(10);

			dashboardPage.clickApp();
			createAppPage.clickAppCreateButton();
			appBuilder.createApp(newStatusApp, testContext, "7.0");
			appBuilder.clickSaveButton(newStatusApp,parameters.get("username2"),parameters.get("password2"));
			createAppPage = appBuilder.clickCloseButton();
			createAppPage.clickAppName(newStatusApp);

			boolean destroyAppButtonInactive2 = appsPage.verifyDestroyAppButtonInactive();
			reportLog(destroyAppButtonInactive2, testContext.getName(),
					"Verify user unable to Destroy App when app set to 'New'", "7.1",
					"Destroy App button is disabled on " + newStatusApp);
			org.testng.Assert.assertTrue(destroyAppButtonInactive2);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void emailOptionsTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the department designer: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = new CreateAppPage(driver);
			AppBuilderPage appBuilder = new AppBuilderPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			AppsPage appsPage = new AppsPage(driver);
			LoginPage loginPage = new LoginPage(driver);
			ShowEmailOptionsPage showEmailOptionsPage = new ShowEmailOptionsPage(driver);
			EditAppPage editAppPage = new EditAppPage(driver);
			ApplicationStorePage applicationStorePage = new ApplicationStorePage(driver);

			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			String electricWorkOrderApp = parameters.get("get_app_name");
			String userEmailLogin = (parameters.get("username"));

			// step 1:
			applicationStorePage.allAppsButtonClick();
			applicationStorePage.clickGetApp(electricWorkOrderApp);
			appsPage.appClick(electricWorkOrderApp);
			editAppPage.clickEmailOptions();

			boolean emailtextdefault = showEmailOptionsPage.verifyDefaultEmailDisplays(userEmailLogin);
			reportLog(emailtextdefault, testContext.getName(),
					"Verifies user login: " + userEmailLogin
							+ " is displayed by default in the E-mail form text field.",
					"1.2", userEmailLogin + " is displayed by default in the e-mail form text field.");
			org.testng.Assert.assertTrue(emailtextdefault);

			// step 2:
			dashboardPage.clickApp();

			createAppPage.clickAppCreateButton();
			appBuilder.createApp(appName, testContext, "2");
			appBuilder.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));

			appBuilder.clickPublishToDeviceButton();

			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilder.clickCloseButton();
			appsPage.appClick(appName);
			editAppPage.clickEmailOptions();

			boolean emailtextdefaultblank = showEmailOptionsPage.verifyDefaultEmailisEmpty();
			reportLog(emailtextdefaultblank, testContext.getName(), "Verify the E-mail form text field is empty.",
					"2.3", "Default E-mail form text field is empty.");
			org.testng.Assert.assertTrue(emailtextdefaultblank);

			// step 3:
			dashboardPage.clickLogOutButton();

			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password2"));
			loginPage.Clickonloginbutton();

			dashboardPage.clickApp();
			appsPage.appClick(appName);
			editAppPage.clickEmailOptions();

			boolean canemailPDFtoAnyoneRadioBox = showEmailOptionsPage.verifyCanEmailPDFtoAnyoneIsSelected();
			reportLog(canemailPDFtoAnyoneRadioBox, testContext.getName(),
					"Verify the 'Can email the PDF to anyone' radio checkbox is selected by default", "3.0",
					"'Can email the PDF to anyone' radio checkbox is selected by default");
			org.testng.Assert.assertTrue(canemailPDFtoAnyoneRadioBox);

			boolean bccFieldRadioBox = showEmailOptionsPage.verifyCanEmailPDFtoAnyoneIsSelected();
			reportLog(bccFieldRadioBox, testContext.getName(), "Verify the 'BCC' radio checkbox is selected by default",
					"3.1", "'BCC' radio checkbox is selected by default");
			org.testng.Assert.assertTrue(bccFieldRadioBox);

			boolean linkRadioBox = showEmailOptionsPage.verifyCanEmailPDFtoAnyoneIsSelected();
			reportLog(linkRadioBox, testContext.getName(),
					"Verify the 'Link' radio checkbox is selected by default for PDF Delivery Options", "3.2",
					"'Link' radio checkbox is selected by default for PDF Delivery Options");
			org.testng.Assert.assertTrue(linkRadioBox);

			boolean showWordsAutoResultsCheckBox = showEmailOptionsPage.verifyCanEmailPDFtoAnyoneIsSelected();
			reportLog(showWordsAutoResultsCheckBox, testContext.getName(),
					"Verify the 'Show the words 'Auto-Results' in subject line' checkbox is selected by default", "3.3",
					"'Show the words 'Auto-Results' in subject line'' checkbox is selected by default");
			org.testng.Assert.assertTrue(showWordsAutoResultsCheckBox);

			// step 4:
			dashboardPage.clickLogOutButton();

			loginPage.typeusername(parameters.get("username"));
			loginPage.typepassword(parameters.get("password"));
			loginPage.Clickonloginbutton();

			dashboardPage.clickApp();
			appsPage.appClick(parameters.get("app_name2"));
			editAppPage.clickEmailOptions();

			showEmailOptionsPage.uploadLogo();

			boolean toastMsg = createAppPage.verifyToastMsgIsDisplayed();
			reportLog(toastMsg, testContext.getName(), "Verify the successful toast message appears", "4.0",
					"Successful toast message appears.");
			org.testng.Assert.assertTrue(toastMsg);

			editAppPage.clickEmailOptions();

			boolean imageText = showEmailOptionsPage.verifyLogoUploadTextPresent(parameters.get("imagetext"));
			reportLog(imageText, testContext.getName(), "Verify the uploaded image text appears", "4.1",
					"Image Text successfully appears");
			org.testng.Assert.assertTrue(imageText);

			showEmailOptionsPage.clickAppsBreadCrumb();

			// step 5: (user does not log back out)
			boolean createButton = createAppPage.isAppCreateButtonDisplayed();
			reportLog(createButton, testContext.getName(), "Verify user lands back on create app page", "5.0",
					"user successfully lands back on Create App page");
			org.testng.Assert.assertTrue(createButton);

			// clean up code - destroy App
			appsPage.appClick(appName);
			editAppPage.destroyApp();

			appsPage.appClick(electricWorkOrderApp);
			editAppPage.destroyApp();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void integrationOptionsTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {

		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = dashboardPage.clickApp();
			EditAppPage editAppPage = createAppPage.clickAppName(parameters.get("app"));
			ShowIntegrationOptionsPage showIntegrationOptionsPage = editAppPage.clickIntegerationOptions();
			String[] optionList = parameters.get("options_list").split(";");
			for (int i = 0; i < optionList.length; i++) {
				boolean status = showIntegrationOptionsPage.isOptionDisplayed(optionList[i]);
				reportLog(status, testContext.getName(), "Integration option: " + optionList[i], "1.1." + i,
						"Is displayed successfully");
				org.testng.Assert.assertTrue(status);
			}
			
			//pre-condition checkpoint to ensure disconnect button was properly triggered on last run. If not, then disconnect
			showIntegrationOptionsPage.disconnectButtonCheck();
			//end check

			showIntegrationOptionsPage.clickIntegrateButton(parameters.get("option1"));
			showIntegrationOptionsPage.clickAuthorizeButton();
			customWait(1);
			String winHandleBefore = driver.getWindowHandle();
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}
		
			driver.findElement(By.id("login")).sendKeys(parameters.get("box_username"));
			driver.findElement(By.id("password")).sendKeys(parameters.get("box_password"));
			driver.findElement(By.xpath("//input[@name = 'login_submit']")).click();
			driver.findElement(By.id("consent_accept_button")).click();
			customWait(2);
			driver.switchTo().window(winHandleBefore);
			
			showIntegrationOptionsPage = new ShowIntegrationOptionsPage(driver);
			showIntegrationOptionsPage.clickSaveButton();
			boolean status = showIntegrationOptionsPage.getToastMessage().equals(parameters.get("message"));
			reportLog(status, testContext.getName(), "Validate toast message", "2.1",
					parameters.get("message") + " : message is displayed successfully");
			org.testng.Assert.assertTrue(status);

			//kp Below line commenting because connected not display after new UI of Integration option 
			
			/*status = showIntegrationOptionsPage.isConnectedDisplayed();
			reportLog(status, testContext.getName(), "After integrating box", "3.1",
					"Connected is displayed successfully");
			org.testng.Assert.assertTrue(status);*/

			status = showIntegrationOptionsPage.isManageButtonDisplayed(parameters.get("option1"));
			reportLog(status, testContext.getName(), "After integrating box", "3.2",
					"Manage button is displayed successfully");
			org.testng.Assert.assertTrue(status);

			status = showIntegrationOptionsPage.isDisconnectButtonDisplayed();
			reportLog(status, testContext.getName(), "After integrating box", "3.3",
					"Disconnect button is displayed successfully");
			org.testng.Assert.assertTrue(status);

			LoginPage login = showIntegrationOptionsPage.clickLogOutButton();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbutton();
			createAppPage = dashboardPage.clickApp();
			editAppPage = createAppPage.clickAppName(parameters.get("app"));
			showIntegrationOptionsPage = editAppPage.clickIntegerationOptions();
			String[] disabledList = parameters.get("disabled_integrate_list").split(";");
			for (int i = 0; i < disabledList.length; i++) {
				status = showIntegrationOptionsPage.isIntegrateButtonDisabled(disabledList[i]);
				reportLog(status, testContext.getName(), "Integrate button for: " + disabledList[i], "4.1." + i,
						"Is disabled");
				org.testng.Assert.assertTrue(status);
			}

			showIntegrationOptionsPage.clickDisconnectButton(parameters.get("option1"));
			status = showIntegrationOptionsPage.isIntegrationButtonDisplayed(parameters.get("option1"));
			reportLog(status, testContext.getName(), "After clicking disconnect button", "5.0",
					"Integration with Box was disconnected successfully");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void retireAppTest(String testDescription, ITestContext testContext)
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
			ShowPDFOptionsPage showPDFOptionsPage = new ShowPDFOptionsPage(driver);
			EditAppPage editAppPage = new EditAppPage(driver);

			String appName = parameters.get("app_name") + randomAlphaNumeric(10);

			dashboardPage.clickApp();

			createAppPage.clickAppCreateButton();
			appBuilder.createApp(appName, testContext, "1.2");
			appBuilder.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));

			appBuilder.clickPublishToDeviceButton();

			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilder.clickCloseButton();
			createAppPage.clickAppName(appName);

			boolean isRetireAppActive = appsPage.verifyRetireAppButtonIsActive();
			reportLog(isRetireAppActive, testContext.getName(), "Verify Retire App button is active", "1.3",
					"User has option to retire app");
			org.testng.Assert.assertTrue(isRetireAppActive);

			appsPage.clickRetireAppButton();
			boolean toastdisplayed = showPDFOptionsPage.verifyToastMsgIsDisplayed();
			reportLog(toastdisplayed, testContext.getName(),
					"Verify 'Retire App Version 1' success toast message displays on page", "2.0",
					"Toast message successfully displayed.");
			org.testng.Assert.assertTrue(toastdisplayed);

			dashboardPage.clickApp();
			boolean appExists = appsPage.verifyAppNoLongerExists(appName);
			reportLog(appExists, testContext.getName(),
					"User verifies if " + appName + " exists on app page after retiring app.", "3.0",
					appName + " app no longer appears on the page");
			org.testng.Assert.assertTrue(appExists);

			// step 3: filter apps by Retired status
			createAppPage.clickFilterDDL();
			createAppPage.selectRetiredStatus();

			// step 3.1: verify app appears in retired filter
		boolean retiredAppExists = createAppPage.isAppNameDisplayed(appName);
			reportLog(retiredAppExists, testContext.getName(),
					"User verifies if " + appName + " exists after filtering for retired apps.", "3.1",
					appName + " app exists on page after filtering for 'Retired'");
			org.testng.Assert.assertTrue(retiredAppExists);
			// step 3.2: verify "retired" status appears
			boolean retiredAppStatus = createAppPage.verifyRetiredAppStatus();
			reportLog(retiredAppStatus, testContext.getName(),
					"User verifies if " + appName + " status is set to 'Retired.'", "3.2",
					appName + " app's status is set to 'Retired'");
			org.testng.Assert.assertTrue(retiredAppStatus);

			// step 4: un-retire the app
			createAppPage.clickQuickLink(appName);
			createAppPage.clickQuickLinkMenu(appName, "Un-Retire");
			// createAppPage.clickUnRetireButton();
			boolean unretiretoastdisplayed = showPDFOptionsPage.verifyToastMsgIsDisplayed();
			reportLog(unretiretoastdisplayed, testContext.getName(),
					"Verify un-Retired App success toast message displays on page", "4.0",
					"Toast message successfully displayed.");
			org.testng.Assert.assertTrue(unretiretoastdisplayed);

			// createAppPage.selectPublishedStatus();
			dashboardPage.clickApp();
			boolean unretiredAppPublishedStatus = createAppPage.getAppStatus(appName).contains("Published");
			reportLog(unretiredAppPublishedStatus, testContext.getName(),
					"User verifies if " + appName + " exists after filtering for published apps.", "4.1",
					appName + " app exists on page after filtering for 'Published' (Version Check NOT AUTOMATED)");
			org.testng.Assert.assertTrue(unretiredAppPublishedStatus);

			// clean up code - destroy App
			createAppPage.clickAppName(appName);
			editAppPage.destroyApp();
			customWait(5);

			// Log out and Login as new user
			dashboardPage.clickLogOutButton();

			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password2"));
			loginPage.Clickonloginbutton();

			String newStatusApp = parameters.get("new_status_app") + randomAlphaNumeric(10);

			dashboardPage.clickApp();

			createAppPage.clickAppCreateButton();
			appBuilder.createApp(newStatusApp, testContext, "5.0");
			appBuilder.clickSaveButton(newStatusApp,parameters.get("username2"),parameters.get("password2"));

			appBuilder.clickPublishToDeviceButton();

			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilder.clickCloseButton();
			createAppPage.clickAppName(newStatusApp);

			editAppPage.moveMouseToRetireButton();
			boolean retiretextpresent = editAppPage
					.verifyAppModalText("Removes a Published app from all mobile devices");
			reportLog(retiretextpresent, testContext.getName(),
					"User hovers over Retire App Button to verify text modal appears", "5.1",
					"Text: 'Removes a Published app from all mobile devices' exists.");
			org.testng.Assert.assertTrue(retiretextpresent);

			editAppPage.destroyApp();
			customWait(5);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void tableOfContentsTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the Company designer: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			EditTableOfContentsPage editTableOfContentsPage = new EditTableOfContentsPage(driver);
			String appName = randomAlphaNumeric(10);
			CreateAppPage createAppPage = dashboardPage.clickApp();
			AppBuilderPage appBuilderPage = createAppPage.clickAppCreateButton();
			appBuilderPage.createAppWithScreen(appName);
			appBuilderPage.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));
			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			publishAppPage.clickCloseButton();
			EditAppPage editAppPage = createAppPage.clickAppName(appName);

			boolean status = editAppPage.isTableOfContentsLinkDisplayed();
			reportLog(status, testContext.getName(), "Verify Table of Contents displayed", "1.2",
					"Table of Contents option displayed in  apps details page.");
			org.testng.Assert.assertTrue(status);

			LoginPage loginPage = editAppPage.clickLogOutButton();

			loginPage.typeusername(parameters.get("username1"));
			loginPage.typepassword(parameters.get("password"));
			loginPage.Clickonloginbutton();
			String appName2 = randomAlphaNumeric(10);
			createAppPage = dashboardPage.clickApp();
			appBuilderPage = createAppPage.clickAppCreateButton();
			appBuilderPage.createAppWithScreen(appName2);
			appBuilderPage.clickSaveButton(appName2,parameters.get("username1"),parameters.get("password"));
			appBuilderPage.clickPublishToDeviceButton().clickNextPublishButton().clickPublishButton();
			appBuilderPage.clickCloseButton();
			editAppPage = createAppPage.clickAppName(appName2);

			status = editTableOfContentsPage.isTableOfContentsEnabledChecked();
			reportLog(!status, testContext.getName(), "Verify by default TOC Option is disable", "2.0",
					"TOC Option is disable by default?");
			org.testng.Assert.assertFalse(status);

			boolean tocEnable = editTableOfContentsPage.clickTableOfContentsCheckBox(parameters.get("message1"));
			reportLog(tocEnable, testContext.getName(),
					"Verify user sees, Table Of Contents has been successfully enabled", "3.0",
					"User sees, Table Of Contents has been successfully enabled");
			org.testng.Assert.assertTrue(tocEnable);

			loginPage = editAppPage.clickLogOutButton();
			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password"));
			loginPage.Clickonloginbutton();
			createAppPage = dashboardPage.clickApp();
			editAppPage = createAppPage.clickAppName(appName2);

			boolean tocDisable = editTableOfContentsPage.clickTableOfContentsCheckBox(parameters.get("message2"));
			reportLog(tocDisable, testContext.getName(), "Table Of Contents has been successfully enabled", "4.0",
					"Table Of Contents has been successfully disabled");
			org.testng.Assert.assertTrue(tocDisable);

			editAppPage.deleteApp();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
