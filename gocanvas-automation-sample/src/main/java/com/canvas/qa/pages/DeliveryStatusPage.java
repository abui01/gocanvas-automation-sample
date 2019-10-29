package com.canvas.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeliveryStatusPage extends BasePage

{
	@FindBy(linkText = "Activate User")
	WebElement activate;

	@FindBy(linkText = "Delivery Status")
	WebElement delStat;
	WebDriver driver;
	@FindBy(id = "advanced_user_search_email")
	WebElement emailSearch;
	@FindBy(xpath = "//table/tbody/tr/td[1]")
	List<WebElement> emailTypes;
	@FindBy(linkText = "GoCanvas Only")
	WebElement goCanvasOnly;
	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(linkText = "Manage")
	WebElement manageButton;
	@FindBy(linkText = "More")
	WebElement more;

	@FindBy(xpath = "//table/tbody/tr/td[2]")
	List<WebElement> recipients;
	@FindBy(linkText = "Resume your own session")
	WebElement resumeSessionLink;

	@FindBy(name = "commit")
	WebElement searchButton;
	@FindBy(linkText = "Search Users")
	WebElement searchUsers;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public DeliveryStatusPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public DeliveryStatusPage activateUser(String email) throws InterruptedException {
		(searchUsers).click();
		(emailSearch).sendKeys(email);
		clickOnHiddenElement(searchButton, driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", more);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", more);
		// (more).click();
		(activate).click();
		driver.switchTo().alert().accept();

		return this;
	}

	public DeliveryStatusPage deliveryStatClick() throws InterruptedException {
		try {
			if (!goCanvasOnly.getAttribute("class").contains("active")) {
				(goCanvasOnly).click();
			}
		} catch (NoSuchElementException e) {
		}
		Thread.sleep(10000);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", delStat);
		// (delStat).click();

		return this;
	}

	public DeliveryStatusPage gotoUser(String email) throws InterruptedException {
		(searchUsers).click();
		(emailSearch).sendKeys(email);
		clickOnHiddenElement(searchButton, driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", manageButton);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", manageButton);
		// (manageButton).click();
		return this;
	}

	public boolean installEmailSent(String user) {
		int i = 0;
		for (WebElement et : emailTypes) {
			if (et.getText().contains("Installation Information")) {
				if (recipients.get(i).getText().toLowerCase().contains(user.toLowerCase())) {
					return true;
				}
			}
			i += 1;
		}
		return false;
	}

	public void logout() throws InterruptedException {
		/*
		 * WebDriverWait wait = new WebDriverWait(driver, 5);
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.
		 * className("toast")));
		 */
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", logout);
		// logout.click();
	}

	public int numActivationEmailSent(String user) {
		int i = 0;
		int n = 0;
		for (WebElement et : emailTypes) {
			if (et.getText().contains("Activation")) {
				if (recipients.get(i).getText().toLowerCase().contains(user.toLowerCase())) {
					n = n + 1;
				}
			}
			i = i + 1;
		}

		return n;
	}

	public int numDownLoadClientEmailSent(String user) {
		int i = 0;
		int n = 0;
		for (WebElement et : emailTypes) {
			if (et.getText().contains("Download Client Email")) {
				if (recipients.get(i).getText().toLowerCase().contains(user.toLowerCase())) {
					n = n + 1;
				}
			}
			i = i + 1;
		}

		return n;
	}

	public void resumeSession() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("toast")));

		resumeSessionLink.click();
	}

	public boolean setPassEmailSent(String user) {
		int i = 0;
		for (WebElement et : emailTypes) {
			if (et.getText().contains("User Invitation")) {
				if (recipients.get(i).getText().toLowerCase().contains(user.toLowerCase())) {
					return true;
				}
			}
			i += 1;
		}
		return false;
	}

}
