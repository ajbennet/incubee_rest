package ee.incub.rest.spring.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Config {
	private static Properties prop = new Properties();
	private static String propFileName = "config.properties";
	private static String propQAFileName = "config-qa.properties";
	private static final Logger logger = LoggerFactory
			.getLogger(Config.class);
	static {
		InputStream inputStream = Config.class.getClassLoader()
				.getResourceAsStream(propQAFileName);
		try {
			prop.load(inputStream);
			logger.info("QA Properties :" +prop);
		} catch (IOException e) {
			logger.info("QA Config not loaded, so trying Prod");
			//if no config file found, it may be in qa mode., so try loading that file.
			inputStream = Config.class.getClassLoader()
					.getResourceAsStream(propFileName);
			try {
				prop.load(inputStream);
				logger.info("Prod Properties :" +prop);
			} catch (IOException ex) {
				logger.error("Both QA and Production failed");
				ex.printStackTrace();
			}
		}
	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

}