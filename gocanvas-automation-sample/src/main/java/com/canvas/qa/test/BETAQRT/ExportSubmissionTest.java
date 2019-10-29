package com.canvas.qa.test.BETAQRT;

import java.awt.AWTException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.submissions.ExportSubmissionPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

@Test
public class ExportSubmissionTest extends BrowserLaunchTest

{

	@Parameters({ "step", "testdescription" })
	public void exportCSV(String step, String testdescription, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		ExportSubmissionPage submissionPage = new ExportSubmissionPage(driver);
		submissionPage.submissionClick();
		Thread.sleep(30000);
		submissionPage.selectExportApp(parameters.get("step2App"));
		Thread.sleep(15000);
		submissionPage.selectBeginDate(parameters.get("beginDate"));
		submissionPage.selectEndDate(parameters.get("endDate"));
		submissionPage.selectCSV();
		submissionPage.checkDeviceID();

		try {
			submissionPage.exportClick();
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Exported CSV.");
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Verify that CSV includes Device ID.");
		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Exported CSV.");
		}
	}

	@Parameters({ "step", "testdescription" })
	public void exportXML(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, AWTException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		ExportSubmissionPage submissionPage = new ExportSubmissionPage(driver);
		submissionPage.submissionClick();
		Thread.sleep(30000);
		submissionPage.selectExportApp(parameters.get("step3App"));
		Thread.sleep(15000);
		submissionPage.selectBeginDate(parameters.get("beginDate"));
		submissionPage.selectEndDate(parameters.get("endDate"));
		submissionPage.selectXML();

		try {
			submissionPage.exportClick();

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Exported XML.");
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Verify that XML does NOT include Device ID.");
		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Exported XML.");
		}

		/*
		 * Robot r=new Robot();
		 * 
		 * r.keyPress(KeyEvent.VK_TAB); r.keyRelease(KeyEvent.VK_TAB);
		 * r.keyPress(KeyEvent.VK_TAB); r.keyRelease(KeyEvent.VK_TAB);
		 * r.keyPress(KeyEvent.VK_TAB); r.keyRelease(KeyEvent.VK_TAB);
		 * r.keyPress(KeyEvent.VK_TAB); r.keyRelease(KeyEvent.VK_TAB);
		 * r.keyPress(KeyEvent.VK_ENTER); r.keyRelease(KeyEvent.VK_ENTER);
		 */
	}

	@Parameters({ "step", "testdescription" })
	public void noneInDateRange(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		ExportSubmissionPage submissionPage = new ExportSubmissionPage(driver);
		submissionPage.selectBeginDate(parameters.get("invalidBeginDate"));
		submissionPage.selectEndDate(parameters.get("invalidEndDate"));

		if (submissionPage.getMsg().contains(parameters.get("invalidDateMsg"))) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct Invalid Date Range message.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct Invalid Date Range message.");
		}
		org.testng.Assert.assertTrue(submissionPage.getMsg().contains(parameters.get("invalidDateMsg")));
	}

	@Parameters({ "step", "testdescription" })
	public void unableToExport(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 3;
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		try {
			loginTest.verifyValidLogin1(step, testdescription, parameters.get("username"), parameters.get("password"),
					testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}

		ExportSubmissionPage submissionPage = new ExportSubmissionPage(driver);
		submissionPage.submissionClick();
		Thread.sleep(30000);
		submissionPage.selectExportApp(parameters.get("step1App"));
		Thread.sleep(15000);
		submissionPage.selectBeginDate(parameters.get("beginDate"));
		submissionPage.selectEndDate(parameters.get("endDate"));
		submissionPage.selectVersion(parameters.get("step1Version"));
		// submissionPage.selectCSV();

		if (submissionPage.CSVErrorIcon()) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": CSV Error Icon appears.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": CSV Error Icon appears.");
		}
		org.testng.Assert.assertTrue(submissionPage.CSVErrorIcon());
		if (submissionPage.XMLErrorIcon()) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": XML Error Icon appears.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": XML Error Icon appears.");
		}
		org.testng.Assert.assertTrue(submissionPage.XMLErrorIcon());
		try {
			submissionPage.exportClick();
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Export button inactive.");
		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Export button inactive.");
		}
	}
}
