package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.CompanyWeekly2of2Page;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

public class CompanyWeekly2of2Test extends BrowserLaunchTest {

	@Parameters({ "step", "testdescription" })
	public void verifyAddUserScreenLink(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		CompanyWeekly2of2Page dashboardLink = new CompanyWeekly2of2Page(driver);
		String valuesOfLink = null;

		valuesOfLink = dashboardLink.checkForAddUsersScreen();
		if (valuesOfLink.contains("Add Users"))

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ":Add Users screen appears.");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ":Add Users screen Not appears.");

		}

		org.testng.Assert.assertTrue(valuesOfLink.contains("Add Users"), "Add Users screen Not appears.");

	}

	@Parameters({ "step", "testdescription" })
	public void verifyAppScreenLink(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		CompanyWeekly2of2Page dashboardLink = new CompanyWeekly2of2Page(driver);
		String valuesOfLink = null;

		valuesOfLink = dashboardLink.checkForAppsscreen();
		if (valuesOfLink.contains("Apps"))

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ":Apps screen appears.");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ":Apps screen Not appears.");

		}

		org.testng.Assert.assertTrue(valuesOfLink.contains("Apps"), "Apps screen Not appears.");

	}

	@Parameters({ "step", "testdescription" })
	public void verifyROIScreenLink(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		CompanyWeekly2of2Page dashboardLink = new CompanyWeekly2of2Page(driver);
		String valuesOfLink = null;

		valuesOfLink = dashboardLink.checkForReturnonInvestmentscreen();
		if (valuesOfLink.contains("Return On Investment"))

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ":Return on Investment screen appears.");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ":Return on Investment screen Not appears.");

		}
		org.testng.Assert.assertTrue(valuesOfLink.contains("Return On Investment"),
				"Return on Investment screen Not appears.");
	}

	@Parameters({ "step", "testdescription" })
	public void verifySubmissionScreenLink(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		CompanyWeekly2of2Page dashboardLink = new CompanyWeekly2of2Page(driver);

		String val = null;
		String valuesOfLink = null;

		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		val = parameters.get("Submission_1");
		valuesOfLink = dashboardLink.checkForSubmissionscreen();

		if (valuesOfLink.equals(val))

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ":Submissions screen appears.");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": User Not able to See Submissions screen");
		}
		org.testng.Assert.assertTrue(valuesOfLink.equals(val), "User Not able to See Submissions screen.");
	}

}
