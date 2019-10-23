package com.canvas.qa.pages.apps;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

/**
 * @author kailash.pathak
 *
 */
public class EditAndViewPage extends BasePage

{

	@FindBy(xpath = "//input[@id='admins']//parent::label")
	WebElement allowDelete1;
	@FindBy(xpath = "//input[@id='d_admins']")
	WebElement allowDelete11;

	@FindBy(xpath = "//input[@id='designers']//parent::label")
	WebElement allowDelete2;

	@FindBy(xpath = "//input[@id='d_designers']")
	WebElement allowDelete21;

	@FindBy(xpath = "//input[@id='reporters']//parent::label")
	WebElement allowDelete3;

	@FindBy(xpath = "//input[@id='d_reporters']")
	WebElement allowDelete31;

	@FindBy(xpath = "//input[@id='reset_date_on_mobile_edit']")
	WebElement allowDelete4;

	@FindBy(xpath = "//input[@id='user']//parent::label")
	WebElement allowDelete5;

	@FindBy(xpath = "//a[contains(.,'Inventory Collection')]")
	WebElement appLinkInventory;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement clickOnSaveButton;

	@FindBy(xpath = ".//*[@id='page-wrapper']//span[contains(.,'Dispatch')]")
	WebElement dispatchlink;

	WebDriver driver;

	// @FindBy(xpath = "//span[contains(.,'Edit & View')]")
	@FindBy(xpath = "//strong[contains(.,'Edit & View')]/..//*[contains(text(),'Settings')]")
	WebElement editAndView;

	// @FindBy(xpath = "//span[contains(.,'Edit & View')]")
	@FindBy(xpath = "//strong[contains(.,'Edit & View')]")
	List<WebElement> editAndViewLinkInBottom;

	@FindBy(xpath = "//span[contains(.,'Log Out')]")
	WebElement logout;

	@FindBy(xpath = "//h1[contains(.,'Apps')]")
	WebElement pageName;

	@FindBy(xpath = "//span[contains(.,'Apps')]")
	WebElement pageNameLeftPanel;

	@FindBy(xpath = "//span[contains(.,'Remember & Recall')]")
	WebElement rememberRecallLink1;

	@FindBy(xpath = "//button[@value='Save']")
	WebElement saveButton;

	@FindBy(xpath = "//a[contains(.,'Test App')]")
	WebElement testAppLink;

	@FindBy(className = "toast-message")
	WebElement toast;

	public EditAndViewPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public EditAndViewPage clickAppLink() throws InterruptedException {

		pageNameLeftPanel.click();
		Actions actions = new Actions(driver);
		actions.moveToElement(appLinkInventory);
		fluentWait(appLinkInventory, driver).click();
		return this;
	}

	public EditAndViewPage clickOnEditAndViewLink() throws InterruptedException {

		// JavascriptExecutor js = ((JavascriptExecutor) driver);
		// js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		// customWait(2);
		Actions actions = new Actions(driver);
		actions.moveToElement(editAndView);
		// driver.manage().timeouts().implicitlyWait(12,TimeUnit.SECONDS) ;
		// WebDriverWait wait = new WebDriverWait(driver, 30);
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(.,'Edit
		// & View')]")));
		clickOnHiddenElement(editAndView, driver);
		return this;
	}

	public void deselectCheckBox() throws InterruptedException {

		/*
		 * pageNameLeftPanel.click(); Actions actions = new Actions(driver);
		 * actions.moveToElement(testAppLink); testAppLink.click();
		 * clickOnEditAndViewLink(); for (int i = 1; i < 5; i++) {
		 * 
		 * List<WebElement> clickOnCheckBoxes = driver.findElements(By.xpath(
		 * ".//*[@id='edit_form_48416']/div[1]/table/tbody/tr" + "[" + i + "]" +
		 * "/td[2]/label"));
		 * 
		 * for (WebElement checkBoxs : clickOnCheckBoxes) { checkBoxs.click(); }
		 * } JavascriptExecutor js = (JavascriptExecutor) driver;
		 * js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		 */

		allowDelete1.click();
		allowDelete2.click();
		allowDelete3.click();
		allowDelete5.click();

	}

	public String getToastMsg() throws NoSuchElementException, InterruptedException {

		fluentWait(toast, driver);
		try {
			return (toast).getText();
		} catch (NoSuchElementException e) {
			return "Failed";
		}
	}

	public boolean isEditAndViewLinkDisplay() throws InterruptedException {
		return editAndViewLinkInBottom.size() > 0;
	}

	public boolean pageNameAfterlogin(String pageNameAfterLogin) throws InterruptedException

	{
		boolean pageNameText = pageName.getText().equals(pageNameAfterLogin);
		return pageNameText;
	}

	public boolean saveDeselectCheckBox(String saveMsg) throws InterruptedException {
		clickOnHiddenElement(clickOnSaveButton, driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toast-message']")));
		boolean saveStatus = toast.getText().contains(saveMsg);
		return saveStatus;

	}

	public boolean saveSelectedCheckBox(String saveMsg) throws InterruptedException {
		clickOnHiddenElement(clickOnSaveButton, driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toast-message']")));
		boolean saveStatus = toast.getText().contains(saveMsg);
		return saveStatus;

	}

	public void selectCheckBox() throws InterruptedException {
		/*
		 * for (int i = 1; i < 5; i++) {
		 * 
		 * List<WebElement> clickOnCheckBoxes = driver.findElements(
		 * By.xpath(".//*[@id='edit_form_48416']/div[1]/table/tbody/tr" + "[" +
		 * i + "]" + "/td[2]/label"));
		 * 
		 * for (WebElement checkBoxs : clickOnCheckBoxes) { checkBoxs.click(); }
		 * } JavascriptExecutor js = (JavascriptExecutor) driver;
		 * js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		 */
		allowDelete1.click();
		allowDelete2.click();
		allowDelete3.click();
		allowDelete5.click();

	}

	public boolean verifyAllowedADRToDeleteCheckBox() throws InterruptedException {
		boolean appStatus = allowDelete11.isSelected() && allowDelete21.isSelected() && allowDelete31.isSelected();
		return appStatus;
	}

	public boolean verifyEditAndView() throws InterruptedException {

		Actions actions = new Actions(driver);
		actions.moveToElement(editAndView);
		boolean appStatus = isEditAndViewLinkDisplay();
		return appStatus;
	}

	public boolean verifyEditedOnMobile() throws InterruptedException {
		boolean appStatus = allowDelete4.isSelected();
		return appStatus;
	}

	public boolean verifyEditReviewLinkInBottom() throws InterruptedException {

		/*
		 * pageNameLeftPanel.click(); Actions actions = new Actions(driver);
		 * actions.moveToElement(testAppLink); testAppLink.click();
		 */
		boolean appStatus = isEditAndViewLinkDisplay();
		return appStatus;
	}
}
