package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.dispatch.DispatchPage;
import com.canvas.qa.pages.dispatch.EditDispatchPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

@Test
public class EditDispatch extends BrowserLaunchTest

{
	@Parameters({ "step", "testdescription" })
	public void updatesAppear(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 3;
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		loginTest.verifyValidLogin1(step, testdescription, parameters.get("designeruser"),
				parameters.get("designerpassword"), testContext);

		EditDispatchPage editDispatch = new EditDispatchPage(driver);
		DispatchPage dispatchPage = new DispatchPage(driver);
		DashboardPage dashboardPage = new DashboardPage(driver);
		dispatchPage = dashboardPage.clickDispatch();
		dispatchPage.clickAdvanceSearchButton();
		dispatchPage.enterTextDispatchfield(parameters.get("dispatchname"));
		dispatchPage.clickSearchButton();

		editDispatch.editDispatchClick();

		editDispatch.profileBtnClick(parameters.get("dispatchname"), parameters.get("dispatchdesc"),
				parameters.get("assignto"), parameters.get("dispatchtype"), parameters.get("newshorttext"),
				parameters.get("newlongtext"), parameters.get("integer"), parameters.get("decimal"),
				parameters.get("newbar"), parameters.get("newmsn"), parameters.get("fieldloopnew"),
				parameters.get("dropDown"), parameters.get("radioOption"), parameters.get("field2Loop"));

		String updatetext = parameters.get("dispatchdesc");
		String successText = editDispatch.SaveUpdatedDispatchData();
		if (successText.contains(updatetext)) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Dispatch was succesfully updated.");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Error in updating dispatch");
		}
		org.testng.Assert.assertTrue(successText.contains(updatetext), "Error in updating dispatch");
	}

	@Parameters({ "step", "testdescription" })
	public void verifyUpdatedDispatchData(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		EditDispatchPage seachvalidlink = new EditDispatchPage(driver);

		String updatetext = parameters.get("updated_data");
		String abc = seachvalidlink.checkForupdateddispatchdata(testContext);
		if (abc.equals(updatetext)) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ":Updated values of dispatch Are : " + abc);
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + "Some issue in updating dispatch values");

		}
		org.testng.Assert.assertTrue(abc.equals(updatetext), "Some issue in updating dispatch values");
	}

}
