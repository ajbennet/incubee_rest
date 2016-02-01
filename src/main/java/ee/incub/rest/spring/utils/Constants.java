package ee.incub.rest.spring.utils;

public class Constants {
	public static final String INCUBEE_TABLE =Config.getProperty("incubee_table_name");
	public static final String USER_TABLE =Config.getProperty("user_table_name");
	public static final String S3_IMAGE_BUCKET = Config.getProperty("s3_bucket");
	public static final String S3_IMAGE_URL = Config.getProperty("s3_url");
	public static final String APPS_DOMAIN_NAME = Config.getProperty("apps_domain_name");
	public static final String CLIENT_ID = Config.getProperty("client_id");
	public static final String MESSAGE_TABLE = Config.getProperty("message_table");
	public static final String MID_INDEX = Config.getProperty("mid_index");
	public static final String LIKE_TABLE = Config.getProperty("like_table");
	public static final String CUSTOMER_TABLE = Config.getProperty("customer_table");
	public static final String REVIEW_TABLE = Config.getProperty("review_table");
	public static final String REPLY_TABLE = Config.getProperty("reply_table");
	public static final String EMAIL_USERNAME = Config.getProperty("email_username");
	public static final String EMAIL_PASSWORD = Config.getProperty("email_password");
}
