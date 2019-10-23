package com.canvas.qa.test;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.test.BETAQRT.CloseBrowserTest;

@Test
public class CommonTest {

	@AfterTest
	public void closeBrowser() {
		CloseBrowserTest cbt = new CloseBrowserTest();
		cbt.closeBrowser();
	}

	@Test
	@Parameters({ "browser" })
	public void openBrowser(String browser) throws IOException {
		BrowserLaunchTest blt = new BrowserLaunchTest();
		blt.launchBrowser(browser);
	}

	@Test
	public void startTest(ITestContext testContext) {
		/*
		 * ExtentReports extent = ReportManager.getExtent(); ExtentTest logger =
		 * extent.startTest(testContext.getName());
		 * ReportManager.setExtentTest(logger);
		 */
	}
}
