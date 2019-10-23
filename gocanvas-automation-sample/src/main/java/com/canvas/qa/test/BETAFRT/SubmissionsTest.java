package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.WorkflowPage;
import com.canvas.qa.pages.submissions.SubmissionAppsPage;
import com.canvas.qa.pages.submissions.SubmissionSearchPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class SubmissionsTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void advanceSearchTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as : " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			WorkflowPage submissionSearch = new WorkflowPage(driver);
			SubmissionAppsPage submissionAppsPage = dashboardPage.clickSubmissions();
			SubmissionSearchPage submissionSearchPage = submissionAppsPage.clickAppName(parameters.get("app"));
			submissionSearchPage.clickAdvanceSearch();
			submissionSearchPage.selectScreen(parameters.get("screen"));
			submissionSearchPage.enterShortText(parameters.get("text"));
			submissionSearchPage.clickSearch();
			boolean status = submissionSearchPage.isMessageDisplayed(parameters.get("message"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("message") + " is displayed", "1.1",
					parameters.get("message") + " is displayed");
			org.testng.Assert.assertTrue(status);

			submissionSearchPage.clickAppBreadCrumb(parameters.get("app"));
			submissionSearchPage.clickAdvanceSearch();
			submissionSearchPage.selectScreen(parameters.get("screen2"));
			submissionSearchPage.selectSubmitter(parameters.get("submitter2"));
			
			
			submissionSearchPage.selectStartDate1();
			
			submissionSearch.selectDate(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);
			submissionSearchPage.selectEndDate1();
			
			submissionSearch.selectDate(parameters.get("create_select_year_to1"), parameters.get("create_select_month_to1"),
					parameters.get("create_select_day_to1"),1);
			
//					parameters.get("month_range_from_no"), parameters.get("year_range_from_no"));
//			submissionSearchPage.selectEndDate(parameters.get("date_range_to_no"), parameters.get("month_range_to_no"),
//					parameters.get("year_range_to_no"));
			
		
			
			
			
			
			submissionSearchPage.clickSearch();
			status = submissionSearchPage.isSubmissionDateDisplayed(parameters.get("Submission_Date"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("Submission_Date") + " is displayed",
					"2.1", parameters.get("Submission_Date") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isSbmitterDisplayed(parameters.get("Submitter"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("Submitter") + " is displayed", "2.2",
					parameters.get("Submitter") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isSubmissionNumberDisplayed(parameters.get("Number"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("Number") + " is displayed", "2.3",
					parameters.get("Number") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isSubmissionIDDisplayed(parameters.get("Submission_ID"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("Submission_ID") + " is displayed",
					"2.4", parameters.get("Submission_ID") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isAppNameDisplayed(parameters.get("App_Name"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("App_Name") + " is displayed", "2.5",
					parameters.get("App_Name") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isAppVersionDisplayed(parameters.get("App_Version"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("App_Version") + " is displayed", "2.6",
					parameters.get("App_Version") + " is displayed");
			org.testng.Assert.assertTrue(status);

			submissionSearchPage.clickAppBreadCrumb(parameters.get("app"));
			submissionSearchPage.clickAdvanceSearch();
			submissionSearchPage.enterSubmissionID(parameters.get("submission_id"));
			
			submissionSearchPage.selectStartDate1();
			submissionSearch.selectDate(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);
			
			submissionSearchPage.selectEndDate1();
			submissionSearch.selectDate(parameters.get("create_select_year_to1"), parameters.get("create_select_month_to1"),
					parameters.get("create_select_day_to1"),1);
			
//			submissionSearchPage.selectStartDate(parameters.get("date_range_from_no"),
//					parameters.get("month_range_from_no"), parameters.get("year_range_from_no"));
//			submissionSearchPage.selectEndDate(parameters.get("date_range_to_no"), parameters.get("month_range_to_no"),
//					parameters.get("year_range_to_no"));
			
			
			
			submissionSearchPage.clickSearch();
			status = submissionSearchPage.isSubmissionDateDisplayed(parameters.get("Submission_Date"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("Submission_Date") + " is displayed",
					"3.1", parameters.get("Submission_Date") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isSbmitterDisplayed(parameters.get("Submitter"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("Submitter") + " is displayed", "3.2",
					parameters.get("Submitter") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isSubmissionNumberDisplayed(parameters.get("Number"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("Number") + " is displayed", "3.3",
					parameters.get("Number") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isSubmissionIDDisplayed(parameters.get("Submission_ID"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("Submission_ID") + " is displayed",
					"3.4", parameters.get("Submission_ID") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isAppNameDisplayed(parameters.get("App_Name"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("App_Name") + " is displayed", "3.5",
					parameters.get("App_Name") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isAppVersionDisplayed(parameters.get("App_Version"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("App_Version") + " is displayed", "3.6",
					parameters.get("App_Version") + " is displayed");
			org.testng.Assert.assertTrue(status);

			submissionSearchPage.clickAppBreadCrumb(parameters.get("app"));
			submissionSearchPage.clickAdvanceSearch();
			submissionSearchPage.enterSubmissionNumber(parameters.get("submission_number"));
			
			submissionSearchPage.selectStartDate1();
			submissionSearch.selectDate(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);
			
			submissionSearchPage.selectEndDate1();
			submissionSearch.selectDate(parameters.get("create_select_year_to1"), parameters.get("create_select_month_to1"),
					parameters.get("create_select_day_to1"),1);
			
//			submissionSearchPage.selectStartDate(parameters.get("date_range_from_no"),
//					parameters.get("month_range_from_no"), parameters.get("year_range_from_no"));
//			submissionSearchPage.selectEndDate(parameters.get("date_range_to_no"), parameters.get("month_range_to_no"),
//					parameters.get("year_range_to_no"));
			
			
			submissionSearchPage.clickSearch();
			status = submissionSearchPage.isSubmissionDateDisplayed(parameters.get("Submission_Date"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("Submission_Date") + " is displayed",
					"4.1", parameters.get("Submission_Date") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isSbmitterDisplayed(parameters.get("Submitter"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("Submitter") + " is displayed", "4.2",
					parameters.get("Submitter") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isSubmissionNumberDisplayed(parameters.get("Number"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("Number") + " is displayed", "4.3",
					parameters.get("Number") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isSubmissionIDDisplayed(parameters.get("Submission_ID"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("Submission_ID") + " is displayed",
					"4.4", parameters.get("Submission_ID") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isAppNameDisplayed(parameters.get("App_Name"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("App_Name") + " is displayed", "4.5",
					parameters.get("App_Name") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionSearchPage.isAppVersionDisplayed(parameters.get("App_Version"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("App_Version") + " is displayed", "4.6",
					parameters.get("App_Version") + " is displayed");
			org.testng.Assert.assertTrue(status);

			submissionSearchPage.clickAppBreadCrumb(parameters.get("app"));
			submissionSearchPage.clickAdvanceSearch();
			
			submissionSearchPage.selectStartDate1();
			submissionSearch.selectDate(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);
			
			submissionSearchPage.selectEndDate1();
			submissionSearch.selectDate(parameters.get("create_select_year_to1"), parameters.get("create_select_month_to1"),
					parameters.get("create_select_day_to1"),1);
//			submissionSearchPage.selectStartDate(parameters.get("date_range_from_no"),
//					parameters.get("month_range_from_no"), parameters.get("year_range_from_no"));
//			submissionSearchPage.selectEndDate(parameters.get("date_range_to_no"), parameters.get("month_range_to_no"),
//					parameters.get("year_range_to_no"));
			
			submissionSearchPage.selectScreen(parameters.get("screen"));
			submissionSearchPage.enterShortText(parameters.get("text"));
			submissionSearchPage.clickSearch();
			status = submissionSearchPage.isViewAndDownloadPDFButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify View and Download PDF button displayed.", "5.0",
					"View and Download PDF button displayed.");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
