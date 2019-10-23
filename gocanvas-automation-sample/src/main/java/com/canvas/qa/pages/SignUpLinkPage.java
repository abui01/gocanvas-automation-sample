package com.canvas.qa.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpLinkPage extends BasePage

{
	// XPATH For - Try Canvas free Button on First Page.
	@FindBy(xpath = ".//*[@id='nav']/div/div/ul/li[2]/a")
	WebElement applicationStore;

	// @FindBy(className = "top_nav_app_store")
	// List<WebElement> navDropDowns;

	@FindBy(linkText = "View All Apps")
	WebElement btnAllApps;

	// XPATH For - Company Name in Sign Up Page (Step 2).
	@FindBy(id = "company_name")
	WebElement companyName;

	// XPATH For - Continue With Email Button in Sign Up Page (Step 1).
	@FindBy(className = "step1-btn")
	WebElement continueWithEmail;

	@FindBy(xpath = "div/div")
	WebElement div;

	WebDriver driver;

	@FindBy(css = "#errorExplanation>ul>li")
	WebElement failureMessage;

	// XPATH For - Industry ID in Sign Up Page (Step 2).
	@FindBy(id = "industry_id")
	WebElement industryId;

	@FindBy(xpath = "//a[contains(text(), 'Application Store')]")
	List<WebElement> navDropDowns;

	// XPATH For - Successful SignUp.
	@FindBy(xpath = "//h3[@class='onboarding-text']")
	WebElement successfulSignup;

	@FindBy(id = "search_results")
	WebElement tblApps;

	@FindBy(xpath = ".//*[@id='search_results']/div[1]/div[2]/div/div[2]/div/a")
	WebElement tryCanvasFree;

	// XPATH For - Try It Free Button in Sign Up Page (Step 2).
	@FindBy(id = "two-step-submit-btn")
	WebElement tryItFree;

	// XPATH For - User Company Email in Sign Up Page (Step 1).
	@FindBy(xpath = ".//*[@id='user_email']")
	WebElement userCompanyEmail;

	// XPATH For - User First Name in Sign Up Page (Step 1).
	@FindBy(xpath = ".//*[@id='user_first_name']")
	WebElement userFirstName;

	// XPATH For - User Last Name in Sign Up Page (Step 1).
	@FindBy(xpath = ".//*[@id='user_last_name']")
	WebElement userLastName;

	// XPATH For - User Password in Sign Up Page (Step 2).
	@FindBy(id = "password_input")
	WebElement userPassword;

	// XPATH For - User Phone in Sign Up Page (Step 2).
	@FindBy(id = "user_phone")
	WebElement userPhone;

	@FindBy(css = ".signup-box-2-step h1")
	WebElement verifySignUpText1;

	@FindBy(css = ".signup-box-2-step p")
	WebElement verifySignUpText2;

	// XPATH For - Step 1 completion and Step 2 reached.
	@FindBy(css = ".signup-box-2-step h1")
	WebElement verifyStep2;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public SignUpLinkPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	/**
	 * Method for Click action on Try Canvas Free Button on First Page.
	 */
	public SignUpLinkPage appStoreButtonClick() {
		try {
			WebElement ddlAppStore = navDropDowns.get(0);
			for (WebElement d : navDropDowns) {
				if (d.getText().contains("Application Store")) {
					ddlAppStore = d;
				}
			}
			ddlAppStore.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public void basic() {
		List<WebElement> apps = tblApps.findElements(By.xpath("div/div"));
		WebElement basicTimeCard = apps.get(0);
		for (WebElement a : apps) {
			if (a.findElement(By.xpath("h4")).getText().contains("Basic Time Card")) {
				basicTimeCard = a;
			}
		}
		WebElement btnGetApp = basicTimeCard.findElement(By.className("btn-primary"));
		btnGetApp.click();
	}

	public String checkForSuccessfulSignUp() {
		String successText;

		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.textToBePresentInElement(successfulSignup, "Welcome to GoCanvas."));
		successText = successfulSignup.getText();
		return successText;

	}

	public void clickViewAllApps() {
		try {
			btnAllApps.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return This method is used to implement "ContinueWithEmail" button click
	 *         on Sign-Up Page (Step-1).
	 */
	public SignUpLinkPage continueWithEmailClick() {
		try {

			(continueWithEmail).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;

	}

	/**
	 * @param userCompanyEmail
	 * @return
	 * @throws IOException
	 *             This method is to send argument value UserCompanyEmail to
	 *             WebPage place holders.
	 */
	public SignUpLinkPage step1CompanyEmail(String userCompanyEmail) throws IOException {
		try {
			(this.userCompanyEmail).sendKeys(userCompanyEmail);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='2px groove red'", this.userCompanyEmail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;

	}

	/**
	 * @param userFirstName
	 * @return
	 * @throws IOException
	 *             This method is to send argument value UserFirstName to
	 *             WebPage place holders.
	 */
	public SignUpLinkPage step1FirstName(String userFirstName) throws IOException {

		try {
			(this.userFirstName).sendKeys(userFirstName);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='2px groove red'", this.userFirstName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;

	}

	/**
	 * @param userLastName
	 * @return
	 * @throws IOException
	 *             This method is to send argument value UserLastName to WebPage
	 *             place holders.
	 */
	public SignUpLinkPage step1LastName(String userLastName) throws IOException {
		try {
			(this.userLastName).sendKeys(userLastName);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='2px groove red'", this.userLastName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;

	}

	/**
	 * @param companyName
	 * @return
	 * @throws IOException
	 *             This method is to send argument value Company Name to WebPage
	 *             place holders.
	 */
	public SignUpLinkPage step2CompanyName(String companyName) throws IOException {
		try {
			(this.companyName).sendKeys(companyName);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='2px groove red'", this.companyName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;

	}

	/**
	 * @param industryId
	 * @return
	 * @throws IOException
	 *             This method is to send argument value Industry ID to WebPage
	 *             place holders.
	 */
	public SignUpLinkPage step2IndustryId(String industryId) throws IOException {
		try {
			new Select(this.industryId).selectByValue(industryId);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='2px groove red'", this.industryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * @param userPassword
	 * @return
	 * @throws IOException
	 *             This method is to send argument value UserPassword to WebPage
	 *             place holders.
	 */
	public SignUpLinkPage step2Password(String userPassword) throws IOException {
		try {

			(this.userPassword).sendKeys(userPassword);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='2px groove red'", this.userPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * @param userPhone
	 * @return
	 * @throws IOException
	 *             This method is to send argument value UserPhone to WebPage
	 *             place holders.
	 */
	public SignUpLinkPage step2Phone(String userPhone) throws IOException {
		try {
			(this.userPhone).sendKeys(userPhone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public SignUpLinkPage tryItFreeButtonClick() {
		try {
			(tryCanvasFree).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * @return This method is used to implement "Try It Free" button click on
	 *         Sign-Up Page (Step-1)
	 */
	public SignUpLinkPage tryItFreeClick() {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", tryItFree);
			// (tryItFree).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public String verifySignUpPageText() {
		String signUpText = "";
		try {
			signUpText = verifySignUpText1.getText().concat(". ").concat(verifySignUpText2.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signUpText;
	}

	public String verifyStep2() {
		String step2Text;
		try {
			step2Text = verifyStep2.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			step2Text = "Failed";
		}
		return step2Text;
	}
}
