package com.canvas.qa.test.BETAFRT;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.submissions.SubmissionAppsPage;
import com.canvas.qa.pages.submissions.SubmissionSearchPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class ElasticSearchTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void submissionsElasticSearchTest(String testDescription, String rallyLink, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		/*try {*/
			String tmpFolderPath = parameters.get("file_path");
			String csvExpectedFileName = parameters.get("csv_file");
			String csvFilePath = System.getProperty("user.dir") + tmpFolderPath + csvExpectedFileName;
			File csvFile = new File(csvFilePath);
			Files.deleteIfExists(Paths.get(csvFilePath));

			String xmlExpectedFileName = parameters.get("xml_file");
			String xmlFilePath = System.getProperty("user.dir") + tmpFolderPath + xmlExpectedFileName;
			File xmlFile = new File(xmlFilePath);
			Files.deleteIfExists(Paths.get(xmlFilePath));

			LoginPage login = new LoginPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			String[] userList = parameters.get("userlist").split(";");
			String[] countList = parameters.get("countlist").split(";");
			for (int i = 0; i < userList.length; i++) {
				login.gotoLogin();
				login.typeusername(userList[i]);
				login.typepassword(parameters.get("password"));
				login.Clickonloginbuttonforinvalidlogin();
				if (login.ifTermsOfUseDispalyed()) {
					login.clickTermsOfUseCheckbox();
					login.typepassword(parameters.get("password"));
					login.Clickonloginbuttonforinvalidlogin();
				}

				SubmissionAppsPage submissionAppsPage = dashboardPage.clickSubmissions();
				SubmissionSearchPage submissionSearchPage = submissionAppsPage
						.clickAppName(parameters.get("submission"));

				submissionSearchPage.enterSearchText(parameters.get("searchtext"));
				submissionSearchPage.clickSearchIcon();
				boolean status = String.valueOf(submissionSearchPage.getResultCount()).equals(countList[i]);
				reportLog(status, testContext.getName(), "Verify 2 results display which searches the Date Field",
						"1." + (i + 1) + ".1.0", "Verify 2 results display which searches the Date Field");
				org.testng.Assert.assertTrue(status);

				String[] searchTextList = parameters.get("searchtext_list").split(";");
				for (int j = 0; j < searchTextList.length; j++) {
					submissionSearchPage.enterSearchText(searchTextList[j]);
					submissionSearchPage.clickSearchIcon();
					status = submissionSearchPage.isSubmissionDateDisplayed(parameters.get("date1"))
							|| submissionSearchPage.isSubmissionDateDisplayed(parameters.get("date2"));
					reportLog(status, testContext.getName(), "Verify Submission Date",
							"1." + (i + 1) + ".1." + (j + 1) + ".0", "Verified Submission Date");
					org.testng.Assert.assertTrue(status);

					status = submissionSearchPage.isSbmitterDisplayed(parameters.get("submitter"));
					reportLog(status, testContext.getName(), "Verify Submitter",
							"1." + (i + 1) + ".1." + (j + 1) + ".1", "Verified Submitter");
					org.testng.Assert.assertTrue(status);
				}

				submissionSearchPage.clickAdvanceSearch();
				submissionSearchPage.selectScreen(parameters.get("screen"));
				submissionSearchPage.selectStartDate(parameters.get("date_range_from1"),
						parameters.get("month_range_from1"), parameters.get("year_range_from1"));
				submissionSearchPage.selectSubmitter(parameters.get("user"));
				submissionSearchPage.clickSearch();
				status = String.valueOf(submissionSearchPage.getResultCount()).equals("5");
				reportLog(status, testContext.getName(), "Verify Result count", "1." + (i + 1) + ".2.1",
						"Verified Result count");
				//org.testng.Assert.assertTrue(status);

				submissionSearchPage.selectGroup(parameters.get("group"));
				submissionSearchPage.clickSearch();
				status = String.valueOf(submissionSearchPage.getResultCount()).equals("5");
				reportLog(status, testContext.getName(), "Verify Result count", "1." + (i + 1) + ".2.2",
						"Verified Result count");
			//	org.testng.Assert.assertTrue(status);

				submissionSearchPage.selectAppVersion(parameters.get("app_version"));
				submissionSearchPage.clickSearch();
				status = String.valueOf(submissionSearchPage.getResultCount()).equals("4");
				reportLog(status, testContext.getName(), "Verify Result count", "1." + (i + 1) + ".2.3",
						"Verified Result count");
			//	org.testng.Assert.assertTrue(status);

				login = dashboardPage.clickLogOut();
			}

			login.typeusername(parameters.get("username"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbuttonforinvalidlogin();
			if (login.ifTermsOfUseDispalyed()) {
				login.clickTermsOfUseCheckbox();
				login.typepassword(parameters.get("password"));
				login.Clickonloginbuttonforinvalidlogin();
			}
			SubmissionAppsPage submissionAppsPage = dashboardPage.clickSubmissions();
			SubmissionSearchPage submissionSearchPage = submissionAppsPage.clickAppName(parameters.get("submission"));
			submissionSearchPage.clickAdvanceSearch();
			boolean status = submissionSearchPage.isDisabledExportButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify Export button is grayed out", "4.1",
					"Verified Export button is grayed out");
			org.testng.Assert.assertTrue(status);

			submissionSearchPage.selectAppVersion(parameters.get("app_version"));
			status = submissionSearchPage.isEnabledExportButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify Export button is not grayed out", "4.2",
					"Verified Export button is not grayed out");
			org.testng.Assert.assertTrue(status);

			submissionSearchPage.selectExportFormat(parameters.get("exportformat1"));
			submissionSearchPage.clickExportButton();
			customWait(10);
			csvFile = new File(csvFilePath);
			status = csvFile.exists();
			reportLog(status, testContext.getName(), "Verify user able to successfully download the CSV export", "4.3",
					"Verified user able to successfully download the CSV export");
			org.testng.Assert.assertTrue(status);

			submissionSearchPage.selectExportFormat(parameters.get("exportformat2"));
			submissionSearchPage.clickExportButton();
			customWait(20);
			xmlFile = new File(xmlFilePath);
			status = xmlFile.exists();
			reportLog(status, testContext.getName(), "Verify user able to successfully download the XML export", "4.4",
					"Verified user able to successfully download the XML export");
			org.testng.Assert.assertTrue(status);

			submissionAppsPage = dashboardPage.clickSubmissions();
			submissionSearchPage = submissionAppsPage.clickAppName(parameters.get("submission"));
			submissionSearchPage.enterSearchText(parameters.get("submission_id"));
			submissionSearchPage.clickSearchIcon();
			submissionSearchPage.clickDateByRowIndex(1);
			submissionSearchPage.clickEditIcon(parameters.get("edit_icon"));
			submissionSearchPage.enterTextValue(parameters.get("edit_icon"), parameters.get("new_text"));
			submissionSearchPage.clickTickIcon(parameters.get("edit_icon"));
			submissionSearchPage.clickSubmissionTitle(parameters.get("submission"));
			submissionSearchPage.enterSearchText(parameters.get("new_text"));
			submissionSearchPage.clickSearchIcon();
			status = submissionSearchPage.isSubmissionIDDisplayed(parameters.get("submission_id"));
			reportLog(status, testContext.getName(), "Verify 9e25070ed54711c1-1503517262277 Result displays", "5.1",
					"Verified 9e25070ed54711c1-1503517262277 Result displays");
			org.testng.Assert.assertTrue(status);

			submissionSearchPage.enterSearchText(parameters.get("old_text"));
			submissionSearchPage.clickSearchIcon();
			status = submissionSearchPage.isSubmissionIDDisplayed(parameters.get("submission_id"));
			reportLog(!status, testContext.getName(), "Verify No results should display", "5.2",
					"Verified No results should display");
			org.testng.Assert.assertFalse(status);

			submissionSearchPage.enterSearchText(parameters.get("submission_id"));
			submissionSearchPage.clickSearchIcon();
			submissionSearchPage.clickDateByRowIndex(1);
			submissionSearchPage.clickEditIcon(parameters.get("edit_icon"));
			submissionSearchPage.enterTextValue(parameters.get("edit_icon"), parameters.get("old_text"));
			submissionSearchPage.clickTickIcon(parameters.get("edit_icon"));
			submissionSearchPage.clickSubmissionTitle(parameters.get("submission"));
			submissionSearchPage.enterSearchText(parameters.get("old_text"));
			submissionSearchPage.clickSearchIcon();
			status = submissionSearchPage.isSubmissionIDDisplayed(parameters.get("submission_id"));
			reportLog(status, testContext.getName(), "Verify 9e25070ed54711c1-1503517262277 Result displays", "5.3",
					"Verified 9e25070ed54711c1-1503517262277 Result displays");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), "Step 6 cannot be automated.", "6.0",
					"Verified Step 6 cannot be automated.");
			reportLog(true, testContext.getName(), "Step 7 cannot be automated.", "7.0",
					"Verified Step 6 cannot be automated.");

			login = dashboardPage.clickLogOut();
			login.typeusername(parameters.get("folder_user"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbuttonforinvalidlogin();
			if (login.ifTermsOfUseDispalyed()) {
				login.clickTermsOfUseCheckbox();
				login.typepassword(parameters.get("password"));
				login.Clickonloginbuttonforinvalidlogin();
			}

			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department"));
			submissionAppsPage = dashboardPage.clickSubmissions();
			submissionAppsPage.clickAppName(parameters.get("folder"));
			submissionSearchPage = submissionAppsPage.clickAppName(parameters.get("app"));
			String searchID = submissionSearchPage.getSubmissionIDByRowIndex(1);
			submissionSearchPage.enterSearchText(searchID);
			submissionSearchPage.clickSearchIcon();
			status = submissionSearchPage.isSubmissionIDDisplayed(searchID);
			reportLog(status, testContext.getName(),
					"Verify  results are returned correctly and user is still within the folder.", "8.1",
					"Verified results are returned correctly and user is still within the folder.");
			org.testng.Assert.assertTrue(status);

			searchID = randomAlphaNumeric(10);
			submissionSearchPage.enterSearchText(searchID);
			submissionSearchPage.clickSearchIcon();
			status = submissionSearchPage.isSubmissionIDDisplayed(searchID);
			reportLog(!status, testContext.getName(),
					"Verify  no results are returned and user is still within the folder.", "8.2",
					"Verified no results are returned and user is still within the folder.");
			org.testng.Assert.assertFalse(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		/*} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}*/
	}
}
