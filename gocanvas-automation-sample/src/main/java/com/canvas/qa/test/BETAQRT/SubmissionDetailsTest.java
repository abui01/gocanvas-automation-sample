package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.SubmissionNumbering;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.submissions.SubmissionAppsPage;
import com.canvas.qa.pages.submissions.SubmissionsDetailPage;
import com.canvas.qa.pages.submissions.SubmissionsPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author priyanka.moral
 *
 */
@Test
public class SubmissionDetailsTest extends BrowserLaunchTest {

	/**
	 * kp Test case ID:TC7846 Summary:Submission Revisions
	 * @throws Exception 
	 **/

	@Test
	@Parameters({ "testdescription" })

	public void submissionRevisions(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException, Exception {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {

			/** Login and Add status **/
			performLogin(1, parameters.get("username1"), parameters.get("password1"),
					"Login as the company admin " + parameters.get("username1"), testContext, testDescription);
			SubmissionNumbering submission = new SubmissionNumbering(driver);
			CreateAppPage createapp = new CreateAppPage(driver);

			submission.clickSubmissionLink();
			createapp.clickAppName(parameters.get("appName"));
			createapp.SearchByDateAndTime(parameters.get("submissiondate"), parameters.get("submissiondatetime"));

			String revision = submission.getRevisionNumber();
			int originalRevision = Integer.parseInt(revision);

			boolean submissionRevision = submission.verfiyRevisionNumber(revision);
			reportLog(submissionRevision, testContext.getName(), "Verify Revisions  ", "1.2.",
					"Revisions number is verified");
			org.testng.Assert.assertTrue(submissionRevision, " ");

			String updateValueShortText = submission.getDefaultText();

			String enterText = parameters.get("updatedshort_text") + randomAlphaNumeric(4);

			submission.editText(enterText);
			submission.refreshPage();

			String updatedRevision = submission.getRevisionNumber();
			int revisionAfterUpdate = Integer.parseInt(updatedRevision);

			boolean newRevision = submission.verfiyUpdatedRevisionNumber(originalRevision, revisionAfterUpdate);
			reportLog(newRevision, testContext.getName(), "Verify Revisions  ", "2.1.",
					"Updated Revisions number is verified from " + originalRevision + " to" + revisionAfterUpdate);
			org.testng.Assert.assertTrue(newRevision, "Updated Revisions number is verfied");

			String ChangeVal = "SCREEN: " + "\"First scren" + "\" FIELD: " + "\"New Short Text" + "\" FROM: " + "\""
					+ updateValueShortText + "\" TO: " + "\"" + enterText + "\"";

			submission.clickRevisionNumber();

			boolean updatedRevisonNumber = submission.verfiyUpdatedRevisionNumber(originalRevision,
					revisionAfterUpdate);
			reportLog(updatedRevisonNumber, testContext.getName(), "Verify Revisions  ", "3.1.",
					"Updated Revisions number is " + revisionAfterUpdate);
			org.testng.Assert.assertTrue(updatedRevisonNumber, "Updated Revisions number is verified");

			boolean changeColumn = submission.verfiyValueInChangeColumn(ChangeVal);
			reportLog(changeColumn, testContext.getName(), "Verify latest updated value in the 'Change' column  ",
					"3.2.",
					"Data display in change column against Latest Submission revision Is verified:  " + ChangeVal);
			org.testng.Assert.assertTrue(changeColumn,
					"Value display in change column against Latest Submission revision is verified");

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm z");

			// df.setTimeZone(TimeZone.getTimeZone("IST"));

			String actualDateTime11 = df.format(cal.getTime());
			Date sysDate = new SimpleDateFormat("MM/dd/yyyy hh:mm z").parse(actualDateTime11);

			cal.add(Calendar.MINUTE, -2);
			String startDateTime11 = df.format(cal.getTime());
			Date startDateTime = df.parse(startDateTime11);

			cal.add(Calendar.MINUTE, 4);
			String endDateTime11 = df.format(cal.getTime());
			Date endDateTime = df.parse(endDateTime11);

			boolean dateTimeVal = submission.appWithinInRange(sysDate, startDateTime, endDateTime);
			reportLog(dateTimeVal, testContext.getName(), "Verify Fourth column value display ", "4.1.",
					"Date verified against Latest Submission revision ");
			org.testng.Assert.assertTrue(dateTimeVal, "Date verified");

			boolean authorColumnVal = submission.verifyAuthorName(parameters.get("authorName"));
			reportLog(authorColumnVal, testContext.getName(), "Verify the correct Author displays  ", "5.1.",
					"Author value verified against Latest Submission revision");
			org.testng.Assert.assertTrue(authorColumnVal, "Author correct value verified");

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/**
	 * kp Test case ID:TC8070 Summary:Un-destroy for Submissions
	 * @throws ParseException 
	 **/

	@Test
	@Parameters({ "testdescription" })

	public void unDestroyforSubmissions(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException, ParseException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {

			/** Login and Add status **/
			performLogin(1, parameters.get("username1"), parameters.get("password1"),
					"Login as the company admin " + parameters.get("username1"), testContext, testDescription);
			SubmissionNumbering submission = new SubmissionNumbering(driver);
			CreateAppPage createapp = new CreateAppPage(driver);
			LoginPage login = new LoginPage(driver);

			submission.clickSubmissionLink();
			createapp.clickAppName(parameters.get("app_Name"));

			String version = submission.getVersionName();
			String appName = submission.getAppName();
			String submissonId = submission.getSubmissionID();

			submission.selectFirstSubmisson();
			submission.deleteSubmisson();

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm z");

			df.setTimeZone(TimeZone.getTimeZone("UTC"));

			String actualDateTime11 = df.format(cal.getTime());
			Date sysDate = new SimpleDateFormat("MM/dd/yyyy hh:mm z").parse(actualDateTime11);

			cal.add(Calendar.MINUTE, -2);
			String startDateTime11 = df.format(cal.getTime());
			Date startDateTime = df.parse(startDateTime11);

			cal.add(Calendar.MINUTE, 4);
			String endDateTime11 = df.format(cal.getTime());
			Date endDateTime = df.parse(endDateTime11);

			createapp.logout();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));

			login.Clickonloginbutton();
			submission.searchUserAndManage(parameters.get("username1"));
			createapp.clickAppName(parameters.get("app_Name"));
			submission.verifyDeletedSubmissonsLinkInBottom();

			boolean col1 = submission.isColumnValueDisplayed(appName);
			reportLog(col1, testContext.getName(), "Verify first column value display ", "1.2.",
					"First Column display 'App Name' is verfied");
			org.testng.Assert.assertTrue(col1, "First Column 'App Name' is verfied");

			boolean col2 = submission.isColumnValueDisplayed(version);
			reportLog(col2, testContext.getName(), "Verify Second column value display. ", "1.3.",
					"Second Column display 'version' is verfied");
			org.testng.Assert.assertTrue(col2, "Second Column 'version' is verfied");

			boolean col3 = submission.isSubmissionColumnValueDisplayed(submissonId);
			reportLog(col3, testContext.getName(), "Verify Third column value display. ", "1.4.",
					"Third Column display 'Submission ID' is verfied");
			org.testng.Assert.assertTrue(col3, "Third Column 'Submission ID' is verfied");

			boolean dateTimeVal = submission.appWithinInRange(sysDate, startDateTime, endDateTime);
			reportLog(dateTimeVal, testContext.getName(), "Verify Fourth column value display ", "1.5.",
					"Date verified");
			org.testng.Assert.assertTrue(dateTimeVal, "Date verfied");

			submission.restoreSubmisson();
			boolean deletedSubmisson = submission.isSubmissionDisplayAfterDelete(submissonId);
			reportLog(!deletedSubmisson, testContext.getName(), "Verify submission after Restore . ", "2.1.",
					"Submissoin not display in 'Deleted' Submissions page is verified");
			org.testng.Assert.assertFalse(deletedSubmisson,
					"Submissoin not display in Deleted Submissions page is verified");

			submission.clickSubmissionLink();
			createapp.clickAppName(parameters.get("app_Name"));

			boolean submissonID = submission.verifyRestoreSubmisson(submissonId);
			reportLog(submissonID, testContext.getName(), "Verify Delete submission in Submissions Page.", "2.2.",
					"Correct Submission that was deleted now displays in the Submissions Page is verified");
			org.testng.Assert.assertTrue(submissonID, "Correct Submission display in submisson Page");

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/**
	 * kp Test case ID:TC4006 Summary:All Editing Options - Web
	 **/

	@Test
	@Parameters({ "testdescription" })
	public void verifyAllEditingOptionsWeb(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {

			/** Login and Add status **/
			performLogin(1, parameters.get("username1"), parameters.get("password1"),
					"Login as the company admin " + parameters.get("username1"), testContext, testDescription);
			SubmissionNumbering submission = new SubmissionNumbering(driver);
			CreateAppPage createapp = new CreateAppPage(driver);
			LoginPage login = new LoginPage(driver);

			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date today = Calendar.getInstance().getTime();
			String reportDate = df.format(today);

			submission.clickSubmissionLink();
			submission.clickOnLinkInSubmissionPage();

			boolean defaultCheckBoxValue = submission.isSubmissionDateCheckBoxDisplayDesigner();
			reportLog(!defaultCheckBoxValue, testContext.getName(),
					"Verify no checkboxes appear (to the left of Submission Date/values). ", "1.2.",
					"By default No checkboxes appear (to the left of Submission Date/values)");
			org.testng.Assert.assertFalse(defaultCheckBoxValue, "By default Check box is not selected");

			boolean editButtonsDisplay = submission.verifyEditButtons();
			reportLog(editButtonsDisplay, testContext.getName(), "Verify the edit icon appears for both fields..",
					"2.1.", "Edit icon appears for both fields.");
			org.testng.Assert.assertTrue(editButtonsDisplay, "Edit icon appears for both fields.");

			submission.saveEditedValue(parameters.get("updated_text"));

			submission.clickSubmissionLink();
			boolean submissonDate = submission.verifySubmissionDate(parameters.get("submisson_date"));
			reportLog(submissonDate, testContext.getName(),
					"Verify the Submission Date is still: 12/06/2017 10:12 UTC ", "3.1.",
					"Submission Date is still: 12/06/2017 10:12 UTC is verfied");
			org.testng.Assert.assertTrue(submissonDate, "Submission Date is still: 12/06/2017 10:12 UTC");

			createapp.logout();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbutton();

			submission.clickSubmissionLink();
			boolean deleteText = submission.deleteLogoText(parameters.get("delete_logo_text"));
			reportLog(deleteText, testContext.getName(), "Verify Delete logo/text appear. ", "4.1.",
					"Delete logo/text appear. is verfied");
			org.testng.Assert.assertTrue(deleteText, "Delete logo/text appear.");

			boolean editButton = submission.isEditButtonDisplay();
			reportLog(!editButton, testContext.getName(), "Verify the edit icon does not appear for the fields. ",
					"5.1.", "Edit icon does not appear for the fields.");
			org.testng.Assert.assertFalse(editButton, "Edit icon does not appear for the fields.");

			createapp.logout();
			login.typeusername(parameters.get("username3"));
			login.typepassword(parameters.get("password3"));
			login.Clickonloginbutton();
			submission.clickSubmissionLink();

			boolean defaultCheckBoxValueUser = submission.isSubmissionDateCheckBoxDisplayUser();
			reportLog(!defaultCheckBoxValueUser, testContext.getName(),
					"Verify no checkboxes appear (to the left of Submission Date/values).. ", "6.1.",
					"No checkboxes appear (to the left of Submission Date/values).");
			org.testng.Assert.assertFalse(defaultCheckBoxValueUser, "By default Check box is not selected");

			boolean editButtonsDisplayReporter = submission.verifyEditIcon();
			reportLog(editButtonsDisplayReporter, testContext.getName(),
					"Verify the edit icon appears for both fields..", "7.1.", "Edit icon appears for both fields.");
			org.testng.Assert.assertTrue(editButtonsDisplayReporter, "Edit icon appears for both fields.");

			submission.enterUpdateSubmissionDate();

			boolean saveSubmisson = submission.saveSubmissionDate(reportDate);
			reportLog(saveSubmisson, testContext.getName(),
					"Verify the Submission Date is updated for the No. 00004 submission. ", "8.1.",
					"Submission Date is updated for the No. 00004 submission.");
			org.testng.Assert.assertTrue(saveSubmisson, "Submission Date is still: 12/06/2017 10:12 UTC");

			createapp.logout();
			login.typeusername(parameters.get("username4"));
			login.typepassword(parameters.get("password4"));
			login.Clickonloginbutton();

			submission.clickSubmissionLink();
			boolean logoDeleteText = submission.verfiyLogoText(parameters.get("delete_logo_text"));
			reportLog(logoDeleteText, testContext.getName(), "Verify Delete logo/text appear. ", "9.1.",
					"Delete logo/text appear. is verfied");
			org.testng.Assert.assertTrue(logoDeleteText, "Delete logo/text appear.");

			boolean editIcon = submission.isEditIconDisplay();
			reportLog(!editIcon, testContext.getName(), "Verify the edit icon does not appear for the fields. ",
					"10.1.", "Edit icon does not appear for the fields.");
			org.testng.Assert.assertFalse(editIcon, "By default Check box is not selected");

			createapp.logout();
			login.typeusername(parameters.get("username1"));
			login.typepassword(parameters.get("password1"));
			login.Clickonloginbutton();
			submission.clickSubmissionLink();
			submission.clickOnLinkInSubmissionPage();
			submission.dateTimeLink();
			submission.saveEditedValue(parameters.get("revert_to_oldtext"));

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Parameters({ "step", "testdescription" })
	public void verifyBreadcrumb(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		Thread.sleep(5000);
		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;

		loginTest.verifyValidLoginnew(step, testdescription, parameters.get("company_user_username"),
				parameters.get("company_user_password"), testContext);

		SubmissionsPage submissionsPage = new SubmissionsPage(driver);
		submissionsPage.submissionsButtonClick();
		String appName = parameters.get("app_name");

		submissionsPage.submissionClick(appName);

		SubmissionAppsPage submissionAppsPage = new SubmissionAppsPage(driver);
		String submissionName = parameters.get("submission_name");

		submissionAppsPage.submissionClick(submissionName);

		SubmissionsDetailPage submissionsDetailPage = new SubmissionsDetailPage(driver);
		submissionsDetailPage.breadcrumbClick(appName);

		SubmissionAppsPage submissionAppsPage2 = new SubmissionAppsPage(driver);

		if (submissionAppsPage2.submissionExist(submissionName)) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct app name exist under submission name " + submissionName);
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ": Incorrect app name exist under submission name " + submissionName);
		}

		org.testng.Assert.assertTrue(submissionAppsPage2.submissionExist(submissionName),
				": Incorrect app name exist under submission name " + submissionName);

	}

	@Parameters({ "step", "testdescription" })
	public void verifyCancelEditDetails(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		Thread.sleep(5000);
		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;

		loginTest.verifyValidLoginnew(step, testdescription, parameters.get("company_user_username3"),
				parameters.get("company_user_password3"), testContext);

		SubmissionsPage submissionsPage = new SubmissionsPage(driver);
		submissionsPage.submissionsButtonClick();
		String appName = parameters.get("app_name3");

		submissionsPage.submissionClick(appName);

		SubmissionAppsPage submissionAppsPage = new SubmissionAppsPage(driver);
		String submissionName = parameters.get("submission_name3");

		submissionAppsPage.submissionClick(submissionName);

		SubmissionsDetailPage submissionsDetailPage = new SubmissionsDetailPage(driver);

		String submissionField = "Short Text Field:";

		String oldValue = submissionsDetailPage.getSubmissionDataValue(submissionField);
		submissionsDetailPage.cancelEditSubmissionDataValue(submissionField, "New Value");

		String newValue = submissionsDetailPage.getSubmissionDataValue(submissionField);
		if (oldValue != null) {
			if (oldValue.equals(newValue)) {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "."
						+ (++i) + ": " + submissionField + " has retained under submission name " + submissionName);
			} else {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "."
						+ (++i) + ": " + submissionField + " has not retained under submission name " + submissionName);
			}

			org.testng.Assert.assertTrue(oldValue.equals(newValue),
					"submissionField has not retained under submission name");

		}

		submissionsDetailPage.logout();
	}

	@Parameters({ "step", "testdescription" })
	public void verifyEditSubmissionDetails(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		Thread.sleep(5000);
		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;

		loginTest.verifyValidLogin1(step, testdescription, parameters.get("company_user_username1"),
				parameters.get("company_user_password1"), testContext);

		SubmissionsPage submissionsPage = new SubmissionsPage(driver);
		submissionsPage.submissionsButtonClick();
		String appName = parameters.get("app_name1");

		submissionsPage.submissionClick(appName);

		SubmissionAppsPage submissionAppsPage = new SubmissionAppsPage(driver);
		String submissionName = parameters.get("submission_name1");

		submissionAppsPage.submissionClick(submissionName);

		SubmissionsDetailPage submissionsDetailPage = new SubmissionsDetailPage(driver);

		if (!submissionsDetailPage.isEditable("Signature Field :")) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ": Signature Field : is not editabe under submission name " + submissionName);
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ": Signature Field : is editabe under submission name " + submissionName);
		}
		org.testng.Assert.assertTrue(!submissionsDetailPage.isEditable("Signature Field :"),
				": Signature Field : is editabe under submission name ");

		submissionsDetailPage.logout();

	}

	// /**kp
	// Test case ID:TC7846
	// Summary:Submission Revisions**/
	//
	// @Test
	// @Parameters({"testdescription" })
	//
	// public void submissionRevisions(String testDescription, ITestContext
	// testContext) throws IOException, InterruptedException{
	// Map<String, String> parameters =
	// FileReaderUtil.getTestParameters(testContext);
	// try{
	//
	// /** Login and Add status **/
	// performLogin(1,parameters.get("username1"),
	// parameters.get("password1"),"Login as the company admin "+
	// parameters.get("username1") ,testContext,testDescription);
	// SubmissionNumbering submission = new SubmissionNumbering(driver);
	// CreateAppPage createapp = new CreateAppPage(driver);
	//
	// submission.clickSubmissionLink();
	// createapp.clickAppName(parameters.get("appName"));
	// createapp.SearchByDateAndTime(parameters.get("submissiondate"),parameters.get("submissiondatetime"));
	//
	//
	// String revision=submission.getRevisionNumber();
	// int originalRevision = Integer.parseInt(revision);
	//
	// boolean submissionRevision= submission.verfiyRevisionNumber(revision);
	// reportLog(submissionRevision, testContext.getName(), "Verify Revisions
	// ","1.2.","Revisions number is verified");
	// org.testng.Assert.assertTrue(submissionRevision, " ");
	//
	// String updateValueShortText =submission.getDefaultText();
	//
	// String enterText =parameters.get("updatedshort_text")+
	// randomAlphaNumeric(4);
	//
	// submission.editText(enterText);
	// submission.refreshPage();
	//
	// String updatedRevision=submission.getRevisionNumber();
	// int revisionAfterUpdate= Integer.parseInt(updatedRevision);
	//
	// boolean newRevision=
	// submission.verfiyUpdatedRevisionNumber(originalRevision,revisionAfterUpdate);
	// reportLog(newRevision, testContext.getName(), "Verify Revisions
	// ","2.1.","Updated Revisions number is verified from "+originalRevision +
	// " to" +revisionAfterUpdate);
	// org.testng.Assert.assertTrue(newRevision, "Updated Revisions number is
	// verfied");
	//
	// String ChangeVal = "SCREEN: "+"\"First scren" +"\" FIELD: " +"\"New Short
	// Text" +"\" FROM: "+"\""+updateValueShortText+ "\" TO:
	// "+"\""+enterText+"\"";
	//
	// submission.clickRevisionNumber();
	//
	// boolean updatedRevisonNumber=
	// submission.verfiyUpdatedRevisionNumber(originalRevision,revisionAfterUpdate);
	// reportLog(updatedRevisonNumber, testContext.getName(), "Verify Revisions
	// ","3.1.","Updated Revisions number is "+revisionAfterUpdate);
	// org.testng.Assert.assertTrue(updatedRevisonNumber, "Updated Revisions
	// number is verified");
	//
	// boolean changeColumn= submission.verfiyValueInChangeColumn(ChangeVal);
	// reportLog(changeColumn, testContext.getName(), "Verify latest updated
	// value in the 'Change' column ","3.2.","Data display in change column
	// against Latest Submission revision Is verified: "+ChangeVal);
	// org.testng.Assert.assertTrue(changeColumn, "Value display in change
	// column against Latest Submission revision is verified");
	//
	// Calendar cal = Calendar.getInstance();
	// SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm z");
	//
	// //df.setTimeZone(TimeZone.getTimeZone("IST"));
	//
	// String actualDateTime11 = df.format(cal.getTime());
	// Date sysDate=new SimpleDateFormat("MM/dd/yyyy hh:mm
	// z").parse(actualDateTime11);
	//
	// cal.add(Calendar.MINUTE, -2);
	// String startDateTime11 = df.format(cal.getTime());
	// Date startDateTime = df.parse(startDateTime11);
	//
	// cal.add(Calendar.MINUTE, 4);
	// String endDateTime11 = df.format(cal.getTime());
	// Date endDateTime = df.parse(endDateTime11);
	//
	// boolean dateTimeVal= submission.withinRange(sysDate,startDateTime,
	// endDateTime);
	// reportLog(dateTimeVal, testContext.getName(), "Verify Fourth column value
	// display ","4.1.","Date verified against Latest Submission revision ");
	// org.testng.Assert.assertTrue(dateTimeVal, "Date verified");
	//
	// boolean authorColumnVal=
	// submission.verifyAuthorName(parameters.get("authorName"));
	// reportLog(authorColumnVal, testContext.getName(), "Verify the correct
	// Author displays ","5.1.","Author value verified against Latest Submission
	// revision");
	// org.testng.Assert.assertTrue(authorColumnVal, "Author correct value
	// verified");
	//
	// reportLog(true, testContext.getName(),testDescription, "", "FINISHED");
	// } catch (Exception e){
	// reportLog(false, testContext.getName(),testDescription, "Error
	// encountered - Details: ", e.getMessage() );
	// org.testng.Assert.assertTrue(false,e.getMessage());
	// }
	// }
	//
	//
	// =======
	// }

	@Parameters({ "step", "testdescription" })
	public void verifySubmissionDetails(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;

		loginTest.verifyValidLogin1(step, testdescription, parameters.get("company_user_username"),
				parameters.get("company_user_password"), testContext);

		SubmissionsPage submissionsPage = new SubmissionsPage(driver);
		submissionsPage.submissionsButtonClick();
		String appName = parameters.get("app_name");

		submissionsPage.submissionClick(appName);

		SubmissionAppsPage submissionAppsPage = new SubmissionAppsPage(driver);
		String submissionName = parameters.get("submission_name");

		submissionAppsPage.submissionClick(submissionName);

		SubmissionsDetailPage submissionsDetailPage = new SubmissionsDetailPage(driver);
		String screenName = parameters.get("screen_name");

		if (screenName.equals(submissionsDetailPage.getScreenName())) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ": Correct screen name display under submission name " + submissionName);
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ": Incorrect screen name display under submission name " + submissionName);
		}

		org.testng.Assert.assertTrue(screenName.equals(submissionsDetailPage.getScreenName()),
				"Incorrect screen name display under submission name");

		/*
		 * Assertion added
		 * 
		 * try { i = matchTextData(step, testContext, parameters, i,
		 * submissionName, submissionsDetailPage); i = matchCheckboxData(step,
		 * testContext, parameters, i, submissionName, submissionsDetailPage); i
		 * = matchImageData(step, testContext, parameters, i, submissionName,
		 * submissionsDetailPage); }catch(Exception e){
		 * ReportManager.lognew(testContext.getName(), testdescription,
		 * LogStatus.FAIL, "Step " + step + "." + (++i) +
		 * ": Incorrect data under submission name " + submissionName); }
		 */
		submissionsDetailPage.logout();
	}

}
