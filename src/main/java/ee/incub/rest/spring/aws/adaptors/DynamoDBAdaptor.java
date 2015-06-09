package ee.incub.rest.spring.aws.adaptors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import ee.incub.rest.spring.model.Incubee;
import ee.incub.rest.spring.utils.Constants;

public class DynamoDBAdaptor {

	static DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
			new ProfileCredentialsProvider()));
	static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static final Logger logger = LoggerFactory
			.getLogger(DynamoDBAdaptor.class);

	public static void loadIncubee(Incubee incubee) {

		Table table = dynamoDB.getTable(Constants.DB_TABLE_NAME);

		try {

			logger.debug("Adding data to " + Constants.DB_TABLE_NAME);

			Item item = new Item()
					.withPrimaryKey("incubee_id", incubee.getCompanyname())
					.withString("company_url", incubee.getCompanyurl())
					.withString("password", incubee.getPassword())
					// need to hash this.
					.withStringSet("photos",
							new HashSet<String>(Arrays.asList("test")))
					.withString("high_concept", incubee.getHighconcept())
					.withString("description", incubee.getDescription())
					.withString("username", incubee.getUsername())
					.withString("founder", incubee.getFounder())
					.withString("logo_url", incubee.getLogourl())
					.withString("twitter_url", incubee.getTwitterurl())
					.withString("location", incubee.getLocation())
					.withString("video_url", incubee.getVideourl())
					.withString("added_time",
							(dateFormatter.format(new Date())))
					.withString("updated_time",
							(dateFormatter.format(new Date())));
			table.putItem(item);
			logger.info("Added data for company :" + incubee.getCompanyname());
			System.out.println("Added data for company :"
					+ incubee.getCompanyname());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to create item in " + Constants.DB_TABLE_NAME);
			logger.error(e.getMessage());
		}

	}

	public static List<Incubee> getAllIncubees() {
		long twoWeeksAgoMilli = (new Date()).getTime()
				- (15L * 24L * 60L * 60L * 1000L);
		Date twoWeeksAgo = new Date();
		twoWeeksAgo.setTime(twoWeeksAgoMilli);
		SimpleDateFormat df = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String twoWeeksAgoStr = df.format(twoWeeksAgo);

		Table table = dynamoDB.getTable(Constants.DB_TABLE_NAME);

		
		AmazonDynamoDBClient client = new AmazonDynamoDBClient(
			    new ProfileCredentialsProvider());
		ScanRequest scanRequest = new ScanRequest().withTableName(Constants.DB_TABLE_NAME);
		List<Incubee> list = new ArrayList<Incubee>();
		ScanResult result = client.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			Incubee incubee = new Incubee();
			incubee.setCompanyname(item.get("incubee_id")!=null?item.get("incubee_id").getS():null);
			incubee.setCompanyurl(item.get("company_url")!=null?item.get("company_url").getS():null);
			incubee.setLogourl(item.get("logo_url")!=null?item.get("logo_url").getS():null);
			incubee.setDescription(item.get("description")!=null?item.get("description").getS():null);
			incubee.setFounder(item.get("founder")!=null?item.get("founder").getS():null);
			incubee.setHighconcept(item.get("high_concept")!=null?item.get("high_concept").getS():null);
			incubee.setLocation(item.get("location")!=null?item.get("location").getS():null);
			incubee.setTwitterurl(item.get("twitter_url")!=null?item.get("twitter_url").getS():null);
			incubee.setVideourl(item.get("video_url")!=null?item.get("video_url").getS():null);
			list.add(incubee);
			logger.debug("Incubee: " +item.toString());
		}
		logger.info("Incubee List :" + list);
		return list;
	}
}