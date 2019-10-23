package com.canvas.qa.test.BETAQRT;

import com.canvas.qa.pages.CloseBrowserPage;
import com.canvas.qa.test.BrowserLaunchTest;

public class CloseBrowserTest extends BrowserLaunchTest {

	public void closeBrowser() {
		CloseBrowserPage closeBrowser = new CloseBrowserPage(driver);
		closeBrowser.CloseBrowser();
	}
}
