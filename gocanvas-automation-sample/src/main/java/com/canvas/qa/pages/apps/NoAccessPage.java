package com.canvas.qa.pages.apps;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;

/**
 * @author kailash.pathak
 *
 */
public class NoAccessPage extends BasePage

{
	@FindBy(xpath = ".//*[@id='page-wrapper']//span[contains(.,'Dispatch')]")
	List<WebElement> dispatchlink;

	WebDriver driver;

	@FindBy(xpath = "//span[contains(.,'Log Out')]")
	WebElement logout;

	@FindBy(xpath = "//h1[contains(.,'Reports')]")
	WebElement pageName2;

	@FindBy(xpath = "//h1[contains(.,'Reports')]")
	WebElement pageNameAfterLogin1;

	@FindBy(xpath = "//h1[contains(.,'Submissions')]")
	WebElement pageNameAfterLogin2;

	@FindBy(xpath = "//span[contains(.,'Workflow & Dispatch')]")
	List<WebElement> workflowAndDispatch;

	public NoAccessPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public boolean pageNameAfterlogin1(String pageNameAfterLogin) throws InterruptedException {
		boolean pageNameText = pageNameAfterLogin1.getText().equals(pageNameAfterLogin);
		return pageNameText;
	}

	public boolean pageNameAfterlogin2(String pageNameAfterLogin) throws InterruptedException {
		boolean pageNameText = pageNameAfterLogin2.getText().equals(pageNameAfterLogin);
		return pageNameText;
	}

	public boolean verifyDispatchAndWorkflowLinks() throws InterruptedException {
		boolean appStatus = dispatchlink.size() > 0 && workflowAndDispatch.size() > 0;
		return appStatus;
	}
}
