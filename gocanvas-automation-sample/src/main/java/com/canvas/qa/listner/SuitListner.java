package com.canvas.qa.listner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.canvas.qa.model.Test;
import com.canvas.util.FileReaderUtil;
import com.canvas.util.PropertyUtils;

public class SuitListner implements IAlterSuiteListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.IAlterSuiteListener#alter(java.util.List) This method
	 * reads input CSV and sets only those testId's which needs to be executed.
	 * TestId's present in input CSV only gets executed irrespective of the Test
	 * Cases present in TestNg_by_id.xml
	 */
	@Override
	public void alter(List<XmlSuite> suites) {

		String env = PropertyUtils.getProperty("env");
		String sourceFile = "src/main/resources/" + env + "_smoke.csv";
		XmlSuite xmlSuite = suites.get(0);
		List<XmlTest> tests = xmlSuite.getTests();

		Map<String, XmlTest> testMapping = new HashMap<>();
		for (XmlTest xmlTest : tests) {
			testMapping.put(xmlTest.getName(), xmlTest);
		}
		tests.clear();
		try {
			FileReaderUtil readerUtil = new FileReaderUtil();
			List<Test> testList = readerUtil.getTestDataList(sourceFile);
			for (Test test : testList) {
				String testCaseId = test.getTestId();
				if (testMapping.containsKey(testCaseId)) {
					XmlTest xmlTest = testMapping.get(testCaseId);
					xmlTest.getAllParameters().putAll(test.getParameters());
					tests.add(xmlTest);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
