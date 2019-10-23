package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.SearchUsersPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.profile.UsersPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class CompanyPlanTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void billingCycleTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickAccount();
			UsersPage usersPage = dashboardPage.clickUsers();
			String[] userList = parameters.get("user_list").split(";");
			for (int i = 0; i < userList.length; i++) {
				boolean status = usersPage.isEmailDisplayed(userList[i]);
				reportLog(status, testContext.getName(), "Verify user displayed: " + userList[i], "1.1." + i,
						userList[i] + " is displayed.");
				org.testng.Assert.assertTrue(status);
			}

			LoginPage login = dashboardPage.clickLogOut();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			if (login.ifTermsOfUseDispalyed()) {
				login.clickTermsOfUseCheckbox();
				login.typepassword(parameters.get("password2"));
				login.Clickonloginbutton();
			}

			boolean status = dashboardPage.isMenuDisplayed(parameters.get("menu"));
			reportLog(!status, testContext.getName(),
					"Verify " + parameters.get("menu") + " is not displayed for user: " + parameters.get("username2"),
					"2.0", parameters.get("menu") + " is not displayed for user: " + parameters.get("username2"));
			org.testng.Assert.assertFalse(status);

			login = dashboardPage.clickLogOut();
			login.typeusername(parameters.get("username3"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			if (login.ifTermsOfUseDispalyed()) {
				login.clickTermsOfUseCheckbox();
				login.typepassword(parameters.get("password2"));
				login.Clickonloginbutton();
			}

			dashboardPage.clickAccount();
			usersPage = dashboardPage.clickUsers();
			String[] userList2 = parameters.get("user_list2").split(";");
			String[] planList2 = parameters.get("plan_list2").split(";");
			String[] billList2 = parameters.get("nextbill_list2").split(";");
			for (int i = 0; i < userList2.length; i++) {
				status = usersPage.isEmailDisplayed(userList2[i]);
				reportLog(status, testContext.getName(), "Verify user displayed: " + userList2[i], "3." + i + ".1",
						userList2[i] + " is displayed.");
				org.testng.Assert.assertTrue(status);

				status = usersPage.getPlanForUser(userList2[i]).contains(planList2[i]);
				reportLog(status, testContext.getName(), "Verify plan displayed for: " + userList2[i], "3." + i + ".2",
						planList2[i] + " plan is displayed for: " + userList2[i]);
				org.testng.Assert.assertTrue(status);

				/*status = usersPage.getNextBillForUser(userList2[i]).contains(billList2[i]);
				reportLog(status, testContext.getName(), "Verify next bill displayed for: " + userList2[i],
						"3." + i + ".3", billList2[i] + " next bill is displayed for: " + userList2[i]);
				org.testng.Assert.assertTrue(status);*/
			}

			login = dashboardPage.clickLogOut();
			login.typeusername(parameters.get("admin"));
			login.typepassword(parameters.get("adminpwd"));
			login.Clickonloginbutton();
			SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
			searchUsersPage.enterEmail(parameters.get("username3"));
			searchUsersPage.clickSearch();
			dashboardPage = searchUsersPage.clickOnManage(parameters.get("username3"));
			dashboardPage.clickAccount();
			usersPage = dashboardPage.clickUsers();

			status = usersPage.getCompanyPlan().contains(parameters.get("company_plan"));
			reportLog(status, testContext.getName(),
					"Verify plan " + parameters.get("company_plan") + " is displayed for user: "
							+ parameters.get("username3"),
					"4.0",
					parameters.get("company_plan") + " plan is displayed for user: " + parameters.get("username3"));
			org.testng.Assert.assertTrue(status);

			status = usersPage.getBalance().contains(parameters.get("balance"));
			reportLog(status, testContext.getName(),
					"Verify balance " + parameters.get("balance") + " is displayed for user: "
							+ parameters.get("username3"),
					"4.1",
					parameters.get("balance") + " balance is displayed for user: " + parameters.get("username3"));
			org.testng.Assert.assertTrue(status);

			/*status = usersPage.getBillingCycle().contains(parameters.get("billing_cycle"));
			reportLog(status, testContext.getName(),
					"Verify billing cycle " + parameters.get("billing_cycle") + " is displayed for user: "
							+ parameters.get("username3"),
					"4.2", parameters.get("billing_cycle") + " billing cycle is displayed for user: "
							+ parameters.get("username3"));
			org.testng.Assert.assertTrue(status);*/

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

}
