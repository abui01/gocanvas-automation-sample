package com.canvas.qa.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PricingPage extends BasePage {

	WebDriver driver;

	String selectPlanXPath = "//strong[contains(.,'%s')]//parent::h4//following-sibling::div[@class = 'plan--info']//a[contains(.,'Select Plan')]";

	@FindBy(xpath = "//p[@class = 'h3 m-b-sm m-t-sm font-bold']")
	WebElement headermsg;
	
	@FindBy(xpath = "//a[contains(@class, 'btn btn-primary')]")
	List <WebElement> planButtonTxt;

	@FindBy(xpath = "//h1[contains(@title,'Checkout')]")
	WebElement checkoutMsg;
	
	

	String selectPricePath = "(//*[@class='plan--pricing m-b-xs'])[%s]/h1/strong";

	String selectPriceAnnualy = "//strong[.='%s']/../..//h1";
	String selectPriceMonthly = "//strong[.='%s']/../..//p[contains(.,'$')]";

	public PricingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		// mixedContentChecker(driver);
		javaScriptErrorChecker(driver);
	}
	
	public boolean verifyProfessionalSelected() {
		boolean buttonText = planButtonTxt.get(2).getText().equals("Current Plan");
		return buttonText;
	}

	public UpgradePlanPage clickSelectPlan(String planName) {
		WebElement element = driver.findElement(By.xpath(String.format(selectPlanXPath, planName)));
		//WebElement element = makeXpath(selectPlanXPath, planName); <--currently broken
		element.click();
		return new UpgradePlanPage(driver);
	}

	public boolean verifyMessage(String message) {
		boolean headerText = headermsg.getText().equals(message);
		return headerText;
	}

	public boolean getPriceAnnualPerUser(String priceannual, String planName) {

		WebElement element = makeXpath(selectPriceAnnualy, planName);
		boolean price = element.getText().contains(priceannual);
		return price;
	}

	public boolean getPriceMonthlyPerUser(String pricemonth, String planName) {

		WebElement element = makeXpath(selectPriceMonthly, planName);
		boolean price = element.getText().contains(pricemonth);
		return price;
	}

	@FindBy(xpath = "((//a[contains(text(),'Select Plan')])[1])")
	WebElement selectPlanButton;

	@FindBy(xpath = "((//a[contains(text(),'Select Plan')])[2])")
	WebElement selectPlanButtonProf;

	@FindBy(id = "licenseCount")
	WebElement licenseCount;

	@FindBy(xpath = "//strong[.='Business']/../..//p[contains(.,'$')]")
	WebElement businessperusermonthly;

	@FindBy(xpath = "//strong[.='Business']/../..//h1")
	WebElement businessperuseryearly;

	@FindBy(xpath = "//strong[.='Professional']/../..//h1")
	WebElement professionalperuseryearly;

	public boolean clickOnSelectPlan(String message) {

		clickOnHiddenElement(fluentWait(selectPlanButton, driver), driver);
		waitForVisbility(driver, checkoutMsg, 60);
		//WebDriverWait wait = new WebDriverWait(driver, 60);
		//wait.until(ExpectedConditions.visibilityOf(checkoutMsg));
		boolean headerText = getElementText(driver, checkoutMsg).equals(message);
		//boolean headerText = checkoutMsg.getText().equals(message);
		return headerText;

	}

	public String planPriceAnnualy(String planName) {

		WebElement element = makeXpath(selectPriceAnnualy, planName);
		String businessPrice = element.getText();
		return businessPrice;
	}

	public String planPriceMonthly(String planName) {

		WebElement element = makeXpath(selectPriceMonthly, planName);
		String businessPrice = element.getText();
		return businessPrice;
	}

	public String getBusinessPerUserPerMonth() {
		String businessPrice = businessperusermonthly.getText();
		return businessPrice;
	}

	public String getProfessionalPerUserYearly() {
		String businessPrice = businessperuseryearly.getText();
		return businessPrice;
	}

	public boolean clickOnSelectPlanProf(String message) {

		clickOnHiddenElement(fluentWait(selectPlanButtonProf, driver), driver);
		boolean headerText = checkoutMsg.getText().equals(message);
		return headerText;
	}
	public void clickOnSelectPlan() {

		clickOnHiddenElement(fluentWait(selectPlanButton, driver), driver);	
	}
	
	@FindBy(xpath = "//button[contains(.,'Select Plan')]")
	WebElement selectPlanButtonIndividual;

	@FindBy(xpath = "//button[contains(.,'Select Individual Plan')]")
	WebElement selectIndividualPlanButton;

	@FindBy(xpath = "//h3[@class='p-w-xl']")
	WebElement headerTextPopUp;

	@FindBy(xpath = "//button[contains(.,'Got It, Thanks')]")
	WebElement gotItButton;

	@FindBy(xpath = "//h1[contains(.,'Dashboard')]")
	WebElement dashBoardHeaderText;

	@FindBy(xpath = "//div[@class = 'plan--info']//a[contains(.,'Current Plan')]")
	WebElement currentPlan;

	@FindBy(xpath = "//button[contains(.,'Select Plan')]")
	WebElement selectPlanButton1;

	String selectPlan = "(//a[contains(.,'Select Plan')])[%s]";

	public boolean clickOnSelectButtonIndividualPlan(String message) {

		clickOnHiddenElement(fluentWait(selectPlanButtonIndividual, driver), driver);
		waitForClickablility(driver, selectIndividualPlanButton, 60);
		boolean headerText = getElementText(driver, headerTextPopUp).equals(message);
		return headerText;
	}

	public boolean clickOnSelectIndividualPlanButton(String message) {
		clickOnHiddenElement(fluentWait(selectIndividualPlanButton, driver), driver);
		boolean headerText = headerTextPopUp.getText().equals(message);
		return headerText;
	}

	public boolean clickOnGotItButtonInPopUpInDashboardPage(String message) {
		clickOnHiddenElement(fluentWait(gotItButton, driver), driver);
		boolean dashBoardMessage = dashBoardHeaderText.getText().equals(message);
		return dashBoardMessage;
	}

	public boolean verifyCurrentPlan() {
		boolean currentPlanStatus = getElementText(driver, currentPlan).contains("Current Plan");
		return currentPlanStatus;
	}

	public boolean selectPlanToUpgradeAndDowngrade(String message, String plan) {
		WebElement element = makeXpath(selectPlan, plan);
		element.click();
		waitForVisbility(driver, checkoutMsg, 60);
		//WebDriverWait wait = new WebDriverWait(driver, 60);
		//wait.until(ExpectedConditions.visibilityOf(checkoutMsg));
		boolean headerText = checkoutMsg.getText().equals(message);
		return headerText;
	}
	
	
	public boolean selectBusinessPlan(String message, String plan) {
		WebElement element = makeXpath(selectPlan, plan);
		element.click();
		waitForVisbility(driver, checkoutMsg, 60);
		//WebDriverWait wait = new WebDriverWait(driver, 60);
		//wait.until(ExpectedConditions.visibilityOf(checkoutMsg));
		boolean headerText = checkoutMsg.getText().equals(message);
		return headerText;
	}


}
