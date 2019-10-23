package com.canvas.qa.pages.apps;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.DashboardPage;

/**
 * @author anna.marek
 *
 */
public class AppsPage extends BasePage

{

	@FindBy(xpath = "//table/tbody/tr/td/a[@class = 'dropdown-toggle']")
	List<WebElement> appQuickLinks;

	@FindBy(xpath = "//table/tbody/tr")
	List<WebElement> appRows;

	// strong[contains(.,'PDF Options')]/..//*[contains(text(),'Settings')]
	
	@FindBy(xpath = "//table/tbody/tr/td[2]/a") 
	List<WebElement> apps;
	

	// @FindBy(linkText = "Apps")
	@FindBy(xpath = "//span[text() = 'Apps']")
	WebElement appsButton;

	@FindBy(xpath = "//iframe[@data-test-id = 'ChatWidgetWindow-iframe']")
	WebElement chatBox;

	@FindBy(xpath = "//span[text() = 'Dashboard']")
	private WebElement dashboard;

	@FindBy(linkText = "Destroy App")
	WebElement destroyAppButton;
	@FindBy(xpath = "//div[@class = 'box_link']/div[contains(., 'Destroy App')]")
	WebElement destroyButton;
	
	//@FindBy(xpath = "//div[contains(.,'Department Share')]")
	@FindBy(linkText = "Department Share")
	WebElement departmentShareButton;

	@FindBy(xpath = "//*[contains(text(),'Destroy App')]")
	List<WebElement> disabledDestroyButton;
	
	@FindBy(id = "department_ids_")
	List<WebElement> departmentCheckboxes;

	WebDriver driver;

	@FindBy(id = "form_email_list")
	WebElement emailList;
	@FindBy(xpath = "//text()[contains(.,'Email Options:')]//parent::strong//following-sibling::div//span[3]/a")
	WebElement emailOptButton;

	@FindBy(xpath = "//strong[contains(.,'Email Options:')]/..//a[contains(.,'Settings')]")
	WebElement emailOptSetting;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(xpath = "//*[@__jx__id = '___$_37']")
	WebElement minimizeChat;

	// @FindBy(xpath = "//span[text() = 'Destroy App']")
	// WebElement disabledDestroyButton;

	@FindBy(linkText = "Profile")
	WebElement myProfile;

	// @FindBy(linkText = "PDF Options")
	@FindBy(xpath = "//strong[contains(.,'PDF Options')]/..//*[contains(text(),'Settings')]")
	WebElement pdfOptionsButton;

//	@FindBy(linkText = "Retire")
//	WebElement quickLinkRetire;
		
	@FindBy(xpath = "//a[@data-confirm='This will retire the app, are you sure?']")
	List<WebElement> quickLinkRetire;

	@FindBy(linkText = "Retire App")
	WebElement retireAppButton;

	@FindBy(linkText = "Retire App")
	WebElement retireAppLink;

	@FindBy(xpath = "//i[@class = 'fa fa-search text-muted']")
	WebElement searchIcon;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement searchIcon1;

	@FindBy(id = "common_search_field")
	List<WebElement> searchTextBox;

	@FindBy(xpath = "//input[contains(@name,'terms')]")
	List<WebElement> searchTextBox1;
	
	

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public AppsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		

		
		javaScriptErrorChecker(driver);
	}
	
	public AppsPage click2ndDepartmentCheckbox(boolean isCheck) {
		handleCheckBox(fluentWait(departmentCheckboxes.get(1), driver), isCheck, driver);
		return this;
	}

	public AppsPage appClick(String appName) throws NoSuchElementException, InterruptedException {
		searchApp(appName);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		boolean invisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("myModal")));
		for (WebElement app : apps) {
			if (app.getText().contains(appName)&& (invisible)) {
				app.click();
				break;
				
			}
			
		}
		return this;
		//throw new NoSuchElementException(appName + " does not appear for this user.");
	}

	public boolean appExists(String appName) throws InterruptedException {

		for (int i = 0; i < apps.size(); i++) {
			new WebDriverWait(driver, 30).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(apps)));
			if (fluentWait(apps.get(i), driver).getText().contains(appName)) {
				return true;
			}
		}
		

		return false;

	}

	/**
	 * clicks the Apps Button
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public AppsPage appsButtonClick() throws InterruptedException {
		//JavascriptExecutor executor = (JavascriptExecutor) driver;
		//executor.executeScript("arguments[0].click();", appsButton);
		clickOnHiddenElement(fluentWait(appsButton, driver), driver);
		return this;
	}

	public DashboardPage clickDashboard() {
		clickOnHiddenElement(dashboard, driver);
		return new DashboardPage(driver);
	}

	/**
	 * clicks the PDF Options Button
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public AppsPage clickPDFOptionsButton() throws InterruptedException {
		clickOnHiddenElement(pdfOptionsButton, driver);
		return this;
	}

	/**
	 * clicks the Retire App Button
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public AppsPage clickRetireAppButton() throws InterruptedException {
		fluentWait(retireAppButton, driver).click();
		return this;
	}

	public AppsPage clickRetireAppLink() throws InterruptedException {
		fluentWait(retireAppLink, driver).click();
		return this;
	}
	
	public AppsPage refresh() throws InterruptedException {
		driver.navigate().refresh();
		return this;
	}

	public AppsPage emailOptionsClick() {
		clickOnHiddenElement(emailOptSetting, driver);
		return this;
	}
	
	public AppsPage clickDepartmentShareButton() {
		fluentWait(departmentShareButton, driver).click();;
		return this;
	}

	public String getEmailOpt() {
		String email;
		try {
			email = fluentWait(emailList, driver).getText();
		} catch (Exception e) {
			e.printStackTrace();
			email = "Failed";
		}

		return email;
	}

	public String getLastModifiedDate(String appName) {
		for (WebElement appRow : appRows) {
			if (appRow.getText().contains(appName)) {
				return appRow.findElement(By.className("formlastedited")).getText();
			}
		}
		return null;
	}

	public void logout() throws InterruptedException {
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//JavascriptExecutor executor = (JavascriptExecutor) driver;
		//executor.executeScript("arguments[0].click();", logout);
		clickOnHiddenElement(fluentWait(logout, driver), driver);
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	public AppsPage moveMouseToDestroyButton() throws InterruptedException {

		moveMouseToElement(destroyAppButton, driver);
		customWait(2); // wait for modal to appear
		return this;
	}

	public AppsPage qlRetireApp(String appName) throws NoSuchElementException, InterruptedException {
		try {
			driver.switchTo().frame(fluentWait(chatBox, driver));
			(fluentWait(minimizeChat, driver)).click();
		} catch (Exception e) {
		}

		driver.switchTo().defaultContent();
		searchApp(appName);
		for (int i = 0; i < apps.size(); i++) {
			if (apps.get(i).getText().contains(appName)) {

				WebDriverWait wait = new WebDriverWait(driver, 30);
				wait.until(ExpectedConditions.refreshed(
				        ExpectedConditions.visibilityOfAllElements(apps)));
				//JavascriptExecutor executor = (JavascriptExecutor) driver;
				//executor.executeScript("arguments[0].click();", appQuickLinks.get(i));
				clickOnHiddenElement(fluentWait(appQuickLinks.get(i), driver), driver);

				//executor = (JavascriptExecutor) driver;
				//executor.executeScript("arguments[0].click();", quickLinkRetire.get(i));
				clickOnHiddenElement(fluentWait(quickLinkRetire.get(i), driver), driver);
				driver.switchTo().alert().accept();
				break;
				
				
			}
			
		}
		return this;
		//throw new NoSuchElementException(appName + " could not be found.");
	}
	

	

	public AppsPage searchApp(String appName) throws InterruptedException {

		if (searchTextBox.size() > 0) {
			searchTextBox.get(0).clear();
			searchTextBox.get(0).sendKeys(appName);
			// Thread.sleep(5000);
			searchIcon.click();
		}

		return this;
	}


	/**
	 * verifies if app has been successfully deleted by checking if name appears on
	 * App table
	 * 
	 * @param appname
	 * @return
	 * @throws InterruptedException
	 */
	public boolean verifyAppNoLongerExists(String appname) throws InterruptedException {

		WebElement appTable = driver.findElement(By.id("form_list_data"));
		String appTableText = appTable.getText();
		{
			if (appTableText.contains(appname))
				return false;
		}

		return true;
	}

	/**
	 * Verifies if the Destroy App button is inactive
	 * 
	 * @return
	 */
	public boolean verifyDestroyAppButtonInactive() throws InterruptedException {

		boolean appStatus = disabledDestroyButton.size() > 0;
		return appStatus;

	}

	/**
	 * Verifies if the Destroy App button is Active
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public boolean verifyDestroyAppButtonIsActive() throws InterruptedException {

		if (destroyAppButton.isDisplayed()) {
			return true;
		}

		return false;
	}

	public boolean verifyDestroyAppModalText(String string) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Verifies if the Destroy App button is Active
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public boolean verifyRetireAppButtonIsActive() throws InterruptedException {

		if (retireAppButton.isDisplayed()) {
			return true;
		}

		return false;
	}

	

}
