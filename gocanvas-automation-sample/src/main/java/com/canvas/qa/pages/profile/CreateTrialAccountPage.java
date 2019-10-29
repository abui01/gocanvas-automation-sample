package com.canvas.qa.pages.profile;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.canvas.qa.pages.BasePage;

public class CreateTrialAccountPage extends BasePage

{
	@FindBy(xpath = "//p[@class = 'step-label step-label-step2 active']")
	WebElement activeStep;

	@FindBy(xpath = "//p[@class = 'step-label step-label-step1 active']")
	WebElement activeStep1;

	@FindBy(id = "company_name")
	WebElement companyName;

	@FindBy(xpath = "//div[contains(@class,'step1-btn')]")
	WebElement continueWithEmail;

	@FindBy(id = "google-signup")
	WebElement continueWithGoogleButton;
	@FindBy(id = "linkedin-signup")
	WebElement linkedinSignUpButton;

	@FindBy(id = "google-signup")
	List<WebElement> continueWithGoogleButtonList;
	@FindBy(id = "linkedin-signup")
	List<WebElement> linkedinSignUpList;
	WebDriver driver;
	@FindBy(id = "company_size")
	WebElement employeeSize;
	@FindBy(xpath = "//select[@id = 'company_size' and datarequiredfield = 'true']")
	List<WebElement> employeeSizeList;
	String errorMessageXpath = "//span[text() = '%s']";
	@FindBy(css = "#errorExplanation>ul>li")
	WebElement failureMessage;
	@FindBy(xpath = "//h1")
	WebElement header;
	@FindBy(id = "industry_id")
	WebElement industryDDL;

	String inputFieldXpath = "//input[@id = '%s']";
	String labelXpath = "//label[@for = '%s']";

	String messageXpath = "//*[contains(.,'%s')]";

	@FindBy(xpath = "//div[text() = 'Sign in with Google']")
	WebElement signInWithGoogle;

	@FindBy(xpath = "//p[@class = 'step-label step-label-step1']")
	WebElement step1;

	@FindBy(css = "h3.onboarding-text")
	WebElement successfulSignup;

	@FindBy(linkText = "Try It Free")
	WebElement tryCanvasFree;

	@FindBy(id = "two-step-submit-btn")
	WebElement tryItFree;

	@FindBy(xpath = ".//*[@id='user_email']")
	WebElement userCompanyEmail;

	@FindBy(xpath = ".//*[@id='user_first_name']")
	WebElement userFirstName;

	@FindBy(xpath = ".//*[@id='user_last_name']")
	WebElement userLastName;

	@FindBy(id = "password_input")
	WebElement userPassword;

	@FindBy(id = "user_phone")
	WebElement userPhone;

	@FindBy(id = "user_phone")
	List<WebElement> userPhoneList;

	@FindBy(css = ".signup-box-2-step h1")
	WebElement verifySignUpText1;

	@FindBy(css = ".signup-box-2-step p")
	WebElement verifySignUpText2;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public CreateTrialAccountPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public CreateTrialAccountPage clickContinueWithGoogleButton() {
		continueWithGoogleButton.click();
		return this;
	}

	public CreateTrialAccountPage clickStep1() {
		step1.click();
		return this;
	}

	/**
	 * @return This method is used to implement "ContinueWithEmail" button click
	 *         on Sign-Up Page (Step-1).
	 */
	public CreateTrialAccountPage continueWithEmailClick() {
		(continueWithEmail).click();
		return this;
	}

	public CreateTrialAccountPage enterFieldData(String fieldID, String data) {
		WebElement element = driver.findElement(By.xpath(String.format(inputFieldXpath, fieldID)));
		element.sendKeys(data);
		return this;
	}

	public String getActiveStep() {
		return activeStep.getText();
	}

	public String getActiveStep1() {
		return activeStep1.getText();
	}

	public String getHeader() {
		return (header).getText();
	}

	public String getLabelText(String label) {
		WebElement element = driver.findElement(By.xpath(String.format(labelXpath, label)));
		return element.getText();
	}

	public String getTryItFreeButtonText() {
		return tryItFree.getText();
	}

	public boolean isContinueWithGoogleButtonDisplayed() {
		return continueWithGoogleButtonList.size() > 0;
	}

	public boolean isElementDisplayed(String text) {
		WebElement element = driver.findElement(By.xpath(String.format(messageXpath, text)));
		return element.isDisplayed();
	}

	public boolean isEmployeeSizeDisplayed() {
		return employeeSizeList.size() > 0;
	}

	public boolean isErrorMessageDisplayed(String text) {
		WebElement element = driver.findElement(By.xpath(String.format(errorMessageXpath, text)));
		return element.isDisplayed();
	}

	public boolean isPhoneNumberDisplayed() {
		return userPhoneList.size() > 0;
	}

	public boolean isSignInWithGoogleDisplayed() {
		return signInWithGoogle.isDisplayed();
	}

	public CreateTrialAccountPage selectEmployeeSize(int index) {
		Select dropdown = new Select(employeeSize);
		dropdown.selectByIndex(index);
		return this;
	}

	public CreateTrialAccountPage selectIndustry(String industry) {
		Select dropdown = new Select(industryDDL);
		dropdown.selectByVisibleText(industry);
		return this;
	}

	/**
	 * @param userCompanyEmail
	 * @return
	 * @throws IOException
	 *             This method is to send argument value UserCompanyEmail to
	 *             WebPage place holders.
	 */
	public CreateTrialAccountPage step1CompanyEmail(String userCompanyEmail) throws IOException {
		(this.userCompanyEmail).sendKeys(userCompanyEmail);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='2px groove red'", this.userCompanyEmail);
		return this;

	}

	/**
	 * @param userFirstName
	 * @return
	 * @throws IOException
	 *             This method is to send argument value UserFirstName to
	 *             WebPage place holders.
	 */
	public CreateTrialAccountPage step1FirstName(String userFirstName) throws IOException {
		(this.userFirstName).sendKeys(userFirstName);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='2px groove red'", this.userFirstName);
		return this;

	}

	/**
	 * @param userLastName
	 * @return
	 * @throws IOException
	 *             This method is to send argument value UserLastName to WebPage
	 *             place holders.
	 */
	public CreateTrialAccountPage step1LastName(String userLastName) throws IOException {
		(this.userLastName).sendKeys(userLastName);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='2px groove red'", this.userLastName);
		return this;

	}

	/**
	 * @param companyName
	 * @return
	 * @throws IOException
	 *             This method is to send argument value Company Name to WebPage
	 *             place holders.
	 */
	public CreateTrialAccountPage step2CompanyName(String companyName) throws IOException {
		(this.companyName).sendKeys(companyName);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='2px groove red'", this.companyName);
		return this;

	}

	/**
	 * @param userPassword
	 * @return
	 * @throws IOException
	 *             This method is to send argument value UserPassword to WebPage
	 *             place holders.
	 */
	public CreateTrialAccountPage step2Password(String userPassword) throws IOException {
		(this.userPassword).sendKeys(userPassword);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='2px groove red'", this.userPassword);
		return this;

	}

	/**
	 * @param userPhone
	 * @return
	 * @throws IOException
	 *             This method is to send argument value UserPhone to WebPage
	 *             place holders.
	 */
	public CreateTrialAccountPage step2Phone(String userPhone) throws IOException {
		(this.userPhone).sendKeys(userPhone);
		return this;
	}

	/**
	 * Method for Click action on Try Canvas Free Button on First Page.
	 */
	public CreateTrialAccountPage tryItFreeButtonClick() {
		(tryCanvasFree).click();
		return this;
	}

	/**
	 * @return This method is used to implement "Try It Free" button click on
	 *         Sign-Up Page (Step-1)
	 */
	public CreateTrialAccountPage tryItFreeClick() {
		(tryItFree).click();
		return this;
	}

	public OnboardingPage tryItFreeStep2Click() {
		(tryItFree).click();
		return new OnboardingPage(driver);
	}

	public CreateTrialAccountPage tryItFreeStep2ClickWithInvalidData() {
		(tryItFree).click();
		return this;
	}
	
	public boolean isLinkedinSignUpButtonDisplayed() {
		return linkedinSignUpList.size() > 0;
	}
	
	public CreateTrialAccountPage clickLinkedinSignUpButton() {
		linkedinSignUpButton.click();
		return this;
		
		
		
	}
}
