package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.EditWorkflowPage;
import com.canvas.qa.pages.PricingPage;
import com.canvas.qa.pages.PublishAppPage;
import com.canvas.qa.pages.SearchUsersPage;
import com.canvas.qa.pages.apps.AppBuilderPage;
import com.canvas.qa.pages.apps.EditAppPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class WorkFlowTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void accessDispatchTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			boolean status = dashboardPage.isDispatchDisplayed();
			reportLog(status, testContext.getName(), "Verify Dispatch Displayed", "1.2", "Dispatch Displayed");
			org.testng.Assert.assertTrue(status);

			LoginPage login = dashboardPage.clickLogOut();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			status = dashboardPage.isDispatchDisplayed();
			reportLog(status, testContext.getName(), "Verify Dispatch Displayed", "2.0", "Dispatch Displayed");
			org.testng.Assert.assertTrue(status);

			login = dashboardPage.clickLogOut();
			login.gotoLogin();
			login.typeusername("admin");
			login.typepassword("Admin89!");
			login.Clickonloginbutton();
			dashboardPage = new DashboardPage(driver);
			SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
			searchUsersPage.enterEmail(parameters.get("username3"));
			searchUsersPage.clickSearch();
			searchUsersPage.clickOnMore(parameters.get("username3").toLowerCase());
			searchUsersPage.clickFeatures();
			if (searchUsersPage.isEnableDepartmentDisplayed()) {
				searchUsersPage.clickEnableDepartment();
			}
			searchUsersPage.logout();
			login.typeusername(parameters.get("username3"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department_new"));
			PricingPage pricingPage = dashboardPage.clickPricingMenu().selectPricingOption();
			dashboardPage = pricingPage.clickSelectPlan(parameters.get("plan_new1")).clickConfirmButton();
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department_original"));
			status = dashboardPage.isDispatchDisplayed();
			reportLog(status, testContext.getName(), "Verify Dispatch Displayed", "3.0", "Dispatch Displayed");
			org.testng.Assert.assertTrue(status);

			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department_new"));
			pricingPage = dashboardPage.clickPricingMenu().selectPricingOption();
			dashboardPage = pricingPage.clickSelectPlan(parameters.get("plan_original1")).clickConfirmButton();

			login = dashboardPage.clickLogOut();
			login.typeusername(parameters.get("username4"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			pricingPage = dashboardPage.clickPricingMenu().selectPricingOption();
			dashboardPage = pricingPage.clickSelectPlan(parameters.get("plan_new2")).clickConfirmButton();
			status = dashboardPage.isDispatchDisplayed();
			reportLog(status, testContext.getName(), "Verify Dispatch Displayed", "4.0", "Dispatch Displayed");
			org.testng.Assert.assertTrue(status);
			pricingPage = dashboardPage.clickPricingMenu().selectPricingOption();
			dashboardPage = pricingPage.clickSelectPlan(parameters.get("plan_original2")).clickConfirmButton();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void accessWorkflowDispatchTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			boolean status = dashboardPage.isWorkflowAndDispatchDisplayed();
			reportLog(status, testContext.getName(), "Verify Workflow & Dispatch appears.", "1.2",
					"Workflow & Dispatch appears.");
			org.testng.Assert.assertTrue(status);

			LoginPage login = dashboardPage.clickLogOut();
			login.enterCredentialsAndLogin((parameters.get("username2")), (parameters.get("password")));
			status = dashboardPage.isWorkflowAndDispatchDisplayed();
			reportLog(status, testContext.getName(), "Verify Workflow & Dispatch appears.", "2.0",
					"Workflow & Dispatch appears.");
			org.testng.Assert.assertTrue(status);

			login = dashboardPage.clickLogOut();
			login.enterCredentialsAndLogin((parameters.get("username3")), (parameters.get("password")));
			PricingPage pricingPage = dashboardPage.clickPricingMenu().selectPricingOption();
			
			//pre-condition check: if 'professional' is already selected, go back to dashboard
			if (pricingPage.verifyProfessionalSelected()) {
				dashboardPage.clickHomeMenu();
				dashboardPage.clickDashboardButton();
			} else {
				//if post-conditions were fulfilled, proceed to purchase 'Professional' plan
					dashboardPage = pricingPage.clickSelectPlan(parameters.get("plan_new")).clickConfirmButton();
				}
			
			status = dashboardPage.isWorkflowAndDispatchDisplayed();
			reportLog(status, testContext.getName(), "Verify Workflow & Dispatch appears.", "3.0",
					"Workflow & Dispatch appears.");
			org.testng.Assert.assertTrue(status);
			pricingPage = dashboardPage.clickPricingMenu().selectPricingOption();
			dashboardPage = pricingPage.clickSelectPlan(parameters.get("plan_original")).clickConfirmButton();

			login = dashboardPage.clickLogOut();
			login.enterCredentialsAndLogin((parameters.get("username4")), (parameters.get("password")));
			status = dashboardPage.isWorkflowAndDispatchDisplayed();
			reportLog(status, testContext.getName(), "Verify Workflow & Dispatch appears.", "4.0",
					"Workflow & Dispatch appears.");
			org.testng.Assert.assertTrue(status);
			
			login = dashboardPage.clickLogOut();
			login.enterCredentialsAndLogin((parameters.get("username5")), (parameters.get("password")));
			
			//flakey login scenario: sometimes script loads a form_icon instead of dashboard. If so, script will nav back and login again
			login.checkForInvalidPageLoad(parameters.get("username5"), parameters.get("password")); //need to get correct URL
			
			status = dashboardPage.isWorkflowAndDispatchDisplayed();
			
			//flakey test case always fails on step 5 therefore script will try logging in again
			if (status==false) 
			{
				System.out.println("Debugging step 5 of TC7544. Status currently is: " + status);
				System.out.println("Debugging form_icon issue. Current URL is: " + driver.getCurrentUrl());
				//check if user is logged in
				if (login.ifSuccessfullLogin()==true)
				{
					//do nothing
				}
				else
				{
					login.typeusername(parameters.get("username5"));
					login.typepassword(parameters.get("password"));
					login.Clickonloginbutton();
					status = dashboardPage.isWorkflowAndDispatchDisplayed();
				}

			}
			//flakey test case end check
			
			reportLog(status, testContext.getName(), "Verify Workflow & Dispatch appears.", "5.0",
					"Workflow & Dispatch appears.");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
	

	@Test
	@Parameters({ "testdescription" })
	public void verifyWorkflowTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {

		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = dashboardPage.clickApp();
			EditAppPage editAppPage = createAppPage.clickAppName(parameters.get("app1"));
			boolean status = editAppPage.isWorkflowLinkDisplayed();
			reportLog(status, testContext.getName(),
					"Verify Workflow does not appear under Features for a start up plan", "1.1",
					"Workflow icon does not appear under Features");
			org.testng.Assert.assertTrue(status);

			LoginPage login = editAppPage.clickLogOutButton();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbutton();
			createAppPage = dashboardPage.clickApp();
			editAppPage = createAppPage.clickAppName(parameters.get("app2"));
			status = editAppPage.isWorkflowLinkDisplayed();
			reportLog(!status, testContext.getName(),
					"Verify Workflow appear under Features for a Professional paid plan", "2.0",
					"Workflow icon appear under Features");
			org.testng.Assert.assertFalse(status);

			login = editAppPage.clickLogOutButton();
			login.typeusername(parameters.get("username3"));
			login.typepassword(parameters.get("password3"));
			login.Clickonloginbutton();
			createAppPage = dashboardPage.clickApp();
			editAppPage = createAppPage.clickAppName(parameters.get("app3"));
			EditWorkflowPage editWorkflowPage = editAppPage.clickWorkflowLink();
			status = editWorkflowPage.isEnableWorkflowChecked();
			if (status) {
				editWorkflowPage.clickEnableWorkflowCheckbox(true);
				editWorkflowPage.clickSaveButton();
				editWorkflowPage = editAppPage.clickWorkflowLink();
			}
			status = editWorkflowPage.isEnableWorkflowChecked();
			reportLog(!status, testContext.getName(), "Verify there is no check mark selected", "3.0",
					"No check mark defaulted to Enable Workflow for this app");
			org.testng.Assert.assertFalse(status);

			editWorkflowPage.clickEnableWorkflowCheckbox(true);
			editWorkflowPage.clickFinalSubmitterRadioButton();
			editWorkflowPage.enterHandOffName(parameters.get("handoff_name"));
			editWorkflowPage.selectHandOffAfter(parameters.get("handoff_after"));
			editWorkflowPage.clickEmailListRadioButton();
			editWorkflowPage.enterEmailList(parameters.get("email_list"));
			
			if (editWorkflowPage.verifyEmailDDLPresent()==true)
			{
				status = editWorkflowPage.getEmailDDLText().contains(parameters.get("email_list"));
			}
			else {
				driver.navigate().refresh();
				editWorkflowPage.clickEnableWorkflowCheckbox(true);
				editWorkflowPage.clickFinalSubmitterRadioButton();
				editWorkflowPage.enterHandOffName(parameters.get("handoff_name"));
				editWorkflowPage.selectHandOffAfter(parameters.get("handoff_after"));
				editWorkflowPage.clickEmailListRadioButton();
				editWorkflowPage.enterEmailList(parameters.get("email_list"));
				status = editWorkflowPage.getEmailDDLText().contains(parameters.get("email_list"));
			}

			reportLog(status, testContext.getName(), "Verify email shows", "4.0",
					"dispatch 13@yahoo.com displayed in the Email list");
			org.testng.Assert.assertTrue(status);

			editWorkflowPage.clickSaveButton();
			String toastMessage = editWorkflowPage.getToastMsg();
			status = toastMessage.equals(parameters.get("message1"))
					|| toastMessage.equals(parameters.get("message11"));
			reportLog(status, testContext.getName(), "Verify toast message on enabling workflow", "4.1",
					"Successfully updated workflow message appeared");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), "Verification on mobile device", "5.0", "Cannot be automated");

			login = editAppPage.clickLogOutButton();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbutton();
			createAppPage = dashboardPage.clickApp();
			editAppPage = createAppPage.clickAppName(parameters.get("app3"));
			editWorkflowPage = editAppPage.clickWorkflowLink();
			editWorkflowPage.clickEnableWorkflowCheckbox(true);
			editWorkflowPage.clickSaveButton();
			toastMessage = editWorkflowPage.getToastMsg();
			status = toastMessage.equals(parameters.get("message2"));
			reportLog(status, testContext.getName(), "Verify toast message on disabling workflow", "6.0",
					"Workflow Disabled message appeared");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
	

	@Test
	@Parameters({ "testdescription" })
	public void workflowAppShowTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as : " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickHomeMenu();
			boolean status = dashboardPage.isWorkflowAndDispatchOptionDisplayed();
			reportLog(status, testContext.getName(), "Verify Workflow & Dispatch appear under the Home menu.", "1.2",
					"Workflow & Dispatch appear under the Home menu");
			org.testng.Assert.assertTrue(status);

			LoginPage login = dashboardPage.clickLogOut();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			CreateAppPage createAppPage = dashboardPage.clickApp();
			createAppPage.deleteEachInstanceOfApp(parameters.get("app2"));
			createAppPage.retireEachInstanceOfApp(parameters.get("app2"));
			createAppPage.clickQuickLink(parameters.get("app1"));
			createAppPage.clickQuickLinkMenu(parameters.get("app1"), parameters.get("quickLinkMenu"));
			AppBuilderPage appBuilderPage = createAppPage.clickEditIcon(parameters.get("app2"));
			appBuilderPage.clickSaveButton();
			PublishAppPage publishAppPage = appBuilderPage.clickPublishToDeviceButton();
			publishAppPage.clickNextPublishButton();
			publishAppPage.checkAllUserCheckbox();
			createAppPage = publishAppPage.clickPublishButton().clickCloseButton();
			EditAppPage editAppPage = createAppPage.clickAppName(parameters.get("app2"));
			EditWorkflowPage editWorkflowPage = editAppPage.clickWorkflowLink();
			editWorkflowPage.clickEnableWorkflowCheckbox(true);
			editWorkflowPage.clickSaveButtonForInvalidData();
			status = editWorkflowPage.getErrorHeading().contains(parameters.get("errorHeading"));
			reportLog(status, testContext.getName(), "Verify error messages", "2.0",
					"Oops, there was a problem displayed");
			org.testng.Assert.assertTrue(status);

			status = editWorkflowPage.getErrorSubject().contains(parameters.get("errorSubject"));
			reportLog(status, testContext.getName(), "Verify error messages", "2.1",
					"There were problems with the following fields: displayed");
			org.testng.Assert.assertTrue(status);

			status = editWorkflowPage.getErrorFieldHeading().contains(parameters.get("errorHeading"));
			reportLog(status, testContext.getName(), "Verify error messages", "2.1.1",
					"Oops, there was a problem displayed");
			org.testng.Assert.assertTrue(status);

			String[] messages = parameters.get("errorMessages").split(";");
			List<String> actualMessages = editWorkflowPage.getErrorMessages();
			for (int i = 0; i < messages.length; i++) {
				status = actualMessages.get(i).replaceAll("\'", "").contains(messages[i]);
				reportLog(status, testContext.getName(), "Verify error messages", "2.2." + i,
						messages[i] + " displayed");
				org.testng.Assert.assertTrue(status);
			}

			editWorkflowPage.clickFinalSubmitterRadioButton();
			editWorkflowPage.clickHandoffSubmitterRadioButton();
			editWorkflowPage.clickOriginalSubmitterRadioButton();
			editWorkflowPage.clickFinalSubmitterRadioButton();
			editWorkflowPage.enterHandOffName(parameters.get("handoff"));
			editWorkflowPage.clickEditExistingDataCheckbox();
			editWorkflowPage.clickOptionToRejectCheckbox();
			editWorkflowPage.clickEditExistingDataCheckbox();
			editWorkflowPage.clickOptionToRejectCheckbox();
			editWorkflowPage.clickEditExistingDataCheckbox();
			editWorkflowPage.clickOptionToRejectCheckbox();

			ArrayList<String> screenActualList = editWorkflowPage.getHandOffScreenList();
			String[] screenExpectedList = parameters.get("handoff_screen").split(";");
			verifyDropdownValuesnew("3.0", screenActualList, screenExpectedList,
					"Verify the screen appear in the correct order.", testContext);

			editWorkflowPage.selectHandOffAfter(parameters.get("screen"));
			editWorkflowPage.clickEmailFieldRadioButton();

			ArrayList<String> emailActualList = editWorkflowPage.getEmailFieldList();
			String[] emailExpectedList = parameters.get("email_field_list").split(";");
			verifyDropdownValues("4.0", emailActualList, emailExpectedList,
					"Verify the email appear in the correct order.", testContext);

			editWorkflowPage.selectEmailField(parameters.get("email_field"));
			editWorkflowPage.clickEmailRadioButton();
			editWorkflowPage.clickBothRadioButton();
			editWorkflowPage.clickPushNotificationRadioButton();
			editWorkflowPage.clickEmailRadioButton();
			editWorkflowPage.clickNextUserBeginningRadioButton();
			editWorkflowPage.clickNextUserAfterRadioButton();
			editWorkflowPage.clickNextUserBeginningRadioButton();
			editAppPage = editWorkflowPage.clickSaveButton();
			editWorkflowPage = editAppPage.clickWorkflowLink();

			// Which user 'owns' the submission? = Final submitter
			status = editWorkflowPage.isFinalSubmitterSelected();
			reportLog(status, testContext.getName(), "Verify Final submitter", "5.0", "Final submitter selected");
			org.testng.Assert.assertTrue(status);

			// -Handoff name = Handoff name: H1&abcde
			status = editWorkflowPage.getHandOffName().contains(parameters.get("handoff"));
			reportLog(status, testContext.getName(), "Verify Handoff name", "5.1", "H1&abcde displayed");
			org.testng.Assert.assertTrue(status);

			// -Handoff will occur after screen = New Screen 1
			status = editWorkflowPage.getSelectedHandOffAfter().contains(parameters.get("screen"));
			reportLog(status, testContext.getName(), "Verify Handoff will occur after screen", "5.2",
					"New Screen 1 displayed");
			org.testng.Assert.assertTrue(status);

			// -checked Next user can edit existing data
			status = editWorkflowPage.isEditExistingDataCheckboxChecked();
			reportLog(status, testContext.getName(), "Verify Next user can edit existing data", "5.3", "Checked");
			org.testng.Assert.assertTrue(status);

			// -checked Next user has the option to reject
			status = editWorkflowPage.isOptionToRejectCheckboxChecked();
			reportLog(status, testContext.getName(), "Verify Next user has the option to reject", "5.4", "Checked");
			org.testng.Assert.assertTrue(status);

			// -Email field = Drop Down
			status = editWorkflowPage.getSelectedEmailField().contains(parameters.get("email_field"));
			reportLog(status, testContext.getName(), "Verify Email field", "5.5", "Drop Down displayed");
			org.testng.Assert.assertTrue(status);

			// -Notify next user with = Email
			status = editWorkflowPage.isEmailRadioButtonSelected();
			reportLog(status, testContext.getName(), "Verify Notify next user with", "5.6", "Email selected");
			org.testng.Assert.assertTrue(status);

			// -Next user begins at = The beginning of the app
			status = editWorkflowPage.isNextUserBeginningRadioButtonSelected();
			reportLog(status, testContext.getName(), "Verify Next user begins at", "5.7",
					"The beginning of the app selected");
			org.testng.Assert.assertTrue(status);

			editWorkflowPage.clickNewHandOffButton();
			editWorkflowPage.enterHandOffName(parameters.get("handoff2"), Integer.parseInt(parameters.get("index")));
			screenActualList = editWorkflowPage.getHandOffScreenList(Integer.parseInt(parameters.get("index")));
			screenExpectedList = parameters.get("screen2").split(";");
			verifyDropdownValuesnew("6.0", screenActualList, screenExpectedList,
					"Verify the screen appear in the correct order.", testContext);

			editWorkflowPage.selectHandOffAfter(parameters.get("screen2"), Integer.parseInt(parameters.get("index")));
			editWorkflowPage.enterEmailList(parameters.get("email_text"), Integer.parseInt(parameters.get("index")));
			status = (editWorkflowPage.getEmailListSize() == Integer.parseInt(parameters.get("email_list_size")));
			
			if (status==false) {
				//this method will refresh the flow on the off-chance that the server lags and doesn't return the e-mail list size
				driver.navigate().refresh();
				editWorkflowPage.clickNewHandOffButton();
				editWorkflowPage.enterHandOffName(parameters.get("handoff2"), Integer.parseInt(parameters.get("index")));
				screenActualList = editWorkflowPage.getHandOffScreenList(Integer.parseInt(parameters.get("index")));
				screenExpectedList = parameters.get("screen2").split(";");
				editWorkflowPage.selectHandOffAfter(parameters.get("screen2"), Integer.parseInt(parameters.get("index")));
				editWorkflowPage.enterEmailList(parameters.get("email_text"), Integer.parseInt(parameters.get("index")));
				status = (editWorkflowPage.getEmailListSize() == Integer.parseInt(parameters.get("email_list_size")));		
			}
			
			reportLog(status, testContext.getName(), "Verify the 4 users appear.", "7.0", "4 users appear");
			org.testng.Assert.assertTrue(status);
			editWorkflowPage.enterEmailList(parameters.get("email"), Integer.parseInt(parameters.get("index")));
			
			if (editWorkflowPage.verifyEmailDDLPresent()==false)
			{
				driver.navigate().refresh();
				editWorkflowPage.clickNewHandOffButton();
				editWorkflowPage.enterHandOffName(parameters.get("handoff2"), Integer.parseInt(parameters.get("index")));
				screenActualList = editWorkflowPage.getHandOffScreenList(Integer.parseInt(parameters.get("index")));
				screenExpectedList = parameters.get("screen2").split(";");
				editWorkflowPage.selectHandOffAfter(parameters.get("screen2"), Integer.parseInt(parameters.get("index")));
				editWorkflowPage.enterEmailList(parameters.get("email"), Integer.parseInt(parameters.get("index")));
			}
			
			editWorkflowPage.clickEmailDDLText();
			editAppPage = editWorkflowPage.clickSaveButton();
			customWait(5);
			editWorkflowPage = editAppPage.clickWorkflowLink();
			editWorkflowPage.clickDeleteIcon(parameters.get("handoff2"));
			editWorkflowPage.clickDeleteIcon(parameters.get("handoff"));
			status = editWorkflowPage.getPopUpMessage().contains(parameters.get("message"));
			reportLog(status, testContext.getName(), "Verify the popup appears", "8.0",
					"A Workflow Must Have At Least One Handoff displayed");
			org.testng.Assert.assertTrue(status);

			editWorkflowPage.acceptPopUpMessage();
			createAppPage = editWorkflowPage.clickAppNameLink(parameters.get("app2")).clickAppLink();
			createAppPage.clickQuickLink(parameters.get("app2"));
			createAppPage.clickQuickLinkMenu(parameters.get("app2"), parameters.get("quickLinkMenu2"));
			createAppPage.acceptPopUpMessage();
			customWait(5);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
