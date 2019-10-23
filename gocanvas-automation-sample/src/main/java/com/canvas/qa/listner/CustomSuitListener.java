package com.canvas.qa.listner;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import com.canvas.qa.core.ReportManager;
import com.canvas.qa.core.UrlListGather;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.PropertyUtils;

public class CustomSuitListener implements ISuiteListener {
	/**
	 * This method is invoked after the SuiteRunner has run all the test suites.
	 */
	@Override
	public void onFinish(ISuite suite) {
		ReportManager.generateCSV();
		ReportManager.generateCSVNew();
		try {
			UrlListGather.writeJSLogsToExcel();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		UrlListGather.storeAppNamesInExcel();
		BrowserLaunchTest blt = new BrowserLaunchTest();
		try {			
			blt.launchBrowser(PropertyUtils.getProperty("driver.type"));
			UrlListGather.deleteCreatedApps(blt.getDriver());
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		 catch (InterruptedException e1) {
			 e1.printStackTrace();
		}
		blt.getDriver().quit();
		blt.getThreadDriver().remove();		
	}

	@Override
	public void onStart(ISuite suite) {

	}
}
