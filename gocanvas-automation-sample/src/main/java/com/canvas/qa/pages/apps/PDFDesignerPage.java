package com.canvas.qa.pages.apps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;

/**
 * @author shalini.pathak
 *
 */
public class PDFDesignerPage extends BasePage

{
	@FindBy(xpath = "//li[@ng-click = 'template=true']/h4")
	WebElement activeTemplate;

	@FindBy(xpath = "//h4[contains(.,'Create a layout for me')]")
	WebElement buttonCreateALayout;

	@FindBy(xpath = "//h4[contains(.,'Start from scratch')]")
	WebElement buttonStartFromScratch;

	WebDriver driver;

	@FindBy(xpath = "//button[contains(.,'Start')]")
	WebElement startButton;

	@FindBy(xpath = "//h1[contains(.,'Design your PDF')]")
	WebElement title;

	public PDFDesignerPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public PDFDesignerPage clickCreateALayout() {
		buttonCreateALayout.click();
		return this;
	}

	public PDFDesignerLayoutPage clickStartButton() {
		startButton.click();
		return new PDFDesignerLayoutPage(driver);
	}

	public PDFDesignerPage clickStartFromScratch() {
		buttonStartFromScratch.click();
		return this;
	}

	public String getActiveTemplateText() {
		return activeTemplate.getText();
	}

	public boolean isCreateALayoutDisplayed() {
		return buttonCreateALayout.isDisplayed();
	}

	public boolean isStartButtonDisplayed() {
		return startButton.isDisplayed();
	}

	public boolean isStartFromScratchDisplayed() {
		return buttonStartFromScratch.isDisplayed();
	}

	public boolean isTitleDisplayed() {
		return title.isDisplayed();
	}
}
