package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.ApplicationStorePage;
import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.apps.EditAppPage;
import com.canvas.qa.pages.apps.ShowEmailOptionsPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class ApplicationStoreTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void getAppTest(String testDescription, ITestContext testContext) throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = dashboardPage.clickApp();
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department1"));
			
			// pre-condition check
			createAppPage.retireEachInstanceOfApp(parameters.get("app1")); //ensures all version of app 1 had been deleted
			createAppPage.checkAndDeletePendingApp(parameters.get("app2")); //deletes any "pending" status apps for app 2
			createAppPage.retireEachInstanceOfApp(parameters.get("app2")); // deletes all "published" status apps for app 2
			// end check
			
			ApplicationStorePage applicationStorePage = dashboardPage.clickApplicationStore();
			applicationStorePage.clickDDLOption(parameters.get("option"));
			applicationStorePage.enterAppToSearch(parameters.get("app1"));
			applicationStorePage.clickAppSearchButton();
			createAppPage = applicationStorePage.clickGetApp(parameters.get("app1"));
			boolean status = createAppPage.isAppNameDisplayed(parameters.get("app1"));
			reportLog(status, testContext.getName(), "Verify the application appears on Apps", "1.1",
					parameters.get("app1") + " :appears on Apps");
			org.testng.Assert.assertTrue(status);

			EditAppPage editAppPage = createAppPage.clickAppName(parameters.get("app1"));
			ShowEmailOptionsPage showEmailOptionsPage = editAppPage.clickEmailOptions();
			status = showEmailOptionsPage.getEmailList().contains(parameters.get("username"));
			reportLog(status, testContext.getName(),
					"Verify the correct email address appears below the heading Email addresses to always receive a copy",
					"2.0", parameters.get("username") + " :appears");
			org.testng.Assert.assertTrue(status);

			createAppPage = showEmailOptionsPage.clickCancelButton().clickAppLink();
			createAppPage.clickQuickLink(parameters.get("app1"));
			createAppPage.clickQuickLinkMenu(parameters.get("app1"), parameters.get("menu"));
			createAppPage.acceptPopUpMessage();

			LoginPage login = createAppPage.clickLogOutButton();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			createAppPage = dashboardPage.clickApp();
			
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department2"));
			
			applicationStorePage = dashboardPage.clickApplicationStore();
			applicationStorePage.clickViewAllApps();
			applicationStorePage.enterAppToSearch(parameters.get("app2"));
			applicationStorePage.clickAppSearchButton();
			applicationStorePage.clickGetAppWithDepartment(parameters.get("app2"));

			String[] departmentList = parameters.get("department_list").split(";");
			ArrayList<String> actualList = applicationStorePage.getDepartmentList();
			for (int i = 0; i < departmentList.length; i++) {
				status = actualList.get(i).contains(departmentList[i]);
				reportLog(status, testContext.getName(), "Department displayed: " + departmentList[i], "3." + i,
						departmentList[i] + " is displayed successfully");
				org.testng.Assert.assertTrue(status);
			}
			applicationStorePage.selectDepartment(parameters.get("department1"));
			applicationStorePage.clickSaveButton();

			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department1"));
			createAppPage = dashboardPage.clickApp();
			status = createAppPage.isAppNameDisplayed(parameters.get("app2"));
			reportLog(status, testContext.getName(), "Verify the application appears on Apps", "4.0",
					parameters.get("app2") + " :appears on Apps");
			org.testng.Assert.assertTrue(status);

			// pre-condition check: if previous test run didn't delete pending app, then delete
			//createAppPage.checkAndDeletePendingApp(parameters.get("app2"));
			// end check
			
			createAppPage.clickQuickLink(parameters.get("app2"));
			createAppPage.clickQuickLinkMenu(parameters.get("app2"), parameters.get("menu"));
			createAppPage.acceptPopUpMessage();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void noAccessTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company reporter: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickCloseToastButton();
			boolean status = dashboardPage.isApplicationStoreDisplayed();
			reportLog(!status, testContext.getName(),
					"Verify Application Store does not appear for: " + parameters.get("username"), "1.1",
					"Application Store does not appear.");
			org.testng.Assert.assertFalse(status);

			String[] userList = parameters.get("username_list").split(";");
			for (int i = 0; i < userList.length; i++) {
				LoginPage login = dashboardPage.clickLogOut();
				login.typeusername(userList[i]);
				login.typepassword(parameters.get("password"));
				login.Clickonloginbutton();
				if (dashboardPage.isToastMessageDisplayed()) {
					dashboardPage.clickCloseToastButton();
				}
				status = dashboardPage.isApplicationStoreDisplayed();
				reportLog(!status, testContext.getName(),
						"Verify Application Store does not appear for: " + userList[i], Integer.toString(i + 2),
						"Application Store does not appear.");
				org.testng.Assert.assertFalse(status);
			}

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void searchFilterTest(String testDescription, String rallyLink, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription,
					rallyLink);
			DashboardPage dashboardPage = new DashboardPage(driver);
			ApplicationStorePage applicationStorePage = dashboardPage.clickApplicationStore();
			applicationStorePage.clickDDLOption(parameters.get("option"));
			ArrayList<String> sortActualList = applicationStorePage.getSortByList();
			String[] sortExpectedList = parameters.get("sortby_list").split(";");
			verifyDropdownValuesnew("1.", sortActualList, sortExpectedList,
					"Verify the option appear in the correct order for Sort By.", testContext);

			applicationStorePage.selectSortOption(parameters.get("sort_option"));
			String[] appList = parameters.get("app_list").split(";");
			for (int j = 1; j <= 2; j++) {
				boolean status = applicationStorePage.getAppNameByIndex(j).contains(appList[j - 1]);
				reportLog(status, testContext.getName(),
						"Verify the application " + appList[j - 1] + " appears on index " + j, "2." + j,
						"Verified the application " + appList[j - 1] + " appears on index " + j);
				org.testng.Assert.assertTrue(status);
			}

			String[] pageList = parameters.get("page_list").split(";");
			for (int j = 0; j < pageList.length; j++) {
				applicationStorePage.clickPaginationNumber(pageList[j]);
				boolean status = applicationStorePage.isRequestADemoButtonDisplayed();
				reportLog(status, testContext.getName(),
						"Verify the page rendered completely on clicking page index " + pageList[j], "3.1." + j,
						"Page rendered completely on clicking page index " + pageList[j]);
				org.testng.Assert.assertTrue(status);
			}

			applicationStorePage = dashboardPage.clickApplicationStore();
			applicationStorePage.clickDDLOption(parameters.get("option"));
			applicationStorePage.clickSubCategoryButton();
			applicationStorePage.clickSubCategory(parameters.get("category"));
			boolean status = Integer.parseInt(parameters.get("app_count")) == applicationStorePage.getAppCount();
			reportLog(status, testContext.getName(),
					"Verify on selecting " + parameters.get("category") + " app count is: "
							+ applicationStorePage.getAppCount(),
					"3.2", "On selecting " + parameters.get("category") + " app count is: "
							+ applicationStorePage.getAppCount());
			org.testng.Assert.assertTrue(status);

			applicationStorePage.clickCountryFilter();
			applicationStorePage.clickCountryCheckbox(parameters.get("country"));
			applicationStorePage.clickApplyButton();
			status = applicationStorePage.getAppCount() == 1;
			reportLog(status, testContext.getName(), "Verify one app apears.", "4.0", "Verified one app appears.");
			org.testng.Assert.assertTrue(status);

			status = applicationStorePage.getAppNameByIndex(1).contains(parameters.get("app"));
			reportLog(status, testContext.getName(), "Verify app " + parameters.get("app") + " appears", "4.1",
					parameters.get("app") + " : app appears.");
			org.testng.Assert.assertTrue(status);

			LoginPage login = dashboardPage.clickLogOut();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			applicationStorePage = dashboardPage.clickApplicationStore();
			applicationStorePage.clickDDLOption(parameters.get("option2"));
			applicationStorePage.enterAppToSearch(parameters.get("search"));
			applicationStorePage.clickAppSearchButton();
			status = applicationStorePage.getAppCount() == 1;
			reportLog(status, testContext.getName(), "Verify one app apears.", "5.0", "Verified one app appears.");
			org.testng.Assert.assertTrue(status);

			status = applicationStorePage.getAppNameByIndex(1).contains(parameters.get("app2"));
			reportLog(status, testContext.getName(), "Verify app " + parameters.get("app2") + " appears", "5.1",
					parameters.get("app2") + " : app appears.");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
