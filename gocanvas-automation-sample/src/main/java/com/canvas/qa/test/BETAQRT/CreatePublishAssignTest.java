package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.PublishAppPage;
import com.canvas.qa.pages.apps.AppBuilderPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author anna.marek
 *
 */
@Test
public class CreatePublishAssignTest extends BrowserLaunchTest {
	@Parameters({ "step", "testdescription" })
	public void correctUsers(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;

		loginTest.verifyValidLoginnew(step, testdescription, parameters.get("admin_username"),
				parameters.get("admin_password"), testContext);

		i += 3;

		CreateAppPage createAppLink = new CreateAppPage(driver);
		AppBuilderPage appBuilder = new AppBuilderPage(driver);

		createAppLink.appsButtonClick();

		createAppLink.newApp();
		createAppLink.startClick();
		createAppLink.loadAppBuilder();
		createAppLink.setAppName(parameters.get("appName"));
		createAppLink.buildBasicApp();
		// createAppLink.saveAndPublish();
		appBuilder.clickSaveButton(parameters.get("appName"),parameters.get("admin_username"),parameters.get("admin_password"));
		appBuilder.clickPublishToDeviceButton();
		createAppLink.nextButtonClick();

		
		String[] groups = parameters.get("groups").split(";");
		if (createAppLink.verifyGroups(groups)) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": All account groups appear");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": All account groups appear");
		}
		org.testng.Assert.assertTrue(createAppLink.verifyGroups(groups), "All account groups appear");
		
		createAppLink.nextButtonInUserScreenClick();
		
		String[] users = parameters.get("users").split(";");
		if (createAppLink.verifyUsers(users)) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": All account users appear");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": All account users appear");
		}
		org.testng.Assert.assertTrue(createAppLink.verifyUsers(users), " All account users appear");

	}

	@Parameters({ "step", "testdescription" })
	public void featureOptions(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;
		AppBuilderPage appBuilder = new AppBuilderPage(driver);
		CreateAppPage createAppLink = new CreateAppPage(driver);
		createAppLink.setAppName(parameters.get("appName"));
		// createAppLink.saveAndPublish();
		appBuilder.clickSaveButton(parameters.get("appName"),parameters.get("designer_username"),parameters.get("designer_password"));
		PublishAppPage publishAppPage = appBuilder.clickPublishToDeviceButton();

		String[] feats = parameters.get("features").split(";");
		for (i = 0; i < feats.length; i++) {
			boolean status = createAppLink.verifyFeats(feats[i]);
			if (status) {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
						"Step " + step + "." + (++i) + ": Enable Features display");
			} else {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": Enable Features display");
			}
			org.testng.Assert.assertTrue(status, "Enable Features display");
		}
		publishAppPage.clickNextPublishButton();
		createAppLink.nextButtonInUserScreenClick();
		publishAppPage.clickPublishButton();
		appBuilder.clickCloseButton();
		createAppLink.logout1();
	}

	@Parameters({ "step", "testdescription" })
	public void prepopulatedScreens(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		CreateAppPage createAppLink = new CreateAppPage(driver);
		createAppLink.selectTemplate(parameters.get("testTemplate"));
		createAppLink.startClick();
		createAppLink.loadAppBuilder();

		String[] simpleScreens = parameters.get("screensSimple").split(";");
		String[] simpleScreenFieldCounts = parameters.get("screensSimpleFieldCounts").split(";");
		String[] parentLoopScreens = parameters.get("screensLoopParents").split(";");
		String[] parentLoopScreenFieldCounts = parameters.get("screensLoopParentsFieldCounts").split(";");
		String[] childLoopScreens = parameters.get("screensLoopChilds").split(";");
		String[] childLoopScreenFieldCounts = parameters.get("screensLoopChildsFieldCounts").split(";");

		if (createAppLink.verifyScreens(simpleScreens, parentLoopScreens, childLoopScreens)) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": All Screens appear");

			if (createAppLink.verifySimpFieldCounts(simpleScreens, simpleScreenFieldCounts)
					&& createAppLink.verifyParLoopFieldCounts(parentLoopScreens, parentLoopScreenFieldCounts)
					&& createAppLink.verifyChildLoopFieldCounts(childLoopScreens, childLoopScreenFieldCounts)) {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
						"Step " + step + "." + (++i) + ": All Fields appear");
			} else {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": All Fields appear");
			}

			org.testng.Assert.assertTrue(
					createAppLink.verifySimpFieldCounts(simpleScreens, simpleScreenFieldCounts)
					&& createAppLink.verifyParLoopFieldCounts(parentLoopScreens, parentLoopScreenFieldCounts)
					&& createAppLink.verifyChildLoopFieldCounts(childLoopScreens, childLoopScreenFieldCounts),
					"All fields appaer");

		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": All Screens appear");
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": All Fields appear");
		}

		org.testng.Assert.assertTrue(createAppLink.verifyScreens(simpleScreens, parentLoopScreens, childLoopScreens),
				"All screen and fields appear");
		;

	}

	@Parameters({ "step", "testdescription" })
	public void publishMessage(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		CreateAppPage createAppLink = new CreateAppPage(driver);

		createAppLink.finalPublishButtonClick();
		createAppLink.clickCloseButton();
		boolean status = parameters.get("publishMsg").contains(createAppLink.getToastMsg()) || parameters.get("publishMsg2").contains(createAppLink.getToastMsg());
		if (status) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Publish message appears");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Publish message appears");
		}
		org.testng.Assert.assertTrue(status,"Publish message appears");
	}

	@Parameters({ "step", "testdescription" })
	public void templateScreen(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;

		loginTest.verifyValidLogin1(step, testdescription, parameters.get("designer_username"),
				parameters.get("designer_password"), testContext);

		i += 3;

		CreateAppPage createAppLink = new CreateAppPage(driver);
		createAppLink.appsButtonClick();
		createAppLink.newApp();

		if (createAppLink.getHeader().equals(parameters.get("templateHeader"))) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Choose a Template appears.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Choose a Template appears.");
		}
		org.testng.Assert.assertTrue(createAppLink.getHeader().equals(parameters.get("templateHeader")),
				"Choose a Template appears.");

		String[] templates = parameters.get("templates").split(";");
		if (createAppLink.verifyTemplates(templates)) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": All Templates appear");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": All Templates appear");
		}
		org.testng.Assert.assertTrue(createAppLink.verifyTemplates(templates), "All templates appear");
	}
}
