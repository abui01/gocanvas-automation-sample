package com.canvas.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReportsPage extends BasePage

{
	@FindBy(xpath = "//ul[@id = 'side-menu']/li[contains(., 'Account')]")
	WebElement account;

	@FindBy(xpath = "//span[contains(.,'Account')]")
	WebElement accountLink;

	@FindAll({ @FindBy(linkText = "Active User Report"), @FindBy(xpath = "//a[contains(@href,'reports/2')]") })
	WebElement activeUserReport;

	@FindBy(id = "form_id")
	WebElement appDDL;

	@FindAll({ @FindBy(id = "form_id"),
			@FindBy(xpath = "//*[@class = 'ui-datepicker-title']/select[@id = 'form_id']") })
	WebElement appsDDL;

	String appsTextXpath = "//a[text() = '%s']//parent::td[@class = 'useremail']//following-sibling::td[@class = 'appname']";

	String appsXpath = "//a[text() = '%s']//parent::td[@class = 'useremail']//following-sibling::td[@class = 'appname']//a[text() = '%s']";
	String assignedAppXpath = "//td[@class = 'appname']//a[contains(.,'%s')]";

	@FindBy(xpath = "//input[@value='Calculate']")
	WebElement calButton;
	@FindBy(xpath = "//*[contains(@class,'btn btn-primary btn-w-m  p-h-xm')]")
	WebElement calButtonRight;
	@FindBy(className = "ui-datepicker-calendar")
	WebElement calendar;

	@FindBy(xpath = "//a[contains(.,'Return On Investment')]")
	WebElement clickROIReportLink;
	@FindBy(id = "begin_date")
	WebElement dateBegin;

	@FindBy(id = "end_date")
	WebElement dateEnd;
	WebDriver driver;
	String emailXpath = "//td[@class = 'useremail']//a[contains(.,'%s')]";

	@FindBy(id = "btn_Export")
	WebElement exportButton;

	@FindBy(xpath = "//td[@class = 'description']")
	WebElement groupDescription;

	String groupNameXpath = "//td[@class = 'groupname']//a[contains(.,'%s')]";

	String groupTextXpath = "//a[contains(.,'%s')]//parent::td[@class = 'useremail']//following-sibling::td[@class = 'groupname']";

	String groupXpath = "//a[contains(.,'%s')]//parent::td[@class = 'useremail']//following-sibling::td[@class = 'groupname']//a[contains(.,'%s')]";

	@FindBy(xpath = "//a[contains(.,'Inactive User Report')]")
	List<WebElement> inactiveReportCA;

	@FindBy(xpath = "//a[contains(.,'Inactive User Report')]")
	WebElement inactiveUserReport;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(xpath = "//a[contains(.,'Mobile Client Version Report')]")
	List<WebElement> mcvReport;

	@FindBy(xpath = "//a[contains(.,'Mobile Client Version Report')]")
	WebElement mcvReportAdmin;

	@FindBy(xpath = "//*[@class = 'ui-datepicker-title']/select[@class = 'ui-datepicker-month']")
	WebElement monthDDL;

	@FindBy(id = "num_workers")
	WebElement noOfUser;

	@FindBy(className = "nodata")
	WebElement noReportsMsg;

	@FindBy(id = "num_days_using_canvas")
	WebElement numOfDay;

	@FindBy(id = "num_submissions")
	WebElement numOfSubmission;

	@FindBy(id = "by")
	WebElement reportByDDL;

	@FindBy(xpath = "//table/thead/tr/th")
	WebElement reportColumnVal;

	@FindBy(className = "count")
	List<WebElement> reportCounts;

	@FindBy(className = "date")
	List<WebElement> reportDates;

	@FindBy(xpath = "//a[contains(.,'Reports')]")
	WebElement reportLink;

	@FindBy(xpath = "//a[contains(.,'Reports')]")
	WebElement reportLinkInActiveUserCA;

	@FindBy(xpath = "//span[contains(.,'Reports')]")
	WebElement reportLinkInActiveUserCR;

	String reportNamexpath = "//a[contains(.,'Reports')]";

	@FindBy(xpath = "//a[contains(@href,'beta.gocanvas.com/reports')]")
	WebElement reports;

	String reportsNamexpath = "//span[contains(.,'Reports')]";

	@FindBy(className = "reportname")
	List<WebElement> reportUsernames;

	@FindBy(xpath = "//span[contains(.,'Reports')]")
	WebElement roiReport;

	@FindBy(xpath = "//a[contains(.,'Return On Investment')]")
	List<WebElement> roiReportLink;

	String table = "//table/thead/tr/th";

	String tablecol = "//table/thead";

	@FindBy(className = "toast-message")
	WebElement toast;

	@FindBy(xpath = "//a[text() = 'Users, Groups, and Apps Report']")
	WebElement usersGroupsAppsReport;

	@FindBy(xpath = "//a[text() = 'Users, Groups, and Apps Report']")
	List<WebElement> usersGroupsAppsReportList;

	String view = "//table/thead/tr/th";

	@FindBy(id = "btn_View")
	WebElement viewButton;
	@FindBy(xpath = "//button[contains(., 'View')]")
	WebElement viewButtonElement;

	@FindBy(xpath = "//*[@class = 'ui-datepicker-title']/select[@class = 'ui-datepicker-year']")
	WebElement yearDDL;

	@FindBy(id = "yearly_roi")
	WebElement yearlyRoi;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public ReportsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public ReportsPage activeUserReportClick() throws InterruptedException {
		clickOnHiddenElement(activeUserReport, driver);
		return this;
	}
	public void clickROILink() throws InterruptedException {
		clickROIReportLink.click();

	}

	public ReportsPage clickUsersGroupsAppsReport() {
		usersGroupsAppsReport.click();
		return this;
	}

	public ReportsPage clickViewButtonElement() {
		clickOnHiddenElement(viewButtonElement, driver);
		return this;
	}

	public boolean countUserUsingForm(String users) throws InterruptedException

	{
		boolean usersPopulated = noOfUser.getAttribute("value").equals(users);
		return usersPopulated;
	}

	public ReportsPage exportClick() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ui-datepicker-calendar")));

		(exportButton).click();

		return this;
	}

	public ReportsPage exportReport() throws InterruptedException {
		exportButton.click();
		Thread.sleep(3000);
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

	public ArrayList<String> getReportList() {
		Select dropdown = new Select(reportByDDL);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}

	public boolean inactiveUserReportCA() throws InterruptedException

	{

		fluentWait(accountLink, driver).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(reportNamexpath))));
		fluentWait(reportLinkInActiveUserCA, driver).click();
		return inactiveReportCA.size() > 0;
	}

	public boolean inactiveUserReportCR() throws InterruptedException

	{
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(reportsNamexpath))));
		fluentWait(reportLinkInActiveUserCR, driver).click();
		return inactiveReportCA.size() > 0;
	}

	public boolean isAppDisplayed(String email, String app) {
		if (!app.isEmpty()) {
			WebElement element = driver.findElement(By.xpath(String.format(appsXpath, email, app)));
			return element.isDisplayed();
		} else {
			WebElement element = driver.findElement(By.xpath(String.format(appsTextXpath, email)));
			return element.getText().isEmpty();
		}
	}

	public boolean isAssignedAppDisplayed(String app) {
		WebElement element = driver.findElement(By.xpath(String.format(assignedAppXpath, app)));
		return element.isDisplayed();
	}

	public boolean isEmailDisplayed(String email) {
		WebElement element = driver.findElement(By.xpath(String.format(emailXpath, email)));
		return element.isDisplayed();
	}

	public boolean isGroupDescriptionDisplayed(String description) {
		return groupDescription.getText().contains(description);
	}

	public boolean isGroupDisplayed(String email, String group) {
		if (!group.isEmpty()) {
			WebElement element = driver.findElement(By.xpath(String.format(groupXpath, email, group)));
			return element.isDisplayed();
		} else {
			WebElement element = driver.findElement(By.xpath(String.format(appsTextXpath, email)));
			return element.getText().isEmpty();
		}
	}

	public boolean isGroupNameDisplayed(String groupName) {
		WebElement element = driver.findElement(By.xpath(String.format(groupNameXpath, groupName)));
		return element.isDisplayed();
	}

	public boolean isUsersGroupsAppsReportDisplayed() {
		return usersGroupsAppsReportList.size() > 0;
	}

	public void logout() throws InterruptedException {
		Thread.sleep(5000);
		logout.click();
	}
	public ReportsPage reportsButtonClick() throws InterruptedException {
		try {
			if (!account.getAttribute("class").contains("active")) {
				(account).click();
			}
		} catch (NoSuchElementException e) {
		}
		Thread.sleep(10000);
		clickOnHiddenElement(reports, driver);
		Thread.sleep(20000);
		return this;
	}
	public boolean roiDiffrentUserReportAccess() throws InterruptedException {
		roiReport.click();
		return roiReportLink.size() > 0;
	}

	public boolean roiReportAccess() throws InterruptedException {

		account.click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(reportNamexpath))));
		reportLink.click();
		return roiReportLink.size() > 0;
	}

	public boolean roiValueAfterChange(String roiValueAfterUpdate, String userUpdatedVal) throws InterruptedException

	{
		noOfUser.clear();
		noOfUser.sendKeys(userUpdatedVal);
		calButton.click();
		boolean roiBeforeUpdatingVal = yearlyRoi.getText().equals(roiValueAfterUpdate);
		return roiBeforeUpdatingVal;
	}

	public boolean roiValueAfterClearingAllValue(String roiValueAfterClear) throws InterruptedException

	{
		noOfUser.clear();
		numOfDay.clear();
		numOfSubmission.clear();
		Actions actions = new Actions(driver);
		actions.moveToElement(calButtonRight);
		calButtonRight.click();
		boolean roiAfterClearAllValue = yearlyRoi.getText().equals(roiValueAfterClear);
		return roiAfterClearAllValue;
	}

	public boolean roiValueBeforeChange(String roiValueBeforeUpdate) throws InterruptedException {

		boolean roiBeforeUpdatingVal = yearlyRoi.getText().equals(roiValueBeforeUpdate);
		return roiBeforeUpdatingVal;
	}

	/* TC7594 */

	public ReportsPage selectApp(String appName) {
		appsDDL.click();
		appsDDL.sendKeys(appName);

		return this;
	}
	
	
	
	public ReportsPage selectBeginDate(String year, String month, String day) {
		(dateBegin).click();

		Select years = new Select(yearDDL);
		years.selectByVisibleText(year);

		Select months = new Select(monthDDL);
		months.selectByVisibleText(month);

		calendar.findElement(By.linkText(day)).click();
	;
		return this;
	}

	public ReportsPage selectBeginDate() {
		(dateBegin).click();

		return this;
	}

	public ReportsPage selectByOption(String option) {
		Select dropdown = new Select(reportByDDL);
		dropdown.selectByVisibleText(option);
		return this;
	}

	public ReportsPage selectEndDate() {
		(dateEnd).click();

		return this;
	}
	
	
	
	
	public ReportsPage selectEndDate(String year, String month, String day) {
		(dateEnd).click();

		Select years = new Select(yearDDL);
		years.selectByVisibleText(year);

		Select months = new Select(monthDDL);
		months.selectByVisibleText(month);

		calendar.findElement(By.linkText(day)).click();
		return this;
	}

	public ReportsPage selectReportApp(String appName) {
		Select dropdown = new Select(appDDL);
		dropdown.selectByVisibleText(appName);
		return this;
	}
	public boolean verifyApps(String[] apps) {
		Select app = new Select(appsDDL);
		List<WebElement> allApps = app.getOptions();

		boolean appPresent = false;
		for (WebElement s : allApps) {
			for (int i = 0; i < apps.length; i++) {
				if (s.getText().equals(apps[i]))
					appPresent = true;
			}
			if (appPresent == false)
				break;
		}
		return appPresent;
	}

	public ArrayList<String> verifyColumnNames() {
		viewButton.click();
		WebElement table_element = driver.findElement(By.xpath(String.format(tablecol)));
		ArrayList<String> appList = new ArrayList<String>();
		ArrayList<WebElement> rows = (ArrayList<WebElement>) table_element.findElements(By.xpath(String.format(table)));
		for (WebElement row : rows) {
			appList.add(row.getText());

		}
		return appList;
	}

	public boolean verifyDataDisplayed(String username, String numSub, String date) {
		boolean status = false;
		for (int i = 0; i < reportUsernames.size(); i++) {
			if (reportUsernames.get(i).getText().equals(username) && reportCounts.get(i).getText().equals(numSub)
					&& reportDates.get(i).getText().equals(date)) {
				status = true;
				break;
			} else {
				status = false;
				continue;
			}
		}

		return status;
	}

	public void verifyInactiveUserReportData() throws InterruptedException

	{
		fluentWait(accountLink, driver).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(reportNamexpath))));
		fluentWait(reportLinkInActiveUserCA, driver).click();
		inactiveUserReport.click();

	}

	public boolean verifyInvalidDate(String msg) throws NoSuchElementException {
		return (noReportsMsg).getText().equals(msg);
	}

	public boolean verifyMessage(String dateRangeMsg) throws InterruptedException

	{
		fluentWait(viewButton, driver).click();
		boolean successMessage = toast.getText().equals(dateRangeMsg);
		return successMessage;
	}

	public boolean verifyMobileClientReport() throws InterruptedException

	{
		roiReport.click();
		return mcvReport.size() > 0;
	}
	public ArrayList<String> verifyMobileClientReportAdmin() {

		account.click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(reportNamexpath))));
		reportLink.click();
		mcvReportAdmin.click();

		WebElement table_element = driver.findElement(By.xpath("//table/thead"));
		ArrayList<String> appList = new ArrayList<String>();
		ArrayList<WebElement> rows = (ArrayList<WebElement>) table_element.findElements(By.xpath(String.format(table)));
		for (WebElement row : rows) {
			appList.add(row.getText());

		}
		return appList;
	}

	public boolean verifyMobileClientReportAdmin1(String colVal) throws InterruptedException

	{
		account.click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(reportNamexpath))));
		reportLink.click();
		mcvReportAdmin.click();

		WebElement table_element = driver.findElement(By.xpath("//table/thead"));
		ArrayList<String> appList = new ArrayList<String>();
		ArrayList<WebElement> rows = (ArrayList<WebElement>) table_element.findElements(By.xpath(String.format(table)));
		for (WebElement row : rows) {
			appList.add(row.getText());
		}

		boolean roiAfterClearAllValue = appList.contains(colVal);
		return roiAfterClearAllValue;
	}

	public boolean verifyMobileClientUserReport() throws InterruptedException

	{
		return mcvReport.size() > 0;
	}

	public void verifyReportsTable(String username, String numSub, String date) throws NoSuchElementException {
		for (int i = 0; i < reportUsernames.size(); i++) {
			if (reportUsernames.get(i).getText().equals(username) && reportCounts.get(i).getText().equals(numSub)
					&& reportDates.get(i).getText().equals(date)) {
				return;
			}
		}

		throw new NoSuchElementException("Reports Row Not Found.");
	}

	public ReportsPage viewClick() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ui-datepicker-calendar")));

		(viewButton).click();

		return this;
	}

}
