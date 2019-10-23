package com.canvas.qa.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class SubmissionNumbering extends BasePage {

	@FindBy(xpath = "//span[contains(.,'Apps')]")
	WebElement appLinkLeftPanel;

	String AppName = "//table/tbody/tr//td[1]";

	@FindBy(xpath = "//a[contains(.,'Submission Numbering')]")
	WebElement appNameLink1;

	String appsAfterRestore = "//table/tbody/tr//td[2]";

	@FindBy(xpath = "//table/tbody/tr[1]//td[3]")
	WebElement capturedDate;

	@FindBy(id = "ids_")
	WebElement checkBox;

	@FindBy(xpath = "//label[contains(@for,'enabled')]")
	WebElement checkBoxEnable;

	@FindBy(xpath = "//*[@id='form-submission']/div[2]/table/tbody/tr[2]/td[1]/label/ins")
	WebElement checkBoxFirst;

	@FindBy(xpath = "//dt[contains(.,'New Short Text:')]/./following-sibling::dd//i[@class='fa fa-check fa-fw fa-lg confirm-icon']")
	WebElement checkCheckBox;

	@FindBy(xpath = "//dt[contains(.,'New Short Text:')]/./following-sibling::dd//i[@class='fa fa-times fa-fw fa-lg cancel-icon']")
	WebElement clickCancel;

	@FindBy(xpath = "//table/tbody/tr[2]/td[2]/a")
	WebElement clickDeleteReportetUserLink;

	@FindBy(xpath = "//table/tbody/tr[1]/td[2]/a")
	WebElement clickDeleteUserLink;

	String clickEditIcon = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Short Text:')]/../following-sibling::dd//*[@class ='btn-link']";

	@FindBy(id = "btn_Save")
	WebElement clickSave;

	@FindBy(xpath = "//table/tbody/tr[2]/td[2]")
	WebElement clicksubmissionDate;

	@FindBy(xpath = "//label[contains(.,'Current GoCanvas App (Each submission, across all users, for this App will have a unique number)')]")
	WebElement CurrentGoCanvasAppRadio;

	@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Date:')]/../following-sibling::dd//*[@class ='editable']")
	WebElement dateDeafultValue;

	@FindBy(xpath = "//table/tbody/tr[1]/td[2]/a")
	WebElement dateTimeClick;

	@FindBy(xpath = "//table/tbody/tr[1]/td[1]/a")
	WebElement dateTimeLick;

	@FindBy(xpath = "//table/tbody/tr[1]//*[@class ='date']")
	WebElement dateTimeLickReporter;

	@FindBy(xpath = "//button[@id='delete']")
	WebElement deleteButtonText;

	@FindBy(xpath = "//*[contains(text(),'Deleted Submissions')]")
	WebElement deleteSubmisson;

	private WebDriver driver;

	String editable = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'Short Text:')]/../following-sibling::dd//*[@class ='editable']";

	@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Date:')]/../following-sibling::dd//*[@class ='btn-link']")
	WebElement editableIcon;

	@FindBy(xpath = "//i[@class='fa fa-edit fa-fw fa-lg']")
	WebElement editButton;

	@FindBy(xpath = "//i[@class='fa fa-edit fa-fw fa-lg']")
	WebElement editButtonReporter;

	String editExistingData = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Short Text:')]/../following-sibling::dd//input[@type='text']";

	@FindBy(xpath = "//i[@class='fa fa-edit fa-fw fa-lg']")
	WebElement editIcon;

	@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Date:')]/../following-sibling::dd//*[@class ='btn-link']")
	List<WebElement> editIconDateField;

	@FindBy(xpath = ".//*[@title='Edit']")
	WebElement editIconField;

	@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Number:')]/../following-sibling::dd//*[@class ='btn-link']")
	List<WebElement> editIconNumField;

	@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Short Text:')]/../following-sibling::dd//*[@class ='btn-link']")
	List<WebElement> editIconShortField;

	@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Time:')]/../following-sibling::dd//*[@class ='btn-link']")
	List<WebElement> editIconTimeField;

	@FindBy(xpath = "//dt[contains(.,'New Short Text:')]/./following-sibling::dd//i[@class='fa fa-edit fa-fw fa-lg']")
	WebElement editText;

	@FindBy(xpath = "//label[@class='icheckbox']")
	WebElement enableSubmissionCheckbox;

	@FindBy(xpath = "//label[contains(.,'Enable Submission Numbering')]")
	WebElement enableSubmissionNumebringCheckbox;

	@FindBy(xpath = "//span[contains(.,'New Short Text:')]/../following-sibling::dd//input[@name='data']")
	WebElement enterInput;
	@FindBy(xpath = ".//*[@id='errorExplanation']/ul")
	WebElement errorMssg;

	// WebElement table_element =
	// driver.findElement(By.xpath(String.format(table1)));

	@FindBy(xpath = "//table/tbody/tr[1]//td[1]/label/ins")
	WebElement firstSubmissonCheckBox;

	@FindBy(xpath = ".//*[@id='footer']/div")
	WebElement footerLink;

	@FindBy(id = "submission_numbering_label")
	WebElement labelDefaultValue;

	@FindBy(xpath = "//a[@class='btn btn-danger btn-sm']")
	WebElement manageButton;

	@FindBy(xpath = "//*[@class= 'next next_page disabled']//a")
	WebElement nextDisabled;

	@FindBy(id = "submission_numbering_prefix")
	WebElement prefix;

	@FindBy(xpath = "//table/tbody/tr[1]//button[@type='submit']")
	WebElement restoreApp;

	@FindBy(id = "subit")
	WebElement restoreSelection;

	@FindBy(xpath = ".//*[@class= 'fa-4x revision-count']//strong")
	WebElement revisionSubmisson;

	@FindBy(xpath = ".//*[@title='Save']")
	WebElement saveValue;

	String saveValueShortText = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Short Text:')]/../following-sibling::dd//*[@title='Save']";
	@FindBy(xpath = "//input[@value='Search']")
	WebElement searchButton;
	@FindBy(id = "advanced_user_search_email")
	WebElement searchUser;

	@FindBy(xpath = "//th[@class='count']")
	List<WebElement> submissionDateCheckBox;

	String submissionID = "//table/tbody/tr//td[4]";

	String submissionIDAfterRestore = "//table/tbody/tr//td[5]";

	@FindBy(xpath = "//th[@class='count']/label/ins")
	WebElement submissionLeftDateCheckBox;

	@FindBy(xpath = "//span[contains(.,'Submissions')]")
	WebElement submissionLink;

	@FindBy(xpath = "//strong[contains(.,'Submission Number')]")
	List<WebElement> submissionNoIcon;

	@FindBy(xpath = "//strong[contains(.,'Submission Number')]/..//a[contains(.,'Settings')]")

	WebElement submissionNoIcon1;

	@FindBy(id = "submission_numbering_number_padding")
	WebElement submissionNumberDefaultVaue;

	String submisson = "//h1[contains(.,'Submissions')]";

	@FindBy(xpath = "//table/tbody/tr[1]/td[2]/a")
	WebElement submissonRowFirst;

	@FindBy(id = "submission_numbering_suffix")
	WebElement suffix;

	String table = "//table/tbody/tr/td[preceding-sibling::td=//input[@class='checkbox'] and following-sibling::td[2]]";

	String table1 = "//table/tbody/tr[1]";

	@FindBy(className = "toast-message")
	WebElement toast;

	String toastMsg = "//div[@class='toast-message']";
	@FindBy(xpath = "//input[@type='text']")
	WebElement updateDateDefaultValue;
	String xpathAuthorColumn = "//table/tbody/tr[1]/td[4]";

	String xpathChangeColumn = "//table/tbody/tr[1]/td[2]";

	String xpathSubmissionRevision = ".//*[@class= 'fa-4x revision-count']//strong";

	public SubmissionNumbering(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public boolean appWithinInRange(Date actualDate, Date startDate, Date endDate)

	{
		return actualDate.getTime() >= startDate.getTime() && actualDate.getTime() <= endDate.getTime();
	}

	public SubmissionNumbering clickCheckBox() throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", enableSubmissionCheckbox);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return this;
	}

	public void clickOnLinkInSubmissionPage() throws InterruptedException {
		clickOnHiddenElement(clickDeleteUserLink, driver);
	}

	public void clickRevisionNumber() {

		revisionSubmisson.click();
	}

	public void clickSubmissionAppLink() throws InterruptedException {

		appLinkLeftPanel.click();
		Actions actions = new Actions(driver);
		actions.moveToElement(appNameLink1);
		fluentWait(appNameLink1, driver).click();
	}
	public void clickSubmissionLink() throws InterruptedException {

		clickOnHiddenElement(submissionLink, driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(submisson))));
	}
	public SubmissionNumbering clickSubmissionNumber() throws InterruptedException {
		fluentWait(submissionNoIcon1, driver).click();
		return this;
	}

	public void dateTimeLink() throws InterruptedException {
		clickOnHiddenElement(dateTimeLick, driver);
	}

	public boolean deleteLogoText(String logo_txt) throws InterruptedException {
		clickOnHiddenElement(clickDeleteUserLink, driver);
		customWait(10);
		clickOnHiddenElement(submissionLeftDateCheckBox, driver);
		boolean deleteLogoText = deleteButtonText.getText().equals(logo_txt);
		return deleteLogoText;
	}

	public void deleteSubmisson() throws InterruptedException {
		deleteButtonText.click();
		Alert alert = driver.switchTo().alert();
		alert.accept();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Log Out")));
	}

	public void editText(String value) {

		editText.click();
		enterInput.clear();
		enterInput.sendKeys(value);
		checkCheckBox.click();
		customWait(2);

	}

	public void enterUpdateSubmissionDate() throws InterruptedException {
		clickOnHiddenElement(editableIcon, driver);
		updateDateDefaultValue.clear();
	}

	public String getAppName() {
		String xpathAppAndVersionName = "//table/tbody/tr[1]/td[6]";
		String completeAppName = driver.findElement(By.xpath(String.format(xpathAppAndVersionName))).getText();
		String appName = completeAppName.substring(0, 8);
		return appName;
	}
	public String getDefaultText() {

		editText.click();
		enterInput.click();
		String defaultShortText = enterInput.getAttribute("value");
		clickCancel.click();
		return defaultShortText;
	}
	public String getRevisionNumber() {
		String submissonRevison = driver.findElement(By.xpath(String.format(xpathSubmissionRevision))).getText();
		return submissonRevison;
	}

	public String getSubmissionID() {
		String xpathSubmisson = "//table/tbody/tr[1]/td[5]";
		String submissonId = driver.findElement(By.xpath(String.format(xpathSubmisson))).getText();
		return submissonId;
	}

	public String getunDestroyAppName(String text) {
		String xpathForAppName = "//a[contains(.,'" + text + "')]";
		String appname = driver.findElement(By.xpath(String.format(xpathForAppName))).getText();
		return appname;

	}

	public String getunDestroyAppStatus(String text) {
		String xpathAppStatusNumber = "//a[contains(.,'" + text + "')]/../following-sibling::td[1]";
		String Version = driver.findElement(By.xpath(String.format(xpathAppStatusNumber))).getText();
		String appStatus = Version.substring(0, 9);
		return appStatus;

	}

	public String getunDestroyVersion(String text) {
		String xpathAppVersionNumber = "//a[contains(.,'" + text + "')]/../following-sibling::td[1]";
		String Version = driver.findElement(By.xpath(String.format(xpathAppVersionNumber))).getText();
		String version = Version.substring(18, 19);
		return version;

	}

	public String getVersionName() {
		String xpathAppAndVersionName = "//table/tbody/tr[1]/td[6]";
		String completeAppName = driver.findElement(By.xpath(String.format(xpathAppAndVersionName))).getText();
		String version = completeAppName.substring(18, 19);
		return version;
	}

	public boolean isAppDisplayInDestroyedAppsPageAfterRestore(String appName) throws InterruptedException

	{
		List<WebElement> cell = driver.findElements(By.xpath(String.format(AppName)));
		for (WebElement c : cell) {
			String cellvalue = c.getText();
			if (cellvalue.equalsIgnoreCase(appName)) {
				return true;
			}
		}
		return false;
	}

	public boolean isAppNameDisplayInTable(String text) {
		String xpathLabel = "//td[contains(.,'" + text + "')]";
		boolean updatedData = driver.findElement(By.xpath((xpathLabel))).getText().equals(text);
		return updatedData;
	}

	public boolean isAppVersioneDisplayInTable(String text, String version) {
		String xpathLabel = "//td[contains(.,'" + text + "')]/following-sibling::td[1]";
		boolean updatedData = driver.findElement(By.xpath((xpathLabel))).getText().equals(version);
		return updatedData;
	}

	public boolean isColumnValueDisplayed(String text) {

		String xpathLabel = "//td[text() = '" + text + "']";
		boolean updatedVal = driver.findElement(By.xpath((xpathLabel))).getText().equals(text);
		return updatedVal;
	}

	public boolean isDateColumnValueDisplayed(String text) {
		String xpathLabel = "//td[text() = '" + text + "']";

		boolean updatedVal = driver.findElement(By.xpath((xpathLabel))).getText().equals(text);
		return updatedVal;
	}

	public boolean isDefaultCheckBox() throws InterruptedException {

		if (checkBoxEnable.isSelected())
			return true;
		else
			return false;
	}

	public boolean isEditButtonDisplay() throws InterruptedException {
		dateTimeClick.click();
		return editIconShortField.size() > 0 && editIconNumField.size() > 0;
	}

	public boolean isEditIconDisplay() throws InterruptedException {
		clicksubmissionDate.click();
		return editIconDateField.size() > 0 && editIconTimeField.size() > 0;
	}

	public boolean isSubmissionColumnValueDisplayed(String text) {
		String xpathLabel = "//td[text() = '" + text + " ']";
		boolean updatedVal = driver.findElement(By.xpath(xpathLabel)).getText().equals(text);
		return updatedVal;
	}

	// @FindBy(id = "advanced_user_search_email")
	// WebElement searchUser;
	// @FindBy(xpath = "//input[@value='Search']")
	// WebElement searchButton;
	// @FindBy(xpath = "//span[contains(.,'Manage')]")
	// WebElement manageButton;

	public boolean isSubmissionDateCheckBoxDisplayDesigner() throws InterruptedException {
		return submissionDateCheckBox.size() > 0;
	}

	// @FindBy(xpath = "//*[contains(text(),'Deleted Submissions')]")
	// WebElement deleteSubmisson;

	// public void verifyDeletedSubmissonsLinkInBottom() throws
	// InterruptedException {
	//
	// Actions actions = new Actions(driver);
	// actions.moveToElement(deleteSubmisson);
	// deleteSubmisson.click();
	// }

	// @FindBy(xpath = "//table/tbody/tr[1]//td[3]")
	// WebElement capturedDate;

	public boolean isSubmissionDateCheckBoxDisplayUser() throws InterruptedException {
		clickDeleteReportetUserLink.click();
		return submissionDateCheckBox.size() > 0;
	}

	public boolean isSubmissionDisplayAfterDelete(String submissonId) throws InterruptedException

	{
		List<WebElement> cell = driver.findElements(By.xpath(String.format(submissionID)));
		for (WebElement c : cell) {
			String cellvalue = c.getText();
			if (cellvalue.equalsIgnoreCase(submissonId)) {
				return true;

			}
		}
		return false;
	}

	public boolean isSubmissionNoIConDisplay() throws InterruptedException {
		return submissionNoIcon.size() > 0;
	}
	public void refreshPage() {

		String CurrentUrl = driver.getCurrentUrl();
		driver.get(CurrentUrl);
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

	// public void searchUserAndManage(String userName) throws
	// InterruptedException {
	// searchUser.sendKeys(userName);
	// Actions actions = new Actions(driver);
	// actions.moveToElement(searchButton);
	// fluentWait(searchButton, driver).click();
	// fluentWait(manageButton, driver).click();
	// fluentWait(appLinkLeftPanel, driver).click();
	// }

	public void restoreApps() throws InterruptedException

	{
		restoreApp.click();
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(toastMsg))));
	}

	public void restoreSubmisson() throws InterruptedException

	{
		checkBox.click();
		restoreSelection.click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(toastMsg))));
	}

	public void saveEditedValue(String updated_txt) throws InterruptedException {

		clickOnHiddenElement(driver.findElement(By.xpath(String.format(clickEditIcon))), driver);
		driver.findElement(By.xpath(String.format(editExistingData))).clear();
		driver.findElement(By.xpath(String.format(editExistingData))).sendKeys(updated_txt);
		clickOnHiddenElement(driver.findElement(By.xpath(String.format(saveValueShortText))), driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(editable))));
	}

	// public boolean withinRange(Date actualDate, Date startDate, Date endDate)
	// throws ParseException
	//
	// {
	//
	// >>>>>>> develop

	public SubmissionNumbering saveStatus() throws InterruptedException {
		clickOnHiddenElement(clickSave, driver);
		return this;
	}

	public boolean saveSubmissionDate(String date) throws InterruptedException {
		updateDateDefaultValue.sendKeys(date);
		clickOnHiddenElement(saveValue, driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Date:')]/../following-sibling::dd//*[@class ='editable']")));
		boolean updatedDate = dateDeafultValue.getText().equals(date);
		return updatedDate;
	}

	public void searchUserAndManage(String userName) throws InterruptedException {
		searchUser.sendKeys(userName);
		Actions actions = new Actions(driver);
		actions.moveToElement(searchButton);
		clickOnHiddenElement(searchButton, driver);
		clickOnHiddenElement(manageButton, driver);
		clickOnHiddenElement(appLinkLeftPanel, driver);
	}

	public void selectFirstSubmisson() throws InterruptedException {
		clickOnHiddenElement(firstSubmissonCheckBox, driver);
	}

	public SubmissionNumbering unSelectCheckBox(String step, ITestContext testContext) throws InterruptedException {

		clickOnHiddenElement(clickSave, driver);
		reportLog(true, testContext.getName(), "Check box is selected", step, "Check box is selected");
		return this;
	}

	public boolean verfiyLogoText(String logo_txt) throws InterruptedException {

		clickOnHiddenElement(submissonRowFirst, driver);
		clickOnHiddenElement(checkBoxFirst, driver);
		boolean deleteLogoText = deleteButtonText.getText().equals(logo_txt);
		return deleteLogoText;
	}

	public boolean verfiyRevisionNumber(String revision) {

		boolean submissionRevision = revisionSubmisson.getText().equals(revision);
		return submissionRevision;

	}

	public boolean verfiyUpdatedRevisionNumber(int revision, int updatedrevision) {

		if (updatedrevision == revision + 1) {
			return true;
		}
		return false;

	}

	public boolean verfiyValueInChangeColumn(String changeVal) {

		String changeColValue = driver.findElement(By.xpath(String.format(xpathChangeColumn))).getText();
		boolean ChangeColFirstVal = changeColValue.equals(changeVal);
		return ChangeColFirstVal;
	}

	public boolean verifyAppStatusAfterRestore(String text, String statusapp) {
		String statusApp = "//a[contains(.,'" + text + "')]/../following-sibling::td//span[contains(.,'Published')]";
		boolean appStatus = driver.findElement(By.xpath((statusApp))).getText().equals(statusapp);
		return appStatus;
	}

	public boolean verifyAppVersionAfterRestore(String appName, String version) {
		String versionApp = "//a[contains(.,'" + appName + "')]//../following-sibling::td/small";
		boolean versions = driver.findElement(By.xpath((versionApp))).getText().substring(8, 9).equals(version);

		return versions;
	}

	public boolean verifyAuthorName(String authorName) {
		String authorColValue = driver.findElement(By.xpath(String.format(xpathAuthorColumn))).getText();
		boolean authorColVal = authorColValue.equals(authorName);
		return authorColVal;

	}

	public boolean verifyCheckBoxDefaultStatus(ITestContext testContext) throws InterruptedException {

		boolean checkBoxDefaultValue = isDefaultCheckBox();
		return checkBoxDefaultValue;
	}

	public boolean verifyDefaultValueofCheckBox() throws InterruptedException {

		if (enableSubmissionNumebringCheckbox.isSelected())
			return true;
		else
			return false;
	}

	public boolean verifyDefaultValueofRadio() throws InterruptedException {
		if (CurrentGoCanvasAppRadio.isSelected())
			return true;
		else
			return false;
	}

	public ArrayList<String> verifyDeletedSubmisson() {

		WebElement table_element = driver.findElement(By.xpath(String.format(table1)));
		ArrayList<String> appList = new ArrayList<String>();
		ArrayList<WebElement> rows = (ArrayList<WebElement>) table_element.findElements(By.xpath(String.format(table)));
		for (WebElement row : rows) {
			appList.add(row.getText());

		}
		return appList;
	}

	public void verifyDeletedSubmissonsLinkInBottom() throws InterruptedException {

		Actions actions = new Actions(driver);
		actions.moveToElement(deleteSubmisson);
		deleteSubmisson.click();
	}

	public boolean verifyEditButtons() throws InterruptedException {

		dateTimeLick.click();
		return editButton.isDisplayed();

	}

	public boolean verifyEditButtonsReporter() throws InterruptedException {
		dateTimeLickReporter.click();
		return editButtonReporter.isDisplayed();
	}

	public boolean verifyEditIcon() throws InterruptedException {

		dateTimeLickReporter.click();
		return editIcon.isDisplayed();
	}

	public boolean verifyErrorMessage(String labelVal, String submissionVal, String otherPrefixvalue,
			String suffixvalue, String error_msg) throws InterruptedException {

		labelDefaultValue.clear();
		labelDefaultValue.sendKeys(labelVal);
		submissionNumberDefaultVaue.clear();
		submissionNumberDefaultVaue.sendKeys(submissionVal);
		prefix.clear();
		prefix.sendKeys(otherPrefixvalue);
		suffix.clear();
		suffix.sendKeys(suffixvalue);
		clickSave.click();
		boolean errorMessage = errorMssg.getText().equals(error_msg);
		return errorMessage;
	}

	public boolean verifyLabelValue(String defaultText) throws InterruptedException {
		boolean labelValue = labelDefaultValue.getAttribute("value").equals(defaultText);
		return labelValue;
	}

	public void verifyReenableSubmissionNumbering() throws InterruptedException {

		enableSubmissionNumebringCheckbox.click();
		clickSave.click();
	}

	public void verifyResetSubmissonNumberingUpdate(String labelVal, String submissionVal) throws InterruptedException {

		labelDefaultValue.clear();
		labelDefaultValue.sendKeys(labelVal);
		submissionNumberDefaultVaue.clear();
		submissionNumberDefaultVaue.sendKeys(submissionVal);
		clickSave.click();
	}

	public boolean verifyRestoreAppInAppsPage(String apps) throws InterruptedException {
		List<WebElement> cell = driver.findElements(By.xpath(String.format(appsAfterRestore)));
		for (WebElement c : cell) {
			String cellvalue = c.getText();
			if (cellvalue.equalsIgnoreCase(apps)) {
				return true;
			}
		}
		return false;
	}

	public boolean verifyRestoreSubmisson(String submissonID) throws InterruptedException {
		List<WebElement> cell = driver.findElements(By.xpath(String.format(submissionIDAfterRestore)));
		for (WebElement c : cell) {
			String cellvalue = c.getText();
			if (cellvalue.equalsIgnoreCase(submissonID)) {
				return true;
			}
		}
		return false;
	}

	public boolean verifySubmissionDate(String submisson_date) throws InterruptedException {

		clickDeleteUserLink.click();
		boolean SubmissionDate = dateTimeLick.getText().equals(submisson_date);
		return SubmissionDate;
	}

	public boolean verifySubmissionValue(String defaultSubmissionValue) throws InterruptedException {
		boolean submissionValue = submissionNumberDefaultVaue.getAttribute("value").equals(defaultSubmissionValue);
		return submissionValue;
	}

	public boolean verifySubmissonNumberingCheckBox() throws InterruptedException {

		if (enableSubmissionNumebringCheckbox.isSelected())
			return true;
		else
			return false;
	}

	public boolean verifySubmissonNumberingDisable(String disable_submissions_msg) throws InterruptedException {

		enableSubmissionNumebringCheckbox.click();
		clickSave.click();
		boolean disableSubmissionMessage = toast.getText().equals(disable_submissions_msg);
		return disableSubmissionMessage;
	}

	public boolean verifySubmissonNumberingUpdate(String labelVal, String submissionVal, String success_msg)
			throws InterruptedException {

		labelDefaultValue.clear();
		labelDefaultValue.sendKeys(labelVal);
		submissionNumberDefaultVaue.clear();
		submissionNumberDefaultVaue.sendKeys(submissionVal);
		clickSave.click();
		boolean successMessage = toast.getText().equals(success_msg);
		return successMessage;
	}

	public boolean withinRange(Date actualDate, Date startDate, Date endDate) throws ParseException

	{
		String revisionSubmissionDate = capturedDate.getText();
		Date SubmissionrevisionDate = new SimpleDateFormat("MM/dd/yyyy hh:mm z").parse(revisionSubmissionDate);
		return SubmissionrevisionDate.getTime() >= startDate.getTime()
				&& SubmissionrevisionDate.getTime() <= endDate.getTime();
	}
}
