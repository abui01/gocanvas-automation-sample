package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.BillingOptionsPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.profile.UsersPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author anna.marek
 *
 */


public class ChangePlanUserLevelTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void changePlansonUserlevel(String step, ITestContext testContext) throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		
		//step 1  openChangePlan

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;
		step = "1";

		loginTest.verifyValidLogin1(step, parameters.get("startupadmin_username"),
				parameters.get("startupadmin_password"), testContext);

		i += 3;

		UsersPage usersLink = new UsersPage(driver);
		usersLink.usersButtonClick();
		
	//------------------------------------------------------------------pre-condition checks
		String[] users = parameters.get("startup_users").split(";");
		String[] plans = parameters.get("startup_plans").split(";");
		
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
				billOptLink.gotoUser(parameters.get("startupadmin_username"));
				billOptLink.billingOptsClick();
				billOptLink.editGrandfather();
				billOptLink.checkLegacy(false);
				billOptLink.saveClick();
				
				//log back in as normal user:
				dashboardPage.clickLogOut();
				login.typeusername(parameters.get("startupadmin_username"));
				login.typepassword(parameters.get("startupadmin_password"));
				login.Clickonloginbutton();
				usersLink.usersButtonClick();
				
				//change plan back to normal pricing
				usersLink.changeUserPlan(parameters.get("startupUser"));
				usersLink.selectMonthly();
				usersLink.purchaseClick();
				usersLink.usersButtonClick();
			}
		}
		
	//------------------------------------------------------------------end pre-condition check
		usersLink.changeUserPlan(parameters.get("startupUser"));

		if (usersLink.getHeader().equals(parameters.get("changePlanHeader"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Change Plan screen appears");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Change Plan screen appears");
		}
		org.testng.Assert.assertTrue(usersLink.getHeader().equals(parameters.get("changePlanHeader")));
		
		// if post-conditions weren't met on previous failed run
		if (usersLink.annualSelected()) {
			usersLink.selectMonthly();
			usersLink.purchaseClick();
			usersLink.changeUserPlan(parameters.get("startupUser"));
		} else {
			//do nothing
		}
		
		if (usersLink.monthlySelected()) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Monthly Option selected");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Monthly Option selected");
		}
		org.testng.Assert.assertTrue(usersLink.monthlySelected());
	


	//step 2  cancelChangePlan
		
		i = 0;
		step = "2";
		
	    usersLink = new UsersPage(driver);
		usersLink.selectAnnual();
		usersLink.cancelClick();
		
		if (usersLink.getPlan(parameters.get("startupUser")).contains(parameters.get("monthlyStartupPlan"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Verify Cancel Purchase");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Verify Cancel Purchase");
		}
		org.testng.Assert.assertTrue(
				usersLink.getPlan(parameters.get("startupUser")).contains(parameters.get("monthlyStartupPlan")));
	

	    //step 3   startupChangePlan
		
		i = 0;
		step = "3";
	  
		
		usersLink = new UsersPage(driver);
		usersLink.changeUserPlan(parameters.get("startupUser"));
		usersLink.selectAnnual();

		if (usersLink.getPurchaseAmount().contains(parameters.get("step3payment"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Payment amount correct");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Payment amount correct");
		}
		org.testng.Assert.assertTrue(usersLink.getPurchaseAmount().contains(parameters.get("step3payment")));
		usersLink.purchaseClick();
		boolean status = usersLink.getMessage().equals(parameters.get("changeMsg"));
		if (status) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Verify successful purchase message");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Verify successful purchase message");
		}
		org.testng.Assert.assertTrue(status);
		
		if (usersLink.getPlan(parameters.get("startupUser")).contains(parameters.get("annualStartupPlan"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Verify purchase updates user table");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Verify purchase updates user table");
		}
		org.testng.Assert.assertTrue(
				usersLink.getPlan(parameters.get("startupUser")).contains(parameters.get("annualStartupPlan")));
		//DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickLogOut();
	

	    //step 4  profChangePlan

		i = 0;
		step = "4";
	   
		//dashboardPage = new DashboardPage(driver);
		//LoginPage login = new LoginPage(driver);
		login.typeusername(parameters.get("profadmin_username"));
		login.typepassword(parameters.get("profadmin_password"));
		login.Clickonloginbutton();
		// loginTest.verifyValidLoginnew(step,
		// parameters.get("profadmin_username"),
		// parameters.get("profadmin_username"), testContext);

		i += 3;

		usersLink = new UsersPage(driver);
		usersLink.usersButtonClick();
		usersLink.changeUserPlan(parameters.get("profUser"));
		usersLink.selectMonthly();

		if (usersLink.getPurchaseAmount().contains(parameters.get("step4payment"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Payment amount correct");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Payment amount correct");
		}
		org.testng.Assert.assertTrue(usersLink.getPurchaseAmount().contains(parameters.get("step4payment")));
		usersLink.purchaseClick();
		status = usersLink.getMessage().equals(parameters.get("changeMsg"));
		if (status) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Verify successful purchase message");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Verify successful purchase message");
		}
		org.testng.Assert.assertTrue(status);
		if (usersLink.getPlan(parameters.get("profUser")).contains(parameters.get("monthlyProfPlan"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Verify purchase updates user table");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Verify purchase updates user table");
		}
		org.testng.Assert
				.assertTrue(usersLink.getPlan(parameters.get("profUser")).contains(parameters.get("monthlyProfPlan")));

		dashboardPage.clickLogOut();
	
	//step 5  busChangePlan
		i = 0;
		step = "5";
		
	    loginTest = new CanvasLoginTest();
		dashboardPage = new DashboardPage(driver);
		login = new LoginPage(driver);
		login.typeusername(parameters.get("busadmin_username"));
		login.typepassword(parameters.get("busadmin_password"));
		login.Clickonloginbutton();
		// loginTest.verifyValidLoginnew(step,
		// parameters.get("busadmin_username"),
		// parameters.get("busadmin_password"), testContext);

		i += 3;

		usersLink = new UsersPage(driver);
		usersLink.usersButtonClick();
		usersLink.changeUserPlan(parameters.get("busUser"));
		usersLink.selectMonthly();

		if (usersLink.getPurchaseAmount().contains(parameters.get("step5payment"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Payment amount correct");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Payment amount correct");
		}
		org.testng.Assert.assertTrue(usersLink.getPurchaseAmount().contains(parameters.get("step5payment")));
		usersLink.purchaseClick();
		status = usersLink.getMessage().equals(parameters.get("changeMsg"));
		if (status) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Verify successful purchase message");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Verify successful purchase message");
		}
		org.testng.Assert.assertTrue(status);
		if (usersLink.getPlan(parameters.get("busUser")).contains(parameters.get("monthlyBusPlan"))) {
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Verify purchase updates user table");
		} else {
			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Verify purchase updates user table");
		}
		org.testng.Assert
				.assertTrue(usersLink.getPlan(parameters.get("busUser")).contains(parameters.get("monthlyBusPlan")));

		dashboardPage.clickLogOut();
	
	//step 6   postconditions
		
		i = 0;
		step = "6";

	    loginTest = new CanvasLoginTest();
		dashboardPage = new DashboardPage(driver);
		login = new LoginPage(driver);
		login.typeusername(parameters.get("startupadmin_username"));
		login.typepassword(parameters.get("startupadmin_password"));
		login.Clickonloginbutton();
		// loginTest.verifyValidLoginnew(step,
		// parameters.get("startupadmin_username"),
		// parameters.get("startupadmin_password"), testContext);

		i += 3;

		usersLink = new UsersPage(driver);
		usersLink.usersButtonClick();
		usersLink.changeUserPlan(parameters.get("startupUser"));
		usersLink.selectMonthly();
		usersLink.purchaseClick();

		dashboardPage.clickLogOut();

		login.typeusername(parameters.get("profadmin_username"));
		login.typepassword(parameters.get("profadmin_password"));
		login.Clickonloginbutton();
		// loginTest.verifyValidLoginnew(step,
		// parameters.get("profadmin_username"),
		// parameters.get("profadmin_password"), testContext);

		i += 3;

		usersLink.usersButtonClick();
		usersLink.changeUserPlan(parameters.get("profUser"));
		usersLink.selectAnnual();
		usersLink.purchaseClick();

		dashboardPage.clickLogOut();

		login.typeusername(parameters.get("busadmin_username"));
		login.typepassword(parameters.get("busadmin_password"));
		login.Clickonloginbutton();

		// loginTest.verifyValidLoginnew(step,
		// parameters.get("busadmin_username"),
		// parameters.get("busadmin_password"), testContext);

		i += 3;

		usersLink.usersButtonClick();
		usersLink.changeUserPlan(parameters.get("busUser"));
		usersLink.selectAnnual();
		usersLink.purchaseClick();

		dashboardPage.clickLogOut();
		i=0;
		ReportManager.log(testContext.getName(), LogStatus.PASS, "Step " + step + "." + (++i)  + ": Post Conditions");

	}

}