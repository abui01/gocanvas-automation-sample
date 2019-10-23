package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.SubmissionAppsPage;
import com.canvas.qa.pages.YopmailPage;
import com.canvas.qa.pages.submissions.SubmissionPDFViewPage;
import com.canvas.qa.pages.submissions.SubmissionSearchPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class ViewGeneratedPDFLinkFromEmailTest extends BrowserLaunchTest {
	
	
	
	
	// QA-154 : Users able to view generated PDF link from e-mail
		@Test
		@Parameters({ "testdescription" })
		public void viewPDFLinkTest(String testDescription, ITestContext testContext)
				throws IOException, InterruptedException {
			Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);

			try {
				performLogin(1, parameters.get("username"), parameters.get("password"),
						"Login as the company admin: " + parameters.get("username"), testContext, testDescription);
				
				DashboardPage dashboardPage = new DashboardPage(driver);
				dashboardPage.clickSubmissions();
				SubmissionAppsPage submissionAppPage = new SubmissionAppsPage(driver);
				
				boolean status = submissionAppPage.isSubmissionDisplayed();
				reportLog(status, testContext.getName(), "Verify Submission for the app Beer Stock Requisition is displayed  ", "1.2.",
						"App Beer Stock Requisition is displayed ");
				org.testng.Assert.assertTrue(status);
				
				submissionAppPage.clickOnAppLink();
				SubmissionSearchPage submissionSearchPage = new SubmissionSearchPage(driver);
				status = submissionSearchPage.isSbmitterDisplayed(parameters.get("submitter"));
				
				reportLog(status, testContext.getName(), "Submitter for the app Beer Stock Requisition is displayed  ", "2.0.",
						"Submitter actual value matches the expected value: Sdet QA  ");
				org.testng.Assert.assertTrue(status);
				
				submissionSearchPage.clickOnQuickLinkEnvelope().clickOnResendEmailSubmission();
				status = submissionSearchPage.isSuccessfullyTextDisplayed();
				
				reportLog(status, testContext.getName(), "Submission email has been sent successfully  ", "2.1.",
						"The submission email has been sent successfully!");
				org.testng.Assert.assertTrue(status);
				
				submissionSearchPage.closeSubmissionPopUp();
				submissionAppPage.logout();
				
				String url = parameters.get("url");
				String userName = parameters.get("username");
				
				YopmailPage yopmailPage = new YopmailPage(driver);
				yopmailPage.yopmailPdfVerification(url, userName);
				
				
				status = yopmailPage.verifyGoCanvasMailerLogoIsDisplayed();
				reportLog(status, testContext.getName(),
						"Verify the Canvas - Beer_Stock_Requisition.pdf email is received (and includes the GoCanvas text/logo)",
						"3.0",
						"Verified the Canvas - Beer_Stock_Requisition.pdf Account email is received (and includes the GoCanvas text/logo)");
				org.testng.Assert.assertTrue(status);
				
				yopmailPage.clickOnPdf();
				driver.switchTo().defaultContent();
				yopmailPage.deleteMail();
				
				String emailTab = driver.getWindowHandle();
				for (String handle : driver.getWindowHandles()) {
					driver.switchTo().window(handle);
				}
				
			SubmissionPDFViewPage submissionPdfViewPage = new SubmissionPDFViewPage(driver);
			submissionPdfViewPage.verifyElementsInPdf(parameters.get("pdf_app_name"));

			status = submissionPdfViewPage.verifyElementsInPdf(parameters.get("pdf_app_name"));
			reportLog(status, testContext.getName(), "PDF Submission link is displaying the correct App name  ", "3.1.",
					"The PDF submission link is displaying the correct App name = Beer Stock Requisition ");
			org.testng.Assert.assertTrue(status);

			status = submissionPdfViewPage.verifyElementsInPdf(parameters.get("pdf_screen_title1"));
			reportLog(status, testContext.getName(), "PDF Submission link is displaying the correct Screen Title 1  ", "3.2.",
					"The PDF submission link is displaying the correct Screen Title 1 = STOCK REQUISITION ");
			org.testng.Assert.assertTrue(status);
			
			status = submissionPdfViewPage.verifyElementsInPdf(parameters.get("pdf_screen_title2"));
			reportLog(status, testContext.getName(), "PDF Submission link is displaying the correct Screen Title 2  ", "3.3.",
					"The PDF submission link is displaying the correct Screen Title 2 = REQUEST INFO ");
			org.testng.Assert.assertTrue(status);

			
			status = submissionPdfViewPage.verifyTryItFreeButtonIsDisplayed();
			reportLog(status, testContext.getName(), "PDF Submission link is displaying the Try It Free ", "4.0.",
					"Users are able to view the generated PDF from an e-mail account while being logged out of GoCanvas. ");
			org.testng.Assert.assertTrue(status);
				

				reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
			} catch (Exception e) {
				reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
				org.testng.Assert.assertTrue(false, e.getMessage());
			}

		}

}
