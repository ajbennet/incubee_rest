package ee.incub.rest.spring.aws.adaptors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

import ee.incub.rest.spring.model.db.Message;
import ee.incub.rest.spring.utils.Constants;
import ee.incub.rest.spring.utils.Utils;

public class MessagesDynamoDB {

	
	static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static final Logger logger = LoggerFactory
			.getLogger(MessagesDynamoDB.class);
	static {
		initializeAndCreateTables();
	}

	public static void loadMessage(Message message) throws Exception{

		Table table = DynamoDBHelper.dynamoDB.getTable(Constants.MESSAGE_TABLE);
		if (message == null || message.getMid() == null){
			throw new IllegalArgumentException("Message null or invalid Mid");
		}
		try {

			logger.debug("Adding data to " + Constants.MESSAGE_TABLE);

			Item item = new Item().withPrimaryKey("eid", message.getEid());
			item.withKeyComponent("mid", message.getMid())
			.withKeyComponent("updated_time",dateFormatter.format(new Date()));
			if (message.getBody() != null)
				item.withString("body", message.getBody());
			if (message.getDir() != null)
				item.withString("dir", message.getDir());
			// need to hash this.
			if (message.getLattitude() != 0)
				item.withNumber("lattitude", message.getLattitude());
			if (message.getLongitude() != 0)
				item.withNumber("longitude", message.getLongitude());
			if (message.getMedia() != null)
				item.withString("media", message.getMedia());
			if (message.getName() != null)
				item.withString("name", message.getName());
			if (message.getStatus() != null)
				item.withString("status", message.getStatus());
			if (message.getTo() != null)
				item.withString("to", message.getTo());
			if (message.getType() != null)
				item.withString("type", message.getType());
			item.withString("stime", (dateFormatter.format(new Date())));
			table.putItem(item);
			logger.info("Added data for message : " + message.getMid());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to create item in " + Constants.MESSAGE_TABLE);
			logger.error(e.getMessage());
			throw e;
		}

	}

	public static Message[] getMessagesForUser(String eid) throws Exception {
		Table table = DynamoDBHelper.dynamoDB.getTable(Constants.MESSAGE_TABLE);
		try {
			QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(
					"eid = :eid").withValueMap(new ValueMap()
					.withString(":eid", eid)
					)
			;
			ItemCollection<QueryOutcome> items = table.query(querySpec);
			Iterator<Item> iterator = items.iterator();

			System.out.println("Query: printing results...: ");
			List<Message> messageList = new ArrayList<Message>();
			
			while (iterator.hasNext()) {
				
				Item item = iterator.next();
				logger.info("Message from DB for eid: " + eid + " - "
						+ item.toJSONPretty() );
				messageList.add( Utils.messageFromItem(item));
			}
			return messageList.toArray(new Message[messageList.size()]);
		} catch (AmazonServiceException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	public static Message getMessageForMessageID(String mid, String eid) throws Exception {
		Table table = DynamoDBHelper.dynamoDB.getTable(Constants.MESSAGE_TABLE);
		Index index = table.getIndex(Constants.MID_INDEX);
		try {
			QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(
					"eid = :eid and mid =:mid").withValueMap(new ValueMap()
					.withString(":eid", eid)
					.withString(":mid", mid)
					)
			// .withProjectionExpression("company_url, description, founder, high_concept, location, logo_url, twitter_url, video_url")
			;
			ItemCollection<QueryOutcome> items = index.query(querySpec);
			Iterator<Item> iterator = items.iterator();

			System.out.println("Query: printing results...: ");
			Message message = null;
			while (iterator.hasNext()) {
				
				Item item = iterator.next();
				logger.info("Message from DB for mid: " + mid + " - "
						+ item.toJSONPretty() );
				message = Utils.messageFromItem(item);
			}
			return message;
		} catch (AmazonServiceException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	

	static void initializeAndCreateTables() {

		TableCollection<ListTablesResult> tables = DynamoDBHelper.dynamoDB.listTables();
		Iterator<Table> iterator = tables.iterator();

		logger.debug("Listing table names");
		boolean createMsgTable = true;
		while (iterator.hasNext()) {
			Table table = iterator.next();
			logger.debug("Table : " + table.getTableName());
			if (table.getTableName().equals(Constants.MESSAGE_TABLE)) {
				createMsgTable = false;
			}
		}
		
		if (createMsgTable) {
			createMessagesTable();
		}
	}
	
	static void createMessagesTable() {
		String tableName = Constants.MESSAGE_TABLE;
		try {

			ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition()
				.withAttributeName("eid").withAttributeType("S"));
			attributeDefinitions.add(new AttributeDefinition()
				.withAttributeName("updated_time").withAttributeType("S"));
			attributeDefinitions.add(new AttributeDefinition()
				.withAttributeName("mid").withAttributeType("S"));
			ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
			keySchema.add(new KeySchemaElement().withAttributeName("eid")
					.withKeyType(KeyType.HASH));
			keySchema.add(new KeySchemaElement().withAttributeName("updated_time")
							.withKeyType(KeyType.RANGE));
			CreateTableRequest createTableRequest = new CreateTableRequest()
					.withTableName(tableName)
					.withKeySchema(keySchema)
					.withAttributeDefinitions(attributeDefinitions)
					.withProvisionedThroughput(
							new ProvisionedThroughput().withReadCapacityUnits(
									5L).withWriteCapacityUnits(5L));

			logger.info("Issuing CreateTable request for " + tableName);
			
			ArrayList<KeySchemaElement> indexKeySchema = new ArrayList<KeySchemaElement>();
			indexKeySchema.add(new KeySchemaElement().withAttributeName("eid").withKeyType(KeyType.HASH));
			indexKeySchema.add(new KeySchemaElement().withAttributeName("mid").withKeyType(KeyType.RANGE));

			Projection projection = new Projection().withProjectionType(ProjectionType.ALL);
			
			LocalSecondaryIndex localSecondaryIndex = new LocalSecondaryIndex()
			    .withIndexName(Constants.MID_INDEX).withKeySchema(indexKeySchema).withProjection(projection);
			
			ArrayList<LocalSecondaryIndex> localSecondaryIndexes = new ArrayList<LocalSecondaryIndex>();
			localSecondaryIndexes.add(localSecondaryIndex);
			createTableRequest.setLocalSecondaryIndexes(localSecondaryIndexes);
			
			Table table = DynamoDBHelper.dynamoDB.createTable(createTableRequest);

			logger.debug("Waiting for " + tableName
					+ " to be created...this may take a while...");
			table.waitForActive();

			getTableInformation(tableName);

		} catch (Exception e) {
			logger.error("CreateTable request failed for " + tableName,e);
		}
	}
	
	public static void updateMessage(Message message) throws Exception {

		Table table = DynamoDBHelper.dynamoDB.getTable(Constants.MESSAGE_TABLE);
		if (message == null || message.getDir() == null){
			throw new IllegalArgumentException("Message obect not valid");
		}
		try {
			ValueMap valueMap = new ValueMap();
			valueMap.withString(":dir", message.getDir());
			
			
			String updateExpression = "set dir=:dir ";
			if (message.getMedia()!= null && !message.getMedia().isEmpty()){
				updateExpression = updateExpression + ",media = :media";
				valueMap.with(":company_name", message.getMedia());
			}
			if (message.getName() != null && !message.getName().isEmpty()){
				updateExpression = updateExpression + ",name = :name ";
				valueMap.with(":company_url", message.getName());
			}
			if (message.getStatus() != null && !message.getStatus().isEmpty()){
				updateExpression = updateExpression + ",status = :status ";
				valueMap.with(":status", message.getStatus());
			}
			if (message.getStime() != null){
				updateExpression = updateExpression + ",#stime = :stime ";
				valueMap.with(":stime", dateFormatter.format(message.getStime()));
			}
			if (message.getTime() != null){
				updateExpression = updateExpression + ",updated_time = :time ";
				valueMap.with(":time", dateFormatter.format(message.getTime()));
			}
			if (message.getTo() != null && !message.getTo().isEmpty()){
				updateExpression = updateExpression + ",to = :to ";
				valueMap.with(":to", message.getTo());
			}
			if (message.getBody() != null && !message.getBody().isEmpty()){
				updateExpression = updateExpression + ",body = :body ";
				valueMap.with(":body", message.getBody());
			}
			if (message.getType() != null && !message.getType().isEmpty()){
				updateExpression = updateExpression + ",type =  :type ";
				valueMap.with(":type", message.getType());
			}
			if (message.getEid() != null && !message.getEid().isEmpty()){
				updateExpression = updateExpression + ",uid = :uid ";
				valueMap.with(":uid", message.getEid());
			}
			
			UpdateItemSpec updateItemSpec = new UpdateItemSpec()
					.withPrimaryKey("id", message.getMid())
					.withReturnValues(ReturnValue.ALL_NEW)
					.withUpdateExpression(updateExpression)
					.withValueMap(valueMap);

			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

			// Check the response.
			logger.info("Printing item after updating it");
			logger.info(outcome.getItem().toJSONPretty());

		} catch (Exception e) {
			logger.error("Error updating item in " + Constants.MESSAGE_TABLE, e);
			logger.error(e.getMessage());
			throw e;
		}
	}
	static void getTableInformation(String tableName) {

		System.out.println("Describing " + tableName);

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