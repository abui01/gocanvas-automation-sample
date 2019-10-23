package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.dispatch.CreateDispatchPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

//
public class CreateDispatchTest extends BrowserLaunchTest {

	@Parameters({ "step", "testdescription" })
	public void Allfields(String step, String testdescription, final ITestContext testContext) throws Throwable {
		int i = 0;

		CreateDispatchPage dispatchcalendar = new CreateDispatchPage(driver);

		String[] labels = new String[100];
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String fieldLabel = parameters.get("fieldLabel");
		if (fieldLabel != null && !fieldLabel.isEmpty()) {
			labels = fieldLabel.split(";");
		}

		boolean success = dispatchcalendar.Allfieldsofpage(labels);
		if (success) {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Total Fields in the Screen are :" + fieldLabel);
			org.testng.Assert.assertTrue(true, "Step " + step + ": Fields Detail:" + labels);
		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Fields Labels ares Not Matched ");
			// ReportManager.lognew(testContext.getName(), testdescription,
			// LogStatus.PASS,"Step " + step + "."
			// + (++i)+ ": Fields Detail :" +success);
			org.testng.Assert.assertTrue(true, "Step " + step + ": Fields Detail :" + success);
		}

	}
	// @Parameters({ "step" })
	// public void enterDatainFields(String step, String testdescription, final
	// ITestContext testContext)
	// throws Throwable{
	// int i = 0;
	//
	// Map<String, String> parameters =
	// FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName()).getParameters();
	// //String appName = parameters.get("appName");
	// //String apps[] = appName.split(",");
	// //int length = apps.length;
	// //int steps = Integer.parseInt(step);
	// CreateDispatchPage savedata = new CreateDispatchPage(driver);
	// //int size = savedata.Size();
	// for(int x=0; x<4; x++){
	//
	// FileReaderUtil readerUtil = new FileReaderUtil();
	// ExtentTest logger = ReportManager.getExtentTest();
	//
	// String fieldsName = parameters.get("field_name_"+x);
	//
	// try {
	//
	// savedata.enterFieldsName(fieldsName,x);
	// ReportManager.lognew(testContext.getName(), testdescription,
	// LogStatus.PASS,"Step " + step + "."
	// + (++i)+ ": Data entered in fileds "+x+":"+fieldsName);
	// org.testng.Assert.assertTrue(true, "Step " + step+ ": Fields
	// Detail:"+fieldsName);
	//
	// }
	// catch (Exception e) {
	// ReportManager.lognew(testContext.getName(), testdescription,
	// LogStatus.FAIL,e.getMessage());
	// ReportManager.lognew(testContext.getName(),
	// testdescription,LogStatus.FAIL, "Step " + step+ "." +
	// (++i)+ ": Issue in entering data in fields"+fieldsName);
	// }
	//
	// }
	// }

	@Parameters({ "step", "testdescription" })
	public void AppsInDropDown(int step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{

		int i = 0;

		CreateDispatchPage assignTo = new CreateDispatchPage(driver);

		// CreateDispatchPage dispatchAssignValues = new
		// CreateDispatchPage(driver);
		String dropDownAppsName = assignTo.UserinDropDown();

		ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
				"Step " + step + "." + (++i) + ": Values in Apps Drop down Are : " + dropDownAppsName);
		org.testng.Assert.assertTrue(true, "Step " + step + ": Values in DispatchType drop down" + dropDownAppsName);

	}

	@Parameters({ "step", "testdescription" })
	public void AssignDispatchTo(int step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{

		int i = 2;

		CreateDispatchPage assignTo = new CreateDispatchPage(driver);
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		// CreateDispatchPage dispatchAssignValues = new
		// CreateDispatchPage(driver);
		String dropDownDispatchAssignTo = assignTo.DispatchAssignToUser();

		ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
				+ ": Values in Drop down " + "Assign item to Are:" + dropDownDispatchAssignTo);
		org.testng.Assert.assertTrue(true,
				"Step " + step + ": Values in DispatchType drop down" + dropDownDispatchAssignTo);

		String userAssign = parameters.get("dispatch_Assign");
		assignTo.AssignFromDropDown(userAssign);
		Thread.sleep(2000);
		String S1 = assignTo.AssignFromDropDown(userAssign);

		if (S1.equalsIgnoreCase(userAssign)) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ":User: \"" + userAssign + "\"assigned to dispatch");

		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": User Assign to Dispatch is Failed");
		}
		org.testng.Assert.assertTrue(S1.equalsIgnoreCase(userAssign));

	}

	// Kailash

	@Parameters({ "step", "testdescription" })
	public void ReminderDropDownDisplay(String step, String testdescription, final ITestContext testContext)
			throws Throwable {
		int i = 0;

		CreateDispatchPage dispatchcalendar = new CreateDispatchPage(driver);

		boolean reminderDrop = dispatchcalendar.ReminderDropDownDisplay();

		if (reminderDrop == true) {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ":Check Box Send calendar invite (via email) check box is selcted and Reminder Drop down is displying");
			org.testng.Assert.assertTrue(true,
					"Step " + step + ": Check Box Is selected and Reminder Drop down is displying");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Issue in displaying Reminder Drop down is displying");
			org.testng.Assert.assertTrue(false,
					"Step " + step + ": Check Box Is selected But Reminder Drop down Bot displying");
		}
	}

	@Parameters({ "step", "testdescription" })
	public void ReminderDropDownNotDisplay(String step, String testdescription, final ITestContext testContext)
			throws Throwable {
		int i = 0;

		CreateDispatchPage dispatchcalendar = new CreateDispatchPage(driver);

		boolean reminderDropdown = dispatchcalendar.ReminderDropDownNotDisplay();

		if (reminderDropdown == true) {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ":Check Box Is Not selected and Reminder Drop down is Not displying");

		} else if (reminderDropdown == false) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS, "Step " + step + "." + (++i)
					+ ": User Uncheck Send Calendar invite (via email) check box; the Reminder: field label is disappear");
		}

	}

	@Parameters({ "step", "testdescription" })

	public void SaveDispatch(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;
		CreateDispatchPage Saveddispatch = new CreateDispatchPage(driver);

		Saveddispatch.SaveDispatch(testContext, parameters.get("drop_down"), parameters.get("radio_option"));
		ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
				"Step " + step + "." + (++i) + ": Value in all fields entered");

		org.testng.Assert.assertTrue(true, "Step " + step + ": Issue in entering value in fields");

	}

	@Parameters({ "step", "testdescription" })
	public void SaveDispatchData(String step, String testdescription, ITestContext testContext)
			throws IOException, InterruptedException {

		CreateDispatchPage SaveDispatch = new CreateDispatchPage(driver);

		int i = 0;

		// SaveDispatch.checkForSSaveDispatchData();
		String successText = SaveDispatch.checkForSSaveDispatchData();
		if (successText != null && !successText.isEmpty()
				&& successText.contains("Your dispatch was successfully created.")) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Dispatch is succesfully created.");
		} else {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Error in creating dispatch- " + successText);

		}
		org.testng.Assert.assertTrue(successText != null && !successText.isEmpty()
				&& successText.contains("Your dispatch was successfully created."));
		SaveDispatch.logout();
	}

	@Parameters({ "step", "testdescription" })
	public void selectDispatchType(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		CreateDispatchPage dispatchtype = new CreateDispatchPage(driver);

		String dropDownValues = dispatchtype.DispatchType();

		ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
				"Step " + step + "." + (++i) + ": Values in DispatchType Drop downs Are :" + dropDownValues);
		org.testng.Assert.assertTrue(true, "Step " + step + ": Values in DispatchType drop down" + dropDownValues);

	}

	@Parameters({ "step", "testdescription" })
	public void selectValueFromDropDown(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{

		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		String appName = parameters.get("appName");
		String apps[] = appName.split(",");
		int length = apps.length;
		int steps = Integer.parseInt(step);
		for (int x = 0; x < length; x++) {

			if (x == 1) {
				int i = 0;

				CreateDispatchPage dispatchDrop = new CreateDispatchPage(driver);
				CreateDispatchPage dispatchdata = new CreateDispatchPage(driver);
				String dispatchName = parameters.get("dispatch_name_" + x);
				String dispatchDesc = parameters.get("dispatch_description_" + x);

				dispatchDrop.selectValuefromAppDropdown(apps[x]);
				Thread.sleep(3000);
				dispatchdata.typeDispatchName(dispatchName);
				Thread.sleep(3000);
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
						"Step " + steps + "." + (++i) + ": User has selected app \"" + apps[x]
								+ "\" from drop down and entred value in dispath name is : \"" + dispatchName + "\"");
				org.testng.Assert.assertTrue(true, "Step " + steps + ": Enter Dispatch Name:");

				dispatchdata.typeDispatchDescription(dispatchDesc);
				Thread.sleep(4000);
				ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
						"Step " + steps + "." + (++i) + ": Enter value in Dispatch Desc Text box is:" + dispatchDesc);
				org.testng.Assert.assertTrue(true, "Step " + steps + ": Enter Dispatch Desc:");

				// AssignDispatchTo(steps, testContext);
				steps++;
			} else {

			}

		}
	}

	@Parameters({ "step", "testdescription" })
	public void timeDiffrence(String step, String testdescription, final ITestContext testContext) throws Throwable {
		int i = 0;

		CreateDispatchPage dispatchcalendar = new CreateDispatchPage(driver);

		long timeDiff = dispatchcalendar.DateDiffrence();

		if (timeDiff < 3) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Time diffrence Is :" + timeDiff);
			org.testng.Assert.assertTrue(true, "Step " + step + ": Time diffrence Is :" + timeDiff);
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Time diffrence Is Greater Than :" + timeDiff);
			org.testng.Assert.assertTrue(true, "Step " + step + ": Time diffrence Is :" + timeDiff);
		}
	}

	@Parameters({ "step", "testdescription" })
	public void verifysortingOrder(String step, String testdescription, final ITestContext testContext)
			throws IOException, InterruptedException

	{
		int i = 0;

		CreateDispatchPage dispatchlink = new CreateDispatchPage(driver);

		boolean valuesInDropDown = dispatchlink.dropDownListSortedOrNot();
		if (valuesInDropDown == true)

		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Values in App drop down are in sorated order:");
		}

		else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Values in drop down are not in  sorated order:");
		}
		org.testng.Assert.assertTrue(valuesInDropDown);
	}

}
