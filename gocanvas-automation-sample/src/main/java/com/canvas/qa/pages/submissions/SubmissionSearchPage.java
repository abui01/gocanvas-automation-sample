package com.canvas.qa.pages.submissions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import com.canvas.qa.pages.BasePage;
import com.canvas.util.FileReaderUtil;

public class SubmissionSearchPage extends BasePage {

	public static final String YAML_FILE = "Testdata/Testdata.yml";
	@FindBy(xpath = "//h1[contains(.,'Add Users')]")
	WebElement addsuerheader;

	@FindBy(xpath = "//a[contains(.,'Add Users')]")
	WebElement adduser;

	@FindBy(xpath = "//small[contains(., 'Advanced Search')]")
	WebElement advanceSearch;
	String appNameXpath = "//td[@class = 'formlabel' and contains(text(),'%s')]";
	@FindBy(id = "form_version_number")
	WebElement appVersionDDL;
	String appVersionXpath = "//td[@class = 'formlabel']//span[text() = '%s']";
	String breadCrumbXpath = "//a[text() = '%s']";
	@FindBy(xpath = "//i[contains(@class,'fa fa-times ui-dialog-titlebar-close')]")
	WebElement closeView;
	@FindBy(xpath = "//a[contains(@href,'dash=true')]")
	WebElement customizebutton;
	String dateXpath = "//td[@data-handler = 'selectDay']//a[contains(.,'%s')]";

	@FindBy(xpath = "//button[@id = 'export'][@disabled]")
	WebElement disabledExportButton;

	WebDriver driver;

	// @FindBy(xpath = "//tbody/tr[2]")
	// @FindBy(xpath = "//td[contains(.,'Submission ID:
	// 0FA3B72D-FAFE-4C68-83C3-C357CAD9C87E')]")

	// tbody/tr[2]
	// @FindBy(xpath = "//td[contains(.,'Submission ID:
	// 0FA3B72D-FAFE-4C68-83C3-C357CAD9C87E')]")
	// WebElement submissionmsg1;

	String editIconXpath = "//span[text() = '%s']//parent::dt//following-sibling::dd//a[@title = 'Edit']/i";

	@FindBy(xpath = "//button[@id = 'export'][not(@disabled)]")
	WebElement enabledExportButton;
	@FindBy(xpath = "//input[@id='end_date']")
	WebElement endDate;

	@FindBy(id = "export_format")
	WebElement exportFormatDDL;
	@FindBy(id = "group")
	WebElement groupDDL;

	@FindBy(xpath = "//h1[contains(.,'Apps')]")
	WebElement manageappheader;

	@FindBy(xpath = "//a[contains(.,'Manage Apps')]")
	WebElement manageApps;

	String messageXpath = "//h3[contains(.,'%s')]";

	@FindBy(xpath = "//select[@class = 'ui-datepicker-month']")
	WebElement monthDatePicker;

	// This is Correct one

	@FindBy(xpath = "//a[@data-original-title='View or download the PDF']")
	WebElement newPDFButton;

	@FindBy(xpath = "//div[@id = 'Second_Screen']//div[2]/div//input")
	WebElement newShortText;

	@FindBy(xpath = "//a[contains(text(),'09/10/2017 11:26 EDT')]/../..//a[@data-original-title='View or download the PDF']")
	WebElement pdfButton;

	@FindBy(xpath = "//td[@class = 'identifier']")
	List<WebElement> resultList;

	@FindBy(xpath = "//h1[contains(.,'Return On Investment')]")
	WebElement roimsg;

	String rowDateXpath = "//tr[%d]//td[@class = 'date']//a";

	String rowSubmissionIDXpath = "//tr[%d]//td[@class = 'identifier']";

	@FindBy(xpath = "//div[@class = 'top_info']/h3")
	WebElement sarchResultMsg;

	@FindAll({ @FindBy(xpath = "//tbody/tr[2]"),
			@FindBy(xpath = "//td[contains(.,'Submission ID: 0FA3B72D-FAFE-4C68-83C3-C357CAD9C87E')]") })

	WebElement sarchresultmsgresult;

	@FindBy(id = "screen_select")
	WebElement screenDDL;

	@FindBy(xpath = "//input[@name='search_value']")
	WebElement searchBox;

	@FindBy(xpath = "//span[contains(., 'Search')]")
	WebElement searchButton;

	// @FindBy(xpath = "//button[@value='Search']")
	@FindBy(xpath = ".//*[@class='fa fa-search text-muted']")
	WebElement searchIcon;

	@FindBy(xpath = "//a[contains(text(),'09/10/2017 11:26 EDT')]/../..//a[contains(text(),'Download')]")
	WebElement serachDownloadSubmisson;

	@FindBy(xpath = "//a[contains(text(),'09/10/2017 11:26 EDT')]/../..//a[contains(text(),'View')]")
	WebElement serachViewSubmisson;

	@FindBy(id = "start_date")
	WebElement startDate;

	String submissionDateXpath = "//td[@class = 'date']//a[text() = '%s']";

	@FindBy(id = "submission_id")
	WebElement submissionID;

	String submissionIDXpath = "//td[@class = 'identifier' and text() = '%s']";

	// @FindBy(linkText = "Submissions")
	@FindBy(xpath = "//span[contains(.,'Submissions')]")
	WebElement submissionLink;

	@FindBy(xpath = "//a[contains(.,'BetaTestingAPP23')]")
	WebElement submissionName;

	@FindBy(id = "submission_number")
	WebElement submissionNumber;

	String submissionNumberXpath = "//td[@class = 'submission_number' and text() = '%s']";

	String submissionTitleXpath = "//a[@title = '%s']";

	@FindBy(id = "submitter")
	WebElement submitterDDL;

	String submitterXpath = "//td[@class = 'user' and text() = '%s']";

	String textBoxXpath = "//span[text() = '%s']//parent::dt//following-sibling::dd//form//input[@type = 'text']";

	String tickXpath = "//span[text() = '%s']//parent::dt//following-sibling::dd//form//div[1]//a[contains(@id,'edit_ok')]//i";

	@FindBy(xpath = ".//*[@class ='btn btn-primary m-t-sm pull-right']")
	WebElement viewall;

	@FindBy(xpath = "//a[@data-original-title='View or download the PDF']")
	WebElement viewAndDownloadPDFButton;

	@FindBy(xpath = "//select[@class = 'ui-datepicker-year']")
	WebElement yearDatePicker;
	
	@FindBy(xpath = "//i[@class='fa fa-envelope submission-email-info fa-fw fa-lg']")
	WebElement quickLinksEnvelopeLogo;
	
	@FindBy(xpath = "//resend-email[@class='fa fa-paper-plane resend-email-ico']")
	WebElement submissionEmailResend;
	
	@FindBy(xpath = "//div[@id = 'deliveryInfo']//div[1]")
	WebElement successfullyText;
	
	@FindBy(xpath = "(//button[@type='button'])[2]")
	WebElement submissionEmailPopUp;
	
	

	public SubmissionSearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public boolean checkDownloadlink(ITestContext testContext, String submisson_find) {

		boolean successText = false;

		fluentWait(submissionLink, driver).click();
		fluentWait(submissionName, driver).click();
		fluentWait(newPDFButton, driver).click();
		//WebDriverWait wait = new WebDriverWait(driver, 15);
		//wait.until(ExpectedConditions.elementToBeClickable(serachViewSubmisson));
		customWait(5); //waits for results to load
		if (fluentWait(serachViewSubmisson, driver).isDisplayed()) {
			successText = true;
		}

		return successText;
	}

	public String checkForinvalidSearchSubmission() throws InterruptedException {

		String successText;

		fluentWait(submissionLink, driver).click();
		//Thread.sleep(10000);
		fluentWait(submissionName, driver).click();
		//Thread.sleep(10000);
		// searchbox.click();
		fluentWait(searchBox, driver).sendKeys("545");
		//Thread.sleep(10000);
		fluentWait(searchIcon, driver).click();
		//Thread.sleep(30000);
		customWait(5); //waits for results to load
		successText = fluentWait(sarchResultMsg, driver).getText();
		//Thread.sleep(10000);
		return successText;
	}

	public String checkForSubmissionID(ITestContext testContext, String search_submission_id_1)
			throws InterruptedException {

		String successText;

		fluentWait(submissionLink, driver).click();
		//Thread.sleep(10000);
		fluentWait(submissionName, driver).click();
		//Thread.sleep(10000);
		fluentWait(searchBox, driver).sendKeys(search_submission_id_1);
		//Thread.sleep(10000);
		fluentWait(searchIcon, driver).click();
		//Thread.sleep(30000);
		customWait(5); //waits for results to load
		successText = fluentWait(sarchresultmsgresult, driver).getText();
		//Thread.sleep(3000);
		return successText;
	}

	public String checkForvalidSearchSubmission(ITestContext testContext) throws IOException, InterruptedException

	{
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		fluentWait(submissionLink, driver).click();
		fluentWait(submissionName, driver).click();
		fluentWait(searchBox, driver).click();
		searchBox.sendKeys(parameters.get("Search_text"));
		fluentWait(searchIcon, driver).click();
		customWait(5); //waits for results to load
		java.util.List<WebElement> allLinks = driver.findElements(By.xpath("//tbody/tr"));

		StringBuilder sb = new StringBuilder();
		try {
			for (WebElement link : allLinks) {

				sb.append(link.getText());
				sb.append("\r\n");

			}
		} catch (Exception e) {

			e.printStackTrace();
			throw e;

		}
		return sb.toString();
	}

	public boolean checkViwlink(ITestContext testContext, String submisson_find) {

		String successText;
		WebDriverWait wait = new WebDriverWait(driver, 15);
		try {
			submissionLink.click();
			submissionName.click();
			// searchbox.click();
			// searchbox.sendKeys(submisson_find);
			// Thread.sleep(2000);
			// searchicon.click();
			// java.util.List<WebElement> allLinks =
			// driver.findElements(By.xpath("//tbody/tr[2]"));

			wait.until(ExpectedConditions.elementToBeClickable(pdfButton));
			pdfButton.click();
			// Thread.sleep(2000);
			// successText = sarchresultmsg1.getText();

			wait.until(ExpectedConditions.elementToBeClickable(serachViewSubmisson));
			serachViewSubmisson.click();

			wait.until(ExpectedConditions.elementToBeClickable(closeView));
			// Thread.sleep(6000);
			closeView.click();
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}
		return false;

		// return successText;
	}

	public SubmissionSearchPage clickAdvanceSearch() {
		advanceSearch.click();
		return this;
	}

	public SubmissionSearchPage clickAppBreadCrumb(String app) {
		WebElement element = driver.findElement(By.xpath(String.format(breadCrumbXpath, app)));
		element.click();
		return this;
	}

	public SubmissionSearchPage clickDateByRowIndex(int index) {
		WebElement element = driver.findElement(By.xpath(String.format(rowDateXpath, index)));
		clickOnHiddenElement(element, driver);
		return this;
	}

	public SubmissionSearchPage clickEditIcon(String text) {
		WebElement element = driver.findElement(By.xpath(String.format(editIconXpath, text)));
		clickOnHiddenElement(element, driver);
		return this;
	}

	public SubmissionSearchPage clickExportButton() {
		enabledExportButton.click();
		return this;
	}

	public SubmissionSearchPage clickSearch() {
		clickOnHiddenElement(searchButton, driver);
		customWait(5); //waits for results to display
		return this;
	}

	public SubmissionSearchPage clickSearchIcon() {
		searchIcon.click();
		customWait(2);
		return this;
	}

	public SubmissionSearchPage clickSubmissionTitle(String title) throws InterruptedException {
		WebElement element = driver.findElement(By.xpath(String.format(submissionTitleXpath, title)));
		clickOnHiddenElement(element, driver);
		return this;
	}

	public SubmissionSearchPage clickTickIcon(String text) {
		WebElement element = driver.findElement(By.xpath(String.format(tickXpath, text)));
		clickOnHiddenElement(element, driver);
		return this;
	}

	public SubmissionSearchPage enterSearchText(String text) {
		if (!searchBox.getAttribute("value").isEmpty()) {
			searchBox.clear();
		}
		Actions actions = new Actions(driver);
		actions.moveToElement(searchBox);
		actions.click();
		actions.sendKeys(text);
		actions.build().perform();
		// searchbox.sendKeys(text);
		return this;
	}

	public SubmissionSearchPage enterShortText(String text) {
		newShortText.sendKeys(text);
		return this;
	}

	public SubmissionSearchPage enterSubmissionID(String text) {
		submissionID.sendKeys(text);
		return this;
	}

	public SubmissionSearchPage enterSubmissionNumber(String text) {
		submissionNumber.sendKeys(text);
		return this;
	}

	public SubmissionSearchPage enterTextValue(String textType, String text) {
		WebElement element = driver.findElement(By.xpath(String.format(textBoxXpath, textType)));
		if (!element.getAttribute("value").isEmpty()) {
			element.clear();
		}
		element.sendKeys(text);
		return this;
	}

	public int getResultCount() {
		return resultList.size();
	}

	public String getSubmissionIDByRowIndex(int index) {
		WebElement element = driver.findElement(By.xpath(String.format(rowSubmissionIDXpath, index)));
		return element.getText();
	}

	public boolean isAppNameDisplayed(String message) {
		List<WebElement> elements = driver.findElements(By.xpath(String.format(appNameXpath, message)));
		return elements.size() > 0;
	}

	public boolean isAppVersionDisplayed(String message) {
		List<WebElement> elements = driver.findElements(By.xpath(String.format(appVersionXpath, message)));
		return elements.size() > 0;
	}

	public boolean isDisabledExportButtonDisplayed() {
		return disabledExportButton.isDisplayed();
	}

	public boolean isEnabledExportButtonDisplayed() {
		return enabledExportButton.isDisplayed();
	}

	public boolean isMessageDisplayed(String message) {
		WebElement element = driver.findElement(By.xpath(String.format(messageXpath, message)));
		return element.isDisplayed();
	}

	public boolean isSbmitterDisplayed(String message) {
		List<WebElement> elements = driver.findElements(By.xpath(String.format(submitterXpath, message)));
		return elements.size() > 0;
	}

	public boolean isSubmissionDateDisplayed(String message) {
		List<WebElement> elements = driver.findElements(By.xpath(String.format(submissionDateXpath, message)));
		return elements.size() > 0;
	}

	public boolean isSubmissionIDDisplayed(String message) {
		List<WebElement> elements = driver.findElements(By.xpath(String.format(submissionIDXpath, message)));
		return elements.size() > 0;
	}

	public boolean isSubmissionNumberDisplayed(String message) {
		List<WebElement> elements = driver.findElements(By.xpath(String.format(submissionNumberXpath, message)));
		return elements.size() > 0;
	}

	public boolean isViewAndDownloadPDFButtonDisplayed() {
		return viewAndDownloadPDFButton.isDisplayed();
	}

	public boolean newCheckLink(ITestContext testContext, String submisson_find) {

		boolean successText = false;

		fluentWait(submissionLink, driver).click();
		fluentWait(submissionName, driver).click();
		fluentWait(newPDFButton, driver).click();

		//WebDriverWait wait = new WebDriverWait(driver, 15);
		//wait.until(ExpectedConditions.elementToBeClickable(serachViewSubmisson));
		customWait(5); //waits for results to load
		if (fluentWait(serachViewSubmisson, driver).isDisplayed()) {
			successText = true;
		}

		return successText;
	}

	public SubmissionSearchPage selectAppVersion(String text) {
		Select dropdown = new Select(fluentWait(appVersionDDL, driver));
		dropdown.selectByVisibleText(text);
		return this;
	}
	
	public SubmissionSearchPage selectEndDate1() {
		endDate.click();
		
		return this;
	}
	
	@FindBy(id = "workflow_start_date")
	WebElement workflowStartDate;
		
	public SubmissionSearchPage selectStartDateWorkflow() {
		workflowStartDate.click();
		return this;
	}

	@FindBy(id = "workflow_end_date")
	WebElement workflowEndDate;

	public SubmissionSearchPage selectEndDateWorkflow() {
		workflowEndDate.click();
		return this;
	}

	
	public SubmissionSearchPage selectEndDate(String date, String month, String year) {
		endDate.click();
		Select monthSelector = new Select(monthDatePicker);
		Select yearSelector = new Select(yearDatePicker);
		yearSelector.selectByVisibleText(year);
		monthSelector.selectByVisibleText(month);
		WebElement dateElement = driver.findElement(By.xpath(String.format(dateXpath, date)));
		dateElement.click();
		return this;
	}

	public SubmissionSearchPage selectExportFormat(String text) {
		Select dropdown = new Select(exportFormatDDL);
		dropdown.selectByVisibleText(text);
		return this;
	}

	public SubmissionSearchPage selectGroup(String text) {
		Select dropdown = new Select(groupDDL);
		dropdown.selectByVisibleText(text);
		return this;
	}

	public SubmissionSearchPage selectScreen(String screen) {
		Select dropdown = new Select(screenDDL);
		dropdown.selectByVisibleText(screen);
		return this;
	}

	public SubmissionSearchPage selectStartDate(String date, String month, String year) {
		startDate.click();
		Select monthSelector = new Select(monthDatePicker);
		Select yearSelector = new Select(yearDatePicker);
		yearSelector.selectByVisibleText(year);
		monthSelector.selectByVisibleText(month);
		WebElement dateElement = driver.findElement(By.xpath(String.format(dateXpath, date)));
		dateElement.click();
		return this;
	}
	
	public SubmissionSearchPage selectStartDate1() {
		startDate.click();
		
		return this;
	}

	public SubmissionSearchPage selectSubmitter(String text) {
		Select dropdown = new Select(submitterDDL);
		dropdown.selectByVisibleText(text);
		return this;
	}
	
	public SubmissionSearchPage clickOnQuickLinkEnvelope() {
		fluentWait(quickLinksEnvelopeLogo, driver).click();
		return this;
	}
	
	public SubmissionSearchPage clickOnResendEmailSubmission() {
		fluentWait(submissionEmailResend,driver).click();
		return this;
	}
	
    public boolean isSuccessfullyTextDisplayed() {
	   
    	customWait(2);
		return fluentWait(successfullyText, driver).isDisplayed();
	}
    
    public SubmissionSearchPage closeSubmissionPopUp() {
    	fluentWait(submissionEmailPopUp, driver).click();
		return this;
    	
    }
    
    
}
