package com.canvas.qa.test.BETAQRT;

import com.canvas.qa.pages.apps.CreateAppCommonPage;
import com.canvas.qa.test.BrowserLaunchTest;

public class CreateAppCommonTest extends BrowserLaunchTest

{
	public boolean createApp() throws InterruptedException {
		try {
			CreateAppCommonPage createApp = new CreateAppCommonPage(driver);
			createApp.createApp();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
