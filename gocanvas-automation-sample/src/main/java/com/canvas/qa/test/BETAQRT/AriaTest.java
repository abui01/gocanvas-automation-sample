package com.canvas.qa.test.BETAQRT;

import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.AriaPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

/**
 * @author taukeer.ahmad
 *
 */
@Test
public class AriaTest extends BrowserLaunchTest

{
	@Parameters({ "step" })
	public void EditFirstName(String step, ITestContext testContext) throws InterruptedException {

		CanvasLoginTest loginTest = new CanvasLoginTest();

		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		try {
			loginTest.verifyValidLogin1(step, parameters.get("company_admin"), parameters.get("company_password"),
					testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}

		AriaPage editaccountdetail = new AriaPage(driver);

		editaccountdetail.EditFirstName();
		editaccountdetail.Saveeditvalue(parameters.get("first_name"));
	}

}
