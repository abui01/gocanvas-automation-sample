package com.canvas.qa.pages.profile;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

/**
 * @author taukeer.ahmad
 *
 */
public class EditInformationPage extends BasePage

{

	@FindBy(name = "commit")
	WebElement commit;

	@FindBy(id = "user_default_location")
	WebElement defaultLocation;

	// @FindBy(linkText = "Profile")
	// WebElement profile;

	WebDriver driver;

	@FindBy(linkText = "Edit")
	WebElement edit;

	@FindBy(xpath = "//h1")
	WebElement header;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(xpath = ".//label[contains(.,'Name')]/..")
	WebElement name;

	// @FindBy(linkText = "Profile")
	@FindBy(xpath = "//span[contains(.,'Profile')]")
	WebElement profile;

	@FindBy(id = "user_country")
	WebElement userCountry;

	@FindBy(id = "user_email")
	WebElement userEmail;

	@FindBy(id = "user_first_name")
	WebElement userFirstName;

	@FindBy(id = "user_time_zone")
	WebElement userTimeZone;

	@FindBy(xpath = ".//label[contains(.,'Email')]/..")
	WebElement verifyEmail;

	@FindBy(id = ".//label[contains(.,'Name')]/..")
	WebElement verifyNameChange;

	@FindBy(xpath = ".//label[contains(.,'Time Zone')]/..")
	WebElement verifyTimeZone;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public EditInformationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public EditInformationPage commitClick() {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			commit.click();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this;
	}

	public EditInformationPage editBtnClick() throws InterruptedException {
		try {
			// WebDriverWait wait = new WebDriverWait(driver, 10);
			/// wait.until(ExpectedConditions.elementToBeClickable(edit));
			Thread.sleep(15000);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", edit);
			// (edit).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public void logout() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		try {
			
			wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.className("toast-message"))));

		} catch (NoSuchElementException e) {

		}
		logout.click();

	}

	/**
	 * Method for Click action on Try Canvas Free Button on First Page.
	 * 
	 * @throws InterruptedException
	 */
	public EditInformationPage profileBtnClick() throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.elementToBeClickable(profile));
			(profile).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public EditInformationPage updateDefaultLocation(String defLocation) throws InterruptedException {
		try {
			Select ddlTimeZones = new Select(defaultLocation);
			ddlTimeZones.selectByVisibleText(defLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public EditInformationPage updateEmail(String user_email) throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.elementToBeClickable(userEmail));
			userEmail.click();
			Thread.sleep(1000);
			userEmail.clear();
			userEmail.sendKeys(user_email);
			Thread.sleep(2000);
			// Thread.sleep(2000);
			// logout.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public EditInformationPage updateFirstName(String firstName) throws InterruptedException {
		try {
			userFirstName.clear();
			userFirstName.sendKeys(firstName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public EditInformationPage updateTimeZone(String timeZone) throws InterruptedException {
		try {
			Select ddlTimeZones = new Select(userTimeZone);
			ddlTimeZones.selectByVisibleText(timeZone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public EditInformationPage updateUserCountry(String country) throws InterruptedException {
		try {
			Select ddlTimeZones = new Select(userCountry);
			ddlTimeZones.selectByVisibleText(country);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public boolean verifyCountryUpdated(String location) {
		Select ddlTimeZones = new Select(userCountry);
		return ddlTimeZones.getFirstSelectedOption().getText().contains(location);
	}

	public boolean verifyDefaultLocationUpdated(String loc) {
		return header.getText().equals(loc);
	}

	public boolean verifyEmailUpdated(String email) {
		return verifyEmail.getText().contains(email);
	}

	public boolean verifyFirstNameUpdated(String firstName) {
		return name.getText().contains(firstName);
	}

	public boolean verifyReLogin() {
		return (driver.findElements(By.linkText("Log Out")).size() == 1);
	}

	public boolean verifyTimeZone(String timeZone) {

		String[] timeZoneData = timeZone.split(" ");
		String validTimeZoneValue = timeZoneData[timeZoneData.length - 1];
		return verifyTimeZone.getText().contains(validTimeZoneValue);
	}
}
