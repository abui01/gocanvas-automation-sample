package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.ApplicationStorePage;
import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.CreateFolderPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.EditWorkflowPage;
import com.canvas.qa.pages.FolderPage;
import com.canvas.qa.pages.PublishAppPage;
import com.canvas.qa.pages.apps.AppBuilderPage;
import com.canvas.qa.pages.apps.AppsPage;
import com.canvas.qa.pages.apps.EditAppPage;
import com.canvas.qa.pages.apps.EditCompressionOptionsPage;
import com.canvas.qa.pages.apps.EditDispatchEnabledPage;
import com.canvas.qa.pages.apps.EditEditingOptionsPage;
import com.canvas.qa.pages.apps.EditRemRecallPage;
import com.canvas.qa.pages.apps.EditSubmissionStatusPage;
import com.canvas.qa.pages.apps.EditTableOfContentsPage;
import com.canvas.qa.pages.apps.ShareTemplatePage;
import com.canvas.qa.pages.apps.ShowPaymentOptionsPage;
import com.canvas.qa.pages.apps.TemplatesPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class AppTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void compressionTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			EditAppPage editAppPage = dashboardPage.clickApp().clickAppName(parameters.get("app"));
			boolean status = editAppPage.isCompressionLinkDisplayed();
			reportLog(status, testContext.getName(), "Verify Compression Link displayed", "1.1",
					"Compression Link displayed successfully");
			org.testng.Assert.assertTrue(status);

			EditCompressionOptionsPage editCompressionOptionsPage = editAppPage.clickCompressionLink();
			customWait(2);
			status = editCompressionOptionsPage.getImageQualityValue().contains(parameters.get("image_quality1"));
			
			//pre-cond check - if script failed prior to resetting back to image_quality1
			if (status==false)
			{
				editCompressionOptionsPage.selectImageQuality(parameters.get("image_quality1"));
				editCompressionOptionsPage.clickSaveButton();
				editCompressionOptionsPage = editAppPage.clickCompressionLink();
				status = editCompressionOptionsPage.getImageQualityValue().contains(parameters.get("image_quality1"));
			}
			//end pre-cond checkpoint
			
			reportLog(status, testContext.getName(), "Verify Image quality", "2.0",
					parameters.get("image_quality1") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = editCompressionOptionsPage.getCompressionQuality()
					.contains(parameters.get("compression_quality1"));
			reportLog(status, testContext.getName(), "Verify Compression Quality", "2.1",
					parameters.get("compression_quality1") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = editCompressionOptionsPage.getCompressionDimension()
					.contains(parameters.get("compression_height1"));
			reportLog(status, testContext.getName(), "Verify Compression Dimension", "2.2",
					parameters.get("compression_height1") + " displayed");
			org.testng.Assert.assertTrue(status);

			editCompressionOptionsPage.selectImageQuality(parameters.get("image_quality2"));
			status = editCompressionOptionsPage.getCompressionQuality()
					.contains(parameters.get("compression_quality2"));
			reportLog(status, testContext.getName(), "Verify Compression Quality", "3.0",
					parameters.get("compression_quality2") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = editCompressionOptionsPage.getCompressionDimension()
					.contains(parameters.get("compression_height2"));
			reportLog(status, testContext.getName(), "Verify Compression Dimension", "3.1",
					parameters.get("compression_height2") + " displayed");
			org.testng.Assert.assertTrue(status);

			editAppPage = editCompressionOptionsPage.clickSaveButton();
			status = editAppPage.getToastMessage().contains(parameters.get("message"));
			reportLog(status, testContext.getName(), "Verify Toast Message", "3.2",
					parameters.get("message") + " : message displayed");
			org.testng.Assert.assertTrue(status);

			editCompressionOptionsPage = editAppPage.clickCompressionLink();
			editCompressionOptionsPage.selectImageQuality(parameters.get("image_quality3"));
			editAppPage = editCompressionOptionsPage.clickCancelButton();
			status = editAppPage.isEditAppDisplayed();
			reportLog(status, testContext.getName(), "Verify App Details page displayed", "4.0",
					"App Details page displayed successfully");
			org.testng.Assert.assertTrue(status);

			LoginPage loginPage = editAppPage.clickLogOutButton();
			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password2"));
			loginPage.Clickonloginbutton();
			editAppPage = dashboardPage.clickApp().clickAppName(parameters.get("app"));
			editCompressionOptionsPage = editAppPage.clickCompressionLink();
			CreateAppPage createAppPage = editCompressionOptionsPage.clickAppLink();
			status = createAppPage.isAppCreateButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify App page displayed", "5.0",
					"User was successfully directed back to the Apps Page by clicking on Breadcrumb. ");
			org.testng.Assert.assertTrue(status);

			createAppPage.clickAppName(parameters.get("app"));
			editCompressionOptionsPage = editAppPage.clickCompressionLink();
			editCompressionOptionsPage.selectImageQuality(parameters.get("image_quality1"));
			editCompressionOptionsPage.clickSaveButton();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void copyAppTest(String testDescription, ITestContext testContext) throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			EditTableOfContentsPage editTableOfContentsPage = new EditTableOfContentsPage(driver);
			EditRemRecallPage editRemRecallPage = new EditRemRecallPage(driver);
			CreateAppPage createAppPage = dashboardPage.clickApp();
			createAppPage.deleteEachInstanceOfApp(parameters.get("app"));
			createAppPage.retireEachInstanceOfApp(parameters.get("app"));
			createAppPage.deleteEachInstanceOfApp(parameters.get("app3"));
			createAppPage.retireEachInstanceOfApp(parameters.get("app3"));
			createAppPage.clickQuickLink(parameters.get("app1"));
			createAppPage.clickQuickLinkMenu(parameters.get("app1"), "Copy");

			EditAppPage editAppPage = createAppPage.clickAppName(parameters.get("app"));
			ShowPaymentOptionsPage showPaymentOptionsPage = editAppPage.clickPaymentOptionsLink();
			boolean status = showPaymentOptionsPage.isDisconnectDisplayed();
			reportLog(status, testContext.getName(), "Disconnect displayed for PayPal", "1.2",
					"Disconnect dispalyed for PayPal");
			org.testng.Assert.assertTrue(status);

			editAppPage = showPaymentOptionsPage.clickAppNameLink(parameters.get("app"));
			EditCompressionOptionsPage editCompressionOptionsPage = editAppPage.clickCompressionLink();

			status = editCompressionOptionsPage.getImageQualityValue().contains(parameters.get("image_quality"));
			reportLog(status, testContext.getName(), "Verify Image Quality value", "2.0",
					"Image quality value correctly displayed");
			org.testng.Assert.assertTrue(status);

			editAppPage = editCompressionOptionsPage.clickAppNameLink(parameters.get("app"));
			EditSubmissionStatusPage editSubmissionStatusPage = editAppPage.clickSubmissionStatusLink();
			status = editSubmissionStatusPage.getStatus().contains(parameters.get("status"));
			reportLog(status, testContext.getName(), "Verify Submisiion status", "3.0",
					"Submission status correctly displayed");
			org.testng.Assert.assertTrue(status);

			editAppPage = editSubmissionStatusPage.clickAppNameLink(parameters.get("app"));
			EditDispatchEnabledPage editDispatchEnabledPage = editAppPage.clickDispatchLink();
			status = editDispatchEnabledPage.isDispatchEnabledChecked();
			reportLog(status, testContext.getName(), "Verify Dispatch Enabled", "4.0", "Dispatch Enabled Checked");
			org.testng.Assert.assertTrue(status);

			editAppPage = editDispatchEnabledPage.clickAppNameLink(parameters.get("app"));
			// EditTableOfContentsPage editTableOfContentsPage =
			// editAppPage.clickTableOfContentsLink();
			status = editTableOfContentsPage.isTableOfContentsEnabledChecked();
			reportLog(status, testContext.getName(), "Verify Table of Content Enabled", "5.0",
					"Table of Content Enabled checked");
			org.testng.Assert.assertTrue(status);

			// editAppPage =
			// editTableOfContentsPage.clickAppNameLink(parameters.get("app"));
			// EditRemRecallPage editRemRecallPage =
			// editAppPage.clickRemAndRecallLink();
			status = editRemRecallPage.isRemRecallEnabledChecked();
			reportLog(status, testContext.getName(), "Verify Remember and Recall enabled", "6.0",
					"Remember and Recall enabled checked");
			org.testng.Assert.assertTrue(status);

			// editAppPage =
			// editRemRecallPage.clickAppNameLink(parameters.get("app"));
			EditEditingOptionsPage editEditingOptionsPage = editAppPage.clickEditAndViewLink();
			status = editEditingOptionsPage.isAdminEditChecked();
			reportLog(status, testContext.getName(), "Verify Admin Edit", "7.0", "Admin Edit Checked");
			org.testng.Assert.assertTrue(status);

			status = editEditingOptionsPage.isAdminDeleteChecked();
			reportLog(status, testContext.getName(), "Verify Admin Delete", "7.1", "Admin Delete checked");
			org.testng.Assert.assertTrue(status);

			status = editEditingOptionsPage.isDesignersEditChecked();
			reportLog(status, testContext.getName(), "Verify Designer Edit", "7.2", "Designer Edit checked");
			org.testng.Assert.assertTrue(status);

			status = editEditingOptionsPage.isDesignersDeleteChecked();
			reportLog(status, testContext.getName(), "Verify Designer Delete", "7.3", "Designer Delete checked");
			org.testng.Assert.assertTrue(status);

			status = editEditingOptionsPage.isReportersEditChecked();
			reportLog(status, testContext.getName(), "Verify Reporter Edit", "7.4", "Reporter Edit checked");
			org.testng.Assert.assertTrue(status);

			status = editEditingOptionsPage.isReportersDeleteChecked();
			reportLog(status, testContext.getName(), "Verify Reporter Delete", "7.5", "Reporter Delete checked");
			org.testng.Assert.assertTrue(status);

			status = editEditingOptionsPage.isUserEditChecked();
			reportLog(status, testContext.getName(), "Verify User Edit", "7.6", "User Edit checked");
			org.testng.Assert.assertTrue(status);

			status = editEditingOptionsPage.isUserDeleteChecked();
			reportLog(status, testContext.getName(), "Verify User Delete", "7.7", "User Delete checked");
			org.testng.Assert.assertTrue(status);

			status = editEditingOptionsPage.isMobileEditChecked();
			reportLog(status, testContext.getName(), "Verify Mobile Edit", "7.8", "Mobile Edit checked");
			org.testng.Assert.assertTrue(status);

			status = editEditingOptionsPage.isMobileViewPDFChecked();
			reportLog(status, testContext.getName(), "Verify Mobile View PDF", "7.9", "Mobile View PDF checked");
			org.testng.Assert.assertTrue(status);

			status = editEditingOptionsPage.isResetDateEditWebChecked();
			reportLog(!status, testContext.getName(), "Verify Reset Date Web", "7.10", "Reset Date Web unchecked");
			org.testng.Assert.assertFalse(status);

			status = editEditingOptionsPage.isResetDateEditMobileChecked();
			reportLog(status, testContext.getName(), "Verify Reset Date Mobile", "7.11", "Reset Date Mobile checked");
			org.testng.Assert.assertTrue(status);

			createAppPage = editEditingOptionsPage.clickAppLink();
			createAppPage.clickQuickLink(parameters.get("app2"));
			createAppPage.clickQuickLinkMenu(parameters.get("app2"), "Copy");
			editAppPage = createAppPage.clickAppName(parameters.get("app3"));
			EditWorkflowPage editWorkflowPage = editAppPage.clickWorkflowLink();
			status = editWorkflowPage.isEnableWorkflowChecked();
			reportLog(status, testContext.getName(), "Verify Workflow Enabled", "8.0", "Workflow Enabled checked");
			org.testng.Assert.assertTrue(status);

			status = editWorkflowPage.isHandOffSubmitterSelected();
			reportLog(status, testContext.getName(), "Verify Which user 'owns' the submission", "8.1",
					"The submitter of the handoff is selected");
			org.testng.Assert.assertTrue(status);

			editAppPage = editWorkflowPage.clickAppNameLink(parameters.get("app3"));
			createAppPage = editAppPage.deleteApp();
			customWait(5);

			createAppPage.deleteApp(parameters.get("app"));
			customWait(5);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void createTemplateTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as : " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = new CreateAppPage(driver);
			AppBuilderPage appBuilder = new AppBuilderPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			String appName = randomAlphaNumeric(10);
			dashboardPage.clickApp();
			createAppPage.clickAppCreateButton();
			appBuilder.createApp(appName, testContext, "1.2");
			appBuilder.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));
			appBuilder.clickPublishToDeviceButton();
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilder.clickCloseButton();
			customWait(2);
			LoginPage loginPage = createAppPage.clickLogOutButton();
			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password2"));
			loginPage.Clickonloginbutton();

			EditAppPage editAppPage = dashboardPage.clickApp().clickAppName(appName);
			editAppPage.clickCreateTemplateLink();
			createAppPage = editAppPage.clickAppLink();
			TemplatesPage templatesPage = createAppPage.clickTemplateViewLink();
			boolean status = templatesPage.isTemplateDisplayed(appName);
			reportLog(status, testContext.getName(), "Verify Template displayed", "1.2",
					appName + " template displayed");
			org.testng.Assert.assertTrue(status);

			String[] actionList = parameters.get("action_list").split(";");
			for (int i = 0; i < actionList.length; i++) {
				status = templatesPage.isActionItemDisplayed(appName, actionList[i]);
				reportLog(status, testContext.getName(), "Verify action items", "1.3." + i,
						actionList[i] + " item displayed");
				org.testng.Assert.assertTrue(status);
			}

			AppBuilderPage appBuilderPage = templatesPage.clickActionItemEdit(appName, actionList[0]);
			appBuilderPage.addLabelToScreen(parameters.get("first_screen_name"),
					parameters.get("extra_first_screen_labels").split(";"),
					parameters.get("extra_first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton();
			appBuilderPage.clickCloseButton();
			createAppPage = templatesPage.clickAppLink();
			status = (createAppPage.getNumberOfAppInstances(appName) == 1);
			reportLog(status, testContext.getName(), "Verify App instances", "2.0",
					"There is still only 1 instance of the app: " + appName);
			org.testng.Assert.assertTrue(status);

			templatesPage = createAppPage.clickTemplateViewLink();
			createAppPage = templatesPage.clickActionItemShareForNotAuthorized(appName, actionList[2]);
			status = createAppPage.getToastMsg().contains(parameters.get("message"));
			reportLog(status, testContext.getName(), "Verify toast message", "3.0",
					parameters.get("message") + " : message displayed");
			org.testng.Assert.assertTrue(status);

			loginPage = createAppPage.clickLogOutButton();
			loginPage.typeusername(parameters.get("username"));
			loginPage.typepassword(parameters.get("password"));
			loginPage.Clickonloginbutton();
			templatesPage = dashboardPage.clickApp().clickTemplateViewLink();
			ShareTemplatePage shareTemplatePage = templatesPage.clickActionItemShareForAuthorized(appName,actionList[2]);
			shareTemplatePage.enterTemplateDescription(parameters.get("Description"));
			templatesPage = shareTemplatePage.clickShareButton();
			status = templatesPage.isActionItemDisplayedDisabled(appName, actionList[2]);
			reportLog(status, testContext.getName(), "Verify share item", "4.0", "Share item disabled");
			org.testng.Assert.assertTrue(status);

			createAppPage = templatesPage.clickActionItemCreateApp(appName, actionList[1]);
			status = createAppPage.isAppVersionDisplayed(appName, parameters.get("version"));
			reportLog(status, testContext.getName(), "Verify app appears in New status", "5.0",
					"App appears in New status");
			org.testng.Assert.assertTrue(status);

			templatesPage = createAppPage.clickTemplateViewLink();
			templatesPage.clickActionItemDelete(appName, actionList[3]);
			status = templatesPage.isTemplateDisplayed(parameters.get("app"));
			reportLog(!status, testContext.getName(), "Verify the template no longer appears", "6.0",
					"Template no longer appears");
			org.testng.Assert.assertFalse(status);

			createAppPage = templatesPage.clickAppLink();
			createAppPage.deleteApp(appName);
			customWait(5);

			dashboardPage.clickApp();
			createAppPage.retireEachInstanceOfApp(appName);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void destroyAppTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			String appName = randomAlphaNumeric(10);
			appBuilderPage.createApp(appName, testContext, "1.2");
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));
			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();

			CreateAppPage createAppPage = publishAppPage.clickNextButton().finalPublishButtonClick().clickCloseButton();
			EditAppPage editAppPage = createAppPage.clickAppName(appName);
			boolean status = editAppPage.isDestroyAppButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify user sees Destroy App icon under Actions.", "1.3",
					"Destroy App icon displayed.");
			org.testng.Assert.assertTrue(status);
			String message1 = "This will delete all versions of this app AND all submissions.  Are you sure you want to destroy '"
					+ appName + "'?";
			String message2 = "'" + appName + "' and all submissions for this app have been destroyed.";
			createAppPage = editAppPage.destroyApp(message1, "2.0", testContext);
			status = createAppPage.getToastMsg().contains(message2);
			reportLog(status, testContext.getName(), "Verify toast message", "3.0",
					"'New App' and all submissions for this app have been destroyed message displayed.");
			org.testng.Assert.assertTrue(status);

			LoginPage login = createAppPage.clickLogOutButton();
			login.typeusername(parameters.get("username"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			createAppPage = dashboardPage.clickApp();
			editAppPage = createAppPage.clickAppName(parameters.get("app2"));
			status = editAppPage.isDisabledDestroyAppButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify the Destroy App button is disabled.", "4.0",
					"The Destroy App button is disabled.");
			org.testng.Assert.assertTrue(status);

			createAppPage = editAppPage.clickAppLink();
			editAppPage = createAppPage.clickAppName(parameters.get("app3"));
			status = editAppPage.isDisabledDestroyAppButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify the Destroy App button is disabled.", "5.0",
					"The Destroy App button is disabled.");
			org.testng.Assert.assertTrue(status);
			
			createAppPage = editAppPage.clickAppLink();
			//pre-cond test
			if (createAppPage.getNumberOfAppInstances(parameters.get("app4")) > 0)
			{
				editAppPage = createAppPage.clickAppName(parameters.get("app4"));
				editAppPage.destroyApp();
			}
			//end pre-cond test
			ApplicationStorePage applicationStorePage = createAppPage.clickApplicationStore();
			applicationStorePage.clickViewAllApps();
			createAppPage = applicationStorePage.clickGetApp(parameters.get("app4"));
			editAppPage = createAppPage.clickAppName(parameters.get("app4"));
			editAppPage.destroyApp();
			status = (createAppPage.getNumberOfAppInstances(parameters.get("app4")) == 0);
			reportLog(status, testContext.getName(),
					"Verify our app that we got from the application store does NOT display on Apps page", "6.0",
					"'Electrical Work Order w/Checklist - Deluxe 6520' does not appear on Apps page.");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/* TC11505 : Edit App description */
	@Test
	@Parameters({ "testdescription" })
	public void editDescriptionApp(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			EditAppPage editAppPage = new EditAppPage(driver);
			AppsPage appPage = new AppsPage(driver);
			appBuilderPage.createApp(appName);
			appBuilderPage.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));
			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			CreateAppPage createAppPage = publishAppPage.clickNextButton().nextButtonClick().finalPublishButtonClick().clickCloseButton();
			createAppPage.clickAppName(appName);
			editAppPage.editAppDescription(parameters.get("app_desc"));

			String toastMsg = "App '" + appName + "'" + " has been successfully updated";

			boolean status = editAppPage.verifyMsg(toastMsg);
			reportLog(status, testContext.getName(), "Verify message after description saved", "2.1",
					"App Description added and saved message verified");
			org.testng.Assert.assertTrue(status);

			appPage.appsButtonClick();
			createAppPage.clickAppName(appName);
			editAppPage.updatedEditAppDescription(parameters.get("app_desc_new"));

			String toastMsgAfterUpdate = "App '" + appName + "'" + " has been successfully updated";

			boolean updatedStatus = editAppPage.verifyMsg(toastMsgAfterUpdate);
			reportLog(updatedStatus, testContext.getName(), "Verify message clearing description add new and saved",
					"3.1", "Updated app Description added and saved message verified");
			org.testng.Assert.assertTrue(updatedStatus);

			editAppPage.destroyApp();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	@Test
	@Parameters({ "testdescription" })
	public void editPendingVersionTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			appBuilderPage.createApp(appName, testContext, "1.2");
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));
			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton().clickNextPublishButton();
			publishAppPage.clickNextPublishButton().checkAllUserCheckbox();
			CreateAppPage createAppPage = publishAppPage.clickPublishButton().clickCloseButton();
			EditAppPage editAppPage = createAppPage.clickAppName(appName);
			appBuilderPage = editAppPage.clickEditAppButton();
			appBuilderPage.addScreenAndAddLabel(parameters.get("second_screen_place_holder"),
					parameters.get("second_screen_name"), parameters.get("second_screen_labels").split(";"),
					parameters.get("second_screen_fields").split(";"));
			appBuilderPage.clickSaveButton();
			createAppPage = appBuilderPage.clickCloseButton();
			boolean status = createAppPage.getAppStatus(appName).contains(parameters.get("app1_status1"));
			reportLog(status, testContext.getName(), "Verify app now has a status set to Pending", "1.3.1",
					"App went from status Published to Pending");
			org.testng.Assert.assertTrue(status);

			status = createAppPage.getAppVersion(appName).contains(parameters.get("app1_version1"));
			reportLog(status, testContext.getName(), "Verify app now has version2", "1.3.2", "App has version2");
			org.testng.Assert.assertTrue(status);

			createAppPage.logout();
			LoginPage loginPage = new LoginPage(driver);
			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password2"));
			loginPage.Clickonloginbutton();
			createAppPage = dashboardPage.clickApp();
			editAppPage = createAppPage.clickAppName(appName);
			appBuilderPage = editAppPage.clickEditAppButton();
			appBuilderPage.addScreenAndAddLabel(parameters.get("third_screen_place_holder"),
					parameters.get("third_screen_name"), parameters.get("third_screen_labels").split(";"),
					parameters.get("third_screen_fields").split(";"));
			appBuilderPage.clickSaveButton();
			publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			createAppPage = publishAppPage.clickNextButton().finalPublishButtonClick().clickCloseButton();
			status = createAppPage.getAppStatus(appName).contains(parameters.get("app1_status2"));
			reportLog(status, testContext.getName(), "Verify New app ha status Published", "2.1",
					"New App is now set to status Published");
			org.testng.Assert.assertTrue(status);

			status = createAppPage.getAppVersion(appName).contains(parameters.get("app1_version2"));
			reportLog(status, testContext.getName(), "Verify app now has version2", "2.2", "App has version2");
			org.testng.Assert.assertTrue(status);

			appBuilderPage = createAppPage.clickAppCreateButton();
			String appName2 = parameters.get("app_name2") + randomAlphaNumeric(10);
			appBuilderPage.createApp(appName2, testContext, "3.0");
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton();
			publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			createAppPage = publishAppPage.clickNextButton();
			publishAppPage.clickNextButton().finalPublishButtonClick().clickCloseButton();
			editAppPage = createAppPage.clickAppName(appName2);
			appBuilderPage = editAppPage.clickEditAppButton();
			appBuilderPage.addScreenAndAddLabel(parameters.get("second_screen_place_holder"),
					parameters.get("second_screen_name"), parameters.get("second_screen_labels").split(";"),
					parameters.get("second_screen_fields").split(";"));
			appBuilderPage.clickSaveButton();
			createAppPage = appBuilderPage.clickCloseButton();
			customWait(5);
			Date date = new Date();
			final SimpleDateFormat formatType = new SimpleDateFormat("MM/dd/yyyy");
			formatType.setTimeZone(TimeZone.getTimeZone("UTC"));
			String modifiedDate = formatType.format(date);
			status = createAppPage.getLastEdited(appName2).contains(modifiedDate);
			reportLog(status, testContext.getName(), "Verify new app2 now last edit time as today's date", "3.1",
					"New App2 has last edited time as today's date");
			org.testng.Assert.assertTrue(status);

			status = createAppPage.getAppStatus(appName2).contains(parameters.get("app1_status1"));
			reportLog(status, testContext.getName(), "Verify New app2 now has a status set to Pending", "3.2",
					"New App2 has status  Pending");
			org.testng.Assert.assertTrue(status);

			status = createAppPage.getAppVersion(appName2).contains(parameters.get("app1_version1"));
			reportLog(status, testContext.getName(), "Verify new app2 now has version2", "3.3",
					"New App2 has version2");
			org.testng.Assert.assertTrue(status);

			appBuilderPage = createAppPage.clickAppCreateButton();
			String appName3 = parameters.get("app_name3") + randomAlphaNumeric(10);
			appBuilderPage.createApp(appName3, testContext, "4.0");
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton();
			publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			createAppPage = publishAppPage.clickNextButton();
			publishAppPage.clickNextButton().finalPublishButtonClick().clickCloseButton();
			editAppPage = createAppPage.clickAppName(appName3);
			appBuilderPage = editAppPage.clickEditAppButton();
			appBuilderPage.addScreenAndAddLabel(parameters.get("second_screen_place_holder"),
					parameters.get("second_screen_name"), parameters.get("second_screen_labels").split(";"),
					parameters.get("second_screen_fields").split(";"));
			appBuilderPage.clickSaveButton();
			createAppPage = appBuilderPage.clickCloseButton();
			status = createAppPage.getAppStatus(appName3).contains(parameters.get("app1_status1"));
			reportLog(status, testContext.getName(), "Verify new app3 now has a status set to Pending", "4.1",
					"New App3 has status Pending");
			org.testng.Assert.assertTrue(status);

			status = createAppPage.getAppVersion(appName3).contains(parameters.get("app1_version1"));
			reportLog(status, testContext.getName(), "Verify new app3 now has version2", "4.2",
					"New App3 has version2");
			org.testng.Assert.assertTrue(status);

			createAppPage.deleteApp(appName3);
			status = createAppPage.getAppStatus(appName3).contains(parameters.get("app1_status2"));
			reportLog(status, testContext.getName(), "Verify new app3 now has a status set to Published", "5.1",
					"New App3 has status Published");
			org.testng.Assert.assertTrue(status);

			status = createAppPage.getAppVersion(appName3).contains(parameters.get("app1_version3"));
			reportLog(status, testContext.getName(), "Verify new app3 now has version1", "5.2",
					"New App3 has version1");
			org.testng.Assert.assertTrue(status);

			createAppPage.deleteApp(appName2);
			createAppPage.clickAppName(appName).destroyApp();
			customWait(2);
			createAppPage.clickAppNameToBeDestroy(appName2).destroyApp();
			customWait(2);
			createAppPage.clickAppNameToBeDestroy(appName3).destroyApp();
			customWait(5);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void folderFunctionalityTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = dashboardPage.clickApp();
			boolean status = createAppPage.isCreateFolderDisplayed();
			reportLog(!status, testContext.getName(), "Verify user does NOT see 'Create Folder' button in blue.", "1.2",
					"User on a start up plan does NOT have 'Create Folder' functionality.");
			org.testng.Assert.assertFalse(status);

			createAppPage.logout();
			LoginPage loginPage = new LoginPage(driver);
			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password"));
			loginPage.Clickonloginbutton();
			createAppPage = dashboardPage.clickApp();
			status = createAppPage.isCreateFolderDisplayed();
			reportLog(status, testContext.getName(),
					"Verify company admin on a Professional account DOES see the 'Create Folder' button.", "2.0",
					"Company Admin on a Professional plan can see the Create Folder button successfully.");
			org.testng.Assert.assertTrue(status);

			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			String folderName = parameters.get("folder_name") + randomAlphaNumeric(10);
			AppBuilderPage appBuilderPage = createAppPage.clickAppCreateButton();
			appBuilderPage.createApp(appName);
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton(appName,parameters.get("username2"),parameters.get("password"));
			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.clickNextPublishButton();
			createAppPage = publishAppPage.clickPublishButton().clickCloseButton();
			CreateFolderPage createFolderPage = createAppPage.clickCreateFolder();
			createFolderPage.enterFolderName(folderName);
			createAppPage = createFolderPage.clickSaveButton();
			status = createAppPage.isAppNameDisplayed(folderName);
			reportLog(status, testContext.getName(), folderName + " displays with the correct name on Apps page.",
					"3.0", folderName + " displayed successfully on Apps page");
			org.testng.Assert.assertTrue(status);

			createAppPage = dashboardPage.clickApp();
			createAppPage.clickQuickLink(appName);
			createAppPage.clickQuickLinkMenu(appName, "Move");
			createAppPage.clickFolder(folderName);
			createAppPage.clickMoveButton();
			// createAppPage.dragAppToFolder(appName, folderName);

			String message1 = "App '" + appName + "' was dropped in the folder '" + folderName + "'.";
			status = createAppPage.getToastMsg().contains(message1);
			reportLog(status, testContext.getName(),
					"Verify user receives App ' TC5581 App ' was dropped in the folder 'TC5581 Folder'", "4.0",
					"App ' TC5581 App ' was dropped in the folder 'TC5581 Folder'");
			org.testng.Assert.assertTrue(status);

			status = (createAppPage.getNumberOfAppInstances(appName) == 0);
			reportLog(status, testContext.getName(), "Please verify TC5581 App now does NOT display on Apps page",
					"4.1", "TC5581 App does NOT display on Apps page any longer");
			org.testng.Assert.assertTrue(status);

			String folderReName = parameters.get("folder_rename") + randomAlphaNumeric(10);
			String message2 = "'" + folderReName + "' has been successfully saved.";
			createFolderPage = createAppPage.clickEditFolder(folderName);
			createFolderPage.clearFolderName();
			createFolderPage.enterFolderName(folderReName);
			createAppPage = createFolderPage.clickSaveButton();
			status = createAppPage.getToastMsg().contains(message2);
			reportLog(status, testContext.getName(), "Verify user sees 'Renamed Folder' has been successfully saved.",
					"5.0", "Message 'Renamed Folder' has been successfully saved displayed");
			org.testng.Assert.assertTrue(status);

			createAppPage.logout();
			loginPage = new LoginPage(driver);
			loginPage.typeusername(parameters.get("username3"));
			loginPage.typepassword(parameters.get("password"));
			loginPage.Clickonloginbutton();
			createAppPage = dashboardPage.clickApp();
			createAppPage.clickQuickLink(parameters.get("folder_name2"));
			createAppPage.clickQuickLinkMenu(parameters.get("folder_name2"), parameters.get("quick_link"));
			status = createAppPage.isAppNameDisplayed(parameters.get("folder_name2"));
			reportLog(status, testContext.getName(), "Verify user CANNOT destroy folder when there are apps in Folder",
					"6.0", "'Test Folder' still appears on Apps page");
			org.testng.Assert.assertTrue(status);

			FolderPage folderPage = createAppPage.clickFolderName(folderReName);
			folderPage.clickQuickLink(appName);
			folderPage.clickQuickLinkMenu(appName, parameters.get("quick_link2"));
			status = folderPage.isMoveSelectedAppDisplayed();
			reportLog(status, testContext.getName(), "Verify 'Move Selected App' window appears", "7.0",
					"Move Selected App' window appears.");
			org.testng.Assert.assertTrue(status);

			folderPage.clickApps();
			folderPage.clickMoveButton();
			createAppPage = folderPage.clickAppLink();
			createAppPage.clickQuickLink(folderReName);
			createAppPage.clickQuickLinkMenu(folderReName, parameters.get("quick_link"));
			status = createAppPage.getPopUpMessageText().contains(parameters.get("message3"));
			reportLog(status, testContext.getName(),
					"Verify user is prompted, This will permanently delete this folder", "8.0",
					"Message This will permanently delete this folder displayed.");
			org.testng.Assert.assertTrue(status);
			String message4 = "'" + folderReName + "' has been deleted.";
			createAppPage.acceptPopUpMessage();
			status = createAppPage.getToastMsg().contains(message4);
			reportLog(status, testContext.getName(), "Verify user sees 'Renamed Folder' has been deleted.", "8.1",
					"The following pop up message appears: 'Renamed Folder' has been deleted");
			org.testng.Assert.assertTrue(status);

			createAppPage.clickAppName(appName).destroyApp();
			customWait(5);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void paymentTest(String testDescription, ITestContext testContext) throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			EditAppPage editAppPage = dashboardPage.clickApp().clickAppName(parameters.get("app"));
			boolean status = editAppPage.isPaymentOptionsDisplayed();
			reportLog(status, testContext.getName(), "Verify Payment Options icon display under Options", "1.1",
					"Payment Options icon displays correctly under Options. ");
			org.testng.Assert.assertTrue(status);

			ShowPaymentOptionsPage showPaymentOptionsPage = editAppPage.clickPaymentOptionsLink();
			status = showPaymentOptionsPage.isIntegrateDisplayed();
			reportLog(status, testContext.getName(), "Verify Integrate button in blue displays", "2.0",
					"Integrate button appear");
			org.testng.Assert.assertTrue(status);

			showPaymentOptionsPage.clickIntegrateButton();
			showPaymentOptionsPage.clickAuthorizeButton();
			String winHandleBefore = driver.getWindowHandle();
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}
			driver.findElement(By.id("splashAccount")).click();
			driver.findElement(By.id("email")).sendKeys(parameters.get("paypal_username"));
			driver.findElement(By.id("btnNext")).click();
			driver.findElement(By.id("password")).sendKeys(parameters.get("paypal_password"));
			driver.findElement(By.id("btnLogin")).click();
			driver.findElement(By.id("doneSdkVerifiedContinueBtn")).click();
			driver.findElement(By.xpath("//input[@name = 'continueLogin']")).click();
			customWait(2);
			driver.switchTo().window(winHandleBefore);
			showPaymentOptionsPage = new ShowPaymentOptionsPage(driver);
			status = showPaymentOptionsPage.getIntegrationStatus().contains(parameters.get("status"));
			reportLog(status, testContext.getName(),
					"Verify user sees Authorization with Paypal was successful in green back on the original page. ",
					"3.0", "Authorization with Paypal was successful message appears");
			org.testng.Assert.assertTrue(status);

			showPaymentOptionsPage.clickSaveButton();
			status = showPaymentOptionsPage.isDisconnectDisplayed();
			reportLog(status, testContext.getName(), "Verify user now sees Disconnect button.", "4.0",
					"User sees Disconnect button");
			org.testng.Assert.assertTrue(status);

			LoginPage login = showPaymentOptionsPage.clickLogOutButton();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbutton();
			CreateAppPage createAppPage = dashboardPage.clickApp();
			editAppPage = createAppPage.clickAppName(parameters.get("app"));
			showPaymentOptionsPage = editAppPage.clickPaymentOptionsLink();
			createAppPage = showPaymentOptionsPage.clickDisconnectButtonNotAuthorized(parameters.get("message1"), "5.0",
					testContext);
			status = createAppPage.getToastMsg().contains(parameters.get("message2"));
			reportLog(status, testContext.getName(), "Verify user sees You do not have access to this page.", "6.0",
					"The following message appears: You do not have access to this page");
			org.testng.Assert.assertTrue(status);

			login = createAppPage.clickLogOutButton();
			login.typeusername(parameters.get("username"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			createAppPage = dashboardPage.clickApp();
			editAppPage = createAppPage.clickAppName(parameters.get("app"));
			showPaymentOptionsPage = editAppPage.clickPaymentOptionsLink();
			showPaymentOptionsPage.clickDisconnectButtonAuthorized(parameters.get("message1"));
			status = showPaymentOptionsPage.isIntegrateDisplayed();
			reportLog(status, testContext.getName(), "Verify now button says Integrate.", "7.0",
					"Button changed from 'Disconnect' to 'Integrate'.");
			org.testng.Assert.assertTrue(status);

			driver.findElement(By.xpath("//span[text() = 'Log Out']")).click();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void publishPendingVersionTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName);
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));
			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			CreateAppPage createAppPage = publishAppPage.clickNextButton();
			publishAppPage.clickNextButton().finalPublishButtonClick().clickCloseButton();
			boolean status = createAppPage.getAppStatus(appName).contains(parameters.get("app1_status1"));
			reportLog(status, testContext.getName(), "New app's status", "1.1",
					"New app's status is: " + parameters.get("app1_status1"));
			org.testng.Assert.assertTrue(status);

			appBuilderPage = createAppPage.editApp(appName);
			appBuilderPage.addScreenAndAddLabel(parameters.get("second_screen_place_holder"),
					parameters.get("second_screen_name"), parameters.get("second_screen_labels").split(";"),
					parameters.get("second_screen_fields").split(";"));
			appBuilderPage.clickSaveButton();
			createAppPage = appBuilderPage.clickCloseButton();
			status = createAppPage.getAppStatus(appName).contains(parameters.get("app1_status2"));
			reportLog(status, testContext.getName(), "New app's status after editing is", "1.2",
					"New app's status is: " + parameters.get("app1_status2"));
			org.testng.Assert.assertTrue(status);

			String appName2 = parameters.get("app_name2") + randomAlphaNumeric(10);
			appBuilderPage = createAppPage.clickAppCreateButton();
			appBuilderPage.createApp(appName2);
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickSaveButton(appName2,parameters.get("username"),parameters.get("password"));
			publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			createAppPage = publishAppPage.clickNextButton();
			publishAppPage.clickNextButton().finalPublishButtonClick().clickCloseButton();
			status = createAppPage.getAppVersion(appName).contains(parameters.get("app1_version2"));
			reportLog(status, testContext.getName(), "New app's version after publishing second app is", "2.0",
					"New app's version is: " + parameters.get("app1_version2"));
			org.testng.Assert.assertTrue(status);

			createAppPage.logout();
			LoginPage loginPage = new LoginPage(driver);
			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password2"));
			loginPage.Clickonloginbutton();
			createAppPage = dashboardPage.clickApp();
			EditAppPage editAppPage = createAppPage.clickAppName(appName);
			publishAppPage = editAppPage.clickPublishAppLink();
			createAppPage = publishAppPage.clickPublishAppButton();
			status = createAppPage.getAppStatus(appName).contains(parameters.get("app1_status1"));
			reportLog(status, testContext.getName(), "New app's status after publishing the app from another account",
					"3.0", "New app's status is: " + parameters.get("app1_status1"));
			org.testng.Assert.assertTrue(status);

			status = createAppPage.getAppVersion(appName).contains(parameters.get("app1_version2"));
			reportLog(status, testContext.getName(), "New app's version after publishing the app from another account",
					"3.1", "New app's version is: " + parameters.get("app1_version2"));
			org.testng.Assert.assertTrue(status);

			appBuilderPage = createAppPage.editApp(appName);
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("third_screen_place_holder"),
					parameters.get("third_screen_name"), parameters.get("third_screen_labels").split(";"),
					parameters.get("third_screen_fields").split(";"));
			appBuilderPage.clickSaveButton();
			createAppPage = appBuilderPage.clickCloseButton();
			status = createAppPage.getAppStatus(appName).contains(parameters.get("app1_status2"));
			reportLog(status, testContext.getName(), "New app's status after editing the app from another account",
					"4.0", "New app's status is: " + parameters.get("app1_status2"));
			org.testng.Assert.assertTrue(status);

			status = createAppPage.getAppVersion(appName).contains(parameters.get("app1_version3"));
			reportLog(status, testContext.getName(), "New app's version after editing the app from another account",
					"4.1", "New app's version is: " + parameters.get("app1_version3"));
			org.testng.Assert.assertTrue(status);

			createAppPage.logout();
			loginPage = new LoginPage(driver);
			loginPage.typeusername(parameters.get("username"));
			loginPage.typepassword(parameters.get("password"));
			loginPage.Clickonloginbutton();
			createAppPage = dashboardPage.clickApp();
			editAppPage = createAppPage.clickAppName(appName);
			publishAppPage = editAppPage.clickPublishAppLink();
			createAppPage = publishAppPage.clickPublishAppButton();
			status = createAppPage.getAppStatus(appName).contains(parameters.get("app1_status1"));
			reportLog(status, testContext.getName(), "New app's status after publishing the app from first account",
					"5.0", "New app's status is: " + parameters.get("app1_status1"));
			org.testng.Assert.assertTrue(status);

			status = createAppPage.getAppVersion(appName).contains(parameters.get("app1_version3"));
			reportLog(status, testContext.getName(), "New app's version after editing the app from first account",
					"5.1", "New app's version is: " + parameters.get("app1_version3"));
			org.testng.Assert.assertTrue(status);
			

			createAppPage.clickAppName(appName).destroyApp();
			createAppPage.clickAppNameToBeDestroy(appName2).destroyApp();
			customWait(2);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

}
