package com.canvas.qa.pages.dispatch;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.WorkflowPage;
import com.canvas.util.FileReaderUtil;

public class CreateDispatchPage extends BasePage {
	// private static final String String = null;
	public static final String YAML_FILE = "Testdata/Testdata.yml";
	// private static final java.lang.String i = null;

	public static boolean sortedOrNot(ArrayList<String> dropDownValues) {
		System.out.println("number of values " + dropDownValues.size());
		for (int i = 0; i < dropDownValues.size() - 1; i++) {
			// int temp =
			// dropDownValues.get(i).compareTo(dropDownValues.get(i+1));
			int temp = dropDownValues.get(i).toUpperCase().compareTo(dropDownValues.get(i + 1));
			if (temp > 1) {
				return false;
			}
		}
		return true;
	}

	@FindBy(id = "form_id")
	WebElement appDDL;
	@FindBy(xpath = ".//*[@id='user_id']")
	WebElement assignItemTo;
	@FindBy(id = "user_id")
	WebElement assignToDDL;
	@FindBy(id = "dispatch_item_scheduled_at")
	WebElement calendarFromDate;
	@FindBy(id = "calendar-invite")
	WebElement calendarinvite;
	@FindBy(id = "dispatch_item_scheduled_end")
	WebElement calendarToDate;
	@FindBy(xpath = "//a[contains(.,'Create Dispatch')]")
	WebElement clickoncreatedispatch;
	@FindBy(xpath = "//input[@canvas-date-picker='']")
	WebElement date;
	@FindBy(xpath = "//button[contains(.,'Done')]")
	WebElement dateDone;
	@FindBy(xpath = "//button[contains(.,'Now')]")
	WebElement dateNow;
	@FindBy(xpath = "//label[contains(.,'New Date')]/following-sibling::div//input")
	WebElement dateTextBox;
	@FindBy(xpath = "//textarea[@id='dispatch_item_description']")
	WebElement dispatchDescription;
	// @FindBy(xpath = "//input[@id='dispatch_item_name']")
	@FindBy(id = "dispatch_item_name")
	WebElement dispatchName;

	@FindBy(xpath = ".//*[@id='dispatch_type']")
	WebElement dispatchType;

	WebDriver driver;

	@FindBy(xpath = "//label[contains(.,'Drop Down: A B C')]/../div//input[contains(@type,'search')]")
	WebElement dropdDown1;
	@FindBy(xpath = "//label[contains(.,'Drop Down: A B C')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement dropdDown2;
	@FindBy(xpath = "//label[contains(.,'New Drop Down')]/../div//input[contains(@type,'search')]")
	WebElement dropDown1;
	@FindBy(xpath = "//label[contains(.,'New Drop Down')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement dropDown2;
	@FindBy(xpath = ".//*[@id='dispatch_entry0']")
	WebElement field1;
	@FindBy(xpath = ".//*[@id='dispatch_entry11']")
	WebElement field10;
	@FindBy(id = "dispatch_entry11")
	WebElement field1ClickLoop;
	@FindBy(css = "select.dispatch_entry")
	WebElement field1ClickLoopold;
	@FindBy(xpath = "//textarea[contains(@type,'text')]")
	WebElement field2;
	@FindBy(id = "dispatch_entry13")
	WebElement field2Loop;
	@FindBy(css = "select.dispatch_entry")
	WebElement field2Loop111;
	@FindBy(xpath = ".//*[@id='dispatch_entry3']")
	WebElement field3;

	@FindBy(xpath = ".//*[@id='dispatch_entry4']")
	WebElement field4;
	@FindBy(xpath = ".//*[@id='dispatch_entry5']")
	WebElement field5;
	@FindBy(xpath = ".//*[@id='dispatch_entry6']")
	WebElement field6;

	@FindBy(xpath = ".//*[@id='dispatch_entry10']")
	WebElement field9;
	@FindBy(xpath = "//*[@id='yes_dispatch_fields']/div[6]/div/div/div[1]/div/div/div/div/label")
	WebElement Fields1;
	@FindBy(xpath = ".//*[@id='yes_dispatch_fields']")
	WebElement Fields2;
	@FindBy(id = "dispatch_item_description")
	WebElement itemDescTextBox;
	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(xpath = "//label[contains(.,'New Drop Down A B C or D')]/../div//input[contains(@type,'search')]")
	WebElement loop1;

	@FindBy(xpath = "//label[contains(.,'New Drop Down A B C or D')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement loop2;

	@FindBy(xpath = "//a[contains(.,' New Loop Item')]")
	WebElement loopbutton;

	@FindBy(xpath = ".//*[@id='yes_dispatch_fields']/div[13]/div/div/div[2]/div/div/div/div[2]/a")
	WebElement loopbutton1;

	@FindBy(xpath = ".//*[@id='yes_dispatch_fields']/div[14]/div/div/div[2]/div/div/div/div/a")
	WebElement loopbutton2;
	@FindBy(xpath = "//h3[contains(.,'Loop')]")
	WebElement loopLabel;
	@FindBy(id = "dispatch_entry5")
	WebElement loopLongTextBox;

	@FindBy(id = "dispatch_entry2")
	WebElement newDropDown;

	@FindBy(id = "dispatch_entry1")
	WebElement newLongTextBox;

	@FindBy(id = "dispatch_entry7")
	WebElement newLongTextBoxafterSave;

	@FindBy(xpath = "//a[contains(.,' New Loop Item')]")
	WebElement newLoop;

	@FindBy(id = "dispatch_entry0")
	WebElement newShortTextBox;

	@FindBy(id = "dispatch_entry6")
	WebElement newShortTextBoxafterSave;

	@FindBy(xpath = "//label[contains(.,'Radio Button')]/../div//input[contains(@type,'search')]")
	WebElement radio1;

	@FindBy(xpath = "//label[contains(.,'Radio Button')]/../div//span[contains(@class,'ui-select-toggle')]")
	WebElement radio2;

	@FindBy(xpath = "//select[contains(@ng-model,'notification.reminder')]")
	WebElement Reminder;

	@FindBy(id = "save_form")
	WebElement saveButton;

	// @FindBy(xpath = "//div[contains(.,'Your dispatch was succesfully
	// created.')]")
	@FindBy(xpath = "//div[@class='toast-message']")
	WebElement saveDatamessage;

	@FindBy(css = "#save_form")
	WebElement savedispatch;

	@FindBy(xpath = "//h3[contains(.,'New Screen 1')]")
	WebElement screenLabel;

	String screenTextXpath = "//div[contains(.,'%s')]";

	@FindBy(xpath = "//select[@id='user_id']")
	WebElement selectUserValueFromDropdown;

	/**
	 * Locators of Login Screen
	 */
	@FindBy(xpath = "//select[@id='form_id']")
	WebElement selectValueFromDropdown;

	@FindBy(xpath = "//h1[contains(.,'Create Dispatch')]")
	WebElement sucessfullClickOnCreateDispatchLink;

	//

	@FindBy(xpath = "//h1[contains(.,'Workflow & Dispatch')]")
	WebElement sucessfullClickOnWorkflowAndDispatchLink;

	//

	@FindBy(xpath = "//input[@canvas-time-picker='']")
	WebElement time;

	@FindBy(xpath = "//input[contains(@ng-model,'$parent.$parent.entryValue')]")
	WebElement time1;

	@FindBy(xpath = "//button[contains(.,'Done')]")
	WebElement timeDone;

	@FindBy(xpath = "//button[contains(.,'Now')]")
	WebElement timeNow;

	@FindBy(xpath = "//label[contains(.,'New Time')]/following-sibling::div//input")
	WebElement timeTextBox;

	@FindBy(xpath = "//a[contains(.,'Workflow & Dispatch')]")
	WebElement workflowAndDispatchLink;

	public CreateDispatchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);


		javaScriptErrorChecker(driver);
	}

	public boolean Allfieldsofpage(String[] labels) throws IOException, InterruptedException

	{

		// WebDriverWait wait = new WebDriverWait(driver, 10);
		// wait.until(ExpectedConditions.elementToBeClickable(Fields1));
		// (Fields1).click();

		boolean flag = false;
		customWait(4);

		// List<WebElement> allElements =
		// driver.findElements(By.xpath("//label[@class='col-sm-4 col-md-4
		// col-lg-4 control-label ng-binding']"));

		List<WebElement> allElements = driver.findElements(
				By.xpath("//label[@class='col-sm-4 col-md-4 col-lg-4 control-label ng-binding ng-scope']"));

		for (WebElement e1 : allElements) {

			for (int i = 0; i < labels.length; i++) {
				if (e1.getText().equals(labels[i]))
					flag = true;
			}
			if (flag == false)
				break;
		}
		return flag;
	}

	public String AssignFromDropDown(String username) throws IOException, InterruptedException

	{
		(assignItemTo).click();
		customWait(2);
		Select dropdown = new Select(assignItemTo);
		dropdown.selectByVisibleText(username);
		customWait(3);
		return username;

	}

	public String checkForSSaveDispatchData() throws InterruptedException {
		String successText;

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", saveButton);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(saveDatamessage));

		try {
			successText = saveDatamessage.getText();
		} catch (Exception e) {
			e.printStackTrace();
			successText = "Failed";
		}

		return successText;

	}

	public CreateDispatchPage clickDate() {
		dateTextBox.click();
		return this;
	}

	public CreateDispatchPage clickNewLoopItemLink() {
		loopbutton.click();
		return this;
	}

	public CreateDispatchPage clickNowButton() {
		dateNow.click();
		return this;
	}

	public CreateDispatchPage clickTime() {
		timeTextBox.click();
		return this;
	}

	public WorkflowPage clickWorkflowAndDispatchLink() {
		clickOnHiddenElement(workflowAndDispatchLink, driver);
		return new WorkflowPage(driver);
	}

	public long DateDiffrence() throws InterruptedException, Throwable {

		String sAt = (calendarFromDate).getAttribute("value");
		String eAt = (calendarToDate).getAttribute("value");

		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy hh:mm a");
		Date sa = dateFormat.parse(sAt);
		Date ea = dateFormat.parse(eAt);
		long timeDifferenceInMillis = ((ea.getTime() - sa.getTime()) / (1000 * 60 * 60));

		return timeDifferenceInMillis;
	}

	public String DispatchAssignToUser() throws InterruptedException

	{
		String dropDownVales = "";
		assignItemTo.click();
		Select dropdown = new Select(assignItemTo);
		List<WebElement> dd = dropdown.getOptions();
		for (int j = 1; j < dd.size(); j++) {
			String abc = dd.get(j).getText();

			dropDownVales = dropDownVales + abc + ",";
			customWait(2);
		}
		return dropDownVales.substring(0, dropDownVales.length() - 1);

	}

	public String DispatchType() throws InterruptedException

	{
		String dropDownVales = "";
		dispatchType.click();
		Select dropdown = new Select(dispatchType);
		List<WebElement> dd = dropdown.getOptions();
		for (int j = 0; j < dd.size(); j++) {
			String abc = dd.get(j).getText();

			dropDownVales = dropDownVales + abc + ",";

			dropdown.selectByIndex(1);

		}

		return dropDownVales.substring(0, dropDownVales.length() - 1);

	}

	public boolean dropDownListSortedOrNot() throws IOException

	{

		WebElement element = driver.findElement(By.xpath(".//*[@id='form_id']"));
		element.click();
		List<WebElement> dropDownvalues = element.findElements(By.tagName("option"));
		ArrayList<String> listValues = new ArrayList<String>();
		for (WebElement value : dropDownvalues) {
			listValues.add(value.getText());
		}
		boolean sortedOrNot = sortedOrNot(listValues);
		return sortedOrNot;

	}

	public CreateDispatchPage enterFieldsName(String fieldsName, int i) throws IOException

	{

		{

			driver.findElement(By.xpath(".//*[@id='dispatch_entry" + String.valueOf(i) + "']")).click();
			driver.findElement(By.xpath(".//*[@id='dispatch_entry" + String.valueOf(i) + "']")).sendKeys(fieldsName);

		}
		return this;

	}

	public CreateDispatchPage enterItemDescription(String desc) {
		itemDescTextBox.sendKeys(desc);
		return this;
	}

	public CreateDispatchPage enterLoopNewLongText(String text) {
		loopLongTextBox.sendKeys(text);
		return this;
	}

	public CreateDispatchPage enterNewLongText(String text) {
		newLongTextBox.sendKeys(text);
		return this;
	}

	public CreateDispatchPage enterNewShortText(String text) {
		newShortTextBox.sendKeys(text);
		return this;
	}

	public ArrayList<String> getAppList() {
		Select dropdown = new Select(appDDL);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}

	public ArrayList<String> getAssignToList() {
		Select dropdown = new Select(assignToDDL);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}

	public String getItemDescription() {
		return itemDescTextBox.getText();
	}

	public String getLongText() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", newLongTextBoxafterSave);
		return newLongTextBoxafterSave.getText();
	}

	public String getSelectedApp() {
		Select dropdown = new Select(appDDL);
		return dropdown.getFirstSelectedOption().getText();
	}

	public String getSelectedAssignTo() {
		Select dropdown = new Select(assignToDDL);
		return dropdown.getFirstSelectedOption().getText();
	}

	public String getShortText() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", newShortTextBoxafterSave);
		return newShortTextBoxafterSave.getText();
	}

	public boolean isLoopLabelDisplayed() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loopLabel);
		return loopLabel.isDisplayed();
	}

	public boolean isNewLoopItemLinkDisplayed() {
		return loopbutton.isDisplayed();
	}

	public boolean isScreenLabelDisplayed() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", screenLabel);
		return screenLabel.isDisplayed();
	}

	public boolean isScreenTextDisplayed(String textName) {
		WebElement element = driver.findElement(By.xpath(String.format(screenTextXpath, textName)));
		return element.isDisplayed();
	}

	public void logout() throws InterruptedException {
		Thread.sleep(5000);
		clickOnHiddenElement(logout, driver);
	}

	/*
	 * When user check Send Calendar Invite check -box
	 */
	public boolean ReminderDropDownDisplay() throws InterruptedException {

		// (dispatchType).click();
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Select dropdown = new Select(dispatchType);
		dropdown.selectByIndex(1);
		// customWait(3);
		WebElement calInviteCheckbox = driver.findElement(By.xpath(".//*[@name ='send-notification']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", calInviteCheckbox);
		customWait(2);
		// JavascriptExecutor jse = ((JavascriptExecutor) driver);
		// jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Point hoverItem = driver.findElement(By.id("dispatch_entry0")).getLocation();
		// Thread.sleep(6000);
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + (hoverItem.getY() - 200) + ");");
		// Thread.sleep(5000);
		boolean checkBoxDisplay = Reminder.isDisplayed();
		return checkBoxDisplay;
	}

	/*
	 * When user un-check Send Calendar Invite check -box
	 */
	public boolean ReminderDropDownNotDisplay() throws InterruptedException {

		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Select dropdown = new Select(dispatchType);
		dropdown.selectByIndex(1);
		Thread.sleep(5000);
		WebElement calInviteCheckbox = driver.findElement(By.xpath(".//*[@name ='send-notification']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", calInviteCheckbox);

		boolean checkBoxDisplay;
		try {
			checkBoxDisplay = Reminder.isDisplayed();
		} catch (Exception e) {
			checkBoxDisplay = false;
			// e.printStackTrace();
		}

		return checkBoxDisplay;
	}

	public CreateDispatchPage SaveDispatch(ITestContext testContext, String dropDown, String radioOption)
			throws IOException, InterruptedException {

		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(field1));
		field1.click();
		customWait(1);

		wait.until(ExpectedConditions.elementToBeClickable(field1));
		field1.sendKeys(parameters.get("field_1"));
		customWait(1);

		wait.until(ExpectedConditions.elementToBeClickable(field2));
		field2.click();
		field2.sendKeys(parameters.get("field_2"));

		wait.until(ExpectedConditions.elementToBeClickable(field3));
		field3.click();
		field3.sendKeys(parameters.get("field_3"));

		wait.until(ExpectedConditions.elementToBeClickable(field3));
		field4.click();
		field4.sendKeys(parameters.get("field_4"));

		WebElement scrolldrop = dropdDown1;
		dropdDown2.click();
		dropdDown1.sendKeys(dropDown);
		scrolldrop.sendKeys(Keys.TAB);
		customWait(2);
		WebElement scrollradio = radio1;
		radio2.click();
		radio1.sendKeys(radioOption);
		scrollradio.sendKeys(Keys.TAB);
		customWait(1);
		WebElement scrolldate = date;
		scrolldate.sendKeys(Keys.PAGE_DOWN);
		customWait(2);
		date.click();

		customWait(2);
		dateNow.click();
		customWait(1);
		dateDone.click();
		customWait(2);

		time.click();
		customWait(2);
		timeNow.click();
		timeDone.click();
		customWait(3);
		field9.click();
		field9.sendKeys(parameters.get("field_9"));

		field10.click();
		field10.sendKeys(parameters.get("field_10"));
		customWait(3);

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", newLoop);
		newLoop.click();
		customWait(3);

		WebElement scrolldropkey = loop1;
		loop2.click();
		loop1.sendKeys(dropDown);
		scrolldropkey.sendKeys(Keys.TAB);
		customWait(2);

		field2Loop.click();
		field2Loop.sendKeys(parameters.get("field2Loop"));
		customWait(3);
		return this;
	}

	public CreateDispatchPage saveDispatchData1(String save) throws IOException

	{
		try {

			saveButton.click();
			customWait(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;

	}

	public CreateDispatchPage selectApp(String appName) {
		Select dropdown = new Select(appDDL);
		dropdown.selectByVisibleText(appName);
		return this;
	}

	public CreateDispatchPage selectAssignTo(String assignTo) {
		Select dropdown = new Select(assignToDDL);
		dropdown.selectByVisibleText(assignTo);
		return this;
	}

	public CreateDispatchPage selectNewDropDown(String value) {

		WebElement scrollDrop = dropDown1;
		dropDown2.click();
		dropDown1.sendKeys(value);
		scrollDrop.sendKeys(Keys.TAB);
		return this;
	}

	public CreateDispatchPage selectValuefromAppDropdown(String appName) throws IOException, InterruptedException

	{
		(selectValueFromDropdown).click();
		Thread.sleep(5000);
		Select objSel = new Select(selectValueFromDropdown);
		objSel.selectByVisibleText(appName);

		return this;
	}

	public int Size() throws IOException, InterruptedException {
		List<WebElement> textboxes = driver.findElements(
				By.xpath("//form[@name='saveDispatchData']/div/div/div/div/div/div[1]/div/..//input[@type='text']"));

		int size = textboxes.size();
		return size;
	}

	public CreateDispatchPage typeDispatchDescription(String dispatchDesc) throws IOException, InterruptedException

	{
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// (dispatchDescription).clear();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(dispatchDescription));
		(dispatchDescription).sendKeys(dispatchDesc);
		customWait(3);
		return this;

	}

	public CreateDispatchPage typeDispatchName(String dispatchname) throws IOException

	{
		try {

			(dispatchName).clear();
			customWait(2);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(dispatchName));
			(dispatchName).sendKeys(dispatchname);
			customWait(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;

	}

	public String UserinDropDown() throws InterruptedException

	{
		String dropDownVales = "";
		(selectValueFromDropdown).click();
		Select dropdown = new Select(selectValueFromDropdown);
		List<WebElement> dd = dropdown.getOptions();
		for (int j = 1; j < dd.size(); j++) {
			String abc = dd.get(j).getText();

			dropDownVales = dropDownVales + abc + ",";
			customWait(2);
		}
		return dropDownVales.substring(0, dropDownVales.length() - 1);

	}
}
