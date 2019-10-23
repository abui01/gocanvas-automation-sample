package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DownloadAndPdfViewPage;
import com.canvas.qa.pages.apps.NoAccessPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class NoAccessTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void verifyNoAccess(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {

		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username1"), parameters.get("password1"),
					"Login as the company admin : " + parameters.get("username1"), testContext, testDescription);

			NoAccessPage enableDisable = new NoAccessPage(driver);
			DownloadAndPdfViewPage clickOnLogoutLink = new DownloadAndPdfViewPage(driver);
			CreateAppPage createapp = new CreateAppPage(driver);
			LoginPage login = new LoginPage(driver);

			boolean defaultNoAccessLink1 = enableDisable.verifyDispatchAndWorkflowLinks();
			reportLog(!defaultNoAccessLink1, testContext.getName(),
					"Verify Dispatch, Workflow & Dispatch do not appear for company admin.", "1.2",
					"Dispatch, Workflow & Dispatch do not appear.");
			org.testng.Assert.assertFalse(defaultNoAccessLink1);

			clickOnLogoutLink.logout();
			boolean logoutMessage1 = createapp.afterlogout(parameters.get("logOut_msg"));
			reportLog(logoutMessage1, testContext.getName(), "Click on logout button in create app page", "2.1",
					"User Logout from the Application");
			org.testng.Assert.assertTrue(logoutMessage1);

			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbutton();

			boolean appNameText_2 = enableDisable.pageNameAfterlogin1(parameters.get("app_name1"));
			reportLog(appNameText_2, testContext.getName(), "Login as the company reporter:", "2.2",
					"SignIn successfull");
			org.testng.Assert.assertTrue(appNameText_2, "SignIn successfull");

			boolean defaultNoAccessLink2 = enableDisable.verifyDispatchAndWorkflowLinks();
			reportLog(!defaultNoAccessLink2, testContext.getName(),
					"Verify Dispatch, Workflow & Dispatch do not appear for company reporter.", "2.3",
					"Dispatch, Workflow & Dispatch do not appear.");
			org.testng.Assert.assertFalse(defaultNoAccessLink2);

			clickOnLogoutLink.logout();
			boolean logoutMessage2 = createapp.afterlogout(parameters.get("logOut_msg"));
			reportLog(logoutMessage2, testContext.getName(), "Click on logout button in create app page", "3.1",
					"User Logout from the Application");
			org.testng.Assert.assertTrue(logoutMessage2);

			login.typeusername(parameters.get("username3"));
			login.typepassword(parameters.get("password3"));
			login.Clickonloginbutton();

			boolean appNameText_3 = enableDisable.pageNameAfterlogin2(parameters.get("app_name2"));
			reportLog(appNameText_3, testContext.getName(), "Login as the company user:", "3.2", "SignIn successfull");
			org.testng.Assert.assertTrue(appNameText_3, "SignIn successfull");

			boolean defaultNoAccessLink3 = enableDisable.verifyDispatchAndWorkflowLinks();
			reportLog(!defaultNoAccessLink3, testContext.getName(),
					"Verify Dispatch, Workflow & Dispatch do not appear for company user.", "3.3",
					"Dispatch, Workflow & Dispatch do not appear.");
			org.testng.Assert.assertFalse(defaultNoAccessLink3);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
