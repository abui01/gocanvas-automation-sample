package com.canvas.qa.pages;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.core.ReportManager;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author kailash.pathak
 *
 */

public class DownloadAndPdfViewPage extends BasePage {
	@FindBy(xpath = "//h1[contains(.,'Test App')]")
	WebElement appDetail;

	@FindBy(xpath = "//a[contains(.,'Test App')]")
	WebElement appLink;

	@FindBy(xpath = "//span[contains(.,'Apps')]")
	WebElement appLinkLeftPanel;

	@FindBy(xpath = "//a[@title='Apps']")
	WebElement apptitle;

	private WebDriver driver;

	// @FindBy(xpath = ".//*[@id='pageContainer1']/div[2]/div[12]")

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(xpath = "//h1[contains(.,'Log In to Your Account')]")
	WebElement logoutMessage;

	@FindBy(xpath = "//i[@class='fa fa-chevron-left fa-lg m-r-sm']")
	WebElement pdfClose;

	@FindBy(xpath = "//a[@title='Download PDF']")
	WebElement pdfDownloadButton;

	@FindBy(xpath = "//span[@id='pdfFile']")

	WebElement pdfPreviewAppName;

	@FindBy(xpath = "//span[contains(.,'PDF Preview')]")
	WebElement pdfPreviewlink;

	@FindBy(xpath = ".//*[@id='opentip-21']/div/div[2]/div")
	WebElement previewText;

	@FindBy(xpath = "//div[contains(.,'View a blank version of the PDF')]/div/div/div")
	WebElement toolTip;

	public DownloadAndPdfViewPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public DownloadAndPdfViewPage clickAppLink() throws InterruptedException {

		Actions actions = new Actions(driver);
		actions.moveToElement(appLink);
		fluentWait(appLink, driver).click();
		return this;
	}

	public DownloadAndPdfViewPage DeletePdfAndPreview(String filepath) throws InterruptedException {

		String tempFile = filepath;
		File fileTemp = new File(tempFile);
		if (fileTemp.exists()) {
			fileTemp.delete();
		}

		Actions actions = new Actions(driver);
		actions.moveToElement(pdfPreviewlink);

		fluentWait(pdfPreviewlink, driver).click();

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='pageContainer1']/div[2]/div[4]")));
		return this;
	}

	public boolean hoverOverPDFpreview(String toolTips) throws InterruptedException {

		Actions builder = new Actions(driver);
		WebElement username_tooltip = pdfPreviewlink;
		builder.moveToElement(username_tooltip).perform();
		WebElement tooltiplocator = toolTip;

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[contains(.,'View a blank version of the PDF')]/div/div/div")));

		boolean tooltip_msg = tooltiplocator.getText().equals(toolTips);
		return tooltip_msg;
	}

	public boolean isFileDownloaded(String downloadPath, String fileName) {

		customWait(2);// Implict Explict wait not working here
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		boolean flag = false;
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();
		for (int i = 0; i < dir_contents.length; i++) {
			if (dir_contents[i].getName().equals(fileName))
				return flag = true;
		}
		return flag;
	}

	public void logout() throws InterruptedException {
		customWait(2);
		clickOnHiddenElement(logout, driver);

	}

	public void pdfClose() throws InterruptedException {
		pdfClose.click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'Test App')]")));
	}

	public DownloadAndPdfViewPage pdfDownload() throws InterruptedException {

		fluentWait(pdfDownloadButton, driver).click();
		return this;
	}

	public boolean pdfDownloadButtonVerify(String buttonName) throws InterruptedException {

		pdfDownloadButton.click();
		boolean pdfDownloadButton1 = pdfDownloadButton.getText().equals(buttonName);
		return pdfDownloadButton1;
	}

	public DownloadAndPdfViewPage PdfpdfDownloadButtonVerifyPreviewSeconUser() throws InterruptedException {

		Actions actions = new Actions(driver);
		actions.moveToElement(pdfPreviewlink);
		fluentWait(pdfPreviewlink, driver).click();
		return this;
	}

	@Override
	public void reportLog(boolean condition, String testId, String testCaseDescription, String stepnumber,
			String testStepDescription) {
		if (condition) {
			ReportManager.lognew(testId, testCaseDescription, LogStatus.PASS,
					"Step " + stepnumber + " " + testStepDescription);
		} else {
			ReportManager.lognew(testId, testCaseDescription, LogStatus.FAIL,
					"Step " + stepnumber + " " + testStepDescription);
		}

	}

	public boolean verifyAppNameInPreview(String appNameInPreview) throws InterruptedException {

		fluentWait(pdfPreviewlink, driver).click();
		customWait(10);
		boolean appNameInpreview = pdfPreviewAppName.getText().equals(appNameInPreview);

		WebDriverWait wait1 = new WebDriverWait(driver, 20);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Download PDF']")));

		fluentWait(pdfClose, driver).click();

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'Test App')]")));

		return appNameInpreview;
	}
}
