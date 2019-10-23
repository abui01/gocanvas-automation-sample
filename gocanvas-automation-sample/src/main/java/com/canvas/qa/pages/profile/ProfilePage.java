package com.canvas.qa.pages.profile;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

public class ProfilePage extends BasePage {

	@FindBy(xpath = "//a[text() = 'App Assignments']")
	private WebElement appAssignments;

	@FindBy(xpath = "//a[text() = 'Change Password']")
	private WebElement changePassword;

	@FindBy(xpath = "//a[text() = 'Download GoCanvas Client']")
	private WebElement downloadGoCanvasClient;

	WebDriver driver;

	@FindBy(linkText = "Edit")
	WebElement edit;

	@FindBy(xpath = "//h1[contains(., 'Profile')]")
	WebElement profile;

	@FindBy(id = "btn_Save")
	WebElement saveButton;

	@FindBy(className = "toast-message")
	WebElement toast;

	@FindBy(id = "user_first_name")
	WebElement userFirstName;

	@FindBy(xpath = "//ol[@class = 'breadcrumb']//a[text() = 'Users']")
	private WebElement users;

	public ProfilePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public ProfilePage clickEdit() {
		edit.click();
		return this;
	}

	public UsersPage clickUsers() {
		clickOnHiddenElement(users, driver);
		return new UsersPage(driver);
	}

	public String getToastMessage() {
		waitForVisbility(this.driver,toast, 30);
		return toast.getText();
	}

	public boolean isAppAssignmentsDisplayed() {
		return appAssignments.isDisplayed();
	}

	public boolean isChangePasswordDisplayed() {
		return changePassword.isDisplayed();
	}

	public boolean isDownloadGoCanvasClientDisplayed() {
		return downloadGoCanvasClient.isDisplayed();
	}

	public boolean isProfileDisplayed() {
		return profile.isDisplayed();
	}

	public ProfilePage updateFirstName(String firstName) throws InterruptedException {
		userFirstName.clear();
		userFirstName.sendKeys(firstName);
		saveButton.click();
		return this;
	}
}
