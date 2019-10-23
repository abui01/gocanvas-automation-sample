package com.canvas.qa.pages;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.core.UrlListGather;
import com.canvas.util.PropertyUtils;
import com.google.common.base.Function;
import com.relevantcodes.extentreports.LogStatus;

public class BasePage extends UrlListGather {

	public static WebDriver driver;

	/**
	 * This method verifies whether or not the page contains a javascript error then
	 * logs it into a separate Excel sheet
	 * 
	 * @param driver
	 */
	public static void javaScriptErrorChecker(WebDriver driver) {
		List<WebElement> userEmailList = driver.findElements(By.id("user-info"));
		String emailID = "";
		if (userEmailList.size() > 0) {
			emailID = userEmailList.get(0).getText();

		}
		String url = driver.getCurrentUrl();
		Logs logs = driver.manage().logs();
		LogEntries logEntries = logs.get(LogType.BROWSER);
		for (LogEntry logEntry : logEntries) {
			if (logEntry.getMessage().toLowerCase().contains("error")) {
				enterJavaScriptLogs(emailID, url, "Error Message in Console:" + logEntry.getMessage());
			} else if (logEntry.getMessage().contains("Mixed Content:")) {
				enterMixedContentLogs(emailID, url, "Mixed Content Message in Console:" + logEntry.getMessage());
			} else if (logEntry.getMessage().toLowerCase().contains("warning")) {
				enterJavaScriptLogs(emailID, url, "Warning Message in Console:" + logEntry.getMessage());
			}
		}

	}

	/**
	 * This method dives through all the URLs and ensures they are returning a 200
	 * 
	 * @param driver
	 */

	public static void siteURLChecker(WebDriver driver) {
		String homePage = PropertyUtils.getProperty("app.url");
		HttpsURLConnection huc = null;
		int respCode = 200;
		String url = "";

		List<WebElement> links = driver.findElements(By.tagName("a"));
		Iterator<WebElement> it = links.iterator();
		while (it.hasNext()) {
			try {
				url = it.next().getAttribute("href");
			} catch (StaleElementReferenceException e) {
				continue;
			}
			if (url == null || url.isEmpty()) {
				continue;
			}
			if (!url.startsWith(homePage)) {
				continue;
			}
			try {
				huc = (HttpsURLConnection) (new URL(url).openConnection());
				huc.setRequestMethod("HEAD");
				huc.connect();
				respCode = huc.getResponseCode();
				if (respCode >= 400) {
					enterURL(url, respCode + " :is a broken link");
				} else {
					enterURL(url, respCode + " :is a valid link");
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/***
	 * This method clicks on hidden WebElement using JavascriptExecutor interface
	 * from Selenium
	 * 
	 * @param element
	 */
	public void clickOnHiddenElement(WebElement element, WebDriver driver) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method scroll to the element using using JavascriptExecutor interface
	 * 
	 * @param driver
	 * @param element
	 */
	public void scrollToEle(WebDriver driver, WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			fluentWait(element, 20, driver);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		}
	}

	/***
	 * This method waits for user given seconds
	 * 
	 * @param inSeconds
	 */
	public void customWait(int inSeconds) {
		try {
			Thread.sleep(inSeconds * 1000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * Waits for element to load before code execution
	 *
	 */

	public WebElement fluentWait(WebElement element, WebDriver driver) {
		WebElement targetElem = null;
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

			targetElem = wait.until(new Function<WebDriver, WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {
					return element;
				}
			});
		} catch (Exception ex) {
			org.testng.Assert.assertTrue(false);
		}
		return targetElem;
	}

	/***
	 * 
	 * /** Waits for element to load before code execution
	 * 
	 * @param element
	 * @param duration
	 * @param driver
	 * @return
	 */
	public WebElement fluentWait(WebElement element, long duration, WebDriver driver) {
		WebElement targetElem = null;
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(duration, TimeUnit.SECONDS)
					.pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

			targetElem = wait.until(new Function<WebDriver, WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {
					return element;
				}
			});
		} catch (Exception ex) {
			org.testng.Assert.assertTrue(false);
		}
		return targetElem;
	}

	/**
	 * This method takes webEelement and uses visibility of the elements to wait for
	 * the element to be visible.
	 * 
	 * @param element
	 * @param timeToWaitInSec
	 * @return
	 */
	public WebElement waitForVisbility(WebDriver driver, WebElement element, int timeToWaitInSec) {
		WebDriverWait wait = new WebDriverWait(driver, timeToWaitInSec);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * This method takes By locator and uses visibility of the elements to wait for
	 * the element to be visible.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForVisbility(WebDriver driver, By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * This method for checking an element is visible and enabled such that you can
	 * click it.
	 * 
	 * @param element
	 * @param timeout
	 * @return
	 */
	public WebElement waitForClickablility(WebDriver driver, WebElement element, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * This method for checking an element is visible and enabled such that you can
	 * click it.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForClickablility(WebDriver driver, By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	/**
	 * This method wait for page to load.
	 * 
	 * @param timeOutInSeconds
	 */
	public void waitForPageToLoad(WebDriver driver, long timeOutInSeconds) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		try {
			System.out.println("waiting for page to load...");
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(expectation);
		} catch (Throwable error) {
			System.out.println(
					"Timeout Waiting for Page Load Request To Complete After " + timeOutInSeconds + " seconds");
		}
	}

	/**
	 * This method use to move to the element.
	 * 
	 * @param element
	 */
	public void moveToElement(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}

	public void scrollToElementClick(WebDriver driver, WebElement element) {
		try {
			scrollToEle(driver, element);
			clickOnHiddenElement(element, driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dragAndDropElement(WebDriver driver, WebElement element, WebElement moveToElement) {
		waitForVisbility(driver, element, 10);
		waitForVisbility(driver, moveToElement, 10);
		Actions actions = new Actions(driver);
		actions.clickAndHold(element).pause(1500).moveToElement(moveToElement).pause(1500).release().build().perform();
	}

	/**
	 * This method use to move the the element and click. Throw Timeout exception
	 * then using JS scroll to element and click with JS this method build in
	 * BasePage class. the driver be called default when the method be called. this
	 * method using in POM. All page extend BasePage. the driver be calling be
	 * default.
	 * 
	 * @param element
	 */
	public void moveToEleClick(WebDriver driver, WebElement element) {
		try {
			fluentWait(element, 12, driver);
			Actions actions = new Actions(driver);
			actions.moveToElement(element).click().build().perform();
		} catch (TimeoutException e) {
			try {
				scrollToEle(driver, element);
				clickOnHiddenElement(element, driver);
			} catch (Exception e2) {
				e.printStackTrace();
			}
		} catch (NoSuchElementException e2) {
			try {
				scrollToEle(driver, element);
				clickOnHiddenElement(element, driver);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * wait for element be clickAble then try to click, throw time out exception and
	 * click with Java script fluenWait, polling the element for every 3secs to
	 * check the element.
	 * 
	 * @param driver
	 * @param element
	 */
	public void clickEle(WebDriver driver, WebElement element) {
		try {
			fluentWait(element, 25, driver);
			element.click();
		} catch (TimeoutException e) {
			try {
				clickOnHiddenElement(element, driver);
			} catch (Exception e2) {
				e.printStackTrace();
			}
		} catch (WebDriverException e2) {
			try {
				clickOnHiddenElement(element, driver);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method handles browser check-box element
	 * 
	 * @param checkBoxLocator
	 * @param isCheck
	 */
	public void handleCheckBox(WebElement element, boolean isCheck, WebDriver driver) {
		try {
			WebElement CheckBoxElem = element;
			boolean userChecboxState = CheckBoxElem.isSelected();

			if (isCheck == true)// user wanted to select
			{
				if (userChecboxState == true)// default selected
				{
					// do nothing
				} else // default Not selected
				{
					clickOnHiddenElement(CheckBoxElem, driver);
				}
			} else // user does not want to select
			{
				if (userChecboxState == true) // default selected
				{
					clickOnHiddenElement(CheckBoxElem, driver);
				} else {
					// do nothing
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/***
	 * This method enters user defined text to text field web-element fluentWait
	 * wait the element
	 * 
	 * @param by
	 * @param textValue
	 */
	public void enterTextField(By by, String textValue) {
		try {
			WebElement textFieldElem = driver.findElement(by);
			fluentWait(textFieldElem, driver);
			textFieldElem.clear();
			textFieldElem.sendKeys(textValue);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * This method enters user defined text to text field web-element, fluentWait
	 * wait the element
	 * 
	 * @param driver
	 * @param textFieldElem
	 * @param textValue
	 */
	public void enterTextField(WebDriver driver, WebElement textFieldElem, String textValue) {
		try {
			fluentWait(textFieldElem, driver);
			textFieldElem.clear();
			textFieldElem.sendKeys(textValue);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method clicks any user given element
	 * 
	 * @param by
	 */
	public void clickElement(By by) {
		try {
			waitForClickablility(driver, by, 10);
			WebElement element = driver.findElement(by);
			element.click();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method calculates current time and returns the value
	 * 
	 * @return String current time-stamp
	 */
	public String getCurrentTime() {
		String finalTimeStamp = null;
		try {
			Date date = new Date();
			String tempTime = (new Timestamp(date.getTime())).toString();
			finalTimeStamp = tempTime.replace(":", "").replace(".", "").replace(" ", "");
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
		return finalTimeStamp;
	}

	/***
	 * This method moves the mouse pointer to the given WebElement
	 * 
	 * @param toElement
	 */
	public void moveMouseToElement(WebElement toElement, WebDriver driver) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(toElement).build().perform();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/***
	 * This method move mouse to first given WebElement then to second given
	 * WebElement.
	 * 
	 * @param firstElem
	 * @param secondElem
	 */
	public void moveMouseToElement(WebElement firstElem, WebElement secondElem) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(firstElem).build().perform();
			customWait(1);
			action.moveToElement(secondElem).build().perform();
			customWait(1);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method switch the driver to iFrame section of the html source
	 * 
	 * @param by
	 */
	public void switchToIframe(By by) {
		try {
			WebElement iframeElement = driver.findElement(by);
			driver.switchTo().frame(iframeElement);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method switch back to original html content from iFrame section
	 */
	public void switchToDefault() {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method searches the target sub-string in given string message
	 * 
	 * @param target
	 * @param msg
	 * @return index of first target sub-string match
	 */
	public int searchStringInString(String target, String msg) {
		int targetIndex = 0;
		for (int i = -1; (i = msg.indexOf(target, i + 1)) != -1;) {
			targetIndex = i;
			break;
		}
		return targetIndex;
	}

	public void reportLog(boolean condition, String testId, String testCaseDescription, String stepnumber,
			String testStepDescription) {
		if (condition) {
			ReportManager.lognew(testId, testCaseDescription, LogStatus.PASS,
					"Step " + stepnumber + " " + testStepDescription);
		} else {
			ReportManager.lognew(testId, testCaseDescription, LogStatus.FAIL,
					"Step " + stepnumber + " " + testStepDescription);
		}
	}

	/***
	 * This method selects drop-down value (Used only for Select and option Html
	 * tags)
	 * 
	 * @param by
	 * @param optionValue
	 */
	public void selectDropDown(By by, String optionValue) {
		try {
			WebElement dropdownElem = driver.findElement(by);
			Select selectList = new Select(dropdownElem);
			selectList.selectByVisibleText(optionValue);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/***
	 * This method selects drop-down value (Used only for Select and option Html
	 * tags)
	 * 
	 * @param by
	 * @param optionValue
	 */
	public void selectDropDown(WebElement element, String optionValue, WebDriver driver) {
		try {
			waitForVisbility(driver, element, 10);
			Select selectList = new Select(element);
			selectList.selectByVisibleText(optionValue);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method wait element visibility then selects drop-down value by Index,
	 * 
	 * @param element
	 * @param selectByIndex
	 * @param driver
	 */
	public void selectDropDown(WebElement element, int selectByIndex, WebDriver driver) {
		try {
			waitForVisbility(driver, element, 10);
			Select selectList = new Select(element);
			selectList.selectByIndex(selectByIndex);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * The method wait for element visibility then return the first selected option
	 * by String
	 * 
	 * @param driver
	 * @param element
	 * @return
	 */
	public String getFirstSeleOption(WebDriver driver, WebElement element) {
		waitForVisbility(driver, element, 10);
		Select dropDown = new Select(element);
		return dropDown.getFirstSelectedOption().getText();
	}

	/**
	 * wait for element visibility then return text by String.
	 * 
	 * @param driver
	 * @param element
	 * @return
	 */
	public String getElementText(WebDriver driver, WebElement element) {
		waitForVisbility(driver, element, 10);
		return element.getText();
	}

	/**
	 * This method verifies if Text is present within the HTML body
	 * 
	 * @param text
	 */
	public boolean verifyTextOnPagePresent(String text, WebDriver driver) {
		WebElement body = driver.findElement(By.tagName("body"));
		String bodyText = body.getText();
		return bodyText.contains(text);
	}

	public void addDataInFields(String inputFieldXpath, String fieldID, String data) {
		WebElement element = driver.findElement(By.xpath(String.format(inputFieldXpath, fieldID)));
		fluentWait(element, driver).clear();
		fluentWait(element, driver).sendKeys(data);
	}

	public WebElement makeXpath(String inputFieldXpath, String fieldID) {
		WebElement element = driver.findElement(By.xpath(String.format(inputFieldXpath, fieldID)));
		return element;

	}

	/**
	 * This method using the file name instead '%s' which is String Xpath location.
	 * eleXpath: Is the Parameter. the String type xpath contain '%s' fieldName: Is
	 * the Parameter. the fieldName you enter will replace '%s' which inside
	 * eleXpath parameter.
	 * 
	 * @param driver
	 * @param eleXpath
	 * @param fieldName
	 * @return  WebElement
	 */
	public WebElement getEleByStringFormat(WebDriver driver, String eleXpath, String fieldName) {
		By by = By.xpath(String.format(eleXpath, fieldName));
		waitForVisbility(driver, by, 15);
		WebElement element = driver.findElement(by);
		return element;
	}
	
	/**
	 * This method usting String xpath return the locators List<WebElement>
	 * @param driver
	 * @param eleXpath
	 * @param fieldName
	 * @return List<WebElement>
	 */
	public List<WebElement> getEleByStringFormats(WebDriver driver, String eleXpath, String fieldName) {
		By by = By.xpath(String.format(eleXpath, fieldName));
		waitForVisbility(driver, by, 15);
		List<WebElement>  elements = driver.findElements(by);
		return elements;
	}
	

	/**
	 * This method will wait for 5 secs to verify element is displayed in the DOM,
	 * If the element not displayed or can’t find the hidden element, it will return
	 * false.
	 * 
	 * @param driver
	 * @param element
	 * @return
	 */
	public boolean isElementPresent(WebDriver driver, WebElement element) {
		try {
			element.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * This method will Passing By as parameter and wait for By 15 secs to return
	 * the Element
	 * 
	 * @param driver
	 * @param by
	 * @return
	 */
	public WebElement getElement(WebDriver driver, By by) {
		waitForVisbility(driver, by, 15);
		return driver.findElement(by);
	}

	/**
	 * This method will passing String as xpath, then calling By Object, wait for 15
	 * secs for By object then return that Element.
	 * 
	 * @param driver
	 * @param xpath
	 * @return
	 */
	public WebElement getElement(WebDriver driver, String xpath) {
		By by = By.xpath(xpath);
		waitForVisbility(driver, by, 15);
		return driver.findElement(By.xpath(xpath));
	}

	/**
	 * This method will slider Pointer, X range from 10 - 90
	 * 
	 * @param driver
	 * @param element
	 * @param X
	 * @param Y
	 */
	public void sliderPointAction(WebDriver driver, WebElement element, int X, int Y) {
		Actions action = new Actions(driver);
		action.dragAndDropBy(element, X, Y).release().build().perform();
		action.click();
	}

	/**
	 * This method validate imgs status code only the img element has 'src'
	 * attribute. also consider if img has only 'class' attribute are true. Its only
	 * return false when found broken imgs.
	 * 
	 * @param imgElement
	 * @return
	 */
	public boolean verifyimageActive(WebElement imgElement) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			String imgSrc = imgElement.getAttribute("src");
			if (imgSrc != null) {
				HttpGet request = new HttpGet(imgSrc);
				HttpResponse response = client.execute(request);
				 // some issue with http client throw in console from this line: 
				int statusCode = response.getStatusLine().getStatusCode();
				return statusCode == 200 || statusCode == 204;
			} else {
				System.out.println("class ! = null");
				return imgElement.getAttribute("class") != null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("false");
		return false;
	}

	public boolean validateImgDisplayed(WebDriver driver) {
		List<Boolean> checkAllimg = new ArrayList<>();
		List<WebElement> imgesList = driver.findElements(By.tagName("img"));
		for (WebElement webElement : imgesList) {
			checkAllimg.add(verifyimageActive(webElement));
		}
		return !(checkAllimg.remove(false));

	}
}
