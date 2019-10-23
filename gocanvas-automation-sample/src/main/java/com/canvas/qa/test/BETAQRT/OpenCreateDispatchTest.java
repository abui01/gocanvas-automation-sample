package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.dispatch.OpenCreateDispatchPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

public class OpenCreateDispatchTest extends BrowserLaunchTest {

	@Parameters({ "step", "testdescription" })
	public void verifyCreateDispatchLink(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{

		OpenCreateDispatchPage dispatchlink = new OpenCreateDispatchPage(driver);

		/*
		 * try {
		 */
		int i = 1;
		// ExtentTest logger = ReportManager.getExtentTest();
		dispatchlink.clickoncreatedispatch();
		String successText = dispatchlink.checkForSuccessfullClickCreateDispatch();
		if (successText != null && !successText.isEmpty() && successText.contains("Create Dispatch")) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": User has clicked on Create Dispatch Link Successful.");

		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Click on Create Dispatch Link Unsuccessful - " + successText);

		}

		org.testng.Assert.assertTrue(successText != null && !successText.isEmpty(),
				"Click on Create Dispatch Link Unsuccessful");

		/*
		 * } catch (Exception e) { ReportManager.lognew(testContext.getName(),
		 * testdescriptionLogStatus.FAIL, e.getMessage());
		 * 
		 * }
		 */

	}

	@Parameters({ "step", "testdescription" })
	public void verifyWorkflowDispatchLink(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		CanvasLoginTest loginTest = new CanvasLoginTest();
		OpenCreateDispatchPage dispatchlink = new OpenCreateDispatchPage(driver);

		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		/* try { */
		loginTest.verifyValidLogin1(step, testdescription, parameters.get("username"), parameters.get("password"),
				testContext);
		/*
		 * } catch (Exception e) { e.printStackTrace(); //LOGGER.
		 * info("EditInformation.updatesAppear : Login has encountered a problem."
		 * ); }
		 */

		/* try { */
		int i = 0;
		// ExtentTest logger = ReportManager.getExtentTest();
		dispatchlink.clickonworkflowanddispatch();
		String successText = dispatchlink.checkForSuccessfulLogin();
		if (successText != null && !successText.isEmpty() && successText.contains("Workflow & Dispatch")
				|| successText.contains("Dispatch")) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": User has clicked on Workflow and Dispatch Link Successful.");

		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Click on Workflow and Dispatch Link Unsuccessful");

		}

		org.testng.Assert.assertTrue(successText != null && !successText.isEmpty(),
				"Click on Workflow and Dispatch Link Unsuccessful");

		/*
		 * } catch (Exception e) { ReportManager.lognew(testContext.getName(),
		 * testdescriptionLogStatus.FAIL, e.getMessage());
		 * 
		 * }
		 */
	}
}
