package com.canvas.qa.test.BETAQRT;

import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.ReportsPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author anna.marek
 *
 */
@Test
public class ActiveUserReportTest extends BrowserLaunchTest {
	@Parameters({ "step" })
	public void adminReportAccess(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		ReportsPage reportsLink = new ReportsPage(driver);

		reportsLink.selectApp(parameters.get("adminReportApp"));
		reportsLink.selectBeginDate(parameters.get("beginYear"), parameters.get("beginMonth"),
				parameters.get("beginDay"));
		reportsLink.selectEndDate(parameters.get("endYear"), parameters.get("endMonth"), parameters.get("endDay"));
		reportsLink.viewClick();

		try {
			reportsLink.verifyReportsTable(parameters.get("admin_username"), parameters.get("reports_num"),
					parameters.get("reports_date"));
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Company Admin Report Access Verified.");

		} catch (NoSuchElementException e) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Company Admin Report Access Verified.");
			org.testng.Assert.assertTrue(false, "Company Admin Report Access not Verified");
		}

		reportsLink.logout();
	}

	@Parameters({ "step" })
	public void exportCSV(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;
		try {
			loginTest.verifyValidLoginnew(step, parameters.get("admin_username"), parameters.get("admin_password"),
					testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}
		i += 3;

		ReportsPage reportsLink = new ReportsPage(driver);
		reportsLink.reportsButtonClick();
		reportsLink.activeUserReportClick();

		reportsLink.selectApp(parameters.get("adminReportApp"));
		reportsLink.selectBeginDate(parameters.get("beginYear"), parameters.get("beginMonth"),
				parameters.get("beginDay"));
		reportsLink.selectEndDate(parameters.get("endYear"), parameters.get("endMonth"), parameters.get("endDay"));
		reportsLink.exportClick();
		/*
		 * ReportManager.log(testContext.getName(), LogStatus.FAIL, "Step " +
		 * step + "." + (++i) + ": Check for exported CSV with" +
		 * parameters.get("csvLine") + ".");
		 */

	}

	@Parameters({ "step" })
	public void invalidDateRange(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;
		try {
			loginTest.verifyValidLoginnew(step, parameters.get("user_username"), parameters.get("user_password"),
					testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}
		i += 3;

		ReportsPage reportsLink = new ReportsPage(driver);
		reportsLink.reportsButtonClick();
		reportsLink.activeUserReportClick();

		reportsLink.selectApp(parameters.get("invalidDateApp"));
		reportsLink.selectBeginDate(parameters.get("beginYear"), parameters.get("beginMonth"),
				parameters.get("beginDay"));
		reportsLink.selectEndDate(parameters.get("endYear"), parameters.get("endMonth"), parameters.get("endDay"));
		reportsLink.viewClick();

		if (reportsLink.verifyInvalidDate(parameters.get("noRecordsMsg"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Invalid Date Range message.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Invalid Date Range message.");
		}
		org.testng.Assert.assertTrue(reportsLink.verifyInvalidDate(parameters.get("noRecordsMsg")),
				"Invalid Date Range message");

		reportsLink.logout();
	}

	@Parameters({ "step" })
	public void reporterReportAccess(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;
		try {
			loginTest.verifyValidLoginnew(step, parameters.get("reporter_username"),
					parameters.get("reporter_password"), testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}
		i += 3;

		ReportsPage reportsLink = new ReportsPage(driver);
		reportsLink.reportsButtonClick();
		reportsLink.activeUserReportClick();

		reportsLink.selectApp(parameters.get("userReportApp"));
		reportsLink.selectBeginDate(parameters.get("beginYear"), parameters.get("beginMonth"),
				parameters.get("beginDay"));
		reportsLink.selectEndDate(parameters.get("endYear"), parameters.get("endMonth"), parameters.get("endDay"));
		reportsLink.viewClick();

		try {
			reportsLink.verifyReportsTable(parameters.get("user_username"), parameters.get("reports_num"),
					parameters.get("reports_date"));
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Company Reporter Report Access Verified.");

		} catch (NoSuchElementException e) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Company Reporter Report Access Verified.");
			org.testng.Assert.assertTrue(false, "Company Reporter Report Access not Verified.");
		}

		reportsLink.logout();
	}

	@Parameters({ "step" })
	public void userReportAccess(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;
		try {
			loginTest.verifyValidLogin1(step, parameters.get("user_username"), parameters.get("user_password"),
					testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}
		i += 3;

		ReportsPage reportsLink = new ReportsPage(driver);
		reportsLink.reportsButtonClick();
		reportsLink.activeUserReportClick();

		reportsLink.selectApp(parameters.get("userReportApp"));
		reportsLink.selectBeginDate(parameters.get("beginYear"), parameters.get("beginMonth"),
				parameters.get("beginDay"));
		reportsLink.selectEndDate(parameters.get("endYear"), parameters.get("endMonth"), parameters.get("endDay"));
		reportsLink.viewClick();

		try {
			reportsLink.verifyReportsTable(parameters.get("user_username"), parameters.get("reports_num"),
					parameters.get("reports_date"));
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Company User Report Access Verified.");

		} catch (NoSuchElementException e) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Company User Report Access Verified.");
			org.testng.Assert.assertTrue(false, "Company User Report Access not Verified.");
		}

		reportsLink.logout();
	}

	@Parameters({ "step" })
	public void verifyAppsList(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;
		try {
			loginTest.verifyValidLoginnew(step, parameters.get("admin_username"), parameters.get("admin_password"),
					testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}
		i += 3;

		ReportsPage reportsLink = new ReportsPage(driver);
		reportsLink.reportsButtonClick();
		reportsLink.activeUserReportClick();

		String[] apps = new String[20];
		apps = parameters.get("reportApps").split(";");
		if (reportsLink.verifyApps(apps)) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": All Apps in DDL");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": All Apps in DDL");
			org.testng.Assert.assertTrue(false, "all apps not in dll");
		}

		reportsLink.selectApp(parameters.get("userReportApp"));
		reportsLink.selectBeginDate(parameters.get("beginYear"), parameters.get("beginMonth"),
				parameters.get("beginDay"));
		reportsLink.selectEndDate(parameters.get("endYear"), parameters.get("endMonth"), parameters.get("endDay"));
		reportsLink.viewClick();

		try {
			reportsLink.verifyReportsTable(parameters.get("user_username"), parameters.get("reports_num"),
					parameters.get("reports_date"));
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Company Admin Report Access Verified.");

		} catch (NoSuchElementException e) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Company Admin Report Access Verified.");
			org.testng.Assert.assertTrue(false, "Company Admin Report Access not Verified.");
		}
	}
}
