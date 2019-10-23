package com.canvas.qa.pages.apps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.PublishAppPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.qa.test.UtilityFunctions;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author shalini.pathak
 *
 */

public class AppBuilderPage extends BasePage {

	@FindBy(xpath = "//span[contains(.,'Add screen')]")
	WebElement addScreenButton;

	@FindBy(xpath = "//a[contains(.,'  APP')]")
	WebElement appBuilderHeader;

	@FindBy(xpath = "//table[@class='table table-hover table-striped']//a[contains(.,'Try This App First - Copy') ]")
	WebElement appDupName;

	@FindBy(xpath = "//table[@class='table table-hover table-striped']//a[contains(.,'Try This App First - Copy') ]/../following-sibling::td//span[contains(.,'New')]")
	WebElement appDupStatus;

	@FindBy(xpath = "//input[contains(@placeholder,'Untitled App')]")
	WebElement appName;

	@FindBy(xpath = "//input[contains(@placeholder,'Untitled App')]")
	WebElement appNameTextBox;

	String appNameXpath = "//a[text() = '%s']";

	@FindBy(xpath = "//a[contains(.,'App with no custom logo')]")
	WebElement appNamNew;

	@FindBy(xpath = "//table[@class='table table-hover table-striped']//a[contains(.,'Try This App First - Copy') ]/../following-sibling::td//small[contains(.,'version 1')]")
	WebElement appVersion;

	@FindBy(xpath = "//li[contains(.,'Barcode')]")
	WebElement barcodeField;

	@FindBy(xpath = "//div[contains(.,'New Barcode')]")
	WebElement barcodeFieldTextBox;

	@FindBy(id = "start-tamplate")
	WebElement btnStart;

	@FindBy(xpath = "//li[contains(.,'Calculation')]")
	WebElement calculationField;

	@FindBy(xpath = "//div[contains(.,'New Calculation')]")
	WebElement calculationFieldTextBox;

	@FindBy(xpath = "//li[contains(.,'Checkbox)]")
	WebElement checkboxField;

	@FindBy(xpath = "//div[contains(.,'New Checkbox')]")
	WebElement checkboxFieldTextBox;

	@FindBy(id = "files")
	WebElement chooseFile;

	@FindBy(xpath = "//a[contains(.,'Close')]")
	WebElement closeButton;

	@FindBy(id = "condition_0")
	WebElement condition;

	@FindBy(id = "condition_field_0")
	WebElement conditionField;

	@FindBy(id = "condition_screen_0")
	WebElement conditionScreen;

	@FindBy(id = "condition_value_0")
	WebElement conditionValueTextBox;

	String copyFieldIconXpath = "//div[@class = 'field-label']//div[contains(text(),'%s')]//parent::div//parent::div//parent::div//parent::field//preceding-sibling::ng-include//a[@title = 'Make copy of this field']";

	// @FindBy(xpath = "//span[contains(.,'Copy App')]")
	@FindBy(xpath = "//*[contains(text(),'Copy App')]")
	WebElement copyIcon;

	// @FindBy(xpath = "//span[contains(.,'Copy App')]")
	@FindBy(xpath = "//*[contains(text(),'Copy App')]")
	List<WebElement> copyIconNew;

	@FindBy(xpath = "//span[contains(@class,'duplicate-icon')]")
	WebElement copyScreenIcon;

	@FindBy(xpath = "//li[contains(.,'Date')]")
	WebElement dateField;

	@FindBy(xpath = "//div[contains(.,'New Date')]")
	WebElement dateFieldTextBox;

	@FindBy(xpath = "//label[contains(.,'Decimal Places')]//following-sibling::select[@id = 'style']")
	WebElement decimalDDL;

	@FindBy(xpath = ".//*[@id='right-sidebar-screens-container']/div/div[1]/screen-outline/sheet-outline/div/p")
	WebElement defaultScreen;

	@FindBy(id = "default_value")
	WebElement defaultValue;

	@FindBy(xpath = "//table[@class='table table-hover table-striped']//a[contains(.,'Try This App First')]")
	List<WebElement> deletedAppDisplay;

	@FindBy(xpath = "//button[contains(.,'Delete field')]")
	WebElement deleteFieldButton;

	String deleteFieldIconXpath = "//div[@class = 'field-label']//div[contains(text(),'%s')]//parent::div//parent::div//parent::div//parent::field//preceding-sibling::ng-include//a[@title = 'Delete this field']";

	@FindBy(xpath = "//span[@title = 'Delete this screen']")
	WebElement deleteIcon;

	@FindBy(xpath = "//button[contains(.,'Delete screen')]")
	WebElement deleteScreenButton;

	@FindBy(xpath = "//span[contains(.,'Destroy App')]")
	WebElement destroyAppButton;

	// @FindBy(xpath = "//span[contains(.,'Destroy App')]")
	@FindBy(xpath = "//a[contains(.,'Destroyed Apps')]")
	WebElement destroyedApp;

	@FindBy(xpath = "//a[contains(.,'Done')]")
	WebElement doneButton;

	@FindBy(xpath = "//button[contains(.,'Keep Editing')]")
	WebElement keepEditing;

	@FindBy(xpath = "//li[contains(@title, 'Settings')]")
	WebElement settingButton;

	@FindBy(xpath = "//input[@ng-change='setAllowDuplicate()']")
	WebElement alllowDuplicate;

	@FindBy(xpath = "//a[@title='Create Calculation']")
	WebElement editCalculation;

	@FindBy(xpath = "//label[@title='Calculation']")
	WebElement popUpTitle;
	/***
	 * This method close app buider without saving the data
	 * 
	 * 
	 */

	@FindBy(xpath = "//button[@data-ng-click='modalOptions.ok();']")
	WebElement doNotSave;

	@FindBy(xpath = "//li[contains(.,'Drawing')]")
	WebElement drawingField;

	@FindBy(xpath = "//div[contains(.,'New Drawing')]")
	WebElement drawingFieldTextBox;

	private WebDriver driver = BrowserLaunchTest.driver;

	@FindBy(xpath = "//li[contains(.,'Drop Down')]")
	WebElement dropDownField;

	@FindBy(xpath = "//div[contains(.,'New Dropdown')]")
	WebElement dropDowntFieldTextBox;

	String dropDownValueXpath = "//div[@title = '%s']//div";

	String elementFieldXpath = "//div[contains(@title,'%s')]";

	String elementXpath = "//span[contains(.,'%s')]";

	@FindBy(xpath = "//label[contains(.,'Email Body')]//input")
	WebElement emailBodyCheckBox;

	@FindBy(xpath = "//label[contains(.,'Email Subject')]//input")
	WebElement emailSubjectCheckBox;

	@FindBy(xpath = "//div[@id = 'existingKeyFieldsCandidates']//div//span//i")
	WebElement existingFieldDropDown;

	@FindBy(id = "export_label")
	WebElement exportLabel;

	@FindBy(xpath = "//span[contains(.,'Field Conditions')]")
	WebElement fieldConditions;

	@FindBy(id = "field_mask")
	WebElement fieldMask;

	@FindBy(id = "key_field_label")
	WebElement fieldName;

	String fieldNameXpath = "//li[contains(.,'%s')]";

	// @FindBy(xpath = "//i[@class = 'fa fa-list flip-h']")
	@FindBy(xpath = ".//*[@title='Fields']")

	WebElement fieldsButton;

	@FindBy(xpath = "//span[text() = 'Field Settings']")
	WebElement fieldSettings;

	@FindBy(id = "style")
	WebElement fieldStyleDDL;

	String fieldXpath = "//div[@class = 'field-label']//div[contains(text(),'%s')]";

	@FindBy(xpath = "//p[contains(.,'Customer Contact Information')]")
	WebElement firstScreen;

	@FindBy(xpath = "//div[@data-placeholder = 'New Screen 1']")
	WebElement firstScreenName;

	@FindBy(xpath = "//ftux-message[@step-number = '1']")
	WebElement ftuxMessage;

	@FindBy(xpath = "//span[contains(.,'GoCanvas Only')]")
	WebElement goCanvasOnly;

	@FindBy(xpath = "//li[contains(.,'GPS')]")
	WebElement gpsField;

	@FindBy(xpath = "//div[contains(.,'New GPS')]")
	WebElement gpsFieldTextBox;

	@FindBy(xpath = "//div[@id = 'workspace']//div[3]//span//strong")
	WebElement gridEnabledMessage;

	@FindBy(xpath = "//div[@id = 'condition_field_0']/div/span/i")
	WebElement icon;

	String iconXpath = "//div[@class = 'field-label']//div[contains(.,'%s')]/preceding-sibling::i[contains(@class,'reference-data-icon')]";

	// @FindBy(xpath = "//img[@ng-src='/images/template_options/T---Blank.png']")
	// WebElement imageBlankTemplate;

	@FindBy(xpath = "(//li[@ng-repeat='template in template_options'])[1]/span/img")
	WebElement imageBlankTemplate;

	@FindBy(xpath = "//img[@alt='Waiver']")
	WebElement imageWaiverTemplate;

	String labelFieldXpath = "//div[@class = 'field-label']//div[contains(.,'%s')]";

	String labelNameXpath = "//div[@class = 'field-label']//div[contains(.,'%s')]";

	String labelNameXpath1 = "//label[contains(.,'%s')]";

	String labelXpath = "//div[@class = 'field-label']//textarea[contains(@placeholder,'%s') and contains(@class,'ng-valid ng-empty')]";

	@FindBy(xpath = "//li[contains(.,'Long Text')]")
	WebElement longTextField;

	@FindBy(xpath = "//div[contains(.,'New Long Text')]")
	WebElement longTextFieldTextBox;

	@FindBy(xpath = "//span[contains(@class,'loop-in')]")
	WebElement loopIcon;

	@FindBy(id = "max_length")
	WebElement maxCharacterLength;

	@FindBy(id = "maximum")
	WebElement maximum;

	@FindBy(id = "minimum")
	WebElement minimum;

	@FindBy(xpath = "//label[contains(.,'Mobile Visible')]//input")
	WebElement mobileVisibleCheckBox;

	@FindBy(xpath = "//span[contains(.,'More')]//following-sibling::i")
	WebElement moreLink;

	@FindBy(xpath = "//li[contains(.,'Multi Choice')]")
	WebElement multipleChoiceField;

	@FindBy(xpath = "//div[contains(.,'New Multi Choice')]")
	WebElement multipleChoiceFieldTextBox;

	@FindBy(xpath = "//span[contains(.,'New field condition')]")
	WebElement newFieldConditionButton;

	@FindBy(xpath = "//span[contains(.,'New screen condition')]")
	WebElement newScreenConditionButton;

	@FindBy(xpath = "//button[contains(.,'Next')]")
	WebElement nextButton;

	@FindBy(xpath = "//li[contains(.,'Number')]")
	WebElement numberField;

	@FindBy(xpath = "//div[contains(.,'New Number')]")
	WebElement numberFieldTextBox;

	@FindBy(xpath = "//div[@ng-include = 'getTemplateUrl()']//label[contains(.,'Page Break After')]//input")
	WebElement pageBreakAfterCheckBox;

	@FindBy(xpath = "//h1[contains(.,'Apps')]")
	WebElement pageName;

	@FindBy(xpath = "//li[contains(.,'Payment')]")
	WebElement paymentField;

	@FindBy(xpath = "//div[contains(.,'New Payment')]")
	WebElement paymentFieldTextBox;

	@FindBy(xpath = "//a[contains(.,'PDF')]")
	WebElement pdfButton;

	@FindBy(xpath = "//label[contains(.,'PDF File Name')]//input")
	WebElement pdfFileNameCheckBox;

	@FindBy(xpath = "//div[@class = 'form-group ng-scope']//select[@id = 'pdf_visible']")
	WebElement pdfVisibleDDL;

	@FindBy(id = "report_label")
	WebElement pdfWebLabel;

	@FindBy(xpath = "//li[contains(.,'Photo')]")
	WebElement photoField;

	@FindBy(xpath = "//div[contains(.,'New Photo')]")
	WebElement photoFieldTextBox;

	@FindBy(xpath = "//i[contains(@class,'add_reference_data')]")
	WebElement plusIcon;

	@FindBy(xpath = "//div[@id = 'workspace']//div[3]//button[text() = ' Preview Grid']")
	WebElement previewGridButton;

	@FindBy(xpath = "//input[contains(@value,'Publish')]")
	WebElement publishButton;

	@FindBy(xpath = "//a[contains(.,'Publish to device')]")
	WebElement publishToDeviceButton;

	@FindBy(xpath = "//label[contains(.,'Read Only')]//input")
	WebElement readOnlyCheckBox;

	@FindBy(id = "recipt_label")
	WebElement receiptLabel;

	@FindBy(xpath = "//span[contains(.,'Reference Data')]")
	WebElement referenceData;

	@FindBy(id = "referenceData_description")
	WebElement referenceDataDescription;

	@FindBy(id = "reference_data")
	WebElement referenceDataFieldElement;

	@FindBy(id = "referenceData_name")
	WebElement referenceDataName;

	@FindBy(id = "reference_screen")
	WebElement referenceDataScreen;

	@FindBy(id = "reference_columns")
	WebElement referenceDataValue;

	@FindBy(id = "reference_fields")
	WebElement referenceFields;

	String referenceFieldXpath = "//div[@id = 'reference_fields']//ul/li//div[@title='%s']";

	@FindBy(xpath = "//div[contains(.,'Click to rename your app')]")
	List<WebElement> renameYourApp;

	@FindBy(xpath = "//label[contains(.,'Required')]//input")
	WebElement requiredCheckBox;

	@FindBy(xpath = "//span[contains(.,'Save')]")
	WebElement saveButton;

	@FindBy(xpath = "//button[contains(@class,'save_reference_data_button')]")
	WebElement saveReferenceDataButton;

	@FindBy(xpath = "//span[contains(.,'Screen Condition(s)')]")
	WebElement screenConditions;

	@FindBy(xpath = ".//*[@id='right-sidebar-screens-container']/div/div[1]/screen-outline/sheet-outline/div")
	List<WebElement> screenName;

	@FindBy(xpath = "//div[@data-placeholder='New Screen 1']")
	WebElement newScreen1;

	String screenNameFieldXpath = "//div[@data-placeholder = '%s']";

	String screenNameXpath = "//div[@id = 'right-sidebar-screens-container']//p[. = '%s']";

	@FindBy(id = "common_search_field")
	List<WebElement> searchField;

	@FindBy(xpath = "//i[contains(@class,'fa fa-search text-muted')]")
	WebElement searchIcon;

	@FindBy(xpath = "//p[contains(.,'Waiver Language')]")
	WebElement secondScreen;

	@FindBy(xpath = "//div[@id='right-sidebar-screens-container']/div/div")
	List<WebElement> rightSidebarScreens;

	@FindBy(xpath = "//div[@drag-source='screen']/div//div[@class='field-label']")
	List<WebElement> dragFields;

	@FindBy(xpath = "//div[@id='screen_container']//img")
	List<WebElement> staticImgs;

	@FindBy(xpath = "//div[@class='modal__wrapper static-image-modal__wrapper']//input")
	WebElement enterImgName;

	@FindBy(xpath = "//div[@class = 'field-label']/div")
	List<WebElement> screenLableNames;

	// String screenNameFieldXpath1 = "//div[@data-placeholder = '%s']";
	// public AppBuilderPage clickScreen(String label, String screenPlaceHolder)
	// {
	// WebElement element =
	// driver.findElement(By.xpath(String.format(screenNameFieldXpath1,
	// screenPlaceHolder)));
	// element.sendKeys(label);
	// return this;
	// }

	@FindBy(xpath = "//div[@data-placeholder = 'New Screen 2']")
	WebElement secondScreenName;

	@FindBy(xpath = "//li[@title= 'Settings']/i")
	WebElement settingsButton;

	@FindBy(xpath = "//li[contains(.,'Short Text')]")
	WebElement shortTextField;

	@FindBy(xpath = "//div[@class = 'field-label']//textarea[contains(@placeholder,'New Short Text')]")
	WebElement shortTextFieldTextBox;

	@FindBy(xpath = "//li[contains(.,'Signature')]")
	WebElement signatureField;

	@FindBy(xpath = "//div[contains(.,'New Signature')]")
	WebElement signatureFieldTextBox;

	@FindBy(xpath = "//span[contains(.,'Standard PDF')]//following-sibling::i")
	WebElement standardPDFLink;

	@FindBy(xpath = "//li[contains(.,'Static Text')]")
	WebElement staticTextField;

	@FindBy(xpath = "//div[contains(.,'New Static Text')]")
	WebElement staticTextFieldTextBox;

	@FindBy(id = "field_type")
	WebElement styleDDL;

	@FindBy(xpath = "//label[contains(.,'Submission Name')]//input")
	WebElement submissionNameCheckBox;

	@FindBy(xpath = "//li[contains(.,'Summary')]")
	WebElement summaryField;

	@FindBy(xpath = "//div[contains(.,'New Summary')]")
	WebElement summaryFieldTextBox;

	@FindBy(xpath = "//p[contains(.,'Client Signature')]")
	WebElement thirdScreen;

	@FindBy(xpath = "//li[contains(.,'Time')]")
	WebElement timeField;

	@FindBy(xpath = "//div[contains(.,'New Time')]")
	WebElement timeFieldTextBox;

	@FindBy(xpath = "//div[@class = 'toast-message']")
	WebElement toastMessage;

	// @FindBy(xpath = ".//*[@id='opentip-16']/div")
	@FindBy(xpath = "//*[contains(text(),'Creates a copy of this app')]")
	WebElement toolTip;

	@FindBy(id = "listKeySourceExistingField")
	WebElement useExistingField;

	@FindBy(xpath = "//li[contains(.,'Web Link')]")
	WebElement webLinkField;

	@FindBy(xpath = "//div[contains(.,'New Web Link')]")
	WebElement webLinkFieldTextBox;

	@FindBy(xpath = "//div[@class = 'form-group ng-scope']//select[@id = 'web_visible']")
	WebElement webVisibleDDL;

	@FindBy(id = "style")
	WebElement selectStyle;

	@FindBy(xpath = "//div[@class='modal__footer']//button[2]")
	WebElement selectButton;

	@FindBy(xpath = "//img[contains(@src,'Test.JPG')]")
	WebElement selectExistingImage;

	@FindBy(id = "reference-image-upload")
	WebElement clickUploadImage;

	@FindBy(xpath = "//div[@ng-if='uploadError']")
	WebElement invalidImageFormat;

	@FindBy(xpath = "//div[@class='button-group']/button[contains(.,'Cancel' )]")
	WebElement cancelButton;

	@FindBy(xpath = "//div[@id='modal-footer']/div[contains(@class,'Error')]")
	WebElement errorMsgCalculation;

	@FindBy(id = "summary_screen")
	WebElement summaryScreenDropDown;

	@FindBy(id = "summary_fields")
	WebElement openDropDownSumField;

	String summaryFieldsDropDown = "//div[@id='summary_fields']//div[.='%s']";

	@FindBy(xpath = "//div[@class='form-group payment-field-extra']/label")
	WebElement dataPaymentDropdown;

	// div[@class='form-group payment-field-extra']//div[@class='ui-select-match
	// ng-scope']
	@FindBy(xpath = "//div[@class='form-group payment-field-extra']/div/div[1]")
	WebElement dataPaymentlabelDropdown;

	@FindBy(xpath = "//div[@class='form-group payment-field-extra']/div/div/span//div")
	WebElement dataPaymentDropDwonText;

	@FindBy(xpath = "//div[@class='form-group payment-field-extra']//div[@role='option']")
	List<WebElement> dataPaymentlabelDropdownOpts;

	@FindBy(xpath = "//div[@class='reference-image__wrapper ng-scope']")
	List<WebElement> allImgs;

	@FindBy(xpath = "//a[@class='photo--change']")
	WebElement changeImageLink;

	@FindBy(xpath = "//div[@id='reference_data_settings']//span/i[@data-content='Edit']")
	WebElement editReferenceDataBtn;

	@FindBy(xpath = "//div[@id='reference_data_settings']//span/i[@data-content='View']")
	WebElement viewRefDataBtn;

	@FindBy(xpath = "//label[@for='reference_columns']")
	WebElement labelRefColumn;

	@FindBy(xpath = "//label[@for='reference_screen']")
	WebElement labelRefScreen;

	@FindBy(xpath = "//label[@for='reference_fields']")
	WebElement labelRefField;

	@FindBy(xpath = "//div[@class='view_label_reference_data']//span[1]")
	WebElement tableName;

	@FindBy(xpath = "//div[@class='view_label_reference_data']//button")
	WebElement xTableIcon;

	@FindBy(xpath = "//button[.='Type my own']")
	WebElement dropDownTypeOwnBtn;

	@FindBy(xpath = "//div[@id='value_list_options']/div//input")
	List<WebElement> inputVlaue;

	@FindBy(id = "drop-down-default-value-select")
	WebElement defaultValueBox;

	@FindBy(className = "ui-select-choices-row-inner")
	List<WebElement> defaultValuetext;

	@FindBy(xpath = "//button[.='Start with a common list']")
	WebElement dropDownCommonList;

	@FindBy(xpath = "//div[contains(@class,'common-list-header')]/button")
	WebElement dropDownCommonBackBtn;

	@FindBy(xpath = "//div[@class='common-list']/div/label")
	List<WebElement> commonListTitles;

	@FindBy(xpath = "//div[@id='value_list_options']//input")
	List<WebElement> commonListValues;

	@FindBy(tagName = "calculation-operations")
	List<WebElement> calOperations;

	@FindBy(xpath = "//div[@class='form-group ng-scope']//span[@aria-label]")
	WebElement existingFieldDropDownBox;
	String dropDownXpath = "//div[@ng-attr-id]//div[@title='%s']";

	public AppBuilderPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		// mixedContentChecker(driver);

		javaScriptErrorChecker(driver);

	}

	public AppBuilderPage addLabelToScreen(String screenName, String[] screenLabels, String[] screenFields)
			throws InterruptedException {

		clickScreen(screenName);
		clickFieldsButton();
		for (int j = 0; j < screenFields.length; j++) {
			clickField(screenFields[j]);
			enterFieldLabel("New " + screenFields[j], screenLabels[j]);
			clickFieldsButton();
		}

		return this;
	}

	public AppBuilderPage addReferenceData(String referenceDataName, String referenceValue, int screenIndex,
			String referenceDataField) throws InterruptedException {
		clickReferenceData();
		selectReferenceData(referenceDataName);
		selectReferenceDataValue(referenceValue);
		selectReferenceDataScreen(screenIndex);// select by value or text
		if (referenceDataField != null) {
			selectReferenceField(referenceDataField);
		}
		clickSaveButton();
		return this;
	}

	public AppBuilderPage addReferenceDataFromCSV(String referenceDataField, String referenceDataName,
			String referenceDataDescription, String filePath) throws InterruptedException {
		customWait(2);
		clickLabelField(referenceDataField);
		clickReferenceData();
		clickPlusIcon();
		enterReferenceDataName(referenceDataName);
		enterReferenceDataDescription(referenceDataDescription);
		uploadFile(filePath);
		clickSaveReferenceDataButton();
		return this;
	}

	/**
	 * @param appBuilder
	 * @param placeHolder
	 * @param screenName
	 * @param screenLabels
	 * @param screenFields
	 * @return
	 * @throws InterruptedException
	 */
	public AppBuilderPage addScreenAndAddLabel(String placeHolder, String screenName, String[] screenLabels,
			String[] screenFields) throws InterruptedException {
		boolean appStatus = isDefaultScreenDisplayed();
		customWait(3); // Implict & Explicit wait not working here so custom wait is
						// requiredd

		if (appStatus == true) {
			String defaultScreenName = defaultScreen.getText();
			customWait(4); // Implict & Explicit wait not working here so custom
							// wait is required
			if (defaultScreenName.equals("New Screen 1")) {
				newScreen1.click();
				newScreen1.clear();
				enterScreenName(screenName, placeHolder);
				customWait(4); // Implict & Explicit wait not working here so
								// custom wait is required
				clickFieldsButton();
				for (int j = 0; j < screenFields.length; j++) {
					clickField(screenFields[j]);
					enterFieldLabel("New " + screenFields[j], screenLabels[j]);
					clickFieldsButton();
				}
			} else if ((defaultScreenName != "New Screen 1")) {
				clickAddScreenButton();
				enterScreenName(screenName, placeHolder);
				customWait(3); // Implict & Explicit wait not working here so
								// custom wait is required
				clickFieldsButton();
				for (int j = 0; j < screenFields.length; j++) {
					clickField(screenFields[j]);
					enterFieldLabel("New " + screenFields[j], screenLabels[j]);
					clickFieldsButton();
				}
			}
		} else {
			clickAddScreenButton();
			enterScreenName(screenName, placeHolder);
			customWait(4); // Implict & Explicit wait not working here so custom
							// wait is required
			clickFieldsButton();
			for (int j = 0; j < screenFields.length; j++) {
				clickField(screenFields[j]);
				enterFieldLabel("New " + screenFields[j], screenLabels[j]);
				clickFieldsButton();
			}
		}

		return this;
	}

	public AppBuilderPage clearAppName() throws InterruptedException {
		customWait(120);
		Actions actions = new Actions(driver);
		actions.moveToElement(appNameTextBox);
		actions.click();
		actions.build().perform();
		appNameTextBox.clear();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return this;

	}

	public AppBuilderPage clickAddScreenButton() {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", addScreenButton);
		// addScreenButton.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public void clickAppName() throws InterruptedException

	{
		appNamNew.click();
	}

	public void keepEditingButton() throws InterruptedException

	{
		clickOnHiddenElement(keepEditing, driver);

	}

	public AppBuilderPage clickBarCodeField() {
		longTextField.click();
		return this;
	}

	public AppBuilderPage clickBlankTemplate() {
		fluentWait(imageBlankTemplate, driver).click();
		return this;
	}

	public AppBuilderPage clickCalculationField() {
		longTextField.click();
		return this;
	}

	public AppBuilderPage clickCheckboxField() {
		longTextField.click();
		return this;
	}

	public CreateAppPage clickCloseButton() throws InterruptedException {
		customWait(2);
		clickOnHiddenElement(fluentWait(closeButton, driver), driver);
		return new CreateAppPage(driver);
	}

	public boolean clickCloseWithoutSave(String message) throws InterruptedException {
		fluentWait(closeButton, driver).click();
		fluentWait(doNotSave, driver).click();
		customWait(3); // Implict & Explicit wait not working here so custom
						// wait is requiredd
		boolean pageName1 = pageName.getText().equals(message);
		return pageName1;
	}

	public AppBuilderPage clickCopyScreenIcon() throws InterruptedException {
		clickOnHiddenElement(copyScreenIcon, driver);
		return this;
	}

	public AppBuilderPage clickDateField() {
		longTextField.click();
		return this;
	}

	public AppBuilderPage clickDeleteFieldButton() throws InterruptedException {
		clickOnHiddenElement(deleteFieldButton, driver);
		return this;
	}

	public AppBuilderPage clickDeleteIcon() {
		clickOnHiddenElement(deleteIcon, driver);
		return this;
	}

	public AppBuilderPage clickDeleteScreenButton() {
		clickOnHiddenElement(deleteScreenButton, driver);
		return this;
	}

	public AppBuilderPage clickDoneButton() throws InterruptedException {
		clickOnHiddenElement(doneButton, driver);
		return this;
	}

	public AppBuilderPage clickDrawingField() {
		longTextField.click();
		return this;
	}

	public AppBuilderPage clickDropDownField() {
		longTextField.click();
		return this;
	}

	public AppBuilderPage clickEmailBodyCheckBox() throws InterruptedException {
		scrollToEle(driver, emailBodyCheckBox);
		clickOnHiddenElement(emailBodyCheckBox, driver);
		return this;
	}

	public AppBuilderPage clickEmailSubjectCheckBox() throws InterruptedException {
		scrollToEle(driver, emailSubjectCheckBox);
		clickOnHiddenElement(emailSubjectCheckBox, driver);
		return this;
	}

	public AppBuilderPage clickExistingFieldDropDown() throws InterruptedException {
		clickOnHiddenElement(existingFieldDropDown, driver);
		return this;
	}

	public AppBuilderPage clickField(String fieldName) throws InterruptedException {
		WebElement element = getEleByStringFormat(driver, fieldNameXpath, fieldName);
		clickOnHiddenElement(element, driver);
		return this;
	}

	public AppBuilderPage clickFieldConditions() throws InterruptedException {
		clickOnHiddenElement(fieldConditions, driver);
		return this;
	}

	public AppBuilderPage clickFieldsButton() {
		clickOnHiddenElement(fieldsButton, driver);
		return this;
	}

	public AppBuilderPage clickGPSField() {
		longTextField.click();
		return this;
	}

	public AppBuilderPage clickLabel(String labelName) throws InterruptedException {
		getEleByStringFormat(driver, labelNameXpath, labelName).click();
		return this;
	}

	public AppBuilderPage clickLabelField(String labelName) throws InterruptedException {
		customWait(2);
		clickEle(driver, getEleByStringFormat(driver, labelFieldXpath, labelName));
		return this;
	}

	public AppBuilderPage clickLongText() {
		longTextField.click();
		return this;
	}

	public AppBuilderPage clickLoopIcon() throws InterruptedException {
		clickOnHiddenElement(loopIcon, driver);

		return this;
	}

	public AppBuilderPage clickMobileVisibleCheckBox() throws InterruptedException {
		scrollToElementClick(driver, mobileVisibleCheckBox);
		return this;
	}

	public AppBuilderPage clickMoreLink() throws InterruptedException {
		clickOnHiddenElement(moreLink, driver);
		return this;
	}

	public AppBuilderPage clickMultipleChoiceField() {
		longTextField.click();
		return this;
	}

	public AppBuilderPage clickNewFieldConditionButton() throws InterruptedException {
		clickOnHiddenElement(newFieldConditionButton, driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage clickNewScreenConditionButton() throws InterruptedException {
		clickOnHiddenElement(newScreenConditionButton, driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public PublishAppPage clickNextButton() {
		waitForClickablility(driver, nextButton, 20);
		clickOnHiddenElement(nextButton, driver);
		return new PublishAppPage(driver);
	}

	public AppBuilderPage clickNumberField() {
		clickEle(driver, longTextField);
		return this;
	}

	public AppBuilderPage clickPageBreakAfterCheckBox() throws InterruptedException {
		clickOnHiddenElement(pageBreakAfterCheckBox, driver);
		return this;
	}

	public AppBuilderPage clickPaymentField() {
		clickEle(driver, longTextField);
		return this;
	}

	public PDFDesignerPage clickPDFButton() throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", pdfButton);
		clickEle(driver, pdfButton);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		customWait(5); // Implict & Explicit wait not working here so custom
						// wait is required
		return new PDFDesignerPage(driver);
	}

	public AppBuilderPage clickPDFFileNameCheckBox() throws InterruptedException {
		scrollToElementClick(driver, pdfFileNameCheckBox);
		return this;
	}

	public AppBuilderPage clickPhotoField() {
		longTextField.click();
		return this;
	}

	public AppBuilderPage clickPlusIcon() {
		waitForVisbility(driver, plusIcon, 10);
		clickOnHiddenElement(plusIcon, driver);
		return this;
	}

	public PublishAppPage clickpublishButton() {
		waitForVisbility(driver, publishButton, 60);
		clickOnHiddenElement(publishButton, driver);
		return new PublishAppPage(driver);
	}

	public void clickPublishToDevice() {
		clickOnHiddenElement(waitForVisbility(driver, publishToDeviceButton, 15), driver);
		customWait(2);
	}

	public PublishAppPage clickPublishToDeviceButton() {
		customWait(6);
		clickOnHiddenElement(publishToDeviceButton, driver);
		customWait(6); // added extra wait time for loading, previously 3 secs
		// this section is commented out due the "Next" button no longer being there in
		// certain scenarios
		return new PublishAppPage(driver);
	}

	public AppBuilderPage clickReadOnlyCheckBox() throws InterruptedException {
		clickOnHiddenElement(readOnlyCheckBox, driver);
		return this;
	}

	/**
	 * This method will check the referenceData dropDown, if not will click
	 * reference data showing the dropdown. If DropDown displayed will do nothing.
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public AppBuilderPage clickReferenceData() throws InterruptedException {
		waitForVisbility(driver, referenceData, 10);
		clickOnHiddenElement(referenceData, driver);
		return this;
	}

	public AppBuilderPage clickRequiredCheckBox() throws InterruptedException {
		clickOnHiddenElement(requiredCheckBox, driver);
		return this;
	}

	public AppBuilderPage clickSaveButton() throws InterruptedException {
		clickOnHiddenElement(saveButton, driver);
		customWait(6);
		return this;
	}

	public AppBuilderPage clickSaveButton(String appName, String userName, String password)
			throws InterruptedException {
		clickEle(driver, saveButton);
		enterAppNameInList(appName, userName, password);
		return this;
	}

	public AppBuilderPage clickSaveReferenceDataButton() throws InterruptedException {
		clickEle(driver, saveReferenceDataButton);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage clickScreen(String screenName) throws InterruptedException {
		WebElement element = getEleByStringFormat(driver, screenNameXpath, screenName);
		moveToEleClick(driver, element);
		customWait(2);
		return this;
	}

	public AppBuilderPage clickScreenConditions() throws InterruptedException {
		clickOnHiddenElement(screenConditions, driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage clickSettingsButton() {
		clickOnHiddenElement(settingsButton, driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage clickShortText() throws InterruptedException {
		clickOnHiddenElement(shortTextField, driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage clickSignatureField() {
		clickEle(driver, longTextField);
		return this;
	}

	public AppBuilderPage clickStandardPDFLink() throws InterruptedException {
		clickOnHiddenElement(standardPDFLink, driver);
		return this;
	}

	public AppBuilderPage clickStartButton() {
		clickEle(driver, btnStart);
		waitForVisbility(driver, appNameTextBox, 40);
		return this;
	}

	public AppBuilderPage clickStaticText() {
		clickEle(driver, longTextField);
		return this;
	}

	public AppBuilderPage clickSubmissionNameCheckBox() throws InterruptedException {
		scrollToElementClick(driver, submissionNameCheckBox);
		return this;
	}

	public AppBuilderPage clickSummaryField() {
		clickEle(driver, longTextField);
		return this;
	}

	public AppBuilderPage clickTimeField() {
		clickEle(driver, longTextField);
		return this;
	}

	public AppBuilderPage clickUseExistingField() throws InterruptedException {
		clickOnHiddenElement(useExistingField, driver);
		customWait(10); // Implict & Explicit wait not working here so
						// Thread.sleep is required
		return this;
	}

	public AppBuilderPage clickWaiverTemplate() {
		clickEle(driver, imageWaiverTemplate);
		return this;
	}

	public AppBuilderPage clickWebLinkField() {
		clickEle(driver, longTextField);
		return this;
	}

	public AppBuilderPage copyScreenField(String fieldName) throws InterruptedException {
		WebElement element = driver.findElement(By.xpath(String.format(fieldXpath, fieldName)));
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click();
		actions.build().perform();
		customWait(1);
		WebElement element2 = driver.findElement(By.xpath(String.format(copyFieldIconXpath, fieldName)));
		clickOnHiddenElement(element2, driver);
		customWait(1); // waits for screen field to be copied
		return this;
	}

	public AppBuilderPage createApp(String appName) throws InterruptedException {
		clickBlankTemplate();
		clickStartButton();
		enterAppName(appName);
		return this;
	}

	/**
	 * @param dashboardPage
	 * @param appName
	 * @param testContext
	 * @return
	 * @throws InterruptedException
	 */
	public AppBuilderPage createApp(String appName, ITestContext testContext, String step) throws InterruptedException {
		clickBlankTemplate();
		clickStartButton();
		boolean status = isAppNameTextBoxDisplayed();
		reportLog(status, testContext.getName(), "User clicked on Create App & then Start button", step + ".0",
				"App builder Opented");
		org.testng.Assert.assertTrue(status, "App builder should open");
		status = isSaveButtonDisplayed();
		reportLog(status, testContext.getName(), "App builder opened with Save button", step + ".1",
				"Save button displayed");
		org.testng.Assert.assertTrue(status);
		status = isCloseButtonDisplayed();
		reportLog(status, testContext.getName(), "App builder opened with Close button", step + ".2",
				"Close button displayed");
		org.testng.Assert.assertTrue(status);
		status = isPublishToDeviceButtonDisplayed();
		reportLog(status, testContext.getName(), "App builder opened with Publish To Device button", step + ".3",
				"Publish To Device button displayed");
		org.testng.Assert.assertTrue(status);
		enterAppName(appName);
		return this;
	}

	public AppBuilderPage createAppWithScreen(String appName) throws InterruptedException {
		clickBlankTemplate();
		clickStartButton();
		customWait(2);
		clickAddScreenButton();
		enterAppName(appName);
		return this;
	}

	/***
	 * This method create App with selecting existing template
	 * 
	 * 
	 */
	public boolean createAppWithTemplate() throws InterruptedException {
		clickWaiverTemplate();
		clickStartButton();
		waitForVisbility(driver, appNameTextBox, 30);
		boolean status = isAppNameTextBoxDisplayed();
		return status;
	}

	/**
	 * TO verify default screen when there is publish app with version 2 or more
	 **/

	public boolean createDefaultApp() throws InterruptedException {
		clickBlankTemplate();
		clickStartButton();
		boolean status = isAppNameTextBoxDisplayed();
		return status;

	}

	public void deletePendingApp(String appName) throws InterruptedException

	{
		String deleteAppStatusxpath = "//a[contains(.,'" + appName
				+ "')]/../following-sibling::td//i[@class='fa fa-trash fa-fw fa-lg']";
		driver.findElement(By.xpath(String.format(deleteAppStatusxpath))).click();
		driver.switchTo().alert().accept();

	}

	public boolean deletePendingApp(String appName, String deleteCopyAppMsg) throws InterruptedException

	{
		String deleteAppStatusxpath = "//a[contains(.,'" + appName
				+ "')]/../following-sibling::td//i[@class='fa fa-trash fa-fw fa-lg']";

		WebElement deleteAppStatusXpath = driver.findElement(By.xpath(String.format(deleteAppStatusxpath)));
		clickOnHiddenElement(deleteAppStatusXpath, driver);
		// deleteAppStatusXpath.click();
		// driver.findElement(By.xpath(String.format(deleteAppStatusxpath))).click();
		driver.switchTo().alert().accept();
		customWait(2);
		boolean deleteAppmsg = toastMessage.getText().contains(deleteCopyAppMsg);
		return deleteAppmsg;
	}

	public AppBuilderPage deleteScreenField(String fieldName) throws InterruptedException {
		WebElement element = driver.findElement(By.xpath(String.format(fieldXpath, fieldName)));
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click();
		actions.build().perform();
		customWait(1);
		WebElement element2 = driver.findElement(By.xpath(String.format(deleteFieldIconXpath, fieldName)));
		clickOnHiddenElement(element2, driver);
		customWait(1);
		return this;
	}

	public PublishAppPage destroyedApp() {
		clickOnHiddenElement(destroyedApp, driver);
		return new PublishAppPage(driver);
	}

	public boolean duplicateAppVersion(String duplicateAppVersion) throws InterruptedException

	{
		moveToElement(driver, appVersion);
		boolean app_version = appVersion.getText().equals(duplicateAppVersion);
		return app_version;
	}

	public boolean duplicatesAppName(String duplicateAppName) throws InterruptedException

	{
		moveToElement(driver, appDupName);
		boolean app_name = appDupName.getText().equals(duplicateAppName);
		return app_name;
	}

	public boolean duplicatesAppStatus(String duplicateAppStatus) throws InterruptedException

	{
		moveToElement(driver, appDupStatus);
		boolean app_status = appDupStatus.getText().contains(duplicateAppStatus);
		return app_status;
	}

	public AppBuilderPage enterAppName(String appName) throws InterruptedException {
		moveToEleClick(driver, appNameTextBox);
		enterTextField(driver, appNameTextBox, appName);
		/*
		 * JavascriptExecutor jse = (JavascriptExecutor)driver;
		 * jse.executeScript("arguments[0].value='"+appName+"';", appNameTextBox);
		 */
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return this;

	}

	public AppBuilderPage enterBarcodeLabel(String label) {
		barcodeFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterCalculationLabel(String label) {
		calculationFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterCheckboxLabel(String label) {
		checkboxFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterConditionValue(String conditionValue) throws InterruptedException {

		clickEle(driver, conditionValueTextBox);
		enterTextField(driver, conditionValueTextBox, conditionValue);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage enterDateLabel(String label) {
		dateFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterDefaultValue(String value) {
		defaultValue.sendKeys(value);
		return this;
	}

	public AppBuilderPage enterDrawingLabel(String label) {
		drawingFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterDropDownLabel(String label) {
		dropDowntFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterExportLabel(String value) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", exportLabel);
		exportLabel.sendKeys(value);
		return this;
	}

	// New Static Image, My Static Image
	public AppBuilderPage enterFieldLabel(String labelName, String value) throws InterruptedException {
		WebElement element = getEleByStringFormat(driver, labelXpath, labelName);
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click();
		actions.sendKeys(value);
		actions.build().perform();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage enterFieldMask(String value) {
		fieldMask.sendKeys(value);
		return this;
	}

	public AppBuilderPage enterFieldName(String name) throws InterruptedException {
		fieldName.sendKeys(name);
		return this;
	}

	public AppBuilderPage enterFirstScreenName(String label) {
		firstScreenName.sendKeys(label);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage enterGPSLabel(String label) {
		gpsFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterLongTextLabel(String label) {
		longTextFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterMaxCharacterLength(String value) {
		maxCharacterLength.sendKeys(value);
		return this;
	}

	public AppBuilderPage enterMaximum(String value) {
		maximum.sendKeys(value);
		return this;
	}

	public AppBuilderPage enterMinimum(String value) {
		minimum.sendKeys(value);
		return this;
	}

	public AppBuilderPage enterMultipleChoiceLabel(String label) {
		multipleChoiceFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterNumberLabel(String label) {
		numberFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterPaymentLabel(String label) {
		paymentFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterPDFWebLabel(String value) {
		scrollToEle(driver, pdfWebLabel);
		enterTextField(driver, pdfWebLabel, value);
		return this;
	}

	public AppBuilderPage enterPhotoLabel(String label) {
		photoFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterReceiptLabel(String value) {
		scrollToEle(driver, receiptLabel);
		enterTextField(driver, receiptLabel, value);
		return this;
	}

	public AppBuilderPage enterReferenceDataDescription(String description) {
		enterTextField(driver, referenceDataDescription, description);
		// referenceDataDescription.sendKeys(description);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage enterReferenceDataName(String name) {
		enterTextField(driver, referenceDataName, name);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage enterScreenName(String label, String screenPlaceHolder) {
		WebElement element = driver.findElement(By.xpath(String.format(screenNameFieldXpath, screenPlaceHolder)));
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click();
		actions.sendKeys(label);
		actions.build().perform();
		// element.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterSecondScreenName(String label) {
		secondScreenName.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterShortTextLabel(String shortText) throws InterruptedException {
		// shortTextFieldTextBox.sendKeys(shortText);
		Actions actions = new Actions(driver);
		actions.moveToElement(shortTextFieldTextBox);
		actions.click();
		actions.sendKeys(shortText);
		actions.build().perform();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage enterSignatureLabel(String label) {
		signatureFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterStaticTextLabel(String label) {
		staticTextFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterSummaryLabel(String label) {
		summaryFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterTimeLabel(String label) {
		timeFieldTextBox.sendKeys(label);
		return this;
	}

	public AppBuilderPage enterWebLinkLabel(String label) {
		webLinkFieldTextBox.sendKeys(label);
		return this;
	}

	public String getConditionValue() throws InterruptedException {
		String fieldValue = conditionValueTextBox.getAttribute("value");
		return fieldValue;
	}

	public String getDefaultValue() {
		return defaultValue.getAttribute("value");
	}

	public String getExportLabel() {
		return exportLabel.getAttribute("value");
	}

	public int getFieldCount(String fieldName) throws InterruptedException {
		List<WebElement> element = driver.findElements(By.xpath(String.format(fieldXpath, fieldName)));
		return element.size();
	}

	public String getFieldMask() {
		return fieldMask.getAttribute("value");
	}

	public String getFtuxMessage() throws InterruptedException {
		waitForVisbility(driver, ftuxMessage, 15);
		return ftuxMessage.getText();
	}

	public String getGridEnabledMessage() {
		return gridEnabledMessage.getText();
	}

	public String getMaximum() {
		return maximum.getAttribute("value");
	}

	public String getMaximumLength() {
		return maxCharacterLength.getAttribute("value");
	}

	public String getMinimum() {
		return minimum.getAttribute("value");
	}

	public String getPdfWebLabel() {
		return pdfWebLabel.getAttribute("value");
	}

	public String getReceiptLabel() {
		return receiptLabel.getAttribute("value");
	}

	public String getSelectedCondition() throws InterruptedException {
		return getFirstSeleOption(driver, condition);
	}

	public String getSelectedConditionField() throws InterruptedException {
		String fieldValue = conditionField.getText();
		return fieldValue;
	}

	public String getSelectedConditionScreen() throws InterruptedException {
		String fieldValue = getFirstSeleOption(driver, conditionScreen);
//		waitForVisbility(driver, conditionScreen, 10);
//		Select dropDown = new Select(conditionScreen);
//		String fieldValue = dropDown.getFirstSelectedOption().getText();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return fieldValue;
	}

	public String getSelectedDecimalPlace() {
		return getFirstSeleOption(driver, decimalDDL);
	}

	public String getSelectedFieldStyle() {
		return getFirstSeleOption(driver, fieldStyleDDL);
	}

	public String getSelectedPDFVisible() {
		;
		return getFirstSeleOption(driver, pdfVisibleDDL);
	}

	public String getSelectedWebVisible() {
		return getFirstSeleOption(driver, webVisibleDDL);
	}

	public String getToastMessage() throws InterruptedException {
		waitForVisbility(driver, toastMessage, 15);
		return toastMessage.getText();
	}

	public PublishAppPage goCanvasOnly() {
		clickOnHiddenElement(goCanvasOnly, driver);
		waitForVisbility(driver, destroyedApp, 60);
		return new PublishAppPage(driver);
	}

	public boolean isAdvancedLabelsDisplayed(String labelName) throws InterruptedException {
		return driver.findElement(By.xpath(String.format(labelNameXpath1, labelName))).isDisplayed();
	}

	public boolean isAppBuilderEditMode(String indicator) {
		return appBuilderHeader.getText().contains(indicator);
	}

	public boolean isAppDisplayAfterDelete() throws InterruptedException {
		return deletedAppDisplay.size() > 0;
	}

	public boolean isAppNameTextBoxDisplayed() {
		return appNameTextBox.isDisplayed();
	}

	public boolean isCloseButtonDisplayed() {
		return closeButton.isDisplayed();
	}

	public boolean isCopiedAppDelete(String deleteCopyAppMsg) throws InterruptedException {
		boolean copiedDeleteMsg = toastMessage.getText().contains(deleteCopyAppMsg);
		return copiedDeleteMsg;
	}

	public boolean isCopyIconDisplay() throws InterruptedException {
		return copyIconNew.size() > 0;
	}

	public boolean isDefaultScreenDisplayed() throws InterruptedException {
		customWait(3); // Implict & Explicit wait not working here so custom
						// wait is requiredd
		return screenName.size() > 0;
	}

	public boolean isEmailBodyCheckBoxSelected() {
		return emailBodyCheckBox.isSelected();
	}

	public boolean isEmailSubjectCheckBoxSelected() {
		return emailSubjectCheckBox.isSelected();
	}

	public boolean isFieldDisplayed(String fieldName) {
		List<WebElement> element = driver.findElements(By.xpath(String.format(fieldXpath, fieldName)));
		return element.size() > 0;
	}

	public boolean isFieldSettingsDisplayed() {
		return fieldSettings.isDisplayed();
	}

	public boolean isIconDisplayed(String labelName) {
		By by = By.xpath(String.format(iconXpath, labelName));
		waitForVisbility(driver, by, 15);
		boolean status = driver.findElement(by).isDisplayed();
		return status;
	}

	public boolean isLabelDisplayed(String labelName) {
		WebElement element = getEleByStringFormat(driver, labelNameXpath, labelName);
		return element.isDisplayed();
	}

	public boolean islabelArrayDisplayed(String[] labels) {
		List<Boolean> displayed = new ArrayList<>();
		for (int i = 0; i < labels.length; i++) {
			displayed.add(isLabelDisplayed(labels[i]));
		}
		return !displayed.remove(false);

	}

	/**
	 * This method check the field if deleted.
	 * 
	 * @param labelName : the field names.
	 * @return false if the element still displayed. true if the element no found in
	 *         the page
	 */
	public boolean isLabelDeleted(String labelName) {
		try {
			getEleByStringFormat(driver, labelNameXpath, labelName).isDisplayed();
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	public boolean isMobileVisibleCheckBoxSelected() {
		return mobileVisibleCheckBox.isSelected();
	}

	public boolean isPageBreakAfterCheckBoxSelected() {
		return pageBreakAfterCheckBox.isSelected();
	}

	public boolean isPdfFileNameCheckBoxSelected() {
		return pdfFileNameCheckBox.isSelected();
	}

	public boolean isPreviewGridButtonDisplayed() {
		return previewGridButton.isDisplayed();
	}

	public boolean isPublishToDeviceButtonDisplayed() {
		return publishToDeviceButton.isDisplayed();
	}

	public boolean isReadOnlyCheckBoxSelected() {
		return readOnlyCheckBox.isSelected();
	}

	public boolean isReferenceFieldDisplayed(String value) throws InterruptedException {
		referenceFields.click();
		return driver.findElement(By.xpath(String.format(referenceFieldXpath, value))).isDisplayed();
	}

	public boolean isRenameYourAppDisplayed() {
		return renameYourApp.size() > 0;
	}

	public boolean isRequiredCheckBoxSelected() {
		return requiredCheckBox.isSelected();
	}

	public boolean isSaveButtonDisplayed() {
		return saveButton.isDisplayed();
	}

	public boolean isScreenDisplayed(String screenName) throws InterruptedException {
		List<WebElement> element = driver.findElements(By.xpath(String.format(screenNameXpath, screenName)));
		return element.size() > 0;
	}

	public boolean isSubmissionNameCheckBoxSelected() {
		return submissionNameCheckBox.isSelected();
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

	public AppBuilderPage selectCondition(String conditionValue) throws InterruptedException {
		selectDropDown(condition, conditionValue, driver);
		customWait(3);
		return this;
	}

	public AppBuilderPage selectConditionField(String fieldName) throws InterruptedException {
		clickOnHiddenElement(conditionField, driver);
		// clickOnHiddenElement(getEleByStringFormat(driver, elementXpath, fieldName),
		// driver);
		WebElement element = getEleByStringFormat(driver, elementXpath, fieldName);
		clickOnHiddenElement(element, driver);
		return this;
	}

	public AppBuilderPage selectConditionScreen(int index) throws InterruptedException {
		waitForVisbility(driver, conditionScreen, 10);
		selectDropDown(conditionScreen, index, driver);
		return this;
	}

	public AppBuilderPage selectDecimalPlaces(String style) {
		selectDropDown(decimalDDL, style, driver);
		return this;
	}

	public AppBuilderPage selectExistingFiedValue(String field) {
		WebElement element = getEleByStringFormat(driver, dropDownValueXpath, field);
		element.click();
		return this;
	}

	public AppBuilderPage selectFieldStyle(String style) {
		selectDropDown(fieldStyleDDL, style, driver);
		return this;
	}

	public AppBuilderPage selectGridStyle(String style) {
		selectDropDown(styleDDL, style, driver);
		return this;
	}

	public AppBuilderPage selectPdfVisible(String style) {
		scrollToEle(driver, pdfVisibleDDL);
		selectDropDown(pdfVisibleDDL, style, driver);
		return this;
	}

	public AppBuilderPage selectReferenceData(String referenceDataText) throws InterruptedException {
		selectDropDown(referenceDataFieldElement, referenceDataText, driver);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage selectReferenceDataScreen(int index) throws InterruptedException {
		selectDropDown(referenceDataScreen, index, driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage selectReferenceDataValue(String referenceValue) throws InterruptedException {
		selectDropDown(referenceDataValue, referenceValue, driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage selectReferenceField(String value) throws InterruptedException {
		clickEle(driver, referenceFields);
		WebElement element = getEleByStringFormat(driver, referenceFieldXpath, value);
		clickEle(driver, element);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage selectScreenConditionField(String fieldName) throws InterruptedException {
		clickOnHiddenElement(icon, driver);
		customWait(5); // Implict & Explicit wait not working here so
						// Thread.sleep is required
		WebElement element = driver.findElement(By.xpath(String.format(elementFieldXpath, fieldName)));
		clickOnHiddenElement(element, driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	public AppBuilderPage selectWebVisible(String style) {
		scrollToEle(driver, webVisibleDDL);
		selectDropDown(webVisibleDDL, style, driver);
		return this;
	}

	public AppBuilderPage uploadFile(String filePath) throws InterruptedException {
		enterTextField(driver, chooseFile, filePath);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return this;
	}

	/**
	 * @param conditionScreen
	 * @param conditionField
	 * @param condition
	 * @param conditionValue
	 * @param appBuilder
	 * @return
	 * @throws InterruptedException
	 */
	public AppBuilderPage validateConditions(String conditionScreen, String conditionField, String condition,
			String conditionValue, ITestContext testContext, String step, String stepDescription)
			throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		boolean status = getSelectedConditionScreen().contains(conditionScreen);
		reportLog(status, testContext.getName(), stepDescription, step + 1,
				"Condition Screen : " + conditionScreen + " correctly displayed.");
		org.testng.Assert.assertTrue(status);

		status = getSelectedConditionField().contains(conditionField);
		reportLog(status, testContext.getName(), stepDescription, step + 2,
				"Condition Field : " + conditionField + " correctly displayed.");
		org.testng.Assert.assertTrue(status);

		status = getSelectedCondition().contains(condition);
		reportLog(status, testContext.getName(), stepDescription, step + 3,
				"Condition : " + condition + " correctly displayed.");
		org.testng.Assert.assertTrue(status);

		status = getConditionValue().contains(conditionValue);
		reportLog(status, testContext.getName(), stepDescription, step + 4,
				"Condition Value : " + conditionValue + " correctly displayed.");
		org.testng.Assert.assertTrue(status);

		return this;
	}

	public boolean verfiyAppStatusAfterDelete(String appName, String appStatus) throws InterruptedException

	{
		String appStatusxpath = "//a[contains(.,'" + appName
				+ "')]/../following-sibling::td//*[contains(text(),'Published')]";
		WebElement appNameXpath = driver.findElement(By.xpath(String.format(appStatusxpath)));
		boolean app_status = appNameXpath.getText().equals(appStatus);
		return app_status;
	}

	public boolean verfiyVersionAfterDelete(String appName, String versionUpdated) throws InterruptedException

	{
		String appVerision = "//a[contains(.,'" + appName + "')]/../following-sibling::td//small[@class='version']";
		String updatedVersion = driver.findElement(By.xpath(String.format(appVerision))).getText();
		boolean verionUpdated = updatedVersion.contains(versionUpdated);
		return verionUpdated;
	}

	public AppBuilderPage verifyAdvancedSerachlabels(String step, ITestContext testContext, String message,
			String[] labelList) throws InterruptedException {

		for (int j = 0; j < labelList.length; j++) {
			boolean status = isAdvancedLabelsDisplayed(labelList[j]);
			reportLog(status, testContext.getName(), message, step + j,
					" Label with name   " + labelList[j] + " of advanced search screen is displying.");
			org.testng.Assert.assertTrue(status);
		}

		return this;
	}

	public EditAppPage verifyAppExistInPage(String appName) throws InterruptedException {
		if (searchField.size() > 0) {
			searchField.get(0).clear();
			searchField.get(0).sendKeys(appName);
			searchIcon.click();
			Thread.sleep(5000);
		}
		return new EditAppPage(driver);

	}

	public boolean verifyAppNameText(String appNameAfterLogin) throws InterruptedException

	{
		Actions actions = new Actions(driver);
		actions.moveToElement(appName);
		actions.click();
		customWait(4); // Implict & Explicit wait not working here so custom
						// wait is required
		boolean appNameAfterLogin_1 = appName.getText().equals(appNameAfterLogin);
		customWait(2); // Implict & Explicit wait not working here so custom
						// wait is required
		return appNameAfterLogin_1;
	}

	public boolean verifyAppStatus(String appName, String appStatus) throws InterruptedException

	{
		String appStatusxpath = "//a[contains(.,'" + appName
				+ "')]/../following-sibling::td//span[@class='app_status_pending center-block p-xxs text-white text-center']";
		WebElement appNameXpath = driver.findElement(By.xpath(String.format(appStatusxpath)));
		boolean app_status = appNameXpath.getText().equals(appStatus);
		return app_status;
	}

	public boolean verifyAppVersion(String appName, String appversion) throws InterruptedException

	{
		String appStatusxpath = "//a[contains(.,'" + appName + "')]/../following-sibling::td//small[@class='version']";
		WebElement appNameXpath = driver.findElement(By.xpath(String.format(appStatusxpath)));
		boolean app_ver = appNameXpath.getText().equals(appversion);
		return app_ver;
	}

	public boolean verifyCopyAppIcon() throws InterruptedException {
		moveToElement(driver, copyIcon);
		boolean appStatus = isCopyIconDisplay();
		return appStatus;
	}

	public boolean verifyCopyText(String copyText) throws InterruptedException {
		moveToEleClick(driver, copyIcon);
		boolean status = toastMessage.getText().contains(copyText);
		return status;
	}

	/***
	 * This method Verify default screen exist or not
	 * 
	 * 
	 */

	public boolean verifyDefaultScreen() throws InterruptedException {
		boolean appStatus = isDefaultScreenDisplayed();
		return appStatus;
	}

	public boolean verifyFirstScreenName(String appNameAfterLogin) throws InterruptedException

	{
		firstScreen.getText();
		customWait(4);// Implict & Explicit wait not working here so custom wait
						// is required
		boolean firstScreenName = firstScreen.getText().equals(appNameAfterLogin);
		return firstScreenName;
	}

	public boolean verifyPopUpMsg(String appName, String popUpMsg) throws InterruptedException

	{
		String deleteAppStatusxpath = "//a[contains(.,'" + appName
				+ "')]/../following-sibling::td//i[@class='fa fa-trash fa-fw fa-lg']";

		WebElement deleteAppStatusXpath = driver.findElement(By.xpath(String.format(deleteAppStatusxpath)));
		clickOnHiddenElement(deleteAppStatusXpath, driver);
		// driver.findElement(By.xpath(String.format(deleteAppStatusxpath))).click();
		customWait(1);
		String popUpmsg = driver.switchTo().alert().getText();
		driver.switchTo().alert().dismiss();
		boolean deletePopUpmsg = popUpmsg.contains(popUpMsg);
		return deletePopUpmsg;
	}

	public AppBuilderPage verifyReferenceData(String name, String column, String screen, String field,
			ITestContext testContext, String step, String message) throws InterruptedException {

		if (name != null) {
			Select dropDown = new Select(referenceDataFieldElement);
			boolean status = dropDown.getFirstSelectedOption().getText().equals(name);
			reportLog(status, testContext.getName(), message, step + 1, "Value in first droprdown is: " + name);
			org.testng.Assert.assertTrue(status);
		}

		if (column != null) {
			Select dropDown = new Select(referenceDataValue);
			boolean status = dropDown.getFirstSelectedOption().getText().equals(column);
			reportLog(status, testContext.getName(), message, step + 2,
					"Value " + column + " in Reference Column in DROP DOWN");
			org.testng.Assert.assertTrue(status);
		}

		if (screen != null) {
			Select dropDown = new Select(referenceDataScreen);
			boolean status = dropDown.getFirstSelectedOption().getText().equals(screen);
			reportLog(status, testContext.getName(), message, step + 3,
					"Value " + screen + " in  Reference Screen in DROP DOWN");
			org.testng.Assert.assertTrue(status);
		}

		if (field != null) {
			boolean status = isReferenceFieldDisplayed(field);
			reportLog(status, testContext.getName(), message, step + 4,
					"Value " + field + " in  Reference Field in DROP DOWN");
			org.testng.Assert.assertTrue(status);
		}

		return this;
	}

	/**
	 * @param testContext
	 * @param message
	 * @param fieldList
	 * @param labelList
	 * @throws InterruptedException
	 */
	public AppBuilderPage verifyScreenLabels(String step, String screenName, ITestContext testContext, String message,
			String[] fieldList, String[] labelList) throws InterruptedException {
		clickScreen(screenName);
		for (int j = 0; j < labelList.length; j++) {
			boolean status = isLabelDisplayed(labelList[j]);
			reportLog(status, testContext.getName(), message, step + j,
					fieldList[j] + " : " + labelList[j] + " field of  " + screenName + " is displayed. ");
			org.testng.Assert.assertTrue(status);
		}

		return this;
	}

	public boolean verifySecondScreenName(String appNameAfterLogin) throws InterruptedException

	{
		secondScreen.getText();
		customWait(4);// Implict & Explicit wait not working here so custom wait
						// is required
		boolean firstScreenName = secondScreen.getText().equals(appNameAfterLogin);
		return firstScreenName;
	}

	public boolean verifyThirdScreenName(String appNameAfterLogin) throws InterruptedException

	{
		thirdScreen.getText();
		customWait(4);// Implict & Explicit wait not working here so custom wait
						// is required
		boolean firstScreenName = thirdScreen.getText().equals(appNameAfterLogin);
		return firstScreenName;
	}

	public boolean verifytoolTipText(String toolTipText) throws InterruptedException

	{
		moveToElement(driver, copyIcon);
		By by = By.xpath("//*[contains(text(),'Creates a copy of this app')]");
		waitForVisbility(driver, by, 15);
		boolean tooltip_msg = toolTip.getText().equals(toolTipText);
		return tooltip_msg;
	}

	public AppBuilderPage verifyupdatedScreenLabels(String step, String screenName, ITestContext testContext,
			String message, String[] fieldList, String[] labelList) throws InterruptedException {
		clickScreen(screenName);
		for (int j = 0; j < labelList.length; j++) {
			boolean status = isLabelDisplayed(labelList[j]);
			reportLog(status, testContext.getName(), message, step + j,
					fieldList[j] + ": 'Newly Added field' " + labelList[j] + " Display in  " + screenName + " ");
			org.testng.Assert.assertTrue(status);
		}
		return this;
	}

	public boolean isAddScreenDisplayed() {
		return addScreenButton.isDisplayed();
	}

	public AppBuilderPage clickAllowDuplicate() throws InterruptedException {
		fluentWait(settingButton, driver).click();
		fluentWait(alllowDuplicate, driver).click();
		return this;
	}

	public AppBuilderPage selectStyle(String style) {
		selectDropDown(selectStyle, style, driver);
		customWait(5);
		return this;
	}

	public boolean openCalculationPopUp(String popUptext) {
		fluentWait(editCalculation, driver).click();
		waitForClickablility(driver, By.xpath("//label[@title='Calculation']"), 15);
		boolean app_status = popUpTitle.getText().equals(popUptext);
		return app_status;
	}

	public void clickEditCalculation() {
		customWait(2);
		clickEle(driver, editCalculation);
	}

	public AppBuilderPage clickOnAddStaticImage(String[] screenLabels, String[] screenFields1)
			throws InterruptedException {
		clickFieldsButton();
		for (int j = 0; j < screenFields1.length; j++) {
			clickField(screenFields1[j]);
			// customWait(2);
		}
		return this;
	}

	/**
	 * @author yuan.gao OverLoading Click On Add Static Image
	 * @param screenFields1
	 * @return
	 * @throws InterruptedException
	 */
	public AppBuilderPage clickOnAddStaticImage(String[] screenFields1) throws InterruptedException {
		clickFieldsButton();
		for (int j = 0; j < screenFields1.length; j++) {
			clickField(screenFields1[j]);
			// customWait(2);
		}
		return this;
	}

	public AppBuilderPage enterStaticPhotoLabel(String[] screenLabels, String[] screenFields1, String[] screenFields2)
			throws InterruptedException {
		for (int j = 0; j < screenFields1.length; j++) {
			enterFieldLabel("New " + screenFields2[j], screenLabels[j]);

		}
		return this;
	}

	public AppBuilderPage uploadStaticPhoto(String referenceDataName, String filePath) throws InterruptedException {
		uploadPhoto(filePath);
		enterImgeName(referenceDataName);
		clickSelectButton();
		waitForClickablility(driver, saveButton, 15);
		return this;
	}

	public void enterImgeName(String textValue) {
		enterTextField(driver, enterImgName, textValue);
	}

	public AppBuilderPage selectExistingRefImage() throws InterruptedException {
		fluentWait(selectExistingImage, driver).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		clickSelectButton();
		return this;
	}

	public boolean imageuploadedsuccessfully() throws Exception {
		waitForVisbility(driver, selectExistingImage, 15);
		Boolean ImagePresent = (Boolean) ((JavascriptExecutor) driver).executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				selectExistingImage);
		return ImagePresent;
	}

	public void clickSelectButton() throws InterruptedException {
		fluentWait(selectButton, driver).click();
		customWait(2); // Implicit explicit wait not working
	}

	public boolean verifyInvalidImageFormat(String expacted) {
		return getElementText(driver, invalidImageFormat).equals(expacted);
	}

	public void clickCancelButton() {
		clickEle(driver, cancelButton);
		// cancelButton.click();
	}

	public void uploadPhoto(String filePath) throws InterruptedException {
		clickUploadImage.sendKeys(filePath);
		customWait(3); // Implicit explicit wait not working
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void moveToScreenDelete(String screenName) {
		WebElement screen = getEleByStringFormat(driver, screenNameXpath, screenName);
		moveToEleClick(driver, screen);
		clickDeleteIcon();
		clickDeleteScreenButton();
	}

	public boolean verifyErrorMsgOnCalculation(String expectMsg) {
		return waitForVisbility(driver, errorMsgCalculation, 10).getText().trim().equals(expectMsg);

	}

	public void selectSummaryScreen(String screenName) {
		selectDropDown(summaryScreenDropDown, screenName, driver);
	}

	public void selectSummaryField(String lableName) {
		clickEle(driver, openDropDownSumField);
		customWait(2);
		WebElement ele = getEleByStringFormat(driver, summaryFieldsDropDown, lableName);
		clickEle(driver, ele);
	}

	/**
	 * This method to verify the error icon is displayed in the field label
	 * 
	 * @param labelName: The fields name
	 */
	public boolean isErrorIconDisplayed(String labelName) {
		String xpath = "//div[@class = 'field-label']//div[contains(.,'%s')]/../i";
		WebElement ele1 = getEleByStringFormat(driver, xpath, labelName);
		return isElementPresent(driver, ele1);
	}

	/**
	 * @param paymentFieldsLable : The payment fields label must be 'PaymentTest'.
	 * @param exceptFieldLables  : Must Name Fields : Static Image → StaticImage;
	 *                           Photo → Photo; Signature → Signature; Drawing →
	 *                           Drawing; CheckBox → CheckBox.
	 * @return List of String contains all fields before Payment Fields and screen.
	 */
	public List<String> allFieldsBeforePayment(String paymentFieldsLable, String UnexceptFieldLables) {
		List<String> Screens = new ArrayList<>();
		List<String> fields = new ArrayList<>();

		outer: for (WebElement screenN : rightSidebarScreens) {
			Screens.add(screenN.getText());
			clickEle(driver, screenN);
			for (WebElement allFields : dragFields) {
				if (!Arrays.asList(UnexceptFieldLables.split(";")).contains(allFields.getText())) {
					if (allFields.getText().trim().equalsIgnoreCase("PaymentTest"))
						break outer;
					fields.add(allFields.getText().trim());
				}
			}
		}
		return fields;
	}

	public List<String> getDataToInCludePaymentDropdowmOpts() {
		List<String> actualOptions = new ArrayList<>();
		waitForVisbility(driver, dataPaymentlabelDropdown, 10).click();
		for (WebElement opts : dataPaymentlabelDropdownOpts) {
			actualOptions.add(opts.getText());
		}
		return actualOptions;
	}

	/**
	 * This method will store all the fields which before payment field into the
	 * List, and store all the fields which are inside 'Data to include with
	 * Payment' dropDown.
	 * 
	 * @param paymentFieldsLable  : the label name of the payment fields in yml
	 *                            file;
	 * @param UnexceptFieldLables : the fields which Un-except in the dropDown;
	 * @return true if all fields as expect in the dropDown.
	 * @throws InterruptedException
	 */
	public boolean verifyDataIncludePaymentField(String paymentFieldsLable, String screenName,
			String UnexceptFieldLables) throws InterruptedException {
		List<String> exceptOps = allFieldsBeforePayment(paymentFieldsLable, UnexceptFieldLables);
		clickScreen(screenName);
		clickLabel(paymentFieldsLable);
		List<String> actualOps = getDataToInCludePaymentDropdowmOpts();
		Assert.assertEquals(exceptOps, actualOps);
		return exceptOps.equals(actualOps);

	}

	/**
	 * This method will drag the Payment field and drop to the target field to swich
	 * the two fields
	 * 
	 * @param paymentLabel
	 * @param targetField
	 */
	public void switchTwoFields(String paymentLabel, String targetField) {
		WebElement payment = getEleByStringFormat(driver, labelNameXpath, paymentLabel);
		WebElement target = getEleByStringFormat(driver, labelNameXpath, targetField);
		dragAndDropElement(driver, payment, target);
	}

	/**
	 * This method will selected one random option from "Data to include with
	 * payment" dropdwon.
	 */
	public boolean selectDataIncPayment() {
		List<String> dataInPayments = getDataToInCludePaymentDropdowmOpts();
		int randomOpts = UtilityFunctions.randomNumber(dataInPayments.size()) - 1;
		String option = dataPaymentlabelDropdownOpts.get(randomOpts).getText().trim();
		clickEle(driver, dataPaymentlabelDropdownOpts.get(randomOpts));
		String expect = dataPaymentDropDwonText.getText().trim();

		return option.equals(expect);
	}

	public void switchTwoScreens(String screen1Label, String screen2Label) {
		String xpath = "//div[@id = 'right-sidebar-screens-container']//p[. = '%s']/..";
		WebElement element = getEleByStringFormat(driver, xpath, screen1Label);
		WebElement eleTo = getEleByStringFormat(driver, xpath, screen2Label);
		dragAndDropElement(driver, element, eleTo);
	}

	/**
	 * This method will check status imgs on app builder screen, check all imgs
	 * displayed on screen status code are 200, if has one or more than one false,
	 * will return false.
	 * 
	 * @return true if all imgs status code 200. false if one or one more invalidate
	 *         img.
	 */
	public boolean validateStaticImgDisplayed() {
		List<Boolean> checkAllimg = new ArrayList<>();
		for (WebElement webElement : staticImgs) {
			checkAllimg.add(verifyimageActive(webElement));
		}
		return !(checkAllimg.remove(false));

	}

	public boolean validateImgDisplayed() {
		List<Boolean> checkAllimg = new ArrayList<>();
		List<WebElement> imgesList = driver.findElements(By.tagName("img"));
		for (WebElement webElement : imgesList) {
			checkAllimg.add(verifyimageActive(webElement));
		}
		return !(checkAllimg.remove(false));
	}

	public String seleRandomImgUpload(String... path) {
		int randomNum = UtilityFunctions.randomNumber(0, path.length - 1);
		return path[randomNum].trim();
	}

	public List<WebElement> getStaticImgs() {
		List<WebElement> staticImage = new ArrayList<>();
		for (WebElement getStaticImg : screenLableNames) {
			moveToElement(driver, getStaticImg);
			if (getStaticImg.getText().trim().equals("New Static Image")) {
				staticImage.add(getStaticImg);
			}
		}
		return staticImage;
	}

	public void selecRandomtExistingRefImage() throws InterruptedException {
		List<WebElement> imgs = allImgs;
		int randomNum = UtilityFunctions.randomNumber(0, imgs.size() - 1);
		waitForVisbility(driver, imgs.get(randomNum), 10);
		imgs.get(randomNum).click();
		waitForVisbility(driver, selectButton, 10);
		clickSelectButton();
	}

	public void clickChangeImage() {
		waitForVisbility(driver, changeImageLink, 10);
		clickOnHiddenElement(changeImageLink, driver);
	}

	public void editReferenceData(String editFieldLable, String referenceName, String filePath)
			throws InterruptedException {
		clickLabelField(editFieldLable);
		waitForVisbility(driver, editReferenceDataBtn, 10);
		clickOnHiddenElement(editReferenceDataBtn, driver);
		enterReferenceDataName(referenceName);
		uploadFile(filePath);
		clickSaveReferenceDataButton();
	}

	/**
	 * This method will verify reference data saved successfully, When its saved
	 * successfully, the label : reference column, refernce screen and reference
	 * field will displayed.
	 * 
	 * @return
	 */
	public boolean verifyRefDataSaved() {
		waitForVisbility(driver, labelRefColumn, 10);
		waitForVisbility(driver, labelRefScreen, 10);
		waitForVisbility(driver, labelRefField, 10);

		return isElementPresent(driver, labelRefColumn) && isElementPresent(driver, labelRefScreen)
				&& isElementPresent(driver, labelRefField);
	}

	/**
	 * This method will loop the view reference data table and stored into
	 * Map<String,List<String>> collection. Action performed : click view -> stored
	 * data -> close table
	 * 
	 * @return
	 */
	public Map<String, List<String>> viewReferenceDataTable() {
		waitForVisbility(driver, viewRefDataBtn, 10);
		clickOnHiddenElement(viewRefDataBtn, driver);
		By byThead = By.xpath("//div[@class='modal-body']//table/thead/tr/th");
		By byTr = By.xpath("//div[@class='modal-body']//table/tbody/tr");
		waitForVisbility(driver, byThead, 10);
		List<WebElement> thead = driver.findElements(byThead);
		waitForVisbility(driver, byTr, 10);
		List<WebElement> tbody = driver.findElements(byTr);

		Map<String, List<String>> table = new HashMap<>();

		for (int i = 0; i < thead.size(); i++) {
			String columnTitle = thead.get(i).getText(); // get column title
			List<String> rowValue = new ArrayList<>();
			for (int j = 0; j < tbody.size(); j++) {
				String xpath = "//div[@class='modal-body']//table/tbody/tr[" + (j + 1) + "]/td[" + (i + 1) + "]";
				rowValue.add(driver.findElement(By.xpath(xpath)).getText()); // get single value
			}
			table.put(columnTitle, rowValue);

		}

//		for (Entry<String, List<String>> webElement : table.entrySet()) {
//			System.out.println("Key: " + webElement.getKey() + ": > " + webElement.getValue());
//		}
		clickEle(driver, xTableIcon);
		return table;

	}

	public boolean compareCopyFielRefDataSame(String fieldLable) {
		List<WebElement> copyFields = driver.findElements(By.xpath(String.format(fieldXpath, fieldLable)));
		copyFields.get(0).click();
		Map<String, List<String>> viewRefData1 = viewReferenceDataTable();
		copyFields.get(1).click();
		Map<String, List<String>> viewRefData2 = viewReferenceDataTable();
		boolean compare = viewRefData1.equals(viewRefData2);
		Assert.assertTrue(compare);
		return compare;
	}

	public boolean compareCopyScreenRefData(String fieldLable, String... screens) {
		getEleByStringFormat(driver, screenNameXpath, screens[0]).click();
		List<WebElement> copyFields = driver.findElements(By.xpath(String.format(fieldXpath, fieldLable)));
		copyFields.get(0).click();
		Map<String, List<String>> firstScreenData1 = viewReferenceDataTable();

		// second Screen
		getEleByStringFormat(driver, screenNameXpath, screens[1]).click();
		List<WebElement> secondScreenfield = driver.findElements(By.xpath(String.format(fieldXpath, fieldLable)));
		secondScreenfield.get(0).click();
		Map<String, List<String>> secondScreenData = viewReferenceDataTable();

		boolean compare = firstScreenData1.equals(secondScreenData);

		return compare;

	}

	/**
	 * Compare two table is same
	 * 
	 * @param beforeEditDdata
	 * @param afterEditDdata
	 * @return
	 */
	public boolean verifTwoReferenceData(Map<String, List<String>> dataTable1, Map<String, List<String>> dataTable2) {
		return dataTable1.equals(dataTable2);
	}

	public List<String> verifyDefaultValueSelRandomValue() {
		waitForClickablility(driver, defaultValueBox, 10);
		clickEle(driver, defaultValueBox);
		List<String> elems = new ArrayList<>();
		for (WebElement webElement : defaultValuetext) {
			elems.add(webElement.getText());
		}
		elems.remove(0);
		clickEle(driver, defaultValuetext.get(UtilityFunctions.getRandomNumberInRange(1, elems.size() - 1)));
		return elems;
	}

	public String[] verifyDefaultValueSelRandomValueMulit() {
		waitForClickablility(driver, defaultValueBox, 10);
		clickEle(driver, defaultValueBox);
		String[] actual = new String[defaultValuetext.size()];
		for (int i = 0; i < defaultValuetext.size(); i++) {
			actual[i] = defaultValuetext.get(i).getText();
		}
		clickEle(driver, defaultValuetext.get(UtilityFunctions.getRandomNumberInRange(0, actual.length - 1)));
		return actual;
	}

	public boolean typeOwnChoicesVerifyInDefaultValue(String[] choices) {
		waitForVisbility(driver, dropDownTypeOwnBtn, 10);
		clickEle(driver, dropDownTypeOwnBtn);
		typeChoices(choices);
		List<String> typed = Arrays.asList(choices);
		return typed.equals(verifyDefaultValueSelRandomValue());
	}

	public void typeChoices(String[] choices) {
		for (int i = 0; i < choices.length; i++) {
			inputVlaue.get(i).click();
			if (i == choices.length - 1) {
				inputVlaue.get(i).sendKeys(choices[i]);
				break;
			}
			inputVlaue.get(i).sendKeys(choices[i] + Keys.RETURN);
		}
	}

	public boolean commonListDropDown() {
		clickEle(driver, dropDownCommonList);
		clickEle(driver, dropDownCommonBackBtn);
		clickEle(driver, dropDownCommonList);
		clickEle(driver, commonListTitles.get(UtilityFunctions.getRandomNumberInRange(0, commonListTitles.size() - 1)));
		String[] values = new String[commonListValues.size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = commonListValues.get(i).getAttribute("value");
		}
		List<String> newValues = Arrays.asList(values);
		return newValues.equals(verifyDefaultValueSelRandomValue());
	}

	public boolean multiChoiceValidation(String[] choice) {
		return Arrays.asList(choice).equals(verifyDefaultValueSelRandomValue());
	}

	public boolean validateDefaultChoiceWithDefaultValue() {
		String[] arr = new String[inputVlaue.size()];
		for (int i = 0; i < inputVlaue.size(); i++) {
			arr[i] = inputVlaue.get(i).getAttribute("value");
		}
		return Arrays.equals(arr, verifyDefaultValueSelRandomValueMulit());
	}

	public void existingFieldDropDown(String dropDownName) {
		clickEle(driver, existingFieldDropDownBox);
		WebElement ele = getEleByStringFormat(driver, dropDownXpath, dropDownName);
		clickOnHiddenElement(ele, driver);
	}

	public boolean isLoopMovedSuccessfulluy(String level) {
		String eleXpath = "//div[@id = 'right-sidebar-screens-container']//sheet-outline[@nesting-level='%s']";
		List<WebElement> elements = getEleByStringFormats(driver, eleXpath, level);
		return elements.size() >= 2;

	}

	public boolean verifytCalculationOperation(String operatorDisplayed) {
		StringBuilder actual = new StringBuilder();
		for (WebElement ele : calOperations) {
			actual.append(getElementText(driver, ele).replaceAll(" ", "").trim());
		}
		return actual.toString().equals(operatorDisplayed);
	}
}
