package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.apps.AppDetailPage;
import com.canvas.qa.pages.apps.AppsPage;
import com.canvas.qa.pages.submissions.SubmissionsPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

@Test
public class SubmissionViewTest extends BrowserLaunchTest {

	@Parameters({ "step", "testdescription" })
	public void verifyAppDetails(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		int i = 0;
		AppsPage appsPage = new AppsPage(driver);
		appsPage.appsButtonClick();
		String appsName = parameters.get("apps_detail");
		String appsInvalidLastModified = "";
		String appsInvalidSubmissionCount = "";
		int appValidLastModifiedCount = 0;
		int appValidSubmissionCount = 0;

		/* try { */
		String appsDetailArray[] = appsName.split(",");

		for (String appDetail : appsDetailArray) {
			String appDetailArr[] = appDetail.split(";");
			String lastModified = appsPage.getLastModifiedDate(appDetailArr[0]);
			if (appDetailArr[1].equals(lastModified)) {
				appValidLastModifiedCount++;
			} else {
				appsInvalidLastModified += appDetailArr[0] + " :" + lastModified + ",";
			}

		}
		if (appsDetailArray.length == appValidLastModifiedCount) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct last modified dates");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Incorrect last modified dates " + appsInvalidLastModified);
		}

		org.testng.Assert.assertTrue(appsDetailArray.length == appValidLastModifiedCount,
				"Incorrect last modified dates");

		for (String appDetail : appsDetailArray) {
			String appDetailArr[] = appDetail.split(";");
			appsPage.appClick(appDetailArr[0]);
			AppDetailPage appDetailPage = new AppDetailPage(driver);
			String submissionCount = appDetailPage.getSubmissionCount();

			if (appDetailArr[2].equals(submissionCount)) {
				appValidSubmissionCount++;
			} else {
				appsInvalidSubmissionCount += appDetailArr[0] + " :" + submissionCount + ",";
			}
			appDetailPage.appsButtonClick();
		}
		if (appsDetailArray.length == appValidSubmissionCount) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct submission counts");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Incorrect submission counts " + appsInvalidSubmissionCount);
		}

		org.testng.Assert.assertTrue(appsDetailArray.length == appValidSubmissionCount, "Incorrect submission counts");

		/*
		 * } catch(Exception e) {
		 * LOGGER.error("Error while executing test: ",e);
		 * ReportManager.lognew(testContext.getName(), testdescription,
		 * LogStatus.FAIL, "Step " + step + "." + (++i) +
		 * ": Incorrect submission counts "+appsInvalidSubmissionCount); }
		 */

	}

	@Parameters({ "step", "testdescription" })
	public void verifyAppNames(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;

		loginTest.verifyValidLogin1(step, testdescription, parameters.get("admin_username"),
				parameters.get("admin_password"), testContext);

		AppsPage appsPage = new AppsPage(driver);
		appsPage.appsButtonClick();
		String appsName = parameters.get("apps_name");
		String appsNotPresent = "";
		int appExistCount = 0;

		/* try { */
		String appsNameArray[] = appsName.split(",");

		for (String appName : appsNameArray) {
			if (appsPage.appExists(appName)) {
				appExistCount++;
			} else {
				appsNotPresent += appName + ",";
			}
		}

		if (appsNameArray.length == appExistCount) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct apps display under App Name " + appsName);

		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Apps do not display under App Name " + appsNotPresent);
		}

		org.testng.Assert.assertTrue(appsNameArray.length == appExistCount, "Apps do not display under App Name");

		/*
		 * } catch(Exception e) { ReportManager.lognew(testContext.getName(),
		 * testdescription, LogStatus.FAIL, "Step " + step + "." + (++i) +
		 * ":  Apps do not display under App Name "+appsNotPresent); }
		 */

	}

	@Parameters({ "step", "testdescription" })
	public void verifyExportIcons(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;
		SubmissionsPage submissionsPage = new SubmissionsPage(driver);
		submissionsPage.submissionsButtonClick();
		String appsName = parameters.get("apps_name");
		String exportNotPresent = "";
		int exportExistCount = 0;

		/*
		 * try {
		 */
		String appsNameArray[] = appsName.split(",");

		for (String appName : appsNameArray) {
			if (submissionsPage.exportIconExists(appName)) {
				exportExistCount++;
			} else {
				exportNotPresent += appName + ",";
			}
		}

		if (appsNameArray.length == exportExistCount) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct export icons display under app name " + appsName);

		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, "Step " + step + "." + (++i)
					+ ": Incorrect export icons display under app name " + exportNotPresent);
		}

		org.testng.Assert.assertTrue(appsNameArray.length == exportExistCount,
				"Incorrect export icons display under app name");

		/*
		 * } catch(Exception e) { ReportManager.lognew(testContext.getName(),
		 * testdescription, LogStatus.FAIL, "Step " + step + "." + (++i) +
		 * ": Incorrect export icons display under app name "+exportNotPresent);
		 * }
		 * 
		 */

	}

}
