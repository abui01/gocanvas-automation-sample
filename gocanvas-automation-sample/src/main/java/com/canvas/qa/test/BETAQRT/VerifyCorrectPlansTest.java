package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.BillingOptionsPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.profile.AddUsersPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.profile.UsersPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

public class VerifyCorrectPlansTest extends BrowserLaunchTest {
	
	@Test
	@Parameters({ "testdescription" })
	public void VerifyCorrectPlansExpiredPlans(String step, ITestContext testContext) throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		//step 1 verifyStartUp	
		
		int i = 0;

		CanvasLoginTest loginTest = new CanvasLoginTest();
		step = "1";
		loginTest.verifyValidLogin1(step, parameters.get("startup_admin_username"),
				parameters.get("startup_admin_password"), testContext);

		i += 3;
		

		UsersPage usersLink = new UsersPage(driver);
		usersLink.usersButtonClick();

		String[] users = parameters.get("startup_users").split(";");
		String[] plans = parameters.get("startup_plans").split(";");
		
		//check if post-conditions weren't met:
		DashboardPage dashboardPage = new DashboardPage(driver);
		LoginPage login = new LoginPage(driver);
		for (int u = 0; u < users.length; u++) {
			if (!usersLink.getPlan(users[u]).contains(plans[u])) {
				//log in as admin and turn off grandfathered plan if post-conditions weren't met
				dashboardPage.clickLogOut();
				login.typeusername(parameters.get("username"));
				login.typepassword(parameters.get("password"));
				login.Clickonloginbutton();
				
				//reset back to normal plan
				BillingOptionsPage billOptLink = new BillingOptionsPage(driver);
				billOptLink.gotoUser(parameters.get("startup_admin_username"));
				billOptLink.billingOptsClick();
				billOptLink.editGrandfather();
				billOptLink.checkLegacy(false);
				billOptLink.saveClick();
				
				//log back in as normal user:
				dashboardPage.clickLogOut();
				login.typeusername(parameters.get("startup_admin_username"));
				login.typepassword(parameters.get("startup_admin_password"));
				login.Clickonloginbutton();
				usersLink.usersButtonClick();
				
				//change plan back to normal pricing
				usersLink.changeUserPlan(parameters.get("startupUser"));
				usersLink.selectMonthly();
				usersLink.purchaseClick();
				usersLink.usersButtonClick();
			}
		}
		//end check point
		
		boolean correctPlans = true;
		for (int u = 0; u < users.length; u++) {
			if (!usersLink.getPlan(users[u]).contains(plans[u])) {
				correctPlans = false;
			}
		}

		if (correctPlans) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Startup Plans appear correctly");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": There is a plan within the list that does not contain 'Startup'");
		}
		org.testng.Assert.assertTrue(correctPlans, "Correct plan is false");;
		usersLink.logout();

	//step 2 dashboardExpiredTrial
		
		i = 0;
		
		loginTest = new CanvasLoginTest();
		step = "2";
		loginTest.verifyValidLogin1(step, parameters.get("trial_username"), parameters.get("trial_password"),
				testContext);

		
		i += 3;
		
		    usersLink = new UsersPage(driver);
		if (usersLink.getTrialBanner().contains("Upgrade Plan")) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Upgrade Plan appears in nav bar");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Upgrade Plan does not appear in nav bar");
		}
		org.testng.Assert.assertTrue(usersLink.getTrialBanner().contains("Upgrade Plan"));


	//step 3 myAccountExpiredTrial

		i = 0;
		step = "3";
		usersLink = new UsersPage(driver);
		usersLink.usersButtonClick();
		
		if (usersLink.getUserCount() == 1) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": User count is one.");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": User count is not one.");
		}
		org.testng.Assert.assertTrue(usersLink.getUserCount() == 1);

		if (usersLink.verifyPlanName("Individual")) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Trial Expired appears for all users");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Trial Expired does not appear for all users");
		}
		org.testng.Assert.assertTrue(usersLink.verifyPlanName("Individual"));
		usersLink.logout();


	//step 4 legacyPricingStartup  very important
		
		i=0;
		loginTest = new CanvasLoginTest();
		step = "4";
		loginTest.verifyValidLogin1(step, parameters.get("username"), parameters.get("password"), testContext);

		
		i += 3;

		BillingOptionsPage billOptLink = new BillingOptionsPage(driver);
		billOptLink.gotoUser(parameters.get("startup_admin_username"));
		billOptLink.billingOptsClick();
		billOptLink.editGrandfather();
		billOptLink.checkLegacy(true);
		billOptLink.saveClick();

		AddUsersPage addUsersPage = new AddUsersPage(driver);
		addUsersPage.usersButtonClick();
		addUsersPage.addUsersButtonClick();
		
		/** commenting out now that we incorporated global checkbox checker
		//if post conditions weren't met:
		if (!addUsersPage.getAnnualAmount().contains(parameters.get("legacy_annual"))
				&& !addUsersPage.getAnnualAmount().contains(parameters.get("legacy_annual2"))) {
			billOptLink.billingOptsClick();
			billOptLink.editGrandfather();
			billOptLink.checkLegacy(true);
			billOptLink.saveClick();
			
			addUsersPage.usersButtonClick();
			addUsersPage.addUsersButtonClick();
		}
		//end-check
		 **/
		
		boolean status = addUsersPage.getAnnualAmount().contains(parameters.get("legacy_annual"))
				&& addUsersPage.getAnnualAmount().contains(parameters.get("legacy_annual2"));
		
		if (status) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Startup Annual Legacy Rate is correct");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Startup Annual Legacy Rate is incorrect");
		}
		org.testng.Assert.assertTrue(status);
		if (addUsersPage.getMonthlyAmount().contains(parameters.get("legacy_monthly"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Startup Monthly Legacy Rate is correct");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Startup Monthly Legacy Rate is incorrect");
		}

		org.testng.Assert.assertTrue(addUsersPage.getMonthlyAmount().contains(parameters.get("legacy_monthly")));
		billOptLink.resumeSession();
	

	//step 5 legacyPricingBusProf very important 

		i = 0;
		step = "5";
		billOptLink = new BillingOptionsPage(driver);
		billOptLink.gotoUser(parameters.get("business_admin_username"));
		billOptLink.billingOptsClick();
		billOptLink.editGrandfather();
		billOptLink.checkLegacy(true);
		billOptLink.saveClick();

		addUsersPage = new AddUsersPage(driver);
		addUsersPage.usersButtonClick();
		addUsersPage.addUsersButtonClick();
		
		/** commenting out now that we incorporated global checkbox checker
		//if post conditions weren't met:
		if (!addUsersPage.getAnnualAmount().contains(parameters.get("legacy_annual"))
				&& !addUsersPage.getAnnualAmount().contains(parameters.get("legacy_annual2"))) {
			billOptLink.billingOptsClick();
			billOptLink.editGrandfather();
			billOptLink.checkLegacy(true);
			billOptLink.saveClick();
			
			addUsersPage.usersButtonClick();
			addUsersPage.addUsersButtonClick();
		}
		//end-check
		 **/
		
		status = addUsersPage.getAnnualAmount().contains(parameters.get("legacy_annual"))
				&& addUsersPage.getAnnualAmount().contains(parameters.get("legacy_annual2"));
		
		if (status) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Busniess Annual Legacy Rate is correct");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Busniess Annual Legacy Rate is incorrect");
		}
		org.testng.Assert.assertTrue(status);
		if (addUsersPage.getMonthlyAmount().contains(parameters.get("legacy_monthly"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Business Monthly Legacy Rate is correct");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Business Monthly Legacy Rate is incorrect");
		}
		org.testng.Assert.assertTrue(addUsersPage.getMonthlyAmount().contains(parameters.get("legacy_monthly")));
		billOptLink.resumeSession();

		billOptLink.gotoUser(parameters.get("prof_admin_username"));
		billOptLink.billingOptsClick();
		billOptLink.editGrandfather();
		billOptLink.checkLegacy(true);
		billOptLink.saveClick();

		addUsersPage.usersButtonClick();
		addUsersPage.addUsersButtonClick();
		/** commenting out now that we incorporated global checkbox checker
		//if post conditions weren't met:
		if (!addUsersPage.getAnnualAmount().contains(parameters.get("legacy_annual"))
				&& !addUsersPage.getAnnualAmount().contains(parameters.get("legacy_annual2"))) {
			billOptLink.billingOptsClick();
			billOptLink.editGrandfather();
			billOptLink.checkLegacy(true);
			billOptLink.saveClick();
			
			addUsersPage.usersButtonClick();
			addUsersPage.addUsersButtonClick();
		}
		//end-check
		 **/
		status = addUsersPage.getAnnualAmount().contains(parameters.get("legacy_annual"))
				&& addUsersPage.getAnnualAmount().contains(parameters.get("legacy_annual2"));
		if (status) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Professional Annual Legacy Rate is correct");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Professional Annual Legacy Rate is incorrect");
		}
		org.testng.Assert.assertTrue(status);
		if (addUsersPage.getMonthlyAmount().contains(parameters.get("legacy_monthly"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Professional Monthly Legacy Rate is correct");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." +(++i) + ": Professional Monthly Legacy Rate is incorrect");
		}
		org.testng.Assert.assertTrue(addUsersPage.getMonthlyAmount().contains(parameters.get("legacy_monthly")));
		billOptLink.resumeSession();
	

	//step 6 postConditions  extra importatnt 
		step = "6";
		//billOptLink = new BillingOptionsPage(driver);
		billOptLink.gotoUser(parameters.get("startup_admin_username"));
		billOptLink.billingOptsClick();
		billOptLink.editGrandfather();
		billOptLink.checkLegacy(false);
		billOptLink.saveClick();

		billOptLink.resumeSession();

		billOptLink.gotoUser(parameters.get("business_admin_username"));
		billOptLink.billingOptsClick();
		billOptLink.editGrandfather();
		billOptLink.checkLegacy(false);
		billOptLink.saveClick();

		billOptLink.resumeSession();

		billOptLink.gotoUser(parameters.get("prof_admin_username"));
		billOptLink.billingOptsClick();
		billOptLink.editGrandfather();
		billOptLink.checkLegacy(false);
		billOptLink.saveClick();
		ReportManager.log(testContext.getName(), LogStatus.PASS, "Step " + step + ": Post Conditions fulfilled");
	}
}
