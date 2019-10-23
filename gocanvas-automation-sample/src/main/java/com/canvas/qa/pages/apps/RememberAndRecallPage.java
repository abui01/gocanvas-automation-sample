package com.canvas.qa.pages.apps;

import java.util.List;

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

import com.canvas.qa.pages.BasePage;

/**
 * @author kailash.pathak
 *
 */
public class RememberAndRecallPage extends BasePage

{

	@FindBy(xpath = "//a[contains(.,'Inventory Collection')]")
	WebElement appLinkInventory;
	@FindBy(xpath = "//a[contains(.,'Detailed Weekly Timesheet')]")
	WebElement appLinkWeeklyTimesheet;

	// @FindBy(xpath = "//span[contains(.,'Remember & Recall')]")
	// WebElement rememberRecallLink1;

	@FindBy(xpath = "//button[@value='Save']")
	WebElement clickOnSaveButton;

	// strong[contains(.,'Remember & Recall')]

	@FindBy(xpath = "//*[contains(@class,'icheckbox')]")
	WebElement dispatchEnableCheckBox;

	@FindBy(xpath = ".//*[@id='page-wrapper']//span[contains(.,'Dispatch')]")
	WebElement dispatchlink;

	WebDriver driver;

	@FindBy(xpath = "//span[contains(.,'Log Out')]")
	WebElement logout;

	@FindBy(xpath = "//h1[contains(.,'Apps')]")
	WebElement pageName;

	@FindBy(xpath = "//h1[contains(.,'Dashboard')]")
	WebElement pageNameAfterLogin1;

	@FindBy(xpath = "//span[contains(.,'Apps')]")
	WebElement pageNameLeftPanel;

	// @FindBy(xpath = "//*[contains(@class,'icheckbox')]//*")
	@FindBy(xpath = "//strong[contains(.,'Remember & Recall:')]//../following-sibling::div//input[@type='checkbox']")
	WebElement rememberReacallCheckBox;

	@FindBy(xpath = "//label[contains(.,'Remember/Recall enabled on this app?')]")
	WebElement rememberReacallCheckBox1;

	// kp added
	@FindBy(xpath = "//strong[contains(.,'Remember & Recall:')]//../following-sibling::div//div[@class='slider round']")
	WebElement rememberReacallLink;

	// strong[contains(.,'Remember & Recall')]
	// @FindBy(xpath = "//span[contains(.,'Remember & Recall')]")
	@FindBy(xpath = "//strong[contains(.,'Remember & Recall:')]")
	List<WebElement> rememberRecallLink;

	@FindBy(xpath = "//strong[contains(.,'Remember & Recall')]")
	WebElement rememberRecallLink1;

	@FindBy(xpath = "//strong[contains(.,'Integration Options')]")
	WebElement remRecall;

	@FindBy(xpath = "//button[@value='Save']")
	WebElement saveButton;

	@FindBy(xpath = "//a[contains(.,'Test App')]")
	WebElement testAppLink;

	@FindBy(className = "toast-message")
	WebElement toast;

	public RememberAndRecallPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public RememberAndRecallPage clickAppLink() throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(appLinkInventory);
		fluentWait(appLinkInventory, driver).click();
		return this;
	}

	public boolean clickOnRememberAndRecallLink(String toastEnable) throws InterruptedException {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", remRecall);
		customWait(2);
		fluentWait(rememberReacallLink, driver).click();

		boolean toastEnableMsg = toast.getText().equals(toastEnable);
		return toastEnableMsg;
	}

	public String getToastMsg() throws NoSuchElementException, InterruptedException {

		fluentWait(toast, driver);
		try {
			return (toast).getText();
		} catch (NoSuchElementException e) {
			return "Failed";
		}
	}

	public boolean isRememberRecallLinkDisplay() throws InterruptedException {
		return rememberRecallLink.size() > 0;
	}

	public boolean pageNameAfterlogin(String pageNameAfterLogin) throws InterruptedException

	{
		boolean pageNameText = pageNameAfterLogin1.getText().equals(pageNameAfterLogin);
		return pageNameText;
	}

	public boolean pageNameAfterlogin1(String pageNameAfterLogin) throws InterruptedException

	{
		boolean pageNameText = pageNameAfterLogin1.getText().equals(pageNameAfterLogin);
		return pageNameText;
	}

	public boolean pageNameAfterlogin2(String pageNameAfterLogin) throws InterruptedException

	{
		boolean pageNameText = pageNameLeftPanel.getText().equals(pageNameAfterLogin);
		return pageNameText;
	}

	public void verifyClickRemAndRecallLink1() throws InterruptedException {
		fluentWait(rememberReacallCheckBox1, driver).click();
	}

	public void verifyClickRemAndRecallLink2() throws InterruptedException {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", remRecall);
		customWait(2);
		fluentWait(rememberReacallLink, driver).click();
		fluentWait(rememberReacallCheckBox1, driver).click();
	}

	public boolean verifyDisableRemAndRecallLink1(String disableMsg) throws InterruptedException {
		fluentWait(saveButton, driver).click();
		customWait(3); // Implict & Explicit wait not working here so custom
						// wait is required
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toast-message']")));
		boolean status = toast.getText().contains(disableMsg);
		return status;
	}

	public boolean verifyDisableRemAndRecallLink2(String enableMsg) throws InterruptedException {
		fluentWait(saveButton, driver).click();
		customWait(2); // Implict & Explicit wait not working here so custom
						// wait is required
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toast-message']")));
		boolean status = toast.getText().contains(enableMsg);
		return status;
	}

	public boolean verifyRememberAndRecall() throws InterruptedException {

		Actions actions = new Actions(driver);
		actions.moveToElement(rememberRecallLink1);
		boolean appStatus = isRememberRecallLinkDisplay();
		return appStatus;
	}

	public boolean verifyRememberRecallCheckBox() throws InterruptedException {
		boolean appStatus = rememberReacallCheckBox.isSelected();
		return appStatus;
	}

	public boolean verifyRememberRecallLinkInBottom() throws InterruptedException {

		pageNameLeftPanel.click();
		Actions actions = new Actions(driver);
		actions.moveToElement(appLinkWeeklyTimesheet);
		appLinkWeeklyTimesheet.click();
		boolean appStatus = isRememberRecallLinkDisplay();
		return appStatus;
	}
}
