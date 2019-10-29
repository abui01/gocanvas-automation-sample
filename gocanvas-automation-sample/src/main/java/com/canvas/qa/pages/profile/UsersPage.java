package com.canvas.qa.pages.profile;

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

import com.canvas.qa.pages.BasePage;

public class UsersPage extends BasePage

{

	@FindBy(xpath = "//ul[@id = 'side-menu']/li[contains(., 'Account')]")
	WebElement account;

	@FindBy(className = "annual_selected")
	WebElement annualOption;
	@FindBy(xpath = "//div[contains(@class, 'ui-multiselect-menu')]/div/ul/li/a")
	WebElement applyButton;

	@FindBy(xpath = "//p[contains(.,'Balance & Billing Cycle')]//parent::div//following-sibling::div[1]/div/div/div[2]")
	WebElement balance;
	@FindBy(xpath = "//p[contains(.,'Balance & Billing Cycle')]//parent::div//following-sibling::div[1]/div/div/div[3]")
	WebElement billingCycle;
	@FindBy(xpath = "//input[contains(@value,'Active')]//parent::label")
	WebElement btnActive;
	@FindBy(xpath = "//span[contains(.,'Apply')]")
	WebElement btnApply;
	@FindBy(xpath = "//input[contains(@value,'Filled')]//parent::label")
	WebElement btnFilled;
	@FindBy(xpath = "//input[contains(@value,'Inactive')]//parent::label")
	WebElement btnInactive;
	@FindBy(xpath = "//input[contains(@value,'Open')]//parent::label")
	WebElement btnOpen;

	@FindBy(xpath = "//h3[contains(., 'Startup')]/../div/a[contains(., 'Buy Now')]")
	WebElement buyStartup;

	@FindBy(linkText = "Cancel")
	WebElement cancelButton;

	@FindBy(xpath = "//table/tbody/tr/td[3]/span/a")
	List<WebElement> changePlanButtons;
	@FindBy(xpath = "//p[contains(.,'Balance & Billing Cycle')]//parent::div//following-sibling::div[1]/div/div/div[1]")
	WebElement companyPlan;
	@FindBy(xpath = "//span[@class = 'ui-icon ui-icon-triangle-1-s']")
	WebElement ddlFilters;
	@FindBy(className = "form_action_disable")
	List<WebElement> disableButtons;
	WebDriver driver;
	String emailXpath = "//td[contains(.,'%s')]";

	String emailXpathByRow = "//tr[%d]//td[2]";

	@FindBy(xpath = "//div[contains(@class, 'ui-multiselect-menu')]/ul/li/label/input")
	List<WebElement> filterCheckboxes;

	String filterOptionXpath = "//span[contains(.,'%s')]";
	@FindBy(xpath = "//div[contains(@class, 'ui-multiselect-menu')]/ul/li/label/span")
	List<WebElement> filters;

	@FindBy(xpath = "//button[@class = 'ui-multiselect ui-widget ui-state-default ui-corner-all']")
	WebElement filtersDDL;
	@FindBy(xpath = "//table/tbody/tr[1]/td[4]/a")
	WebElement firstRowRate;

	@FindBy(xpath = "//h1")
	WebElement header;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	String messageXpath = "//p[contains(.,'%s')]";

	@FindBy(xpath = "(//label[@class='iradio pull-left'])[2]/input")
	WebElement monthlyOption;

	@FindBy(xpath = "//td[@class = 'login']//a")
	WebElement name;

	String nameWithoutLinkXpath = "//td[@class = 'login']//div[text() = '%s']";

	String nameXpath = "//td[@class = 'login']//a[text() = '%s']";

	String nameXpath2 = "//td[contains(.,'%s')]//preceding-sibling::td[1]//a";

	String nameXpathByRow = "//tr[%d]//td[1]/a";

	@FindBy(className = "navbar-top-links")
	WebElement navBar;

	@FindBy(linkText = "Next")
	WebElement nextButton;

	@FindBy(xpath = "//div[@class = 'ibox-content clearfix' and contains(., 'Total Due:')]")
	WebElement paymentSummary;

	@FindBy(xpath = "//td[@class = 'serviceplan']//span")
	WebElement plan;

	@FindBy(xpath = "//table/tbody/tr/td[3]")
	List<WebElement> planCells;

	@FindBy(xpath = "//table/tbody/tr/td[3]/span")
	WebElement planName;

	@FindBy(xpath = "//table/tbody/tr/td[3]/span")
	List<WebElement> plans;

	@FindBy(xpath = "//button[@id='btn_Purchase']")
	WebElement purchaseButton;

	String roleXpath = "//div[contains(.,'%s')]";

	@FindBy(id = "common_search_field")
	WebElement searchBox;

	@FindBy(xpath = "//i[@class = 'fa fa-search text-muted']")
	WebElement searchIcon;

	@FindBy(id = "common_search_field")
	WebElement searchTextBox;

	@FindBy(className = "table-responsive")
	WebElement table;

	String textXpath = "//*[contains(text(),'%s')]";

	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement toast;

	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement toastMessage;

	@FindBy(xpath = "//div[@id = 'trial_banner']//button//span")
	WebElement trialBanner;

	@FindBy(xpath = "//table/tbody/tr/td[2]")
	List<WebElement> userEmails;

	@FindBy(xpath = "//table/tbody/tr/td[1]")
	List<WebElement> userNames;

	@FindBy(linkText = "Users")
	WebElement users;

	@FindBy(xpath = "//h1[contains(., 'Users')]")
	WebElement usersHeading;

	String xpathNextBillForUser = "//td[contains(.,'%s')]//following-sibling::td[@class = 'currentbillingcycle']";

	String xpathPlanForUser = "//td[contains(.,'%s')]//following-sibling::td[@class = 'serviceplan']//span";

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public UsersPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		javaScriptErrorChecker(driver);
	}

	public boolean allExpired() {
		for (WebElement p : planCells) {
			if (p.findElements(By.className("fa-exclamation-triangle")).isEmpty()) {
				return false;
			}
		}

		return true;
	}

	public boolean annualSelected() {
		return fluentWait(annualOption, driver).isSelected();
	}

	public UsersPage cancelClick() {
		fluentWait(cancelButton, driver).click();

		return this;
	}

	public UsersPage changeUserPlan(String user) throws InterruptedException {
		for (int i = 0; i < userEmails.size(); i++) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", userEmails.get(i));

			  //Thread.sleep(5000);
			  customWait(10); //waits for server to load "change plan" button

			if (userEmails.get(i).getText().equals(user)) {
				changePlanButtons.get(i).click();
				return this;
			}
		}

		return this;
	}

	public UsersPage clickActive() {
		clickOnHiddenElement(btnActive, driver);
		return this;
	}

	public UsersPage clickApply() {
		btnApply.click();
		customWait(2);
		return this;
	}

	public UsersPage clickFilled() {
		clickOnHiddenElement(btnFilled, driver);
		return this;
	}

	public UsersPage clickFilterDDL() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ddlFilters);
		clickOnHiddenElement(ddlFilters, driver);
		customWait(2);
		return this;
	}

	public UsersPage clickInactive() {
		clickOnHiddenElement(btnInactive, driver);
		return this;
	}

	public ProfilePage clickName(String name) {
		WebElement element = driver.findElement(By.xpath(String.format(nameXpath, name)));
		element.click();
		return new ProfilePage(driver);
	}

	public ProfilePage clickNameByEmail(String email) {
		WebElement element = driver.findElement(By.xpath(String.format(nameXpath2, email)));
		element.click();
		return new ProfilePage(driver);
	}

	public UsersPage clickOpen() {
		clickOnHiddenElement(btnOpen, driver);
		return this;
	}

	public UsersPage disableOpenUsers() {
		clickOnHiddenElement(filtersDDL, driver);
		// filtersDDL.click();
		for (int i = 0; i < filters.size(); i++) {
			if (filters.get(i).getText().contains("Filled") && filterCheckboxes.get(i).isSelected()) {
				clickOnHiddenElement(filterCheckboxes.get(i), driver);
			}
		}
		clickOnHiddenElement(applyButton, driver);
		// (applyButton).click();

		while (userEmails.size() > 0) {
			(disableButtons.get(0)).click();
			driver.switchTo().alert().accept();
		}

		return this;
	}

	public UsersPage disableUser(String user) {
		driver.manage().window().fullscreen();
		for (int i = 0; i < userEmails.size(); i++) {
			if (userEmails.get(i).getText().contains(user)) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView(true);", disableButtons.get(i));
				disableButtons.get(i).click();
				driver.switchTo().alert().accept();
				return this;
			}
		}

		return this;
	}

	public String getBalance() {
		return balance.getText();
	}

	public String getBillingCycle() {
		return billingCycle.getText();
	}

	public String getCompanyPlan() {
		return companyPlan.getText();
	}

	public String getEmailByRow(int rowIndex) {
		WebElement element = driver.findElement(By.xpath(String.format(emailXpathByRow, rowIndex)));
		return element.getText();
	}

	public String getHeader() {
		return (header).getText();
	}

	public String getMessage() {

		// WebDriverWait wait = new WebDriverWait(driver, 5);
		// wait.until(ExpectedConditions.visibilityOf(toast));
		String message = fluentWait(toast, driver).getText();
		return message;

	}

	public String getName() {
		return name.getText();
	}

	public String getNameByRow(int rowIndex) {
		WebElement element = driver.findElement(By.xpath(String.format(nameXpathByRow, rowIndex)));
		return element.getText();
	}

	public String getNavBar() {
		return (navBar).getText();
	}

	public String getNextBillForUser(String email) {
		WebElement element = driver.findElement(By.xpath(String.format(xpathNextBillForUser, email)));
		return element.getText();
	}

	public int getNumOpen() {
		clickOnHiddenElement(filtersDDL, driver);
		// filtersDDL.click();
		for (int i = 0; i < filters.size(); i++) {
			if (filters.get(i).getText().contains("Filled") && filterCheckboxes.get(i).isSelected()) {
				clickOnHiddenElement(filterCheckboxes.get(i), driver);
			}
		}
		// Thread.sleep(20000);
		clickOnHiddenElement(fluentWait(applyButton, driver), driver);
		// (applyButton).click();

		int i = 0;
		for (WebElement user : userEmails) {
			if (user.getText().equals("Fill Seat")) {
				i += 1;
			}
		}
		
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			while (!nextButton.getAttribute("href").contains("#")) {
				js.executeScript("arguments[0].scrollIntoView(true);", nextButton);
				(nextButton).click();

				for (WebElement user : userEmails) {
					if (user.getText().equals("Fill Seat")) {
						i += 1;
					}
				}
			}
		} catch (Exception e) {
		}

		return i;
	}

	public String getOpenRate() {
		clickOnHiddenElement(filtersDDL, driver);
		// filtersDDL.click();
		for (int i = 0; i < filters.size(); i++) {
			if (!filters.get(i).getText().contains("Open") && filterCheckboxes.get(i).isSelected()) {
				clickOnHiddenElement(filterCheckboxes.get(i), driver);
			}
		}

		// Thread.sleep(20000);
		clickOnHiddenElement(fluentWait(applyButton, driver), driver);

		// Thread.sleep(25000);
		customWait(10); // waits for results to load prior to getting text
		return (fluentWait(firstRowRate, driver)).getText();

	}

	public String getPlan() {
		return plan.getText();
	}

	public String getPlan(String user) {
		for (int i = 0; i < userEmails.size(); i++) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", userEmails.get(i));
			// Thread.sleep(5000);
			customWait(3);
			if (userEmails.get(i).getText().equals(user)) {
				return plans.get(i).getText();
			}
		}

		return "Failed";
	}

	public String getPlanForUser(String email) {
		WebElement element = driver.findElement(By.xpath(String.format(xpathPlanForUser, email)));
		return element.getText();
	}

	public String getPurchaseAmount() {
		return (paymentSummary.getText());
	}

	public String getToastMessage() {
		return toastMessage.getText();
	}

	public String getTrialBanner() {
		return (trialBanner).getText();
	}

	public int getUserCount() {
		return planCells.size();
	}

	public UsersPage goToUser(String user) {
		for (int i = 0; i < userEmails.size(); i++) {
			if (userEmails.get(i).getText().equals(user)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", userNames.get(i));
				// userNames.get(i).click();
				return this;
			}
		}

		return this;
	}

	public boolean isApplyButtonDisplayed() {
		return fluentWait(btnApply, driver).isDisplayed();
	}

	public boolean isEmailDisplayed(String email) {
		WebElement element = driver.findElement(By.xpath(String.format(emailXpath, email)));
		return element.isDisplayed();
	}

	public boolean isFilterOptionDisplayed(String option) {
		WebElement element = driver.findElement(By.xpath(String.format(filterOptionXpath, option)));
		return element.isDisplayed();
	}

	public boolean isMessageDisplayed(String message) {
		WebElement element = driver.findElement(By.xpath(String.format(messageXpath, message)));
		return element.isDisplayed();
	}

	public boolean isNameDisplayed(String name) {
		List<WebElement> element = driver.findElements(By.xpath(String.format(nameXpath, name)));
		return element.size() > 0;
	}

	public boolean isNameWithoutLinkDisplayed(String name) {
		WebElement element = driver.findElement(By.xpath(String.format(nameWithoutLinkXpath, name)));
		return element.isDisplayed();
	}

	public boolean isRoleDisplayed(String role) {
		WebElement element = driver.findElement(By.xpath(String.format(roleXpath, role)));
		return element.isDisplayed();
	}

	public boolean isTextPresent(String text) {
		WebElement element = driver.findElement(By.xpath(String.format(textXpath, text)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		// System.out.println(element.getText());
		return element.isDisplayed();
	}

	public boolean isUsersDisplayed() {
		return usersHeading.isDisplayed();
	}

	public void logout() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("toast")));
		clickOnHiddenElement(logout, driver);

	}

	public boolean monthlySelected() {
		return fluentWait(monthlyOption, driver).isSelected();
	}

	public UsersPage purchaseClick() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,0)");
		fluentWait(purchaseButton, driver).click();
		customWait(2);

		return this;
	}

	public UsersPage searchUser(String userEmail) {
		if (searchTextBox.getText() != null) {
			searchTextBox.clear();
		}
		searchTextBox.sendKeys(userEmail);
		searchIcon.click();
		customWait(10);
		return this;
	}

	public UsersPage selectAnnual() {
		customWait(3); // waits for page to load
		clickOnHiddenElement(fluentWait(annualOption, driver), driver);
		customWait(3); // waits for selection to be made
		return this;
	}

	public UsersPage selectMonthly() {
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		// js.executeScript("arguments[0].click();", monthlyOption);
		clickOnHiddenElement(fluentWait(monthlyOption, driver), driver);
		customWait(3); // waits for selection to be made
		return this;
	}

	public UsersPage selectStartup() {
		(buyStartup).click();

		return this;
	}

	public boolean userAppears(String email) {
		(searchBox).sendKeys(email);
		(searchBox).submit();
		boolean status = false;
		for (WebElement u : userEmails) {
			if (u.getText().equalsIgnoreCase(email)) {
				status = true;
				break;
			} else
				status = false;
			continue;
		}
		return status;
	}

	public UsersPage usersButtonClick() {
		// Thread.sleep(2000);
		try {
			if (!fluentWait(account, driver).getAttribute("class").contains("active")) {
				(account).click();
			}
		} catch (NoSuchElementException e) {
		}
		// JavascriptExecutor executor = (JavascriptExecutor) driver;
		// executor.executeScript("arguments[0].click();", fluentWait(users, driver));
		clickOnHiddenElement(fluentWait(users, driver), driver);
		// Thread.sleep(30000);
		return this;
	}

	public boolean verifyPlanName(String plan) {
		return planName.getText().contains(plan);
	}
}