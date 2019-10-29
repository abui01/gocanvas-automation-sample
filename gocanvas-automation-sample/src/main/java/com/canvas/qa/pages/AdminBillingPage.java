package com.canvas.qa.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import com.canvas.qa.pages.dispatch.DispatchPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.profile.UsersPage;
import com.canvas.qa.pages.submissions.SubmissionAppsPage;
import com.canvas.qa.test.BrowserLaunchTest;

public class AdminBillingPage extends BasePage {

	@FindBy(xpath = "//a[contains(.,'Billing Options')]")
	WebElement billingOption;

	@FindBy(xpath = "(//i[@class='fa fa-edit fa-lg'])[1]")
	WebElement editRateAndDiscount;

	@FindBy(id = "business-monthly-rate")
	WebElement businessMonthlyRate;

	@FindBy(id = "business-annual-rate")
	WebElement businessAnnualRate;

	@FindBy(xpath = "//button[@value='Save']")
	WebElement saveButtonRateAndDiscount;

	public String radioSelectedXpath = "(//label[@class='iradio '])[%s]";

	public String planXpath = "//input[@id='%s']";

	public AdminBillingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		javaScriptErrorChecker(driver);
	}

	public AdminBillingPage clickBillingOption() {
		billingOption.click();
		return this;
	}

	public AdminBillingPage editBillingOption() {
		editRateAndDiscount.click();
		return this;
	}

	/**
	 * Select radio Option and Save the Value
	 * 
	 * @param radionOption
	 */
	public void selectRateAndEnterValue(String radionOption, String enterBusinessMonthlyRate, String planXpathVal) {

		WebElement element1 = makeXpath(radioSelectedXpath, radionOption);
		element1.click();
		WebElement element2 = makeXpath(planXpath, planXpathVal);
		enterTextField(driver, element2, enterBusinessMonthlyRate);
		saveButtonRateAndDiscount.click();
	}

	/**
	 * Edit and Enter data for Rate and Discount radio options
	 * 
	 * @param radioButtonVal1
	 * @param discount1
	 * @param val1
	 * @param radioButtonVal2
	 * @param discount2
	 * @param va12
	 */
	public void enterRateAndDiscount(String radioButtonVal1, String discount1, String val1, String radioButtonVal2,
			String discount2, String va12) {
		clickBillingOption().editBillingOption();
		selectRateAndEnterValue(radioButtonVal1, discount1, val1);
		clickBillingOption().editBillingOption();
		selectRateAndEnterValue(radioButtonVal2, discount2, va12);
	}

	/**
	 * Calculate Annual discount
	 * 
	 * @param businessannualdiscount
	 * @param perBusinessPricYearlyPlanCost
	 * @return
	 */
	public double discountCalculationAnnual(String discountPercentage, double planPrice) {
		String dicountStringAnnual = discountPercentage;
		double dicountValAnnualMonthly = Double.parseDouble(dicountStringAnnual);
		double discount = (planPrice * dicountValAnnualMonthly) / 100;
		double annualDiscount = (planPrice - discount) * 12;
		return annualDiscount;
	}

	/**
	 * Calculate Monthly discount
	 * 
	 * @param businessannualdiscount
	 * @param perBusinessPricYearlyPlanCost
	 * @return
	 */

	public double discountCalculationMonthly(String discountPercentage, double planPrice) {
		String dicountStringMonthly = discountPercentage;
		double dicountMonthly = Double.parseDouble(dicountStringMonthly);
		double discount = (planPrice * dicountMonthly) / 100;
		double monthlylDiscount = (planPrice - discount);
		return monthlylDiscount;
	}
}
