package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.dispatch.UploadDispatcFilePage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

@Test
public class UploadDispatchFile extends BrowserLaunchTest

{
	@Parameters({ "step", "testdescription" })
	public void diffnewDepartment(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		Thread.sleep(5000);
		int i = 3;

		/* try { */

		UploadDispatcFilePage dispatchPage = new UploadDispatcFilePage(driver);
		boolean addSuccess = dispatchPage.diffDepartment(parameters.get("step3dispatchdesc"));
		if (!addSuccess == true) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ": Dispatch Uploaded in previous step does not appear in this departement..");
		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ":  Some error in dispatch Uploaded in previous step .");
		}

		org.testng.Assert.assertTrue(!addSuccess == true,
				"UploadDispatchFile.diffDepartment : Verifying Dispatch exist on previous Step contains Error.");

		dispatchPage.logout();
		/*
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
	}

	@Parameters({ "step", "testdescription" })
	public void secondDepartment(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		Thread.sleep(5000);
		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 3;
		/*
		 * try {
		 */
		loginTest.verifyValidLoginnew(step, testdescription, parameters.get("departmentuser"),
				parameters.get("departmentpassword"), testContext);
		/*
		 * } catch (Exception e) { e.printStackTrace(); LOGGER.
		 * info("UploadDispatchFile.secondDepartment : Login has encountered a problem."
		 * ); }
		 */
		/*
		 * try {
		 */

		UploadDispatcFilePage dispatchPage = new UploadDispatcFilePage(driver);
		dispatchPage.header();
		dispatchPage.workflowAnddispatchBtnClick();
		dispatchPage.uploadBtnClick();
		boolean uploadSuccess = dispatchPage.csvFileDep2(parameters.get("step3dispatchdesc"));
		if (uploadSuccess) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": CSV File Uploaded.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ":  Issue in Uploading CSV File.");
		}

		org.testng.Assert.assertTrue(uploadSuccess,
				"UploadDispatchFile.secondDepartment : Upload CSV File Contains Error.");

		/*
		 * } catch (Exception e) { e.printStackTrace(); }
		 * 
		 */

	}

	@Parameters({ "step", "testdescription" })
	public void uploadCSVDispatchFile(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {

		int i = 3;
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		UploadDispatcFilePage dispatchPage = new UploadDispatcFilePage(driver);
		dispatchPage.dispatchBtnClick();
		dispatchPage.uploadBtnClick();
		boolean success = dispatchPage.csvFileDep1(parameters.get("step2dispatchname"),
				parameters.get("step2dispatchdesc"), parameters.get("step2dispatchtime"));

		if (success) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": CSV File Uploaded.");
		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ":  Issue in Uploading CSV File.");
		}

		org.testng.Assert.assertTrue(success,
				"UploadDispatchFile.uploadCSVDispatchFile : Text Verification failed. returned text doesnot match with expected text.");

		dispatchPage.logout();
	}

	@Parameters({ "step", "testdescription" })
	public void uploadNonCSVDispatchFile(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 3;
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		/* try { */
		loginTest.verifyValidLogin1(step, testdescription, parameters.get("designeruser"),
				parameters.get("designerpassword"), testContext);
		/*
		 * } catch (Exception e) { e.printStackTrace(); LOGGER.
		 * info("UploadDispatchFile.uploadNonCSVDispatchFile : Login has encountered a problem."
		 * ); }
		 */

		UploadDispatcFilePage dispatchPage = new UploadDispatcFilePage(driver);
		dispatchPage.dispatchBtnClick();
		dispatchPage.uploadBtnClick();
		String successText = dispatchPage.nonCSVFile();

		if (successText != null && !successText.isEmpty()
				&& successText.contains("The file you uploaded contains errors.")) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": No dispatch is created.");
		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Verify the header display. Sign up for your 30-day free trial.");
		}

		org.testng.Assert.assertTrue(successText != null && !successText.isEmpty(),
				"UploadDispatchFile.uploadNonCSVDispatchFile : Text Verification failed. returned text doesnot match with expected text.");
	}
}
