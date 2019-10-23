package com.canvas.qa.pages.dispatch;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

public class DispatchManagerPage extends BasePage {

	//
	@FindBy(xpath = ".//*[@id='dispatch_submission_actions']/li/a/..")
	WebElement avilableOptions;

	@FindBy(xpath = "//span[contains(.,'Copy')]")
	WebElement btncopy;
	@FindBy(xpath = "//span[contains(.,'Delete')]")
	WebElement btnDelete;
	@FindBy(xpath = "//span[contains(.,'Un-assign')]")
	WebElement btnUnassign;
	@FindBy(xpath = "//span[contains(.,'Workflow & Dispatch')]")
	WebElement clickondispatch;

	@FindBy(xpath = ".//*[@class='table-responsive']//tr[1]/td[10]/a")
	WebElement clickonScheduledate;
	@FindBy(linkText = "Close")
	WebElement closebutton;

	@FindBy(xpath = ".//*[@class='table-responsive']//tr[1]/td[4]")
	WebElement copieddispatchname;

	@FindBy(css = ".fc-center>h2")
	WebElement date;

	@FindBy(xpath = ".//*[@class='table-responsive']//tr[1]/td[7]")
	WebElement dispatchstatus;

	/*
	 * @FindBy(css = ".toast-message") WebElement toast;
	 */

	WebDriver driver;

	@FindBy(xpath = ".//*[@class='table-responsive']//tr[1]/td[1]")
	WebElement firstCheckBox;

	@FindBy(xpath = "//div[contains(.,'Dispatch item successfully duplicated')]")
	WebElement selectplanbutton;

	@FindBy(xpath = "//div[@class='toast-message']")
	WebElement toast;

	@FindBy(xpath = "//button[contains(., 'Week')]")
	WebElement weekButton;

	/*
	 * 
	 */

	public DispatchManagerPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public String checkForCalendarView() {
		String successText;
		try {
			clickondispatch.click();
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("scroll(0, 250)");
			// Thread.sleep(4000);
			clickonScheduledate.click();
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(weekButton));
			// Thread.sleep(4000);
			successText = date.getText();
			clickOnHiddenElement(closebutton, driver);

		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}
	/*
	 * Dispatches copied
	 */

	public String checkForCloseButtonaVerifyLink() {
		String successText;
		try {
			closebutton.click();
			// Thread.sleep(2000);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("clickondispatch")));

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("scroll(0, 250)");
			Thread.sleep(3000);
			firstCheckBox.click();
			// avilableOptions.click();
			Thread.sleep(3000);
			successText = avilableOptions.getText();
			Thread.sleep(4000);

		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public String checkForcopieddispatchname() {
		String dispatchname;
		try {
			dispatchname = copieddispatchname.getText();

			// Thread.sleep(5000);
		} catch (Exception ex) {
			ex.printStackTrace();
			dispatchname = "Failed";
		}

		return dispatchname;
	}

	/*
	 * Dispatch Unassigned
	 */

	public String checkForDispatchDeleted() throws InterruptedException {
		String toastMessage;
		try {
			// closebutton.click();
			Thread.sleep(3000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("scroll(0, 250)");
			// Thread.sleep(5000);
			firstCheckBox.click();
			btnDelete.click();
			Thread.sleep(2000);
			driver.switchTo().alert().accept();
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[contains(.,'dispatch item successfully deleted.')]")));
			toastMessage = toast.getText();

		} catch (

		Exception ex) {
			ex.printStackTrace();
			toastMessage = "Failed";
		}

		return toastMessage;
	}

	/*
	 * Dispatch status after un-assigning
	 */

	public String checkForDispatchescopied() throws InterruptedException {
		String toastMessage;
		try {

			Thread.sleep(3000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("scroll(0, 250)");
			// Thread.sleep(3000);
			firstCheckBox.click();
			btncopy.click();
			Thread.sleep(4000);
			driver.switchTo().alert().accept();

			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[contains(.,'Dispatch item successfully duplicated')]")));
			toastMessage = toast.getText();
			// Thread.sleep(3000);

		} catch (

		Exception ex) {
			ex.printStackTrace();
			toastMessage = "Failed";
		}

		return toastMessage;
	}

	/*
	 * Dispatch Delete
	 */

	public String checkForDispatchUnassigned() throws InterruptedException {
		String toastMessage;
		try {

			Thread.sleep(3000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("scroll(0, 250)");
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(firstCheckBox));
			firstCheckBox.click();
			// Thread.sleep(3000);

			wait.until(ExpectedConditions.elementToBeClickable(btnUnassign));
			btnUnassign.click();
			Thread.sleep(3000);
			driver.switchTo().alert().accept();
			// WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[contains(.,'dispatch item successfully un-assigned')]")));
			toastMessage = toast.getText();

		} catch (

		Exception ex) {
			ex.printStackTrace();
			toastMessage = "Failed";
		}

		return toastMessage;
	}

	public String checkstatusofdispatch() {
		String dispatchupdatedstatus;
		try {
			Thread.sleep(3000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("scroll(0, 250)");
			dispatchupdatedstatus = dispatchstatus.getText();
			// Thread.sleep(4000);
		} catch (Exception ex) {
			ex.printStackTrace();
			dispatchupdatedstatus = "Failed";
		}

		return dispatchupdatedstatus;
	}
}
