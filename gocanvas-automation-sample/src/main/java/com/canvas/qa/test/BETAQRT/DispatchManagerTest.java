package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.dispatch.DispatchItemDetailPage;
import com.canvas.qa.pages.dispatch.DispatchManagerPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

@Test
public class DispatchManagerTest extends BrowserLaunchTest {

	@Parameters({ "step", "testdescription" })
	public void EditReceiveDispatch(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {

		DispatchItemDetailPage editDispatch = new DispatchItemDetailPage(driver);

		int i = 0;
		String successText = editDispatch.RetriveReceivedDispatchdetaillink();
		if (successText != null && !successText.isEmpty()
				&& successText.contains("That item can not be edited. It is in the state 'Received'")) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Dispatch can not be edited. It is in the state 'Received'");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Error in Editing dispatch- " + successText);

		}
		org.testng.Assert.assertTrue(successText != null && !successText.isEmpty()
				&& successText.contains("That item can not be edited. It is in the state 'Received'"));
	}

	@Parameters({ "step", "testdescription" })
	public void verifyCalendarFilterView(String step, String testdescription, final ITestContext testContext)
			throws Throwable {
		int i = 0;

		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		DispatchManagerPage dashboardLink = new DispatchManagerPage(driver);
		try {
			CanvasLoginTest loginTest = new CanvasLoginTest();

			loginTest.verifyValidLogin1(step, testdescription, parameters.get("account_admin"),
					parameters.get("account_admin_pass"), testContext);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String calendarView = parameters.get("date");
		String calendarViewdate = dashboardLink.checkForCalendarView();
		if (calendarViewdate.equals(calendarView))

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Calendar filter mode is Day view");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Calendar filter mode is other than Day view");
		}
		org.testng.Assert.assertTrue(calendarViewdate.equals(calendarView));
	}

	@Parameters({ "step", "testdescription" })
	public void verifyCopyDispatchButton(String step, String testdescription, final ITestContext testContext)
			throws Throwable {
		int i = 0;

		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		DispatchManagerPage dashboardLink = new DispatchManagerPage(driver);

		String toastmsgaccepted = parameters.get("toastmsgcopy");
		String toastmessageactual = dashboardLink.checkForDispatchescopied();
		String copieddispatchname = dashboardLink.checkForcopieddispatchname();

		if (toastmessageactual.equals(toastmsgaccepted))

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ": Dispatch Is copied And copied dispatch name is : " + copieddispatchname);
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ":Some issue in copying the Dispatch");
		}
		org.testng.Assert.assertTrue(toastmessageactual.equals(toastmsgaccepted));
	}

	@Parameters({ "step", "testdescription" })
	public void verifyCUserSeconddesignerDispatchData(String step, String testdescription,
			final ITestContext testContext) throws Throwable {
		int i = 2;

		DispatchItemDetailPage AccessLinks = new DispatchItemDetailPage(driver);

		String[] links = new String[100];
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String availablelinks = parameters.get("dispatch_values2");
		if (availablelinks != null && !availablelinks.isEmpty()) {
			links = availablelinks.split(";");
		}

		boolean success = AccessLinks.RetriveSecondDispatchdetaillink(links);
		if (success)

		{

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Labels in dispatch items Screen :" + availablelinks);
			org.testng.Assert.assertTrue(true, "Step " + step + ": Fields Detail:" + links);

		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Test case fail because values Not matched");
		}
		org.testng.Assert.assertTrue(success);
	}

	@Parameters({ "step", "testdescription" })
	public void verifyDeleteDispatchButton(String step, String testdescription, final ITestContext testContext)
			throws Throwable {
		int i = 0;

		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		DispatchManagerPage dashboardLink = new DispatchManagerPage(driver);

		String deleteddispatchname = dashboardLink.checkForcopieddispatchname();
		String toastmsgaccepted = parameters.get("toastmsgdelete");
		String toastmessageactual = dashboardLink.checkForDispatchDeleted();

		if (toastmessageactual.equals(toastmsgaccepted))

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ":Dispatch Is deleted And deleted dispatch name is : " + deleteddispatchname);
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ":Some issue in deleteting the dispatch");
		}
		org.testng.Assert.assertTrue(toastmessageactual.equals(toastmsgaccepted));
	}

	@Parameters({ "step", "testdescription" })
	public void verifyEditButtonOfFirst(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		DispatchItemDetailPage editButton = new DispatchItemDetailPage(driver);

		String editButtonVisible = parameters.get("edit_button_text");
		String valuesOfLink = editButton.checkForFirstEditButton();
		if (valuesOfLink.equals(editButtonVisible))

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ": Edit button is enable in dispatch entries page and user can edit the dispatch");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, "Step " + step + "." + (++i)
					+ ": Edit button is disable in dispatch entries page and user can't edit the dispatch");
		}
		org.testng.Assert.assertTrue(valuesOfLink.equals(editButtonVisible));
	}

	@Parameters({ "step", "testdescription" })
	public void verifyEditButtonOfSecond(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		DispatchItemDetailPage editButton = new DispatchItemDetailPage(driver);

		String editButtonVisible = parameters.get("edit_button_text");
		String valuesOfLink = editButton.checkForSecondditButton();
		if (valuesOfLink.equals(editButtonVisible))

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ": Edit button is enable in dispatch entries page and user can edit the dispatch");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, "Step " + step + "." + (++i)
					+ ": Edit button is disable in dispatch entries page and user can't edit the dispatch");
		}
		org.testng.Assert.assertTrue(valuesOfLink.equals(editButtonVisible));
	}

	@Parameters({ "step", "testdescription" })
	public void verifyUnAssignDispatchButton(String step, String testdescription, final ITestContext testContext)
			throws Throwable {
		int i = 0;

		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		DispatchManagerPage dashboardLink = new DispatchManagerPage(driver);

		String toastmsgaccepted = parameters.get("toastmsgunassign");
		String toastmessageactual = dashboardLink.checkForDispatchUnassigned();
		String dispatchstatus = dashboardLink.checkstatusofdispatch();

		if (toastmessageactual.equals(toastmsgaccepted))

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ":Dispatch is un-assigned and status of the dispatch is after update is : " + dispatchstatus);
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ":Some issue in un-assiging user from dispatch");
		}
		org.testng.Assert.assertTrue(toastmessageactual.equals(toastmsgaccepted));
	}

}
