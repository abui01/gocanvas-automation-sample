package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

@Test
public class CanvasLoginTest extends BrowserLaunchTest

{

	public void commonLogin(String step, String testdescription, String userName, String password,
			ITestContext testContext) throws IOException, InterruptedException {
		LoginPage login = new LoginPage(driver);
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		String Uname = "";
		String Pwd = "";
		if (userName == null || userName.isEmpty()) {
			Uname = parameters.get("username");
		} else {
			Uname = userName;
		}

		if (password == null || password.isEmpty()) {
			Pwd = parameters.get("password");
		} else {
			Pwd = password;
		}
		int i = 0;
		/*
		 * User Name
		 */
		try {
			login.gotoLogin();
			login.typeusername(Uname);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Email ID");

		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Email ID");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Email ID");
		}
		/*
		 * Password
		 */
		try {
			login.typepassword(Pwd);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Passsword");

		} catch (Exception e) {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Password");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Password");
		}
		/*
		 * Login Button
		 */
		try {
			login.Clickonloginbutton();
			String successText = login.checkForSuccessfulLogin();
			if (successText != null && !successText.isEmpty() && successText.contains("Dashboard")) {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
						"Step " + step + "." + (++i) + ": SignIn Successful.");
			} else {

				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": SignIn Failure - " + successText);

			}
		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, e.getMessage());
		}

	}

	/**
	 * 
	 * Login into Canvas Application with reading data from excel
	 * 
	 */

	@Parameters({ "step", "testdescription" })
	public void verifyBlankLogin(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		LoginPage login = new LoginPage(driver);
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String Uname = parameters.get("username_empty");
		String Pwd = parameters.get("password_empty");

		try {

			if (Uname == null || "".equals(Uname.trim())) {

				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
						"Step " + step + "." + (++i) + ": Keep Email Field Empty:");
				org.testng.Assert.assertTrue(true, "Step " + step + ": Email ID");
			}
			login.gotoLogin();
			login.typeusername(Uname);

		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": FAILED");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Email");

		}

		try {

			if (Pwd == null || "".equals(Pwd.trim())) {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
						"Step " + step + "." + (++i) + ": Keep Password Field Empty:");
				org.testng.Assert.assertTrue(true, "Step " + step + ": Email ID");

			}
			login.typepassword(Pwd);

		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + ": Password : FAILED");
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, e.getMessage());
			org.testng.Assert.assertTrue(false, "Step " + step + "." + (++i) + ": Password");

		}

		/**
		 * Click On Login Button
		 */
		try {
			login.Clickonloginbutton();

			String successText = login.checkForUnSuccessfulLogin1();
			if (successText != null && !successText.isEmpty()
					&& successText.equals("Username and password cannot be blank.")) {

				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
						"Step " + step + "." + (++i) + ": SignIn Successful.");

			} else {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": SignIn Failure -With user name " + successText);

			}
		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, e.getMessage());

		}
	}

	//

	@Parameters({ "step", "testdescription" })
	public void verifyInvalidLogin(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;
		LoginPage login = new LoginPage(driver);
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String Uname = parameters.get("username_valid");
		String Pwd = parameters.get("password_empty");

		try {

			login.typeusername(Uname);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Email ID");

		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Email ID");

			e.printStackTrace();

			org.testng.Assert.assertTrue(false, "Step " + step + ": Email ID");

		}

		try {

			if (Pwd == null || "".equals(Pwd.trim())) {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
						"Step " + step + "." + (++i) + ": Keep Password Field Empty");

			}
			login.typepassword(Pwd);

		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Password : FAILED");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Password");
		}

		try {
			login.Clickonloginbutton();

			String successText = login.checkForUnSuccessfulLogin1();
			if (successText != null && !successText.isEmpty()
					&& successText.equals("Username and password cannot be blank.")) {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
						"Step " + step + "." + (++i) + ": SignIn Successful.");
			} else {

				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": SignIn Failure - " + successText);
			}
		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, e.getMessage());

		}
	}

	@Parameters({ "step" })
	public void verifyInValidLogin(String step, String userName, String password, ITestContext testContext)
			throws IOException, InterruptedException {
		LoginPage login = new LoginPage(driver);
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		String Uname = "";
		String Pwd = "";
		if (userName == null || userName.isEmpty()) {
			Uname = parameters.get("username");
		} else {
			Uname = userName;
		}

		if (password == null || password.isEmpty()) {
			Pwd = parameters.get("password");
		} else {
			Pwd = password;
		}
		int i = 0;
		/*
		 * User Name
		 */
		try {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions
						.not(ExpectedConditions.visibilityOf(driver.findElement(By.className("toast")))));
			} catch (Exception e) {

			}
			login.gotoLogin();
			//Thread.sleep(2000);
			login.typeusername(Uname);
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Email ID");
		} catch (Exception e) {
			e.printStackTrace();
			ReportManager.log(testContext.getName(), LogStatus.FAIL, "Step " + step + "." + (++i) + ": Email ID");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Email ID");
		}
		try {
			login.typepassword(Pwd);
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Passsword");

		} catch (Exception e) {

			ReportManager.log(testContext.getName(), LogStatus.FAIL, "Step " + step + "." + (++i) + ": Password");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Password");
		}
		try {
			login.Clickonloginbutton();
			//Thread.sleep(5000);
			String successText = login.checkForSuccessfulLogin();
			if (!(successText != null && !successText.isEmpty() && successText.equalsIgnoreCase("Home"))) {
				ReportManager.log(testContext.getName(), LogStatus.PASS,
						"Step " + step + "." + (++i) + ": Not able to Sign-In because user is disabled.");
			} else {

				ReportManager.log(testContext.getName(), LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": Signed-In though user was disabled. - " + successText);

			}
		} catch (Exception e) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL, e.getMessage());
		}

	}

	@Parameters({ "step", "testdescription" })
	public void verifyInValidLogin(String step, String testdescription, String userName, String password,
			ITestContext testContext) throws IOException, InterruptedException {
		LoginPage login = new LoginPage(driver);
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		String Uname = "";
		String Pwd = "";
		if (userName == null || userName.isEmpty()) {
			Uname = parameters.get("username");
		} else {
			Uname = userName;
		}

		if (password == null || password.isEmpty()) {
			Pwd = parameters.get("password");
		} else {
			Pwd = password;
		}
		int i = 0;
		/*
		 * User Name
		 */
		try {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions
						.not(ExpectedConditions.visibilityOf(driver.findElement(By.className("toast")))));
			} catch (Exception e) {

			}
			login.gotoLogin();
			//Thread.sleep(2000);
			login.typeusername(Uname);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Email ID");
		} catch (Exception e) {
			e.printStackTrace();
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Email ID");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Email ID");
		}
		try {
			login.typepassword(Pwd);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Passsword");

		} catch (Exception e) {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Password");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Password");
		}
		try {
			login.Clickonloginbuttonforinvalidlogin();
			//Thread.sleep(5000);
			String successText = login.checkForSuccessfulLogin();
			if (!(successText != null && !successText.isEmpty() && successText.equalsIgnoreCase("Home"))) {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
						"Step " + step + "." + (++i) + ": Not able to Sign-In because user is disabled.");
			} else {

				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": Signed-In though user was disabled. - " + successText);

			}
		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, e.getMessage());
			org.testng.Assert.assertTrue(false);
		}

	}

	@Parameters({ "step", "testdescription" })
	public void verifyInvalidLogin2(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		LoginPage login = new LoginPage(driver);
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String Uname = parameters.get("username_invalid");
		String Pwd = parameters.get("password_invalid");
		try {

			login.typeusername(Uname);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter InValid Email ID");

		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": InValid Email ID");
			org.testng.Assert.assertTrue(false, "Step " + step + ": InvalidEmail ID");
		}
		/*
		 * Password
		 */
		try {
			login.typepassword(Pwd);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter InValid Passsword");

		} catch (Exception e) {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": InValid Password");
			org.testng.Assert.assertTrue(false, "Step " + step + ": InValid Password");
		}
		/*
		 * Login Button
		 */
		try {
			login.Clickonloginbutton();
			String unsuccessText = login.checkForUnSuccessfulLogin2();
			if (unsuccessText != null && !unsuccessText.isEmpty() && unsuccessText
					.equals("Could not sign you in as '" + Uname + "', your email or password is incorrect.")) {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
						"Step " + step + "." + (++i) + ": SignIn Successful.");
			} else {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": SignIn Failure - " + unsuccessText);

			}
		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, e.getMessage());
		}
	}

	@Parameters({ "step", "testdescription" })
	public void verifyValidLogin(String step, String testdescription, ITestContext testContext)
			throws IOException, InterruptedException {

		LoginPage login = new LoginPage(driver);
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		String Uname = parameters.get("username_valid");
		String Pwd = parameters.get("password_valid");
		// String Uname = parameters.get("username_valid");
		// String Pwd = parameters.get("password_valid");
		int i = 0;
		/*
		 * User Name
		 */
		try {
			login.typeusername(Uname);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Email ID");

		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Email ID");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Email ID");
		}
		/*
		 * Password
		 */
		try {
			login.typepassword(Pwd);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Passsword");

		} catch (Exception e) {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Password");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Password");
		}
		/*
		 * Login Button
		 */
		try {
			login.Clickonloginbutton();
			String successText = login.checkForSuccessfulLogin();
			if (successText != null && !successText.isEmpty() && successText.contains("Dashboard")
					|| successText.contains("Apps") || successText.contains("Reports")
					|| successText.contains("Submissions") || successText.contains("Workflow & Dispatch")) {
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
						"Step " + step + "." + (++i) + ": SignIn Successful.");
			} else {

				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
						"Step " + step + "." + (++i) + ": SignIn Failure - " + successText);

			}
		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL, e.getMessage());
		}

	}

	@Parameters({ "step" })
	public void verifyValidLogin1(String step, String userName, String password, ITestContext testContext)
			throws IOException, InterruptedException {
		LoginPage login = new LoginPage(driver);
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		String Uname = "";
		String Pwd = "";
		if (userName == null || userName.isEmpty()) {
			Uname = parameters.get("username");
		} else {
			Uname = userName;
		}

		if (password == null || password.isEmpty()) {
			Pwd = parameters.get("password");
		} else {
			Pwd = password;
		}
		int i = 0;
		/*
		 * User Name
		 */
		try {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions
						.not(ExpectedConditions.visibilityOf(driver.findElement(By.className("toast")))));
			} catch (Exception e) {

			}
			login.gotoLogin();
			//Thread.sleep(2000);
			login.typeusername(Uname);
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Email ID");
		} catch (Exception e) {
			e.printStackTrace();
			ReportManager.log(testContext.getName(), LogStatus.FAIL, "Step " + step + "." + (++i) + ": Email ID");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Email ID");
		}
		try {
			login.typepassword(Pwd);
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Passsword");

		} catch (Exception e) {

			ReportManager.log(testContext.getName(), LogStatus.FAIL, "Step " + step + "." + (++i) + ": Password");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Password");
		}

		login.Clickonloginbutton();
		//Thread.sleep(5000);
		String successText = login.checkForSuccessfulLogin();
		if (successText != null && !successText.isEmpty() && successText.equalsIgnoreCase("Home")) {

			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": SignIn Successful.");
		} else {

			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": SignIn Failure - " + successText);

		}

		org.testng.Assert.assertTrue(
				successText != null && !successText.isEmpty() && successText.equalsIgnoreCase("Home"),
				"SignIn Failure ");
	}

	@Parameters({ "step" })
	public void verifyValidLogin1(String step, String testdescription, String userName, String password,
			ITestContext testContext) throws IOException, InterruptedException {
		LoginPage login = new LoginPage(driver);
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		String Uname = "";
		String Pwd = "";
		if (userName == null || userName.isEmpty()) {
			Uname = parameters.get("username");
		} else {
			Uname = userName;
		}

		if (password == null || password.isEmpty()) {
			Pwd = parameters.get("password");
		} else {
			Pwd = password;
		}
		int i = 0;
		/*
		 * User Name
		 */
		try {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions
						.not(ExpectedConditions.visibilityOf(driver.findElement(By.className("toast")))));
			} catch (Exception e) {

			}
			login.gotoLogin();
			//Thread.sleep(2000);
			login.typeusername(Uname);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Email ID");
		} catch (Exception e) {
			e.printStackTrace();
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Email ID");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Email ID");
		}
		try {
			login.typepassword(Pwd);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Passsword");

		} catch (Exception e) {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Password");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Password");
		}

		login.Clickonloginbutton();
		//Thread.sleep(5000);
		String successText = login.checkForSuccessfulLogin();
		if (successText != null && !successText.isEmpty() && successText.equalsIgnoreCase("Home")) {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": SignIn Successful.");
		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": SignIn Failure - " + successText);

		}

		org.testng.Assert.assertTrue(
				successText != null && !successText.isEmpty() && successText.equalsIgnoreCase("Home"),
				"SignIn Failure ");
	}

	@Parameters({ "step" })
	public void verifyValidLoginnew(String step, String userName, String password, ITestContext testContext)
			throws IOException, InterruptedException {
		LoginPage login = new LoginPage(driver);
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		String Uname = "";
		String Pwd = "";
		if (userName == null || userName.isEmpty()) {
			Uname = parameters.get("username");
		} else {
			Uname = userName;
		}

		if (password == null || password.isEmpty()) {
			Pwd = parameters.get("password");
		} else {
			Pwd = password;
		}
		int i = 0;
		/*
		 * User Name
		 */
		try {
			// login.gotoLogin();
			//Thread.sleep(30000);
			login.typeusername(Uname);
			//Thread.sleep(30000);
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Email ID");

		} catch (Exception e) {
			ReportManager.log(testContext.getName(), LogStatus.FAIL, "Step " + step + "." + (++i) + ": Email ID");
			// org.testng.Assert.assertTrue(false, "Step " + step + ": Email
			// ID");
		}
		try {
			login.typepassword(Pwd);
			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Passsword");

		} catch (Exception e) {

			ReportManager.log(testContext.getName(), LogStatus.FAIL, "Step " + step + "." + (++i) + ": Password");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Password");
		}

		login.Clickonloginbutton();
		//Thread.sleep(5000);
		String successText = login.checkForSuccessfulLogin();
		if (successText != null && !successText.isEmpty() && successText.equalsIgnoreCase("Home")) {

			ReportManager.log(testContext.getName(), LogStatus.PASS,
					"Step " + step + "." + (++i) + ": SignIn Successful.");
		} else {

			ReportManager.log(testContext.getName(), LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": SignIn Failure - " + successText);

		}
		org.testng.Assert.assertTrue(
				successText != null && !successText.isEmpty() && successText.equalsIgnoreCase("Home"),
				"SignIn Failure");

	}

	@Parameters({ "step", "testdescription" })
	public void verifyValidLoginnew(String step, String testdescription, String userName, String password,
			ITestContext testContext) throws IOException, InterruptedException {
		LoginPage login = new LoginPage(driver);
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		String Uname = "";
		String Pwd = "";
		if (userName == null || userName.isEmpty()) {
			Uname = parameters.get("username");
		} else {
			Uname = userName;
		}

		if (password == null || password.isEmpty()) {
			Pwd = parameters.get("password");
		} else {
			Pwd = password;
		}
		int i = 0;
		/*
		 * User Name
		 */
		try {
			// login.gotoLogin();
			//Thread.sleep(30000);
			login.typeusername(Uname);
			//Thread.sleep(30000);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Email ID");

		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Email ID");
			// org.testng.Assert.assertTrue(false, "Step " + step + ": Email
			// ID");
		}
		try {
			login.typepassword(Pwd);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Passsword");

		} catch (Exception e) {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Password");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Password");
		}

		login.Clickonloginbutton();
		//Thread.sleep(5000);
		String successText = login.checkForSuccessfulLogin();
		if (successText != null && !successText.isEmpty() && successText.equalsIgnoreCase("Home")) {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": SignIn Successful.");
		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": SignIn Failure - " + successText);

		}
		org.testng.Assert.assertTrue(
				successText != null && !successText.isEmpty() && successText.equalsIgnoreCase("Home"),
				"SignIn Failure");

	}

	@Parameters({ "step", "testdescription", "value" })
	public void verifyValidLoginPermission(String step, String testdescription, int value, ITestContext testContext)
			throws IOException, InterruptedException {

		LoginPage login = new LoginPage(driver);
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String Uname = parameters.get("username_" + value);
		String Pwd = parameters.get("password_" + value);

		/*
		 * User Name
		 */
		int i = 0;
		try {

			login.gotoLogin();
			login.typeusername(Uname);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Email ID");

		} catch (Exception e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Email ID");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Email ID");
		}
		/*
		 * Password
		 */
		try {
			login.typepassword(Pwd);
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Enter Valid Passsword");

		} catch (Exception e) {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Password");
			org.testng.Assert.assertTrue(false, "Step " + step + ": Password");
		}
		/*
		 * Login Button
		 */

		login.Clickonloginbutton();
		String successText = login.checkForSuccessfulLogin1();

		if (successText != null && !successText.isEmpty() && successText.contains("Dashboard")
				|| successText.contains("Apps") || successText.contains("Reports")
				|| successText.contains("Submissions") || successText.contains("Workflow & Dispatch")) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": SignIn Successful.");
		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": SignIn Failure - " + successText);

		}

		org.testng.Assert.assertTrue(
				successText != null && !successText.isEmpty() && successText.contains("Dashboard")
						|| successText.contains("Apps") || successText.contains("Reports")
						|| successText.contains("Submissions") || successText.contains("Workflow & Dispatch"),
				"SignIn Failure");
		
	}
}