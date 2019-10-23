package com.canvas.qa.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.dispatch.CreateDispatchPage;
import com.canvas.qa.pages.dispatch.DispatchDetailsPage;
import com.canvas.qa.pages.dispatch.ExportWorkflowAndDispatchesPage;
import com.canvas.qa.pages.dispatch.SubmissionDetailsPage;
import com.canvas.qa.pages.profile.LoginPage;

/**
 * @author anna.marek
 *
 */
public class WorkflowPage extends BasePage

{
	public static boolean sortedOrNot(ArrayList<String> dropDownValues) {
		for (int i = 0; i < dropDownValues.size() - 1; i++) {
			int temp = dropDownValues.get(i).toUpperCase().compareTo(dropDownValues.get(i + 1));
			if (temp > 1) {
				return false;
			}
		}
		return true;
	}

	 @FindBy(id = "advSearch")
	 WebElement advSearchButtonID;
	 
	@FindBy(xpath = "//button[@value='Search']")
	WebElement advSearchButton;

	@FindBy(id = "form_id")
	WebElement appDDL;

	String appNamexpath = "//select[@id = 'form_id']/option[text() = '%s']";
	@FindBy(id = "form_id")
	WebElement appsDDL;

	@FindBy(xpath = "//form[@id = 'search-form']/div[2]/div/select/option[3]")
	WebElement appsName;

	@FindBy(id = "advSearchCancel")
	WebElement cancelButton;

	String checkBoxXpath = "//table/tbody/tr[%d]/td[1]/label/ins";

	@FindBy(id = "completed_at_from")
	WebElement completedDateFrom;

	@FindBy(id = "completed_at_to")
	WebElement completedDateTo;

	@FindBy(xpath = "//span[contains(., 'Copy')]")
	WebElement copyButton;

	@FindBy(id = "created_at_from")
	WebElement createdDateFrom;

	@FindBy(id = "created_at_to")
	WebElement createdDateTo;

	@FindBy(xpath = "//a[contains(.,'Create Dispatch')]")
	WebElement createDispatchLink;

	String dateXpath = "//td[@data-handler = 'selectDay']//a[text() = '%s']";

	@FindBy(xpath = "//span[contains(., 'Delete')]")
	WebElement deleteButton;
	@FindBy(xpath = "//*[@id='side_nav']/div/nav/div[2]/div[3]/a")
	WebElement departmentsDDL;
	@FindBy(xpath = "//li[@class = 'nav-header']/div/ul/li")
	List<WebElement> departs;
	String dispatchIDLinkXpath = "//td[text() = '%s']//parent::tr//td[2]/a";

	String dispatchXpath = "//td[contains(.,'%s')]";
	@FindBy(xpath = "//span[contains(., 'Download')]")
	WebElement downloadButton;
	WebDriver driver;
	@FindBy(xpath = "//div[@class = 'top_info']/h3")
	WebElement errorMessage;

	@FindBy(xpath = "//a[contains(., 'Export')]")
	WebElement exportButton;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(xpath = "//span[contains(., 'Log Out')]")
	WebElement logOutButton;

	@FindBy(xpath = "//select[@class = 'ui-datepicker-month']")
	WebElement monthDatePicker;

	@FindBy(xpath = "//a[text() = 'Next']")
	WebElement nextButton;

	@FindBy(xpath = "//li[@class = 'next next_page ']//a[text() = 'Next']")
	List<WebElement> nextEnabledButton;

	@FindBy(className = "fa-users")
	WebElement reassignButton;

	@FindBy(xpath = "//span[contains(., 'Re-assign')]")
	WebElement reAssignButton;

	@FindBy(xpath = "//button[text() = 'Submit']")
	WebElement reassignForm;

	@FindBy(id = "reassign_to_user_id")
	WebElement reassignUserDDL;

	@FindBy(xpath = "//span[text() = 'Search']")
	WebElement searchButton;

	@FindBy(xpath = "//input[@placeholder = 'Search Dispatch Fields' and @class = 'form-control']")
	WebElement searchDispatchFieldTextbox;

	@FindBy(id = "common_search_field")
	List<WebElement> searchField;

	@FindBy(xpath = "//span[text() = 'Search']")
	WebElement searchForm;

	@FindBy(xpath = "//i[contains(@class,'fa fa-search text-muted')]")
	WebElement searchIcon;
	@FindBy(xpath = "//a[contains(.,'9')]")
	WebElement searchResult;

	@FindBy(xpath = "//table/tbody/tr/td/label/input")
	List<WebElement> searchResultCheckboxes;

	@FindBy(xpath = "//table/tbody/tr/td[4]")
	List<WebElement> searchResultDesc;

	@FindBy(xpath = "//table/tbody/tr/td[2]")
	List<WebElement> searchResultIDs;

	@FindBy(xpath = "//table/tbody/tr/td[7]")
	List<WebElement> searchResultStatuses;

	@FindBy(xpath = "//table/tbody/tr/td[5]")
	List<WebElement> searchResultUsers;

	@FindBy(xpath = "//h3[contains(.,'Your search did not return any results, here are some tips:')]")
	WebElement searchText;

	@FindBy(xpath = "//*[@id='side_nav']/div/nav/div[2]/div[3]/ul/li[3]/a")
	WebElement secondDepartment;

	@FindBy(id = "status")
	WebElement statusDDL;

	@FindBy(id = "status")
	WebElement statusDDList;

	@FindBy(xpath = "//form[@id = 'search-form']/div[3]/div/select/option[9]")
	WebElement statusName;
	String statusNamexpath = "//select[@id = 'status']/option[text() = '%s']";
	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement toastMessage;

	@FindBy(xpath = "//span[contains(.,'Un-assign')]")
	WebElement unassignButton;

	@FindBy(id = "user_id")
	WebElement userDDL;

	@FindBy(id = "user_id")
	WebElement userDDL_1;

	@FindBy(linkText = "Workflow & Dispatch")
	WebElement workflow;

	String workflowIDLinkXpath = "//a[text() = '%s']";

	@FindBy(xpath = "//table[@class = 'table table-hover table-striped']//tr")
	List<WebElement> workflowList;

	@FindBy(xpath = "//select[@class = 'ui-datepicker-year']")
	WebElement yearDatePicker;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public WorkflowPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		// mixedContentChecker(driver);

		javaScriptErrorChecker(driver);
	}

	public WorkflowPage advancedSearchClick() throws InterruptedException {
		try {
			scrollToElementClick(driver, advSearchButton);
		} catch (Exception e) {
			e.printStackTrace();
		}
		customWait(3); //need to wait for results to populate
		return this;
	}

	public boolean appdropDownListSortedOrNot() throws IOException

	{

		String appDropDownXpath = ".//*[@id='form_id']";
		WebElement element = driver.findElement(By.xpath(String.format(appDropDownXpath)));
		element.click();
		List<WebElement> dropDownvalues = element.findElements(By.tagName("option"));
		ArrayList<String> listValues = new ArrayList<String>();
		for (WebElement value : dropDownvalues) {
			listValues.add(value.getText());
		}
		boolean sortedOrNot = sortedOrNot(listValues);
		return sortedOrNot;

	}

	public ArrayList<String> appsDropDownList() {
		Select dropdown = new Select(appsDDL);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());

		}
		return appList;
	}

	public boolean appSearchdropDownListSortedOrNot() throws IOException

	{
		String appSearchDropDownXpath = ".//*[@id='form_id']";
		WebElement element = driver.findElement(By.xpath(String.format(appSearchDropDownXpath)));
		element.click();
		List<WebElement> dropDownvalues = element.findElements(By.tagName("option"));
		ArrayList<String> listValues = new ArrayList<String>();
		for (WebElement value : dropDownvalues) {
			listValues.add(value.getText().split(",")[0]);
		}
		boolean sortedOrNot = sortedOrNot(listValues);

		return sortedOrNot;

	}

	public WorkflowPage checkWorkflowByDesc(String desc) {
		for (int i = 0; i < searchResultDesc.size(); i++) {
			if (searchResultDesc.get(i).getText().contains(desc)) {
				WebElement element = driver.findElement(By.xpath(String.format(checkBoxXpath, i + 1)));
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", element);
				break;
			} else
				continue;
		}
		return this;
	}

	public WorkflowPage checkWorkflowByID(String id) {
		for (int i = 0; i < searchResultIDs.size(); i++) {
			if (searchResultIDs.get(i).getText().equals(id)) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", searchResultCheckboxes.get(i));
			}
		}
		return this;
	}

	public WorkflowPage clearSearchField() {
		searchDispatchFieldTextbox.clear();
		return this;
	}

	public WorkflowPage clickCancel() {
		cancelButton.click();
		return this;
	}

	public WorkflowPage clickCompleteFrom() {
		completedDateFrom.click();
		return this;
	}

	public WorkflowPage clickCompleteTo() {
		completedDateTo.click();
		return this;
	}

	public WorkflowPage clickCopyButton() {
		clickOnHiddenElement(copyButton, driver);
		driver.switchTo().alert().accept();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public CreateDispatchPage clickCreateDispatchLink() {
		clickOnHiddenElement(createDispatchLink, driver);
		return new CreateDispatchPage(driver);
	}
	
	@FindBy(xpath = "//input[@canvas-time-picker ]") 
	WebElement BirthTime;

	@FindBy(xpath = "//div[contains(.,'Date')]/div/input") 
	//@FindBy(xpath = "//input[contains(@date-style,'1')]")
	WebElement dateSelect;

	public WorkflowPage clickDate() {
		fluentWait(BirthTime, driver).click();
		Actions act = new Actions(driver);
		act.sendKeys(Keys.TAB).build().perform();
		fluentWait(dateSelect, driver).click();
		
		return this;
	}

	/*
	 * @FindBy(xpath = "//div[contains(.,'Birth')]/div/input") WebElement
	 * dateDaySelect;
	 * 
	 * public WorkflowPage clickDayDate() { dateSelect.click(); return this; }
	 */
	public WorkflowPage clickCreateFrom() {
		createdDateFrom.click();
		return this;
	}

	public WorkflowPage clickCreateTo() {
		createdDateTo.click();
		return this;
	}

	public WorkflowPage clickDeleteButton() {
		clickOnHiddenElement(deleteButton, driver);
		driver.switchTo().alert().accept();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public DispatchDetailsPage clickDispatchIDLink(String dispatchDesc) {
		if (searchField.size() > 0) {
			if (searchField.get(0).isDisplayed()) {
				searchField.get(0).clear();
				searchField.get(0).sendKeys(dispatchDesc);
				searchIcon.click();
				customWait(5);
			}
		}

		WebElement element = driver.findElement(By.xpath(String.format(dispatchIDLinkXpath, dispatchDesc)));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", element);
		clickOnHiddenElement(element, driver);
		customWait(5);
		return new DispatchDetailsPage(driver);
	}

	public WorkflowPage clickDownloadButton() {
		try {
			clickOnHiddenElement(downloadButton, driver);
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			downloadButton.click();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public ExportWorkflowAndDispatchesPage clickExportButton() throws InterruptedException {
		clickOnHiddenElement(exportButton, driver);
		return new ExportWorkflowAndDispatchesPage(driver);
	}

	public LoginPage clickLogOutButton() {
		clickOnHiddenElement(logOutButton, driver);
		return new LoginPage(driver);
	}

	public WorkflowPage clickReAssignButton() {
		clickOnHiddenElement(reAssignButton, driver);
		return this;
	}

	public WorkflowPage clickSearchButton() {
		clickOnHiddenElement(searchButton, driver);
		customWait(2);
		return this;
	}

	public SubmissionDetailsPage clickWorkflowIDLink(String dispatchID) {
		WebElement element = driver.findElement(By.xpath(String.format(workflowIDLinkXpath, dispatchID)));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", element);
		clickOnHiddenElement(element, driver);
		customWait(5);
		return new SubmissionDetailsPage(driver);
	}

	public WorkflowPage enterSearchField(String value) {
		searchDispatchFieldTextbox.clear();
		Actions actions = new Actions(driver);
		actions.moveToElement(searchDispatchFieldTextbox);
		actions.click();
		actions.sendKeys(value);
		actions.build().perform();
		// searchDispatchFieldTextbox.sendKeys(value);
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

	public String getCompleteFrom() {
		return completedDateFrom.getText();
	}

	public String getCompleteTo() {
		return completedDateTo.getText();
	}

	public String getCreateFrom() {
		return createdDateFrom.getText();
	}

	public String getCreateTo() {
		return createdDateTo.getText();
	}

	public String getErrorMessage() {
		return errorMessage.getText();
	}

	public String getSelectedApp() {
		Select dropdown = new Select(appDDL);
		return dropdown.getFirstSelectedOption().getText();
	}

	public String getSelectedStatus() {
		Select dropdown = new Select(statusDDList);
		return dropdown.getFirstSelectedOption().getText();
	}

	public String getSelectedUser() {
		Select dropdown = new Select(userDDL);
		return dropdown.getFirstSelectedOption().getText();
	}

	public ArrayList<String> getStatusList() {
		Select dropdown = new Select(statusDDList);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}

	public String getToastMessage() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(toastMessage));
		return toastMessage.getText();
	}

	public ArrayList<String> getUserList() {
		Select dropdown = new Select(userDDL);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}

	public int getWorkflowCount() {
		return workflowList.size();
	}

	public boolean isDispatchDisplayed(String dispatchDescription) throws InterruptedException {
		if (searchField.size() > 0) {
			searchField.get(0).clear();
			searchField.get(0).sendKeys(dispatchDescription);
			searchIcon.click();
			//Thread.sleep(5000);
		}
		WebElement element = driver.findElement(By.xpath(String.format(dispatchXpath, dispatchDescription)));
		return fluentWait(element, driver).isDisplayed();
	}

	public boolean isDispatchInfoDisplayed(String info) {
		boolean status = true;
		List<WebElement> element = driver.findElements(By.xpath(String.format(dispatchXpath, info)));
		while (nextEnabledButton.size() > 1) {
			if (element.size() == 0) {
				status = false;
				clickOnHiddenElement(nextButton, driver);
				continue;
			} else {
				status = true;
				break;
			}
		}

		return status;
	}

	public void logout() throws InterruptedException {
		//Thread.sleep(5000);
		fluentWait(logout, driver).click();
	}

	public WorkflowPage reassignClick() {
		//JavascriptExecutor executor = (JavascriptExecutor) driver;
		//executor.executeScript("arguments[0].click();", reassignButton);
		clickOnHiddenElement(fluentWait(reassignButton, driver), driver);
		
		return this;
	}

	public WorkflowPage reassignSubmit() {
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript("arguments[0].click();", reassignForm);
		clickOnHiddenElement(fluentWait(reassignForm, driver), driver);
		driver.switchTo().alert().accept();

		return this;
	}

	public boolean searchResult(String searchDispatchText) throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(searchResult);
		boolean searchText = searchResult.getText().equals(searchDispatchText);
		return searchText;
	}

	public WorkflowPage searchSubmit() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", searchForm);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchForm);
		// (searchForm).click();
		//Thread.sleep(10000);
		customWait(10); //wait for results to load
		return this;
	}

	public boolean searchtext(String orgMsg) throws InterruptedException {

		Actions actions = new Actions(driver);
		actions.moveToElement(searchText);

		boolean workflowSearchText = searchText.getText().equals(orgMsg);
		return workflowSearchText;

	}

	public WorkflowPage selectApp(String app) throws InterruptedException {
		Select apps = new Select(fluentWait(appDDL, driver));
		apps.selectByVisibleText(app);
		return this;
	}

	public WorkflowPage selectAppfromDDL(String appName) {
		Select dropdown = new Select(appDDL);
		dropdown.selectByVisibleText(appName);
		return this;
	}

	/*
	 * public WorkflowPage selectDate(String date, String month, String year) {
	 * Select monthSelector = new Select(monthDatePicker); Select yearSelector = new
	 * Select(yearDatePicker); yearSelector.selectByVisibleText(year);
	 * monthSelector.selectByVisibleText(month); WebElement dateElement =
	 * driver.findElement(By.xpath(String.format(dateXpath, date)));
	 * dateElement.click(); customWait(2); return this; }
	 */

	@FindBy(xpath = "//input[@name='created_at_from']")
	WebElement openFromCalendar;

	@FindBy(xpath = ".//*[@id='created_at_to']")
	WebElement openToCalendar;

	@FindBy(xpath = "//div[@class='flatpickr-months year-picker']//span[@class='fa fa-chevron-left']")
	WebElement arrowleft;

	@FindBy(xpath = "//div[@class='flatpickr-months year-picker']//span[@class='fa fa-chevron-right']")
	WebElement arrowRight;

	// span[@class='arrowUp']

	@FindBy(xpath = "//input[contains(@class,'numInput cur-year') and @aria-label='Year']")
	WebElement clickYear;

	@FindBy(xpath = "//div[@class ='flatpickr-month']")
	WebElement clickmonth1;

	@FindBy(xpath = "(//div[@class ='flatpickr-month'])")
	List<WebElement> clickmonthList;

	@FindBy(xpath = "//span[@class ='cur-month']")
	List<WebElement> clickmonthList1;

	@FindBy(xpath = "//div[@class ='flatpickr-month']")
	List<WebElement> dateElement;
	@FindBy(xpath = "//div[@class ='month-year-picker']//div[@class = 'flatpickr-months year-picker']//div[contains(@class,'numInputWrapper')]")
	WebElement clickYear1;

	public WorkflowPage clickCreateFrom1() {
		openFromCalendar.click();
		return this;
	}

	public WorkflowPage clickCreateTo1() {
		openToCalendar.click();
		return this;
	}

	public WorkflowPage clickCompleteFrom1() {
		openFromCalendar.click();
		return this;
	}

	public WorkflowPage clickCompleteTo1() {
		openToCalendar.click();
		return this;
	}

	public WorkflowPage selectDate(String year, String month, String day, int calendarNum) {
		customWait(3); //allows page to load calendar after clicking. Intermittent failures - added 5/29/19
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		fluentWait(clickmonthList.get(calendarNum),driver).click();
		customWait(3);
       
		//customWait(5); //this is the old wait time
		//method below checks whether or not the previous step executed. If not, it will try to click the calendar modal again
		if (!clickYear1.isDisplayed())
		{
			
			fluentWait(clickmonthList.get(calendarNum),driver).click();
			customWait(3);
		}
		
		String calendarYearValue = (String) jse.executeScript("return arguments[0].innerHTML", clickYear1);
		
		customWait(1);
		int actualVal = Integer.parseInt(calendarYearValue);

		int ymlVal = Integer.parseInt(year);

		if (actualVal == ymlVal) {

		} else if (actualVal < ymlVal) {

			do {
				arrowRight.click();
				actualVal++;

			} while (actualVal < ymlVal);

		} else if (actualVal > ymlVal) {

			do {
				arrowleft.click();
				actualVal--;

			} while (actualVal > ymlVal);
		}

		String monthXpath = "//div[contains(@class,'month-entry')][%s]";
		WebElement monthElement = driver.findElement(By.xpath(String.format(monthXpath, month)));
		monthElement.click();
		//customWait(5);
		customWait(3);
		
		String dayXpath = "//div[contains(@class,'flatpickr-day')]//span[contains(@aria-label, '%s')]";
		List<WebElement> dayElement = driver.findElements(By.xpath(String.format(dayXpath, day)));
		dayElement.get(calendarNum).click();

		/**
		String dayXpath = "//div[contains(@class,'flatpickr-day')]//span[contains(@aria-label, '%s')]";
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement dayElement = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(dayXpath, day))));
		dayElement.click();
		//fluentWait(dayElement, driver).click();
		**/
		//customWait(5);
		customWait(1);
		return this;
	}
	
	public WorkflowPage selectDate2(String year, String month, String day, int calendarNum, int dayNum) {
		customWait(3); //allows page to load calendar after clicking. Intermittent failures - added 5/29/19
		fluentWait(clickmonthList.get(calendarNum),driver).click();
		//customWait(5);
		customWait(3);
		//String calendarYearValue = clickYear1.getText();
		String calendarYearValue = fluentWait(clickYear1,driver).getText();
		//customWait(4);
		customWait(1);
		int actualVal = Integer.parseInt(calendarYearValue);

		int ymlVal = Integer.parseInt(year);

		if (actualVal == ymlVal) {

		} else if (actualVal < ymlVal) {

			do {
				arrowRight.click();
				actualVal++;

			} while (actualVal < ymlVal);

		} else if (actualVal > ymlVal) {

			do {
				arrowleft.click();
				actualVal--;

			} while (actualVal > ymlVal);
		}

		String monthXpath = "//div[contains(@class,'month-entry')][%s]";
		WebElement monthElement = driver.findElement(By.xpath(String.format(monthXpath, month)));
		monthElement.click();
		//customWait(5);
		customWait(3);
		
		String dayXpath = "//div[contains(@class,'flatpickr-day')]//span[contains(@aria-label, '%s')]";
		List<WebElement> dayElement = driver.findElements(By.xpath(String.format(dayXpath, day)));
		//dayElement.get(dayNum).click();
		clickEle(driver,dayElement.get(dayNum));
		
		//customWait(5);
		customWait(1);
		return this;
	}
	

	public WorkflowPage selectDepart(String departName) {
		//JavascriptExecutor executor = (JavascriptExecutor) driver;
		//executor.executeScript("arguments[0].click();", departmentsDDL);
		clickOnHiddenElement(fluentWait(departmentsDDL, driver), driver);
		fluentWait(secondDepartment, driver).click();
		/*
		 * //departmentsDDL.click(); for(WebElement depart: departs) {
		 * if(depart.getText().contains(departName)) { depart.click(); return this; } }
		 */
		return this;
	}

	public WorkflowPage selectReassignUser(String user) {
		Select users = new Select(reassignUserDDL);
		users.selectByVisibleText(user);
		return this;
	}

	public WorkflowPage selectStatus(String status) throws InterruptedException {
		Select statuses = new Select(statusDDL);
		statuses.selectByVisibleText(status);
		return this;
	}

	public WorkflowPage selectStatusbyIndex(int index) throws InterruptedException {
		Select statuses = new Select(statusDDL);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", statusDDL);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", statusDDL);

		Thread.sleep(10000);
		statuses.selectByIndex(index);

		return this;
	}

	public WorkflowPage selectStatusfromDDL(String status) {
		Select dropdown = new Select(statusDDL);
		dropdown.selectByVisibleText(status);
		return this;
	}

	public WorkflowPage selectStatusnew(String status) throws InterruptedException {
		Select statuses = new Select(statusDDL);
		/*
		 * ((JavascriptExecutor)driver).executeScript( "arguments[0].scrollIntoView();",
		 * statusDDL); JavascriptExecutor executor = (JavascriptExecutor)driver;
		 * executor.executeScript("arguments[0].click();", statusDDL);
		 * Thread.sleep(10000); WebElement statusNameNew =
		 * driver.findElement(By.xpath(String.format(statusNamexpath, status)));
		 * executor.executeScript("arguments[0].click();", statusNameNew);
		 */

		statuses.selectByVisibleText(status);

		return this;
	}

	public WorkflowPage selectStatusnew2(String status) throws InterruptedException {
		Select statuses = new Select(statusDDL);

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", statusDDL);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", statusDDL);
		//Thread.sleep(10000);
		WebElement statusNameNew = driver.findElement(By.xpath(String.format(statusNamexpath, status)));
		executor.executeScript("arguments[0].click();", fluentWait(statusNameNew, driver));

		return this;
	}

	public WorkflowPage selectUser(String user) {
		Select dropdown = new Select(userDDL);
		dropdown.selectByVisibleText(user);
		return this;
	}

	public WorkflowPage unassignClick() {
		//JavascriptExecutor executor = (JavascriptExecutor) driver;
		//executor.executeScript("arguments[0].click();", unassignButton);
		clickOnHiddenElement(fluentWait(unassignButton, driver), driver);
		driver.switchTo().alert().accept();

		return this;
	}

	public ArrayList<String> userDropDownList() {
		Select dropdown = new Select(userDDL_1);
		ArrayList<String> userList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			userList.add(element.getText());

		}
		return userList;
	}

	public boolean userdropDownListSortedOrNot() throws IOException

	{
		String userDropDownXpath = ".//*[@id='user_id']";
		WebElement element = driver.findElement(By.xpath(String.format(userDropDownXpath)));
		element.click();
		List<WebElement> dropDownvalues = element.findElements(By.tagName("option"));
		ArrayList<String> listValues = new ArrayList<String>();
		for (WebElement value : dropDownvalues) {
			listValues.add(value.getText().split(",")[0]);
		}
		boolean sortedOrNot = sortedOrNot(listValues);

		return sortedOrNot;

	}

	public boolean userSearchdropDownListSortedOrNot() throws IOException

	{

		String userDropDownXpath = ".//*[@id='user_id']";
		WebElement element = driver.findElement(By.xpath(String.format(userDropDownXpath)));
		element.click();
		List<WebElement> dropDownvalues = element.findElements(By.tagName("option"));
		ArrayList<String> listValues = new ArrayList<String>();
		for (WebElement value : dropDownvalues) {
			listValues.add(value.getText().split(",")[0]);
		}
		boolean sortedOrNot = sortedOrNot(listValues);
		return sortedOrNot;

	}

	public boolean verifyReassignDDL(String[] users) {
		Select user = new Select(reassignUserDDL);
		List<WebElement> allUsers = user.getOptions();

		boolean userPresent = false;
		for (WebElement s : allUsers) {
			for (int i = 0; i < users.length; i++) {
				if (s.getText().equals(users[i]))
					userPresent = true;
			}
			if (userPresent == false)
				break;
		}

		return userPresent;
	}

	public boolean verifyReassignedWorkflow(String id, String user) {
		try {
			for (int i = 0; i < searchResultIDs.size(); i++) {
				if (searchResultIDs.get(i).getText().equals(id)) {
					return (searchResultUsers.get(i).getText()).equals(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean verifyStatuses(String[] statuses) {
		Select status = new Select(statusDDL);
		List<WebElement> allStatuses = status.getOptions();

		boolean statusPresent = false;
		for (WebElement s : allStatuses) {
			for (int i = 0; i < statuses.length; i++) {
				if (s.getText().contains(statuses[i]))
					statusPresent = true;
			}
			if (statusPresent == false)
				break;
		}

		return statusPresent;
	}

	public boolean verifyStatusSearch(String status) {
		boolean flag = false;
		for (WebElement s : searchResultStatuses) {
			if (s.getText().contains(status)) {
				flag = true;
				break;

			} else
				flag = false;
		}

		return flag;
	}

	public boolean verifyUnassignSearch(String id) {
		for (int i = 0; i < searchResultIDs.size(); i++) {
			if (searchResultIDs.get(i).getText().equals(id)) {
				return (searchResultUsers.get(i).findElement(By.xpath("i")).getAttribute("alt")).equals("Invalid User");
			}
		}

		return false;
	}

	public WorkflowPage workflowButtonClick() throws InterruptedException {
		//JavascriptExecutor executor = (JavascriptExecutor) driver;
		//executor.executeScript("arguments[0].click();", fluentWait(workflow, driver));
		clickOnHiddenElement(fluentWait(workflow, driver), driver);
		//Thread.sleep(30000);
		//removed 30 sec sleep and waits for Export button on next page
		fluentWait(exportButton, driver);
		return this;
	}
}