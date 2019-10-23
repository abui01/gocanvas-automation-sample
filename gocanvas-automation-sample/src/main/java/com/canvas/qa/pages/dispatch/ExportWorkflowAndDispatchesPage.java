package com.canvas.qa.pages.dispatch;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.canvas.qa.pages.BasePage;

public class ExportWorkflowAndDispatchesPage extends BasePage {

	@FindBy(id = "form")
	WebElement appsDDL;

	@FindBy(id = "form_version")
	WebElement appVersionDDL;

	@FindBy(xpath = "//span[text() = 'Cancel']")
	WebElement cancelButton;

	String dateXpath = "//a[text() = '%s']";

	WebDriver driver;

	@FindBy(id = "end_date")
	WebElement endDate;

	@FindBy(xpath = "//div[@id = 'dispatch_form_container']//button[@id = 'btn_Export']")
	WebElement exportButton;

	@FindBy(xpath = "//input[@id = 'include_submission_data']//following-sibling::ins")
	WebElement includeSubmissionCheckBox;

	@FindBy(xpath = "//input[@id = 'include_submission_data']//following-sibling::ins")
	WebElement includeSubmissionDataCheckbox;

	@FindBy(xpath = "//select[@class = 'ui-datepicker-month']")
	WebElement monthDatePicker;

	@FindBy(id = "start_date")
	WebElement startDate;

	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement toastMessage;

	@FindBy(id = "export_type")
	WebElement typeDDL;

	@FindBy(id = "form_version")
	WebElement versionDDL;

	@FindBy(xpath = "//select[@class = 'ui-datepicker-year']")
	WebElement yearDatePicker;

	public ExportWorkflowAndDispatchesPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public ExportWorkflowAndDispatchesPage clickExportButton() {
		clickOnHiddenElement(exportButton, driver);
		customWait(20);
		return this;
	}

	public ExportWorkflowAndDispatchesPage clickSubmissionCheckBox() {
		clickOnHiddenElement(includeSubmissionCheckBox, driver);
		return this;
	}

	public ArrayList<String> getAppList() {
		Select dropdown = new Select(appsDDL);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}

	public String getToastMessage() {
		return toastMessage.getText();
	}

	public ExportWorkflowAndDispatchesPage selectApp(String appName) {
		Select app = new Select(appsDDL);
		app.selectByVisibleText(appName);
		return this;
	}

	public ExportWorkflowAndDispatchesPage selectEndDate(String date, String month, String year) {
		endDate.click();
		Select monthSelector = new Select(monthDatePicker);
		Select yearSelector = new Select(yearDatePicker);
		yearSelector.selectByVisibleText(year);
		monthSelector.selectByVisibleText(month);
		WebElement dateElement = driver.findElement(By.xpath(String.format(dateXpath, date)));
		dateElement.click();
		return this;
	}

	public ExportWorkflowAndDispatchesPage selectStartDate(String date, String month, String year) {
		startDate.click();
		Select monthSelector = new Select(monthDatePicker);
		Select yearSelector = new Select(yearDatePicker);
		yearSelector.selectByVisibleText(year);
		monthSelector.selectByVisibleText(month);
		WebElement dateElement = driver.findElement(By.xpath(String.format(dateXpath, date)));
		dateElement.click();
		return this;
	}

	public ExportWorkflowAndDispatchesPage selectType(String type) {
		Select app = new Select(typeDDL);
		app.selectByVisibleText(type);
		return this;
	}

	public ExportWorkflowAndDispatchesPage selectVersion(String versionNumber) {
		Select app = new Select(versionDDL);
		app.selectByVisibleText(versionNumber);
		return this;
	}
}
