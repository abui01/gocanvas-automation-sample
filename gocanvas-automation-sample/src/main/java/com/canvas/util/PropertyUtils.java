package com.canvas.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyUtils {

	private static String ENV = "env";
	private static Properties properties = new Properties();

	public static String getEnvironment() {
		return properties.getProperty(ENV);
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static void init(String env) {
		try {
			properties.load(new FileInputStream("src/main/resources/config.properties"));

			File envFile = new File("src/main/resources/environments", env + ".config.properties");
			if (envFile.exists()) {
				properties.load(new FileInputStream(envFile));
			}
		} catch (Exception e) {
		}

	}

	public static void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

}
