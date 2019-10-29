package com.canvas.qa.test.BETAFRT;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.ReportsPage;
import com.canvas.qa.pages.WorkflowPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.submissions.ExportSubmissionPage;
import com.canvas.qa.pages.submissions.SubmissionSearchPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

/**
 * 
 * ID:TC7598 Name:Return on Investment
 * 
 *
 */
public class ReportTest extends BrowserLaunchTest {

	/**
	 * TC7594 Name:Mobile Client Version Report
	 **/

	@Test
	@Parameters({ "testdescription" })
	public void inactiveUserReport(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException

	{
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

			CreateAppPage createApp = new CreateAppPage(driver);
			LoginPage loginPage = new LoginPage(driver);
			ReportsPage reportPage = new ReportsPage(driver);
			WorkflowPage inactiveUserReportSelectDate = new WorkflowPage(driver);
			//SubmissionSearchPage inactiveUserReport = new SubmissionSearchPage(driver);
			//ExportSubmissionPage inactiveUserReportDateRange = new ExportSubmissionPage(driver);
			performLogin(1, parameters.get("username1"), parameters.get("password1"),
					"Login as the company admin : " + parameters.get("username1"), testContext, testDescription);

			boolean inactiveReportReportCompanyAdmin = reportPage.inactiveUserReportCA();
			reportLog(inactiveReportReportCompanyAdmin, testContext.getName(),
					"Verify user has access to the Inactive User Report", "1.2",
					"Inactive User report shows for a company admin for a start up account");
			org.testng.Assert.assertTrue(inactiveReportReportCompanyAdmin);

			createApp.logout();
			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password2"));
			loginPage.Clickonloginbutton();

			boolean inactiveReportReportCompanyReporter = reportPage.inactiveUserReportCR();
			reportLog(!inactiveReportReportCompanyReporter, testContext.getName(),
					"Verify Inactive User report does not display ", "2.1",
					"Inactive user Report does not display for a company reporter for a Business account ");
			org.testng.Assert.assertFalse(inactiveReportReportCompanyReporter);

			createApp.logout();
			loginPage.typeusername(parameters.get("username3"));
			loginPage.typepassword(parameters.get("password3"));
			loginPage.Clickonloginbutton();

			reportPage.verifyInactiveUserReportData();
			
			reportPage.selectBeginDate();
			inactiveUserReportSelectDate.selectDate(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);
			
			reportPage.selectEndDate();
			inactiveUserReportSelectDate.selectDate(parameters.get("create_select_year_to1"), parameters.get("create_select_month_to1"),
					parameters.get("create_select_day_to1"),1);
			
			//inactiveUserReportDateRange.selectBeginDate(parameters.get("beginDate"));
			//inactiveUserReportDateRange.selectEndDate(parameters.get("endDate"));

			ArrayList<String> appActualList = reportPage.verifyColumnNames();
			String[] appExpectedList = parameters.get("colName").split(";");
			verifyDropdownValuesnew("3.", appActualList, appExpectedList, "Verify the Column Names of report.",
					testContext);

			reportPage.exportReport();
			String workingDir = System.getProperty("user.dir");
			String compare_filepath1 = workingDir + parameters.get("compare_file_path");
			int value = comparefiles(filepath1, compare_filepath1);
			status = value == 0 ? true : false;
			reportLog(status, testContext.getName(), "Verify " + parameters.get("file_name") + " content", "4.1",
					"Content of each column including email is verfied in .csv file");
			org.testng.Assert.assertTrue(status);

			
			reportPage.selectBeginDate();
			inactiveUserReportSelectDate.selectDate(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);

			reportPage.selectEndDate();
			inactiveUserReportSelectDate.selectDate2(parameters.get("create_select_year_to2"), parameters.get("create_select_month_to2"),
					parameters.get("create_select_day_to2"),1,0);
			
			
			//inactiveUserReportDateRange.selectBeginDate(parameters.get("beginDate"));
			//inactiveUserReportDateRange.selectEndDate(parameters.get("endDateUpdated"));

			boolean inactiveUsermsg = reportPage.verifyMessage(parameters.get("daterang_msg"));
			reportLog(inactiveUsermsg, testContext.getName(), "Verify user sees no results", "5.1",
					"The date range was more than 30 days. Please select 30 days or less");
			org.testng.Assert.assertTrue(inactiveUsermsg);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	/**
	 * ID: TC7595 Name:Mobile Client Version Report
	 **/

	@Test
	@Parameters({ "testdescription" })
	public void mobileClientVersionReport(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException

	{
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

			CreateAppPage createApp = new CreateAppPage(driver);
			LoginPage loginPage = new LoginPage(driver);
			ReportsPage reportPage = new ReportsPage(driver);

			performLogin(1, parameters.get("username1"), parameters.get("password1"),
					"Login as the company reporter : " + parameters.get("username"), testContext, testDescription);

			boolean mcvReportCompanyReporter = reportPage.verifyMobileClientReport();
			reportLog(mcvReportCompanyReporter, testContext.getName(),
					"Verify the user can access the following report, Mobile Client Version Report ", "1.2",
					"Mobile Client Version Report Displays successfully");
			org.testng.Assert.assertTrue(mcvReportCompanyReporter);

			createApp.logout();
			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password2"));
			loginPage.Clickonloginbutton();

			boolean mcvReportCompanyDesigner = reportPage.verifyMobileClientReport();
			reportLog(mcvReportCompanyDesigner, testContext.getName(),
					"Verify the user can access the following area, Mobile Client Version Report ", "2.1",
					"Mobile Client Version Report Displays successfully");
			org.testng.Assert.assertTrue(mcvReportCompanyDesigner);

			createApp.logout();
			loginPage.typeusername(parameters.get("username3"));
			loginPage.typepassword(parameters.get("password3"));
			loginPage.Clickonloginbutton();

			ArrayList<String> appActualList = reportPage.verifyMobileClientReportAdmin();
			String[] appExpectedList = parameters.get("colName").split(";");
			verifyDropdownValuesnew("3.", appActualList, appExpectedList,
					"Verify the correct apps appear in the correct order.", testContext);

			reportPage.exportReport();
			String workingDir = System.getProperty("user.dir");
			String compare_filepath1 = workingDir + parameters.get("compare_file_path");
			int value = comparefiles(filepath1, compare_filepath1);
			status = value == 0 ? true : false;
			reportLog(status, testContext.getName(), "Verify " + parameters.get("file_name") + " content", "4.1",
					"Content in .csv file is correct");
			org.testng.Assert.assertTrue(status);

			createApp.logout();
			loginPage.typeusername(parameters.get("username4"));
			loginPage.typepassword(parameters.get("password4"));
			loginPage.Clickonloginbutton();

			boolean mcvReportCompanUser = reportPage.verifyMobileClientUserReport();
			reportLog(!mcvReportCompanUser, testContext.getName(),
					"Verify the user cannot access the Mobile Client Version Report. ", "5.1",
					"Mobile Client Report does not display for a company user on a Start up account");
			org.testng.Assert.assertFalse(mcvReportCompanUser);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	@Test
	@Parameters({ "testdescription" })
	public void roiReport(String testDescription, ITestContext testContext) throws IOException, InterruptedException

	{
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {

			CreateAppPage createApp = new CreateAppPage(driver);
			LoginPage loginPage = new LoginPage(driver);
			ReportsPage reportPage = new ReportsPage(driver);

			performLogin(1, parameters.get("username1"), parameters.get("password1"),
					"Login as the company reporter : " + parameters.get("username"), testContext, testDescription);

			boolean CompanyAdminRoiReport = reportPage.roiReportAccess();
			reportLog(CompanyAdminRoiReport, testContext.getName(),
					"Verify Return on Investment Report displays for a company admin ", "1.2",
					"Company admin has access to the Return on Investment reports on a Business Plan. ");
			org.testng.Assert.assertTrue(CompanyAdminRoiReport);

			createApp.logout();
			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password2"));
			loginPage.Clickonloginbutton();

			boolean CompanyDesignerRoiReport = reportPage.roiDiffrentUserReportAccess();
			reportLog(!CompanyDesignerRoiReport, testContext.getName(),
					"Verify company designer does not have access to the Return on Investment Report ", "2.2",
					"Company designer for a Business plan does not have access to Return on Investment Report ");
			org.testng.Assert.assertFalse(CompanyDesignerRoiReport);

			createApp.logout();
			loginPage.typeusername(parameters.get("username3"));
			loginPage.typepassword(parameters.get("password3"));
			loginPage.Clickonloginbutton();

			boolean CompanyReporterRoiReport = reportPage.roiDiffrentUserReportAccess();
			reportLog(!CompanyReporterRoiReport, testContext.getName(),
					"Verify company reporter does not have access to the Return on Investment Report ", "3.2",
					"Company Reporter does not have access to the Return on Investment Report");
			org.testng.Assert.assertFalse(CompanyReporterRoiReport);

			createApp.logout();
			loginPage.typeusername(parameters.get("username1"));
			loginPage.typepassword(parameters.get("password1"));
			loginPage.Clickonloginbutton();

			reportPage.roiReportAccess();
			reportPage.clickROILink();

			boolean numberOfUsersPopulated = reportPage.countUserUsingForm(parameters.get("userBeforeUpdate"));
			reportLog(numberOfUsersPopulated, testContext.getName(),
					"Verify the number 4 appears for the question 'How many employees fill out information on forms' under Just the Basics. ",
					"4.1", "Correct number of users populated the Just the Basics question.");
			org.testng.Assert.assertTrue(numberOfUsersPopulated);

			boolean roiValueBeforeUpdate = reportPage.roiValueBeforeChange(parameters.get("YearlyROIBeforeUpdate"));
			reportLog(roiValueBeforeUpdate, testContext.getName(),
					"Yearly ROI value before updating value for user from '4' To '5'  ", "4.2",
					"Correct value of before update ROI display is verfied");
			org.testng.Assert.assertTrue(roiValueBeforeUpdate);

			boolean roiValueAfterUpdate = reportPage.roiValueAfterChange(parameters.get("YearlyROIAfterUpdate"),
					parameters.get("userAfterUpdate"));
			reportLog(roiValueAfterUpdate, testContext.getName(),
					"Yearly ROI value after updating value for user from '4' To '5'  ", "4.3",
					"Correct value of ROI display after updating value of user from 4 To 5 is verfied");
			org.testng.Assert.assertTrue(roiValueAfterUpdate);

			boolean roiValueAfterClearingValue = reportPage
					.roiValueAfterClearingAllValue(parameters.get("YearlyROIAfterClear"));
			reportLog(roiValueAfterClearingValue, testContext.getName(),
					"Clear all the values in all fields and hit calculate ", "5.1",
					"Yearly ROI and Total Savings In A Year now display $0.00 successfully");
			org.testng.Assert.assertTrue(roiValueAfterClearingValue);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

}
