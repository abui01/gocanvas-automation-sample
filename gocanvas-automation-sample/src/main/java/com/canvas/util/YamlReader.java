package com.canvas.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.canvas.qa.test.BrowserLaunchTest;

public class YamlReader extends BrowserLaunchTest

{
	public static String yamlFilePath;

	public static Map<String, String> getYmlMap(String testId) {
		Reader doc = null;
		try {
			String env = PropertyUtils.getEnvironment();
			String YAML_FILE_NEW = "src/main/resources/data/" + env + ".yml";
			File ymlFileNew = new File(YAML_FILE_NEW);
			if (ymlFileNew.exists()) {
				doc = new FileReader(YAML_FILE_NEW);
			} else {
				doc = new FileReader(YAML_FILE);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Yaml yaml = new Yaml();
		@SuppressWarnings("unchecked")
		Map<String, Map<String, String>> object = (Map<String, Map<String, String>>) yaml.load(doc);
		return object.get(testId);
	}
}
