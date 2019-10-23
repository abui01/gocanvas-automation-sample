package com.canvas.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.apps.EditAppPage;

public class EditWorkflowPage extends BasePage {

	@FindBy(xpath = "//a[contains(.,'Add New Handoff')]")
	WebElement addNewHandOffButton;

	String appNameLinkXpath = "//a[text() = '%s']";

	@FindBy(xpath = "//input[@id = 'workflow_new_handoff_attributes__notification_type_both_0']/following-sibling::ins")
	WebElement bothRadioButton;

	@FindBy(xpath = "//input[@id = 'workflow_final']")
	WebElement checkedFinalSubmitterRadioButton;

	@FindBy(xpath = "//input[@id = 'workflow_handoff']")
	WebElement checkedHandOffSubmitterRadioButton;

	String deleteIconXpath = "//span[contains(.,'%s')]//following-sibling::a/i";

	WebDriver driver;

	@FindBy(xpath = "//input[@id = 'workflow_new_handoff_attributes__can_edit_existing']/following-sibling::ins")
	WebElement editExistingDataCheckbox;

	@FindBy(xpath = "//input[@id = 'workflow_new_handoff_attributes__can_edit_existing']")
	WebElement editExistingDataCheckboxChecked;

	@FindBy(xpath = "//li[@class = 'token-input-dropdown-item2 token-input-selected-dropdown-item']")
	WebElement emailDDL;
	
	@FindBy(xpath = "//li[@class = 'token-input-dropdown-item2 token-input-selected-dropdown-item']")
	List<WebElement> emailDDLList;

	@FindBy(id = "workflow_new_handoff_attributes__email_field_id")
	WebElement emailFieldDDL;

	@FindBy(xpath = "//input[@id = 'email_option_dynamic_handoff_id_0_field']/following-sibling::ins")
	WebElement emailFieldRadioButton;

	@FindBy(xpath = "//div[@class = 'token-input-dropdown']/ul/li")
	List<WebElement> emailList;

	@FindBy(xpath = "//input[@id = 'email_option_dynamic_handoff_id_0_list']/following-sibling::ins")
	WebElement emailListRadioButton;

	@FindBy(id = "token-input-workflow_new_handoff_attributes__email_list")
	WebElement emailListTextBox;

	@FindBy(id = "token-input-workflow_new_handoff_attributes__email_list")
	List<WebElement> emailListTextBoxList;

	String emailListXpath = "//div[@class = 'token-input-dropdown']/ul/li[contains(.,'%s')]";

	@FindBy(xpath = "//input[@id = 'workflow_new_handoff_attributes__notification_type_email_0']/following-sibling::ins")
	WebElement emailRadioButton;

	@FindBy(xpath = "//input[@id = 'workflow_new_handoff_attributes__notification_type_email_0']")
	WebElement emailRadioButtonSelected;

	@FindBy(xpath = "//input[@id = 'workflow_enabled']")
	WebElement enableWorkflowCheckbox;

	@FindBy(xpath = "//div[@id = 'workflow_options']//div[@id = 'errorExplanation']/h2")
	WebElement errorFieldHeading;

	@FindBy(xpath = "//div[@id = 'errorExplanation']/h2")
	WebElement errorHeading;

	@FindBy(xpath = "//div[@id = 'errorExplanation']/ul/li")
	List<WebElement> errorMessages;

	@FindBy(xpath = "//div[@id = 'errorExplanation']/p")
	WebElement errorSubject;

	@FindBy(xpath = "//input[@id = 'workflow_final']/following-sibling::ins")
	WebElement finalSubmitterRadioButton;

	@FindBy(id = "workflow_new_handoff_attributes__sheet_id")
	WebElement handOffAfterScreenDDL;

	@FindBy(id = "workflow_new_handoff_attributes__sheet_id")
	List<WebElement> handOffAfterScreenDDLList;

	@FindBy(id = "workflow_new_handoff_attributes__state_name")
	WebElement handOffNameTextBox;

	@FindBy(id = "workflow_new_handoff_attributes__state_name")
	List<WebElement> handOffNameTextBoxList;

	@FindBy(xpath = "//input[@id = 'workflow_handoff']/following-sibling::ins")
	WebElement handOffSubmitterRadioButton;

	@FindBy(xpath = "//input[@id = 'workflow_new_handoff_attributes__start_after_sheet_id_after']/following-sibling::ins")
	WebElement nextUserAfterRadioButton;

	@FindBy(xpath = "//input[@id = 'workflow_new_handoff_attributes__start_after_sheet_id_begining']/following-sibling::ins")
	WebElement nextUserBeginningRadioButton;

	@FindBy(xpath = "//input[@id = 'workflow_new_handoff_attributes__start_after_sheet_id_begining']")
	WebElement nextUserBeginningRadioButtonSelected;

	@FindBy(xpath = "//input[@id = 'workflow_new_handoff_attributes__can_reject']/following-sibling::ins")
	WebElement optionToRejectCheckbox;

	@FindBy(xpath = "//input[@id = 'workflow_new_handoff_attributes__can_reject']")
	WebElement optionToRejectCheckboxChecked;

	@FindBy(xpath = "//input[@id = 'workflow_original']/following-sibling::ins")
	WebElement originalSubmitterRadioButton;

	@FindBy(xpath = "//input[@id = 'workflow_new_handoff_attributes__notification_type_push_0']/following-sibling::ins")
	WebElement pushNotificationRadioButton;

	@FindBy(xpath = "//span[text() = 'Save']")
	WebElement saveButton;

	@FindBy(className = "toast")
	WebElement toast;

	public EditWorkflowPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public EditWorkflowPage acceptPopUpMessage() {
		driver.switchTo().alert().accept();
		return this;
	}

	public EditAppPage clickAppNameLink(String appName) {
		clickOnHiddenElement(driver.findElement(By.xpath(String.format(appNameLinkXpath, appName))), driver);
		return new EditAppPage(driver);
	}

	public EditWorkflowPage clickBothRadioButton() {
		clickOnHiddenElement(bothRadioButton, driver);
		return this;
	}

	public EditWorkflowPage clickDeleteIcon(String text) {
		WebElement element = driver.findElement(By.xpath(String.format(deleteIconXpath, text)));
		clickOnHiddenElement(element, driver);
		customWait(5);
		return this;
	}

	public EditWorkflowPage clickEditExistingDataCheckbox() {
		clickOnHiddenElement(editExistingDataCheckbox, driver);
		return this;
	}

	public EditWorkflowPage clickEmailDDLText() {
		//fluentWait(emailDDL, driver).click();
		waitForVisbility(driver, emailDDL, 30).click();
		return this;
	}

	public EditWorkflowPage clickEmailFieldRadioButton() {
		clickOnHiddenElement(emailFieldRadioButton, driver);
		return this;
	}

	public EditWorkflowPage clickEmailListRadioButton() {
		clickOnHiddenElement(emailListRadioButton, driver);
		return this;
	}

	public EditWorkflowPage clickEmailListText(String text) {
		WebElement element = driver.findElement(By.xpath(String.format(emailListXpath, text)));
		clickOnHiddenElement(element, driver);
		customWait(5);
		return this;
	}

	public EditWorkflowPage clickEmailRadioButton() {
		clickOnHiddenElement(emailRadioButton, driver);
		return this;
	}


	public EditWorkflowPage clickEnableWorkflowCheckbox(boolean checkstatus) {
		handleCheckBox(enableWorkflowCheckbox, checkstatus, driver);
		//clickOnHiddenElement(fluentWait(legacyCkbox, driver), driver);

		return this;
	}

	public EditWorkflowPage clickFinalSubmitterRadioButton() {
		clickOnHiddenElement(finalSubmitterRadioButton, driver);
		return this;
	}

	public EditWorkflowPage clickHandoffSubmitterRadioButton() {
		clickOnHiddenElement(handOffSubmitterRadioButton, driver);
		return this;
	}

	public EditWorkflowPage clickNewHandOffButton() {
		clickOnHiddenElement(addNewHandOffButton, driver);
		return this;
	}

	public EditWorkflowPage clickNextUserAfterRadioButton() {
		clickOnHiddenElement(nextUserAfterRadioButton, driver);
		return this;
	}

	public EditWorkflowPage clickNextUserBeginningRadioButton() {
		clickOnHiddenElement(nextUserBeginningRadioButton, driver);
		return this;
	}

	public EditWorkflowPage clickOptionToRejectCheckbox() {
		clickOnHiddenElement(optionToRejectCheckbox, driver);
		return this;
	}

	public EditWorkflowPage clickOriginalSubmitterRadioButton() {
		clickOnHiddenElement(originalSubmitterRadioButton, driver);
		return this;
	}

	public EditWorkflowPage clickPushNotificationRadioButton() {
		clickOnHiddenElement(pushNotificationRadioButton, driver);
		return this;
	}

	public EditAppPage clickSaveButton() {
		clickOnHiddenElement(saveButton, driver);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(toast));
		return new EditAppPage(driver);
	}

	public EditWorkflowPage clickSaveButtonForInvalidData() {
		clickOnHiddenElement(saveButton, driver);
		return this;
	}

	public EditWorkflowPage enterEmailList(String text) {
		emailListTextBox.clear();
		emailListTextBox.sendKeys(text);
		return this;
	}

	public EditWorkflowPage enterEmailList(String text, int index) {
		emailListTextBoxList.get(index).clear();
		emailListTextBoxList.get(index).sendKeys(text);
		return this;
	}

	public EditWorkflowPage enterHandOffName(String text) {
		handOffNameTextBox.clear();
		handOffNameTextBox.sendKeys(text);
		return this;
	}

	public EditWorkflowPage enterHandOffName(String text, int index) {
		handOffNameTextBoxList.get(index).sendKeys(text);
		return this;
	}

	public String getEmailDDLText() {
		//return fluentWait(emailDDL, driver).getText();
		return waitForVisbility(driver, emailDDL, 30).getText();
	}
	
	public boolean verifyEmailDDLPresent() {
		customWait(10); //waits for results to load
		int DDLcount = emailDDLList.size();
		if (DDLcount>0) {
			return true;
		}
		else {
			return false;
		}
	}

	public ArrayList<String> getEmailFieldList() {
		Select dropdown = new Select(emailFieldDDL);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}

	public int getEmailListSize() {
		customWait(15); //often fails due to database not having sufficient time to load results
		return emailList.size();
	}

	public String getErrorFieldHeading() {
		return errorFieldHeading.getText();
	}

	public String getErrorHeading() {
		return errorHeading.getText();
	}

	public List<String> getErrorMessages() {
		List<String> errorMessagesText = new ArrayList<String>();
		for (WebElement element : errorMessages) {
			errorMessagesText.add(element.getText());
		}
		return errorMessagesText;
	}

	public String getErrorSubject() {
		return errorSubject.getText();
	}

	public String getHandOffName() {
		return handOffNameTextBox.getAttribute("value");
	}

	public ArrayList<String> getHandOffScreenList() {
		Select dropdown = new Select(handOffAfterScreenDDL);
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}

	public ArrayList<String> getHandOffScreenList(int index) {
		Select dropdown = new Select(handOffAfterScreenDDLList.get(index));
		ArrayList<String> appList = new ArrayList<String>();
		for (WebElement element : dropdown.getOptions()) {
			appList.add(element.getText());
		}
		return appList;
	}

	public String getPopUpMessage() {
		return driver.switchTo().alert().getText();
	}

	public String getSelectedEmailField() {
		Select app = new Select(emailFieldDDL);
		return app.getFirstSelectedOption().getText();
	}

	public String getSelectedHandOffAfter() {
		Select app = new Select(handOffAfterScreenDDL);
		return app.getFirstSelectedOption().getText();
	}

	public String getToastMsg() throws NoSuchElementException {
		return (toast).getText();
	}

	public boolean isEditExistingDataCheckboxChecked() {
		String value = editExistingDataCheckboxChecked.getAttribute("checked");
		return (value.equalsIgnoreCase("true"));
	}

	public boolean isEmailRadioButtonSelected() {
		String value = emailRadioButtonSelected.getAttribute("checked");
		return (value.equalsIgnoreCase("true"));
	}

	public boolean isEnableWorkflowChecked() {
		return enableWorkflowCheckbox.isSelected();
	}

	public boolean isFinalSubmitterSelected() {
		String value = checkedFinalSubmitterRadioButton.getAttribute("checked");
		return (value.equalsIgnoreCase("true"));
	}

	public boolean isHandOffSubmitterSelected() {
		String value = checkedHandOffSubmitterRadioButton.getAttribute("checked");
		return (value.equalsIgnoreCase("true"));
	}

	public boolean isNextUserBeginningRadioButtonSelected() {
		String value = nextUserBeginningRadioButtonSelected.getAttribute("checked");
		return (value.equalsIgnoreCase("true"));
	}

	public boolean isOptionToRejectCheckboxChecked() {
		String value = optionToRejectCheckboxChecked.getAttribute("checked");
		return (value.equalsIgnoreCase("true"));
	}

	public EditWorkflowPage selectEmailField(String option) {
		Select app = new Select(emailFieldDDL);
		app.selectByVisibleText(option);
		return this;
	}

	public EditWorkflowPage selectHandOffAfter(String option) {
		Select app = new Select(handOffAfterScreenDDL);
		app.selectByVisibleText(option);
		return this;
	}

	public EditWorkflowPage selectHandOffAfter(String option, int index) {
		Select app = new Select(handOffAfterScreenDDLList.get(index));
		app.selectByVisibleText(option);
		return this;
	}

}
