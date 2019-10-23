package com.canvas.qa.test.BETAFRT;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.ReportsPage;
import com.canvas.qa.pages.WorkflowPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class ReportsTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void activeUserReportTest(String testDescription, String rallyLink, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			String tmpFolderPath = parameters.get("app_file_path");
			String expectedFileName1 = parameters.get("file_name");
			String filepath1 = System.getProperty("user.dir") + tmpFolderPath + expectedFileName1;
			File file1 = new File(filepath1);
			boolean status = file1.exists();
			if (status) {
				file1.delete();
			}

			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company reporter: " + parameters.get("username"), testContext, testDescription,
					rallyLink);
			DashboardPage dashboardPage = new DashboardPage(driver);
			WorkflowPage workflowPage = new WorkflowPage(driver);
			ReportsPage reportsPage = dashboardPage.clickReports();
			reportsPage.activeUserReportClick();
			reportsPage.selectReportApp(parameters.get("app1"));
			reportsPage.selectBeginDate();
			
			workflowPage.selectDate(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);
			reportsPage.selectEndDate();
			workflowPage.selectDate(parameters.get("create_select_year_to1"), parameters.get("create_select_month_to1"),
					parameters.get("create_select_day_to1"),1);
			
			reportsPage.clickViewButtonElement();
			status = reportsPage.verifyDataDisplayed(parameters.get("user"), parameters.get("count"),
					parameters.get("date"));
			reportLog(status, testContext.getName(), "Verify user276@yahoo.com, 1, 19 Oct 17 displayed", "1.1",
					"user276@yahoo.com, 1, 19 Oct 17 displayed successfully.");
			org.testng.Assert.assertTrue(status);

			LoginPage login = dashboardPage.clickLogOut();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage.clickAccount();
			reportsPage = dashboardPage.clickReports();
			reportsPage.activeUserReportClick();
			String[] appList = parameters.get("app_list").split(";");
			ArrayList<String> appActualList = reportsPage.getAppList();
			for (int i = 0; i < appList.length; i++) {
				status = appActualList.contains(appList[i]);
				reportLog(status, testContext.getName(), "Verify app displayed: " + appList[i], "2.1." + i,
						"App displayed: " + appList[i]);
				org.testng.Assert.assertTrue(status);
			}
			reportsPage.selectReportApp(parameters.get("app1"));
			reportsPage.selectBeginDate();
			
			workflowPage.selectDate(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);
			reportsPage.selectEndDate();
			workflowPage.selectDate(parameters.get("create_select_year_to1"), parameters.get("create_select_month_to1"),
					parameters.get("create_select_day_to1"),1);
			reportsPage.clickViewButtonElement();
			status = reportsPage.verifyDataDisplayed(parameters.get("user"), parameters.get("count"),
					parameters.get("date"));
			reportLog(status, testContext.getName(), "Verify user276@yahoo.com, 1, 19 Oct 17 displayed", "2.2",
					"user276@yahoo.com, 1, 19 Oct 17 displayed successfully.");
			org.testng.Assert.assertTrue(status);

			reportsPage.selectReportApp(parameters.get("app2"));
			reportsPage.selectBeginDate();
			
			workflowPage.selectDate(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);
			reportsPage.selectEndDate();
			workflowPage.selectDate(parameters.get("create_select_year_to1"), parameters.get("create_select_month_to1"),
					parameters.get("create_select_day_to1"),1);
			reportsPage.clickViewButtonElement();
			status = reportsPage.verifyDataDisplayed(parameters.get("username2"), parameters.get("count"),
					parameters.get("date"));
			reportLog(status, testContext.getName(), "Verify db277@gmail.com, 1, 19 Oct 17 displayed", "3.0",
					"db277@gmail.com, 1, 19 Oct 17 displayed successfully.");
			org.testng.Assert.assertTrue(status);
			reportsPage.exportReport();
			String workingDir = System.getProperty("user.dir");
			String comapre_filepath1 = workingDir + parameters.get("compare_file_path");
			file1 = new File(filepath1);
			status = file1.exists();
			reportLog(status, testContext.getName(), "Verify " + parameters.get("file_name") + " is created.", "4.0",
					parameters.get("file_name") + " created successfully");
			org.testng.Assert.assertTrue(status);

			int value = comparefiles(filepath1, comapre_filepath1);
			status = value == 0 ? true : false;
			reportLog(status, testContext.getName(), "Verify " + parameters.get("file_name") + " content", "4.2",
					"db277@gmail.com, Active User Report App, 1, 10/19/2017 displays correctly on the excel sheet");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void userGroupsAppsReportsTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as a company designer : " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			ReportsPage reportsPage = dashboardPage.clickReports();
			boolean status = reportsPage.isUsersGroupsAppsReportDisplayed();
			reportLog(!status, testContext.getName(),
					"Company designer does not have access to Users, Groups, And Apps Report.", "1.1",
					"Company designer does not have access to Users, Groups, And Apps Report.");
			org.testng.Assert.assertFalse(status);

			LoginPage login = dashboardPage.clickLogOut();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage.clickAccount();
			reportsPage = dashboardPage.clickReports();
			status = reportsPage.isUsersGroupsAppsReportDisplayed();

			reportLog(status, testContext.getName(), "Users, Groups and Apps Report displays successfully. ", "2.0",
					"Users, Groups and Apps Report displays successfully. ");
			org.testng.Assert.assertTrue(status);

			reportsPage.clickUsersGroupsAppsReport();
			ArrayList<String> reportActualList = reportsPage.getReportList();
			String[] reportExpectedList = parameters.get("reportbylist").split(";");
			verifyDropdownValuesnew("3.", reportActualList, reportExpectedList,
					"Verify the correct option appear in the correct order.", testContext);

			reportsPage.selectByOption(reportExpectedList[0]);
			reportsPage.clickViewButtonElement();
			String[] emailList = parameters.get("email_list").split(";");
			String[] appsList = parameters.get("apps_list").split(";");
			for (int i = 0; i < emailList.length; i++) {
				status = reportsPage.isEmailDisplayed(emailList[i]);
				reportLog(status, testContext.getName(), "Verify user email displayed: " + emailList[i], "4." + i,
						"User email displayed: " + emailList[i]);
				org.testng.Assert.assertTrue(status);

				String[] app = appsList[i].split(",");
				for (int j = 0; j < app.length; j++) {
					status = reportsPage.isAppDisplayed(emailList[i], app[j]);
					reportLog(status, testContext.getName(), "Verify app displayed: " + app[j], "4." + i + "." + j + 1,
							"App displayed: " + app[j]);
					org.testng.Assert.assertTrue(status);
				}
			}

			reportsPage.selectByOption(reportExpectedList[1]);
			reportsPage.clickViewButtonElement();
			status = reportsPage.isGroupNameDisplayed(parameters.get("group_name"));
			reportLog(status, testContext.getName(), "Verify group name displayed: " + parameters.get("group_name"),
					"5.0", "Group name displayed: " + parameters.get("group_name"));
			org.testng.Assert.assertTrue(status);

			status = reportsPage.isGroupDescriptionDisplayed(parameters.get("group_desc"));
			reportLog(status, testContext.getName(),
					"Verify group description displayed: " + parameters.get("group_desc"), "5.1",
					"Group description displayed: " + parameters.get("group_desc"));
			org.testng.Assert.assertTrue(status);

			status = reportsPage.isAssignedAppDisplayed(parameters.get("assigned_app"));
			reportLog(status, testContext.getName(), "Verify assigned app displayed: " + parameters.get("assigned_app"),
					"5.2", "Assigned app displayed: " + parameters.get("assigned_app"));
			org.testng.Assert.assertTrue(status);

			reportsPage.selectByOption(reportExpectedList[2]);
			reportsPage.clickViewButtonElement();
			String[] groupList = parameters.get("group_list").split(";");
			for (int i = 0; i < emailList.length; i++) {
				status = reportsPage.isEmailDisplayed(emailList[i]);
				reportLog(status, testContext.getName(), "Verify user email displayed: " + emailList[i], "6." + i,
						"User email displayed: " + emailList[i]);
				org.testng.Assert.assertTrue(status);

				status = reportsPage.isGroupDisplayed(emailList[i], groupList[i]);
				reportLog(status, testContext.getName(), "Verify group displayed: " + groupList[i], "6." + i + ".1",
						"Group displayed: " + groupList[i]);
				org.testng.Assert.assertTrue(status);
			}

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
