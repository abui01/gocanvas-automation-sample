package com.canvas.qa.pages.profile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.canvas.qa.pages.BasePage;

public class UserPermissionsBusinessPage extends BasePage {

	@FindBy(xpath = "//span[contains(.,'Account')]")
	WebElement account;

	@FindBy(xpath = "//span[contains(.,'Account')]")
	List<WebElement> accountLink;

	@FindBy(xpath = "//span[contains(.,'Apps')]")
	List<WebElement> appLink;

	@FindBy(xpath = "//span[contains(.,'Apps')]")
	WebElement apps;
	@FindBy(xpath = "//h1[contains(.,'Apps')]")
	WebElement Apps1;

	@FindBy(xpath = "//span[contains(.,'Dashboard')]")
	WebElement dashboardlink;

	@FindBy(xpath = "//span[contains(.,'Dashboard')]")
	List<WebElement> dashboardLink;

	@FindBy(xpath = "//h1[contains(.,'Dashboard')]")
	WebElement dashboardlink1;

	@FindBy(xpath = "//span[contains(.,'Dispatch')]")
	List<WebElement> dispatchLink;

	WebDriver driver;

	@FindBy(xpath = "//a[contains(.,'Edit')]")
	WebElement editButton;

	@FindBy(xpath = "//ul[@id = 'side-menu']/li[@class != 'nav-header' and @class != 'side_bar_app_store']")
	WebElement elements;

	@FindAll({ @FindBy(xpath = "//span[contains(.,'Log Out')]"), @FindBy(linkText = "Log Out") })
	WebElement logout;

	@FindAll({ @FindBy(xpath = "//h1[contains(.,'Log In to Your Account')]"),
			@FindBy(xpath = "//span[contains(.,'Log Out')]") })
	WebElement logout1;

	@FindBy(xpath = "//span[contains(.,'Profile')]")
	WebElement Profile;
	@FindBy(xpath = "//h1[contains(.,'Profile')]")
	WebElement Profile1;
	@FindBy(xpath = "//span[contains(.,'Reference Data & Images')]")
	List<WebElement> refDateImageappLink;

	@FindBy(xpath = "//span[contains(.,'Reference Data & Images')]")
	WebElement ReferenceDataAndImages;
	@FindBy(xpath = "//h1[contains(.,'Reference Data & Images')]")
	WebElement ReferenceDataAndImages1;
	@FindBy(xpath = "//span[contains(.,'Report')]")
	List<WebElement> reporLink;

	@FindBy(xpath = ".//*[@class='ibox-content clearfix']")
	WebElement report;

	@FindBy(xpath = ".//*[@class='ibox-content clearfix']//a")
	WebElement report1;

	@FindAll({ @FindBy(xpath = "//span[contains(.,'Reports')]"), @FindBy(xpath = "//a[contains(.,'Reports')]"),
			@FindBy(linkText = "Reports") })

	WebElement reportlink;

	@FindBy(xpath = "//label[contains(.,'Role')]")
	List<WebElement> role;

	@FindBy(xpath = "//span[contains(.,'Submissions')]")
	WebElement Submissions;

	@FindBy(xpath = "//h1[contains(.,'Submissions')]")
	WebElement Submissions1;

	@FindBy(xpath = "//span[contains(.,'Dispatch')]")
	WebElement WFandDI;

	@FindBy(xpath = "//h1[contains(.,'Dispatch')]")
	WebElement WFandDI1;

	@FindBy(xpath = "//span[contains(.,'Workflow & Dispatch')]")
	WebElement workflwAndDispatch;

	@FindBy(xpath = "//h1[contains(.,'Workflow & Dispatch')]")
	WebElement workflwAndDispatch1;

	public UserPermissionsBusinessPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

		javaScriptErrorChecker(driver);
	}

	public boolean checkForAppsLink(String text) {

		apps.click();
		moveToEleClick(driver, Apps1);
		// Apps1.click();
		boolean successText = Apps1.getText().equals(text);
		return successText;

	}

	public String checkForCAdminAppsLink() {
		String successText;
		try {
			apps.click();
			successText = Apps1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}
		return successText;
	}

	public String checkForCAdminProfileLink() {
		String successText;
		try {
			Profile.click();
			successText = Profile1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}
		return successText;
	}

	public String checkForCAdminSubmissionsLink() {
		String successText;
		try {
			Submissions.click();
			successText = Submissions1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}
		return successText;
	}

	public String checkForCDesignerAppsLink() {
		String successText;
		try {
			apps.click();
			successText = Apps1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public String checkForCDesignerDashboardLink() {
		String successText;
		try {
			dashboardlink.click();
			successText = dashboardlink1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public String checkForCDesignerProfileLink() {
		String successText;
		try {
			Profile.click();
			successText = Profile1.getText();

		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public String checkForCDesignerSubmissionsLink() {
		String successText;
		try {
			Submissions.click();
			successText = Submissions1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public String checkForCReporterProfileLink() {
		String successText;
		try {
			Profile.click();
			successText = Profile1.getText();

		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public String checkForCReporterSubmissionsLink() {
		String successText;
		try {
			Submissions.click();
			successText = Submissions1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public String checkForCUserProfileLink() {
		String successText;
		try {
			Profile.click();
			successText = Profile1.getText();

		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public String checkForCUserSubmissionsLink() {
		String successText;
		try {
			Submissions.click();
			successText = Submissions1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public boolean checkForDashboardLink(String text) {

		dashboardlink.click();
		moveToElement(driver,dashboardlink1);
		dashboardlink1.click();
		boolean successText = dashboardlink1.getText().equals(text);
		return successText;
	}

	public boolean checkForDispatcLink(String text) {

		WFandDI.click();
		moveToElement(driver,WFandDI1);
		WFandDI1.click();
		boolean successText = WFandDI1.getText().equals(text);
		return successText;

	}

	public String checkForLogoutLink() {
		String successText;
		try {
			logout.click();
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.className("toast-message"))));
			successText = logout1.getText();
			Thread.sleep(2000);
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public boolean checkForProfileLink(String text) {

		Profile.click();
		moveToElement(driver,Profile1);
		Profile1.click();
		boolean successText = Profile1.getText().equals(text);
		return successText;
	}

	public String checkForReferenceDataAndImageCAdminLink() {
		String successText;
		try {
			ReferenceDataAndImages.click();
			successText = ReferenceDataAndImages1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}
		return successText;
	}

	public String checkForReferenceDataAndImageCDesignerLink() {
		String successText;
		try {
			ReferenceDataAndImages.click();
			successText = ReferenceDataAndImages1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public String checkForReferenceDataAndImageCReporterLink() {
		String successText;
		try {
			ReferenceDataAndImages.click();
			successText = ReferenceDataAndImages1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public boolean checkForReferenceDataImagesLink(String text) {

		ReferenceDataAndImages.click();
		moveToEleClick(driver,ReferenceDataAndImages1);
		// ReferenceDataAndImages1.click();
		boolean successText = ReferenceDataAndImages1.getText().equals(text);
		return successText;

	}

	public boolean checkForSubmissonLink(String text) {

		Submissions.click();
		moveToEleClick(driver,Submissions1);
		// Submissions1.click();
		boolean successText = Submissions1.getText().equals(text);
		return successText;

	}

	public String checkForWFandDICAdminLink() {
		String successText;
		try {

			waitForClickablility(driver,WFandDI, 10);

			WFandDI.click();
			successText = WFandDI1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}
		return successText;
	}

	public String checkForWFandDICDesignerLink() {
		String successText;
		try {
			WFandDI.click();
			successText = WFandDI1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	// Login as a Company Designer:
	// **************************************************************************************//

	public String checkForWFandDICReporterLink() {
		String successText;
		try {
			WFandDI.click();
			successText = WFandDI1.getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			successText = "Failed";
		}

		return successText;
	}

	public boolean checkForWorkflowAndDispatcLink(String text) {

		workflwAndDispatch.click();
		moveToEleClick(driver,workflwAndDispatch1);
		// workflwAndDispatch1.click();
		boolean successText = workflwAndDispatch1.getText().equals(text);
		return successText;
	}

	private boolean compareList(String[] links, List<String> pageLinks) {

		List<String> roleLinks = Arrays.asList(links);
		if (roleLinks.size() != pageLinks.size()) {
			return false;
		}

		Collections.sort(roleLinks);
		Collections.sort(pageLinks);
		for (int i = 0; i < roleLinks.size(); i++) {
			if (!roleLinks.get(i).equals(pageLinks.get(i))) {

				return false;
			}
		}
		return true;
	}

	public boolean isAccountLinkDisplayed() {
		return accountLink.size() > 0;
	}

	public boolean isAppLinkDisplayed() {
		return appLink.size() > 0;
	}

	public boolean isDashboardLinkDisplayed() {
		return dashboardLink.size() > 0;
	}

	public boolean isDispatchLinkDisplayed() {
		Profile.click();
		return dispatchLink.size() > 0;
	}

	public boolean isRefAndImageLinkDisplayed() {
		return refDateImageappLink.size() > 0;
	}

	// Login as a Company Reporter:
	// **************************************************************************************//

	public boolean isReportLinkDisplayed() {
		return reporLink.size() > 0;
	}

	public boolean PremissonOnCAdminLinks(String[] links) throws IOException, InterruptedException

	{
		boolean flag = true;
		driver.findElement(By.xpath("//span[contains(.,'Account')]")).click();

		List<WebElement> allLinks = driver.findElements(
				By.xpath(".//*[@id='side-menu']/li/ul[contains(@class, 'nav nav-second-level collapse in')]/li/a"));
		List<String> pageLinks = new ArrayList<>();

		for (WebElement p : allLinks) {
			{
				pageLinks.add(p.getText());
			}
		}
		flag = compareList(links, pageLinks);

		return flag;
	}

	public boolean PremissonOnCDesignerLinks(String[] links) throws IOException, InterruptedException

	{
		boolean flag = true;
		List<WebElement> allLinks = driver.findElements(
				By.xpath("//ul[@id = 'side-menu']/li[@class != 'nav-header' and @class != 'side_bar_app_store']"));

		List<String> pageLinks = new ArrayList<>();

		for (WebElement p : allLinks) {

			List<WebElement> innerLinks = p.findElements(By.tagName("a"));
			for (WebElement p1 : innerLinks) {
				pageLinks.add(p1.getText());
			}
		}

		return flag = compareList(links, pageLinks);
	}

	public boolean PremissonOnCReporterLinks(String[] links) throws IOException, InterruptedException

	{
		boolean flag = true;
		List<WebElement> allLinks = driver.findElements(
				By.xpath("//ul[@id = 'side-menu']/li[@class != 'nav-header' and @class != 'side_bar_app_store']"));
		List<String> pageLinks = new ArrayList<>();

		for (WebElement p : allLinks) {

			List<WebElement> innerLinks = p.findElements(By.tagName("a"));
			for (WebElement p1 : innerLinks) {
				pageLinks.add(p1.getText());
			}
		}

		flag = compareList(links, pageLinks);

		return flag;
	}

	public boolean PremissonOnCUserLinks(String[] links) throws IOException, InterruptedException

	{
		boolean flag = true;
		List<WebElement> allLinks = driver.findElements(
				By.xpath("//ul[@id = 'side-menu']/li[@class != 'nav-header' and @class != 'side_bar_app_store']"));
		List<String> pageLinks = new ArrayList<>();

		for (WebElement p : allLinks) {

			List<WebElement> innerLinks = p.findElements(By.tagName("a"));
			for (WebElement p1 : innerLinks) {
				pageLinks.add(p1.getText());
			}
		}

		flag = compareList(links, pageLinks);

		return flag;
	}

	public boolean RetriveReportCAdminLink(String[] links) throws IOException, InterruptedException

	{
		boolean flag = true;
		driver.findElement(By.xpath("//span[contains(.,'Account')]")).click();
		waitForClickablility(driver,reportlink, 10);

		reportlink.click();
		waitForClickablility(driver,report,10);
		report.click();
		List<WebElement> allLinks = driver.findElements(By.xpath(".//*[@class='ibox-content clearfix']//a"));
		List<String> pageLinks = new ArrayList<>();

		for (WebElement p : allLinks) {
			{
				pageLinks.add(p.getText());
			}
		}
		flag = compareList(links, pageLinks);
		return flag;
	}

	// Company User *********************************************************

	public boolean RetriveReportCDesignerLink(String[] links) throws IOException, InterruptedException

	{
		boolean flag = true;
		reportlink.click();
		report.click();
		List<WebElement> allLinks = driver.findElements(By.xpath(".//*[@class='ibox-content clearfix']//a"));
		List<String> pageLinks = new ArrayList<>();

		for (WebElement p : allLinks) {
			{
				pageLinks.add(p.getText());
			}
		}
		flag = compareList(links, pageLinks);
		return flag;
	}

	public boolean RetriveReportCReporterLink(String[] links) throws IOException, InterruptedException

	{
		boolean flag = true;
		reportlink.click();
		report.click();
		List<WebElement> allLinks = driver.findElements(By.xpath(".//*[@class='ibox-content clearfix']//a"));

		List<String> pageLinks = new ArrayList<>();

		for (WebElement p : allLinks) {

			{
				pageLinks.add(p.getText());
			}
		}

		flag = compareList(links, pageLinks);

		return flag;
	}

	public boolean RetriveReportCUserLink(String[] links) throws IOException, InterruptedException

	{
		boolean flag = true;
		reportlink.click();
		report.click();
		List<WebElement> allLinks = driver.findElements(By.xpath(".//*[@class='ibox-content clearfix']//a"));
		List<String> pageLinks = new ArrayList<>();

		for (WebElement p : allLinks) {

			{
				pageLinks.add(p.getText());
			}
		}

		flag = compareList(links, pageLinks);

		return flag;
	}

	public boolean updateRole() {
		Profile.click();
		editButton.click();
		return role.size() > 0;
	}
}
