package com.canvas.qa.pages.submissions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.canvas.qa.pages.BasePage;

public class SubmissionViewPage extends BasePage

{
	@FindBy(id = "advSearch")
	WebElement advSearchButton;

	@FindBy(id = "form_id")
	WebElement appDDL;

	@FindBy(xpath = "//li[@class = 'nav-header']/div/a")
	WebElement departmentsDDL;
	@FindBy(xpath = "//li[@class = 'nav-header']/div/ul/li")
	List<WebElement> departs;

	WebDriver driver;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(className = "fa-users")
	WebElement reassignButton;
	@FindBy(xpath = "//span[@id = 'reassignto']/div/button")
	WebElement reassignForm;

	@FindBy(xpath = "//span[@id = 'reassignto']/div/select")
	WebElement reassignUserDDL;

	@FindBy(id = "search-form")
	WebElement searchForm;
	@FindBy(xpath = "//table/tbody/tr/td/label/input")
	List<WebElement> searchResultCheckboxes;
	@FindBy(xpath = "//table/tbody/tr/td[2]")
	List<WebElement> searchResultIDs;
	@FindBy(xpath = "//table/tbody/tr/td[7]")
	List<WebElement> searchResultStatuses;

	@FindBy(xpath = "//table/tbody/tr/td[5]")
	List<WebElement> searchResultUsers;
	@FindBy(id = "status")
	WebElement statusDDL;
	@FindBy(className = "unassign-svg")
	WebElement unassignButton;
	@FindBy(linkText = "Workflow & Dispatch")
	WebElement workflow;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public SubmissionViewPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public SubmissionViewPage advancedSearchClick() throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, 250)");

		try {
			(advSearchButton).click();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this;
	}

	public SubmissionViewPage checkWorkflowByID(String id) {
		for (int i = 0; i < searchResultIDs.size(); i++) {
			if (searchResultIDs.get(i).getText().equals(id)) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", searchResultCheckboxes.get(i));
			}
		}
		return this;
	}

	public void logout() throws InterruptedException {
		Thread.sleep(5000);
		logout.click();
	}

	public SubmissionViewPage reassignClick() {
		(reassignButton).click();

		return this;
	}

	public SubmissionViewPage reassignSubmit() {
		(reassignForm).click();
		driver.switchTo().alert().accept();

		return this;
	}

	public SubmissionViewPage searchSubmit() {
		(searchForm).submit();
		return this;
	}

	public SubmissionViewPage selectApp(String appName) {
		Select apps = new Select(appDDL);
		apps.selectByVisibleText(appName);

		return this;
	}

	public SubmissionViewPage selectDepart(String departName) {
		departmentsDDL.click();
		for (WebElement depart : departs) {
			if (depart.getText().contains(departName)) {
				depart.click();
				return this;
			}
		}

		return this;
	}

	public SubmissionViewPage selectReassignUser(String user) {
		Select users = new Select(reassignUserDDL);
		users.selectByVisibleText(user);

		return this;
	}

	public SubmissionViewPage selectStatus(String status) {
		Select statuses = new Select(statusDDL);
		statuses.selectByVisibleText(status);

		return this;
	}

	public SubmissionViewPage unassignClick() {
		(unassignButton).click();
		driver.switchTo().alert().accept();

		return this;
	}

	public boolean verifyReassignDDL(String[] users) {
		Select user = new Select(reassignUserDDL);
		List<WebElement> allUsers = user.getOptions();

		boolean userPresent = false;
		for (WebElement s : allUsers) {
			for (int i = 0; i < users.length; i++) {
				if (s.getText().equals(users[i]))
					userPresent = true;
			}
			if (userPresent == false)
				break;
		}

		return userPresent;
	}

	public boolean verifyReassignedWorkflow(String id, String user) {
		try {
			for (int i = 0; i < searchResultIDs.size(); i++) {
				if (searchResultIDs.get(i).getText().equals(id)) {
					return (searchResultUsers.get(i).getText()).equals(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean verifyStatuses(String[] statuses) {
		Select status = new Select(statusDDL);
		List<WebElement> allStatuses = status.getOptions();

		boolean statusPresent = false;
		for (WebElement s : allStatuses) {
			for (int i = 0; i < statuses.length; i++) {
				if (s.getText().equals(statuses[i]))
					statusPresent = true;
			}
			if (statusPresent == false)
				break;
		}

		return statusPresent;
	}

	public boolean verifyStatusSearch(String status) {
		for (WebElement s : searchResultStatuses) {
			if (!s.getText().equals(status)) {
				return false;
			}
		}

		return true;
	}

	public boolean verifyUnassignSearch(String id) {
		for (int i = 0; i < searchResultIDs.size(); i++) {
			if (searchResultIDs.get(i).getText().equals(id)) {
				return (searchResultUsers.get(i).findElement(By.xpath("i")).getAttribute("alt")).equals("Invalid User");
			}
		}

		return false;
	}

	public SubmissionViewPage workflowButtonClick() throws InterruptedException {
		Thread.sleep(2000);
		(workflow).click();
		return this;
	}

}
