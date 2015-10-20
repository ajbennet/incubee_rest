package ee.incub.rest.spring.controllers;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ee.incub.rest.spring.aws.adaptors.UserStoreDynamoDB;
import ee.incub.rest.spring.model.http.CustomerResponse;
import ee.incub.rest.spring.model.http.LikeResponse;
import ee.incub.rest.spring.utils.GoogleVerificationController;

/**
 * Handles requests for the application file upload requests
 */
@Controller
public class UserStoreController {

	private static final Logger logger = LoggerFactory
			.getLogger(UserStoreController.class);

	@RequestMapping(value = "/like/{incubeeId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> like(@PathVariable("incubeeId") String incubeeId,
				@RequestParam("uid") String uid
				,@RequestHeader("token") String token) {
		logger.info("Like Incubee request recieved for" + incubeeId +" : " + uid);

		

		if (token == null || !GoogleVerificationController.verifyToken(token)) {
			return new ResponseEntity<String>("Please sign-in with Google",
					HttpStatus.UNAUTHORIZED);
		}
		try {
			if (UserStoreDynamoDB.loadLike(uid, incubeeId))
					return new ResponseEntity<String>("{  \"statusMessage\":\"Success\","
							+ "\"statusCode\":\"LIK_1000\"}", HttpStatus.OK);
			else
					return new ResponseEntity<String>("{  \"statusMessage\":\"Server Error\","
							+ "\"statusCode\":\"LIK_1003\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ResponseEntity<String>("{  \"statusMessage\":\"Success\","
					+ "\"statusCode\":\"LIK_1003\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@RequestMapping(value = "/customer/{incubeeId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> customer(@PathVariable("incubeeId") String incubeeId,
				@RequestParam("uid") String uid
				,@RequestHeader("token") String token) {
		logger.info("Customer Incubee request recieved for" + incubeeId +" : " + uid);

		if (token == null || !GoogleVerificationController.verifyToken(token)) {
			return new ResponseEntity<String>("Please sign-in with Google",
					HttpStatus.UNAUTHORIZED);
		}
		try {
			if (UserStoreDynamoDB.loadCustomer(uid, incubeeId))
					return new ResponseEntity<String>("{  \"statusMessage\":\"Success\","
							+ "\"statusCode\":\"CUS_1000\"}", HttpStatus.OK);
			else
					return new ResponseEntity<String>("{  \"statusMessage\":\"Server Error\","
							+ "\"statusCode\":\"CUS_1003\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ResponseEntity<String>("{  \"statusMessage\":\"Success\","
					+ "\"statusCode\":\"CUS_1003\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@RequestMapping(value = "/like", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<LikeResponse> getLikesForId(@RequestParam("id") String id) {
		logger.info("Recieved getLikes for Id: " + id);
		String [] incubeeList = UserStoreDynamoDB.getLikedIncubees(id);
		if (incubeeList!=null){
			LikeResponse response = new LikeResponse();
			response.setStatusCode(LikeResponse.SUCCESS);
			response.setStatusMessage("Success");
			response.setIncubeeList(incubeeList);
			return new ResponseEntity<LikeResponse>(response, HttpStatus.OK);
		}else{
			LikeResponse response = new LikeResponse();
			response.setStatusCode(LikeResponse.NO_LIKES_YET);
			response.setStatusMessage("Looks like you have no likes yet");
			return new ResponseEntity<LikeResponse>(response, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<CustomerResponse> getCustomersForId(@RequestParam("id") String id) {
		logger.info("Recieved getCustomers for Id: " + id);
		String [] incubeeList = UserStoreDynamoDB.getCustomeredUsersforIncubees(id);
		if (incubeeList!=null){
			CustomerResponse response = new CustomerResponse();
			response.setStatusCode(CustomerResponse.SUCCESS);
			response.setStatusMessage("Success");
			response.setIncubeeList(incubeeList);
			return new ResponseEntity<CustomerResponse>(response, HttpStatus.OK);
		}else{
			CustomerResponse response = new CustomerResponse();
			response.setStatusCode(CustomerResponse.NO_CUSTOMERS_YET);
			response.setStatusMessage("Looks like you have no likes yet");
			return new ResponseEntity<CustomerResponse>(response, HttpStatus.OK);
		}
	}	
}
