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

import ee.incub.rest.spring.aws.adaptors.ReviewDynamoDB;
import ee.incub.rest.spring.aws.adaptors.UserStoreDynamoDB;
import ee.incub.rest.spring.model.db.Review;
import ee.incub.rest.spring.model.http.v010.BaseResponse;
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
	public ResponseEntity<BaseResponse> addReview(@RequestBody ReviewRequest reviewRequest,@RequestParam("uid") String uid
			,@RequestHeader("token") String token
			) 
			{
		logger.info("Review Object" + reviewRequest);
		
		if (token == null || !GoogleVerificationController.verifyToken(token)) {
			BaseResponse reviewResponse = new BaseResponse();
			reviewResponse.setStatusMessage("Token not found");
			reviewResponse.setStatusCode(ReviewResponse.TOKEN_NOT_FOUND);
			return new ResponseEntity<BaseResponse>(reviewResponse,
					HttpStatus.UNAUTHORIZED);
		}
		try{
			//checking if the user already has a review for that incubee
			Review review = ReviewDynamoDB.getReviewForIncubeeByUser(reviewRequest.getIncubee_id(), uid);
			
			if (review!=null){
				BaseResponse reviewResponse = new BaseResponse();
				reviewResponse.setStatusMessage("Review for user already found, please update");
				reviewResponse.setStatusCode(ReviewResponse.REVIEW_ALREADY_FOUND);
				return new ResponseEntity<BaseResponse>(reviewResponse,
						HttpStatus.CONFLICT);
			}
				
		} catch (Exception e) {
			logger.error("Error creating Review :" + e.getMessage(), e);
			e.printStackTrace();
			BaseResponse reviewResponse = new BaseResponse();
			reviewResponse.setStatusMessage("Create Review Failed");
			reviewResponse.setStatusCode(ReviewResponse.REVIEW_POST_FAILED);
			return new ResponseEntity<BaseResponse>(reviewResponse,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		Review review = Utils.reviewFromReviewRequest(reviewRequest, uid);
		try {
			ReviewDynamoDB.createReview(review);
		} catch (Exception e) {
			logger.error("Error creating Review :" + e.getMessage(), e);
			e.printStackTrace();
			BaseResponse reviewResponse = new BaseResponse();
			reviewResponse.setStatusMessage("Create Review Failed");
			reviewResponse.setStatusCode(ReviewResponse.REVIEW_POST_FAILED);
			return new ResponseEntity<BaseResponse>(reviewResponse,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		BaseResponse reviewResponse = new BaseResponse();
		reviewResponse.setStatusMessage("Success");
		reviewResponse.setStatusCode(ReviewResponse.SUCCESS);
		
		//Like the incubee if the investor left a review for the user.
		try {
			UserStoreDynamoDB.loadLike(uid, review.getIncubee_id());
			logger.info("Added like for the review: UID: " + uid + " IncubeeID: " + review.getIncubee_id());
		} catch (Exception e) {
			logger.error("Error adding a like, when reviewed : UID: " + uid + " IncubeeID: " +  review.getIncubee_id() +" \nError " + e.getMessage(), e);
			e.printStackTrace();
		}
		
		return new ResponseEntity<BaseResponse>(reviewResponse,
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/v1.0/review/{incubee_id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ReviewResponse> getMessageForMid(@PathVariable("incubee_id") String incubee_id) {
		logger.info("Recieved getReview for incubeeId: " + incubee_id );
		try {
			ReviewResponse reviewResponse = new ReviewResponse();
			reviewResponse.setStatusMessage("Success");
			reviewResponse.setStatusCode(ReviewResponse.SUCCESS);
			reviewResponse.setReviews( ReviewDynamoDB.getReviewsForIncubee(incubee_id));
			reviewResponse.setReviewData(Utils.getReviewData(reviewResponse.getReviews()));
			return new ResponseEntity<ReviewResponse>(reviewResponse,
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception getMessage for Id: " + incubee_id ,e);
			ReviewResponse messageResponse = new ReviewResponse();
			messageResponse.setStatusMessage("Get Message failed");
			messageResponse.setStatusCode(ReviewResponse.GET_FAILED);
			return new ResponseEntity<ReviewResponse>(messageResponse,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
}
