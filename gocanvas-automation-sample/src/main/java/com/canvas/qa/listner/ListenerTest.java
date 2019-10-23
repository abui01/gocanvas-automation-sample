package com.canvas.qa.listner;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.CaptureScreenshot;
import com.relevantcodes.extentreports.LogStatus;

public class ListenerTest extends BrowserLaunchTest implements ITestListener {

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailure(ITestResult result) {
		try {
			String path = CaptureScreenshot.fn_captureScreenshot(driver, result.getName(),
					result.getTestContext().getName());
			Reporter.setCurrentTestResult(result);
			test.log(LogStatus.FAIL,
					"Snapshot below: " + test.addScreenCapture(CaptureScreenshot.CaptureScreen(driver)));
			Reporter.log(
					String.format("SCREENSHOT: <a href='%s'><img src=\"file://" + path + "\" alt=\"\"/></a>", path));
			Reporter.setCurrentTestResult(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub

	}

}
