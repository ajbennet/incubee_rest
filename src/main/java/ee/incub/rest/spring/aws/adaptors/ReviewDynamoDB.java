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
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
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
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

import ee.incub.rest.spring.model.db.Incubee;
import ee.incub.rest.spring.model.db.Message;
import ee.incub.rest.spring.model.db.Review;
import ee.incub.rest.spring.model.db.User;
import ee.incub.rest.spring.model.http.Customer;
import ee.incub.rest.spring.utils.Constants;
import ee.incub.rest.spring.utils.Utils;

public class ReviewDynamoDB {

	
	static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static final Logger logger = LoggerFactory
			.getLogger(ReviewDynamoDB.class);
	static {
		initializeAndCreateTables();
	}

	
	public static boolean createReview(Review review) throws Exception {

		Table table = DynamoDBHelper.dynamoDB.getTable(Constants.REVIEW_TABLE);
		
		try {

			logger.debug("Adding data to " + Constants.REVIEW_TABLE);

			Item item = new Item()
					.withPrimaryKey("incubee_id", review.getIncubee_id())
					.withString("user_id", review.getUser_id())
					.withString("review_id", review.getReview_id())
					.withString("title", review.getTitle())
					.withString("description", review.getDescription())
					.withString("date", (dateFormatter.format(new Date())))
					.withNumber("likes", review.getLikes())
					.withString("meeting", review.getMeeting())
					.withString("review_status", review.getStatus())
					.withNumber("rating", review.getRating())
					.withNumber("dislikes", review.getDislikes())
					.withNumber("replies", review.getLikes())
					.withNumber("views", review.getViews());
					
			table.putItem(item);
			logger.info("Added Review  :" + review);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to create item in " + Constants.REVIEW_TABLE);
			logger.error(e.getMessage());
			throw e;
		}

	}
	
	
	static void initializeAndCreateTables() {

		TableCollection<ListTablesResult> tables = DynamoDBHelper.dynamoDB.listTables();
		Iterator<Table> iterator = tables.iterator();

		logger.debug("Listing table names");
		boolean createReviewTable = true;
		while (iterator.hasNext()) {
			Table table = iterator.next();
			logger.debug("Table : " + table.getTableName());
			if (table.getTableName().equals(Constants.REVIEW_TABLE)) {
				createReviewTable = false;
			}
		}
		if (createReviewTable) {
			createReviewTable();
		}
	}

	static void createReviewTable() {
		String tableName = Constants.REVIEW_TABLE;
		try {

			ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("incubee_id").withAttributeType("S"));
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("user_id").withAttributeType("S"));
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("review_id").withAttributeType("S"));
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("date").withAttributeType("S"));

			CreateTableRequest createTableRequest = new CreateTableRequest()
					.withTableName(tableName);

			// ProvisionedThroughput
			createTableRequest
					.setProvisionedThroughput(new ProvisionedThroughput()
							.withReadCapacityUnits((long) 2)
							.withWriteCapacityUnits((long) 2));

			createTableRequest.setAttributeDefinitions(attributeDefinitions);

			// KeySchema
			ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<KeySchemaElement>();
			tableKeySchema.add(new KeySchemaElement().withAttributeName(
					"review_id").withKeyType(KeyType.HASH)); // Partition key
//			tableKeySchema.add(new KeySchemaElement()
//					.withAttributeName("incubee_id").withKeyType(KeyType.RANGE)); // Sort
			
			createTableRequest.setKeySchema(tableKeySchema);

			ArrayList<KeySchemaElement> userIdIndexKeySchema = new ArrayList<KeySchemaElement>();
			userIdIndexKeySchema.add(new KeySchemaElement().withAttributeName(
					"user_id").withKeyType(KeyType.HASH)); // Partition key
			userIdIndexKeySchema.add(new KeySchemaElement().withAttributeName("date")
					.withKeyType(KeyType.RANGE)); // Sort key
			
			ArrayList<KeySchemaElement> reviewIdKeySchema = new ArrayList<KeySchemaElement>();
			reviewIdKeySchema.add(new KeySchemaElement().withAttributeName(
					"incubee_id").withKeyType(KeyType.HASH)); // Partition key
			reviewIdKeySchema.add(new KeySchemaElement().withAttributeName("user_id")
					.withKeyType(KeyType.RANGE)); // Sort key

			Projection projection = new Projection()
					.withProjectionType(ProjectionType.INCLUDE);
			ArrayList<String> nonKeyAttributes = new ArrayList<String>();
			nonKeyAttributes.add("rating");
			nonKeyAttributes.add("description");
			nonKeyAttributes.add("title");
			nonKeyAttributes.add("review_status");
			nonKeyAttributes.add("meeting");
			nonKeyAttributes.add("date");
			projection.setNonKeyAttributes(nonKeyAttributes);

			GlobalSecondaryIndex globalSecondaryUserIdIndex = new GlobalSecondaryIndex()
					.withIndexName(Constants.REVIEW_USERID_INDEX )
					.withKeySchema(userIdIndexKeySchema).withProjection(projection);

			globalSecondaryUserIdIndex
					.setProvisionedThroughput(new ProvisionedThroughput()
							.withReadCapacityUnits((long) 2)
							.withWriteCapacityUnits((long) 2));
			
			GlobalSecondaryIndex globalSecondaryIncubeeIdIndex = new GlobalSecondaryIndex()
					.withIndexName(Constants.REVIEW_INCUBEEID_INDEX)
					.withKeySchema(reviewIdKeySchema).withProjection(projection);

			globalSecondaryIncubeeIdIndex
					.setProvisionedThroughput(new ProvisionedThroughput()
							.withReadCapacityUnits((long) 2)
							.withWriteCapacityUnits((long) 2));

			ArrayList<GlobalSecondaryIndex> globalSecondaryIndexes = new ArrayList<GlobalSecondaryIndex>();
			globalSecondaryIndexes.add(globalSecondaryUserIdIndex);
			globalSecondaryIndexes.add(globalSecondaryIncubeeIdIndex);
			createTableRequest
					.setGlobalSecondaryIndexes(globalSecondaryIndexes);

			Table table = DynamoDBHelper.dynamoDB.createTable(createTableRequest);
			logger.info("Creating Table : " + table.getDescription());

			logger.info("Waiting for " + tableName
					+ " to be created...this may take a while...");
			table.waitForActive();

			getTableInformation(tableName);

		} catch (Exception e) {
			logger.error("CreateTable request failed for " + tableName);
			logger.error(e.getMessage());
		}

	}
	
	public static Review[] getReviewsForIncubee(String incubee_id) throws Exception {
		Table table = DynamoDBHelper.dynamoDB.getTable(Constants.REVIEW_TABLE);
		Index index = table.getIndex(Constants.REVIEW_INCUBEEID_INDEX);
		try {
			QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(
					"incubee_id = :incubee_id").withValueMap(new ValueMap()
					.withString(":incubee_id", incubee_id)
					)
			;
			ItemCollection<QueryOutcome> items = index.query(querySpec);
			Iterator<Item> iterator = items.iterator();

			List<Review> reviewList = new ArrayList<Review>();
			
			while (iterator.hasNext()) {
				
				Item item = iterator.next();
				logger.info("Review from DB for incubee_id: " + incubee_id + " - "
						+ item.toJSONPretty() );
				reviewList.add( Utils.reviewFromItem(item));
			}
			return reviewList.toArray(new Review[reviewList.size()]);
		} catch (AmazonServiceException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}
	
	public static Review getReviewForIncubeeByUser(String incubee_id, String uid) throws Exception {
		Table table = DynamoDBHelper.dynamoDB.getTable(Constants.REVIEW_TABLE);
		Index index = table.getIndex(Constants.REVIEW_INCUBEEID_INDEX);
		try {
			QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(
					"incubee_id = :incubee_id and user_id = :user_id").withValueMap(new ValueMap()
					.withString(":incubee_id", incubee_id)
					.withString(":user_id", uid)
					)
			;
			ItemCollection<QueryOutcome> items = index.query(querySpec);
			Iterator<Item> iterator = items.iterator();

			while (iterator.hasNext()) {
				
				Item item = iterator.next();
				logger.info("Review from DB for incubee_id: " + incubee_id + " for user : "+ uid
						+ item.toJSONPretty() );
				return Utils.reviewFromItem(item);
			}
		} catch (AmazonServiceException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return null;
	}
	
	public static Review getReviewForIncubeeByReviewId( String review_id) throws Exception {
		Table table = DynamoDBHelper.dynamoDB.getTable(Constants.REVIEW_TABLE);
		try {
			QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(
					"review_id = :review_id").withValueMap(new ValueMap()
					.withString(":review_id", review_id)
					)
			;
			ItemCollection<QueryOutcome> items = table.query(querySpec);
			Iterator<Item> iterator = items.iterator();

			while (iterator.hasNext()) {
				
				Item item = iterator.next();
				logger.info("Review from DB for review_id : "+ review_id
						+ item.toJSONPretty() );
				return Utils.reviewFromItem(item);
			}
		} catch (AmazonServiceException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return null;
	}
	
	public static void updateReview(Review review) throws Exception {

		Table table = DynamoDBHelper.dynamoDB.getTable(Constants.REVIEW_TABLE);

		/*
		 * .withString("user_id", review.getUser_id())
					.withString("review_id", review.getReview_id())
					.withString("title", review.getTitle())
					.withString("description", review.getDescription())
					.withString("date", (dateFormatter.format(new Date())))
					.withString("meeting", review.getMeeting())
					.withString("status", review.getStatus())
					.withNumber("rating", review.getRating())
					.withNumber("dislikes", review.getDislikes())
					.withNumber("replies", review.getLikes())
					.withNumber("views", review.getViews());
		 */
		try {
			ValueMap valueMap = new ValueMap().withString(":title",
					review.getTitle());

			String updateExpression = "set title=:title ";
			if (review.getDescription() != null
					&& !review.getDescription().isEmpty()) {
				updateExpression = updateExpression
						+ ",description = :description";
				valueMap.with(":description", review.getDescription());
			}
			if (review.getMeeting() != null
					&& !review.getMeeting().isEmpty()) {
				updateExpression = updateExpression
						+ ",meeting = :meeting ";
				valueMap.with(":meeting", review.getMeeting());
			}
			if (review.getStatus() != null
					&& !review.getStatus().isEmpty()) {
				updateExpression = updateExpression + ",review_status = :status ";
				valueMap.with(":status", review.getStatus());
			}
			if (review.getRating() <= 0) {
				updateExpression = updateExpression + ",rating = :rating ";
				valueMap.with(":rating", review.getRating());
			}
			
			UpdateItemSpec updateItemSpec = new UpdateItemSpec()
					.withPrimaryKey("review_id", review.getReview_id())
					.withReturnValues(ReturnValue.ALL_NEW)
					.withUpdateExpression(updateExpression)
					.withValueMap(valueMap);

			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

			// Check the response.
			logger.info("Printing item after updating it");
			logger.info(outcome.getItem().toJSONPretty());

		} catch (Exception e) {
			logger.error("Error updating item in " + Constants.REVIEW_TABLE, e);
			logger.error(e.getMessage());
			throw e;
		}
	}
	public static void deleteReview(String review_id) throws Exception {

		Table table = DynamoDBHelper.dynamoDB.getTable(Constants.REVIEW_TABLE);

		try {

			DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
					.withPrimaryKey("review_id", review_id).withReturnValues(
							ReturnValue.ALL_OLD);

			DeleteItemOutcome outcome = table.deleteItem(deleteItemSpec);

			// Check the response.
			logger.info("Printing item that was deleted...");
			logger.info(outcome.getItem().toJSONPretty());

		} catch (Exception e) {
			logger.error("Error deleting item in " + Constants.REVIEW_TABLE);
			logger.error(e.getMessage());
			throw e;
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

	// public static Incubee getIncubee(String incubee_id) {
	// Table table = DynamoDBHelper.dynamoDB.getTable(Constants.INCUBEE_TABLE);
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