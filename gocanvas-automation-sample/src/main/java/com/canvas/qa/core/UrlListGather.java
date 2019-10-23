package com.canvas.qa.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.util.PropertyUtils;

public class UrlListGather {

	public static List<List<String>> appNameList = new ArrayList<List<String>>();

	public static List<List<String>> dataURL = new ArrayList<List<String>>();

	public static List<List<String>> javaScriptLogs = new ArrayList<List<String>>();
	
	public static List<List<String>> MixedContentLogs = new ArrayList<List<String>>();
	
	public static XSSFWorkbook workBook;
	
	public static XSSFSheet sheet;
	
	public static String filepath = "src/main/resources/javaScriptErrorAndMixedContentLogs.xlsx";
	
	public static FileOutputStream webdata;

	public static void deleteCreatedApps(WebDriver driver) throws IOException, InterruptedException {
		String url = PropertyUtils.getProperty("app.url");
		String filepath = "src/main/resources/appNamesList.xlsx";
		String sheetName = "AppNameList";
		FileInputStream file = new FileInputStream(filepath);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		int rowTotal = sheet.getLastRowNum();
		if ((rowTotal > 0) || (sheet.getPhysicalNumberOfRows() > 0)) {
			rowTotal++;
		}		
		if(rowTotal>=2){
		driver.get(url);
		LoginPage login = new LoginPage(driver);
		for (int i = 1; i <rowTotal; i++) {
			Row row = sheet.getRow(i);
			String appName = row.getCell(0).getStringCellValue();
			String userName = row.getCell(1).getStringCellValue();
			String password = row.getCell(2).getStringCellValue();
			login.gotoLogin();
			login.typeusername(userName);
			login.typepassword(password);
			login.Clickonloginbutton();
			DashboardPage dashboardPage = new DashboardPage(driver);
			CreateAppPage createAppPage = dashboardPage.clickApp();
			createAppPage.deleteEachInstanceOfApp(appName);
			createAppPage.retireEachInstanceOfApp(appName);
			createAppPage.clickLogOutButton();
		}
		}
	}

	public static void enterAppNameInList(String appName, String userName, String password) {
		List<String> appDataList = new ArrayList<>();
		appDataList.add(0,appName);
		appDataList.add(1,userName);
		appDataList.add(2,password);
		appNameList.add(appDataList);
	}

	public static void enterJavaScriptLogs(String email, String url, String jsLogs) {
		List<String> jsLogList = new ArrayList<>();
		jsLogList.add(0,email);
		jsLogList.add(1,url);
		jsLogList.add(2,jsLogs);
		javaScriptLogs.add(jsLogList);
	}
	
	public static void enterMixedContentLogs(String email, String url, String jsLogs) {
		List<String> jsMCLogList = new ArrayList<>();
		jsMCLogList.add(0,email);
		jsMCLogList.add(1,url);
		jsMCLogList.add(2,jsLogs);
		MixedContentLogs.add(jsMCLogList);
		
	}

	public static void enterURL(String url, String responseCode) {
		List<String> urlList = new ArrayList<>();
		urlList.add(0,url);
		urlList.add(1,responseCode);
		dataURL.add(urlList);
	}

	public static void storeAppNamesInExcel() {
		int index = 0;
		String filepath = "src/main/resources/appNamesList.xlsx";
		String sheetName = "AppNameList";
		try {
			XSSFWorkbook workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet(sheetName);
			sheet = workBook.getSheet(sheetName);
			FileOutputStream webdata = new FileOutputStream(filepath);
			XSSFRow row = sheet.createRow(index);
			row.createCell(0).setCellValue("App Name");
			row.createCell(1).setCellValue("Username");
			row.createCell(2).setCellValue("Password");
			for(List<String> list : appNameList ){
				index++;
				row = sheet.createRow(index);
				row.createCell(0).setCellValue(list.get(0));;
				row.createCell(1).setCellValue(list.get(1));
				row.createCell(2).setCellValue(list.get(2));
			}
			workBook.write(webdata);
			webdata.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeDataFromExcel() {
		int index = 0;
		String filepath = "src/main/resources/urlList.xlsx";
		String sheetName = "URL_LIST";
		try {
			XSSFWorkbook workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet(sheetName);
			sheet = workBook.getSheet(sheetName);
			FileOutputStream webdata = new FileOutputStream(filepath);
			XSSFRow row = sheet.createRow(index);
			row.createCell(0).setCellValue("URL");
			row.createCell(1).setCellValue("Response Code");
			for(List<String> list : dataURL ){
				index++;
				row = sheet.createRow(index);
				row.createCell(0).setCellValue(list.get(0));
				row.createCell(1).setCellValue(list.get(1));
			}
			workBook.write(webdata);
			webdata.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeJSLogsToExcel() throws IOException {
		int index = 0;
		String sheetName = "JS_LOGS";
		try {
			workBook = new XSSFWorkbook();
			sheet = workBook.createSheet(sheetName);
			sheet = workBook.getSheet(sheetName);
			webdata = new FileOutputStream(filepath);
			XSSFRow row = sheet.createRow(index);
			row.createCell(0).setCellValue("User Email");
			row.createCell(1).setCellValue("URL");
			row.createCell(2).setCellValue("Javascript Logs");
			for (List<String> list : javaScriptLogs) {
				if (!list.contains("Mixed Content:")) {
					index++;
					row = sheet.createRow(index);
					row.createCell(0).setCellValue(list.get(0));
					row.createCell(1).setCellValue(list.get(1));
					row.createCell(2).setCellValue(list.get(2));
				}
			}

			index = 0;
			String sheetName1 = "JS_MIXEDCONTENT_LOGS";
			try {
				sheet = workBook.createSheet(sheetName1);
				sheet = workBook.getSheet(sheetName1);
				webdata = new FileOutputStream(filepath);
				row = sheet.createRow(index);
				row.createCell(0).setCellValue("User Email");
				row.createCell(1).setCellValue("URL");
				row.createCell(2).setCellValue("Javascript Mixed Content Logs");
				for (List<String> list : MixedContentLogs) {
					if (!list.contains("Error Message in Console:")) {
						index++;
						row = sheet.createRow(index);
						row.createCell(0).setCellValue(list.get(0));
						row.createCell(1).setCellValue(list.get(1));
						row.createCell(2).setCellValue(list.get(2));

					}
				}

			} catch (IOException e) {
				e.printStackTrace();

			}
		} finally {
			workBook.write(webdata);
			webdata.close();

		}

	}

}
