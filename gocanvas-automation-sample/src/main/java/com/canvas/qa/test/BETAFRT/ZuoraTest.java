package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.AdminBillingPage;
import com.canvas.qa.pages.CustomizePage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.HomePage;
import com.canvas.qa.pages.LicensesAndBilling;
import com.canvas.qa.pages.PricingPage;
import com.canvas.qa.pages.SearchUsersPage;
import com.canvas.qa.pages.SignUpStep2;
import com.canvas.qa.pages.UserPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.canvas.util.PropertyUtils;

//QA-355 : Zuora - Business /Annual Plan

public class ZuoraTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void firstTimePurchaseBusinessAnnual(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);
			LicensesAndBilling licensesBilling = new LicensesAndBilling(driver);
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();

			String[] labelIDList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");
			homePage.enterFieldData(labelIDList, fieldDataList, email);
			homePage.clickTryItFreeButton();

			boolean msgVerify = signUpStep2.verifyMessage(parameters.get("msg_1"));
			reportLog(msgVerify, testContext.getName(), "Verify " + parameters.get("msg_1") + " is displayed", "1.0",
					parameters.get("msg_1") + " is displayed after clicking on 'TRY IT FREE' button in Dashboard");
			org.testng.Assert.assertTrue(msgVerify);

			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);

			signUpStep2.clickGetStartedButton();

			boolean msgVerifyTahnkYouPage = signUpStep2.verifyMessageInThankYouPage(parameters.get("msg_4"));
			reportLog(msgVerifyTahnkYouPage, testContext.getName(),
					"Verify " + parameters.get("msg_4") + " is displayed", "2.0",
					parameters.get("msg_4") + " is displayed in thank you page after clicking on Get Strated Button");
			org.testng.Assert.assertTrue(msgVerifyTahnkYouPage);

			driver.get(PropertyUtils.getProperty("app.url"));

			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			String renewDateFormat = licensesBilling.getDateFormat();
			String renewDateYearly = userPage.findTodaysDate(parameters.get("planType"), renewDateFormat);

			dashboardPage.clickPricingMenu().selectPricingOption();

			// Read Price of Plans in run time
			String monthlyText = pricePage.planPriceAnnualy(parameters.get("plan")); // 45
			String businesspriceyearlyplan = monthlyText.substring(monthlyText.indexOf("$") + 1,
					monthlyText.indexOf("USD"));
			String yearlyText = pricePage.planPriceMonthly(parameters.get("plan"));
			String businesspricemonthlyplan = yearlyText.substring(yearlyText.indexOf("$") + 1,
					yearlyText.indexOf("month to"));// 51
			int perBusinessPricYearlyPlanCost = Integer.parseInt(businesspriceyearlyplan.trim()); // int 45

			boolean pricePageMsg = pricePage.verifyMessage(parameters.get("msg_2"));
			reportLog(pricePageMsg, testContext.getName(), "Verify " + parameters.get("msg_2") + " is displayed", "3.0",
					parameters.get("msg_2")
							+ " is displayed in pricing page in header after clicking on Pricing sub-link");
			org.testng.Assert.assertTrue(pricePageMsg);

			boolean businessPrice = pricePage.getPriceAnnualPerUser(businesspriceyearlyplan, parameters.get("plan"));
			reportLog(businessPrice, testContext.getName(), "Verify business per user price in Pricing Page", "4.0",
					"Yearly Business price per user is :" + businesspriceyearlyplan + " is verified in pricing page");
			org.testng.Assert.assertTrue(businessPrice);

			boolean professionalPrice = pricePage.getPriceMonthlyPerUser(businesspricemonthlyplan,
					parameters.get("plan"));
			reportLog(professionalPrice, testContext.getName(), "Verify monthly  price in Pricing Page", "5.0",
					"Monthly Business price per user is :  " + businesspricemonthlyplan + " verified in pricing page");
			org.testng.Assert.assertTrue(professionalPrice);

			boolean checkoutmsg = pricePage.clickOnSelectPlan(parameters.get("msg_3"));
			reportLog(checkoutmsg, testContext.getName(), "Verify " + parameters.get("msg_3") + " is displayed", "6.0",
					parameters.get("msg_3")
							+ " page is open after clicking on 'Select Plan' button in show pricing plans page");
			org.testng.Assert.assertTrue(checkoutmsg);

			String countString = userPage.getLicenseCount(0);
			int noOfLicense = Integer.parseInt(countString);
			int licenseCostAnnual = perBusinessPricYearlyPlanCost * noOfLicense; // int 90 (45*2)
			int totaAannualCost = licenseCostAnnual * 12; // int 1080 (90*12)
			String annualCostLiceses = Integer.toString(licenseCostAnnual); // Covert 90 into string
			NumberFormat formatter = new DecimalFormat("#,###");
			String totalAmount = formatter.format(totaAannualCost); // String 1,028 (comma seprated)
			boolean selectedLicenseCount = userPage.selectlicenseCount(countString);

			reportLog(selectedLicenseCount, testContext.getName(), "Verify selected license counte in Pricing Page",
					"7.0",
					"Selected license " + countString + " is verified in user page under Number of Licenses section");
			org.testng.Assert.assertTrue(selectedLicenseCount);

			userPage.clickNextBtnNumberOfLicenses();
			boolean paymentAnnualType = userPage.verifyPaymentType(parameters.get("text1"));
			reportLog(paymentAnnualType, testContext.getName(), "Verify text 'Annual' in Billing  Frequency page",
					"8.0", "Payment type 'Annual' display in Billing Frequency section ");
			org.testng.Assert.assertTrue(paymentAnnualType);

			boolean paymentMonthlyType = userPage.verifyPaymentType(parameters.get("text2"));
			reportLog(paymentMonthlyType, testContext.getName(), "Verify text 'Monthly' in Billing Frequency page",
					"9.0", "Payment type 'Monthly' display in Billing Frequency section ");
			org.testng.Assert.assertTrue(paymentMonthlyType);

			boolean paymentAnnualRate = userPage.verifyPlanRate(annualCostLiceses);
			reportLog(paymentAnnualRate, testContext.getName(), "Verify business Annual price in user Page", "10.0",
					"Annual Business price per user " + annualCostLiceses
							+ " is verified in user page in Billing Frequency section");
			org.testng.Assert.assertTrue(paymentAnnualRate);

			boolean businessAnnualRate = userPage.verifyTotalCostAnnualy(totalAmount);
			reportLog(businessAnnualRate, testContext.getName(), "Verify business annual Rate for '1' liceses", "11.0",
					"Annual 'Total Annual Cost' is " + totalAmount
							+ " verified in user page under Billing Frequency section for "
							+ (parameters.get("licenseCount") + " licenses"));
			org.testng.Assert.assertTrue(businessAnnualRate);
			userPage.clickNextBtnBillFreq();
			userPage.waitForBillingInfo();
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();

			boolean firstName = userPage.isErrorMessageDisplayed(parameters.get("fn_error_message"));
			reportLog(firstName, testContext.getName(), "Verify error message against first name", "12.0",
					parameters.get("fn_error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(firstName);
			boolean lastName = userPage.isErrorMessageDisplayed(parameters.get("ln_error_message"));
			reportLog(lastName, testContext.getName(), "Verify error message against last name", "12.1",
					parameters.get("ln_error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(lastName);
			boolean emails = userPage.isErrorMessageDisplayed(parameters.get("email_error_message"));
			reportLog(emails, testContext.getName(), "Verify error message against last name", "12.2",
					parameters.get("email_error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(emails);
			boolean phone = userPage.isErrorMessageDisplayed(parameters.get("phone_error_message"));
			reportLog(phone, testContext.getName(), "Verify error message against phone number", "12.3",
					parameters.get("phone_error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(phone);
			boolean address = userPage.isErrorMessageDisplayed(parameters.get("address_error_message"));
			reportLog(address, testContext.getName(), "Verify error message against phone number", "12.4",
					parameters.get("address_error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(address);
			boolean city = userPage.isErrorMessageDisplayed(parameters.get("city_error_message"));
			reportLog(city, testContext.getName(), "Verify error message against phone number", "12.5",
					parameters.get("city_error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(city);
			boolean zip = userPage.isErrorMessageDisplayed(parameters.get("zip__error_message"));
			reportLog(zip, testContext.getName(), "Verify error message against phone number", "12.6",
					parameters.get("zip__error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(zip);
			String[] labelList = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList11 = parameters.get("filedinputlist").split(";");

			userPage.enterFieldData(labelList, fieldDataList11);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();

			boolean dataSaved = userPage.verifyDataSaved();
			reportLog(dataSaved, testContext.getName(), "Verify data is saved and next button display in ", "13",
					"Data is saved and Credit Card Info section open with Next button");
			org.testng.Assert.assertTrue(dataSaved);

			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.switchToIframe(By.id("z_hppm_iframe"));

			boolean nameOnCard = userPage.isCardErrorMessageDisplayed(parameters.get("ccfieldnamelabel"),
					parameters.get("nameoncard_error_message"));
			reportLog(nameOnCard, testContext.getName(), "Verify error message against nameoncard name", "14.0",
					parameters.get("nameoncard_error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(nameOnCard);

			boolean ccNumber = userPage.isCardErrorMessageDisplayed(parameters.get("ccfieldnumberlabel"),
					parameters.get("ccnumber_error_message"));
			reportLog(ccNumber, testContext.getName(), "Verify error message against credit card number", "14.1",
					parameters.get("ccnumber_error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(ccNumber);

			boolean expDate = userPage.isCardErrorMessageDisplayed(parameters.get("ccfielexpdatelabel"),
					parameters.get("expdate_error_message"));
			reportLog(expDate, testContext.getName(), "Verify error message against last name", "14.2",
					parameters.get("expdate_error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(expDate);

			boolean cvvNumber = userPage.isCardErrorMessageDisplayed(parameters.get("ccfieldCvvLabel"),
					parameters.get("cvv_error_message"));
			reportLog(cvvNumber, testContext.getName(), "Verify error message against phone number", "14.3",
					parameters.get("cvv_error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(cvvNumber);

			String[] ccLabelList = parameters.get("ccfieldlabelist").split(";");
			String[] ccFieldDataList = parameters.get("ccfiledinputlist").split(";");

			// *** Cover Scenario when user enter expire credit card information
			userPage.enterInvalidCcData(ccLabelList, ccFieldDataList, parameters.get("invalidccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("invalidccYear"), parameters.get("ccYearxpath"));

			userPage.selectCCMonth(parameters.get("invalidccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("invalidccYear"), parameters.get("ccYearxpath"));

			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();

			userPage.switchToIframe(By.id("z_hppm_iframe"));

			boolean errorMessage = userPage.ccValdationErrorMsg(parameters.get("cvv_error_messagecard"));
			reportLog(errorMessage, testContext.getName(), "Verify error message against credit card", "14.4",
					parameters.get("cvv_error_messagecard")
							+ " error message displayed when user enter expiry card detail");

			/**
			 * * Verify validation for Invalid Card Number
			 */

			String[] ccFieldDataListInvalid = parameters.get("ccfiledinputlistinvaildcard").split(";");
			userPage.enterInvalidCcData(ccLabelList, ccFieldDataListInvalid, parameters.get("invalidccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("invalidccYear"), parameters.get("ccYearxpath"));

			userPage.selectCCMonth(parameters.get("invalidccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("invalidccYear"), parameters.get("ccYearxpath"));

			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();

			userPage.switchToIframe(By.id("z_hppm_iframe"));

			boolean errorMessageInvalidCard = userPage
					.cardNumberValdationErrorMsg(parameters.get("card_error_message"));
			reportLog(errorMessageInvalidCard, testContext.getName(), "Verify error message against credit card",
					"14.5", parameters.get("card_error_message")
							+ " error message displayed when user enter invaild credit card number");

			/**
			 * Enter credit card valid data
			 */

			userPage.enterCcData(ccLabelList, ccFieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();

			boolean confirmPurchaseButton = userPage.confrimPurchaseButtonText(parameters.get("confpurButtontxt"));
			reportLog(confirmPurchaseButton, testContext.getName(), "Verify confirm button text ", "15.0",
					"User redirect to " + parameters.get("confpurButtontxt")
							+ " section, its verfied user fill credit card deatil sucessfully");
			org.testng.Assert.assertTrue(confirmPurchaseButton);

			String[] fieldXpath = parameters.get("fieldxpath").split(";");
			String[] fieldData = parameters.get("data").split(";");
			String[] fieldLabel = parameters.get("fieldlabel").split(";");
			String stepsArry_confirm[] = new String[] { "16.1", "16.2", "16.3" };

			userPage.verifyConfirmPurchase(testContext, fieldXpath, fieldData, stepsArry_confirm, fieldLabel);

			boolean confirmTotalAnnualCost = userPage.confirmPurchaseTotalDue(totalAmount);
			reportLog(confirmTotalAnnualCost, testContext.getName(), "Verify total annual cost ", "16.4",
					"Total due " + (totalAmount) + " is verified in user page under Confirm Purchase section");

			boolean renewalDateConfirm = userPage.verifyRenewalDate(renewDateYearly);
			reportLog(renewalDateConfirm, testContext.getName(), "Verify renewal date ", "16.5",
					"Renewal Date " + (renewDateYearly) + " is verified in user page under Confirm Purchase section");
			org.testng.Assert.assertTrue(renewalDateConfirm);
			String[] fieldXpathSummary = parameters.get("fieldlabelSummary").split(";");
			String stepsArrySummary[] = new String[] { "17.1", "17.2", "17.3" };

			userPage.verifyBillingSummary(testContext, fieldXpathSummary, fieldData, stepsArrySummary, fieldLabel);

			boolean summaryTotalAnnualCost = userPage.summaryPurchaseTotalDue(totalAmount);
			reportLog(summaryTotalAnnualCost, testContext.getName(), "Verify total annual cost ", "17.4",
					"Total due " + (totalAmount) + " is verified in user page under Confirm Purchase section");
			org.testng.Assert.assertTrue(summaryTotalAnnualCost);
			boolean renewalDateSummary = userPage.verifyRenewalDate(renewDateYearly);
			reportLog(renewalDateSummary, testContext.getName(), "Verify renewal date ", "17.5",
					"Renewal Date " + (renewDateYearly) + " is verified in user page under Billing Summary section");
			org.testng.Assert.assertTrue(renewalDateSummary);
			boolean confirmPurchase = userPage.confirmPurchase(parameters.get("headertext"));
			reportLog(confirmPurchase, testContext.getName(), "Verify header text ", "18.0",
					"User redirect to " + parameters.get("headertext") + " Page and its verfied liceses is purchased ");
			org.testng.Assert.assertTrue(confirmPurchase);

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-394 :Zuora - First Time Purchase (Business/Monthly) Automation
	@Test
	@Parameters({ "testdescription" })
	public void firstTimePurchaseBusinessMonthly(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);
			LicensesAndBilling licensesBilling = new LicensesAndBilling(driver);
			String random_email = randomAlphaNumeric(10) + "@yopmail.com";
			String email = random_email.toLowerCase();
			String[] labelList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");

			homePage.enterFieldData(labelList, fieldDataList, email);
			homePage.clickTryItFreeButton();

			boolean msgVerify = signUpStep2.verifyMessage(parameters.get("msg_1"));
			reportLog(msgVerify, testContext.getName(), "Verify " + parameters.get("msg_1") + " is displayed", "1.0",
					parameters.get("msg_1") + " is displayed after clicking on 'TRY IT FREE' button in Dashboard");
			org.testng.Assert.assertTrue(msgVerify);

			String[] passlabelIDList = parameters.get("labelidlist2").split(";");
			String[] passfieldDataList = parameters.get("inputlist2").split(";");
			signUpStep2.enterFieldData(passlabelIDList, passfieldDataList);
			signUpStep2.clickGetStartedButton();

			boolean msgVerifyThankYouPage = signUpStep2.verifyMessageInThankYouPage(parameters.get("msg_4"));
			reportLog(msgVerifyThankYouPage, testContext.getName(),
					"Verify " + parameters.get("msg_4") + " is displayed", "2.0",
					parameters.get("msg_4") + " is displayed in thank you page after clicking on Get Strated Button");

			org.testng.Assert.assertTrue(msgVerifyThankYouPage);
			driver.get(PropertyUtils.getProperty("app.url"));
			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			String renewDateFormat = licensesBilling.getDateFormat();
			String renewDateMonthly = userPage.findTodaysDate(parameters.get("planType"), renewDateFormat);
			dashboardPage.clickPricingMenu().selectPricingOption();

			// Read Price of Plans in run time
			String monthlyText = pricePage.planPriceAnnualy(parameters.get("plan")); // 45
			String businessPriceYearlyPlan = monthlyText.substring(monthlyText.indexOf("$") + 1,
					monthlyText.indexOf("USD"));
			String yearlyText = pricePage.planPriceMonthly(parameters.get("plan"));
			String businessPriceMonthlyPlan = yearlyText.substring(yearlyText.indexOf("$") + 1,
					yearlyText.indexOf("month to"));// 51
			int perbusinesspriceMonthlyPlanCost = Integer.parseInt(businessPriceMonthlyPlan.trim()); // int 51

			boolean pricePageMsg = pricePage.verifyMessage(parameters.get("msg_2"));
			reportLog(pricePageMsg, testContext.getName(), "Verify " + parameters.get("msg_2") + " is displayed", "3.0",
					parameters.get("msg_2")
							+ " is displayed in pricing page in header after clicking on Pricing sub-link");
			org.testng.Assert.assertTrue(pricePageMsg);

			boolean businessPriceAnnualy = pricePage.getPriceAnnualPerUser(businessPriceYearlyPlan,
					parameters.get("plan"));
			reportLog(businessPriceAnnualy, testContext.getName(), "Verify business per user price in Pricing Page",
					"4.0",
					"Yearly Business price per user is :" + businessPriceYearlyPlan + " is verified in pricing page");

			boolean professionalPriceMonthly = pricePage.getPriceMonthlyPerUser(businessPriceMonthlyPlan,
					parameters.get("plan"));
			reportLog(professionalPriceMonthly, testContext.getName(), "Verify monthly  price in Pricing Page", "5.0",
					"Monthly Business price per user is :  " + businessPriceMonthlyPlan + " verified in pricing page");

			boolean checkOutMsg = pricePage.clickOnSelectPlan(parameters.get("msg_3"));
			reportLog(checkOutMsg, testContext.getName(), "Verify " + parameters.get("msg_3") + " is displayed", "6.0",
					parameters.get("msg_3")
							+ " page is open after clicking on 'Select Plan' button in show pricing plans page");
			org.testng.Assert.assertTrue(pricePageMsg);

			String countString = userPage.getLicenseCount(0);
			int noOfLicense = Integer.parseInt(countString);
			int licensesCostMonthly = perbusinesspriceMonthlyPlanCost * noOfLicense; // int 102 (51*2)
			int totaAnnualCost = licensesCostMonthly * 12; // int 1224 (102*12)
			String annualCostLiceses = Integer.toString(licensesCostMonthly); // 102 string
			NumberFormat formatter = new DecimalFormat("#,###");
			String total_annual_cost = formatter.format(totaAnnualCost); // String 1,224

			boolean selectedLicenseCount = userPage.selectlicenseCount(countString);
			reportLog(selectedLicenseCount, testContext.getName(), "Verify selected license counte in Pricing Page",
					"7.0",
					"Selected license " + countString + " is verified in user page under Number of Licenses section");

			userPage.clickNextBtnNumberOfLicenses();
			boolean paymentAnnualType = userPage.verifyPaymentType(parameters.get("text1"));
			reportLog(paymentAnnualType, testContext.getName(), "Verify text 'Annual' in Billing  Frequency page",
					"8.0", "Payment type 'Annual' display in Billing Frequency section ");

			boolean paymentMonthlyType = userPage.verifyPaymentType(parameters.get("text2"));
			reportLog(paymentMonthlyType, testContext.getName(), "Verify text 'Monthly' in Billing Frequency page",
					"9.0", "Payment type 'Monthly' display in Billing Frequency section ");

			boolean paymentAnnualRate = userPage.verifyPlanRate(annualCostLiceses);
			reportLog(paymentAnnualRate, testContext.getName(), "Verify business Monthly price in user Page", "10.0",
					"Monthly Business price " + annualCostLiceses
							+ " is verified in user page in Billing Frequency section for license " + (noOfLicense));

			boolean businessAnnualRate = userPage.verifyTotalCostMonthly(total_annual_cost);
			reportLog(businessAnnualRate, testContext.getName(), "Verify business annual Rate for '2' liceses", "11.0",
					"Monthly Business price is " + total_annual_cost
							+ " verified in user page under Billing Frequency section for license " + (noOfLicense));

			userPage.selectMonthlyRadioOption();
			userPage.clickNextBtnBillFreq();
			userPage.waitForBillingInfo();
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			boolean firstName = userPage.isErrorMessageDisplayed(parameters.get("fn_error_message"));
			reportLog(firstName, testContext.getName(), "Verify error message against first name", "12.0",
					parameters.get("fn_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean lastName = userPage.isErrorMessageDisplayed(parameters.get("ln_error_message"));
			reportLog(lastName, testContext.getName(), "Verify error message against last name", "12.1",
					parameters.get("ln_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean emails = userPage.isErrorMessageDisplayed(parameters.get("email_error_message"));
			reportLog(emails, testContext.getName(), "Verify error message against last name", "12.2",
					parameters.get("email_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean phone = userPage.isErrorMessageDisplayed(parameters.get("phone_error_message"));
			reportLog(phone, testContext.getName(), "Verify error message against phone number", "12.3",
					parameters.get("phone_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean address = userPage.isErrorMessageDisplayed(parameters.get("address_error_message"));
			reportLog(address, testContext.getName(), "Verify error message against phone number", "12.4",
					parameters.get("address_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean city = userPage.isErrorMessageDisplayed(parameters.get("city_error_message"));
			reportLog(city, testContext.getName(), "Verify error message against phone number", "12.5",
					parameters.get("city_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean zip = userPage.isErrorMessageDisplayed(parameters.get("zip__error_message"));
			reportLog(zip, testContext.getName(), "Verify error message against phone number", "12.6",
					parameters.get("zip__error_message")
							+ " error message displayed when user keep field blank and click on next button");

			String[] labelIDList1 = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList1 = parameters.get("filedinputlist").split(";");

			userPage.enterFieldData(labelIDList1, fieldDataList1);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();

			boolean dataSaved = userPage.verifyDataSaved();
			reportLog(dataSaved, testContext.getName(), "Verify data is saved and next button display in ", "13",
					"Data is saved and Credit Card Info section open with Next button");

			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.switchToIframe(By.id("z_hppm_iframe"));

			boolean nameOnCard = userPage.isCardErrorMessageDisplayed(parameters.get("ccfieldnamelabel"),
					parameters.get("nameoncard_error_message"));
			reportLog(nameOnCard, testContext.getName(), "Verify error message against nameoncard name", "14.0",
					parameters.get("nameoncard_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean ccNumber = userPage.isCardErrorMessageDisplayed(parameters.get("ccfieldnumberlabel"),
					parameters.get("ccnumber_error_message"));
			reportLog(ccNumber, testContext.getName(), "Verify error message against credit card number", "14.1",
					parameters.get("ccnumber_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean expDate = userPage.isCardErrorMessageDisplayed(parameters.get("ccfielexpdatelabel"),
					parameters.get("expdate_error_message"));
			reportLog(expDate, testContext.getName(), "Verify error message against last name", "14.2",
					parameters.get("expdate_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean cvvNumber = userPage.isCardErrorMessageDisplayed(parameters.get("ccfieldCvvLabel"),
					parameters.get("cvv_error_message"));
			reportLog(cvvNumber, testContext.getName(), "Verify error message against phone number", "14.3",
					parameters.get("cvv_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			String[] cclabelIDList = parameters.get("ccfieldlabelist").split(";");
			String[] ccfieldDataList = parameters.get("ccfiledinputlist").split(";");
			userPage.enterCcData(cclabelIDList, ccfieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();

			boolean confirmpurchasebutton = userPage.confrimPurchaseButtonText(parameters.get("confpurButtontxt"));
			reportLog(confirmpurchasebutton, testContext.getName(), "Verify confirm button text ", "15.0",
					"User redirect to " + parameters.get("confpurButtontxt")
							+ " section, its verfied user fill credit card deatil sucessfully  ");

			String[] fieldXpath = parameters.get("fieldxpath").split(";");
			String[] fieldData = parameters.get("data").split(";");
			String[] fieldLabel = parameters.get("fieldlabel").split(";");
			// String renewDateMonthly = userPage.findTodayDate(parameters.get("planType"));
			String stepsArry_confirm[] = new String[] { "15.1", "15.2" };

			userPage.verifyConfirmPurchase(testContext, fieldXpath, fieldData, stepsArry_confirm, fieldLabel);
			// Verify Confirm Purchase Section data

			boolean licensesCountConfirm = userPage.getLicenseCountConfirmPurchase().equals(countString);
			reportLog(licensesCountConfirm, testContext.getName(), "Verify Number of llicense ", "15.3",
					"Licenses count: " + (noOfLicense) + " is verified in user page under Confirm Purchase section");

			boolean confirmTotalAnnualCost = userPage.confirmPurchaseTotalDue(annualCostLiceses);
			reportLog(confirmTotalAnnualCost, testContext.getName(), "Verify total annual cost ", "15.4",
					"Total due " + (annualCostLiceses) + " is verified in user page under Confirm Purchase section");

			boolean renewalDateConfirm = userPage.verifyRenewalDate(renewDateMonthly);
			reportLog(renewalDateConfirm, testContext.getName(), "Verify renewal date ", "15.5",
					"Renewal Date " + (renewDateMonthly) + " is verified in user page under Confirm Purchase section");

			String[] fieldxpathsummary = parameters.get("fieldlabelSummary").split(";");
			String stepsArry_summary[] = new String[] { "16.1", "16.2" };

			userPage.verifyBillingSummary(testContext, fieldxpathsummary, fieldData, stepsArry_summary, fieldLabel);

			boolean licensesCountCofirmSummary = userPage.getLicenseCountBillingSummary().equals(countString);
			reportLog(licensesCountCofirmSummary, testContext.getName(), "Verify Number of llicense ", "16.3",
					"Licenses count: " + (noOfLicense) + " is verified in user page under Billing Summary section");

			boolean summaryTotalAnnualCost = userPage.summaryPurchaseTotalDue(annualCostLiceses);
			reportLog(summaryTotalAnnualCost, testContext.getName(), "Verify total annual cost ", "16.4",
					"Total due " + (annualCostLiceses) + " is verified in user page under Billing Summary section");

			boolean renewalDateSummary = userPage.verifyRenewalDate(renewDateMonthly);
			reportLog(renewalDateSummary, testContext.getName(), "Verify renewal date ", "16.5",
					"Renewal Date " + (renewDateMonthly) + " is verified in user page under Billing Summary section");

			boolean confirmPurchase = userPage.confirmPurchase(parameters.get("headertext"));
			reportLog(confirmPurchase, testContext.getName(), "Verify header text ", "17.0",
					"User redirect to " + parameters.get("headertext") + " Page and its verfied liceses is purchased ");

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-393 : First Time Purchase (Professional/Annual)

	@Test
	@Parameters({ "testdescription" })
	public void firstTimePurchaseProfessionalAnnual(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);
			LicensesAndBilling licensesBilling = new LicensesAndBilling(driver);
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();

			String[] labelIDList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");
			homePage.enterFieldData(labelIDList, fieldDataList, email);
			homePage.clickTryItFreeButton();

			boolean msgVerify = signUpStep2.verifyMessage(parameters.get("msg_1"));
			reportLog(msgVerify, testContext.getName(), "Verify " + parameters.get("msg_1") + " is displayed", "1.0",
					parameters.get("msg_1") + " is displayed after clicking on 'TRY IT FREE' button in Dashboard");
			org.testng.Assert.assertTrue(msgVerify);

			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);

			signUpStep2.clickGetStartedButton();

			boolean msgVerifyTahnkYouPage = signUpStep2.verifyMessageInThankYouPage(parameters.get("msg_4"));
			reportLog(msgVerifyTahnkYouPage, testContext.getName(),
					"Verify " + parameters.get("msg_4") + " is displayed", "2.0",
					parameters.get("msg_4") + " is displayed in thank you page after clicking on Get Strated Button");
			org.testng.Assert.assertTrue(msgVerifyTahnkYouPage);

			driver.get(PropertyUtils.getProperty("app.url"));

			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			String renewDateFormat = licensesBilling.getDateFormat();
			String renewDateYearly = userPage.findTodaysDate(parameters.get("planType"), renewDateFormat);
			dashboardPage.clickPricingMenu().selectPricingOption();

			// Read Price of Plans in run time
			String monthlyText = pricePage.planPriceAnnualy(parameters.get("plan")); // 55
			String professionalpriceyearlyplan = monthlyText.substring(monthlyText.indexOf("$") + 1,
					monthlyText.indexOf("USD"));

			String yearlyText = pricePage.planPriceMonthly(parameters.get("plan"));
			String professionalPriceMonthlyPlan = yearlyText.substring(yearlyText.indexOf("$") + 1,
					yearlyText.indexOf("month to"));// 55

			int perprofessionalsPriceYearlyPlanCost = Integer.parseInt(professionalpriceyearlyplan.trim()); // int 55

			boolean pricePageMsg = pricePage.verifyMessage(parameters.get("msg_2"));
			reportLog(pricePageMsg, testContext.getName(), "Verify " + parameters.get("msg_2") + " is displayed", "3.0",
					parameters.get("msg_2")
							+ " is displayed in pricing page in header after clicking on Pricing sub-link");
			org.testng.Assert.assertTrue(pricePageMsg);

			boolean businessPrice = pricePage.getPriceAnnualPerUser(professionalpriceyearlyplan,
					parameters.get("plan"));
			reportLog(businessPrice, testContext.getName(), "Verify professional per user price in Pricing Page", "4.0",
					"Yearly Business price per user is :" + professionalpriceyearlyplan
							+ " is verified in pricing page for Professional plan");

			boolean professionalPrice = pricePage.getPriceMonthlyPerUser(professionalPriceMonthlyPlan,
					parameters.get("plan"));
			reportLog(professionalPrice, testContext.getName(), "Verify monthly  price in Pricing Page", "5.0",
					"Monthly professional price per user is :  " + professionalPriceMonthlyPlan
							+ " verified in pricing page for Professional plan");

			boolean checkOutMsg = pricePage.clickOnSelectPlanProf(parameters.get("msg_3"));
			reportLog(checkOutMsg, testContext.getName(), "Verify " + parameters.get("msg_3") + " is displayed", "6.0",
					parameters.get("msg_3")
							+ " page is open after clicking on 'Select Plan' button in show pricing plans page");
			org.testng.Assert.assertTrue(checkOutMsg);

			String countString = userPage.getLicenseCount(0);
			int noOfLicenses = Integer.parseInt(countString);
			int licenseCostAnnual = perprofessionalsPriceYearlyPlanCost * noOfLicenses; // int 110 (55*2)
			int totalAnnualCost = licenseCostAnnual * 12; // int 2640 (110*12)
			String annualCostLiceses = Integer.toString(licenseCostAnnual); // Covert 110 into string
			NumberFormat formatter = new DecimalFormat("#,###");
			String total_annual_cost = formatter.format(totalAnnualCost); // String 2,640 (comma seprated)

			boolean selectedLicenseCount = userPage.selectlicenseCount(countString);
			reportLog(selectedLicenseCount, testContext.getName(), "Verify selected license counte in Pricing Page",
					"7.0",
					"Selected license " + countString + " is verified in user page under Number of Licenses section");

			userPage.clickNextBtnNumberOfLicenses();
			boolean paymentAnnualType = userPage.verifyPaymentType(parameters.get("text1"));
			reportLog(paymentAnnualType, testContext.getName(), "Verify text 'Annual' in Billing  Frequency page",
					"8.0", "Payment type 'Annual' display in Billing Frequency section ");

			boolean paymentMonthlyType = userPage.verifyPaymentType(parameters.get("text2"));
			reportLog(paymentMonthlyType, testContext.getName(), "Verify text 'Monthly' in Billing Frequency page",
					"9.0", "Payment type 'Monthly' display in Billing Frequency section ");

			boolean paymentAnnualRate = userPage.verifyPlanRate(annualCostLiceses);
			reportLog(paymentAnnualRate, testContext.getName(), "Verify professional Annual price in user Page", "10.0",
					"Annual professional price per user " + annualCostLiceses
							+ " is verified in user page in Billing Frequency section for license " + (noOfLicenses));

			boolean businessAnnualRate = userPage.verifyTotalCostAnnualy(total_annual_cost);
			reportLog(businessAnnualRate, testContext.getName(), "Verify professional annual Rate for '1' liceses",
					"11.0", "Annual 'Total Annual Cost' is " + total_annual_cost
							+ " is verified in user page in Billing Frequency section for " + (noOfLicenses));

			userPage.clickNextBtnBillFreq();
			userPage.waitForBillingInfo();
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();

			boolean firstName = userPage.isErrorMessageDisplayed(parameters.get("fn_error_message"));
			reportLog(firstName, testContext.getName(), "Verify error message against first name", "12.0",
					parameters.get("fn_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean lastName = userPage.isErrorMessageDisplayed(parameters.get("ln_error_message"));
			reportLog(lastName, testContext.getName(), "Verify error message against last name", "12.1",
					parameters.get("ln_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean emails = userPage.isErrorMessageDisplayed(parameters.get("email_error_message"));
			reportLog(emails, testContext.getName(), "Verify error message against last name", "12.2",
					parameters.get("email_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean phone = userPage.isErrorMessageDisplayed(parameters.get("phone_error_message"));
			reportLog(phone, testContext.getName(), "Verify error message against phone number", "12.3",
					parameters.get("phone_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean address = userPage.isErrorMessageDisplayed(parameters.get("address_error_message"));
			reportLog(address, testContext.getName(), "Verify error message against phone number", "12.4",
					parameters.get("address_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean city = userPage.isErrorMessageDisplayed(parameters.get("city_error_message"));
			reportLog(city, testContext.getName(), "Verify error message against phone number", "12.5",
					parameters.get("city_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean zip = userPage.isErrorMessageDisplayed(parameters.get("zip__error_message"));
			reportLog(zip, testContext.getName(), "Verify error message against phone number", "12.6",
					parameters.get("zip__error_message")
							+ " error message displayed when user keep field blank and click on next button");

			String[] labelIDList11 = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList11 = parameters.get("filedinputlist").split(";");

			userPage.enterFieldData(labelIDList11, fieldDataList11);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();

			boolean dataSaved = userPage.verifyDataSaved();
			reportLog(dataSaved, testContext.getName(), "Verify data is saved and next button display in ", "13",
					"Data is saved and Credit Card Info section open with Next button");

			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.switchToIframe(By.id("z_hppm_iframe"));

			boolean nameOnCard = userPage.isCardErrorMessageDisplayed(parameters.get("ccfieldnamelabel"),
					parameters.get("nameoncard_error_message"));
			reportLog(nameOnCard, testContext.getName(), "Verify error message against nameoncard name", "14.0",
					parameters.get("nameoncard_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean ccNumber = userPage.isCardErrorMessageDisplayed(parameters.get("ccfieldnumberlabel"),
					parameters.get("ccnumber_error_message"));
			reportLog(ccNumber, testContext.getName(), "Verify error message against credit card number", "14.1",
					parameters.get("ccnumber_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean expDate = userPage.isCardErrorMessageDisplayed(parameters.get("ccfielexpdatelabel"),
					parameters.get("expdate_error_message"));
			reportLog(expDate, testContext.getName(), "Verify error message against last name", "14.2",
					parameters.get("expdate_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean cvvNumber = userPage.isCardErrorMessageDisplayed(parameters.get("ccfieldCvvLabel"),
					parameters.get("cvv_error_message"));
			reportLog(cvvNumber, testContext.getName(), "Verify error message against phone number", "14.3",
					parameters.get("cvv_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			String[] cclabelIDList = parameters.get("ccfieldlabelist").split(";");
			String[] ccfieldDataList = parameters.get("ccfiledinputlist").split(";");
			userPage.enterCcData(cclabelIDList, ccfieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();

			boolean confirmPurchaseButton = userPage.confrimPurchaseButtonText(parameters.get("confpurButtontxt"));
			reportLog(confirmPurchaseButton, testContext.getName(), "Verify confirm button text ", "15.0",
					"User redirect to " + parameters.get("confpurButtontxt")
							+ " section, its verfied user fill credit card deatil sucessfully  ");

			String[] fieldXpath = parameters.get("fieldxpath").split(";");
			String[] fieldData = parameters.get("data").split(";");
			String[] fieldLabel = parameters.get("fieldlabel").split(";");
			String stepsArry_confirm[] = new String[] { "15.1", "15.2" };

			userPage.verifyConfirmPurchase(testContext, fieldXpath, fieldData, stepsArry_confirm, fieldLabel);

			boolean licensesCountConfirm = userPage.getLicenseCountConfirmPurchase().equals(countString);
			reportLog(licensesCountConfirm, testContext.getName(), "Verify Number of llicense ", "15.3",
					"Licenses count: " + (noOfLicenses) + " is verified in user page under Confirm Purchase section");

			boolean confirmTotalAnnualCost = userPage.confirmPurchaseTotalDue(total_annual_cost);
			reportLog(confirmTotalAnnualCost, testContext.getName(), "Verify total annual cost ", "15.4",
					"Total due " + (total_annual_cost) + " is verified in user page under Confirm Purchase section");

			boolean renewalDateConfirm = userPage.verifyRenewalDate(renewDateYearly);
			reportLog(renewalDateConfirm, testContext.getName(), "Verify renewal date ", "15.5",
					"Renewal Date " + (renewDateYearly) + " is verified in user page under Confirm Purchase section");

			String[] fieldXpathSummary = parameters.get("fieldlabelSummary").split(";");
			String stepsArrySummary[] = new String[] { "16.1", "16.2" };

			userPage.verifyBillingSummary(testContext, fieldXpathSummary, fieldData, stepsArrySummary, fieldLabel);

			boolean licensesCountBilling = userPage.getLicenseCountBillingSummary().equals(countString);
			reportLog(licensesCountBilling, testContext.getName(), "Verify Number of llicense ", "16.3",
					"Licenses count: " + (noOfLicenses) + " is verified in user page under Billing Summary section");

			boolean summaryTotalAnnualCost = userPage.summaryPurchaseTotalDue(total_annual_cost);
			reportLog(summaryTotalAnnualCost, testContext.getName(), "Verify total annual cost ", "16.4",
					"Total due " + (total_annual_cost) + " is verified in user page under Billing Summary section");

			boolean renewalDateSummary = userPage.verifyRenewalDate(renewDateYearly);
			reportLog(renewalDateSummary, testContext.getName(), "Verify renewal date ", "16.5",
					"Renewal Date " + (renewDateYearly) + " is verified in user page under Billing Summary section");

			boolean confirmPurchase = userPage.confirmPurchase(parameters.get("headertext"));
			reportLog(confirmPurchase, testContext.getName(), "Verify header text ", "17.0",
					"User redirect to " + parameters.get("headertext") + " Page and its verfied liceses is purchased ");

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-395 First Time Purchase (Professional/Monthly) Automation
	@Test
	@Parameters({ "testdescription" })
	public void firstTimePurchaseProfessionalMonthly(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);
			LicensesAndBilling licensesBilling = new LicensesAndBilling(driver);
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();

			String[] labelIDList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");
			homePage.enterFieldData(labelIDList, fieldDataList, email);
			homePage.clickTryItFreeButton();

			boolean msgVerify = signUpStep2.verifyMessage(parameters.get("msg_1"));
			reportLog(msgVerify, testContext.getName(), "Verify " + parameters.get("msg_1") + " is displayed", "1.0",
					parameters.get("msg_1") + " is displayed after clicking on 'TRY IT FREE' button in Dashboard");
			org.testng.Assert.assertTrue(msgVerify);

			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);

			signUpStep2.clickGetStartedButton();

			boolean msgVerifyThankYouPage = signUpStep2.verifyMessageInThankYouPage(parameters.get("msg_4"));
			reportLog(msgVerifyThankYouPage, testContext.getName(),
					"Verify " + parameters.get("msg_4") + " is displayed", "2.0",
					parameters.get("msg_4") + " is displayed in thank you page after clicking on Get Strated Button");
			org.testng.Assert.assertTrue(msgVerifyThankYouPage);

			driver.get(PropertyUtils.getProperty("app.url"));

			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			String renewDateFormat = licensesBilling.getDateFormat();
			dashboardPage.clickPricingMenu().selectPricingOption();

			// Read Price of Plans in run time

			String monthlyText = pricePage.planPriceAnnualy(parameters.get("plan")); // 45
			String professionalMonthlyPlan = monthlyText.substring(monthlyText.indexOf("$") + 1,
					monthlyText.indexOf("USD"));
			String yearlyText = pricePage.planPriceMonthly(parameters.get("plan"));
			String professionalPriceMonthlyPlan = yearlyText.substring(yearlyText.indexOf("$") + 1,
					yearlyText.indexOf("month to"));// 51
			int perprofessionalPriceMonthlyPlanCost = Integer.parseInt(professionalPriceMonthlyPlan.trim()); // int 51

			boolean pricePageMsg = pricePage.verifyMessage(parameters.get("msg_2"));
			reportLog(pricePageMsg, testContext.getName(), "Verify " + parameters.get("msg_2") + " is displayed", "3.0",
					parameters.get("msg_2")
							+ " is displayed in pricing page in header after clicking on Pricing sub-link");
			org.testng.Assert.assertTrue(pricePageMsg);

			boolean businessPriceAnnualy = pricePage.getPriceAnnualPerUser(professionalMonthlyPlan,
					parameters.get("plan"));
			reportLog(businessPriceAnnualy, testContext.getName(), "Verify business per user price in Pricing Page",
					"4.0", "Yearly professional price per user is :" + professionalMonthlyPlan
							+ " is verified in pricing page");

			boolean professionalPriceMonthly = pricePage.getPriceMonthlyPerUser(professionalPriceMonthlyPlan,
					parameters.get("plan"));
			reportLog(professionalPriceMonthly, testContext.getName(), "Verify monthly  price in Pricing Page", "5.0",
					"Monthly professional price per user is :  " + professionalPriceMonthlyPlan
							+ " verified in pricing page");

			boolean checkOutMsg = pricePage.clickOnSelectPlanProf(parameters.get("msg_3"));
			reportLog(checkOutMsg, testContext.getName(), "Verify " + parameters.get("msg_3") + " is displayed", "6.0",
					parameters.get("msg_3")
							+ " page is open after clicking on 'Select Plan' button in show pricing plans page");

			String countString = userPage.getLicenseCount(0);
			int noOfLicense = Integer.parseInt(countString);
			int licensesCostMonthly = perprofessionalPriceMonthlyPlanCost * noOfLicense; // int 102 (51*2)
			int total_annualCost = licensesCostMonthly * 12; // int 1224 (102*12)
			String annualcost_liceses = Integer.toString(licensesCostMonthly); // 102 string
			NumberFormat formatter = new DecimalFormat("#,###");
			String totalAnnualCost = formatter.format(total_annualCost); // String 1,224

			boolean selectedLicenseCount = userPage.selectlicenseCount(countString);
			reportLog(selectedLicenseCount, testContext.getName(), "Verify selected license counte in Pricing Page",
					"7.0",
					"Selected license " + countString + " is verified in user page under Number of Licenses section");

			userPage.clickNextBtnNumberOfLicenses();
			boolean paymentAnnualType = userPage.verifyPaymentType(parameters.get("text1"));
			reportLog(paymentAnnualType, testContext.getName(), "Verify text 'Annual' in Billing  Frequency page",
					"8.0", "Payment type 'Annual' display in Billing Frequency section ");

			boolean paymentMonthlyType = userPage.verifyPaymentType(parameters.get("text2"));
			reportLog(paymentMonthlyType, testContext.getName(), "Verify text 'Monthly' in Billing Frequency page",
					"9.0", "Payment type 'Monthly' display in Billing Frequency section ");

			boolean paymentAnnualRate = userPage.verifyPlanRate(annualcost_liceses);
			reportLog(paymentAnnualRate, testContext.getName(), "Verify business Monthly price in user Page", "10.0",
					"Monthly professional price " + annualcost_liceses
							+ " is verified in user page in Billing Frequency section for license " + (noOfLicense));

			boolean businessAnnualRate = userPage.verifyTotalCostMonthly(totalAnnualCost);
			reportLog(businessAnnualRate, testContext.getName(), "Verify business annual Rate for '2' liceses", "11.0",
					"Monthly professional price is " + totalAnnualCost
							+ " verified in user page under Billing Frequency section for license " + (noOfLicense));

			userPage.selectMonthlyRadioOption();
			userPage.clickNextBtnBillFreq();
			userPage.waitForBillingInfo();
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			boolean firstName = userPage.isErrorMessageDisplayed(parameters.get("fn_error_message"));
			reportLog(firstName, testContext.getName(), "Verify error message against first name", "12.0",
					parameters.get("fn_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean lastName = userPage.isErrorMessageDisplayed(parameters.get("ln_error_message"));
			reportLog(lastName, testContext.getName(), "Verify error message against last name", "12.1",
					parameters.get("ln_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean emails = userPage.isErrorMessageDisplayed(parameters.get("email_error_message"));
			reportLog(emails, testContext.getName(), "Verify error message against last name", "12.2",
					parameters.get("email_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean phone = userPage.isErrorMessageDisplayed(parameters.get("phone_error_message"));
			reportLog(phone, testContext.getName(), "Verify error message against phone number", "12.3",
					parameters.get("phone_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean address = userPage.isErrorMessageDisplayed(parameters.get("address_error_message"));
			reportLog(address, testContext.getName(), "Verify error message against phone number", "12.4",
					parameters.get("address_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean city = userPage.isErrorMessageDisplayed(parameters.get("city_error_message"));
			reportLog(city, testContext.getName(), "Verify error message against phone number", "12.5",
					parameters.get("city_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean zip = userPage.isErrorMessageDisplayed(parameters.get("zip__error_message"));
			reportLog(zip, testContext.getName(), "Verify error message against phone number", "12.6",
					parameters.get("zip__error_message")
							+ " error message displayed when user keep field blank and click on next button");

			String[] labelIDListProfMonthly = parameters.get("fieldlabelist").split(";");
			String[] fieldDataListProfMonthly = parameters.get("filedinputlist").split(";");

			userPage.enterFieldData(labelIDListProfMonthly, fieldDataListProfMonthly);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			boolean dataSaved = userPage.verifyDataSaved();
			reportLog(dataSaved, testContext.getName(), "Verify data is saved and next button display in ", "13.0",
					"Data is saved and Credit Card Info section open with Next button");

			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.switchToIframe(By.id("z_hppm_iframe"));

			boolean nameOnCard = userPage.isCardErrorMessageDisplayed(parameters.get("ccfieldnamelabel"),
					parameters.get("nameoncard_error_message"));
			reportLog(nameOnCard, testContext.getName(), "Verify error message against nameoncard name", "14.0",
					parameters.get("nameoncard_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean ccNumber = userPage.isCardErrorMessageDisplayed(parameters.get("ccfieldnumberlabel"),
					parameters.get("ccnumber_error_message"));
			reportLog(ccNumber, testContext.getName(), "Verify error message against credit card number", "14.1",
					parameters.get("ccnumber_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean expDate = userPage.isCardErrorMessageDisplayed(parameters.get("ccfielexpdatelabel"),
					parameters.get("expdate_error_message"));
			reportLog(expDate, testContext.getName(), "Verify error message against last name", "14.2",
					parameters.get("expdate_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean cvvNumber = userPage.isCardErrorMessageDisplayed(parameters.get("ccfieldCvvLabel"),
					parameters.get("cvv_error_message"));
			reportLog(cvvNumber, testContext.getName(), "Verify error message against phone number", "14.3",
					parameters.get("cvv_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			String[] cclabelIDList = parameters.get("ccfieldlabelist").split(";");
			String[] ccfieldDataList = parameters.get("ccfiledinputlist").split(";");
			userPage.enterCcData(cclabelIDList, ccfieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();

			boolean confirmPurchaseButton = userPage.confrimPurchaseButtonText(parameters.get("confpurButtontxt"));
			reportLog(confirmPurchaseButton, testContext.getName(), "Verify confirm button text ", "15.0",
					"User redirect to " + parameters.get("confpurButtontxt")
							+ " section, its verfied user fill credit card deatil sucessfully  ");

			String[] fieldXpath = parameters.get("fieldxpath").split(";");
			String[] fieldData = parameters.get("data").split(";");
			String[] fieldLabel = parameters.get("fieldlabel").split(";");

			String renewDateMonthly = userPage.findTodaysDate(parameters.get("planType"), renewDateFormat);
			String stepsArry_confirm[] = new String[] { "15.1", "15.2" };

			// Verify Confirm Purchase Section data
			userPage.verifyConfirmPurchase(testContext, fieldXpath, fieldData, stepsArry_confirm, fieldLabel);

			boolean licensesCountConfirm = userPage.getLicenseCountConfirmPurchase().equals(countString);
			reportLog(licensesCountConfirm, testContext.getName(), "Verify Number of llicense ", "15.3",
					"Licenses count: " + (noOfLicense) + " is verified in user page under Confirm Purchase section");

			boolean confirmTotalAnnualCost = userPage.confirmPurchaseTotalDue(annualcost_liceses);
			reportLog(confirmTotalAnnualCost, testContext.getName(), "Verify total annual cost ", "15.4",
					"Total due " + (annualcost_liceses) + " is verified in user page under Confirm Purchase section");

			boolean renewalDateConfirm = userPage.verifyRenewalDate(renewDateMonthly);
			reportLog(renewalDateConfirm, testContext.getName(), "Verify renewal date ", "15.5",
					"Renewal Date " + (renewDateMonthly) + " is verified in user page under Confirm Purchase section");

			String[] fieldXpathSummary = parameters.get("fieldlabelSummary").split(";");
			String stepsArrySummary[] = new String[] { "16.1", "16.2" };

			userPage.verifyBillingSummary(testContext, fieldXpathSummary, fieldData, stepsArrySummary, fieldLabel);

			boolean licensesCountBilling = userPage.getLicenseCountBillingSummary().equals(countString);
			reportLog(licensesCountBilling, testContext.getName(), "Verify Number of llicense ", "16.3",
					"Licenses count: " + (noOfLicense) + " is verified in user page under Billing Summary section");

			boolean summaryTotalAnnualCost = userPage.summaryPurchaseTotalDue(annualcost_liceses);
			reportLog(summaryTotalAnnualCost, testContext.getName(), "Verify total annual cost ", "16.4",
					"Total due " + (annualcost_liceses) + " is verified in user page under Billing Summary section");

			boolean renewalDateSummary = userPage.verifyRenewalDate(renewDateMonthly);
			reportLog(renewalDateSummary, testContext.getName(), "Verify renewal date ", "16.5",
					"Renewal Date " + (renewDateMonthly) + " is verified in user page under Billing Summary section");

			// userPage.confrimPurchase();
			boolean confirmPurchase = userPage.confirmPurchase(parameters.get("headertext"));
			reportLog(confirmPurchase, testContext.getName(), "Verify header text ", "17.0",
					"User redirect to " + parameters.get("headertext") + " Page and its verfied liceses is purchased ");

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	// QA-423
	@Test
	@Parameters({ "testdescription" })
	public void zuoraSalesTax(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);
			LicensesAndBilling licensesBilling = new LicensesAndBilling(driver);
			// String renewDateYearly = userPage.findTodayDate(parameters.get("planType"));
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();

			String[] labelIDList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");
			homePage.enterFieldData(labelIDList, fieldDataList, email);
			homePage.clickTryItFreeButton();

			boolean msgVerify = signUpStep2.verifyMessage(parameters.get("msg_1"));
			reportLog(msgVerify, testContext.getName(), "Verify " + parameters.get("msg_1") + " is displayed", "1.0",
					parameters.get("msg_1") + " is displayed after clicking on 'TRY IT FREE' button in Dashboard");
			org.testng.Assert.assertTrue(msgVerify);

			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);
			signUpStep2.clickGetStartedButton();

			boolean msgVerifyThankYouPage = signUpStep2.verifyMessageInThankYouPage(parameters.get("msg_4"));
			reportLog(msgVerifyThankYouPage, testContext.getName(),
					"Verify " + parameters.get("msg_4") + " is displayed", "2.0",
					parameters.get("msg_4") + " is displayed in thank you page after clicking on Get Strated Button");
			org.testng.Assert.assertTrue(msgVerifyThankYouPage);

			driver.get(PropertyUtils.getProperty("app.url"));

			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			String renewDateFormat = licensesBilling.getDateFormat();
			String renewDateMonthlypur = userPage.findTodaysDate(parameters.get("planType"), renewDateFormat);
			String renewDateYearly = userPage.findTodaysDate(parameters.get("planType"), renewDateFormat);
			dashboardPage.clickPricingMenu().selectPricingOption();

			// Read Price of Plans in run time from Pricing Page

			String monthlyText = pricePage.planPriceAnnualy(parameters.get("plan")); // 45
			String businesspriceyearlyplan = monthlyText.substring(monthlyText.indexOf("$") + 1,
					monthlyText.indexOf("USD"));
			String yearlyText = pricePage.planPriceMonthly(parameters.get("plan"));
			String businessPriceMonthlyPlan = yearlyText.substring(yearlyText.indexOf("$") + 1,
					yearlyText.indexOf("month to"));// 51
			int perbusinessPriceYearlyPlanCost = Integer.parseInt(businesspriceyearlyplan.trim()); // int 45

			boolean pricePageMsg = pricePage.verifyMessage(parameters.get("msg_2"));
			reportLog(pricePageMsg, testContext.getName(), "Verify " + parameters.get("msg_2") + " is displayed", "3.0",
					parameters.get("msg_2")
							+ " is displayed in pricing page in header after clicking on Pricing sub-link");
			org.testng.Assert.assertTrue(pricePageMsg);

			boolean businessPrice = pricePage.getPriceAnnualPerUser(businesspriceyearlyplan, parameters.get("plan"));
			reportLog(businessPrice, testContext.getName(), "Verify business per user price in Pricing Page", "4.0",
					"Yearly Business price per user is :" + businesspriceyearlyplan + " is verified in pricing page");

			boolean professionalPrice = pricePage.getPriceMonthlyPerUser(businessPriceMonthlyPlan,
					parameters.get("plan"));
			reportLog(professionalPrice, testContext.getName(), "Verify monthly  price in Pricing Page", "5.0",
					"Monthly Business price per user is :  " + businessPriceMonthlyPlan + " verified in pricing page");
			org.testng.Assert.assertTrue(professionalPrice);

			boolean checkOutMsg = pricePage.clickOnSelectPlan(parameters.get("msg_3"));
			reportLog(checkOutMsg, testContext.getName(), "Verify " + parameters.get("msg_3") + " is displayed", "6.0",
					parameters.get("msg_3")
							+ " page is open after clicking on 'Select Plan' button in show pricing plans page");
			org.testng.Assert.assertTrue(checkOutMsg);

			// Get Amount of licenses
			String countString = userPage.getLicenseCount(0);
			int noOfLicense = Integer.parseInt(countString);
			int licensesCostAnnual = perbusinessPriceYearlyPlanCost * noOfLicense; // int 90 (45*2)
			int total_annualCost = licensesCostAnnual * 12; // int 1080 (90*12)
			String annualCostliceses = Integer.toString(licensesCostAnnual); // Covert 90 into string
			NumberFormat formatter = new DecimalFormat("#,###.00");
			String totalAmount = formatter.format(total_annualCost); // String 1,028 (comma seprated)

			userPage.clickNextBtnNumberOfLicenses();
			boolean paymentAnnualType = userPage.verifyPaymentType(parameters.get("text1"));
			reportLog(paymentAnnualType, testContext.getName(), "Verify text 'Annual' in Billing  Frequency page",
					"7.0", "Payment type 'Annual' display in Billing Frequency section ");

			boolean paymentMonthlyType = userPage.verifyPaymentType(parameters.get("text2"));
			reportLog(paymentMonthlyType, testContext.getName(), "Verify text 'Monthly' in Billing Frequency page",
					"8.0", "Payment type 'Monthly' display in Billing Frequency section ");

			boolean paymentAnnualRate = userPage.verifyPlanRate(annualCostliceses);
			reportLog(paymentAnnualRate, testContext.getName(), "Verify business Annual price in user Page", "9.0",
					"Annual Business price per user is " + annualCostliceses
							+ " is verified in user page in Billing Frequency section for license " + (noOfLicense));

			boolean businessAnnualRate = userPage.verifyTotalCostAnnualy(totalAmount);
			reportLog(businessAnnualRate, testContext.getName(), "Verify business annual Rate for '1' liceses", "10.0",
					"Annual 'Total Annual Cost' is " + totalAmount
							+ " verified in user page under Billing Frequency section for license " + (noOfLicense));

			userPage.clickNextBtnBillFreq();
			userPage.waitForBillingInfo();
			userPage.clickNextBtnBillingInfo();

			// Verify validation message for of Billing info Page e.g First Name,Last
			// Name,Email ... etc..

			boolean firstName = userPage.isErrorMessageDisplayed(parameters.get("fn_error_message"));
			reportLog(firstName, testContext.getName(), "Verify error message against first name", "11.0",
					parameters.get("fn_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean lastName = userPage.isErrorMessageDisplayed(parameters.get("ln_error_message"));
			reportLog(lastName, testContext.getName(), "Verify error message against last name", "11.1",
					parameters.get("ln_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean emails = userPage.isErrorMessageDisplayed(parameters.get("email_error_message"));
			reportLog(emails, testContext.getName(), "Verify error message against last name", "11.2",
					parameters.get("email_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean phone = userPage.isErrorMessageDisplayed(parameters.get("phone_error_message"));
			reportLog(phone, testContext.getName(), "Verify error message against phone number", "11.3",
					parameters.get("phone_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean address = userPage.isErrorMessageDisplayed(parameters.get("address_error_message"));
			reportLog(address, testContext.getName(), "Verify error message against phone number", "11.4",
					parameters.get("address_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean city = userPage.isErrorMessageDisplayed(parameters.get("city_error_message"));
			reportLog(city, testContext.getName(), "Verify error message against phone number", "11.5",
					parameters.get("city_error_message")
							+ " error message displayed when user keep field blank and click on next button");

			boolean zip = userPage.isErrorMessageDisplayed(parameters.get("zip__error_message"));
			reportLog(zip, testContext.getName(), "Verify error message against phone number", "11.6",
					parameters.get("zip__error_message")
							+ " error message displayed when user keep field blank and click on next button");

			// FIRST Test case -Country : US and Tax state AND [Is your shipping address the
			// same as your billing address? ] --> "YES" Selected

			String[] labelIDList11 = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList11 = parameters.get("filedinputlist").split(";");

			userPage.waitForInvisibilityOfProgress();
			userPage.enterFieldData(labelIDList11, fieldDataList11);
			userPage.enterFieldDataUsStateTaxAndNonTax(parameters.get("Us_nontax_country1"),
					parameters.get("Us_nontax_city1"), parameters.get("Us_nontax_state1"),
					parameters.get("Us_nontax_zip1"));

			// userPage.getFirstName();

			// Get Country and State
			String getCountry = userPage.getCountry();
			String getState = userPage.getState();
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			/*
			 * * * ** * ** * ** * * Billing Summary data verification SATRT * * * * * ** *
			 */

			String[] fieldData = parameters.get("data").split(";");
			String[] fieldlabel = parameters.get("fieldlabel").split(";");
			String[] fieldxPathSummary = parameters.get("fieldlabelSummary").split(";");

			// Get Sales tax
			String sales_tax = userPage.getSalesTax().replace("$", ""); // $0.00
			float sales_tax_int = Float.parseFloat(sales_tax);

			// Calculate Total Due Amount of licenses
			String totalDueSummary = returnTotalDueAmount(totalAmount, sales_tax_int);
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			boolean dataSaved = userPage.verifyDataSaved();
			reportLog(dataSaved, testContext.getName(), "Verify data is saved and next button display in ", "12",
					"Data is saved and Credit Card Info section open with Next button");

			userPage.switchToIframe(By.id("z_hppm_iframe"));

			boolean nameOnCard = userPage.isCardErrorMessageDisplayed(parameters.get("ccfieldnamelabel"),
					parameters.get("nameoncard_error_message"));
			reportLog(nameOnCard, testContext.getName(), "Verify error message against nameoncard name", "13.0",
					parameters.get("nameoncard_error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(nameOnCard);

			boolean ccNumber = userPage.isCardErrorMessageDisplayed(parameters.get("ccfieldnumberlabel"),
					parameters.get("ccnumber_error_message"));
			reportLog(ccNumber, testContext.getName(), "Verify error message against credit card number", "13.1",
					parameters.get("ccnumber_error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(ccNumber);

			boolean expDate = userPage.isCardErrorMessageDisplayed(parameters.get("ccfielexpdatelabel"),
					parameters.get("expdate_error_message"));
			reportLog(expDate, testContext.getName(), "Verify error message against last name", "13.2",
					parameters.get("expdate_error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(expDate);

			boolean cvvNumber = userPage.isCardErrorMessageDisplayed(parameters.get("ccFieldCvvLabel"),
					parameters.get("cvv_error_message"));
			reportLog(cvvNumber, testContext.getName(), "Verify error message against phone number", "13.3",
					parameters.get("cvv_error_message")
							+ " error message displayed when user keep field blank and click on next button");
			org.testng.Assert.assertTrue(expDate);

			String[] cclabelIDList = parameters.get("ccfieldlabelist").split(";");
			String[] ccfieldDataList = parameters.get("ccfiledinputlist").split(";");
			userPage.enterCcData(cclabelIDList, ccfieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));
			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			// Confirm Purchase xpath and data from .yml file

			String[] fieldXpathPur = parameters.get("fieldxpathpur").split(";");
			String[] fieldDatapur = parameters.get("data").split(";");
			String[] fieldLabelPur = parameters.get("fieldlabel").split(";");
			// String renewDateMonthlypur =
			// userPage.findTodayDate(parameters.get("planType"));
			String stepsArry_summary[] = new String[] { "14.1", "14.2" };
			String stepsArry_confirm[] = new String[] { "15.1", "15.2" };

			// Read Billing Summary and Confirm Purchase detail from Web Page
			userPage.countryStateTax(testContext, getCountry, getState, fieldData, fieldlabel, fieldxPathSummary,
					stepsArry_summary, renewDateYearly, noOfLicense, totalAmount, countString, sales_tax, sales_tax_int,
					totalDueSummary, fieldXpathPur, fieldDatapur, fieldLabelPur, renewDateMonthlypur, stepsArry_confirm,
					"14.3", "14.4", "14.5", "14.6", "14.7", "15.3", "15.4", "15.5", "15.6", "15.7");

			reportLog(true, testContext.getName(), testDescription, "",
					"---->>>> First Test case for US and Tax state is completed >>>>--------- ");

			// Second Test case -------Country (US) and Non tax state AND [Is your shipping
			// address the same as your billing address? ] --> "YES" Selected

			String stepsArry_summary2[] = new String[] { "16.1", "16.2" };
			String stepsArry_confirm2[] = new String[] { "17.1", "17.2" };
			userPage.clickBillingInfo();
			userPage.enterFieldDataUsStateTaxAndNonTax(parameters.get("Us_nontax_country2"),
					parameters.get("Us_nontax_city2"), parameters.get("Us_nontax_state2"),
					parameters.get("Us_nontax_zip2"));

			// userPage.getFirstName();

			String getCountry2 = userPage.getCountry();
			String getState2 = userPage.getState();
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			// Get Sales Tax
			String sales_tax2 = userPage.getSalesTax().replace("$", ""); // $0.00
			float sales_tax_int2 = Float.parseFloat(sales_tax2);

			// Calculate Total Due Amount of licenses
			String total_amount2 = formatter.format(total_annualCost); // String 1,028 (comma seprated)
			String total_due_summary2 = returnTotalDueAmount(total_amount2, sales_tax_int2);
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.countryStateTax(testContext, getCountry2, getState2, fieldData, fieldlabel, fieldxPathSummary,
					stepsArry_summary2, renewDateYearly, noOfLicense, total_amount2, countString, sales_tax2,
					sales_tax_int2, total_due_summary2, fieldXpathPur, fieldDatapur, fieldLabelPur, renewDateMonthlypur,
					stepsArry_confirm2, "16.3", "16.4", "16.5", "16.6", "16.7", "17.3", "17.4", "17.5", "17.6", "17.7");

			reportLog(true, testContext.getName(), testDescription, "",
					"---->>>> Second Test case for US and NON  Tax state is completed >>>> - - - - - -  ---- - - ----- ");

			// Third Test case -------Country: Non US (e.g India) on tax state AND [Is your
			// shipping address the same as your billing address? ] --> "YES" Selected

			String stepsArry_summary3[] = new String[] { "18.1", "18.2" };
			String stepsArry_confirm3[] = new String[] { "19.1", "19.2" };
			userPage.clickBillingInfo();
			userPage.enterFieldDataNonUsStateNonTax(parameters.get("Us_nontax_country3"),
					parameters.get("Us_nontax_city3"), parameters.get("Us_nontax_zip3"));

			String getCountry3 = userPage.getCountry();
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			// Calculate Total Due Amount of licenses
			String total_amount3 = formatter.format(total_annualCost); // String 1,028 (comma seprated
			String total_due_summary3 = returnTotalDueAmount(total_amount3, 0);
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.countryStateTax(testContext, getCountry3, "", fieldData, fieldlabel, fieldxPathSummary,
					stepsArry_summary3, renewDateYearly, noOfLicense, total_amount3, countString, sales_tax2,
					sales_tax_int2, total_due_summary3, fieldXpathPur, fieldDatapur, fieldLabelPur, renewDateMonthlypur,
					stepsArry_confirm3, "18.3", "18.4", "18.5", "18.6", "18.7", "19.3", "19.4", "19.5", "19.6", "19.7");

			reportLog(true, testContext.getName(), testDescription, "",
					"---->>>> Third  Test case for NON US country is completed >>>>--------- - - ----- ");

			// Fourth Test case -------Country: US and Tax state AND [Is your shipping
			// address the same as your billing address? ] --> "No" Selected

			String stepsArry_summary4[] = new String[] { "20.1", "20.2" };
			String stepsArry_confirm4[] = new String[] { "21.1", "21.2" };
			userPage.clickBillingInfo();
			userPage.clickNoRadioOption();
			userPage.enterFieldDataShippingNotSameBillingUsState(parameters.get("Us_tax_country4"),
					parameters.get("Us_tax_city4"), parameters.get("Us_tax_state4"), parameters.get("Us_tax_zip4"),
					parameters.get("Us_tax_address4"));

			String getCountry4 = userPage.getCountryForNoOption();
			String getState4 = userPage.getStateForNoOption();
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			// Get Sales Tax
			String sales_tax4 = userPage.getSalesTax().replace("$", "");
			float sales_tax_int4 = Float.parseFloat(sales_tax4);

			// Calculate Total Due Amount of licenses
			String total_amount4 = formatter.format(total_annualCost); // String 1,028 (comma seprated)
			String total_due_summary4 = returnTotalDueAmount(total_amount4, sales_tax_int4);
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.countryStateTax(testContext, getCountry4, getState4, fieldData, fieldlabel, fieldxPathSummary,
					stepsArry_summary4, renewDateYearly, noOfLicense, total_amount4, countString, sales_tax4,
					sales_tax_int4, total_due_summary4, fieldXpathPur, fieldDatapur, fieldLabelPur, renewDateMonthlypur,
					stepsArry_confirm4, "20.3", "20.4", "20.5", "20.6", "20.7", "21.3", "21.4", "21.5", "21.6", "21.7");

			reportLog(true, testContext.getName(), testDescription, "",
					"-->>>> Fourth Test case for country US and Tax state is completed And **Is your shipping address the same as your billing address?:: NO  Option selected--- -- ");

			// Fifth Test case -------Country: US and Non Tax state AND [Is your shipping
			// address the same as your billing address? ] --> "No" Selected

			String stepsArry_summary5[] = new String[] { "22.1", "22.2" };
			String stepsArry_confirm5[] = new String[] { "23.1", "23.2" };
			userPage.clickBillingInfo();
			userPage.clickNoRadioOption();
			userPage.enterFieldDataShippingNotSameBillingUsState(parameters.get("Us_nontax_country5"),
					parameters.get("Us_nontax_city5"), parameters.get("Us_nontax_state5"),
					parameters.get("Us_nontax_zip5"), parameters.get("Us_nontax_address5"));

			String getCountry5 = userPage.getCountryForNoOption();
			String getState5 = userPage.getStateForNoOption();

			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			// Get Sales Tax
			String sales_tax5 = userPage.getSalesTax().replace("$", ""); // $0.00
			float sales_tax_int5 = Float.parseFloat(sales_tax5);

			// Calculate Total Due Amount of licenses
			String total_amount5 = formatter.format(total_annualCost); // String 1,028 (comma seprated)
			String total_due_summary5 = returnTotalDueAmount(total_amount5, sales_tax_int5);
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.countryStateTax(testContext, getCountry5, getState5, fieldData, fieldlabel, fieldxPathSummary,
					stepsArry_summary5, renewDateYearly, noOfLicense, total_amount5, countString, sales_tax5,
					sales_tax_int5, total_due_summary5, fieldXpathPur, fieldDatapur, fieldLabelPur, renewDateMonthlypur,
					stepsArry_confirm5, "22.3", "22.4", "22.5", "22.6", "22.7", "23.3", "23.4", "23.5", "23.6", "23.7");

			reportLog(true, testContext.getName(), testDescription, "",
					"-->>>> Fifth  Test case for country US and Non Tax state is completed And **Is your shipping address the same as your billing address?:: NO  Option selected--- -- ");

			// Sixth Test case -------Country: Non US (e.g INDIA) and [Is your shipping
			// address the same as your billing address? ] --> "No" Selected

			String stepsArry_summary6[] = new String[] { "24.1", "24.2" };
			String stepsArry_confirm6[] = new String[] { "24.1", "24.2" };
			userPage.clickBillingInfo();
			userPage.clickNoRadioOption();
			userPage.enterFieldDataNonUsStateNonTaxNoState(parameters.get("Us_nontax_country6"),
					parameters.get("Us_nontax_city6"), parameters.get("Us_nontax_zip6"));

			String getCountry6 = userPage.getCountryForNoOption();
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			// Calculate Total Due Amount of licenses
			String total_amount6 = formatter.format(total_annualCost); // String 1,028 (comma seprated)
			String total_due_summary6 = returnTotalDueAmount(total_amount6, 0);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.countryStateTax(testContext, getCountry6, "", fieldData, fieldlabel, fieldxPathSummary,
					stepsArry_summary6, renewDateYearly, noOfLicense, total_amount6, countString, sales_tax2,
					sales_tax_int2, total_due_summary6, fieldXpathPur, fieldDatapur, fieldLabelPur, renewDateMonthlypur,
					stepsArry_confirm6, "24.3", "24.4", "24.5", "24.6", "24.7", "25.3", "25.4", "25.5", "25.6", "25.7");

			reportLog(true, testContext.getName(), testDescription, "",
					"-->>>> Sixth  Test case for country Non US country e.g China is completed And **Is your shipping address the same as your billing address?:: NO  Option selected--- -- ");

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-415 Add user after adding new license Automation

	@Test
	@Parameters({ "testdescription" })
	public void zuoraAddUser(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();
			String[] labelIDList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");
			String[] labelIDList11 = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList11 = parameters.get("filedinputlist").split(";");
			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			homePage.enterFieldData(labelIDList, fieldDataList, email);
			homePage.clickTryItFreeButton();
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);

			signUpStep2.clickGetStartedButton();
			driver.get(PropertyUtils.getProperty("app.url"));

			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage.clickPricingMenu().selectPricingOption();

			pricePage.clickOnSelectPlan();
			String readLicenseCount = userPage.getLicenseCount(0);
			int count = Integer.parseInt(readLicenseCount);

			userPage.clickNextBtnNumberOfLicenses();
			userPage.clickNextBtnBillFreq();
			userPage.waitForInvisibilityOfProgress();

			userPage.enterFieldData(labelIDList11, fieldDataList11);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.switchToIframe(By.id("z_hppm_iframe"));

			String[] cclabelIDList = parameters.get("ccfieldlabelist").split(";");
			String[] ccfieldDataList = parameters.get("ccfiledinputlist").split(";");
			userPage.enterCcData(cclabelIDList, ccfieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.confirmPurchase();

			boolean userMessage = userPage.verifyUserMessage(parameters.get("headertext"));
			reportLog(userMessage, testContext.getName(), "Verify " + parameters.get("headertext") + " is displayed",
					"1.0",
					" User redirect to User & Seats pages after purchasing seat : " + count + " from checkout page");
			org.testng.Assert.assertTrue(userMessage);

			/*
			 * Adding user as per the available license
			 */
			userPage.addUserPerLicense(count, testContext, parameters.get("fn_error_message"),
					parameters.get("ln_error_message"), parameters.get("email_error_message"),
					parameters.get("buttontext"), parameters.get("duplicatErrormsg"), parameters.get("duplicatemail"),"Users");

			boolean verifyCountMatch = userPage.countRow(count);
			reportLog(verifyCountMatch, testContext.getName(), "Verify added user count match" + " with seat count",
					"5.0", " No. of user displying in User & Seats page " + " become equal to Seat counts");
			org.testng.Assert.assertTrue(verifyCountMatch);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-416 Disable user after adding though new license

	@Test
	@Parameters({ "testdescription" })
	public void zuoraDisableUser(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();
			String[] labelIDList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");
			String[] labelIDList11 = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList11 = parameters.get("filedinputlist").split(";");
			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			homePage.enterFieldData(labelIDList, fieldDataList, email);
			homePage.clickTryItFreeButton();
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);

			signUpStep2.clickGetStartedButton();
			driver.get(PropertyUtils.getProperty("app.url"));

			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage.clickPricingMenu().selectPricingOption();

			pricePage.clickOnSelectPlan();
			String readLicenseCount = userPage.getLicenseCount(2);
			int count = Integer.parseInt(readLicenseCount);

			userPage.clickNextBtnNumberOfLicenses();
			userPage.clickNextBtnBillFreq();
			userPage.waitForInvisibilityOfProgress();

			userPage.enterFieldData(labelIDList11, fieldDataList11);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.switchToIframe(By.id("z_hppm_iframe"));

			String[] cclabelIDList = parameters.get("ccfieldlabelist").split(";");
			String[] ccfieldDataList = parameters.get("ccfiledinputlist").split(";");
			userPage.enterCcData(cclabelIDList, ccfieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.confirmPurchase();

			boolean userMessage = userPage.verifyUserMessage(parameters.get("headertext"));
			reportLog(userMessage, testContext.getName(), "Verify " + parameters.get("headertext") + " is displayed",
					"1.0", " User redirect to User & Seats pages after purchasing licenses : " + count
							+ " from checkout page");
			org.testng.Assert.assertTrue(userMessage);

			// Adding user as per the available license

			userPage.addUserPerLicense(count, testContext, parameters.get("fn_error_message"),
					parameters.get("ln_error_message"), parameters.get("email_error_message"),
					parameters.get("buttontext"), parameters.get("duplicatErrormsg"), parameters.get("duplicatemail"),"Users");

			boolean verifyCountMatch = userPage.countRow(count);
			reportLog(verifyCountMatch, testContext.getName(), "Verify added user count match" + " with seats count",
					"5.0", " All users are added and No. of user count displying in user page "
							+ " become equal to seats counts");
			org.testng.Assert.assertTrue(verifyCountMatch);

			// Remove Seat : Get mail id of disable user and verify user is disabled

			String disablEmail = userPage.getUserHaveToDisable();
			userPage.disableUsers();
			boolean userDisable = userPage.serachDisableUser(disablEmail).equals(parameters.get("usernotfound"));
			reportLog(userDisable, testContext.getName(), "Verify user disable ", "6.0",
					"After disabling ONE User By clicking on 'Remove Seat' button user not dispaly in Active user list Is verified after search");
			org.testng.Assert.assertTrue(userDisable);

			boolean countAfterUserDisable = userPage.countRowAfterDisable(count);
			reportLog(countAfterUserDisable, testContext.getName(),
					"Verify added user count match after user disable" + " with seats count", "7.0",
					" After disabling One user, Number of user displying in User & Seats page "
							+ " is less by '1' from Total seats counts is verified");
			org.testng.Assert.assertTrue(countAfterUserDisable);

			boolean readButonText = userPage.buttenTextAfterUserDisable()
					.equals(parameters.get("disableUserButtonText"));

			reportLog(readButonText, testContext.getName(), "Verify button text", "8.0",
					" Button text not change and it should remain " + parameters.get("disableUserButtonText")
							+ " Is Verified Users & Seats Page");
			org.testng.Assert.assertTrue(readButonText);

			// Keep Seat : Get mail id of disable user and verify user is disabled

			String disablEmailKeepSeat = userPage.getUserHaveToDisable();
			userPage.keepSeat();
			boolean disablEmailKeepSeats = userPage.serachDisableUser(disablEmailKeepSeat)
					.equals(parameters.get("usernotfound"));
			reportLog(userDisable, testContext.getName(), "Verify user disable ", "9.0",
					"After disabling ONE User By Clicking ON 'Keep Seat' from the Users list, user not dispaly in Active user list Is verified after search");
			org.testng.Assert.assertTrue(disablEmailKeepSeats);

			boolean readButonTextAfterKeepSeat = userPage.buttenTextAfterClickinOnKeepSeat()
					.equals(parameters.get("buttontextkeepseat"));
			reportLog(readButonTextAfterKeepSeat, testContext.getName(), "Verify button text", "10.0",
					" Button text change from 'Purchase Seats' to  " + parameters.get("buttontextkeepseat")
							+ " Is Verfied in Users & Seats Page");
			org.testng.Assert.assertTrue(readButonTextAfterKeepSeat);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
	// QA-417 Add More Licenses after adding consuming all existing users

	@Test
	@Parameters({ "testdescription" })
	public void zuoraAddLicenses(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			LicensesAndBilling licensesBilling = new LicensesAndBilling(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);

			// Sign up
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();
			String[] labelIDList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");
			String[] labelIDList11 = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList11 = parameters.get("filedinputlist").split(";");
			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			homePage.enterFieldData(labelIDList, fieldDataList, email);
			homePage.clickTryItFreeButton();
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);
			signUpStep2.clickGetStartedButton();
			driver.get(PropertyUtils.getProperty("app.url"));

			/**
			 * Login after Sign up
			 */
			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();

			String renewDateFormat = licensesBilling.getDateFormat();

			dashboardPage.clickPricingMenu().selectPricingOption();
			pricePage.clickOnSelectPlan();

			/**
			 * Purchase "Annual" licenses from Checkout Page
			 */

			// Select Licenses and Billing frequency

			String countString = userPage.getLicenseCount(0);
			int annualNoOfLicense1 = Integer.parseInt(countString);

			userPage.clickNextBtnNumberOfLicenses();
			userPage.waitForBillingFreq();
			userPage.clickNextBtnBillFreq();
			userPage.waitForInvisibilityOfProgress();
			userPage.waitForBillingInfo();
			// String renewDateFormat ="DD/MM/YYYY";
			String renewDateAnnual = userPage.findTodaysDate(parameters.get("planType"), renewDateFormat);

			// Enter data in Billing Info

			userPage.enterFieldData(labelIDList11, fieldDataList11);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();

			// switchToIframe
			userPage.switchToIframe(By.id("z_hppm_iframe"));

			// Enter data in Credit card

			String[] ccLabelIDList = parameters.get("ccfieldlabelist").split(";");
			String[] ccfieldDataList = parameters.get("ccfiledinputlist").split(";");
			userPage.enterCcData(ccLabelIDList, ccfieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));
			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.switchToDefault();
			// userPage.clickNextButton(3);

			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();

			// Confirm licenses purchase

			userPage.confirmPurchase();
			boolean userMessage = userPage.verifyUserMessage(parameters.get("headertext"));
			reportLog(userMessage, testContext.getName(), "Verify " + parameters.get("headertext") + " is displayed",
					"1.0", " User redirect to  Users & Seats pages after purchasing 'Annual' licenses : "
							+ annualNoOfLicense1 + " from checkout page");
			org.testng.Assert.assertTrue(userMessage);
			userPage.addUserPerLicense(annualNoOfLicense1, testContext, parameters.get("fn_error_message"),
					parameters.get("ln_error_message"), parameters.get("email_error_message"),
					parameters.get("buttontext"), parameters.get("duplicatErrormsg"), parameters.get("duplicatemail"),"Users");
			// Compare data for Fields (Plan,Licenses,Billing,Renewal Date in Licenses &
			// Billing PAGE)

			boolean licensesAndBillingMsg = userPage.licensesAndBilling()
					.equals(parameters.get("licensesAndBillingHeaderMsg"));
			reportLog(licensesAndBillingMsg, testContext.getName(), "Verify User redirect to" + " ", "5.0",
					" Verify User redirect to 'Billing page' " + " after clicking on Billing link");
			org.testng.Assert.assertTrue(licensesAndBillingMsg);

			// Get Open Seat after First time purchase

			String openSeatFirstPurchase1 = licensesBilling.openSeat();

			// Get Filled Seat after First time purchase

			String filledSeatFirstPurchase1 = licensesBilling.filledSeat();

			String[] licenseslabel = parameters.get("licensesLabel").split(";");
			String licensesdata[] = new String[] { parameters.get("confirm_billing_freq_firstpur"), renewDateAnnual,
					openSeatFirstPurchase1, filledSeatFirstPurchase1, countString };
			String stepsArry_confirm[] = new String[] { "6.0", "7.0", "8.0", "9.0", "10.0" };

			licensesBilling.readLicensesCount(testContext, licenseslabel, licensesdata, stepsArry_confirm, 1,
					"For first time Annual license purchase");

			/**
			 * Purchase More Second time "Annual" licenses from Checkout Page
			 */

			// Add More Licenses (Monthly) SECOND TIME From Users Page By clicking on Add
			// Licenses button

			licensesBilling.addLicenseButton();
			String countAdditionalLicenses = userPage.getLicenseCount(1);
			int annualNoOfLicense2 = Integer.parseInt(countAdditionalLicenses);

			// Select Billing Frequency

			userPage.clickNextBtnNumberOfLicenses();
			userPage.clickNextBtnBillingContact();
			userPage.clickNextBtnBillingCreditCard();
			userPage.waitForInvisibilityOfProgress();
			userPage.confirmPurchase();

			boolean licensesAndBillingHeaderMsg = userPage.licensesAndBilling()
					.equals(parameters.get("licensesAndBillingHeaderMsg"));
			reportLog(licensesAndBillingHeaderMsg, testContext.getName(),
					"Verify " + parameters.get("licensesAndBillingHeaderMsg") + " is displayed", "11.0",
					" User redirect to Billing  pages after purchasing seats with frequency 'Annual' in SECOND time with count  : "
							+ annualNoOfLicense2 + " from checkout page");

			org.testng.Assert.assertTrue(licensesAndBillingHeaderMsg);

			// Get Open Seat after First time purchase

			String openSeatFirstPurchase2 = licensesBilling.openSeat();

			// Get Filled Seat after First time purchase

			String filledSeatFirstPurchase2 = licensesBilling.filledSeat();

			int totalLicensesAnnualPurchase = annualNoOfLicense1 + annualNoOfLicense2;
			String total_licensesAnnualPurchase = Integer.toString(totalLicensesAnnualPurchase);
			String[] licensesLabel2 = parameters.get("licensesLabel").split(";");
			String licensesData2[] = new String[] { parameters.get("confirm_billing_freq_firstpur"), renewDateAnnual,
					openSeatFirstPurchase2, filledSeatFirstPurchase2, total_licensesAnnualPurchase };
			String stepsArryConfirm2[] = new String[] { "12.0", "13.0", "14.0", "15.0", "16.0" };

			licensesBilling.readLicensesCount(testContext, licensesLabel2, licensesData2, stepsArryConfirm2, 1,
					"For first time Annual seats purchase");

			boolean toalMonthlyLicensesPurchased = userPage.totaLicensesMonthlyPurchased(totalLicensesAnnualPurchase);
			reportLog(toalMonthlyLicensesPurchased, testContext.getName(), "Verify Total seats purchased monthly" + " ",
					"17.0", " Total Seat purchased count in Billing Page  " + "  is   " + total_licensesAnnualPurchase
							+ " verfied in User & Seats page");
			org.testng.Assert.assertTrue(toalMonthlyLicensesPurchased);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-425 Zuora - Company Admin - Reduce Licenses (First Time Cancel) Automation

	@Test
	@Parameters({ "testdescription" })
	public void reduceLicenses(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			LicensesAndBilling licensesBilling = new LicensesAndBilling(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);

			// Sign up
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();
			String[] labelIDList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");
			String[] labelIDList11 = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList11 = parameters.get("filedinputlist").split(";");
			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			homePage.enterFieldData(labelIDList, fieldDataList, email);
			homePage.clickTryItFreeButton();
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);
			signUpStep2.clickGetStartedButton();
			driver.get(PropertyUtils.getProperty("app.url"));

			/**
			 * Login after Sign up
			 */
			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage.clickPricingMenu().selectPricingOption();
			pricePage.clickOnSelectPlan();

			/**
			 * --->>> Purchase "Annual" licenses from Checkout Page
			 */

			// Select Licenses and Billing frequency

			String countstring = userPage.getLicenseCount(0);
			int no_oflicense = Integer.parseInt(countstring);
			userPage.clickNextBtnNumberOfLicenses();

			userPage.clickNextBtnBillFreq();
			userPage.waitForBillingInfo();
			String renewDateYearly = userPage.findTodayDate(parameters.get("planyearly"));

			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();

			// Enter data in Billing Info and Credit card
			userPage.enterFieldData(labelIDList11, fieldDataList11);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();

			// switchToIframe
			userPage.switchToIframe(By.id("z_hppm_iframe"));

			// Enter data in Credit card

			String[] cclabelIDList = parameters.get("ccfieldlabelist").split(";");
			String[] ccfieldDataList = parameters.get("ccfiledinputlist").split(";");
			userPage.enterCcData(cclabelIDList, ccfieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));
			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();

			userPage.confirmPurchase();
			boolean usermessage = userPage.verifyUserMessage(parameters.get("headertext"));
			reportLog(usermessage, testContext.getName(), "Verify " + parameters.get("headertext") + " is displayed",
					"1.0", " User redirect to User pages after purchasing -> 'Annual' licenses : " + no_oflicense
							+ " from checkout page");
			org.testng.Assert.assertTrue(usermessage);

			boolean licensesAndBillingmsg = userPage.licensesAndBilling()
					.equals(parameters.get("licensesAndBillingHeaderMsg"));
			reportLog(licensesAndBillingmsg, testContext.getName(), "Verify User redirect to" + " ", "2.0",
					" Verify User redirect to 'Licenses & Billing page' "
							+ " after clicking on Licenses & Billing link");

			String[] licenseslabel = parameters.get("licensesLabel").split(";");
			String licensesdata_yearly[] = new String[] { parameters.get("confirm_billing_freq_firstpur"),
					renewDateYearly, countstring, countstring };
			String stepsArry_confirm_yearly[] = new String[] { "3.0", "4.0", "5.0", "6.0" };

			licensesBilling.readLicensesCount(testContext, licenseslabel, licensesdata_yearly, stepsArry_confirm_yearly,
					1, "For first time ''Annual'' license purchase");

			/**
			 * For Annual Licenses :: Verify validation After clicking on Remove Licenses
			 * -licensesToRenew < activeUsers,licensesToRenew >
			 * -total_Licenses_Available,licensesToRenew == 0 -licensesToRenew ==activeUsers
			 * -licensesToRenew ==total_Licenses_Available, -total_Licenses_Available >
			 * licensesToRenew user Licenses To Renew MORE than Licenses Available
			 */
			licensesBilling.verifyRenewalsScenarios(testContext, parameters.get("zerro_error_msg"),
					parameters.get("nonzerro_error_msg"), parameters.get("license_renewals_msg"),
					parameters.get("licenseRenewAnnual"), parameters.get("confirm_billing_freq_firstpur"), "7.0", "8.0",
					"9.0");
			licensesBilling.clickonAccountLink();
			boolean licensesAndBillingHeaderMsg = userPage.licensesAndBilling()
					.equals(parameters.get("licensesAndBillingHeaderMsg"));
			reportLog(licensesAndBillingHeaderMsg, testContext.getName(),
					"Verify " + parameters.get("headertext") + " is displayed", "10.0",
					" User redirect to Licenses & Billing pages after clicking on Remove Licenses LINK is Verified");

			licensesBilling.addLicenseButton();

			String licensescountstring = userPage.getLicenseCount(1);
			int no_oflicenses = Integer.parseInt(licensescountstring);
			userPage.clickNextBtnNumberOfLicenses();
			userPage.waitForBillingFreq();
			userPage.selectPlanRadioOption(1);
			userPage.clickNextBtnBillFreq();
			userPage.waitForBillingInfo();

			String renewDatemonthly = userPage.findTodayDate(parameters.get("planmonthly"));
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();

			userPage.confirmPurchase();

			String licensesdata_monthly[] = new String[] { parameters.get("confirm_billing_freq_secondpur"),
					renewDatemonthly, licensescountstring, licensescountstring };
			String stepsArry_confirm_monthly[] = new String[] { "11.0", "12.0", "13.0", "14.0" };

			/**
			 * For Monthly Licenses :: Verify validation After clicking on Remove Licenses
			 * -licensesToRenew < activeUsers,licensesToRenew >
			 * -total_Licenses_Available,licensesToRenew == 0 -licensesToRenew ==activeUsers
			 * -licensesToRenew ==total_Licenses_Available, -total_Licenses_Available >
			 * licensesToRenew
			 * 
			 * Test when user Licenses To Renew MORE than Licenses Available
			 */

			licensesBilling.readLicensesCount(testContext, licenseslabel, licensesdata_monthly,
					stepsArry_confirm_monthly, 2, "For first time Monthly license purchase");
			boolean licensesAndBillingHeaderMsgTop = userPage.licensesAndBilling()
					.equals(parameters.get("licensesAndBillingHeaderMsg"));
			reportLog(licensesAndBillingHeaderMsgTop, testContext.getName(),
					"Verify " + parameters.get("headertext") + " is displayed", "15.0",
					" User redirect to Licenses & Billing  pages AGAIN after purchasing 'Monthly' licenses with count "
							+ no_oflicenses + " from checkout page");
			licensesBilling.verifyRenewalsScenarios(testContext, parameters.get("zerro_error_msg"),
					parameters.get("nonzerro_error_msg"), parameters.get("license_renewals_msg"),
					parameters.get("licenseRenewMonthly"), parameters.get("confirm_billing_freq_secondpur"), "16.0",
					"17.0", "18.0");

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-428 Zuora - Company Admin - Display Pending Orders When User Reduces
	// Licenses

	@Test
	@Parameters({ "testdescription" })
	public void displayPendingOrders(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			LicensesAndBilling licensesBilling = new LicensesAndBilling(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);

			// Sign up
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();
			String[] labelIDList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");
			String[] labelIDList11 = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList11 = parameters.get("filedinputlist").split(";");
			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			homePage.enterFieldData(labelIDList, fieldDataList, email);
			homePage.clickTryItFreeButton();
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);
			signUpStep2.clickGetStartedButton();
			driver.get(PropertyUtils.getProperty("app.url"));

			/**
			 * Login after Sign up
			 */
			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage.clickPricingMenu().selectPricingOption();
			pricePage.clickOnSelectPlan();

			/**
			 * --->>> Purchase "Annual" licenses from Checkout Page
			 */

			// Select Licenses and Billing frequency

			String countstring = userPage.getLicenseCount(0);
			int no_oflicense = Integer.parseInt(countstring);
			userPage.clickNextBtnNumberOfLicenses();

			userPage.clickNextBtnBillFreq();
			userPage.waitForBillingInfo();
			String renewDateYearly = userPage.findTodayDate(parameters.get("planyearly"));

			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();

			// Enter data in Billing Info and Credit card
			userPage.enterFieldData(labelIDList11, fieldDataList11);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();

			// switchToIframe
			userPage.switchToIframe(By.id("z_hppm_iframe"));

			// Enter data in Credit card

			String[] cclabelIDList = parameters.get("ccfieldlabelist").split(";");
			String[] ccfieldDataList = parameters.get("ccfiledinputlist").split(";");
			userPage.enterCcData(cclabelIDList, ccfieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));
			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();

			userPage.confirmPurchase();
			boolean usermessage = userPage.verifyUserMessage(parameters.get("headertext"));
			reportLog(usermessage, testContext.getName(), "Verify " + parameters.get("headertext") + " is displayed",
					"1.0", " User redirect to User pages after purchasing -> 'Annual' licenses : " + no_oflicense
							+ " from checkout page");
			org.testng.Assert.assertTrue(usermessage);

			boolean licensesAndBillingmsg = userPage.licensesAndBilling()
					.equals(parameters.get("licensesAndBillingHeaderMsg"));
			reportLog(licensesAndBillingmsg, testContext.getName(), "Verify User redirect to" + " ", "2.0",
					" Verify User redirect to 'Licenses & Billing page' "
							+ " after clicking on Licenses & Billing link");

			String[] licenseslabel = parameters.get("licensesLabel").split(";");
			String licensesdata_yearly[] = new String[] { parameters.get("confirm_billing_freq_firstpur"),
					renewDateYearly, countstring, countstring };
			String stepsArry_confirm_yearly[] = new String[] { "3.0", "4.0", "5.0", "6.0" };

			licensesBilling.readLicensesCount(testContext, licenseslabel, licensesdata_yearly, stepsArry_confirm_yearly,
					1, "For first time ''Annual'' license purchase");

			/**
			 * For Annual Licenses :: Verify validation After clicking on Remove Licenses
			 * -licensesToRenew < activeUsers,licensesToRenew >
			 * -total_Licenses_Available,licensesToRenew == 0 -licensesToRenew ==activeUsers
			 * -licensesToRenew ==total_Licenses_Available, -total_Licenses_Available >
			 * licensesToRenew user Licenses To Renew MORE than Licenses Available
			 */
			boolean licensesAndBillingHeaderMsg = userPage.licensesAndBilling()
					.equals(parameters.get("licensesAndBillingHeaderMsg"));
			reportLog(licensesAndBillingHeaderMsg, testContext.getName(),
					"Verify " + parameters.get("headertext") + " is displayed", "7.0",
					" User redirect to Licenses & Billing pages after clicking on Remove Licenses LINK is Verified");

			/**
			 * This method used to verify "Pending license" link
			 */
			licensesBilling.verifyPendingLicenses(testContext, parameters.get("license_renewals_msg"),
					parameters.get("licenseRenewAnnual"), "8.0", "9.0", "10.0");

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
	// QA-478 Zuora - Company Admin - Upgrade to paid

	@Test
	@Parameters({ "testdescription" })
	public void upgradeToPaidFromIndividual(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);
			LicensesAndBilling licensesBilling = new LicensesAndBilling(driver);
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();

			String[] labelList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");

			String[] labelList11 = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList11 = parameters.get("filedinputlist").split(";");
			homePage.enterFieldData(labelList, fieldDataList, email);
			homePage.clickTryItFreeButton();

			boolean msgVerify = signUpStep2.verifyMessage(parameters.get("msg_1"));
			reportLog(msgVerify, testContext.getName(), "Verify " + parameters.get("msg_1") + " is displayed", "1.0",
					parameters.get("msg_1") + " is displayed after clicking on 'TRY IT FREE' button during sign up");
			org.testng.Assert.assertTrue(msgVerify);

			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);

			signUpStep2.clickGetStartedButton();

			boolean msgVerifyTahnkYouPage = signUpStep2.verifyMessageInThankYouPage(parameters.get("msg_4"));
			reportLog(msgVerifyTahnkYouPage, testContext.getName(),
					"Verify " + parameters.get("msg_4") + " is displayed", "2.0", parameters.get("msg_4")
							+ " is displayed in thank you page after clicking on Get Started Button during sign up");
			org.testng.Assert.assertTrue(msgVerifyTahnkYouPage);

			driver.get(PropertyUtils.getProperty("app.url"));

			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage.clickPricingMenu().selectPricingOption();

			String monthlyText = pricePage.planPriceAnnualy(parameters.get("plan")); // 55
			String professionalPriceyearlyPlan = monthlyText.substring(monthlyText.indexOf("$") + 1,
					monthlyText.indexOf("USD"));

			int perprofessionalsPriceYearlyPlanCost = Integer.parseInt(professionalPriceyearlyPlan.trim()); // int 55

			boolean pricePageMessage = pricePage.verifyMessage(parameters.get("msg_2"));
			reportLog(pricePageMessage, testContext.getName(), "Verify " + parameters.get("msg_2") + " is displayed",
					"3.0", parameters.get("msg_2")
							+ " is displayed in pricing page in header after clicking on Pricing sub-link");
			org.testng.Assert.assertTrue(pricePageMessage);

			/**
			 * Select Individual Plan from show pricing plans page
			 */

			boolean verifyPopUpMessageSelectButton = pricePage
					.clickOnSelectButtonIndividualPlan(parameters.get("pop_up_msg_1"));
			reportLog(verifyPopUpMessageSelectButton, testContext.getName(),
					"Verify " + parameters.get("pop_up_msg_1") + " is displayed", "4.0", parameters.get("pop_up_msg_1")
							+ " message display in open Pop up after clicking on 'Select Plan' button in show pricing plans page Is Verified");
			org.testng.Assert.assertTrue(verifyPopUpMessageSelectButton);

			boolean verifyPopUpDashboard = pricePage.clickOnSelectIndividualPlanButton(parameters.get("pop_up_msg_2"));
			reportLog(verifyPopUpDashboard, testContext.getName(),
					"Verify " + parameters.get("pop_up_msg_2") + " is displayed", "5.0", parameters.get("pop_up_msg_2")
							+ " message display in open Pop up after clicking on 'Select Individual Plan' button Is Verified");
			org.testng.Assert.assertTrue(verifyPopUpDashboard);

			boolean clickOnGotItButton = pricePage
					.clickOnGotItButtonInPopUpInDashboardPage(parameters.get("pop_up_msg_3"));
			reportLog(clickOnGotItButton, testContext.getName(),
					"Verify " + parameters.get("pop_up_msg_3") + " is displayed", "6.0", parameters.get("pop_up_msg_3")
							+ " Page open after clicking on 'Got It,Thanks Button!' Is Verified");
			org.testng.Assert.assertTrue(clickOnGotItButton);

			/**
			 * Open Pricing Page and Select Business Plan for to upgrade
			 */
			dashboardPage.clickPricingMenu().selectPricingOption();

			boolean checkoutMessage = pricePage.clickOnSelectPlan(parameters.get("msg_3"));
			reportLog(checkoutMessage, testContext.getName(), "Verify " + parameters.get("msg_3") + " is displayed",
					"7.0", parameters.get("msg_3")
							+ " page is open after clicking on 'Select Plan' button in show pricing plans page against Business Plan");
			org.testng.Assert.assertTrue(checkoutMessage);

			/**
			 * Select licenses from Checkout Page for Business Plan
			 */

			String countString = userPage.getLicenseCount(0);
			int numberOfLicenses = Integer.parseInt(countString);
			int licensesProfessionalMonthly = perprofessionalsPriceYearlyPlanCost * 12; // int 110 (55*2)
			int estimatedRate = licensesProfessionalMonthly * numberOfLicenses;
			String newRate = Integer.toString(licensesProfessionalMonthly); // Covert 110 into string
			NumberFormat formatter = new DecimalFormat("#,###.00");
			String newEstimatedTotalAnnualRate = formatter.format(estimatedRate); // String 1,028 (comma seprated)

			userPage.clickNextBtnNumberOfLicenses();
			userPage.clickNextBtnBillFreq();
			userPage.waitForBillingInfo();

			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.enterFieldData(labelList11, fieldDataList11);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();

			/**
			 * Enter Credit card detail
			 */

			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.switchToIframe(By.id("z_hppm_iframe"));

			String[] ccLabelList = parameters.get("ccfieldlabelist").split(";");
			String[] ccFieldDataList = parameters.get("ccfiledinputlist").split(";");
			userPage.enterCcData(ccLabelList, ccFieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));
			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();

			/**
			 * Final Confirmation of Business Plan Purchase
			 */

			boolean confirmPurchase = userPage.confirmPurchase(parameters.get("headertext"));
			reportLog(confirmPurchase, testContext.getName(), "Verify header text ", "8.0",
					"User redirect to " + parameters.get("headertext")
							+ " Page after purchasing the plan and its verified 'Business plan is purchased");
			org.testng.Assert.assertTrue(confirmPurchase);
			customWait(4); // This wait required because IN SOME of case After submitting data from
							// Check-out page have to wait for Price Menu to visible
			dashboardPage.clickPricingMenu().selectPricingOption();

			boolean verifyDisablePlan = pricePage.verifyCurrentPlan();
			reportLog(verifyDisablePlan, testContext.getName(), "Verify disable plan in pricing page ", "9.0",
					" 'Current Plan' Button against Business is disabled in Show pricing page and its verified user has Business Plan");
			org.testng.Assert.assertTrue(verifyDisablePlan);

			/**
			 * Upgrade plan from Business to Professional though Change Plan Button
			 */

			licensesBilling.clickonHomeAccountLink();
			licensesBilling.changePlan();

			boolean upgradeHeaderMessage = licensesBilling
					.UpgradToProfessionalPlan(parameters.get("headertextupgrade"));
			reportLog(upgradeHeaderMessage, testContext.getName(), "Verify header text ", "10.0", "User redirect to "
					+ parameters.get("headertextupgrade") + " Page after clicking on Change plan button");
			org.testng.Assert.assertTrue(upgradeHeaderMessage);

			/**
			 * Verify Current and New Plan in Upgrade to Professional Plan page
			 */

			String planLabel[] = parameters.get("planLabel").split(";");
			String planData[] = parameters.get("planData").split(";");
			String stepsArryConfirm[] = new String[] { "11.0", "12.0" };
			String stepsArryRate[] = new String[] { "13.0", "14.0", "15.0" };
			String upgradePlan[] = { newRate, countString, newEstimatedTotalAnnualRate };
			String rateLabel[] = parameters.get("ratelabel").split(";");

			licensesBilling.verifyUpgradePlan(testContext, planLabel, planData, stepsArryConfirm);
			licensesBilling.verifyNewUpgradeRate(testContext, upgradePlan, rateLabel, stepsArryRate);

			boolean professionalplanUpgradeMsg = licensesBilling
					.confirmCurrentPlanAfterUpgrade(parameters.get("headertext"));
			reportLog(professionalplanUpgradeMsg, testContext.getName(), "Verify header text ", "16.0",
					"User redirect to " + parameters.get("headertext")
							+ "Page after clicking on Confirm button AND Toast message 'Your plan has successfully been updated.' display");
			org.testng.Assert.assertTrue(professionalplanUpgradeMsg);

			boolean professionalplanUpgradeOrNot = licensesBilling
					.professionalPlanUpdateOrNot(parameters.get("professionalplanupgrade"));
			reportLog(professionalplanUpgradeOrNot, testContext.getName(), "Verify header text ", "17.0",
					"Text" + parameters.get("professionalplanupgrade")
							+ "  is verified in Billing Page and Its Verified Plan is upgrade to Professiona plan");
			org.testng.Assert.assertTrue(professionalplanUpgradeOrNot);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}

	}

	// QA-477 Zuora - Company Admin - Downgrade to Individual from paid
	@Test
	@Parameters({ "testdescription" })
	public void downgradeToIndividualFromPaid(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);
			LicensesAndBilling licensesBilling = new LicensesAndBilling(driver);
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();

			String[] labelList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");

			String[] labelList11 = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList11 = parameters.get("filedinputlist").split(";");
			homePage.enterFieldData(labelList, fieldDataList, email);
			homePage.clickTryItFreeButton();

			boolean msgVerify = signUpStep2.verifyMessage(parameters.get("msg_1"));
			reportLog(msgVerify, testContext.getName(), "Verify " + parameters.get("msg_1") + " is displayed", "1.0",
					parameters.get("msg_1") + " is displayed after clicking on 'TRY IT FREE' button during sign up");
			org.testng.Assert.assertTrue(msgVerify);

			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);

			signUpStep2.clickGetStartedButton();

			boolean msgVerifyTahnkYouPage = signUpStep2.verifyMessageInThankYouPage(parameters.get("msg_4"));
			reportLog(msgVerifyTahnkYouPage, testContext.getName(),
					"Verify " + parameters.get("msg_4") + " is displayed", "2.0", parameters.get("msg_4")
							+ " is displayed in thank you page after clicking on Get Started Button during sign up");
			org.testng.Assert.assertTrue(msgVerifyTahnkYouPage);

			driver.get(PropertyUtils.getProperty("app.url"));

			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage.clickPricingMenu().selectPricingOption();

			String monthlyText = pricePage.planPriceAnnualy(parameters.get("plan")); // 45
			String businessPriceYearlyPlan = monthlyText.substring(monthlyText.indexOf("$") + 1,
					monthlyText.indexOf("USD"));

			int businessPriceYearlyPlanCost = Integer.parseInt(businessPriceYearlyPlan.trim()); // int 45

			boolean pricePageMessage = pricePage.verifyMessage(parameters.get("msg_2"));
			reportLog(pricePageMessage, testContext.getName(), "Verify " + parameters.get("msg_2") + " is displayed",
					"3.0", parameters.get("msg_2")
							+ " is displayed in pricing page in header after clicking on Pricing sub-link");
			org.testng.Assert.assertTrue(pricePageMessage);

			/**
			 * Select Individual Plan from show pricing plans page
			 */

			boolean verifyPopUpMessageSelectButton = pricePage
					.clickOnSelectButtonIndividualPlan(parameters.get("pop_up_msg_1"));
			reportLog(verifyPopUpMessageSelectButton, testContext.getName(),
					"Verify " + parameters.get("pop_up_msg_1") + " is displayed", "4.0", parameters.get("pop_up_msg_1")
							+ " message display in open Pop up after clicking on 'Select Plan' button in show pricing plans page Is Verified");
			org.testng.Assert.assertTrue(verifyPopUpMessageSelectButton);

			boolean verifyPopUpDashboard = pricePage.clickOnSelectIndividualPlanButton(parameters.get("pop_up_msg_2"));
			reportLog(verifyPopUpDashboard, testContext.getName(),
					"Verify " + parameters.get("pop_up_msg_2") + " is displayed", "5.0", parameters.get("pop_up_msg_2")
							+ " message display in open Pop up after clicking on 'Select Individual Plan' button Is Verified");
			org.testng.Assert.assertTrue(verifyPopUpDashboard);

			boolean clickOnGotItButton = pricePage
					.clickOnGotItButtonInPopUpInDashboardPage(parameters.get("pop_up_msg_3"));
			reportLog(clickOnGotItButton, testContext.getName(),
					"Verify " + parameters.get("pop_up_msg_3") + " is displayed", "6.0", parameters.get("pop_up_msg_3")
							+ " Page open after clicking on 'Got It,Thanks Button!' Is Verified");
			org.testng.Assert.assertTrue(clickOnGotItButton);

			/**
			 * Open Pricing Page and Select Business Plan for to upgrade
			 * 
			 */
			dashboardPage.clickPricingMenu().selectPricingOption();

			boolean checkoutMessage = pricePage.selectPlanToUpgradeAndDowngrade(parameters.get("msg_3"), "2");
			reportLog(checkoutMessage, testContext.getName(), "Verify " + parameters.get("msg_3") + " is displayed",
					"7.0", parameters.get("msg_3")
							+ " page is open after clicking on 'Select Plan' button in show pricing plans page against Business Plan");
			org.testng.Assert.assertTrue(checkoutMessage);

			/**
			 * Select licenses from Checkout Page for Business Plan
			 */

			String countString = userPage.getLicenseCount(0);
			int numberOfLicenses = Integer.parseInt(countString);
			int licensesProfessionalMonthly = businessPriceYearlyPlanCost * 12; // int 110 (55*2)
			int estimatedRate = licensesProfessionalMonthly * numberOfLicenses;
			String newRate = Integer.toString(licensesProfessionalMonthly); // Covert 110 into string
			NumberFormat formatter = new DecimalFormat("#,###.00");
			String newEstimatedTotalAnnualRate = formatter.format(estimatedRate); // String 1,028 (comma seprated)

			userPage.clickNextBtnNumberOfLicenses();
			userPage.clickNextBtnBillFreq();
			userPage.waitForBillingInfo();

			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.enterFieldData(labelList11, fieldDataList11);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();

			/**
			 * Enter Credit card detail
			 */

			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.switchToIframe(By.id("z_hppm_iframe"));

			String[] ccLabelList = parameters.get("ccfieldlabelist").split(";");
			String[] ccFieldDataList = parameters.get("ccfiledinputlist").split(";");
			userPage.enterCcData(ccLabelList, ccFieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));
			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();

			/**
			 * Final Confirmation of Business Plan Purchase
			 * 
			 */

			boolean confirmPurchase = userPage.confirmPurchase(parameters.get("headertext"));
			reportLog(confirmPurchase, testContext.getName(), "Verify header text ", "8.0",
					"User redirect to " + parameters.get("headertext")
							+ " Page after purchasing the plan and its verified Business plan is purchased");
			org.testng.Assert.assertTrue(confirmPurchase);
			// userPage.waitForPricingMenu();
			customWait(4); // This wait required because IN SOME of case After submitting data from
							// Check-out page have to wait for Price Menu to visible
			dashboardPage.clickPricingMenu().selectPricingOption();

			boolean verifyDisablePlan = pricePage.verifyCurrentPlan();
			reportLog(verifyDisablePlan, testContext.getName(), "Verify disable plan in pricing page ", "9.0",
					" 'Current Plan' Button against Professional is disabled in Show pricing page and its verified user has Professional Plan");
			org.testng.Assert.assertTrue(verifyDisablePlan);

			/**
			 * Downgrade Plan from Professional to Business
			 */

			licensesBilling.clickonHomeAccountLink();
			licensesBilling.changePlan();

			boolean downgradeHeaderMessage = licensesBilling
					.downgradeToBusinessPlan(parameters.get("headertextupgrade"));
			reportLog(downgradeHeaderMessage, testContext.getName(), "Verify header text ", "10.0", "User redirect to "
					+ parameters.get("headertextupgrade") + " Page after clicking on 'Change plan' button");
			org.testng.Assert.assertTrue(downgradeHeaderMessage);

			/**
			 * Verify Current and New Plan Price and all in down-grade to Business Plan page
			 */

			String planLabel[] = parameters.get("planLabel").split(";");
			String planData[] = parameters.get("planData").split(";");
			String stepsArryConfirm[] = new String[] { "11.0", "12.0" };
			String stepsArryRate[] = new String[] { "13.0", "14.0", "15.0" };
			String upgradePlan[] = { newRate, countString, newEstimatedTotalAnnualRate };
			String rateLabel[] = parameters.get("ratelabel").split(";");

			licensesBilling.verifyUpgradePlan(testContext, planLabel, planData, stepsArryConfirm);
			licensesBilling.verifyNewUpgradeRate(testContext, upgradePlan, rateLabel, stepsArryRate);

			boolean downgradeYourPlanMsg = licensesBilling
					.downGradeYourPlan(parameters.get("downgradeheadertextafterupgrade"));
			reportLog(downgradeYourPlanMsg, testContext.getName(), "Verify header text ", "16.0",
					"" + parameters.get("downgradeheadertextafterupgrade")
							+ "  message is verified in 'Downgrade to Business Plan' Page ");
			org.testng.Assert.assertTrue(downgradeYourPlanMsg);

			boolean messageAfterConfirmingDowngrade = licensesBilling.confirmDowngrade(parameters.get("headertext"));
			reportLog(messageAfterConfirmingDowngrade, testContext.getName(), "Verify user re-direct to ", "17.0 ",
					"  User re-direct to " + parameters.get("headertext")
							+ " Page after clicking on 'Downgrade' Button in Open Pop Up");
			org.testng.Assert.assertTrue(messageAfterConfirmingDowngrade);

			boolean professionalplanDowngradeOrNot = licensesBilling
					.confirmDowngradeToBusiness(parameters.get("businessplanupgrade"));
			reportLog(professionalplanDowngradeOrNot, testContext.getName(), "Verify header text ", "18.0", "Text "
					+ parameters.get("businessplanupgrade")
					+ "  is verified in Licenses & Billing Page and Its Verified Plan is downgrade to Business plan");
			org.testng.Assert.assertTrue(professionalplanDowngradeOrNot);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-5509 Zuora - Sales User - Billing Options - Rates & Discounts
	@Test
	@Parameters({ "testdescription" })
	public void billingRateAndDiscounts(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);
			AdminBillingPage billingAdmin = new AdminBillingPage(driver);
			LicensesAndBilling licensesBilling = new LicensesAndBilling(driver);
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();

			String[] labelIDList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");
			homePage.enterFieldData(labelIDList, fieldDataList, email);
			homePage.clickTryItFreeButton();

			boolean msgVerify = signUpStep2.verifyMessage(parameters.get("msg_1"));
			reportLog(msgVerify, testContext.getName(), "Verify " + parameters.get("msg_1") + " is displayed", "1.0",
					parameters.get("msg_1") + " is displayed after clicking on 'TRY IT FREE' button in Dashboard");
			org.testng.Assert.assertTrue(msgVerify);

			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);

			signUpStep2.clickGetStartedButton();

			boolean msgVerifyTahnkYouPage = signUpStep2.verifyMessageInThankYouPage(parameters.get("msg_4"));
			reportLog(msgVerifyTahnkYouPage, testContext.getName(),
					"Verify " + parameters.get("msg_4") + " is displayed", "2.0", parameters.get("msg_4")
							+ " is displayed in thank you page after clicking on Get Started Button During Sign up");
			org.testng.Assert.assertTrue(msgVerifyTahnkYouPage);
			driver.get(PropertyUtils.getProperty("app.url"));

			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.clickOnManage(email);
			dashboardPage.clickPricingMenu().selectPricingOption();

			// Business - Yearly - Price
			String yearlyTextBusiness = pricePage.planPriceAnnualy(parameters.get("plan"));
			String businessPriceYearlyPlan = yearlyTextBusiness.substring(yearlyTextBusiness.indexOf("$") + 1,
					yearlyTextBusiness.indexOf("USD"));
			int perBusinessPricYearlyPlanCost = Integer.parseInt(businessPriceYearlyPlan.trim()); // int 45

			// Business - Monthly - Price
			String monthlyTextBusiness = pricePage.planPriceMonthly(parameters.get("plan"));
			String businessPriceMonthlyplan = monthlyTextBusiness.substring(monthlyTextBusiness.indexOf("$") + 1,
					monthlyTextBusiness.indexOf("month to"));// 51
			int businessMonthlyPrice = Integer.parseInt(businessPriceMonthlyplan.trim()); // int 51

			// Professional - Yearly - Price
			String yearlyTextProfessional = pricePage.planPriceAnnualy(parameters.get("planprof"));
			String professionalPriceyearlyPlan = yearlyTextProfessional
					.substring(yearlyTextProfessional.indexOf("$") + 1, yearlyTextProfessional.indexOf("USD"));
			int perProfessionalPricYearlyPlanCost = Integer.parseInt(professionalPriceyearlyPlan.trim()); // int 55

			// Professional - Monthly - Price
			String monthlyTextProfessional = pricePage.planPriceMonthly(parameters.get("planprof"));
			String professionalPriceMonthlyplan = monthlyTextProfessional
					.substring(monthlyTextProfessional.indexOf("$") + 1, monthlyTextProfessional.indexOf("month to"));// 51
			int professionalMonthlyPrice = Integer.parseInt(professionalPriceMonthlyplan.trim()); // int 62

			String[] labelList11 = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList11 = parameters.get("filedinputlist").split(";");
			String[] ccLabelList = parameters.get("ccfieldlabelist").split(";");
			String[] ccFieldDataList = parameters.get("ccfiledinputlist").split(";");

			/**
			 * Set Business -- Annual /Monthly -- Rate -- in Billing Page
			 */

			dashboardPage.clickHomeMenu().clickGoCanvasOnlyFromHomeDropDown();
			billingAdmin.enterRateAndDiscount("3", parameters.get("businessannualrate"), parameters.get("val1"), "1",
					parameters.get("businessmonthlyrate"), parameters.get("val2"));
			dashboardPage.clickPricingMenu().selectPricingOption();

			boolean checkoutMsg = pricePage.selectBusinessPlan(parameters.get("msg_3"), "1");
			reportLog(checkoutMsg, testContext.getName(), "Verify " + parameters.get("msg_3") + " is displayed", "3.0",
					parameters.get("msg_3")
							+ " page is open after clicking on 'Select Plan' button in show pricing plans page");
			org.testng.Assert.assertTrue(checkoutMsg);
			String countString = userPage.getLicenseCounts();
			userPage.clickNextButtonsAndFillDataOnCheckOutPage(labelList11, fieldDataList11);

			userPage.creditCardDetail(ccLabelList, ccFieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));

			/**
			 * Verify Correct Total Due calculated for Business -- Annual --Rate
			 */

			boolean businessSummaryAnnualRate = userPage
					.summaryAmountBusinessAnnualRate(parameters.get("businessannualrate"));
			reportLog(businessSummaryAnnualRate, testContext.getName(), "", "4.0",
					"Verify Total Due after applying Business Annual Rate in Summary Section is " + "$"
							+ parameters.get("businessannualrate") + " , Is verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(businessSummaryAnnualRate);

			boolean businessConfirmAnnualRate = userPage
					.purchaseAmounttBusinessAnnualRate(parameters.get("businessannualrate"));
			reportLog(businessConfirmAnnualRate, testContext.getName(), "", "5.0",
					"Verify Total Due after applying Business Annual Rate in Purchase Section is " + "$"
							+ parameters.get("businessannualrate") + ", Is verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(businessConfirmAnnualRate);

			/**
			 * Verify Correct Total Due calculated for Business -- Monthly -- Rate
			 */

			userPage.clickNextButtonsOnCheckOutPage();
			boolean businessSummaryMonthlyRate = userPage
					.summaryAmountBusinessAnnualRate(parameters.get("businessmonthlyrate"));
			reportLog(businessSummaryMonthlyRate, testContext.getName(), "", "6.0",
					"Verify Total Due after applying Business Monthly Rate in Summary Section is " + "$"
							+ parameters.get("businessmonthlyrate") + " , Is verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(businessSummaryMonthlyRate);

			boolean businessConfirmMonthlyRate = userPage
					.purchaseAmounttBusinessAnnualRate(parameters.get("businessmonthlyrate"));
			reportLog(businessConfirmMonthlyRate, testContext.getName(), "", "7.0",
					"Verify Total Due after applying Business Monthly Rate in Purchase Section is  " + "$"
							+ parameters.get("businessmonthlyrate") + ", Is verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(businessConfirmMonthlyRate);

			/**
			 * Set Business -- Annual/Monthly --- Discount in Billing Page
			 */

			dashboardPage.clickHomeMenu().clickGoCanvasOnlyFromHomeDropDown();
			billingAdmin.enterRateAndDiscount("4", parameters.get("businessannualdiscount"), parameters.get("val3"),
					"2", parameters.get("businessmonthlydicount"), parameters.get("val4"));
			dashboardPage.clickPricingMenu().selectPricingOption();

			boolean checkoutMsgBusiness = pricePage.selectBusinessPlan(parameters.get("msg_3"), "1");
			reportLog(checkoutMsgBusiness, testContext.getName(), "Verify " + parameters.get("msg_3") + " is displayed",
					"8.0", parameters.get("msg_3")
							+ " page is open after clicking on 'Select Plan' button in show pricing plans page");
			org.testng.Assert.assertTrue(checkoutMsgBusiness);
			userPage.clickNextButtonsAndFillDataOnCheckOutPage(labelList11, fieldDataList11);
			userPage.creditCardDetail(ccLabelList, ccFieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));

			// -- - Calculation of Discount Business - Annual- - - //

			double businessAnnualDiscount = billingAdmin
					.discountCalculationAnnual(parameters.get("businessannualdiscount"), perBusinessPricYearlyPlanCost);
			DecimalFormat df = new DecimalFormat("#.00");
			String discountString = df.format(businessAnnualDiscount);

			/**
			 * Verify Correct Total Due calculated for Business -- Annual -- Discount
			 */

			boolean businessSummaryAnnualDiscount = userPage.summaryAmountBusinessAnnualRate(discountString);
			reportLog(businessSummaryAnnualDiscount, testContext.getName(), "", "9.0",
					"Verify Total Due after applying Business Annual Discount of "
							+ parameters.get("businessannualdiscount") + "%" + " in Summary Section Is " + "$"
							+ discountString + " ,  verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(businessSummaryAnnualDiscount);

			boolean businessConfirmAnnualDiscount = userPage.purchaseAmounttBusinessAnnualRate(discountString);
			reportLog(businessConfirmAnnualDiscount, testContext.getName(), "", "10.0",
					"Verify Total Due after applying Business Annual Discount of "
							+ parameters.get("businessannualdiscount") + "%" + " in Purchase Section Is  " + "$"
							+ discountString + " ,  verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(businessConfirmAnnualDiscount);

			/** Set Business -- Monthly -- Discount * */

			userPage.clickNextButtonsOnCheckOutPage();

			double businessMonthlyDiscount = billingAdmin
					.discountCalculationMonthly(parameters.get("businessmonthlydicount"), businessMonthlyPrice);
			DecimalFormat discountMonthly = new DecimalFormat("#.00");
			String discountStringMonthly = discountMonthly.format(businessMonthlyDiscount);

			/**
			 * Verify Correct Total Due calculated for Business -- Monthly -- Discount
			 */

			boolean businessSummaryMonthlyDiscount = userPage.summaryAmountBusinessAnnualRate(discountStringMonthly);
			reportLog(businessSummaryMonthlyDiscount, testContext.getName(), "", "11.0",
					"Verify Total Due after applying Business Monthly Discount of "
							+ parameters.get("businessmonthlydicount") + "%" + " in Summary Section Is  " + "$"
							+ discountStringMonthly + " ,  verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(businessSummaryMonthlyDiscount);
			boolean businessConfirmMonthlyDiscount = userPage.purchaseAmounttBusinessAnnualRate(discountStringMonthly);
			reportLog(businessConfirmMonthlyDiscount, testContext.getName(), "", "12.0",
					"Verify Total Due after applying Business Monthly Discount of "
							+ parameters.get("businessmonthlydicount") + "%" + " in Purchase Section Is " + "$"
							+ discountStringMonthly + " ,  verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(businessConfirmMonthlyDiscount);

			/**
			 * Set Professional -- Annual/Monthly --- Rate in Billing Page
			 */

			dashboardPage.clickHomeMenu().clickGoCanvasOnlyFromHomeDropDown();
			billingAdmin.enterRateAndDiscount("7", parameters.get("professionalannualrate"), parameters.get("val5"),
					"5", parameters.get("professionalmonthlyrate"), parameters.get("val6"));
			dashboardPage.clickPricingMenu().selectPricingOption();

			boolean checkOutMsgProf = pricePage.selectBusinessPlan(parameters.get("msg_3"), "2");
			reportLog(checkOutMsgProf, testContext.getName(), "Verify " + parameters.get("msg_3") + " is displayed",
					"13.0", parameters.get("msg_3")
							+ " page is open after clicking on 'Select Plan' button in show pricing plans page");
			org.testng.Assert.assertTrue(checkOutMsgProf);
			userPage.clickNextButtonsAndFillDataOnCheckOutPage(labelList11, fieldDataList11);

			/**
			 * Credit Card Data detail entered and verify Correct Total due calculated for
			 * Business -- Monthly -- Discount
			 **/

			userPage.creditCardDetail(ccLabelList, ccFieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));
			boolean professionalSummaryAnnualRate = userPage
					.summaryAmountBusinessAnnualRate(parameters.get("professionalannualrate"));
			reportLog(professionalSummaryAnnualRate, testContext.getName(), "", "14.0",
					"Verify Total Due after applying Professional Annual Rate in Summary Section is " + "$"
							+ parameters.get("professionalannualrate") + " , Is verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(professionalSummaryAnnualRate);
			boolean professionalConfirmAnnualRate = userPage
					.purchaseAmounttBusinessAnnualRate(parameters.get("professionalannualrate"));
			reportLog(professionalConfirmAnnualRate, testContext.getName(), "", "15.0",
					"Verify Total Due after applying Professional Annual Rate in Purchase Section is " + "$"
							+ parameters.get("professionalannualrate") + ", Is verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(professionalConfirmAnnualRate);

			/**
			 * Verify Correct Total Due calculated for Professional -- Monthly -- Rate
			 */

			userPage.clickNextButtonsOnCheckOutPage();
			boolean professionalSummaryMonthlyRate = userPage
					.summaryAmountBusinessAnnualRate(parameters.get("professionalmonthlyrate"));
			reportLog(professionalSummaryMonthlyRate, testContext.getName(), "", "16.0",
					"Verify Total Due after applying Professional Monthly Rate in Summary Section is " + "$"
							+ parameters.get("professionalmonthlyrate") + " , Is verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(professionalSummaryMonthlyRate);
			boolean professionalConfirmMonthlyRate = userPage
					.purchaseAmounttBusinessAnnualRate(parameters.get("professionalmonthlyrate"));
			reportLog(professionalConfirmMonthlyRate, testContext.getName(), "", "17.0",
					"Verify Total Due after applying Professional Monthly Rate in Purchase Section is " + "$"
							+ parameters.get("professionalmonthlyrate") + ", Is verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(professionalConfirmMonthlyRate);

			/**
			 * Set Professional -- Annual/Monthly --- Discount in Billing Page
			 */

			dashboardPage.clickHomeMenu().clickGoCanvasOnlyFromHomeDropDown();
			billingAdmin.enterRateAndDiscount("8", parameters.get("professionalannualdiscount"), parameters.get("val7"),
					"6", parameters.get("professionalmonthlydiscount"), parameters.get("val8"));
			dashboardPage.clickPricingMenu().selectPricingOption();

			boolean profAnnualChecOutMsg = pricePage.selectBusinessPlan(parameters.get("msg_3"), "2");
			reportLog(profAnnualChecOutMsg, testContext.getName(),
					"Verify " + parameters.get("msg_3") + " is displayed", "18.0", parameters.get("msg_3")
							+ " page is open after clicking on 'Select Plan' button in show pricing plans page");
			org.testng.Assert.assertTrue(profAnnualChecOutMsg);
			userPage.clickNextButtonsAndFillDataOnCheckOutPage(labelList11, fieldDataList11);
			userPage.creditCardDetail(ccLabelList, ccFieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));

			// Calculation of Discount Professional - Annual

			double professionalAnnualDiscountVal = billingAdmin.discountCalculationAnnual(
					parameters.get("professionalannualdiscount"), perProfessionalPricYearlyPlanCost);
			DecimalFormat profAnnualDecimal = new DecimalFormat("#.00");
			String profDiscountString = profAnnualDecimal.format(professionalAnnualDiscountVal);

			/**
			 * Verify Correct Total Due calculated for Professional -- Annual --Discount
			 */

			boolean professionalSummaryAnnualDiscount = userPage.summaryAmountBusinessAnnualRate(profDiscountString);
			reportLog(professionalSummaryAnnualDiscount, testContext.getName(), "", "19.0",
					"Verify Total Due after applying Professional Annual Discount of "
							+ parameters.get("professionalannualdiscount") + "%" + " in Summary Section Is " + "$"
							+ profDiscountString + " ,  verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(professionalSummaryAnnualDiscount);
			boolean professionalConfirmAnnualDiscount = userPage.purchaseAmounttBusinessAnnualRate(profDiscountString);
			reportLog(professionalConfirmAnnualDiscount, testContext.getName(), "", "20.0",
					"Verify Total Due after applying Professional Annual Discount of "
							+ parameters.get("professionalannualdiscount") + "%" + " in Purchase Section Is " + "$"
							+ profDiscountString + " ,  verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(professionalConfirmAnnualDiscount);

			/** Professional -- Monthly -- Discount * */

			userPage.clickNextButtonsOnCheckOutPage();
			String professionalDiscountString = parameters.get("professionalmonthlydiscount");
			double profDiscountValInt = Double.parseDouble(professionalDiscountString);
			double professionalDiscount = (professionalMonthlyPrice * profDiscountValInt) / 100;
			double professionalMonthlyDiscount = (professionalMonthlyPrice - professionalDiscount);

			DecimalFormat profDecimal = new DecimalFormat("#.00");
			String profDiscountStringMonthly = profDecimal.format(professionalMonthlyDiscount);

			/**
			 * Verify Correct Total Due calculated for Professional -- Monthly --Discount
			 */

			boolean professionalSummaryMonthlyDiscount = userPage
					.summaryAmountBusinessAnnualRate(profDiscountStringMonthly);
			reportLog(professionalSummaryMonthlyDiscount, testContext.getName(), "", "21.0",
					"Verify Total Due after applying Professional Monthly Discount of "
							+ parameters.get("professionalmonthlydiscount") + "%" + " in Summary Section Is " + "$"
							+ profDiscountStringMonthly + " ,  verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(professionalSummaryMonthlyDiscount);
			boolean professionalConfirmMonthlyDiscount = userPage
					.purchaseAmounttBusinessAnnualRate(profDiscountStringMonthly);
			reportLog(professionalConfirmMonthlyDiscount, testContext.getName(), "", "22.0",
					"Verify Total Due after applying Professional Monthly Discount of "
							+ parameters.get("professionalmonthlydiscount") + "%" + " in Purchase Section Is " + "$"
							+ profDiscountStringMonthly + " ,  verified for 1 Seat Purchase");
			org.testng.Assert.assertTrue(professionalConfirmMonthlyDiscount);

			/**
			 * *** **** ******* Final Purchase **** With Applying Some Discount ***** *****
			 * * **** **** ** *****
			 */

			userPage.clickBillingInfo();
			userPage.enterFieldDataUsStateTaxAndNonTax(parameters.get("Us_tax_country"), parameters.get("Us_tax_city"),
					parameters.get("Us_tax_state"), parameters.get("Us_tax_zip"));
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();

			String salesTax = userPage.getSalesTax().replace("$", ""); // $0.00
			double salesTaxInt = Float.parseFloat(salesTax);
			double totalDueAfterApplySalesTax = (professionalMonthlyDiscount + salesTaxInt);
			DecimalFormat profDecimalDue = new DecimalFormat("#.00");
			String totalDueProfDiscountStringMonthly = profDecimalDue.format(totalDueAfterApplySalesTax);

			userPage.confirmPurchase();
			String renewDateFormat = userPage.getDateFormat();
			String renewalDate = userPage.findTodaysDate(parameters.get("planmonthly"), renewDateFormat);

			boolean licensesAndBillingMsg = userPage.licensesAndBilling()
					.equals(parameters.get("licensesAndBillingHeaderMsg"));
			reportLog(licensesAndBillingMsg, testContext.getName(), "Verify User redirect to" + " ", "23.0",
					" Verify User redirect to 'Billing page' " + " after purchasing Seat and clicking on Billing link");
			org.testng.Assert.assertTrue(licensesAndBillingMsg);
			String openSeatFirstPurchase = licensesBilling.openSeat();
			String filledSeatFirstPurchase = licensesBilling.filledSeat();

			String[] licensesLabel = parameters.get("licensesLabel").split(";");
			String licensesData[] = new String[] { parameters.get("confirm_billing_freq_firstpur"), renewalDate,
					openSeatFirstPurchase, filledSeatFirstPurchase, countString };
			String stepsConfirm[] = new String[] { "24.0", "25.0", "26.0", "27.0", "28.0" };

			licensesBilling.readLicensesCount(testContext, licensesLabel, licensesData, stepsConfirm, 1, "");

			boolean amountPaymentHistory = userPage.getAmountPaymentHistoey(totalDueProfDiscountStringMonthly);
			reportLog(amountPaymentHistory, testContext.getName(), "Verify amount" + " ", "29.0",
					" Total Due (Amount+Sales Tax) in Payment History Section Is : " + "$"
							+ totalDueProfDiscountStringMonthly + " Verified after purchasing Seat with Applying "
							+ parameters.get("professionalmonthlydiscount") + "%" + " Discount");
			org.testng.Assert.assertTrue(amountPaymentHistory);
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-3557 Zuora - Company Admin - Cancel Account

	@Test
	@Parameters({ "testdescription" })
	public void companyAdminCancelAccount(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);
			LicensesAndBilling licensesBilling = new LicensesAndBilling(driver);
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();
			String[] labelIDList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");
			String[] labelIDList11 = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList11 = parameters.get("filedinputlist").split(";");
			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			homePage.enterFieldData(labelIDList, fieldDataList, email);
			homePage.clickTryItFreeButton();
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);

			signUpStep2.clickGetStartedButton();
			driver.get(PropertyUtils.getProperty("app.url"));

			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			login.clickSearchUser();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage.clickPricingMenu().selectPricingOption();

			pricePage.clickOnSelectPlan();
			String readLicenseCount = userPage.getParticularLicenseCount();
			int count = Integer.parseInt(readLicenseCount);
			System.out.println("Licenses val " + count);

			userPage.clickNextBtnNumberOfLicenses();
			userPage.clickNextBtnBillFreq();
			userPage.waitForInvisibilityOfProgress();

			userPage.enterFieldData(labelIDList11, fieldDataList11);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.switchToIframe(By.id("z_hppm_iframe"));

			String[] ccLabelIDList = parameters.get("ccfieldlabelist").split(";");
			String[] ccFieldDataList = parameters.get("ccfiledinputlist").split(";");
			userPage.enterCcData(ccLabelIDList, ccFieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.confirmPurchase();

			boolean userMessage = userPage.verifyUserMessage(parameters.get("headertext"));
			reportLog(userMessage, testContext.getName(), "Verify " + parameters.get("headertext") + " is displayed",
					"1.0",
					" User redirect to User & Seats pages after purchasing seat : " + count + " from checkout page");
			org.testng.Assert.assertTrue(userMessage);

			/*
			 * Adding user as per the available license
			 */
			userPage.addUserPerLicense(count, testContext, parameters.get("fn_error_message"),
					parameters.get("ln_error_message"), parameters.get("email_error_message"),
					parameters.get("buttontext"), parameters.get("duplicatErrormsg"), parameters.get("duplicatemail"),"Users");

			boolean verifyCountMatch = userPage.countRow(count);
			reportLog(verifyCountMatch, testContext.getName(), "Verify added user count match" + " with seat count",
					"5.0", " No. of user displying in User & Seats page " + " become equal to Seat counts");
			org.testng.Assert.assertTrue(verifyCountMatch);

			/*
			 * Get all mail id's
			 */

			ArrayList<String> emailValues = userPage.getUserEmails(1);
			String[] strArray = emailValues.toArray(new String[emailValues.size()]);

			boolean licensesAndBillingMsg = userPage.licensesAndBilling()
					.equals(parameters.get("licensesAndBillingHeaderMsg"));
			reportLog(licensesAndBillingMsg, testContext.getName(), "Verify User redirect to" + " ", "6.0",
					" Verify User redirect to 'Billing page' " + " after clicking on Billing link");
			org.testng.Assert.assertTrue(licensesAndBillingMsg);

			boolean cancelPlanPopUpMesg = licensesBilling
					.clickCancelPlanButton(parameters.get("cancelYourAccountPopUpMsg"));
			reportLog(cancelPlanPopUpMesg, testContext.getName(),
					"Verify " + parameters.get("cancelYourAccountPopUpMsg") + " is displayed", "7.0",
					"Message  " + parameters.get("cancelYourAccountPopUpMsg")
							+ " is displayed in Open Pop Up after clicking on Cancel Plan ");
			org.testng.Assert.assertTrue(cancelPlanPopUpMesg);

			boolean cancelPlan = licensesBilling.clickCancelMyAccount(parameters.get("afterCancelPlanMsg"));
			reportLog(cancelPlan, testContext.getName(),
					"Verify " + parameters.get("afterCancelPlanMsg") + " is displayed", "8.0",
					"User redirect to " + parameters.get("afterCancelPlanMsg") + " page after cancelling the account");
			org.testng.Assert.assertTrue(cancelPlan);

			/* Verify disabled user able to log-in or Not */
			userPage.verifyDisableUserAbleToLogin(strArray, (parameters.get("accountdisablemsg")), testContext, 9);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// QA-5501 Zuora - Company Admin - User can update shipping address via Billing
	// Info

	@Test
	@Parameters({ "testdescription" })
	public void updateAddressViaBillingInfo(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);
			LicensesAndBilling licensesBilling = new LicensesAndBilling(driver);
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();
			String[] labelIDList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");
			String[] labelIDList11 = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList11 = parameters.get("filedinputlist").split(";");
			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			String[] labelIDListAddress = parameters.get("fieldlabelistaddress").split(";");

			homePage.enterFieldData(labelIDList, fieldDataList, email);
			homePage.clickTryItFreeButton();
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);

			signUpStep2.clickGetStartedButton();
			driver.get(PropertyUtils.getProperty("app.url"));

			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));
			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage.clickPricingMenu().selectPricingOption();

			pricePage.clickOnSelectPlan();
			String readLicenseCount = userPage.getLicenseCount(0);
			int count = Integer.parseInt(readLicenseCount);

			userPage.clickNextBtnNumberOfLicenses();
			userPage.clickNextBtnBillFreq();
			userPage.waitForInvisibilityOfProgress();

			/**
			 * Enter data in Check-out page with Address (Billing and Shipping)
			 */
			userPage.enterFieldData(labelIDList11, fieldDataList11);
			userPage.clickNoRadioOption();
			userPage.enterFieldDataShippingNotSameBillingUsState(parameters.get("Us_tax_country"),
					parameters.get("Us_tax_city"), parameters.get("Us_tax_state"), parameters.get("Us_tax_zip"),
					parameters.get("Us_tax_address"));

			/**
			 * Read Address (Billing and Shipping) From Check-out Page after entering the
			 * address
			 */

			ArrayList<String> emailValuesCheckoutPage = userPage.readAddressData(labelIDListAddress);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.switchToIframe(By.id("z_hppm_iframe"));

			String[] cclabelIDList = parameters.get("ccfieldlabelist").split(";");
			String[] ccfieldDataList = parameters.get("ccfiledinputlist").split(";");
			userPage.enterCcData(cclabelIDList, ccfieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));

			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));
			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.confirmPurchase();

			boolean userMessage = userPage.verifyUserMessage(parameters.get("headertext"));
			reportLog(userMessage, testContext.getName(), "Verify " + parameters.get("headertext") + " is displayed",
					"1.0",
					" Billing and Shipping Address is Entered and User redirect to User & Seats pages after purchasing seat : "
							+ count + " from checkout page");
			org.testng.Assert.assertTrue(userMessage);

			boolean licensesAndBillingMsg = userPage.licensesAndBilling()
					.equals(parameters.get("licensesAndBillingHeaderMsg"));
			reportLog(licensesAndBillingMsg, testContext.getName(), "Verify User redirect to" + " ", "2.0",
					" User redirect to 'Billing page' "
							+ " after clicking on Billing link from Checkout Page Is verified");
			org.testng.Assert.assertTrue(licensesAndBillingMsg);

			/**
			 * Verify entered address in (Billing and Shipping) is same as Entered in
			 * Check-out page
			 */
			userPage.editAddressInBillingPage();
			ArrayList<String> emailValuesBillingPage = userPage.readAddressData(labelIDListAddress);
			userPage.verifyAddress(emailValuesCheckoutPage, emailValuesBillingPage, testContext, 3, "Checkout",
					"Billing", "Entered");

			/**
			 * Update Shipping address with No Option
			 */
			String[] updatedLabelAddressFromBillingPage = parameters.get("fieldlabelist").split(";");
			String[] updatedDataAddressFromBillingPage = parameters.get("updatedFiledInputList").split(";");
			userPage.enterFieldData(updatedLabelAddressFromBillingPage, updatedDataAddressFromBillingPage);
			userPage.enterFieldDataShippingNotSameBillingUsState(parameters.get("Us_tax_country_updated"),
					parameters.get("Us_tax_city_updated"), parameters.get("Us_tax_state_updated"),
					parameters.get("Us_tax_zip_updated"), parameters.get("Us_tax_address_updated"));

			/**
			 * Get updated Address data from Billing Page
			 */
			ArrayList<String> entereAddressInBillingPage = userPage.readAddressData(labelIDListAddress);
			userPage.saveButtonBillingPage();
			boolean checkOutPage = userPage.checkoutPageRedirect().equals(parameters.get("additionalSeatsHeaderMsg"));
			reportLog(checkOutPage, testContext.getName(), "Verify User redirect to" + " ", "4.0",
					" User redirect to 'Purchase Additional Seats page' "
							+ " after updating address from billing page Is verified");
			org.testng.Assert.assertTrue(checkOutPage);
			userPage.redirectCheckoutPageAndVerifyAddress();

			/**
			 * Verify updated address in Checkout (Additional Seats) page updated from
			 * Billing Page
			 */
			ArrayList<String> getAddressInCheckOutPage = userPage.readAddressData(labelIDListAddress);
			userPage.verifyAddress(entereAddressInBillingPage, getAddressInCheckOutPage, testContext, 5, "Billing",
					"Purchase Additional Seats", "Updated");

			boolean licensesAndBillingMsgVerify = userPage.licensesAndBilling().equals(parameters.get("licensesAndBillingHeaderMsg"));
			reportLog(licensesAndBillingMsg, testContext.getName(), "Verify User redirect to" + " ", "6.0"," User redirect to 'Billing page' "+ " after clicking on Billing link From Checkout Page Is verified");
			org.testng.Assert.assertTrue(licensesAndBillingMsgVerify);

			/**
			 * Open Down-grade / Upgrade Plan Page and verify the Save address data
			 */

			boolean changePlan = licensesBilling.changePlanLink().equals(parameters.get("downgradeupgrade"));
			reportLog(changePlan, testContext.getName(), "Verify User redirect to" + " ", "7.0"," User redirect to 'Upgrade Page ' "+ " after clicking on Change Plan link from Billing Page Is verified");
			org.testng.Assert.assertTrue(changePlan);

			licensesBilling.editAddressInDowngradeUpgradePage();
			ArrayList<String> getAddressInDowngradeUpgradePage = userPage.readAddressData(labelIDListAddress);
			userPage.verifyAddress(entereAddressInBillingPage, getAddressInDowngradeUpgradePage, testContext, 8,"Downgrade to Business Plan", "Billing", "");

			/**
			 * Update Address (Billing/Shipping) from Down-grade / Upgrade Plan Page and
			 * verify address in check-out page
			 */

			String[] updatedLabelAddressFromDowngradePage = parameters.get("fieldlabelist").split(";");
			String[] updatedDataAddressFromDowngradePage = parameters.get("updatedFiledInputListDowngrade").split(";");
			userPage.enterFieldData(updatedLabelAddressFromDowngradePage, updatedDataAddressFromDowngradePage);
			userPage.enterFieldDataShippingNotSameBillingUsState(parameters.get("Us_tax_country_updated_downgrade"),
					parameters.get("Us_tax_city_updated_downgrade"), parameters.get("Us_tax_state_updated_downgrade"),
					parameters.get("Us_tax_zip_updated_downgrade"), parameters.get("Us_tax_address_updated_downgrade"));
			ArrayList<String> enterAddressInDowngradepage = userPage.readAddressData(labelIDListAddress);

			boolean confirmPurchaseButton = userPage.confrimButton(parameters.get("headertext"));
			reportLog(confirmPurchaseButton, testContext.getName(), "Verify confirm button text ", "9.0","User redirect to " + parameters.get("headertext")
							+ " after updating address from downgrade page Is verified");
			org.testng.Assert.assertTrue(confirmPurchaseButton);

			boolean licensesAndBilling = userPage.licensesAndBilling()
					.equals(parameters.get("licensesAndBillingHeaderMsg"));
			reportLog(licensesAndBilling, testContext.getName(), "Verify User redirect to" + " ", "10.0",
					" User redirect to 'Billing page' "
							+ " after clicking on Billing link from Checkout Page Is verified");
			org.testng.Assert.assertTrue(licensesAndBilling);

			/**
			 * Verify entered address in Billing page is same as Entered in Down-grade page
			 */
			userPage.editAddressInBillingPage();
			ArrayList<String> upatedAddressFromBillingPage = userPage.readAddressData(labelIDListAddress);
			userPage.verifyAddress(enterAddressInDowngradepage, upatedAddressFromBillingPage, testContext, 11,"Downgrade", "Billing", "Entered");

			licensesBilling.clickBillingLink();
			boolean checkoutPageFromBilling = userPage.checkoutPageRedirect().equals(parameters.get("additionalSeatsHeaderMsg"));
			reportLog(checkoutPageFromBilling, testContext.getName(), "Verify User redirect to" + " ", "12.0"," User redirect to 'Purchase Additional Seats page' " + "Is verified");
			org.testng.Assert.assertTrue(checkoutPageFromBilling);

			userPage.redirectCheckoutPageAndVerifyAddress();
			ArrayList<String> getUpdatedAddressInCheckOutPage = userPage.readAddressData(labelIDListAddress);
			userPage.verifyAddress(enterAddressInDowngradepage, getUpdatedAddressInCheckOutPage, testContext, 13,"Downgrade", "'Purchase Additional Seats page' ", "Entered");
			
		
			/**
			 * Update Address in Billing frequency page
			 */
			boolean licensesAndBillingFreq = userPage.licensesAndBilling().equals(parameters.get("licensesAndBillingHeaderMsg"));
			reportLog(licensesAndBillingFreq, testContext.getName(), "Verify User redirect to" + " ", "14.0"," User redirect to 'Billing page' "+ " after clicking on Billing link from Checkout Page Is verified");
			org.testng.Assert.assertTrue(licensesAndBillingFreq);
			
			boolean changeFrequency = licensesBilling.changeFrequencyLink().equals(parameters.get("changeBillingFreqHeaderText"));
			reportLog(changeFrequency, testContext.getName(), "Verify User redirect to" + " ", "15.0"," User redirect to 'Change Billing Frequency Page' "+ " after clicking on Change Billing Frequency  link from Billing Page Is verified");
			org.testng.Assert.assertTrue(changeFrequency);
		
			licensesBilling.editAddressInDowngradeUpgradePage();
			ArrayList<String> getAddressInChangeFrequencyPage = userPage.readAddressData(labelIDListAddress);
			userPage.verifyAddress(getUpdatedAddressInCheckOutPage, getAddressInChangeFrequencyPage, testContext, 16,"Change Frequency", "Billing", "");

			
			/**
			 * Update Address (Billing/Shipping) from Change Frequency Page and
			 * verify address in check-out page
			 */

			String[] updatedLabelChangeFrequencyPage = parameters.get("fieldlabelist").split(";");
			String[] updatedDataAddressFromChangeFrequencyPage = parameters.get("updatedFiledInputListFrequency").split(";");
			userPage.enterFieldData(updatedLabelChangeFrequencyPage, updatedDataAddressFromChangeFrequencyPage);
			userPage.enterFieldDataShippingNotSameBillingUsState(parameters.get("Us_tax_country_updated_ChangeFrequency"),
					parameters.get("Us_tax_city_updated_ChangeFrequency"), parameters.get("Us_tax_state_updated_ChangeFrequency"),
					parameters.get("Us_tax_zip_updated_ChangeFrequency"), parameters.get("Us_tax_address_updated_ChangeFrequency"));
			
			ArrayList<String> enterAddressInChangeFrequencypage = userPage.readAddressData(labelIDListAddress);
			
			boolean confirmPurchaseButtonFrequency = userPage.confrimButton(parameters.get("licensesAndBillingHeaderMsg"));
			reportLog(confirmPurchaseButtonFrequency, testContext.getName(), "Verify User redirect to" + " ", "17.0"," User redirect to 'Billing page' "+ " after updating address from change billing frequency Page Is verified");
			org.testng.Assert.assertTrue(confirmPurchaseButtonFrequency);
			
			boolean checkoutPageFromBillingChangeFrequency = userPage.checkoutPageRedirect().equals(parameters.get("additionalSeatsHeaderMsg"));
			reportLog(checkoutPageFromBillingChangeFrequency, testContext.getName(), "Verify User redirect to" + " ", "18.0"," User redirect to 'Purchase Additional Seats page' " + "Is verified");
			org.testng.Assert.assertTrue(checkoutPageFromBillingChangeFrequency);
			
			
			userPage.redirectCheckoutPageAndVerifyAddress();
			ArrayList<String> getUpdatedAddressInCheckOutPageFromChangeFrequency = userPage.readAddressData(labelIDListAddress);
			userPage.verifyAddress(enterAddressInChangeFrequencypage, getUpdatedAddressInCheckOutPageFromChangeFrequency, testContext, 19,"Change Billing Frequency", "'Purchase Additional Seats page' ", "Entered");
			
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	// GOC-6055 Zuora - Add seats while in a department

	@Test
	@Parameters({ "testdescription" })
	public void zuoraAddSeatsIndDepartment(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			LoginPage login = new LoginPage(driver);
			UserPage userPage = new UserPage(driver);
			DashboardPage dashboardPage = new DashboardPage(driver);
			LicensesAndBilling licensesBilling = new LicensesAndBilling(driver);
			PricingPage pricePage = new PricingPage(driver);
			SearchUsersPage searchUsersPage = new SearchUsersPage(driver);
			SignUpStep2 signUpStep2 = new SignUpStep2(driver);
			String email1 = randomAlphaNumeric(10) + "@yopmail.com";
			String email = email1.toLowerCase();
			String[] labelIDList = parameters.get("labelidlist").split(";");
			String[] fieldDataList = parameters.get("inputlist").split(";");
			String[] labelIDList11 = parameters.get("fieldlabelist").split(";");
			String[] fieldDataList11 = parameters.get("filedinputlist").split(";");
			String[] labelIDList1 = parameters.get("labelidlist2").split(";");
			String[] fieldDataList1 = parameters.get("inputlist2").split(";");
			homePage.enterFieldData(labelIDList, fieldDataList, email);
			homePage.clickTryItFreeButton();
			signUpStep2.enterFieldData(labelIDList1, fieldDataList1);

			signUpStep2.clickGetStartedButton();
			driver.get(PropertyUtils.getProperty("app.url"));

			login.gotoLogin();
			login.typeusername(parameters.get("admin")).typepassword(parameters.get("adminPwd"));
			login.Clickonloginbutton();
			searchUsersPage.enterEmail(email);
			searchUsersPage.clickSearch().clickOnMore(email);
			searchUsersPage.clickOption(parameters.get("text_1")).clickActivate(parameters.get("text_2"));

			/* Enable department from Admin*/

			searchUsersPage.clickOnMore(email).clickFeatures().clickEnableDepartment();
			searchUsersPage.clickOnMore(email).clickFeatures();
	
			boolean departmentDisable = searchUsersPage.isDisableDepartmentDisplayed();
			reportLog(departmentDisable, testContext.getName(), " Departement disable or not", "1.0",
					" Departement is Enabled By Admin " + " Is Verified ");
			org.testng.Assert.assertTrue(departmentDisable);

			searchUsersPage.logout();
			login.typeusername(email).typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			dashboardPage.clickPricingMenu().selectPricingOption();

			pricePage.clickOnSelectPlan();
			String readLicenseCount = userPage.getLicenseCount(0);
			int count = Integer.parseInt(readLicenseCount);
			userPage.clickNextBtnNumberOfLicenses();
			userPage.clickNextBtnBillFreq();
			userPage.waitForInvisibilityOfProgress();

			userPage.enterFieldData(labelIDList11, fieldDataList11);
			userPage.clickNextBtnBillingInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.switchToIframe(By.id("z_hppm_iframe"));

			String[] cclabelIDList = parameters.get("ccfieldlabelist").split(";");
			String[] ccfieldDataList = parameters.get("ccfiledinputlist").split(";");
			userPage.enterCcData(cclabelIDList, ccfieldDataList, parameters.get("ccMonth"),
					parameters.get("ccMonthxpath"), parameters.get("ccYear"), parameters.get("ccYearxpath"));
			userPage.selectCCMonth(parameters.get("ccMonth"), parameters.get("ccMonthxpath"));
			userPage.selectCcYear(parameters.get("ccYear"), parameters.get("ccYearxpath"));
			userPage.switchToDefault();
			userPage.clickNextBtnCreditCardInfo();
			userPage.waitForInvisibilityOfProgress();
			userPage.confirmPurchase();
			userPage.clickOnDepartmentLink();
			userPage.clickOnUsersSeatsButton();

			boolean userMessage = userPage.verifyDepartmentMessage(parameters.get("departmentheadertext"));
			reportLog(userMessage, testContext.getName(),
					"Verify " + parameters.get("departmentheadertext") + " is displayed", "1.1",
					" User redirect to department  " + parameters.get("departmentheadertext")
							+ " pages after purchasing seat : " + count + " from checkout page");
			org.testng.Assert.assertTrue(userMessage);

			boolean byDefaultDepartementSelected = dashboardPage
					.isDepartmentDisplayed(parameters.get("departmentheadertext"));
			reportLog(byDefaultDepartementSelected, testContext.getName(),
					" Verify by default First departement selected", "1.2"," By default " + parameters.get("departmentheadertext")+ " is Selected when user Re-direct After purchasing the seat from Checkout page" + " Is Verified ");
			org.testng.Assert.assertTrue(byDefaultDepartementSelected);

			/* Adding user as per the available license */
			
			userPage.addUserPerLicense(count, testContext, parameters.get("fn_error_message"),
					parameters.get("ln_error_message"), parameters.get("email_error_message"),
					parameters.get("buttontext"), parameters.get("duplicatErrormsg"), parameters.get("duplicatemail"),"department");

			boolean verifyCountMatch = userPage.countRow(count);
			reportLog(verifyCountMatch, testContext.getName(), "Verify added user count match" + " with seat count",
					"5.0", " No. of Users displying in First Department page " + " become equal to Seat counts");
			org.testng.Assert.assertTrue(verifyCountMatch);
			
			
		
			//Add More Seats
			licensesBilling.addLicenseButton();
			String countAdditionalLicenses = userPage.getLicenseCount(1);
			
			// Select Billing Frequency

			userPage.clickNextBtnNumberOfLicenses();
			userPage.clickNextBtnBillingContact();
			userPage.clickNextBtnBillingCreditCard();
			userPage.waitForInvisibilityOfProgress();
			userPage.confirmPurchase();

			boolean purchaseAdditionalLicenses = userPage.verifyDepartmentMessage(parameters.get("departmentheadertext"));
			reportLog(purchaseAdditionalLicenses, testContext.getName(),
					"Verify " + parameters.get("departmentheadertext") + " is displayed", "6.0"," User redirect to   " + parameters.get("departmentheadertext")+ " pages AGAIN after purchasing Additional seat: " + countAdditionalLicenses + " from checkout page");
			org.testng.Assert.assertTrue(userMessage);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
