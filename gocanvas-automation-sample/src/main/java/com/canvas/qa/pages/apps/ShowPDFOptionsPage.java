package com.canvas.qa.pages.apps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

public class ShowPDFOptionsPage extends BasePage {

	String appNameLinkXpath = "//a[text() = '%s']";

	WebDriver driver;

	@FindBy(xpath = "//*[@id=\"page-wrapper\"]/div[2]/div[2]/div/div/div/div[10]/div[2]/a")
	WebElement editPDFDateFormatButton;

	@FindBy(xpath = "//*[@id=\"page-wrapper\"]/div[2]/div[2]/div/div/div/div[6]/div[2]/a")
	WebElement editPDFHeaderButton;

	@FindBy(linkText = "Launch PDF Designer")
	WebElement launchPDFDesignerButton;

	@FindBy(xpath = "//*[contains(@class,'logo-image')]")
	WebElement logoImage;

	@FindBy(xpath = "//h1[contains(.,'PDF Options')]")
	WebElement pdfOptionsHeaderText;

	@FindBy(id = "icon_save_button")
	WebElement saveButton;

	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement toastMessage;

	@FindBy(id = "my_image_file")
	WebElement uploadImageButton;

	public ShowPDFOptionsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public EditAppPage clickAppNameLink(String appName) {
		driver.findElement(By.xpath(String.format(appNameLinkXpath, appName))).click();
		return new EditAppPage(driver);
	}

	public ShowPDFOptionsPage clickEditPDFDateFormat() {
		clickOnHiddenElement(fluentWait(editPDFDateFormatButton, driver), driver);
		// fluentWait(editPDFDateFormatButton,driver).click();
		return this;
	}

	public ShowPDFOptionsPage clickEditPDFHeader() {
		clickOnHiddenElement(fluentWait(editPDFHeaderButton, driver), driver);
		return this;
	}

	/**
	 * Clicks the Launch PDF Designer Button
	 * 
	 * @return
	 */
	public ShowPDFOptionsPage clickLaunchPDFDesignerButton() {
		fluentWait(launchPDFDesignerButton, driver).click();
		return this;
	}

	public ShowPDFOptionsPage uploadLogo() throws InterruptedException {
		fluentWait(uploadImageButton, driver)
				.sendKeys(System.getProperty("user.dir") + "/src/main/resources/PDF_LOGO.JPG");
		fluentWait(saveButton, driver).click();
		customWait(5); // wait for logo to load
		return this;
	}

	/**
	 * Verifies if the Logo is displayed after uploading
	 * 
	 * @return
	 */
	public boolean verifyLogoIsDisplayed() throws InterruptedException {
		return logoImage.isDisplayed();
	}

	/**
	 * Verifies if the PDF Options text is displayed
	 * 
	 * @return
	 */
	public boolean verifyPDFOptionsTextIsDisplayed() throws InterruptedException {

		if (fluentWait(pdfOptionsHeaderText, driver).isDisplayed()) {
			return true;
		}
		return false;
	}

	public boolean verifyToastMsgIsDisplayed() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(toastMessage));
		if (fluentWait(toastMessage, driver).isDisplayed()) {
			return true;
		}

		return false;
	}

}
