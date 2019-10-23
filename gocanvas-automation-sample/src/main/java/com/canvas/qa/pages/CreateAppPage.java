package com.canvas.qa.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.apps.AppBuilderPage;
import com.canvas.qa.pages.apps.EditAppPage;
import com.canvas.qa.pages.apps.PDFDesignerLayoutPage;
import com.canvas.qa.pages.apps.PDFDesignerPage;
import com.canvas.qa.pages.apps.TemplatesPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.UtilityFunctions;
import com.relevantcodes.extentreports.LogStatus;

public class CreateAppPage extends BasePage

{

	@FindBy(xpath = "//span[contains(., 'Pending')]")
	WebElement pendingStatus;

	@FindBy(className = "fa-plus")
	WebElement addScreenButton;

	String appDateXpath = "//a[text() = '%s']";

	@FindBy(xpath = "//a[contains(., 'Application Store')]")
	WebElement applicationStoreLink;

	@FindBy(xpath = "//a[contains(.,'Inventory Collection')]")
	WebElement appLinkInventory;

	@FindBy(xpath = "//span[text() = 'Apply']")
	WebElement applyButton;

	@FindBy(id = "formName")
	WebElement appName;
	@FindBy(xpath = "//table/tbody/tr[1]/td[2]/a")
	WebElement appNameText;

	String appNameXpath = "//a[text() = '%s']";

	@FindBy(linkText = "Apps")
	WebElement appsButton;

	@FindBy(xpath = "//table/tbody/tr[1]/td[3]/span")
	WebElement appStatusText;

	@FindBy(xpath = "//div[@ng-if='useReferenceData() && entryValue']")
	WebElement textStatic;

	@FindBy(xpath = "//table[@class='table table-hover table-striped']/tbody/tr/td[3]//span")
	WebElement textFromPublished;

	// String appStatusXpath = "//a[text() =
	// '%s']//parent::td/following-sibling::td[1]/span";

	@FindBy(xpath = "//small[@class='version']")
	WebElement appVersion;

	String appVersionXpath = "//a[text() = '%s']//parent::td/following-sibling::td[1]/small[@class = 'version']";

	@FindBy(xpath = "//table[@class='table table-hover table-striped']/tbody/tr/td[3]//small")
	WebElement textFromVersion;
	String appVersionXpathWithAppName = "//a[text() = '%s']//parent::td/following-sibling::td[1]/small[@class = 'version']/preceding-sibling::span[contains(.,'%s')]";

	@FindBy(xpath = "//iframe[@data-test-id = 'ChatWidgetWindow-iframe']")
	WebElement chatBox;

	String checkBoxXpath = "//input[@type = 'checkbox']/following-sibling::span[text() = '%s']";

	@FindBy(xpath = "//div[@id = 'right-sidebar-screens-container']/div/div/screen-outline/sheet-outline/div/div/div")
	List<WebElement> childLoopScreens;

	@FindBy(xpath = "//a[contains(.,'Close')]")
	WebElement closeButton;
	@FindBy(className = "cvs-prim-btn")
	WebElement confirmPublishButton;

	//@FindBy(xpath = "(//a[@class='btn-custom-big btn btn-primary top-margin-25 hidden-xs hidden-sm'])//span")
	@FindBy(linkText = "Create App")
	WebElement createAppButton;

	@FindBy(xpath = "//span[text() = ' Create Folder']")
	WebElement createFolder;

	@FindBy(xpath = "//span[text() = ' Create Folder']")
	List<WebElement> createFolderList;

	String deleteIconXpath = "//a[text() = '%s']//parent::td//following-sibling::td[3]//a[@data-method = 'delete']/i";

	String deleteXpath = "//a[text() = '%s']//parent::td//following-sibling::td[1]//span[contains(.,'%s')]//parent::td//following-sibling::td[2]/a[@data-method = 'delete']/i";

	WebDriver driver;

	String editFolderXpath = "//a[text() = '%s']/parent::td[1]/following-sibling::td[3]/a[@data-original-title = 'Edit Folder']/i";

	String editIconXpath = "//a[text() = '%s']/parent::td[1]/following-sibling::td[3]/a[@data-original-title = 'Edit App']/i";

	String editPDFXpath = "//a[text() = '%s']/parent::td[1]/following-sibling::td[3]/a[@data-original-title = 'Edit PDF']/i[2]";

	@FindBy(xpath = "//publish-option//h5")
	List<WebElement> features;

	@FindBy(id = "field_label")
	WebElement fieldLabel;

	@FindBy(xpath = "//div[@id = 'workspace']//div[contains(@class, 'field-label')]")
	List<WebElement> fieldLabels;
	@FindBy(id = "filter")
	WebElement filterDropDown;
	@FindBy(xpath = "//span[@class = 'ui-icon ui-icon-triangle-1-s']")
	WebElement filterDropDownIcon;

	// @FindBy(xpath = "//button[contains(., 'Publish')]")
	// WebElement finalPublishButton;

	@FindBy(xpath = "(//button[@ng-click='publish()'])[1]")
	WebElement finalPublishButton;

	String folderNameXpath = "//a[text() = '%s']";

	String folderXpath = "//span[text() = '%s']";

	// @FindBy(xpath = "//tr[@ng-repeat = 'group in app.groups']//td[2]")
	@FindBy(xpath = "//tr[@ng-repeat = 'option in options']//td[2]")

	List<WebElement> groupNames;

	String lastEditedXpath = "//a[text() = '%s']//parent::td/following-sibling::td[2]/span";

	@FindBy(linkText = "Log Out")
	WebElement logout;

	@FindBy(xpath = "//span[contains(., 'Log Out')]")
	WebElement logOutButton;

	@FindBy(xpath = "//h1[contains(.,'Log In to Your Account')]")
	WebElement logoutMessage;

	// h1[contains(.,'Log In to Your Account')]

	@FindBy(className = "multiline-text-icon")
	WebElement longTextButton;

	@FindBy(xpath = "//*[@__jx__id = '___$_37']")
	WebElement minimizeChat;

	@FindBy(xpath = "//button[contains(.,'Move')]")
	WebElement moveButton;

	@FindBy(linkText = "Profile")
	WebElement myProfile;

	@FindBy(id = "ui-multiselect-filter-option-0")
	WebElement newCheckbox;

	@FindBy(xpath = "//button[contains(., 'Next')]")
	WebElement nextButton;

	// @FindBy(xpath = "//button[@class='btn cvs-prim-btn']")
	// WebElement nextButton;

	@FindBy(xpath = "//div[@id = 'splsh-window']/h1")
	WebElement pageHeader;

	@FindBy(xpath = "//h1[contains(.,'Apps')]")
	WebElement pageName;

	@FindBy(xpath = "//h1[contains(.,'Dashboard')]")
	WebElement pageName_1;

	@FindBy(xpath = "//span[contains(.,'Apps')]")
	WebElement pageNameLeftPanel;

	@FindBy(xpath = "//div[@id = 'right-sidebar-screens-container']/div/div/screen-outline/sheet-outline/div/div/span")
	List<WebElement> parentLoopScreens;

	@FindBy(id = "ui-multiselect-filter-option-1")
	WebElement pendingCheckbox;

	@FindBy(className = "fa-tablet")
	WebElement publishButton;

	@FindBy(id = "ui-multiselect-filter-option-2")
	WebElement publishedCheckbox;

	String quickLinkMenuXpath = "//a[text() = '%s']/parent::td[1]/following-sibling::td[3]/a[@data-original-title = 'Quick Links']/parent::td/ul/li/a[text() = '%s']";

	@FindBy(xpath = "//a[@data-original-title = 'Quick Links']")
	WebElement quickLinksButton;

	String quickLinkXpath = "//a[text() = '%s']/parent::td[1]/following-sibling::td[3]/a[@data-original-title = 'Quick Links']/i";
	String deletePendingXpath = "//a[text() = '%s']/parent::td[1]/following-sibling::td[3]/a[@data-method = 'delete']/i";

	// @FindBy(xpath = "//span[contains(.,'Retire App')]")
	@FindBy(xpath = "//*[contains(text(),'Retire App')]")
	WebElement retireButton;

	@FindBy(id = "ui-multiselect-filter-option-3")
	WebElement retiredCheckbox;

	@FindBy(xpath = "//span[contains(., 'Retired')]")
	WebElement retiredStatus;

	@FindBy(id = "icon_save_button")
	WebElement saveButton;

	@FindBy(className = "screen-name")
	WebElement screenName;

	@FindBy(id = "common_search_field")
	List<WebElement> searchField;

	@FindBy(xpath = "//i[contains(@class,'fa fa-search text-muted')]")
	WebElement searchIcon;

	@FindBy(xpath = "//div[@id = 'right-sidebar-screens-container']/div/div/screen-outline/sheet-outline/div/p")
	List<WebElement> simpleScreens;

	@FindBy(id = "start-tamplate")
	WebElement startButton;

	@FindBy(xpath = "//ul[@id ='template-item']/li")
	List<WebElement> templates;

	@FindBy(xpath = "//p[contains(.,'Templates')]//following-sibling::a[text() = 'View']")
	WebElement templateViewLink;

	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement toast;

	@FindBy(linkText = "Un-Retire")
	WebElement unretireButton;

	@FindBy(id = "my_image_file")
	WebElement uploadAppLogo;

	@FindBy(xpath = "//tr[@ng-repeat = 'user in app.users']/td/div/label/input")
	List<WebElement> userCheckboxes;

	// String userCheckBoxXpath = "//td[text() =
	// '%s']//preceding-sibling::td[2]/div/label/input";
	String userCheckBoxXpath = "//td[text() = '%s']//preceding-sibling::td[2]";
	// @FindBy(xpath = "//tr[@ng-repeat = 'user in app.users']/td[3]")
	@FindBy(xpath = "//tr[@ng-repeat = 'option in options']//td[3]")
	List<WebElement> userEmails;

	// @FindBy(xpath = "//tr[@ng-repeat = 'user in app.users']")
	@FindBy(xpath = "//tr[@ng-repeat = 'option in options']")
	List<WebElement> userRows;

	@FindBy(xpath = "//input[@name='login']")
	WebElement userName;

	@FindBy(xpath = "//label[contains(.,'City')]/../div//input[contains(@type,'search')]")
	WebElement cityDropDownSearch;
	@FindBy(xpath = "//label[contains(.,'City')]/../div//input[contains(@type,'search')]")
	List<WebElement> serachcityDropDown;

	// @FindBy(id = "dispatch_entry0')]")
	@FindBy(xpath = "//label[contains(.,'City')]/../div//span[contains(@class,'btn btn-default form-control ui-select-toggle')]")
	WebElement cityDropDown;

	@FindBy(xpath = "//label[contains(.,'City')]/../div//span[contains(@class,'btn btn-default form-control ui-select-toggle')]")
	List<WebElement> cityDropDown1;

	@FindBy(xpath = "//div[@ng-if='useReferenceData() && entryValue']")
	WebElement staticText;

	@FindBy(xpath = "//a[contains(.,' New Loop Item')]")
	WebElement newLoopItemIcon;

	@FindBy(id = "dispatch_entry1")
	WebElement shortText;

	@FindBy(xpath = "//label[contains(.,'Operator 1')]/../div//span[contains(@class,'btn btn-default form-control ui-select-toggle')]")
	WebElement clickOperatorDropDownOp1;

	@FindBy(xpath = "//label[contains(.,'Operator 2')]/../div//span[contains(@class,'btn btn-default form-control ui-select-toggle')]")
	WebElement clickOperatorDropDownOp2;

	@FindBy(xpath = "//label[contains(.,'Operator 2')]/../div")
	WebElement operatorDorpdowm2;

	String operatorFields = "//label[contains(.,'%s')]/../div//span[contains(@class,'btn btn-default form-control ui-select-toggle')]";
	String numberNameXpath = "//*[contains(@title,'%s')]";
	String numberNameXpath1 = "//*[contains(@title,'%s')]/div";
	String deleteOperationIcon = "//label[contains(.,'%s')]/../a[@title='Delete this operation']";

	@FindBy(xpath = "//div[@class='modal-content']//button[.='Yes']")
	WebElement confirmDeleteOp;
	@FindBy(xpath = "//label[contains(.,'Operator 3')]/../div//span[contains(@class,'btn btn-default form-control ui-select-toggle')]")
	WebElement clickOperatorDropDownOp3;

	@FindBy(xpath = "//a[contains(.,'Close')]")
	WebElement rightCloseButton;

	@FindBy(xpath = ".//*[@id='bar']/a")
	WebElement clickOnOperator;

	@FindBy(xpath = "//a[contains(@id,'addField')]")
	List<WebElement> addField;

	@FindBy(id = "addConstant")
	List<WebElement> addConstant;

	@FindBy(xpath = "//div[@id='modal-footer']/button[.='Cancel']")
	WebElement CancelButton;

	@FindBy(xpath = "//button[contains(.,'Done')]")
	WebElement doneButton;

	@FindBy(xpath = "//a[contains(.,'Edit Calculation')]")
	WebElement editCalculation;

	@FindBy(xpath = ".//*[@id='operation_field_2']")
	WebElement operator3SelectedValue;

	@FindBy(id = "operation_field_0")
	WebElement operator1DropDownValue;

	@FindBy(id = "operation_field_1")
	WebElement operator2DropDownValue;

	@FindBy(id = "operation_field_2")
	WebElement operator3DropDownValue;

	@FindBy(id = "operation_field_3")
	WebElement operator4DropDownValue;

	@FindBy(xpath = "operation_field_3")
	List<WebElement> operator4DropDown;

	@FindBy(id = "operation_field_4")
	WebElement operator5DropDownValue;

	@FindBy(id = "operation_field_4")
	List<WebElement> operator5DropDown;

	@FindBy(id = "operation_field_5")
	WebElement operator6DropDownValue;

	@FindBy(id = "operation_field_5")
	List<WebElement> operator6DropDown;

	@FindBy(id = "operation_value_3")
	WebElement enterConstantValue;

	@FindBy(id = "operation_value_6")
	WebElement enterConstantsValues;

	@FindBy(xpath = "//button[@data-ng-click='modalOptions.ok();']")
	WebElement clickDonotSave;

	@FindBy(xpath = ".//*[@id='dispatch_entry0']/ul[2]/li/div")
	WebElement noRecord;

	@FindBy(xpath = "//nav/ul/li/a[contains(., 'Apps')]")
	WebElement btnApps;

	@FindBy(xpath = "//table[@class='table table-hover table-striped']/tbody/tr/td[2]/a")
	WebElement appNameIsDisplayed;

	@FindBy(xpath = ".//*[@id='bar']/div[3]/field-operations/div[1]")
	WebElement clickonoperator;

	@FindBy(xpath = ".//*[@id='bar']/div[4]/field-operations/div[1]")
	WebElement hovernDivideOperatorIcons;

	@FindBy(xpath = ".//*[@id='bar']/div[3]//*[contains(@title, '- (Subtraction)')]")
	WebElement clickonMinusoperator;

	@FindBy(xpath = ".//*[@id='bar']/div[4]//*[contains(@title, '/ (Division)')]")
	WebElement clickonDivideOperator;

	@FindBy(xpath = ".//*[@id='bar']/div[5]//*[contains(@title, '* (Multiplication)')]")
	WebElement clickonMulOperator;

	@FindBy(xpath = ".//*[@id='bar']/div[6]//*[contains(@title, '+ (Addition)')]")
	WebElement clickonPlusOperator;

	@FindBy(xpath = ".//*[@id='bar']/div[3]//*[contains(@title, '- (Subtraction)')]")
	List<WebElement> valueofminusoperator;
	
	@FindBy(xpath =  "//div[@id='bar']/div")
	List<WebElement> operators;
	
	@FindBy(xpath = "//button[@class='close']")
	WebElement xButtonOperator;
	
	@FindBy(xpath = "//button[@type='button' and text()='No']")
	WebElement noButtonOperator;
	/**
	 * Constructor to initialize instance variable - @param driver
	 */
	public CreateAppPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		// mixedContentChecker(driver);

		javaScriptErrorChecker(driver);
	}

	public CreateAppPage acceptPopUpMessage() {
		driver.switchTo().alert().accept();
		return this;
	}

	public boolean afterlogin(String pageNameAfterLogin) throws InterruptedException

	{
		pageName_1.getText();
		customWait(4);
		boolean pageNameText = pageName_1.getText().equals(pageNameAfterLogin);
		return pageNameText;
	}

	public boolean afterlogout(String logOutmsg) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'Log In to Your Account')]")));
		customWait(3);
		boolean logOutMessage = logoutMessage.getText().equals(logOutmsg);
		return logOutMessage;
	}

	public CreateAppPage appsButtonClick() throws InterruptedException {
		customWait(2);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", appsButton);
		return this;
	}

	public CreateAppPage buildBasicApp() throws NoSuchElementException {
		(addScreenButton).click();
		Actions actions = new Actions(driver);
		actions.moveToElement(screenName);
		actions.click();
		actions.build().perform();
		(screenName).sendKeys("Screen");
		return this;
	}

	/***
	 * This method click on create App button
	 * 
	 * 
	 */
	public AppBuilderPage clickAppCreateButton() throws NoSuchElementException {
		clickOnHiddenElement(fluentWait(createAppButton, driver), driver);
		return new AppBuilderPage(driver);
	}

	public ApplicationStorePage clickApplicationStore() {
		applicationStoreLink.click();
		return new ApplicationStorePage(driver);
	}

	public void clickAppLink() throws InterruptedException {

		pageNameLeftPanel.click();
		Actions actions = new Actions(driver);
		actions.moveToElement(appLinkInventory);
		fluentWait(appLinkInventory, driver).click();

	}

	public EditAppPage clickAppName(String appName) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); customWait(5);
		 * //waits for results to load }
		 */
		searchField(appName);
		WebElement element = driver.findElement(By.xpath(String.format(appNameXpath, appName)));
		element.click();
		return new EditAppPage(driver);
	}

	public EditAppPage clickAppNameToBeDestroy(String appName) throws InterruptedException {
		clickOnHiddenElement(btnApps, driver);
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); customWait(2); }
		 */
		searchField(appName);

		WebElement element = driver.findElement(By.xpath(String.format(appNameXpath, appName)));
		element.click();
		return new EditAppPage(driver);
	}

	public CreateAppPage clickDonotSave() throws InterruptedException {
		clickOnHiddenElement(clickDonotSave, driver);
		customWait(2);
		return this;
	}

	public CreateFolderPage clickCreateFolder() {
		createFolder.click();
		return new CreateFolderPage(driver);
	}

	public CreateFolderPage clickEditFolder(String folderName) throws InterruptedException {
		if (searchField.size() > 0) {
			searchField.get(0).clear();
			searchField.get(0).sendKeys(folderName);
			searchIcon.click();
			Thread.sleep(5000);
		}
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(String.format(editFolderXpath, folderName)));
		js.executeScript("arguments[0].click();", element);
		return new CreateFolderPage(driver);
	}

	public AppBuilderPage clickEditIcon(String appName) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); Thread.sleep(5000);
		 * }
		 */
		searchField(appName);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(String.format(editIconXpath, appName)));
		js.executeScript("arguments[0].click();", element);
		return new AppBuilderPage(driver);
	}

	public PDFDesignerLayoutPage clickEditPDF(String appName) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); Thread.sleep(5000);
		 * }
		 */
		searchField(appName);
		WebElement element = driver.findElement(By.xpath(String.format(editPDFXpath, appName)));
		clickOnHiddenElement(element, driver);
		return new PDFDesignerLayoutPage(driver);
	}

	public PDFDesignerPage clickEditPDFPublishedApp(String appName) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); Thread.sleep(5000);
		 * }
		 */
		searchField(appName);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(String.format(editPDFXpath, appName)));
		js.executeScript("arguments[0].click();", element);
		return new PDFDesignerPage(driver);
	}

	/**
	 * This will click on the "Filter" DDL
	 * 
	 * @return
	 */
	public CreateAppPage clickFilterDDL() {
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		// js.executeScript("arguments[0].click();", filterDropDownIcon);
		fluentWait(filterDropDownIcon, driver);
		clickOnHiddenElement(filterDropDownIcon, driver);
		return this;
	}

	public CreateAppPage clickFolder(String folderName) {
		WebElement element = driver.findElement(By.xpath(String.format(folderXpath, folderName)));
		element.click();
		return this;
	}

	public FolderPage clickFolderName(String folderName) throws InterruptedException {
		if (searchField.size() > 0) {
			searchField.get(0).clear();
			searchField.get(0).sendKeys(folderName);
			searchIcon.click();
			customWait(5); // waits for results to load
		}
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(String.format(folderNameXpath, folderName)));
		js.executeScript("arguments[0].click();", element);
		return new FolderPage(driver);
	}

	public LoginPage clickLogOutButton() {
		clickOnHiddenElement(logOutButton, driver);
		return new LoginPage(driver);
	}

	public CreateAppPage clickMoveButton() {
		moveButton.click();
		return this;
	}

	public CreateAppPage clickQuickLink(String appName) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); customWait(5);
		 * //waits for results to load }
		 */
		searchField(appName);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(String.format(quickLinkXpath, appName)));
		js.executeScript("arguments[0].click();", element);
		return this;
	}

	public CreateAppPage clickQuickLinkMenu(String appName, String quickLinkMenu) throws InterruptedException {

		if (driver.findElements(By.xpath(String.format(quickLinkMenuXpath, appName, quickLinkMenu))).size() > 0) {
			WebElement element = driver
					.findElement(By.xpath(String.format(quickLinkMenuXpath, appName, quickLinkMenu)));
			element.click();
			Thread.sleep(2000);
		}
		return this;
	}

	public CreateAppPage clickQuickLinksButton() throws InterruptedException {
		fluentWait(quickLinksButton, driver).click();
		return this;
	}

	public CreateAppPage clickRetireButton() throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(retireButton);
		fluentWait(retireButton, driver).click();
		return this;
	}

	public TemplatesPage clickTemplateViewLink() {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", templateViewLink);
		// templateViewLink.click();
		return new TemplatesPage(driver);
	}

	public CreateAppPage clickUnRetireButton() throws InterruptedException {
		fluentWait(unretireButton, driver).click();
		return this;
	}

	public CreateAppPage deleteApp(String appName) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); Thread.sleep(5000);
		 * }
		 */
		searchField(appName);
		clickOnHiddenElement(driver.findElement(By.xpath(String.format(deleteIconXpath, appName))), driver);
		driver.switchTo().alert().accept();
		return this;
	}

	public CreateAppPage deleteAppWithVersion(String appName, String version) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); Thread.sleep(5000);
		 * }
		 */
		searchField(appName);
		clickOnHiddenElement(driver.findElement(By.xpath(String.format(deleteXpath, appName, version))), driver);
		driver.switchTo().alert().accept();
		customWait(5);
		return this;
	}

	public CreateAppPage deleteEachInstanceOfApp(String appName) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); customWait(10);
		 * //waits for app to delete and page to refresh }
		 */
		searchField(appName);
		List<WebElement> elementList = driver.findElements(By.xpath(String.format(appNameXpath, appName)));
		int count = elementList.size();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				List<WebElement> elementList2 = driver.findElements(By.xpath(String.format(deleteIconXpath, appName)));
				int counter = elementList2.size();
				if (counter > 0) {
					clickOnHiddenElement(driver.findElement(By.xpath(String.format(deleteIconXpath, appName))), driver);
					driver.switchTo().alert().accept();
				}
			}
		}
		return this;
	}

	public CreateAppPage dragAppToFolder(String appName, String folderName) {
		WebElement source = driver.findElement(By.xpath(String.format(appNameXpath, appName)));
		WebElement target = driver.findElement(By.xpath(String.format(appNameXpath, folderName)));
		Actions act = new Actions(driver);
		act.clickAndHold(source).build().perform();
		customWait(5);
		act.moveToElement(target).build().perform();
		customWait(5);
		act.release().build().perform();
		return this;
	}

	public AppBuilderPage editApp(String appName) throws InterruptedException {
		return clickAppName(appName).clickEditAppButton();
	}

	public CreateAppPage finalPublishButtonClick() throws NoSuchElementException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// js.executeScript("arguments[0].scrollIntoView(true);", finalPublishButton);
		js.executeScript("arguments[0].click();", finalPublishButton);
		// clickOnHiddenElement(finalPublishButton, driver);
		// (finalPublishButton).click();

		return this;
	}

	public String getAppName() throws InterruptedException {
		return appNameText.getText();
	}

	public String getAppStatus(String appName) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); Thread.sleep(5000);
		 * }
		 */
		searchField(appName);
		return textFromPublished.getText();
	}

	public String getAppVersion(String appName) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); Thread.sleep(5000);
		 * }
		 */
		searchField(appName);
		// return driver.findElement(By.xpath(String.format(appVersionXpath,
		// appName))).getText();
		return textFromVersion.getText();
	}

	public String getHeader() throws NoSuchElementException {
		return (pageHeader).getText();
	}

	public String getLastEdited(String appName) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); Thread.sleep(5000);
		 * }
		 */
		searchField(appName);
		return driver.findElement(By.xpath(String.format(lastEditedXpath, appName))).getText();
	}

	public int getNumberOfAppInstances(String appName) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click();
		 * Thread.sleep(10000); }
		 */
		searchField(appName);
		return driver.findElements(By.xpath(String.format(appNameXpath, appName))).size();
	}

	public String getPopUpMessageText() {
		return driver.switchTo().alert().getText();
	}

	public String getToastMsg() throws NoSuchElementException {
		waitForVisbility(driver, toast, 10);
		String text = toast.getText();
		System.out.println("toast message: " + text);
		return text;
	}

	public boolean isAppCreateButtonDisplayed() {
		waitForVisbility(driver, createAppButton, 10);
		return createAppButton.isDisplayed();
	}

	public CreateAppPage checkAndDeletePendingApp(String appName) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); customWait(10);
		 * //waits for results to load }
		 */
		searchField(appName);
		if (pendingStatus.isDisplayed()) {
			WebElement element = driver.findElement(By.xpath(String.format(deletePendingXpath, appName)));
			clickOnHiddenElement(element, driver);
			driver.switchTo().alert().accept();
			customWait(10); // waits for page to load results
		}

		else {
			// do nothing
		}
		return this;
	}

	/*
	 * public CreateAppPage checkForPendingAppsAndDelete(String appName) throws
	 * InterruptedException {
	 * 
	 * //if there are pending apps, delete them if
	 * (appStatusText.getText().equals("Pending")) { //appstatustext element only
	 * locates first item on table, need to re-write String deleteAppStatusxpath =
	 * "//a[contains(.,'" + appName +
	 * "')]/../following-sibling::td//i[@class='fa fa-trash fa-fw fa-lg']";
	 * driver.findElement(By.xpath(String.format(deleteAppStatusxpath))).click();
	 * driver.switchTo().alert().accept(); customWait(5); } else { //do nothing }
	 * return this; }
	 */

	public boolean isAppNameDisplayed(String appName) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); Thread.sleep(5000);
		 * }
		 */
		searchField(appName);
		// return driver.findElement(By.xpath(String.format(appNameXpath,
		// appName))).isDisplayed();
		return appNameIsDisplayed.isDisplayed();
	}

	/**
	 * If a search bar is present on the apps page, this method will trigger a
	 * search
	 * 
	 * @param appName
	 */
	public void searchField(String appName) {

		if (searchField.size() > 0) {
			searchField.get(0).clear();
			searchField.get(0).sendKeys(appName);
			searchIcon.click();
			customWait(5);
		}
	}

	/**
	 * This verifies if the app name appears on the page
	 * 
	 * @param appName
	 * @param isPresent: true = user expects app to appear/false = user does nto
	 *                   expect app to appear
	 * @return
	 */
	public boolean isAppNameShared(String appName, boolean isPresent) {

		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); customWait(5); }
		 */
		searchField(appName);

		String bodyText = driver.findElement(By.tagName("body")).getText();

		boolean textPresent = bodyText.contains(appName);

		if (isPresent == true)// user wants to know app appears on page
		{
			if (textPresent == true) {
				return true;
			} else {
				return false;
			}
		} else // user does not want app to appear on page
		{
			if (textPresent == true) {
				return false;
			} else {
				return true;
			}
		}
	}

	public boolean isAppVersionDisplayed(String appName, String version) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); Thread.sleep(8000);
		 * }
		 */
		searchField(appName);
		return driver.findElement(By.xpath(String.format(appVersionXpathWithAppName, appName, version))).isDisplayed();
	}

	public boolean isCreateFolderDisplayed() {
		return createFolderList.size() > 0;
	}

	public boolean isQuickLinkMenuDisplayed(String appName, String quickLinkMenu) {
		WebElement element = driver.findElement(By.xpath(String.format(quickLinkMenuXpath, appName, quickLinkMenu)));
		return element.isDisplayed();
	}

	public CreateAppPage loadAppBuilder() throws InterruptedException {

		// Thread.sleep(5000);
		customWait(5);
		try {
			driver.switchTo().frame(chatBox);
			(minimizeChat).click();
		} catch (Exception e) {
		}

		driver.switchTo().defaultContent();

		return this;
	}

	/***
	 * This method for log-out from Create App page
	 * 
	 * @return
	 * 
	 * 
	 */
	public void logout() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(logout));
		clickOnHiddenElement(logout, driver);
		wait.until(ExpectedConditions.visibilityOf(userName));
	}

	public CreateAppPage logout1() throws InterruptedException {
		clickOnHiddenElement(logout, driver);
		customWait(5);
		return this;
		// Thread.sleep(5000); // Required because after log-out page take time
		// to load
	}

	public CreateAppPage newApp() throws NoSuchElementException {
		// (createAppButton).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", createAppButton);
		return this;
	}

	public CreateAppPage nextButtonClick() throws NoSuchElementException, InterruptedException {
		Thread.sleep(15000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", nextButton);
		(nextButton).click();
		return this;
	}

	public CreateAppPage nextButtonInUserScreenClick() throws NoSuchElementException, InterruptedException {
		Thread.sleep(15000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", nextButton);
		(nextButton).click();
		return this;
	}

	public boolean pdfDownloadAfterlogin(String pageNameAfterLogin) throws InterruptedException

	{
		pageName.getText();
		customWait(4);
		boolean pageNameText = pageName.getText().equals(pageNameAfterLogin);
		return pageNameText;
	}

	@Override
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

	public CreateAppPage retireEachInstanceOfApp(String appName) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); customWait(10);
		 * //waits for page to load results }
		 */
		searchField(appName);
		List<WebElement> elementList = driver.findElements(By.xpath(String.format(appNameXpath, appName)));
		int count = elementList.size();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				WebElement element = driver.findElement(By.xpath(String.format(quickLinkXpath, appName)));
				clickOnHiddenElement(element, driver);
				List<WebElement> elementList2 = driver
						.findElements(By.xpath(String.format(quickLinkMenuXpath, appName, "Retire")));
				int counter = elementList2.size();
				if (counter > 0) {
					WebElement element2 = driver
							.findElement(By.xpath(String.format(quickLinkMenuXpath, appName, "Retire")));
					element2.click();
					driver.switchTo().alert().accept();
					customWait(10); // waits for page to load results
				}
			}
		}
		return this;
	}

	public CreateAppPage saveAndPublish() throws NoSuchElementException, InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", publishButton);
		// (publishButton).click();
		Thread.sleep(5000);
		js.executeScript("arguments[0].click();", confirmPublishButton);
		// (confirmPublishButton).click();
		return this;
	}

	public CreateAppPage SearchByDateAndTime(String appName, String submDate) throws InterruptedException {
		/*
		 * if (searchField.size() > 0) { searchField.get(0).clear();
		 * searchField.get(0).sendKeys(appName); searchIcon.click(); Thread.sleep(4000);
		 * }
		 */
		searchField(appName);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(String.format(appDateXpath, submDate)));
		js.executeScript("arguments[0].click();", element);
		return new CreateAppPage(driver);
	}

	public CreateAppPage selectAppStatus(String appStatus) throws InterruptedException {
		// Select dropdown = new Select(filterDropDown);
		// dropdown.selectByVisibleText(appStatus);
		WebElement element = driver.findElement(By.xpath(String.format(checkBoxXpath, appStatus)));
		element.click();
		applyButton.click();
		return this;
	}

	/**
	 * This filters the App results by "Published" status only
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public CreateAppPage selectPublishedStatus() throws InterruptedException {

		handleCheckBox(newCheckbox, false, driver);
		handleCheckBox(pendingCheckbox, false, driver);
		handleCheckBox(publishedCheckbox, true, driver);
		handleCheckBox(retiredCheckbox, false, driver);

		fluentWait(applyButton, driver);
		clickOnHiddenElement(applyButton, driver);

		return this;
	}

	/**
	 * This filters the App results by "Retired" status only
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public CreateAppPage selectRetiredStatus() throws InterruptedException {

		handleCheckBox(newCheckbox, false, driver);
		handleCheckBox(pendingCheckbox, false, driver);
		handleCheckBox(publishedCheckbox, false, driver);
		handleCheckBox(retiredCheckbox, true, driver);

		fluentWait(applyButton, driver);
		clickOnHiddenElement(applyButton, driver);

		return this;
	}

	public CreateAppPage selectTemplate(String temp) {
		for (WebElement t : templates) {
			if (t.getText().equals(temp)) {
				t.click();
			}
		}
		return this;
	}

	public CreateAppPage setAppName(String name) throws NoSuchElementException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", appName);
		// (appName).click();
		(appName).clear();
		(appName).sendKeys(name);

		return this;
	}

	public CreateAppPage startClick() throws NoSuchElementException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", startButton);
		// (startButton).click();
		return this;
	}

	public CreateAppPage uncheckUser(String userEmail) throws NoSuchElementException {
		for (int i = 0; i < userRows.size(); i++) {
			WebElement row = userRows.get(i);
			if (row.getText().contains(userEmail)) {
				if (userCheckboxes.get(i).isSelected()) {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click();", userCheckboxes.get(i));
				}
			}
		}

		return this;
	}

	public CreateAppPage uncheckUserEmail(String userEmail) {
		WebElement element = driver.findElement(By.xpath(String.format(userCheckBoxXpath, userEmail)));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		if (element.isSelected()) {
			clickOnHiddenElement(element, driver);
		}
		return this;
	}

	public CreateAppPage uploadAppLogo() throws InterruptedException {
		customWait(3);
		fluentWait(uploadAppLogo, driver).sendKeys(System.getProperty("user.dir") + "/src/main/resources/Test.JPG");
		fluentWait(saveButton, driver).click();
		return this;
	}

	/**
	 * Verifies if App exists in App page
	 * 
	 * @param appsName
	 * @return
	 * @throws InterruptedException
	 */
	public boolean verifyAppExists(String appsName) throws InterruptedException {
		String AppName = appsName;
		WebElement appLink = driver.findElement(By.linkText(AppName));
		if (appLink.isDisplayed()) {
			return true;
		}

		return false;
	}

	public boolean verifyAppPageText(String pageNameAfterLogin) throws InterruptedException

	{
		pageName.getText();
		customWait(4);
		boolean pageNameText = pageName.getText().equals(pageNameAfterLogin);
		return pageNameText;
	}

	public boolean verifyChildLoopFieldCounts(String[] screens, String[] fieldCounts) {
		for (WebElement s : childLoopScreens) {
			String sName = s.getText();
			s.click();
			(appName).click();

			for (int i = 0; i < screens.length; i++) {
				if (screens[i].equals(sName)) {
					int fCount = Integer.parseInt(fieldCounts[i]);
					if (fieldLabels.size() != fCount) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public boolean verifyFeats(String feats) throws InterruptedException {
		Thread.sleep(5000);

		boolean featPresent = false;

		for (WebElement f : features) {
			if (f.getText().contains(feats)) {
				featPresent = true;
				break;
			} else {
				featPresent = false;
				continue;
			}
		}

		return featPresent;
	}

	public boolean verifyGroups(String[] groups) {
		boolean groupPresent = false;
		for (int i = 0; i < groups.length; i++) {
			for (WebElement g : groupNames) {
				if (g.getText().equals(groups[i]))
					groupPresent = true;
			}
			if (groupPresent == false)
				break;
		}

		return groupPresent;
	}

	public boolean verifyParLoopFieldCounts(String[] screens, String[] fieldCounts) throws InterruptedException {
		for (WebElement s : parentLoopScreens) {
			String sName = s.getText();
			s.click();
			for (int i = 0; i < screens.length; i++) {
				if (screens[i].equals(sName)) {
					int fCount = Integer.parseInt(fieldCounts[i]);
					if (fieldLabels.size() != fCount) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public void verifyPublishedApp(ITestContext testContext, String version, String appName, String appStatus,
			String step) throws InterruptedException {

		boolean status = getToastMsg().trim().toLowerCase()
				.contains((version + " of the " + appName + " app has been published.").trim().toLowerCase())
				|| getToastMsg().trim().toLowerCase()
						.contains(("App '" + appName + "' has been updated").trim().toLowerCase());
		reportLog(status, testContext.getName(), "User clicked on Publish button", step + "1",
				"Version 1 of the " + appName + " app has been published.");
		org.testng.Assert.assertTrue(status, "Verify publish successful");

		status = isAppCreateButtonDisplayed();
		reportLog(status, testContext.getName(), "User clicked on Close button", step + "2",
				"User re-direct to App page");
		org.testng.Assert.assertTrue(status, "User re-direct to App page");

		status = getAppStatus(appName).contains(appStatus);
		reportLog(status, testContext.getName(), "User clicked on Close button", step + "3", "App should be published");
		org.testng.Assert.assertTrue(status, "App should be published");
	}

	public void verifyPublishedApp(ITestContext testContext, String userName, String email, String appName,
			String appStatus, String step, String step2) throws InterruptedException {
		boolean status = getToastMsg().trim().toLowerCase()
				.contains(("Version 1 of the " + appName + " app has been published.").trim().toLowerCase())
				|| getToastMsg().trim().toLowerCase()
						.contains(("App '" + appName + "' has been updated").trim().toLowerCase());
		reportLog(status, testContext.getName(), "User clicked on Publish button", step + "1",
				"Version 1 of the " + appName + " app has been published.");
		org.testng.Assert.assertTrue(status, "Verify publish successful");

		status = isAppCreateButtonDisplayed();
		reportLog(status, testContext.getName(), "User clicked on Close button", step + "2",
				"User re-direct to App page");
		org.testng.Assert.assertTrue(status, "User re-direct to App page");

		status = getAppStatus(appName).contains(appStatus);
		reportLog(status, testContext.getName(), "User clicked on Close button", step + "3", "App should be published");
		org.testng.Assert.assertTrue(status, "App should be published");

		status = isAppNameDisplayed(appName);
		reportLog(status, testContext.getName(), "Verify Created App in Apps page after publishing the app", step2,
				"Created App with name: " + appName + " displayed in the list in Apps page");
		org.testng.Assert.assertTrue(status, "App should be displayed");
	}

	public void verifyPublishedApp(ITestContext testContext, String version, String appName, String step)
			throws InterruptedException {

		boolean status = getToastMsg().trim().toLowerCase()
				.contains((version + " of the " + appName + " app has been published.").trim().toLowerCase())
				|| getToastMsg().trim().toLowerCase()
						.contains(("App '" + appName + "' has been updated").trim().toLowerCase());
		reportLog(status, testContext.getName(), "User clicked on Publish button", step + "1",
				"Version 1 of the " + appName + " app has been published.");
		org.testng.Assert.assertTrue(status, "Verify publish successful");

		status = isAppCreateButtonDisplayed();
		reportLog(status, testContext.getName(), "User clicked on Close button", step + "2",
				"User re-direct to App page");
		org.testng.Assert.assertTrue(status, "User re-direct to App page");
	}

	/**
	 * This verifies the "Retired" tag status of an App
	 */
	public boolean verifyRetiredAppStatus() throws InterruptedException {
		if (retiredStatus.isDisplayed()) {
			return true;
		}

		return false;
	}

	public boolean verifyScreens(String[] simpScreens, String[] parLoopScreens, String[] cLoopScreens) {
		boolean tempPresent = false;
		for (int i = 0; i < simpScreens.length; i++) {
			if (!simpScreens[i].equals("")) {
				for (WebElement t : simpleScreens) {
					if (t.getText().equals(simpScreens[i])) {
						tempPresent = true;
					}
				}
				if (tempPresent == false)
					break;
			}
		}

		for (int i = 0; i < parLoopScreens.length; i++) {
			if (!parLoopScreens[i].equals("")) {
				for (WebElement t : parentLoopScreens) {
					if (t.getText().equals(parLoopScreens[i])) {
						tempPresent = true;
					}
				}
				if (tempPresent == false)
					break;
			}
		}

		for (int i = 0; i < cLoopScreens.length; i++) {
			if (!cLoopScreens[i].equals("")) {
				for (WebElement t : childLoopScreens) {
					if (t.getText().equals(cLoopScreens[i])) {
						tempPresent = true;
					}
				}
				if (tempPresent == false)
					break;
			}
		}

		return tempPresent;
	}

	public boolean verifySimpFieldCounts(String[] screens, String[] fieldCounts) throws InterruptedException {
		for (WebElement s : simpleScreens) {
			try {
				String sName = s.getText();
				s.click();

				for (int i = 0; i < screens.length; i++) {
					if (screens[i].equals(sName)) {
						int fCount = Integer.parseInt(fieldCounts[i]);
						if (fieldLabels.size() != fCount) {
							return false;
						}
					}
				}
			} catch (Exception e) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView(true);", s);

				try {
					String sName = s.getText();
					s.click();

					for (int i = 0; i < screens.length; i++) {
						if (screens[i].equals(sName)) {
							int fCount = Integer.parseInt(fieldCounts[i]);
							if (fieldLabels.size() != fCount) {
								return false;
							}
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	public boolean verifyTemplates(String[] temps) {
		boolean tempPresent = false;
		for (int i = 0; i < temps.length; i++) {
			for (WebElement t : templates) {
				if (t.getText().equals(temps[i]))
					tempPresent = true;
			}
			if (tempPresent == false)
				break;
		}

		return tempPresent;
	}

	public boolean verifyToastMsgIsDisplayed() throws InterruptedException {

		if (fluentWait(toast, driver).isDisplayed()) {
			return true;
		}

		return false;
	}

	public boolean verifyUsers(String[] users) {
		boolean userPresent = false;
		for (int i = 0; i < users.length; i++) {
			for (WebElement u : userEmails) {
				if (u.getText().equals(users[i]))
					userPresent = true;
			}
			if (userPresent == false)
				break;
		}

		return userPresent;
	}

	public void verifyReferenceDataPopulation(ITestContext testContext, String populatedValue[], String city[],
			String step) {

		for (int i = 0; i < city.length; i++) {

			WebElement scrolldrop = cityDropDownSearch;
			cityDropDown.click();
			customWait(5);
			cityDropDownSearch.sendKeys(city[i]);
			scrolldrop.sendKeys(Keys.TAB);

			boolean status = textStatic.getText().equals(populatedValue[i]);

			reportLog(status, testContext.getName(), "Selected value of city id from drop down is :",
					step + "." + i + "",
					" Populated value for selected city  " + city[i] + "  is  : " + populatedValue[i] + "");
		}
	}

	public boolean verifyVersion(String appversion) throws InterruptedException {
		appVersion.getText();
		customWait(3);
		boolean versionOfApp = appVersion.getText().equals(appversion);
		return versionOfApp;

	}

	public void allowDuplicateData(ITestContext testContext, String populatedValue[], String city[], String step) {

		for (int i = 0; i < city.length; i++) {
			newLoopItemIcon.click();
			fluentWait(cityDropDown1.get(i), driver).click();
			serachcityDropDown.get(i).sendKeys(city[i]);
			serachcityDropDown.get(i).sendKeys(Keys.ENTER);
			boolean status = textStatic.getText().equals(populatedValue[i]);
			reportLog(status, testContext.getName(), "Selected duplicate value of city id from drop down is :",
					step + "." + i + "", "User able to select duplicate value and duplicate value for selected city  "
							+ city[i] + "  is  : " + populatedValue[i] + "");
		}
	}

	public void verifyEditableField(ITestContext testContext, String city, String populatedVal, String step) {
		cityDropDown.click();
		cityDropDownSearch.sendKeys(city);
		reportLog(true, testContext.getName(), "", step + "1", "Entered value in Drop down field is : " + city);
		shortText.click();
		shortText.sendKeys(populatedVal);
		reportLog(true, testContext.getName(), "", step + "2",
				"Entered value in short text field is : " + populatedVal);
	}

	public String verifyNonEditableField(String noneditablevalue, String editablevalue) {
		cityDropDown.click();
		cityDropDownSearch.sendKeys(noneditablevalue);
		customWait(3);
		String editabeText = noRecord.getText();
		shortText.click();
		shortText.sendKeys(editablevalue);
		return editabeText;
	}

	public void verifyValuesInDropDownFieldOperator1(ITestContext testContext, String operatorDropDownValue[],
			String step) {

		clickOperatorDropDownOp1.click();
		customWait(1);
		for (int i = 0; i < operatorDropDownValue.length; i++) {
			By by = By.xpath(String.format(numberNameXpath, operatorDropDownValue[i]));
			waitForVisbility(driver, by, 10);
			List<WebElement> dropDownValues = driver.findElements(by);
			String defaultValue = getElementText(driver, dropDownValues.get(0));
			boolean status = defaultValue.equals(operatorDropDownValue[i]);
			reportLog(status, testContext.getName(), "Verify value in drop down:",
					step + "." + i + " Value  " + operatorDropDownValue[i] + " from drop down 'operator 1' ",
					"Is verfied");
			Assert.assertTrue(status, "Actual :" + defaultValue + ", Expected :" + operatorDropDownValue[i]);

		}
	}

	/**
	 * This method will using will verify default value in the operator field is the
	 * expect value. It will get default selected value through the operator which
	 * you provide to compare the expect Value. operatorFieldCount: which operator
	 * you want to verify expectValue: the expect value
	 * 
	 * @param operatorFieldCount
	 * @param expactValue
	 * @return
	 */
	public boolean verifyOperatorDefaultValue(String operatorFieldCount, String expectValue) {
		WebElement operatorEle = getEleByStringFormat(driver, operatorFields, operatorFieldCount);
		String defaultValue = operatorEle.findElement(By.xpath("./span/span[2]")).getText();
		return defaultValue.trim().equals(expectValue.trim());
	}

	/**
	 * Negative test method, If element displayed return false, not displayed return
	 * true
	 * 
	 * @param operatorFieldCount
	 * @return
	 */
	public boolean verifyEmptyOperator2() {
		boolean emptyFields = isElementPresent(driver, operatorDorpdowm2);
		if (emptyFields)
			return false;
		return true;
	}

	public void deleteOperators(String operator) {
		clickEle(driver, getEleByStringFormat(driver, deleteOperationIcon, operator));
	}

	public void confirmDeleteOperator() {
		clickEle(driver, waitForVisbility(driver, confirmDeleteOp, 15));
		reportLog(true, "", "Confirm click delete button  ", "", "Successfully delete operator");
	}

	public void clickDoneOnCalculationField() {
		clickEle(driver, waitForVisbility(driver, doneButton, 15));
	}

	public CreateAppPage closeButton() throws InterruptedException {
		clickOnHiddenElement(closeButton, driver);
		customWait(2);
		return this;
	}

	public CreateAppPage clickCloseButton() throws InterruptedException {
		clickOnHiddenElement(closeButton, driver);
		customWait(2);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(createAppButton));
		return this;
	}

	public void verifyValuesInDropDownFieldOperator2(ITestContext testContext, String operatorDropDownValue[],
			String step) {
		customWait(1);
		clickOperatorDropDownOp2.click();
		customWait(1);
		for (int i = 0; i < operatorDropDownValue.length; i++) {
			By by = By.xpath(String.format(numberNameXpath, operatorDropDownValue[i]));
			waitForVisbility(driver, by, 10);
			List<WebElement> dropDownValues = driver.findElements(by);
			String defaultValue = getElementText(driver, dropDownValues.get(0));
			boolean status = defaultValue.equals(operatorDropDownValue[i]);
			reportLog(status, testContext.getName(), "Verify value in drop down:",
					step + "." + i + " Value  " + operatorDropDownValue[i] + " from drop down 'operator 2' ",
					"Is verfied");
			Assert.assertTrue(status, "Actual :" + defaultValue + ", Expected :" + operatorDropDownValue[i]);

		}
	}

	public boolean addNewField(ITestContext testContext, String operatorDropDownValue[]) {

		fluentWait(clickOnOperator, driver).click();
		fluentWait(addField.get(1), driver).click();
		fluentWait(clickOperatorDropDownOp3, driver).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		customWait(3);

		List<WebElement> dropDownValues = driver
				.findElements(By.xpath(String.format(numberNameXpath, operatorDropDownValue[0])));
		fluentWait(dropDownValues.get(0), driver).click();

		boolean status = operator3SelectedValue.getText().equals(operatorDropDownValue[0]);
		return status;

	}

	public void addConstant(ITestContext testContext, String operatorDropDownValue[], String numberDecimal) {

		fluentWait(clickOnOperator, driver).click();
		customWait(3);
		// driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		fluentWait(addConstant.get(2), driver).click();
		enterConstantValue.clear();
		enterConstantValue.sendKeys(numberDecimal);
		// driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		customWait(2);
		fluentWait(doneButton, driver).click();
	}

	public CreateAppPage verifyCalculationSavedValues(ITestContext testContext, String operator1, String operator2,
			String operator3, String operator4, String step1, String step2, String step3, String step4, String appName)
			throws InterruptedException {

		boolean dropDown1Value = operator1DropDownValue.getText().equals(operator1);
		reportLog(dropDown1Value, testContext.getName(), "On Calculation pop up model", step1 + "",
				"First drop down saved value: " + operator1 + " Is Verified");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		// customWait(2);

		boolean dropDown2Value = operator2DropDownValue.getText().equals(operator2);
		reportLog(dropDown2Value, testContext.getName(), "On Calculation pop up model", step2 + "",
				"Second drop down saved value: " + operator2 + " Is Verified");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		// customWait(2);

		boolean dropDown3Value = operator3DropDownValue.getText().equals(operator3);
		reportLog(dropDown3Value, testContext.getName(), "On Calculation pop up model", step3 + "",
				"Third drop down saved value: " + operator3 + " Is Verified");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		// customWait(3);

		moveToEleClick(driver, enterConstantValue);

		fluentWait(enterConstantValue, driver).click();
		boolean dropDown4Value = enterConstantValue.getAttribute("value").equals(operator4);
		reportLog(dropDown4Value, testContext.getName(), "On Calculation pop up model", step4 + "",
				"Entered constant value: " + operator4 + " Is Verified");

		fluentWait(doneButton, driver).click();
		CreateAppPage appBuilderPage = closeButton().clickDonotSave().clickAppName(appName).destroyApp();

		return appBuilderPage;
	}

	// ---QA-101

	public void verifyCalculationFieldSavedValues(ITestContext testContext, String operator1, String operator2,
			String operator3, String operator4, String operator5, String operator6, String operator7, String step1,
			String step2, String step3, String step4, String step5, String step6, String step7, String appName)
			throws InterruptedException {

		boolean dropDown1Value = operator1DropDownValue.getText().equals(operator1);
		reportLog(dropDown1Value, testContext.getName(), "On Calculation pop up model", step1 + "",
				"After Edit First drop down saved value: " + operator1 + " Is Verified");
		org.testng.Assert.assertTrue(dropDown1Value);
		// driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		boolean dropDown2Value = operator2DropDownValue.getText().equals(operator2);
		reportLog(dropDown2Value, testContext.getName(), "On Calculation pop up model", step2 + "",
				"After Edit Second drop down saved value: " + operator2 + " Is Verified");
		org.testng.Assert.assertTrue(dropDown2Value);
		// driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		boolean dropDown3Value = operator3DropDownValue.getText().equals(operator3);
		reportLog(dropDown3Value, testContext.getName(), "On Calculation pop up model", step3 + "",
				"After Edit Third drop down saved value: " + operator3 + " Is Verified");
		org.testng.Assert.assertTrue(dropDown3Value);
		// driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", operator4DropDownValue);

		boolean dropDown4Value = operator4DropDownValue.getText().equals(operator4);
		reportLog(dropDown4Value, testContext.getName(), "On Calculation pop up model", step4 + "",
				"After Edit Fourth drop down saved value: " + operator4 + " Is Verified");
		org.testng.Assert.assertTrue(dropDown4Value);
		// driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		Actions actions = new Actions(driver);
		actions.moveToElement(operator5DropDown.get(1));
		actions.click();
		actions.build().perform();

		boolean dropDown5Value = operator5DropDownValue.getText().equals(operator5);
		reportLog(dropDown5Value, testContext.getName(), "On Calculation pop up model", step5 + "",
				"After Edit Fifth drop down saved value: " + operator5 + " Is Verified");
		org.testng.Assert.assertTrue(dropDown5Value);
		// driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		actions.moveToElement(operator6DropDown.get(1));
		actions.click();
		actions.build().perform();

		boolean dropDown6Value = operator6DropDownValue.getText().equals(operator6);
		reportLog(dropDown6Value, testContext.getName(), "On Calculation pop up model", step6 + "",
				"After Edit Sixth drop down saved value: " + operator6 + " Is Verified");
		js.executeScript("arguments[0].scrollIntoView();", enterConstantsValues);
		org.testng.Assert.assertTrue(dropDown6Value);

		waitForVisbility(driver, enterConstantsValues, 10);
		boolean constantValue = enterConstantsValues.getAttribute("value").equals(operator7);
		reportLog(constantValue, testContext.getName(), "On Calculation pop up model", step7 + "",
				"After edit Entered constant value: " + operator7 + " Is Verified");
		org.testng.Assert.assertTrue(constantValue);
	}

	public void selectFieldAndApplyOperators(ITestContext testContext, String operatorDropDownValue[],
			String operatorselectedDropDownValue[]) {

		int i = 1;
		int j = 3;
		int k = 2;
		int m = 0;

		addNewFieldsAndOperator(i, j);
		selectValueFromDropDown(testContext, operatorDropDownValue, "6.1.0", 0, k, operatorselectedDropDownValue, m);
		clickSubOperator();
		customWait(2);

		addNewFieldsAndOperator(++i, ++j);
		selectValueFromDropDown(testContext, operatorDropDownValue, "6.1.1", 1, ++k, operatorselectedDropDownValue,
				++m);
		clickDivOperator();
		customWait(2);

		addNewFieldsAndOperator(++i, ++j);
		selectValueFromDropDown(testContext, operatorDropDownValue, "6.1.2", 0, ++k, operatorselectedDropDownValue,
				++m);
		clickMulOperator();
		customWait(2);

		addNewFieldsAndOperator(++i, ++j);
		selectValueFromDropDown(testContext, operatorDropDownValue, "6.1.3", 1, ++k, operatorselectedDropDownValue,
				++m);
		clickPlusOperator();
		customWait(2);
	}

	public void addConstants(ITestContext testContext, String operatorDropDownValue[], String numberDecimal) {

		fluentWait(clickOnOperator, driver).click();
		customWait(2); // implicit,explicit wait not working
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		fluentWait(addConstant.get(5), driver).click();
		enterConstantsValues.clear();
		enterConstantsValues.sendKeys(numberDecimal);
		customWait(2); // implicit,explicit wait not working
		fluentWait(doneButton, driver).click();
	}

	public void addNewFieldsAndOperator(int i, int j) {
		waitForVisbility(driver, clickOnOperator, 10);
		clickOnOperator.click();
		customWait(2); // implicit,explicit wait not working
		waitForVisbility(driver, addField.get(i), 10);
		addField.get(i).click();
		By by = By.xpath("//label[contains(.,'Operator " + j
				+ "')]/../div//span[contains(@class,'btn btn-default form-control ui-select-toggle')]");
		waitForVisbility(driver, by, 10);
		driver.findElement(by).click();
	}

	public void clickSubOperator() {
		fluentWait(clickonMinusoperator, driver).click();
	}
	
	/*
	 * This Method used to get Operator Symbol
	 */
	public CreateAppPage verifySymbolOfOperator(ITestContext testContext, String[] symbolArray, String[] stepsArry,
			String appName) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		for (int i = 1; i <= symbolArray.length; i++) {
			String dropDown1Value1 = driver
					.findElement(By.xpath("//*[@id=\"bar\"]/div[" + (i + 1) + "]/field-operations/div[1]/div"))
					.getText();
			boolean operatorVal = dropDown1Value1.equals(symbolArray[i - 1]);
			reportLog(operatorVal, testContext.getName(), "On Calculation pop up model In Edit Mode",
					stepsArry[i - 1] + "", "After Edit Operator symbol value : " + symbolArray[i - 1] + " Is Verified");
			org.testng.Assert.assertTrue(operatorVal);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		}
		customWait(2);
		fluentWait(doneButton, driver).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		wait.until(ExpectedConditions.elementToBeClickable(closeButton));
		CreateAppPage appBuilderPage = closeButton().clickDonotSave().clickAppName(appName).destroyApp();
		return appBuilderPage;
	}

	public void clickDivOperator() {
		waitForClickablility(driver, clickonDivideOperator, 15);
		clickEle(driver, clickonDivideOperator);
		// fluentWait(clickonDivideOperator, driver).click();
	}

	public void clickMulOperator() {
		fluentWait(clickonMulOperator, driver).click();
	}

	public void clickPlusOperator() {
		fluentWait(clickonPlusOperator, driver).click();
	}

	public void selectValueFromDropDown(ITestContext testContext, String operatorDropDownValue[], String step,
			int value2, int value1, String operatorselectedDropDownValue[], int value) {
		List<WebElement> dropDownValues = driver
				.findElements(By.xpath(String.format(numberNameXpath, operatorDropDownValue[value2])));
		fluentWait(dropDownValues.get(0), driver).click();
		customWait(1);
		boolean status = driver.findElement(By.xpath(".//*[@id='operation_field_" + value1 + "']")).getText()
				.equals(operatorselectedDropDownValue[value]);
		reportLog(status, testContext.getName(), "",
				step + " Verify value " + operatorselectedDropDownValue[value] + "  selected from drop down ", "");

	}
	
	public void clickCancelCalculation() {
		clickEle(driver, CancelButton);
	}
	

	
	public void xToDeleteOperator(ITestContext testContext,String step) {
		int beforeDele = operators.size();
		WebElement deleteIcon = operators.get(6).findElement(By.tagName("a"));
		waitForVisbility(driver, deleteIcon, 10);
		clickOnHiddenElement(deleteIcon, driver);
		clickEle(driver, xButtonOperator);
		int afterDele = operators.size();
		reportLog(beforeDele==afterDele,testContext.getName() , "click X icon to cancel deletion",step ,"X icon cancel deletion,Before delete size:"+beforeDele+", After cancel deletion: "+afterDele);
		org.testng.Assert.assertTrue(beforeDele==afterDele);
	}
	
	public void noButtonCancelDeleOperator(ITestContext testContext, String step) {
		int beforeDele = operators.size();
		WebElement deleteIcon = operators.get(4).findElement(By.tagName("a"));
		waitForVisbility(driver, deleteIcon, 10);
		clickOnHiddenElement(deleteIcon, driver);
		clickEle(driver, noButtonOperator);
		int afterDele = operators.size();
		reportLog(beforeDele==afterDele,testContext.getName() , "click No button to cancel deletion",step ,"Click No cancel deletion,Before delete size:"+beforeDele+", After cancel deletion: "+afterDele);
		org.testng.Assert.assertTrue(beforeDele==afterDele);
	}

	public void confirmDeleOperator(ITestContext testContext,String step) {
		int beforeDele = operators.size();
		WebElement deleteIcon = operators.get(3).findElement(By.tagName("a"));
		waitForVisbility(driver, deleteIcon, 10);
		clickOnHiddenElement(deleteIcon, driver);
		clickEle(driver, confirmDeleteOp);
		int afterDele = operators.size();
		reportLog(beforeDele>afterDele,testContext.getName() , "click No button to cancel deletion",step ,"Click Yes confirm deletion,Before delete size:"+beforeDele+", After confirmed deletion: "+afterDele);
		org.testng.Assert.assertTrue(beforeDele>afterDele);
	}
}
