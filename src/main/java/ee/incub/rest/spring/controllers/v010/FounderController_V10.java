package ee.incub.rest.spring.controllers.v010;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ee.incub.rest.spring.aws.adaptors.UserDynamoDB;
import ee.incub.rest.spring.aws.adaptors.UserStoreDynamoDB;
import ee.incub.rest.spring.aws.adaptors.S3Adaptor;
import ee.incub.rest.spring.model.db.User;
import ee.incub.rest.spring.model.http.IncubeeRequest;
import ee.incub.rest.spring.model.http.IncubeeResponse;
import ee.incub.rest.spring.model.http.Token;
import ee.incub.rest.spring.model.http.v010.AdhocIncubee;
import ee.incub.rest.spring.model.http.v010.AdhocIncubeeRequest;
import ee.incub.rest.spring.model.http.v010.AdhocIncubeeResponse;
import ee.incub.rest.spring.utils.GoogleVerificationController;
import ee.incub.rest.spring.utils.Utils;

/**
 * Handles requests for the application file upload requests
 */
@Controller
public class FounderController_V10 {

	private static final Logger logger = LoggerFactory.getLogger(FounderController_V10.class);

	@RequestMapping(value = "/v1.0/handle", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> handleRequest(IncubeeRequest incubee) {
		logger.info("Incubee Object" + incubee);
		Token token = null;

		if (incubee.getToken() != null) {
			try {
				token = new ObjectMapper().readValue(incubee.getToken(), Token.class);
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
			return new ResponseEntity<String>("Please sign-in with Google", HttpStatus.UNAUTHORIZED);
		}
		String uuid = "inc_" + UUID.randomUUID().toString();
		User user = UserDynamoDB.getUserForHandle(token.getId());
		if (user == null) {

			boolean isSuccess = false;
			// creating new incubee & user
			isSuccess = createOrUpdateIncubee(incubee, uuid, false);
			if (isSuccess) {
				boolean isCreated = false;
				try {
					isCreated = createUser(token, uuid);
				} catch (Exception e) {
					logger.error("Error Creating User ", e);
					e.printStackTrace();
				}
				if (isCreated) {
					return new ResponseEntity<String>("User & Startup Created", HttpStatus.CREATED);
				} else {
					return new ResponseEntity<String>("Incubee Created, but user creation failed",
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				return new ResponseEntity<String>("Incubee Creation/Updation failed", HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} else {
			if (createOrUpdateIncubee(incubee, uuid, user.isAdmin()))
				return new ResponseEntity<String>("Incubee Updated", HttpStatus.OK);
			else
				return new ResponseEntity<String>("Failed to update Incubee", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/v1.0/all", method = RequestMethod.GET)
	@ResponseBody
	public List<IncubeeResponse> getAll() {
		return Utils.fromIncubeeList(UserDynamoDB.getAllIncubees());
	}

	@RequestMapping(value = "/v1.0/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<IncubeeResponse> getForId(@PathVariable("id") String id) {
		logger.info("Recieved getIncubee for Id: " + id);
		IncubeeResponse response = null;
		if (id.startsWith("adh")){
			response = Utils.fromIncubee(UserDynamoDB.getAdhocIncubee(id));
			if (response!=null)
				return new ResponseEntity<IncubeeResponse>(response, HttpStatus.OK) ;
			else
				return new ResponseEntity<IncubeeResponse>(response, HttpStatus.NOT_FOUND) ;
		}
		
		response =  Utils.fromIncubee(UserDynamoDB.getIncubee(id));
		if (response!=null)
			return new ResponseEntity<IncubeeResponse>(response, HttpStatus.OK) ;
		else
			return new ResponseEntity<IncubeeResponse>(response, HttpStatus.NOT_FOUND) ;
	}
	
	@RequestMapping(value = "/v1.0/adhocincubee", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<AdhocIncubeeResponse> getAllAdhocIncubees(@RequestHeader("token") String token) {
		List<AdhocIncubee> adhocList = null;
		if (token == null || !GoogleVerificationController.verifyToken(token)) {
			AdhocIncubeeResponse adhocResponse = new AdhocIncubeeResponse();
			adhocResponse.setStatusMessage("Token not found");
			adhocResponse.setStatusCode(AdhocIncubeeResponse.TOKEN_NOT_FOUND);
			return new ResponseEntity<AdhocIncubeeResponse>(adhocResponse,
					HttpStatus.UNAUTHORIZED);
		}
		
		try {
			adhocList = UserDynamoDB.getAllAdhocIncubees();
		} catch (Exception e) {
			logger.error("Error creating Review :" + e.getMessage(), e);
			e.printStackTrace();
			AdhocIncubeeResponse adhocResponse = new AdhocIncubeeResponse();
			adhocResponse.setStatusMessage("Create Review Failed");
			adhocResponse.setStatusCode(AdhocIncubeeResponse.GET_FAILED);
			return new ResponseEntity<AdhocIncubeeResponse>(adhocResponse,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		AdhocIncubeeResponse adhocResponse = new AdhocIncubeeResponse();
		adhocResponse.setStatusMessage("Success");
		adhocResponse.setStatusCode(AdhocIncubeeResponse.SUCCESS);
		adhocResponse.setIncubeeList(adhocList);
		return new ResponseEntity<AdhocIncubeeResponse>(adhocResponse,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/v1.0/adhocincubee", method = RequestMethod.POST)
	public ResponseEntity<AdhocIncubeeResponse> createAdhocIncubee(@RequestBody AdhocIncubeeRequest incubee,
			@RequestParam("uid") String uid,
			@RequestHeader("token") String token ) {
		logger.info("Create Adhoc Incubee : " + incubee);

		if (token == null || !GoogleVerificationController.verifyToken(token)) {
			AdhocIncubeeResponse adhocIncResponse = new AdhocIncubeeResponse();
			adhocIncResponse.setStatusMessage("Token not found");
			adhocIncResponse.setStatusCode(AdhocIncubeeResponse.TOKEN_NOT_FOUND);
			return new ResponseEntity<AdhocIncubeeResponse>(adhocIncResponse, HttpStatus.UNAUTHORIZED);
		}
		String uuid = "adh_" + UUID.randomUUID().toString();

		// creating adhoc incubee
		try {
			UserDynamoDB.createAdhocIncubee(uuid, incubee.getName(), incubee.getEmail_id(), uid);
			
			//add like incubee, so it shows up in the investor's dashboard.
			
			try {
				UserStoreDynamoDB.loadLike(uid, uuid);
				logger.info("Added like for the Adhoc Incubee: UID: " + uid + " IncubeeID: " + uuid);
			} catch (Exception e) {
				logger.error("Error adding a like, for the Adhoc Incubee : UID: " + uid + " IncubeeID: " + uuid +" \nError "+ e.getMessage(), e);
				e.printStackTrace();
			}
			
			AdhocIncubeeResponse adhocIncResponse = new AdhocIncubeeResponse();
			adhocIncResponse.setStatusMessage("Success");
			adhocIncResponse.setStatusCode(AdhocIncubeeResponse.SUCCESS);
			AdhocIncubee adhocIncubee = new AdhocIncubee();
			adhocIncubee.setCreated_by_id(uid);
			adhocIncubee.setId(uuid);
			adhocIncubee.setEmail_id( incubee.getEmail_id());
			adhocIncubee.setName(incubee.getName());
			List<AdhocIncubee> list = new ArrayList<AdhocIncubee>();
			list.add(adhocIncubee);
			adhocIncResponse.setIncubeeList(list);
			logger.info("Adhoc Incubee creation successful :" + adhocIncResponse);
			return new ResponseEntity<AdhocIncubeeResponse>(adhocIncResponse, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Adhoc Incubee Creation failed - " + e.getLocalizedMessage(), e);
			AdhocIncubeeResponse adhocIncResponse = new AdhocIncubeeResponse();
			adhocIncResponse.setStatusMessage("Create Review Failed");
			adhocIncResponse.setStatusCode(AdhocIncubeeResponse.ADHOC_INCUBEE_CREATION_FAILED);
			return new ResponseEntity<AdhocIncubeeResponse>(adhocIncResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * 
	 * @param incubee
	 * @param uuid
	 * @param isAdmin
	 *            - Admins can only create startups and can have multiple
	 *            startups under their name
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
		// adding this because multipartfile has a null entry even if no file is
		// uploaded.
		if (keyList != null && keyList.length == 1 && keyList[0] == null) {
			keyList = null;
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

				UserDynamoDB.updateIncubee(Utils.fromIncubeeRequest(incubee, keyList, video, incubee.getId()));

			} else {
				UserDynamoDB.loadIncubee(Utils.fromIncubeeRequest(incubee, keyList, video, uuid));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		// check if user already has a company associated to his account.
		isCreated = true;
		logger.info("Incubee Created/Updated : " + incubee.getCompany_name());
		return isCreated;
	}

	private boolean createUser(Token token, String company_id) throws Exception {
		if (token != null && company_id != null) {
			logger.info("Creating User with : " + token + " for company :" + company_id);
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
