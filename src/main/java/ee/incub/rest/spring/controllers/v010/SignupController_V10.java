package ee.incub.rest.spring.controllers.v010;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ee.incub.rest.spring.aws.adaptors.UserDynamoDB;
import ee.incub.rest.spring.model.db.User;
import ee.incub.rest.spring.model.http.LoginResponse;
import ee.incub.rest.spring.model.http.SignupResponse;
import ee.incub.rest.spring.model.http.Token;

/**
 * Handles requests for the application file upload requests
 */
@Controller
public class SignupController_V10 {

	private static final Logger logger = LoggerFactory
			.getLogger(SignupController_V10.class);

	@RequestMapping(value = "/v1.0/login", method = RequestMethod.POST)
	public ResponseEntity<LoginResponse> login(@RequestBody final Token token) {
		// String incubee_id= null;
		logger.info("Recieved Login Request with token : " + token);
		LoginResponse response = new LoginResponse();
		// retreive the user with the id.
		if (token != null && token.getId() != null) {
			User user = UserDynamoDB.getUser(token.getId());
			if (user == null) {
				// create user
				// /boolean createdUser = DynamoDBAdaptor.createUser(token);
				response.setStatusCode(LoginResponse.USER_NOT_FOUND);
				response.setStatusMessage("User not found");
				return new ResponseEntity<LoginResponse>(response,
						HttpStatus.NOT_FOUND);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();

				String user_type = user.getUser_type();
				if (user_type !=null){
					map.put("user_type", user_type);
				}
				else{
					map.put("user_type", "U");
				}
				if (user.isAdmin()){
					response.setStatusCode(LoginResponse.SUCCESS);
					response.setStatusMessage("Success");
					
					if(user.getCompany_id()!=null && !user.getCompany_id().isEmpty()){
						map.put("is_admin", new Boolean(true));
					}
				}else{
				
					response.setStatusCode(LoginResponse.SUCCESS);
					response.setStatusMessage("Success");
					if(user.getCompany_id()!=null && !user.getCompany_id().isEmpty()){
						map.put("company_id", user.getCompany_id());
						
					}
				}
				response.setServicedata(map);
				return new ResponseEntity<LoginResponse>(response,
						HttpStatus.OK);
			}
		}
		// DynamoDBAdaptor.getIncubee(incubee_id);
		response.setStatusCode(LoginResponse.TOKEN_NOT_FOUND);
		response.setStatusMessage("Token not found");
		return new ResponseEntity<LoginResponse>(response,
				HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/v1.0/user", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@RequestParam("uid")  String userid) {
		logger.info("Recieved delete user with id : " + userid);
		try {
			UserDynamoDB.deleteUser(userid);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("User Deleted",
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/v1.0/signup", method = RequestMethod.POST)
	public ResponseEntity<SignupResponse> signup(@RequestBody final Token token) {
		// String incubee_id= null;
		logger.info("Recieved Login Request with token : " + token);
		SignupResponse response = new SignupResponse();
		// retreive the user with the id.
		if (token != null && token.getId() != null) {
			User user = UserDynamoDB.getUser(token.getId());
			if (user == null) {
				boolean createdUser = false;
				String msg = "";
				// create user
				try {
					 createdUser = createUser(token);
					
				} catch (Exception e) {
					logger.error("Error creating User ",e);
					msg = e.getMessage();
				}
				if (createdUser){
					response.setStatusCode(SignupResponse.SUCCESS);
					response.setStatusMessage("User Created");
					return new ResponseEntity<SignupResponse>(response,
							HttpStatus.CREATED);
				}else{
					response.setStatusCode(SignupResponse.SERVER_FAILURE);
					response.setStatusMessage("Server Error : " + msg);
					return new ResponseEntity<SignupResponse>(response,
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				response.setStatusCode(SignupResponse.USER_ALREADY_FOUND);
				response.setStatusMessage("User already found with that User ID please login");
				
				return new ResponseEntity<SignupResponse>(response,
						HttpStatus.CONFLICT);
			}
		}
		response.setStatusCode(SignupResponse.TOKEN_NOT_FOUND);
		response.setStatusMessage("Token not found");
		return new ResponseEntity<SignupResponse>(response,
				HttpStatus.BAD_REQUEST);
	}
	
	private boolean createUser(Token token) throws Exception {
		if (token != null ) {
			logger.info("Creating User with : " + token );
			User user = new User();
			String user_id = "usr_" + UUID.randomUUID().toString();
			user.setId(user_id);
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
			logger.error("Created User Failed - No Token ");
			return false;
		}
	}
	
}
