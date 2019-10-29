package com.canvas.qa.pages.apps;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

public class RetiringAppsPage extends BasePage

{

	@FindBy(xpath = "//span[contains(.,'Apps')]")
	List<WebElement> appAppage;

	@FindBy(xpath = "//a[contains(.,'Apply')]")
	WebElement applyButton;

	@FindBy(xpath = "//span[contains(.,'Apps')]")
	List<WebElement> appName;

	@FindBy(xpath = "//a[@class = 'inline ']//div[contains(.,'Retire App')]")
	List<WebElement> appRetire;

	@FindBy(xpath = "//span[contains(.,'Apps')]")
	WebElement appsLink;

	@FindBy(xpath = ".//*[@class='fa fa-ellipsis-h fa-fw fa-lg']")
	WebElement clickAction;

	@FindBy(xpath = ".//*[@id='status_filter']/button")
	WebElement clickOnFilter;

	WebDriver driver;

	@FindBy(xpath = "//span[contains(.,'Log Out')]")
	WebElement logout;

	@FindBy(xpath = "//h1[contains(.,'Dashboard')]")
	WebElement pageNameAfterLogin1;

	@FindBy(xpath = "//h1[contains(.,'Apps')]")
	WebElement pageNameAfterLogin11;

	@FindBy(xpath = "//a[contains(.,'Retire App')]")
	WebElement retireApp;

	@FindBy(xpath = "//a[contains(.,'Retire app pending')]")
	WebElement retireAppPending;

	@FindBy(xpath = "//span[contains(.,'Retired')]")
	WebElement retiredApp;

	@FindBy(xpath = "//a[contains(.,'Retire New')]")
	WebElement retireNewApp;

	@FindBy(className = "toast-message")
	WebElement toast;

	@FindBy(xpath = "//a[contains(.,'Un-Retire')]")
	WebElement unRetireTheApps;

	public RetiringAppsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		


		javaScriptErrorChecker(driver);
	}

	public boolean accessToApppage() throws InterruptedException {
		return appAppage.size() > 0;
	}

	public boolean companyAdminlogin(String pageNameAfterLogin) throws InterruptedException

	{
		boolean pageNameText = pageNameAfterLogin1.getText().equals(pageNameAfterLogin);
		return pageNameText;
	}

	public boolean companydesignerlogin(String pageNameAfterLogin) throws InterruptedException

	{
		boolean pageNameText = pageNameAfterLogin11.getText().equals(pageNameAfterLogin);
		return pageNameText;
	}

	public boolean isRetireAppLinkExist() throws InterruptedException {
		return appRetire.size() > 0;
	}

	public void moveToRetireAppLink() {
		Actions actions = new Actions(driver);
		actions.moveToElement(retireApp);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'Retire App')]")));
	}

	public void verifyClickAppLink() throws InterruptedException {

		appsLink.click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	public boolean verifyClickOnRetireAppLink(String retire_msg) throws InterruptedException {

		moveToRetireAppLink();
		retireApp.click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toast-message']")));
		boolean retireMsgs = toast.getText().contains(retire_msg);
		return retireMsgs;
	}

	public boolean verifyRetireAppInAppPage(String retired_app) throws InterruptedException {
		boolean appStatus = retiredApp.getText().equals(retired_app);
		return appStatus;
	}

	public void verifyRetireAppInFilter() throws InterruptedException {
		clickOnFilter.click();
		for (int i = 0; i < 5; i++) {

			List<WebElement> clickOnCheckBoxes = driver
					.findElements(By.xpath("//label[contains(@for,'ui-multiselect-filter-option-" + i + "')]"));

			for (WebElement checkBoxs : clickOnCheckBoxes) {
				checkBoxs.click();
			}
		}
		applyButton.click();
	}

	public boolean verifyRetireAppLink() throws InterruptedException {

		boolean retireAppIconDisplayOrNot = isRetireAppLinkExist();
		return retireAppIconDisplayOrNot;
	}

	public boolean verifyRetireAppLinkInBottom() throws InterruptedException {
		moveToRetireAppLink();
		retireApp.click();
		boolean retireAppIconDisplayOrNot = isRetireAppLinkExist();
		return retireAppIconDisplayOrNot;
	}

	public boolean verifyRetireNewAppIcon() throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(retireNewApp);
		retireNewApp.click();
		boolean retireAppIcon = isRetireAppLinkExist();
		return retireAppIcon;
	}

	public boolean verifyRetirePendingAppIcon() throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(retireAppPending);
		retireAppPending.click();
		actions.moveToElement(retireApp);
		boolean retireAppIconDisplayOrNot = isRetireAppLinkExist();
		return retireAppIconDisplayOrNot;
	}

	public void verifyUnretiretheApp() throws InterruptedException {
		clickAction.click();
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		wait1.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class='fa fa-ellipsis-h fa-fw fa-lg']")));
		unRetireTheApps.click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
}
