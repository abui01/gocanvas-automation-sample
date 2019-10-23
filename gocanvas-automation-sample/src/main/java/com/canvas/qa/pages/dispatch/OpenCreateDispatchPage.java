package com.canvas.qa.pages.dispatch;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;

public class OpenCreateDispatchPage extends BasePage {
	@FindBy(xpath = "//small[contains(.,'Create Dispatch')]")
	WebElement clickoncreatedispatch;

	@FindBy(xpath = "//span[contains(.,'Dispatch')]")
	WebElement clickondispatchlink;
	/**
	 * Locators of Login Screen
	 */

	@FindBy(xpath = "//span[contains(.,'Workflow & Dispatch')]")
	WebElement clickonworkflowAndispatchlink;
	WebDriver driver;
	@FindBy(xpath = "//h1[contains(.,'Create Dispatch')]")
	WebElement sucessfullClickOnCreateDispatchLink;
	@FindBy(xpath = "//h1[contains(.,'Dispatch')]")
	WebElement sucessfullClickOnWorkflowAndDispatchLink;
	@FindBy(xpath = "//h1[contains(.,'Workflow & Dispatch')]")
	WebElement sucessfullClickOnWorkflowAndDispatchLink1;

	public OpenCreateDispatchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public String checkForSuccessfullClickCreateDispatch() {
		String successText1;
		try {
			successText1 = sucessfullClickOnCreateDispatchLink.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText1 = "Failed";
		}

		return successText1;
	}

	public String checkForSuccessfulLogin() {
		String successText;
		try {
			successText = sucessfullClickOnWorkflowAndDispatchLink.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public OpenCreateDispatchPage clickoncreatedispatch() throws IOException

	{
		(clickoncreatedispatch).click();

		return this;

	}

	public OpenCreateDispatchPage clickonworkflowanddispatch() throws IOException

	{
		(clickondispatchlink).click();
		return this;

	}

}
