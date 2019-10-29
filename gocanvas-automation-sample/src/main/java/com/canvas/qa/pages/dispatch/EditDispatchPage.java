package com.canvas.qa.pages.dispatch;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.WorkflowPage;

public class EditDispatchPage extends BasePage

{

	public static void scrolltoElement(WebElement ScrolltoThisElement) {
		Coordinates coordinate = ((Locatable) ScrolltoThisElement).getCoordinates();
		coordinate.onPage();
		coordinate.inViewPort();
	}

	@FindBy(xpath = "//div[contains(.,'Date')]/div/input")
	WebElement dateSelect;

	@FindBy(xpath = "//td[contains(.,'App')]")
	WebElement appText;

	@FindBy(id = "//*[@id='dispatch_fields']/div[6]/div/div/div[7]/div/div/div/div/div/div[2]/label/input")
	WebElement checkbox;

	@FindBy(xpath = "//button[contains(.,'Done')]")
	WebElement dateDone;

	@FindBy(xpath = "//label[contains(.,'New Date')]/following-sibling::div//input")
	WebElement dateTextBox;

	String dateXpath = "//a[contains(.,'%s')]";

	@FindBy(id = "dispatch_entry4")
	WebElement decimal;

	@FindBy(xpath = "//tbody/tr/td/a")
	// @FindBy(xpath = "//a[contains(.,'41')]")
	WebElement dispatch;

	// @FindBy(linkText = "Dispatch")
	@FindBy(xpath = "//span[contains(.,'Dispatch')]")
	WebElement dispatchBtn;

	@FindBy(id = "dispatch_item_description")
	WebElement dispatchDescription;

	@FindBy(id = "dispatch_item_description")
	WebElement dispatchItemDesc;

	@FindBy(id = "dispatch_item_name")
	WebElement dispatchName;

	@FindBy(id = "dispatch_type")
	WebElement dispatchType;

	WebDriver driver;

	@FindBy(xpath = "//label[contains(.,'Drop Down: A, B, C')]/../div//input[contains(@type,'search')]")
	WebElement dropdDown1;

	@FindBy(xpath = "//label[contains(.,'Drop Down: A, B, C')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement dropdDown2;

	@FindBy(xpath = "//label[contains(.,'New Drop Down')]/../div//input[contains(@type,'search')]")
	WebElement dropDown1;

	@FindBy(xpath = "//label[contains(.,'New Drop Down')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement dropDown2;

	// @FindBy(id = "dispatch_entry4")
	// WebElement DropdownABC;

	// @FindBy(xpath = ".//*[@id='dispatch_entry5']")
	// WebElement Radiobutton;

	@FindBy(linkText = "Edit")
	WebElement edit;
	@FindBy(id = "dispatch_entry11")
	WebElement field1ClickLoop;

	@FindBy(css = "select.dispatch_entry")
	WebElement field1ClickLoopold;

	@FindBy(id = "dispatch_entry13")
	WebElement field2Loop;

	@FindBy(xpath = "//select[@class = 'ui-timepicker-select' and @data-unit = 'hour']")
	WebElement hourTimePicker;

	@FindBy(id = "dispatch_entry3")
	WebElement integer;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(id = "dispatch_entry5")
	WebElement loopLongTextBox;

	@FindBy(xpath = "//select[@class = 'ui-timepicker-select' and @data-unit = 'minute']")
	WebElement minuteTimePicker;

	@FindBy(xpath = "//select[@class = 'ui-datepicker-month']")
	WebElement monthDatePicker;

	@FindBy(xpath = ".//*[@id='dispatch_entry10']")
	WebElement Newbarcode;

	@FindBy(id = "dispatch_entry2")
	WebElement newDropDown;

	@FindBy(id = "dispatch_entry1")
	WebElement newLongText;

	@FindBy(id = "dispatch_entry1")
	WebElement newLongTextBox;

	@FindBy(id = "dispatch_entry7")
	WebElement newLongTextBoxafterSave;

	@FindBy(xpath = "//a[contains(.,' New Loop Item')]")
	WebElement newLoop;

	@FindBy(xpath = ".//*[@id='dispatch_entry11']")
	WebElement Newmsn;

	@FindBy(id = "dispatch_entry0")
	WebElement newShortText;

	@FindBy(id = "dispatch_entry0")
	WebElement newShortTextBox;

	@FindBy(id = "dispatch_entry6")
	WebElement newShortTextBoxafterSave;

	@FindBy(linkText = "Profile")
	WebElement profile;

	@FindBy(xpath = "//label[contains(.,'Radio Button')]/../div//input[contains(@type,'search')]")
	WebElement radio1;

	@FindBy(xpath = "//label[contains(.,'Radio Button')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement radio2;

	@FindBy(id = "save_form")
	WebElement save;

	@FindBy(css = "#save_form")
	WebElement saveButton;

	@FindBy(xpath = "//div[contains(@class,'toast-message')]")
	WebElement saveDatamessage;

	@FindBy(xpath = "//label[contains(.,'New Time')]/following-sibling::div//input")
	WebElement timeTextBox;

	@FindBy(xpath = "//div[@class = 'ibox-title no-borders']/p")
	WebElement title;

	@FindBy(id = "user_id")
	WebElement userId;

	@FindBy(xpath = "//a[contains(.,'Workflow & Dispatch')]")
	WebElement workflowAndDispatchLink;

	@FindBy(xpath = "//select[@class = 'ui-datepicker-year']")
	WebElement yearDatePicker;

	@FindBy(xpath = "//input[contains(@class,'numInput flatpickr-hour')]")
	WebElement hrsTextBox;
	@FindBy(xpath = "//input[contains(@class,'numInput flatpickr-minute')]")
	WebElement minTextBox;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public EditDispatchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

		// mixedContentChecker(driver);

		javaScriptErrorChecker(driver);
	}

	public String checkForupdateddispatchdata(ITestContext testContext) throws IOException, InterruptedException

	{

		java.util.List<WebElement> allLinks = driver.findElements(By.xpath("//tbody/tr"));
		// Thread.sleep(1000);

		StringBuilder sb = new StringBuilder();
		for (WebElement link : allLinks) {

			sb.append(link.getText());
			// sb.append("\r\n");
			sb.append(" ");

			Thread.sleep(2000);
		}

		return sb.toString().substring(0, sb.length() - 1);
	}

	public void clearFields() {
		dispatchName.click();
		dispatchName.clear();
		dispatchDescription.click();
		dispatchDescription.clear();
		newShortText.clear();
		newLongText.clear();
		integer.clear();
	}

	public EditDispatchPage clickDate() {
		dateTextBox.click();
		return this;
	}

	public EditDispatchPage clickDoneButton() {
		dateDone.click();
		return this;
	}

	public DispatchDetailsPage clickSaveButton() throws InterruptedException {
		clickOnHiddenElement(saveButton, driver);
		customWait(5);
		return new DispatchDetailsPage(driver);
	}

	public EditDispatchPage clickTime() {
		timeTextBox.click();
		return this;
	}

	public WorkflowPage clickWorkflowAndDispatchLink() {
		clickOnHiddenElement(workflowAndDispatchLink, driver);
		return new WorkflowPage(driver);
	}

	public void editDispatchClick() throws InterruptedException {
		/*
		 * JavascriptExecutor executor = (JavascriptExecutor)driver;
		 * executor.executeScript("arguments[0].click();", dispatchBtn);
		 */
		// (dispatchBtn).click();
		Thread.sleep(15000);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", dispatch);
		Thread.sleep(15000);
		// (dispatch).click();
		(edit).click();
		// Thread.sleep(5000);
	}

	public EditDispatchPage enterDispatchItemDesc(String text) {
		dispatchItemDesc.clear();
		dispatchItemDesc.sendKeys(text);
		return this;
	}

	public EditDispatchPage enterLoopNewLongText(String text) {
		loopLongTextBox.clear();
		loopLongTextBox.sendKeys(text);
		return this;
	}

	public EditDispatchPage enterNewLongText(String text) {
		newLongTextBox.clear();
		newLongTextBox.sendKeys(text);
		return this;
	}

	public EditDispatchPage enterNewShortText(String text) {
		newShortTextBox.clear();
		newShortTextBox.sendKeys(text);
		return this;
	}

	public String getLongText() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", newLongTextBoxafterSave);
		return newLongTextBoxafterSave.getText();
	}

	public String getShortText() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", newShortTextBoxafterSave);
		return newShortTextBoxafterSave.getText();
	}

	public void logout() throws InterruptedException {
		// Thread.sleep(5000);
		logout.click();
	}

	public EditDispatchPage profileBtnClick(String dispatchname, String dispatchDesc, String assign, String dispatchTyp,
			String shortText, String longText, String newInteger, String newdecimal, String newbar, String newmsn,
			String field2Loopnew, String dropDown, String radioOption, String integerval) throws InterruptedException {
		// clearFields();
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(dispatchName));
		dispatchName.click();
		dispatchName.clear();
		dispatchName.sendKeys(dispatchname);

		wait.until(ExpectedConditions.elementToBeClickable(dispatchDescription));
		dispatchDescription.click();
		dispatchDescription.clear();
		dispatchDescription.sendKeys(dispatchDesc);
		Thread.sleep(5000);

		wait.until(ExpectedConditions.elementToBeClickable(userId));
		Select assignTo = new Select(userId);
		assignTo.selectByVisibleText(assign);

		wait.until(ExpectedConditions.elementToBeClickable(dispatchType));
		Select type = new Select(dispatchType);
		type.selectByVisibleText(dispatchTyp);

		Point hoverItem = driver.findElement(By.id("dispatch_entry0")).getLocation();
		// Thread.sleep(6000);
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + (hoverItem.getY() - 200) + ");");

		newShortText.click();
		Thread.sleep(2000);
		newShortText.clear();
		newShortText.sendKeys(shortText);

		// WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("dispatch_entry1")));
		newLongText.click();
		newLongText.clear();
		newLongText.sendKeys(longText);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("dispatch_entry3")));
		integer.click();
		integer.clear();
		integer.sendKeys(newInteger);
		// Thread.sleep(1000);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("dispatch_entry4")));
		decimal.click();
		decimal.clear();
		decimal.sendKeys(newdecimal);
		// Thread.sleep(2000);

		WebElement scrolldrop = dropdDown1;
		dropdDown2.click();
		dropdDown1.sendKeys(dropDown);
		scrolldrop.sendKeys(Keys.TAB);
		Thread.sleep(2000);

		WebElement scrollradio = radio1;
		radio2.click();
		radio1.sendKeys(radioOption);
		scrollradio.sendKeys(Keys.TAB);
		Thread.sleep(1000);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", Newbarcode);

		Newbarcode.clear();
		Newbarcode.sendKeys(newbar);
		// Thread.sleep(2000);

		Newmsn.click();
		Newmsn.clear();
		Newmsn.sendKeys(newmsn);

		Thread.sleep(3000);
		field2Loop.click();
		field2Loop.clear();
		field2Loop.sendKeys(integerval);

		return this;
	}

	public String SaveUpdatedDispatchData() throws InterruptedException {
		String successText;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", save);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'toast-message')]")));
		// successText = saveDatamessage.getText();
		Thread.sleep(10000);

		successText = title.getText();
		return successText;

	}

	@FindBy(xpath = "//div[@class ='flatpickr-month']")
	List<WebElement> clickmonthList;

	public EditDispatchPage selectDate(int index1, String day, String month) {

		clickmonthList.get(0).click();
		String monthXpath = "//div[contains(@class,'month-entry')][%s]";
		WebElement monthElement = driver.findElement(By.xpath(String.format(monthXpath, month)));
		monthElement.click();
		customWait(5);

		dateSelect.click();
		String dayXpath = "//div[contains(@class,'flatpickr-day')]//span[contains(@aria-label, '%s')]";
		List<WebElement> dayElement = driver.findElements(By.xpath(String.format(dayXpath, day)));
		dayElement.get(index1).click();

		/*
		 * Select monthSelector = new Select(monthDatePicker); Select yearSelector = new
		 * Select(yearDatePicker); yearSelector.selectByVisibleText(year);
		 * monthSelector.selectByVisibleText(month); WebElement dateElement =
		 * driver.findElement(By.xpath(String.format(dateXpath, date)));
		 * dateElement.click();
		 */
		return this;
	}

	public EditDispatchPage selectNewDropDown(String value) {

		WebElement scrollDrop = dropDown1;
		dropDown2.click();
		dropDown1.sendKeys(value);
		scrollDrop.sendKeys(Keys.TAB);
		// Select dropdown = new Select(newDropDown);
		// dropdown.selectByVisibleText(value);
		return this;
	}

	public EditDispatchPage selectTime(String hour, String minute) {
		hrsTextBox.click();
		customWait(2);
		hrsTextBox.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
		hrsTextBox.sendKeys(hour);
		customWait(3);
		minTextBox.click();
		minTextBox.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
		minTextBox.sendKeys(minute);
		WebElement webElement = driver.findElement(By.xpath("//input[contains(@class,'numInput flatpickr-minute')]"));
		webElement.sendKeys(Keys.TAB);
		customWait(2);
		return this;
	}

	public void verify() throws InterruptedException {
		// TODO

	}
}
