package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.PublishAppPage;
import com.canvas.qa.pages.apps.AppBuilderPage;
import com.canvas.qa.pages.apps.PDFDesignerLayoutPage;
import com.canvas.qa.pages.apps.PDFDesignerPage;
import com.canvas.qa.pages.apps.ShowPDFOptionsPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @param testContext
 * @throws IOException
 * @throws InterruptedException
 */

public class PDFDesignerTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void createPDFDesignerFromCreateALayout(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);

			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "1.2");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			boolean status = appBuilderPage.isLabelDisplayed("First name");
			reportLog(status, testContext.getName(), "User created first screen with labels", "2.0",
					"First screen created successfully");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));
			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Successfull message",
					"2.1", "Verify Save successful appears: IS NOT AUTOMATED");

			PDFDesignerPage pdfDesignerPage = appBuilderPage.clickPDFButton();

			status = pdfDesignerPage.isTitleDisplayed();
			reportLog(status, testContext.getName(), "User clicked on PDF button", "3.0", "Design your PDF displayed");

			status = pdfDesignerPage.isCreateALayoutDisplayed();
			reportLog(status, testContext.getName(), "User is on Design your PDF", "3.1",
					"Create a layout for me displayed");
			org.testng.Assert.assertTrue(status);

			status = pdfDesignerPage.isStartFromScratchDisplayed();
			reportLog(status, testContext.getName(), "User is on Design your PDF", "3.2",
					"Start from scratch displayed");
			org.testng.Assert.assertTrue(status);

			status = pdfDesignerPage.isStartButtonDisplayed();
			reportLog(status, testContext.getName(), "User is on Design your PDF", "3.3", "Start button displayed");
			org.testng.Assert.assertTrue(status);

			String selectedTemplateText = pdfDesignerPage.getActiveTemplateText();
			status = selectedTemplateText.equals(parameters.get("active_template"));
			reportLog(status, testContext.getName(), "User is on Design your PDF", "3.4",
					"By Default option Create a layout for me is selected");
			org.testng.Assert.assertTrue(status);

			PDFDesignerLayoutPage pdfDesignerLayoutPage = pdfDesignerPage.clickCreateALayout().clickStartButton();

			status = pdfDesignerLayoutPage.getOutlineScreenName().equals(parameters.get("first_screen_name"));
			reportLog(status, testContext.getName(), "User is on PDF Designer screen", "4.0",
					"My First screen in left side in App outline is displayed");
			org.testng.Assert.assertTrue(status);

			status = pdfDesignerLayoutPage.isSaveButtonDisplayed();
			reportLog(status, testContext.getName(), "User is on PDF Designer screen", "4.1.1",
					"Save button is displayed on right side");
			org.testng.Assert.assertTrue(status);

			status = pdfDesignerLayoutPage.isPreviewButtonDisplayed();
			reportLog(status, testContext.getName(), "User is on PDF Designer screen", "4.1.2",
					"Preview button is displayed on right side");
			org.testng.Assert.assertTrue(status);

			status = pdfDesignerLayoutPage.isCloseButtonDisplayed();
			reportLog(status, testContext.getName(), "User is on PDF Designer screen", "4.1.3",
					"Close button is displayed on right side");
			org.testng.Assert.assertTrue(status);

			pdfDesignerLayoutPage.verifyFieldsInAppOutline("4.2.", testContext,
					"Verify fields under My First screen in App outline",
					parameters.get("first_screen_labels").split(";"));

			status = pdfDesignerLayoutPage.getFieldValue(0, 1, 0, 1).equals(parameters.get("first_screen_name"));
			reportLog(status, testContext.getName(), "User is on PDF Designer screen", "5.1",
					"In section: 1 and column: 1 field displayed is: "
							+ pdfDesignerLayoutPage.getFieldValue(0, 1, 0, 1));
			org.testng.Assert.assertTrue(status);

			String[] fieldIndexList = parameters.get("field_index").split(";");
			String[] sectionIndexList = parameters.get("section_index").split(";");
			String[] columnIndexList = parameters.get("column_index").split(";");
			String[] fieldValueList = parameters.get("first_screen_labels").split(";");
			String[] pageIndexList = parameters.get("page_index").split(";");
			String[] stepNumberList = parameters.get("step_number").split(";");
			for (int i = 0; i < fieldValueList.length; i++) {
				String fieldValue = pdfDesignerLayoutPage.getColumnSectionValue(Integer.parseInt(pageIndexList[i]),
						Integer.parseInt(sectionIndexList[i]), Integer.parseInt(columnIndexList[i]),
						Integer.parseInt(fieldIndexList[i]));
				status = fieldValue.equals(fieldValueList[i]);
				reportLog(status, testContext.getName(), "User is on PDF Designer screen", stepNumberList[i],
						"In section: " + sectionIndexList[i] + " and column: "
								+ (Integer.parseInt(columnIndexList[i]) + 1) + " field displayed is: " + fieldValue);
				org.testng.Assert.assertTrue(status);
			}

			pdfDesignerLayoutPage.clickSaveButton();
			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Successfull message",
					"10.0", "Verify Save successful appears: IS NOT AUTOMATED");

			pdfDesignerLayoutPage.clickPreviewButton();

			status = pdfDesignerLayoutPage.isPDFFileDisplayed();
			reportLog(status, testContext.getName(), "User clicked on Preview Button", "11.0", "PDF file displayed");
			org.testng.Assert.assertTrue(status);

			status = pdfDesignerLayoutPage.isDownloadPDFButtonDisplayed();
			reportLog(status, testContext.getName(), "User is on PDF file", "11.1", "DownloadPDF  button displayed");
			org.testng.Assert.assertTrue(status);

			status = pdfDesignerLayoutPage.isClosePDFButtonDisplayed();
			reportLog(status, testContext.getName(), "User is on PDF file", "11.2", "Close(X) button displayed");
			org.testng.Assert.assertTrue(status);

			pdfDesignerLayoutPage.clickClosePDFButton();

			status = pdfDesignerLayoutPage.getOutlineScreenName().equals(parameters.get("first_screen_name"));
			reportLog(status, testContext.getName(), "User clicked on close(X) button in PDF file", "12.0",
					"PDF designer displayed");
			org.testng.Assert.assertTrue(status);

			pdfDesignerLayoutPage.clickCloseButton();
			CreateAppPage createAppPage = pdfDesignerLayoutPage.clickYesButton();

			status = createAppPage.isAppCreateButtonDisplayed();
			reportLog(status, testContext.getName(), "User clicked on close button in PDF designer", "13.0",
					"App page is displayed.");
			org.testng.Assert.assertTrue(status);

			createAppPage.clickAppName(appName).deleteApp();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void createPDFDesignerFromScratchTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);

			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "1.2");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			boolean status = appBuilderPage.isLabelDisplayed("First name");
			reportLog(status, testContext.getName(), "User created first screen with labels", "2.0",
					"First screen created successfully");
			org.testng.Assert.assertTrue(status);

			appBuilderPage.clickSaveButton(appName,parameters.get("username"),parameters.get("password"));
			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Successfull message",
					"2.1", "Verify Save successful appears: IS NOT AUTOMATED");
			PDFDesignerPage pdfDesignerPage = appBuilderPage.clickPDFButton();

			status = pdfDesignerPage.isTitleDisplayed();
			reportLog(status, testContext.getName(), "User clicked on PDF button", "3.0", "Design your PDF displayed");

			status = pdfDesignerPage.isCreateALayoutDisplayed();
			reportLog(status, testContext.getName(), "User is on Design your PDF", "3.1",
					"Create a layout for me displayed");
			org.testng.Assert.assertTrue(status);

			status = pdfDesignerPage.isStartFromScratchDisplayed();
			reportLog(status, testContext.getName(), "User is on Design your PDF", "3.2",
					"Start from scratch displayed");
			org.testng.Assert.assertTrue(status);

			status = pdfDesignerPage.isStartButtonDisplayed();
			reportLog(status, testContext.getName(), "User is on Design your PDF", "3.3", "Start button displayed");
			org.testng.Assert.assertTrue(status);

			String selectedTemplateText = pdfDesignerPage.getActiveTemplateText();
			status = selectedTemplateText.equals(parameters.get("active_template"));
			reportLog(status, testContext.getName(), "User is on Design your PDF", "3.4",
					"By Default option Create a layout for me is selected");
			org.testng.Assert.assertTrue(status);

			PDFDesignerLayoutPage pdfDesignerLayoutPage = pdfDesignerPage.clickStartFromScratch().clickStartButton();

			status = pdfDesignerLayoutPage.getOutlineScreenName().equals(parameters.get("first_screen_name"));
			reportLog(status, testContext.getName(), "User is on PDF Designer screen", "4.0",
					"My First screen in left side in App outline is displayed");
			org.testng.Assert.assertTrue(status);

			pdfDesignerLayoutPage.verifyFieldsInAppOutline("4.1.", testContext,
					"Verify fields under My First screen in App outline",
					parameters.get("first_screen_labels").split(";"));

			pdfDesignerLayoutPage.clickSectionContainer().clickingOnClickToAddSection().clickAddColumn()
					.clickAddColumn();

			pdfDesignerLayoutPage.clickingOnClickToAddSection().clickAddColumnSection(2).clickAddColumnSection(2);

			status = pdfDesignerLayoutPage.isAddSectionDisplayed();
			reportLog(status, testContext.getName(), "On Mouse hover in PDF Designer screen near header", "5.0",
					"Add section displayed");
			org.testng.Assert.assertTrue(status);

			status = pdfDesignerLayoutPage.getSectionCount().equals(parameters.get("section_count"));
			reportLog(status, testContext.getName(), "Verify the number of sections in PDF designer", "5.1",
					"Number of sections in PDF designer are: " + pdfDesignerLayoutPage.getSectionCount());
			org.testng.Assert.assertTrue(status);

			int sectionIndex = 1;
			status = pdfDesignerLayoutPage.getColumnCountInSection(sectionIndex).equals(parameters.get("column_count"));
			reportLog(status, testContext.getName(), "Verify the number of columns in section 1", "5.2",
					"Number of columns in section 1 are: " + pdfDesignerLayoutPage.getColumnCountInSection(1));
			org.testng.Assert.assertTrue(status);

			sectionIndex = 2;
			status = pdfDesignerLayoutPage.getColumnCountInSection(sectionIndex).equals(parameters.get("column_count"));
			reportLog(status, testContext.getName(), "Verify the number of columns in section 2", "5.3",
					"Number of columns in section 2 are: " + pdfDesignerLayoutPage.getColumnCountInSection(2));
			org.testng.Assert.assertTrue(status);

			String[] fieldNameList = parameters.get("first_screen_labels").split(";");
			String[] sectionIndexList = parameters.get("section_index").split(";");
			String[] columnIndexList = parameters.get("column_index").split(";");

			for (int i = 0; i < fieldNameList.length; i++) {
				pdfDesignerLayoutPage.dragDropField(fieldNameList[i], Integer.parseInt(sectionIndexList[i]),
						Integer.parseInt(columnIndexList[i]));
			}

			pdfDesignerLayoutPage.clickSaveButton();
			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Successfull message",
					"6.0", "Verify Save successful appears: IS NOT AUTOMATED");

			pdfDesignerLayoutPage.clickCloseButton();

			status = pdfDesignerLayoutPage.getPopUpMessageContent().contains(parameters.get("popup_message"));
			reportLog(status, testContext.getName(), "Verify On clicking Close button pop up message displayed.", "7.1",
					"Pop Up message displayed successfully");
			org.testng.Assert.assertTrue(status);

			CreateAppPage createAppPage = pdfDesignerLayoutPage.clickYesButton();
			status = createAppPage.isAppCreateButtonDisplayed();
			reportLog(status, testContext.getName(),
					"Verify On clicking Yes button in pop up message App Page is displayed.", "7.2",
					"App page displayed successfully");
			org.testng.Assert.assertTrue(status);

			pdfDesignerLayoutPage = createAppPage.clickEditPDF(appName);
			status = pdfDesignerLayoutPage.getOutlineScreenName().equals(parameters.get("first_screen_name"));
			reportLog(status, testContext.getName(), "Verify User is on PDF Designer screen after clicking Edit PDF",
					"8.0", "PDF Designer screen displayed");
			org.testng.Assert.assertTrue(status);

			status = pdfDesignerLayoutPage.getDisabledButtonText().contains(parameters.get("disabled_button"));
			reportLog(status, testContext.getName(), "User is on PDF Designer screen", "8.1",
					"Save button is displayed as disabled");
			org.testng.Assert.assertTrue(status);

			String[] fieldIndexList = parameters.get("field_index").split(";");
			String[] fieldValueList = parameters.get("field_value_list").split(";");
			String[] pageIndexList = parameters.get("page_index").split(";");
			for (int i = 0; i < fieldNameList.length; i++) {
				String fieldValue = pdfDesignerLayoutPage.getColumnSectionValue(Integer.parseInt(pageIndexList[i]),
						Integer.parseInt(sectionIndexList[i]), Integer.parseInt(columnIndexList[i]),
						Integer.parseInt(fieldIndexList[i]));
				status = fieldValue.equals(fieldValueList[i]);
				reportLog(status, testContext.getName(), "User is on PDF Designer screen", "8.2." + i,
						"In section: " + sectionIndexList[i] + " and column: "
								+ (Integer.parseInt(columnIndexList[i]) + 1) + " field displayed is: " + fieldValue);
				org.testng.Assert.assertTrue(status);
			}
			pdfDesignerLayoutPage.clickSaveButton();
			createAppPage = pdfDesignerLayoutPage.clickCloseButtonafterSave();

			createAppPage.clickAppName(appName).deleteApp();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	/**
	 * QA-456 Verify Column Setting of Normal Screen in Designer
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test
	@Parameters({ "testdescription" })
	public void verifyColumnSettingInDesignerTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
//		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			CreateAppPage createAppPage = new CreateAppPage(driver);
			ShowPDFOptionsPage PDFOptionsPage = new ShowPDFOptionsPage(driver);
			PDFDesignerPage pdfDesignerPage = new PDFDesignerPage(driver);
			PDFDesignerLayoutPage pdfDesignerLayoutPage = new PDFDesignerLayoutPage(driver);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "2");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			boolean status = appBuilderPage.isLabelDisplayed(parameters.get("first_screen_labels").split(";")[0])
					&& appBuilderPage.isLabelDisplayed(parameters.get("first_screen_labels").split(";")[1])
					&& appBuilderPage.isLabelDisplayed(parameters.get("first_screen_labels").split(";")[2]);
			reportLog(status, testContext.getName(), "User created first screen with labels", "3.0",
					"First screen created successfully");

			// publish to devices
			appBuilderPage.clickPublishToDevice();
			publishAppPage.clickPublishToDeviceButton();
			appBuilderPage.clickNextButton();
			publishAppPage.clickPublishButton();
			publishAppPage.clickCloseAppBuilderButton();
			createAppPage.verifyPublishedApp(testContext, "Version 1", appName, "4.");

			createAppPage.clickAppName(appName).clickPDFOptions();
			status = PDFOptionsPage.verifyPDFOptionsTextIsDisplayed();
			reportLog(status, testContext.getName(), "Verify user on PDF options page", "5.0",
					"User Successfully in PDF Options Page");
			PDFOptionsPage.clickLaunchPDFDesignerButton();
			pdfDesignerPage.clickCreateALayout().clickStartButton();

			status = pdfDesignerLayoutPage.getOutlineScreenName().equals(parameters.get("first_screen_name"));
			reportLog(status, testContext.getName(), "User is on PDF Designer screen", "6.0",
					"My First screen in left side in App outline is displayed");
			org.testng.Assert.assertTrue(status);

			// first field column
			pdfDesignerLayoutPage.clickColumnByIndex(parameters.get("columnIndex1"));
			boolean FSDisplayed = pdfDesignerLayoutPage.verifyfieldSettingsDisplayed();
			reportLog(FSDisplayed, testContext.getName(), "Check the Field Setting ", "7.0",
					"Field Setting displayed Successfully");
			pdfDesignerLayoutPage.fieldSettingGeneral(parameters.get("fieldSet_BGC"), parameters.get("fieldSet_Style"),
					parameters.get("fieldSet_Alig"));
			pdfDesignerLayoutPage.clickGlobalSetting();
			pdfDesignerLayoutPage.useGlobalSettingsInFieldSetting(parameters.get("textColor"),
					parameters.get("fieldSet_borders"));
			ReportManager.lognew(testContext.getName(), "Verify all data input ", LogStatus.PASS,
					"Step " + " 8.0" + " All test data input successfully");

			// second column setteing
			pdfDesignerLayoutPage.clickColumnByIndex(parameters.get("columnIndex2"));
			status = pdfDesignerLayoutPage.verifyColumnSettingDisplayed();
			reportLog(status, testContext.getName(), "Verify Column Setting display", "9.0",
					"Column Setting displayed successfylly");
			pdfDesignerLayoutPage.columnSettings(parameters.get("column_2_BG"), parameters.get("column_2_BodyStyle"),
					parameters.get("column_2_BorderColor"));
			pdfDesignerLayoutPage.paddingGlobalSetting(parameters.get("Padding2").split(";"));
			ReportManager.lognew(testContext.getName(), "Verify all data input ", LogStatus.PASS,
					"Step " + " 10.0" + " All test data input successfully");

			pdfDesignerLayoutPage.clickSaveButton();
			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Successfull message",
					"11.0", " Verify Save successful appears: IS NOT AUTOMATED");
			pdfDesignerLayoutPage.refreshDriver(driver);
			status = pdfDesignerLayoutPage.getOutlineScreenName().equals(parameters.get("first_screen_name"));
			reportLog(status, testContext.getName(), "User is on PDF Designer screen", "11.1",
					"My First screen in left side in App outline is displayed");
			org.testng.Assert.assertTrue(status);

			pdfDesignerLayoutPage.clickColumnByIndex(parameters.get("columnIndex1"));
			status = pdfDesignerLayoutPage.verifyFieldSettings(parameters.get("fieldSet_BGC"),
					parameters.get("fieldSet_Alig"), parameters.get("textColor"));
			reportLog(status, testContext.getName(), "Verify the data in Field Settings saved", "12.0",
					" All data in field Setting are saved successfully");
			Assert.assertTrue(status);

			pdfDesignerLayoutPage.clickColumnByIndex(parameters.get("columnIndex2"));
			status = pdfDesignerLayoutPage.verifyColumnSettings(parameters.get("column_2_BG"),
					parameters.get("column_2_BodyStyle"), parameters.get("column_2_BorderColor"),
					parameters.get("Padding2").split(";"));
			reportLog(status, testContext.getName(), "Verify the data in Field Settings saved", "13.0",
					" All data in field Setting are saved successfully");
			Assert.assertTrue(status);
			
			pdfDesignerLayoutPage.clickCloseButton();
			pdfDesignerLayoutPage.clickYesButton();
			createAppPage.clickAppName(appName).deleteApp();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
//
//		} catch (Exception e) {
//			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
//			org.testng.Assert.assertTrue(false, e.getMessage());
//		}
	}

	/**
	 * QA-5185 Verify loop Setting in Designer
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test
	@Parameters("testdescription")
	public void verifyLoopSettingInDesigner(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
//		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the company admin: " + parameters.get("username"), testContext, testDescription);

			DashboardPage dashboardPage = new DashboardPage(driver);
			String appName = parameters.get("app_name") + randomAlphaNumeric(10);
			PublishAppPage publishAppPage = new PublishAppPage(driver);
			CreateAppPage createAppPage = new CreateAppPage(driver);
			ShowPDFOptionsPage PDFOptionsPage = new ShowPDFOptionsPage(driver);
			PDFDesignerPage pdfDesignerPage = new PDFDesignerPage(driver);
			PDFDesignerLayoutPage pdfDesignerLayoutPage = new PDFDesignerLayoutPage(driver);
			AppBuilderPage appBuilderPage = dashboardPage.clickApp().clickAppCreateButton();
			appBuilderPage.createApp(appName, testContext, "2");

			appBuilderPage = appBuilderPage.addScreenAndAddLabel(parameters.get("first_screen_place_holder"),
					parameters.get("first_screen_name"), parameters.get("first_screen_labels").split(";"),
					parameters.get("first_screen_fields").split(";"));
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			boolean status = appBuilderPage.isLabelDisplayed(parameters.get("first_screen_labels").split(";")[0])
					&& appBuilderPage.isLabelDisplayed(parameters.get("first_screen_labels").split(";")[1])
					&& appBuilderPage.isLabelDisplayed(parameters.get("first_screen_labels").split(";")[2]);
			reportLog(status, testContext.getName(), "User created first screen with labels", "3.0",
					"First screen created successfully");
			Assert.assertTrue(status);

			// create loop screen
			appBuilderPage.clickScreen(parameters.get("first_screen_name"));
			appBuilderPage.clickLoopIcon();
			appBuilderPage.enterFieldName(parameters.get("first_parent_screen_name"));
			appBuilderPage.clickDoneButton();
			status = appBuilderPage.isLabelDisplayed(parameters.get("first_parent_screen_name"));
			reportLog(status, testContext.getName(), "User created parent screen with loop", "4,0",
					"Parent screen created successfully");
			org.testng.Assert.assertTrue(status);

			// publish to devices
			appBuilderPage.clickPublishToDevice();
			publishAppPage.clickPublishToDeviceButton();
			appBuilderPage.clickNextButton();
			publishAppPage.clickPublishButton();
			publishAppPage.clickCloseAppBuilderButton();

			createAppPage.clickAppName(appName).clickPDFOptions();
			status = PDFOptionsPage.verifyPDFOptionsTextIsDisplayed();
			reportLog(status, testContext.getName(), "Verify user on PDF options page", "6.0",
					"User Successfully in PDF Options Page");
			PDFOptionsPage.clickLaunchPDFDesignerButton();
			pdfDesignerPage.clickCreateALayout().clickStartButton();
			
			status = pdfDesignerLayoutPage.getOutlineScreenName().equals(parameters.get("first_parent_screen_name"));
			reportLog(status, testContext.getName(), "User is on PDF Designer screen", "7.0",
					"Loop screen in left side in App outline is displayed");
			org.testng.Assert.assertTrue(status);

			pdfDesignerLayoutPage.clickLoopTable();
			status = pdfDesignerLayoutPage.verifyLoopSettingDisplayed(parameters.get("loopSettingText"));
			reportLog(status, testContext.getName(), "Check the Loop Setting ", "8.0",
					"Loop Setting displayed Successfully");
			Assert.assertTrue(status);

			// enter data on table headers
			pdfDesignerLayoutPage.enterLoopSettingsTableHeaders(parameters.get("headers_BGC"),
					parameters.get("headers_TextColor"), parameters.get("headers_alignment"));
			ReportManager.lognew(testContext.getName(), "Verify Table Headers data input ", LogStatus.PASS,
					"Step " + " 8.1" + " All test data input successfully");
			status = pdfDesignerLayoutPage.verifyLoopSetHeaderBordersDefaultValue();
			reportLog(status, testContext.getName(), "Verify default borders value", "8.2",
					"The default borders value was all selected as expected!");

			// enter data on table Rows
			pdfDesignerLayoutPage.enterloopSettingsTableRows(parameters.get("rows_BGC"),
					parameters.get("rows_TextColor"), parameters.get("rows_alignment"));
			ReportManager.lognew(testContext.getName(), "Verify Table Rows data input ", LogStatus.PASS,
					"Step " + " 8.2" + " All test data input successfully");

			pdfDesignerLayoutPage.clickSaveButton();
			reportLog(true, testContext.getName(), "User clicked on Save Button and verify Save Successfull message",
					"9.0", " Verify Save successful appears: IS NOT AUTOMATED");
			pdfDesignerLayoutPage.refreshDriver(driver);
			pdfDesignerLayoutPage.clickLoopTable();
			status = pdfDesignerLayoutPage.verifyLoopSettingDisplayed(parameters.get("loopSettingText"));
			reportLog(status, testContext.getName(), "Check the Loop Setting ", "9.1",
					"Loop Setting displayed Successfully");
			org.testng.Assert.assertTrue(status);

			status = pdfDesignerLayoutPage.verifyLoopSettingHeaderDataSaved(parameters.get("headers_BGC"),
					parameters.get("headers_TextColor"), parameters.get("headers_alignment"));
			reportLog(status, testContext.getName(), "Verify the data in Loop Settings Table Headers saved", "10.0",
					" All data in Loop Setting Table Headers are saved successfully");
			Assert.assertTrue(status);

			status = pdfDesignerLayoutPage.verifyLoopSettingRowsDataSaved(parameters.get("rows_BGC"),
					parameters.get("rows_TextColor"), parameters.get("rows_alignment"));
			reportLog(status, testContext.getName(), "Verify the data in Loop Settings Table Rows saved", "10.1",
					" All data in Loop Setting Table Rows are saved successfully");
			Assert.assertTrue(status);

			pdfDesignerLayoutPage.clickCloseButton();
			pdfDesignerLayoutPage.clickYesButton();
			createAppPage.clickAppName(appName).deleteApp();
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");

//		} catch (Exception e) {
//			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
//			org.testng.Assert.assertTrue(false, e.getMessage());
//		}
	}

}
