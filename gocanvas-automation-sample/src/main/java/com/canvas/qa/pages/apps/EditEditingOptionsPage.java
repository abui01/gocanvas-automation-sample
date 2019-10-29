package com.canvas.qa.pages.apps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.CreateAppPage;

public class EditEditingOptionsPage extends BasePage {

	@FindBy(xpath = "//input[@id = 'd_admins']")
	WebElement adminDeleteCheckbox;

	@FindBy(xpath = "//input[@id = 'admins']")
	WebElement adminEditCheckbox;

	@FindBy(xpath = "//a[text() = 'Apps']")
	WebElement appsLink;

	@FindBy(xpath = "//input[@id = 'd_designers']")
	WebElement designersDeleteCheckbox;

	@FindBy(xpath = "//input[@id = 'designers']")
	WebElement designersEditCheckbox;

	WebDriver driver;

	@FindBy(xpath = "//input[@id = 'mobile']")
	WebElement mobileEditCheckbox;

	@FindBy(xpath = "//input[@id = 'view_pdf_mobile']")
	WebElement mobileViewPDFCheckbox;

	@FindBy(xpath = "//input[@id = 'd_reporters']")
	WebElement reportersDeleteCheckbox;

	@FindBy(xpath = "//input[@id = 'reporters']")
	WebElement reportersEditCheckbox;

	@FindBy(xpath = "//input[@id = 'reset_date_on_mobile_edit']")
	WebElement resetDateEditMobileCheckbox;

	@FindBy(xpath = "//input[@id = 'reset_date_on_web_edit']")
	WebElement resetDateEditWebCheckbox;

	@FindBy(xpath = "//input[@id = 'd_user']")
	WebElement userDeleteCheckbox;

	@FindBy(xpath = "//input[@id = 'user']")
	WebElement userEditCheckbox;

	public EditEditingOptionsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public CreateAppPage clickAppLink() {
		appsLink.click();
		return new CreateAppPage(driver);
	}

	public boolean isAdminDeleteChecked() {
		return adminDeleteCheckbox.isSelected();
	}

	public boolean isAdminEditChecked() {
		return adminEditCheckbox.isSelected();
	}

	public boolean isDesignersDeleteChecked() {
		return designersDeleteCheckbox.isSelected();
	}

	public boolean isDesignersEditChecked() {
		return designersEditCheckbox.isSelected();
	}

	public boolean isMobileEditChecked() {
		return mobileEditCheckbox.isSelected();
	}

	public boolean isMobileViewPDFChecked() {
		return mobileViewPDFCheckbox.isSelected();
	}

	public boolean isReportersDeleteChecked() {
		return reportersDeleteCheckbox.isSelected();
	}

	public boolean isReportersEditChecked() {
		return reportersEditCheckbox.isSelected();
	}

	public boolean isResetDateEditMobileChecked() {
		return resetDateEditMobileCheckbox.isSelected();
	}

	public boolean isResetDateEditWebChecked() {
		return resetDateEditWebCheckbox.isSelected();
	}

	public boolean isUserDeleteChecked() {
		return userDeleteCheckbox.isSelected();
	}

	public boolean isUserEditChecked() {
		return userEditCheckbox.isSelected();
	}
}
