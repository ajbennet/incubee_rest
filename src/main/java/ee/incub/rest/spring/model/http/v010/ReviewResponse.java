package ee.incub.rest.spring.model.http.v010;

import java.util.Arrays;

import ee.incub.rest.spring.model.db.Review;


public class ReviewResponse extends BaseResponse {

	private ReviewData reviewData;
	private Review[] reviews;
	public final static String SUCCESS = "REV_1000";
	public final static String REVIEW_POST_FAILED = "REV_1003";
	public final static String BAD_REQUEST = "REV_1004";
	public final static String TOKEN_NOT_FOUND = "REV_1099";
	public final static String GET_FAILED = "REV_1005";
	public final static String REVIEW_ALREADY_FOUND = "REV_1006";
	

	public ReviewData getReviewData() {
		return reviewData;
	}
	public void setReviewData(ReviewData reviewData) {
		this.reviewData = reviewData;
	}
	public Review[] getReviews() {
		return reviews;
	}
	public void setReviews(Review[] reviews) {
		this.reviews = reviews;
	}
	@Override
	public String toString() {
		return "ReviewResponse [reviewData=" + reviewData + ", reviews=" + Arrays.toString(reviews)
				+ ", getStatusMessage()=" + getStatusMessage() + ", getStatusCode()=" + getStatusCode() + "]";
	}
	
}


