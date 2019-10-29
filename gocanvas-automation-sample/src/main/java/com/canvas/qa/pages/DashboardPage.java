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

public class DashboardPage extends BasePage {

	@FindBy(xpath = "//span[text() = 'Account']")
	private WebElement account;

	@FindBy(xpath = "//a[contains(., 'Application Store')]")
	WebElement applicationStoreLink;

	@FindBy(xpath = "//a[contains(., 'Application Store')]")
	List<WebElement> applicationStoreLinkList;

	@FindBy(xpath = "//nav/ul/li/a[contains(., 'Apps')]")
	WebElement btnApps;

	@FindBy(xpath = "//a[text() = 'Change Password']")
	private WebElement changePassword;

	@FindBy(id = "btn_Change Password")
	private WebElement changePasswordButton;

	@FindBy(xpath = "//div[contains(., 'Top Four Apps by Submissions')]//following-sibling::div[@id = 'dash-chart']")
	List<WebElement> chart;

	// TC7487
	@FindBy(xpath = "//button[contains(.,'×')]")
	WebElement clickOnCrossSign;

	@FindBy(id = "user_password_confirmation")
	private WebElement confirmPassword;

	@FindBy(xpath = "//p[contains(., 'Your GoCanvas Savings')]//following-sibling::a[text() = 'Customize']")
	List<WebElement> customizeButton;

	@FindBy(xpath = "//span[contains(.,'Dashboard')]")
	List<WebElement> dashBoardLink;
	
	@FindBy(linkText = "Dashboard")
	WebElement dashboardButton;

	@FindBy(id = "dash-number-label")
	List<WebElement> dashNumberLabel;

	@FindBy(xpath = "//a[text() = 'Delivery Status']")
	private WebElement deliveryStatus;

	@FindBy(xpath = "//span[text() = 'Department']")
	private WebElement department;

	@FindBy(xpath = "//span[@class = 'fa fa-angle-down m-l-xs']")
	private WebElement departmentDropDownIcon;

	String departmentXpath = "//a[text() = '%s']";

	@FindBy(xpath = "//a[text() = 'Disable All Users']")
	private WebElement disableAllUsers;

	// @FindBy(xpath = "//span[text() = 'Dispatch']")
	// private WebElement dispatch;

	// @FindBy(xpath = "//span[text() = 'Workflow & Dispatch']")
	@FindBy(partialLinkText = "Dispatch")
	private WebElement dispatch;

	@FindBy(xpath = "//span[@class = 'fa fa-angle-down m-l-xs']//parent::a")
	private WebElement displayedDepartment;

	@FindBy(xpath = "//a[text() = 'Download GoCanvas Client']")
	private WebElement downloadGoCanvasClient;

	private WebDriver driver;

	@FindBy(xpath = "//a[text() = 'Edit']")
	private WebElement edit;

	public String emailTypeXpath = "//tr/td[1][text() = '%s']";

	@FindBy(id = "user_email")
	WebElement enterEmail;

	public String goCanvasMenuOptionXpath = "//ul[@id = 'canvas_only_menu']//a[text() = '%s']";

	@FindBy(xpath = "//span[text() = 'GoCanvas Only']")
	private WebElement goCanvasOnly;
	
	@FindBy(xpath = "(//a[contains(.,'GoCanvas Only')])[1]")
	//@FindBy(css = "#side-menu > li.border-red-4 > a")
	WebElement goCanvasOnlyAdmin;
	

	@FindBy(xpath = "//span[contains(., 'Home')]")
	WebElement homeMenu;

	String labelNameXpath1 = "//label[contains(.,'%s')]";

	@FindBy(xpath = "//span[contains(., 'Log Out')]")
	WebElement logOut;

	@FindBy(linkText = "Log Out")
	WebElement logOutButton;

	public String menuOptionXpath = "//a[text() = '%s']";

	public String menuXpath = "//span[text() = '%s']";

	@FindBy(id = "monthly-chart-button")
	WebElement monthlyButton;

	@FindBy(xpath = "//p[contains(., 'Current Users')]//parent::div//following-sibling::div/div/div[2]/div/p/strong")
	List<WebElement> myAccountValue;

	@FindBy(xpath = "//input[@id= 'user_password' or @id= 'password' ]")
	private WebElement newPassword;

	@FindBy(xpath = "//label[text() = 'Phone Number']//parent::div")
	private WebElement phoneNumber;

	//@FindBy(xpath = "//a[contains(., 'Pricing')]")
	//WebElement pricing;
	
	@FindBy(xpath = "//a[@data-toggle='dropdown'][contains(.,'Pricing')]")
	WebElement pricing;

	@FindBy(xpath = "//ul[@class = 'dropdown-menu m-t-xs']/li/a[contains(.,'Pricing')]")
	WebElement pricingOption;

	@FindBy(xpath = "//span[text() = 'Profile']")
	private WebElement profile;

	public String recipientXpath = "//tr/td[2][text() = '%s']";

	@FindBy(xpath = "//*[text() = 'Reports']")
	private WebElement reports;

	@FindBy(xpath = "//a[text() = 'Resume your own session']")
	private WebElement resumeYourSession;

	@FindBy(id = "btn_Save")
	WebElement saveButton;

	@FindBy(id = "savings_from_paper")
	WebElement savingsFromPaper;

	@FindBy(id = "savings_inc_productivity")
	WebElement savingsFromproductivity;

	@FindBy(xpath = "//span[text() = 'Search Users']")
	private WebElement searchUsers;

	@FindBy(xpath = "//span[text() = 'Submissions']")
	private WebElement submissions;

	@FindBy(className = "toast-message")
	WebElement toast;

	@FindBy(xpath = "//button[@class = 'toast-close-button']")
	WebElement toastCloseButon;

	@FindBy(xpath = "//button[@class = 'toast-close-button']")
	List<WebElement> toastCloseButonList;

	@FindBy(xpath = "//div[@class = 'toast-message']")
	List<WebElement> toastMessage;

	@FindBy(xpath = "//div[@id = 'dash-table']/table/tbody/tr")
	List<WebElement> topFourAppsBySubmissions;

	@FindBy(id = "savings_possible")
	WebElement totalGoCanvasSavings;

	@FindBy(xpath = "//ul[@id = 'side-menu']//a[text() = 'Users']")
	private WebElement users;

	@FindBy(xpath = "//span[text() = 'Workflow & Dispatch']")
	private WebElement workflowAndDispatchEditDispatch;

	@FindBy(xpath = "(//span[@class= 'nav-label'])[1]")
	private WebElement workflowAndDispatch;

	@FindBy(xpath = "//ul[@class = 'dropdown-menu m-t-xs']//li/a[contains(.,'Workflow & Dispatch')] ")
	WebElement workflowAndDispatchOption;

	@FindBy(id = "yearly-chart-button")
	WebElement yearlyButton;

	public DashboardPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		javaScriptErrorChecker(driver);
	}

	public DashboardPage changeUserPassword(String password) {
		newPassword.sendKeys(password);
		confirmPassword.sendKeys(password);
		changePasswordButton.click();
		customWait(2);
		return this;
	}

	public DashboardPage clickAccount() {
		clickOnHiddenElement(account, driver);
		customWait(2);
		return this;
	}

	public CreateAppPage clickApp() {
		clickOnHiddenElement(btnApps, driver);
		return new CreateAppPage(driver);
	}

	public ApplicationStorePage clickApplicationStore() {
		applicationStoreLink.click();
		return new ApplicationStorePage(driver);
	}

	public DashboardPage clickChangePassword() {
		clickOnHiddenElement(changePassword, driver);
		return this;
	}

	public DashboardPage clickCloseToastButton() {
		if (toastCloseButonList.size() > 0) {
			toastCloseButon.click();
		}
		return this;
	}

	public DashboardPage clickDeliveryStatus() {
		clickOnHiddenElement(deliveryStatus, driver);
		return this;
	}

	public DashboardPage clickDepartment(String departmentName) {
		WebElement element = driver.findElement(By.xpath(String.format(departmentXpath, departmentName)));
		// ((JavascriptExecutor)
		// driver).executeScript("arguments[0].scrollIntoView(true);", element);
		clickOnHiddenElement(element, driver);
		customWait(10);
		// element.click();
		return this;
	}

	public DashboardPage clickDepartmentDropDownIcon() {
		departmentDropDownIcon.click();
		customWait(2);
		return this;
	}

	public DashboardPage clickDepartmentMenu() {
		clickOnHiddenElement(department, driver);
		customWait(2);
		return this;
	}

	public DashboardPage clickDisableAllUsers() {
		disableAllUsers.click();
		return this;
	}

	public DispatchPage clickDispatch() {
		clickOnHiddenElement(dispatch, driver);
		return new DispatchPage(driver);
	}

	public DashboardPage clickDownloadGoCanvasClient() {
		clickOnHiddenElement(downloadGoCanvasClient, driver);
		return this;
	}

	public DashboardPage clickEdit() {
		clickOnHiddenElement(edit, driver);
		return this;
	}

	public DashboardPage clickGoCanvasMenuOption(String menu) {
		WebElement element = driver.findElement(By.xpath(String.format(goCanvasMenuOptionXpath, menu)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		clickOnHiddenElement(element, driver);
		return this;
	}

	public DashboardPage clickGoCanvasOnly() {
		//fluentWait(goCanvasOnlyAdmin, driver).click();
		goCanvasOnly.click();
		return this;
	}

	public DashboardPage clickGoCanvasOnlyFromHomeDropDown() {
		//fluentWait(goCanvasOnlyAdmin, driver).click();
		goCanvasOnlyAdmin.click();
		return this;
	}
	
	public DashboardPage clickHomeMenu() {
		clickOnHiddenElement(fluentWait(homeMenu, driver), driver);
		return this;
	}
	
	public DashboardPage clickDashboardButton() {
		clickOnHiddenElement(fluentWait(dashboardButton, driver), driver);
		return this;
	}

	public LoginPage clickLogOut() {
		clickOnHiddenElement(fluentWait(logOutButton, driver), driver);
		customWait(10); //slowing the script down to give it enough time to log back in 
		return new LoginPage(driver);
	}

	public DashboardPage clickLogOutButton() {
		clickOnHiddenElement(logOutButton, driver);
		return this;
	}

	public DashboardPage clickMenuOption(String menu) {
		WebElement element = driver.findElement(By.xpath(String.format(menuOptionXpath, menu)));
		element.click();
		return this;
	}

	public DashboardPage clickMonthlyButton() {
		monthlyButton.click();
		return this;
	}

	public DashboardPage clickOnToastMessageCrossSign() {
		clickOnCrossSign.click();
		return this;
	}

	public DashboardPage clickPricingMenu() {
		clickOnHiddenElement(pricing, driver);
		return this;
	}

	public DashboardPage clickProfile() {
		clickOnHiddenElement(profile, driver);
		return this;
	}

	public ReportsPage clickReports() {
		clickOnHiddenElement(reports, driver);
		return new ReportsPage(driver);
	}

	public SearchUsersPage clickResumeYourSession() {
		clickOnHiddenElement(resumeYourSession, driver);
		return new SearchUsersPage(driver);
	}

	public DashboardPage clickSave() {
		clickOnHiddenElement(saveButton, driver);
		return this;
	}

	public SearchUsersPage clickSearchUsers() {
		clickOnHiddenElement(searchUsers, driver);
		return new SearchUsersPage(driver);
	}

	public SubmissionAppsPage clickSubmissions() {
		clickOnHiddenElement(submissions, driver);
		return new SubmissionAppsPage(driver);
	}

	public UsersPage clickUsers() {
		clickOnHiddenElement(users, driver);
		customWait(2);
		return new UsersPage(driver);
	}

	public WorkflowPage clickWorkflowAndDispatch() {
		workflowAndDispatchEditDispatch.click();
		return new WorkflowPage(driver);
	}

	public DashboardPage clickYearlyButton() {
		yearlyButton.click();
		return this;
	}

	public DashboardPage enterEmail(String email) {
		enterEmail.clear();
		enterEmail.sendKeys(email);
		return this;
	}

	public boolean enterUrlInBrowser(String orgMsg, String url) throws InterruptedException {

		driver.get(url);
		boolean toastMessage = toast.getText().equals(orgMsg);
		return toastMessage;
	}

	public String getAlertMessage() {
		return driver.switchTo().alert().getText();
	}

	public String getPhoneNumber() {
		return phoneNumber.getText();
	}

	public String getSavingsFromPaper() {
		return savingsFromPaper.getText();
	}

	public String getSavingsFromProductivity() {
		return savingsFromproductivity.getText();
	}

	public String getToastMessage() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(toast));
		return toast.getText();
	}

	public String getTotalGoCanvasSavings() {
		return totalGoCanvasSavings.getText();
	}

	public boolean isApplicationStoreDisplayed() {
		return applicationStoreLinkList.size() > 0;
	}

	public boolean isChartDisplayed() {
		return chart.size() > 0;
	}

	public boolean isCustomizeButtonDisplayed() {
		return customizeButton.size() > 0;
	}

	public boolean isDashBoardLinkDispaly() {
		return dashBoardLink.size() > 0;
	}

	public boolean isDashNumberLabelDisplayed() {
		return dashNumberLabel.size() > 0;
	}

	public boolean isDeliveryStatusDisplayed() {
		return deliveryStatus.isDisplayed();
	}

	public boolean isDepartmentDisplayed(String departmentName) {
		return displayedDepartment.getText().contains(departmentName);
	}

	public boolean isDepartmentDropdownDisplayed() {
		return departmentDropDownIcon.isDisplayed();
	}

	public boolean isDisableAllUserDisplayed() {
		return disableAllUsers.isDisplayed();
	}

	public boolean isDispatchDisplayed() {
		return dispatch.isDisplayed();
	}

	public boolean isDownloadGoCanvasClientDisplayed() {
		return downloadGoCanvasClient.isDisplayed();
	}

	public boolean isEmailTypeDisplayed(String text) {
		WebElement element = driver.findElement(By.xpath(String.format(emailTypeXpath, text)));
		return element.isDisplayed();
	}

	public boolean isGoCanvasMenuOptionDisplayed(String menu) {
		WebElement element = driver.findElement(By.xpath(String.format(goCanvasMenuOptionXpath, menu)));
		return element.isDisplayed();
	}

	public boolean isLabelsDisplayed(String labelName) throws InterruptedException {
		return driver.findElement(By.xpath(String.format(labelNameXpath1, labelName))).isDisplayed();
	}

	public boolean isMenuDisplayed(String menu) {
		List<WebElement> element = driver.findElements(By.xpath(String.format(menuXpath, menu)));
		return element.size() > 0;
	}

	public boolean isMenuOptionDisplayed(String menu) {
		WebElement element = driver.findElement(By.xpath(String.format(menuOptionXpath, menu)));
		return element.isDisplayed();
	}

	public boolean isMyAccountValueDisplayed() {
		return myAccountValue.size() > 0;
	}

	public boolean isRecipientDisplayed(String text) {
		WebElement element = driver.findElement(By.xpath(String.format(recipientXpath, text)));
		return element.isDisplayed();
	}

	public boolean isToastMessageDisplayed() {
		return toastMessage.size() > 0;
	}

	public boolean isTopFourAppsBySubmissionsDisplayed() {
		return topFourAppsBySubmissions.size() > 0;
	}

	public boolean isWorkflowAndDispatchDisplayed() {
		// return workflowAndDispatch.isDisplayed();
		return fluentWait(workflowAndDispatch, driver).isDisplayed();
	}

	public boolean isWorkflowAndDispatchOptionDisplayed() {
		return fluentWait(workflowAndDispatchOption, driver).isDisplayed();
	}

	public PricingPage selectPricingOption() {
		pricingOption.click();
		return new PricingPage(driver);
	}

	public DashboardPage verifyAdvancedSerachlabels(String step, ITestContext testContext, String message,
			String[] labelList) throws InterruptedException {

		for (int j = 0; j < labelList.length; j++) {
			boolean status = isLabelsDisplayed(labelList[j]);
			reportLog(status, testContext.getName(), message, step + j,
					" Label with name   " + labelList[j] + " of advanced search screen is displying.");
			org.testng.Assert.assertTrue(status);
		}

		return this;
	}

	@FindBy(xpath = ".//*[@id='page-wrapper']/div[2]/div[2]/div[2]/div/table/tbody/tr[1]/td[3]/div/a[2]/i")
	WebElement deleteRow;

	@FindBy(xpath = "//span[contains(.,'Reference Data & Images')]")
	WebElement refDataImage;

	public boolean deleteExistingStaticPhoto() {
		 boolean result = false;
		fluentWait(refDataImage, driver).click();
		int rowCount = driver.findElements(By.xpath("//*[@class='table-responsive']/table/tbody/tr")).size();
		for (int i = 1; i <= rowCount; i++) {
			Actions actions = new Actions(driver);
			WebElement firstRow = driver.findElement(
					By.xpath(".//*[@id='page-wrapper']/div[2]/div[2]/div[2]/div/table/tbody/tr[1]/td[3]/div/a[2]/i"));
			actions.moveToElement(firstRow);
			customWait(2); // Implicit explicit wait not working
			fluentWait(deleteRow, driver).click();
			customWait(2); // Implicit explicit wait not working
			Alert alert = driver.switchTo().alert();
			alert.accept();
	
			if (rowCount==i)
			result = true;
		}

		return result;
	}
	
}
