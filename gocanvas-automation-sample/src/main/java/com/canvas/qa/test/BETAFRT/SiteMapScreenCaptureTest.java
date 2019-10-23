package com.canvas.qa.test.BETAFRT;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.BasePage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.profile.CreateTrialAccountPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.CaptureScreenshot;
import com.canvas.util.FileReaderUtil;
import com.canvas.util.PropertyUtils;

public class SiteMapScreenCaptureTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription", "rallylink", "mode", "rowCount" })
	public void screenCaptureTest(String testDescription, String rallyLink, String mode, String rowCount,
			ITestContext testContext) throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username2"), parameters.get("password2"),
					"Login as: " + parameters.get("username2"), testContext, testDescription, rallyLink);
			DashboardPage dashboardPage = new DashboardPage(driver);
			LoginPage login = new LoginPage(driver);
			String homePage = PropertyUtils.getProperty("app.url");
			String filepath = "src/main/resources/TestData/gocanvas-all-pages.xlsx";
			FileInputStream file = new FileInputStream(filepath);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet("Rails5 ActiveInActive URLs");
			Iterator<Row> rowIterator = sheet.iterator();
			int respCode = 0;
			boolean status = true;
			String result = "";
			int counter = 0;
			int rowLength = (!rowCount.isEmpty()) ? Integer.parseInt(rowCount) : 259;
			for (int i = 1; i <= rowLength; i++) {
				status = true;
				Row row = rowIterator.next();
				if(row.getCell(3) != null) {
				String url = row.getCell(3).getStringCellValue();
				String urlStatus = new DataFormatter().formatCellValue(row.getCell(4));
				String loginCredentials = row.getCell(5).getStringCellValue();
				String loginStatus = row.getCell(6).getStringCellValue();
				String department = row.getCell(7).getStringCellValue();
				if (!url.isEmpty() && urlStatus.equals("0")) {
					if (loginCredentials.isEmpty() && (counter == 0) && loginStatus.isEmpty()) {
						counter = 1;
						dashboardPage.clickLogOut();
						login.gotoLogin();
						login.typeusername(parameters.get("username"));
						login.typepassword(parameters.get("password"));
						login.Clickonloginbutton();
					} else {
						if (loginStatus.contains("need to log out") && (counter == 1)) {
							counter = 0;
							dashboardPage.clickLogOut();
						}

						if (loginStatus.contains("after singing up")) {
							String signUPPage = "https://devqrt5.gocanvas.com/signup";
							signUPPage = signUPPage.replace("https://devqrt5.gocanvas.com", homePage);
							driver.navigate().to(signUPPage);
							CreateTrialAccountPage createTrialAccountPage = new CreateTrialAccountPage(driver);
							String email = randomAlphaNumeric(10) + "@bogus.com";
							String[] labelIDList = parameters.get("labelidlist").split(";");
							String[] fieldDataList = parameters.get("inputlist").split(";");
							for (int k = 0; k < labelIDList.length; k++) {
								if (k == 2) {
									createTrialAccountPage.enterFieldData(labelIDList[k], email);
								} else {
									createTrialAccountPage.enterFieldData(labelIDList[k], fieldDataList[k]);
								}
							}
							createTrialAccountPage.continueWithEmailClick();
							if (createTrialAccountPage.isPhoneNumberDisplayed()) {
								createTrialAccountPage.step2Phone("9876543210");
							}
							if (createTrialAccountPage.isEmployeeSizeDisplayed()) {
								createTrialAccountPage.selectEmployeeSize(1);
							}
							createTrialAccountPage.enterFieldData(parameters.get("passwordid"),
									parameters.get("userpassword"));
							createTrialAccountPage.tryItFreeStep2Click();
						}
					}
					url = url.replace("https://devqrt5.gocanvas.com", homePage);
					if (department.contains("All Departments")) {
						dashboardPage.clickDepartmentDropDownIcon();
						dashboardPage.clickDepartment("All");
					} else {
						if (department.contains("First Department")) {
							dashboardPage.clickDepartmentDropDownIcon();
							dashboardPage.clickDepartment("First Department");
						}
					}
					driver.navigate().to(url);
					HttpsURLConnection huc = (HttpsURLConnection) (new URL(url).openConnection());
					huc.setRequestMethod("GET");
					huc.connect();
					respCode = huc.getResponseCode();
					result = " Response code is: " + String.valueOf(respCode);
					System.out.println(row.getRowNum() + 1 + " : " + respCode);
					List<WebElement> links = driver.findElements(By.tagName("h1"));
					Iterator<WebElement> it = links.iterator();
					while (it.hasNext()) {
						result = it.next().getText();
						if (respCode != 200) {
							status = false;
							break;
						} else {
							if (result.contains("Error") || result.contains("This page isn’t working")) {
								status = false;
								break;
							} else {
								status = true;
								result = " Response code is: " + String.valueOf(respCode);
								continue;
							}
						}
					}
					huc.disconnect();
					if (mode.equals("All")) {
						CaptureScreenshot.fn_captureScreenshot(driver, (row.getRowNum() + 1) + "_",
								testContext.getName() + "_");
					} else {
						if (!status) {
							CaptureScreenshot.fn_captureScreenshot(driver, (row.getRowNum() + 1) + "_",
									testContext.getName() + "_");
						}
					}
				}
				if (urlStatus.equals("0")) {
					reportLog(status, testContext.getName(), "Page status", String.valueOf(i), url + ">>>>" + result);
				}
			}
		}
			file.close();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription", "rallylink" , "mode"})
	public void javascriptErrorCaptureTest(String testDescription, String rallyLink, String mode,ITestContext testContext) throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username2"), parameters.get("password2"),
					"Login as: " + parameters.get("username2"), testContext, testDescription, rallyLink);
			DashboardPage dashboardPage = new DashboardPage(driver);
			LoginPage login = new LoginPage(driver);
			String homePage = PropertyUtils.getProperty("app.url");
			String filepath = "src/main/resources/TestData/beta5JSErrorScreen.xlsx";
			FileInputStream file = new FileInputStream(filepath);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet("beta5 ActiveInActive URLs");
			Iterator<Row> rowIterator = sheet.iterator();
			int respCode = 0;
			boolean status = true;
			String result = "";
			int counter = 0;
			int rowLength = sheet.getLastRowNum();
			if ((rowLength > 0) || (sheet.getPhysicalNumberOfRows() > 0)) {
				rowLength++;
			}	
			for (int i = 1; i < rowLength; i++) {
				status = true;
				Row row = rowIterator.next();
				String url = row.getCell(3).getStringCellValue();
				String urlStatus = new DataFormatter().formatCellValue(row.getCell(4));
				String loginCredentials = row.getCell(5).getStringCellValue();
				String loginStatus = row.getCell(6).getStringCellValue();
				String department = row.getCell(7).getStringCellValue();
				if (!url.isEmpty() && urlStatus.equals("0")) {
					if (loginCredentials.isEmpty() && (counter == 0) && loginStatus.isEmpty()) {
						counter = 1;
						dashboardPage.clickLogOut();
						login.gotoLogin();
						login.typeusername(parameters.get("username"));
						login.typepassword(parameters.get("password"));
						login.Clickonloginbutton();
					} else {
						if (loginStatus.contains("need to log out") && (counter == 1)) {
							counter = 0;
							dashboardPage.clickLogOut();
						}

						if (loginStatus.contains("after singing up")) {
							/*String signUPPage = "https://beta5.gocanvas.com/signup";
							signUPPage = signUPPage.replace("https://beta5.gocanvas.com", homePage);*/
							String signUPPage = homePage + "/signup";
							driver.navigate().to(signUPPage);
							CreateTrialAccountPage createTrialAccountPage = new CreateTrialAccountPage(driver);
							String email = randomAlphaNumeric(10) + "@bogus.com";
							String[] labelIDList = parameters.get("labelidlist").split(";");
							String[] fieldDataList = parameters.get("inputlist").split(";");
							for (int k = 0; k < labelIDList.length; k++) {
								if (k == 2) {
									createTrialAccountPage.enterFieldData(labelIDList[k], email);
								} else {
									createTrialAccountPage.enterFieldData(labelIDList[k], fieldDataList[k]);
								}
							}
							createTrialAccountPage.continueWithEmailClick();
							if (createTrialAccountPage.isPhoneNumberDisplayed()) {
								createTrialAccountPage.step2Phone("9876543210");
							}
							if (createTrialAccountPage.isEmployeeSizeDisplayed()) {
								createTrialAccountPage.selectEmployeeSize(1);
							}
							createTrialAccountPage.enterFieldData(parameters.get("passwordid"),
									parameters.get("userpassword"));
							createTrialAccountPage.tryItFreeStep2Click();
						}
					}
					url = url.replace("https://beta5.gocanvas.com", homePage);
					if (department.contains("All Departments")) {
						dashboardPage.clickDepartmentDropDownIcon();
						dashboardPage.clickDepartment("All");
					} else {
						if (department.contains("First Department")) {
							dashboardPage.clickDepartmentDropDownIcon();
							dashboardPage.clickDepartment("First Department");
						}
					}
					driver.navigate().to(url);
					BasePage.javaScriptErrorChecker(driver);
					HttpsURLConnection huc = (HttpsURLConnection) (new URL(url).openConnection());
					huc.setRequestMethod("GET");
					huc.connect();
					respCode = huc.getResponseCode();
					result = " Response code is: " + String.valueOf(respCode);
					System.out.println(row.getRowNum() + 1 + " : " + respCode);
					List<WebElement> links = driver.findElements(By.tagName("h1"));
					Iterator<WebElement> it = links.iterator();
					while (it.hasNext()) {
						result = it.next().getText();
						if (respCode != 200) {
							status = false;
							break;
						} else {
							if (result.contains("Error") || result.contains("This page isn’t working")) {
								status = false;
								break;
							} else {
								status = true;
								result = " Response code is: " + String.valueOf(respCode);
								continue;
							}
						}
					}
					huc.disconnect();
					if (mode.equals("All")) {
						CaptureScreenshot.fn_captureScreenshot(driver, (row.getRowNum() + 1) + "_",
								testContext.getName() + "_");
					} else {
						if (!status) {
							CaptureScreenshot.fn_captureScreenshot(driver, (row.getRowNum() + 1) + "_",
									testContext.getName() + "_");
						}
					}
				}
				if (urlStatus.equals("0")) {
					reportLog(status, testContext.getName(), "Page status", String.valueOf(i), url + ">>>>" + result);
				}
			}
			file.close();

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}


}
