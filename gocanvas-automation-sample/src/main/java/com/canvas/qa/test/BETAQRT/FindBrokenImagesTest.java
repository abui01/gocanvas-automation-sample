package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.PublishAppPage;
import com.canvas.qa.pages.apps.AppBuilderPage;
import com.canvas.qa.pages.apps.PDFDesignerLayoutPage;
import com.canvas.qa.pages.apps.PDFDesignerPage;
import com.canvas.qa.pages.apps.ShowPDFOptionsPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

/**
 * 
 * @author yuan.gao Test Case 'verifyImgsResponseWhenCreateAppAndPDF' Checking
 *         the image status. Fouce on static image creation and PDF submission
 */
public class FindBrokenImagesTest extends BrowserLaunchTest {

	// GOC-5557 Verify All Pages Imgs
	@Test
	@Parameters({ "testdescription" })
	public void verifyImgsResponseWhenCreateAppAndPDF(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {

			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);

			boolean status = dashboardPage.validateImgDisplayed(driver);
			reportLog(status, testContext.getName(), "Validate imgs response on home page", "1.2",
					"All imgs response code are 200");

			PublishAppPage publishAppPage = new PublishAppPage(driver);
			CreateAppPage createAppPage = new CreateAppPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			AppBuilderPage appBuilderPage = new AppBuilderPage(driver);
			ShowPDFOptionsPage PDFOptionsPage = new ShowPDFOptionsPage(driver);
			PDFDesignerPage pdfDesignerPage = new PDFDesignerPage(driver);
			PDFDesignerLayoutPage pdfDesignerLayoutPage = new PDFDesignerLayoutPage(driver);
			dashboardPage.clickApp();

			status = dashboardPage.validateImgDisplayed(driver);
			reportLog(status, testContext.getName(), "Validate dashBoard Page imgs response", "2.0",
					"Imgs on dashBoard Page response code are 200");

			createAppPage.clickAppCreateButton();
			status = createAppPage.validateImgDisplayed(driver);
			reportLog(status, testContext.getName(), "Validate template imgs response", "3.0",
					"Imgs on choose template page response 200");

			appBuilderPage.createApp(appName, testContext, "4");
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels1").split(";"),
					parameters.get("first_screen_field1").split(";"));

			// Upload Static Image in FIRST Screen
			appBuilderPage = appBuilderPage.clickOnAddStaticImage(parameters.get("first_screen_labels2").split(";"),
					parameters.get("first_screen_field2").split(";"));
			appBuilderPage.selecRandomtExistingRefImage();

			appBuilderPage = appBuilderPage.enterStaticPhotoLabel(parameters.get("first_screen_labels2").split(";"),
					parameters.get("first_screen_field2").split(";"), parameters.get("first_screen_field3").split(";"));

			appBuilderPage = appBuilderPage.clickOnAddStaticImage(parameters.get("first_screen_labels3").split(";"),
					parameters.get("first_screen_field2").split(";"));
			appBuilderPage.selecRandomtExistingRefImage();

			appBuilderPage = appBuilderPage.enterStaticPhotoLabel(parameters.get("first_screen_labels3").split(";"),
					parameters.get("first_screen_field2").split(";"), parameters.get("first_screen_field3").split(";"));

			status = appBuilderPage.validateStaticImgDisplayed();
			reportLog(status, testContext.getName(), "Validate static Imgs response and dispalyed", "5.0",
					"Static Image response in Screen 1 are 200");

			// Change Static image and verify image response
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLabelField(parameters.get("first_screen_labels2"));
			appBuilderPage.clickChangeImage();
			appBuilderPage.selecRandomtExistingRefImage();
			status = appBuilderPage.validateStaticImgDisplayed();
			reportLog(status, testContext.getName(), "Validate static Imgs response and dispalyed", "6.0",
					"After change static image All images response in Screen 1 are 200");

			// Second Screen
			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("second_screen_place_holder"),
					parameters.get("second_screen_name"), parameters.get("second_screen_labels").split(";"),
					parameters.get("second_screen_field").split(";"));

			appBuilderPage = appBuilderPage.clickOnAddStaticImage(parameters.get("first_screen_field2").split(";"));
			appBuilderPage.selecRandomtExistingRefImage();

			appBuilderPage = appBuilderPage.enterStaticPhotoLabel(parameters.get("second_screen_label2").split(";"),
					parameters.get("second_screen_field2").split(";"),
					parameters.get("second_screen_field2").split(";"));

			// copy static image field
			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			appBuilderPage.copyScreenField(parameters.get("second_screen_label2"));
			int integer = appBuilderPage.getFieldCount(parameters.get("second_screen_label2"));
			status = (integer > 1);
			reportLog(status, testContext.getName(), "Verify copied field created.", "7.0",
					parameters.get("copy_field") + " : new field created successfully.");
			org.testng.Assert.assertTrue(status);

			status = appBuilderPage.validateImgDisplayed();
			reportLog(status, testContext.getName(), "Validate Image status code after copy field", "7.1",
					"After copy static image All images status code in Screen 2 are 200");

			// copy screen
			appBuilderPage.clickScreen(parameters.get("second_screen_name"));
			appBuilderPage.clickCopyScreenIcon();
			String copiedScreenName = parameters.get("second_screen_name") + " - copy";
			status = appBuilderPage.isScreenDisplayed(copiedScreenName);
			reportLog(status, testContext.getName(), "Verify copied screen created.", "8.0",
					copiedScreenName + " : screen created successfully.");
			// org.testng.Assert.assertTrue(status);
			appBuilderPage.clickScreen(copiedScreenName);
			status = appBuilderPage.validateImgDisplayed();
			reportLog(status, testContext.getName(), "Validate Image status code after Copy Screen", "8.1",
					"After Copied All images response in Copy Screen are 200");

			// publish to devices
			appBuilderPage.clickPublishToDevice();
			publishAppPage.clickPublishToDeviceButton();
			// upload App Icon
			String workingDir = System.getProperty("user.dir");
			publishAppPage.updateAppIcon(workingDir, parameters.get("updateAppIcon_Path").split(";"));
			status = appBuilderPage.validateImgDisplayed();
			reportLog(status, testContext.getName(), "Validate Image status code After upload App Icon", "9.0",
					"All Image status code response are expected!");
			Assert.assertTrue(status);
			appBuilderPage.clickNextButton();
			publishAppPage.clickPublishButton();
			publishAppPage.clickCloseAppBuilderButton();

			// Verify All images on dashboard page
			status = appBuilderPage.validateImgDisplayed();
			reportLog(status, testContext.getName(), "Validate Image status code On DashBoard Page", "10.0",
					"All Image status code response are expected on DashBoard Page!");

			createAppPage.clickAppName(appName).clickPDFOptions();
			status = appBuilderPage.validateImgDisplayed();
			reportLog(status, testContext.getName(), "Validate Image status code on Settings Page", "11.0",
					"All Image status code response are expected on Settings Page");

			PDFOptionsPage.clickLaunchPDFDesignerButton();
			pdfDesignerPage.clickCreateALayout().clickStartButton();

			status = appBuilderPage.validateImgDisplayed();
			reportLog(status, testContext.getName(), "Validate Image status code on PDF Page", "12.0",
					"All Image status code response are expected on PDF page");

			// Preview PDF
			pdfDesignerLayoutPage.clickPreviewButton();
			status = pdfDesignerLayoutPage.isPDFFileDisplayed();
			reportLog(status, testContext.getName(), "User clicked on Preview Button", "13.0", "PDF file displayed");
			org.testng.Assert.assertTrue(status);

			status = appBuilderPage.validateImgDisplayed();
			reportLog(status, testContext.getName(), "Validate Image status code on preview PDF Page", "13.1",
					"All Image status code response are expected on preview PDF page");
			pdfDesignerLayoutPage.clickBackToGoCanvas();
			status = appBuilderPage.validateImgDisplayed();
			reportLog(status, testContext.getName(), "Validate Image status code on PDF Designer Page", "14.0",
					"All Image status code response are expected on preview PDF Designer Page");
			pdfDesignerLayoutPage.clickCloseButton();
			createAppPage = pdfDesignerLayoutPage.clickYesButton();
			status = createAppPage.isAppCreateButtonDisplayed();
			reportLog(status, testContext.getName(), "User clicked on close button in PDF designer", "14.1",
					"App page is displayed.");
			org.testng.Assert.assertTrue(status);

			status = appBuilderPage.validateImgDisplayed();
			reportLog(status, testContext.getName(), "Validate Image status code on Apps Page", "14.2",
					"All Image status code response are expected on Apps Page");

			createAppPage.clickAppName(appName).destroyApp();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}
}
