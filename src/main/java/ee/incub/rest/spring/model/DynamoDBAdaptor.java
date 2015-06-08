package ee.incub.rest.spring.model;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import ee.incub.rest.spring.utils.Constants;

public class DynamoDBAdaptor {

    static DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
            new ProfileCredentialsProvider()));
    static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final Logger logger = LoggerFactory
			.getLogger(DynamoDBAdaptor.class);
    
    
    public static void loadIncubee(Incubee incubee) {

        Table table = dynamoDB.getTable(Constants.DB_TABLE_NAME);

        try {

            logger.debug("Adding data to " + Constants.DB_TABLE_NAME);

            Item item = new Item()
                .withPrimaryKey("incubee_id", incubee.getCompanyname())
                .withString("company_url", incubee.getCompanyurl())
                .withString("password", incubee.getPassword()) // need to hash this.
                .withStringSet("photos",
                    new HashSet<String>(Arrays.asList("test")))
                .withString("high_concept", incubee.getHighconcept())
                .withString("description", incubee.getDescription())
                .withString("username", incubee.getUsername())
                .withString("founder", incubee.getFounder())
                .withString("logo_url",incubee.getLogourl())
                .withString("twitter_url",incubee.getTwitterurl())
                .withString("location",incubee.getLocation())
                .withString("video_url", incubee.getVideourl())
                .withString("added_time", (dateFormatter.format(new Date())))
            	.withString("updated_time",(dateFormatter.format(new Date())))
                ;
            table.putItem(item);
            logger.info("Added data for company :" + incubee.getCompanyname());
            System.out.println("Added data for company :" + incubee.getCompanyname());
        } catch (Exception e) {
            e.printStackTrace();
        	logger.error("Failed to create item in " + Constants.DB_TABLE_NAME);
            logger.error(e.getMessage());
        }

    }


}