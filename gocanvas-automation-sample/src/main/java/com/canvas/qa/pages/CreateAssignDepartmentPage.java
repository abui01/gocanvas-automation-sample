package com.canvas.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateAssignDepartmentPage extends BasePage

{
	// @FindBy(linkText = "Account")
	@FindBy(xpath = "//nav/ul/li/a[contains(., 'Account')]")
	WebElement account;

	// @FindBy(xpath = "//a[contains(.,'All')]")
	@FindBy(linkText = "All")
	WebElement allDepart;

	// @FindBy(linkText = "Apps")
	@FindBy(xpath = "//nav/ul/li/a[contains(., 'Apps')]")
	WebElement btnApps;

	@FindBy(linkText = "Create Department")
	WebElement btnCreateDepartment;

	@FindBy(linkText = "Departments")
	WebElement btnDepartment;

	// @FindBy(xpath = "//ul[@id='side-menu']//*[contains(@class, 'fa')]/..")

	@FindBy(linkText = "Manage Members")
	WebElement btnManageMembers;

	@FindBy(className = "icheckbox")
	WebElement checkBox;

	// @FindBy(linkText = "Customize")
	@FindBy(xpath = "//a[contains(.,'Account Settings')]")
	WebElement customize;

	@FindBy(xpath = ".//*[@id='side_nav']//*[contains(@class, 'fa fa-angle-down m-l-xs')]/..")
	WebElement ddlDepartments;

	@FindBy(className = "dropdown-menu")
	WebElement departs;

	WebDriver driver;

	@FindBy(className = "form-horizontal")
	WebElement formHorizontal;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(className = "nav-header")
	WebElement nav;

	@FindBy(id = "btn_Save")
	WebElement saveBtn;

	@FindBy(id = "side-menu")
	WebElement sideMenu;

	@FindBy(className = "table-striped")
	WebElement tblMembers;

	/**
	 * Method for Click action on Try Canvas Free Button on First Page.
	 * 
	 * @throws InterruptedException
	 */
	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement toastMessage;

	@FindBy(id = "department_description")
	WebElement txtDepartmentDescrip;

	@FindBy(id = "department_name")
	WebElement txtDepartmentName;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public CreateAssignDepartmentPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public void createApp() throws InterruptedException {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(ddlDepartments));
			ddlDepartments.click();
			departs = nav.findElement(By.className("dropdown-menu"));

			wait.until(ExpectedConditions.elementToBeClickable(allDepart));
			allDepart.click();
			sideMenu.findElement(By.linkText("Customize")).click();
			List<WebElement> tblFeats = driver.findElement(By.className("form-horizontal"))
					.findElements(By.xpath("div"));
			WebElement rowDepart = tblFeats.get(0).findElement(By.className("fa-edit"));
			for (WebElement row : tblFeats) {
				if (row.getText().contains("Department Functionality")) {
					rowDepart = row;
				}
			}
			WebElement btnEdit = rowDepart.findElement(By.className("inline"));
			WebElement scroll = btnEdit;
			scroll.sendKeys(Keys.PAGE_DOWN);
			btnEdit.click();
			checkBox.click();
			saveBtn.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String createDepartment(String adminName, String departmentName, String departmentDesc)
			throws InterruptedException {
		String memberText = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(btnDepartment));
			btnDepartment.click();
			wait.until(ExpectedConditions.elementToBeClickable(btnCreateDepartment));
			btnCreateDepartment.click();
			wait.until(ExpectedConditions.elementToBeClickable(txtDepartmentName));
			txtDepartmentName.sendKeys(departmentName);
			txtDepartmentDescrip.sendKeys(departmentDesc);
			saveBtn.click();
			List<WebElement> members = tblMembers.findElements(By.xpath("tbody/tr"));

			for (WebElement m : members) {
				if (m.getText().contains(adminName)) {
					memberText = m.getText();
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return memberText;

	}

	public String departmentDisplayed() {
		return ddlDepartments.getText();
	}

	public String enableDepartment() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(account));
		(account).click();
		Thread.sleep(4000);
		wait.until(ExpectedConditions.elementToBeClickable(customize));
		(customize).click();
		driver.findElement(By
				.xpath("//strong[contains(.,'Department Functionality')]//following-sibling::div//div[@class = 'slider round']//i"))
				.click();

		/*
		 * List<WebElement> tblFeats =
		 * driver.findElement(By.className("form-horizontal"))
		 * .findElements(By.xpath("div")); WebElement rowDepart =
		 * tblFeats.get(0).findElement(By.className("fa-edit")); for (WebElement
		 * row : tblFeats) { if
		 * (row.getText().contains("Department Functionality")) { rowDepart =
		 * row; } } WebElement btnEdit =
		 * rowDepart.findElement(By.className("inline")); WebElement scroll =
		 * btnEdit; scroll.sendKeys(Keys.PAGE_DOWN); // JavascriptExecutor jse =
		 * ((JavascriptExecutor) driver); //
		 * jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		 * btnEdit.click(); checkBox.click(); saveBtn.click(); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */

		return driver.getPageSource();

	}

	public void logout() throws InterruptedException {
		try {
			Thread.sleep(5000);
			logout.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean verifyNoApp(String designerName, String designerRole, String departmentName)
			throws InterruptedException {
		boolean success = false;
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(btnManageMembers));
			btnManageMembers.click();
			List<WebElement> members = tblMembers.findElements(By.xpath("tbody/tr"));
			members = tblMembers.findElements(By.xpath("tbody/tr"));
			for (WebElement m : members) {
				if (m.getText().contains(designerName)) {
					WebElement chkbox = m.findElement(By.className("icheckbox"));
					chkbox.click();
					Select selRole = new Select(m.findElement(By.className("form-control")));
					selRole.selectByVisibleText(designerRole);
				}
			}
			wait.until(ExpectedConditions.elementToBeClickable(saveBtn));
			saveBtn.click();
			wait.until(ExpectedConditions.elementToBeClickable(ddlDepartments));
			ddlDepartments.click();
			WebElement departs = nav.findElement(By.className("dropdown-menu"));
			WebElement newDepart = departs.findElement(By.linkText(departmentName));
			newDepart.click();
			btnApps.click();
			if (driver.findElements(By.className("table-striped")).size() == 0)
				success = true;
			else
				success = false;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return success;
	}
}
