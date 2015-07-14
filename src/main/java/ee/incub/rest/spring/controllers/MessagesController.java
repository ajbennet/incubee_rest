package ee.incub.rest.spring.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ee.incub.rest.spring.aws.adaptors.UserDynamoDB;
import ee.incub.rest.spring.aws.adaptors.S3Adaptor;
import ee.incub.rest.spring.model.db.User;
import ee.incub.rest.spring.model.http.IncubeeRequest;
import ee.incub.rest.spring.model.http.IncubeeResponse;
import ee.incub.rest.spring.model.http.LoginResponse;
import ee.incub.rest.spring.model.http.Token;
import ee.incub.rest.spring.utils.Utils;

/**
 * Handles requests for the application file upload requests
 */
@Controller
public class MessagesController {

	private static final Logger logger = LoggerFactory
			.getLogger(MessagesController.class);


	@RequestMapping(value = "/msg", method = RequestMethod.POST)
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

		if (token == null || !GoogleVerificationController.verifyToken(token)) {
			return new ResponseEntity<String>("Please sign-in with Google",
					HttpStatus.UNAUTHORIZED);
		}
		String uuid = "inc_" + UUID.randomUUID().toString();
		User user = UserDynamoDB.getUserForHandle(token.getId());
		if (user == null) {
		
			return new ResponseEntity<String>("User & Startup Created", HttpStatus.CREATED);
		} else {
			
			return new ResponseEntity<String>("Incubee Updated", HttpStatus.OK);
		}
	}

	
	

}
