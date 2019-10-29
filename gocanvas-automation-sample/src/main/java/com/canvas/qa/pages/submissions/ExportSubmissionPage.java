package com.canvas.qa.pages.submissions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.canvas.qa.pages.BasePage;

public class ExportSubmissionPage extends BasePage

{

	@FindBy(xpath = "//table/tbody/tr/td[2]/a")
	List<WebElement> appNames;

	String appNameXpath = "//table/tbody/tr[%d]/td[2]/a";
	@FindBy(className = "ui-datepicker-calendar")
	WebElement calendar;
	@FindBy(xpath = "//button[contains(@class, 'ui-datepicker-close')]")
	WebElement closeCal;
	@FindBy(xpath = "//i[@alt = 'Alert' and contains(@data-ot, 'CSV')]")
	WebElement CSValert;
	@FindBy(id = "export_format_csv")
	WebElement CSVbutton;

	@FindBy(id = "begin_date")
	WebElement dateBegin;

	@FindBy(id = "end_date")
	WebElement dateEnd;

	@FindBy(id = "device_id_included")
	WebElement deviceIDCheckbox;
	WebDriver driver;

	@FindBy(id = "btn_Export")
	WebElement exportBtn;
	@FindBy(xpath = "//table/tbody/tr/td[@class = 'action_control']/a")
	List<WebElement> exportButtons;
	String exportButtonXpath = "//table/tbody/tr[%d]/td[@class = 'action_control']/a";
	@FindBy(id = "export_types")
	WebElement exportTypes;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(xpath = "//*[@class = 'ui-datepicker-title']/select[@class = 'ui-datepicker-month']")
	WebElement monthDDL;
	@FindBy(id = "form_version")
	WebElement msg;
	@FindBy(xpath = "//table/tbody/tr")
	List<WebElement> rowList;
	@FindBy(linkText = "Submissions")
	WebElement submission;

	@FindBy(id = "form_version_id")
	WebElement versionDDL;

	@FindBy(xpath = "//i[@alt = 'Alert' and contains(@data-ot, 'XML')]")
	WebElement XMLalert;

	@FindBy(id = "export_format_xml")
	WebElement XMLbutton;
	@FindBy(xpath = "//*[@class = 'ui-datepicker-title']/select[@class = 'ui-datepicker-year']")
	WebElement yearDDL;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public ExportSubmissionPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public ExportSubmissionPage checkDeviceID() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", deviceIDCheckbox);

		return this;
	}

	public boolean CSVErrorIcon() {
		try {
			(CSValert).click();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public ExportSubmissionPage exportClick() {
		exportBtn.click();

		return this;
	}

	public String getMsg() throws InterruptedException {
		//Thread.sleep(2000);
		try {
			return (fluentWait(msg, driver)).getText();
		} catch (Exception e) {
			return "Failed";
		}
	}

	public void logout() throws InterruptedException {
		//Thread.sleep(5000);
		fluentWait(logout, driver).click();
	}

	public ExportSubmissionPage selectBeginDate(String date) {
		String[] dateArray = date.split(";");
		String month = dateArray[0];
		String day = dateArray[1];
		String year = dateArray[2];

		(dateBegin).click();

		Select years = new Select(yearDDL);
		years.selectByVisibleText(year);

		Select months = new Select(monthDDL);
		months.selectByVisibleText(month);

		calendar.findElement(By.linkText(day)).click();
		// (closeCal).click();
		return this;
	}

	public ExportSubmissionPage selectCSV() throws InterruptedException {
		//Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", fluentWait(CSVbutton, driver));

		return this;
	}

	public ExportSubmissionPage selectEndDate(String date) throws InterruptedException {
		String[] dateArray = date.split(";");
		String month = dateArray[0];
		String day = dateArray[1];
		String year = dateArray[2];
		(dateEnd).click();

		Select years = new Select(yearDDL);
		years.selectByVisibleText(year);

		Select months = new Select(monthDDL);
		months.selectByVisibleText(month);

		calendar.findElement(By.linkText(day)).click();
		// (closeCal).click();
		Thread.sleep(3000);
		return this;
	}

	public ExportSubmissionPage selectExportApp(String appName) {
		for (int i = 1; i <= rowList.size(); i++) {
			if (driver.findElement(By.xpath(String.format(appNameXpath, i))).getText().equals(appName)) {
				driver.findElement(By.xpath(String.format(exportButtonXpath, i))).click();
				break;
			} else
				continue;
		}
		return this;
	}

	public ExportSubmissionPage selectVersion(String v) {
		Select version = new Select(versionDDL);
		version.selectByVisibleText(v);

		return this;
	}

	public ExportSubmissionPage selectXML() throws InterruptedException {
		//Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", fluentWait(XMLbutton, driver));

		return this;
	}

	public ExportSubmissionPage submissionClick() throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", submission);
		// submission.click();
		return this;
	}

	public boolean XMLErrorIcon() {
		try {
			(XMLalert).click();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
