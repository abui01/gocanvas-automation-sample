package com.canvas.qa.pages.dispatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

public class DispatchItemDetailPage extends BasePage {

	@FindBy(xpath = "//a[@id = 'advSearch']/i")
	WebElement advSearchButton;

	@FindBy(xpath = "//span[contains(.,'Dispatch')]")
	WebElement clickondispatch;
	@FindBy(xpath = "//a[@href='/dispatch_items/963']")
	WebElement clickonrecord14;
	@FindBy(xpath = "//a[@href='/dispatch_items/963']")
	List<WebElement> clickonrecord14List;

	@FindBy(xpath = "//a[contains(.,'69')]")
	WebElement clickonrecord69;

	@FindBy(xpath = "//a[contains(.,'71')]")
	WebElement clickonrecord71;
	// @FindBy(xpath = "//a[contains(.,'15')]")

	WebDriver driver;

	@FindBy(linkText = "Edit")
	WebElement editbutton;
	@FindBy(xpath = "//div[@class='toast-message']")
	WebElement editdispatcherror;

	@FindBy(xpath = "//i[@class = 'fa fa-search text-muted']")
	WebElement searchButton;

	@FindBy(id = "common_search_field")
	WebElement searchTextBox;
	// @FindBy(xpath = "//a[contains(.,'Edit')]")
	// WebElement editbutton;
	@FindBy(xpath = "//a[contains(.,'ID')]")
	WebElement sortcolumn;

	public DispatchItemDetailPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public String checkForFirstEditButton() throws InterruptedException {
		String successText;
		/*
		 * clickondispatch.click(); //Thread.sleep(1000);
		 * 
		 * JavascriptExecutor js = (JavascriptExecutor) driver;
		 * js.executeScript("arguments[0].click();", clickonrecord69);
		 * Thread.sleep(20000);
		 */

		//JavascriptExecutor jse = (JavascriptExecutor) driver;
		//jse.executeScript("arguments[0].click();", clickondispatch);
		clickOnHiddenElement(fluentWait(clickondispatch, driver), driver);
		
		//Thread.sleep(20000);
		fluentWait(searchTextBox, driver).click();
		searchTextBox.sendKeys("TC7564 step 1");
		//Thread.sleep(20000);
		fluentWait(searchButton, driver).click();
		//Thread.sleep(20000);
		customWait(10); //added to wait for search results
		
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript("arguments[0].click();", clickonrecord69);
		clickOnHiddenElement(fluentWait(clickonrecord69, driver), driver);

		WebElement abc = (editbutton);
		// String ab1 =abc.getText();

		successText = fluentWait(abc, driver).getText();

		return successText;
	}

	public String checkForSecondditButton() throws InterruptedException {
		String successText;

		/*
		 * clickondispatch.click(); //Thread.sleep(1000);
		 * 
		 * JavascriptExecutor js = (JavascriptExecutor) driver;
		 * js.executeScript("arguments[0].click();", clickonrecord71);
		 */
		//Thread.sleep(30000);

		//JavascriptExecutor jse = (JavascriptExecutor) driver;
		//jse.executeScript("arguments[0].click();", clickondispatch);
		clickOnHiddenElement(fluentWait(clickondispatch, driver), driver);
		
		//Thread.sleep(20000);
		fluentWait(searchTextBox, driver).click();
		searchTextBox.sendKeys("TC7564, step 2");
		//Thread.sleep(20000);
		fluentWait(searchButton, driver).click();
		customWait(10); //added to wait for search results
		// advSearchButton.click();
		//Thread.sleep(30000);
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript("arguments[0].click();", clickonrecord71);
		clickOnHiddenElement(fluentWait(clickonrecord71, driver), driver);
		WebElement abc = (editbutton);
		successText = fluentWait(abc, driver).getText();
		return successText;
	}

	/*
	 * Verify User not able to edit the Dispatch having status is "Received"
	 */

	private boolean compareList(String[] links, List<String> pageLinks) {

		List<String> roleLinks = Arrays.asList(links);
		boolean flag = true;
		if (roleLinks.size() != pageLinks.size()) {

			return false;
		}

		Collections.sort(roleLinks);
		Collections.sort(pageLinks);

		for (int i = 0; i < roleLinks.size(); i++) {
			if (!roleLinks.get(i).equals(pageLinks.get(i))) {
				System.out
						.println(" link mismatched roleLinks:" + roleLinks.get(i) + ", pageLinks: " + pageLinks.get(i));
				flag = false;
				break;
			}

		}
		return flag;
	}

	public boolean RetriveFirstDispatchdetaillink(String[] links) throws IOException, InterruptedException

	{
		//Thread.sleep(20000);
		boolean flag = true;
		clickOnHiddenElement(fluentWait(clickondispatch, driver), driver);
		//Thread.sleep(20000);
		fluentWait(searchTextBox, driver).click();
		searchTextBox.sendKeys("TC7564 step 1");
		//Thread.sleep(20000);
		fluentWait(searchButton, driver).click();
		customWait(10); //needs to be here to load results after search
		clickOnHiddenElement(fluentWait(clickonrecord69, driver), driver);

		customWait(4); //wait to load results on page
		List<WebElement> allLinks = driver.findElements(By.xpath(".//*[@class='table-responsive']//tr"));

		customWait(4);

		List<String> pageLinks = new ArrayList<>();

		for (WebElement p : allLinks) {

			{
				pageLinks.add(p.getText());
				customWait(4);

			}
		}

		flag = compareList(links, pageLinks);

		return flag;
	}

	public String RetriveReceivedDispatchdetaillink() throws IOException, InterruptedException

	{
		//WebDriverWait wait = new WebDriverWait(driver, 20);
		//wait.until(ExpectedConditions.elementToBeClickable(clickondispatch));
		fluentWait(clickondispatch, driver).click();

		//wait.until(ExpectedConditions.elementToBeClickable(sortcolumn));
		fluentWait(sortcolumn, driver).click();
		if (clickonrecord14List.size() == 0) {
			//wait.until(ExpectedConditions.elementToBeClickable(sortcolumn));
			fluentWait(sortcolumn, driver).click();
		}
		//wait.until(ExpectedConditions.elementToBeClickable(clickonrecord14));
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript("arguments[0].click();", clickonrecord14);
		clickOnHiddenElement(fluentWait(clickonrecord14, driver), driver);
		//Thread.sleep(9000);
		fluentWait(editbutton, driver).click();
		//Thread.sleep(2000);

		String successText;

		try {
			successText = fluentWait(editdispatcherror, driver).getText();
			customWait(2);
		} catch (Exception e) {
			e.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public boolean RetriveSecondDispatchdetaillink(String[] links) throws IOException, InterruptedException

	{
		//Thread.sleep(30000);
		boolean flag = true;
		clickOnHiddenElement(fluentWait(clickondispatch, driver), driver);
		//Thread.sleep(20000);
		fluentWait(searchTextBox, driver).click();
		searchTextBox.sendKeys("TC7564, step 2");
		//Thread.sleep(20000);
		fluentWait(searchButton, driver).click();
		customWait(10); //needs to be here because results are loading
		clickOnHiddenElement(fluentWait(clickonrecord71, driver), driver);
		customWait(2);
		List<WebElement> allLinks = driver.findElements(By.xpath(".//*[@class='table-responsive']//tr"));

		customWait(4);

		List<String> pageLinks = new ArrayList<>();

		for (WebElement p : allLinks) {

			{

				pageLinks.add(p.getText());

			}
		}

		flag = compareList(links, pageLinks);

		return flag;
	}

}
