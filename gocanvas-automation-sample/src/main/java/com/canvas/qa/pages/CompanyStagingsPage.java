package com.canvas.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.canvas.qa.pages.profile.OnboardingPage;

public class CompanyStagingsPage extends BasePage {

	@FindBy(id = "company_staging_account_id")
	WebElement accountID;

	@FindBy(xpath = "//button[text() = 'Activate Account']")
	WebElement activateButton;

	public String activationStatusXpath = "//td[contains(text(),'%s')]//following-sibling::td[4]";

	@FindBy(xpath = "//span[text() = 'Company Name']//parent::td//following-sibling::td[1]//span")
	WebElement companyName;

	WebDriver driver;

	@FindBy(id = "company_staging_email")
	WebElement email;

	@FindBy(id = "company_staging_email")
	WebElement emailID;

	@FindBy(id = "company_staging_first_name")
	WebElement firstName;

	@FindBy(id = "company_staging_last_name")
	WebElement lastName;

	@FindBy(id = "company_staging_license_count")
	WebElement licenseCount;

	@FindBy(xpath = "//span[text() = 'License Count']//parent::td")
	WebElement licenseCountNumber;

	public String menuOptionXpath = "//a[text() = '%s']";

	@FindBy(id = "password_input")
	WebElement password;

	@FindBy(id = "company_staging_phone_number")
	WebElement phoneNumber;

	@FindBy(id = "btn_Save")
	WebElement saveButton;
	
	@FindBy(id = "company_staging_service_level")
	WebElement serviceDDL;

	@FindBy(id = "company_staging_sku")
	WebElement skuDDL;

	@FindBy(id = "company_staging_vendor_id")
	WebElement vendorDDL;

	public String vendorXpath = "//td[contains(text(),'%s')]//following-sibling::td[3]";

	public CompanyStagingsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public OnboardingPage clickActivateButton() {
		activateButton.click();
		customWait(10);
		return new OnboardingPage(driver);
	}

	public CompanyStagingsPage clickMenuOption(String menu) {
		WebElement element = driver.findElement(By.xpath(String.format(menuOptionXpath, menu)));
		element.click();
		return this;
	}

	public CompanyStagingsPage clickSaveButton() {
		//saveButton.click();
		clickOnHiddenElement(saveButton, driver);
		customWait(10);
		return this;
	}

	public CompanyStagingsPage enterAccountID(String text) {
		accountID.sendKeys(text);
		return this;
	}

	public CompanyStagingsPage enterEmail(String text) {
		email.sendKeys(text);
		return this;
	}

	public CompanyStagingsPage enterEmailID(String text) {
		emailID.clear();
		emailID.sendKeys(text);
		return this;
	}

	public CompanyStagingsPage enterFirstName(String text) {
		firstName.sendKeys(text);
		return this;
	}

	public CompanyStagingsPage enterLastName(String text) {
		lastName.sendKeys(text);
		return this;
	}

	public CompanyStagingsPage enterLicenseCount(String text) {
		licenseCount.sendKeys(text);
		return this;
	}

	public CompanyStagingsPage enterPassword(String text) {
		password.sendKeys(text);
		return this;
	}

	public CompanyStagingsPage enterPhoneNumber(String text) {
		phoneNumber.sendKeys(text);
		return this;
	}

	public boolean isActivationStatusDisplayed(String email, String text) {
		WebElement element = driver.findElement(By.xpath(String.format(activationStatusXpath, email.toLowerCase())));
		return element.getText().contains(text);
	}

	public boolean isCompanyNameDisplayed(String text) {
		return companyName.getText().contains(text);
	}

	public boolean isLicenseCountDisplayed(String text) {
		return licenseCountNumber.getText().contains(text);
	}

	public boolean isVendorDisplayed(String email, String text) {
		WebElement element = driver.findElement(By.xpath(String.format(vendorXpath, email.toLowerCase())));
		return element.getText().contains(text);
	}

	public CompanyStagingsPage selectService(String text) {
		Select dropdown = new Select(serviceDDL);
		dropdown.selectByVisibleText(text);
		return this;
	}

	public CompanyStagingsPage selectSKU(String text) {
		Select dropdown = new Select(skuDDL);
		dropdown.selectByVisibleText(text);
		return this;
	}

	public CompanyStagingsPage selectVendor(String vendor) {
		Select dropdown = new Select(vendorDDL);
		dropdown.selectByVisibleText(vendor);
		return this;
	}
}
