package com.canvas.qa.model;

import java.util.HashMap;
import java.util.Map;

public class Test {

	private String description;
	private String env;
	private int failCount = 0;
	private String methodName;
	private Map<String, String> parameters = new HashMap<>();
	private int successCount = 0;
	private String testId;

	public String getDescription() {
		return description;
	}

	public String getEnv() {
		return env;
	}

	public int getFailCount() {
		return failCount;
	}

	public String getMethodName() {
		return methodName;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public String getTestId() {
		return testId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

}
