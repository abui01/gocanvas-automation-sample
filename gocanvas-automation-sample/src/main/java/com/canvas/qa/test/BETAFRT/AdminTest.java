package com.canvas.qa.test.BETAFRT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.GoCanvasOnlyPage;
import com.canvas.qa.pages.SearchUsersPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.pages.profile.UsersPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class AdminTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void adminDisableTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
			searchUsersPage.enterEmail(parameters.get("user1"));
			searchUsersPage.clickSearch();
			dashboardPage = searchUsersPage.clickOnManage(parameters.get("user1"));
			dashboardPage.clickGoCanvasOnly();
			customWait(2);
			boolean status = dashboardPage.isDisableAllUserDisplayed();
			reportLog(status, testContext.getName(),
					"Verify Admin is able to view 'Disable All Users' link under the GoCanvas Only Options.", "1.1",
					"Admin was able to view the 'Disable All Users' link when viewing GoCanvas Only options: on the managed account.");
			org.testng.Assert.assertTrue(status);

			dashboardPage.clickDisableAllUsers();
			status = dashboardPage.getAlertMessage().contains(parameters.get("message"));
			reportLog(status, testContext.getName(),
					"Verify we get prompted Are you sure you want to disable all users of the account?", "2.0",
					"Are you sure you want to disable all users of the account? was displayed");
			org.testng.Assert.assertTrue(status);

			driver.switchTo().alert().dismiss();
			dashboardPage.clickDisableAllUsers();
			driver.switchTo().alert().accept();
			UsersPage usersPage = new UsersPage(driver);
			status = usersPage.isMessageDisplayed(parameters.get("message2"));
			reportLog(status, testContext.getName(), "Verify on the Users page it says No User found", "3.0",
					"No User found displays correctly on the Users Page.");
			org.testng.Assert.assertTrue(status);

			usersPage.clickFilterDDL();
			usersPage.clickInactive();
			usersPage.clickApply();
			String[] emailList = parameters.get("email_list").split(";");
			for (int i = 0; i < emailList.length; i++) {
				status = usersPage.isEmailDisplayed(emailList[i]);
				reportLog(status, testContext.getName(), "Verify disabled users display:" + emailList[i], "4." + i,
						emailList[i] + " :displayed");
				org.testng.Assert.assertTrue(status);
			}

			usersPage.logout();
			LoginPage login = new LoginPage(driver);
			login.typeusername(parameters.get("user2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbuttonforinvalidlogin();
			if (login.ifTermsOfUseDispalyed()) {
				login.clickTermsOfUseCheckbox();
				login.typepassword(parameters.get("password2"));
				login.Clickonloginbuttonforinvalidlogin();
			}
			status = login.getToastMessage().contains(parameters.get("message3"));
			reportLog(status, testContext.getName(),
					"Verify users gets Your account has been disabled, please contact support.", "5.0",
					"Your account has been disabled, please contact support displayed");
			org.testng.Assert.assertTrue(status);

			login.typeusername(parameters.get("username"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();

			for (int i = 0; i < emailList.length; i++) {
				searchUsersPage.enterEmail(emailList[i]);
				searchUsersPage.clickSearch();
				searchUsersPage.clickOnMore(emailList[i]);
				usersPage = searchUsersPage.clickEnable();
				usersPage.purchaseClick();
				
				
		
			}

			usersPage.logout();
			login.typeusername(parameters.get("user1"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbuttonforinvalidlogin();
			customWait(2);

			if (login.ifTermsOfUseDispalyed()) {
				login.clickTermsOfUseCheckbox();
				login.typepassword(parameters.get("password2"));
				login.Clickonloginbutton();
			}

			status = dashboardPage.isWorkflowAndDispatchDisplayed();
			reportLog(status, testContext.getName(), "User is able to successfully login to GoCanvas Website.", "6.0",
					"User is able to successfully login to GoCanvas Website.");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void bulkUploadUserTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the admin: " + parameters.get("username"), testContext, testDescription);
			GoCanvasOnlyPage goCanvasOnlyPage = new GoCanvasOnlyPage(driver);
			goCanvasOnlyPage.searchUser(parameters.get("emailToManage")).clickOnManage();
			goCanvasOnlyPage.clickGoCanvasOnly();
			customWait(2);
			goCanvasOnlyPage.clickBulkUploadUsers();
			String tmpFolderPath = parameters.get("app1_file_path");
			String expectedFileName = parameters.get("file_name");
			String filepath1 = System.getProperty("user.dir") + tmpFolderPath + expectedFileName;
			File file = new File(filepath1);
			boolean status = file.exists();
			if (status) {
				file.delete();
			}
			goCanvasOnlyPage.clickDownloadSampleFile();
			customWait(5);
			file = new File(filepath1);
			status = file.exists();

			
			BufferedReader br = new BufferedReader(new FileReader(filepath1));
			String[] data = br.readLine().split(",");
			String[] expectedList = parameters.get("column_list").split(";");
			for (int i = 0; i < data.length; i++) {
				status = data[i].equals(expectedList[i]);
				reportLog(status, testContext.getName(), "Verify CSV column list", "1.1." + i,
						expectedList[i] + " exists in CSV file");
				org.testng.Assert.assertTrue(status);
			}

			String uploadFilepath = System.getProperty("user.dir") + parameters.get("upload_file_path");
			goCanvasOnlyPage.uploadFile(uploadFilepath);
			goCanvasOnlyPage.clickUpload();
			customWait(10);
			LoginPage login = goCanvasOnlyPage.clickLogOutButton();
			login.typeusername(parameters.get("username2"));
			login.typepassword(parameters.get("password2"));
			login.Clickonloginbuttonforinvalidlogin();
			if (login.ifTermsOfUseDispalyed()) {
				login.clickTermsOfUseCheckbox();
				login.typepassword(parameters.get("password2"));
				login.Clickonloginbutton();
			}

			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickAccount();
			UsersPage usersPage = dashboardPage.clickUsers();
			usersPage.searchUser(parameters.get("username2"));
			status = usersPage.getName().contains(parameters.get("name1"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("name1") + " is displayed", "2.0",
					parameters.get("name1") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = usersPage.isEmailDisplayed(parameters.get("email1"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("email1") + " is displayed", "2.1",
					parameters.get("email1") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = usersPage.getPlan().contains(parameters.get("plan1"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("plan1") + " is displayed", "2.2",
					parameters.get("plan1") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = usersPage.isRoleDisplayed(parameters.get("role1"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("role1") + " is displayed", "2.3",
					parameters.get("role1") + " is displayed");
			org.testng.Assert.assertTrue(status);

			usersPage = dashboardPage.clickUsers();
			usersPage.searchUser(parameters.get("username3"));
			status = usersPage.getName().contains(parameters.get("name2"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("name2") + " is displayed", "2.4",
					parameters.get("name2") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = usersPage.isEmailDisplayed(parameters.get("email2"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("email2") + " is displayed", "2.5",
					parameters.get("email2") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = usersPage.getPlan().contains(parameters.get("plan2"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("plan2") + " is displayed", "2.6",
					parameters.get("plan2") + " is displayed");
			org.testng.Assert.assertTrue(status);

			status = usersPage.isRoleDisplayed(parameters.get("role2"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("role2") + " is displayed", "2.7",
					parameters.get("role2") + " is displayed");
			org.testng.Assert.assertTrue(status);

			login = goCanvasOnlyPage.clickLogOutButton();
			login.typeusername(parameters.get("username3"));
			login.typepassword(parameters.get("password3"));
			login.Clickonloginbuttonforinvalidlogin();
			if (login.ifTermsOfUseDispalyed()) {
				login.clickTermsOfUseCheckbox();
				login.typepassword(parameters.get("password3"));
				login.Clickonloginbutton();
			}
			dashboardPage.clickProfile();
			status = dashboardPage.getPhoneNumber().contains(parameters.get("phone_number"));
			reportLog(status, testContext.getName(), "Verify " + parameters.get("phone_number") + " is displayed",
					"3.0", parameters.get("phone_number") + " is displayed");
			org.testng.Assert.assertTrue(status);

			login = dashboardPage.clickLogOut();
			login.typeusername(parameters.get("username"));
			login.typepassword(parameters.get("password"));
			login.Clickonloginbutton();
			String[] userlist = { parameters.get("username2"), parameters.get("username3") };
			for (int i = 0; i < userlist.length; i++) {
				SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
				searchUsersPage.enterEmail(userlist[i]);
				searchUsersPage.clickSearch();
				dashboardPage = searchUsersPage.clickOnManage(userlist[i]);
				dashboardPage.clickProfile();
				dashboardPage.clickEdit();
				String email = randomAlphaNumeric(10) + "@bogus.com";
				dashboardPage.enterEmail(email);
				dashboardPage.clickSave();
				customWait(2);
				searchUsersPage = dashboardPage.clickResumeYourSession();
				searchUsersPage.enterEmail(email);
				searchUsersPage.clickSearch();
				searchUsersPage.clickOnMore(email.toLowerCase());
				searchUsersPage.clickDisable();
				customWait(2);
			}

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void moreOptionsTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
			searchUsersPage.enterEmail(parameters.get("email"));
			searchUsersPage.clickSearch();
			searchUsersPage.clickOnMore(parameters.get("email"));
			String[] optionList = parameters.get("more_options").split(";");
			for (int i = 0; i < optionList.length; i++) {
				boolean status = searchUsersPage.isOptionFieldDisplayed(optionList[i]);
				reportLog(status, testContext.getName(), "Verify more option displays:" + optionList[i], "1.1." + i,
						optionList[i] + " : more option displayed");
				org.testng.Assert.assertTrue(status);
			}

			customWait(2);
			searchUsersPage.clickOnMore(parameters.get("email"));
			searchUsersPage.clickOnMore(parameters.get("email"));
			searchUsersPage.clickFeatures();
			if (searchUsersPage.isOptionInstanceDisplayed(parameters.get("option5"))) {
				searchUsersPage.clickOption(parameters.get("option5"));
			}

			customWait(2);
			searchUsersPage.clickOnMore(parameters.get("email"));
			searchUsersPage.clickOnMore(parameters.get("email"));
			searchUsersPage.clickFeatures();
			String[] featureList = parameters.get("option_list").split(";");
			for (int i = 0; i < featureList.length; i++) {
				boolean status = searchUsersPage.isOptionFieldDisplayed(featureList[i]);
				reportLog(status, testContext.getName(), "Verify feature option displays:" + featureList[i], "2." + i,
						featureList[i] + " : feature option displayed");
				org.testng.Assert.assertTrue(status);
			}

			customWait(2);
			searchUsersPage.clickOnMore(parameters.get("email"));
			searchUsersPage.clickOnMore(parameters.get("email"));
			searchUsersPage.clickOption(parameters.get("option2"));
			String[] logsList = parameters.get("logs_list").split(";");
			for (int i = 0; i < logsList.length; i++) {
				boolean status = searchUsersPage.isOptionFieldDisplayed(logsList[i]);
				reportLog(status, testContext.getName(), "Verify logs option displays:" + logsList[i], "3." + i,
						logsList[i] + " : logs option displayed");
				org.testng.Assert.assertTrue(status);
			}

			customWait(2);
			searchUsersPage.clickOnMore(parameters.get("email"));
			searchUsersPage.clickOnMore(parameters.get("email"));
			searchUsersPage.clickOption(parameters.get("option3"));
			String[] salesList = parameters.get("sales_list").split(";");
			for (int i = 0; i < salesList.length; i++) {
				boolean status = searchUsersPage.isOptionFieldDisplayed(salesList[i]);
				reportLog(status, testContext.getName(), "Verify sales associtae  option displays:" + salesList[i],
						"4." + i, salesList[i] + " : sales associate option displayed");
				org.testng.Assert.assertTrue(status);
			}

			customWait(2);
			searchUsersPage.clickOnMore(parameters.get("email"));
			searchUsersPage.clickOnMore(parameters.get("email"));
			searchUsersPage.clickFeatures();
			searchUsersPage.clickOption(parameters.get("option4"));
			boolean status = searchUsersPage.getToastMessage().contains(parameters.get("message"));
			reportLog(status, testContext.getName(), "Verify toast message displayed: " + parameters.get("message"),
					"5.0", parameters.get("message") + " : toast message displayed");
			org.testng.Assert.assertTrue(status);

			customWait(2);
			searchUsersPage.clickOnMore(parameters.get("email"));
			searchUsersPage.clickFeatures();
			searchUsersPage.clickOption(parameters.get("option5"));

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void searchUsersTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		//try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
			String[] labelList = parameters.get("label_list").split(";");
			for (int i = 0; i < labelList.length; i++) {
				boolean status = searchUsersPage.isLabelDisplayed(labelList[i]);
				reportLog(status, testContext.getName(), labelList[i] + " : label displayed", "1.1." + i,
						labelList[i] + " : label displayed");
				org.testng.Assert.assertTrue(status);
			}

			boolean status = searchUsersPage.isSearchButtonDisplayed();
			reportLog(status, testContext.getName(), "Verify search button displayed", "1.2",
					"Search button displayed");
			org.testng.Assert.assertTrue(status);

			searchUsersPage.enterEmail(parameters.get("email"));
			searchUsersPage.clickSearch();
			String[] fieldList1 = parameters.get("field_list1").split(";");
			for (int i = 0; i < fieldList1.length; i++) {
				status = searchUsersPage.isTabelFieldDisplayed(fieldList1[i]);
				reportLog(status, testContext.getName(), fieldList1[i] + " :  displayed", "2.0." + i,
						fieldList1[i] + " : displayed");
				org.testng.Assert.assertTrue(status);
			}

			String[] fieldList2 = parameters.get("field_list2").split(";");
			for (int i = 0; i < fieldList2.length; i++) {
				status = searchUsersPage.isTabelField2Displayed(fieldList2[i]);
				reportLog(status, testContext.getName(), fieldList2[i] + " :  displayed", "2.1." + i,
						fieldList2[i] + " : displayed");
				org.testng.Assert.assertTrue(status);
			}

			status = searchUsersPage.isMoreDisplayed(parameters.get("email"));
			reportLog(status, testContext.getName(), "Verify More displayed", "2.2", "More displayed");
			org.testng.Assert.assertTrue(status);

			status = searchUsersPage.isManageDisplayed(parameters.get("email"));
			reportLog(status, testContext.getName(), "Verify Manage displayed", "2.3", "Manage displayed");
			org.testng.Assert.assertTrue(status);

			searchUsersPage = dashboardPage.clickSearchUsers();
			searchUsersPage.selectRole(parameters.get("role"));
			searchUsersPage.clickSearch();

			status = searchUsersPage.isRoleDisplayed(parameters.get("role"));
			reportLog(status, testContext.getName(), "Verify role displayed: " + parameters.get("role"), "3.0",
					parameters.get("role") + " : role displayed");
			org.testng.Assert.assertTrue(status);

			status = searchUsersPage.isEmailDisplayed(parameters.get("email2"));
			reportLog(status, testContext.getName(), "Verify Email displayed " + parameters.get("email2"), "3.1",
					parameters.get("email2") + " is email displayed");
			org.testng.Assert.assertTrue(status);

			searchUsersPage = dashboardPage.clickSearchUsers();
			searchUsersPage.enterCompanyName(parameters.get("company_name"));
			searchUsersPage.clickSearch();

			status = searchUsersPage.isEmailDisplayed(parameters.get("email3"));
			reportLog(status, testContext.getName(), "Verify email displayed: " + parameters.get("email3"), "4.0",
					parameters.get("email3") + " : email displayed");
			org.testng.Assert.assertTrue(status);

			status = searchUsersPage.getCreatedAt(parameters.get("email3")).contains(parameters.get("created"));
			reportLog(status, testContext.getName(), "Verify created for email: " + parameters.get("email3"), "4.1",
					"For email: " + parameters.get("email3") + " created displayed");
			org.testng.Assert.assertTrue(status);

			searchUsersPage = dashboardPage.clickSearchUsers();
			searchUsersPage.enterFirstName(parameters.get("first_name"));
			searchUsersPage.clickSearch();

			status = searchUsersPage.isEmailDisplayed(parameters.get("email_info"));
			reportLog(status, testContext.getName(), "Verify email displayed: " + parameters.get("email_info"), "5.0",
					parameters.get("email_info") + " : email displayed");
			org.testng.Assert.assertTrue(status);

			status = searchUsersPage.getCreatedAt(parameters.get("email_info"))
					.contains(parameters.get("created_info"));
			reportLog(status, testContext.getName(), "Verify created for email: " + parameters.get("email_info"), "5.1",
					"For email: " + parameters.get("email_info") + " created displayed is: "
							+ parameters.get("created_info"));
			org.testng.Assert.assertTrue(status);

			status = searchUsersPage.getLastLogin(parameters.get("email_info")).contains(parameters.get("last_login"));
			reportLog(status, testContext.getName(), "Verify last login for email: " + parameters.get("email_info"),
					"5.2", "For email: " + parameters.get("email_info") + " last login displayed is: "
							+ parameters.get("last_login"));
			org.testng.Assert.assertTrue(status);

			status = searchUsersPage.getCompanyName(parameters.get("email_info"))
					.contains(parameters.get("account_info"));
			reportLog(status, testContext.getName(), "Verify company for email: " + parameters.get("email_info"), "5.3",
					"For email: " + parameters.get("email_info") + " company displayed is: "
							+ parameters.get("account_info"));
			org.testng.Assert.assertTrue(status);

			status = searchUsersPage.getName(parameters.get("email_info")).contains(parameters.get("name"));
			reportLog(status, testContext.getName(), "Verify name for email: " + parameters.get("email_info"), "5.5",
					"For email: " + parameters.get("email_info") + " name displayed is: " + parameters.get("name"));
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		//} catch (Exception e) {
		//	reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			//org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

