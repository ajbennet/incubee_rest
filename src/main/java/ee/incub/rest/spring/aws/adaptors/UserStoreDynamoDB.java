package ee.incub.rest.spring.aws.adaptors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

import ee.incub.rest.spring.utils.Constants;

public class UserStoreDynamoDB {

	
	static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static final Logger logger = LoggerFactory
			.getLogger(UserStoreDynamoDB.class);
	static {
		initializeAndCreateTables();
	}

	public static boolean loadLike(String userId, String incubeeId) throws Exception {
		Table table = DynamoDBHelper.dynamoDB.getTable(Constants.LIKE_TABLE);
		if(userId== null || incubeeId == null || userId.isEmpty() || incubeeId.isEmpty()){
			logger.error("UserId or IncubeeID empty in loadLike request: " + userId + incubeeId);
			return false;
		}
		//check if the like already exists
		if(Arrays.asList(getLikedIncubees(userId)).contains(incubeeId)){
			logger.info("User already liked");
			return true;
		}
		
		try {
			logger.debug("Adding data to " + Constants.LIKE_TABLE);
			Item item = new Item().withPrimaryKey("id", userId);
				item.withKeyComponent("updated_time",dateFormatter.format(new Date()));
				item.withString("incubee_id", incubeeId);
			table.putItem(item);
			logger.info("Added like data for user :" + userId + " incubee :" + incubeeId);
			return true;
		} catch (Exception e) {
			logger.error("Failed to create item in " + Constants.LIKE_TABLE);
			logger.error(e.getMessage());
			throw e;
		}
	}
	
	public static boolean loadCustomer(String userId, String incubeeId) throws Exception {
		Table table = DynamoDBHelper.dynamoDB.getTable(Constants.CUSTOMER_TABLE);
		if(userId== null || incubeeId == null || userId.isEmpty() || incubeeId.isEmpty()){
			logger.error("UserId or IncubeeID empty in loadCustomer request : " + userId + incubeeId);
			return false;
		}
		//check if the like already exists
		if(Arrays.asList(getCustomeredUsersforIncubees(incubeeId)).contains(userId)){
			logger.info("User already customered");
			return true;
		}
		try {
			logger.debug("Adding data to " + Constants.CUSTOMER_TABLE);
			Item item = new Item().withPrimaryKey("id", incubeeId);
				item.withKeyComponent("updated_time",dateFormatter.format(new Date()));
				item.withString("user_id", userId);
			table.putItem(item);
			logger.info("Added Customer data for user :" + userId + " incubee :" + incubeeId);
			return true;
		} catch (Exception e) {
			logger.error("Failed to create item in " + Constants.CUSTOMER_TABLE);
			logger.error(e.getMessage());
			throw e;
		}
	}
		
	public static String[] getCustomeredUsersforIncubees(String incubee_id) {
		Table table = DynamoDBHelper.dynamoDB.getTable(Constants.CUSTOMER_TABLE);
		 long fiveYearsAgoMilli = (new Date()).getTime()
				 - (15L * 365L * 24L * 60L * 60L * 1000L);
				 Date twoWeeksAgo = new Date();
				 twoWeeksAgo.setTime(fiveYearsAgoMilli);
				 SimpleDateFormat df = new SimpleDateFormat(
				 "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				 String fiveYearsAgoMilliStr = df.format(fiveYearsAgoMilli);
		try {
			QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(
					"id = :v1 and updated_time > :d1").withValueMap(
					new ValueMap().withString(":v1", incubee_id)
					.withString(":d1",fiveYearsAgoMilliStr ))
			;
			ItemCollection<QueryOutcome> items = table.query(querySpec);
			Iterator<Item> iterator = items.iterator();

			logger.info("Query: printing results...: ");
			List<String> userList = new ArrayList<String>();
			while (iterator.hasNext()) {
				
				Item item = iterator.next();
				logger.info("Customers from DB for incubee_id: " + incubee_id
						+ " - " + item.toJSONPretty());
				 if (item.getString("user_id")!=null || !item.getString("user_id").isEmpty()){
					 userList.add(item.getString("user_id"));
				 }

			}
			return  userList.toArray(new String[userList.size()]);
		} catch (AmazonServiceException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	

	public static String[] getLikedIncubees(String user_id) {
		Table table = DynamoDBHelper.dynamoDB.getTable(Constants.LIKE_TABLE);
		 long fiveYearsAgoMilli = (new Date()).getTime()
				 - (15L * 365L * 24L * 60L * 60L * 1000L);
				 Date twoWeeksAgo = new Date();
				 twoWeeksAgo.setTime(fiveYearsAgoMilli);
				 SimpleDateFormat df = new SimpleDateFormat(
				 "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				 String fiveYearsAgoMilliStr = df.format(fiveYearsAgoMilli);
		try {
			QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(
					"id = :v1 and updated_time > :d1").withValueMap(
					new ValueMap().withString(":v1", user_id)
					.withString(":d1",fiveYearsAgoMilliStr ))
			;
			ItemCollection<QueryOutcome> items = table.query(querySpec);
			Iterator<Item> iterator = items.iterator();

			logger.info("Query: printing results...: " + items.getTotalCount());
			List<String> incubeeList = new ArrayList<String>();
			while (iterator.hasNext()) {
				
				Item item = iterator.next();
				logger.info("Likes from DB for userID: " + user_id
						+ " - " + item.toJSONPretty());
				 if (item.getString("incubee_id")!=null || !item.getString("incubee_id").isEmpty()){
					 incubeeList.add(item.getString("incubee_id"));
				 }

			}
			return incubeeList.toArray(new String[incubeeList.size()]);
		} catch (AmazonServiceException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	static void initializeAndCreateTables() {

		TableCollection<ListTablesResult> tables = DynamoDBHelper.dynamoDB.listTables();
		Iterator<Table> iterator = tables.iterator();

		logger.debug("Listing table names");
		boolean createCustomerTable = true;
		boolean createLikeTable = true;
		while (iterator.hasNext()) {
			Table table = iterator.next();
			logger.debug("Table : " + table.getTableName());
			if (table.getTableName().equals(Constants.LIKE_TABLE)) {
				createLikeTable = false;
			} else if (table.getTableName().equals(Constants.CUSTOMER_TABLE)) {
				createCustomerTable = false;
			} 
		}
		if (createCustomerTable) {
			createCustomerTable();
		}
		if (createLikeTable) {
			createLikeTable();
		}
	}

	
	static void createLikeTable() {
		String tableName = Constants.LIKE_TABLE;
		try {

			ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("id").withAttributeType("S"));
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("updated_time").withAttributeType("S"));

			ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
			keySchema.add(new KeySchemaElement().withAttributeName("id")
					.withKeyType(KeyType.HASH));
			keySchema.add(new KeySchemaElement().withAttributeName("updated_time")
					.withKeyType(KeyType.RANGE));

			CreateTableRequest request = new CreateTableRequest()
					.withTableName(tableName)
					.withKeySchema(keySchema)
					.withAttributeDefinitions(attributeDefinitions)
					.withProvisionedThroughput(
							new ProvisionedThroughput().withReadCapacityUnits(
									2L).withWriteCapacityUnits(2L));

			logger.info("Issuing CreateTable request for " + tableName);
			Table table = DynamoDBHelper.dynamoDB.createTable(request);

			logger.info("Waiting for " + tableName
					+ " to be created...this may take a while...");
			table.waitForActive();

			getTableInformation(tableName);

		} catch (Exception e) {
			logger.error("CreateTable request failed for " + tableName);
			logger.error(e.getMessage());
		}

	}
	static void createCustomerTable() {
		String tableName = Constants.CUSTOMER_TABLE;
		try {

			ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("id").withAttributeType("S"));
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("updated_time").withAttributeType("S"));

			ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
			keySchema.add(new KeySchemaElement().withAttributeName("id")
					.withKeyType(KeyType.HASH));
			keySchema.add(new KeySchemaElement().withAttributeName("updated_time")
					.withKeyType(KeyType.RANGE));

			CreateTableRequest request = new CreateTableRequest()
					.withTableName(tableName)
					.withKeySchema(keySchema)
					.withAttributeDefinitions(attributeDefinitions)
					.withProvisionedThroughput(
							new ProvisionedThroughput().withReadCapacityUnits(
									2L).withWriteCapacityUnits(2L));

			logger.info("Issuing CreateTable request for " + tableName);
			Table table = DynamoDBHelper.dynamoDB.createTable(request);

			logger.info("Waiting for " + tableName
					+ " to be created...this may take a while...");
			table.waitForActive();

			getTableInformation(tableName);

		} catch (Exception e) {
			logger.error("CreateTable request failed for " + tableName, e);
			logger.error(e.getMessage());
		}

	}

	static void getTableInformation(String tableName) {

		logger.info("Describing " + tableName);

		TableDescription tableDescription = DynamoDBHelper.dynamoDB.getTable(tableName)
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