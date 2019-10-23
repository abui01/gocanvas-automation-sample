package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

//import org.sikuli.script.Pattern;
//import org.sikuli.script.Screen;
import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.apps.EditAppPage;
import com.canvas.qa.pages.apps.ShowEmailOptionsPage;
import com.canvas.qa.pages.apps.ShowIntegrationOptionsPage;
import com.canvas.qa.pages.apps.ShowPDFOptionsPage;
import com.canvas.qa.pages.submissions.SubmissionNumberingPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class FRTTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void copyAPPTest(String testDescription, ITestContext testContext) throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = dashboardPage.clickApp();
			createAppPage.deleteEachInstanceOfApp(parameters.get("app4"));
			createAppPage.retireEachInstanceOfApp(parameters.get("app4"));
			createAppPage = dashboardPage.clickApp().clickFilterDDL().selectAppStatus(parameters.get("app_status"))
					.clickQuickLink(parameters.get("app1"));

			String[] quickLinkList = parameters.get("quick_links1").split(";");
			for (int i = 0; i < quickLinkList.length; i++) {
				boolean status = createAppPage.isQuickLinkMenuDisplayed(parameters.get("app1"), quickLinkList[i]);
				reportLog(status, testContext.getName(), "User clicks on quicklink for app: " + parameters.get("app1"),
						"1.2." + i, "Qucik menu: " + quickLinkList[i] + " is displayed.");
				org.testng.Assert.assertTrue(status);
			}

			createAppPage.clickQuickLink(parameters.get("app2"));
			quickLinkList = parameters.get("quick_links2").split(";");
			for (int i = 0; i < quickLinkList.length; i++) {
				boolean status = createAppPage.isQuickLinkMenuDisplayed(parameters.get("app2"), quickLinkList[i]);
				reportLog(status, testContext.getName(), "User clicks on quicklink for app: " + parameters.get("app2"),
						"2." + i, "Qucik menu: " + quickLinkList[i] + " is displayed.");
				org.testng.Assert.assertTrue(status);
			}

			createAppPage.clickQuickLink(parameters.get("app3"));
			quickLinkList = parameters.get("quick_links3").split(";");
			for (int i = 0; i < quickLinkList.length; i++) {
				boolean status = createAppPage.isQuickLinkMenuDisplayed(parameters.get("app3"), quickLinkList[i]);
				reportLog(status, testContext.getName(), "User clicks on quicklink for app: " + parameters.get("app3"),
						"3." + i, "Qucik menu: " + quickLinkList[i] + " is displayed.");
				org.testng.Assert.assertTrue(status);
			}

			createAppPage.clickQuickLinkMenu(parameters.get("app3"), "Copy");
			boolean status = createAppPage.isAppNameDisplayed(parameters.get("app4"));
			reportLog(status, testContext.getName(), "User clicks on quickmenu copy for app: " + parameters.get("app3"),
					"4.0", parameters.get("app3") + " is created.");
			org.testng.Assert.assertTrue(status);

			createAppPage.clickFilterDDL().selectAppStatus(parameters.get("app_status"));

			EditAppPage editAppPage = createAppPage.clickAppName(parameters.get("app4"));
			ShowEmailOptionsPage showEmailOptionsPage = editAppPage.clickEmailOptions();
			status = showEmailOptionsPage.getPDFToEmailOption().contains(parameters.get("pdf_option"));
			reportLog(status, testContext.getName(), "In show email options page for Choose which PDF to email", "4.1",
					parameters.get("pdf_option") + " is selected");
			org.testng.Assert.assertTrue(status);

			status = showEmailOptionsPage.isMobileOptionChecked(parameters.get("mail_option"));
			reportLog(status, testContext.getName(), "In show email options page for Choose an option for mobile users",
					"4.2", "Can email the PDF to anyone is selected is: "
							+ showEmailOptionsPage.isMobileOptionChecked(parameters.get("mail_option")));
			org.testng.Assert.assertTrue(status);

			status = showEmailOptionsPage.getEmailList().contains(parameters.get("email_list"));
			reportLog(status, testContext.getName(), "In show email options page for Enter email addresses below.",
					"4.3", parameters.get("email_list") + " is displayed");
			org.testng.Assert.assertTrue(status);

			editAppPage = showEmailOptionsPage.clickCancelButton();
			ShowPDFOptionsPage showPDFOptionsPage = editAppPage.clickPDFOptions();
			// The below script won't work in jenkins so commented.
			/*
			 * String workingDir = System.getProperty("user.dir"); String
			 * filepath = workingDir + parameters.get("pdflogo_file_path");
			 * Screen screen = new Screen(); Pattern image = new
			 * Pattern(filepath); status = screen.exists(image) != null;
			 */
			reportLog(status, testContext.getName(), "In show PDF options page check PDF logo image displayed", "5.0",
					"PDF logo image displayed automated script cannot be run on jenkins");
			org.testng.Assert.assertTrue(status);

			editAppPage = showPDFOptionsPage.clickAppNameLink(parameters.get("app4"));
			ShowIntegrationOptionsPage showIntegrationOptionsPage = editAppPage.clickIntegerationOptions();
			status = showIntegrationOptionsPage.isManageButtonDisplayed();
			reportLog(status, testContext.getName(), "In Integration Options Page", "6.0",
					"Manage button is displayed.");
			org.testng.Assert.assertTrue(status);

			editAppPage = showIntegrationOptionsPage.clickAppNameLink(parameters.get("app4"));
			editAppPage.deleteApp();
			customWait(5);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	@Test
	@Parameters({ "testdescription" })
	public void submissionNumberingTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = dashboardPage.clickApp();

			EditAppPage editAppPage = createAppPage.clickAppName(parameters.get("app"));
			SubmissionNumberingPage submissionNumberingPage = editAppPage.clickSubmissionNumberLink();

			submissionNumberingPage.clickFirstOptionNumberingBasedOnRadioButton();
			submissionNumberingPage.enterSubmissionNumberingData(parameters.get("wrong_label"),
					parameters.get("wrong_length"), parameters.get("wrong_prefix"), parameters.get("wrong_suffix"));
			submissionNumberingPage.clickSaveButtonWithInCorrectData();

			boolean status = submissionNumberingPage.getErrorMessageHeading()
					.contains(parameters.get("error_explanation1"));
			reportLog(status, testContext.getName(),
					"User is on Submisison Numbering page and clicks on save button after entering incorrect data ",
					"1.2.0", parameters.get("error_explanation1") + " is displayed.");
			org.testng.Assert.assertTrue(status);

			status = submissionNumberingPage.getErrorMessageSubject().contains(parameters.get("error_explanation2"));
			reportLog(status, testContext.getName(),
					"User is on Submisison Numbering page and clicks on save button after entering incorrect data ",
					"1.2.1", parameters.get("error_explanation2") + " is displayed.");
			org.testng.Assert.assertTrue(status);

			status = submissionNumberingPage.isErrorMessageDisplayed(parameters.get("label_error_message"));
			reportLog(status, testContext.getName(),
					"User is on Submisison Numbering page and clicks on save button after entering incorrect data ",
					"1.2.2", parameters.get("label_error_message") + " is displayed.");
			org.testng.Assert.assertTrue(status);

			status = submissionNumberingPage.isErrorMessageDisplayed(parameters.get("length_error_message"));
			reportLog(status, testContext.getName(),
					"User is on Submisison Numbering page and clicks on save button after entering incorrect data ",
					"1.2.3", parameters.get("length_error_message") + " is displayed.");
			org.testng.Assert.assertTrue(status);

			status = submissionNumberingPage.isErrorMessageDisplayed(parameters.get("prefix_error_message"));
			reportLog(status, testContext.getName(),
					"User is on Submisison Numbering page and clicks on save button after entering incorrect data ",
					"1.2.4", parameters.get("prefix_error_message") + " is displayed.");
			org.testng.Assert.assertTrue(status);

			status = submissionNumberingPage.isErrorMessageDisplayed(parameters.get("suffix_error_message"));
			reportLog(status, testContext.getName(),
					"User is on Submisison Numbering page and clicks on save button after entering incorrect data ",
					"1.2.5", parameters.get("suffix_error_message") + " is displayed.");
			org.testng.Assert.assertTrue(status);

			submissionNumberingPage.clickFirstOptionNumberingBasedOnRadioButton();
			submissionNumberingPage.enterSubmissionNumberingData(parameters.get("correct_label"),
					parameters.get("correct_length"), parameters.get("correct_prefix"),
					parameters.get("correct_suffix"));
			submissionNumberingPage.checkResetNumberCheckbox();
			submissionNumberingPage.enterNextSubmissionNumber(parameters.get("submission_number"));
			editAppPage = submissionNumberingPage.clickSaveButtonWithCorrectData();
			submissionNumberingPage = editAppPage.clickSubmissionNumberLink();
			DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
			Date date = new Date();

			reportLog(true, testContext.getName(),
					"User submits correct data saves it and go to edit page and click submission number", "2.0",
					"User is on submission numbering page");
			String nextSubmissionNumberContent = parameters.get("next_submission_number") + dateFormat.format(date)
					+ parameters.get("next_submission_number_1");
			status = submissionNumberingPage.getNextSubmissionNumberText().contains(nextSubmissionNumberContent);
			reportLog(status, testContext.getName(),
					"User is on Submisison Numbering page and submission number text is", "2.1",
					nextSubmissionNumberContent);
			org.testng.Assert.assertTrue(status);

			status = submissionNumberingPage.isResetNumberCheckboxSelected();
			reportLog(!status, testContext.getName(), "User is on Submisison Numbering page", "2.2",
					"Reset number checkbox is not checked");
			org.testng.Assert.assertFalse(status);

			submissionNumberingPage.clickSecondOptionNumberingBasedOnRadioButton();
			submissionNumberingPage.enterSubmissionNumberingData(parameters.get("correct_label2"),
					parameters.get("correct_length2"), parameters.get("correct_prefix2"),
					parameters.get("correct_suffix2"));
			submissionNumberingPage.checkResetNumberCheckbox();
			submissionNumberingPage.enterNextSubmissionNumber(parameters.get("submission_number2"));
			editAppPage = submissionNumberingPage.clickSaveButtonWithCorrectData();
			submissionNumberingPage = editAppPage.clickSubmissionNumberLink();
			date = new Date();

			reportLog(true, testContext.getName(),
					"User submits correct data saves it and go to edit page and click submission number", "3.0",
					"User is on submission numbering page");
			nextSubmissionNumberContent = parameters.get("next_submission_number") + dateFormat.format(date)
					+ parameters.get("next_submission_number_2");
			status = submissionNumberingPage.getNextSubmissionNumberText().contains(nextSubmissionNumberContent);
			reportLog(status, testContext.getName(),
					"User is on Submisison Numbering page and submission number text is", "3.1",
					nextSubmissionNumberContent);
			org.testng.Assert.assertTrue(status);

			status = submissionNumberingPage.isResetNumberCheckboxSelected();
			reportLog(!status, testContext.getName(), "User is on Submisison Numbering page", "3.2",
					"Reset number checkbox is not checked");
			org.testng.Assert.assertFalse(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

}
