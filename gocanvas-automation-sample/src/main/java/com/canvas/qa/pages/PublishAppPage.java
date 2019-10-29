package com.canvas.qa.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.apps.AppBuilderPage;
import com.canvas.qa.test.UtilityFunctions;
import com.relevantcodes.extentreports.LogStatus;

public class PublishAppPage extends BasePage {

	@FindBy(xpath = "//button[contains(.,'Cancel')]")
	WebElement cancelButton;

	//@FindBy(xpath = "//input[@id = 'flipAll']//following-sibling::ins")
	//@FindBy(xpath = "//tr/th[1]")
	//WebElement checkboxAllUser;
	
	@FindBy(xpath = "//tr/th[1]//following-sibling::ins")
	WebElement checkboxAllUser;

	@FindBy(xpath = "//input[@name='send_notification' and @checked='checked']")
	WebElement checkboxUser;

	@FindBy(xpath = "//button[contains(.,'Publish to device')]")
	WebElement clickConfirmPublishButton;

	@FindBy(xpath = "//a[@title='Close']")
	WebElement closeButton;

	WebDriver driver;

	@FindBy(xpath = " //div[contains(@class,'publish-option-list')]//table/tbody/tr[1]/td[3]")
	WebElement emailText;

	@FindBy(xpath = "//button[contains(.,'Finish')]")
	WebElement finishButton;
	
	@FindBy(xpath="//button[contains(.,'Close App Builder')]")
	WebElement closeAppBuilderButton;
	
	@FindBy(xpath = "//button[contains(.,'Keep Editing')]")
	WebElement keepEditing;

	@FindBy(xpath = "//button[contains(.,'Next: Assign to')]")
	WebElement nextButton;

	@FindBy(xpath = "//button[contains(.,'Next: Assign to')]")
	List<WebElement> nextButtonList;

	@FindBy(xpath = "//publish-option[2]/div/div/div[2]/option-toggle/label/div")
	WebElement dispatchButton;

	@FindBy(xpath = "//h1[contains(.,'Publish App')]")
	WebElement pageTitle;

	@FindBy(xpath = "//input[contains(@value,'Publish')]")
	WebElement publishAppButton;

	@FindBy(xpath = "//button[contains(.,'Finish') or contains(.,'Publish') ]")
	WebElement publishButton;

	@FindBy(xpath = "//td[contains(@ng-repeat,'key in keys')]")
	WebElement userNameText;
	
	@FindBy(xpath="//div[@class='button-group']/button[.='Publish']")
	WebElement publishAssignUser;
	
	//@FindBy(xpath = "//input[@id='iconInput']/../span")
	@FindBy(xpath = "//input[@id='iconInput']")
	WebElement updateAppImgIcon;
	
	@FindBy(id = "icon_save_button")
	WebElement iconSaveButton;
	
	@FindBy(xpath = "//img[@alt='App Icon']")
	WebElement appIconupdateDone;
	

	public PublishAppPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public PublishAppPage checkAllUserCheckbox() {
		clickOnHiddenElement(checkboxAllUser, driver);
		return this;
	}

	public CreateAppPage clickCloseButton() {
		clickOnHiddenElement(closeButton, driver);
		return new CreateAppPage(driver);
	}
	
	public CreateAppPage clickPublishAssignUser() {
		clickEle(driver, waitForVisbility(driver, publishAssignUser, 15));
		return new CreateAppPage(driver);
	}

	public CreateAppPage clickFinishButton() {
		clickOnHiddenElement(finishButton, driver);
		return new CreateAppPage(driver);
	}

	public CreateAppPage clickNextButton() {
		waitForVisbility(driver, nextButton, 15);
		if (nextButtonList.size() > 0) {
			clickOnHiddenElement(nextButton, driver);
			customWait(3);
		}
		return new CreateAppPage(driver);
	}

	public CreateAppPage clickNextButtonUsers() {
		if (nextButtonList.size() > 0) {
			clickOnHiddenElement(nextButton, driver);
		}
		return new CreateAppPage(driver);
	}
	public CreateAppPage selectDispatchToggleButton() {

		clickOnHiddenElement(dispatchButton, driver);
		return new CreateAppPage(driver);
	}

	// publish-option[2]/div/div/div[2]/option-toggle/label/div
	public PublishAppPage clickNextPublishButton() {
		waitForVisbility(driver, nextButton, 15);
		clickOnHiddenElement(nextButton, driver);
		return this;
	}

	public CreateAppPage clickPublishAppButton() {

		clickOnHiddenElement(publishAppButton, driver);
		return new CreateAppPage(driver);
	}

	public AppBuilderPage clickPublishButton() {
		waitForVisbility(driver, publishButton, 15);
		clickOnHiddenElement(publishButton, driver);
		return new AppBuilderPage(driver);
	}

	public PublishAppPage clickPublishToDeviceButton() {
		clickOnHiddenElement(waitForVisbility(driver, clickConfirmPublishButton, 15), driver);
		return this;
	}
	
	public PublishAppPage clickCloseAppBuilderButton() {
		clickEle(driver, waitForVisbility(driver, closeAppBuilderButton, 15));
		return this;
	}

	public PublishAppPage clickKeepEditing() {
		waitForVisbility(driver, keepEditing, 15);
		clickOnHiddenElement(keepEditing, driver);
		return this;
	}

	public String getEmail() {
		return emailText.getText();
	}

	public String getUserName() {
		return userNameText.getText();
	}

	public boolean isCancelButtonDisplayed() {
		return cancelButton.isDisplayed();
	}

	public boolean isPublishButtonDisplayed() {
		return publishButton.isDisplayed();
	}

	public boolean isTitleDisplayed() {
		return pageTitle.isDisplayed();
	}

	public boolean isUserCheckboxSelected() {
		return checkboxUser.isSelected();
	}

	@Override
	public void reportLog(boolean condition, String testId, String testCaseDescription, String stepnumber,
			String testStepDescription) {
		if (condition) {
			ReportManager.lognew(testId, testCaseDescription, LogStatus.PASS,
					"Step " + stepnumber + " " + testStepDescription);
		} else {
			ReportManager.lognew(testId, testCaseDescription, LogStatus.FAIL,
					"Step " + stepnumber + " " + testStepDescription);
		}
	}

	/**
	 * @param appBuilder
	 * @param testContext
	 * @param userName
	 * @param email
	 * @param appName
	 * @param appStatus
	 * @return
	 * @throws InterruptedException
	 */
	public AppBuilderPage verifyCreatedApp(ITestContext testContext, String userName, String email, String appName,
			String appStatus, String step, String step2, String step3) throws InterruptedException {

		/*
		 * boolean status = isTitleDisplayed(); reportLog(status,
		 * testContext.getName(),"User clicked on publish to device button", step,
		 * "User re-direct to Publish App Page" ); org.testng.Assert.assertTrue(
		 * status,"User re-direct to Publish App Page");
		 */

		boolean status = getUserName().contains(userName);
		reportLog(status, testContext.getName(), "On Publish App page", step2 + ".1",
				"Username: " + userName + " displayed");
		org.testng.Assert.assertTrue(status);
		status = getEmail().contains(email);
		reportLog(status, testContext.getName(), "On Publish App page", step2 + ".2", "Email: " + email + " displayed");

		org.testng.Assert.assertTrue(status);

		status = isUserCheckboxSelected();
		reportLog(status, testContext.getName(), "On Publish App page", step2 + ".3",
				"User name checkbox selected by default");
		org.testng.Assert.assertTrue(status);

		status = isCancelButtonDisplayed();
		reportLog(status, testContext.getName(), "On Publish App page", step3 + ".1", "Cancel button displayed");
		org.testng.Assert.assertTrue(status);

		status = isPublishButtonDisplayed();
		reportLog(status, testContext.getName(), "On Publish App page", step3 + ".2", "Publish button displayed");
		org.testng.Assert.assertTrue(status);

		AppBuilderPage appBuilderPage = clickPublishButton();
		clickKeepEditing();

		return appBuilderPage;
	}
	
	/**
	 * Select random file as App Icon
	 * @param filePath
	 */
	public void updateAppIcon(String systemPath, String []filePath) {
		int count = UtilityFunctions.getRandomNumberInRange(0, filePath.length-1);
		updateAppImgIcon.sendKeys(systemPath+filePath[count]);
		waitForClickablility(driver, iconSaveButton, 10);
		iconSaveButton.click();
		waitForVisbility(driver, appIconupdateDone, 10);
	}

	
	
	
}
