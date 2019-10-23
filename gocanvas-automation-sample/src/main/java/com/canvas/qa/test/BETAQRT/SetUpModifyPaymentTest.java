package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.NoSuchElementException;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.BillingOptionsPage;
import com.canvas.qa.pages.profile.AddUsersPage;
import com.canvas.qa.pages.profile.UsersPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author anna.marek
 *
 */
@Test
public class SetUpModifyPaymentTest extends BrowserLaunchTest {

	int randomInt = (new Random()).nextInt(1000) + 10;

	@Parameters({ "step", "testdescription" })
	public void addUser(String step, String testdescription, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		AddUsersPage addUsersLink = new AddUsersPage(driver);
		addUsersLink.usersButtonClick();
		addUsersLink.addUsersButtonClick();
		addUsersLink.fillFields(parameters.get("firstName"), parameters.get("lastName"),
				randomInt + parameters.get("email"));
		addUsersLink.purchaseClick();

		UsersPage usersLink = new UsersPage(driver);
		usersLink.usersButtonClick();
		if (usersLink.userAppears(randomInt + parameters.get("email"))) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": New user appears.");
			org.testng.Assert.assertTrue(true);
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": New user appears.");
			org.testng.Assert.assertTrue(false);
		}

	}

	@Parameters({ "step", "testdescription" })
	public void changeRate(String step, String testdescription, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		AddUsersPage addUsersLink = new AddUsersPage(driver);
		addUsersLink.cancelClick();

		BillingOptionsPage billingOptsLink = new BillingOptionsPage(driver);

		billingOptsLink.billingOptsClick();
		billingOptsLink.editAnnual();
		billingOptsLink.selectProfRate(parameters.get("step4AnnualRate"));
		billingOptsLink.saveClick();

		billingOptsLink.editMonthly();
		billingOptsLink.selectProfRate(parameters.get("step4MonthlyRate"));
		billingOptsLink.saveClick();

		try {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Company Admin Report Access Verified.");
			org.testng.Assert.assertTrue(true);

		} catch (NoSuchElementException e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Company Admin Report Access Verified.");
			org.testng.Assert.assertTrue(false);
		}

	}

	@Parameters({ "step", "testdescription" })
	public void formSubDisplays(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		UsersPage usersLink = new UsersPage(driver);
		usersLink.usersButtonClick();

		String rate = usersLink.getOpenRate();
		if (rate.equals(parameters.get("formSub"))) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Form Submission displays for Open User.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Form Submission displays for Open User.");
		}
		org.testng.Assert.assertTrue(rate.equals(parameters.get("formSub")));
	}

	@Parameters({ "step", "testdescription" })
	public void noDiscount(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		try {
			CanvasLoginTest loginTest = new CanvasLoginTest();
			int i = 0;

			loginTest.verifyValidLogin1(step, testdescription, parameters.get("username"), parameters.get("password"),
					testContext);

			i += 3;

			BillingOptionsPage billingOptionsLink = new BillingOptionsPage(driver);
			billingOptionsLink.gotoUser(parameters.get("companyAdmin"));
			billingOptionsLink.billingOptsClick();

			billingOptionsLink.editAnnual();
			billingOptionsLink.selectProfRate("Form Submission");
			billingOptionsLink.saveClick();

			if (billingOptionsLink.allNoDiscount()) {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
						"Step " + step + "." + (++i) + ": All Annual plans have No Discount.");

			} else {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": All Annual plans have No Discount.");
			}
			org.testng.Assert.assertTrue(billingOptionsLink.allNoDiscount());
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testdescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Parameters({ "step", "testdescription" })
	public void postConditions(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		BillingOptionsPage billingOptsLink = new BillingOptionsPage(driver);
		billingOptsLink.billingOptsClick();

		billingOptsLink.editAnnual();
		billingOptsLink.selectProfRate(parameters.get("originalAnnualRate"));
		billingOptsLink.saveClick();

		billingOptsLink.editMonthly();
		billingOptsLink.selectProfRate(parameters.get("originalMonthlyRate"));
		billingOptsLink.saveClick();
		ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
				"Step " + step + ": Post Conditions");
	}

	@Parameters({ "step", "testdescription" })
	public void verifyChangedRates(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		AddUsersPage addUsersLink = new AddUsersPage(driver);
		addUsersLink.usersButtonClick();
		addUsersLink.addUsersButtonClick();

		String annualText = addUsersLink.getAnnualAmount();
		String monthlyText = addUsersLink.getMonthlyAmount();
		if (annualText.contains(parameters.get("newRateAnnual"))) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Annual No Discount rate correct for adding a user.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Annual No Discount rate correct for adding a user.");
		}
		org.testng.Assert.assertTrue(annualText.contains(parameters.get("newRateAnnual")));
		if (monthlyText.contains(parameters.get("newRateMonthly"))) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Monthly No Discount rate correct for adding a user.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Monthly No Discount rate correct for adding a user.");
		}
		org.testng.Assert.assertTrue(monthlyText.contains(parameters.get("newRateMonthly")));
	}

	@Parameters({ "step", "testdescription" })
	public void verifyRates(String step, String testdescription, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		AddUsersPage addUsersLink = new AddUsersPage(driver);
		addUsersLink.addUsersButtonClick();

		String annualText = addUsersLink.getAnnualAmount();
		String monthlyText = addUsersLink.getMonthlyAmount();
		if (annualText.contains(parameters.get("noDiscountAnnual"))) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Annual No Discount rate correct for adding a user.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Annual No Discount rate correct for adding a user.");
		}
		org.testng.Assert.assertTrue(annualText.contains(parameters.get("noDiscountAnnual")));
		if (monthlyText.contains(parameters.get("noDiscountMonthly"))) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Monthly No Discount rate correct for adding a user.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Monthly No Discount rate correct for adding a user.");
		}
		org.testng.Assert.assertTrue(monthlyText.contains(parameters.get("noDiscountMonthly")));
	}
}
