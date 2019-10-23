package com.canvas.qa.pages;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import com.canvas.qa.test.UtilityFunctions;
import com.gargoylesoftware.htmlunit.javascript.host.intl.DateTimeFormat;

/**
 * @author kailash.pathak
 *
 */
public class UserPage extends BasePage {

	@FindBy(xpath = "//span[contains(text(),'Cancel')]")
	WebElement cancelButton;

	String selectPaymentType = "//*[@class='plan-type' and contains(text(),'%s')][1]";
	String selectPaymentTypeUserPage = "(//*[@class='plan-cost'])[%s]/h3";
	String businesesAnnualPrice = "(//div[@class ='total-plan-cost'])[%s]/b";

	public UserPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		// mixedContentChecker(driver);
		javaScriptErrorChecker(driver);
	}

	@FindBy(xpath = "((//a[contains(text(),'Select Plan')])[1])")
	WebElement selectPlanButton;

	@FindBy(id = "licenseCount")
	WebElement licenseCount;

	@FindBy(xpath = "//*[@class='plan-type' and contains(text(),'Annual')][1]")
	WebElement annual;

	@FindBy(xpath = "//button[contains(.,' Next')]")
	List<WebElement> nextButton;

	@FindBy(xpath = "//*[contains(@class,'plan-type')]")
	List<WebElement> monthlyRadio;

	@FindBy(xpath = "(//*[@class='plan-cost'])[1]/h3")
	WebElement annulaPlanCost;

	@FindBy(xpath = "(//div[@class ='total-plan-cost'])[1]/b")
	WebElement totalAnnualyPlanCost;

	@FindBy(xpath = "(//div[@class ='total-plan-cost'])[2]/b")
	WebElement totalMonthlyPlanCost;

	@FindBy(id = "billing_contact_first_name")
	WebElement firstNameBilling;

	@FindBy(id = "input-creditCardHolderName")
	WebElement nameOnCard;

	@FindBy(xpath = "//span[contains(.,'First Name is required')]")
	WebElement firstNameError;

	@FindBy(xpath = "//span[contains(.,'Please wait...')]")
	WebElement progressBar;

	public boolean selectlicenseCount(String count) throws InterruptedException

	{
		Select selectValue = new Select(driver.findElement(By.id("licenseCount")));
		WebElement option = selectValue.getFirstSelectedOption();
		boolean selectedLicenceCount = option.getText().equals(count);
		return selectedLicenceCount;
	}

	public void clickNextButton(int num) throws InterruptedException {

		fluentWait(nextButton.get(num), driver).click();
		customWait(20);
	}

	public void selectPlanRadioOption(int num) throws InterruptedException {
		fluentWait(monthlyRadio.get(num), driver).click();
	}

	public boolean verifyPaymentType(String paymentType) throws InterruptedException {
		WebElement element = makeXpath(selectPaymentType, paymentType);
		String paymentText = element.getText();
		if (paymentText.contains(paymentType))
			;
		return true;
	}

	public boolean verifyPlanRate(String annualValue) {
		String paymentText = getElementText(driver, annulaPlanCost);
		if (paymentText.contains(annualValue))
			;
		return true;
	}

	public boolean verifyTotalCostMonthly(String annualPrice) {
		String annualRate = getElementText(driver, totalMonthlyPlanCost);
		if (annualRate.contains(annualPrice))
			;
		return true;
	}

	public boolean verifyTotalCostAnnualy(String annualPrice) {
		String annualRate = getElementText(driver, totalAnnualyPlanCost);
		if (annualRate.contains(annualPrice))
			;
		return true;
	}

	String errorMessageXpath = "//span[text() = '%s']";

	String inputFieldXpath = "//input[@id='%s']";
	String inputFieldXpathAddress = "//Select[@id='%s']";
	String confirmPurchasexpath = "//span[@class='%s']";
	String summaryPurchasexpath = "//h3[@class='%s']";
	String ccerrorMessageXpath = "//div[@id='%s']";
	String ccFieldXpath = ".//*[@id='%s']";

	public boolean isErrorMessageDisplayed(String text) {
		WebElement element = makeXpath(errorMessageXpath, text);
		return element.isDisplayed();
	}

	public UserPage enterFieldData(String fieldID, String data) {
		addDataInFields(inputFieldXpath, fieldID, data);
		return this;
	}

	public void enterFieldData(String fieldID[], String data[]) {

		for (int i = 0; i < fieldID.length; i++) {
			addDataInFields(inputFieldXpath, fieldID[i], data[i]);
		}
	}

	public boolean verifyDataSaved() {
		return nextButton.get(3).isDisplayed();
	}

	@FindBy(xpath = "//span[@class='font-bold selected-plan-type']")
	WebElement planValue;

	@FindBy(xpath = "//span[@class='font-bold license-count']")
	WebElement licensecount;

	@FindBy(xpath = "//span[@class='font-bold billing-cycle']")
	WebElement billingFrequency;

	@FindBy(xpath = "//span[@class='font-bold renewal-date']")
	WebElement renewalDate;

	@FindBy(xpath = "//span[@class='total-due']")
	WebElement totalDueAmt;

	@FindBy(id = "purchase-submit")
	WebElement confirmPurchase;

	@FindBy(xpath = "//span[@class='total-due']")
	WebElement confirmPurchaseTotalDue;

	@FindBy(xpath = "//h3[@class='total-due']")
	WebElement summaryPurchaseTotalDue;

	@FindBy(xpath = "//span[contains(@class,'font-bold license-count break-word')]")
	WebElement confirmPurchaseCount;

	@FindBy(xpath = "//h3[@class='license-count break-word']")
	WebElement confirmBillingSummaryCount;

	@FindBy(xpath = "//a[@data-toggle='dropdown'][contains(.,'Pricing')]")
	WebElement pricing;

	@FindBy(xpath = "//div[@id='error']")
	WebElement ccErrorMsg;

	@FindBy(id = "error-creditCardNumber")
	WebElement cardNumber;

	public boolean verifyRenewalDate(String renewdate) throws InterruptedException {
		boolean renewalDates = getElementText(driver, renewalDate).equalsIgnoreCase(renewdate);
		return renewalDates;
	}

	public UserPage verifyConfirmPurchase(ITestContext testContext, String fieldxpath[], String data[],
			String stepsArry[], String fieldlabel[]) {
		for (int i = 0; i < fieldxpath.length; i++) {
			WebElement element = makeXpath(confirmPurchasexpath, fieldxpath[i]);
			boolean operatorVal = element.getText().equals(data[i]);
			reportLog(operatorVal, testContext.getName(), "On Confirm Purchase Section ",
					stepsArry[i] + " On Confirm Purchase Section " + fieldlabel[i] + " : ",
					"" + data[i] + "  Is Verified");
		}
		return this;
	}

	public UserPage verifyBillingSummary(ITestContext testContext, String fieldxpath[], String data[],
			String stepsArry[], String fieldlabel[]) {
		for (int i = 0; i < fieldxpath.length; i++) {
			WebElement element = makeXpath(summaryPurchasexpath, fieldxpath[i]);
			boolean operatorVal = element.getText().equals(data[i]);
			reportLog(operatorVal, testContext.getName(), "On Billing Summary Section ",
					stepsArry[i] + " On Billing Summary Section " + fieldlabel[i] + " : ",
					"" + data[i] + "  Is Verified");
		}
		return this;
	}

	public boolean confrimPurchaseButtonText(String buttontxt) throws InterruptedException {
		boolean buttonText = getElementText(driver, confirmPurchase).equals(buttontxt);
		return buttonText;
	}

	public boolean confirmPurchase(String buttontxt) throws InterruptedException {
		clickOnHiddenElement(fluentWait(confirmPurchase, driver), driver);
		waitForVisbility(driver, userMsg, 60);
		boolean buttonText = getElementText(driver, userMsg).equals(buttontxt);
		return buttonText;
	}

	public void waitForPricingMenu() throws InterruptedException {
		waitForClickablility(driver, pricing, 30);
	}

	public String findTodayDate(String plantype) {
		String pattern1 = "d";
		String pattern2 = "MMMMM";
		String pattern4 = "yyyy";
		Date today = new Date();

		Calendar now = Calendar.getInstance();
		TimeZone timeZone = now.getTimeZone();

		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);
		simpleDateFormat1.setTimeZone(timeZone);
		String day = simpleDateFormat1.format(today);

		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
		simpleDateFormat2.setTimeZone(timeZone);
		String month = simpleDateFormat2.format(today);

		SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat(pattern4);
		simpleDateFormat4.setTimeZone(timeZone);
		String year = simpleDateFormat4.format(today);

		int annualYear;
		if (plantype.equals("Monthly")) {
			annualYear = Integer.parseInt(year);
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 1);
			Date afterDate = new Date(c.getTimeInMillis());
			String afterDateOneMonth = simpleDateFormat2.format(afterDate);
			month = afterDateOneMonth;
		} else
			annualYear = Integer.parseInt(year) + 1;
		String sufffix = sufix(Integer.parseInt(day));
		String renewalDate = month + " " + day + sufffix + "," + " " + annualYear;
		return renewalDate;

	}

	public String findTodaysDate(String plantype, String dateFormate) {

		String renewalDate = "dd/MM/yyyy";
		/*
		 * DateTimeFormatter etFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		 * 
		 * ZoneId istZoneId = ZoneId.of("Asia/Kolkata"); ZoneId etZoneId =
		 * ZoneId.of("America/New_York");
		 * 
		 * LocalDateTime currentDateTime = LocalDateTime.now(); ZonedDateTime
		 * currentISTime = currentDateTime.atZone(istZoneId); ZonedDateTime currentETime
		 * = currentISTime.withZoneSameInstant(etZoneId);
		 */

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-4"));
		String dateString = dateFormat.format(date);

		String str[] = dateString.split("/");
		int day = Integer.parseInt(str[0]);
		int month = Integer.parseInt(str[1]);
		int year = Integer.parseInt(str[2]);

		if (plantype.equals("Annual") && dateFormate.equalsIgnoreCase("dd/MM/yyyy")) {
			renewalDate = (day < 10 ? ("0" + day) : (day)) + "/" + (month < 10 ? ("0" + month) : (month)) + "/"
					+ (year + 1); // DD/MM/YYYY

		} else if (plantype.equals("Annual") && dateFormate.equalsIgnoreCase("MM/dd/yyyy")) {
			renewalDate = (month < 10 ? ("0" + month) : (month)) + "/" + (day < 10 ? ("0" + day) : (day)) + "/"
					+ (year + 1); // MM/DD/YYYY

		} else if (plantype.equals("Annual") && dateFormate.equalsIgnoreCase("yyyy/MM/dd")) {
			renewalDate = (year + 1) + "/" + (month < 10 ? ("0" + month) : (month)) + "/"
					+ (day < 10 ? ("0" + day) : (day));

		} else if (plantype.equals("Monthly") && dateFormate.equalsIgnoreCase("dd/MM/yyyy")) {
			renewalDate = (day < 10 ? ("0" + day) : (day)) + "/" + (month < 10 ? ("0" + (month + 1)) : (month + 1))
					+ "/" + year; // DD/MM/YYYY

		} else if (plantype.equals("Monthly") && dateFormate.equalsIgnoreCase("MM/dd/yyyy")) {
			renewalDate = (month < 10 ? ("0" + (month + 1)) : (month + 1)) + "/" + (day < 10 ? ("0" + day) : (day))
					+ "/" + year; // MM/DD/YYYY

		} else if (plantype.equals("Monthly") && dateFormate.equalsIgnoreCase("yyyy/MM/dd")) {
			renewalDate = year + "/" + (month < 10 ? ("0" + (month + 1)) : (month + 1)) + "/"
					+ (day < 10 ? ("0" + day) : (day));
		}
		return renewalDate;

	}

	public boolean isCardErrorMessageDisplayed(String text, String errorMsg) {

		WebElement element = makeXpath(ccerrorMessageXpath, text);
		return element.isDisplayed();
	}

	public boolean ccValdationErrorMsg(String errorMsg) {

		boolean errorValidation = getElementText(driver, ccErrorMsg).contains(errorMsg);
		return errorValidation;
	}

	public boolean cardNumberValdationErrorMsg(String errorMsg) {

		boolean errorValidation = getElementText(driver, cardNumber).contains(errorMsg);
		return errorValidation;
	}

	public UserPage enterInvalidCcData(String cclabelIDList[], String ccfieldDataList[], String selectValueMonth,
			String ccPathMonth, String selectValueYear, String ccPathYear) throws InterruptedException {
		for (int i = 0; i < cclabelIDList.length; i++) {
			addDataInFields(ccFieldXpath, cclabelIDList[i], ccfieldDataList[i]);
		}
		return this;
	}

	public UserPage enterCcData(String cclabelIDList[], String ccfieldDataList[], String selectValueMonth,
			String ccPathMonth, String selectValueYear, String ccPathYear) throws InterruptedException {
		for (int i = 0; i < cclabelIDList.length; i++) {
			addDataInFields(ccFieldXpath, cclabelIDList[i], ccfieldDataList[i]);
		}
		return this;
	}

	public UserPage selectCCMonth(String selectValue, String ccPath) throws InterruptedException

	{
		Select selectValues = new Select(makeXpath(ccFieldXpath, ccPath));
		selectValues.selectByValue(selectValue);
		return this;
	}

	public UserPage selectCcYear(String selectValue, String ccPath) throws InterruptedException

	{
		Select selectValues = new Select(makeXpath(ccFieldXpath, ccPath));
		selectValues.selectByValue(selectValue);
		return this;
	}

	public String sufix(int day) {

		if (day > 3 && day < 21)
			return "th";
		switch (day % 10) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

	public boolean confirmPurchaseTotalDue(String totalDue) {
		boolean totalDueConfpur = getElementText(driver, confirmPurchaseTotalDue).contains(totalDue);
		return totalDueConfpur;
	}

	public boolean summaryPurchaseTotalDue(String totalDue) {
		boolean totalDueConfpur = getElementText(driver, summaryPurchaseTotalDue).contains(totalDue);
		return totalDueConfpur;
	}

	public String getLicenseCountConfirmPurchase() throws InterruptedException

	{
		String selectedLicenceCount = getElementText(driver, confirmPurchaseCount);
		return selectedLicenceCount;
	}

	public String getLicenseCountBillingSummary() throws InterruptedException

	{
		String selectedLicenceCount = getElementText(driver, confirmBillingSummaryCount);
		return selectedLicenceCount;
	}

	public String getLicenseCount(int index) throws InterruptedException {
		WebElement drpDwnList = licenseCount;
		Select valSel = new Select(drpDwnList);
		List<WebElement> itemsInDropdown = valSel.getOptions();
		int size = itemsInDropdown.size();
		int randnMumber = ThreadLocalRandom.current().nextInt(index, size - 2);
		valSel.selectByIndex(randnMumber);
		WebElement option = valSel.getFirstSelectedOption();
		String selectedLicenceCount = getElementText(driver, option);
		return selectedLicenceCount;
	}

	public void confirmPurchase() throws InterruptedException {
		clickOnHiddenElement(fluentWait(confirmPurchase, driver), driver);
	}

	@FindBy(xpath = "//a[contains(.,'Add Users')]")
	WebElement addUser;

	@FindBy(id = "user_first_name")
	WebElement firstName;

	@FindBy(id = "user_last_name")
	WebElement lastName;

	@FindBy(id = "user_email")
	WebElement usereMail;

	@FindBy(xpath = "//button[contains(.,'Add')]")
	WebElement addButton;

	@FindBy(xpath = "(//label[@class='icheckbox '])[1]")
	WebElement selectCheckbox;

	@FindBy(xpath = "//button[@value='Save']")
	WebElement saveButton;

	@FindBy(xpath = "//h1[contains(.,'Users & Seats') or //h1[contains(.,'First Department')] or //h1[contains(.,'Billing')]]")
	WebElement userMsg;

	@FindBy(xpath = "//span[contains(.,'Purchase Seats')]")
	WebElement purchaseSeats;

	@FindBy(xpath = "//span[contains(.,'Add Users')]")
	WebElement addUsersButton;

	String emailFieldXpath = "//td[@class='login'][contains(.,'%s')]";

	@FindBy(xpath = "//input[@type='password']")
	WebElement enterPassword;

	@FindBy(xpath = "(//i[@class='fa fa-user-times'])[2]")
	WebElement disableUser;

	@FindBy(xpath = "//a[contains(.,'Remove Seat')]")
	WebElement removeSeats;

	@FindBy(xpath = "//a[contains(.,'Keep Seat')]")
	WebElement keepSeats;

	@FindBy(xpath = "//table/tbody/tr[2]/td[2]")
	WebElement geteMailOfDisableUser;

	@FindBy(id = "common_search_field")
	WebElement searchTextBox;

	@FindBy(xpath = "//i[@class='fa fa-search text-muted']")
	WebElement searchIcon;

	@FindBy(xpath = "//p[contains(.,'No User found')]")
	WebElement noUserFound;

	@FindBy(xpath = "//tbody/tr")
	List<WebElement> tableRows;

	/*
	 * Add users one by one in Users page Till user Count Not Equal to License Count
	 */

	public void addUsers(int count, ITestContext testContext, String buttonText,String pageHeader) throws InterruptedException {

		for (int i = 1; i < count; i++) {
			fluentWait(addUser, driver).click();
			String email = enterData(15);
			setPassword();
			scrollToEle(driver, addButton);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			fluentWait(addButton, driver).click();
			fluentWait(selectCheckbox, driver).click();
			saveButton();
			// Scroll to BOTTOM : **Method in Base Class Not working**
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			WebElement element = makeXpath(emailFieldXpath, email);
			boolean verifyemail = getElementText(driver, element).equals(email);
			reportLog(verifyemail, testContext.getName(), "Verify email added in user page text", "3." + i + "",
					" User with email : " + email + " is added in  "+pageHeader+"  page");

			// Scroll to TOP : **Method in Base Class Not working**
			js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
			if (i == count - 1) {
				boolean readbutonText = getElementText(driver, purchaseSeats).equals(buttonText);
				reportLog(readbutonText, testContext.getName(), "Verify button text", "4.0"," Button text Change from 'Add User' To  " + buttonText
								+ " and its verifed purchase seats are used");
			}
		}
	}

	public boolean verifyUserMessage(String message) {
		boolean headerText = getElementText(driver, userMsg).equals(message);
		return headerText;
	}

	public void addUserPerLicense(int count, ITestContext testContext, String fn, String ln, String email,
			String buttonText, String duplicateErrorMsg, String duplicateEmaild,String pageHeader) throws InterruptedException {

		if (count > 1) {
			fluentWait(addUser, driver).click();
			scrollToEle(driver, addButton);
			addButton.click();
			boolean firstName = isErrorMessageDisplayed(fn);
			reportLog(firstName, testContext.getName(), "Verify error message against first name", "2.0", fn
					+ " error message displayed verified when user keep field blank and click on add button in add user page");

			boolean lastName = isErrorMessageDisplayed(ln);
			reportLog(lastName, testContext.getName(), "Verify error message against last name", "2.1", ln
					+ " error message displayed verified when user keep field blank and click on add button in add user page");

			boolean emails = isErrorMessageDisplayed(email);
			reportLog(emails, testContext.getName(), "Verify error message against last name", "2.2", email
					+ " error message displayed verified when user keep field blank and click on add button in add user page");
			// Duplicate mail
			boolean duplicateMail = verifyDuplicateEmail(duplicateErrorMsg, duplicateEmaild);
			reportLog(duplicateMail, testContext.getName(), "Verify error message when user enter duplicate Mail id",
					"2.3", duplicateErrorMsg + " error message displayed when user enter duplicate Mail id");
			// clickOnCancelButton();
			clickOnUsersSeatsButton();
			addUsers(count, testContext, buttonText,pageHeader);
		} else if (count == 1) {
			boolean readbutonText = getElementText(driver, purchaseSeats).equals(buttonText);
			reportLog(readbutonText, testContext.getName(), "Verify button text", "2.0", buttonText
					+ " Button text is verfied in user page and its verifed that all users as per purchased license are added");
		}
	}

	public boolean countRow(int count) {
		List<WebElement> rows = tableRows;
		int rowCount = rows.size();
		return rowCount == count;
	}

	public void clickOnCancelButton() {
		fluentWait(cancelButton, driver).click();
	}

	public String getCountry() {
		return getFirstSeleOption(driver, countryValue);
	}

	public String getState() {
		return getFirstSeleOption(driver, stateValue);

	}

	/**
	 * 
	 * @param country
	 * @param state
	 * @throws InterruptedException
	 */
	public void countryStateTax(ITestContext testContext, String getCountry, String getState, String[] fieldData,
			String[] fieldlabel, String[] fieldxpathsummary, String[] stepsArry_summary, String renewDateYearly,
			int no_oflicense, String total_amount, String countstring, String sales_tax, float sales_tax_int,
			String total_due_summary, String[] fieldxpathpur, String[] fieldDatapur, String[] fieldlabelpur,
			String renewDateMonthlypur, String[] stepsArry_confirm, String step1, String step2, String step3,
			String step4, String step5, String step6, String step7, String step8, String step9, String step10)
			throws InterruptedException {

		if (getCountry.trim().equalsIgnoreCase("United States")) {

			/*
			 * -- This case will run for US country -- Billing Summary section
			 */
			verifyBillingSummary(testContext, fieldxpathsummary, fieldData, stepsArry_summary, fieldlabel);

			boolean renewalDateSummary = verifySummaryRenewalDate(renewDateYearly);
			reportLog(renewalDateSummary, testContext.getName(), "Verify renewal date ", step1,
					"Renewal Date " + (renewDateYearly) + " is verified in user page under Billing Summary section");

			boolean licensesCountCofirmSummary = getLicenseCountBillingSummary().contains(countstring);
			reportLog(licensesCountCofirmSummary, testContext.getName(), "Verify Number of llicense ", step2,
					"Licenses count: " + (no_oflicense) + " is verified in user page under Billing Summary section");

			boolean summaryAmount = summaryAmount(total_amount);
			reportLog(summaryAmount, testContext.getName(), "Verify Amount ", step3,
					"Amount " + "$" + (total_amount)
							+ " is verified in user page under Billing Summary section for counry " + getCountry
							+ " and State " + getState);

			boolean salesTaxBillingSummary = summarySalestax(sales_tax);
			reportLog(salesTaxBillingSummary, testContext.getName(), "Verify Sales Tax ", step4,
					"Sales Tax " + "$" + (sales_tax_int)
							+ " is verified in user page under Billing Summary section for counry " + getCountry
							+ " and State " + getState);

			boolean totalDuesummary = summaryPurchaseTotalDue(total_due_summary);
			reportLog(totalDuesummary, testContext.getName(), "Verify total due ", step5,
					"Total due " + "$" + (total_due_summary)
							+ " is verified in user page under summary section for counry " + getCountry + " and State "
							+ getState);

			/*
			 * -- This case will run for US country -- Confirm Purchase section
			 */
			verifyConfirmPurchase(testContext, fieldxpathpur, fieldDatapur, stepsArry_confirm, fieldlabelpur);

			boolean licensesCountConfirm = getLicenseCountConfirmPurchase().equals(countstring);
			reportLog(licensesCountConfirm, testContext.getName(), "Verify Number of llicense ", step6,
					"Licenses count: " + (no_oflicense) + " is verified in user page under Confirm Purchase section");

			boolean renewalDateConfirm = verifyRenewalDate(renewDateMonthlypur);
			reportLog(renewalDateConfirm, testContext.getName(), "Verify renewal date ", step7, "Renewal Date "
					+ (renewDateMonthlypur) + " is verified in user page under Confirm Purchase section");

			boolean purAmount = purchaseAmount(total_amount);
			reportLog(purAmount, testContext.getName(), "Verify Amount ", step8,
					"Amount " + "$" + (total_amount)
							+ " is verified in user page under Confirm Purchase section for counry " + getCountry
							+ " and State " + getState);

			boolean salesTaxPurchaseSummary = purchaseSalestax(sales_tax);
			reportLog(salesTaxPurchaseSummary, testContext.getName(), "Verify Sales Tax ", step9,
					"Sales Tax " + "$" + (sales_tax_int)
							+ " is verified in user page under Confirm Purchase section  for counry " + getCountry
							+ " and State " + getState);

			boolean totalDuePurchase = confirmPurchaseTotDue(total_due_summary);
			reportLog(totalDuePurchase, testContext.getName(), "Verify total due ", step10,
					"Total due " + "$" + (total_due_summary)
							+ " is verified in user page under Confirm Purchase section for counry " + getCountry
							+ " and State " + getState);
		} else {

			/*
			 * -- This case will run for Non-US country -- Billing Summary section
			 */
			verifyBillingSummary(testContext, fieldxpathsummary, fieldData, stepsArry_summary, fieldlabel);

			boolean renewalDateSummary = verifySummaryRenewalDate(renewDateYearly);
			reportLog(renewalDateSummary, testContext.getName(), "Verify renewal date ", step1,
					"Renewal Date " + (renewDateYearly) + " is verified in user page under Billing Summary section");

			boolean licensesCountCofirmSummary = getLicenseCountBillingSummary().equals(countstring);
			reportLog(licensesCountCofirmSummary, testContext.getName(), "Verify Number of llicense ", step2,
					"Licenses count: " + (no_oflicense) + " is verified in user page under Billing Summary section");

			boolean salesTaxDisplayOrNot = isSalesTaxDisplaySummary(); // Here I have to verify Sales tax line not
																		// display
			reportLog(!salesTaxDisplayOrNot, testContext.getName(), "Verify Sales Tax field ", step3,
					" Sales Tax " + "  not diplay is verified in user page under Billing Summary section for counry "
							+ getCountry + "");

			boolean totalDuesummary = summaryPurchaseTotalDue(total_due_summary);
			reportLog(totalDuesummary, testContext.getName(), "Verify total due ", step4,
					"Total due " + "$" + (total_due_summary)
							+ " is verified in user page under summary section for counry " + getCountry + "");

			/*
			 * --This case will run for Non-US country --Confirm Purchase Section
			 */

			verifyConfirmPurchase(testContext, fieldxpathpur, fieldDatapur, stepsArry_confirm, fieldlabelpur);

			boolean licensesCountConfirm = getLicenseCountConfirmPurchase().equals(countstring);
			reportLog(licensesCountConfirm, testContext.getName(), "Verify Number of llicense ", step6,
					"Licenses count: " + (no_oflicense) + " is verified in user page under Confirm Purchase section");

			boolean renewalDateConfirm = verifyRenewalDate(renewDateMonthlypur);
			reportLog(renewalDateConfirm, testContext.getName(), "Verify renewal date ", step7, "Renewal Date "
					+ (renewDateMonthlypur) + " is verified in user page under Confirm Purchase section");

			boolean salesTaxDisplayPurchase = isSalesTaxDisplayConfirmPurchase(); // Here I have to verify Salest tax
																					// line not display
			reportLog(!salesTaxDisplayPurchase, testContext.getName(), "Verify Sales Tax field ", step8,
					" Sales Tax " + "  not diplay is verified in user page under Confirm Purchase section for counry "
							+ getCountry + "");

			boolean totalDuePurchase = confirmPurchaseTotDue(total_due_summary);
			reportLog(totalDuePurchase, testContext.getName(), "Verify total due ", step9,
					"Total due " + "$" + (total_due_summary)
							+ " is verified in user page under Confirm Purchase section for counry " + getCountry + "");
		}
	}

	@FindBy(id = "billing_contact_country")
	WebElement countryValue;

	@FindBy(id = "select_us_state")
	WebElement stateValue;

	@FindBy(id = "billing_contact_shipping_country")
	WebElement shippingCountryValue;

	@FindBy(id = "select_shipping_us_state")
	WebElement shippingStateValue;

	@FindBy(xpath = "//h3[@class='sales-tax']")
	WebElement summarySalesTax;

	@FindBy(xpath = "//span[@class='font-bold sales-tax']")
	WebElement purchaseSalesTax;

	@FindBy(xpath = "//h3[@class='amount']")
	WebElement summaryAmount;

	@FindBy(xpath = "//span[@class='font-bold amount']")
	WebElement purchaseAmount;

	@FindBy(xpath = "//h3[@class='renewal-date']")
	WebElement summaryRenewalDate;

	@FindBy(xpath = "//span[contains(.,'Billing Contact')]")
	WebElement billingInfo;

	@FindBy(xpath = "//label[@class='iradio'][contains(.,'No')]")
	WebElement clickRadioButtonOn;

	// @FindBy(id = "input-creditCardHolderName")
	// WebElement cardHolderName;

	/**
	 * 
	 * @param country
	 * @param city
	 * @param state
	 * @param zip
	 */
	public void enterFieldDataUsStateTaxAndNonTax(String country, String city, String state, String zip) {
		Select countryDropDown = new Select(countryValue);
		countryDropDown.selectByVisibleText(country);
		enterTextField(By.id("billing_contact_city"), city);
		Select stateDropDown = new Select(stateValue);
		stateDropDown.selectByVisibleText(state);
		enterTextField(By.id("billing_contact_zipcode"), zip);
	}

	public String getSalesTax() {
		String salesTaxTotal = getElementText(driver, summarySalesTax);
		return salesTaxTotal;
	}

	public boolean summarySalestax(String salestax) {
		boolean salesTaxSummary = getElementText(driver, summarySalesTax).replace("$", "").equals(salestax);
		return salesTaxSummary;
	}

	public boolean purchaseSalestax(String salestax) {
		boolean salesTaxPur = getElementText(driver, purchaseSalesTax).replace("$", "").equals(salestax);
		return salesTaxPur;
	}

	public boolean summaryAmount(String amount) {
		boolean totalDueConfPur = getElementText(driver, summaryAmount).replace("$", "").equals(amount);
		return totalDueConfPur;
	}

	public boolean verifySummaryRenewalDate(String renewdate) throws InterruptedException {

		boolean renewalDates = getElementText(driver, summaryRenewalDate).contains(renewdate);
		return renewalDates;
	}

	public boolean purchaseAmount(String amount) {
		boolean purchaseAmt = getElementText(driver, purchaseAmount).replace("$", "").equals(amount);
		return purchaseAmt;
	}

	public boolean confirmPurchaseTotDue(String totaldue) {
		boolean totalDueConfPurcs = getElementText(driver, confirmPurchaseTotalDue).replace("$", "").equals(totaldue);
		return totalDueConfPurcs;
	}

	public void clickBillingInfo() {
		fluentWait(billingInfo, driver).click();
		customWait(2);
	}

	public void enterFieldDataNonUsStateNonTax(String country, String city, String zip) {
		Select countryDropDown = new Select(countryValue);
		countryDropDown.selectByVisibleText(country);
		customWait(2);
		enterTextField(By.id("billing_contact_city"), city);
		enterTextField(By.id("billing_contact_zipcode"), zip);
	}

	public boolean isSalesTaxDisplaySummary() {
		boolean salesTaxSummary = summarySalesTax.isDisplayed();
		return salesTaxSummary;
	}

	public boolean isSalesTaxDisplayConfirmPurchase() {
		boolean salesTaxSummary = purchaseSalesTax.isDisplayed();
		return salesTaxSummary;
	}

	/**
	 * Enter Shipping address when No Option is selected
	 * 
	 * @param country
	 * @param city
	 * @param state
	 * @param zip
	 * @param address
	 */
	public void enterFieldDataShippingNotSameBillingUsState(String country, String city, String state, String zip,
			String address) {
		Select countryDropdown = new Select(shippingCountryValue);
		countryDropdown.selectByVisibleText(country);
		customWait(2);
		enterTextField(By.id("billing_contact_shipping_city"), city);
		Select stateDropdown = new Select(shippingStateValue);
		stateDropdown.selectByVisibleText(state);
		enterTextField(By.id("billing_contact_shipping_zipcode"), zip);
		enterTextField(By.id("billing_contact_shipping_address_1"), address);
	}

	public void clickNoRadioOption() {
		fluentWait(clickRadioButtonOn, driver).click();
		// Scroll to bottom
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
		customWait(2);
	}

	public String getCountryForNoOption() {

		return getFirstSeleOption(driver, shippingCountryValue);
	}

	public String getStateForNoOption() {
		return getFirstSeleOption(driver, shippingStateValue);
	}

	/**
	 * 
	 * @param country
	 * @param city
	 * @param zip
	 */
	public void enterFieldDataNonUsStateNonTaxNoState(String country, String city, String zip) {
		Select countrydropdown = new Select(shippingCountryValue);
		countrydropdown.selectByVisibleText(country);
		customWait(2);
		enterTextField(By.id("billing_contact_shipping_city"), city);
		enterTextField(By.id("billing_contact_shipping_zipcode"), zip);
	}

	public void saveButton() {
		saveButton.click();
		waitForVisbility(driver, userMsg, 60);
	}

	public void setPassword() {
		selectCheckbox.click();
		enterPassword.sendKeys("canvas");
	}

	public String enterData(int length) {
		String value = UtilityFunctions.randomAlphaNumeric(length).toLowerCase();
		String email = value + "@yopmail.com";
		String FN = value + "Test1";
		String LN = value + "Test2";
		enterTextField(By.id("user_first_name"), FN);
		enterTextField(By.id("user_last_name"), LN);
		enterTextField(By.id("user_email"), email);
		return email;
	}

	public void disableUsers() {
		fluentWait(disableUser, driver).click();
		customWait(1);
		removeSeats.click();
	}

	public void keepSeat() {
		fluentWait(disableUser, driver).click();
		customWait(1);
		keepSeats.click();
	}

	public boolean countRowAfterDisable(int count) {
		List<WebElement> rows = tableRows;
		int rowCount = rows.size();
		return rowCount == count - 1;
	}

	public String buttenTextAfterUserDisable() throws InterruptedException {
		String buttonTextAfterDisable = getElementText(driver, purchaseSeats);
		return buttonTextAfterDisable;
	}

	public String buttenTextAfterClickinOnKeepSeat() throws InterruptedException {
		String buttonTextAfterDisable = getElementText(driver, addUser);
		return buttonTextAfterDisable;
	}

	public String getUserHaveToDisable() throws InterruptedException {
		String getMailUserDisable = getElementText(driver, geteMailOfDisableUser);
		return getMailUserDisable;
	}

	public String serachDisableUser(String mail) throws InterruptedException {
		waitForClickablility(driver, searchTextBox, 30);
		enterTextField(By.id("common_search_field"), mail);
		searchIcon.click();
		String userFoundOrNot = getElementText(driver, noUserFound);
		searchTextBox.clear();
		searchIcon.click();
		customWait(2);
		return userFoundOrNot;
	}

	public void clickNextBtnNumberOfLicenses() {
		fluentWait(nextButton.get(0), driver);
		clickEle(driver, nextButton.get(0));
		customWait(2); // Tried all combination not worked that why use custom Wait required
	}

	public void clickNextBtnBillFreq() {
		fluentWait(nextButton.get(1), driver);
		clickEle(driver, nextButton.get(1));
	}

	public void waitForBillingInfo() {
		waitForVisbility(driver, nextButton.get(2), 90);
		customWait(3); // Tried all combination not worked that why use custom Wait required
	}

	public void waitForBillingFreq() {
		waitForVisbility(driver, nextButton.get(1), 90);
	}

	public void clickNextBtnBillingInfo() {
		fluentWait(nextButton.get(2), driver);
		clickEle(driver, nextButton.get(2));
		customWait(2); // Tried all combination not worked that why use custom Wait required
	}

	public void clickNextBtnBillingContact() {
		fluentWait(nextButton.get(2), driver);
		clickEle(driver, nextButton.get(2));
		customWait(2); // Tried all combination not worked that why use custom Wait required
	}

	public void clickNextBtnBillingCreditCard() {
		fluentWait(nextButton.get(3), driver);
		clickEle(driver, nextButton.get(3));
		customWait(2); // Tried all combination not worked that why use custom Wait required
	}

	public void waitForInvisibilityOfProgress() {
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.invisibilityOf(progressBar));
		customWait(1);
	}

	public void clickNextBtnCreditCardInfo() {
		fluentWait(nextButton.get(3), driver);
		clickEle(driver, nextButton.get(3));
	}

	public void selectMonthlyRadioOption() throws InterruptedException {
		fluentWait(monthlyRadio.get(1), driver).click();
	}

	@FindBy(xpath = "//h3[@class='billing-cycle']")
	WebElement summaryBillingFrequency;

	@FindBy(xpath = "//h3[@class='selected-plan-type']")
	WebElement summaryPlan;

	@FindBy(xpath = "//span[contains(.,'Account')]")
	WebElement account;

	@FindBy(xpath = "(//a[contains(.,'Users & Seats')])[1]")
	WebElement usersSeat;

	@FindBy(xpath = "//a[contains(.,'Billing')]")
	WebElement licensesAndBilling;

	@FindBy(xpath = "//h1[contains(.,'Billing')]")
	WebElement licensesAndBillingheadertext;

	@FindBy(xpath = "//strong[contains(.,'Total Licenses:')]//..//..//div")
	WebElement totalLicenses;

	@FindBy(xpath = "//td[@class='text-right'][3]")
	WebElement totalMonthlyLicenses;

	@FindBy(xpath = "//strong[contains(.,'Annual Licenses:')]//..//..//div")
	WebElement totalYearlyLicenses;

	@FindBy(xpath = "//a[contains(.,'Users')]")
	WebElement clickUsersLink;

	@FindBy(xpath = "//div[contains(@class,'toast-message')]")
	WebElement duplicateMail;

	public String licensesAndBilling() {
		fluentWait(licensesAndBilling, driver).click();
		String licensesAndBillingHeadermsg = getElementText(driver, licensesAndBillingheadertext);
		return licensesAndBillingHeadermsg;
	}

	public boolean totalLicensePurchased(Integer totallicenses) {
		String licensesInString = totalLicenses.getText();
		String total_licenses_string_value = licensesInString
				.substring(licensesInString.indexOf("of") + 2, licensesInString.indexOf("available")).trim();
		Integer totalLicensesPurchesed = Integer.parseInt(total_licenses_string_value);
		return totalLicensesPurchesed.equals(totallicenses);
	}

	public void clickUsersLink() {
		fluentWait(clickUsersLink, driver).click();
		waitForVisbility(driver, By.xpath("//h1[contains(.,'Users')]"), 30);
	}

	public boolean totalLicensesYearlyPurchased(Integer totalyearly_licenses) {
		String licensesInString = totalYearlyLicenses.getText();
		String totalLicenseStringValue = licensesInString.substring(licensesInString.indexOf("Licenses:") + 10).trim();
		Integer totalLicensesYearlyPurchesed = Integer.parseInt(totalLicenseStringValue);
		return totalLicensesYearlyPurchesed.equals(totalyearly_licenses);
	}

	public boolean totaLicensesMonthlyPurchased(Integer totalmonthly_licenses) {
		String licensesInString = totalMonthlyLicenses.getText();
		Integer totalLicensesPurchesedMonthly = Integer.parseInt(licensesInString);
		return totalLicensesPurchesedMonthly.equals(totalmonthly_licenses);
	}

	public boolean verifyDuplicateEmail(String dupMsg, String duplicateMailId) {

		enterTextField(driver, firstName, "Peter");
		enterTextField(driver, lastName, "Josh");
		usereMail.sendKeys(duplicateMailId);
		scrollToEle(driver, addButton);
		fluentWait(addButton, driver).click();
		customWait(2); // Wait for toast message
		boolean duplicateMailMsg = duplicateMail.getText().contains(dupMsg);
		return duplicateMailMsg;
	}

	public void clickOnUsersSeatsButton() {
		fluentWait(usersSeat, driver).click();
	}
	// QA-5509

	@FindBy(xpath = "//span[contains(.,'Number of Seats')]")
	WebElement clickOnNumberOfSeats;

	public boolean summaryAmountBusinessAnnualRate(String amount) {
		boolean businessAnnualRate = getElementText(driver, summaryPurchaseTotalDue).replace("$", "").contains(amount);
		return businessAnnualRate;
	}

	public boolean purchaseAmounttBusinessAnnualRate(String amount) {
		boolean purchaseAnnualRate = getElementText(driver, confirmPurchaseTotalDue).replace("$", "").contains(amount);
		return purchaseAnnualRate;
	}

	public void clickOnNumberOfSeats() {
		// Scroll to bottom
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
		customWait(3); // Implicit wait not working here ,Custom Wait required because after scroll
						// have to wait
		fluentWait(clickOnNumberOfSeats, driver).click();
	}

	public void clickNextButtonsOnCheckOutPage() throws InterruptedException {
		clickOnNumberOfSeats();
		clickNextBtnNumberOfLicenses();
		selectMonthlyRadioOption();
		clickNextBtnBillFreq();
		waitForInvisibilityOfProgress();
		waitForBillingInfo();
		clickNextBtnBillingInfo();
		waitForInvisibilityOfProgress();
		clickNextBtnCreditCardInfo();
		waitForInvisibilityOfProgress();
	}

	public void clickNextButtonsAndFillDataOnCheckOutPage(String[] labelList11, String[] fieldDataList11)
			throws InterruptedException {
		clickNextBtnNumberOfLicenses();
		clickNextBtnBillFreq();
		waitForInvisibilityOfProgress();
		waitForBillingInfo();
		enterFieldData(labelList11, fieldDataList11);
		clickNextBtnBillingInfo();
		waitForInvisibilityOfProgress();
	}

	public void creditCardDetail(String[] ccLabelList, String[] ccFieldDataList, String month, String monthXpath,
			String year, String yearXpath) throws InterruptedException {
		switchToIframe(By.id("z_hppm_iframe"));
		enterCcData(ccLabelList, ccFieldDataList, month, monthXpath, year, yearXpath);
		selectCCMonth(month, monthXpath);
		selectCcYear(year, yearXpath);
		switchToDefault();
		clickNextBtnCreditCardInfo();
	}

	public String getLicenseCounts() throws InterruptedException

	{
		WebElement drpDwnList = licenseCount;
		Select valSel = new Select(drpDwnList);
		WebElement option = valSel.getFirstSelectedOption();
		String selectedLicenceCount = getElementText(driver, option);
		return selectedLicenceCount;
	}

	@FindBy(xpath = "//span[contains(.,'Account')]")
	WebElement clickOnAccount;
	@FindBy(xpath = "//a[contains(.,'Account Settings')]")
	WebElement accountSettingLink;
	@FindBy(xpath = "(//a[contains(.,'Settings')])[7]")
	WebElement accountAdminDate;
	@FindBy(xpath = "//select[contains(@id,'format')]")
	WebElement defaultDateFormat;
	@FindBy(xpath = "(//td[contains(@class,'count')])[1]")
	WebElement amountPaymentHistory;
	String userEmailId = "//tbody/tr['%s']/td[2]";

	public String getDateFormat() {
		accountSettingLink.click();
		accountAdminDate.click();
		String dateFormat = defaultDateFormat.getAttribute("value");
		return dateFormat;
	}

	public boolean getAmountPaymentHistoey(String amountString) {
		// Scroll to bottom
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
		customWait(2); // Custom Wait required because after scroll have to wait
		boolean businessAnnualRate = getElementText(driver, amountPaymentHistory).replace("$", "")
				.contains(amountString);
		return businessAnnualRate;
	}

	public String getParticularLicenseCount() throws InterruptedException {
		WebElement drpDwnList = licenseCount;
		Select valSel = new Select(drpDwnList);
		valSel.selectByIndex(4);
		WebElement option = valSel.getFirstSelectedOption();
		String selectedLicenceCount = getElementText(driver, option);
		return selectedLicenceCount;
	}

	/**
	 * Get all mail id's after adding users as per available seat
	 * 
	 * @param size
	 * @return
	 */
	public ArrayList<String> getUserEmails(int size) {
		ArrayList<String> emailId = new ArrayList<String>();
		for (int i = 1; i < size + 1; i++) {
			List<WebElement> emailID = driver.findElements(By.xpath(String.format(userEmailId, i)));
			for (WebElement mailID : emailID) {
				emailId.add(mailID.getText());
			}
		}
		return emailId;
	}

	@FindBy(xpath = "//button[@id='btn_Log In']")
	WebElement clickonlogin;

	@FindBy(xpath = "//input[@name='login']")
	WebElement userName;

	@FindBy(xpath = "//input[@type='password']")
	WebElement password;

	@FindBy(xpath = "//label//ins")
	WebElement checkBox;

	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement toastMessage;

	@FindBy(xpath = "//input[@id = 'terms_of_use']//parent::label")
	WebElement termsOfUseCheckbox;

	/**
	 * Method used to login with all disable users
	 * 
	 * @param strArray
	 * @param dupMsg
	 * @param testContext
	 * @param step
	 * @throws InterruptedException
	 */
	public void verifyDisableUserAbleToLogin(String[] strArray, String dupMsg, ITestContext testContext, int step)
			throws InterruptedException {
		for (int i = 0; i < strArray.length; i++) {
			fluentWait(userName, driver).click();
			userName.clear();
			userName.sendKeys(strArray[i]);
			clickOnHiddenElement(password, driver);
			password.clear();
			password.sendKeys("canvas");
			if (i > 0) {
				clickOnHiddenElement(fluentWait(clickonlogin, driver), driver);
				password.clear();
				password.sendKeys("canvas");
				clickOnHiddenElement(termsOfUseCheckbox, driver);
			}
			clickOnHiddenElement(fluentWait(clickonlogin, driver), driver);
			boolean userDisableMsg = toastMessage.getText().equals(dupMsg);
			reportLog(userDisableMsg, testContext.getName(), " Verify User not able to login ", "" + (step + i) + ".0",
					" Verify User " + (strArray[i]) + " Not able to login and Messeage " + dupMsg + " Is verified");
		}
	}

	// QA-5500

	@FindBy(xpath = "(//a[contains(.,'Edit')])[1]")
	WebElement editAddressFromBillingPage;

	@FindBy(xpath = "//div[contains(@id,'edit-billing-contact')]")
	WebElement editAddressFromCheckoutPage;

	@FindBy(xpath = "//a[contains(.,'Purchase Seats')]")
	WebElement purchaseSeatsBillingPage;

	@FindBy(xpath = "//h1[contains(.,'Purchase Additional Seats')]")
	WebElement purchaseAditionalSeat;

	@FindBy(xpath = "//span[contains(.,'Confirm')]")
	WebElement confirmButton;

	@FindBy(xpath = "//h1[contains(.,'Company')]")
	WebElement company;

	/**
	 * This method used to read address data (Billing /Shipping)
	 * 
	 * @param fieldID
	 * @return
	 */

	public ArrayList<String> readAddressData(String fieldID[]) {
		ArrayList<String> emailId = new ArrayList<String>();
		for (int i = 0; i < fieldID.length; i++) {
			if (i == 5 || i == 9) {
				List<WebElement> addressNew = driver
						.findElements(By.xpath(String.format(inputFieldXpathAddress, fieldID[i])));
				for (WebElement getAddress : addressNew) {
					emailId.add(getAddress.getAttribute("value"));
				}
			} else {
				List<WebElement> address = driver.findElements(By.xpath(String.format(inputFieldXpath, fieldID[i])));
				for (WebElement getAddress : address) {
					emailId.add(getAddress.getAttribute("value"));
				}
			}
		}
		return emailId;
	}

	public void billingLink() {
		fluentWait(licensesAndBilling, driver).click();
	}

	public void editAddressInBillingPage() {
		scrollToElementClick(driver, editAddressFromBillingPage);

	}

	public void verifyAddress(ArrayList<String> emailValuesCheckoutPage, ArrayList<String> emailValuesBillingPage,ITestContext testContext, int step, String msg1, String msg2, String msg3) {
		boolean emailAddress = emailValuesCheckoutPage.equals(emailValuesBillingPage);
		reportLog(emailAddress, testContext.getName(), " Verify address entered is same", "" + (step) + ".0", " " + msg3
				+ " Address in " + msg1 + " page is Matched with Address in " + msg2 + " page " + " Is verified");
		org.testng.Assert.assertTrue(emailAddress);
	}

	public void saveButtonBillingPage() {
		saveButton.click();
		waitForVisbility(driver, licensesAndBillingheadertext, 60);
	}

	public String checkoutPageRedirect() {
		fluentWait(purchaseSeatsBillingPage, driver).click();
		waitForVisbility(driver, purchaseAditionalSeat, 60);
		customWait(2);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String checkoutPage = getElementText(driver, purchaseAditionalSeat);
		return checkoutPage;
	}

	public void redirectCheckoutPageAndVerifyAddress() {
		clickNextBtnNumberOfLicenses();
		waitForInvisibilityOfProgress();
		editAddressFromCheckoutPage.click();
	}

	public String companyHeaderMsg() {
		String companyText = getElementText(driver, userMsg);
		return companyText;
	}

	public boolean confrimButton(String buttontxt) throws InterruptedException {
		confirmButton();
		waitForVisbility(driver, userMsg, 90);
		boolean buttonText = getElementText(driver, userMsg).equals(buttontxt);
		return buttonText;
	}

	public void confirmButton() {
		scrollToElementClick(driver, confirmButton);
	}

	@FindBy(xpath = "//span[contains(.,'Department')]")
	WebElement clickOnDepartment;

	@FindBy(xpath = "//h1[contains(.,'First Department')]")
	WebElement departmentHeaderMsg;

	public void clickOnDepartmentLink() {
		clickOnHiddenElement(fluentWait(clickOnDepartment, driver), driver);
	}
	
	public boolean verifyDepartmentMessage(String message) {
		boolean headerText = getElementText(driver, departmentHeaderMsg).equals(message);
		return headerText;
	}
}