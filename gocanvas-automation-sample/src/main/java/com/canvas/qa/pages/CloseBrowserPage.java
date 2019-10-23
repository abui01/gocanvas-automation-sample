package com.canvas.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CloseBrowserPage extends BasePage

{
	WebDriver driver;

	public CloseBrowserPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	/**
	 * @return This method shuts down browser window.
	 */
	public CloseBrowserPage CloseBrowser() {
		 driver.close();
		 driver.quit();
		return this;
	}
}
