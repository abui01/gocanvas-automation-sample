package com.canvas.qa.test.BETAQRT;

import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.profile.EditInformationPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author taukeer.ahmad
 *
 */
@Test
public class EditInformation extends BrowserLaunchTest

{

	@Parameters({ "step" })
	public void loginChangeCountry(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		Thread.sleep(5000);
		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 3;
		try {
			loginTest.verifyValidLogin1(step, parameters.get("useremail"), parameters.get("password"), testContext);
			EditInformationPage editInformation = new EditInformationPage(driver);
			editInformation.profileBtnClick();
			try {
				editInformation.updateUserCountry(parameters.get("country"));
				ReportManager.log(testContext.getName(), LogStatus.PASS,
						"Step " + (step) + "." + (++i) + ": Country Change.");
			} catch (Exception e) {
				e.printStackTrace();
				ReportManager.log(testContext.getName(), LogStatus.FAIL,
						"Step " + (step) + "." + (++i) + ": Country Change.");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Parameters({ "step" })
	public void loginChangeEmail(String step, ITestContext testContext) throws InterruptedException {

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 3;
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String email = parameters.get("useremail");
		try {
			loginTest.verifyValidLogin1(step, null, null, testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}
		EditInformationPage editInformation = new EditInformationPage(driver);
		editInformation.profileBtnClick();

		try {
			editInformation.updateEmail(email);
			boolean verified = editInformation.verifyEmailUpdated(email);
			if (verified)
				ReportManager.log(testContext.getName(), LogStatus.PASS,
						"Step " + step + "." + (++i) + ": Email Updated.");
			else
				ReportManager.log(testContext.getName(), LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": Email Not Updated.");
			editInformation.logout();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@Parameters({ "step" })
	public void loginChangeFirstName(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		Thread.sleep(5000);
		int i = 0;
		try {
			EditInformationPage editInformation = new EditInformationPage(driver);
			editInformation.updateFirstName(parameters.get("firstname"));
			Thread.sleep(5000);
			boolean verified = editInformation.verifyFirstNameUpdated(parameters.get("firstname"));
			if (verified)
				ReportManager.log(testContext.getName(), LogStatus.PASS,
						"Step " + (step) + "." + (++i) + ": First Name Change.");
			else
				ReportManager.log(testContext.getName(), LogStatus.FAIL,
						"Step " + (step) + "." + (++i) + ": First Name Change.");
		} catch (Exception e) {
			e.printStackTrace();
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + (step) + "." + (++i) + ": First Name Change.");
		}
	}

	@Parameters({ "step" })
	public void loginChangeTimeZone(String step, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		Thread.sleep(5000);
		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 3;
		int steps = Integer.parseInt(step);
		try {
			loginTest.verifyValidLogin1(step, parameters.get("useremail"), parameters.get("password"), testContext);
			EditInformationPage editInformation = new EditInformationPage(driver);
			editInformation.profileBtnClick();
			try {
				editInformation.updateTimeZone(parameters.get("timezone"));
				boolean verified = editInformation.verifyTimeZone(parameters.get("timezone"));
				if (verified)
					ReportManager.log(testContext.getName(), LogStatus.PASS,
							"Step " + (steps++) + "." + (++i) + ": Time Zone Change.");
				else
					ReportManager.log(testContext.getName(), LogStatus.FAIL,
							"Step " + (steps++) + "." + (++i) + ": Time Zone Change.");
			} catch (Exception e) {
				e.printStackTrace();
				ReportManager.log(testContext.getName(), LogStatus.FAIL,
						"Step " + (steps++) + "." + (++i) + ": Time Zone Change.");

			}
			try {
				i = 0;
				editInformation.updateDefaultLocation((parameters.get("defaultlocation")));
				ReportManager.log(testContext.getName(), LogStatus.PASS,
						"Step " + (steps++) + "." + (++i) + ": Default Location Change.");
			} catch (Exception e) {
				e.printStackTrace();
				ReportManager.log(testContext.getName(), LogStatus.FAIL,
						"Step " + (steps++) + "." + (++i) + ": Default Location Change.");
			}
			editInformation.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
