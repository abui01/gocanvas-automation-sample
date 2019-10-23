package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.CompanyStagingsPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.SearchUsersPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.profile.OnboardingPage;
import com.canvas.qa.pages.profile.UsersPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.canvas.util.PropertyUtils;

public class GlobeSprintTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void addUserTest(String testDescription, String rallyLink, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			driver.navigate().to(parameters.get("url"));
			driver.findElement(By.id("login")).sendKeys(parameters.get("email"));
			driver.findElement(By.xpath("//input[@type = 'submit']")).click();
			driver.switchTo().frame("ifinbox");
			if(driver.findElements(By.xpath("//a[@class = 'igif lmenudelall']")).size()>0){
				driver.findElement(By.xpath("//a[@class = 'igif lmenudelall']")).click();
				driver.findElement(By.xpath("//a[@class = 'igif lmen_all']")).click();
			}
			driver.get(PropertyUtils.getProperty("app.url"));
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription,
					rallyLink);
			DashboardPage dashboardPage = new DashboardPage(driver);
			CompanyStagingsPage companyStagingsPage = new CompanyStagingsPage(driver);
			String accountID = randomAlphaNumeric(10);
			dashboardPage.clickGoCanvasOnly();
			dashboardPage.clickGoCanvasMenuOption(parameters.get("gocanvasmenu"));
			companyStagingsPage.clickMenuOption(parameters.get("menu"));
			companyStagingsPage.selectVendor(parameters.get("vendor"));
			companyStagingsPage.selectSKU(parameters.get("sku"));
			companyStagingsPage.enterAccountID(accountID);
			companyStagingsPage.enterEmail(parameters.get("email"));
			companyStagingsPage.enterLicenseCount(parameters.get("license"));
			companyStagingsPage.clickSaveButton();
			dashboardPage.clickLogOut();
			driver.navigate().to(parameters.get("url"));
			driver.findElement(By.id("login")).sendKeys(parameters.get("email"));
			driver.findElement(By.xpath("//input[@type = 'submit']")).click();
			driver.switchTo().frame("ifmail");
			driver.findElement(By.id("affim")).click();
			boolean status = driver
					.findElement(By.xpath(String.format("//img[contains(@src,'%s')]", parameters.get("globelogo"))))
					.isDisplayed();
			reportLog(status, testContext.getName(),
					"Verify the Canvas - Activate Your Canvas Account email is received (and includes the Globe Business text/logo)",
					"1.1",
					"Verified the Canvas - Activate Your Canvas Account email is received (and includes the Globe Business text/logo)");
			org.testng.Assert.assertTrue(status);

			driver.findElement(By.xpath("//a[text()= 'Activate Account']")).click();
			driver.switchTo().defaultContent();
			driver.findElement(By.xpath("//a[@href = 'javascript:suppr_mail()']")).click();
			String emailTab1 = driver.getWindowHandle();
			for (String handle : driver.getWindowHandles()) {
				driver.switchTo().window(handle);
			}

			String emailID = randomAlphaNumeric(10) + "@gmail.com";
			companyStagingsPage = new CompanyStagingsPage(driver);
			companyStagingsPage.enterFirstName(parameters.get("firstName"));
			companyStagingsPage.enterLastName(parameters.get("lastName"));
			companyStagingsPage.enterEmailID(emailID);
			companyStagingsPage.enterPhoneNumber(parameters.get("phone"));
			companyStagingsPage.enterPassword(parameters.get("password"));
			OnboardingPage onboardingPage = companyStagingsPage.clickActivateButton();
			status = onboardingPage.isMessageDisplayed(parameters.get("message"));
			reportLog(status, testContext.getName(), "Verify the user is taken to onboarding.", "2.1",
					"Verify the user is taken to onboarding.");
			org.testng.Assert.assertTrue(status);

			driver.get(PropertyUtils.getProperty("app.url"));
			dashboardPage = new DashboardPage(driver);
			LoginPage login = dashboardPage.clickLogOut();
			login.typeusername(parameters.get("username"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
			searchUsersPage.enterEmail(emailID);
			searchUsersPage.clickSearch();
			dashboardPage = searchUsersPage.clickOnManage(emailID);
			dashboardPage.clickAccount();
			UsersPage usersPage = dashboardPage.clickUsers();
			String[] expectedMessageList = parameters.get("text").split(";");
			for (int i = 0; i < expectedMessageList.length; i++) {
				status = usersPage.isTextPresent(expectedMessageList[i]);
				if (!status) {
					break;
				} else {
					continue;
				}

			}
			/*
			 * reportLog(status, testContext.getName()
			 * ,"Verify Company Plan: Globe  & 1 out of 12 licenses used is displayed."
			 * , "3.1",
			 * "Verified Company Plan: Globe  & 1 out of 12 licenses used is displayed."
			 * ); org.testng.Assert.assertTrue(status);
			 */

			searchUsersPage = dashboardPage.clickResumeYourSession();
			dashboardPage.clickGoCanvasOnly();
			dashboardPage.clickGoCanvasMenuOption(parameters.get("gocanvasmenu"));
			status = companyStagingsPage.isVendorDisplayed(emailID, parameters.get("vendor"));
			reportLog(status, testContext.getName(), "Verify vendor displayed is globe.", "4.0",
					"Verified vendor displayed is globe.");
			org.testng.Assert.assertTrue(status);

			status = companyStagingsPage.isActivationStatusDisplayed(emailID, parameters.get("vendorstatus"));
			reportLog(status, testContext.getName(), "Verify the Globe company staging is Activated = Yes.", "4.1",
					"Verified the Globe company staging is Activated = Yes.");
			org.testng.Assert.assertTrue(status);

			String accountID2 = randomAlphaNumeric(10);
			companyStagingsPage.clickMenuOption(parameters.get("menu"));
			companyStagingsPage.selectVendor(parameters.get("vendor2"));
			companyStagingsPage.selectSKU(parameters.get("sku"));
			companyStagingsPage.enterAccountID(accountID2);
			companyStagingsPage.enterEmail(parameters.get("email"));
			companyStagingsPage.enterLicenseCount(parameters.get("license2"));
			companyStagingsPage.clickSaveButton();
			status = companyStagingsPage.isCompanyNameDisplayed(parameters.get("companyname"));
			reportLog(status, testContext.getName(), "Verify Staging for Company Name appears.", "4.2",
					"Verified Staging for Company Name appears.");
			org.testng.Assert.assertTrue(status);

			dashboardPage.clickLogOut();
			driver.navigate().to(parameters.get("url"));
			driver.findElement(By.id("login")).sendKeys(parameters.get("email"));
			driver.findElement(By.xpath("//input[@type = 'submit']")).click();
			driver.switchTo().frame("ifmail");
			driver.findElement(By.id("affim")).click();
			status = driver
					.findElement(By.xpath(String.format("//img[contains(@src,'%s')]", parameters.get("sprintlogo"))))
					.isDisplayed();
			reportLog(status, testContext.getName(),
					"Verify the Canvas - Activate Your Canvas Account email is received (and includes the Sprint text/logo)",
					"4.3",
					"Verified the Canvas - Activate Your Canvas Account email is received (and includes the Sprint text/logo)");
			org.testng.Assert.assertTrue(status);

			driver.findElement(By.xpath("//a[text()= 'Activate Account']")).click();
			driver.switchTo().defaultContent();
			driver.findElement(By.xpath("//a[@href = 'javascript:suppr_mail()']")).click();
			String emailTab = driver.getWindowHandle();
			for (String handle : driver.getWindowHandles()) {
				driver.switchTo().window(handle);
			}
			String canvasTab = driver.getWindowHandle();

			String emailID2 = randomAlphaNumeric(10) + "@gmail.com";
			companyStagingsPage = new CompanyStagingsPage(driver);
			companyStagingsPage.enterFirstName(parameters.get("firstName"));
			companyStagingsPage.enterLastName(parameters.get("lastName"));
			companyStagingsPage.enterEmailID(emailID2);
			companyStagingsPage.enterPhoneNumber(parameters.get("phone"));
			companyStagingsPage.enterPassword(parameters.get("password"));
			onboardingPage = companyStagingsPage.clickActivateButton();
			status = onboardingPage.isMessageDisplayed(parameters.get("message"));
			reportLog(status, testContext.getName(), "Verify the user is taken to onboarding.", "5.1",
					"Verify the user is taken to onboarding.");
			org.testng.Assert.assertTrue(status);

			driver.get(PropertyUtils.getProperty("app.url"));
			dashboardPage = new DashboardPage(driver);
			login = dashboardPage.clickLogOut();
			login.typeusername(parameters.get("username"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage.clickGoCanvasOnly();
			dashboardPage.clickGoCanvasMenuOption(parameters.get("gocanvasmenu"));
			status = companyStagingsPage.isVendorDisplayed(emailID2, parameters.get("vendor3"));
			reportLog(status, testContext.getName(), "Verify vendor displayed is Sprint (BAM).", "6.0",
					"Verified vendor displayed is Sprint (BAM).");
			org.testng.Assert.assertTrue(status);

			status = companyStagingsPage.isActivationStatusDisplayed(emailID2, parameters.get("vendorstatus"));
			reportLog(status, testContext.getName(), "Verify the Sprint (BAM) company staging is Activated = Yes.",
					"6.1", "Verified the Sprint (BAM) company staging is Activated = Yes.");
			org.testng.Assert.assertTrue(status);

			companyStagingsPage.clickMenuOption(parameters.get("menu"));
			companyStagingsPage.selectVendor(parameters.get("vendor2"));
			companyStagingsPage.selectSKU(parameters.get("sku"));
			companyStagingsPage.enterAccountID(accountID2);
			companyStagingsPage.enterEmail(parameters.get("email"));
			companyStagingsPage.enterLicenseCount(parameters.get("license3"));
			companyStagingsPage.clickSaveButton();
			status = companyStagingsPage.isCompanyNameDisplayed(parameters.get("companyname"));
			reportLog(status, testContext.getName(), "Verify Staging for Company Name appears.", "6.2",
					"Verified Staging for Company Name appears.");
			org.testng.Assert.assertTrue(status);

			String totalLicenseCount = String.valueOf(
					Integer.parseInt(parameters.get("license2")) + Integer.parseInt(parameters.get("license3")));
			status = companyStagingsPage.isLicenseCountDisplayed(totalLicenseCount);
			reportLog(status, testContext.getName(),
					"Verify the License Count is correct (value from step 4 + value from step 6).", "6.3",
					"Verify the License Count is correct (value from step 4 + value from step 6).");
			org.testng.Assert.assertTrue(status);

			driver.switchTo().window(emailTab).close();
			driver.switchTo().window(emailTab1).close();
			driver.switchTo().window(canvasTab);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
