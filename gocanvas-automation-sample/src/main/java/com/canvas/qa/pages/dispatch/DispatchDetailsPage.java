package com.canvas.qa.pages.dispatch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.WorkflowPage;

public class DispatchDetailsPage extends BasePage {

	String columnValueXpath = "//td[text() = '%s']//following-sibling::td[1]";

	WebDriver driver;

	@FindBy(xpath = "//a[contains(.,'Edit')]")
	WebElement editLink;

	@FindBy(xpath = "//div[@class = 'table-responsive'][2]//tbody//tr[6]/td[2]")
	WebElement newLongTextLoop;

	@FindBy(xpath = "//a[contains(.,'Workflow & Dispatch')]")
	WebElement workflowAndDispatchLink;

	public DispatchDetailsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public EditDispatchPage clickEditLink() {
		clickOnHiddenElement(editLink, driver);
		//editLink.click();
		customWait(10);
		return new EditDispatchPage(driver);
	}

	public WorkflowPage clickEditLinkNoAccess() {
		clickOnHiddenElement(editLink, driver);
		//editLink.click();
		return new WorkflowPage(driver);
	}

	public WorkflowPage clickWorkflowAndDispatchLink() {
		clickOnHiddenElement(workflowAndDispatchLink, driver);
		customWait(2);
		return new WorkflowPage(driver);
	}

	public String getColumnText(String columnHeading) {
		WebElement element = driver.findElement(By.xpath(String.format(columnValueXpath, columnHeading)));
		return element.getText();
	}

	public String getNewLongTextLoopValue() {
		return newLongTextLoop.getText();
	}

	public boolean isEditLinkDisplayed() {
		return editLink.isDisplayed();
	}
}
