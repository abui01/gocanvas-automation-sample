package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.PublishAppPage;
import com.canvas.qa.pages.apps.AppBuilderPage;
import com.canvas.qa.pages.apps.AppsPage;
import com.canvas.qa.pages.apps.PDFDateFormatPage;
import com.canvas.qa.pages.apps.PDFDesignerPage;
import com.canvas.qa.pages.apps.PDFHeaderPage;
import com.canvas.qa.pages.apps.ShowPDFOptionsPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class PDFTests extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void verifyPDFOptions(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = new CreateAppPage(driver);
			AppBuilderPage appBuilder = new AppBuilderPage(driver);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			AppsPage appsPage = new AppsPage(driver);
			LoginPage loginPage = new LoginPage(driver);
			PDFDesignerPage pdfDesignerPage = new PDFDesignerPage(driver);
			ShowPDFOptionsPage showPDFOptionsPage = new ShowPDFOptionsPage(driver);
			PDFHeaderPage pdfHeaderPage = new PDFHeaderPage(driver);
			PDFDateFormatPage pdfDateFormatPage = new PDFDateFormatPage(driver);

			String appName = parameters.get("app_name") + randomAlphaNumeric(10);

			dashboardPage.clickApp();
			createAppPage.clickAppCreateButton();
			appBuilder.createApp(appName, testContext, "1.2");
			appBuilder.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));

			appBuilder.clickPublishToDeviceButton();
			if (appBuilder.isRenameYourAppDisplayed()) {
				appBuilder.clearAppName();
				appBuilder.enterAppName(appName);
				appBuilder.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));
				appBuilder.clickPublishToDeviceButton();
			}
			publishAppPage.clickNextButton();
			publishAppPage.clickPublishButton();
			appBuilder.clickCloseButton();
			createAppPage.clickAppName(appName);

			appsPage.clickPDFOptionsButton();
			boolean status = showPDFOptionsPage.verifyPDFOptionsTextIsDisplayed();
			reportLog(status, testContext.getName(), "Verify user lands on PDF Options page", "1.3",
					"PDF Options is available for " + appName + " app");
			org.testng.Assert.assertTrue(status);

			showPDFOptionsPage.clickLaunchPDFDesignerButton();

			boolean pdfDesignerPageLoaded = pdfDesignerPage.isTitleDisplayed();
			reportLog(pdfDesignerPageLoaded, testContext.getName(), "Verify user lands on PDF Designer page", "2.0",
					"PDF Designer Page opens");
			org.testng.Assert.assertTrue(pdfDesignerPageLoaded);

			driver.navigate().back();

			// Log out and Login as new user
			dashboardPage.clickLogOutButton();

			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password2"));
			loginPage.Clickonloginbutton();

			dashboardPage.clickApp();
			createAppPage.clickAppName(appName);
			appsPage.clickPDFOptionsButton();

			showPDFOptionsPage.uploadLogo();
			boolean logodisplayed = showPDFOptionsPage.verifyLogoIsDisplayed();
			reportLog(logodisplayed, testContext.getName(), "Verify uploaded logo displays on page", "3.0",
					"Logo successfully loaded");
			org.testng.Assert.assertTrue(logodisplayed);

			showPDFOptionsPage.clickEditPDFHeader();
			pdfHeaderPage.fillOutPDFHeader();

			boolean toastdisplayed = showPDFOptionsPage.verifyToastMsgIsDisplayed();
			reportLog(toastdisplayed, testContext.getName(),
					"Verify 'PDF Header' success toast message displays on page", "4.0",
					"Toast message successfully displayed.");
			org.testng.Assert.assertTrue(toastdisplayed);

			// log out and back in as bj
			dashboardPage.clickLogOutButton();

			loginPage.typeusername(parameters.get("username"));
			loginPage.typepassword(parameters.get("password"));
			loginPage.Clickonloginbutton();
			dashboardPage.clickApp();
			createAppPage.clickAppName(appName);
			appsPage.clickPDFOptionsButton();

			showPDFOptionsPage.clickEditPDFDateFormat();
			pdfDateFormatPage.fillOutPDFDateFormat();
			boolean toastdisplayed2 = showPDFOptionsPage.verifyToastMsgIsDisplayed();
			reportLog(toastdisplayed2, testContext.getName(),
					"Verify 'PDF Date Format' success toast message displays on page", "5.0",
					"Toast message successfully displayed.");
			org.testng.Assert.assertTrue(toastdisplayed2);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

}
