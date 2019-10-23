package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.dispatch.DispatchItemDetailPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

public class DispatchItemDetailTest extends BrowserLaunchTest {

	@Parameters({ "step", "testdescription" })
	public void EditReceiveDispatch(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {

		DispatchItemDetailPage editDispatch = new DispatchItemDetailPage(driver);
		String successText = null;

		int i = 0;
		successText = editDispatch.RetriveReceivedDispatchdetaillink();
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
	public void verifyCUserFirstdesignerDispatchData(String step, String testdescription,
			final ITestContext testContext) throws Throwable {
		int i = 0;

		DispatchItemDetailPage AccessLinks = new DispatchItemDetailPage(driver);
		boolean success = false;

		String[] links = new String[100];
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String availablelinks = parameters.get("dispatch_values1");
		if (availablelinks != null && !availablelinks.isEmpty()) {
			links = availablelinks.split(";");
		}

		success = AccessLinks.RetriveFirstDispatchdetaillink(links);
		if (success)

		{

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Labels in dispatch items Screen :" + availablelinks);

		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Test case fail because values Not matched");

		}

		org.testng.Assert.assertTrue(success, "Test case fail because values are Not matched");

	}

	@Parameters({ "step", "testdescription" })
	public void verifyCUserSeconddesignerDispatchData(String step, String testdescription,
			final ITestContext testContext) throws Throwable {
		int i = 0;

		DispatchItemDetailPage AccessLinks = new DispatchItemDetailPage(driver);
		boolean success = false;

		String[] links = new String[100];
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String availablelinks = parameters.get("dispatch_values2");
		if (availablelinks != null && !availablelinks.isEmpty()) {
			links = availablelinks.split(";");
		}

		success = AccessLinks.RetriveSecondDispatchdetaillink(links);
		if (success)

		{

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Labels in dispatch items Screen :" + availablelinks);

		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Test case fail because values are Not matched");

		}

		org.testng.Assert.assertTrue(success, "Test case fail because values are Not matched");

	}

	@Parameters({ "step", "testdescription" })
	public void verifyEditButtonOfFirst(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		DispatchItemDetailPage editButton = new DispatchItemDetailPage(driver);

		String editButtonVisible = null;
		String valuesOfLink = null;

		editButtonVisible = parameters.get("edit_button_text");
		valuesOfLink = editButton.checkForFirstEditButton();
		if (valuesOfLink.equals(editButtonVisible))

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ": Edit button is enable in dispatch entries page and user can edit the dispatch");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, "Step " + step + "." + (++i)
					+ ": Edit button is disable in dispatch entries page and user can't edit the dispatch");

		}

		org.testng.Assert.assertTrue(valuesOfLink.equals(editButtonVisible),
				"Edit button is enable in dispatch entries page and user can edit the dispatch.");

	}

	@Parameters({ "step", "testdescription" })
	public void verifyEditButtonOfSecond(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		DispatchItemDetailPage editButton = new DispatchItemDetailPage(driver);

		String valuesOfLink = null;
		String editButtonVisible = null;

		editButtonVisible = parameters.get("edit_button_text");
		valuesOfLink = editButton.checkForSecondditButton();
		if (valuesOfLink.equals(editButtonVisible))

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ": Edit button is enable in dispatch entries page and user can edit the dispatch");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, "Step " + step + "." + (++i)
					+ ": Edit button is disable in dispatch entries page and user can't edit the dispatch");
		}

		org.testng.Assert.assertTrue(valuesOfLink.equals(editButtonVisible),
				"Edit button is enable in dispatch entries page and user can edit the dispatch.");

	}

}
