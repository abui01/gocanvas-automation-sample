package com.canvas.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;

import com.canvas.qa.test.UtilityFunctions;

/**
 * @author kailash.pathak
 *
 */
public class LicensesAndBilling extends BasePage {

	@FindBy(xpath = "//h3[@class='billing-cycle']")
	WebElement summaryBillingFrequency;

	@FindBy(xpath = "//span[@class='font-bold renewal-date']")
	WebElement renewaldate;

	@FindBy(xpath = "//span[contains(@class,'font-bold license-count break-word')]")
	WebElement confirmPurchaseCount;

	@FindBy(xpath = "//a[contains(.,'Purchase Seats')]")
	WebElement addLicenses;

	@FindBy(xpath = "//span[contains(.,'Number of Seats')]")
	WebElement numberOfLicenses;

	String businesesAnnualPrice = "(//div[@class ='total-plan-cost'])[%s]/b";

	public LicensesAndBilling(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		// mixedContentChecker(driver);
		javaScriptErrorChecker(driver);
	}

	/**
	 * 
	 * @param testContext
	 * @param licenseslabel
	 * @param licensesdata
	 * @param stepsArry_confirm
	 * @param num
	 * @param time
	 * @throws InterruptedException
	 * 
	 *             This method read Billing frequency and renewal date
	 */
	public void readLicensesCount(ITestContext testContext, String licenseslabel[], String licensesdata[],
			String stepsArry_confirm[], int num, String time) throws InterruptedException {

		for (int i = 0; i <= 4; i++) {
			By by = By.xpath("(//div[contains(@class,'table-responsive')])[1]//tr[" + num + "]//td[" + (i + 1) + "]");
			WebElement ele = getElement(driver, by);
			boolean billingFrequency = ele.getText().contains(licensesdata[i]);
			reportLog(billingFrequency, testContext.getName(), "On Billing page ", stepsArry_confirm[i] + " ",
					(licenseslabel[i]) + "  " + (licensesdata[i]) + "  is verified in Billing page " + time);
			org.testng.Assert.assertTrue(billingFrequency);
		}
	}

	public void addLicenseButton() {
		clickOnHiddenElement(fluentWait(addLicenses, driver), driver);
		waitForVisbility(driver, numberOfLicenses, 30);
	}

	@FindBy(xpath = "//a[contains(.,'Remove Licenses')]")
	WebElement removeLicensePresent;

	@FindBy(xpath = "//h1[contains(.,'Update License Renewals')]")
	WebElement updateLicenseRenewal;

	public boolean verifyRemoveLicenses() {
		return isElementPresent(driver, removeLicensePresent);

	}

	public void clickonRemoveLicenses(ITestContext testContext, String license_renewals_msg, String step2) {
		clickOnHiddenElement(fluentWait(removeLicensePresent, driver), driver);
		boolean updateLicenseRenewalsMsg = licenseRenewals().equals(license_renewals_msg);
		reportLog(updateLicenseRenewalsMsg, testContext.getName(),
				"Verify User redirect to Update License Renewals " + " ", step2,
				" Verify User redirect to Update License Renewals page after clicking on 'Licenses & Billing link ' is verified");
	}

	public String licenseRenewals() {
		String licenseRenewalsHeadermsg = getElementText(driver, updateLicenseRenewal);
		return licenseRenewalsHeadermsg;
	}

	@FindBy(xpath = "//input[@data-index='license_0']")
	WebElement licensetorenew;

	@FindBy(xpath = "//h1[@class='font-bold']")
	WebElement licensesfilled;

	@FindBy(xpath = "(//td[@class='text-right'])[1]")
	WebElement annualFrequency;

	@FindBy(xpath = "(//td[@class='text-right'])[2]")
	WebElement monthlyFrequency;

	@FindBy(xpath = "//span[contains(@id,'all-users-count')]")
	WebElement activeusers;

	@FindBy(xpath = "//input[@data-index='license_0']")
	WebElement licensestoRenewAnnully;

	@FindBy(xpath = "//p[contains(@id,'errorMsg')]")
	WebElement errormsg;

	@FindBy(id = "adjust-licenses-submit")
	WebElement updateButton;

	@FindBy(xpath = "//span[contains(.,'Account')]")
	WebElement clickOnAccount;

	@FindBy(xpath = "(//a[contains(.,'Account')])[1]")
	WebElement clickOnAccountPriceingPage;

	String licensesToRenewMonthlyAndAnnuly = "//input[@data-index='%s']";

	@FindBy(xpath = "//a[contains(.,'Account Settings')]")
	WebElement accountSettingLink;

	@FindBy(xpath = "(//a[contains(.,'Settings')])[6]")
	WebElement accountDate;

	@FindBy(xpath = "(//a[contains(.,'Settings')])[7]")
	WebElement accountAdminDate;

	@FindBy(xpath = "//select[contains(@id,'format')]")
	WebElement defaultDateFormat;

	/**
	 * 
	 * @param testContext
	 * @param zerroCountErrormsg
	 * @param otherthanZeroErrormsg
	 * @param license_renewals_msg
	 * @param licensesXpath
	 * @param Frequency
	 * @param step1
	 * @param step2
	 * @param step3
	 * 
	 *            This method cover different -2 scenario for licenses renew for
	 *            Annual and Monthly plan
	 */
	public void verifyRenewalsScenarios(ITestContext testContext, String zerroCountErrormsg,
			String otherthanZeroErrormsg, String license_renewals_msg, String licensesXpath, String Frequency,
			String step1, String step2, String step3) {

		if (isElementPresent(driver, removeLicensePresent) == false) {

			boolean removeLicensesPresent = verifyRemoveLicenses();
			reportLog(!removeLicensesPresent, testContext.getName(), "Verify Remove Licenses Link", step1,
					" Remove Licenses is NOT present in Licenses & Billing is verified");
		} else if (Frequency.equals("Annual")) {

			boolean removeLicensesPresent = verifyRemoveLicenses();
			reportLog(removeLicensesPresent, testContext.getName(), "Verify Remove Licenses Link", step1,
					" Remove Licenses is present in Licenses & Billing is verified");
			clickonRemoveLicenses(testContext, license_renewals_msg, step2);

			String licenseAvailable1 = getElementText(driver, annualFrequency);
			String users = getElementText(driver, activeusers).trim();
			int activeUsers = Integer.parseInt(users);
			int licensesToRenew = UtilityFunctions.getRandomNumberInRange(0, 10);
			WebElement xpath = makeXpath(licensesToRenewMonthlyAndAnnuly, licensesXpath);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].value=" + licensesToRenew, xpath);
			int total_Licenses_Available1 = Integer.parseInt(licenseAvailable1);
			verifyPendingLicense(testContext, zerroCountErrormsg, otherthanZeroErrormsg, step3, activeUsers,
					licensesToRenew, total_Licenses_Available1);
		}

		else if (Frequency.equals("Monthly")) {
			boolean removeLicensesPresent = verifyRemoveLicenses();
			reportLog(removeLicensesPresent, testContext.getName(), "Verify Remove Licenses Link", step1,
					" Remove Licenses is present in Licenses & Billing is verified");
			clickonRemoveLicenses(testContext, license_renewals_msg, step2);

			String licenseAvailable2 = getElementText(driver, monthlyFrequency);
			int total_Licenses_Available2 = Integer.parseInt(licenseAvailable2);
			String users = getElementText(driver, activeusers).trim();
			int activeUsers = Integer.parseInt(users);
			int licensesToRenew = UtilityFunctions.getRandomNumberInRange(0, 10);
			WebElement xpath = makeXpath(licensesToRenewMonthlyAndAnnuly, licensesXpath);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].value=" + licensesToRenew, xpath);
			verifyPendingLicense(testContext, zerroCountErrormsg, otherthanZeroErrormsg, step3, activeUsers,
					licensesToRenew, total_Licenses_Available2);

		}
	}

	public void verifyPendingLicense(ITestContext testContext, String zerroCountErrormsg, String otherthanZeroErrormsg,
			String step3, int activeUsers, int licensesToRenew, int total_Licenses_Available) {
		if (licensesToRenew == 0) {
			boolean errorMsg = getvalueErrorMsg(zerroCountErrormsg);
			reportLog(errorMsg, testContext.getName(), "Verify Licenses To Renew when user enter value Zero ", step3,
					"Number of licenses entered in text box for renew is " + licensesToRenew
							+ " , As licenses To Renew is EQUAL to =0 Error message  " + zerroCountErrormsg
							+ " is verified");

		} else if (licensesToRenew > total_Licenses_Available) {
			boolean error_msg = getvalueErrorMsg(otherthanZeroErrormsg);
			reportLog(error_msg, testContext.getName(), "Verify Licenses To Renew when user enter value Zero ", step3,
					"Number of licenses entered in text box for renew is   " + licensesToRenew
							+ " , As licenses To Renew is greater than total_Licenses_Available  Error message  "
							+ otherthanZeroErrormsg + " is verified");

		} else if (licensesToRenew < activeUsers) {
			boolean error_msg = getvalueErrorMsg(zerroCountErrormsg);
			reportLog(error_msg, testContext.getName(), "Verify Licenses To Renew when user enter value Zero ", step3,
					"Number of licenses entered in text box for renew is " + licensesToRenew
							+ " , As licenses To Renew is less than active user Error message  " + zerroCountErrormsg
							+ " is verified");
		} else if (licensesToRenew == activeUsers) {
			boolean buttonEnable = verifyUpdateButton();
			reportLog(buttonEnable, testContext.getName(), "Verify update button is enabled ", step3,
					"Number of licenses entered in text box for renew is " + licensesToRenew
							+ " , As licenses To Renew is EQUAL to active user, Update button is enabled and clickable is verified");
		} else if (licensesToRenew == total_Licenses_Available) {
			boolean buttonEnable = verifyUpdateButton();
			reportLog(buttonEnable, testContext.getName(), "Verify update button is enabled ", step3,
					"Number of licenses entered in text box for renew is " + licensesToRenew
							+ ", As licenses To Renew equal to total_Licenses_Available  , Update button is enabled and clickable is verified");
		} else if (licensesToRenew == activeUsers) {
			boolean buttonEnable = verifyUpdateButton();
			reportLog(!buttonEnable, testContext.getName(), "Verify Update button is not display ", step3,
					"Number of licenses entered in text box for renew is " + licensesToRenew
							+ "As licenses To Renew equal to Active user, Update button is enabled and clickable is verified");

		} else if (total_Licenses_Available > licensesToRenew) {
			boolean buttonEnable = verifyUpdateButton();
			reportLog(buttonEnable, testContext.getName(), "Verify Update button is  display ", step3,
					"Number of licenses entered in text box for renew is  " + licensesToRenew
							+ " ,As total_Licenses_Available is greater than licensesToRenew , Update button is enabled and clickable is verified");
		}
	}

	public boolean getvalueErrorMsg(String Errormsg) {
		licensestoRenewAnnully.sendKeys(Keys.TAB);
		boolean errorMsg = errormsg.getText().contains(Errormsg);
		return errorMsg;

	}

	public boolean verifyUpdateButton() {
		licensestoRenewAnnully.sendKeys(Keys.TAB);
		boolean buttonEnable = updateButton.isEnabled();
		return buttonEnable;
	}

	public void clickonAccountLink() {
		clickOnHiddenElement(fluentWait(clickOnAccount, driver), driver);
	}

	@FindBy(xpath = "(//strong[contains(.,'Confirm License Renewals')])[2]")
	WebElement licenseRenewalsPopUpMsg;

	@FindBy(xpath = "//button[contains(.,'Confirm Renewals')]")
	WebElement confirmRenewalsButton;

	@FindBy(xpath = "//h1[contains(.,'Licenses & Billing')]")
	WebElement licensesBillingHeaderMsg;

	@FindBy(xpath = "(//span[contains(.,'Pending')])[2]")
	WebElement pendingLink;

	@FindBy(xpath = "//i[@class='fa fa-question-circle fa-fw t-tip-icon pending-order icon-color']")
	WebElement toopTip;

	public void verifyPendingLicenses(ITestContext testContext, String license_renewals_msg, String licensesXpath,
			String step1, String step2, String step3) {

		if (isElementPresent(driver, removeLicensePresent) == false) {

			boolean removeLicensesPresent = verifyRemoveLicenses();
			reportLog(!removeLicensesPresent, testContext.getName(), "Verify Remove Licenses Link", step1,
					" Remove Licenses is NOT present in Licenses & Billing is verified");
		} else {
			boolean removeLicensesPresent = verifyRemoveLicenses();
			reportLog(removeLicensesPresent, testContext.getName(), "Verify Remove Licenses Link", step1,
					" Remove Licenses is present in Licenses & Billing is verified");
			clickonRemoveLicenses(testContext, license_renewals_msg, step2);
			String licenseAvailableString = getElementText(driver, annualFrequency);
			int licenseAvailable = Integer.parseInt(licenseAvailableString);
			int licensesToRenew = licenseAvailable - 1;
			WebElement xpath = makeXpath(licensesToRenewMonthlyAndAnnuly, licensesXpath);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].value=" + licensesToRenew, xpath);
			xpath.sendKeys(Keys.TAB);
			clickOnUpdateButton(testContext, "10.0");
			clickOnConfirmRenewalsButton(testContext, "11.0");
			verifyPendingLinkAfterDecereasingLicenseCount(testContext, "12.0");
			verifyToolTip(testContext, "13.0");
		}
	}

	/**
	 * This method user to click on Update button and to verify Confirm License
	 * Renewals Pop up open
	 * 
	 * @param testContext
	 * @param step
	 */
	public void clickOnUpdateButton(ITestContext testContext, String step) {
		clickOnHiddenElement(fluentWait(updateButton, driver), driver);
		boolean confirmLicenseRenewal = licenseRenewalsPopUpMsg.getText().contains("Confirm License Renewals");
		reportLog(confirmLicenseRenewal, testContext.getName(), "Verify Confirm License Renewals in Pop up", step,
				" Verify Confirm License Renewals Pop up open when user click on 'update button' Is verified");
	}

	/**
	 * This method used to click on Confirm Renewal Button on Open Pop up
	 * 
	 * @param testContext
	 * @param step
	 */
	public void clickOnConfirmRenewalsButton(ITestContext testContext, String step) {
		String myWindowHandle = driver.getWindowHandle();
		driver.switchTo().window(myWindowHandle);
		clickOnHiddenElement(fluentWait(confirmRenewalsButton, driver), driver);
		boolean confirmLicenseRenewal = licensesBillingHeaderMsg.getText().contains("Licenses & Billing");
		reportLog(confirmLicenseRenewal, testContext.getName(),
				"Verify User redirect to Licenses & Billing Page after clicking on confirm Renewals Button ", step,
				" Verify User redirect to Licenses & Billing Page after clicking on confirm Renewals Button Is verified");
	}

	/**
	 * This method used to verify Text "Pending" under Licenses to Renew column
	 * 
	 * @param testContext
	 * @param step
	 */
	public void verifyPendingLinkAfterDecereasingLicenseCount(ITestContext testContext, String step) {
		boolean confirmLicenseRenewal = pendingLink.getText().equals("Pending");
		reportLog(confirmLicenseRenewal, testContext.getName(), "Verify Pending link ", step,
				" Verify Pending link is verfied after entering one value less than total 'Licenses Available'");
	}

	/**
	 * This method used to verify toop tip against text "Pending"
	 * 
	 * @param testContext
	 * @param step
	 */

	public void verifyToolTip(ITestContext testContext, String step) {
		boolean toolTip = toopTip.isDisplayed();
		reportLog(toolTip, testContext.getName(), "Verify tool tip ", step,
				" Tool tip against Pending text is Present Is verified");
	}

	@FindBy(xpath = "//h1[@title='Upgrade to Professional Plan']")
	WebElement upgradeToProfessional;

	String planUpgrade = "(//div[@class='col-sm-6'])['%s']";

	@FindBy(xpath = "//span[contains(.,'Confirm')]")
	WebElement confirmButton;

	@FindBy(xpath = "//h1[contains(.,'Users & Seats')]")
	WebElement userHeaderMsg;

	@FindBy(xpath = "//a[contains(.,'Billing')]")
	WebElement licensesAndBilling;

	@FindBy(xpath = "//p[contains(.,'Current Professional Licenses')]")
	WebElement planUpgradeHeaderMsg;

	@FindBy(xpath = "//h1[@title='Downgrade to Business Plan']")
	WebElement downGradeToBusiness;

	@FindBy(xpath = "//strong[contains(.,'Are you sure you want to downgrade your plan?')]")
	WebElement downGradeYourPlan;

	@FindBy(xpath = "//button[@type='button'][contains(.,'Downgrade')]")
	WebElement downGradeButton;

	@FindBy(xpath = "//span[contains(.,'Home')]")
	WebElement homeLink;

	@FindBy(xpath = "//a[contains(.,'Change Plan')]")
	WebElement changePlan;

	@FindBy(xpath = "//strong[contains(.,'Professional Plan')]")
	WebElement professionalPlanText;

	@FindBy(xpath = "//strong[contains(.,'Business Plan')]")
	WebElement businessPlanText;

	public boolean UpgradToProfessionalPlan(String headerMessgae) throws InterruptedException {
		boolean buttonText = getElementText(driver, upgradeToProfessional).equals(headerMessgae);
		return buttonText;
	}

	/**
	 * Verify upgrade Plan from Current Plan to New Plan
	 * 
	 * @param testContext
	 * @param planLabel
	 * @param planData
	 * @param stepsArry_confirm
	 * @throws InterruptedException
	 */
	public void verifyUpgradePlan(ITestContext testContext, String planLabel[], String planData[],
			String stepsArry_confirm[]) throws InterruptedException {
		for (int i = 0; i <= 1; i++) {
			By by = By.xpath("(//div[@class='col-sm-6'])[" + (i + 1) + "]");
			WebElement ele = getElement(driver, by);
			boolean billingPlan = ele.getText().trim().contains(planData[i]);
			reportLog(billingPlan, testContext.getName(), "On Upgrade to Professional Plan page ",
					stepsArry_confirm[i] + " ", (planLabel[i]) + "  " + (planData[i])
							+ "  For 'Annual' Frequency is verified in 'Upgrade to Professional' Plan page");
			org.testng.Assert.assertTrue(billingPlan);
		}
	}

	/**
	 * Verify upgrade plan Rate (New Rate,Number of Licenses,New Estimated Total
	 * Annual Rate
	 * 
	 * @param testContext
	 * @param planRate
	 * @param rateLabel
	 * @param stepsArry_rate
	 * @throws InterruptedException
	 */
	public void verifyNewUpgradeRate(ITestContext testContext, String planRate[], String rateLabel[],
			String stepsArry_rate[]) throws InterruptedException {

		for (int i = 0; i <= 2; i++) {
			By by = By.xpath("(//div[@class='col-sm-6'])[" + (i + 3) + "]");
			WebElement ele = getElement(driver, by);
			boolean billingPlan = ele.getText().trim().contains(planRate[i]);
			reportLog(billingPlan, testContext.getName(), "On Upgrade to Professional Plan page ",
					stepsArry_rate[i] + " ",
					(rateLabel[i]) + "  " + (planRate[i]) + "  is verified in 'Upgrade to Professional' Plan page");
			org.testng.Assert.assertTrue(billingPlan);
		}
	}

	/**
	 * This method is to verify after plan upgrade user re-direct to correct page
	 * 
	 * @param headerMsg
	 * @return
	 */
	public boolean confirmCurrentPlanAfterUpgrade(String headerMsg) {
		clickOnHiddenElement(fluentWait(confirmButton, driver), driver);
		waitForVisbility(driver, userHeaderMsg, 60);
		boolean confirmLicenseRenewal = getElementText(driver, userHeaderMsg).contains(headerMsg);
		return confirmLicenseRenewal;
	}

	public boolean downgradeToBusinessPlan(String headerMessgae) throws InterruptedException {
		boolean headerText = getElementText(driver, downGradeToBusiness).equals(headerMessgae);
		return headerText;
	}

	public boolean downGradeYourPlan(String headerMsg) {
		boolean downGradeYourPlanMessage = getElementText(driver, downGradeYourPlan).contains(headerMsg);
		return downGradeYourPlanMessage;
	}

	public boolean confirmDowngrade(String userHeaderConfirmMsg) {
		clickOnHiddenElement(fluentWait(confirmButton, driver), driver);
		clickOnHiddenElement(fluentWait(downGradeButton, driver), driver);
		waitForVisbility(driver, userHeaderMsg, 60);
		boolean headerMessageAfterConfirmPurchase = getElementText(driver, userHeaderMsg)
				.contains(userHeaderConfirmMsg);
		return headerMessageAfterConfirmPurchase;
	}

	public boolean confirmCurrentPlanAfterDownGrade(String headerMsg) {
		boolean confirmLicenseRenewal = getElementText(driver, planUpgradeHeaderMsg).contains(headerMsg);
		return confirmLicenseRenewal;
	}

	public void clickonHomeAccountLink() {
		clickOnHiddenElement(fluentWait(homeLink, driver), driver);
		clickOnHiddenElement(fluentWait(clickOnAccountPriceingPage, driver), driver);
		fluentWait(licensesAndBilling, driver).click();
	}

	public void changePlan() {
		clickOnHiddenElement(fluentWait(changePlan, driver), driver);
	}

	public boolean professionalPlanUpdateOrNot(String professionalplanupgrade) {
		fluentWait(licensesAndBilling, driver).click();
		boolean professionalPlanUpgradeMsg = getElementText(driver, professionalPlanText)
				.contains(professionalplanupgrade);
		return professionalPlanUpgradeMsg;
	}

	public boolean confirmDowngradeToBusiness(String headerConfirmMsg) {
		fluentWait(licensesAndBilling, driver).click();
		boolean headerMessageAfterConfirmPurchaseBusiness = getElementText(driver, businessPlanText)
				.contains(headerConfirmMsg);
		return headerMessageAfterConfirmPurchaseBusiness;
	}

	@FindBy(xpath = "//td[@class='text-right'][1]")
	WebElement openSeat;
	@FindBy(xpath = "//td[@class='text-right'][2]")
	WebElement filledSeat;

	@FindBy(xpath = "//a[contains(.,'Cancel Plan')]")
	WebElement cancelPlan;

	@FindBy(xpath = "//a[@class='btn btn-primary btn-sm']")
	WebElement CancelMyAccount;

	@FindBy(xpath = "//h5[contains(.,'Cancel your account?')]")
	WebElement cancelYourPopUpMsg;

	@FindBy(xpath = "//h1[contains(.,'Log In to Your Account')]")
	WebElement loginPageText;

	public String openSeat() {
		String noOfOpenSeat = getElementText(driver, openSeat);
		return noOfOpenSeat;
	}

	public String filledSeat() {
		String noOfOpenSeat = getElementText(driver, filledSeat);
		return noOfOpenSeat;
	}

	public String getDateFormat() {
		clickOnAccount.click();
		accountSettingLink.click();
		accountDate.click();
		String dateFormat = defaultDateFormat.getAttribute("value");
		return dateFormat;
	}

	public boolean clickCancelPlanButton(String cancelYourAccountMsg) {
		fluentWait(cancelPlan, driver).click();
		boolean msgAfterCancelAccount = getElementText(driver, cancelYourPopUpMsg).equals(cancelYourAccountMsg);
		return msgAfterCancelAccount;
	}

	public boolean clickCancelMyAccount(String loginPageTexts) {
		fluentWait(CancelMyAccount, driver).click();
		boolean msgAfterCancelAccount = getElementText(driver, loginPageText).equals(loginPageTexts);
		return msgAfterCancelAccount;
	}

	@FindBy(id = "edit-billing-contact")
	WebElement editBillingConact;

	@FindBy(xpath = "//label[contains(.,'Current Plan')]")
	WebElement currentPlanText;

	@FindBy(xpath = "//h1[contains(@title,'Upgrade to Professional Plan')]")
	WebElement upgradeProfessonalPlan;

	@FindBy(xpath = "//h1[contains(.,'Company')]")
	WebElement company;

	@FindBy(xpath = "//h1[contains(.,'Billing')]")
	WebElement billingHeaderText;
	
	@FindBy(xpath = "//a[contains(.,'Change Billing Frequency')]")
	WebElement changeFrequency;
	
	@FindBy(xpath = "//label[contains(.,'Current Billing Frequency')]")
	WebElement currentBillingFrequency;
	
	@FindBy(xpath = "//h1[contains(@title,'Change Billing Frequency')]")
	WebElement changeBillingFrequencyHeaderText;

	public String changePlanLink() {
		changePlan();
		waitForVisbility(driver, currentPlanText, 60);
		String changePlanText = getElementText(driver, upgradeProfessonalPlan);
		return changePlanText;
	}

	public void editAddressInDowngradeUpgradePage() {
		scrollToElementClick(driver, editBillingConact);
	}

	public void clickBillingLink() {
		fluentWait(licensesAndBilling, driver).click();
		waitForVisbility(driver, billingHeaderText, 60);
	}
	
	public void changeFrequency() {
		clickOnHiddenElement(fluentWait(changeFrequency, driver), driver);
	}
	
	public String changeFrequencyLink() {
		changeFrequency();
		waitForVisbility(driver, currentBillingFrequency, 60);
		String changeFrequencyText = getElementText(driver, changeBillingFrequencyHeaderText);
		return changeFrequencyText;
	}

}
