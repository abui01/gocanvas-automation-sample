package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.BillingOptionsPage;
import com.canvas.qa.pages.CustomizePage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.SearchUsersPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.profile.UsersPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class MyAccountTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void customizeTest(String testDescription, String rallyLink, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company user: " + parameters.get("username"), testContext, testDescription,
					rallyLink);
			DashboardPage dashboardPage = new DashboardPage(driver);
			boolean status = dashboardPage.isMenuDisplayed(parameters.get("menu"));
			reportLog(!status, testContext.getName(),
					"Verify " + parameters.get("menu") + " is not displayed for user: " + parameters.get("username"),
					"1.1", parameters.get("menu") + " is not displayed for user: " + parameters.get("username"));
			org.testng.Assert.assertFalse(status);

			LoginPage login = dashboardPage.clickLogOut();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage.clickAccount();
			dashboardPage.clickMenuOption(parameters.get("menu2"));
			CustomizePage customizePage = new CustomizePage(driver);
			String[] optionsList = parameters.get("options_list").split(";");
			for (int i = 0; i < optionsList.length; i++) {
				status = customizePage.isLabelsDisplayed(optionsList[i]);
				reportLog(status, testContext.getName(),
						"Verify on Customize page " + optionsList[i] + " is displayed.", "2." + i,
						"On Customize page " + optionsList[i] + " is displayed.");
				org.testng.Assert.assertTrue(status);
			}

			status = customizePage.isLogoImageDisplayed();
			reportLog(status, testContext.getName(), "Verify Custom Account Logo displays, 'Add Your Logo'.", "3.0",
					"Custom Account Logo displays Add Your Logo as the default Null message.");
			org.testng.Assert.assertTrue(status);

			customizePage.clickEditButton(parameters.get("label1"));
			status = customizePage.isHIPPACompliantCheckboxEnabled();
			reportLog(!status, testContext.getName(), "Verify HIPPA Compliant is set to Disabled.", "3.1",
					"HIPPA compliant is set for 'disabled'.");
			org.testng.Assert.assertFalse(status);
			customizePage.clickCancelButton();

			customizePage.clickEditButton(parameters.get("label2"));
			status = customizePage.isAppPublishedNotificationCheckboxSelected()
					&& customizePage.isDispatchCheckboxEnabled() && customizePage.isRefDataCheckboxEnabled();
			reportLog(status, testContext.getName(), "Verify the 3 Notifications, are set for 'enabled", "4.0",
					"3 Notification options are defaulted to 'enabled'.");
			org.testng.Assert.assertTrue(status);
			customizePage.clickCancelButton();

			customizePage.clickEditButton(parameters.get("label3"));
			status = customizePage.getSelectedAccountDateFormat().contains(parameters.get("date_format"));
			reportLog(status, testContext.getName(), "Verify Account Date Format is defaulted to MM/DD/YYYY", "4.1",
					"Account Date Format is defaulted to MM/DD/YYYY.");
			org.testng.Assert.assertTrue(status);
			customizePage.clickCancelButton();

			customizePage.clickEditButton(parameters.get("label4"));
			status = customizePage.isCompanyTestingCheckboxEnabled();
			reportLog(!status, testContext.getName(), "Verify Testing Apps is 'disabled'.", "4.2",
					"Testing Apps is defaulted to 'disabled'.");
			org.testng.Assert.assertFalse(status);
			customizePage.clickCancelButton();

			customizePage.clickEditButton(parameters.get("label5"));
			status = customizePage.isDepartmentFunctionalityCheckboxEnabled();
			reportLog(!status, testContext.getName(), "Verify Department functionality is defaulted to 'disabled'",
					"4.3", "Department Functionality defaulted to 'disabled'.");
			org.testng.Assert.assertFalse(status);
			customizePage.clickCancelButton();

			customizePage.clickEditButton(parameters.get("label5"));
			customizePage.clickDepartmentFunctionalityCheckbox();
			customizePage.clickSaveButton();
			status = dashboardPage.isDepartmentDropdownDisplayed();
			reportLog(status, testContext.getName(), "Verify user sees the department drop down", "5.1",
					"User sees the department drop down.");
			org.testng.Assert.assertTrue(status);

			status = dashboardPage.isDepartmentDisplayed(parameters.get("department"));
			reportLog(status, testContext.getName(), "Verify user the department ALL", "5.2",
					"Department ALL is displayed.");
			org.testng.Assert.assertTrue(status);
			customizePage.clickEditButton(parameters.get("label5"));
			customizePage.clickDepartmentFunctionalityCheckbox();
			customizePage.clickSaveButton();

			customizePage.clickEditButton(parameters.get("label6"));
			status = customizePage.getSelectedEmailSubmissionsFrom().contains(parameters.get("email"));
			reportLog(status, testContext.getName(),
					"Verify 'Email submissions from' drop down defaulted to 'donotreply@gocanvas.com'", "6.0",
					"'Email submissions from' drop down defaulted to 'donotreply@gocanvas.com'");
			org.testng.Assert.assertTrue(status);
			customizePage.clickCancelButton();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void filterUsersTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickAccount();
			UsersPage usersPage = dashboardPage.clickUsers();
			usersPage.clickFilterDDL();
			String[] filtereList = parameters.get("filter_list").split(";");
			for (int i = 0; i < filtereList.length; i++) {
				boolean status = usersPage.isFilterOptionDisplayed(filtereList[i]);
				reportLog(status, testContext.getName(), filtereList[i] + " :  displayed", "1.1." + i,
						filtereList[i] + " : displayed");
				org.testng.Assert.assertTrue(status);
			}

			boolean status = usersPage.isApplyButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify Apply button displayed", "1.2", "Apply button displayed");
			org.testng.Assert.assertTrue(status);

			usersPage = dashboardPage.clickUsers();
			usersPage.clickFilterDDL();
			usersPage.clickInactive();
			usersPage.clickApply();
			usersPage.searchUser(parameters.get("user"));
			status = usersPage.getEmailByRow(1).contains(parameters.get("email"))
					&& usersPage.getEmailByRow(1).contains(parameters.get("status"));
			reportLog(status, testContext.getName(),
					"Verify email displayed: " + parameters.get("email") + parameters.get("status"), "2.0",
					parameters.get("email") + parameters.get("status") + " displayed");
			org.testng.Assert.assertTrue(status);

			usersPage = dashboardPage.clickUsers();
			usersPage.clickFilterDDL();
			usersPage.clickActive();
			usersPage.clickFilled();
			usersPage.clickApply();
			for (int i = 1; i <= 10; i++) {
				status = usersPage.isNameWithoutLinkDisplayed(parameters.get("open_user") + i);
				reportLog(status, testContext.getName(), "Verify user displayed: " + parameters.get("open_user") + i,
						"3." + i, parameters.get("open_user") + i + " : displayed");
				org.testng.Assert.assertTrue(status);
			}

			usersPage = dashboardPage.clickUsers();
			usersPage.clickFilterDDL();
			usersPage.clickActive();
			usersPage.clickOpen();
			usersPage.clickApply();
			String[] filledList = parameters.get("filled_list").split(";");
			for (int i = 0; i < filledList.length; i++) {
				status = usersPage.isEmailDisplayed(filledList[i]);
				reportLog(status, testContext.getName(), filledList[i] + " :  displayed", "4." + i,
						filledList[i] + " : displayed");
				org.testng.Assert.assertTrue(status);
			}

			usersPage = dashboardPage.clickUsers();
			usersPage.clickFilterDDL();
			usersPage.clickOpen();
			usersPage.clickFilled();
			usersPage.clickApply();
			status = usersPage.getNameByRow(Integer.parseInt(parameters.get("row_index")))
					.contains(parameters.get("active_user"));
			reportLog(status, testContext.getName(),
					"Verify name displayed in first row: " + parameters.get("active_user"), "5.0",
					parameters.get("active_user") + " : name displayed in first row");
			org.testng.Assert.assertTrue(status);

			status = usersPage.getEmailByRow(Integer.parseInt(parameters.get("row_index")))
					.contains(parameters.get("active_email"));
			reportLog(status, testContext.getName(),
					"Verify email displayed in first row: " + parameters.get("active_email"), "5.1",
					parameters.get("active_email") + " : email displayed in first row");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void manageUsersTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
			searchUsersPage.enterEmail(parameters.get("email"));
			searchUsersPage.clickSearch();
			dashboardPage = searchUsersPage.clickOnManage(parameters.get("email"));
			String[] messageList = parameters.get("message").split(";");
			for (int i = 0; i < messageList.length; i++) {
				boolean status = searchUsersPage.isMessageDisplayed(messageList[i]);
				reportLog(status, testContext.getName(), "Verify message displayed on clicking Manage.", "1.1." + i,
						messageList[i] + " :message displayed on clicking manage.");
				org.testng.Assert.assertTrue(status);
			}

			searchUsersPage.clickResumeYourSession();
			boolean status = searchUsersPage.isSearchUsersPageDisplayed();
			reportLog(status, testContext.getName(), "Verify Search Users page displayed on resume session", "2.0",
					"Search Users page displayed on clicking resume your session.");
			org.testng.Assert.assertTrue(status);

			searchUsersPage.enterEmail(parameters.get("email"));
			searchUsersPage.clickSearch();
			dashboardPage = searchUsersPage.clickOnManage(parameters.get("email"));
			status = dashboardPage.isMenuDisplayed(parameters.get("menu"));
			reportLog(status, testContext.getName(), "Verify Go Canvas Only displayed.", "3.0",
					"Go Canvas Only displayed under profile.");
			org.testng.Assert.assertTrue(status);

			dashboardPage.clickGoCanvasOnly();
			String[] optionsList = parameters.get("options_list").split(";");
			for (int i = 0; i < optionsList.length; i++) {
				status = dashboardPage.isGoCanvasMenuOptionDisplayed(optionsList[i]);
				reportLog(status, testContext.getName(),
						"Verify on clicking Go Canvas Only " + optionsList[i] + " is displayed.", "4." + i,
						"On clicking Go Canvas Only " + optionsList[i] + " is displayed.");
				org.testng.Assert.assertTrue(status);
			}

			dashboardPage.clickGoCanvasMenuOption(parameters.get("option"));
			BillingOptionsPage billingOptionsPage = new BillingOptionsPage(driver);
			billingOptionsPage.editAnnual();
			billingOptionsPage.selectRate(parameters.get("rate"));
			billingOptionsPage.saveClick();
			status = billingOptionsPage.getToastMessage().contains(parameters.get("message2"));
			reportLog(status, testContext.getName(), "Verify toast message displayed on updating annual rate", "5.0",
					parameters.get("message2") + " : message displayed on updating annual rate.");
			org.testng.Assert.assertTrue(status);

			billingOptionsPage.editAnnual();
			billingOptionsPage.selectRate(parameters.get("original_rate"));
			billingOptionsPage.saveClick();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
