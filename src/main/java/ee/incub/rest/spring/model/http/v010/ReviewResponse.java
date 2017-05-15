package ee.incub.rest.spring.model.http.v010;

import java.util.Arrays;

import ee.incub.rest.spring.model.db.Review;


public class ReviewResponse extends BaseResponse {

	private Review[] reviews;
	public final static String SUCCESS = "REV_1000";
	public final static String REVIEW_POST_FAILED = "REV_1003";
	public final static String BAD_REQUEST = "REV_1004";
	public final static String TOKEN_NOT_FOUND = "REV_1099";
	public final static String GET_FAILED = "REV_1005";
	public final static String REVIEW_ALREADY_FOUND = "REV_1006";
	public final static String REVIEW_NOT_FOUND = "REV_1007";
	public final static String REVIEW_PUT_FAILED = "REV_1008";
	

	
	public Review[] getReviews() {
		return reviews;
	}
	public void setReviews(Review[] reviews) {
		this.reviews = reviews;
	}
	@Override
	public String toString() {
		return "ReviewResponse [reviews=" + Arrays.toString(reviews) + ", getStatusMessage()=" + getStatusMessage()
				+ ", getStatusCode()=" + getStatusCode() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
}


