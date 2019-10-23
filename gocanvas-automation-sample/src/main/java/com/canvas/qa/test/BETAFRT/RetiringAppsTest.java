package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DownloadAndPdfViewPage;
import com.canvas.qa.pages.apps.RetiringAppsPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class RetiringAppsTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void verifyRetireApps(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {

		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the Company user: " + parameters.get("username"), testContext, testDescription);

			RetiringAppsPage retireApps = new RetiringAppsPage(driver);
			LoginPage login = new LoginPage(driver);

			DownloadAndPdfViewPage LogoutFromApp = new DownloadAndPdfViewPage(driver);
			CreateAppPage createapp = new CreateAppPage(driver);

			boolean accessToAppPage = retireApps.accessToApppage();
			reportLog(!accessToAppPage, testContext.getName(),
					"Verify user does not have access to the Apps page in order to retire an app.  ", "1.2",
					"Company users don't have access to the Apps page.");
			org.testng.Assert.assertFalse(accessToAppPage);

			LogoutFromApp.logout();
			boolean logoutMessage1 = createapp.afterlogout(parameters.get("logOut_msg"));
			reportLog(logoutMessage1, testContext.getName(), "Click on logout button in create app page", "2.1",
					"User Logout from the Application");
			org.testng.Assert.assertTrue(logoutMessage1);

			login.typeusername(parameters.get("username1"));
			login.typepassword(parameters.get("password1"));
			login.Clickonloginbutton();

			boolean appNameText_2 = retireApps.companyAdminlogin(parameters.get("app_name"));
			reportLog(appNameText_2, testContext.getName(), "Login as the company admin:", "2.2", "SignIn successfull");
			org.testng.Assert.assertTrue(appNameText_2, "SignIn successfull");

			retireApps.verifyClickAppLink();

			retireApps.verifyRetireAppInFilter();
			if (createapp.getNumberOfAppInstances("Retire App") > 0) {
				createapp.clickQuickLink("Retire App");
				createapp.clickQuickLinkMenu("Retire App", "Un-Retire");
			}

			retireApps.verifyClickAppLink();
			boolean retireAppLink = retireApps.verifyRetireAppLinkInBottom();
			reportLog(retireAppLink, testContext.getName(), "Verify under Actions 'Retire App' icon displays.", "2.3",
					"Retire App icon displays successfully under Actions for a Company admin user.");
			org.testng.Assert.assertTrue(retireAppLink);

			boolean retireAppMsg = retireApps.verifyClickOnRetireAppLink(parameters.get("retire_msg"));
			reportLog(retireAppMsg, testContext.getName(), "Verify message after retiring the app.", "3.1",
					"Message 'Version 1 of the Retire App app has been retired and will no longer be downloaded to your client' is verfied");
			org.testng.Assert.assertTrue(retireAppMsg);

			retireApps.verifyClickAppLink();
			boolean retireAppIcon = retireApps.verifyRetirePendingAppIcon();
			reportLog(!retireAppIcon, testContext.getName(),
					"Verify 'Retire App' icon is grayed out for app 'Retire app pending.'", "4.1",
					"User cannot click Retire App.");
			org.testng.Assert.assertFalse(retireAppIcon);

			retireApps.verifyClickAppLink();
			boolean retireNewIcon = retireApps.verifyRetireNewAppIcon();
			reportLog(!retireNewIcon, testContext.getName(),
					"Verify 'Retire App' icon is grayed out for app 'Retire New'.", "5.1",
					"User cannot click Retire App.");
			org.testng.Assert.assertFalse(retireNewIcon);

			LogoutFromApp.logout();
			boolean logoutMessage2 = createapp.afterlogout(parameters.get("logOut_msg"));
			reportLog(logoutMessage2, testContext.getName(), "Click on logout button in create app page", "6.1",
					"User Logout from the Application");
			org.testng.Assert.assertTrue(logoutMessage2);

			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbutton();

			boolean appNameText_3 = retireApps.companydesignerlogin(parameters.get("app_name1"));
			reportLog(appNameText_3, testContext.getName(), "Login as the company designer:", "6.2",
					"SignIn successfull");
			org.testng.Assert.assertTrue(appNameText_3, "SignIn successfull");

			retireApps.verifyClickAppLink();
			retireApps.verifyRetireAppInFilter();
			boolean retireAppFilter = retireApps.verifyRetireAppInAppPage(parameters.get("app_staus"));
			reportLog(retireAppFilter, testContext.getName(), "Verify 'Retire App' displays", "6.3",
					"'Retire App' displays in Apps Page is verified");
			org.testng.Assert.assertTrue(retireAppFilter);
			retireApps.verifyUnretiretheApp();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
