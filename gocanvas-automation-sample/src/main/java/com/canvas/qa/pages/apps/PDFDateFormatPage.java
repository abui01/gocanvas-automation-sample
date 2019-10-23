package com.canvas.qa.pages.apps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;

/**
 * @author anna.marek
 *
 */
public class PDFDateFormatPage extends BasePage

{

	WebDriver driver;

	@FindBy(id = "form_pdf_date_format")
	WebElement pdfDateFormatDropdown;

	@FindBy(id = "btn_Save")
	WebElement saveButton;

	@FindBy(xpath = "//option[@value = 'YYYY/MM/DD')]")
	WebElement yyyymmddOption;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public PDFDateFormatPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public PDFDateFormatPage fillOutPDFDateFormat() throws InterruptedException {
		fluentWait(pdfDateFormatDropdown, driver).click();
		selectDropDown(pdfDateFormatDropdown, "YYYY/MM/DD", driver);
		fluentWait(saveButton, driver).click();
		return this;
	}

}
