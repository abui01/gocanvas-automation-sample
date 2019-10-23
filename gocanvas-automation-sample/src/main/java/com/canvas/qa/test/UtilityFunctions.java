package com.canvas.qa.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.testng.ITestContext;

import com.canvas.qa.core.ReportManager;
import com.relevantcodes.extentreports.LogStatus;

public class UtilityFunctions {

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	/**
	 * This method will return the random int number between the 1 to the number you
	 * provide, example if you enter 10, the range will be 1 - 10, (include 1 and
	 * 10)
	 * 
	 * @param maxNum
	 * @return The number between 1 to manNum
	 */
	public static int randomNumber(int maxNum) {
		int min = 1;
		int max = maxNum;
		int range = max - min + 1;
		return (int) (Math.random() * range) + min;
	}

	public static int randomNumber(int minNum, int maxNum) {
		int max = maxNum;
		int range = max - minNum + 1;
		return (int) (Math.random() * range) + minNum;
	}

	/**
	 * @param file1path
	 * @param file2path
	 * @return
	 * @throws IOException
	 */
	public static int comparefiles(String file1path, String file2path) throws IOException {

		ArrayList<String> al1 = new ArrayList<String>();
		ArrayList<String> al2 = new ArrayList<String>();

		BufferedReader CSVFile1 = new BufferedReader(new FileReader(file1path));
		String dataRow1 = CSVFile1.readLine();
		while (dataRow1 != null) {
			String[] dataArray1 = dataRow1.split(",");
			for (String item1 : dataArray1) {
				al1.add(item1);
			}
			dataRow1 = CSVFile1.readLine(); // Read next line of data.
		}

		CSVFile1.close();

		BufferedReader CSVFile2 = new BufferedReader(new FileReader(file2path));
		String dataRow2 = CSVFile2.readLine();
		while (dataRow2 != null) {
			String[] dataArray2 = dataRow2.split(",");
			for (String item2 : dataArray2) {
				al2.add(item2);

			}
			dataRow2 = CSVFile2.readLine(); // Read next line of data.
		}
		CSVFile2.close();

		for (String bs : al2) {
			al1.remove(bs);
		}

		int size = al1.size();
		return size;

	}

	/***
	 * This method waits for user given seconds
	 * 
	 * @param inSeconds
	 */
	public void customWait(int inSeconds) {
		try {
			Thread.sleep(inSeconds * 1000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void deleteEmails(String user, String password) {
		try {
			Properties props = new Properties();
			props.put("mail.store.protocol", "imaps");
			Session session = Session.getInstance(props);
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", user, password);
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_WRITE);
			Message[] messages = emailFolder.getMessages();
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				message.setFlag(Flags.Flag.DELETED, true);
			}
			emailFolder.close(true);
			store.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void reportLog(boolean condition, String testId, String testStep, String testStepDescription) {
		if (condition) {
			ReportManager.lognew(testId, testStep, LogStatus.PASS, testStepDescription);
		} else {
			ReportManager.lognew(testId, testStep, LogStatus.FAIL, testStepDescription);
		}
	}

	public void reportLog(boolean condition, String testId, String testCaseDescription, String stepnumber,
			String testStepDescription) {
		if (condition) {
			ReportManager.lognew(testId, testCaseDescription, LogStatus.PASS,
					"Step " + stepnumber + " " + testStepDescription);
		} else {
			ReportManager.lognew(testId, testCaseDescription, LogStatus.FAIL,
					"Step " + stepnumber + " " + testStepDescription);
		}
	}

	public void reportLog(boolean condition, String testId, String testCaseDescription, String stepnumber,
			String testStepDescription, Exception e) throws Exception {

		condition = e != null ? condition : false;

		if (condition) {
			ReportManager.lognew(testId, testCaseDescription, LogStatus.PASS,
					"Step " + stepnumber + " " + testStepDescription);
		} else {
			ReportManager.lognew(testId, testCaseDescription, LogStatus.FAIL,
					"Step " + stepnumber + " " + testStepDescription);
		}

		throw e;
	}

	public void verifyDispatchValues(String stepNumber, String[] appExpectedList, ArrayList<String> appActualList,
			String testDescription, ITestContext testContext) {
		int counter = 1;
		for (int j = 0; j < appExpectedList.length; j++) {
			String step = stepNumber + counter;
			boolean status = appActualList.get(j).equals(appExpectedList[j]);
			reportLog(status, testContext.getName(), testDescription, step, " Actual value of Dispatch ID : "
					+ appActualList.get(j) + "  matches with the expected value : " + appExpectedList[j]);
			org.testng.Assert.assertTrue(status);
			counter = counter + 1;
		}
	}

	public void verifyDropdownMultiOptionValues(String stepNumber, ArrayList<String> appActualList,
			String[] appExpectedList, String testDescription, ITestContext testContext) {
		int counter = 1;
		for (int j = 0; j < appExpectedList.length; j++) {
			String step = stepNumber + counter;
			boolean status = appActualList.get(j).equals(appExpectedList[j]);
			reportLog(status, testContext.getName(), testDescription, step,
					"Actual value:" + appActualList.get(j) + " matches with the expected value:" + appExpectedList[j]);
			org.testng.Assert.assertTrue(status);
			counter = counter + 1;
		}
	}

	/**
	 * @param stepNumber
	 * @param appActualList
	 * @param appExpectedList
	 * @param testDescription
	 * @param testContext
	 */
	public void verifyDropdownValues(String stepNumber, ArrayList<String> appActualList, String[] appExpectedList,
			String testDescription, ITestContext testContext) {
		int counter = 1;
		for (int j = 0; j < appExpectedList.length; j++) {
			String step = stepNumber + counter;
			boolean status = appActualList.get(j + 1).equals(appExpectedList[j]);
			reportLog(status, testContext.getName(), testDescription, step, "Actual value:" + appActualList.get(j + 1)
					+ " matches with the expected value:" + appExpectedList[j]);
			org.testng.Assert.assertTrue(status);
			counter = counter + 1;
		}
	}

	/**
	 * @param stepNumber
	 * @param appActualList
	 * @param appExpectedList
	 * @param testDescription
	 * @param testContext
	 */
	public void verifyDropdownValuesnew(String stepNumber, ArrayList<String> appActualList, String[] appExpectedList,
			String testDescription, ITestContext testContext) {
		int counter = 1;
		for (int j = 0; j < appExpectedList.length; j++) {
			String step = stepNumber + counter;
			boolean status = appActualList.get(j).equals(appExpectedList[j]);
			reportLog(status, testContext.getName(), testDescription, step, "Actual value: " + appActualList.get(j)
					+ " matches with the expected value: " + appExpectedList[j]);
			org.testng.Assert.assertTrue(status);
			counter = counter + 1;
		}
	}

	public boolean verifyEmail(String USERNAME, String PASSWORD, String subject, String from) {
		Folder folder = null;
		Store store = null;
		boolean flag = false;
		try {
			Properties props = new Properties();
			props.put("mail.store.protocol", "imaps");
			Session session = Session.getInstance(props);
			store = session.getStore("imaps");
			store.connect("imap.gmail.com", USERNAME, PASSWORD);
			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			Message[] messages = folder.getMessages();
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				if (message.getSubject().contains(subject) && message.getFrom()[0].toString().contains(from)) {
					flag = true;
					break;
				} else {
					continue;
				}
			}
		} catch (MessagingException messagingException) {
			messagingException.printStackTrace();
		} finally {
			if (folder != null) {
				try {
					folder.close(true);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			if (store != null) {
				try {
					store.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	public void verifyWorkflowValues(String stepNumber, ArrayList<String> appActualList, String[] appExpectedList,
			String testDescription, ITestContext testContext) {
		int counter = 1;
		for (int j = 0; j < appExpectedList.length; j++) {
			String step = stepNumber + counter;
			boolean status = appActualList.get(j).equals(appExpectedList[j]);
			reportLog(status, testContext.getName(), testDescription, step, "Actual value of workflow  :  " + (j + 1)
					+ "  " + appActualList.get(j) + " matches with the expected value:  " + appExpectedList[j]);
			org.testng.Assert.assertTrue(status);
			counter = counter + 1;
		}
	}

	/**
	 * This method calculate the total due amount of Purchased licenses i.e Total
	 * Due = (Amount + Sales Tax) of purchase licenses
	 * 
	 * @param amount
	 * @param salestax
	 * @return
	 */
	public static String returnTotalDueAmount(String amount, float salestax) {
		String amount1 = amount.replace(",", "");
		float total_annual_cost_int1 = Float.parseFloat(amount1);
		float total_due1 = (total_annual_cost_int1 + salestax);
		NumberFormat formatter2 = new DecimalFormat("#,###.00");
		String total_due = formatter2.format(total_due1);
		return total_due;
	}

	/**
	 * This method generate ONLY random numeric value
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNumberInRange(int min, int max) {

		Random r = new Random();
		return r.ints(min, (max + 1)).findFirst().getAsInt();

	}

	public static Map<String, List<String>> getDataFromCSVFile(String filePath) {
		String line = "";
		Map<String, List<String>> storeage = new HashMap<>();
		List<String> value = new ArrayList<>();
		List<String> title = new ArrayList<>();
		int lineNumber = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			while ((line = br.readLine()) != null) {
				String[] storeTitle = line.split(",");
				if (lineNumber == 0) { // title
					title = Arrays.asList(storeTitle);
				} else { // add column
					value.add(line);
				}
				lineNumber++;
			}
			List<String> mapValue = new ArrayList<>();
			for (int i = 0; i < title.size(); i++) {
				for (int j = 0; j < value.size(); j++) {
					mapValue.add(value.get(j).split(",")[i]);
				}
				List<String> newArr = new ArrayList<>();
				newArr.addAll(mapValue);
				storeage.put(title.get(i), newArr);
				mapValue.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return storeage;
	}
}
