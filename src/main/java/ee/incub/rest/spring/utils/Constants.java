package ee.incub.rest.spring.utils;

public class Constants {
	public static final String DB_TABLE_NAME =Config.getProperty("dynamodb_name");
	public static final String S3_IMAGE_BUCKET = Config.getProperty("s3_bucket");
	public static final String S3_IMAGE_URL = Config.getProperty("s3_url");
}
