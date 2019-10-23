package com.canvas.qa.pages;

import java.util.List;

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

import com.canvas.qa.pages.profile.UsersPage;

public class SearchUsersPage extends BasePage {

	@FindBy(id = "advanced_user_search_company_name")
	WebElement companyName;

	String companyXpath = "//td[contains(.,'%s')]//preceding-sibling::td[1]//div[1]/a";

	String createdXpath = "//td[contains(.,'%s')]//following-sibling::td[1]";

	@FindBy(xpath = "//a[text() = 'Disable']")
	private WebElement disable;

	@FindBy(xpath = "//a[text() = 'Disable Departments']")
	private WebElement disableDepartment;

	@FindBy(xpath = "//a[text() = 'Disable Departments']")
	private List<WebElement> disableDepartmentList;

	WebDriver driver;

	String emailXpath = "//table[@class = 'table table-hover table-striped']//tbody/tr[%d]//td[3]";

	@FindBy(xpath = "//a[text() = 'Enable']")
	private WebElement enable;

	@FindBy(xpath = "//a[text() = 'Enable Departments']")
	private WebElement enableDepartment;

	@FindBy(xpath = "//a[text() = 'Enable Departments']")
	private List<WebElement> enableDepartmentList;

	@FindBy(xpath = "//a[text() = 'Enable Dispatch']")
	private WebElement enableDispatch;

	@FindBy(xpath = "//a[text() = 'Enable Dispatch']")
	private List<WebElement> enableDispatchList;

	@FindBy(xpath = "//a[text() = 'Enable']")
	private List<WebElement> enableList;

	@FindBy(xpath = "//a[text() = ' Features']")
	private WebElement features;

	@FindBy(id = "advanced_user_search_first_name")
	WebElement firstName;

	String lastLoginXpath = "//td[contains(.,'%s')]//following-sibling::td[2]";

	@FindBy(linkText = "Log Out")
	WebElement logout;

	String manageXpath = "//td[contains(.,'%s')]//following-sibling::td[3]//a[2]/span[contains(.,'Manage')]";

	String messageXpath = "//*[contains(.,'%s')]";

	String moreXpath = "//td[contains(.,'%s')]//following-sibling::td[3]//a[1]";

	String nameXpath = "//td[contains(.,'%s')]//preceding-sibling::td[2]//div[1]/a";

	String optionFieldXpath = "//li//a[text() = '%s']";

	@FindBy(xpath = "//a[text() = 'Resume your own session']")
	private WebElement resumeYourSession;

	@FindBy(id = "advanced_user_search_role_id")
	WebElement roleDDL;

	String roleXpath = "//table[@class = 'table table-hover table-striped']//tbody/tr[%d]//td[1]/div[2]/span[2]";

	@FindBy(xpath = "//table[@class = 'table table-hover table-striped']//tbody/tr")
	private List<WebElement> rowList;

	@FindBy(xpath = "//input[@value = 'Search']")
	private WebElement searchButton;

	@FindBy(id = "advanced_user_search_email")
	WebElement searchEmail;

	String searchUserLabelXpath = "//label[text() = '%s']";

	String tableFieldXpath = "//a[contains(text(),'%s')]";

	String tableFieldXpath2 = "//th[contains(text(),'%s')]";

	@FindBy(xpath = "//h1[@title = 'Search Users']")
	private WebElement title;

	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement toastMessage;

	String userXpath = "//div[@class = 'table-responsive']//td[contains(text(),'%s')]";

	public SearchUsersPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}
	public SearchUsersPage clickDisable() {
		disable.click();
		driver.switchTo().alert().accept();
		customWait(5);
		return this;
	}

	public SearchUsersPage clickDisableDepartment() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", disableDepartment);
		disableDepartment.click();
		customWait(2);
		return this;
	}

	public UsersPage clickEnable() {
		enable.click();
		customWait(10);
		return new UsersPage(driver);
	}

	public SearchUsersPage clickEnableDepartment() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", enableDepartment);
		enableDepartment.click();
		customWait(2);
		return this;
	}

	public SearchUsersPage clickEnableDispatch() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", enableDispatch);
		enableDispatch.click();
		customWait(2);
		return this;
	}

	public SearchUsersPage clickFeatures() {
		clickOnHiddenElement(features, driver);
		customWait(2);
		return this;
	}

	public DashboardPage clickOnManage(String email) {
		WebElement element = driver.findElement(By.xpath(String.format(manageXpath, email.toLowerCase())));
		clickOnHiddenElement(element, driver);
		return new DashboardPage(driver);
	}

	public SearchUsersPage clickOnMore(String email) {
		WebElement element = driver.findElement(By.xpath(String.format(moreXpath, email)));
		//WebElement element = makeXpath(moreXpath, email); <-- this method is currently broken
		clickOnHiddenElement(fluentWait(element,driver),driver); 
		//customWait(4);
		
		return this;
	}

	public SearchUsersPage clickOption(String option) {
		WebElement element = driver.findElement(By.xpath(String.format(optionFieldXpath, option)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		clickOnHiddenElement(element, driver);
		customWait(2);
		return this;
	}

	public SearchUsersPage clickResumeYourSession() {
		resumeYourSession.click();
		return this;
	}

	public SearchUsersPage clickSearch() {
		clickOnHiddenElement(searchButton, driver);
		customWait(10);
		return this;
	}

	public SearchUsersPage enterCompanyName(String text) {
		companyName.sendKeys(text);
		return this;
	}

	public SearchUsersPage enterEmail(String email) {
		searchEmail.sendKeys(email);
		return this;
	}
	
	public SearchUsersPage enterFirstName(String text) {
		firstName.sendKeys(text);
		return this;
	}

	public String getCompanyName(String email) {
		WebElement element = driver.findElement(By.xpath(String.format(companyXpath, email)));
		return element.getText();
	}

	public String getCreatedAt(String email) {
		WebElement element = driver.findElement(By.xpath(String.format(createdXpath, email)));
		return element.getText();
	}

	public String getEmailForRow(int rowIndex) {
		WebElement element = driver.findElement(By.xpath(String.format(emailXpath, rowIndex)));
		return element.getText();
	}

	public String getLastLogin(String email) {
		WebElement element = driver.findElement(By.xpath(String.format(lastLoginXpath, email)));
		return element.getText();
	}

	public String getName(String email) {
		WebElement element = driver.findElement(By.xpath(String.format(nameXpath, email)));
		return element.getText();
	}

	public String getRoleForRow(int rowIndex) {
		WebElement element = driver.findElement(By.xpath(String.format(roleXpath, rowIndex)));
		return element.getText();
	}

	public int getRowCount() {
		return rowList.size();
	}

	public String getToastMessage() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(toastMessage));
		return toastMessage.getText();
	}

	public boolean isDisableDepartmentDisplayed() {
		return disableDepartmentList.size() > 0;
	}

	public boolean isEmailDisplayed(String email) {
		boolean flag = false;
		for (int i = 1; i < getRowCount(); i++) {
			WebElement element = driver.findElement(By.xpath(String.format(emailXpath, i)));
			if (element.getText().contains(email)) {
				flag = true;
				break;
			} else {
				flag = false;
				continue;
			}
		}
		return flag;
	}

	public boolean isEnableDepartmentDisplayed() {
		return enableDepartmentList.size() > 0;
	}

	public boolean isEnableDispatchDisplayed() {
		return enableDispatchList.size() > 0;
	}

	public boolean isEnableDisplayed() {
		return enableList.size() > 0;
	}

	public boolean isLabelDisplayed(String label) {
		WebElement element = driver.findElement(By.xpath(String.format(searchUserLabelXpath, label)));
		return element.isDisplayed();
	}

	public boolean isManageDisplayed(String email) {
		WebElement element = driver.findElement(By.xpath(String.format(manageXpath, email)));
		return element.isDisplayed();
	}

	public boolean isMessageDisplayed(String message) {
		WebElement element = driver.findElement(By.xpath(String.format(messageXpath, message)));
		return element.isDisplayed();
	}

	public boolean isMoreDisplayed(String email) {
		WebElement element = driver.findElement(By.xpath(String.format(moreXpath, email)));
		return element.isDisplayed();
	}

	public boolean isOptionFieldDisplayed(String field) {
		WebElement element = driver.findElement(By.xpath(String.format(optionFieldXpath, field)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		return element.isDisplayed();
	}

	public boolean isOptionInstanceDisplayed(String field) {
		List<WebElement> element = driver.findElements(By.xpath(String.format(optionFieldXpath, field)));
		return element.size() > 0;
	}

	public boolean isRoleDisplayed(String role) {
		boolean flag = false;
		for (int i = 1; i < getRowCount(); i++) {
			WebElement element = driver.findElement(By.xpath(String.format(roleXpath, i)));
			if (element.getText().contains(role)) {
				flag = true;
				break;
			} else {
				flag = false;
				continue;
			}
		}
		return flag;
	}

	public boolean isSearchButtonDisplayed() {
		return searchButton.isDisplayed();
	}

	public boolean isSearchUsersPageDisplayed() {
		return title.isDisplayed();
	}

	public boolean isTabelField2Displayed(String field) {
		WebElement element = driver.findElement(By.xpath(String.format(tableFieldXpath2, field)));
		return element.isDisplayed();
	}

	public boolean isTabelFieldDisplayed(String field) {
		WebElement element = driver.findElement(By.xpath(String.format(tableFieldXpath, field)));
		return element.isDisplayed();
	}

	public boolean isUserDisplayed(String email) {
		List<WebElement> element = driver.findElements(By.xpath(String.format(userXpath, email)));
		return element.size() > 0;
	}

	public void logout() throws InterruptedException {
		clickOnHiddenElement(logout, driver);
		customWait(2);
	}

	public SearchUsersPage selectRole(String role) {
		Select dropdown = new Select(roleDDL);
		dropdown.selectByVisibleText(role);
		return this;
	}
	public SearchUsersPage clickActivate(String option) {
		//WebElement element = driver.findElement(By.xpath(String.format(optionFieldXpath, option)));
		WebElement element = makeXpath(optionFieldXpath, option);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		clickOnHiddenElement(fluentWait(element,driver),driver); 
		//clickOnHiddenElement(element, driver);
		driver.switchTo().alert().accept();
		customWait(2);
		return this;
	}
	
	
	
	
	
}
