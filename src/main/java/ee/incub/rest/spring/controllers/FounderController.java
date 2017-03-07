package ee.incub.rest.spring.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ee.incub.rest.spring.aws.adaptors.UserDynamoDB;
import ee.incub.rest.spring.aws.adaptors.S3Adaptor;
import ee.incub.rest.spring.model.db.User;
import ee.incub.rest.spring.model.http.IncubeeRequest;
import ee.incub.rest.spring.model.http.IncubeeResponse;
import ee.incub.rest.spring.model.http.Token;
import ee.incub.rest.spring.utils.GoogleVerificationController;
import ee.incub.rest.spring.utils.Utils;

/**
 * Handles requests for the application file upload requests
 */
@Controller
public class FounderController {

	private static final Logger logger = LoggerFactory
			.getLogger(FounderController.class);

	@SuppressWarnings("unused")
	@RequestMapping(value = "/handle", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> handleRequest(IncubeeRequest incubee) {
		logger.info("Incubee Object" + incubee);
		Token token = null;

		if (incubee.getToken() != null) {
			try {
				token = new ObjectMapper().readValue(incubee.getToken(),
						Token.class);
			} catch (JsonParseException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}

		if (token == null || !GoogleVerificationController.verifyToken(token.getToken())) {
			return new ResponseEntity<String>("Please sign-in with Google",
					HttpStatus.UNAUTHORIZED);
		}
		String uuid = "inc_" + UUID.randomUUID().toString();
		boolean isSuccess = false;
		User user = UserDynamoDB.getUserForHandle(token.getId());
		// creating new incubee & user
		boolean isAdmin = (user ==null)? false:user.isAdmin();
		isSuccess = createOrUpdateIncubee(incubee, uuid, isAdmin);
		if(isAdmin){
			return new ResponseEntity<String>("{\"statusMessage\":\"Incubee Created for the admin user\",\"statusCode\":\"INC_1000\"}",
				HttpStatus.CREATED);
		}
		if (user == null) {
			if (isSuccess){
				boolean isCreated = false;
				try {
					isCreated = createUser(token, uuid);
				} catch (Exception e) {
					logger.error("Error Creating User ", e);
					e.printStackTrace();
				}
				if (isCreated){
					return new ResponseEntity<String>("{\"statusMessage\":\"User & Incubee created\",\"statusCode\":\"INC_1000\"}",
							HttpStatus.CREATED);
				}else {
					return new ResponseEntity<String>("{\"statusMessage\":\"Incubee created but user creation failed\",\"statusCode\":\"INC_1001\"}",
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				return new ResponseEntity<String>("{\"statusMessage\":\"Incubee creation or updation failed\",\"statusCode\":\"INC_1002\"}",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} else {
			// update user if the user already exists and is not associated with a company
			if (user.getCompany_id()==null || user.getCompany_id().isEmpty()){
				boolean isUpdated = false;
				user.setCompany_id(uuid);
				try {
					isUpdated = UserDynamoDB.updateUser(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (isUpdated){
					return new ResponseEntity<String>("{\"statusMessage\":\"Incubee created and user updated with company information\",\"statusCode\":\"INC_1000\"}", HttpStatus.OK);
				}else{
					return new ResponseEntity<String>("{\"statusMessage\":\"Incubee created but user failed to update\",\"statusCode\":\"INC_1003\"}", HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			else
				return new ResponseEntity<String>("{\"statusMessage\":\"Incubee updated\",\"statusCode\":\"INC_1000\"}", HttpStatus.OK);
		}
		
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public List<IncubeeResponse> getAll() {
		return Utils.fromIncubeeList(UserDynamoDB.getAllIncubees());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public IncubeeResponse getForId(@PathVariable("id") String id) {
		logger.info("Recieved getIncubee for Id: " + id);
		return Utils.fromIncubee(UserDynamoDB.getIncubee(id));
	}
	/**
	 * 
	 * @param incubee
	 * @param uuid
	 * @param isAdmin - Admins can only create startups and can have multiple startups under their name
	 * @return
	 */
	private boolean createOrUpdateIncubee(IncubeeRequest incubee, String uuid, boolean isAdmin) {
		boolean isCreated = false;
		logger.info("Creating new Incubee : " + incubee + " with id :" + uuid);
		String[] keyList = null;
		if (incubee.getImages() != null) {
			S3Adaptor adaptor = new S3Adaptor();
			MultipartFile[] fileList = incubee.getImages();
			keyList = new String[fileList.length];
			for (int i = 0; i < fileList.length; i++) {
				try {
					if (!fileList[i].isEmpty())
						keyList[i] = adaptor.uploadFile(fileList[i], "img");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("S3 Upload Exception :" + e);
				}
			}
		}
		//adding this because multipartfile has a null entry even if no file is uploaded.
		if (keyList!=null && keyList.length==1 && keyList[0]==null){
			keyList=null;
		}
		String video = null;
		if (incubee.getVideo() != null) {
			S3Adaptor adaptor = new S3Adaptor();
			MultipartFile file = incubee.getVideo();
			if (file != null) {
				try {
					if (!file.isEmpty())
						video = adaptor.uploadFile(file, "vid");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("S3 Upload Exception :" + e);
				}
			}
		}
		try {
			if (incubee.getId() != null && !incubee.getId().isEmpty() && !isAdmin) {
	
				UserDynamoDB.updateIncubee(Utils.fromIncubeeRequest(incubee,
						keyList, video, incubee.getId()));
				logger.info("Incubee Updated : " + incubee.getCompany_name());
	
			} else {
				UserDynamoDB.loadIncubee(Utils.fromIncubeeRequest(incubee,
						keyList, video, uuid));
				logger.info("Incubee Created : " + incubee.getCompany_name());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return false;
		}
		
		return true;
	}

	private boolean createUser(Token token, String company_id) throws Exception {
		if (token != null && company_id != null) {
			logger.info("Creating User with : " + token + " for company :"
					+ company_id);
			User user = new User();
			String user_id = "usr_" + UUID.randomUUID().toString();
			user.setId(user_id);
			user.setCompany_id(company_id);
			user.setEmail(token.getEmail());
			user.setImage_url(token.getImage_url());
			user.setName(token.getName());
			user.setToken(token.getToken());
			user.setHandle_id(token.getId());
			user.setLogin_type("google");
			UserDynamoDB.createUser(user);
			logger.info("Created User : " + user_id);
			return true;
		} else {
			logger.error("Created User Failed - No Token or company_id  ");
			return false;
		}
	}

	
}
