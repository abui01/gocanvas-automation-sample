
package com.canvas.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import org.testng.ITestContext;

import com.canvas.qa.model.Result;
import com.canvas.qa.model.Test;
import com.canvas.qa.test.BrowserLaunchTest;

public class FileReaderUtil extends BrowserLaunchTest {

	public static Map<String, List<Result>> resultMap = new ConcurrentHashMap<String, List<Result>>();

	public static Map<String, Test> testMap = new ConcurrentHashMap<String, Test>();

	public static Map<String, String> getTestParameters(ITestContext testContext) {
		return testMap.get(testContext.getCurrentXmlTest().getName()).getParameters();
	}

	public List<Test> getTestDataList(String fileName) {

		List<Test> dataList = new ArrayList<Test>();
		try {
			Scanner scanner = new Scanner(new File(fileName));
			while (scanner.hasNext()) {

				String rowData = scanner.nextLine();
				Test test = parseTestData(rowData);

				dataList.add(test);
				testMap.put(test.getTestId(), test);
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}

	private Test parseTestData(String rowData) {

		Test test = new Test();
		try {
			String columns[] = rowData.split(",", -1);
			test.setTestId(columns[0].trim());
			int columnCount = columns.length;
			if (columnCount > 1) {
				test.setDescription(columns[1]);
			}
			Map<String, String> defaultParameters = YamlReader.getYmlMap(test.getTestId());
			test.getParameters().putAll(defaultParameters);
			if (columnCount > 2) {
				for (int i = 2; i < columns.length; i++) {
					String parameters[] = columns[i].split(":", -1);
					test.getParameters().put(parameters[0], parameters[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Yml data missing for testacse: " + test.getTestId());
			System.exit(1);
		}
		return test;
	}
}
