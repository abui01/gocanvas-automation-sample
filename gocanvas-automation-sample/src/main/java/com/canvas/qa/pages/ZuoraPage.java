package com.canvas.qa.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.canvas.qa.pages.profile.OnboardingPage;

public class ZuoraPage extends BasePage

{
	@FindBy(linkText = "Account")
	WebElement account;

	@FindBy(linkText = "View All Apps")
	WebElement allAppsButton;

	String applicationStoreDDLXpath = "//a[text() = '%s']";

	@FindBy(xpath = "//div[@id = 'search_results']/div/div/div/div/div/h5")
	List<WebElement> appLinks;

	@FindBy(xpath = "//div[@id = 'app--results']//div[@class = 'app-cards']//div[@class = 'app-card']")
	List<WebElement> appList;

	@FindBy(xpath = "//button[text() = 'Apply']")
	WebElement applyButton;

	String appNameByIndexXpath = "//div[@id = 'app--results']//div[@class = 'app-cards']//div[%d]//a//p";

	String appNameXpath = "//p[text() = '%s']";

	@FindBy(xpath = "//button[@class = 'app-header__submit']/i")
	WebElement appSearchButton;

	@FindBy(xpath = "//input[@class = 'app-header__search app-search-field']")
	WebElement appSearchTextBox;

	@FindBy(linkText = "Application Store")
	WebElement appStoreDDL;

	String countryCheckBoxXpath = "//*[@id= 'modalCountry']//text()[contains(.,'%s')]//preceding-sibling::ins";

	@FindBy(xpath = "//button[@data-modal = '#modalCountry']//i")
	WebElement countryFilter;
	@FindBy(xpath = "//div[@id = 'myModal']//select[@id = 'dept']")
	WebElement departmentDDL;
	@FindBy(xpath = "//div[@id = 'myModal']//select[@id = 'dept']")
	List<WebElement> departmentDDLList;

	WebDriver driver;

	@FindBy(id = "company_size")
	WebElement employeeSize;

	@FindBy(xpath = "//select[@id = 'company_size' and datarequiredfield = 'true']")
	List<WebElement> employeeSizeList;

	@FindBy(xpath = "//a[contains(.,'Get App')]")
	WebElement getAppButton;

	@FindBy(xpath = "//div[@id = 'search_results']/div/div/div/div/div/a")
	List<WebElement> getAppButtons;

	String getAppXpath = "//a[text() = '%s']//parent::h5//preceding-sibling::a[text() = 'Get App']";

	@FindBy(id = "two-step-submit-btn")
	WebElement getStartedButton;

	String inputFieldXpath = "//input[@id = '%s']";

	String labelXpath = "//label[@for = '%s']";

	@FindBy(linkText = "Log Out")
	WebElement logout;

	String pageHeadingXpath = "//h1[contains(text(),'%s')]";

	String paginationXpath = "//ul[@class = 'pagination']//li//a[contains(text(),'%s')]";

	@FindBy(xpath = "//a[text() = 'Request A Demo']")
	WebElement requestADemoButton;

	@FindBy(xpath = "//div[@id = 'myModal']//input[@type = 'submit']")
	WebElement saveButton;

	@FindBy(id = "sort")
	WebElement sortByDDL;

	@FindBy(xpath = "//button[@id = 'cat']//i")
	WebElement subCategoryButton;

	String subCategoryXpath = "//a[contains(text(),'%s')]";

	@FindBy(xpath = "//form[@action = '/users/create']//button[@type = 'submit']")
	WebElement tryItFreeButton;

	@FindBy(id = "user_phone")
	WebElement userPhone;

	@FindBy(id = "user_phone")
	List<WebElement> userPhoneList;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public ZuoraPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		//mixedContentChecker(driver);

		javaScriptErrorChecker(driver);
	}

	public ZuoraPage allAppsButtonClick() throws InterruptedException {
		// Thread.sleep(2000);
		fluentWait(appStoreDDL, driver).click();
		fluentWait(allAppsButton, driver).click();
		return this;
	}

	public ZuoraPage clickApp(String appName) {
		WebElement element = driver.findElement(By.xpath(String.format(appNameXpath, appName)));
		clickOnHiddenElement(element, driver);
		return this;
	}

	public ZuoraPage clickApplyButton() {
		clickOnHiddenElement(applyButton, driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(requestADemoButton));
		return this;
	}

	public ZuoraPage clickAppSearchButton() {
		clickOnHiddenElement(appSearchButton, driver);
		return this;
	}

	public ZuoraPage clickCountryCheckbox(String country) {
		WebElement element = driver.findElement(By.xpath(String.format(countryCheckBoxXpath, country)));
		clickOnHiddenElement(element, driver);
		return this;
	}

	public ZuoraPage clickCountryFilter() {
		clickOnHiddenElement(countryFilter, driver);
		return this;
	}

	public ZuoraPage clickDDLOption(String option) {
		WebElement element = driver.findElement(By.xpath(String.format(applicationStoreDDLXpath, option)));
		element.click();
		return this;
	}

	public CreateAppPage clickGetApp(String appName) {
		WebElement element = driver.findElement(By.xpath(String.format(appNameXpath, appName)));
		clickOnHiddenElement(fluentWait(element, driver), driver);
		clickOnHiddenElement(fluentWait(getAppButton, driver), driver);
		return new CreateAppPage(driver);
	}

	public ZuoraPage clickGetAppWithDepartment(String appName) {
		WebElement element = driver.findElement(By.xpath(String.format(appNameXpath, appName)));
		clickOnHiddenElement(fluentWait(element, driver), driver);
		getAppButton.click();
		return this;
	}

	public OnboardingPage clickGetStartedButton() {
		fluentWait(getStartedButton, driver).click();
		return new OnboardingPage(driver);
	}

	public ZuoraPage clickPaginationNumber(String pageNumber) {
		WebElement element = driver.findElement(By.xpath(String.format(paginationXpath, pageNumber)));
		fluentWait(element, driver).click();
		return this;
	}

	public ZuoraPage clickSaveButton() {
		fluentWait(saveButton, driver).click();
		return this;
	}

	public ZuoraPage clickSubCategory(String subCategory) {
		WebElement element = driver.findElement(By.xpath(String.format(subCategoryXpath, subCategory)));
		fluentWait(element, driver).click();
		return this;
	}

	public ZuoraPage clickSubCategoryButton() {
		clickOnHiddenElement(fluentWait(subCategoryButton, driver), driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(requestADemoButton));
		return this;
	}

	public ZuoraPage clickTryItFreeButton() {
		clickOnHiddenElement(fluentWait(tryItFreeButton, driver), driver);
		return this;
	}

	public ZuoraPage clickViewAllApps() throws InterruptedException {
		fluentWait(allAppsButton, driver).click();
		return this;
	}

	public ZuoraPage enterAppToSearch(String appName) {
		fluentWait(appSearchTextBox, driver).sendKeys(appName);
		return this;
	}

	public ZuoraPage enterFieldData(String fieldID, String data) {
		WebElement element = driver.findElement(By.xpath(String.format(inputFieldXpath, fieldID)));
		fluentWait(element, driver).sendKeys(data);
		return this;
	}

	public ZuoraPage getApp(String appName) throws NoSuchElementException {
		for (int i = 0; i < appLinks.size(); i++) {
			if (appLinks.get(i).getText().equals(appName)) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", getAppButtons.get(i));
				// getAppButtons.get(i).click();
				return this;
			}
		}

		throw new NoSuchElementException(appName + " not found on Application Store.");
	}

	public int getAppCount() {
		return appList.size();
	}

	public String getAppNameByIndex(int index) {
		WebElement element = driver.findElement(By.xpath(String.format(appNameByIndexXpath, index)));
		return element.getText();
	}

	public ArrayList<String> getDepartmentList() {
		Select dropdown = new Select(departmentDDL);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}

	public String getLabelText(String label) {
		WebElement element = driver.findElement(By.xpath(String.format(labelXpath, label)));
		return element.getText();
	}

	public ArrayList<String> getSortByList() {
		Select dropdown = new Select(sortByDDL);
		ArrayList<String> sortList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			sortList.add(element.getText());
		}
		return sortList;
	}

	public boolean isAppDetailPageDisplayed(String text) {
		WebElement element = driver.findElement(By.xpath(String.format(pageHeadingXpath, text)));
		return element.isDisplayed();
	}

	public boolean isDepartmentDDLDisplayed() {
		return departmentDDLList.size() > 0;
	}

	public boolean isEmployeeSizeDisplayed() {
		return employeeSizeList.size() > 0;
	}

	public boolean isFieldDisplayed(String label) {
		WebElement element = driver.findElement(By.xpath(String.format(inputFieldXpath, label)));
		return element.isDisplayed();
	}

	public boolean isPhoneNumberDisplayed() {
		return userPhoneList.size() > 0;
	}

	public boolean isRequestADemoButtonDisplayed() {
		return requestADemoButton.isDisplayed();
	}

	public void logout() throws InterruptedException {
		fluentWait(logout, driver).click();
	}

	public ZuoraPage selectDepartment(String department) {
		Select dropdown = new Select(departmentDDL);
		dropdown.selectByVisibleText(department);
		return this;
	}

	public ZuoraPage selectEmployeeSize(int index) {
		Select dropdown = new Select(employeeSize);
		dropdown.selectByIndex(index);
		return this;
	}

	public ZuoraPage selectSortOption(String option) {
		Select dropdown = new Select(sortByDDL);
		dropdown.selectByVisibleText(option);
		return this;
	}

	public ZuoraPage step2Phone(String phone) throws IOException {
		fluentWait(userPhone, driver).sendKeys(phone);
		return this;
	}
}
