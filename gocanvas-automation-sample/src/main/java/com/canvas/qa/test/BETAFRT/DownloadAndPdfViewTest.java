package com.canvas.qa.test.BETAFRT;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.DownloadAndPdfViewPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class DownloadAndPdfViewTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void verifyPdfPreview(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {

		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {

			String tmpFolderPath = parameters.get("file_path");
			String expectedFileName = parameters.get("file_name");
			String filepath1 = System.getProperty("user.dir") + tmpFolderPath + expectedFileName;

			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company designer: " + parameters.get("username"), testContext, testDescription);

			DashboardPage clickAppLink = new DashboardPage(driver);
			DownloadAndPdfViewPage clickOnLink = new DownloadAndPdfViewPage(driver);
			CreateAppPage createapp = new CreateAppPage(driver);
			LoginPage login = new LoginPage(driver);

			reportLog(true, testContext.getName(), "Custom Logo was displayed successfully on PDF preview.", "1.2",
					"We have not to automate  this step at present");

			clickAppLink.clickApp();
			createapp.clickAppName("Test App");
			clickOnLink.DeletePdfAndPreview(filepath1);

			boolean pdfDownloadButton = clickOnLink.pdfDownloadButtonVerify(parameters.get("button_name"));
			reportLog(pdfDownloadButton, testContext.getName(), "Verify download button ", "2.1",
					"PDF Download button is avilable.");
			org.testng.Assert.assertTrue(pdfDownloadButton);
			customWait(10);
			File file1 = new File(filepath1);
			boolean status = file1.exists();

			reportLog(status, testContext.getName(), "Verify Pdf download or not ", "2.2",
					"User was able to Download PDF successfully.");
			org.testng.Assert.assertTrue(status);

			clickOnLink.pdfClose();
			clickOnLink.logout();
			boolean logoutMessage = createapp.afterlogout(parameters.get("logOut_msg"));
			reportLog(logoutMessage, testContext.getName(), "Click on logout button in create app page", "2.3",
					"User Logout from the Application");
			org.testng.Assert.assertTrue(logoutMessage);

			login.typeusername(parameters.get("user1"));
			login.typepassword(parameters.get("password1"));
			login.Clickonloginbutton();

			boolean appNameText_2 = createapp.pdfDownloadAfterlogin(parameters.get("app_name"));
			reportLog(appNameText_2, testContext.getName(), "Login as the company Designer:", "3.1",
					"SignIn successfull");
			org.testng.Assert.assertTrue(appNameText_2, "SignIn successfull");
			createapp.clickAppName("Test App");

			boolean appName = clickOnLink.verifyAppNameInPreview(parameters.get("file2"));
			reportLog(appName, testContext.getName(), "Verify user can view PDF. ", "3.2",
					"Pdf Preview open,user can view the PDF and App name is verfied in generated pdf preview");
			org.testng.Assert.assertTrue(appName);

			// boolean toolTips =
			// clickOnLink.hoverOverPDFpreview(parameters.get("toolTips"));
			// reportLog(toolTips, testContext.getName(), "Verify tool tips on
			// hover over Pdf Preview link", "4.1","User can see,'View a blank
			// version of the PDF' while hovering mouse over the PDF Preview
			// link. ");
			// org.testng.Assert.assertTrue(toolTips);

			reportLog(true, testContext.getName(), "Hovering mouse over the PDF Preview link funcationlity removed ",
					"4.1" + "", "Hovering mouse over the PDF Preview link funcationlity removed");

			reportLog(true, testContext.getName(),
					"Verify the PDF preview says 'Add Your Logo' on the top left header ", "5.1" + "",
					"We have not to automate  this step at present");

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
