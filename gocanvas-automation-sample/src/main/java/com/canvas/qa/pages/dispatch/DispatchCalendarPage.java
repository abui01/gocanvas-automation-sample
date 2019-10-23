package com.canvas.qa.pages.dispatch;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

/**
 * @author shalini.pathak This class contains objects of dispatch calendar page
 */
public class DispatchCalendarPage extends BasePage {

	@FindBy(xpath = "//a[contains(., 'Create New Dispatch')]")
	WebElement createNewDispatchButton;
	
	//@FindBy(xpath = "//a[contains(.,'Create Dispatch')]")
//	@FindBy(xpath = "//a[@class='btn btn-primary btn-w-m']")
	@FindBy(linkText = "Create Dispatch")
	WebElement createDispatchButton;
	
	@FindBy(xpath = "//button[contains(.,'Yes')]")
	WebElement clickYesButton;

	@FindBy(xpath = "//button[contains(., 'Day')]")
	WebElement dayButton;

	WebDriver driver;

	@FindBy(xpath = "//button[contains(., 'Month')]")
	WebElement monthButton;

	@FindBy(xpath = "//button[contains(@class,'fc-state-active')]")
	WebElement selectedField;

	@FindBy(xpath = "//button[contains(., 'Week')]")
	WebElement weekButton;

	public DispatchCalendarPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public DispatchCalendarViewPage clickCreateNewDispatchLink() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOf(createNewDispatchButton));
		createNewDispatchButton.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return new DispatchCalendarViewPage(driver);
	}

	
	public DispatchCalendarViewPage clickCreateDispatchLink() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(createDispatchButton));
		//clickOnHiddenElement(createDispatchButton, driver);
		//createDispatchButton.click();
		fluentWait(createDispatchButton, driver).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return new DispatchCalendarViewPage(driver);
	}
	
	
	public DispatchCalendarViewPage clickYesButtonPopUp() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOf(clickYesButton));
		clickYesButton.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		customWait(5);
		return new DispatchCalendarViewPage(driver);
	}
	
	public DispatchCalendarViewPage clickDayButton() throws InterruptedException {
		clickOnHiddenElement(dayButton, driver);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return new DispatchCalendarViewPage(driver);
	}

	public DispatchCalendarViewPage clickMonthButton() throws InterruptedException {
		clickOnHiddenElement(monthButton, driver);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return new DispatchCalendarViewPage(driver);
	}

	public DispatchCalendarViewPage clickWeekButton() throws InterruptedException {
		weekButton.click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return new DispatchCalendarViewPage(driver);
	}

	public boolean isWeekSelected() {
		return selectedField.getText().contains("Week");
	}
}
