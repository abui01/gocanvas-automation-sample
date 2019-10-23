package com.canvas.qa.pages.dispatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import org.testng.ITestContext;

import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.profile.LoginPage;

public class DispatchPage extends BasePage {

	@FindBy(id = "advSearch")
	WebElement advanceSearchButton;

	@FindBy(id = "form_id")
	WebElement appDDL;


	@FindBy(xpath = ".//*[@id='dispatch-sample']/div[2]/div[2]/div[1]")
	WebElement appNameText;

	@FindBy(xpath = "//input[@id = 'toggle_all']//following-sibling::ins")
	WebElement checkAllCheckbox;

	String checkBoxXpath = "//a[text() = '%s']//parent::td//parent::tr//td[1]//label//ins";

	String columnHeadingXpath = "//th//a[text() = '%s']";

	String columnValueXpath = "//div[@class ='table-responsive']/table/tbody/tr[%d]/td[%d]";

	String columnValueXpath2 = "//div[@class ='table-responsive']/table/tbody/tr[%d]/td[%d]/a";

	@FindBy(xpath = ".//*[@id='completed_at_from']")
	WebElement completedAtFrom;

	@FindBy(xpath = ".//*[@id='completed_at_to']")
	WebElement completedAtTo;

	@FindBy(xpath = "//span[contains(., 'Copy')]")
	WebElement copyButton;

	@FindBy(xpath = ".//*[@id='created_at_from']")
	WebElement createAtDate;

	@FindBy(xpath = "//span[contains(.,'Delete')]")
	WebElement deleteButton;

	@FindBy(xpath = "//span[contains(., 'Delete')]")
	WebElement deleteButtonElement;

	@FindBy(xpath = ".//*[@id='dispatch-sample']//*[contains(text(),'Another App')]")
	WebElement dispatchAnotherAppText;

	@FindBy(xpath = "//table/tbody/tr/td[1]/label")
	WebElement dispatchCheckBox;

	String dispatchIDTextXpath = "//td[contains(.,'%s')]//parent::tr//td[2]//a";

	@FindBy(xpath = "//table/tbody/tr/td[3]")
	WebElement dispatchName;

	String dispatchNameTextXpath = "//td[contains(.,'%s')]";

	@FindBy(xpath = ".//*[@id='dispatch-sample']//*[contains(text(),'Step 3 App - Second Department')]")
	WebElement dispatchStep3AppText;

	String dispatchXpath = "//td[contains(.,'%s')]";

	//@FindBy(xpath = "//a[contains(.,'Download')]")
	
	@FindBy(xpath = ".//*[@id='dispatch-sample']/div[2]/div[1]//a[contains(text(),'Download')]")
	WebElement downloadAnotherButtons;

	@FindBy(xpath = "//span[contains(., 'Download')]")
	WebElement downloadButton;

	@FindBy(xpath = ".//*[@id='dispatch-sample']/div[2]/div[2]//a[contains(text(),'Download')]")
	WebElement downloadStep3Buttons;

	WebDriver driver;

	String eachRowcheckBoxXpath = "//div[@class ='table-responsive']/table/tbody/tr[%d]/td[1]/label/ins";

	@FindBy(xpath = "//input[@id='q']")
	WebElement enterValueDispatchField;

	@FindBy(xpath = "//div[@class = 'top_info']/h3")
	WebElement errorMessage;

	@FindBy(xpath = "//a[contains(., 'Export')]")
	WebElement exportButton;

	String iconXpath = "//div[contains(@id,'dispatch_submission_actions')]/ul//*[contains(.,'%s')]";

	String labelNameXpath1 = "//label[contains(.,'%s')]";

	@FindBy(xpath = "//span[contains(., 'Log Out')]")
	WebElement logOutButton;

	@FindBy(xpath = "//a[text() = 'Next']")
	WebElement nextButton;

	@FindBy(xpath = "//li[@class = 'next next_page ']//a[text() = 'Next']")
	List<WebElement> nextEnabledButton;

	@FindBy(xpath = "//input[@placeholder='Search Dispatch Fields']")
	WebElement placeHolderText;

	@FindBy(xpath = "//div[@class = 'animated fadeIn modal-content p-h-lg']//div[text() = 'Please wait...']")
	WebElement progressBar;

	@FindBy(xpath = "//span[contains(., 'Re-assign')]")
	WebElement reAssignButton;

	@FindBy(id = "reassign_to_user_id")
	WebElement reAssignUserDDL;

	String rowCheckBoxXpath = "//div[@class ='table-responsive']/table/tbody/tr[%d]/td[1]/label/input";

	@FindBy(xpath = "//div[@class ='table-responsive']/table/tbody/tr")
	List<WebElement> rowList;

	String scheduledAtElementXpath = "//div[@class ='table-responsive']/table/tbody/tr[%d]/td[10]";

	String scheduledAtXpath = "//div[@class ='table-responsive']/table/tbody/tr[%d]/td[10]/a";

	@FindBy(xpath = "//span[contains(., 'Search')]")
	WebElement searchButton;

	@FindBy(xpath = "//i[@class = 'fa fa-search text-muted']")
	WebElement searchIcon;

	@FindBy(xpath = "//h3[contains(.,'Your search did not return any results, here are some tips:')]")
	WebElement searchResult;

	@FindBy(xpath = ".//*[@id='q']")
	WebElement searchText;

	@FindBy(id = "common_search_field")
	WebElement searchTextBox;

	String statusColumnXpath = "//table[@class = 'table table-hover table-striped']//tbody//tr[%d]//td[7]";

	@FindBy(id = "status")
	WebElement statusDDL;

	@FindBy(xpath = "//button[contains(., 'Submit')]")
	WebElement submitButton;

	@FindBy(xpath = "//table[@class = 'table table-hover table-striped']//tbody//tr")
	List<WebElement> tableRows;

	String userColumnXpath = "//table[@class = 'table table-hover table-striped']//tbody//tr[%d]//td[5]";

	@FindBy(id = "user_id")
	WebElement userDDL;

	String userNameXpath = "//td[contains(.,'%s')]//parent::tr//td[5]";

	@FindBy(xpath = "//small[contains(., 'View Calendar')]")
	WebElement viewCalendar;

	@FindBy(id = "per_page_selection")
	WebElement viewDDL;

	@FindBy(xpath = "//button[contains(., 'Week')]")
	WebElement weekButton;

	@FindBy(xpath = ".//*[@class ='table-responsive']/table/tbody/tr/td[2]")
	List<WebElement> worflowlList;

	public DispatchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public DispatchPage checkRowCheckBox(int index) {
		WebElement element = driver.findElement(By.xpath(String.format(eachRowcheckBoxXpath, index)));
		clickOnHiddenElement(element, driver);
		return this;
	}

	public DispatchPage clearTextDispatchfield() throws InterruptedException {
		enterValueDispatchField.clear();
		return this;
	}

	public DispatchPage clickAdvanceSearchButton() {
		clickOnHiddenElement(advanceSearchButton, driver);
		return this;
	}

	public DispatchPage clickCheckAllCheckbox() {
		clickOnHiddenElement(checkAllCheckbox, driver);
		customWait(2);
		return this;
	}

	public DispatchPage clickCheckBox(String dispatchID) {
		WebElement element = driver.findElement(By.xpath(String.format(checkBoxXpath, dispatchID)));
		clickOnHiddenElement(element, driver);
		return this;
	}

	public DispatchPage clickColumnHeading(String columnName) {
		WebElement element = driver.findElement(By.xpath(String.format(columnHeadingXpath, columnName)));
		clickOnHiddenElement(element, driver);
		return this;
	}

	public DispatchPage clickCopyButton() {
		clickOnHiddenElement(copyButton, driver);
		driver.switchTo().alert().accept();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public DispatchPage clickDeleteButton() {
		clickOnHiddenElement(deleteButtonElement, driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.switchTo().alert().accept();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public DispatchPage clickDownloadButton() {
		try {
			clickOnHiddenElement(downloadButton, driver);
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			downloadButton.click();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public ExportDispatchesPage clickExportButton() throws InterruptedException {
		clickOnHiddenElement(exportButton, driver);
		return new ExportDispatchesPage(driver);
	}

	public LoginPage clickLogOutButton() {
		clickOnHiddenElement(logOutButton, driver);
		return new LoginPage(driver);
	}

	public DispatchPage clickOnNextEnabledButton() {
		clickOnHiddenElement(nextEnabledButton.get(0), driver);
		return this;
	}

	public DispatchPage clickReAssignButton() {
		clickOnHiddenElement(reAssignButton, driver);
		return this;
	}

	public DispatchPage clickSearchButton() {
		clickOnHiddenElement(searchButton, driver);
		customWait(3);
		return this;
	}

	public DispatchPage clickSubmitButton() {
		clickOnHiddenElement(submitButton, driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.switchTo().alert().accept();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public DispatchCalendarPage clickViewCalendar() throws InterruptedException {
		viewCalendar.click();
		WebDriverWait wait = new WebDriverWait(driver, 300);
		wait.until(ExpectedConditions.visibilityOf(weekButton));
		return new DispatchCalendarPage(driver);
	}

	public boolean defaultcompletedAtFrom(String createToDateDefault) throws InterruptedException

	{
		boolean searchText = completedAtFrom.getText().equals(createToDateDefault);
		return searchText;
	}

	public boolean defaultcompletedToFrom(String createToDateDefault) throws InterruptedException

	{
		boolean searchText = completedAtTo.getText().equals(createToDateDefault);
		return searchText;
	}

	public boolean defaultcreationAtDate(String createAtDateDefault) throws InterruptedException

	{
		boolean searchText = createAtDate.getText().equals(createAtDateDefault);
		return searchText;
	}

	public boolean defaultcreationToDate(String createToDateDefault) throws InterruptedException

	{
		boolean searchText = createAtDate.getText().equals(createToDateDefault);
		return searchText;
	}

	public String defaultPlaceHolderText() throws InterruptedException

	{
		String defaultCreateAtValue = placeHolderText.getAttribute("placeholder");
		return defaultCreateAtValue;
	}

	public boolean defaultPlaceHolderValue(String placeHoldertext) throws InterruptedException

	{
		boolean placeHolderTextValue = placeHolderText.getAttribute("placeholder").equals(placeHoldertext);
		return placeHolderTextValue;
	}

	public String defaultTextAppDropDown() throws InterruptedException

	{
		Select select = new Select(driver.findElement(By.id("form_id")));
		WebElement option = select.getFirstSelectedOption();
		String defaultAppSelectedValue = option.getText();
		return defaultAppSelectedValue;
	}

	public String defaultTextcompletedAtFrom() throws InterruptedException

	{
		String defaultCreateAtValue = completedAtFrom.getText();
		return defaultCreateAtValue;
	}

	public String defaultTextcompletedToFrom() throws InterruptedException

	{
		String defaultCreateAtValue = completedAtTo.getText();
		return defaultCreateAtValue;
	}

	public String defaultTextcreationAtDate() throws InterruptedException

	{
		String defaultCreateAtValue = createAtDate.getText();
		return defaultCreateAtValue;
	}

	public String defaultTextcreationToDate() throws InterruptedException

	{
		String defaultCreateAtValue = createAtDate.getText();
		return defaultCreateAtValue;
	}

	public String defaultTextStatusDropDown() throws InterruptedException

	{
		Select select = new Select(driver.findElement(By.id("status")));
		WebElement option = select.getFirstSelectedOption();
		String defaultStatusSelectedValue = option.getText();
		return defaultStatusSelectedValue;
	}

	public String defaultTextUserDropDown() throws InterruptedException

	{
		Select select = new Select(driver.findElement(By.id("user_id")));
		WebElement option = select.getFirstSelectedOption();
		String defaultUserSelectedValue = option.getText();
		return defaultUserSelectedValue;
	}

	public boolean defaultValueAppDropDown(String app) throws InterruptedException

	{
		Select select = new Select(driver.findElement(By.id("form_id")));
		WebElement option = select.getFirstSelectedOption();
		boolean searchText = option.getText().equals(app);
		return searchText;
	}

	public boolean defaultValueStatusDropDown(String app) throws InterruptedException

	{
		Select select = new Select(driver.findElement(By.id("status")));
		WebElement option = select.getFirstSelectedOption();
		boolean searchText = option.getText().equals(app);
		return searchText;
	}

	public boolean defaultValueUserDropDown(String user) throws InterruptedException

	{
		Select select = new Select(driver.findElement(By.id("user_id")));
		WebElement option = select.getFirstSelectedOption();
		boolean searchText = option.getText().equals(user);
		return searchText;
	}

	public DispatchPage deleteDispatch() throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", dispatchCheckBox);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		deleteButton.click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.switchTo().alert().accept();
		Thread.sleep(10000);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return this;
	}

	public boolean dispatchSearchResultMatch(String serachTexts) throws InterruptedException

	{
		Actions actions = new Actions(driver);
		actions.moveToElement(searchResult);

		boolean searchTextMatch = searchResult.getText().equals(serachTexts);
		return searchTextMatch;
	}

	public String dispatchSearchResultText() throws InterruptedException

	{
		String searchText = searchResult.getText();
		return searchText;
	}

	public void downloaDispatchAnotherButton() throws InterruptedException {
		downloadAnotherButtons.click();
		customWait(10);
	}

	public void downloaDispatchStep3Button() throws InterruptedException {
		downloadStep3Buttons.click();
		customWait(10);
	}

	public DispatchPage enterText(String Text) {
		searchText.sendKeys(Text);
		return this;
	}

	public void enterTextDispatchfield(String text) throws InterruptedException {
		clickOnHiddenElement(enterValueDispatchField, driver);
		enterValueDispatchField.sendKeys(text);
	}

	public ArrayList<String> getAppList() {
		Select dropdown = new Select(appDDL);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}

	public ArrayList<String> getColumnListValues(int columnIndex) {
		ArrayList<String> columnValues = new ArrayList<String>();
		boolean moreRows = true;
		while (moreRows) {
			for (int i = 1; i <= rowList.size(); i++) {
				if (columnIndex == 2 || columnIndex == 10) {
					List<WebElement> list = driver
							.findElements(By.xpath(String.format(columnValueXpath2, i, columnIndex)));
					if (list.size() > 0) {
						WebElement element = driver
								.findElement(By.xpath(String.format(columnValueXpath2, i, columnIndex)));
						columnValues.add((element.getText()));
					} else {
						columnValues.add("");
					}
				} else {
					WebElement element = driver.findElement(By.xpath(String.format(columnValueXpath, i, columnIndex)));
					columnValues.add((element.getText()));
				}
			}
			if (nextEnabledButton.size() > 0) {
				moreRows = true;
				clickOnHiddenElement(nextEnabledButton.get(0), driver);
			} else {
				moreRows = false;
			}

		}
		return columnValues;
	}

	public String getDispatchID(String dispatchName) {
		WebElement element = driver.findElement(By.xpath(String.format(dispatchIDTextXpath, dispatchName)));
		return element.getText();
	}

	public ArrayList<String> getDispatchValues(int size) {
		ArrayList<String> dispatchValueList = new ArrayList<String>();
		String dispatchValueXpath = "//div[@class='table-responsive']//table//tbody//tr[%d]//td[2]/a";

		for (int i = 1; i < size + 1; i++) {
			List<WebElement> getDispatchValue = driver.findElements(By.xpath(String.format(dispatchValueXpath, i)));
			for (WebElement dispatchValues : getDispatchValue) {
				dispatchValueList.add(dispatchValues.getText());
			}
		}

		return dispatchValueList;
	}

	public String getErrorMessage() {
		return errorMessage.getText();
	}
	public ArrayList<String> getReAssignUserList() {
		Select dropdown = new Select(reAssignUserDDL);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}
	public int getRowCount() {
		return rowList.size();
	}

	public ArrayList<String> getStatusList() {
		Select dropdown = new Select(statusDDL);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}

	public ArrayList<String> getUserList() {
		Select dropdown = new Select(userDDL);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}

	public String getUserName(String dispatchDesc) {
		WebElement element = driver.findElement(By.xpath(String.format(userNameXpath, dispatchDesc)));
		return element.getText();
	}

	public ArrayList<String> getWorkflowDispatchValues(int size) {

		ArrayList<String> dispatchValueList = new ArrayList<String>();
		String dispatchValueXpath = ".//*[@id='page-wrapper']/div[2]//div[2]//div//div[5]//table//tbody//tr[%d]//td[2]";

		for (int i = 1; i < size + 1; i++) {
			List<WebElement> getDispatchValue = driver.findElements(By.xpath(String.format(dispatchValueXpath, i)));
			for (WebElement dispatchValues : getDispatchValue) {
				dispatchValueList.add(dispatchValues.getText());
			}
		}
		return dispatchValueList;
	}
	public int getWorkflowListSize() {
		return worflowlList.size();
	}
	public ArrayList<String> getWorkflowValues(int size) {

		String dispatchValueXpath = ".//*[@id='page-wrapper']/div[2]//div[2]//div//div[5]//table//tbody//tr[%d]//td[2]";
		ArrayList<String> workflowValueList = new ArrayList<String>();

		for (int i = 1; i < size + 1; i++) {
			List<WebElement> getWorkflowValue = driver.findElements(By.xpath(String.format(dispatchValueXpath, i)));
			for (WebElement workflowValues : getWorkflowValue) {
				workflowValueList.add(workflowValues.getText());
			}
		}

		return workflowValueList;
	}

	public boolean isAdvancedLabelsDisplayed(String labelName) throws InterruptedException {
		return driver.findElement(By.xpath(String.format(labelNameXpath1, labelName))).isDisplayed();
	}

	public boolean isDispatchDisplayed(String dispatchNameText) {
		return dispatchName.getText().equals(dispatchNameText);
	}
	public boolean isDispatchInfoDisplayed(String info) {
		boolean status = true;
		boolean nextRow = true;
		List<WebElement> element = driver.findElements(By.xpath(String.format(dispatchXpath, info)));
		while ((status == true) && (status == false)) {
			if (element.size() == 0) {
				status = false;
				if (nextEnabledButton.size() > 0) {
					clickOnHiddenElement(nextButton, driver);
					nextRow = true;
				} else {
					nextRow = false;
				}
				continue;
			} else {
				status = true;
				break;
			}
		}
		return status;
	}

	public boolean isDispatchNameDisplayed(String dispatchNameText) {
		WebElement element = driver.findElement(By.xpath(String.format(dispatchNameTextXpath, dispatchNameText)));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", element);
		return element.isDisplayed();
	}

	public boolean isDispatchPageDisplayed() {
		return viewCalendar.isDisplayed();
	}

	public boolean isIconDisplayed(String icon) {
		WebElement element = driver.findElement(By.xpath(String.format(iconXpath, icon)));
		return element.isDisplayed();
	}

	public boolean isNextButonEnabled() {
		return (nextEnabledButton.size() > 0);
	}
	public boolean isReAssignUserDDLDisplayed() {
		return reAssignUserDDL.isDisplayed();
	}

	public boolean isStatusDisplayedInEachRow(String status) {
		boolean flag = true;
		for (int i = 1; i <= tableRows.size(); i++) {
			WebElement element = driver.findElement(By.xpath(String.format(statusColumnXpath, i)));
			flag = element.getText().equals(status);
			if (flag != true) {
				break;
			} else {
				continue;
			}
		}
		return flag;
	}

	public boolean isUserDisplayedInEachRow(String user) {
		boolean flag = true;
		for (int i = 1; i <= tableRows.size(); i++) {
			WebElement element = driver.findElement(By.xpath(String.format(userColumnXpath, i)));
			flag = element.getText().equals(user);
			if (flag != true) {
				break;
			} else {
				continue;
			}
		}
		return flag;
	}

	public DispatchPage searchDispatch(String dispatchName) throws InterruptedException {
		if (searchTextBox.getText() != null) {
			searchTextBox.clear();
		}
		searchTextBox.sendKeys(dispatchName);
		searchIcon.click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		customWait(5);
		return this;
	}

	public DispatchPage selectApp(String appName) {
		Select dropdown = new Select(appDDL);
		dropdown.selectByVisibleText(appName);
		return this;
	}

	public DispatchPage selectReAssignUser(String user) {
		Select dropdown = new Select(reAssignUserDDL);
		dropdown.selectByVisibleText(user);
		return this;
	}

	public DispatchPage selectStatus(String status) {
		Select dropdown = new Select(statusDDL);
		dropdown.selectByVisibleText(status);
		return this;
	}

	public DispatchPage selectUser(String user) {
		Select dropdown = new Select(userDDL);
		dropdown.selectByVisibleText(user);
		return this;
	}

	public DispatchPage selectView(String count) {
		Select dropdown = new Select(viewDDL);
		dropdown.selectByVisibleText(count);
		return this;
	}

	public DispatchPage verifyAdvancedSearchlabels(String step, ITestContext testContext, String message,
			String[] labelList) throws InterruptedException {

		for (int j = 0; j < labelList.length; j++) {
			boolean status = isAdvancedLabelsDisplayed(labelList[j]);
			reportLog(status, testContext.getName(), message, step + j,
					" Label with name   " + labelList[j] + " of advanced search screen is displying.");
			org.testng.Assert.assertTrue(status);
		}

		return this;
	}

	public boolean verifyAllCheckBoxChecked() {
		boolean status = true;
		for (int i = 1; i <= rowList.size(); i++) {
			WebElement element = driver.findElement(By.xpath(String.format(rowCheckBoxXpath, i)));
			if (element.isSelected()) {
				status = true;
				continue;
			} else {
				status = false;
				break;
			}
		}
		return status;
	}

	public boolean verifyAllCheckBoxUnChecked() {
		boolean status = true;
		for (int i = 1; i <= rowList.size(); i++) {
			WebElement element = driver.findElement(By.xpath(String.format(rowCheckBoxXpath, i)));
			if (element.isSelected()) {
				status = false;
				break;
			} else {
				status = true;
				continue;
			}
		}
		return status;
	}

	public boolean verifyAnotherAppText(String appNameTextDisplay) throws InterruptedException

	{
		Actions actions = new Actions(driver);
		actions.moveToElement(dispatchAnotherAppText);
		boolean appText = dispatchAnotherAppText.getText().equals(appNameTextDisplay);
		return appText;
	}

	public boolean verifyAppText(String appNameTextDisplay) throws InterruptedException

	{
		Actions actions = new Actions(driver);
		actions.moveToElement(appNameText);
		boolean appText = appNameText.getText().equals(appNameTextDisplay);
		return appText;
	}

	public boolean verifyScheduledAtAreHyperLink() {
		boolean status = true;
		for (int i = 1; i <= rowList.size(); i++) {
			List<WebElement> element = driver.findElements(By.xpath(String.format(scheduledAtXpath, i)));
			WebElement element2 = driver.findElement(By.xpath(String.format(scheduledAtElementXpath, i)));
			if (element.size() > 0) {
				status = true;
				continue;
			} else {
				if (element2.getText().equals("")) {
					status = true;
					continue;
				} else {
					status = false;
					break;
				}
			}
		}
		return status;
	}

	public boolean verifyStep3AppText(String appNameTextDisplay) throws InterruptedException

	{
		Actions actions = new Actions(driver);
		actions.moveToElement(dispatchStep3AppText);
		boolean appText = dispatchStep3AppText.getText().equals(appNameTextDisplay);
		return appText;
	}
}
