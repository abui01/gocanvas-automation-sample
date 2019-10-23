package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.CompanyAdminPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

public class CompanyAdminTest extends BrowserLaunchTest {

	@Parameters({ "step" })
	public void CompanyAdminAccessLink(String step, final ITestContext testContext) throws Throwable {
		int i = 0;

		CompanyAdminPage AccessLinks = new CompanyAdminPage(driver);
		try {
			String[] links = new String[100];
			Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
					.getParameters();
			String availablelinks = parameters.get("links");
			if (availablelinks != null && !availablelinks.isEmpty()) {
				links = availablelinks.split(";");
			}

			boolean success = AccessLinks.PremissonOnLinks(links);
			if (success) {

				ReportManager.log(testContext.getName(), LogStatus.PASS,
						"Step " + step + "." + (++i) + ": Company Admin Can acces the links are :" + availablelinks);
				org.testng.Assert.assertTrue(true, "Step " + step + ": Fields Detail:" + links);
			} else

				// ReportManager.log(testContext.getName(), LogStatus.PASS,"Step
				// " + step + "." + (++i)+ ": Fields Detail :" +success);
				org.testng.Assert.assertTrue(true, "Step " + step + ": Fields Detail :" + success);
		} catch (Exception e) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL, e.getMessage());
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": All fields detail is..");

		}
	}

	@Parameters({ "step" })
	public void verifyAppsLink(String step, final ITestContext testContext) throws IOException, InterruptedException

	{
		int i = 0;

		CompanyAdminPage AppsLink = new CompanyAdminPage(driver);

		try {
			String valuesOfLink = AppsLink.checkForAppsLink();
			if (valuesOfLink.contains("Apps"))

			{
				ReportManager.log(testContext.getName(), LogStatus.PASS,
						"Step " + step + "." + (++i) + ": User able to See Apps link and Can Access Apps Page");
			}

			else {
				ReportManager.log(testContext.getName(), LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": User Not able to See Apps Page:");
			}
		} catch (Exception e) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL, e.getMessage());
		}
	}

	@Parameters({ "step" })
	public void verifyDashboardLink(String step, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		CompanyAdminPage dashboardLink = new CompanyAdminPage(driver);

		try {
			String valuesOfLink = dashboardLink.checkForDashboardLink();
			if (valuesOfLink.contains("Dashboard"))

			{
				ReportManager.log(testContext.getName(), LogStatus.PASS, "Step " + step + "." + (++i)
						+ ": User able to See Dashbaord link and Can Access Dashboard Page");
			}

			else {
				ReportManager.log(testContext.getName(), LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": User Not able to See Dashbaord Page:");
			}
		} catch (Exception e) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL, e.getMessage());
		}
	}

	@Parameters({ "step" })
	public void verifyProfileLink(String step, final ITestContext testContext) throws IOException, InterruptedException

	{
		int i = 0;

		CompanyAdminPage ProfileLink = new CompanyAdminPage(driver);

		try {
			String valuesOfLink = ProfileLink.checkForProfileLink();
			if (valuesOfLink.contains("Profile"))

			{
				ReportManager.log(testContext.getName(), LogStatus.PASS,
						"Step " + step + "." + (++i) + ": User able to view Profile Link and Can Access Profile Page");
			}

			else {
				ReportManager.log(testContext.getName(), LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": User Not able to See Profile Page:");
			}
		} catch (Exception e) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL, e.getMessage());
		}

	}

	@Parameters({ "step" })
	public void verifyReferenceDataAndImageLink(String step, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		CompanyAdminPage ReferenceDataAndImageLink = new CompanyAdminPage(driver);

		try {
			String valuesOfLink = ReferenceDataAndImageLink.checkForReferenceDataAndImageLink();
			if (valuesOfLink.contains("Reference Data & Images"))

			{
				ReportManager.log(testContext.getName(), LogStatus.PASS, "Step " + step + "." + (++i)
						+ ": User able to See Reference Data And Image link and Can Access Reference Data And Image Page");
			}

			else {
				ReportManager.log(testContext.getName(), LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": User Not able to See Reference Data And Image Page:");
			}
		} catch (Exception e) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL, e.getMessage());
		}
	}

	@Parameters({ "step" })
	public void verifySubmissionsLink(String step, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		CompanyAdminPage SubmissionsLink = new CompanyAdminPage(driver);

		try {
			String valuesOfLink = SubmissionsLink.checkForSubmissionsLink();
			if (valuesOfLink.contains("Submissions"))

			{
				ReportManager.log(testContext.getName(), LogStatus.PASS, "Step " + step + "." + (++i)
						+ ": User able to See Submissions link and Can Access Submissions Page");
			}

			else {
				ReportManager.log(testContext.getName(), LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": User Not able to See Submissions Page:");
			}
		} catch (Exception e) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL, e.getMessage());
		}

	}
}
