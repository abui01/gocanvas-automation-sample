package com.canvas.qa.pages.apps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;

public class ShareTemplatePage extends BasePage {

	WebDriver driver;

	@FindBy(id = "btn_Share")
	WebElement shareButton;

	@FindBy(id = "template_description")
	WebElement templateDescriptionTextBox;
	
	

	public ShareTemplatePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		javaScriptErrorChecker(driver);
		


	}

	public TemplatesPage clickShareButton() {
		
		shareButton.click();
		return new TemplatesPage(driver);
	}

	public ShareTemplatePage enterTemplateDescription(String text) {
		templateDescriptionTextBox.sendKeys(text);
		customWait(2);
		return this;
	}
}
