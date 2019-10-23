package com.canvas.qa.test.BETAQRT;

import java.awt.AWTException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.WorkflowPage;
import com.canvas.qa.pages.dispatch.DispatchCalendarPage;
import com.canvas.qa.pages.dispatch.DispatchCalendarViewPage;
import com.canvas.qa.pages.dispatch.DispatchPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

/**
 * @author shalini.pathak Class contains test cases related to dispatch calendar
 */

public class CreateDispatchCalendarTest extends BrowserLaunchTest {

	/**
	 * @param dispatchName
	 * @param dispatchCalendarViewPage
	 * @param testContext
	 * @param appName
	 * @param expectedAssignToList
	 * @param indexOfAssignTo
	 * @param scheduledAt
	 * @param scheduledEnd
	 * @param reminderDDLValue
	 * @return
	 * @throws InterruptedException
	 */
	public DispatchCalendarViewPage createDispatch(String dispatchName,
			DispatchCalendarViewPage dispatchCalendarViewPage, ITestContext testContext, String appName,
			String[] expectedAssignToList, int indexOfAssignTo, String scheduledAt, String scheduledEnd,
			String reminderDDLValue) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		dispatchCalendarViewPage.selectApp(appName);

		dispatchCalendarViewPage.enterDispatchName(dispatchName);
		dispatchCalendarViewPage.enterItemDescription(dispatchName);

		ArrayList<String> assignToActualList = dispatchCalendarViewPage.getAssignItemToList();

		verifyDropdownValues("3.", assignToActualList, expectedAssignToList,
				"Verify the values are correct in Assign item to drop down:", testContext);

		dispatchCalendarViewPage.selectAssignTo(indexOfAssignTo);

		boolean statusScheduledAt = dispatchCalendarViewPage.getDispatchScheduledAtTime(scheduledAt);
		reportLog(statusScheduledAt, testContext.getName(), "Verify the start time in Date Range", "4.1",
				"Start time correctly displayed as: " + scheduledAt);
		org.testng.Assert.assertTrue(statusScheduledAt);

		boolean statusScheduledEnd = dispatchCalendarViewPage.getDispatchScheduledEndTime(scheduledEnd);
		reportLog(statusScheduledEnd, testContext.getName(), "Verify the end time in Date Range", "4.2",
				"End time correctly displayed as: " + scheduledEnd);
		org.testng.Assert.assertTrue(statusScheduledEnd);

		dispatchCalendarViewPage.clickCalendarInvite();
		boolean status = dispatchCalendarViewPage.isReminderDDLDisplayed();
		reportLog(!status, testContext.getName(), "Uncheck Send Calendar invite", "5.1.1",
				"On calendar invite checkbox unchecked Reminder DDL dissapears");
		org.testng.Assert.assertFalse(status);
		status = dispatchCalendarViewPage.isReminderLabelDisplayed();
		reportLog(!status, testContext.getName(), "Uncheck Send Calendar invite", "5.1.2",
				"On calendar invite checkbox unchecked Reminder Label dissapears");
		org.testng.Assert.assertFalse(status);

		dispatchCalendarViewPage.clickCalendarInvite();
		status = dispatchCalendarViewPage.isReminderDDLDisplayed();
		reportLog(status, testContext.getName(), "Check Send calendar invite", "5.2.1",
				"On calendar invite checkbox checked Reminder DDL dispalyed");
		org.testng.Assert.assertTrue(status);
		status = dispatchCalendarViewPage.isReminderLabelDisplayed();
		reportLog(status, testContext.getName(), "Check Send calendar invite", "5.2.2",
				"On calendar invite checkbox checked Reminder Label dispalyed");
		org.testng.Assert.assertTrue(status);
		status = dispatchCalendarViewPage.getReminderDDLSelectedValue().equals(reminderDDLValue);
		reportLog(status, testContext.getName(), "Check Send calendar invite", "5.2.3",
				"Reminder DDL value  = 2 hours before correctly displayed");
		org.testng.Assert.assertTrue(status);
		return dispatchCalendarViewPage;
	}

	@Test
	@Parameters({ "testdescription" })
	public void createDispatchCalendarDayViewTest(String testDescription, ITestContext testContext)
			throws InterruptedException, IOException, ParseException, Exception {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DispatchCalendarPage dispatchCalendarPage = openCalendar(driver, testContext);
			WorkflowPage dispatchMonthView = new WorkflowPage(driver);
			DispatchCalendarViewPage dispatchCalendarDayPage = dispatchCalendarPage.clickDayButton()
					.clickDayNextDayDispatchButton().clickCreateNewDispatchLink();
			reportLog(dispatchCalendarDayPage.isSelectAppDisplayed(), testContext.getName(),
					"On clicking Create New Dispatch", "2.0", "Select App displayed successfully");
			ArrayList<String> appActualList = dispatchCalendarDayPage.getAppList();
			String[] appExpectedList = parameters.get("app_dropdown_list").split(";");
			verifyDropdownValues("2.", appActualList, appExpectedList,
					"Verify the correct apps appear in the correct order.", testContext);
			int indexOfAssignTo = 1;
			String dispatchName = randomAlphaNumeric(10);
			dispatchCalendarDayPage = createDispatch(dispatchName, dispatchCalendarDayPage, testContext,
					parameters.get("app_name"), parameters.get("assign_item_to_list").split(";"), indexOfAssignTo,
					parameters.get("scheduled_at"), parameters.get("scheduled_end"),
					parameters.get("reminder_ddl_selected_value"));

			boolean status = dispatchCalendarDayPage.isDayScreenLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify screen label", "5.3",
					"Screen label Dispatch Day View Screen displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarDayPage.isNameLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify first name label", "5.4",
					"Name label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarDayPage.isLastNameLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify last name label", "5.5",
					"Last	Name label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarDayPage.isDescLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify description label", "5.6",
					"Description label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarDayPage.isSalaryLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify salary label", "5.7", "Salary label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarDayPage.isStateLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify state label", "5.8", "State label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarDayPage.isDateLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify birth date label", "5.9",
					"Birth Date label displayed correctly");
			org.testng.Assert.assertTrue(status);

			status = dispatchCalendarDayPage.isTimeLabelDisplayed();
			org.testng.Assert.assertTrue(status);
			reportLog(status, testContext.getName(), "Verify birth time label", "5.10",
					"Birth Time label displayed correctly");

			status = dispatchCalendarDayPage.isCompanyURLLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify company URL label", "5.11",
					"Company URL label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarDayPage.isMultiOptionLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify multi option label", "5.12",
					"Multi Option label displayed correctly");
			org.testng.Assert.assertTrue(status);

			String[] stateExpectedList = parameters.get("state_dropdown_list").split(";");
			ArrayList<String> stateActualList = dispatchCalendarDayPage.getStateList();
			verifyDropdownValues("5.13.", stateActualList, stateExpectedList, "Verify state dropdown list value",
					testContext);

			String[] multiOptionChoiceExpectedList = parameters.get("multioptions_dropdown_list").split(";");
			ArrayList<String> multiOptionActualList = dispatchCalendarDayPage.getMultiOptionList();
			verifyDropdownMultiOptionValues("5.14.", multiOptionActualList, multiOptionChoiceExpectedList,
					"Verify multioption dropdown list value", testContext);

			dispatchCalendarDayPage.fillScreenValues(2,parameters.get("screen_name"), parameters.get("screen_last_name"),
					parameters.get("screen_desc"), Integer.parseInt(parameters.get("screen_salary")),
					parameters.get("screen_state"), parameters.get("screen_company_url"),parameters.get("screen_multi_options"),
					parameters.get("screen_day"));
			dispatchMonthView.clickDate();
			//dispatchMonthView.selectDateCompleteToIndex2(2,0,parameters.get("create_select_year_from1"),
			dispatchMonthView.selectDate2(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),2,0); //use to be 2
			dispatchCalendarDayPage.clickSaveButton();
			
			status = dispatchCalendarDayPage.getToastMessage().equals(parameters.get("toast_message"));
			reportLog(status, testContext.getName(), "Verify dispatch successfully created", "6.1",
					"Dispatch successfully created.");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), "Verify Email in inbox", "7.1", "Email check not automated");

			DispatchPage dispatchPage = dispatchCalendarDayPage.clickCloseButton();

			validateDispatchCreated(dispatchPage, dispatchName, testDescription, testContext);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void createDispatchCalendarMonthViewTest(String testDescription, ITestContext testContext)
			throws InterruptedException, IOException, ParseException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DispatchCalendarPage dispatchCalendarPage = openCalendar(driver, testContext);
			WorkflowPage dispatchMonthView = new WorkflowPage(driver);

			DispatchCalendarViewPage dispatchCalendarMonthPage = dispatchCalendarPage.clickMonthButton()
					.clickNextDayButton().clickCreateNewDispatchLink();
			reportLog(dispatchCalendarMonthPage.isSelectAppDisplayed(), testContext.getName(),
					"On clicking Create New Dispatch", "2.0", "Select App displayed successfully");
			ArrayList<String> appActualList = dispatchCalendarMonthPage.getAppList();
			String[] appExpectedList = parameters.get("app_dropdown_list").split(";");
			verifyDropdownValues("2.", appActualList, appExpectedList,
					"Verify the correct apps appear in the correct order.", testContext);

			int indexOfAssignTo = 1;
			String dispatchName = randomAlphaNumeric(10);
			dispatchCalendarMonthPage = createDispatch(dispatchName, dispatchCalendarMonthPage, testContext,
					parameters.get("app_name"), parameters.get("assign_item_to_list").split(";"), indexOfAssignTo,
					parameters.get("scheduled_at"), parameters.get("scheduled_end"),
					parameters.get("reminder_ddl_selected_value"));

			boolean status = dispatchCalendarMonthPage.isMonthScreenLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify screen label displayed", "5.3",
					"Screen label  Screen 1 displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarMonthPage.isNameLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify first name label displayed", "5.4",
					"Name label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarMonthPage.isDescLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify description label displayed", "5.5",
					"Description label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarMonthPage.isNumLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify num label displayed", "5.6",
					"Num label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarMonthPage.isCityLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify city label displayed", "5.7",
					"City label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarMonthPage.isDateLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify date label displayed", "5.8",
					"Date label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarMonthPage.isTimeLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify time label displayed", "5.9",
					"Time label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarMonthPage.isWebLinkLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify web link label displayed", "5.10",
					"Web Link label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarMonthPage.isGenderLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify gender label displayed", "5.11",
					"Gender label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarMonthPage.isMultiChoiceLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify multichoice label displayed", "5.12",
					"MultiChoice label displayed correctly");
			org.testng.Assert.assertTrue(status);

			String[] cityExpectedList = parameters.get("city_dropdown_list").split(";");
			ArrayList<String> cityActualList = dispatchCalendarMonthPage.getCityList();
			verifyDropdownValues("5.13.", cityActualList, cityExpectedList, "Verify city dropdown list", testContext);

			String[] multiChoiceExpectedList = parameters.get("multichoice_dropdown_list").split(";");
			ArrayList<String> multiChoiceActualList = dispatchCalendarMonthPage.getMultiChoiceList();
			verifyDropdownValues("5.14.", multiChoiceActualList, multiChoiceExpectedList,
					"Verify multichoice dropdown list", testContext);

			dispatchCalendarMonthPage.fillMonthScreenValues(2,parameters.get("screen_name"),
					parameters.get("screen_desc"), Integer.parseInt(parameters.get("screen_num")),
					parameters.get("screen_city"), parameters.get("screen_weblink"),
					parameters.get("screen_multichoice"),parameters.get("screen_day"));
			
			dispatchMonthView.clickDate();
			dispatchMonthView.selectDate2(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),2,0); //use to be 2
			
			dispatchCalendarMonthPage.clickSaveButton();

			status = dispatchCalendarMonthPage.getToastMessage().equals(parameters.get("toast_message"));
			reportLog(status, testContext.getName(), "Verify dispatch successfully created", "6.1",
					"Dispatch successfully created.");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), "Verify Email in inbox", "7.1", "Email check not automated");
			DispatchPage dispatchPage = dispatchCalendarMonthPage.clickCloseButton();

			validateDispatchCreated(dispatchPage, dispatchName, testDescription, testContext);
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			e.printStackTrace();
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}


	@Test
	@Parameters({ "testdescription" })
	public void createDispatchCalendarWeekViewTest(String testDescription, ITestContext testContext)
			throws InterruptedException, IOException, ParseException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DispatchCalendarPage dispatchCalendarPage = openCalendar(driver, testContext);
			WorkflowPage dispatchWeekView = new WorkflowPage(driver);
			DispatchCalendarViewPage dispatchCalendarWeekPage = dispatchCalendarPage.clickWeekButton()
					.clickNextDayDispatchButton().clickCreateNewDispatchLink();
			reportLog(dispatchCalendarWeekPage.isSelectAppDisplayed(), testContext.getName(),
					"On clicking Create New Dispatch", "2.0", "Select App displayed successfully");
			ArrayList<String> appActualList = dispatchCalendarWeekPage.getAppList();
			String[] appExpectedList = parameters.get("app_dropdown_list").split(";");
			verifyDropdownValues("2.", appActualList, appExpectedList,
					"Verify the correct apps appear in the correct order.", testContext);

			int indexOfAssignTo = 1;

			// ToDo: Create a method in utility class.
			String dispatchName = randomAlphaNumeric(10);
			dispatchCalendarWeekPage = createDispatch(dispatchName, dispatchCalendarWeekPage, testContext,
					parameters.get("app_name"), parameters.get("assign_item_to_list").split(";"), indexOfAssignTo,
					parameters.get("scheduled_at"), parameters.get("scheduled_end"),
					parameters.get("reminder_ddl_selected_value"));

			boolean status = dispatchCalendarWeekPage.isScreenLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify screen label displayed", "5.3",
					"Screen label Screen Dispatch for Week View displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarWeekPage.isNameLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify first name label displayed", "5.4",
					"Name label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarWeekPage.isDescLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify description label displayed", "5.5",
					"Description label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarWeekPage.isNumLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify number label displayed", "5.6",
					"Number label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarWeekPage.isStateLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify state label displayed", "5.7",
					"State label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarWeekPage.isDateLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify date label displayed", "5.8",
					"Date label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarWeekPage.isTimeLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify time label displayed", "5.9",
					"Time label displayed correctly");
			status = dispatchCalendarWeekPage.isWebLinkLabelWeekDisplayed();
			reportLog(status, testContext.getName(), "Verify web link label displayed", "5.10",
					"Web link label displayed correctly");
			org.testng.Assert.assertTrue(status);
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarWeekPage.isGenderLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify gender label displayed", "5.11",
					"Gender label displayed correctly");
			org.testng.Assert.assertTrue(status);
			status = dispatchCalendarWeekPage.isCountryLabelDisplayed();
			reportLog(status, testContext.getName(), "Verify country label displayed", "5.12",
					"Country label displayed correctly");
			org.testng.Assert.assertTrue(status);

			String[] stateExpectedList = parameters.get("state_dropdown_list").split(";");
			ArrayList<String> stateActualList = dispatchCalendarWeekPage.getWeeKStateList();
			verifyDropdownValues("5.13.", stateActualList, stateExpectedList, "Verify state dropdown list",
					testContext);

			String[] multiCountryExpectedList = parameters.get("country_dropdown_list").split(";");
			ArrayList<String> multiOptionActualList = dispatchCalendarWeekPage.getCountryList();
			verifyDropdownValues("5.14.", multiOptionActualList, multiCountryExpectedList,
					"Verify country dropdown list", testContext);

			dispatchCalendarWeekPage.fillWeekScreenValues(2,parameters.get("screen_name"), parameters.get("screen_desc"),
					Integer.parseInt(parameters.get("screen_number")), parameters.get("screen_state"),
					parameters.get("screen_weblink"), parameters.get("screen_country"),parameters.get("screen_day"));
			
			dispatchWeekView.clickDate();
			dispatchWeekView.selectDate2(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),2,0);
			
			dispatchCalendarWeekPage.clickSaveButton();

			status = dispatchCalendarWeekPage.getToastMessage().equals(parameters.get("toast_message"));
			reportLog(status, testContext.getName(), "Verify dispatch successfully created", "6.1",
					"Dispatch successfully created.");
			org.testng.Assert.assertTrue(status);

			// ToDo: use SKIP as status instead of PASS
			reportLog(true, testContext.getName(), "Verify Email in inbox", "7.1", "Email check not automated");

			DispatchPage dispatchPage = dispatchCalendarWeekPage.clickCloseButton();

			validateDispatchCreated(dispatchPage, dispatchName, testDescription, testContext);
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/**
	 * @param driver
	 * @param testContext
	 * @return
	 * @throws InterruptedException
	 */
	private DispatchCalendarPage openCalendar(WebDriver driver, ITestContext testContext) throws InterruptedException {
		DashboardPage dashboardPage = new DashboardPage(driver);
		DispatchCalendarPage dispatchCalendarPage = dashboardPage.clickDispatch().clickViewCalendar();
		boolean status = dispatchCalendarPage.isWeekSelected();
		reportLog(status, testContext.getName(), "On clicking dispatch & view calendar", "1.2",
				"Week View of calendar should open.");
		org.testng.Assert.assertTrue(status);
		return dispatchCalendarPage;
	}

	/**
	 * @param dispatchPage
	 * @param dispatchName
	 * @param testDescription
	 * @param testContext
	 * @throws InterruptedException
	 */
	private void validateDispatchCreated(DispatchPage dispatchPage, String dispatchName, String testDescription,
			ITestContext testContext) throws InterruptedException {
		boolean status = dispatchPage.isDispatchPageDisplayed();
		reportLog(status, testContext.getName(), "Verify on clicking close button user redirected to dispatch page",
				"8.1", "User re-direct to dispatch page.");
		org.testng.Assert.assertTrue(status);

		dispatchPage.searchDispatch(dispatchName);
		status = dispatchPage.isDispatchDisplayed(dispatchName);
		reportLog(status, testContext.getName(), "Verify dispatch displayed in list", "8.2",
				"Dispatch created displayed successfully in the list.");
		org.testng.Assert.assertTrue(status);
		dispatchPage.deleteDispatch();
	}
}
