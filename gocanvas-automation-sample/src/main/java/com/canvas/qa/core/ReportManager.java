package com.canvas.qa.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.canvas.qa.model.Result;
import com.canvas.qa.test.BrowserLaunchTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReportManager extends BrowserLaunchTest {

	public static int failed = 0;
	public static String failures = "";
	public static int passed = 0;
	private static List<Result> results = new ArrayList<Result>();
	private static List<Result> resultsNew = new ArrayList<Result>();
	public static int testFailed = 0;
	public static int testPassed = 0;

	/**
	 * This method reads Result DTO and writes it into csv File.
	 */

	public static void generateCSV() {
		try {
			Set<String> totalTest = new HashSet<String>();
			int finalTestSize = 0;
			File csvFile = new File("src/main/resources/report_output.csv");
			FileWriter fileWriter = new FileWriter(csvFile);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			for (Result result : results) {
				totalTest.add(result.getTestId());
			}
			finalTestSize = totalTest.size();
			for (Result result : results) {
				writer.write(result.getCsvData());
				writer.newLine();
				if (result.getStatus() != null && !"".equals(result.getStatus())
						&& result.getStatus().equalsIgnoreCase("PASS"))
					passed = passed + 1;
				else if (result.getStatus() != null && !"".equals(result.getStatus())
						&& result.getStatus().equalsIgnoreCase("FAILED")) {
					failed = failed + 1;
					if (failures == null) {
						failures = result.getTestId() + "\t" + result.getFormattedDescription() + "\n";
					} else {
						failures += (result.getTestId() + "\t" + result.getFormattedDescription() + "\n");
					}
					boolean fails = false;
					for (String testId : totalTest) {
						if (testId.equalsIgnoreCase(result.getTestId())
								&& result.getStatus().equalsIgnoreCase("FAILED")) {
							fails = true;
							testFailed++;
						}
					}
					if (fails)
						totalTest.remove(result.getTestId());
				}
			}
			testPassed = finalTestSize - testFailed;
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static void generateCSVNew() {
		try {
			Set<String> totalTest = new HashSet<String>();
			int finalTestSize = 0;
			File csvFile = new File("src/main/resources/report_outputNew.csv");
			FileWriter fileWriter = new FileWriter(csvFile);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			for (Result result : resultsNew) {
				totalTest.add(result.getTestId());
			}
			finalTestSize = totalTest.size();
			for (Result result : resultsNew) {
				writer.write(result.getNewCsvData());
				writer.newLine();
				if (result.getStatus() != null && !"".equals(result.getStatus())
						&& result.getStatus().equalsIgnoreCase("PASS"))
					passed = passed + 1;
				else if (result.getStatus() != null && !"".equals(result.getStatus())
						&& result.getStatus().equalsIgnoreCase("FAILED")) {
					failed = failed + 1;
					if (failures == null) {
						failures = result.getTestId() + "\t" + result.getFormattedDescription() + "\n";
					} else {
						failures += (result.getTestId() + "\t" + result.getFormattedDescription() + "\n");
					}
					boolean fails = false;
					for (String testId : totalTest) {
						if (testId.equalsIgnoreCase(result.getTestId())
								&& result.getStatus().equalsIgnoreCase("FAILED")) {
							fails = true;
							testFailed++;
						}
					}
					if (fails)
						totalTest.remove(result.getTestId());
				}
			}
			testPassed = finalTestSize - testFailed;
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param testId
	 * @param status
	 * @param description
	 *            This method logs and writes into report and stores testId,
	 *            status and description into Result DTO.
	 */
	public static void log(String testId, LogStatus status, String description) {
		test.log(status, description);
		Result result = new Result();
		result.setTestId(testId);
		result.setStatus(LogStatus.PASS.equals(status) ? "PASS" : "FAILED");
		result.setDescription(description);
		results.add(result);
	}

	public static void lognew(String testId, String testCaseDescription, LogStatus status, String testStepDescription) {
		test.log(status, testStepDescription);
		Result result = new Result();
		result.setTestId(testId);
		result.setTestDescription(testCaseDescription);
		result.setStatus(LogStatus.PASS.equals(status) ? "PASS" : "FAILED");
		result.setDescription(testStepDescription);
		resultsNew.add(result);
	}

}
