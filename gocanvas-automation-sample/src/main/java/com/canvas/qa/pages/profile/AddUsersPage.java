package com.canvas.qa.pages.profile;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

/**
 * @author anna.marek
 *
 */
public class AddUsersPage extends BasePage

{
	@FindBy(xpath = "//ul[@id = 'side-menu']/li[contains(., 'Account')]")
	WebElement account;

	@FindBy(id = "btn_Add")
	WebElement addButton;
	@FindBy(linkText = "Add Users")
	WebElement addUsers;
	@FindBy(xpath = "//label[contains(., 'Annual')]")
	WebElement annualAmount;

	@FindBy(className = "annual_selected")
	WebElement annualOption;

	@FindBy(linkText = "Cancel")
	WebElement cancelButton;
	@FindBy(id = "company_role")
	WebElement companyRole;
	WebDriver driver;

	@FindBy(id = "user_email")
	WebElement email;

	@FindBy(id = "user_email")
	WebElement emailField;
	public String errorMessageXpath = "//span[@class = 'error_field' and text() = '%s']";

	@FindBy(id = "user_first_name")
	WebElement firstName;
	@FindBy(id = "user_first_name")
	WebElement firstNameField;

	@FindBy(xpath = "//h1")
	WebElement header;
	@FindBy(id = "user_last_name")
	WebElement lastName;
	@FindBy(id = "user_last_name")
	WebElement lastNameField;

	@FindBy(linkText = "Log Out")
	WebElement logout;
	@FindBy(xpath = "//label[contains(., 'Monthly')]")
	WebElement monthlyAmount;
	@FindBy(className = "monthly_selected")
	WebElement monthlyOption;
	@FindBy(id = "user_password")
	WebElement password;
	@FindBy(xpath = "//div[@class = 'ibox-content clearfix' and contains(., 'Total Due:')]")
	WebElement paymentSummary;
	@FindBy(linkText = "privacy policy")
	WebElement policy;
	@FindBy(id = "btn_Purchase")
	WebElement purchaseButton;

	@FindBy(id = "btn_Save")
	WebElement saveButton;
//	@FindBy(id = "send_instructions")
//	WebElement sendInstructionsCheckbox;

	@FindBy(id = "send_email_for_password")
	WebElement setPasswordCheckbox;
	@FindBy(linkText = "terms of use")
	WebElement terms;

	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement toast;

	@FindBy(xpath = "//input[@id = 'new_user_count']")
	WebElement userCount;

	@FindBy(id = "ddl_new_user_count")
	WebElement userCountDDL;

	@FindBy(linkText = "Users")
	WebElement users;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public AddUsersPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public AddUsersPage addButtonClick() {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", addButton);
		// (addButton).click();

		return this;
	}

	public AddUsersPage addUsersButtonClick() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", addUsers);
		// (addUsers).click();

		return this;
	}

	public boolean annualSelected() {
		return annualOption.isSelected();
	}

	public boolean areAddOptionsChecked() {
		//return sendInstructionsCheckbox.isSelected() && setPasswordCheckbox.isSelected();
		return setPasswordCheckbox.isSelected();
	}

	public AddUsersPage cancelClick() {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", cancelButton);
		// cancelButton.click();
		return this;
	}

	public AddUsersPage fillFields(String fName, String lName, String email) {
		(firstNameField).sendKeys(fName);
		(lastNameField).sendKeys(lName);
		(emailField).sendKeys(email);

		return this;
	}

	public String getAnnualAmount() {

		//return (annualAmount.getText());
		return fluentWait(annualAmount, driver).getText();
	}

	public String getEmail() {
		try {
			return email.getText();
		} catch (Exception e) {
			return "Failed";
		}
	}

	public String getFirstName() {
		try {
			return firstName.getText();
		} catch (Exception e) {
			return "Failed";
		}
	}

	public String getHeader() {
		return (header).getText();
	}

	public String getLastName() {
		try {
			return lastName.getText();
		} catch (Exception e) {
			return "Failed";
		}
	}

	public String getMessage() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(toast));
		return toast.getText();
	}

	public String getMonthlyAmount() {

		return (monthlyAmount.getText());
	}

	public String getNumUsersDDLValue() {
		Select userDDL = new Select(userCountDDL);
		try {
			return userDDL.getFirstSelectedOption().getText();
		} catch (Exception e) {
			return "Failed";
		}
	}

	public String getPurchaseAmount() {
		return (paymentSummary.getText());
	}

	public String getRole() {
		Select compRole = new Select(companyRole);

		return compRole.getFirstSelectedOption().getText();
	}

	public boolean isErrorMessageDisplayed(String text) {
		WebElement element = driver.findElement(By.xpath(String.format(errorMessageXpath, text)));
		return element.isDisplayed();
	}

	public void logout() throws InterruptedException {
		Thread.sleep(5000);
		logout.click();
	}

	public boolean monthlySelected() {
		return monthlyOption.isSelected();
	}

	public AddUsersPage purchaseClick() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// js.executeScript("window.scrollTo(0,0)");
		js.executeScript("arguments[0].scrollIntoView(true);", purchaseButton);
		js.executeScript("arguments[0].click();", purchaseButton);
		// purchaseButton.click();

		return this;
	}

	public AddUsersPage saveButtonClick() {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", saveButton);
		// (saveButton).click();

		return this;
	}

	public AddUsersPage selectAnnual() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", annualOption);

		return this;
	}

	public AddUsersPage selectMonthly() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", monthlyOption);

		return this;
	}

	public AddUsersPage setEmail(String userEmail) {
		email.clear();
		email.sendKeys(userEmail);

		return this;
	}

	public AddUsersPage setFirstName(String fName) {
		firstName.sendKeys(fName);

		return this;
	}

	public AddUsersPage setLastName(String lName) {
		lastName.sendKeys(lName);

		return this;
	}

	public AddUsersPage setNumUsers(String num) {
		Select usersDDL = new Select(userCountDDL);
		usersDDL.selectByVisibleText(num);

		return this;
	}

	public AddUsersPage setNumUsersText(String num) {
		userCount.clear();
		userCount.sendKeys(num);

		return this;
	}

	public AddUsersPage setPassword(String pass) {
		password.clear();
		password.sendKeys(pass);

		return this;
	}

	public AddUsersPage setRole(String cRole) {
		Select compRole = new Select(companyRole);
		compRole.selectByVisibleText(cRole);

		return this;
	}

	public AddUsersPage uncheckAddOptions() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

//		if (sendInstructionsCheckbox.isSelected())
//			js.executeScript("arguments[0].click();", sendInstructionsCheckbox);

		if (setPasswordCheckbox.isSelected())
			js.executeScript("arguments[0].click();", setPasswordCheckbox);

		return this;
	}

	public AddUsersPage usersButtonClick() throws InterruptedException {
		try {
			if (!account.getAttribute("class").contains("active")) {
				(account).click();
			}
		} catch (NoSuchElementException e) {
		}
		Thread.sleep(15000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", users);
		// (users).click();

		return this;
	}
}
