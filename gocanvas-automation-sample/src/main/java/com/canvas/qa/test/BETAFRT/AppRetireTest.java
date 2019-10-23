package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class AppRetireTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void apiTest(String testDescription, ITestContext testContext) throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as: " + parameters.get("username"), testContext, testDescription);
			/*
			 * String url =
			 * "https://betaqrt.gocanvas.com/apiv2/forms.xml?username=appmultiplescreens@yopmail.com&password=canvas/54909/retire";
			 * HttpsURLConnection huc = (HttpsURLConnection)(new
			 * URL(url).openConnection()); huc.setRequestMethod("POST");
			 * huc.connect(); int respCode = huc.getResponseCode(); String
			 * result = " Response code is: " + String.valueOf(respCode);
			 * System.out.println(result);
			 */
			/*
			 * js = '''var xhr = new XMLHttpRequest(); xhr.open('POST',
			 * 'http://httpbin.org/post', true);
			 * xhr.setRequestHeader('Content-type',
			 * 'application/x-www-form-urlencoded'); xhr.onload = function () {
			 * alert(this.responseText); };
			 * 
			 * xhr.send('login=test&password=test');'''
			 * 
			 * JavascriptExecutor js = (JavascriptExecutor) driver;
			 * js.executeScript("arguments[0].click();", element);
			 */
			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
