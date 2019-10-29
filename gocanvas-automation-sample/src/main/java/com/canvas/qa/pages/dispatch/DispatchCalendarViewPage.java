package com.canvas.qa.pages.dispatch;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.canvas.qa.pages.BasePage;

/**
 * Class contains objects of calendar view opened while
 *         creating dispatch
 */
public class DispatchCalendarViewPage extends BasePage {

	public static final String dayDispatchTime = "09:00:00";

	public static final String weekDispatchTime = "23:30:00";

	@FindBy(id = "form_id")
	WebElement appDropdown;

	@FindBy(id = "user_id")
	WebElement asignItemTo;

	@FindBy(xpath = "//label[@for = 'calendar-invite']")
	WebElement calendarInviteCheckBox;

	@FindBy(xpath = "//button[contains(.,'Cancel')]")
	WebElement cancelButton;

	@FindBy(xpath = "//label[contains(.,'City')]/../div//input[contains(@type,'search')]")
	WebElement city1;

	@FindBy(xpath = "//label[contains(.,'City')]/../div//span[contains(@class,'ui-select-match-text pull-left')]")
	WebElement city2;

	@FindBy(xpath = "//label[contains(.,'City')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement selectCity;

	@FindBy(xpath = "//div[contains(.,'City')]")
	WebElement cityLabel;

	@FindBy(xpath = "//label[contains(.,'State')]/../div//span[contains(@class,'ui-select-choices-row-inner')]")
	WebElement stateDropDownClick;

	// @FindBy(id = "dispatch_entry3")
	@FindBy(xpath = ".//*[@id='dispatch_entry3']")
	// @FindBy( id = "ui-select-choices-0")
	WebElement citySelectBox;

	@FindBy(xpath = "//a[contains(.,'Close')]")
	WebElement closeButton;

	@FindBy(xpath = "//div[@class ='ng-scope']/label[contains(.,'Company URL')]")
	List<WebElement> companyURLLabel;

	@FindBy(xpath = "//div[contains(.,'Country')]")
	WebElement countryLabel;

	@FindBy(id = "dispatch_entry8")
	WebElement countrySelect;

	@FindBy(xpath = "//span[contains(., 'Create New Dispatch')]")
	WebElement createNewDispatchButton;

	String createNewDispatchButtonXpath = "//span[contains(., 'Create New Dispatch')]";

	@FindBy(xpath = "//div[@ng-switch-when = 'Date']/label[contains(.,'Birth Date')]")
	List<WebElement> dateDayLabel;

	@FindBy(xpath = "//label[contains(.,'Birth Date')]/following-sibling::div/input")
	WebElement dateDaySelect;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@FindBy(xpath = "//div[@class = 'dispatch_field']//label[contains(.,'Date')]")
	List<WebElement> dateLabel;
	@FindBy(xpath = "//div[contains(.,'Date')]/div/input")
	WebElement dateSelect;

	@FindBy(id = "dispatch_entry2")
	WebElement descriptionDayTextBox;

	@FindBy(xpath = "//div[contains(.,'Description')]")
	WebElement descriptionLabel;

	@FindBy(id = "dispatch_entry1")
	WebElement descriptionTextBox;

	@FindBy(id = "dispatch_item_name")
	WebElement dispatchNameTextBox;

	@FindBy(id = "dispatch_item_scheduled_at")
	WebElement dispatchScheduledAT;

	@FindBy(id = "dispatch_item_scheduled_end")
	WebElement dispatchScheduledEnd;

	@FindBy(xpath = "//button[contains(.,'Done')]")
	WebElement doneButton;

	WebDriver driver;

	@FindBy(xpath = "//div[contains(.,'Gender')]/div/label")
	WebElement genderCheckBox;

	@FindBy(xpath = "//div[contains(.,'Gender')]")
	WebElement genderLabel;

	// @FindBy(xpath = "//select[@class = 'ui-timepicker-select' and @data-unit =
	// 'hour']")

	@FindBy(xpath = "//input[contains(@class,'numInput flatpickr-hour')]")
	// input[contains(@class,'numInput flatpickr-hour')]
	WebElement hourSelect;

	@FindBy(xpath = "//span[contains(@class,'flatpickr-am-pm')]")
	// input[contains(@class,'numInput flatpickr-hour')]
	WebElement timeFormat;

	@FindBy(id = "dispatch_item_description")
	WebElement itemDescTextBox;

	String labelNameXpath = "//label[contains(., '%s')]";

	@FindBy(xpath = "//div[contains(.,'Last Name')]")
	WebElement lastNameLabel;

	@FindBy(id = "dispatch_entry1")
	WebElement lastNameTextBox;

	@FindBy(xpath = "//a[contains(.,' New Loop Item')]")
	WebElement loopbutton;

	// @FindBy(xpath = "//select[@class = 'ui-timepicker-select' and @data-unit =
	// 'minute']")
	@FindBy(xpath = "//input[contains(@class,'numInput flatpickr-minute')]")
	WebElement minutesSelect;

	// input[contains(@class,'numInput flatpickr-hour')]

	@FindBy(xpath = "//label[contains(.,'Multi-Choice')]/../div//input[contains(@type,'search')]")
	WebElement multi1;

	@FindBy(xpath = "//label[contains(.,'Multi-Choice')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement multi2;

	@FindBy(xpath = "//div[contains(.,'Multi-Choice')]")
	WebElement multiChoiceLabel;

	@FindBy(id = "dispatch_entry8")
	WebElement multiChoiceSelect;

	@FindBy(xpath = "//label[contains(.,'Multi Options')]/../div//input[contains(@type,'search')]")
	WebElement multiOption1;

	@FindBy(xpath = "//label[contains(.,'Multi Options')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement multiOption2;

	@FindBy(xpath = "//div[contains(.,'Multi Options')]")
	WebElement multiOptionsLabel;

	@FindBy(id = "dispatch_entry9")
	WebElement multiOptionsSelect;

	@FindBy(xpath = "//div[contains(.,'First Name')]")
	WebElement nameLabel;

	@FindBy(id = "dispatch_entry0")
	WebElement nameTextBox;

	@FindBy(id = "dispatch_entry12")
	WebElement newDropDownABCorD;

	@FindBy(xpath = "//span[contains(@class,'fc-icon fc-icon-right-single-arrow')]")
	WebElement nextCalendar;

	String nextDayButtonXpath = "//td[@data-date = '%s' and contains(@class,'fc-day-top')]";

	@FindBy(xpath = "//div[@id = 'calendar']/div/div/div/button[2]/span")
	WebElement nextDispatchDayButton;

	// String nextDispatchDayButtonXpath = "//table//table/tbody/tr[@data-time =
	// '%s']/td[2]" ;
	String nextDispatchDayButtonXpath = "//*[@id='calendar']/div[2]/div/table/tbody/tr/td/div/div/div[2]/table/tbody/tr[@data-time = '%s']/td[2]";

	String nextDispatchTimeButtonXpath = "//tr[@data-time = '%s']/td[2]";

	@FindBy(xpath = "//button[contains(.,'Now')]")
	WebElement nowButton;

	@FindBy(xpath = "//div[contains(.,'Number')]")
	WebElement numberLabel;

	@FindBy(xpath = "//div[contains(.,'Num')]")
	WebElement numLabel;

	@FindBy(id = "dispatch_entry2")
	WebElement numTextBox;

	@FindBy(xpath = "//select[@ng-model = 'data.dispatch_item.dispatch_item_notification.reminder']")
	List<WebElement> reminderDDL;

	@FindBy(xpath = "//label[text() = 'Reminder:']")
	List<WebElement> reminderLabel;

	@FindBy(xpath = "//div[contains(.,'Salary')]")
	WebElement salaryLabel;

	@FindBy(id = "dispatch_entry4")
	WebElement salaryTextBox;

	@FindBy(xpath = "//button[contains(.,'Save')]")
	WebElement saveButton;
	
	//@FindBy(xpath = "//a[@class='btn btn-sm cvs-def-btn']//i")
	//WebElement saveButton;

	@FindBy(xpath = "//h3[contains(.,'Screen 1')]")
	WebElement screen1Label;

	@FindBy(xpath = "//h3[contains(.,'Dispatch Day View Screen')]")
	WebElement screenDayLabel;

	String screenNameXpath = "//h3[contains(., '%s')]";

	@FindBy(xpath = "//h3[contains(.,'Dispatch for Week View')]")
	WebElement screenWeekLabel;

	@FindBy(xpath = "//label[contains(.,'State')]/../div//input[contains(@type,'search')]")
	WebElement state1;

	@FindBy(xpath = "//label[contains(.,'State')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement state2;
	@FindBy(xpath = "//div[contains(.,'State')]")
	WebElement stateLabel;

	@FindBy(id = "dispatch_entry5")
	WebElement stateSelectBox;

	@FindBy(id = "dispatch_entry3")
	WebElement stateWeekSelectBox;

	@FindBy(xpath = "//label[contains(.,'Birth Time')]")
	WebElement timeDayLabel;

	@FindBy(xpath = "//label[contains(.,'Birth Time')]/following-sibling::div/div/input")
	WebElement timeDaySelect;

	@FindBy(xpath = "//div[contains(.,'Time')]")
	WebElement timeLabel;

	@FindBy(xpath = "//div[contains(.,'Time')]/div/div/input")
	WebElement timeSelect;

	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement toastMessage;

	String valueXpath = "//div[contains(@title, '%s')]";

	@FindBy(id = "dispatch_entry8")
	WebElement webLinkDayTextBox;

	@FindBy(xpath = "//div[@class ='ng-scope']/label[contains(.,'Web-Link')]")
	List<WebElement> webLinkLabel;

	@FindBy(xpath = "//div[@class ='ng-scope']/label[contains(.,'Web-link')]")
	List<WebElement> webLinkLabelWeek;

	@FindBy(id = "dispatch_entry6")
	WebElement webLinkTextBox;

	@FindBy(xpath = "//div[@ng-switch-when = 'Web Link']/label[contains(.,'Web-link')]")
	List<WebElement> webLinkWeekLabel;

	// @FindBy(id = "dispatch_entry3")
	// WebElement stateDropDown;

	@FindBy(xpath = "//label[contains(.,'State')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement clickStateDropDown;

	@FindBy(xpath = "//label[contains(.,'City')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement clickCityDropDown;

	@FindBy(xpath = "//label[contains(.,'State')]/../div//input[contains(@type,'search')]")
	WebElement selectStateDropDownVal;

	@FindBy(xpath = "//label[contains(.,'City')]/../div//input[contains(@type,'search')]")
	WebElement selectCityDropDownVal;

	@FindBy(xpath = "//label[contains(.,'Multi-Choice')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement clickMultichoiceDropDown;

	@FindBy(xpath = "//label[contains(.,'Multi-Choice')]/../div//input[contains(@type,'search')]")
	WebElement selectMultichoiceDropDown;

	@FindBy(xpath = "//label[contains(.,'Country')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement clickCountryDropDown;

	@FindBy(xpath = "//label[contains(.,'Country')]/../div//input[contains(@type,'search')]")
	WebElement selectCountryDropDown;

	@FindBy(id = "dispatch_entry8")
	WebElement countryDropDown;

	@FindBy(xpath = "//input[@ng-model='$parent.$parent.entryValue']")
	WebElement clickTime;

	
	@FindBy(id = "operation_field_0")
	WebElement operatorDropdown;
	
	
	public DispatchCalendarViewPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

		// mixedContentChecker(driver);

		javaScriptErrorChecker(driver);
	}

	public DispatchCalendarViewPage clickCalendarInvite() throws InterruptedException {
		calendarInviteCheckBox.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public DispatchPage clickCloseButton() throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", closeButton);
		// ToDo : Remove sleep
		Thread.sleep(10000); // The sleep is required here as explicit wait and
								// implicit wait not working.
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return new DispatchPage(driver);
	}

	public DispatchCalendarViewPage clickCreateMonthNewDispatchLink() throws InterruptedException {
		createNewDispatchButton.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public DispatchCalendarViewPage clickCreateNewDispatchLink() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOf(createNewDispatchButton));
		createNewDispatchButton.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public DispatchCalendarViewPage clickDayNextDayDispatchButton() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(nextDispatchDayButton));
		nextDispatchDayButton.click();
		Thread.sleep(10000); // The sleep is required here as explicit wait and
								// implicit wait not working.
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement element = driver.findElement(By.xpath(String.format(nextDispatchTimeButtonXpath, dayDispatchTime)));
		element.click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return this;
	}

	public DispatchCalendarViewPage clickNewLoopItemLink() {
		loopbutton.click();
		return this;
	}

	public DispatchCalendarViewPage clickNextDayButton() throws ParseException, InterruptedException {
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		String convertedDate = dateFormat.format(dt);
		// ToDo : Remove sleep
		Thread.sleep(10000); // The sleep is required here as explicit wait and
								// implicit wait not working.

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement element = driver.findElement(By.xpath(String.format(nextDayButtonXpath, convertedDate)));
		element.click();
		// clickOnHiddenElement(element, driver);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(createNewDispatchButton));
		return this;
	}

	public DispatchCalendarViewPage clickNextDayDispatchButton() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		Thread.sleep(7000); // The sleep is required here as explicit wait and
							// implicit wait not working.
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath(String.format(nextDispatchDayButtonXpath, weekDispatchTime))));
		WebElement element = driver.findElement(By.xpath(String.format(nextDispatchDayButtonXpath, weekDispatchTime)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		String convertedDate = dateFormat.format(dt);

		String xpath = "//*[@id='calendar']/div[2]/div/table/tbody/tr/td/div/div/div[1]/table/tbody/tr/td[@data-date = '%s']";
		List<WebElement> element2 = driver.findElements(By.xpath(String.format(xpath, convertedDate)));
		if (element2.size() > 0) {
			element.click();
		} else {
			nextCalendar.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			element.click();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(createNewDispatchButtonXpath)));
		return this;
		
	}


	public DispatchCalendarViewPage clickNextDayWeekDispatchButton() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(nextDispatchDayButton));
		nextDispatchDayButton.click();

		Thread.sleep(10000); // The sleep is required here as explicit wait and
								// implicit wait not working.
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement element = driver.findElement(By.xpath(String.format(nextDispatchTimeButtonXpath, dayDispatchTime)));
		element.click();
		/*
		 * JavascriptExecutor executor = (JavascriptExecutor)driver;
		 * executor.executeScript("arguments[0].click();", element);
		 */

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return this;
	}

	public DispatchCalendarViewPage clickSaveButton() throws InterruptedException {
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		fluentWait(saveButton, driver).click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(toastMessage));
		//saveButton.click();
		return this;
	}

	public DispatchCalendarViewPage enterDispatchName(String dispatchName) {
		dispatchNameTextBox.click();
		customWait(2);
		dispatchNameTextBox.sendKeys(dispatchName);
		return this;
	}

	public DispatchCalendarViewPage enterFieldValues(String fieldID, String fieldValue) {
		WebElement element = driver.findElement(By.id(fieldID));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		element.sendKeys(fieldValue);
		return this;
	}

	public DispatchCalendarViewPage enterItemDescription(String itemDescription) {
		itemDescTextBox.sendKeys(itemDescription);
		customWait(2);
		return this;
	}

	public DispatchCalendarViewPage fillMonthScreenValues(int index1, String name, String desc, int num, String city,

			String weblink, String multichoice, String day) throws InterruptedException, AWTException {
		nameTextBox.click();
		nameTextBox.sendKeys(name);
		descriptionTextBox.sendKeys(desc);
		numTextBox.click();
		numTextBox.sendKeys(Integer.toString(num));

		clickCityDropDown.click();
		selectCityDropDownVal.sendKeys(city);

		/*
		 * dateSelect.click(); String dayXpath =
		 * "//div[contains(@class,'flatpickr-day')]//span[contains(@aria-label, '%s')]";
		 * List<WebElement> dayElement =
		 * driver.findElements(By.xpath(String.format(dayXpath, day)));
		 * dayElement.get(index1).click();
		 */

		webLinkTextBox.sendKeys(weblink);

		clickTime.click();

		customWait(3);
		clickMultichoiceDropDown.click();
		selectMultichoiceDropDown.sendKeys(multichoice);

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", genderCheckBox);
		/*
		 * //city2.click(); //city1.sendKeys(city); dateSelect.click();
		 * //nowButton.click(); //doneButton.click();
		 * driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 * timeSelect.click(); JavascriptExecutor executor = (JavascriptExecutor)
		 * driver; executor.executeScript("arguments[0].click();", nowButton);
		 * executor.executeScript("arguments[0].click();", doneButton);
		 * webLinkTextBox.sendKeys(weblink);
		 * executor.executeScript("arguments[0].click();", genderCheckBox);
		 * genderCheckBox.click(); webLinkTextBox.click(); multi2.click();
		 * Thread.sleep(3000); multi1.sendKeys(city);
		 * driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 */
		return this;
	}

	public DispatchCalendarViewPage fillScreenValues(String name, String desc, int num, String city, String weblink,
			String multichoice) throws InterruptedException {
		nameTextBox.sendKeys(name);
		descriptionTextBox.sendKeys(desc);
		numTextBox.sendKeys(Integer.toString(num));
		Select citydropdown = new Select(citySelectBox);
		citydropdown.selectByVisibleText(city);
		dateSelect.click();
		nowButton.click();
		doneButton.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		timeSelect.click();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", nowButton);
		executor.executeScript("arguments[0].click();", doneButton);
		webLinkTextBox.click();
		webLinkTextBox.sendKeys(weblink);
		executor.executeScript("arguments[0].click();", genderCheckBox);
		// genderCheckBox.click();
		Select multiChoiceSelectDropdown = new Select(multiChoiceSelect);
		multiChoiceSelectDropdown.selectByVisibleText(multichoice);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public DispatchCalendarViewPage fillScreenValues(int index1, String name, String lastName, String desc, int salary,
			String state, String companyURL, String multiOption, String day) throws InterruptedException {
		nameTextBox.sendKeys(name);
		lastNameTextBox.sendKeys(lastName);
		descriptionDayTextBox.sendKeys(desc);
		salaryTextBox.sendKeys(Integer.toString(salary));
		state2.click();
		state1.sendKeys(state);
		clickTime.click();

		// WebElement webElement =
		// driver.findElement(By.xpath("//label[contains(.,'State')]/../div//input[contains(@type,'search')]"));
		// webElement.sendKeys(Keys.TAB);
		/*
		 * dateDaySelect.click(); String dayXpath =
		 * "//div[contains(@class,'flatpickr-day')]//span[contains(@aria-label, '%s')]";
		 * List<WebElement> dayElement =
		 * driver.findElements(By.xpath(String.format(dayXpath, day)));
		 * dayElement.get(index1).click();
		 */

		/*
		 * nowButton.click(); doneButton.click();
		 * driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		 * JavascriptExecutor executor = (JavascriptExecutor) driver;
		 * executor.executeScript("arguments[0].click();", timeDaySelect);
		 * executor.executeScript("arguments[0].click();", nowButton);
		 * executor.executeScript("arguments[0].click();", doneButton);
		 */
		webLinkDayTextBox.sendKeys(companyURL);
		multiOption2.click();
		multiOption1.sendKeys(multiOption);
		customWait(2);
		return this;
	}

	// ToDo:::: Create a modal for dispatch field screen and use as a parameter

	/*
	 * public DispatchCalendarViewPage fillWeekScreenValues(String name, String
	 * desc, int num, String state, String weblink, String multichoice) throws
	 * InterruptedException { nameTextBox.sendKeys(name);
	 * descriptionTextBox.sendKeys(desc);
	 * numTextBox.sendKeys(Integer.toString(num)); Select statedropdown = new
	 * Select(stateWeekSelectBox); statedropdown.selectByVisibleText(state);
	 * dateSelect.click(); nowButton.click(); doneButton.click();
	 * driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	 * timeSelect.click(); JavascriptExecutor executor = (JavascriptExecutor)
	 * driver; executor.executeScript("arguments[0].click();", nowButton);
	 * executor.executeScript("arguments[0].click();", doneButton);
	 * webLinkTextBox.sendKeys(weblink);
	 * executor.executeScript("arguments[0].click();", genderCheckBox); Select
	 * countrySelectDropdown = new Select(countrySelect);
	 * countrySelectDropdown.selectByVisibleText(multichoice); return this; }
	 */

	public DispatchCalendarViewPage fillWeekScreenValues(int index1, String name, String desc, int num, String state,

		String weblink, String country, String day) throws InterruptedException {
		nameTextBox.sendKeys(name);
		descriptionTextBox.sendKeys(desc);
		numTextBox.sendKeys(Integer.toString(num));
		clickStateDropDown.click();
		selectStateDropDownVal.sendKeys(state);
		/*
		 * dateSelect.click(); String dayXpath =
		 * "//div[contains(@class,'flatpickr-day')]//span[contains(@aria-label, '%s')]";
		 * List<WebElement> dayElement =
		 * driver.findElements(By.xpath(String.format(dayXpath, day)));
		 * dayElement.get(index1).click();
		 */
		clickTime.click();
		webLinkTextBox.sendKeys(weblink);
		customWait(3);
		clickCountryDropDown.click();
		selectCountryDropDown.sendKeys(country);

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", genderCheckBox);
		return this;
	}

	public ArrayList<String> getAppList() {
		Select dropdown = new Select(appDropdown);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}

	
	public ArrayList<String> getAssignItemToList() {
		Select dropdown = new Select(asignItemTo);
		ArrayList<String> assignList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			assignList.add(element.getText());
		}
		return assignList;
	}

	public ArrayList<String> getCityList() throws AWTException, InterruptedException {

		selectCity.click();
		customWait(3);
		List<WebElement> choices = driver.findElements(
				By.xpath("//label[contains(.,'City')]/../div//span[contains(@class,'ui-select-choices-row-inner')]"));
		Thread.sleep(2000);
		ArrayList<String> assignList = new ArrayList<String>();
		for (WebElement element : choices) {
			assignList.add(element.getText());
		}
		return assignList;
	}

	public ArrayList<String> getCountryList() {
		countryDropDown.click();
		customWait(3);
		List<WebElement> options = driver.findElements((By
				.xpath("//label[contains(.,'Country')]/../div//span[contains(@class,'ui-select-choices-row-inner')]")));
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : options) {
			appList.add(element.getText());
		}
		return appList;
	}

	/*
	 * public ArrayList<String> getCountryList() { Select dropdown = new
	 * Select(countrySelect); ArrayList<String> appList = new ArrayList<String>();
	 * for (WebElement element : dropdown.getOptions()) {
	 * appList.add(element.getText()); } return appList;
	 * 
	 */

	public boolean getDispatchScheduledAtTime(String scheduledAt) throws InterruptedException {

		String scheduleAt = dispatchScheduledAT.getAttribute("value");
		if (scheduleAt.contains(scheduledAt));
		return true;
			
	}

	public boolean getDispatchScheduledEndTime(String scheduledEnd) throws InterruptedException {

		String scheduleEnd = dispatchScheduledEnd.getAttribute("value");
		if (scheduleEnd.contains(scheduledEnd));
		return true;
	}

	public ArrayList<String> getMultiChoiceList() throws AWTException, InterruptedException {

		multi2.click();
		List<WebElement> choices = driver.findElements(By.xpath(
				"//label[contains(.,'Multi-Choice')]/../div//span[contains(@class,'ui-select-choices-row-inner')]"));
		ArrayList<String> assignList = new ArrayList<String>();
		for (WebElement element : choices) {
			assignList.add(element.getText());
		}
		return assignList;
	}

	public ArrayList<String> getMultiOptionList() {
		multiOption2.click();
		List<WebElement> choices = driver.findElements(By.xpath(
				"//label[contains(.,'Multi Options')]/../div//span[contains(@class,'ui-select-choices-row-inner')]"));
		ArrayList<String> multiOptions = new ArrayList<String>();
		for (WebElement element : choices) {
			multiOptions.add(element.getText());
		}
		return multiOptions;
	}

	public String getReminderDDLSelectedValue() {
		Select dropdown = new Select(reminderDDL.get(0));
		return dropdown.getFirstSelectedOption().getText();
	}

	public ArrayList<String> getStateList() {

		state2.click();
		List<WebElement> choices = driver.findElements(
				By.xpath("//label[contains(.,'State')]/../div//span[contains(@class,'ui-select-choices-row-inner')]"));
		ArrayList<String> stateOptions = new ArrayList<String>();
		for (WebElement element : choices) {
			stateOptions.add(element.getText());
		}
		return stateOptions;
	}

	public String getToastMessage() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(toastMessage));
		return toastMessage.getText();
	}

	/*
	 * public ArrayList<String> getWeekStateList() { Select dropdown = new
	 * Select(stateWeekSelectBox); ArrayList<String> appList = new
	 * ArrayList<String>(); for (WebElement element : dropdown.getOptions()) {
	 * appList.add(element.getText()); } return appList; }
	 */

	public ArrayList<String> getWeeKStateList() {

		clickStateDropDown.click();
		customWait(3);
		List<WebElement> options = driver.findElements((By
				.xpath("//label[contains(.,'State')]/../div//span[contains(@class,'ui-select-choices-row-inner')]")));
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : options) {
			appList.add(element.getText());
		}
		return appList;
	}
	/*
	 * public ArrayList<String> getWeeKStateList() { Select dropdown = new
	 * Select(stateWeekSelectBox); ArrayList<String> appList = new
	 * ArrayList<String>(); for (WebElement element : dropdown.getOptions()) {
	 * appList.add(element.getText()); } return appList; }
	 */

	public boolean isCityLabelDisplayed() {
		return cityLabel.isDisplayed();
	}

	public boolean isCompanyURLLabelDisplayed() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", companyURLLabel.get(0));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return (companyURLLabel.size() == 1);
	}

	public boolean isCountryLabelDisplayed() {
		return countryLabel.isDisplayed();
	}

	public boolean isDateLabelDisplayed() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateLabel.get(0));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return (dateLabel.size() == 1);
	}

	public boolean isDayDateLabelDisplayed() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateDayLabel.get(0));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return (dateDayLabel.size() == 1);
	}

	public boolean isDayScreenLabelDisplayed() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", screenDayLabel);
		return screenDayLabel.isDisplayed();
	}

	public boolean isDayTimeLabelDisplayed() {
		return timeDayLabel.isDisplayed();
	}

	public boolean isDescLabelDisplayed() {
		return descriptionLabel.isDisplayed();
	}

	public boolean isGenderLabelDisplayed() {
		return genderLabel.isDisplayed();
	}

	public boolean isLabelDisplayed(String labelName) {
		WebElement element = driver.findElement(By.xpath(String.format(labelNameXpath, labelName)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		return element.isDisplayed();
	}

	public boolean isLastNameLabelDisplayed() {
		return lastNameLabel.isDisplayed();
	}

	public boolean isMonthDateLabelDisplayed() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateLabel.get(0));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return (dateLabel.size() == 1);
	}

	public boolean isMonthScreenLabelDisplayed() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", screen1Label);
		return screen1Label.isDisplayed();
	}

	public boolean isMonthTimeLabelDisplayed() {
		return timeLabel.isDisplayed();
	}

	public boolean isMultiChoiceLabelDisplayed() {
		return multiChoiceLabel.isDisplayed();
	}

	public boolean isMultiOptionLabelDisplayed() {
		return multiOptionsLabel.isDisplayed();
	}

	public boolean isNameLabelDisplayed() {
		return nameLabel.isDisplayed();
	}

	public boolean isNumLabelDisplayed() {
		return numLabel.isDisplayed();
	}

	public boolean isNumLabelWeekDisplayed() {
		return numberLabel.isDisplayed();
	}

	public boolean isReminderDDLDisplayed() {
		return (reminderDDL.size() == 1);
	}

	public boolean isReminderLabelDisplayed() {
		return (reminderLabel.size() == 1);
	}

	public boolean isSalaryLabelDisplayed() {
		return salaryLabel.isDisplayed();
	}

	public boolean isScreenLabelDisplayed() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", screenWeekLabel);
		return screenWeekLabel.isDisplayed();
	}

	public boolean isScreenNameDisplayed(String screenName) {
		WebElement element = driver.findElement(By.xpath(String.format(screenNameXpath, screenName)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		return element.isDisplayed();
	}

	public boolean isSelectAppDisplayed() {
		return appDropdown.isDisplayed();
	}

	public boolean isStateLabelDisplayed() {
		return stateLabel.isDisplayed();
	}

	public boolean isTimeLabelDisplayed() {
		return timeLabel.isDisplayed();
	}

	public boolean isWebLinkLabelDisplayed() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webLinkLabel.get(0));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return (webLinkLabel.size() == 1);
	}

	public boolean isWebLinkLabelWeekDisplayed() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webLinkLabelWeek.get(0));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return (webLinkLabelWeek.size() == 1);
	}

	public boolean isWebLinkWeekLabelDisplayed() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webLinkWeekLabel.get(0));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return (webLinkWeekLabel.size() == 1);
	}

	public DispatchCalendarViewPage selectApp(String appName) {
		selectDropDown(appDropdown, appName, driver);
		waitForVisbility(driver, dispatchNameTextBox, 10);
		return this;
	}

	public DispatchCalendarViewPage selectAssignTo(int index) {
		Select dropdown = new Select(asignItemTo);
		dropdown.selectByIndex(index);
		customWait(3);
		return this;
	}

	public DispatchCalendarViewPage selectCity(int index) {
		Select dropdown = new Select(city2);
		dropdown.selectByIndex(index);
		customWait(5);
		return this;
	}

	public DispatchCalendarViewPage selectNewDropDown(String value) {
		newDropDownABCorD.click();
		WebElement element = driver.findElement(By.xpath(String.format(valueXpath, value)));
		clickOnHiddenElement(element, driver);
		return this;
	}
	
	public ArrayList<String> getOperatorDropDownList() {
		
		WebElement dropdown = driver.findElement(By.id("operation_field_0"));
		dropdown.click();
		
		Select dropdown1 = new Select(dropdown);
		//Select dropdown = new Select(operatorDropdown);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown1.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}
}
