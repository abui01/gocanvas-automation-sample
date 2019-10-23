package com.canvas.qa.test.BETAFRT;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.WorkflowPage;
import com.canvas.qa.pages.dispatch.DispatchDetailsPage;
import com.canvas.qa.pages.dispatch.DispatchPage;
import com.canvas.qa.pages.dispatch.SubmissionDetailsPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class FRTDispatchManagerTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void dispatchItemDetailTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department"));
			WorkflowPage workflowPage = dashboardPage.clickWorkflowAndDispatch();
			DispatchDetailsPage dispatchDetailsPage = workflowPage
					.clickDispatchIDLink(parameters.get("dispatch_desc1"));
			boolean status = dispatchDetailsPage.isEditLinkDisplayed();
			reportLog(status, testContext.getName(), "Verify Edit Link Appear", "1.2", "Edit button appears.");
			org.testng.Assert.assertTrue(status);

			String[] columnHeadingList = parameters.get("column_list1").split(";");
			String[] dispatchValueList = parameters.get("dispatch_list1").split(";");
			String step = "1.3.";
			for (int i = 0; i < dispatchValueList.length; i++) {
				status = dispatchDetailsPage.getColumnText(columnHeadingList[i]).equals(dispatchValueList[i]);
				reportLog(status, testContext.getName(), "Column value for " + columnHeadingList[i] + " displayed is",
						step + i, dispatchDetailsPage.getColumnText(columnHeadingList[i]));
				org.testng.Assert.assertTrue(status);
			}

			workflowPage = dispatchDetailsPage.clickWorkflowAndDispatchLink();
			workflowPage.advancedSearchClick();
			workflowPage.selectStatus(parameters.get("status2"));
			workflowPage.clickSearchButton();
			dispatchDetailsPage = workflowPage.clickDispatchIDLink(parameters.get("dispatch_desc2"));
			status = dispatchDetailsPage.isEditLinkDisplayed();
			reportLog(status, testContext.getName(), "Verify Edit Link Appear", "2.0", "Edit button appears.");
			org.testng.Assert.assertTrue(status);

			columnHeadingList = parameters.get("column_list2").split(";");
			dispatchValueList = parameters.get("dispatch_list2").split(";");
			step = "2.1.";
			for (int i = 0; i < dispatchValueList.length; i++) {
				status = dispatchDetailsPage.getColumnText(columnHeadingList[i]).equals(dispatchValueList[i]);
				reportLog(status, testContext.getName(), "Column value for " + columnHeadingList[i] + " displayed is",
						step + i, dispatchDetailsPage.getColumnText(columnHeadingList[i]));
				org.testng.Assert.assertTrue(status);
			}

			workflowPage = dispatchDetailsPage.clickWorkflowAndDispatchLink();
			workflowPage.advancedSearchClick();
			workflowPage.selectStatus(parameters.get("status3"));
			workflowPage.clickSearchButton();
			dispatchDetailsPage = workflowPage.clickDispatchIDLink(parameters.get("dispatch_desc3"));
			columnHeadingList = parameters.get("column_list3").split(";");
			dispatchValueList = parameters.get("dispatch_list3").split(";");
			step = "3.0.";
			for (int i = 0; i < dispatchValueList.length; i++) {
				status = dispatchDetailsPage.getColumnText(columnHeadingList[i]).equals(dispatchValueList[i]);
				reportLog(status, testContext.getName(), "Column value for " + columnHeadingList[i] + " displayed is",
						step + i, dispatchDetailsPage.getColumnText(columnHeadingList[i]));
				org.testng.Assert.assertTrue(status);
			}

			workflowPage = dispatchDetailsPage.clickEditLinkNoAccess();
			status = workflowPage.getToastMessage().contains(parameters.get("message"));
			reportLog(status, testContext.getName(), "Verify the popup appears.", "4.0",
					"Message appears: That item can not be edited. It is in the state 'Deleted'");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void DispatchManagerAdvancedSearch1of2(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			DispatchPage dispatchPage = dashboardPage.clickDispatch();
			WorkflowPage advancedSearch = new WorkflowPage(driver);
			dispatchPage.clickAdvanceSearchButton();
			dispatchPage.verifyAdvancedSearchlabels("1.2.", testContext, "Verify screen labels",
					parameters.get("first_screen_labels").split(";"));

			// For first search result
			dispatchPage.selectUser(parameters.get("user"));
			dispatchPage.selectApp(parameters.get("app"));
			dispatchPage.selectStatus(parameters.get("status"));

			/*
			 * advancedSearch.clickCreateFrom();
			 * advancedSearch.selectDate(parameters.get("create_date_range_from1"),
			 * parameters.get("create_month_range_from1"),
			 * parameters.get("create_year_range_from1")); advancedSearch.clickCreateTo();
			 * advancedSearch.selectDate(parameters.get("create_date_range_to1"),
			 * parameters.get("create_month_range_to1"),
			 * parameters.get("create_year_range_to1"));
			 */

			advancedSearch.clickCreateFrom();
			advancedSearch.selectDate(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);

			advancedSearch.clickCreateTo();
			advancedSearch.selectDate(parameters.get("create_select_year_to1"),
					parameters.get("create_select_month_to1"), parameters.get("create_select_day_to1"),1);

			advancedSearch.clickCompleteFrom();

			advancedSearch.selectDate2(parameters.get("complete_select_year_from1"),
					parameters.get("complete_select_month_from1"), parameters.get("complete_select_day_from1"),2,2);

			advancedSearch.clickCompleteTo();
			advancedSearch.selectDate2(parameters.get("complete_select_year_to1"),
					parameters.get("complete_select_month_to1"), parameters.get("complete_select_day_to1"),3,3);

			/*
			 * advancedSearch.clickCompleteFrom();
			 * advancedSearch.selectDate(parameters.get("complete_date_range_from1"),
			 * parameters.get("complete_month_range_from1"),
			 * parameters.get("complete_year_range_from1"));
			 * 
			 * advancedSearch.clickCompleteTo();
			 * advancedSearch.selectDate(parameters.get("complete_date_range_to1"),
			 * parameters.get("complete_month_range_to1"),
			 * parameters.get("complete_year_range_to1"));
			 */

			dispatchPage.clickSearchButton();
			String[] userExpectedList_1 = parameters.get("app_list1").split(";");
			int size_1 = userExpectedList_1.length;
			ArrayList<String> userActualList_1 = dispatchPage.getDispatchValues(size_1);
			verifyDispatchValues("2.", userExpectedList_1, userActualList_1, "Verify the correct dispatch list .",
					testContext);

			// For second search result Step#3

			advancedSearch.clickCancel();
			dispatchPage.clickAdvanceSearchButton();
			dispatchPage.selectUser(parameters.get("user1"));
			dispatchPage.selectApp(parameters.get("app1"));
			dispatchPage.selectStatus(parameters.get("status1"));

			advancedSearch.clickCompleteFrom();

			advancedSearch.selectDate2(parameters.get("complete_select_year_from2"),
					parameters.get("complete_select_month_from2"), parameters.get("complete_select_day_from2"),2,2);

			advancedSearch.clickCompleteTo();
			advancedSearch.selectDate2(parameters.get("complete_select_year_to2"),
					parameters.get("complete_select_month_to2"), parameters.get("complete_select_day_to2"),3,3);

			/*
			 * advancedSearch.selectDate(parameters.get("complete_date_range_from1"),
			 * parameters.get("complete_month_range_from1"),
			 * parameters.get("complete_year_range_from1"));
			 * advancedSearch.clickCompleteTo();
			 * advancedSearch.selectDate(parameters.get("complete_date_range_to1"),
			 * parameters.get("complete_month_range_to1"),
			 * parameters.get("complete_year_range_to1"));
			 */
			dispatchPage.clickSearchButton();

			String[] userExpectedList_2 = parameters.get("app_list1").split(";");
			int size_2 = userExpectedList_2.length;
			ArrayList<String> userActualList_2 = dispatchPage.getDispatchValues(size_2);
			verifyDispatchValues("3.", userExpectedList_2, userActualList_2, "Verify the correct dispatch list .",
					testContext);

			// For third dispatch search result Step #4

			advancedSearch.clickCancel();
			dispatchPage.clickAdvanceSearchButton();
			dispatchPage.selectUser(parameters.get("user2"));
			dispatchPage.selectApp(parameters.get("app2"));
			dispatchPage.selectStatus(parameters.get("status2"));

			advancedSearch.clickCreateFrom();
			advancedSearch.selectDate(parameters.get("create_select_year_from3"),
					parameters.get("create_select_month_from3"), parameters.get("create_select_day_from3"),0);

			advancedSearch.clickCreateTo();
			advancedSearch.selectDate(parameters.get("create_select_year_to3"),
					parameters.get("create_select_month_to3"), parameters.get("create_select_day_to3"),1);
			/*
			 * advancedSearch.selectDate(parameters.get("create_date_range_from2"),
			 * parameters.get("create_month_range_from2"),
			 * parameters.get("create_year_range_from2")); advancedSearch.clickCreateTo();
			 * advancedSearch.selectDate(parameters.get("create_date_range_to2"),
			 * parameters.get("create_month_range_to2"),
			 * parameters.get("create_year_range_to2"));
			 */
			dispatchPage.clickSearchButton();

			String[] userExpectedList_3 = parameters.get("app_list2").split(";");
			int size_3 = userExpectedList_3.length;
			ArrayList<String> userActualList_3 = dispatchPage.getDispatchValues(size_3);
			verifyDispatchValues("4.", userExpectedList_3, userActualList_3, "Verify the correct dispatch list.",
					testContext);

			// For fourth dispatch search result Step #5

			advancedSearch.clickCancel();
			dispatchPage.clickAdvanceSearchButton();
			dispatchPage.selectUser(parameters.get("user3"));
			dispatchPage.selectApp(parameters.get("app3"));
			dispatchPage.selectStatus(parameters.get("status3"));

			advancedSearch.clickCreateFrom();

			advancedSearch.clickCreateFrom();
			advancedSearch.selectDate(parameters.get("create_select_year_from4"),
					parameters.get("create_select_month_from4"), parameters.get("create_select_day_from4"),0);

			advancedSearch.clickCreateTo();
			advancedSearch.selectDate(parameters.get("create_select_year_to4"),
					parameters.get("create_select_month_to4"), parameters.get("create_select_day_to4"),1);
			/*
			 * advancedSearch.selectDate(parameters.get("create_date_range_from3"),
			 * parameters.get("create_month_range_from3"),
			 * parameters.get("create_year_range_from3")); advancedSearch.clickCreateTo();
			 * advancedSearch.selectDate(parameters.get("create_date_range_to3"),
			 * parameters.get("create_month_range_to3"),
			 * parameters.get("create_year_range_to3"));
			 */
			dispatchPage.clickSearchButton();

			String[] userExpectedList_4 = parameters.get("app_list3").split(";");
			int size_4 = userExpectedList_4.length;
			ArrayList<String> userActualList_4 = dispatchPage.getDispatchValues(size_4);
			verifyDispatchValues("5.", userExpectedList_4, userActualList_4, "Verify the correct dispatch list.",
					testContext);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	@Test
	@Parameters({ "testdescription" })
	public void dispatchManagerAdvancedSearch2of2(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the Account admin : " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			DispatchPage workflowDispatchPage = new DispatchPage(driver);
			WorkflowPage WorkflowDispatchAdvancedSearch = new WorkflowPage(driver);

			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department"));
			dashboardPage.clickWorkflowAndDispatch();
			workflowDispatchPage.clickAdvanceSearchButton();
			workflowDispatchPage.selectUser(parameters.get("user"));
			workflowDispatchPage.selectApp(parameters.get("app"));
			workflowDispatchPage.selectStatus(parameters.get("status"));

			// Search Step #1
			WorkflowDispatchAdvancedSearch.clickCreateFrom();
			WorkflowDispatchAdvancedSearch.selectDate(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);

			WorkflowDispatchAdvancedSearch.clickCreateTo();
			WorkflowDispatchAdvancedSearch.selectDate(parameters.get("create_select_year_to1"),
					parameters.get("create_select_month_to1"), parameters.get("create_select_day_to1"),1);

			/*
			 * WorkflowDispatchAdvancedSearch.selectDate(parameters.get(
			 * "create_date_range_from1"), parameters.get("create_month_range_from1"),
			 * parameters.get("create_year_range_from1"));
			 * WorkflowDispatchAdvancedSearch.clickCreateTo();
			 * WorkflowDispatchAdvancedSearch.selectDate(parameters.get(
			 * "create_date_range_to1"), parameters.get("create_month_range_to1"),
			 * parameters.get("create_year_range_to1"));
			 */

			/*
			 * WorkflowDispatchAdvancedSearch.clickCompleteFrom();
			 * WorkflowDispatchAdvancedSearch.selectDate(parameters.get(
			 * "complete_date_range_from1"), parameters.get("complete_month_range_from1"),
			 * parameters.get("complete_year_range_from1"));
			 * 
			 * WorkflowDispatchAdvancedSearch.clickCompleteTo();
			 * WorkflowDispatchAdvancedSearch.selectDate(parameters.get(
			 * "complete_date_range_to1"), parameters.get("complete_month_range_to1"),
			 * parameters.get("complete_year_range_to1"));
			 */

			WorkflowDispatchAdvancedSearch.clickCompleteFrom();
			WorkflowDispatchAdvancedSearch.selectDate2(parameters.get("complete_select_year_from1"),
					parameters.get("complete_select_month_from1"), parameters.get("complete_select_day_from1"),2,2);

			WorkflowDispatchAdvancedSearch.clickCompleteTo();
			WorkflowDispatchAdvancedSearch.selectDate2(parameters.get("complete_select_year_to1"),
					parameters.get("complete_select_month_to1"), parameters.get("complete_select_day_to1"),3,3);

			workflowDispatchPage.clickSearchButton();

			boolean searchResult = workflowDispatchPage
					.dispatchSearchResultMatch(parameters.get("dispatch_search_result"));
			String searchResultText = workflowDispatchPage.dispatchSearchResultText();
			reportLog(searchResult, testContext.getName(), "Verify Search result result text Match or Not  ", "1.2",
					"Search result text is match with Actual result and Serach text is : " + searchResultText);
			org.testng.Assert.assertTrue(searchResult);

			WorkflowDispatchAdvancedSearch.clickCancel();
			workflowDispatchPage.clickAdvanceSearchButton();
			workflowDispatchPage.selectUser(parameters.get("user"));
			workflowDispatchPage.selectApp(parameters.get("app"));
			workflowDispatchPage.selectStatus(parameters.get("status"));

			// Search Step #2

			/*
			 * WorkflowDispatchAdvancedSearch.clickCompleteFrom();
			 * WorkflowDispatchAdvancedSearch.selectDate(parameters.get(
			 * "complete_date_range_from2"), parameters.get("complete_month_range_from2"),
			 * parameters.get("complete_year_range_from2"));
			 * WorkflowDispatchAdvancedSearch.clickCompleteTo();
			 * WorkflowDispatchAdvancedSearch.selectDate(parameters.get(
			 * "complete_date_range_to2"), parameters.get("complete_month_range_to2"),
			 * parameters.get("complete_year_range_to2"));
			 */

			WorkflowDispatchAdvancedSearch.clickCompleteFrom();
			WorkflowDispatchAdvancedSearch.selectDate2(parameters.get("complete_select_year_from2"),
					parameters.get("complete_select_month_from2"), parameters.get("complete_select_day_from2"),2,2);

			WorkflowDispatchAdvancedSearch.clickCompleteTo();
			WorkflowDispatchAdvancedSearch.selectDate2(parameters.get("complete_select_year_to2"),
					parameters.get("complete_select_month_to2"), parameters.get("complete_select_day_to2"),3,3);

			workflowDispatchPage.clickSearchButton();

			String[] userExpectedList_1 = parameters.get("app_list1").split(";");
			int appListSize_1 = userExpectedList_1.length;
			ArrayList<String> userActualList = workflowDispatchPage.getWorkflowDispatchValues(appListSize_1);
			verifyDispatchValues("2.", userExpectedList_1, userActualList, "Verify the correct dispatch list .",
					testContext);

			// Step #3
			WorkflowDispatchAdvancedSearch.clickCancel();
			workflowDispatchPage.clickAdvanceSearchButton();
			workflowDispatchPage.selectUser(parameters.get("user1"));
			workflowDispatchPage.selectApp(parameters.get("app1"));
			workflowDispatchPage.selectStatus(parameters.get("status1"));
			workflowDispatchPage.enterTextDispatchfield(parameters.get("Search dispatch fields"));

			WorkflowDispatchAdvancedSearch.clickCreateFrom();
			WorkflowDispatchAdvancedSearch.selectDate(parameters.get("create_select_year_from3"),
					parameters.get("create_select_month_from3"), parameters.get("create_select_day_from3"),0);

			WorkflowDispatchAdvancedSearch.clickCreateTo();
			WorkflowDispatchAdvancedSearch.selectDate(parameters.get("create_select_year_to3"),
					parameters.get("create_select_month_to3"), parameters.get("create_select_day_to3"),1);

			WorkflowDispatchAdvancedSearch.clickCompleteFrom();
			WorkflowDispatchAdvancedSearch.selectDate2(parameters.get("complete_select_year_from3"),
					parameters.get("complete_select_month_from3"), parameters.get("complete_select_day_from3"),2,2);
	
			WorkflowDispatchAdvancedSearch.clickCompleteTo();
			WorkflowDispatchAdvancedSearch.selectDate2(parameters.get("complete_select_year_to3"),
					parameters.get("complete_select_month_to3"), parameters.get("complete_select_day_to3"),3,3);

			/*
			 * WorkflowDispatchAdvancedSearch.selectDate(parameters.get(
			 * "create_date_range_from3"), parameters.get("create_month_range_from3"),
			 * parameters.get("create_year_range_from3"));
			 * WorkflowDispatchAdvancedSearch.clickCreateTo();
			 * WorkflowDispatchAdvancedSearch.selectDate(parameters.get(
			 * "create_date_range_to3"), parameters.get("create_month_range_to3"),
			 * parameters.get("create_year_range_to3"));
			 * WorkflowDispatchAdvancedSearch.clickCompleteFrom();
			 * WorkflowDispatchAdvancedSearch.selectDate(parameters.get(
			 * "complete_date_range_from3"), parameters.get("complete_month_range_from3"),
			 * parameters.get("complete_year_range_from3"));
			 * WorkflowDispatchAdvancedSearch.clickCompleteTo();
			 * WorkflowDispatchAdvancedSearch.selectDate(parameters.get(
			 * "complete_date_range_to3"), parameters.get("complete_month_range_to3"),
			 * parameters.get("complete_year_range_to3"));
			 */

			WorkflowDispatchAdvancedSearch.clickSearchButton();

			String[] userExpectedList_2 = parameters.get("app_list1").split(";");
			int appListSize_2 = userExpectedList_2.length;
			ArrayList<String> userActualList2 = workflowDispatchPage.getWorkflowDispatchValues(appListSize_2);
			verifyDispatchValues("3.", userExpectedList_2, userActualList2, "Verify the correct dispatch list.",
					testContext);

			WorkflowDispatchAdvancedSearch.clickCancel();
			workflowDispatchPage.clickAdvanceSearchButton();

			boolean userDropDownText = workflowDispatchPage.defaultValueUserDropDown(parameters.get("user"));
			String valueInDropDown = workflowDispatchPage.defaultTextUserDropDown();
			reportLog(userDropDownText, testContext.getName(), "Verify default selected value in user drop down  ",
					"4.1", "Default Value of 'User' drop down is verfied and default value is: " + valueInDropDown);
			org.testng.Assert.assertTrue(userDropDownText);

			boolean appDropDownText = workflowDispatchPage.defaultValueAppDropDown(parameters.get("app"));
			String valueInAppDropDown = workflowDispatchPage.defaultTextAppDropDown();
			reportLog(appDropDownText, testContext.getName(), "Verify default selected value in app drop down  ", "4.2",
					"Default Value of 'App' drop down is verified and default value is : " + valueInAppDropDown);
			org.testng.Assert.assertTrue(appDropDownText);

			boolean statusDropDownText = workflowDispatchPage.defaultValueStatusDropDown(parameters.get("status"));
			String valueInStatusDropDown = workflowDispatchPage.defaultTextStatusDropDown();
			reportLog(statusDropDownText, testContext.getName(), "Verify default selected value in status drop down  ",
					"4.3",
					"Default Value of 'Status' drop down is verified and default value is : " + valueInStatusDropDown);
			org.testng.Assert.assertTrue(statusDropDownText);

			boolean placeHolderText = workflowDispatchPage.defaultPlaceHolderValue(parameters.get("placeHolder_text"));
			String valuePlaceHolder = workflowDispatchPage.defaultPlaceHolderText();
			reportLog(placeHolderText, testContext.getName(), "Verify placeholder value ", "4.4",
					"Place holder value is verfied and default value is : " + valuePlaceHolder);
			org.testng.Assert.assertTrue(placeHolderText);

			boolean creationAtDate = workflowDispatchPage.defaultcreationAtDate(parameters.get("created_at_from"));
			String valueInCreateAtDate = workflowDispatchPage.defaultTextcreationAtDate();
			reportLog(creationAtDate, testContext.getName(), "Verify default selected value in Creation Date Range  ",
					"4.5", "Default Value of 'created at from' is verfied and value is reset to blank: "
							+ valueInCreateAtDate);
			org.testng.Assert.assertTrue(creationAtDate);

			boolean creationToDate = workflowDispatchPage.defaultcreationToDate(parameters.get("created_at_to"));
			String valueInCreateToDate = workflowDispatchPage.defaultTextcreationToDate();
			reportLog(creationToDate, testContext.getName(), "Verify default selected value in Creation Date Range  ",
					"4.6",
					"Default Value of 'created at to' is verfied and value is reset to blank: " + valueInCreateToDate);
			org.testng.Assert.assertTrue(creationToDate);

			boolean completedAtfrom = workflowDispatchPage.defaultcompletedAtFrom(parameters.get("completed_at_from"));
			String valueInCompletedAtfrom = workflowDispatchPage.defaultTextcompletedAtFrom();
			reportLog(completedAtfrom, testContext.getName(), "Verify default selected value in Completed at from  ",
					"4.7", "Default Value of 'completed at from' is verfied and value is reset to blank: "
							+ valueInCompletedAtfrom);
			org.testng.Assert.assertTrue(completedAtfrom);

			boolean completedAtTo = workflowDispatchPage.defaultcompletedToFrom(parameters.get("completed_at_to"));
			String valueInCompletedAtTo = workflowDispatchPage.defaultTextcompletedAtFrom();
			reportLog(completedAtTo, testContext.getName(), "Verify default selected value in Completed at from  ",
					"4.8", "Default Value of 'completed at to' is verfied and value is reset to blank: "
							+ valueInCompletedAtTo);
			org.testng.Assert.assertTrue(completedAtTo);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// ID:TC7673
	// Test case Summary :FRT-TC7673- Dispatch Manager: Advanced Search (2 of 2)

	@Test
	@Parameters({ "testdescription" })
	public void dispatchManagerAdvanceSearch1Of3Test(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			DispatchPage dispatchPage = dashboardPage.clickDispatch();
			reportLog(true, testContext.getName(), "Verify sort order is correct (for ascending and descending).",
					"1.1", "For time being sorting not to be covered in automation.");
			/*
			 * String[] columnList = parameters.get("column_list").split(";"); for (int i =
			 * 1; i <= columnList.length; i++) { if (columnList[i - 1].equals("ID")) {
			 * ArrayList<String> actualList = dispatchPage.getColumnListValues(i + 1);
			 * ArrayList<Integer> ascList = (ArrayList<Integer>)
			 * actualList.stream().map(Integer::parseInt) .collect(Collectors.toList());
			 * ArrayList<Integer> dscList = (ArrayList<Integer>)
			 * actualList.stream().map(Integer::parseInt) .collect(Collectors.toList()); ;
			 * Collections.sort(ascList); Collections.sort(dscList,
			 * Collections.reverseOrder()); dispatchPage.clickColumnHeading(columnList[i -
			 * 1]); actualList = dispatchPage.getColumnListValues(i + 1); ArrayList<Integer>
			 * newList = (ArrayList<Integer>) actualList.stream().map(Integer::parseInt)
			 * .collect(Collectors.toList()); boolean status = newList.equals(dscList);
			 * reportLog(status, testContext.getName(), "Verify descending order for: " +
			 * columnList[i - 1], "1." + i + ".1", "Sort order is correct descending.");
			 * org.testng.Assert.assertTrue(status);
			 * dispatchPage.clickColumnHeading(columnList[i - 1]); actualList =
			 * dispatchPage.getColumnListValues(i + 1); newList = (ArrayList<Integer>)
			 * actualList.stream().map(Integer::parseInt) .collect(Collectors.toList());
			 * status = newList.equals(ascList); reportLog(status, testContext.getName(),
			 * "Verify ascending order for: " + columnList[i - 1], "1." + i + ".2",
			 * "Sort order is correct ascending."); org.testng.Assert.assertTrue(status); }
			 * else { if (!columnList[i - 1].equals("App Name") && !columnList[i -
			 * 1].equals("Scheduled At") && !columnList[i - 1].equals("Creation Date")) {
			 * ArrayList<String> actualList = dispatchPage.getColumnListValues(i + 1);
			 * ArrayList<String> ascList = new ArrayList<String>(actualList);
			 * ArrayList<String> dscList = new ArrayList<String>(actualList);
			 * Collections.sort(ascList, String.CASE_INSENSITIVE_ORDER);
			 * Collections.sort(dscList, String.CASE_INSENSITIVE_ORDER.reversed());
			 * dispatchPage.clickColumnHeading(columnList[i - 1]); ArrayList<String> newList
			 * = dispatchPage.getColumnListValues(i + 1); boolean status =
			 * newList.equals(dscList); reportLog(status, testContext.getName(),
			 * "Verify descending order for: " + columnList[i - 1], "1." + i + ".1",
			 * "Sort order is correct descending for: " + columnList[i - 1]);
			 * org.testng.Assert.assertTrue(status);
			 * dispatchPage.clickColumnHeading(columnList[i - 1]); newList =
			 * dispatchPage.getColumnListValues(i + 1); status = newList.equals(ascList);
			 * reportLog(status, testContext.getName(), "Verify ascending order for: " +
			 * columnList[i - 1], "1." + i + ".2", "Sort order is correct ascending for: " +
			 * columnList[i - 1]); org.testng.Assert.assertTrue(status); } else {
			 * reportLog(true, testContext.getName(), "Sorting not working for: " +
			 * columnList[i - 1], "1." + i + ".1", "Sort not working for: " + columnList[i -
			 * 1]); org.testng.Assert.assertTrue(true); } } dashboardPage.clickDispatch(); }
			 */
			dispatchPage.clickCheckAllCheckbox();
			boolean status = dispatchPage.verifyAllCheckBoxChecked();
			reportLog(status, testContext.getName(), "Verify the correct checkboxes are checked.", "2.0",
					"Correct checkboxes are checked.");
			org.testng.Assert.assertTrue(status);
			customWait(5);
			dispatchPage.clickCheckAllCheckbox();
			status = dispatchPage.verifyAllCheckBoxUnChecked();
			reportLog(status, testContext.getName(), "Verify the correct checkboxes are unchecked.", "2.1",
					"Correct checkboxes are unchecked.");
			org.testng.Assert.assertTrue(status);

			String[] viewList = parameters.get("view_list").split(";");
			for (int i = 1; i <= viewList.length; i++) {
				boolean moreRows = true;
				while (moreRows) {
					dispatchPage.selectView(viewList[i - 1]);
					int count = dispatchPage.getRowCount();
					status = (count <= Integer.parseInt(viewList[i - 1]));
					if (dispatchPage.isNextButonEnabled() && (status == true)) {
						moreRows = true;
						dispatchPage.clickOnNextEnabledButton();
					} else {
						moreRows = false;
					}
				}
				reportLog(status, testContext.getName(),
						"Verify the View selections" + viewList[i - 1] + " function correctly", "3." + i,
						viewList[i - 1] + " functions correctly");
				org.testng.Assert.assertTrue(status);
			}

			boolean moreRows = true;
			while (moreRows) {
				status = dispatchPage.verifyScheduledAtAreHyperLink();
				if (dispatchPage.isNextButonEnabled() && (status == true)) {
					moreRows = true;
					dispatchPage.clickOnNextEnabledButton();
				} else {
					moreRows = false;
				}
			}
			reportLog(status, testContext.getName(),
					"For those dispatches with a Scheduled At value, verify the date/time is a hyperlink ", "4.0",
					"Date/time is a hyperlink");
			org.testng.Assert.assertTrue(status);

			dispatchPage.checkRowCheckBox(1);
			String[] iconList = parameters.get("icon_list").split(";");
			for (int i = 0; i < iconList.length; i++) {
				status = dispatchPage.isIconDisplayed(iconList[i]);
				reportLog(status, testContext.getName(), "Verify the icon " + iconList[i] + " is displayed", "5." + i,
						iconList[i] + " is displayed");
				org.testng.Assert.assertTrue(status);
			}

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/*
	 * kp Test case ID TC7912 Summary : Workflow: Search (1 of 2)
	 */

	@Test
	@Parameters({ "testdescription" })
	public void dispatchManagerAdvanceSearchTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			DispatchPage dispatchPage = dashboardPage.clickDispatch();
			dispatchPage.clickAdvanceSearchButton();

			dispatchPage.selectUser(parameters.get("user"));
			dispatchPage.selectApp(parameters.get("app"));
			dispatchPage.selectStatus(parameters.get("status"));
			dispatchPage.clickSearchButton();
			boolean status = dispatchPage.isStatusDisplayedInEachRow(parameters.get("status"));
			reportLog(status, testContext.getName(), "Checking status value in each row", "1.1",
					"Status value is Un-assigned in each row");
			org.testng.Assert.assertTrue(status);

			LoginPage login = dispatchPage.clickLogOutButton();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbutton();

			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department"));
			WorkflowPage workflowPage = dashboardPage.clickWorkflowAndDispatch();
			workflowPage.advancedSearchClick();
			workflowPage.selectUser(parameters.get("select_user"));
			workflowPage.selectAppfromDDL(parameters.get("select_app"));
			workflowPage.selectStatusfromDDL(parameters.get("select_status"));
			workflowPage.enterSearchField(parameters.get("search_dispatch_fields"));
			// workflowPage.clickCreateFrom();

			workflowPage.clickCreateFrom();
			workflowPage.selectDate(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);

			workflowPage.clickCreateTo();
			workflowPage.selectDate2(parameters.get("create_select_year_to1"), parameters.get("create_select_month_to1"),
					parameters.get("create_select_day_to1"),1,0);

			workflowPage.clickCompleteFrom();
			//workflowPage.selectDateCompleteFrom(1, parameters.get("complete_select_year_from1"),
					//parameters.get("complete_select_month_from1"), parameters.get("complete_select_day_from1"));

			workflowPage.selectDate2(parameters.get("complete_select_year_from1"),
					parameters.get("complete_select_month_from1"), parameters.get("complete_select_day_from1"),2,1);

			workflowPage.clickCompleteTo();
			workflowPage.selectDate2(parameters.get("complete_select_year_to1"),
					parameters.get("complete_select_month_to1"), parameters.get("complete_select_day_to1"),3,1);

			workflowPage.clickCancel();
			workflowPage.advancedSearchClick();

			// workflowPage.clickCompleteFrom();

			// old

			/*
			 * workflowPage.selectDate(parameters.get("complete_date_range_from1"),
			 * parameters.get("complete_month_range_from1"),
			 * parameters.get("complete_year_range_from1")); workflowPage.clickCompleteTo();
			 * workflowPage.selectDate(parameters.get("complete_date_range_to1"),
			 * parameters.get("complete_month_range_to1"),
			 * parameters.get("complete_year_range_to1")); workflowPage.clickCancel();
			 */

			status = workflowPage.getSelectedUser().equals(parameters.get("User2"));
			reportLog(status, testContext.getName(), "Checking user value", "2.0",
					workflowPage.getSelectedUser() + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = workflowPage.getSelectedApp().equals(parameters.get("App2"));
			reportLog(status, testContext.getName(), "Checking app value", "2.1",
					workflowPage.getSelectedApp() + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = workflowPage.getSelectedStatus().equals(parameters.get("Status2"));
			reportLog(status, testContext.getName(), "Checking status value", "2.2",
					workflowPage.getSelectedStatus() + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = workflowPage.getCreateFrom().equals("");
			reportLog(status, testContext.getName(), "Checking create from value", "2.3", "Nothing is displayed");
			org.testng.Assert.assertTrue(status);

			status = workflowPage.getCreateTo().equals("");
			reportLog(status, testContext.getName(), "Checking create to value", "2.4", "Nothing is displayed");
			org.testng.Assert.assertTrue(status);

			status = workflowPage.getCompleteFrom().equals("");
			reportLog(status, testContext.getName(), "Checking complete from value", "2.5", "Nothing is displayed");
			org.testng.Assert.assertTrue(status);

			status = workflowPage.getCompleteTo().equals("");
			reportLog(status, testContext.getName(), "Checking complete to value", "2.6", "Nothing is displayed");
			org.testng.Assert.assertTrue(status);

			ArrayList<String> userActualList = dispatchPage.getUserList();
			String[] userExpectedList = parameters.get("user_list").split(";");
			verifyDropdownValuesnew("3.", userActualList, userExpectedList,
					"Verify the correct user appear in the correct order.", testContext);

			ArrayList<String> appActualList = dispatchPage.getAppList();
			String[] appExpectedList = parameters.get("app_list").split(";");
			verifyDropdownValuesnew("4.", appActualList, appExpectedList,
					"Verify the correct apps appear in the correct order.", testContext);

			ArrayList<String> statusActualList = dispatchPage.getStatusList();
			String[] statusExpectedList = parameters.get("status_list").split(";");
			verifyDropdownValuesnew("5.", statusActualList, statusExpectedList,
					"Verify the correct status appear in the correct order.", testContext);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// Kp
	// ID:TC7672
	// Test case Summary :Dispatch Manager: Advanced Search (1 of 2)

	@Test
	@Parameters({ "testdescription" })
	public void dispatchManagerDispatchSearchTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department"));
			WorkflowPage workflowPage = dashboardPage.clickWorkflowAndDispatch();
			workflowPage.advancedSearchClick();
			workflowPage.selectUser(parameters.get("user1"));
			workflowPage.selectApp(parameters.get("app1"));
			workflowPage.selectStatus(parameters.get("status1"));
			workflowPage.enterSearchField(parameters.get("search1"));
			workflowPage.clickSearchButton();
			boolean status = workflowPage.isDispatchInfoDisplayed(parameters.get("dispatchID1"));
			reportLog(status, testContext.getName(), "Verify the correct dispatch appears: ID: 32", "1.2",
					"Dispatch ID: 32 displayed successfully.");
			org.testng.Assert.assertTrue(status);

			workflowPage.selectUser(parameters.get("user2"));
			workflowPage.selectApp(parameters.get("app2"));
			workflowPage.selectStatus(parameters.get("status2"));
			workflowPage.enterSearchField(parameters.get("search2"));
			workflowPage.clickSearchButton();
			status = workflowPage.getErrorMessage().contains(parameters.get("message"));
			reportLog(status, testContext.getName(), "Verify the error message", "2.0",
					"Your search did not return any results, here are some tips displayed successfully.");
			org.testng.Assert.assertTrue(status);

			workflowPage.selectUser(parameters.get("user2"));
			workflowPage.selectApp(parameters.get("app2"));
			workflowPage.selectStatus(parameters.get("status2"));
			workflowPage.enterSearchField(parameters.get("search3"));
			workflowPage.clickSearchButton();
			status = workflowPage.isDispatchInfoDisplayed(parameters.get("dispatchID1"));
			reportLog(status, testContext.getName(), "Verify the correct dispatch appears: ID: 32", "3.0",
					"Dispatch ID: 32 displayed successfully.");
			org.testng.Assert.assertTrue(status);

			workflowPage.selectUser(parameters.get("user4"));
			workflowPage.selectApp(parameters.get("app4"));
			workflowPage.selectStatus(parameters.get("status4"));
			workflowPage.clearSearchField();
			workflowPage.clickSearchButton();
			status = workflowPage.isDispatchInfoDisplayed(parameters.get("info1"));
			reportLog(status, testContext.getName(), "Verify Assigned dispatches appear.", "4.0",
					"Received dispatches appear");
			org.testng.Assert.assertTrue(status);

			status = workflowPage.isDispatchInfoDisplayed(parameters.get("info2"));
			reportLog(status, testContext.getName(), "Verify Received dispatches appear.", "4.1",
					"Assigned dispatches appear");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	@Test
	@Parameters({ "testdescription" })
	public void dispatchManagerOtherSearchTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			String tmpFolderPath = parameters.get("app1_file_path");
			String expectedFileName = parameters.get("file_name");
			String filepath1 = System.getProperty("user.dir") + tmpFolderPath + expectedFileName;
			File file = new File(filepath1);
			boolean status = file.exists();
			if (status) {
				file.delete();
			}

			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			DispatchPage dispatchPage = dashboardPage.clickDispatch();
			dispatchPage.searchDispatch(parameters.get("dispatch_name"));
			dispatchPage.clickCheckBox(parameters.get("dispatch_id"));
			customWait(2);
			dispatchPage.clickDownloadButton();
			customWait(5);
			file = new File(filepath1);
			status = file.exists();

			reportLog(status, testContext.getName(), "Check downloaded csv file", "1.2",
					"CSV file downloaded successfully");
			org.testng.Assert.assertTrue(status);

			dispatchPage.searchDispatch(parameters.get("dispatch_name2"));
			dispatchPage.clickCheckBox(parameters.get("dispatch_id2"));
			dispatchPage.clickCopyButton();
			customWait(5);

			status = dispatchPage.isDispatchNameDisplayed(parameters.get("dispatch_name_copy"));
			String dispatchID = dispatchPage.getDispatchID(parameters.get("dispatch_name_copy"));
			reportLog(status, testContext.getName(), "After clicking for: " + parameters.get("dispatch_name2"), "2.0",
					parameters.get("dispatch_name_copy") + " created successfully");
			org.testng.Assert.assertTrue(status);

			dispatchPage.searchDispatch(parameters.get("dispatch_name_copy"));
			customWait(5);
			dispatchPage.clickCheckBox(dispatchID);
			dispatchPage.clickDeleteButton();

			dispatchPage.searchDispatch(parameters.get("dispatch_name3"));
			dispatchPage.clickCheckBox(parameters.get("dispatch_id3"));
			dispatchPage.clickReAssignButton();
			status = dispatchPage.isReAssignUserDDLDisplayed();

			ArrayList<String> userActualList = dispatchPage.getReAssignUserList();
			String[] userExpectedList = parameters.get("user_list").split(";");
			verifyDropdownValuesnew("3.", userActualList, userExpectedList,
					"Verify the correct user appear in the correct order.", testContext);

			dispatchPage.selectReAssignUser(parameters.get("user2"));
			dispatchPage.clickSubmitButton();
			status = dispatchPage.getUserName(parameters.get("dispatch_name3")).equals(parameters.get("user2"));
			reportLog(status, testContext.getName(), "After clicking reassign for: " + parameters.get("dispatch_name3"),
					"4.0", parameters.get("dispatch_name3") + " assigned to " + parameters.get("user2"));
			org.testng.Assert.assertTrue(status);

			dispatchPage.selectReAssignUser(parameters.get("user1"));
			dispatchPage.clickSubmitButton();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void dispatchManagerSearch2Of4Test(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			DispatchPage dispatchPage = dashboardPage.clickDispatch();
			dispatchPage.clickAdvanceSearchButton();

			dispatchPage.selectUser(parameters.get("user"));
			dispatchPage.selectApp(parameters.get("app"));
			dispatchPage.selectStatus(parameters.get("status"));
			dispatchPage.enterTextDispatchfield(parameters.get("search_dispatch_fields"));
			dispatchPage.clickSearchButton();
			boolean status = dispatchPage.isDispatchNameDisplayed(parameters.get("dispatch_id1"));
			reportLog(status, testContext.getName(), "Verify the correct dispatch appears: ID 9", "1.0",
					"Correct dispatch appears: ID 9");
			org.testng.Assert.assertTrue(status);

			dispatchPage.clearTextDispatchfield();
			dispatchPage.enterTextDispatchfield(parameters.get("search_dispatch_fields12"));
			dispatchPage.clickSearchButton();
			status = dispatchPage.getErrorMessage().contains(parameters.get("message"));
			reportLog(status, testContext.getName(), "Verify Your search did not match any results...appears.", "2.0",
					parameters.get("message") + " appears");
			org.testng.Assert.assertTrue(status);

			dispatchPage.selectUser(parameters.get("user2"));
			dispatchPage.selectApp(parameters.get("app2"));
			dispatchPage.selectStatus(parameters.get("status2"));
			dispatchPage.clearTextDispatchfield();
			dispatchPage.enterTextDispatchfield(parameters.get("search_dispatch_fields2"));
			dispatchPage.clickSearchButton();
			status = dispatchPage.isDispatchNameDisplayed(parameters.get("dispatch_id2"));
			reportLog(status, testContext.getName(), "Verify the correct dispatch appears: ID 1", "3.0",
					"Correct dispatch appears: ID 1");
			org.testng.Assert.assertTrue(status);

			dispatchPage.selectUser(parameters.get("user3"));
			dispatchPage.selectApp(parameters.get("app3"));
			dispatchPage.selectStatus(parameters.get("status3"));
			dispatchPage.clearTextDispatchfield();
			dispatchPage.clickSearchButton();
			String[] statusList = parameters.get("status_list").split(";");
			for (int i = 0; i < statusList.length; i++) {
				status = dispatchPage.isDispatchInfoDisplayed(statusList[i]);
				reportLog(status, testContext.getName(), "Verify dispatch appears having status " + statusList[i],
						"4." + i, "Dispatch appears having status " + statusList[i]);
				org.testng.Assert.assertTrue(status);
			}

			dispatchPage.selectUser(parameters.get("user4"));
			dispatchPage.selectApp(parameters.get("app4"));
			dispatchPage.selectStatus(parameters.get("status4"));
			dispatchPage.clickSearchButton();
			String[] dispatchList = parameters.get("dispatch_list").split(";");
			for (int i = 0; i < dispatchList.length; i++) {
				status = dispatchPage.isDispatchInfoDisplayed(dispatchList[i]);
				reportLog(status, testContext.getName(), "Verify the correct dispatch appears: " + dispatchList[i],
						"5." + i, "Dispatch appears: " + dispatchList[i]);
				org.testng.Assert.assertTrue(status);
			}

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/*
	 * kp Test case ID TC7943 Summary : Workflow: Search (2 of 2)
	 */

	@Test
	@Parameters({ "testdescription" })
	public void dispatchManagerSearchTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			DispatchPage dispatchPage = dashboardPage.clickDispatch();
			dispatchPage.clickAdvanceSearchButton();

			ArrayList<String> userActualList = dispatchPage.getUserList();
			String[] userExpectedList = parameters.get("user_list").split(";");
			verifyDropdownValuesnew("1.", userActualList, userExpectedList,
					"Verify the correct user appear in the correct order.", testContext);

			ArrayList<String> appActualList = dispatchPage.getAppList();
			String[] appExpectedList = parameters.get("app_list").split(";");
			verifyDropdownValuesnew("2.", appActualList, appExpectedList,
					"Verify the correct apps appear in the correct order.", testContext);

			ArrayList<String> statusActualList = dispatchPage.getStatusList();
			String[] statusExpectedList = parameters.get("status_list").split(";");
			verifyDropdownValuesnew("3.", statusActualList, statusExpectedList,
					"Verify the correct status appear in the correct order.", testContext);

			dispatchPage.selectUser(parameters.get("user"));
			dispatchPage.selectApp(parameters.get("app"));
			dispatchPage.selectStatus(parameters.get("status"));
			dispatchPage.clickSearchButton();
			boolean flag = dispatchPage.isStatusDisplayedInEachRow(parameters.get("status"));
			reportLog(flag, testContext.getName(), "Checking status value in each row", "4.0",
					"Status value is Assigned in each row");
			org.testng.Assert.assertTrue(flag);

			dispatchPage.selectUser(parameters.get("user2"));
			dispatchPage.clickSearchButton();
			flag = dispatchPage.isUserDisplayedInEachRow(parameters.get("user2"));
			reportLog(flag, testContext.getName(), "Checking user value in each row", "5.0",
					"User value is " + parameters.get("user2") + " in each row");
			org.testng.Assert.assertTrue(flag);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void workflowAdvanceSearchTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			WorkflowPage workflowPage = dashboardPage.clickWorkflowAndDispatch();
			
			workflowPage.advancedSearchClick();
			workflowPage.selectUser(parameters.get("user"));
			workflowPage.selectAppfromDDL(parameters.get("app"));
			workflowPage.selectStatusfromDDL(parameters.get("status"));

			// Step#1
			workflowPage.clickCreateFrom();
			workflowPage.selectDate(parameters.get("create_select_year_from1"),
					parameters.get("create_select_month_from1"), parameters.get("create_select_day_from1"),0);

			workflowPage.clickCreateTo();
			workflowPage.selectDate(parameters.get("create_select_year_to1"), parameters.get("create_select_month_to1"),
					parameters.get("create_select_day_to1"),1);

			workflowPage.clickCompleteFrom();
			workflowPage.selectDate(parameters.get("complete_select_year_from1"),
					parameters.get("complete_select_month_from1"), parameters.get("complete_select_day_from1"),2);

			workflowPage.clickCompleteTo();
			workflowPage.selectDate(parameters.get("complete_select_year_to1"),
					parameters.get("complete_select_month_to1"), parameters.get("complete_select_day_to1"),3);

			/*
			 * workflowPage.selectDate(parameters.get("create_date_range_from1"),
			 * parameters.get("create_month_range_from1"),
			 * parameters.get("create_year_range_from1")); workflowPage.clickCreateTo();
			 * workflowPage.selectDate(parameters.get("create_date_range_to1"),
			 * parameters.get("create_month_range_to1"),
			 * parameters.get("create_year_range_to1")); workflowPage.clickCompleteFrom();
			 * workflowPage.selectDate(parameters.get("complete_date_range_from1"),
			 * parameters.get("complete_month_range_from1"),
			 * parameters.get("complete_year_range_from1")); workflowPage.clickCompleteTo();
			 * workflowPage.selectDate(parameters.get("complete_date_range_to1"),
			 * parameters.get("complete_month_range_to1"),
			 * parameters.get("complete_year_range_to1"));
			 */

			workflowPage.clickSearchButton();
			boolean status = (workflowPage.getWorkflowCount() > 0);
			reportLog(status, testContext.getName(), "Verify workflow appears.", "1.0", "Workflow appears.");
			org.testng.Assert.assertTrue(status);

			LoginPage login = workflowPage.clickLogOutButton();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();

			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department"));
			workflowPage = dashboardPage.clickWorkflowAndDispatch();
			workflowPage.advancedSearchClick();
			workflowPage.selectUser(parameters.get("user2"));
			workflowPage.selectAppfromDDL(parameters.get("app2"));
			workflowPage.selectStatusfromDDL(parameters.get("status2"));

			
			
			// Step #2 Kailash un-comment it
			workflowPage.clickCreateFrom();
			workflowPage.selectDate(parameters.get("create_select_year_from2"),
					parameters.get("create_select_month_from2"), parameters.get("create_select_day_from2"),0);

			workflowPage.clickCreateTo();
			workflowPage.selectDate(parameters.get("create_select_year_to2"), parameters.get("create_select_month_to2"),
					parameters.get("create_select_day_to2"),1);

			workflowPage.clickSearchButton();
			status = (workflowPage.getWorkflowCount() > 0);
			reportLog(status, testContext.getName(), "Verify workflow appears.", "2.0", "Workflow appears.");
			org.testng.Assert.assertTrue(status);
			
		
			/*
			 * workflowPage.selectDate(parameters.get("create_date_range_from2"),
			 * parameters.get("create_month_range_from2"),
			 * parameters.get("create_year_range_from2")); workflowPage.clickCreateTo();
			 * workflowPage.selectDate(parameters.get("create_date_range_to2"),
			 * parameters.get("create_month_range_to2"),
			 * parameters.get("create_year_range_to2"));
			 */


			/*//workflowPage.clickCompleteFrom();
			 * workflowPage.selectDate(parameters.get("complete_date_range_from3"),
			 * parameters.get("complete_month_range_from3"),
			 * parameters.get("complete_year_range_from3")); workflowPage.clickCompleteTo();
			 * workflowPage.selectDate(parameters.get("complete_date_range_to3"),
			 * parameters.get("complete_month_range_to3"),
			 * parameters.get("complete_year_range_to3"));
			 */

			// Step #3
			dashboardPage.clickWorkflowAndDispatch();
			workflowPage.advancedSearchClick();
			workflowPage.selectUser(parameters.get("user3"));
			workflowPage.selectAppfromDDL(parameters.get("app3"));
			workflowPage.selectStatusfromDDL(parameters.get("status3"));
			
			workflowPage.clickCompleteFrom();
			workflowPage.selectDate2(parameters.get("complete_select_year_from2"),
					parameters.get("complete_select_month_from2"), parameters.get("complete_select_day_from2"),2,0);

			workflowPage.clickCompleteTo();
			workflowPage.selectDate2(parameters.get("complete_select_year_to2"),
					parameters.get("complete_select_month_to2"), parameters.get("complete_select_day_to2"),3,1);
			workflowPage.clickSearchButton();
			
			status = (workflowPage.getWorkflowCount() > 0);
			reportLog(status, testContext.getName(), "Verify workflow appears.", "3.0", "Workflow appears.");
			org.testng.Assert.assertTrue(status);

			SubmissionDetailsPage submissionDetailsPage = workflowPage.clickWorkflowIDLink(parameters.get("id"));
			status = submissionDetailsPage.getSubmissionDetails(1).contains(parameters.get("detail1"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.0",
					parameters.get("detail1") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionDetailsPage.getSubmissionDetails(2).contains(parameters.get("detail2"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.1",
					parameters.get("detail2") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionDetailsPage.getSubmissionDetails(3).contains(parameters.get("detail3"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.2",
					parameters.get("detail3") + " displayed");
			org.testng.Assert.assertTrue(status);

			submissionDetailsPage.getDeviceID().contains(parameters.get("detail32"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.3",
					parameters.get("detail32") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionDetailsPage.getSubmissionDetails(4).contains(parameters.get("detail4"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.4",
					parameters.get("detail4") + " displayed");
			org.testng.Assert.assertTrue(status);

			submissionDetailsPage.getDepartment().contains(parameters.get("detail5"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.5",
					parameters.get("detail5") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionDetailsPage.isScreenDisplayed(parameters.get("screen1"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.6",
					parameters.get("screen1") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionDetailsPage.isScreenDisplayed(parameters.get("screen2"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.7",
					parameters.get("screen2") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionDetailsPage.isScreenDisplayed(parameters.get("screen3"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.8",
					parameters.get("screen3") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionDetailsPage.isTextDisplayed(parameters.get("screen1"), parameters.get("text1"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.9",
					parameters.get("text1") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionDetailsPage.getTextValue(parameters.get("text1")).contains(parameters.get("value1"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.10",
					parameters.get("value1") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionDetailsPage.isTextDisplayed(parameters.get("screen2"), parameters.get("text2"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.11",
					parameters.get("text2") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionDetailsPage.getTextValue(parameters.get("text2")).contains(parameters.get("value2"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.12",
					parameters.get("value2") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionDetailsPage.isTextDisplayed(parameters.get("screen3"), parameters.get("text3"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.13",
					parameters.get("text3") + " displayed");
			org.testng.Assert.assertTrue(status);

			status = submissionDetailsPage.getTextValue(parameters.get("text3")).contains(parameters.get("value3"));
			reportLog(status, testContext.getName(), "Verify the Submission Detail", "4.14",
					parameters.get("value3") + " displayed");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void workflowManager2Of2Test(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the account admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department"));
			WorkflowPage workflowPage = dashboardPage.clickWorkflowAndDispatch();
			workflowPage.advancedSearchClick();
			workflowPage.selectStatus(parameters.get("status1"));
			workflowPage.clickSearchButton();
			workflowPage.checkWorkflowByID(parameters.get("dispatch_id1"));
			workflowPage.clickCopyButton();
			// This appears: No Dispatch items selected

			workflowPage.checkWorkflowByID(parameters.get("dispatch_id2"));
			workflowPage.clickCopyButton();
			workflowPage.checkWorkflowByDesc(parameters.get("dispatch_desc2"));
			workflowPage.clickDeleteButton();
			workflowPage.advancedSearchClick();
			workflowPage.selectUser(parameters.get("user"));
			workflowPage.selectStatus(parameters.get("status2"));
			workflowPage.clickSearchButton();
			boolean status = workflowPage.isDispatchInfoDisplayed(parameters.get("dispatch_desc2"));
			reportLog(status, testContext.getName(), "Verify the deleted workflow displays Status = Deleted", "2.0",
					"Deleted workflow displays Status = Deleted");
			org.testng.Assert.assertTrue(status);

			String tmpFolderPath = parameters.get("app1_file_path");
			String expectedFileName = parameters.get("file_name");
			String filepath1 = System.getProperty("user.dir") + tmpFolderPath + expectedFileName;
			File file = new File(filepath1);
			status = file.exists();
			if (status) {
				file.delete();
			}
			customWait(2);
			workflowPage.checkWorkflowByDesc(parameters.get("dispatch_desc2"));
			customWait(2);
			workflowPage.clickDownloadButton();
			customWait(5);
			file = new File(filepath1);
			status = file.exists();
			String workingDir = System.getProperty("user.dir");
			String comapre_filepath1 = workingDir + parameters.get("compare_file_path");
			int value = comparefiles(filepath1, comapre_filepath1);
			status = value == 0 ? true : false;
			// reportLog(status, testContext.getName(),"Verify a .csv appears
			// and contains the correct information.", "3.0", ".csv appears and
			// contains the correct information.");
			// org.testng.Assert.assertTrue(status);

			workflowPage.clickReAssignButton();
			workflowPage.selectReassignUser(parameters.get("user2"));
			workflowPage.reassignSubmit();
			String message = workflowPage.getToastMessage();
			status = message.contains(parameters.get("message1"));
			reportLog(status, testContext.getName(), "Verify this appears: 1 dispatch item failed to be re-assigned",
					"4.0", "This appears: 1 dispatch item failed to be re-assigned");
			org.testng.Assert.assertTrue(status);

			status = message.contains(parameters.get("message2"));
			reportLog(status, testContext.getName(), "Verify User not assigned to all apps is displayed", "4.1",
					"User not assigned to all apps is displayed");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void workflowSearch1of2Test(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			DispatchPage dispatchPage = new DispatchPage(driver);
			WorkflowPage workflowPage = dashboardPage.clickWorkflowAndDispatch();

			workflowPage.advancedSearchClick();
			dashboardPage.clickWorkflowAndDispatch();
			dispatchPage.clickAdvanceSearchButton();

			ArrayList<String> userActualList = workflowPage.userDropDownList();
			String[] userExpectedList = parameters.get("user_list").split(";");
			verifyWorkflowValues("1.1", userActualList, userExpectedList,
					"Verify  correct user appear in the correct order.", testContext);

			boolean userListSorated = workflowPage.userSearchdropDownListSortedOrNot();
			reportLog(userListSorated, testContext.getName(),
					"Verify DDL contains the correct values and is sorted by last name ascending  ", "1.2",
					"User list is in sorated order by last name");
			org.testng.Assert.assertTrue(userListSorated);

			ArrayList<String> userActualList1 = workflowPage.appsDropDownList();
			String[] userExpectedList1 = parameters.get("app_list").split(";");
			verifyWorkflowValues("2.", userActualList1, userExpectedList1,
					"Verify  correct apps appear in the correct order.", testContext);

			boolean appListSorated = workflowPage.appSearchdropDownListSortedOrNot();
			reportLog(appListSorated, testContext.getName(),
					"Verify DDL contains the correct values and is sorted in ascending order  ", "2.4",
					"Apps names are in sorated order");
			org.testng.Assert.assertTrue(appListSorated);

			// Search first work-flow records

			workflowPage.clickCancel();
			dispatchPage.clickAdvanceSearchButton();
			dispatchPage.selectUser(parameters.get("user_1"));
			dispatchPage.selectApp(parameters.get("apps_1"));
			dispatchPage.selectStatus(parameters.get("status_1"));
			dispatchPage.clickSearchButton();

			boolean status_1 = (dispatchPage.getWorkflowListSize() == Integer
					.parseInt(parameters.get("workflow_list_size_1")));
			int nofFWorkflow_1 = dispatchPage.getWorkflowListSize();
			reportLog(status_1, testContext.getName(), "Verify the 4 workflow appear.", "3.0",
					"Total number of workflow display equal to:  " + nofFWorkflow_1);
			org.testng.Assert.assertTrue(status_1);

			String[] userExpectedList_1 = parameters.get("workflow_list_1").split(";");
			int size_1 = userExpectedList.length;
			ArrayList<String> userActualList_1 = dispatchPage.getWorkflowValues(size_1);
			verifyWorkflowValues("3.", userActualList_1, userExpectedList_1, "Verify Workflow After Search .",
					testContext);

			// Search second work-flow records
			workflowPage.clickCancel();
			dispatchPage.clickAdvanceSearchButton();
			dispatchPage.selectUser(parameters.get("user_2"));
			dispatchPage.selectApp(parameters.get("apps_2"));
			dispatchPage.selectStatus(parameters.get("status_2"));
			dispatchPage.clickSearchButton();

			boolean status_2 = (dispatchPage.getWorkflowListSize() == Integer
					.parseInt(parameters.get("workflow_list_size_2")));
			int nofFWorkflow_2 = dispatchPage.getWorkflowListSize();
			reportLog(status_2, testContext.getName(), "Verify the 4 workflow appear.", "4.0",
					"Total number of workflow display equal to:  " + nofFWorkflow_2);
			org.testng.Assert.assertTrue(status_2);

			String[] userExpectedList_2 = parameters.get("workflow_list_2").split(";");
			int size_2 = userExpectedList_2.length;
			ArrayList<String> userActualList_2 = dispatchPage.getWorkflowValues(size_2);
			verifyWorkflowValues("4.", userActualList_2, userExpectedList_2, "Verify Workflow After Search .",
					testContext);

			// Search third work-flow records

			workflowPage.clickCancel();
			dispatchPage.clickAdvanceSearchButton();
			dispatchPage.selectUser(parameters.get("user_3"));
			dispatchPage.selectApp(parameters.get("apps_3"));
			dispatchPage.selectStatus(parameters.get("status_3"));
			dispatchPage.clickSearchButton();

			boolean status_3 = (dispatchPage.getWorkflowListSize() == Integer
					.parseInt(parameters.get("workflow_list_size_3")));
			int nofFWorkflow_3 = dispatchPage.getWorkflowListSize();
			reportLog(status_3, testContext.getName(), "Verify the 4 workflow appear.", "5.0",
					"Total number of workflow display equal to:  " + nofFWorkflow_3);
			org.testng.Assert.assertTrue(status_3);

			String[] userExpectedList_3 = parameters.get("workflow_list_3").split(";");
			int size_3 = userExpectedList_3.length;
			ArrayList<String> userActualList_3 = dispatchPage.getWorkflowValues(size_3);
			verifyWorkflowValues("5.", userActualList_3, userExpectedList_3, "Verify Workflow After Search .",
					testContext);
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());

		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void workflowSearch2of2Test(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			DispatchPage dispatchPage = new DispatchPage(driver);
			// WorkflowPage workflowPage =
			// dashboardPage.clickWorkflowAndDispatch();
			WorkflowPage workflowPage = new WorkflowPage(driver);

			dashboardPage.clickWorkflowAndDispatch();
			dispatchPage.clickAdvanceSearchButton();
			dispatchPage.selectUser(parameters.get("user_1"));
			dispatchPage.selectApp(parameters.get("apps_1"));
			dispatchPage.selectStatus(parameters.get("status_1"));
			dispatchPage.enterText(parameters.get("search_field_1"));
			dispatchPage.clickSearchButton();

			boolean status = workflowPage.searchtext(parameters.get("search_ message"));
			reportLog(status, testContext.getName(), "Verify Your search did not return any results... appears.", "1.2",
					"Your search did not return any results, here are some tips displayed verified.");
			org.testng.Assert.assertTrue(status);

			LoginPage login = dispatchPage.clickLogOutButton();
			login.typeusername(parameters.get("username1"));
			login.typepassword(parameters.get("password1"));
			login.Clickonloginbutton();

			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department"));
			dashboardPage.clickWorkflowAndDispatch();
			workflowPage.advancedSearchClick();
			ArrayList<String> userActualList1 = workflowPage.appsDropDownList();
			String[] userExpectedList1 = parameters.get("app_list").split(";");
			verifyWorkflowValues("2.", userActualList1, userExpectedList1,
					"Verify  correct apps appear in the correct order.", testContext);

			boolean appListSorted = workflowPage.appdropDownListSortedOrNot();
			reportLog(appListSorted, testContext.getName(),
					"Verify DDL contains the correct values and is sorted in ascending order  ", "2.4",
					"Apps names are in sorted order");
			org.testng.Assert.assertTrue(appListSorted);

			// Search first work-flow records

			// workflowPage.clickCancel();
			dashboardPage.clickWorkflowAndDispatch();
			dispatchPage.clickAdvanceSearchButton();
			dispatchPage.selectUser(parameters.get("user_2"));
			dispatchPage.selectApp(parameters.get("apps_2"));
			dispatchPage.selectStatus(parameters.get("status_2"));
			dispatchPage.clickSearchButton();

			boolean status_1 = (dispatchPage.getWorkflowListSize() == Integer
					.parseInt(parameters.get("workflow_list_size_1")));
			int nofFWorkflow_1 = dispatchPage.getWorkflowListSize();
			// reportLog(status_1, testContext.getName(),"Verify the 4 workflow
			// appear.", "3.1", "Total number of workflow appear after search is
			// : " +nofFWorkflow_1);
			// org.testng.Assert.assertTrue(status_1);

			workflowPage.clickCancel();
			dispatchPage.clickAdvanceSearchButton();
			dispatchPage.selectUser(parameters.get("user_3"));
			dispatchPage.selectApp(parameters.get("apps_3"));
			dispatchPage.selectStatus(parameters.get("status_3"));
			dispatchPage.clickSearchButton();

			boolean status_2 = (dispatchPage.getWorkflowListSize() == Integer
					.parseInt(parameters.get("workflow_list_size_2")));
			int nofFWorkflow_2 = dispatchPage.getWorkflowListSize();
			// reportLog(status_2, testContext.getName(),"Verify the 2 workflow
			// appear.", "4.1", "Total number of workflow appear after search is
			// : " +nofFWorkflow_2);
			// org.testng.Assert.assertTrue(status_2);

			dashboardPage.clickWorkflowAndDispatch();
			dispatchPage.clickAdvanceSearchButton();
			dispatchPage.enterText(parameters.get("search_field_2"));
			dispatchPage.clickSearchButton();

			String[] userExpectedList_2 = parameters.get("workflow_list").split(";");
			int size_2 = userExpectedList_2.length;
			ArrayList<String> userActualList_2 = dispatchPage.getWorkflowValues(size_2);
			verifyDispatchValues("5.", userExpectedList_2, userActualList_2, "Verify Workflow After Search .",
					testContext);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
