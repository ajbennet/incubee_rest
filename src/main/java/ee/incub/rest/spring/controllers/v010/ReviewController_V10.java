package ee.incub.rest.spring.controllers.v010;


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

import ee.incub.rest.spring.aws.adaptors.MessagesDynamoDB;
import ee.incub.rest.spring.aws.adaptors.ReviewDynamoDB;
import ee.incub.rest.spring.model.db.Review;
import ee.incub.rest.spring.model.http.MessageResponse;
import ee.incub.rest.spring.model.http.v010.ReviewRequest;
import ee.incub.rest.spring.model.http.v010.ReviewResponse;
import ee.incub.rest.spring.utils.GoogleVerificationController;
import ee.incub.rest.spring.utils.Utils;

/**
 * Handles requests for the application Messages Requests
 */
@Controller
public class ReviewController_V10 {

	private static final Logger logger = LoggerFactory
			.getLogger(ReviewController_V10.class);

	@RequestMapping(value = "/v1.0/review", method = RequestMethod.POST)
	public ResponseEntity<ReviewResponse> addReview(@RequestBody ReviewRequest reviewRequest
			,@RequestHeader("token") String token
			) 
			{
		logger.info("Review Object" + reviewRequest);
		
		if (token == null || !GoogleVerificationController.verifyToken(token)) {
			ReviewResponse reviewResponse = new ReviewResponse();
			reviewResponse.setStatusMessage("Token not found");
			reviewResponse.setStatusCode(ReviewResponse.TOKEN_NOT_FOUND);
			return new ResponseEntity<ReviewResponse>(reviewResponse,
					HttpStatus.UNAUTHORIZED);
		}
		Review review = Utils.reviewFromReviewRequest(reviewRequest);
		try {
			ReviewDynamoDB.createReview(review);
		} catch (Exception e) {
			logger.error("Error creating Review :" + e.getMessage(), e);
			e.printStackTrace();
			ReviewResponse reviewResponse = new ReviewResponse();
			reviewResponse.setStatusMessage("Create Review Failed");
			reviewResponse.setStatusCode(ReviewResponse.REVIEW_FAILED);
			return new ResponseEntity<ReviewResponse>(reviewResponse,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		ReviewResponse reviewResponse = new ReviewResponse();
		reviewResponse.setStatusMessage("Success");
		reviewResponse.setStatusCode(ReviewResponse.SUCCESS);
		return new ResponseEntity<ReviewResponse>(reviewResponse,
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/v1.0/review/{mid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<MessageResponse> getMessageForMid(@PathVariable("mid") String mid, @RequestParam("eid") String eid) {
		logger.info("Recieved getReview for incubeeId: " + mid + " for eid :" + eid);
		try {
			MessageResponse messageResponse = new MessageResponse();
			messageResponse.setStatusMessage("Success");
			messageResponse.setStatusCode(MessageResponse.SUCCESS);
			messageResponse.setMessage( MessagesDynamoDB.getMessageForMessageID(mid, eid));
			return new ResponseEntity<MessageResponse>(messageResponse,
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception getMessage for Id: " + mid + " for eid :" + eid,e);
			MessageResponse messageResponse = new MessageResponse();
			messageResponse.setStatusMessage("Get Message failed");
			messageResponse.setStatusCode(MessageResponse.GET_FAILED);
			return new ResponseEntity<MessageResponse>(messageResponse,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
