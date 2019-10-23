package com.canvas.qa.pages.dispatch;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;

public class SubmissionDetailsPage extends BasePage {

	@FindBy(id = "dept_name")
	WebElement departmentText;

	@FindBy(xpath = "//div[@class = 'submission_details']/div/p[3]/i")
	WebElement deviceIDText;

	WebDriver driver;

	String screenXpath = "//div[@class = 'ibox-title ']//p[text() = '%s']";

	String submissionDetailXpath = "//div[@class = 'submission_details']/div/p[%d]";

	String textValueXpath = "//span[text() = '%s']//parent::dt//following-sibling::dd";

	String textXpath = "//p[text() = '%s']//parent::div[@class = 'ibox-title ']/following-sibling::div[1]/dl/dt/span[text() = '%s']";

	public SubmissionDetailsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public String getDepartment() {
		return departmentText.getText();
	}

	public String getDeviceID() {
		return deviceIDText.getText();
	}

	public String getSubmissionDetails(int index) {
		WebElement element = driver.findElement(By.xpath(String.format(submissionDetailXpath, index)));
		return element.getText();
	}

	public String getTextValue(String text) {
		WebElement element = driver.findElement(By.xpath(String.format(textValueXpath, text)));
		return element.getText();
	}

	public boolean isScreenDisplayed(String screenName) {

		WebElement element = driver.findElement(By.xpath(String.format(screenXpath, screenName)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		return element.isDisplayed();
	}

	public boolean isTextDisplayed(String screenName, String text) {
		WebElement element = driver.findElement(By.xpath(String.format(textXpath, screenName, text)));
		return element.isDisplayed();
	}
}
