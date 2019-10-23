package com.canvas.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateFolderPage extends BasePage

{
	@FindBy(xpath = "//span[text() = 'Cancel']")
	WebElement cancelButton;

	WebDriver driver;

	@FindBy(id = "folder_name")
	WebElement folderNameTextBox;

	@FindBy(xpath = "//span[text() = 'Save']")
	WebElement saveButton;

	public CreateFolderPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public CreateFolderPage clearFolderName() {
		folderNameTextBox.clear();
		return this;
	}

	public CreateAppPage clickSaveButton() {
		saveButton.click();
		return new CreateAppPage(driver);
	}

	public CreateFolderPage enterFolderName(String text) {
		folderNameTextBox.sendKeys(text);
		return this;
	}
}
