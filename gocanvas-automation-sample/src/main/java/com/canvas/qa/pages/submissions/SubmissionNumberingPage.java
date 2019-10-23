package com.canvas.qa.pages.submissions;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.apps.EditAppPage;

public class SubmissionNumberingPage extends BasePage {

	private WebDriver driver;

	@FindBy(xpath = "//div[@id = 'errorExplanation']/h2")
	WebElement errorMessageHeading;

	@FindBy(xpath = "//div[@id = 'errorExplanation']/p")
	WebElement errorMessageSubject;

	String errorMessageXpath = "//div[@id = 'errorExplanation']/ul/li[contains(.,'%s')]";

	@FindBy(xpath = "//input[@id = 'submission_numbering_scope_1']//parent::label/ins")
	WebElement firstRadioOption;

	@FindBy(id = "submission_numbering_label")
	WebElement labelTextBox;

	@FindBy(id = "submission_numbering_number_padding")
	WebElement lengthTextBox;

	@FindBy(xpath = "//input[@id = 'new_counter']")
	WebElement nextSubmisisonNumberTextBox;

	@FindBy(xpath = "//div[@id = 'submission_numbering_options']/div[8]/div")
	WebElement nextSubmissionNumberText;

	String numberingBasedOnXpath = "//input[@id = 'submission_numbering_scope_2']//parent::label/ins";

	@FindBy(id = "submission_numbering_prefix")
	WebElement prefixTextBox;

	@FindBy(xpath = "//input[@id = 'reset_counter']/parent::label/ins")
	WebElement resetNumberCheckBox;

	@FindBy(xpath = "//span[contains(.,'Save')]")
	WebElement saveButton;

	@FindBy(id = "submission_numbering_suffix")
	WebElement suffixTextBox;

	public SubmissionNumberingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public SubmissionNumberingPage checkResetNumberCheckbox() throws InterruptedException {
		Thread.sleep(1000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", resetNumberCheckBox);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", resetNumberCheckBox);
		Thread.sleep(1000);
		return this;
	}

	public SubmissionNumberingPage clickFirstOptionNumberingBasedOnRadioButton() throws InterruptedException {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstRadioOption);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", firstRadioOption);
		return this;
	}

	public EditAppPage clickSaveButtonWithCorrectData() {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", saveButton);
		return new EditAppPage(driver);
	}

	public SubmissionNumberingPage clickSaveButtonWithInCorrectData() throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", saveButton);
		Thread.sleep(5000);
		return this;
	}

	public SubmissionNumberingPage clickSecondOptionNumberingBasedOnRadioButton() throws InterruptedException {
		WebElement element = driver.findElement(By.xpath(numberingBasedOnXpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		return this;
	}

	public SubmissionNumberingPage enterLabel(String label) {
		if (labelTextBox.getText() != null) {
			labelTextBox.clear();
		}
		labelTextBox.sendKeys(label);
		return this;
	}

	public SubmissionNumberingPage enterLength(String length) {
		if (lengthTextBox.getText() != null) {
			lengthTextBox.clear();
		}
		lengthTextBox.sendKeys(length);
		return this;
	}

	public SubmissionNumberingPage enterNextSubmissionNumber(String submissionNumber) throws InterruptedException {

		if (nextSubmisisonNumberTextBox.getText() != null) {
			nextSubmisisonNumberTextBox.clear();
		}
		nextSubmisisonNumberTextBox.click();
		nextSubmisisonNumberTextBox.sendKeys(submissionNumber);
		Thread.sleep(5000);
		return this;
	}

	public SubmissionNumberingPage enterPrefix(String prefix) {
		if (prefixTextBox.getText() != null) {
			prefixTextBox.clear();
		}
		prefixTextBox.sendKeys(prefix);
		return this;
	}

	public SubmissionNumberingPage enterSubmissionNumberingData(String label, String length, String prefix,
			String suffix) {
		enterLabel(label);
		enterLength(length);
		enterPrefix(prefix);
		enterSuffix(suffix);
		return this;
	}

	public SubmissionNumberingPage enterSuffix(String suffix) {
		if (suffixTextBox.getText() != null) {
			suffixTextBox.clear();
		}
		suffixTextBox.sendKeys(suffix);
		return this;
	}

	public String getErrorMessageHeading() {
		return errorMessageHeading.getText();
	}

	public String getErrorMessageSubject() {
		return errorMessageSubject.getText();
	}

	public String getNextSubmissionNumberText() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextSubmissionNumberText);
		return nextSubmissionNumberText.getText();
	}

	public boolean isErrorMessageDisplayed(String message) {
		WebElement element = driver.findElement(By.xpath(String.format(errorMessageXpath, message)));
		return element.isDisplayed();
	}

	public boolean isResetNumberCheckboxSelected() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", resetNumberCheckBox);
		return resetNumberCheckBox.isSelected();
	}
}
