package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.DeliveryStatusPage;
import com.canvas.qa.pages.HomePage;
import com.canvas.qa.pages.profile.CreateTrialAccountPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.profile.OnboardingPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.canvas.util.PropertyUtils;

public class FTUXExploreOnYourOwnTest extends BrowserLaunchTest

{
	@Test
	@Parameters({ "testdescription" })
	public void signUpTest(String testDescription, ITestContext testContext) throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		try {
			int i = 0;

			HomePage homePage = new HomePage(driver);
			CreateTrialAccountPage createAccountLink = homePage.tryItFreeButtonClick();
			String companyEmail = randomAlphaNumeric(10) + parameters.get("email");

			String[] fieldDataList = parameters.get("inputlist").split(";");
			String[] labelIDList = parameters.get("labelidlist").split(";");
			for (i = 0; i < labelIDList.length; i++) {
				if (i == 2) {
					createAccountLink.enterFieldData(labelIDList[i], companyEmail);
				} else {
					createAccountLink.enterFieldData(labelIDList[i], fieldDataList[i]);
				}
			}
			createAccountLink.continueWithEmailClick();

			if (createAccountLink.isPhoneNumberDisplayed()) {
				createAccountLink.step2Phone(parameters.get("phone"));
			}
			if (createAccountLink.isEmployeeSizeDisplayed()) {
				createAccountLink.selectEmployeeSize(1);
			}
			createAccountLink.enterFieldData("password_input", parameters.get("password"));
			OnboardingPage onboardingPage = createAccountLink.tryItFreeStep2Click();

			boolean status = onboardingPage.isMessageDisplayed(parameters.get("text5"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("text5"), "1.1",
					parameters.get("text5") + " :displayed");
			org.testng.Assert.assertTrue(status);

			status = onboardingPage.isMessageDisplayed(parameters.get("text6"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("text6"), "1.2",
					parameters.get("text6") + " :displayed");
			org.testng.Assert.assertTrue(status);

			String[] messageList = parameters.get("text7").split("'");
			for (i = 0; i < messageList.length; i++) {
				status = onboardingPage.isMessageDisplayed(messageList[i]);
				reportLog(status, testContext.getName(), "Verify display:" + messageList[i], "1.3." + i,
						messageList[i] + " :displayed");
				org.testng.Assert.assertTrue(status);
			}

			status = onboardingPage.isButtonDisplayed(parameters.get("button3"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("button3"), "1.4",
					parameters.get("button3") + " :displayed");
			org.testng.Assert.assertTrue(status);

			status = onboardingPage.isButtonDisplayed(parameters.get("button4"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("button4"), "1.5",
					parameters.get("button4") + " :displayed");
			org.testng.Assert.assertTrue(status);

			onboardingPage.exploreButtonClick();
			status = onboardingPage.getHeader().contains(parameters.get("activationHeader"));
			reportLog(status, testContext.getName(), "Verify header displays:" + parameters.get("activationHeader"),
					"2.0", parameters.get("activationHeader") + " : header displayed");
			org.testng.Assert.assertTrue(status);

			onboardingPage.resendActivationClick();
			status = onboardingPage.getMessage().equals(parameters.get("emailSentMsg"));
			reportLog(status, testContext.getName(), "Verify message  displays:" + parameters.get("emailSentMsg"),
					"3.0", parameters.get("emailSentMsg") + " : message displayed");
			org.testng.Assert.assertTrue(status);

			status = onboardingPage.getHeader().equals(parameters.get("loginHeader"));
			reportLog(status, testContext.getName(), "Verify header displays:" + parameters.get("loginHeader"), "3.1",
					parameters.get("loginHeader") + " : header displayed");
			org.testng.Assert.assertTrue(status);

			LoginPage login = new LoginPage(driver);
			login.typeusername(parameters.get("admin_username"));
			login.typepassword(parameters.get("admin_password"));
			login.Clickonloginbutton();

			DeliveryStatusPage deliveryLink = new DeliveryStatusPage(driver);
			deliveryLink.gotoUser(companyEmail);
			deliveryLink.deliveryStatClick();
			status = (deliveryLink.numActivationEmailSent(companyEmail) == 2);
			reportLog(status, testContext.getName(), "Verify second activation mail", "3.2",
					"Verified second activation mail");
			org.testng.Assert.assertTrue(status);

			deliveryLink.resumeSession();
			deliveryLink.activateUser(companyEmail);
			deliveryLink.logout();

			login.typeusername(companyEmail);
			login.typepassword(parameters.get("password"));
			onboardingPage = login.Clickonloginbuttonforfirstlogin();

			status = onboardingPage.getHeader().equals(parameters.get("devicesHeader"));
			reportLog(status, testContext.getName(), "Verify header: " + parameters.get("devicesHeader"), "5.0",
					parameters.get("devicesHeader") + " : header verified.");
			org.testng.Assert.assertTrue(status);
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void skipFlowTest(String testDescription, String rallyLink, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		/*try {*/

			reportLog(true, testContext.getName(), "Rally Link: ",
					"Rally Link: " + String.format("<a href='%s'>" + rallyLink + "</a>", rallyLink));

			HomePage homePage = new HomePage(driver);
			CreateTrialAccountPage createAccountLink = homePage.tryItFreeButtonClick();
			String companyEmail = randomAlphaNumeric(10) + parameters.get("email");

			String[] fieldDataList = parameters.get("inputlist").split(";");
			String[] labelIDList = parameters.get("labelidlist").split(";");
			for (int i = 0; i < labelIDList.length; i++) {
				if (i == 2) {
					createAccountLink.enterFieldData(labelIDList[i], companyEmail);
				} else {
					createAccountLink.enterFieldData(labelIDList[i], fieldDataList[i]);
				}
			}
			createAccountLink.continueWithEmailClick();

			if (createAccountLink.isPhoneNumberDisplayed()) {
				createAccountLink.step2Phone(parameters.get("phone"));
			}
			if (createAccountLink.isEmployeeSizeDisplayed()) {
				createAccountLink.selectEmployeeSize(1);
			}
			createAccountLink.enterFieldData("password_input", parameters.get("password"));
			OnboardingPage onboardingPage = createAccountLink.tryItFreeStep2Click();

			boolean status = onboardingPage.isMessageDisplayed(parameters.get("text5"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("text5"), "1.1",
					parameters.get("text5") + " :displayed");
			org.testng.Assert.assertTrue(status);

			status = onboardingPage.isMessageDisplayed(parameters.get("text6"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("text6"), "1.2",
					parameters.get("text6") + " :displayed");
			org.testng.Assert.assertTrue(status);

			String[] messageList = parameters.get("text7").split("'");
			for (int i = 0; i < messageList.length; i++) {
				status = onboardingPage.isMessageDisplayed(messageList[i]);
				reportLog(status, testContext.getName(), "Verify display:" + messageList[i], "1.3." + i,
						messageList[i] + " :displayed");
				org.testng.Assert.assertTrue(status);
			}

			status = onboardingPage.isButtonDisplayed(parameters.get("button3"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("button3"), "1.4",
					parameters.get("button3") + " :displayed");
			org.testng.Assert.assertTrue(status);

			status = onboardingPage.isButtonDisplayed(parameters.get("button4"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("button4"), "1.5",
					parameters.get("button4") + " :displayed");
			org.testng.Assert.assertTrue(status);

			onboardingPage.clickButton(parameters.get("button3"));
			status = onboardingPage.getHeader().equals(parameters.get("devicesHeader"));
			reportLog(status, testContext.getName(), "Verify header: " + parameters.get("devicesHeader"), "2.0",
					parameters.get("devicesHeader") + " : header verified.");
			org.testng.Assert.assertTrue(status);

			status = onboardingPage.isMessageDisplayed(parameters.get("messagestarted"));
			reportLog(status, testContext.getName(), "Verify header displays:" + parameters.get("messagestarted"),
					"2.1", parameters.get("messagestarted") + " : header displayed");
			org.testng.Assert.assertTrue(status);

			status = onboardingPage.isMessageDisplayed(parameters.get("skipmessage"));
			reportLog(status, testContext.getName(), "Verify header displays:" + parameters.get("skipmessage"), "2.2",
					parameters.get("skipmessage") + " : header displayed");
			org.testng.Assert.assertTrue(status);

			onboardingPage.clickButton(parameters.get("skipmessage"));
			onboardingPage.clickButton(parameters.get("skipmessage"));
			onboardingPage.clickButton(parameters.get("skipmessage"));
			onboardingPage.clickButton(parameters.get("skipmessage"));

			status = onboardingPage.getHeader().contains(parameters.get("activationHeader"));
			reportLog(status, testContext.getName(), "Verify header displays:" + parameters.get("activationHeader"),
					"3.0", parameters.get("activationHeader") + " : header displayed");
			org.testng.Assert.assertTrue(status);

			status = onboardingPage.isButtonDisplayed(parameters.get("resendbutton"));
			reportLog(status, testContext.getName(), "Verify display:" + parameters.get("resendbutton"), "3.1",
					parameters.get("resendbutton") + " :displayed");
			org.testng.Assert.assertTrue(status);

			driver.get(PropertyUtils.getProperty("app.url"));
			LoginPage login = new LoginPage(driver);
			login.gotoLogin();
			login.typeusername(parameters.get("admin_username"));
			login.typepassword(parameters.get("admin_password"));
			login.Clickonloginbutton();

			DeliveryStatusPage deliveryLink = new DeliveryStatusPage(driver);
			deliveryLink.gotoUser(companyEmail);
			deliveryLink.deliveryStatClick();
			status = (deliveryLink.numActivationEmailSent(companyEmail) == 1);
			reportLog(status, testContext.getName(), "Verify activation mail", "4.0", "Verified activation mail");
			org.testng.Assert.assertTrue(status);

			deliveryLink.resumeSession();
			deliveryLink.activateUser(companyEmail);
			deliveryLink.logout();

			login.typeusername(companyEmail);
			login.typepassword(parameters.get("password"));
			onboardingPage = login.Clickonloginbuttonforfirstlogin();

			status = onboardingPage.getHeader().equals(parameters.get("devicesHeader"));
			reportLog(status, testContext.getName(), "Verify header: " + parameters.get("devicesHeader"), "5.0",
					parameters.get("devicesHeader") + " : header verified.");
			org.testng.Assert.assertTrue(status);

			status = onboardingPage.isMessageDisplayed(parameters.get("messagestarted"));
			reportLog(status, testContext.getName(), "Verify header displays:" + parameters.get("messagestarted"),
					"5.1", parameters.get("messagestarted") + " : header displayed");
			org.testng.Assert.assertTrue(status);

			status = onboardingPage.isMessageDisplayed(parameters.get("skipmessage"));
			reportLog(status, testContext.getName(), "Verify header displays:" + parameters.get("skipmessage"), "5.2",
					parameters.get("skipmessage") + " : header displayed");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		/*} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}*/
	}

}
