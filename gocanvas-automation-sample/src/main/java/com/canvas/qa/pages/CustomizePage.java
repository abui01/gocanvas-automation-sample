package com.canvas.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CustomizePage extends BasePage {

	@FindBy(id = "company_date_format")
	WebElement accountDateformat;

	@FindBy(xpath = "//span[text() = 'Cancel']")
	private WebElement cancelButton;

	@FindBy(xpath = "//input[@id = 'company_departments_enabled' and @type= 'checkbox']//following-sibling::ins")
	WebElement departmentFunctionalityCheckbox;

	WebDriver driver;

	String editButtonXpath = "//label[contains(.,'%s')]//following-sibling::div[2]/a/i";

	@FindBy(id = "company_email_from")
	WebElement emailSubmissionsFrom;

	@FindBy(xpath = "//input[@id = 'company_notification_app_published_enabled' and @type= 'checkbox']")
	WebElement enableAppPublishedNotificationCheckbox;

	@FindBy(xpath = "//input[@id = 'company_testing_enabled' and @type= 'checkbox']")
	WebElement enableCompanyTestingCheckbox;

	@FindBy(xpath = "//input[@id = 'company_departments_enabled' and @type= 'checkbox']")
	WebElement enableDepartmentFunctionalityCheckbox;

	@FindBy(xpath = "//input[@id = 'company_notification_dispatch_enabled' and @type= 'checkbox']")
	WebElement enableDispatchCheckbox;

	@FindBy(xpath = "//input[@id = 'company_hipaa_compliant' and @type= 'checkbox']")
	WebElement enableHIPPACompliantCheckbox;

	@FindBy(xpath = "//input[@id = 'company_notification_reference_data_enabled' and @type= 'checkbox']")
	WebElement enableRefDataNotificationCheckbox;

	String labelNameXpath1 = "//strong[contains(.,'%s')]";

	@FindBy(xpath = "//strong[contains(.,'Custom Account Logo')]//following-sibling::div[1]/div/div/img")
	WebElement logoImage;

	@FindBy(id = "btn_Save")
	private WebElement saveButton;

	public CustomizePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public CustomizePage clickCancelButton() {
		cancelButton.click();
		return this;
	}

	public CustomizePage clickDepartmentFunctionalityCheckbox() {
		clickOnHiddenElement(departmentFunctionalityCheckbox, driver);
		return this;
	}

	public CustomizePage clickEditButton(String label) {
		WebElement element = driver.findElement(By.xpath(String.format(editButtonXpath, label)));
		clickOnHiddenElement(element, driver);
		customWait(2);
		return this;
	}

	public CustomizePage clickSaveButton() {
		saveButton.click();
		return this;
	}

	public String getSelectedAccountDateFormat() {
		Select dropdown = new Select(accountDateformat);
		return dropdown.getFirstSelectedOption().getText();
	}

	public String getSelectedEmailSubmissionsFrom() {
		Select dropdown = new Select(emailSubmissionsFrom);
		return dropdown.getFirstSelectedOption().getText();
	}

	public boolean isAppPublishedNotificationCheckboxSelected() {
		return enableAppPublishedNotificationCheckbox.getAttribute("checked").equalsIgnoreCase("true");
	}

	public boolean isCompanyTestingCheckboxEnabled() {
		return enableCompanyTestingCheckbox.isSelected();
	}

	public boolean isDepartmentFunctionalityCheckboxEnabled() {
		return enableDepartmentFunctionalityCheckbox.isSelected();
	}

	public boolean isDispatchCheckboxEnabled() {
		return enableDispatchCheckbox.getAttribute("checked").equalsIgnoreCase("true");
	}

	public boolean isHIPPACompliantCheckboxEnabled() {
		return enableHIPPACompliantCheckbox.isSelected();
	}

	public boolean isLabelsDisplayed(String labelName) throws InterruptedException {
		return driver.findElement(By.xpath(String.format(labelNameXpath1, labelName))).isDisplayed();
	}

	public boolean isLogoImageDisplayed() {
		return logoImage.isDisplayed();
	}

	public boolean isRefDataCheckboxEnabled() {
		return enableRefDataNotificationCheckbox.getAttribute("checked").equalsIgnoreCase("true");
	}
}
