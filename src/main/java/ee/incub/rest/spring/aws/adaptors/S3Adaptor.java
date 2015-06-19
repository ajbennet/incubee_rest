package ee.incub.rest.spring.aws.adaptors;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import ee.incub.rest.spring.utils.Constants;

public class S3Adaptor {
	AWSCredentials credentials = null;
	public S3Adaptor() {
		initializeS3();
	}
	
	private void initializeS3(){
		try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (~/.aws/credentials), and is in valid format.",
                    e);
        }
	}
	
	public String uploadFile(MultipartFile file, String prefix ) throws IOException{
		
		AmazonS3 s3 = new AmazonS3Client(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        s3.setRegion(usWest2);
        if (prefix == null){
        	prefix = "img";
        }
        String key = prefix+"_"+UUID.randomUUID().toString();
        System.out.println("Uploading a new file to S3 \n");
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        s3.putObject(new PutObjectRequest(Constants.S3_IMAGE_BUCKET, key, file.getInputStream(),metadata));
		return key;
	}
}
