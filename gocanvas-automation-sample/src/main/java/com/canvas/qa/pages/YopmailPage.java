package com.canvas.qa.pages;


import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;




public class YopmailPage extends BasePage {
	
	
	
	public YopmailPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

		
	}
	
	
	WebDriver driver;
	
	@FindBy (id = "login")
	WebElement yopmailEmailBar;
	
	@FindBy (xpath = "//input[@type = 'submit']")
	WebElement checkInboxButton;
	
	@FindBy (xpath = "(//a[@rel='nofollow'])[1]")
	WebElement beerStockRequisitionPdf;
	
	@FindBy (id = "affim")
	WebElement showPicturesButton;
	
	@FindBy (id = "affim")
	List<WebElement> showPicturesButtons;
	
	@FindBy(xpath = "//a[@href = 'javascript:suppr_mail()']")
	WebElement deleteMailButton;
	
	@FindBy(xpath = "//span[@class = 'lmf']")
	WebElement doNotReplyButton;
	
	@FindBy(xpath = "//img[@alt ='Gocanvas mailerlogo']")
	WebElement logo;
	
	
	
	public YopmailPage yopmailPdfVerification(String url, String userName) {
		driver.navigate().to(url);
		yopmailEmailBar.sendKeys(userName);
		checkInboxButton.click();
		driver.switchTo().frame("ifmail");
		
		//pre-cond test to verify "Show Pictures" button loaded:
		int buttonCount = showPicturesButtons.size();
		if (buttonCount<1)
		{
			driver.navigate().refresh();
			waitForVisbility(driver,showPicturesButton,30);
		}
		
		showPicturesButton.click();
		
		return this;
	}
	
   public boolean verifyGoCanvasMailerLogoIsDisplayed() {
		
	 WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(logo));
		
	   return logo.isDisplayed();
	
   }
   
   public YopmailPage clickOnPdf() {
		beerStockRequisitionPdf.click();
		
		return this;
   }
   
   public YopmailPage deleteMail() {
	   deleteMailButton.click();
	   return this;
   }

}
