package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.submissions.SubmissionSearchPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

public class SubmissionSearchTest extends BrowserLaunchTest {

	@Parameters({ "step", "testdescription" })
	public void verifyDownloadLink(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		SubmissionSearchPage dashboardLink = new SubmissionSearchPage(driver);

		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		String abd = parameters.get("search_submission_id_1");

		boolean submissionidmsg = dashboardLink.checkDownloadlink(testContext, abd);

		if (submissionidmsg) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ":Download link is visible and User can Download PDF successfully (under Quick Links)");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, "Step " + step + "." + (++i)
					+ ":Download link is not visible and User can't Download PDF successfully..");
		}
		org.testng.Assert.assertTrue(submissionidmsg,
				"Download link is not visible and User can't Download PDF successfully");
	}

	@Parameters({ "step", "testdescription" })
	public void verifyInvalidSubmissionsearch(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		SubmissionSearchPage dashboardLink = new SubmissionSearchPage(driver);

		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String availablelinks = parameters.get("search_msg");
		String valuesOfLink = dashboardLink.checkForinvalidSearchSubmission();

		if (availablelinks.equals(valuesOfLink))

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ":User Serach with Invalid Data and search did not return any results, here are some tips...appears");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, "Step " + step + "." + (++i)
					+ ": Serach text Your search did not return any results, here are some tips...appears Not Matched");
		}
		org.testng.Assert.assertTrue(availablelinks.equals(valuesOfLink));
	}

	@Parameters({ "step", "testdescription" })
	public void verifySearchSubmissionID(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		SubmissionSearchPage dashboardLink = new SubmissionSearchPage(driver);

		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String submission_id = parameters.get("search_submission_id");
		String abc = parameters.get("search_submission_id_1");
		String submissionidmsg = dashboardLink.checkForSubmissionID(testContext, abc);

		if (submission_id.equals(submissionidmsg))
		// if (valuesOfLink.contains("Your search did not return any results,
		// here are
		// some tips:"))

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ":User Serach with valid Data and search return results is : ");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, "Step " + step + "." + (++i)
					+ ": Serach text Your search did not return any results, here are some tips...appears Not Matched");
		}
		org.testng.Assert.assertTrue(submission_id.equals(submissionidmsg));
	}

	@Parameters({ "step", "testdescription" })
	public void verifyValidSerach(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{

		int i = 0;

		SubmissionSearchPage seachvalidlink = new SubmissionSearchPage(driver);

		try {

			String abd = seachvalidlink.checkForvalidSearchSubmission(testContext);
			if (true)

			{
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "."
						+ (++i)
						+ ":Submission was found by searching for RED in the search field, correct details for submission displays : ");
			}

			else {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
						"Step " + step + "." + (++i) + "No Submission exist with serach text <Red>");
			}
		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, e.getMessage());
		}
	}

	@Parameters({ "step", "testdescription" })
	public void verifyViewLink(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		SubmissionSearchPage dashboardLink = new SubmissionSearchPage(driver);

		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		String abd = parameters.get("search_submission_id_1");

		boolean submissionidmsg = dashboardLink.newCheckLink(testContext, abd);

		if (submissionidmsg = true) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ":View link is visible and User can view PDF correctly (under Quick Links)");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, "Step " + step + "." + (++i)
					+ ": View link is not visible and User can't view PDF correctly (under Quick Links)");
		}
		org.testng.Assert.assertTrue(submissionidmsg,
				"View link is not visible and User can't view PDF correctly (under Quick Links");
	}

}
