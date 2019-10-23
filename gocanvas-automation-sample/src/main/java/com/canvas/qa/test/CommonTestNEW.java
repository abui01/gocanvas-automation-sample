package com.canvas.qa.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.test.BETAQRT.CloseBrowserTest;

@Test
public class CommonTestNEW {

	@AfterTest
	public void closeBrowser() {
		CloseBrowserTest cbt = new CloseBrowserTest();
		cbt.closeBrowser();
	}

	@BeforeTest
	@Parameters({ "browser" })
	public void startTest(String browser, ITestContext testContext) throws ClientProtocolException, IOException {
		/*
		 * ExtentReports extent = ReportManager.getExtent(); ExtentTest logger =
		 * extent.startTest(testContext.getName());
		 * ReportManager.setExtentTest(logger);
		 */
		BrowserLaunchTest blt = new BrowserLaunchTest();
		blt.launchBrowser(browser);
	}
}
