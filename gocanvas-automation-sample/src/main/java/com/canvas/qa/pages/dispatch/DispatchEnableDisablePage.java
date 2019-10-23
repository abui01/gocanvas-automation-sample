package com.canvas.qa.pages.dispatch;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

public class DispatchEnableDisablePage extends BasePage {

	@FindBy(xpath = "//a[contains(.,'Inventory Collection')]")
	WebElement appLinkInventory;

	@FindBy(xpath = "//a[contains(.,'Detailed Weekly Timesheet')]")
	WebElement appLinkWeeklyTimesheet;

	@FindBy(xpath = "//select[@name='form_id']")
	WebElement clickAppDropDown;

	@FindBy(xpath = "//select[@name='form_id']")
	List<WebElement> clickAppDropDownList;

	@FindBy(xpath = "//a[contains(.,'Create Dispatch')]")
	WebElement clickCreateDispatch;

	@FindBy(xpath = "//button[@value='Save']")
	WebElement clickOnSaveButton;

	@FindBy(xpath = "//span[contains(.,'Workflow & Dispatch')]")
	WebElement clickWorkflowDispatchLink;

	// @FindBy(xpath = "//span[contains(.,'Dispatch')]")
	@FindBy(xpath = "//strong[contains(.,'Dispatch:')]")
	List<WebElement> dispatch;

	@FindBy(xpath = "//*[contains(@class,'icheckbox')]")
	WebElement dispatchEnableCheckBox;

	// @FindBy(xpath = ".//*[@id='page-wrapper']//span[contains(.,'Dispatch')]")
	@FindBy(xpath = "//strong[contains(.,'Dispatch:')]/..//a[contains(.,'Settings')]")
	WebElement dispatchlink;

	WebDriver driver;

	@FindBy(xpath = "//h1[contains(.,'Apps')]")
	WebElement pageName;

	@FindBy(xpath = "//h1[contains(.,'Dashboard')]")
	WebElement pageNameAfterLogin1;

	@FindBy(xpath = "//span[contains(.,'Apps')]")
	WebElement pageNameLeftPanel;

	@FindBy(id = "common_search_field")
	List<WebElement> searchField;

	@FindBy(xpath = "//i[contains(@class,'fa fa-search text-muted')]")
	WebElement searchIcon;

	@FindBy(className = "toast-message")
	WebElement toast;

	public DispatchEnableDisablePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public DispatchEnableDisablePage clickAppLink() throws InterruptedException {

		Actions actions = new Actions(driver);
		actions.moveToElement(appLinkInventory);
		fluentWait(appLinkInventory, driver).click();
		return this;
	}
	public DispatchEnableDisablePage clickOnDispatchLink() throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(dispatchlink);
		clickOnHiddenElement(dispatchlink, driver);
		return this;
	}

	public String getToastMsg() throws NoSuchElementException, InterruptedException {

		fluentWait(toast, driver);
		try {
			// WebDriverWait wait=new WebDriverWait(driver, 20);
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toast-message']")));
			return (toast).getText();
		} catch (NoSuchElementException e) {
			return "Failed";
		}
	}

	public boolean isDispatchLinkDisplay() throws InterruptedException {
		customWait(3); // Implict & Explicit wait not working here so custom
						// wait is required
		return dispatch.size() > 0;
	}

	public boolean pageNameAfterlogin(String pageNameAfterLogin) throws InterruptedException

	{
		boolean pageNameText = pageName.getText().equals(pageNameAfterLogin);
		return pageNameText;
	}

	public boolean pageNameAfterlogin1(String pageNameAfterLogin) throws InterruptedException

	{
		boolean pageNameText = pageNameAfterLogin1.getText().equals(pageNameAfterLogin);
		return pageNameText;
	}

	public boolean selectCheckBoxAndSave(String orgMsg) throws InterruptedException {

		dispatchEnableCheckBox.click();
		clickOnSaveButton.click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toast-message']")));
		boolean status = getToastMsg().equals(orgMsg);
		return status;
	}

	public boolean verifyAppNameAfterDisableDispatch(String AppNameInDropDown) throws InterruptedException {

		clickWorkflowDispatchLink.click();
		clickCreateDispatch.click();
		if (clickAppDropDownList.size() > 0) {
			Select select = new Select(clickAppDropDown);
			boolean found = false;

			List<WebElement> allOptionsInDropDown = select.getOptions();
			for (WebElement option : allOptionsInDropDown) {
				for (int i = 0; i < allOptionsInDropDown.size(); i++) {
					if (option.getText().equals(AppNameInDropDown)) {
						found = true;
						break;
					}
				}
			}
			return found;
		} else {
			return false;
		}
	}

	public boolean verifyAppNameBeforeDisableDispatch(String AppNameInDropDown) throws InterruptedException {

		clickWorkflowDispatchLink.click();
		clickCreateDispatch.click();
		Select select = new Select(clickAppDropDown);
		boolean found = false;

		List<WebElement> allOptions = select.getOptions();
		for (WebElement option : allOptions) {
			for (int i = 0; i < allOptions.size(); i++) {
				if (option.getText().equals(AppNameInDropDown)) {
					found = true;
					break;
				}
			}
		}
		return found;
	}

	public boolean verifyDispatchCheckMark() throws InterruptedException {
		boolean appStatus = dispatchEnableCheckBox.isSelected();
		return appStatus;
	}

	public boolean verifyDispatchDisable() throws InterruptedException {

		Actions actions = new Actions(driver);
		actions.moveToElement(dispatchlink);
		boolean appStatus = isDispatchLinkDisplay();
		return appStatus;
	}

	public boolean verifyDispatchDisableAfterLogin(String disableMsg) throws InterruptedException {
		pageNameLeftPanel.click();
		if (searchField.size() > 0) {
			searchField.get(0).clear();
			searchField.get(0).sendKeys("Detailed Weekly Timesheet");
			searchIcon.click();
			customWait(5);
		}
		Actions actions1 = new Actions(driver);
		actions1.moveToElement(appLinkWeeklyTimesheet);
		appLinkWeeklyTimesheet.click();
		Actions actions2 = new Actions(driver);
		actions2.moveToElement(dispatchlink);
		clickOnHiddenElement(dispatchlink, driver);
		clickOnHiddenElement(dispatchEnableCheckBox, driver);
		clickOnSaveButton.click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toast-message']")));
		boolean status = getToastMsg().equals(disableMsg);
		return status;
	}

	public boolean verifyDispatchEnable() throws InterruptedException {
		if (searchField.size() > 0) {
			searchField.get(0).clear();
			searchField.get(0).sendKeys("Detailed Weekly Timesheet");
			searchIcon.click();
			customWait(5);
		}
		Actions actions = new Actions(driver);
		actions.moveToElement(appLinkWeeklyTimesheet);
		appLinkWeeklyTimesheet.click();
		boolean appStatus = isDispatchLinkDisplay();
		return appStatus;
	}
}
