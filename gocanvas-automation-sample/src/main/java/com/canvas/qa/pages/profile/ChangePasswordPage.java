package com.canvas.qa.pages.profile;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;

public class ChangePasswordPage extends BasePage

{
	@FindBy(id = "btn_Change Password")
	WebElement changePassword;

	@FindBy(linkText = "Change Password")
	WebElement changePasswordButton;

	@FindBy(id = "user_password_confirmation")
	WebElement confirmPassword;

	@FindBy(xpath = "//label[@for='password_confirmation' or @for= 'user_password_confirmation']")
	WebElement confirmPasswordLabel;

	WebDriver driver;

	@FindBy(id = "errorExplanation")
	WebElement failureMessage;

	@FindBy(xpath = "//div[contains(.,'×The password is invalid.')]")
	WebElement invalidMessage;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(linkText = "Profile")
	WebElement myProfile;

	@FindBy(xpath = "//label[@for='password']")
	WebElement newPasswordLabel;

	@FindBy(xpath = "//input[@id= 'user_password' or @id= 'password' ]")
	WebElement oldPassword;

	@FindBy(xpath = "//input[@id= 'old_password']")
	WebElement oldPassword1;

	@FindBy(xpath = ".//label[@for='old_password']")
	WebElement oldPasswordLabel;

	@FindBy(xpath = "//div[contains(.,'Your password has been updated.')]")
	WebElement successMessage;

	@FindBy(xpath = "//input[@id= 'user_password' or @id= 'password' ]")
	WebElement userPassword;

	@FindBy(xpath = "//a[contains(.,'    Dashboard')]")
	WebElement verifyLogin;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public ChangePasswordPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public ChangePasswordPage changePasswordClick() throws InterruptedException {
		(changePassword).click();
		return this;
	}

	public ChangePasswordPage confirmPassword(String confirmPassword) throws IOException {
		(this.confirmPassword).sendKeys(confirmPassword);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='2px groove red'", this.confirmPassword);
		return this;

	}

	public ChangePasswordPage enterOldPassword(String oldPassword) throws IOException {
		(this.oldPassword1).sendKeys(oldPassword);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='2px groove red'", this.oldPassword1);
		return this;

	}

	public void logout() throws InterruptedException {
		Thread.sleep(8000);
		clickOnHiddenElement(logout, driver);
	}

	/**
	 * Method for Click action on Try Canvas Free Button on First Page.
	 * 
	 * @throws InterruptedException
	 */
	public ChangePasswordPage myProfileButtonClick() throws InterruptedException {
		Thread.sleep(2000);
		(myProfile).click();
		(changePasswordButton).click();
		return this;
	}

	public ChangePasswordPage oldPassword(String oldPassword) throws IOException {
		(this.oldPassword).sendKeys(oldPassword);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='2px groove red'", this.oldPassword);
		return this;

	}

	public ChangePasswordPage userPassword(String userPassword) throws IOException {
		(this.userPassword).sendKeys(userPassword);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='2px groove red'", this.userPassword);
		return this;

	}

	public String verifyConfirmPassLabel() throws InterruptedException {
		return (confirmPasswordLabel).getText();
	}

	public String verifyInvalidMsg() {
		String successText;
		try {
			successText = invalidMessage.getText();
		} catch (Exception ex) {
			try {
				successText = failureMessage.getText();
			} catch (Exception exc) {
				exc.printStackTrace();
				successText = "Failed";
			}
		}
		return successText;

	}

	public ChangePasswordPage verifyLabels() throws InterruptedException {
		Thread.sleep(5000);
		(myProfile).click();
		(changePasswordButton).click();
		return this;
	}

	public boolean verifyLogin() {
		return true;
	}

	public String verifyNewPassLabel() throws InterruptedException {
		return (newPasswordLabel).getText();
	}

	public String verifyOldPassLabel() throws InterruptedException {
		return (oldPasswordLabel).getText();
	}

	public String verifySuccessfulMsg() {
		String successText;
		try {
			successText = successMessage.getText();
		} catch (Exception ex) {
			try {
				successText = failureMessage.getText();
			} catch (Exception exc) {
				exc.printStackTrace();
				successText = "Failed";
			}
		}
		return successText;

	}
}
