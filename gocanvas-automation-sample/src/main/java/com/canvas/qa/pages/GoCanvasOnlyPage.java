package com.canvas.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.profile.LoginPage;

public class GoCanvasOnlyPage extends BasePage {

	@FindBy(xpath = "//a[text() = 'Bulk Upload Users']")
	private WebElement bulkUploadUsers;

	@FindBy(xpath = "//span[text() = 'Choose file']")
	private WebElement chooseFileButton;

	@FindBy(xpath = "//span[text() = 'Upload']")
	private WebElement chooseUploadButton;

	@FindBy(id = "new_company_users")
	private WebElement choseFileInput;

	@FindBy(xpath = "//a[text() = 'Download Sample CSV file']")
	private WebElement downloadSampleFileButton;

	WebDriver driver;

	@FindBy(xpath = "//span[text() = 'GoCanvas Only']")
	private WebElement goCanvasOnly;

	@FindBy(xpath = "//span[contains(., 'Log Out')]")
	WebElement logOutButton;
	
	@FindBy(xpath = "(//input[@class='form-control'])[1]")
	WebElement searchTextBox;
	
	@FindBy(xpath = "//input[@type='submit']")
	WebElement searchButton;
	
	@FindBy(xpath = "//a[@class='btn btn-danger btn-sm']/span")
	WebElement manageButton;
	

	public GoCanvasOnlyPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public GoCanvasOnlyPage clickBulkUploadUsers() {
		bulkUploadUsers.click();
		return this;
	}

	public GoCanvasOnlyPage clickChooseFile() {
		chooseFileButton.click();
		return this;
	}

	public GoCanvasOnlyPage clickDownloadSampleFile() {
		downloadSampleFileButton.click();
		return this;
	}

	public GoCanvasOnlyPage clickGoCanvasOnly() {
		goCanvasOnly.click();
		return this;
	}

	public LoginPage clickLogOutButton() {
		clickOnHiddenElement(logOutButton, driver);
		return new LoginPage(driver);
	}

	public GoCanvasOnlyPage clickUpload() {
		chooseUploadButton.click();
		return this;
	}

	public GoCanvasOnlyPage uploadFile(String filePath) {
		choseFileInput.sendKeys(filePath);
		return this;
	}
	
	public GoCanvasOnlyPage searchUser(String userEmail) throws InterruptedException {
		if (searchTextBox.getText() != null) {
			searchTextBox.clear();
		}
		searchTextBox.sendKeys(userEmail);
		searchButton.click();
		
		return this;
	}
	
	public GoCanvasOnlyPage clickOnManage() {
		manageButton.click();
		return this;
	}
}
