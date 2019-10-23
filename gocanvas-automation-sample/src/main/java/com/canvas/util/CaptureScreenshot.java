package com.canvas.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CaptureScreenshot {

	public static String CaptureScreen(WebDriver driver) {
		TakesScreenshot newScreen = (TakesScreenshot) driver;
		String scnShot = newScreen.getScreenshotAs(OutputType.BASE64);
		return "data:image/jpg;base64, " + scnShot;
	}

	public static String fn_captureScreenshot(WebDriver driver, String screenshotName, String testCaseID)
			throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String dest = System.getProperty("user.dir") + "/src/main/resources/Screenshots/" + testCaseID + "_"
				+ screenshotName + "" + timeStamp + ".png";
		File destination = new File(dest);
		FileUtils.copyFile(src, destination);
		return destination.getAbsolutePath();
	}
}
