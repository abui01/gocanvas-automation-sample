package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.ApplicationStorePage;
import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.DeliveryStatusPage;
import com.canvas.qa.pages.HomePage;
import com.canvas.qa.pages.SearchUsersPage;
import com.canvas.qa.pages.profile.CreateTrialAccountPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.profile.OnboardingPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.canvas.util.PropertyUtils;

public class SignUpTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void errorValidationTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			String email = randomAlphaNumeric(10) + "@bogus.com";
			CreateTrialAccountPage createTrialAccountPage = homePage.tryItFreeButtonClick();
			boolean status = createTrialAccountPage.getActiveStep1().contains(parameters.get("step1"));
			reportLog(status, testContext.getName(), "Verify sign up displayed", "1.0", "Sign up displayed");
			org.testng.Assert.assertTrue(status);

			String[] messageList = parameters.get("text1").split(";");
			for (int i = 0; i < messageList.length; i++) {
				status = createTrialAccountPage.isElementDisplayed(messageList[i]);
				reportLog(status, testContext.getName(), "Verify display:" + messageList[i], "1.1." + i,
						messageList[i] + " :displayed");
				org.testng.Assert.assertTrue(status);
			}

			status = createTrialAccountPage.isElementDisplayed(parameters.get("text2"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("text2"), "1.2",
					parameters.get("text2") + " :displayed");
			org.testng.Assert.assertTrue(status);

			status = createTrialAccountPage.isElementDisplayed(parameters.get("text3"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("text3"), "1.3",
					parameters.get("text3") + " :displayed");
			org.testng.Assert.assertTrue(status);

			String[] labelIDList = parameters.get("labelidlist").split(";");
			String[] labelTextList = parameters.get("labellist").split(";");
			for (int i = 0; i < labelIDList.length; i++) {
				status = createTrialAccountPage.getLabelText(labelIDList[i]).contains(labelTextList[i]);
				reportLog(status, testContext.getName(), "Verify display:" + labelTextList[i], "1.4." + i,
						labelTextList[i] + " :displayed");
				org.testng.Assert.assertTrue(status);
			}

			String[] buttonList = parameters.get("buttonlist").split(";");
			for (int i = 0; i < buttonList.length; i++) {
				status = createTrialAccountPage.isElementDisplayed(buttonList[i]);
				reportLog(status, testContext.getName(), "Verify display:" + buttonList[i], "1.5." + i,
						buttonList[i] + " :displayed");
				org.testng.Assert.assertTrue(status);
			}

			createTrialAccountPage.continueWithEmailClick();
			status = createTrialAccountPage.getActiveStep1().contains(parameters.get("step1"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("step1"), "2.0",
					parameters.get("step1") + " :displayed");
			org.testng.Assert.assertTrue(status);

			String[] errorList = parameters.get("errorlist").split(";");
			for (int i = 0; i < errorList.length; i++) {
				status = createTrialAccountPage.isErrorMessageDisplayed(errorList[i]);
				reportLog(status, testContext.getName(), "Verify display:" + errorList[i], "2.1." + i,
						errorList[i] + " :displayed");
				org.testng.Assert.assertTrue(status);
			}

			String[] fieldDataList = parameters.get("inputlist").split(";");
			for (int i = 0; i < labelIDList.length; i++) {
				if (i == 2) {
					createTrialAccountPage.enterFieldData(labelIDList[i], email);
				} else {
					createTrialAccountPage.enterFieldData(labelIDList[i], fieldDataList[i]);
				}
			}

			createTrialAccountPage.continueWithEmailClick();
			status = createTrialAccountPage.getActiveStep().contains(parameters.get("step2"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("step2"), "3.0",
					parameters.get("step2") + " :displayed");
			org.testng.Assert.assertTrue(status);

			messageList = parameters.get("text4").split(";");
			for (int i = 0; i < messageList.length; i++) {
				status = createTrialAccountPage.isElementDisplayed(messageList[i]);
				reportLog(status, testContext.getName(), "Verify display:" + messageList[i], "3.1." + i,
						messageList[i] + " :displayed");
				org.testng.Assert.assertTrue(status);
			}

			status = createTrialAccountPage.isElementDisplayed(parameters.get("text2"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("text2"), "3.2",
					parameters.get("text2") + " :displayed");
			org.testng.Assert.assertTrue(status);

			status = createTrialAccountPage.isElementDisplayed(parameters.get("text3"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("text3"), "3.3",
					parameters.get("text3") + " :displayed");
			org.testng.Assert.assertTrue(status);

			String[] labelIDList2 = parameters.get("labelidlist2").split(";");
			String[] labelTextList2 = parameters.get("labellist2").split(";");
			for (int i = 0; i < labelIDList2.length; i++) {
				status = createTrialAccountPage.getLabelText(labelIDList2[i]).contains(labelTextList2[i]);
				reportLog(status, testContext.getName(), "Verify  display:" + labelTextList2[i], "3.4." + i,
						labelTextList2[i] + " :displayed");
				org.testng.Assert.assertTrue(status);
			}
			status = createTrialAccountPage.getTryItFreeButtonText().trim().contains(parameters.get("button2").trim());
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("button2"), "3.5",
					parameters.get("button2") + " :displayed");
			org.testng.Assert.assertTrue(status);

			createTrialAccountPage.tryItFreeStep2ClickWithInvalidData();
			status = createTrialAccountPage.getActiveStep().contains(parameters.get("step2"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("step2"), "4.0",
					parameters.get("step2") + " :displayed");
			org.testng.Assert.assertTrue(status);

			status = createTrialAccountPage.isErrorMessageDisplayed(parameters.get("error2"));
			reportLog(status, testContext.getName(), "Verify  display:" + parameters.get("error2"), "4.1",
					parameters.get("error2") + " :displayed");
			org.testng.Assert.assertTrue(status);

			if (createTrialAccountPage.isPhoneNumberDisplayed()) {
				createTrialAccountPage.step2Phone("9876543210");
			}
			if (createTrialAccountPage.isEmployeeSizeDisplayed()) {
				createTrialAccountPage.selectEmployeeSize(1);
			}
			createTrialAccountPage.enterFieldData(labelIDList2[3], parameters.get("password"));
			OnboardingPage onboardingPage = createTrialAccountPage.tryItFreeStep2Click();
			status = driver.getCurrentUrl().contains(PropertyUtils.getProperty("app.url") + "/thankyou/");
			reportLog(status, testContext.getName(), "Verify new url", "5.0",
					PropertyUtils.getProperty("app.url") + "/thankyou/" + " :displayed");
			org.testng.Assert.assertTrue(status);

			status = onboardingPage.isMessageDisplayed(parameters.get("text5"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("text5"), "5.1",
					parameters.get("text5") + " :displayed");
			org.testng.Assert.assertTrue(status);

			status = onboardingPage.isMessageDisplayed(parameters.get("text6"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("text6"), "5.2",
					parameters.get("text6") + " :displayed");
			org.testng.Assert.assertTrue(status);

			messageList = parameters.get("text7").split("'");
			for (int i = 0; i < messageList.length; i++) {
				status = onboardingPage.isMessageDisplayed(messageList[i]);
				reportLog(status, testContext.getName(), "Verify display:" + messageList[i], "5.3." + i,
						messageList[i] + " :displayed");
				org.testng.Assert.assertTrue(status);
			}

			status = onboardingPage.isButtonDisplayed(parameters.get("button3"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("button3"), "5.4",
					parameters.get("button3") + " :displayed");
			org.testng.Assert.assertTrue(status);

			status = onboardingPage.isButtonDisplayed(parameters.get("button4"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("button4"), "5.5",
					parameters.get("button4") + " :displayed");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void googleAutoFillTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			login.gotoLogin();
			login.typeusername(parameters.get("username"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			DashboardPage dashboardPage = new DashboardPage(driver);
			SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
			searchUsersPage.enterEmail(parameters.get("gmail_login"));
			searchUsersPage.clickSearch();
			if (searchUsersPage.isUserDisplayed(parameters.get("gmail_login"))) {
				dashboardPage = searchUsersPage.clickOnManage(parameters.get("gmail_login"));
				dashboardPage.clickProfile();
				dashboardPage.clickEdit();
				String email = randomAlphaNumeric(10) + "@bogus.com";
				dashboardPage.enterEmail(email);
				dashboardPage.clickSave();
				customWait(2);
				dashboardPage.clickResumeYourSession();
				customWait(2);
			}
			searchUsersPage.logout();

			CreateTrialAccountPage createTrialAccountPage = homePage.tryItFreeButtonClick();
			boolean status = createTrialAccountPage.isContinueWithGoogleButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify you see the button, in color white 'Continue with Google'",
					"1.0", "'Continue with Google' button displayed.");
			org.testng.Assert.assertTrue(status);
			deleteEmails(parameters.get("gmail_login"), parameters.get("gmail_pwd"));

			String counterTab = driver.getWindowHandle();
			((JavascriptExecutor) driver).executeScript("window.open()");
			for (String handle : driver.getWindowHandles()) {
				driver.switchTo().window(handle);
			}
			String winTab = driver.getWindowHandle();
			driver.get("https://accounts.google.com/ServiceLogin?");
			driver.findElement(By.xpath("//input[@type='email']")).sendKeys(parameters.get("gmail_login"));
			driver.findElement(By.xpath("//*[@id='identifierNext' or @id='next']")).click();
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type=password]")));
			driver.findElement(By.cssSelector("input[type=password]")).sendKeys(parameters.get("gmail_pwd"));
			driver.findElement(By.xpath("//*[@id='passwordNext' or @id='signIn']")).click();
			customWait(2);
			driver.findElement(By.xpath("//a[@data-track-as = 'Welcome Header Mail']")).click();
			customWait(5);
			driver.switchTo().window(counterTab);
			customWait(2);
			createTrialAccountPage.clickContinueWithGoogleButton();
			customWait(10);
			if (createTrialAccountPage.isErrorMessageDisplayed("First Name is required")) {
				createTrialAccountPage.clickContinueWithGoogleButton();
				customWait(2);
				for (String handle : driver.getWindowHandles()) {
					driver.switchTo().window(handle);
				}
				driver.findElement(
						By.xpath(String.format("//p[contains(@data-email,'%s')]", parameters.get("gmail_login"))))
						.click();
				driver.switchTo().window(counterTab);
			}
			customWait(2);
			status = createTrialAccountPage.getActiveStep().contains(parameters.get("active_step"));
			reportLog(status, testContext.getName(), "Verify user directed to the Step 2 of the sign up.", "2.0",
					"User is directed to the Step 2 of the Sign up successfully.");
			org.testng.Assert.assertTrue(status);

			driver.switchTo().window(winTab);
			driver.findElement(By.xpath(String.format("//a[contains(@title,'%s')]", parameters.get("gmail_login"))))
					.click();
			driver.findElement(By.xpath("//a[text() = 'Sign out']")).click();
			customWait(2);
			driver.switchTo().window(counterTab);
			createTrialAccountPage.clickStep1();
			createTrialAccountPage.clickContinueWithGoogleButton();
			for (String handle : driver.getWindowHandles()) {
				driver.switchTo().window(handle);
			}
			status = driver.findElement(By.xpath("//div[text() = 'Sign in with Google']")).isDisplayed();
			reportLog(status, testContext.getName(), "Verify the Sign In for Google Window displays to the user.",
					"3.0", "Sign In for Google, private window displays successfully to the user. ");
			org.testng.Assert.assertTrue(status);

			driver.findElement(
					By.xpath(String.format("//p[contains(@data-email,'%s')]", parameters.get("gmail_login")))).click();
			driver.findElement(By.xpath("//div[@id = 'password']/div/div/div/input"))
					.sendKeys(parameters.get("gmail_pwd"));
			clickOnHiddenElement(driver.findElement(By.id("passwordNext")));
			customWait(2);
			driver.switchTo().window(counterTab);
			customWait(10);
			status = createTrialAccountPage.getActiveStep().contains(parameters.get("active_step"));
			reportLog(status, testContext.getName(), "Verify user directed to the Step 2 of the sign up.", "4.0",
					"User is directed to the Step 2 of the Sign up successfully.");
			org.testng.Assert.assertTrue(status);

			createTrialAccountPage.step2CompanyName(parameters.get("company_name"));
			createTrialAccountPage.selectIndustry(parameters.get("industry"));
			createTrialAccountPage.step2Phone(parameters.get("phone"));
			if (createTrialAccountPage.isEmployeeSizeDisplayed()) {
				createTrialAccountPage.selectEmployeeSize(1);
			}
			createTrialAccountPage.step2Password(parameters.get("password"));
			OnboardingPage onboardingPage = createTrialAccountPage.tryItFreeStep2Click();
			customWait(5);
			status = onboardingPage.getHeader().contains(parameters.get("welcome_text"));
			reportLog(status, testContext.getName(), "Verify user is directed to On-Boarding flow.", "5.0",
					"User was directed to On-Boarding flow successfully.");
			org.testng.Assert.assertTrue(status);

			status = verifyEmail(parameters.get("gmail_login"), parameters.get("gmail_pwd"), "Subject 1 Test",
					"jason@gocanvas.com");
			reportLog(status, testContext.getName(), "Verify recived email.", "5.1", "Verified received email.");
			org.testng.Assert.assertTrue(status);

			driver.get(PropertyUtils.getProperty("app.url"));
			homePage = new HomePage(driver);
			createTrialAccountPage = homePage.tryItFreeButtonClick();
			createTrialAccountPage.clickContinueWithGoogleButton();
			createTrialAccountPage.step2CompanyName(parameters.get("company_name"));
			createTrialAccountPage.selectIndustry(parameters.get("industry"));
			createTrialAccountPage.step2Phone(parameters.get("phone"));
			if (createTrialAccountPage.isEmployeeSizeDisplayed()) {
				createTrialAccountPage.selectEmployeeSize(1);
			}
			createTrialAccountPage.step2Password(parameters.get("password"));
			createTrialAccountPage.tryItFreeStep2ClickWithInvalidData();
			status = createTrialAccountPage.isErrorMessageDisplayed(parameters.get("error_message"));
			reportLog(status, testContext.getName(), "Verify error message displayed", "6.0",
					parameters.get("error_message") + " error message displayed.");
			org.testng.Assert.assertTrue(status);

			driver.get(PropertyUtils.getProperty("app.url"));
			login = new LoginPage(driver);
			login.gotoLogin();
			login.typeusername(parameters.get("gmail_login"));
			login.typepassword(parameters.get("password"));
			onboardingPage = login.Clickonloginbuttonforfirstlogin();
			status = onboardingPage.isMessageDisplayed(parameters.get("onboarding_message"));
			reportLog(status, testContext.getName(), "Verify user can login to the website successfully.", "7.0",
					"User was successfully able to login to the Website with new account registered with the gmail account.");
			org.testng.Assert.assertTrue(status);

			driver.get(PropertyUtils.getProperty("app.url"));
			login.gotoLogin();
			login.typeusername(parameters.get("username"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage = new DashboardPage(driver);
			searchUsersPage = dashboardPage.clickSearchUsers();
			searchUsersPage.enterEmail(parameters.get("gmail_login"));
			searchUsersPage.clickSearch();
			dashboardPage = searchUsersPage.clickOnManage(parameters.get("gmail_login"));
			dashboardPage.clickProfile();
			dashboardPage.clickEdit();
			String email = randomAlphaNumeric(10) + "@bogus.com";
			dashboardPage.enterEmail(email);
			dashboardPage.clickSave();
			customWait(2);
			dashboardPage.clickResumeYourSession();
			customWait(2);

			reportLog(true, testContext.getName(), testDescription, "8.0",
					"Cannot be automated as require submission from mobile.");
			reportLog(true, testContext.getName(), testDescription, "9.0",
					"Cannot be automated as require submission from mobile.");
			reportLog(true, testContext.getName(), testDescription, "10.0",
					"Cannot be automated as require submission from mobile.");

			deleteEmails(parameters.get("gmail_login"), parameters.get("gmail_pwd"));

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
	
	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void newUserSignUpTest(String testDescription, String rallyLink, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			reportLog(true, testContext.getName(), "Rally Link: ",
					"Rally Link: " + String.format("<a href='%s'>" + rallyLink + "</a>", rallyLink));
			DashboardPage dashboardPage = new DashboardPage(driver);
			ApplicationStorePage applicationStorePage = dashboardPage.clickApplicationStore();
			applicationStorePage.clickViewAllApps();
			String appName = parameters.get("appName");
			applicationStorePage.enterAppToSearch(appName);	
			applicationStorePage.clickAppSearchButton();
			applicationStorePage.clickApp(appName);
	
			String[] labelIDList = parameters.get("labelidlist").split(";");
			String email = randomAlphaNumeric(10) + "@bogus.com";
			String[] fieldDataList = parameters.get("inputlist").split(";");
			for (int i = 0; i < labelIDList.length; i++) {
				if (i == 2) {
					applicationStorePage.enterFieldData(labelIDList[i], email);
				} else {
					applicationStorePage.enterFieldData(labelIDList[i], fieldDataList[i]);
				}
			}
			applicationStorePage.clickTryItFreeButton();

			if (applicationStorePage.isPhoneNumberDisplayed()) {
				applicationStorePage.step2Phone("9876543210");
			}
			if (applicationStorePage.isEmployeeSizeDisplayed()) {
				applicationStorePage.selectEmployeeSize(1);
			}
			applicationStorePage.enterFieldData(parameters.get("labelidlist2"), parameters.get("password"));

			OnboardingPage onboardingPage = applicationStorePage.clickGetStartedButton();
			boolean status = onboardingPage.isMessageDisplayed(parameters.get("welcometext"));
			reportLog(status, testContext.getName(), "Verify First screen of Sign Up appears.", "1.1",
					"First screen of Sign Up appears.");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void twoStepSignUpTest(String testDescription, String rallyLink, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			reportLog(true, testContext.getName(), "Rally Link: ",
					"Rally Link: " + String.format("<a href='%s'>" + rallyLink + "</a>", rallyLink));
			DashboardPage dashboardPage = new DashboardPage(driver);
			ApplicationStorePage applicationStorePage = dashboardPage.clickApplicationStore();
			applicationStorePage.clickViewAllApps();
			String appName = applicationStorePage.getAppNameByIndex(1);
			applicationStorePage.clickApp(appName);
			boolean status = applicationStorePage.isAppDetailPageDisplayed(appName);
			reportLog(status, testContext.getName(), "Verify app detail page displayed.", "1.1",
					"App detail page displayed.");

			String[] labelIDList = parameters.get("labelidlist").split(";");
			for (int i = 0; i < labelIDList.length; i++) {
				status = applicationStorePage.isFieldDisplayed(labelIDList[i]);
				reportLog(status, testContext.getName(), "Verify display:" + labelIDList[i], "2.1." + i,
						labelIDList[i] + " :displayed");
				org.testng.Assert.assertTrue(status);
			}

			String email = randomAlphaNumeric(10) + "@bogus.com";
			String[] fieldDataList = parameters.get("inputlist").split(";");
			for (int i = 0; i < labelIDList.length; i++) {
				if (i == 2) {
					applicationStorePage.enterFieldData(labelIDList[i], email);
				} else {
					applicationStorePage.enterFieldData(labelIDList[i], fieldDataList[i]);
				}
			}
			applicationStorePage.clickTryItFreeButton();

			if (applicationStorePage.isPhoneNumberDisplayed()) {
				applicationStorePage.step2Phone("9876543210");
			}
			if (applicationStorePage.isEmployeeSizeDisplayed()) {
				applicationStorePage.selectEmployeeSize(1);
			}
			applicationStorePage.enterFieldData(parameters.get("labelidlist2"), parameters.get("password"));

			OnboardingPage onboardingPage = applicationStorePage.clickGetStartedButton();
			status = onboardingPage.isMessageDisplayed(parameters.get("text6"));
			reportLog(status, testContext.getName(), "Verify First screen of FTUX appears.", "3.1",
					"First screen of FTUX appears.");
			org.testng.Assert.assertTrue(status);

			driver.get(PropertyUtils.getProperty("app.url"));
			LoginPage login = new LoginPage(driver);
			login.gotoLogin();
			login.typeusername(parameters.get("admin"));
			login.typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();

			DeliveryStatusPage deliveryLink = new DeliveryStatusPage(driver);
			deliveryLink.gotoUser(email);
			deliveryLink.deliveryStatClick();
			status = (deliveryLink.numDownLoadClientEmailSent(email) == 1);
			reportLog(status, testContext.getName(), "Verify activation mail", "4.1", "Verified activation mail");
			org.testng.Assert.assertTrue(status);

			deliveryLink.resumeSession();
			deliveryLink.activateUser(email);
			deliveryLink.logout();

			login.typeusername(email);
			login.typepassword(parameters.get("password"));
			onboardingPage = login.Clickonloginbuttonforfirstlogin();

			status = onboardingPage.getHeader().equals(parameters.get("devicesHeader"));
			reportLog(status, testContext.getName(), "Verify header: " + parameters.get("devicesHeader"), "5.0",
					parameters.get("devicesHeader") + " : header verified.");
			org.testng.Assert.assertTrue(status);

			onboardingPage.clickButton(parameters.get("skipmessage"));
			onboardingPage.clickButton(parameters.get("skipmessage"));
			onboardingPage.clickButton(parameters.get("skipmessage"));
			onboardingPage.clickButton(parameters.get("skipmessage"));

			status = onboardingPage.getHeader().contains(parameters.get("activationHeader"));
			reportLog(status, testContext.getName(), "Verify header displays:" + parameters.get("activationHeader"),
					"5.1", parameters.get("activationHeader") + " : header displayed");
			org.testng.Assert.assertTrue(status);

			driver.get(PropertyUtils.getProperty("app.url"));
			dashboardPage.clickHomeMenu();
			CreateAppPage createAppPage = dashboardPage.clickApp();
			status = createAppPage.isAppNameDisplayed(appName);
			reportLog(status, testContext.getName(), "Verify app displayed for new user", "6.1",
					"Verified app displayed for new user.");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void linkedinSignUpTest(String testDescription, String rallyLink, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			CreateTrialAccountPage createTrialAccountPage = homePage.tryItFreeButtonClick();
			boolean status = createTrialAccountPage.isLinkedinSignUpButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify you see the linkedIn signup button.",
					"1.0", "LinkedIn signup button displayed.");
			org.testng.Assert.assertTrue(status);
			
			createTrialAccountPage.clickLinkedinSignUpButton();
			customWait(2);
			for (String handle : driver.getWindowHandles()) {
				driver.switchTo().window(handle);
			}
			
			status = driver.findElement(By.xpath(String.format("//p[contains(text(),'%s')]", parameters.get("message")))).isDisplayed();
			reportLog(status, testContext.getName(), "LinkedIn signup window displayed.",
					"2.0", "LinkedIn signup window displayed.");
			org.testng.Assert.assertTrue(status);
			
			driver.findElement(By.id("session_key-oauthAuthorizeForm")).sendKeys(parameters.get("linkedin_login"));
			driver.findElement(By.id("session_password-oauthAuthorizeForm")).sendKeys(parameters.get("linkedin_pwd"));
			driver.findElement(By.xpath("//input[@type = 'submit']")).click();
			customWait(5);
			
			status = driver.findElement(By.xpath(String.format("//h1[text() = '%s']", parameters.get("message2")))).isDisplayed();
			reportLog(status, testContext.getName(), "LinkedIn signup verification window displayed.",
					"3.0", "LinkedIn signup verification window displayed.");
			org.testng.Assert.assertTrue(status);
			
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

}
