package com.canvas.qa.pages.apps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.canvas.qa.pages.BasePage;

/**
 * @author shalini.pathak
 *
 */
public class ShowEmailOptionsPage extends BasePage {

	@FindBy(id = "form_recipient_type_2")
	WebElement bccFieldRadioButton;

	@FindBy(xpath = "//span[contains(.,'Cancel')]")
	WebElement cancelButton;

	@FindBy(id = "form_user_specified_email")
	WebElement canEmailPDFtoAnyoneRadioButton;

	WebDriver driver;

	@FindBy(id = "form_email_list")
	WebElement emailList;

	@FindBy(id = "form_delivery_option_1")
	WebElement linkPDFDeliveryOptionsRadioButton;

	String optionForMobileUsersXpath = "//label[contains(.,'%s') ]/input[@type = 'radio']";

	@FindBy(id = "btn_Save")
	WebElement saveButton;

	@FindBy(xpath = "//select[@name = 'email_report_definition']")
	WebElement selectPDFToEmail;

	@FindBy(id = "form_show_autoresults")
	WebElement showWordsAutoResultsCheckBox;

	@FindBy(id = "uploaded_file")
	WebElement uploadAttachmentButton;

	public ShowEmailOptionsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public ShowEmailOptionsPage clickAppsBreadCrumb() throws InterruptedException {
		WebElement breadcrumb = driver.findElement(By.className("breadcrumb"));
		WebElement appLink = breadcrumb.findElement(By.linkText("Apps"));
		fluentWait(appLink, driver).click();

		return this;
	}

	public EditAppPage clickCancelButton() {
		cancelButton.click();
		return new EditAppPage(driver);
	}

	public String getEmailList() {
		return emailList.getText();
	}

	public String getPDFToEmailOption() {
		Select dropdown = new Select(selectPDFToEmail);
		return dropdown.getFirstSelectedOption().getText();
	}

	public boolean isMobileOptionChecked(String option) {
		WebElement element = driver.findElement(By.xpath(String.format(optionForMobileUsersXpath, option)));
		return element.isSelected();
	}

	public ShowEmailOptionsPage uploadLogo() throws InterruptedException {
		fluentWait(uploadAttachmentButton, driver)
				.sendKeys(System.getProperty("user.dir") + "/src/main/resources/PDF_LOGO.JPG");
		fluentWait(saveButton, driver).click();
		return this;
	}

	public boolean verifyBCCFieldIsSelected() throws InterruptedException {

		if (bccFieldRadioButton.isSelected()) {
			return true;
		}

		return false;
	}

	public boolean verifyCanEmailPDFtoAnyoneIsSelected() throws InterruptedException {

		if (canEmailPDFtoAnyoneRadioButton.isSelected()) {
			return true;
		}

		return false;
	}

	public boolean verifyDefaultEmailDisplays(String userLogin) throws InterruptedException {

		if (getEmailList().equalsIgnoreCase(userLogin)) {
			return true;
		}

		return false;
	}

	public boolean verifyDefaultEmailisEmpty() throws InterruptedException {

		if (getEmailList().isEmpty()) {
			return true;
		}

		return false;
	}

	public boolean verifyLinkPDFDeliveryOptionsIsSelected() throws InterruptedException {

		if (linkPDFDeliveryOptionsRadioButton.isSelected()) {
			return true;
		}

		return false;
	}

	public boolean verifyLogoUploadTextPresent(String imageTxt) throws InterruptedException {

		if (verifyTextOnPagePresent(imageTxt, driver)) {
			return true;
		}

		return false;
	}

	public boolean verifyShowWordsAutoResultsCheckBoxIsSelected() throws InterruptedException {

		if (showWordsAutoResultsCheckBox.isSelected()) {
			return true;
		}

		return false;
	}

}
