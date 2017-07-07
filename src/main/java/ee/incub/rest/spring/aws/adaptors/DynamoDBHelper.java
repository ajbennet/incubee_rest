package ee.incub.rest.spring.aws.adaptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import ee.incub.rest.spring.utils.Constants;

public class DynamoDBHelper {

	// public static DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
	// new ProfileCredentialsProvider()));
	private static final Logger logger = LoggerFactory.getLogger(MessagesDynamoDB.class);
	public static DynamoDB dynamoDB;
	public static AmazonDynamoDBClient dynamoDBClient;

	static {
		if (Constants.ENV.equalsIgnoreCase("qa")) {
			dynamoDB = new DynamoDB(Regions.US_WEST_1);
			dynamoDBClient =  new AmazonDynamoDBClient(
					new ProfileCredentialsProvider());
			dynamoDBClient.setRegion(Regions.US_WEST_1);
			logger.info("Initializing QA Database at " + Regions.US_WEST_1);
			
		} else {
			dynamoDB = new DynamoDB(Regions.US_EAST_1);

			dynamoDBClient =  new AmazonDynamoDBClient(
					new ProfileCredentialsProvider());
			dynamoDBClient.setRegion(Regions.US_EAST_1);
			logger.info("Initializing PROD Database at " + Regions.US_EAST_1);
		}
	}
}
