package com.canvas.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BillingOptionsPage extends BasePage

{
	@FindBy(linkText = "All")
	WebElement allDepartment;

	@FindBy(xpath = "//label[contains(., 'Annual Rate')]/../div/div/p[contains(., 'No Discount.')]")
	List<WebElement> annualNoDiscountRows;
	@FindBy(linkText = "Billing Options")
	List<WebElement> billOptions;
	@FindBy(linkText = "Billing Options")
	WebElement billOpts;
	WebDriver driver;

	@FindBy(xpath = "//label[contains(., 'Annual Rate')]/..//label[contains(., 'Annual Rate')]/../div/a")
	WebElement editAnnual;

	@FindBy(xpath = "//label[contains(., 'Grandfather Settings')]/..//label[contains(., 'Grandfather Settings')]/../div/a")
	WebElement editGrandfather;

	@FindBy(xpath = "//label[contains(., 'Monthly Rate')]/..//label[contains(., 'Monthly Rate')]/../div/a")
	WebElement editMonthly;

	@FindBy(id = "advanced_user_search_email")
	WebElement emailSearch;
	@FindBy(xpath = "//a[@data-toggle = 'dropdown' and contains(.,'First Department')]")
	WebElement firstDepartment;

	@FindBy(linkText = "GoCanvas Only")
	WebElement goCanvasOnly;
	@FindBy(xpath = "//label[@class = 'icheckbox ']/input[@id = 'company_legacy_pricing']")
	WebElement legacyCkbox;
	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(linkText = "Manage")
	WebElement manageButton;

	@FindBy(xpath = "//label[contains(., 'Professional')]/../../div/label/input")
	List<WebElement> profButtons;
	@FindBy(xpath = "//label[contains(., 'Professional')]/../../div/label[contains(., 'Form Submission')]")
	WebElement profFormSub;

	@FindBy(xpath = "//label[contains(., 'Professional')]/../../div/label[@class != 'control-label m-b']")
	List<WebElement> profOpts;
	String rateXpath = "//input[contains(@value,'%s')]//following-sibling::ins";

	@FindBy(linkText = "Resume your own session")
	WebElement resumeSessionLink;

	//@FindBy(name = "commit")
	@FindBy(id = "btn_Save")
	WebElement saveButton;

	@FindBy(name = "commit")
	WebElement searchButton;

	@FindBy(xpath = "//span[text() = 'Search Users']")
	WebElement searchUsers;
	@FindBy(className = "toast-message")
	WebElement toast;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public BillingOptionsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public boolean allNoDiscount() {
		return annualNoDiscountRows.size() == 3;
	}

	public BillingOptionsPage billingOptsClick() throws InterruptedException {
		//Thread.sleep(2000);
		customWait(2); //putting back in waits due to multiple failures on post-conditions
		try {
			if (!goCanvasOnly.getAttribute("class").contains("active")) {
				(fluentWait(goCanvasOnly, driver)).click();
			}
		} catch (NoSuchElementException e) {
		}
		//Thread.sleep(10000);
		customWait(5); //putting back in waits due to multiple failures on post-conditions
		if (billOptions.size() < 1) {

			//fluentWait(firstDepartment, driver); 
			clickOnHiddenElement(fluentWait(firstDepartment, driver), driver);
			//fluentWait(allDepartment, driver);
			clickOnHiddenElement(fluentWait(allDepartment, driver), driver);
			(fluentWait(goCanvasOnly, driver)).click();
		}
		//fluentWait(billOpts, driver);

		clickOnHiddenElement(fluentWait(billOpts, driver), driver);

		return this;
	}

	public BillingOptionsPage checkLegacy(boolean checkstatus) {
		handleCheckBox(fluentWait(legacyCkbox, driver), checkstatus, driver);
		//clickOnHiddenElement(fluentWait(legacyCkbox, driver), driver);

		return this;
	}

	public BillingOptionsPage editAnnual() throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", editAnnual);
		// (editAnnual).click();
		Thread.sleep(3000);
		return this;
	}

	public BillingOptionsPage editGrandfather() {
		clickOnHiddenElement(fluentWait(editGrandfather, driver), driver);

		return this;
	}

	public BillingOptionsPage editMonthly() {
		(fluentWait(editMonthly, driver)).click();

		return this;
	}

	public String getToastMessage() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(toast));
		return toast.getText();
	}

	public BillingOptionsPage gotoUser(String email) throws InterruptedException {
		(searchUsers).click();
		(emailSearch).sendKeys(email);
		clickOnHiddenElement(searchButton, driver);
		// (searchButton).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", manageButton);
		(manageButton).click();
		customWait(2);
		return this;
	}

	public void logout() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("toast")));

		logout.click();
	}

	public void resumeSession() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("toast")));

		clickOnHiddenElement(fluentWait(resumeSessionLink, driver),driver);
	}

	public BillingOptionsPage saveClick() {
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript("arguments[0].click();", saveButton);
		clickOnHiddenElement(fluentWait(saveButton, driver), driver);
		// (saveButton).click();
		customWait(3);
		return this;
	}

	public BillingOptionsPage selectFormSub() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", profFormSub);
		(profFormSub).click();

		return this;
	}

	public BillingOptionsPage selectProfRate(String rate) {
		for (int i = 0; i < profOpts.size(); i++) {
			if (profOpts.get(i).getText().contains(rate)) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", profButtons.get(i));
			}
		}

		return this;
	}

	public BillingOptionsPage selectRate(String rate) {
		WebElement element = driver.findElement(By.xpath(String.format(rateXpath, rate)));
		clickOnHiddenElement(element, driver);
		return this;
	}

}
