package com.canvas.qa.model;

public class Result {

	private String description;
	private String duration;
	private String errorMessage;
	private String status;
	private String testCaseDescription;
	private String testId;
	private String testType;

	public String getCsvData() {
		return testId + "," + getFormattedDescription() + "," + status;
	}

	public String getDescription() {
		return description;
	}

	public String getDuration() {
		return duration;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getFormattedDescription() {
		if (description != null) {
			return description.replaceAll(",", ":");
		} else {
			return description;
		}
	}

	public String getNewCsvData() {
		return testId + "," + getTestCaseDescription() + "," + getFormattedDescription() + "," + status;
	}

	public String getStatus() {
		return status;
	}

	public String getTestCaseDescription() {
		if (testCaseDescription != null) {
			return testCaseDescription.replaceAll(",", ":");
		} else {
			return testCaseDescription;
		}
	}

	public String getTestId() {
		return testId;
	}

	public String getTestType() {
		return testType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTestDescription(String description) {
		this.testCaseDescription = description;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	@Override
	public String toString() {
		return "Result [testId=" + testId + ", description=" + description + ", duration=" + duration + ", testType="
				+ testType + ", status=" + status + ", errorMessage=" + errorMessage + "]";
	}

}
