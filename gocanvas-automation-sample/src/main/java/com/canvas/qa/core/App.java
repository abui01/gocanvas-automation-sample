package com.canvas.qa.core;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

import com.canvas.util.PropertyUtils;

/**
 * This class executes testng programmatically using different configurations
 * based on main() method arguments
 *
 *
 *
 */
public class App {
	public static void main(String[] args) {
		String env = "beta";
		String driverType = "CHROME";

		if (args.length > 0) {
			env = args[0];
			PropertyUtils.init(env);
			PropertyUtils.setProperty("env", env);
		} else {
			PropertyUtils.init(env);
		}

		if (args.length > 1) {
			driverType = args[1].trim().toUpperCase();
			PropertyUtils.setProperty("driver.type", driverType);
		}
		List<String> testSuites = new ArrayList<String>();
		testSuites.add("testng_by_id.xml");
		TestNG testNG = new TestNG();
		testNG.setTestSuites(testSuites);
		testNG.setOutputDirectory("test-ouput");
		testNG.run();
		if (testNG.hasFailure()) {
			System.exit(1);
		}
	}
}
