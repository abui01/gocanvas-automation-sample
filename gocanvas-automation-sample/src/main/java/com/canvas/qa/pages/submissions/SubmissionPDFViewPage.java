package com.canvas.qa.pages.submissions;


import java.util.List;


import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.canvas.qa.pages.BasePage;

public class SubmissionPDFViewPage  extends BasePage{
	
	public SubmissionPDFViewPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}
	
	
	WebDriver driver;
	
	@FindBy(xpath = "//div[@class = 'textLayer']/div")
	List<WebElement> pdfListOfElements;
	
	@FindBy(xpath = "//a[@class = 'btn btn-primary mq-try-free']")
	WebElement tryitFreeButton;
	
	
	public  boolean verifyElementsInPdf(String pdfElement) {
		for (int i = 0; i < pdfListOfElements.size(); i++) {
			if ( pdfListOfElements.get(i).getText().contains(pdfElement)) {
				
				return true;
			}
		}

		throw new NoSuchElementException(pdfElement + " not found on the PDF.");
		
	}
	
	public boolean verifyTryItFreeButtonIsDisplayed () {
		return tryitFreeButton.isDisplayed();
	}
}
