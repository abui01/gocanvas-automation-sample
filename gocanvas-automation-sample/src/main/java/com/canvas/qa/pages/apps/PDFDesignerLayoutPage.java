package com.canvas.qa.pages.apps;

import java.awt.AWTException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.test.BETAQRT.PDFDesignerTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author shalini.pathak
 *
 */
public class PDFDesignerLayoutPage extends BasePage

{
	@FindBy(xpath = "//ul[@class = 'sectionActions']/li[contains(.,'Add column')]")
	WebElement addColumn;

	String addColumnInSectionXpath = "//div[@ui-sortable-items = 'ui-sortable-section']/div[%d]//ul[@class = 'sectionActions']/li[contains(.,'Add column')]";

	@FindBy(xpath = "//div[@class = 'addSectionBtn']")
	WebElement clickToAddSection;

	@FindBy(xpath = "//a[contains(.,'Close')]")
	WebElement closeButton;

	@FindBy(xpath = "//div[contains(.,' Back to GoCanvas')]")
	WebElement closePDFButton;

	String columnWhereToDrop = "//div[@ui-sortable-items = 'ui-sortable-section']/div[%d]/div/div//div[@column = 'section' and @index = '%d']/div";

	String columnXpath = "//div[@ui-sortable-items = 'ui-sortable-section']/div[%d]/div/div//div[@column = 'section']";

	@FindBy(xpath = "//a[contains(@class,'btn btn-sm cvs-def-btn disabled')]/span[1]")
	WebElement disabledButton;

	@FindBy(xpath = "//a[contains(.,'Download PDF')]")
	WebElement downloadPDFButton;

	WebDriver driver;

	String fieldNameXpath = "//div[contains(.,'%s')]";

	String fieldToDrag = "//div[@class = 'field-box ng-scope ui-draggable ui-draggable-handle' and @title = '%s']";
	String fieldValueXpath = "//div[@class = 'pages']/div[@class = 'page' and @page-index = '%d']//div[@ui-sortable-items = 'ui-sortable-section']/div[%d]/div/div//div[@column = 'section' and @index = '%d']/div/div[%d]/div//div[@class = 'field-label ng-binding' or @class ='field-label checkbox-label ng-binding'] ";

	String fieldXpath = "//div[@class = 'pages']/div[@class = 'page' and @page-index = '%d']//div[@ui-sortable-items = 'ui-sortable-section']/div[%d]/div/div//div[@column = 'section' and @index = '%d']/div/div[%d]/div//div[@ng-include = 'getTemplateUrl()']/div[1] ";

	@FindBy(xpath = "//span[@class = 'outline-screen-description ng-binding ng-scope']")
	WebElement outlineScreen;

	@FindBy(id = "pdfFile")
	WebElement pdfFile;

	@FindBy(xpath = "//div[contains(@class,'modal-header ng-scope')]/h3")
	WebElement popUpMessage;

	@FindBy(xpath = "//a[contains(.,'Preview')]")
	WebElement previewButton;

	@FindBy(xpath = "//span[contains(.,'Save')]")
	WebElement saveButton;

	@FindBy(xpath = "//div[contains(@class,'addSectionBtnContainer')]")
	WebElement sectionContainer;

	@FindBy(xpath = "//div[contains(@class,'ui-sortable-section no-drag-section ng-scope')]")
	List<WebElement> sectionsList;

	@FindBy(xpath = "//button[contains(.,'Yes')]")
	WebElement yesButton;
	
	@FindBy(xpath = "//a[contains(@title,'App Builder')]")
	WebElement appLink;

	@FindBy(xpath = "//span[contains(.,'Add screen')]")
	WebElement addScreenButton;
	
	@FindBy(xpath="//div[@ui-sortable-items = 'ui-sortable-section']/div/div/div//div[@column = 'section']/div[@ui-sortable]")
	List<WebElement> allColumns;
	
	@FindBy(xpath = "//div[contains(@title,'Border All')]")
	WebElement borderAll;
	@FindBy(xpath="//div[@class='panel-collapse collapse in']//div[@title='Set background color']//input[@type]")
	WebElement columnSettingBGColor;
	
	@FindBy(xpath="//div[@class='border-section']//div[@class='jslider-pointer']")
	WebElement sliderColumnSetting;
	
	@FindBy(xpath="//div[@class='border-section']//div[@title='Set border style']/div/div/span")
	WebElement bodyStyleColumnSetting;
	
	@FindBy(xpath="//div[@class='border-section']//div[@title='Set border style']//div[contains(@class,'selected-border')]")
	WebElement selectedBodyStyleColumnSetting;

	@FindBy(xpath="//div[@class='border-section']//div[@title='Set border style']//div[@ng-attr-id]")
	List<WebElement> bodyStyleDropDownOps;
	
	@FindBy(xpath="//div[@class='border-section']//div[@title='Set border color']//input[@type]")
	WebElement borderStyleColor;
	
	@FindBy(xpath = "//span[@title='Field Settings'][contains(.,'Column Settings')]")
	WebElement columnSettingText;
	
	@FindBy(xpath="//div[@class='panel-collapse collapse in']//div[@class='global-prop-checkbox']/input")
	WebElement paddingCheckBox;
	
	@FindBy(xpath="//div[@class='panel-collapse collapse in']//div[@class='property-fields']//input")
	List<WebElement> enterPadding;
	
	@FindBy(xpath="//span[@title='Field Settings']")
	WebElement fieldSettingTxt;
	
	@FindBy(xpath = "//div[@class='panel-collapse collapse in']//div[.='General']/..//input[@type]")
	WebElement fieldSetBGC;
	
	@FindBy(xpath="//div[@class='text-style-set ng-isolate-scope']/i")
	List<WebElement> allTextStyle;
	
	String alignmentXpath= "//div[.='General']/..//i[contains(@title,'%s')]";
	String loopSetAlignmentXpath="//div[.='Table Headers']/..//i[contains(@title,'%s')] ";
	String tableRowsAlignment = "(//i[contains(@title,'%s')])[4]";
	
	@FindBy(xpath="//div[@class='global-prop-checkbox']/..//div[@class='property-fields']//input[2]")
	WebElement textColor;
	
	@FindBy(xpath = "//div[@class='global-prop-checkbox']/input")
	WebElement useGlobalSetting;
	
	@FindBy(xpath="//div[@class='global-prop-checkbox']/..//div[@class='field']/div[@class='border-set ng-isolate-scope']/div")
	List<WebElement> borderOptiions;
	
	@FindBy(xpath="//div[@class='global-prop-checkbox']/..//div[@class='jslider-pointer']")
	List<WebElement> twoSliderPointers;

	@FindBy(xpath="//table[@class='list-table']")
	WebElement firstLoopTable;
	
	@FindBy(xpath = "//span[contains(.,'Loop Settings')]")
	WebElement loopSettingTitle;
	
	@FindBy(xpath="//div[@class='properties-content']/div[1]//div[@class='prop-box']")
	List<WebElement> loopSetHeaderFields;
	
	@FindBy(xpath="//div[@class='properties-content']/div[1]//div[@class='color-box']/input[@type]")
	List<WebElement> loopSetHeaderColorsInput;
	
	@FindBy(xpath="//div[@class='properties-content']/div[1]//div[@class='jslider-pointer']")
	List<WebElement> loopSetHeadersliders;
	
	@FindBy(xpath="//div[@class='properties-content']/div[1]//div[contains(@class,'bdr')]")
	List<WebElement> loopSetHeaderAllBorders;
	
	@FindBy(xpath="//div[@class='properties-content']/div[2]//div[@class='color-box']/input[@type]")
	List<WebElement> loopSetRowColorsInput;
	
	@FindBy(xpath="//div[@class='properties-content']/div[2]//div[@class='jslider-pointer']")
	List<WebElement> loopSetRowsliders;
	
	@FindBy(xpath="//div[@class='properties-content']/div[2]//div[contains(@class,'bdr')]")
	List<WebElement> loopSetRowAllBorders;
	
	@FindBy(xpath = "//div[.=' Back to GoCanvas']")
	WebElement backToGocanvas;
	
	
	
	public PDFDesignerLayoutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		javaScriptErrorChecker(driver);
	}

	public PDFDesignerLayoutPage clickAddColumn() throws InterruptedException {
		clickOnHiddenElement(addColumn, driver);
		return this;
	}

	public PDFDesignerLayoutPage clickAddColumnSection(int index) throws InterruptedException {
		WebElement element = driver.findElement(By.xpath(String.format(addColumnInSectionXpath, index)));
		clickOnHiddenElement(element, driver);
		return this;
	}

	public PDFDesignerLayoutPage clickCloseButton() throws InterruptedException {
		clickOnHiddenElement(closeButton, driver);

		return this;
	}

	public CreateAppPage clickCloseButtonafterSave() throws InterruptedException {
		clickOnHiddenElement(closeButton, driver);
		return new CreateAppPage(driver);
	}

	public PDFDesignerLayoutPage clickClosePDFButton() throws InterruptedException {
		clickOnHiddenElement(closePDFButton, driver);
		return this;
	}

	public PDFDesignerLayoutPage clickingOnClickToAddSection() throws InterruptedException {
		clickOnHiddenElement(clickToAddSection, driver);
		// clickToAddSection.click();
		return this;
	}

	@FindBy(xpath = "//button[@class='btn ng-binding cvs-prim-btn']")
	WebElement saveAndPreviewBtn;
	@FindBy(xpath = "//h3[.='Save changes?']")
	WebElement saveChangePopUp;
	public PDFDesignerLayoutPage clickPreviewButton() throws InterruptedException {
		clickOnHiddenElement(previewButton, driver);
		if(isElementPresent(driver, saveChangePopUp)) {
			clickEle(driver, saveAndPreviewBtn);
		}
		waitForPageToLoad(driver,20);
		return this;
	}

	public PDFDesignerLayoutPage clickSaveButton() throws InterruptedException {
		clickOnHiddenElement(saveButton, driver);
		customWait(5);// Implict & Explicit wait not working here so
							// Thread.sleep is required
		return this;
	}

	public PDFDesignerLayoutPage clickSectionContainer() throws InterruptedException {
		sectionContainer.click();
		return this;
	}

	public CreateAppPage clickYesButton() throws InterruptedException {
		clickOnHiddenElement(yesButton, driver);
		return new CreateAppPage(driver);
	}

	public PDFDesignerLayoutPage dragDropField(String fieldName, int sectionIndex, int columnIndex)
			throws AWTException, InterruptedException {
		WebElement source = driver.findElement(By.xpath(String.format(fieldToDrag, fieldName)));
		WebElement target = driver.findElement(By.xpath(String.format(columnWhereToDrop, sectionIndex, columnIndex)));
		Actions act = new Actions(driver);
		act.clickAndHold(source).build().perform();
		Thread.sleep(1000);
		act.moveToElement(target).build().perform();
		Thread.sleep(1000);
		act.release().build().perform();
		return this;
	}

	public String getColumnCountInSection(int sectionIndex) {
		List<WebElement> columnsList = driver.findElements(By.xpath(String.format(columnXpath, sectionIndex)));
		return Integer.toString(columnsList.size());
	}

	public String getColumnSectionValue(int pageIndex, int sectionIndex, int columnIndex, int fieldIndex) {
		return driver
				.findElement(By.xpath(String.format(fieldValueXpath, pageIndex, sectionIndex, columnIndex, fieldIndex)))
				.getText();
	}

	public String getDisabledButtonText() {
		return disabledButton.getText();
	}

	public String getFieldValue(int pageIndex, int sectionIndex, int columnIndex, int fieldIndex) {
		return driver.findElement(By.xpath(String.format(fieldXpath, pageIndex, sectionIndex, columnIndex, fieldIndex)))
				.getText();
	}

	public String getOutlineScreenName() {
		waitForVisbility(driver, outlineScreen, 60);
		return outlineScreen.getText();
	}

	public String getPopUpMessageContent() {
		return popUpMessage.getText();
	}

	public String getSectionCount() {
		return Integer.toString(sectionsList.size());
	}

	public boolean isAddSectionDisplayed() {
		return clickToAddSection.isDisplayed();
	}

	public boolean isCloseButtonDisplayed() throws InterruptedException {
		return closeButton.isDisplayed();
	}

	public boolean isClosePDFButtonDisplayed() throws InterruptedException {
		return closePDFButton.isDisplayed();
	}

	public boolean isDownloadPDFButtonDisplayed() throws InterruptedException {
		return downloadPDFButton.isDisplayed();
	}

	public boolean isFieldDisplayed(String labelName) throws InterruptedException {
		return driver.findElement(By.xpath(String.format(fieldNameXpath, labelName))).isDisplayed();
	}

	public boolean isPDFFileDisplayed() throws InterruptedException {
		waitForVisbility(driver, pdfFile, 10);
		return pdfFile.isDisplayed();
	}

	public boolean isPreviewButtonDisplayed() throws InterruptedException {
		return previewButton.isDisplayed();
	}

	public boolean isSaveButtonDisplayed() throws InterruptedException {
		return saveButton.isDisplayed();
	}

	public PDFDesignerLayoutPage mouseHoverOverAddSection() {
		WebElement hoverElement = driver.findElement(By.xpath("//div[@column = 'section' and @index = '0']"));
		Actions builder = new Actions(driver);
		builder.moveToElement(hoverElement).perform();
		addColumn.click();
		return this;
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

	public PDFDesignerLayoutPage verifyFieldsInAppOutline(String step, ITestContext testContext, String message,
			String[] fieldList) throws InterruptedException {
		for (int j = 0; j < fieldList.length; j++) {
			boolean status = isFieldDisplayed(fieldList[j]);
			reportLog(status, testContext.getName(), message, step + j, fieldList[j] + " is displayed.");
			org.testng.Assert.assertTrue(status);
		}

		return this;
	}

	public AppBuilderPage clickAppLink() throws InterruptedException {
		clickOnHiddenElement(appLink, driver);
		driver.switchTo().alert().accept();
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(addScreenButton));
		return new AppBuilderPage(driver);
	}

	public boolean verifyfieldSettingsDisplayed() {
		return isElementPresent(driver, fieldSettingTxt);
	}

	public void clickColumnByIndex(String columnIndex) {
		int index = Integer.parseInt(columnIndex.trim());
		clickEle(driver, allColumns.get(index));
		customWait(2); //added wait due to flaky test case
	}

	/**
	 * 
	 * @param backGroundColor
	 * @param textStyle
	 * @param alignment
	 */
	public void fieldSettingGeneral(String backGroundColor, String textStyle, String alignment) {
		enterTextField(driver, fieldSetBGC, backGroundColor);
		selectOptionsOneOfFour(allTextStyle, textStyle);
		selectAlignment(alignment);
	}

	public void selectAlignment(String alignment) {
		WebElement ele = getEleByStringFormat(driver, alignmentXpath, alignment);
		clickEle(driver, ele);
	}
	
	public void selectLoopSetAlignment(String alignment) {
		WebElement ele = getEleByStringFormat(driver, loopSetAlignmentXpath, alignment);
		clickEle(driver, ele);
	}

	/**
	 * This method input text Style. four Options: all, 1, 2, 3.
	 * 
	 * @param textStyle
	 */
	public void selectOptionsOneOfFour(List<WebElement> element, String options) {
		switch (options.trim().toLowerCase()) {
		case "all":
			for (WebElement ele : element) {
				waitForVisbility(driver, ele, 10);
				clickOnHiddenElement(ele, driver);
			}
			break;
		case "1":
			clickEle(driver, element.get(0));
			break;
		case "2":
			clickEle(driver, element.get(1));
			break;
		case "3":
			clickEle(driver, element.get(2));
			break;
		default:
			System.out.println("Wrong choice !!!");
		}
	}

	public void selectBorders(String selectBorder) {
		selectOptionsOneOfFour(borderOptiions, selectBorder);

	}

	public void useGlobalSettingsInFieldSetting(String enterTextColor, String borders) {
		enterTextField(driver, textColor, enterTextColor);
		sliderTextSize();
		sliderVerticalSpacting();
		selectBorders(borders);

	}

	public void clickGlobalSetting() {
		clickEle(driver, useGlobalSetting);
	}

	public void sliderTextSize() {
		sliderPointAction(driver, twoSliderPointers.get(0), 90, 385);
	}

	public void sliderVerticalSpacting() {
		sliderPointAction(driver, twoSliderPointers.get(1), 70, 420);
	}

	/**
	 * This method will enter column setting background color, border all, slider
	 * border to %80, selet the body style, enter border style colr by passing data.
	 * 
	 * @param enterTextColor
	 * @param bodyStyle
	 * @param borderColor
	 */
	public void columnSettings(String enterTextColor, String bodyStyle, String borderColor) {
		waitForVisbility(driver, columnSettingBGColor, 10);
		enterTextField(driver, columnSettingBGColor, enterTextColor);
		clickEle(driver, borderAll);
		clicksliderBorderStyle();
		dropDownBorder(bodyStyle);
		enterTextField(driver, borderStyleColor, borderColor);
	}

	/**
	 * Drop Down options: Dotted, Dashed, Solid, Double. Default Options: solid
	 * 
	 * @param bodyStyle
	 */
	public void dropDownBorder(String bodyStyle) {
		waitForVisbility(driver, bodyStyleColumnSetting, 10);
		clickEle(driver, bodyStyleColumnSetting);
		try {
			for (WebElement elemnts : bodyStyleDropDownOps) {
				if (elemnts.getText().trim().equals(bodyStyle)) {
					clickEle(driver, elemnts);
				}
			}
		} catch (StaleElementReferenceException e) {
			for (WebElement elemnts : bodyStyleDropDownOps) {
				if (elemnts.getText().trim().equals(bodyStyle)) {
					clickEle(driver, elemnts);
				}
			}
		}

	}

	public void clicksliderBorderStyle() {
		sliderPointAction(driver, sliderColumnSetting, 80, 311);
	}

	/**
	 * This method will enter padding value by: top,left,right,button
	 * 
	 * @param padding
	 */
	public void paddingGlobalSetting(String[] paddingValue) {
		clickEle(driver, paddingCheckBox);
		for (int i = 0; i < enterPadding.size(); i++) {
			enterTextField(driver, enterPadding.get(i), paddingValue[i]);
		}

	}

	public boolean verifyColumnSettingDisplayed() {
		return isElementPresent(driver, columnSettingText);
	}

	/**
	 * This method will verify the Field Setting features: Back ground color, body
	 * style all select, Alignment and text color
	 * 
	 * @param BGColor
	 * @param alignment
	 * @param textColorText
	 * @return
	 */
	public boolean verifyFieldSettings(String BGColor, String alignment, String textColorText) {

		boolean BGColorDisplayed = fieldSetBGC.getAttribute("value").trim().equals(BGColor.trim());
		if(!BGColorDisplayed) System.out.println("Debug Error: BGColorDisplayed "+BGColorDisplayed);   // just for debug, Once catched error will remove it
		boolean bodyStyleDisplayed = verifyBodyStyleAllSelect();
		if(!bodyStyleDisplayed) System.out.println("Debug Error: bodyStyleDisplayed "+bodyStyleDisplayed);// just for debug, Once catched error will remove it
		boolean alignmentDisplayed = verifyAlignmentSelect(alignment);
		if(!alignmentDisplayed) System.out.println("Debug Error: alignmentDisplayed "+alignmentDisplayed);// just for debug, Once catched error will remove it
		boolean textColorDisplayed = textColor.getAttribute("value").trim().equals(textColorText);
		if(!textColorDisplayed) System.out.println("Debug Error: textColorDisplayed "+textColorDisplayed);// just for debug, Once catched error will remove it
		return BGColorDisplayed && bodyStyleDisplayed && alignmentDisplayed && textColorDisplayed;
	}

	public boolean verifyBodyStyleAllSelect() {
		int i = 0;
		customWait(2);
		for (WebElement ele : allTextStyle) {
			// waitForVisbility(driver, ele, 10);
			if (ele.getAttribute("title").contains("Remove")) {
				i++;
			}
		}
		return i == allTextStyle.size();
	}

	public boolean verifyAlignmentSelect(String alignment) {
		WebElement ele = getEleByStringFormat(driver, alignmentXpath, alignment);
		return ele.getAttribute("class").contains("active-blue");
	}

	public boolean verifyColumnSettings(String BGColor, String bodyStyle, String borderColor, String[] paddingPXs) {
		boolean BGColorDisplayed = columnSettingBGColor.getAttribute("value").trim().equals(BGColor);
		boolean borderAllSelected = borderAll.getAttribute("class").contains("bdr-active");
		boolean bodyStyleSelected = selectedBodyStyleColumnSetting.getText().equals(bodyStyle);
		boolean borderStyleColorValue = borderStyleColor.getAttribute("value").trim().equals(borderColor);
		boolean paddingValue = verifyPaddingSaved(paddingPXs);

		return BGColorDisplayed && borderAllSelected && bodyStyleSelected && borderStyleColorValue && paddingValue;
	}

	public boolean verifyPaddingSaved(String[] value) {
		int count = 0;
		for (int i = 0; i < enterPadding.size(); i++) {
			if (enterPadding.get(i).getAttribute("value").trim().equals(value[i].trim())) {
				count++;
			}
		}

		return count == enterPadding.size();
	}

	public void clickLoopTable() {
		try {
			
			waitForVisbility(driver, firstLoopTable, 60);
			clickEle(driver, firstLoopTable);
		} catch (Exception e) {
			try {
				driver.get(driver.getCurrentUrl());
				clickEle(driver, firstLoopTable);
			} catch (Exception e2) {
				driver.get(driver.getCurrentUrl());
				clickEle(driver, firstLoopTable);
			}
		
		}
		
	}

	public boolean verifyLoopSettingDisplayed(String text) {
		String actTitle = loopSettingTitle.getText();
		return actTitle.trim().equals(text.trim());
	}
	
	/**
	 * This method enter the datas in loop Setting table headers. Include: Enter
	 * table header back ground color, Text Color, slider text size, slider Vertical
	 * Spacing, Select Alignmnet.
	 * 
	 * @param backGroundColor
	 * @param textColor
	 * @param alignment
	 */
	public void enterLoopSettingsTableHeaders(String backGroundColor, String textColor, String alignment) {
		enterLoopSetTabHeaderBGC(backGroundColor);
		enterLoopSetTabHeaderTxtColor(textColor);
		loopSetTabHeaderSliderTxtSize();
		loopSetTabHeaderSliderVerSpace();
		selectLoopSetAlignment(alignment);

	}

	public boolean verifyLoopSetHeaderBordersDefaultValue() {
		List<WebElement> allBorders = loopSetHeaderAllBorders;
		int count = 0;
		for (int i = 0; i < allBorders.size(); i++) {
			if (allBorders.get(i).getAttribute("class").contains("bdr-active")) {
				count++;
			}
		}
		return count == allBorders.size();
	}

	/**
	 * Enter back ground color in loop setting table header
	 * 
	 * @param backGroundColor
	 */
	public void enterLoopSetTabHeaderBGC(String backGroundColor) {
		enterTextField(driver, loopSetHeaderColorsInput.get(0), backGroundColor);
	}

	/**
	 * Enter text color in loop setting table header
	 * 
	 * @param textColor
	 */
	public void enterLoopSetTabHeaderTxtColor(String textColor) {
		enterTextField(driver, loopSetHeaderColorsInput.get(1), textColor);
	}

	/**
	 * This method sliding the text size on Loop Setting column in Table Headers
	 */
	public void loopSetTabHeaderSliderTxtSize() {
		sliderPointAction(driver, loopSetHeadersliders.get(0), 80, 280);
	}

	/**
	 * This method sliding the Vertical Spacing on Loop Setting column in Table
	 * Headers
	 */
	public void loopSetTabHeaderSliderVerSpace() {
		sliderPointAction(driver, loopSetHeadersliders.get(1), 80, 315);
	}

	/**
	 * This method enter by test data in loop setting Table Rows. Will performing :
	 * Enter Background Color, Text Color, slider Text size, Vertical spacing,
	 * Alignment
	 * 
	 * @param backGroundColor
	 * @param textColor
	 * @param alignment
	 */
	public void enterloopSettingsTableRows(String backGroundColor, String textColor, String alignment) {
		enterLoopSetTableRowsBGC(backGroundColor);
		enterLoopSetTableRowsTxtColor(textColor);
		loopSetTabRowSliderTxtSize();
		loopSetTabRowSliderVerSpace();
		loopSetTabRowAlign(alignment);

	}

	public void loopSetTabRowSliderTxtSize() {
		sliderPointAction(driver, loopSetRowsliders.get(0), 78, 289);
	}

	public void loopSetTabRowSliderVerSpace() {
		sliderPointAction(driver, loopSetRowsliders.get(1), 82, 310);
	}

	public void loopSetTabRowAlign(String alignment) {
		WebElement ele = getEleByStringFormat(driver, tableRowsAlignment, alignment);
		clickEle(driver, ele);
	}

	/**
	 * Enter back ground color in loop setting table Rows
	 * 
	 * @param backGroundColor
	 */
	public void enterLoopSetTableRowsBGC(String backGroundColor) {
		enterTextField(driver, loopSetRowColorsInput.get(0), backGroundColor);
	}

	/**
	 * Enter Text color in loop setting table Rows
	 * 
	 * @param backGroundColor
	 */
	public void enterLoopSetTableRowsTxtColor(String textColor) {
		enterTextField(driver, loopSetRowColorsInput.get(1), textColor);
	}

	/**
	 * Verify all data in Loop Setting Table Header saved correct after page
	 * refreshed
	 * 
	 * @param backGroundColor
	 * @param textColor
	 * @param alignment
	 * @return
	 */
	public boolean verifyLoopSettingHeaderDataSaved(String backGroundColor, String textColor, String alignment) {
		boolean verifyBGC = loopSetHeaderColorsInput.get(0).getAttribute("value").trim().equals(backGroundColor.trim());
		boolean verifyTC = loopSetHeaderColorsInput.get(1).getAttribute("value").trim().equals(textColor);

		WebElement ele = getEleByStringFormat(driver, loopSetAlignmentXpath, alignment);
		boolean verifyAlig = ele.getAttribute("class").contains("active-blue");

		return verifyBGC && verifyTC && verifyAlig;
	}

	public boolean verifyLoopSettingRowsDataSaved(String backGroundColor, String textColor, String alignment) {
		boolean verifyBGC = loopSetRowColorsInput.get(0).getAttribute("value").trim().equals(backGroundColor.trim());
		boolean verifyTC = loopSetRowColorsInput.get(1).getAttribute("value").trim().equals(textColor);
		WebElement ele = getEleByStringFormat(driver, tableRowsAlignment, alignment);
		boolean verifyAlig = ele.getAttribute("class").contains("active-blue");

		return verifyBGC && verifyTC && verifyAlig;
	}
	
	public void clickBackToGoCanvas() {
		clickEle(driver, backToGocanvas);
		
	}

	public void refreshDriver(WebDriver driver) {
		try {
			driver.get(driver.getCurrentUrl());
			waitForVisbility(driver, saveButton, 15);
		} catch (Exception e) {
			try {
				driver.get(driver.getCurrentUrl());
				waitForVisbility(driver, saveButton, 15);
			} catch (Exception e2) {
				driver.get(driver.getCurrentUrl());
				waitForVisbility(driver, saveButton, 15);
			}
		}
		
		
	}
	

}
