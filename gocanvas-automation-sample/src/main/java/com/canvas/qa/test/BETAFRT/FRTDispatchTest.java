package com.canvas.qa.test.BETAFRT;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.DownloadAndPdfViewPage;
import com.canvas.qa.pages.SearchUsersPage;
import com.canvas.qa.pages.WorkflowPage;
import com.canvas.qa.pages.dispatch.CreateDispatchPage;
import com.canvas.qa.pages.dispatch.DispatchCalendarPage;
import com.canvas.qa.pages.dispatch.DispatchCalendarViewPage;
import com.canvas.qa.pages.dispatch.DispatchDetailsPage;
import com.canvas.qa.pages.dispatch.DispatchEnableDisablePage;
import com.canvas.qa.pages.dispatch.DispatchPage;
import com.canvas.qa.pages.dispatch.EditDispatchPage;
import com.canvas.qa.pages.dispatch.ExportDispatchesPage;
import com.canvas.qa.pages.dispatch.ExportWorkflowAndDispatchesPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.submissions.SubmissionSearchPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class FRTDispatchTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void createDispatch2Of3Test(String testDescription, String rallyLink, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as: " + parameters.get("username"), testContext, testDescription, rallyLink);
			DashboardPage dashboardPage = new DashboardPage(driver);
			DispatchCalendarPage dispatchCalendarPage = dashboardPage.clickDispatch().clickViewCalendar();
			DispatchCalendarViewPage dispatchCalendarViewPage = dispatchCalendarPage.clickCreateNewDispatchLink();
			ArrayList<String> appActualList = dispatchCalendarViewPage.getAppList();
			String[] appExpectedList = parameters.get("app_dropdown_list").split(";");
			verifyDropdownValues("1.1.", appActualList, appExpectedList,
					"Verify the correct apps appear in the correct order.", testContext);

			String dispatchName = "TC7589" + randomAlphaNumeric(10);
			dispatchCalendarViewPage.selectApp(parameters.get("app_name"));
			dispatchCalendarViewPage.enterDispatchName(dispatchName);
			dispatchCalendarViewPage.enterItemDescription(dispatchName);

			ArrayList<String> assignToActualList = dispatchCalendarViewPage.getAssignItemToList();
			String[] expectedAssignToList = parameters.get("assign_item_to_list").split(";");
			verifyDropdownValues("2.", assignToActualList, expectedAssignToList,
					"Verify the values are correct in Assign item to drop down:", testContext);

			/*
			 * Date date = new Date(); SimpleDateFormat formatType = new
			 * SimpleDateFormat("hh:'00' a");
			 * formatType.setTimeZone(TimeZone.getTimeZone("EST")); String
			 * modifiedDate = formatType.format(date);
			 * 
			 * String scheduledAt =
			 * dispatchCalendarViewPage.getDispatchScheduledAtTime(); String
			 * scheduledEnd =
			 * dispatchCalendarViewPage.getDispatchScheduledEndTime();
			 * System.out.println(modifiedDate);
			 * System.out.println(scheduledAt);
			 * System.out.println(scheduledAt.substring(0,
			 * 1)+":"+scheduledAt.substring(2, 3));
			 * 
			 * boolean status = modifiedDate.contains(scheduledAt.substring(0,
			 * 1)+":"+scheduledAt.substring(2, 3));
			 * reportLog(status,testContext.getName()
			 * ,"Verify Start Time defaults to current time in EST.", "3.1",
			 * "Verified Start Time defaults to current time in EST.");
			 * org.testng.Assert.assertTrue(status);
			 * 
			 * status =
			 * dispatchCalendarViewPage.getDispatchScheduledEndTime().contains(
			 * scheduledEnd); reportLog(status,testContext.getName()
			 * ,"Verify End Time defaults to 2 hours after start time.", "3.2",
			 * "End Time defaults to 2 hours after start time.");
			 * org.testng.Assert.assertTrue(status);
			 */

			dispatchCalendarViewPage.clickCalendarInvite();

			boolean status = dispatchCalendarViewPage.isScreenNameDisplayed(parameters.get("screen"));
			reportLog(status, testContext.getName(), "Verify the LA screen appears.", "4.1",
					"Verified the LA screen appears");
			org.testng.Assert.assertTrue(status);

			String[] labelNameList = parameters.get("label_list").split(";");
			for (int j = 0; j < labelNameList.length; j++) {
				status = dispatchCalendarViewPage.isLabelDisplayed(labelNameList[j]);
				reportLog(status, testContext.getName(), "Verify " + labelNameList[j] + " is displayed.", "4.2." + j,
						"Verified " + labelNameList[j] + " is displayed.");
				org.testng.Assert.assertTrue(status);
			}

			dispatchCalendarViewPage.clickNewLoopItemLink();

			String[] labelNameList2 = parameters.get("label_list2").split(";");
			for (int j = 0; j < labelNameList2.length; j++) {
				status = dispatchCalendarViewPage.isLabelDisplayed(labelNameList2[j]);
				reportLog(status, testContext.getName(), "Verify " + labelNameList2[j] + " is displayed.", "4.3." + j,
						"Verified " + labelNameList2[j] + " is displayed.");
				org.testng.Assert.assertTrue(status);
			}

			String[] fieldIDList = parameters.get("field_id").split(";");
			String[] fieldValueList = parameters.get("field_value").split(";");
			for (int j = 0; j < fieldIDList.length; j++) {
				dispatchCalendarViewPage.enterFieldValues(fieldIDList[j], fieldValueList[j]);
			}
			dispatchCalendarViewPage.selectNewDropDown("A");
			dispatchCalendarViewPage.clickSaveButton();
			status = dispatchCalendarViewPage.getToastMessage().equals(parameters.get("toast_message"));
			reportLog(status, testContext.getName(), "Verify Your dispatch was successfully created appears.", "5.1",
					"Verified Your dispatch was successfully created appears.");
			org.testng.Assert.assertTrue(status);

			DispatchPage dispatchPage = dispatchCalendarViewPage.clickCloseButton();
			dispatchPage.searchDispatch(dispatchName);
			dispatchPage.deleteDispatch();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void createDispatchTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the account admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department"));
			WorkflowPage workflowPage = dashboardPage.clickWorkflowAndDispatch();
			CreateDispatchPage createDispatchPage = workflowPage.clickCreateDispatchLink();
			ArrayList<String> appActualList = createDispatchPage.getAppList();
			String[] appExpectedList = parameters.get("app_list").split(";");
			verifyDropdownValues("1.", appActualList, appExpectedList,
					"Verify the correct apps appear in the correct order.", testContext);

			createDispatchPage.selectApp(parameters.get("select_app"));
			String dispatchDesc = parameters.get("item_desc") + randomAlphaNumeric(10);
			createDispatchPage.enterItemDescription(dispatchDesc);

			ArrayList<String> assignActualList = createDispatchPage.getAssignToList();
			String[] assignExpectedList = parameters.get("assign_to_list").split(";");
			verifyDropdownValues("2.", assignActualList, assignExpectedList,
					"Verify the correct value appear in the correct order.", testContext);

			createDispatchPage.selectAssignTo(parameters.get("assign_to"));
			String[] screenTextList = parameters.get("screen1_texts").split(";");
			boolean status = createDispatchPage.isScreenLabelDisplayed();
			reportLog(status, testContext.getName(), "Screen label", "3.0", "Screen label is displayed successfully");
			org.testng.Assert.assertTrue(status);

			for (int i = 0; i < screenTextList.length; i++) {
				status = createDispatchPage.isScreenTextDisplayed(screenTextList[i]);
				reportLog(status, testContext.getName(), "Screen text value", "3.1." + i,
						screenTextList[i] + " is displayed successfully");
				org.testng.Assert.assertTrue(status);
			}

			status = createDispatchPage.isLoopLabelDisplayed();
			reportLog(status, testContext.getName(), "Loop label", "3.2.0", "Loop label is displayed successfully");
			org.testng.Assert.assertTrue(status);

			status = createDispatchPage.isNewLoopItemLinkDisplayed();
			reportLog(status, testContext.getName(), "New Loop Item", "3.3.0",
					"New Loop Item is displayed successfully");
			org.testng.Assert.assertTrue(status);

			createDispatchPage.clickNewLoopItemLink();
			String[] loopTextList = parameters.get("loop_texts").split(";");

			for (int i = 0; i < loopTextList.length; i++) {
				status = createDispatchPage.isScreenTextDisplayed(loopTextList[i]);
				reportLog(status, testContext.getName(), "Loop text value", "3.4." + i,
						loopTextList[i] + " is displayed successfully");
				org.testng.Assert.assertTrue(status);
			}

			createDispatchPage.enterNewShortText(parameters.get("short_text"));
			createDispatchPage.enterNewLongText(parameters.get("long_text"));
			createDispatchPage.selectNewDropDown(parameters.get("new_drop_down"));
			/*
			 * createDispatchPage.clickDate();
			 * createDispatchPage.clickNowButton();
			 * createDispatchPage.clickTime();
			 * createDispatchPage.clickNowButton();
			 */
			createDispatchPage.enterLoopNewLongText(parameters.get("new_long_text"));
			String toastMessage = createDispatchPage.checkForSSaveDispatchData();
			status = toastMessage.equals(parameters.get("toast_message"));
			reportLog(status, testContext.getName(), "Toast message validation after clicking save button", "4.0",
					toastMessage + " is displayed successfully");
			org.testng.Assert.assertTrue(status);

			status = createDispatchPage.getSelectedApp().equals(parameters.get("select_app"));
			reportLog(status, testContext.getName(), "Selected app displayed after clicking save is", "4.1",
					createDispatchPage.getSelectedApp());
			org.testng.Assert.assertTrue(status);

			reportLog(status, testContext.getName(), "Item description displayed after clicking save is", "4.2",
					createDispatchPage.getItemDescription());
			org.testng.Assert.assertTrue(status);

			status = createDispatchPage.getSelectedAssignTo().equals(parameters.get("default_assignto"));
			reportLog(status, testContext.getName(), "Assign to displayed after clicking save is", "4.3",
					createDispatchPage.getSelectedAssignTo());
			org.testng.Assert.assertTrue(status);

			status = createDispatchPage.getShortText().equals("");
			reportLog(status, testContext.getName(), "New short text displayed after clicking save is", "4.4",
					createDispatchPage.getShortText());
			org.testng.Assert.assertTrue(status);

			status = createDispatchPage.getLongText().equals("");
			reportLog(status, testContext.getName(), "New long text displayed after clicking save is", "4.5",
					createDispatchPage.getLongText());
			org.testng.Assert.assertTrue(status);

			workflowPage = createDispatchPage.clickWorkflowAndDispatchLink();
			status = workflowPage.isDispatchDisplayed(dispatchDesc);
			reportLog(status, testContext.getName(), "Verifying dispatch is displayed", "5.0",
					"Dispatch displayed successfully.");
			org.testng.Assert.assertTrue(status);

			status = workflowPage.isDispatchInfoDisplayed(dispatchDesc);
			reportLog(status, testContext.getName(), "Verifying dispatch description is displayed", "5.1",
					"Dispatch description displayed successfully.");
			org.testng.Assert.assertTrue(status);

			status = workflowPage.isDispatchInfoDisplayed(parameters.get("user"));
			reportLog(status, testContext.getName(), "Verifying user is displayed", "5.2",
					"user displayed successfully.");
			org.testng.Assert.assertTrue(status);

			status = workflowPage.isDispatchInfoDisplayed(parameters.get("app_name"));
			reportLog(status, testContext.getName(), "Verifying app name is displayed", "5.3",
					"App name displayed successfully.");
			org.testng.Assert.assertTrue(status);

			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			String creationDate = dateFormat.format(date);

			status = workflowPage.isDispatchInfoDisplayed(creationDate);
			reportLog(status, testContext.getName(), "Verifying ceation Date is displayed", "5.4",
					"Creation Date displayed successfully.");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	@Test
	@Parameters({ "testdescription" })
	public void editDispatchTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the account admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			WorkflowPage editTheDispatch = new WorkflowPage(driver);
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department"));
			WorkflowPage workflowPage = dashboardPage.clickWorkflowAndDispatch();
			workflowPage.advancedSearchClick();
			workflowPage.selectStatusfromDDL(parameters.get("status"));
			workflowPage.clickSearchButton();
			DispatchDetailsPage dispatchDetailsPage = workflowPage.clickDispatchIDLink(parameters.get("desc"));
			EditDispatchPage editDispatchPage = dispatchDetailsPage.clickEditLink();
			editDispatchPage.enterNewShortText(parameters.get("New_Short_Text"));
			editDispatchPage.enterNewLongText(parameters.get("New_Long_Text"));
			editDispatchPage.selectNewDropDown(parameters.get("New_Drop_Down"));
			editTheDispatch.selectDate(parameters.get("create_select_year_from1"),
			parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);
			editDispatchPage.clickTime();
			editDispatchPage.selectTime(parameters.get("new_hour"), parameters.get("new_minute"));
			editDispatchPage.enterLoopNewLongText(parameters.get("New_Long_Text_loop"));
			dispatchDetailsPage = editDispatchPage.clickSaveButton();
			String[] columnHeadingList = parameters.get("column_list").split(";");
			String[] dispatchValueList = parameters.get("dispatch_list").split(";");
			String step = "1.";
			for (int i = 0; i < dispatchValueList.length; i++) {
				boolean status = dispatchDetailsPage.getColumnText(columnHeadingList[i]).equals(dispatchValueList[i]);
				reportLog(status, testContext.getName(), "Column value for " + columnHeadingList[i] + " displayed is",
						step + i, dispatchDetailsPage.getColumnText(columnHeadingList[i]));
				org.testng.Assert.assertTrue(status);
			}

			boolean status = dispatchDetailsPage.getNewLongTextLoopValue().equals(parameters.get("New_Long_Text_loop"));
			reportLog(status, testContext.getName(), "Column value for New Long Text Loop displayed is", "1.5",
					dispatchDetailsPage.getNewLongTextLoopValue());
			org.testng.Assert.assertTrue(status);
			editDispatchPage = dispatchDetailsPage.clickEditLink();
			editDispatchPage.enterNewShortText(parameters.get("Old_Short_Text"));
			editDispatchPage.enterNewLongText(parameters.get("Old_Long_Text"));
			editDispatchPage.selectNewDropDown(parameters.get("Old_Drop_Down"));
			editTheDispatch.selectDate(parameters.get("create_select_year_from2"),parameters.get("create_select_month_from2"), parameters.get("create_select_day_from2"),0);
			editDispatchPage.clickTime();
			editDispatchPage.selectTime(parameters.get("old_hour"), parameters.get("old_minute"));
			editDispatchPage.enterLoopNewLongText(parameters.get("Old_Long_Text_loop"));
			dispatchDetailsPage = editDispatchPage.clickSaveButton();
			workflowPage = dispatchDetailsPage.clickWorkflowAndDispatchLink();
			workflowPage.advancedSearchClick();
			workflowPage.selectStatusfromDDL(parameters.get("status"));
			workflowPage.clickSearchButton();
			dispatchDetailsPage = workflowPage.clickDispatchIDLink(parameters.get("desc2"));
			editDispatchPage = dispatchDetailsPage.clickEditLink();
			editDispatchPage.enterDispatchItemDesc(parameters.get("new_desc"));
			dispatchDetailsPage = editDispatchPage.clickSaveButton();
			status = dispatchDetailsPage.getColumnText(parameters.get("desc_column"))
					.equals(parameters.get("new_desc"));
			reportLog(status, testContext.getName(), "Column value for Dispatch Item Description displayed is", "2.0",
					dispatchDetailsPage.getColumnText(parameters.get("desc_column")));
			org.testng.Assert.assertTrue(status);
			editDispatchPage = dispatchDetailsPage.clickEditLink();
			editDispatchPage.enterDispatchItemDesc(parameters.get("desc2"));
			editDispatchPage.clickSaveButton();
			customWait(5);
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// TC7697: Dispatch
	@Test
	@Parameters({ "testdescription" })
	public void enableDisableDispatchTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the Company designer: " + parameters.get("username"), testContext, testDescription);

			DispatchEnableDisablePage enableDisable = new DispatchEnableDisablePage(driver);
			DownloadAndPdfViewPage clickOnLogoutLink = new DownloadAndPdfViewPage(driver);
			CreateAppPage createapp = new CreateAppPage(driver);
			LoginPage login = new LoginPage(driver);
			enableDisable.clickAppLink();

			boolean defaultDispatchLink = enableDisable.verifyDispatchDisable();
			reportLog(!defaultDispatchLink, testContext.getName(), "Verify default dispatch link in app detail page  ",
					"1.2", "Dispatch link not Present and user cannot enable Dispatch for an app on a start up plan.");
			org.testng.Assert.assertFalse(defaultDispatchLink);

			clickOnLogoutLink.logout();
			boolean logoutMessage = createapp.afterlogout(parameters.get("logOut_msg"));
			reportLog(logoutMessage, testContext.getName(), "Click on logout button in create app page", "2.1",
					"User Logout from the Application");
			org.testng.Assert.assertTrue(logoutMessage);

			login.typeusername(parameters.get("username1"));
			login.typepassword(parameters.get("password1"));
			login.Clickonloginbutton();

			boolean appNameText_2 = enableDisable.pageNameAfterlogin(parameters.get("app_name"));
			reportLog(appNameText_2, testContext.getName(), "Login as the company Designer:", "2.2",
					"SignIn successfull");
			org.testng.Assert.assertTrue(appNameText_2, "SignIn successfull");

			boolean enableDispatchLink = enableDisable.verifyDispatchEnable();
			reportLog(enableDispatchLink, testContext.getName(), "Verify default dispatch link in app detail page  ",
					"2.3",
					"Dispatch link Present for the App and user can enable Dispatch on an app on a Professional Plan.");
			org.testng.Assert.assertTrue(enableDispatchLink);

			enableDisable.clickOnDispatchLink();
			boolean checkBoxValue = enableDisable.verifyDispatchCheckMark();
			reportLog(!checkBoxValue, testContext.getName(), "Verify the check mark 'Dispatch enabled on this app' ",
					"3.1", "Apps don't have dispatch enabled initially.");
			org.testng.Assert.assertFalse(checkBoxValue);

			boolean selectcheckBox = enableDisable.selectCheckBoxAndSave(parameters.get("orgMsg"));
			reportLog(selectcheckBox, testContext.getName(), "Verify user sees Dispatch has been successfully enabled",
					"4.1", "Dispatch has been successfully enabled.");
			org.testng.Assert.assertTrue(selectcheckBox);

			boolean selectAppName = enableDisable.verifyAppNameBeforeDisableDispatch(parameters.get("appnamedropdown"));
			reportLog(selectAppName, testContext.getName(),
					"Verify 'Detailed Weekly Timesheet' app displays in drop down", "5.1",
					"App displays correctly in the 'Select App' drop down list.");
			org.testng.Assert.assertTrue(selectAppName);

			clickOnLogoutLink.logout();
			boolean logoutMessage1 = createapp.afterlogout(parameters.get("logOut_msg"));
			reportLog(logoutMessage1, testContext.getName(), "Click on logout button in create app page", "6.1",
					"User Logout from the Application");
			org.testng.Assert.assertTrue(logoutMessage1);

			login.typeusername(parameters.get("admin"));
			login.typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			DashboardPage dashboardPage = new DashboardPage(driver);
			SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
			searchUsersPage.enterEmail(parameters.get("username2"));
			searchUsersPage.clickSearch();
			searchUsersPage.clickOnMore(parameters.get("username2").toLowerCase());
			searchUsersPage.clickFeatures();
			if (searchUsersPage.isEnableDispatchDisplayed()) {
				searchUsersPage.clickEnableDispatch();
			}
			searchUsersPage.logout();

			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbutton();

			boolean appNameText_3 = enableDisable.pageNameAfterlogin1(parameters.get("app_name1"));
			reportLog(appNameText_3, testContext.getName(), "Login as the Company admin:", "6.2", "SignIn successfull");
			org.testng.Assert.assertTrue(appNameText_3, "SignIn successfull");

			boolean disableDispatchLink = enableDisable.verifyDispatchDisableAfterLogin(parameters.get("disableMsg"));
			reportLog(disableDispatchLink, testContext.getName(),
					"Deselect the checkbox 'Dispatch enabled on this app?' ", "6.3",
					"Dispatch has been successfully disabled");
			org.testng.Assert.assertTrue(disableDispatchLink);

			boolean appNameAfterDisable = enableDisable
					.verifyAppNameAfterDisableDispatch(parameters.get("appnamedropdown"));
			reportLog(!appNameAfterDisable, testContext.getName(), "Verify App Name after disabling dispatch", "6.4",
					"App does not display on the Dispatch drop down list");
			org.testng.Assert.assertFalse(appNameAfterDisable);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	@Test
	@Parameters({ "testdescription" })
	public void exportDispatchesTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		//try {
			String tmpFolderPath = parameters.get("app_file_path");
			String expectedFileName1 = parameters.get("file_name");
			String filepath1 = System.getProperty("user.dir") + tmpFolderPath + expectedFileName1;
			File file1 = new File(filepath1);
			boolean status = file1.exists();
			if (status) {
				file1.delete();
			}

			String expectedFileName2 = parameters.get("file_name2");
			String filepath2 = System.getProperty("user.dir") + tmpFolderPath + expectedFileName2;
			File file2 = new File(filepath2);
			status = file2.exists();
			if (status) {
				file2.delete();
			}

			String expectedFileName3 = parameters.get("file_name3");
			String filepath3 = System.getProperty("user.dir") + tmpFolderPath + expectedFileName3;
			File file3 = new File(filepath3);
			status = file3.exists();
			if (status) {
				file3.delete();
			}

			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			WorkflowPage exportDispatchesPageDateRange = new WorkflowPage(driver);
			SubmissionSearchPage exportDispatchesSelectDate = new SubmissionSearchPage(driver);
			DispatchPage dispatchPage = dashboardPage.clickDispatch();
			ExportDispatchesPage exportDispatchesPage = dispatchPage.clickExportButton();
			
			exportDispatchesSelectDate.selectStartDate1();
			exportDispatchesPageDateRange.selectDate(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);

			exportDispatchesSelectDate.selectEndDate1();
			exportDispatchesPageDateRange.selectDate(parameters.get("create_select_year_to1"), parameters.get("create_select_month_to1"),
					parameters.get("create_select_day_to1"),1);
			//exportDispatchesPage.selectStartDate(parameters.get("date_range_from1"),
					//parameters.get("month_range_from1"), parameters.get("year_range_from1"));
			
			
			
			//exportDispatchesPage.selectEndDate(parameters.get("date_range_to1"), parameters.get("month_range_to1"),
					//parameters.get("year_range_to1"));
			
			exportDispatchesPage.selectApp(parameters.get("app1"));
			exportDispatchesPage.clickExportButton();

			String workingDir = System.getProperty("user.dir");
			String comapre_filepath1 = workingDir + parameters.get("compare_file_path");
			file1 = new File(filepath1);
			status = file1.exists();
			if (!status) {
				file1.createNewFile();
				status = file1.exists();
			}
			reportLog(status, testContext.getName(), "Verify " + parameters.get("file_name") + " is created.", "1.1",
					parameters.get("file_name") + " created successfully");
			org.testng.Assert.assertTrue(status);

			int value = comparefiles(filepath1, comapre_filepath1);
			status = value == 0 ? true : false;
			/*
			 * reportLog(status,testContext.getName(), "Verify "
			 * +parameters.get("file_name")+ " content", "1.2" ,
			 * "Content is correct"); org.testng.Assert.assertTrue(status);
			 */

			ArrayList<String> appActualList = exportDispatchesPage.getAppList();
			String[] appExpectedList = parameters.get("app_list").split(";");
			verifyDropdownValuesnew("2.", appActualList, appExpectedList,
					"Verify the correct apps appear in the correct order.", testContext);

			/*exportDispatchesPage.selectStartDate(parameters.get("date_range_from_no"),
					parameters.get("month_range_from_no"), parameters.get("year_range_from_no"));
			exportDispatchesPage.selectEndDate(parameters.get("date_range_to_no"), parameters.get("month_range_to_no"),
					parameters.get("year_range_to_no"));*/
			
	
			exportDispatchesSelectDate.selectStartDate1();
			exportDispatchesPageDateRange.selectDate(parameters.get("create_select_year_from2"),
					parameters.get("create_select_month_from2"), parameters.get("create_select_day_from2"),0);

			exportDispatchesSelectDate.selectEndDate1();
			exportDispatchesPageDateRange.selectDate2(parameters.get("create_select_year_to2"), parameters.get("create_select_month_to2"),
					parameters.get("create_select_day_to2"),1,3);
			
			
			exportDispatchesPage.selectApp(parameters.get("app1"));
			exportDispatchesPage.clickExportButton();
			status = exportDispatchesPage.getToastMessage().equals(parameters.get("message"));
			reportLog(status, testContext.getName(), "Validate error message", "3.1",
					parameters.get("message") + " : message is displayed successfully");
			org.testng.Assert.assertTrue(status);

			/*exportDispatchesPage.selectStartDate(parameters.get("date_range_from2"),
					parameters.get("month_range_from2"), parameters.get("year_range_from2"));
			exportDispatchesPage.selectEndDate(parameters.get("date_range_to2"), parameters.get("month_range_to2"),
					parameters.get("year_range_to2"));*/
			
			exportDispatchesSelectDate.selectStartDate1();
			exportDispatchesPageDateRange.selectDate(parameters.get("create_select_year_from3"),
					parameters.get("create_select_month_from3"), parameters.get("create_select_day_from3"),0);

			exportDispatchesSelectDate.selectEndDate1();
			exportDispatchesPageDateRange.selectDate(parameters.get("create_select_year_to3"), parameters.get("create_select_month_to3"),
					parameters.get("create_select_day_to3"),1);
			
			exportDispatchesPage.selectApp(parameters.get("app2"));
			customWait(2);
			status = exportDispatchesPage.isSubmissionCheckBoxEnabled();
			reportLog(status, testContext.getName(), "On selecting app: " + parameters.get("app2"), "4.0",
					" Submission checkbox is enabled.");
			org.testng.Assert.assertTrue(status);

			exportDispatchesPage.clickSubmissionCheckBox();
			exportDispatchesPage.selectVersion(parameters.get("app_version2"));
			exportDispatchesPage.clickExportButton();

			String comapre_filepath2 = workingDir + parameters.get("compare_file_path2");
			file2 = new File(filepath2);
			status = file2.exists();
			if (!status) {
				file2.createNewFile();
				status = file2.exists();
			}
			reportLog(status, testContext.getName(), "Verify " + parameters.get("file_name2") + " is created.", "4.1",
					parameters.get("file_name2") + " created successfully");
			org.testng.Assert.assertTrue(status);

			value = comparefiles(filepath2, comapre_filepath2);
			status = value == 0 ? true : false;
			/*
			 * reportLog(status,testContext.getName(), "Verify "
			 * +parameters.get("file_name2")+ " content", "4.2" ,
			 * "Content is correct"); org.testng.Assert.assertTrue(status);
			 */

			LoginPage login = dispatchPage.clickLogOutButton();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbutton();

			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department"));
			WorkflowPage workflowPage = dashboardPage.clickWorkflowAndDispatch();
			ExportWorkflowAndDispatchesPage exportWorkflowAndDispatchesPage = workflowPage.clickExportButton();

			exportWorkflowAndDispatchesPage.selectType(parameters.get("type"));
			
			
			exportDispatchesSelectDate.selectStartDate1();

			exportDispatchesPageDateRange.selectDate2(parameters.get("create_select_year_from4"),
					parameters.get("create_select_month_from4"), parameters.get("create_select_day_from4"),2,2);
			
			exportDispatchesSelectDate.selectEndDate1();
			exportDispatchesPageDateRange.selectDate2(parameters.get("create_select_year_to4"), parameters.get("create_select_month_to4"),
					parameters.get("create_select_day_to4"),3,0);
			
			
			/**
			 * Old Script
			exportDispatchesSelectDate.selectStartDate1();
			exportDispatchesPageDateRange.selectDateCompleteToIndex1(0,parameters.get("create_select_year_from4"),
					parameters.get("create_select_month_from4"), parameters.get("create_select_day_from4"));

			exportDispatchesSelectDate.selectEndDate1();
			exportDispatchesPageDateRange.selectDateCompleteToIndex2(1,1,parameters.get("create_select_year_to4"), parameters.get("create_select_month_to4"),
					parameters.get("create_select_day_to4"));
			*/
			//end here
			
			/*exportWorkflowAndDispatchesPage.selectStartDate(parameters.get("date_range_from3"),
					parameters.get("month_range_from3"), parameters.get("year_range_from3"));
			exportWorkflowAndDispatchesPage.selectEndDate(parameters.get("date_range_to3"),
					parameters.get("month_range_to3"), parameters.get("year_range_to3"));*/
			
			exportWorkflowAndDispatchesPage.selectApp(parameters.get("app3"));
			exportWorkflowAndDispatchesPage.clickExportButton();

			String comapre_filepath3 = workingDir + parameters.get("compare_file_path3");
			file3 = new File(filepath3);
			status = file3.exists();
			if (!status) {
				file3.createNewFile();
				status = file3.exists();
			}
			reportLog(status, testContext.getName(), "Verify " + parameters.get("file_name3") + " is created.", "5.1",
					parameters.get("file_name3") + " created successfully");
			org.testng.Assert.assertTrue(status);

			value = comparefiles(filepath3, comapre_filepath3);
			status = value == 0 ? true : false;
			/*
			 * reportLog(status,testContext.getName(), "Verify "
			 * +parameters.get("file_name3")+ " content", "5.2" ,
			 * "Content is correct"); org.testng.Assert.assertTrue(status);
			 */
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		//} catch (Exception e) {
		//	reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
		//	org.testng.Assert.assertTrue(false, e.getMessage());
		//}
	}


	/**
	 * kp ID:TC7546 Name:Sample Dispatch File
	 */
	@Test
	@Parameters({ "testdescription" })
	public void sampleDispatchFile(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {

		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {

			String tmpFolderPath = parameters.get("app_file_path");
			String expectedFileName1 = parameters.get("file_name1");
			String expectedFileName2 = parameters.get("file_name2");
			String filepath1 = System.getProperty("user.dir") + tmpFolderPath + expectedFileName1;
			String filepath2 = System.getProperty("user.dir") + tmpFolderPath + expectedFileName2;
			File file1 = new File(filepath1);
			File file2 = new File(filepath2);

			boolean status = file1.exists();
			if (status) {
				file1.delete();
				
			}
			
			file1.createNewFile();
			
			DashboardPage dashboardPage = new DashboardPage(driver);
			DispatchPage dispatchPage = new DispatchPage(driver);
			LoginPage login = new LoginPage(driver);
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);

			dashboardPage.clickDispatch();
			boolean appNameText = dispatchPage.verifyAppText(parameters.get("app_name1"));
			reportLog(appNameText, testContext.getName(), "Under Sample Dispatch File, verify the correct app appear: ",
					"1.2", "Correct App 'Try This App First' display under Sample Dispatch File ");
			org.testng.Assert.assertTrue(appNameText,
					"Correct App 'Try This App First' display under Sample Dispatch File");

			dispatchPage.downloaDispatchStep3Button();
			String workingDir = System.getProperty("user.dir");
			String compare_filepath1 = workingDir + parameters.get("compare_file_path");
			int value = comparefiles(filepath1, compare_filepath1);
			status = value == 0 ? true : false;
			reportLog(status, testContext.getName(), "Verify " + parameters.get("file_name") + " content", "2.1",
					"Content is correct");
			org.testng.Assert.assertTrue(status);

			dispatchPage.clickLogOutButton();
			login.typeusername(parameters.get("username1"));
			login.typepassword(parameters.get("password1"));
			login.Clickonloginbutton();

			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department"));
			dashboardPage.clickWorkflowAndDispatch();

			boolean anotherAppNameText = dispatchPage.verifyAnotherAppText(parameters.get("app_name2"));
			reportLog(anotherAppNameText, testContext.getName(),
					"Under Sample Dispatch File, verify the correct app appear: ", "3.1",
					"Correct App 'Another App' display under Sample Dispatch File ");
			org.testng.Assert.assertTrue(anotherAppNameText,
					"Correct App 'Try This App First' display under Sample Dispatch File");

			boolean step3AppNameText = dispatchPage.verifyStep3AppText(parameters.get("app_name3"));
			reportLog(step3AppNameText, testContext.getName(),
					"Under Sample Dispatch File, verify the correct app appear: ", "3.2",
					"Correct App 'Step 3 App - Second Department' display under Sample Dispatch File ");
			org.testng.Assert.assertTrue(step3AppNameText,
					"Correct App 'Try This App First' display under Sample Dispatch File");
			file1.delete();

			// Download Second dispatch file data

			status = file2.exists();
			if (status) {
				file2.delete();
				
			}
			
			file2.createNewFile();

			dispatchPage.downloaDispatchStep3Button();
			String compare_filepath2 = workingDir + parameters.get("compare_file_path2");
			int value2 = comparefiles(filepath2, compare_filepath2);
			status = value2 == 0 ? true : false;
			file2.delete();
			reportLog(status, testContext.getName(), "Verify " + parameters.get("file_name") + " content", "4.1",
					"Content is correct");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
