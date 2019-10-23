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
public class PDFHeaderPage extends BasePage

{

	WebDriver driver;

	@FindBy(id = "form_print_header")
	WebElement pdfHeaderTextarea;

	@FindBy(id = "btn_Save")
	WebElement saveButton;

	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public PDFHeaderPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public PDFHeaderPage fillOutPDFHeader() throws InterruptedException {
		fluentWait(pdfHeaderTextarea, driver).sendKeys("fill out header test");
		fluentWait(saveButton, driver).click();
		return this;
	}

}
