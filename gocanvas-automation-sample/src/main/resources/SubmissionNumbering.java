	package com.canvas.qa.pages;
	
	import java.text.DateFormat;
	import java.text.SimpleDateFormat;
	import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
	
	import org.apache.poi.util.SystemOutLogger;
	import org.apache.xerces.util.SynchronizedSymbolTable;
	import org.openqa.selenium.Alert;
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.NoSuchElementException;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.support.FindBy;
	import org.openqa.selenium.support.PageFactory;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.testng.ITestContext;
	
	import com.canvas.qa.core.ReportManager;
	import com.relevantcodes.extentreports.LogStatus;
	
	
	/**
	 * @author kailash.pathak
	 *
	 */
	
	public class SubmissionNumbering extends BasePage {
		
		
		
		@FindBy(xpath = "//a[contains(.,'Submission Numbering')]")
		WebElement appNameLink1;
		
		@FindBy(className = "toast-message")
		WebElement toast;
	
		@FindBy(xpath = "//label[contains(@for,'enabled')]")
		WebElement checkBoxEnable;
	
		@FindBy(id = "btn_Save")
		WebElement clickSave;
		
		
		@FindBy(xpath = "//span[contains(.,'Apps')]")
		WebElement appLinkLeftPanel;
		
		@FindBy(xpath = "//span[contains(.,'Submission No.')]")
		List<WebElement> submissionNoIcon;
		
		@FindBy(xpath = "//span[contains(.,'Submission No.')]")
		WebElement submissionNoIcon1;
		
		
		@FindBy(xpath = "//label[contains(.,'Current GoCanvas App (Each submission, across all users, for this App will have a unique number)')]")
		WebElement CurrentGoCanvasAppRadio;
		
		@FindBy(id = "submission_numbering_label")
		WebElement labelDefaultValue;
	
		@FindBy(id = "submission_numbering_number_padding")
		WebElement submissionNumberDefaultVaue;
		
		@FindBy(xpath = ".//*[@id='errorExplanation']/ul")
		WebElement errorMssg;
		
		@FindBy(id ="submission_numbering_prefix")
		WebElement prefix;
		
		@FindBy(id = "submission_numbering_suffix")
		WebElement suffix;
		
		@FindBy(xpath = "//label[contains(.,'Enable Submission Numbering')]")
		WebElement enableSubmissionNumebringCheckbox;
		
		@FindBy(xpath = "//label[@class='icheckbox']")
		WebElement enableSubmissionCheckbox;
		
		private WebDriver driver;
		
		public SubmissionNumbering(WebDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
		}
		public void clickSubmissionAppLink() throws InterruptedException {
			
			appLinkLeftPanel.click();
			Actions actions = new Actions(driver);
			actions.moveToElement(appNameLink1);
			fluentWait(appNameLink1, driver).click();
		}
		public boolean verifyCheckBoxDefaultStatus(ITestContext testContext) throws InterruptedException {
	
			boolean checkBoxDefaultValue = isDefaultCheckBox();
			return checkBoxDefaultValue;
		}
		public boolean verifyDefaultValueofCheckBox() throws InterruptedException {
	
			if (enableSubmissionNumebringCheckbox.isSelected())
				return true;
			else
				return false;
		}
		public boolean verifyDefaultValueofRadio() throws InterruptedException {
			if (CurrentGoCanvasAppRadio.isSelected())
				return true;
			else
				return false;
		}
		public SubmissionNumbering unSelectCheckBox(String step, ITestContext testContext) throws InterruptedException {
			
			clickOnHiddenElement(clickSave,driver);
			reportLog(true, testContext.getName(), "Check box is selected", step, "Check box is selected");
			return this;
		}
	
		public SubmissionNumbering saveStatus() throws InterruptedException {
			clickOnHiddenElement(clickSave,driver);
			return this;
		}
		public SubmissionNumbering  clickCheckBox() throws InterruptedException {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", enableSubmissionCheckbox);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			return this;
		}
		public boolean isDefaultCheckBox() throws InterruptedException {
	
			if (checkBoxEnable.isSelected())
				return true;
			else
				return false;
		}
		
		public SubmissionNumbering clickSubmissionNumber() throws InterruptedException {		
			fluentWait(submissionNoIcon1, driver).click();
			return this;
		}
	
		
		public boolean isSubmissionNoIConDisplay() throws InterruptedException {
			return submissionNoIcon.size() > 0;
		}
		
		
		public boolean verifyLabelValue(String defaultText) throws InterruptedException {
			boolean labelValue = labelDefaultValue.getAttribute("value").equals(defaultText);
			return labelValue;
		}
		
		
		public boolean verifySubmissionValue(String defaultSubmissionValue) throws InterruptedException {
			boolean submissionValue = submissionNumberDefaultVaue.getAttribute("value").equals(defaultSubmissionValue);
			return submissionValue;
		}
		
		public boolean verifyErrorMessage(String labelVal,String submissionVal,String otherPrefixvalue,String suffixvalue,String error_msg) throws InterruptedException {
			
			labelDefaultValue.clear();
			labelDefaultValue.sendKeys(labelVal);
			submissionNumberDefaultVaue.clear();
			submissionNumberDefaultVaue.sendKeys(submissionVal);
			prefix.clear();
			prefix.sendKeys(otherPrefixvalue);
			suffix.clear();
			suffix.sendKeys(suffixvalue);
			clickSave.click();	
			boolean errorMessage = errorMssg.getText().equals(error_msg);
			return errorMessage;
		}
		
		public boolean verifySubmissonNumberingUpdate(String labelVal,String submissionVal,String success_msg) throws InterruptedException {
			
			labelDefaultValue.clear();
			labelDefaultValue.sendKeys(labelVal);
			submissionNumberDefaultVaue.clear();
			submissionNumberDefaultVaue.sendKeys(submissionVal);
			clickSave.click();
			boolean successMessage = toast.getText().equals(success_msg);
			return successMessage;
		}
		
		public void verifyResetSubmissonNumberingUpdate(String labelVal,String submissionVal) throws InterruptedException {
			
			labelDefaultValue.clear();
			labelDefaultValue.sendKeys(labelVal);
			submissionNumberDefaultVaue.clear();
			submissionNumberDefaultVaue.sendKeys(submissionVal);
			clickSave.click();
		}
	
		public boolean verifySubmissonNumberingDisable(String disable_submissions_msg) throws InterruptedException {
			
			enableSubmissionNumebringCheckbox.click();
			clickSave.click();
			boolean disableSubmissionMessage = toast.getText().equals(disable_submissions_msg);
			return disableSubmissionMessage;
		}
		
		public boolean verifySubmissonNumberingCheckBox() throws InterruptedException {
	
			if (enableSubmissionNumebringCheckbox.isSelected())
				return true;
			else
				return false;
		}
		public void verifyReenableSubmissionNumbering() throws InterruptedException {
			
			enableSubmissionNumebringCheckbox.click();
			clickSave.click();	
		}
		
		@FindBy(xpath = "//span[contains(.,'Submissions')]")
		WebElement submissionLink;
		String submisson ="//h1[contains(.,'Submissions')]";
		
	
		//WebElement table_element = driver.findElement(By.xpath(String.format(table1)));
		
		public void clickSubmissionLink() throws InterruptedException {
		
			clickOnHiddenElement(submissionLink, driver);
			WebDriverWait wait=new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(submisson))));
		}
		
		@FindBy(xpath = "//table/tbody/tr[1]/td[2]/a")
		WebElement clickDeleteUserLink;
		
		public void clickOnLinkInSubmissionPage() throws InterruptedException {
			fluentWait(clickDeleteUserLink, driver).click();
		}
		@FindBy(xpath = "//th[@class='count']")
		List<WebElement> submissionDateCheckBox;
		
		public boolean isSubmissionDateCheckBoxDisplayDesigner() throws InterruptedException {
			return submissionDateCheckBox.size() > 0;
		}
	
		@FindBy(xpath = "//table/tbody/tr[1]/td[1]/a")
		WebElement dateTimeLick;
		
		@FindBy(xpath = "//i[@class='fa fa-edit fa-fw fa-lg']")
		WebElement editButton;
		
		
		public boolean verifyEditButtons() throws InterruptedException {
			
			dateTimeLick.click();
			return editButton.isDisplayed();
			
		}
		
		String clickEditIcon ="//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Short Text:')]/../following-sibling::dd//*[@class ='btn-link']";
		//WebElement table_element = driver.findElement(By.xpath(String.format(table1)));
		
		//@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Short Text:')]/../following-sibling::dd//*[@class ='btn-link']")
		//WebElement clickEditIcon;
		
		@FindBy(xpath = ".//*[@title='Save']")
		WebElement saveValue;
		//@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Short Text:')]/../following-sibling::dd//*[@title='Save']")
		//WebElement saveValueShortText;
		
		String editable ="//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'Short Text:')]/../following-sibling::dd//*[@class ='editable']";
		String saveValueShortText ="//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Short Text:')]/../following-sibling::dd//*[@title='Save']";
		String editExistingData ="//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Short Text:')]/../following-sibling::dd//input[@type='text']";
	//	@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Short Text:')]/../following-sibling::dd//input[@type='text']")
	//	WebElement editExistingData;
		
		public void saveEditedValue(String updated_txt) throws InterruptedException {
			
			driver.findElement(By.xpath(String.format(clickEditIcon))).click();
		
			//By.xpath(String.format(clickEditIcon)).click();
			//clickEditIcon.click();
			//editExistingData.clear();
			driver.findElement(By.xpath(String.format(editExistingData))).clear();
			driver.findElement(By.xpath(String.format(editExistingData))).sendKeys(updated_txt);
			//editExistingData.sendKeys(updated_txt);
			//saveValueShortText.click();	
			driver.findElement(By.xpath(String.format(saveValueShortText))).click();
			WebDriverWait wait=new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(editable))));
		}
		
		public boolean verifySubmissionDate(String submisson_date) throws InterruptedException {
			
			clickDeleteUserLink.click();
			boolean SubmissionDate = dateTimeLick.getText().equals(submisson_date);
			return SubmissionDate;
		}
		
		@FindBy(xpath = "//th[@class='count']")
		WebElement submissionLeftDateCheckBox;
		
		@FindBy(xpath = "//button[@id='delete']")
		WebElement deleteButtonText;
		
		public boolean deleteLogoText(String logo_txt) throws InterruptedException {
			clickDeleteUserLink.click();
			submissionLeftDateCheckBox.click();
			boolean deleteLogoText = deleteButtonText.getText().equals(logo_txt);
			return deleteLogoText;
		}
		
		@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Short Text:')]/../following-sibling::dd//*[@class ='btn-link']")
		List<WebElement> editIconShortField;
		
		@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Number:')]/../following-sibling::dd//*[@class ='btn-link']")
		List<WebElement> editIconNumField;
		
		@FindBy(xpath = "//table/tbody/tr[1]/td[2]/a")
		WebElement dateTimeClick; 
		
		public boolean isEditButtonDisplay() throws InterruptedException {
			dateTimeClick.click();
			return editIconShortField.size()>0 && editIconNumField.size()>0;
		}
		
		//@FindBy(xpath = "//a[contains(.,'TC4006 - Edit/Reporter Delete/User')]")
		@FindBy(xpath = "//table/tbody/tr[2]/td[2]/a")
		WebElement clickDeleteReportetUserLink;
		
		public boolean isSubmissionDateCheckBoxDisplayUser() throws InterruptedException {
			clickDeleteReportetUserLink.click();
			return submissionDateCheckBox.size() > 0;
		}
	
		@FindBy(xpath = "//table/tbody/tr[1]//*[@class ='date']")
		WebElement dateTimeLickReporter;
		
		@FindBy(xpath = "//i[@class='fa fa-edit fa-fw fa-lg']")
		WebElement editButtonReporter;
		
		public boolean verifyEditButtonsReporter() throws InterruptedException {
			dateTimeLickReporter.click();
			return editButtonReporter.isDisplayed();
		}
		
		@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Date:')]/../following-sibling::dd//*[@class ='btn-link']")
		WebElement editableIcon;
		@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Date:')]/../following-sibling::dd//*[@class ='editable']")
		WebElement dateDeafultValue;
		@FindBy(xpath = "//input[@type='text']")
		WebElement updateDateDefaultValue;
		
		public void enterUpdateSubmissionDate() throws InterruptedException {
			editableIcon.click();
			updateDateDefaultValue.clear();
		}
		
		public boolean saveSubmissionDate(String date) throws InterruptedException {
			updateDateDefaultValue.sendKeys(date);
			saveValue.click();
			WebDriverWait wait=new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Date:')]/../following-sibling::dd//*[@class ='editable']")));
			boolean updatedDate = dateDeafultValue.getText().equals(date);
			return updatedDate;
		}
		
		@FindBy(xpath = "//i[@class='fa fa-edit fa-fw fa-lg']")
		WebElement editIcon;
		
		public boolean verifyEditIcon() throws InterruptedException {
			
			dateTimeLickReporter.click();
			return editIcon.isDisplayed();
		}
	
		@FindBy(xpath = "//table/tbody/tr[1]/td[2]/a")
		WebElement submissonRowFirst;
		
		public boolean verfiyLogoText(String logo_txt) throws InterruptedException {
			
			submissonRowFirst.click();
			submissionLeftDateCheckBox.click();
			boolean deleteLogoText = deleteButtonText.getText().equals(logo_txt);
			return deleteLogoText;
		}
		
		@FindBy(xpath = "//table/tbody/tr[2]/td[2]")
		WebElement clicksubmissionDate;
		@FindBy(xpath = ".//*[@title='Edit']")
		WebElement editIconField;
		@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Date:')]/../following-sibling::dd//*[@class ='btn-link']")
		List<WebElement> editIconDateField;
		
		@FindBy(xpath = "//*[@class ='submission_section ibox left-right-margin-0 m-md']//span[contains(.,'New Time:')]/../following-sibling::dd//*[@class ='btn-link']")
		List<WebElement> editIconTimeField;
		
		public boolean isEditIconDisplay() throws InterruptedException {
			clicksubmissionDate.click();
			return editIconDateField.size()>0 && editIconTimeField.size()>0;
		}
		
		public void dateTimeLink() throws InterruptedException {
			dateTimeLick.click();
		}
		
		
		//TC8070
		
		public void deleteSubmisson() throws InterruptedException {
			deleteButtonText.click();
			 Alert alert = driver.switchTo().alert();
			 alert.accept();
			 WebDriverWait wait = new WebDriverWait(driver, 20);
			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Log Out")));
		}
	
		@FindBy(xpath = "//table/tbody/tr[1]//td[@class ='sel']")
		WebElement firstSubmisson;
		
		@FindBy(xpath = "//table/tbody/tr[1]//td[1]/label/ins")
		WebElement firstSubmissonCheckBox;
		
		public void selectFirstSubmisson() throws InterruptedException {
			clickOnHiddenElement(firstSubmissonCheckBox, driver);
			}
		
		@FindBy(id = "advanced_user_search_email")
		WebElement searchUser;
		@FindBy(xpath = "//input[@value='Search']")
		WebElement searchButton;
		@FindBy(xpath = "//span[contains(.,'Manage')]")
		WebElement manageButton;
		
		public void searchUserAndManage(String userName) throws InterruptedException {
			searchUser.sendKeys(userName);
			Actions actions = new Actions(driver);
			actions.moveToElement(searchButton);
			fluentWait(searchButton, driver).click();
			fluentWait(manageButton, driver).click();
			fluentWait(appLinkLeftPanel, driver).click();		
			}
		
		@FindBy(xpath = "//span[contains(.,'Deleted Submissions')]")
		WebElement deleteSubmisson;
		public void verifyDeletedSubmissons() throws InterruptedException {
			
			Actions actions = new Actions(driver);
			actions.moveToElement(deleteSubmisson);
			deleteSubmisson.click();
			}
		
		public boolean withinRange(Date actualDate,Date startDate,Date endDate) 
		
		{		    
			return actualDate.getTime() >= startDate.getTime() &&
		    actualDate.getTime() <= endDate.getTime();
		}
		
		
		String table1 ="//table/tbody/tr[1]";
		String table ="//table/tbody/tr/td[preceding-sibling::td=//input[@class='checkbox'] and following-sibling::td[2]]";
		String toastMsg ="//div[@class='toast-message']";
		
	
		public boolean isColumnValueDisplayed(String text)
		 {	
			
			
			String xpathLabel = "//td[text() = '"+text+"']";
			
			boolean updatedData = driver.findElement(By.xpath((xpathLabel))).getText().equals(text);
			return updatedData;
			//WebElement element = driver.findElement(By.xpath(xpathLabel));
			//return element.isDisplayed();
			}
			
		public boolean isSubmissionColumnValueDisplayed(String text)
		 {	
			String xpathLabel = "//td[text() = '"+text+" ']";
			WebElement element = driver.findElement(By.xpath(xpathLabel));
			return element.isDisplayed();
			}
		
		public boolean isDateColumnValueDisplayed(String text)
		 {	
			String xpathLabel = "//td[text() = '"+text+"']";
			
			boolean updatedDate = driver.findElement(By.xpath((xpathLabel))).getText().equals(text);
			return updatedDate;
			}
		
		public ArrayList<String> verifyDeletedSubmisson(){
	
			WebElement table_element = driver.findElement(By.xpath(String.format(table1)));
			ArrayList<String> appList = new ArrayList<String>();
		    ArrayList<WebElement> rows = (ArrayList<WebElement>) table_element.findElements(By.xpath(String.format(table)));
		    for (WebElement row : rows) {
		    	appList.add(row.getText());
		            
		        }
		    return appList;
			}
		
	
		@FindBy(id= "ids_")
		WebElement checkBox;
		
		@FindBy(id="subit")
		WebElement restoreSelection;	
		public void restoreSubmisson() throws InterruptedException 
		
		{
			checkBox.click();
			restoreSelection.click();
			WebDriverWait wait=new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(toastMsg))));
		}
		
			@FindBy(xpath="//table/tbody/tr/td[4]")
			WebElement submissionId;
			
		public boolean isSubmissionDisplayAfterDelete(String submissonId) throws InterruptedException 
		
		{
			
			List<WebElement> cell =  driver.findElements(By.xpath("//table/tbody/tr/td[4]"));
			
			 for (WebElement c : cell) {
		            String cellvalue = c.getText();
		            System.out.println("Cell Values : "+cellvalue);
		            if (cellvalue.equalsIgnoreCase(submissonId)) {
		                return true;
		            }
		        }
			
			//boolean submissionidVal  =submissionId.getText().equals(submissonId);
				return false;
		}
			
			@FindBy(xpath = "//table/tbody/tr[1]//td[5]")
			WebElement submissionID;
			
			public boolean verifyRestoreSubmisson(String submissonID) throws InterruptedException {
				boolean submissionId = submissionID.getText().equals(submissonID);
				return submissionId;
			}
		
		public void reportLog(boolean condition, String testId, String testCaseDescription, String stepnumber, String testStepDescription) {
			if(condition){
				ReportManager.lognew(testId, testCaseDescription,LogStatus.PASS,"Step " + stepnumber + " " +  testStepDescription);
			} else
			{
				ReportManager.lognew(testId, testCaseDescription,LogStatus.FAIL,"Step " + stepnumber + " " +  testStepDescription);
			}
		}
	}
