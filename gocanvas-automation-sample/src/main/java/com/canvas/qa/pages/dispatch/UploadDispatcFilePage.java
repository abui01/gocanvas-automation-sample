package com.canvas.qa.pages.dispatch;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

public class UploadDispatcFilePage extends BasePage

{

	@FindBy(linkText = "Workflow & Dispatch")
	WebElement btnDispatch;

	@FindBy(linkText = "Upload")
	WebElement btnUpload;

	// @FindBy(xpath = "//span[contains(.,'Choose File')]")
	@FindBy(id = "csv_file")
	WebElement csvFile;

	@FindBy(xpath = "//*[contains(@id, 'wrapper')]//*[contains(@class, 'fa-angle-down')]")
	WebElement ddldepartments;
	@FindBy(xpath = "//*[contains(@class, 'fa fa-angle-down m-l-xs')]")
	WebElement departments;

	@FindBy(className = "dropdown-menu")
	WebElement departs;

	@FindBy(xpath = "//span[contains(.,'Dispatch')]")
	WebElement dispatch;

	List<WebElement> dispatches;

	@FindBy(xpath = "//table/tbody")
	WebElement dispatchTable;

	WebDriver driver;

	@FindBy(linkText = "First Department")
	WebElement firstDepart;

	@FindBy(linkText = "Log Out")
	WebElement logout;

	// @FindBy(xpath = "//ul[@id='side-menu']//*[contains(@class, 'fa')]")
	// WebElement departments;

	@FindBy(className = "nav-header")
	WebElement navHeader;

	@FindBy(id = "btn_Save")
	WebElement save;

	@FindBy(linkText = "Second Department")
	WebElement secondDepart;

	WebElement tblDispatches;

	@FindBy(className = "toast")
	WebElement toast;

	@FindBy(linkText = "Upload")
	WebElement upload;

	@FindBy(linkText = "Workflow & Dispatch")
	WebElement workFlowAndDispatch;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public UploadDispatcFilePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public boolean csvFileDep1(String step2dispatchname, String step2dispatchdesc, String step2dispatchtime)
			throws InterruptedException {
		csvFile.sendKeys(System.getProperty("user.dir") + "/src/main/resources/TC7547+Step+2.csv");
		save.click();
		Thread.sleep(2000);
		tblDispatches = driver.findElement(By.xpath("//table/tbody"));
		dispatches = tblDispatches.findElements(By.xpath("tr"));
		boolean added = false;
		for (WebElement d : dispatches) {
			if (d.getText().contains(step2dispatchname) && d.getText().contains(step2dispatchdesc)
					&& d.getText().contains(step2dispatchtime))
				added = true;
			break;
		}
		return added;
	}

	public boolean csvFileDep2(String step2dispatchdesc) {
		csvFile.sendKeys(System.getProperty("user.dir") + "/src/main/resources/TC7547+Step+3.csv");
		save.click();
		tblDispatches = driver.findElement(By.xpath("//table/tbody"));
		dispatches = tblDispatches.findElements(By.xpath("tr"));
		boolean added = false;
		for (WebElement d : dispatches) {
			if (d.getText().contains(step2dispatchdesc))
				added = true;
			break;
		}
		return added;
	}

	public boolean diffDepartment(String step3dispatchdesc) throws InterruptedException {
		departments.click();
		departs = navHeader.findElement(By.className("dropdown-menu"));
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("First Department")));

		WebElement firstDepart = departs.findElement(By.linkText("First Department"));
		firstDepart.click();
		workFlowAndDispatch.click();
		WebElement scroll = upload;
		scroll.sendKeys(Keys.PAGE_DOWN);

		boolean added = false;
		try {
			tblDispatches = driver.findElement(By.xpath("//table/tbody"));
			dispatches = tblDispatches.findElements(By.xpath("tr"));
			for (WebElement d : dispatches) {
				if (d.getText().contains(step3dispatchdesc))
					added = true;
				break;
			}
		} catch (Exception NoSuchElementException) {
			added = false;
		}

		return added;
	}

	public void dispatchBtnClick() throws InterruptedException {

		try {
			// Thread.sleep(30000);
			// WebDriverWait wait = new WebDriverWait(driver,50);
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Dispatch']")));
			fluentWait(dispatch, driver).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void header() throws InterruptedException {
		try {
			departments.click();
			WebElement nav = driver.findElement(By.className("nav-header"));
			WebElement departs = nav.findElement(By.className("dropdown-menu"));
			WebElement secondDepart = departs.findElement(By.linkText("Second Department"));
			secondDepart.click();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void logout() throws InterruptedException {
		Thread.sleep(5000);
		logout.click();

	}

	public String nonCSVFile() throws InterruptedException {
		try {

			//WebDriverWait wait = new WebDriverWait(driver, 10);
			//wait.until(ExpectedConditions.elementToBeClickable(csvFile));
			fluentWait(csvFile, driver).sendKeys(System.getProperty("user.dir") + "/src/main/resources/NotACSV.txt");
			//Thread.sleep(4000);
			fluentWait(save, driver).click();
			//Thread.sleep(4000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fluentWait(toast, driver).getText();
	}

	public void uploadBtnClick() throws InterruptedException {
		try {
			WebElement scroll = fluentWait(upload, driver);
			scroll.sendKeys(Keys.PAGE_DOWN);
			//Thread.sleep(3000);
			upload.click();
			//Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void workflowAnddispatchBtnClick() throws InterruptedException {
		try {
			workFlowAndDispatch.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void workflowAndDispatchClick(String step3dispatchdesc) throws InterruptedException {
		workFlowAndDispatch.click();
		uploadBtnClick();
		boolean added = false;
		try {
			tblDispatches = driver.findElement(By.xpath("//table/tbody"));
			dispatches = tblDispatches.findElements(By.xpath("tr"));
			for (WebElement d : dispatches) {
				if (d.getText().contains(step3dispatchdesc))
					added = true;
				break;
			}
		} catch (Exception NoSuchElementException) {
			added = false;
		}
	}
}
