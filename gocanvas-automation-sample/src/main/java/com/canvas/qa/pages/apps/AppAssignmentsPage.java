package com.canvas.qa.pages.apps;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;

/**
 * @author taukeer.ahmad
 *
 */
public class AppAssignmentsPage extends BasePage

{
	@FindBy(linkText = "Account")
	WebElement account;

	@FindBy(linkText = "App Assignments")
	WebElement appAssignmentsButton;
	
	@FindBy(linkText = "App Assignments")
	List<WebElement> appAssignmentsButtons;

	@FindBy(xpath = "//table[descendant::label[contains(.,'App')]]/tbody/tr/td/div/label/input")
	List<WebElement> appCheckboxes;

	@FindBy(xpath = "//table[descendant::label[contains(.,'App')]]/tbody/tr")
	List<WebElement> appRows;

	WebDriver driver;

	@FindBy(xpath = "//table[descendant::label[contains(.,'Group')]]/tbody/tr/td/div/label/input")
	List<WebElement> groupCheckboxes;

	@FindBy(id = "group_name")
	WebElement groupNameInput;

	@FindBy(xpath = "//table[descendant::label[contains(.,'Group')]]/tbody/tr")
	List<WebElement> groupRows;

	@FindBy(linkText = "Groups")
	WebElement groups;

	@FindBy(linkText = "Log Out")
	WebElement logout;
	@FindBy(linkText = "Profile")
	WebElement myProfile;

	@FindBy(linkText = "Create Group")
	WebElement newGroupButton;
	
//	@FindBy(id = "btn_Save")
//	WebElement saveAssignmentsButton;
	
	@FindBy(xpath = "//button[@id='btn_Save']/span")
	WebElement saveAssignmentsButton;

	@FindBy(id = "btn_Save")
	WebElement saveGroup;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public AppAssignmentsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public AppAssignmentsPage appAssignmentsButtonClick() throws InterruptedException {
		//Thread.sleep(2000);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		//executor.executeScript("arguments[0].click();", myProfile);
		clickProfileButton();
		//customWait(3);
		//executor = (JavascriptExecutor) driver;
		//executor.executeScript("arguments[0].click();", appAssignmentsButton);
		clickOnHiddenElement(waitForVisbility(driver, appAssignmentsButton, 15), driver);
		//Thread.sleep(20000);
		return this;
	}
	
	public AppAssignmentsPage clickProfileButton() throws InterruptedException {
		clickOnHiddenElement(waitForVisbility(driver, myProfile, 15), driver);
		return this;
	}
	
	public boolean verifyAppAssignmentsButtonPresent() {
		
		int appAssignmentButtonCount = appAssignmentsButtons.size();
		if (appAssignmentButtonCount > 0)
			return true;
		else {
			return false;
		}
	}

	public AppAssignmentsPage checkApp(String appName) {
		for (int i = 0; i < appRows.size(); i++) {
			WebElement row = appRows.get(i);
			if (row.getText().split(" ")[0].equals(appName)) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				if (!appCheckboxes.get(i).isSelected()) {
					js.executeScript("arguments[0].click();", appCheckboxes.get(i));
				}

				return this;
			}
		}
		return this;
	}

	public AppAssignmentsPage checkGroup(String groupName) {
		for (int i = 0; i < groupRows.size(); i++) {
			WebElement row = groupRows.get(i);
			if (row.getText().split(" ")[0].equals(groupName)) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				if (!groupCheckboxes.get(i).isSelected()) {
					js.executeScript("arguments[0].click();", groupCheckboxes.get(i));
				}
				return this;
			}
		}
		return this;
	}

	public AppAssignmentsPage createGroup(String groupName) throws InterruptedException {
		//Thread.sleep(15000);
		(waitForVisbility(driver, groupNameInput, 15)).sendKeys(groupName);
		//(saveGroup).click();
		clickEle(driver, saveGroup);
		return this;
	}

	public AppAssignmentsPage groupsClick() throws InterruptedException {
		//JavascriptExecutor executor = (JavascriptExecutor) driver;
		//executor.executeScript("arguments[0].click();", account);
		clickOnHiddenElement(waitForVisbility(driver, account, 15), driver);
		//Thread.sleep(3000);
		//executor = (JavascriptExecutor) driver;
		//executor.executeScript("arguments[0].click();", groups);
		clickOnHiddenElement(waitForVisbility(driver, groups, 15), driver);
		// (account).click();
		// (groups).click();
		return this;
	}

	public boolean isAppChecked(String appName) throws InterruptedException {
		//Thread.sleep(30000);
		for (int i = 0; i < appRows.size(); i++) {
			WebElement row = appRows.get(i);

			if ((row.getText().split(" ")[0]).equals(appName)) {
				return appCheckboxes.get(i).isSelected();
			}
		}

		return false;
	}

	public boolean isAppDisplayed(String appName) throws InterruptedException {
		//Thread.sleep(30000);
		boolean found = false;
		for (WebElement row : appRows) {
			if (row.getText().contains(appName)) {
				found = true;
				break;
			}
		}
		return found;
	}

	public boolean isGroupChecked(String groupName) throws InterruptedException {
		//Thread.sleep(30000);
		for (int i = 0; i < groupRows.size(); i++) {
			WebElement row = groupRows.get(i);
			if ((row.getText().split(" ")[0]).equals(groupName)) {
				return groupCheckboxes.get(i).isSelected();
			}
		}

		return false;
	}

	public boolean isGroupDisplayed(String groupName) throws InterruptedException {
		//Thread.sleep(30000);
		boolean found = false;
		for (WebElement row : groupRows) {
			if (row.getText().contains(groupName)) {
				found = true;
				break;
			}
		}
		return found;
	}

	public void logout() throws InterruptedException {
		Thread.sleep(5000);
		clickOnHiddenElement(logout, driver);
		Thread.sleep(10000);
	}

	public AppAssignmentsPage newGroup() throws InterruptedException {
		//JavascriptExecutor executor = (JavascriptExecutor) driver;
		//Thread.sleep(3000);
		//executor.executeScript("arguments[0].click();", newGroupButton);
		clickOnHiddenElement(waitForVisbility(driver, newGroupButton, 15), driver);
		
		return this;
	}

	public AppAssignmentsPage saveButtonClick() throws InterruptedException {
		//Thread.sleep(15000);
		fluentWait(saveAssignmentsButton, driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", saveAssignmentsButton);
		(saveAssignmentsButton).click();
		//Thread.sleep(30000);
		customWait(10); //wait for results to load
		return this;
	}

}
