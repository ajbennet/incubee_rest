package ee.incub.rest.spring.aws.adaptors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

import ee.incub.rest.spring.model.Incubee;
import ee.incub.rest.spring.model.Token;
import ee.incub.rest.spring.model.User;
import ee.incub.rest.spring.utils.Constants;

public class DynamoDBAdaptor {

	static DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
			new ProfileCredentialsProvider()));
	static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static final Logger logger = LoggerFactory
			.getLogger(DynamoDBAdaptor.class);
	static {
		initializeAndCreateTables();
	}

	public static void loadIncubee(Incubee incubee) {

		Table table = dynamoDB.getTable(Constants.INCUBEE_TABLE);

		try {

			logger.debug("Adding data to " + Constants.INCUBEE_TABLE);

			Item item = new Item().withPrimaryKey("id", incubee.getId());
			if (incubee.getCompany_name() != null)
				item.withString("company_name", incubee.getCompany_name());
			if (incubee.getCompany_url() != null)
				item.withString("company_url", incubee.getCompany_url());
			// need to hash this.
			if (incubee.getCompany_name() != null)
				item.withStringSet("photos",
						new HashSet<String>(Arrays.asList(incubee.getImages())));
			if (incubee.getVideo() != null)
				item.withString("video", incubee.getVideo());
			if (incubee.getHigh_concept() != null)
				item.withString("high_concept", incubee.getHigh_concept());
			if (incubee.getDescription() != null)
				item.withString("description", incubee.getDescription());
			if (incubee.getFounder() != null)
				item.withString("founder", incubee.getFounder());
			if (incubee.getLogo_url() != null)
				item.withString("logo_url", incubee.getLogo_url());
			if (incubee.getTwitter_url() != null)
				item.withString("twitter_url", incubee.getTwitter_url());
			if (incubee.getLocation() != null)
				item.withString("location", incubee.getLocation());
			if (incubee.getVideo_url() != null)
				item.withString("video_url", incubee.getVideo_url());
			item.withBoolean("funding", incubee.isFunding());
			if (incubee.getField() != null)
				item.withString("field", incubee.getField());
			if (incubee.getProject_status() != null)
				item.withString("project_status", incubee.getProject_status());
			item.withString("added_time", (dateFormatter.format(new Date())));
			item.withString("updated_time", (dateFormatter.format(new Date())));
			table.putItem(item);
			logger.info("Added data for company :" + incubee.getCompany_name());
			System.out.println("Added data for company :"
					+ incubee.getCompany_name());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to create item in " + Constants.INCUBEE_TABLE);
			logger.error(e.getMessage());
		}

	}

	public static boolean createUser(User user) {

		Table table = dynamoDB.getTable(Constants.USER_TABLE);

		try {

			logger.debug("Adding data to " + Constants.USER_TABLE);

			Item item = new Item()
					.withPrimaryKey("id", user.getId())
					.withString("img_url", user.getImage_url())
					.withString("compnay_id", user.getCompany_id())
					.withString("email", user.getEmail())
					.withString("name", user.getName())
					.withString("login_type", user.getLogin_type())
					.withString("added_time",
							(dateFormatter.format(new Date())))
					.withString("updated_time",
							(dateFormatter.format(new Date())));
			table.putItem(item);
			logger.info("Added user  :" + user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to create item in " + Constants.INCUBEE_TABLE);
			logger.error(e.getMessage());
		}
		return false;
	}

	public static Incubee getIncubee(String incubee_id) {
		Table table = dynamoDB.getTable(Constants.INCUBEE_TABLE);
		QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(
				"id = :v1 ").withValueMap(
				new ValueMap().withString(":v1", incubee_id))
		// .withProjectionExpression("company_url, description, founder, high_concept, location, logo_url, twitter_url, video_url")
		;
		ItemCollection<QueryOutcome> items = table.query(querySpec);
		Iterator<Item> iterator = items.iterator();

		System.out.println("Query: printing results...");
		Incubee incubee = null;
		while (iterator.hasNext()) {
			if (incubee == null) {
				incubee = new Incubee();
			}
			Item item = iterator.next();
			logger.info("Incubee from DB for company: " + incubee_id + " - "
					+ item.toJSONPretty());

			incubee.setCompany_name(item.getString("incubee_id"));
			incubee.setCompany_url(item.getString("company_url"));
			incubee.setImages((String[]) item.getStringSet("photos").toArray());
			incubee.setHigh_concept(item.getString("high_concept"));
			incubee.setDescription(item.getString("description"));
			incubee.setVideo(item.getString("video"));
			incubee.setFounder(item.getString("founder"));
			incubee.setLogo_url(item.getString("logo_url"));
			incubee.setTwitter_url(item.getString("twitter_url"));
			incubee.setLocation(item.getString("location"));
			incubee.setVideo_url(item.getString("video_url"));
			incubee.setFunding(item.getBoolean("funding"));
			incubee.setProject_status(item.getString("project_status"));
			incubee.setField(item.getString("field"));

		}
		return incubee;
	}

	public static User getUser(String user_id) {
		Table table = dynamoDB.getTable("incubee_db");
		try {
			QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(
					"incubee_id = :v1 ").withValueMap(
					new ValueMap().withString(":v1", user_id))
			// .withProjectionExpression("company_url, description, founder, high_concept, location, logo_url, twitter_url, video_url")
			;
			ItemCollection<QueryOutcome> items = table.query(querySpec);
			Iterator<Item> iterator = items.iterator();

			System.out.println("Query: printing results...: ");
			User user = null;
			while (iterator.hasNext()) {
				if (user == null) {
					user = new User();
				}
				Item item = iterator.next();
				logger.info("User from DB for user_id: " + user_id + " - "
						+ item.toJSONPretty());

				user.setId(item.getString("id"));
				user.setCompany_id(item.getString("company_id"));
				user.setEmail(item.getString("email"));
				user.setImage_url(item.getString("image_url"));
				user.setLogin_type(item.getString("login_type"));
				user.setUser_type(item.getString("user_type"));
				user.setName(item.getString("name"));

			}
			return user;
		} catch (AmazonServiceException e) {
			logger.error(e.getMessage(), e);
			return null;
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

		AmazonDynamoDBClient client = new AmazonDynamoDBClient(
				new ProfileCredentialsProvider());
		ScanRequest scanRequest = new ScanRequest()
				.withTableName(Constants.INCUBEE_TABLE);
		List<Incubee> list = new ArrayList<Incubee>();
		ScanResult result = client.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			Incubee incubee = new Incubee();
			incubee.setCompany_name(item.get("company_name") != null ? item.get(
					"company_name").getS() : null);
			incubee.setCompany_url(item.get("company_url") != null ? item.get(
					"company_url").getS() : null);
			incubee.setLogo_url(item.get("logo_url") != null ? item.get(
					"logo_url").getS() : null);
			incubee.setDescription(item.get("description") != null ? item.get(
					"description").getS() : null);
			incubee.setFounder(item.get("founder") != null ? item
					.get("founder").getS() : null);
			incubee.setHigh_concept(item.get("high_concept") != null ? item
					.get("high_concept").getS() : null);
			incubee.setId(item.get("id") != null ? item.get("id").getS() : null);
			incubee.setContact_email(item.get("contact_email") != null ? item
					.get("contact_email").getS() : null);
			incubee.setLocation(item.get("location") != null ? item.get(
					"location").getS() : null);
			incubee.setTwitter_url(item.get("twitter_url") != null ? item.get(
					"twitter_url").getS() : null);
			incubee.setVideo_url(item.get("video_url") != null ? item.get(
					"video_url").getS() : null);
			incubee.setVideo(item.get("video") != null ? item.get("video")
					.getS() : null);
			List<String> photos = item.get("photos") != null ?item
					.get("photos").getSS() : null;
			String[] photosStr = new String[photos.size()];
			if (photos!=null){	
				incubee.setImages(photos.toArray(photosStr));	
			}
			incubee.setFunding(item.get("funding") != null ? item
					.get("funding").getBOOL() : null);
			incubee.setField(item.get("field") != null ? item.get("field")
					.getS() : null);
			incubee.setProject_status(item.get("project_status") != null ? item
					.get("project_status").getS() : null);
			list.add(incubee);
			logger.debug("Incubee: " + item.toString());
		}
		logger.info("Incubee List :" + list);
		return list;
	}

	static void initializeAndCreateTables() {

		TableCollection<ListTablesResult> tables = dynamoDB.listTables();
		Iterator<Table> iterator = tables.iterator();

		logger.debug("Listing table names");
		boolean createUserTable = true;
		boolean createIncubeeTable = true;
		while (iterator.hasNext()) {
			Table table = iterator.next();
			logger.debug("Table : " + table.getTableName());
			if (table.getTableName().equals(Constants.INCUBEE_TABLE)) {
				createIncubeeTable = false;
			} else if (table.getTableName().equals(Constants.USER_TABLE)) {
				createUserTable = false;
			}
		}
		if (createUserTable) {
			createUserTable();
		}
		if (createIncubeeTable) {
			createIncubeeTable();
		}
	}

	static void createUserTable() {
		String tableName = Constants.USER_TABLE;
		try {

			ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("id").withAttributeType("S"));

			ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
			keySchema.add(new KeySchemaElement().withAttributeName("id")
					.withKeyType(KeyType.HASH));

			CreateTableRequest request = new CreateTableRequest()
					.withTableName(tableName)
					.withKeySchema(keySchema)
					.withAttributeDefinitions(attributeDefinitions)
					.withProvisionedThroughput(
							new ProvisionedThroughput().withReadCapacityUnits(
									20L).withWriteCapacityUnits(10L));

			System.out.println("Issuing CreateTable request for " + tableName);
			Table table = dynamoDB.createTable(request);

			System.out.println("Waiting for " + tableName
					+ " to be created...this may take a while...");
			table.waitForActive();

			getTableInformation(tableName);

		} catch (Exception e) {
			System.err.println("CreateTable request failed for " + tableName);
			System.err.println(e.getMessage());
		}

	}

	static void createIncubeeTable() {
		String tableName = Constants.INCUBEE_TABLE;
		try {

			ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("id").withAttributeType("S"));

			ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
			keySchema.add(new KeySchemaElement().withAttributeName("id")
					.withKeyType(KeyType.HASH));

			CreateTableRequest request = new CreateTableRequest()
					.withTableName(tableName)
					.withKeySchema(keySchema)
					.withAttributeDefinitions(attributeDefinitions)
					.withProvisionedThroughput(
							new ProvisionedThroughput().withReadCapacityUnits(
									20L).withWriteCapacityUnits(10L));

			System.out.println("Issuing CreateTable request for " + tableName);
			Table table = dynamoDB.createTable(request);

			System.out.println("Waiting for " + tableName
					+ " to be created...this may take a while...");
			table.waitForActive();

			getTableInformation(tableName);

		} catch (Exception e) {
			System.err.println("CreateTable request failed for " + tableName);
			System.err.println(e.getMessage());
		}

	}

	static void getTableInformation(String tableName) {

		System.out.println("Describing " + tableName);

		TableDescription tableDescription = dynamoDB.getTable(tableName)
				.describe();
		System.out.format("Name: %s:\n" + "Status: %s \n"
				+ "Provisioned Throughput (read capacity units/sec): %d \n"
				+ "Provisioned Throughput (write capacity units/sec): %d \n",
				tableDescription.getTableName(), tableDescription
						.getTableStatus(), tableDescription
						.getProvisionedThroughput().getReadCapacityUnits(),
				tableDescription.getProvisionedThroughput()
						.getWriteCapacityUnits());
	}

}