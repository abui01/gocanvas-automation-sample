package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.HomePage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.canvas.util.PropertyUtils;

public class AnalyticsTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription", "rallylink" })
	public void analyticsPageRerouteVerificationTest(String testDescription, String rallyLink, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			HomePage homePage = new HomePage(driver);
			homePage.tryItFreeButtonClick();
			List<WebElement> scripts = driver.findElements(By.tagName("script"));
			List<String> actualList = new ArrayList<String>();

			String googleAnalytics1 = "//<![CDATA[(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*newDate();a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','//www.google-analytics.com/analytics.js','ga');ga('set','anonymizeIp',true);ga('create',";
			String googleAnalytics2 = "\"" + "UA-8721026-1" + "\""
					+ ",'auto');ga('require','displayfeatures');varemailRegEx=/(";
			String googleAnalytics3 = "[a-zA-Z0-9_.+-]+" + "\\" + "@[a-zA-Z0-9-]+" + "\\" + "." + "[a-zA-Z0-9-.]+)/gi;";
			String googleAnalytics4 = (parameters.get("google_analytics6") + parameters.get("google_analytics7")
			+ parameters.get("google_analytics8") + parameters.get("google_analytics9")
			+ parameters.get("google_analytics10")).replaceAll("\t", "").replace("\r", "").replaceAll("\n", "")
					.replaceAll(" ", "").trim();
			String googleAnalytics2Beta5 = "\"" + "UA-8721026-3" + "\""
					+ ",'auto');ga('require','displayfeatures');varemailRegEx=/(";

			String googleAnalytics;
			if(PropertyUtils.getProperty("app.url").contains("beta.gocanvas")){
				googleAnalytics = googleAnalytics1 + googleAnalytics2Beta5 + googleAnalytics3 + googleAnalytics4;
			}else{
				googleAnalytics = googleAnalytics1 + googleAnalytics2 + googleAnalytics3 + googleAnalytics4;
			}
			String kissMetrics = parameters.get("kiss_metrics").replaceAll("\t", "").replace("\r", "")
					.replaceAll("\n", "").replaceAll(" ", "").trim();
			String googleTagManager = parameters.get("google_tag_manager").replaceAll("\t", "").replace("\r", "")
					.replaceAll("\n", "").replaceAll(" ", "").trim();

			for (int i = 0; i < scripts.size(); i++) {
				String htmlCode = (String) ((JavascriptExecutor) driver)
						.executeScript("return arguments[0].textContent;", scripts.get(i));
				htmlCode = htmlCode.replaceAll("\t", "").replace("\r", "").replaceAll("\n", "").replaceAll(" ", "")
						.trim();
				actualList.add(htmlCode);
			}

			boolean status = actualList.contains(googleAnalytics);
			reportLog(status, testContext.getName(), "Verify Google Analytics data is present in Sign Up Page.", "1.0",
					"Verified Google Analytics data is present in Sign Up Page.");
			org.testng.Assert.assertTrue(status);

			status = actualList.contains(kissMetrics);
			reportLog(status, testContext.getName(), "Verify Kiss Metrics data is present in Sign Up Page.", "2.0",
					"Verified Kiss Metrics data is present in Sign Up Page.");
			org.testng.Assert.assertTrue(status);

			status = actualList.contains(googleTagManager);
			reportLog(status, testContext.getName(), "Verify Google Tag Manager data is present in Sign Up Page.",
					"3.0", "Verified Google Tag Manager data is present in Sign Up Page.");
			org.testng.Assert.assertTrue(status);

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
