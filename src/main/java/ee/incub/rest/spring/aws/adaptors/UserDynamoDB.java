package ee.incub.rest.spring.aws.adaptors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

import ee.incub.rest.spring.model.db.Incubee;
import ee.incub.rest.spring.model.db.User;
import ee.incub.rest.spring.model.http.Customer;
import ee.incub.rest.spring.model.http.v010.AdhocIncubee;
import ee.incub.rest.spring.utils.Constants;
import ee.incub.rest.spring.utils.Utils;

public class UserDynamoDB {

	static DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
			new ProfileCredentialsProvider()));
	static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static final Logger logger = LoggerFactory
			.getLogger(UserDynamoDB.class);
	static {
		initializeAndCreateTables();
	}

	public static void loadIncubee(Incubee incubee) throws Exception {

		Table table = dynamoDB.getTable(Constants.INCUBEE_TABLE);

		try {

			logger.debug("Adding data to " + Constants.INCUBEE_TABLE);

			Item item = new Item().withPrimaryKey("id", incubee.getId());
			if (incubee.getCompany_name() != null)
				item.withString("company_name", incubee.getCompany_name());
			if (incubee.getCompany_url() != null)
				item.withString("company_url", incubee.getCompany_url());
			// need to hash this.
			if (incubee.getImages() != null)
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
		} catch (Exception e) {
			logger.error("Failed to create item in " + Constants.INCUBEE_TABLE);
			logger.error(e.getMessage());
			throw e;
		}

	}
	
	public static void createAdhocIncubee(String incubeeId, String name, String emailId , String createdById) throws Exception {

		Table table = dynamoDB.getTable(Constants.ADHOC_INCUBEE_TABLE);

		try {

			logger.debug("Adding data to " + Constants.ADHOC_INCUBEE_TABLE);

			Item item = new Item().withPrimaryKey("id", incubeeId);
			if (name != null)
				item.withString("name", name);
			if (emailId != null)
				item.withString("email_id", emailId);
			if (createdById != null)
				item.withString("created_by_id", createdById);
			
			item.withString("added_time", (dateFormatter.format(new Date())));
			item.withString("updated_time", (dateFormatter.format(new Date())));
			table.putItem(item);
			logger.info("Added adhoc incubee data for company :" + name);
		} catch (Exception e) {
			logger.error("Failed to create item in " + Constants.ADHOC_INCUBEE_TABLE);
			logger.error(e.getMessage());
			throw e;
		}

	}

	public static boolean createUser(User user) throws Exception {

		Table table = dynamoDB.getTable(Constants.USER_TABLE);

		try {

			logger.debug("Adding data to " + Constants.USER_TABLE);

			Item item = new Item()
					.withPrimaryKey("id", user.getHandle_id())
					.withString("handle_id", user.getHandle_id())
					.withString("email", user.getEmail())
					.withString("login_type", user.getLogin_type())
					.withString("added_time",
							(dateFormatter.format(new Date())))
					.withString("updated_time",
							(dateFormatter.format(new Date())));
			if (user.getImage_url() != null && !user.getImage_url().isEmpty())
				item.withString("img_url", user.getImage_url());
			if (user.getName() != null && !user.getName().isEmpty())
				item.withString("name", user.getName());
			if (user.getCompany_id() != null && !user.getCompany_id().isEmpty())
				item.withString("company_id", user.getCompany_id());

			table.putItem(item);
			logger.info("Added user  :" + user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to create item in " + Constants.INCUBEE_TABLE);
			logger.error(e.getMessage());
			throw e;
		}

	}

	// public static Incubee getIncubee(String incubee_id) {
	// Table table = dynamoDB.getTable(Constants.INCUBEE_TABLE);
	// QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(
	// "id = :v1 ").withValueMap(
	// new ValueMap().withString(":v1", incubee_id))
	// //
	// .withProjectionExpression("company_url, description, founder, high_concept, location, logo_url, twitter_url, video_url")
	// ;
	// ItemCollection<QueryOutcome> items = table.query(querySpec);
	// Iterator<Item> iterator = items.iterator();
	//
	// logger.info("Query: printing results...");
	// Incubee incubee = null;
	// while (iterator.hasNext()) {
	// if (incubee == null) {
	// incubee = new Incubee();
	// }
	// Item item = iterator.next();
	// logger.info("Incubee from DB for company: " + incubee_id + " - "
	// + item.toJSONPretty());
	//
	// incubee.setCompany_name(item.getString("incubee_id"));
	// incubee.setCompany_url(item.getString("company_url"));
	// incubee.setImages((String[]) item.getStringSet("photos").toArray());
	// incubee.setHigh_concept(item.getString("high_concept"));
	// incubee.setDescription(item.getString("description"));
	// incubee.setVideo(item.getString("video"));
	// incubee.setFounder(item.getString("founder"));
	// incubee.setLogo_url(item.getString("logo_url"));
	// incubee.setTwitter_url(item.getString("twitter_url"));
	// incubee.setLocation(item.getString("location"));
	// incubee.setVideo_url(item.getString("video_url"));
	// incubee.setFunding(item.getBoolean("funding"));
	// incubee.setProject_status(item.getString("project_status"));
	// incubee.setField(item.getString("field"));
	//
	// }
	// return incubee;
	// }

	public static User getUser(String user_id) throws AmazonServiceException {
		Table table = dynamoDB.getTable(Constants.USER_TABLE);
		try {
			QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(
					"id = :v1 ").withValueMap(
					new ValueMap().withString(":v1", user_id))
			// .withProjectionExpression("company_url, description, founder, high_concept, location, logo_url, twitter_url, video_url")
			;
			ItemCollection<QueryOutcome> items = table.query(querySpec);
			Iterator<Item> iterator = items.iterator();

			logger.info("Query: printing results...: ");
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
				user.setImage_url(item.getString("img_url"));
				user.setLogin_type(item.getString("login_type"));
				user.setUser_type(item.getString("user_type"));
				user.setName(item.getString("name"));
				user.setHandle_id(item.getString("handle_id"));
				user.setAdmin((item.isNull("is_admin")
						|| !item.isPresent("is_admin") ? false : item
						.getBoolean("is_admin")));
			}
			return user;
		} catch (AmazonServiceException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static User getUserForHandle(String handle_id)
			throws AmazonServiceException {
		Table table = dynamoDB.getTable(Constants.USER_TABLE);
		try {
			QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(
					"id = :v1 ").withValueMap(
					new ValueMap().withString(":v1", handle_id))
			// .withProjectionExpression("company_url, description, founder, high_concept, location, logo_url, twitter_url, video_url")
			;
			ItemCollection<QueryOutcome> items = table.query(querySpec);
			Iterator<Item> iterator = items.iterator();

			logger.info("Query: printing results...: ");
			User user = null;
			while (iterator.hasNext()) {
				if (user == null) {
					user = new User();
				}
				Item item = iterator.next();
				logger.info("User from DB for handle_id: " + handle_id + " - "
						+ item.toJSONPretty());

				user.setId(item.getString("id"));
				user.setCompany_id(item.getString("company_id"));
				user.setEmail(item.getString("email"));
				user.setImage_url(item.getString("img_url"));
				user.setLogin_type(item.getString("login_type"));
				user.setUser_type(item.getString("user_type"));
				user.setName(item.getString("name"));
				user.setHandle_id(handle_id);
				user.setAdmin((item.isNull("is_admin")
						|| !item.isPresent("is_admin") ? false : item
						.getBoolean("is_admin")));
			}
			return user;
		} catch (AmazonServiceException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static Customer[] getCustomerDetailsforIds(Set<String> userIdSet)
			throws AmazonServiceException {
		Table table = dynamoDB.getTable(Constants.USER_TABLE);
		List<Customer> customerList = null;
		Iterator<String> userIterator = userIdSet.iterator();
		try {
			while (userIdSet != null && userIterator.hasNext()) {
				QuerySpec querySpec = new QuerySpec()
						.withKeyConditionExpression("id = :v1")
						// .withProjectionExpression("id, email, image_url, #n")
						.withValueMap(
								new ValueMap().withString(":v1", userIterator.next()))
				// .withAttributesToGet("id","email","image_url", "name")
				// .withNameMap(new NameMap().with("#n", "name"))
				;
				ItemCollection<QueryOutcome> items = table.query(querySpec);
				Iterator<Item> iterator = items.iterator();

				logger.info("Query: printing results...: ");
				while (iterator.hasNext()) {
					if (customerList == null) {
						customerList = new ArrayList<Customer>();
					}
					Customer customer = new Customer();

					Item item = iterator.next();
					logger.info("Customers from DB for user IDs: " + userIdSet
							+ " - " + item.toJSONPretty());

					customer.setId(item.getString("id"));
					if(item.getString("email")!=null)
						customer.setEmail(item.getString("email"));
					if(item.getString("img_url")!=null)
						customer.setImage_url(item.getString("img_url"));
					if(item.getString("name")!=null)
						customer.setName(item.getString("name"));
					customerList.add(customer);
				}
			}
			Customer[] customerArray = null;
			if (customerList != null) {
				customerArray = customerList.toArray(new Customer[customerList
						.size()]);
			}
			return customerArray;
		} catch (AmazonServiceException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static void deleteUser(String userid) throws Exception {

		Table table = dynamoDB.getTable(Constants.USER_TABLE);

		try {

			DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
					.withPrimaryKey("id", userid).withReturnValues(
							ReturnValue.ALL_OLD);

			DeleteItemOutcome outcome = table.deleteItem(deleteItemSpec);

			// Check the response.
			logger.info("Printing item that was deleted...");
			logger.info(outcome.getItem().toJSONPretty());

		} catch (Exception e) {
			logger.error("Error deleting item in " + Constants.USER_TABLE);
			logger.error(e.getMessage());
			throw e;
		}
	}

	public static Incubee getIncubee(String incubee_id)
			throws AmazonServiceException {
		Table table = dynamoDB.getTable(Constants.INCUBEE_TABLE);
		try {
			QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(
					"id = :v1 ").withValueMap(
					new ValueMap().withString(":v1", incubee_id))
			// .withProjectionExpression("company_url, description, founder, high_concept, location, logo_url, twitter_url, video_url")
			;
			ItemCollection<QueryOutcome> items = table.query(querySpec);
			Iterator<Item> iterator = items.iterator();

			logger.info("Query: printing results...: ");
			Incubee incubee = null;
			while (iterator.hasNext()) {

				Item item = iterator.next();
				logger.info("Incubee from DB for incubee_id: " + incubee_id
						+ " - " + item.toJSONPretty());
				incubee = Utils.fromItem(item);

			}
			return incubee;
		} catch (AmazonServiceException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static List<Incubee> getAllIncubees() {
		// long twoWeeksAgoMilli = (new Date()).getTime()
		// - (15L * 24L * 60L * 60L * 1000L);
		// Date twoWeeksAgo = new Date();
		// twoWeeksAgo.setTime(twoWeeksAgoMilli);
		// SimpleDateFormat df = new SimpleDateFormat(
		// "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		// String twoWeeksAgoStr = df.format(twoWeeksAgo);

		AmazonDynamoDBClient client = new AmazonDynamoDBClient(
				new ProfileCredentialsProvider());
		ScanRequest scanRequest = new ScanRequest()
				.withTableName(Constants.INCUBEE_TABLE);
		List<Incubee> list = new ArrayList<Incubee>();
		ScanResult result = client.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			list.add(Utils.fromItem(item));
		}
		logger.info("Incubee List :" + list);
		return list;
	}

	public static List<AdhocIncubee> getAllAdhocIncubees() {
		// long twoWeeksAgoMilli = (new Date()).getTime()
		// - (15L * 24L * 60L * 60L * 1000L);
		// Date twoWeeksAgo = new Date();
		// twoWeeksAgo.setTime(twoWeeksAgoMilli);
		// SimpleDateFormat df = new SimpleDateFormat(
		// "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		// String twoWeeksAgoStr = df.format(twoWeeksAgo);

		AmazonDynamoDBClient client = new AmazonDynamoDBClient(
				new ProfileCredentialsProvider());
		ScanRequest scanRequest = new ScanRequest()
				.withTableName(Constants.ADHOC_INCUBEE_TABLE);
		List<AdhocIncubee> list = new ArrayList<AdhocIncubee>();
		ScanResult result = client.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			list.add(Utils.adhocIncubeeFromItem(item));
		}
		logger.info("Adhoc Incubee List :" + list);
		return list;
	}
	
	static void initializeAndCreateTables() {

		TableCollection<ListTablesResult> tables = dynamoDB.listTables();
		Iterator<Table> iterator = tables.iterator();

		logger.debug("Listing table names");
		boolean createUserTable = true;
		boolean createIncubeeTable = true;
		boolean createAdhocIncubeeTable = true;
		while (iterator.hasNext()) {
			Table table = iterator.next();
			logger.debug("Table : " + table.getTableName());
			if (table.getTableName().equals(Constants.INCUBEE_TABLE)) {
				createIncubeeTable = false;
			} else if (table.getTableName().equals(Constants.USER_TABLE)) {
				createUserTable = false;
			}else if (table.getTableName().equals(Constants.ADHOC_INCUBEE_TABLE)) {
				createAdhocIncubeeTable = false;
			}
		}
		if (createUserTable) {
			createUserTable();
		}
		if (createIncubeeTable) {
			createIncubeeTable();
		}
		if (createAdhocIncubeeTable) {
			createAdhocIncubeeTable();
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

			logger.info("Issuing CreateTable request for " + tableName);
			Table table = dynamoDB.createTable(request);

			logger.info("Waiting for " + tableName
					+ " to be created...this may take a while...");
			table.waitForActive();

			getTableInformation(tableName);

		} catch (Exception e) {
			logger.error("CreateTable request failed for " + tableName);
			logger.error(e.getMessage());
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

			logger.info("Issuing CreateTable request for " + tableName);
			Table table = dynamoDB.createTable(request);

			logger.info("Waiting for " + tableName
					+ " to be created...this may take a while...");
			table.waitForActive();

			getTableInformation(tableName);

		} catch (Exception e) {
			logger.error("CreateTable request failed for " + tableName);
			logger.error(e.getMessage());
		}

	}
	
	static void createAdhocIncubeeTable() {
		String tableName = Constants.ADHOC_INCUBEE_TABLE;
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
									2L).withWriteCapacityUnits(2L));

			logger.info("Issuing CreateTable request for " + tableName);
			Table table = dynamoDB.createTable(request);

			logger.info("Waiting for " + tableName
					+ " to be created...this may take a while...");
			table.waitForActive();

			getTableInformation(tableName);

		} catch (Exception e) {
			logger.error("CreateTable request failed for " + tableName);
			logger.error(e.getMessage());
		}

	}
	
	public static void updateIncubee(Incubee incubee) throws Exception {

		Table table = dynamoDB.getTable(Constants.INCUBEE_TABLE);

		try {
			NameMap nameMap = new NameMap().with("#loc", "location");
			ValueMap valueMap = new ValueMap().withBoolean(":funding",
					incubee.isFunding());

			String updateExpression = "set funding=:funding ";
			if (incubee.getCompany_name() != null
					&& !incubee.getCompany_name().isEmpty()) {
				updateExpression = updateExpression
						+ ",company_name = :company_name";
				valueMap.with(":company_name", incubee.getCompany_name());
			}
			if (incubee.getCompany_url() != null
					&& !incubee.getCompany_url().isEmpty()) {
				updateExpression = updateExpression
						+ ",company_url = :company_url ";
				valueMap.with(":company_url", incubee.getCompany_url());
			}
			if (incubee.getLogo_url() != null
					&& !incubee.getLogo_url().isEmpty()) {
				updateExpression = updateExpression + ",logo_url = :logo_url ";
				valueMap.with(":logo_url", incubee.getLogo_url());
			}
			if (incubee.getLocation() != null
					&& !incubee.getLocation().isEmpty()) {
				updateExpression = updateExpression + ",#loc = :location ";
				valueMap.with(":location", incubee.getLocation());
			}
			if (incubee.getHigh_concept() != null
					&& !incubee.getHigh_concept().isEmpty()) {
				updateExpression = updateExpression
						+ ",high_concept = :high_concept ";
				valueMap.with(":high_concept", incubee.getHigh_concept());
			}
			if (incubee.getDescription() != null
					&& !incubee.getDescription().isEmpty()) {
				updateExpression = updateExpression
						+ ",description = :description ";
				valueMap.with(":description", incubee.getDescription());
			}
			if (incubee.getField() != null && !incubee.getField().isEmpty()) {
				updateExpression = updateExpression + ",field = :field ";
				valueMap.with(":field", incubee.getField());
			}
			if (incubee.getTwitter_url() != null
					&& !incubee.getTwitter_url().isEmpty()) {
				updateExpression = updateExpression
						+ ",twitter_url =  :twitter_url ";
				valueMap.with(":twitter_url", incubee.getTwitter_url());
			}
			if (incubee.getProject_status() != null
					&& !incubee.getProject_status().isEmpty()) {
				updateExpression = updateExpression
						+ ",project_status = :project_status ";
				valueMap.with(":project_status", incubee.getProject_status());
			}
			if (incubee.getVideo_url() != null
					&& !incubee.getVideo_url().isEmpty()) {
				updateExpression = updateExpression
						+ ",video_url = :video_url ";
				valueMap.with(":video_url", incubee.getVideo_url());
			}
			if (incubee.getImages() != null && incubee.getImages().length > 0) {
				updateExpression = updateExpression + ",photos = :photos ";
				valueMap.withStringSet(":photos", incubee.getImages());
			}
			if (incubee.getVideo() != null && !incubee.getVideo().isEmpty()) {
				updateExpression = updateExpression + ",video = :video";
				valueMap.with(":video", incubee.getVideo());
			}
			UpdateItemSpec updateItemSpec = new UpdateItemSpec()
					.withPrimaryKey("id", incubee.getId())
					.withReturnValues(ReturnValue.ALL_NEW)
					.withUpdateExpression(updateExpression)
					.withNameMap(nameMap).withValueMap(valueMap);

			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

			// Check the response.
			logger.info("Printing item after updating it");
			logger.info(outcome.getItem().toJSONPretty());

		} catch (Exception e) {
			logger.error("Error updating item in " + Constants.INCUBEE_TABLE, e);
			logger.error(e.getMessage());
			throw e;
		}
	}

	static void getTableInformation(String tableName) {

		logger.info("Describing " + tableName);

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

	// public static Incubee getIncubee(String incubee_id) {
	// Table table = dynamoDB.getTable(Constants.INCUBEE_TABLE);
	// QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(
	// "id = :v1 ").withValueMap(
	// new ValueMap().withString(":v1", incubee_id))
	// //
	// .withProjectionExpression("company_url, description, founder, high_concept, location, logo_url, twitter_url, video_url")
	// ;
	// ItemCollection<QueryOutcome> items = table.query(querySpec);
	// Iterator<Item> iterator = items.iterator();
	//
	// logger.info("Query: printing results...");
	// Incubee incubee = null;
	// while (iterator.hasNext()) {
	// if (incubee == null) {
	// incubee = new Incubee();
	// }
	// Item item = iterator.next();
	// logger.info("Incubee from DB for company: " + incubee_id + " - "
	// + item.toJSONPretty());
	//
	// incubee.setCompany_name(item.getString("incubee_id"));
	// incubee.setCompany_url(item.getString("company_url"));
	// incubee.setImages((String[]) item.getStringSet("photos").toArray());
	// incubee.setHigh_concept(item.getString("high_concept"));
	// incubee.setDescription(item.getString("description"));
	// incubee.setVideo(item.getString("video"));
	// incubee.setFounder(item.getString("founder"));
	// incubee.setLogo_url(item.getString("logo_url"));
	// incubee.setTwitter_url(item.getString("twitter_url"));
	// incubee.setLocation(item.getString("location"));
	// incubee.setVideo_url(item.getString("video_url"));
	// incubee.setFunding(item.getBoolean("funding"));
	// incubee.setProject_status(item.getString("project_status"));
	// incubee.setField(item.getString("field"));
	//
	// }
	// return incubee;
	// }

}