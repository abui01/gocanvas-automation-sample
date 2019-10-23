package com.canvas.qa.pages.apps;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.EditWorkflowPage;
import com.canvas.qa.pages.PublishAppPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.submissions.SubmissionNumberingPage;

/**
 * @author shalini.pathak
 *
 */
public class EditAppPage extends BasePage {
	@FindBy(xpath = "//a[text() = 'Apps']")
	WebElement appsLink;

	@FindBy(xpath = "//strong[contains(.,'Compression')]/..//*[contains(text(),'Settings')]")
	WebElement clickCompressionLink;

	@FindBy(xpath = "//strong[contains(.,'Submission Status')]/..//*[contains(text(),'Settings')]")
	WebElement clickSettingSubmissionStatus;

	// @FindBy(xpath = "//span[text() = 'Compression']")
	@FindBy(xpath = "//strong[contains(.,'Compression')]")
	WebElement compressionLink;

	@FindBy(xpath = "//*[contains(text(),'Create Template')]")
	WebElement createTemplateLink;

	// @FindBy(xpath = "//span[contains(.,'Delete App')]")
	@FindBy(xpath = "//div[contains(text(),'Delete App')]")
	WebElement deleteAppButton;

	// strong[contains(.,'Email Options')]/..//*[contains(text(),'Settings')]

	@FindBy(xpath = "//*[contains(text(),'Destroy App')]")

	// @FindBy(xpath = "//span[contains(.,'Destroy App')]")

	WebElement destroyAppButton;

	@FindBy(xpath = "//span[text() = 'Destroy App' and @class = 'disabled btn-link']")
	WebElement disabledDestroyAppButton;

	@FindBy(xpath = "//strong[contains(.,'Dispatch')]/..//*[contains(text(),'Settings')]")
	WebElement dispatchLink;

	WebDriver driver;

	@FindBy(xpath = "//strong[contains(.,'Edit & View')]/..//*[contains(text(),'Settings')]")
	WebElement editAndViewLink;

	@FindBy(xpath = "//span[contains(.,'Edit App')]")
	WebElement editAppButton;

	@FindBy(xpath = "//i[contains(@class,'fa fa-edit  fa-fw fa-lg')]")
	WebElement editDescription;

	@FindBy(id = "form_description")
	WebElement editDesriptionTextArea;

	// @FindBy(xpath = "//span[contains(.,'Email Options')]")
	@FindBy(xpath = "//strong[contains(.,'Email Options')]/..//*[contains(text(),'Settings')]")
	WebElement emailOptionsLink;

	// @FindBy(xpath = "//span[contains(.,'Integration Options')]")
	@FindBy(xpath = "//strong[contains(.,'Integration Options:')]/..//*[contains(text(),'Settings')]")
	WebElement integrationOptionsLink;

	@FindBy(xpath = "//span[contains(., 'Log Out')]")
	WebElement logOutButton;

	@FindBy(xpath = "//strong[contains(.,'Payment Options')]/..//a[contains(.,'Settings')]")
	WebElement paymentOptionsLink;

	@FindBy(xpath = "//strong[contains(.,'PDF Options:')]/..//*[contains(text(),'Settings')]")
	WebElement pdfOptionsLink;

	@FindBy(xpath = "//a[text() = 'Publish App']")
	WebElement publishAppLink;

	@FindBy(xpath = "//strong[contains(.,'Remember & Recall')]/..//*[contains(text(),'Settings')]")
	WebElement rememberAndRecallLink;

	@FindBy(xpath = "//a[@class = 'inline ']//div[contains(.,'Retire App')]")
	WebElement retireAppButton;

	@FindBy(id = "btn_Save")
	WebElement savebutton;

	// @FindBy(xpath = "//span[contains(.,'Submission No.')]")
	@FindBy(xpath = "//strong[contains(.,'Submission Number')]/..//a[contains(.,'Settings')]")
	WebElement submissionNumberLink;

	@FindBy(xpath = "//span[contains(.,'Submission Status')]")
	WebElement submissionStatusLink;

	// @FindBy(xpath = "//span[contains(.,'Table of Contents')]")
	@FindBy(xpath = "//strong[contains(.,'Table of Contents')]")
	WebElement tableOfContentsLink;

	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement toastMessage;

	@FindBy(xpath = "//strong[contains(.,'Table of Contents')]/..//a[contains(.,'Settings')]")
	WebElement tocSetting;

	@FindBy(xpath = "//*[@id='page-wrapper']/div[1]/div[2]/div[2]/strong")
	WebElement version;

	@FindBy(xpath = "//strong[contains(.,'Workflow:')]/..//a[contains(.,'Settings')]")
	WebElement workflowLink;

	@FindBy(xpath = "//strong[contains(.,'Workflow')]/..//a[contains(.,'Settings')]")
	List<WebElement> workflowLinkList;

	public EditAppPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public CreateAppPage clickAppLink() {
		appsLink.click();
		return new CreateAppPage(driver);
	}

	public EditCompressionOptionsPage clickCompressionLink() {
		clickOnHiddenElement(clickCompressionLink, driver);
		return new EditCompressionOptionsPage(driver);
	}

	public EditAppPage clickCreateTemplateLink() {
		clickOnHiddenElement(createTemplateLink, driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(toastMessage));
		return this;
	}

	public EditDispatchEnabledPage clickDispatchLink() {
		clickOnHiddenElement(dispatchLink, driver);
		return new EditDispatchEnabledPage(driver);
	}

	public EditEditingOptionsPage clickEditAndViewLink() {
		clickOnHiddenElement(editAndViewLink, driver);
		return new EditEditingOptionsPage(driver);
	}

	public AppBuilderPage clickEditAppButton() {
		editAppButton.click();
		customWait(2);
		return new AppBuilderPage(driver);
	}

	public ShowEmailOptionsPage clickEmailOptions() {
		fluentWait(emailOptionsLink, driver).click();
		return new ShowEmailOptionsPage(driver);
	}

	public ShowIntegrationOptionsPage clickIntegerationOptions() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		jse.executeScript("window.scrollBy(0,500)", "");
		clickOnHiddenElement(integrationOptionsLink, driver);
		return new ShowIntegrationOptionsPage(driver);
	}

	public LoginPage clickLogOutButton() {
		clickOnHiddenElement(logOutButton, driver);
		return new LoginPage(driver);
	}

	public ShowPaymentOptionsPage clickPaymentOptionsLink() {
		clickOnHiddenElement(paymentOptionsLink, driver);
		return new ShowPaymentOptionsPage(driver);
	}

	public ShowPDFOptionsPage clickPDFOptions() {
		clickOnHiddenElement(pdfOptionsLink, driver);

		return new ShowPDFOptionsPage(driver);
	}

	public PublishAppPage clickPublishAppLink() {
		publishAppLink.click();
		return new PublishAppPage(driver);
	}

	public EditRemRecallPage clickRemAndRecallLink() {
		clickOnHiddenElement(rememberAndRecallLink, driver);
		return new EditRemRecallPage(driver);
	}

	public SubmissionNumberingPage clickSubmissionNumberLink() {
		fluentWait(submissionNumberLink, driver).click();
		return new SubmissionNumberingPage(driver);
	}

	public EditSubmissionStatusPage clickSubmissionStatusLink() {
		clickOnHiddenElement(clickSettingSubmissionStatus, driver);
		return new EditSubmissionStatusPage(driver);
	}

	public EditTableOfContentsPage clickTableOfContentsLink() {

		// tocSetting.click();
		clickOnHiddenElement(tocSetting, driver);
		return new EditTableOfContentsPage(driver);
	}

	public EditWorkflowPage clickWorkflowLink() {
		clickOnHiddenElement(workflowLink, driver);
		return new EditWorkflowPage(driver);
	}

	public CreateAppPage deleteApp() {
		fluentWait(deleteAppButton, driver);
		clickOnHiddenElement(deleteAppButton, driver);
		acceptAlertAfterdestory(driver);
		return new CreateAppPage(driver);
		
	}

	public EditAppPage deleteAppWithoutAlert() {
		fluentWait(deleteAppButton, driver);
		return this;
	}

	public CreateAppPage destroyApp() {
		moveToEleClick(driver, destroyAppButton);
		acceptAlertAfterdestory(driver);
		return new CreateAppPage(driver);
	}
	
	public CreateAppPage acceptAlertAfterdestory(WebDriver driver) {
		try {
			driver.switchTo().alert().accept();
			return new CreateAppPage(driver);
			// Alert exists and we switched to it
		} catch (NoAlertPresentException exception) {

			return new CreateAppPage(driver);
		}
	}
	
	public CreateAppPage destroyApp(String message, String stepNumber, ITestContext testContext) {
		clickOnHiddenElement(destroyAppButton, driver);
		boolean status = driver.switchTo().alert().getText().contains(message);
		reportLog(status, testContext.getName(), "Verify message", stepNumber, "Pop up message appears: " + message);
		org.testng.Assert.assertTrue(status);

		acceptAlertAfterdestory(driver);
		return new CreateAppPage(driver);
	}

	public void editAppDescription(String enterText)

	{
		editDescription.click();
		editDesriptionTextArea.sendKeys(enterText);
		savebutton.click();
	}

	public String getToastMessage() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(toastMessage));
		return toastMessage.getText();
	}

	public String getVersion() {
		return version.getText();
	}

	public boolean isCompressionLinkDisplayed() {

		return compressionLink.isDisplayed();
	}

	public boolean isDestroyAppButtonDisplayed() {
		return destroyAppButton.isDisplayed();
	}

	public boolean isDisabledDestroyAppButtonDisplayed() {
		return destroyAppButton.isDisplayed();
	}

	public boolean isEditAppDisplayed() {
		return editAppButton.isDisplayed();
	}

	public boolean isPaymentOptionsDisplayed() {
		return paymentOptionsLink.isDisplayed();
	}

	public boolean isTableOfContentsLinkDisplayed() {
		return tableOfContentsLink.isDisplayed();
	}
	public boolean isWorkflowLinkDisplayed() {
		return workflowLinkList.size() < 1;
	}
	/**
	 * moves user's mouse to the Retire button
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public EditAppPage moveMouseToRetireButton() throws InterruptedException {

		moveMouseToElement(retireAppButton, driver);
		customWait(2); // wait for modal to appear
		return this;
	}

	public void updatedEditAppDescription(String enterText)

	{
		editDescription.click();
		editDesriptionTextArea.clear();
		editDesriptionTextArea.sendKeys(enterText);
		savebutton.click();
	}

	/**
	 * When hovering over a button, this verifies the text appears on the page
	 * 
	 * @param bodyText
	 * @return
	 */
	public boolean verifyAppModalText(String bodyText) {
		verifyTextOnPagePresent(bodyText, driver);
		return bodyText.contains(bodyText);
	}

	public boolean verifyMsg(String msg)

	{
		boolean mesg = toastMessage.getText().equals(msg);
		return mesg;
	}

}
