package com.canvas.qa.test.BETAQRT;

import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.WorkflowPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

@Test
public class WorkflowManager1Test extends BrowserLaunchTest {

	@Parameters({ "step", "testdescription" })
	public void postconditions(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		WorkflowPage workflowLink = new WorkflowPage(driver);

		workflowLink.checkWorkflowByID(parameters.get("testWorkflowID"));
		workflowLink.reassignClick();
		workflowLink.selectReassignUser(parameters.get("postconditionsUser"));
		workflowLink.reassignSubmit();
		ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
				"Step " + step + ": Post Conditions");
	}

	@Parameters({ "step", "testdescription" })
	public void reassignWorkflow(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		WorkflowPage workflowLink = new WorkflowPage(driver);

		workflowLink.selectReassignUser(parameters.get("step5user"));
		workflowLink.reassignSubmit();

		workflowLink.advancedSearchClick();
		String app = parameters.get("testApp");
		// int index = Integer.parseInt(app);
		workflowLink.selectApp(app);
		workflowLink.selectStatusnew(parameters.get("assignedStatus"));
		workflowLink.searchSubmit();

		if (workflowLink.verifyReassignedWorkflow(parameters.get("testWorkflowID"), parameters.get("step5user"))) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ":  Workflow correctly reassigned");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ":  Workflow correctly reassigned");
		}
		org.testng.Assert.assertTrue(
				workflowLink.verifyReassignedWorkflow(parameters.get("testWorkflowID"), parameters.get("step5user")));
	}

	@Parameters({ "step", "testdescription" })
	public void unassignWorkflow(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		WorkflowPage workflowLink = new WorkflowPage(driver);
		workflowLink.advancedSearchClick();
		//customWait(5); //already has 10 sec customWait on page class
		String app = parameters.get("testApp");
		// int index = Integer.parseInt(app);
		workflowLink.selectApp(app);
		workflowLink.selectStatusnew2(parameters.get("assignedStatus"));
		workflowLink.searchSubmit();

		workflowLink.checkWorkflowByID(parameters.get("testWorkflowID"));
		workflowLink.unassignClick();

		workflowLink.advancedSearchClick();
		//customWait(5); //already has 10 sec customWait on page class
		workflowLink.selectApp(app);
		workflowLink.selectStatusnew2(parameters.get("unassignedStatus"));
		workflowLink.searchSubmit();

		if (workflowLink.verifyUnassignSearch(parameters.get("testWorkflowID"))) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ":  Unassigned Workflow '!' appears");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ":  Unassigned Workflow '!' appears");
		}
		org.testng.Assert.assertTrue(workflowLink.verifyUnassignSearch(parameters.get("testWorkflowID")));
	}

	@Parameters({ "step", "testdescription" })
	public void verifyStatusDDL(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;

		try {
			loginTest.verifyValidLogin1(step, testdescription, parameters.get("admin_username"),
					parameters.get("admin_password"), testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}
		i += 3;

		WorkflowPage workflowLink = new WorkflowPage(driver);
		workflowLink.selectDepart(parameters.get("step1depart"));

		workflowLink.workflowButtonClick();

		workflowLink.advancedSearchClick();
		String app = parameters.get("testApp");
		// int index = Integer.parseInt(app);
		workflowLink.selectApp(app);
		String[] statuses = new String[20];
		statuses = parameters.get("statuses").split(";");
		if (workflowLink.verifyStatuses(statuses)) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": All Workflow Statuses in DDL");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": All Workflow Statuses in DDL");
		}
		org.testng.Assert.assertTrue(workflowLink.verifyStatuses(statuses));
	}

	@Parameters({ "step", "testdescription" })
	public void verifyStatusSearch(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		WorkflowPage workflowLink = new WorkflowPage(driver);
		workflowLink.selectDepart(parameters.get("step1depart"));

		workflowLink.workflowButtonClick();
		workflowLink.advancedSearchClick();
		String app = parameters.get("testApp");
		// int index = Integer.parseInt(app);
		workflowLink.selectApp(app);
		workflowLink.selectStatusnew(parameters.get("step2status"));
		workflowLink.searchSubmit();

		if (workflowLink.verifyStatusSearch(parameters.get("step2status"))) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ":  Search by Status Correct");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ":  Search by Status Correct");
		}
		org.testng.Assert.assertTrue(workflowLink.verifyStatusSearch(parameters.get("step2status")));
	}

	@Parameters({ "step", "testdescription" })
	public void verifyToValues(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		WorkflowPage workflowLink = new WorkflowPage(driver);

		workflowLink.checkWorkflowByID(parameters.get("testWorkflowID"));
		workflowLink.reassignClick();

		String[] users = new String[20];
		users = parameters.get("users").split(";");
		if (workflowLink.verifyReassignDDL(users)) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": All Workflow Statuses in DDL");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": All Workflow Statuses in DDL");
		}
		org.testng.Assert.assertTrue(workflowLink.verifyReassignDDL(users));
	}

}
