package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.profile.UserPermissionsBusinessPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class UserPermissionsProfessionalTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })

	public void UserPermissionsProfessional(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {

		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("company_admin_username"), parameters.get("company_admin_password"),
					"Login as the Company designer: " + parameters.get("company_admin_username"), testContext,
					testDescription);

			CreateAppPage createapp = new CreateAppPage(driver);
			LoginPage login = new LoginPage(driver);
			UserPermissionsBusinessPage dashboardLink = new UserPermissionsBusinessPage(driver);

			boolean valuesOfDashboardLink = dashboardLink.checkForDashboardLink(parameters.get("dashboard_link"));
			reportLog(valuesOfDashboardLink, testContext.getName(), "Verify Company Admin can access dashbaord link  ",
					"1.2", "Dashboard Link is verified for Company Admin");
			org.testng.Assert.assertTrue(valuesOfDashboardLink);

			boolean valuesOfAppLink = dashboardLink.checkForAppsLink(parameters.get("apps_link"));
			reportLog(valuesOfAppLink, testContext.getName(), "Verify Company Admin can access app link  ", "1.3",
					"App Link is verified for Company Admin");
			org.testng.Assert.assertTrue(valuesOfAppLink);

			boolean valuesOfSubmissionLink = dashboardLink.checkForSubmissonLink(parameters.get("submission_link"));
			reportLog(valuesOfSubmissionLink, testContext.getName(),
					"Verify Company Admin can access submission link  ", "1.4",
					"Submission Link is verified for Company Admin");
			org.testng.Assert.assertTrue(valuesOfSubmissionLink);

			boolean valuesOfWorkflowDispatchLink = dashboardLink
					.checkForWorkflowAndDispatcLink(parameters.get("workflow_Dispatch_link"));
			reportLog(valuesOfWorkflowDispatchLink, testContext.getName(),
					"Verify Company Admin can access Workflow and Dispatch link  ", "1.5",
					"Workflow and Dispatch Link is verified for Company Admin");
			org.testng.Assert.assertTrue(valuesOfWorkflowDispatchLink);

			boolean valuesOfRefImageLink = dashboardLink
					.checkForReferenceDataImagesLink(parameters.get("refimage_link"));
			reportLog(valuesOfRefImageLink, testContext.getName(),
					"Verify Company Admin can access Reference Data & Images  link  ", "1.6",
					"Reference Data & Images Link is verified for Company Admin");
			org.testng.Assert.assertTrue(valuesOfRefImageLink);

			String[] linksCA = new String[100];
			String availablelinks = parameters.get("links_Admin");
			linksCA = availablelinks.split(";");

			boolean success1 = dashboardLink.PremissonOnCAdminLinks(linksCA);
			reportLog(success1, testContext.getName(), "Verify Link under Account  ", "1.7",
					"Link under tab 'Account' is verified for Company Admin");
			org.testng.Assert.assertTrue(success1);

			boolean valuesOfProfileLink = dashboardLink.checkForProfileLink(parameters.get("profile_link"));
			reportLog(valuesOfProfileLink, testContext.getName(), "Verify Company Admin can access Profile link  ",
					"1.8", "Profile Link is verified for Company Admin");
			org.testng.Assert.assertTrue(valuesOfProfileLink);

			createapp.logout();
			login.typeusername(parameters.get("company_designer_username"));
			login.typepassword(parameters.get("company_designer_password"));
			login.Clickonloginbuttonforinvalidlogin();
			if (login.ifTermsOfUseDispalyed()) {
				login.clickTermsOfUseCheckbox();
				login.typepassword(parameters.get("company_designer_password"));
				login.Clickonloginbuttonforinvalidlogin();
			}

			boolean valuesOfCDDashboardLink = dashboardLink.checkForDashboardLink(parameters.get("dashboard_link"));
			reportLog(valuesOfCDDashboardLink, testContext.getName(), "Verify Designer can access dashbaord link  ",
					"2.1", "Dashboard Link is verified for Company Designer");
			org.testng.Assert.assertTrue(valuesOfCDDashboardLink);

			boolean valuesOfCDAppLink = dashboardLink.checkForAppsLink(parameters.get("apps_link"));
			reportLog(valuesOfCDAppLink, testContext.getName(), "Verify Designer can access app link  ", "2.2",
					"App Link is verified for Company Designer");
			org.testng.Assert.assertTrue(valuesOfCDAppLink);

			boolean valuesOfCDSubmissionLink = dashboardLink.checkForSubmissonLink(parameters.get("submission_link"));
			reportLog(valuesOfCDSubmissionLink, testContext.getName(), "Verify Designer can access submission link  ",
					"2.3", "Submission Link is verified for Company Designer");
			org.testng.Assert.assertTrue(valuesOfCDSubmissionLink);

			boolean valuesOfCDWorkflowDispatchLink = dashboardLink
					.checkForWorkflowAndDispatcLink(parameters.get("workflow_Dispatch_link"));
			reportLog(valuesOfCDWorkflowDispatchLink, testContext.getName(),
					"Verify Designer can access Workflow & Dispatch link  ", "2.4",
					"Workflow & Dispatch Link is verified for Company Designer");
			org.testng.Assert.assertTrue(valuesOfCDWorkflowDispatchLink);

			boolean valuesOfCDRefImageLink = dashboardLink
					.checkForReferenceDataImagesLink(parameters.get("refimage_link"));
			reportLog(valuesOfCDRefImageLink, testContext.getName(),
					"Verify Designer can access Reference Data & Images  link  ", "2.5",
					"Reference Data & Images Link is verified for Company Designer");
			org.testng.Assert.assertTrue(valuesOfCDRefImageLink);

			String[] reportCDLinks = new String[100];
			String availablereportlinks = parameters.get("reports_links_CDesigner");
			reportCDLinks = availablereportlinks.split(";");
			boolean success2 = dashboardLink.RetriveReportCDesignerLink(reportCDLinks);
			reportLog(success2, testContext.getName(), "Verify Link under report  ", "2.6",
					"Link under report is verfied for Company Designer");
			org.testng.Assert.assertTrue(success2);

			boolean valuesOfCDProfileLink = dashboardLink.checkForProfileLink(parameters.get("profile_link"));
			reportLog(valuesOfCDProfileLink, testContext.getName(), "Verify Designer can access Profile link  ", "2.7",
					"Profile Link is verified for Company Designer");
			org.testng.Assert.assertTrue(valuesOfCDProfileLink);

			boolean valuesOfCDAccountLink = dashboardLink.isAccountLinkDisplayed();
			reportLog(!valuesOfCDAccountLink, testContext.getName(),
					"Verify Company Designer can access Account link  ", "2.8",
					"Company Designer does not see the Account link");
			org.testng.Assert.assertFalse(valuesOfCDAccountLink);

			createapp.logout();
			login.typeusername(parameters.get("company_reporter_username"));
			login.typepassword(parameters.get("company_reporter_password"));
			login.Clickonloginbuttonforinvalidlogin();
			if (login.ifTermsOfUseDispalyed()) {
				login.clickTermsOfUseCheckbox();
				login.typepassword(parameters.get("company_reporter_password"));
				login.Clickonloginbuttonforinvalidlogin();
			}

			boolean valuesOfCRSubmissionLink = dashboardLink.checkForSubmissonLink(parameters.get("submission_link"));
			reportLog(valuesOfCRSubmissionLink, testContext.getName(), "Verify Reporter can access submission link  ",
					"3.1", "Submission Link is verified for Company Reporter");
			org.testng.Assert.assertTrue(valuesOfCRSubmissionLink);

			boolean valuesOfCRRefImageLink = dashboardLink
					.checkForReferenceDataImagesLink(parameters.get("refimage_link"));
			reportLog(valuesOfCRRefImageLink, testContext.getName(),
					"Verify Reporter can access Reference Data & Images  link  ", "3.2",
					"Reference Data & Images Link is verified for Company Reporter");
			org.testng.Assert.assertTrue(valuesOfCRRefImageLink);

			String[] reportCRLinks = new String[100];
			String availableCRreportlinks = parameters.get("reports_links_CReporter");
			reportCRLinks = availableCRreportlinks.split(";");

			boolean success3 = dashboardLink.RetriveReportCDesignerLink(reportCRLinks);
			reportLog(success2, testContext.getName(), "Verify Link under report  ", "3.3",
					"Link under report is verfied for Company Reporter");
			org.testng.Assert.assertTrue(success3);

			boolean valuesOfCRDdashboardLink = dashboardLink.checkForProfileLink(parameters.get("profile_link"));
			reportLog(valuesOfCRDdashboardLink, testContext.getName(), "Verify Reporter can access Profile link  ",
					"3.4", "Profile Link is verified for Company Reporter");
			org.testng.Assert.assertTrue(valuesOfCRDdashboardLink);

			boolean valuesOfCRAppLink = dashboardLink.isDashboardLinkDisplayed();
			reportLog(!valuesOfCRAppLink, testContext.getName(), "Verify Company Reporter can access Account link  ",
					"3.5", "Company Reporter does not see the Dashboard link");
			org.testng.Assert.assertFalse(valuesOfCRAppLink);

			boolean valuesOfCRAccountLink = dashboardLink.isAppLinkDisplayed();
			reportLog(!valuesOfCRAccountLink, testContext.getName(),
					"Verify Company Reporter can access Account link  ", "3.6",
					"Company Reporter does not see the App link");
			org.testng.Assert.assertFalse(valuesOfCRAccountLink);

			boolean valuesOfCRDispatchLink = dashboardLink.isDispatchLinkDisplayed();
			reportLog(!valuesOfCRDispatchLink, testContext.getName(),
					"Verify Company Reporter can access Workflow & Dispatch link  ", "3.7",
					"Company Reporter does not see the Workflow & Dispatch link");
			org.testng.Assert.assertFalse(valuesOfCRDispatchLink);

			boolean valuesOfCUAccountLink = dashboardLink.isAccountLinkDisplayed();
			reportLog(!valuesOfCUAccountLink, testContext.getName(),
					"Verify Company Reporter can access Account link  ", "3.8",
					"Company Reporter does not see the Account link");
			org.testng.Assert.assertFalse(valuesOfCUAccountLink);

			createapp.logout();
			login.typeusername(parameters.get("company_user_username"));
			login.typepassword(parameters.get("company_user_password"));
			login.Clickonloginbuttonforinvalidlogin();
			if (login.ifTermsOfUseDispalyed()) {
				login.clickTermsOfUseCheckbox();
				login.typepassword(parameters.get("company_user_password"));
				login.Clickonloginbuttonforinvalidlogin();
			}

			boolean editProfile = dashboardLink.updateRole();
			reportLog(!editProfile, testContext.getName(),
					" Verify the reporter cannot update the Role from Profile Link ", "4.1",
					"Company Reporter  Can't edit role");
			org.testng.Assert.assertFalse(editProfile);

			boolean valuesOfCUSubmissionLink = dashboardLink.checkForSubmissonLink(parameters.get("submission_link"));
			reportLog(valuesOfCUSubmissionLink, testContext.getName(),
					"Verify  Company user can access submission link  ", "5.1",
					"Submission Link is verified for Company User");
			org.testng.Assert.assertTrue(valuesOfCUSubmissionLink);

			boolean valuesOfCUProfileLink = dashboardLink.checkForProfileLink(parameters.get("profile_link"));
			reportLog(valuesOfCUProfileLink, testContext.getName(), "Verify  Company user can access Profile link  ",
					"5.2", "Profile Link is verified for Company User");
			org.testng.Assert.assertTrue(valuesOfCUProfileLink);

			boolean valuesOfCUdashboardLink = dashboardLink.isDashboardLinkDisplayed();
			reportLog(!valuesOfCUdashboardLink, testContext.getName(),
					"Verify Company user can access Dashboard link  ", "5.3",
					"Company User does not see the Dashboard link");
			org.testng.Assert.assertFalse(valuesOfCUdashboardLink);

			boolean valuesOfCUAppLink = dashboardLink.isAppLinkDisplayed();
			reportLog(!valuesOfCUAppLink, testContext.getName(), "Verify Company user can access App link  ", "5.4",
					"Company User does not see the App link");
			org.testng.Assert.assertFalse(valuesOfCUAppLink);

			boolean valuesOfCURefDataAndImageLink = dashboardLink.isRefAndImageLinkDisplayed();
			reportLog(!valuesOfCURefDataAndImageLink, testContext.getName(),
					"Verify Company user can access Reference data & image link  ", "5.5",
					"Company User does not see the Reference data & image link link");
			org.testng.Assert.assertFalse(valuesOfCURefDataAndImageLink);

			boolean valuesOfCUDispatchLink = dashboardLink.isDispatchLinkDisplayed();
			reportLog(!valuesOfCUDispatchLink, testContext.getName(),
					"Verify Company user can access Workflow & dispatch link ", "5.6",
					"Company User does not see the workflow & dispatch link");
			org.testng.Assert.assertFalse(valuesOfCUDispatchLink);

			boolean valuesOfCUReport = dashboardLink.isReportLinkDisplayed();
			reportLog(!valuesOfCUReport, testContext.getName(), "Verify Company user can access Report link  ", "5.7",
					"Company User does not see the report link");
			org.testng.Assert.assertFalse(valuesOfCUReport);

			boolean valuesOfCUAccountLink1 = dashboardLink.isAccountLinkDisplayed();
			reportLog(!valuesOfCUAccountLink1, testContext.getName(), "Verify Company user can access Account link  ",
					"5.8", "Company User does not see the Account link");
			org.testng.Assert.assertFalse(valuesOfCUAccountLink1);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
