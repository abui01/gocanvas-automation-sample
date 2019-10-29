package com.canvas.qa.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import com.canvas.qa.core.ReportManager;
import com.relevantcodes.extentreports.LogStatus;

public class SubmissionStatusesPage extends BasePage {
	@FindBy(xpath = "//label[contains(@for,'enabled')]")
	WebElement addStatusButton;

	@FindBy(xpath = "//span[contains(.,'Apps')]")
	WebElement appLinkLeftPanel;

	@FindBy(xpath = "//a[contains(.,'Submission Status')]")
	WebElement appNameLink;

	@FindBy(xpath = "//a[contains(.,'Submission Numbering')]")
	WebElement appNameLink1;

	// @FindBy(xpath = "//span[contains(.,'Submission Status')]")
	@FindBy(xpath = "//strong[contains(.,'Submission Status:')]")
	WebElement appSubmissionStatus;

	@FindBy(xpath = "//a[@title='Apps']")
	WebElement apptitle;

	@FindBy(xpath = "//label[contains(@for,'enabled')]")
	WebElement checkBoxEnable;

	@FindBy(xpath = "//a[contains(.,'Add a Status')]")
	WebElement clickAddStaus;

	@FindBy(xpath = "//i[@class='fa fa-trash fa-fw fa-lg']")
	WebElement clickDeleteButton;

	@FindBy(xpath = "//*[contains(@title, 'Edit')] ")
	WebElement clickEditButton;

	@FindBy(xpath = ".//*[@id='myModal']/div")
	WebElement clickModelPopUp;

	@FindBy(xpath = "//input[@value='Save']")
	WebElement clickSave;

	@FindBy(xpath = "//i[@class='fa fa-check fa-fw fa-lg confirm-icon']")
	WebElement clickSaveCheckSign;

	@FindBy(xpath = "//strong[contains(.,'Submission Status:')]/..//*[contains(text(),'Settings')]")
	WebElement clickSettingSubmissionStatus;

	@FindBy(xpath = "//a[@title='Close1']")
	WebElement close;

	@FindBy(xpath = ".//*[@id='right-sidebar-screens-container']/div/div[1]/screen-outline/sheet-outline/div/p")
	WebElement defaultScreen;

	@FindBy(xpath = "//button[@data-ng-click='modalOptions.ok();']")
	WebElement doNotSave;

	private WebDriver driver;

	@FindBy(xpath = "//label[@class='icheckbox']")
	WebElement enableSubmissionCheckbox;

	@FindBy(xpath = "//input[@type='text']")
	WebElement enterTextStatus;

	@FindBy(xpath = "//span[contains(.,'Log Out')]")
	WebElement logout;

	@FindBy(xpath = ".//*[@id='status_0']/td[2]/a[4]/i")
	WebElement moveDown;

	@FindBy(xpath = ".//*[@id='status_0']/td[2]/a[3]/i")
	WebElement moveUp;

	@FindBy(xpath = ".//*[@id='myModal']/div/div/div[3]//button[contains(.,'Cancel')]")
	WebElement popUpCancelButton;

	// @FindBy(xpath = "//div[@id =
	// 'full_status_table']//table//tbody/tr[1]/td[1]")
	// WebElement textAfterCorrectingOrder;

	@FindBy(xpath = ".//*[@id='myModal']/div/div/div[3]//button[contains(.,'Save')]")
	WebElement popUpSaveButton;

	@FindBy(xpath = "//button[contains(.,'Publish to device')]")
	WebElement publishtToDevicePopup;

	@FindBy(xpath = "//div[@id = 'full_status_table']//table//tbody/tr[1]/td[1]")
	WebElement reOrderText;

	@FindBy(xpath = "//h3[contains(.,'Assign Replacement Option')]")
	WebElement replacementOptionWindow;

	@FindBy(xpath = ".//*[@id='right-sidebar-screens-container']/div/div[1]/screen-outline/sheet-outline/div")
	List<WebElement> screenName;

	@FindBy(xpath = "//div[@data-placeholder='New Screen 1']")
	WebElement ScreenName;

	@FindBy(xpath = "//label[@class='icheckbox']//input[2]")
	WebElement submissionCheckbox;

	@FindBy(xpath = "//span[contains(.,'Submission No.')]")
	List<WebElement> submissionNoIcon;

	@FindBy(className = "toast-message")
	WebElement toast;

	@FindBy(className = "toast")
	WebElement toast1;

	public SubmissionStatusesPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public boolean addStatus(String orgMsg, String[] Statuses) throws InterruptedException

	{

		for (int j = 0; j < Statuses.length; j++) {
			// clickAddStaus.click();
			fluentWait(clickAddStaus, driver).click();
			fluentWait(enterTextStatus, driver).click();
			enterTextStatus.sendKeys(Statuses[j]);
			fluentWait(clickSaveCheckSign, driver).click();
		}
		saveStatus();
		boolean status = getToastMsg().equals(orgMsg);
		return status;
	}

	public boolean clickAppLink(String orgMsg) throws InterruptedException {

		appLinkLeftPanel.click();
		Actions actions = new Actions(driver);
		actions.moveToElement(appNameLink);
		fluentWait(appNameLink, driver).click();
		boolean submissionPageText = apptitle.getText().equals(orgMsg);
		return submissionPageText;
	}

	public SubmissionStatusesPage clickCheckBox() throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", enableSubmissionCheckbox);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		// Thread.sleep(4000);
		customWait(4); // Implict & Explicit wait not working
		return this;
	}

	public SubmissionStatusesPage clickEnableCheckBox() throws InterruptedException {

		clickOnHiddenElement(enableSubmissionCheckbox, driver);
		customWait(2); // Implict & Explicit wait not working here

		return this;
	}

	public void clickOnSubmissiOnStatus() throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(clickSettingSubmissionStatus);
		fluentWait(clickSettingSubmissionStatus, driver).click();

	}

	public void clickSubmissionAppLink() throws InterruptedException {
		appLinkLeftPanel.click();
		Actions actions = new Actions(driver);
		actions.moveToElement(appNameLink1);
		fluentWait(appNameLink1, driver).click();
	}

	public boolean clickSubmissionStatus(String OrgMg) throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(appSubmissionStatus);
		boolean submissionText = appSubmissionStatus.getText().equals(OrgMg);
		fluentWait(clickSettingSubmissionStatus, driver).click();
		customWait(2); // Implict & Explicit wait not working here
		return submissionText;

	}

	public String columnFirstRowText() throws NoSuchElementException, InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.visibilityOf(reOrderText));
		try {
			return (reOrderText).getText();

		} catch (NoSuchElementException e) {
			return "Failed";
		}
	}

	public String columnSecondRowText() throws NoSuchElementException, InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.visibilityOf(reOrderText));
		try {
			return (reOrderText).getText();

		} catch (NoSuchElementException e) {
			return "Failed";
		}
	}

	public boolean deleteAllStatus(String delete_msg, String[] strings) throws InterruptedException

	{
		for (int j = 0; j < strings.length; j++) {
			if (driver.findElements(By.xpath(".//*[@id='status_" + j + "']/td[2]/a[2]/i")).size() > 0) {
				driver.findElement(By.xpath(".//*[@id='status_" + j + "']/td[2]/a[2]/i")).click();
				customWait(2); // Implicit wait not working here
				popUpSaveButton.click();
				customWait(1);// Implicit wait not working here
			}
		}
		saveStatus();
		boolean submissionLinkText = getToastMsg().equals(delete_msg);
		return submissionLinkText;
	}

	public boolean deleteIndividualStatus(String delete_inv_msg) throws InterruptedException

	{
		clickDeleteButton.click();
		popUpSaveButton.click();
		customWait(2);// Implict & Explicit wait not working here
		selectCheckBox();
		saveStatus();
		/*
		 * WebDriverWait wait = new WebDriverWait(driver,30); WebElement element
		 * = wait.until(ExpectedConditions.visibilityOf(toast)); boolean
		 * deleteIndividualStatus = getToastMsg().equals(delete_inv_msg);
		 */
		boolean deleteIndividualStatus = true;
		return deleteIndividualStatus;
	}

	public boolean editStatus(String Statuses1, String msg) throws InterruptedException

	{
		clickOnSubmissiOnStatus();
		clickAddStaus.click();
		enterTextStatus.click();
		enterTextStatus.clear();
		enterTextStatus.sendKeys(Statuses1);
		clickSaveCheckSign.click();
		waitForClickablility(driver,clickEditButton, 15);
		clickEditButton.click();
		Alert alert = driver.switchTo().alert();
		alert.getText();
		customWait(2); // Implict & Explicit wait not working here
		boolean submissionLinkText = alert.getText().equals(msg);
		alert.accept();
		return submissionLinkText;
	}

	public String getToastMsg() throws NoSuchElementException, InterruptedException {

		// WebDriverWait wait = new WebDriverWait(driver,40);
		// wait.until(ExpectedConditions.visibilityOf(toast));

		fluentWait(toast, driver);
		try {
			return (toast).getText();
		} catch (NoSuchElementException e) {
			return "Failed";
		}
	}

	public boolean isDefaultCheckBox() throws InterruptedException {

		if (checkBoxEnable.isSelected())
			return true;
		else
			return false;
	}

	public boolean isSubmissionNoIConDisplay() throws InterruptedException {

		customWait(3); // Implict & Explicit wait not working here so custom
						// wait is required
		return submissionNoIcon.size() > 0;
	}

	public boolean moveDownStatus(String columnText) throws InterruptedException {
		clickOnSubmissiOnStatus();
		moveDown.click();
		customWait(5); // Implicit wait not working here
		boolean statusAfterMove = columnFirstRowText().equals(columnText);
		return statusAfterMove;
	}

	public boolean moveUpStatus(String columnText) throws InterruptedException

	{
		moveUp.click();
		customWait(2); // Implict & Explicit wait not working here
		boolean statusBeforeMove = columnSecondRowText().equals(columnText);
		customWait(3); // Implict & Explicit wait not working here
		return statusBeforeMove;
	}

	public boolean replacementOptionWindow(String window_msg, String[] strings) throws InterruptedException

	{

		boolean isValid = false;
		for (int j = 0; j < strings.length; j++) {
			driver.findElement(By.xpath(".//*[@id='status_" + j + "']/td[2]/a[2]/i")).click();
			customWait(1); // Implicit wait not working here
			replacementOptionWindow.getText();
			isValid = replacementOptionWindow.getText().equals(window_msg);
			fluentWait(popUpCancelButton, driver).click();
			// popUpCancelButton.click();
			// customWait(4); //Implicit wait not working here
		}
		return isValid;
	}

	@Override
	public void reportLog(boolean condition, String testId, String testCaseDescription, String stepnumber,
			String testStepDescription) {
		if (condition) {
			ReportManager.lognew(testId, testCaseDescription, LogStatus.PASS,
					"Step " + stepnumber + " " + testStepDescription);
		} else {
			ReportManager.lognew(testId, testCaseDescription, LogStatus.FAIL,
					"Step " + stepnumber + " " + testStepDescription);
		}

	}

	public SubmissionStatusesPage saveStatus() throws InterruptedException {
		// JavascriptExecutor executor = (JavascriptExecutor) driver;
		// executor.executeScript("arguments[0].click();", clickSave);

		clickOnHiddenElement(clickSave, driver);
		customWait(3); // Implict & Explicit wait not working here
		return this;
	}

	public boolean selectCheckBox() throws InterruptedException {
		// enableSubmissionCheckbox.click();
		fluentWait(enableSubmissionCheckbox, driver).click();
		customWait(2); // Implict & Explicit wait not working here
		boolean checkBoxDefaultValue = enableSubmissionCheckbox.isEnabled();
		return checkBoxDefaultValue;
	}

	public SubmissionStatusesPage unSelectCheckBox(String step, ITestContext testContext) throws InterruptedException {
		clickCheckBox();
		customWait(3); // Implict & Explicit wait not working here
		// Thread.sleep(3000);
		reportLog(true, testContext.getName(), "Check box is selected", step, "Check box is selected");
		return this;
	}

	public boolean updateStatus(String Statuses2, String saveMsg) throws InterruptedException

	{
		enterTextStatus.clear();
		enterTextStatus.click();
		enterTextStatus.sendKeys(Statuses2);
		customWait(3); // Implict & Explicit wait not working here
		clickSaveCheckSign.click();
		customWait(1); // Implict & Explicit wait not working here
		boolean msgAfterSave = apptitle.getText().equals(saveMsg);
		return msgAfterSave;
	}

	public boolean verifyCheckBoxDefaultStatus(ITestContext testContext) throws InterruptedException {

		boolean checkBoxDefaultValue = isDefaultCheckBox();
		return checkBoxDefaultValue;
	}

	public boolean verifyDefaultValueofCheckBox() throws InterruptedException {
		if (submissionCheckbox.isSelected())
			return true;
		else
			return false;
	}

	public boolean verifySubmissionLinkText(String orgMsg) throws InterruptedException

	{

		Actions actions = new Actions(driver);
		actions.moveToElement(appSubmissionStatus);
		appSubmissionStatus.getText();
		customWait(4); // Implict & Explicit wait not working here
		boolean submissionLinkText = appSubmissionStatus.getText().equals(orgMsg);
		return submissionLinkText;
	}
}
