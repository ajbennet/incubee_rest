package ee.incub.rest.spring.model.http.v010;

import java.util.Arrays;

public class ReviewDataResponse extends ReviewResponse {

	private ReviewData reviewData;
	

	public ReviewData getReviewData() {
		return reviewData;
	}
	public void setReviewData(ReviewData reviewData) {
		this.reviewData = reviewData;
	}
	@Override
	public String toString() {
		return "ReviewDataResponse [reviewData=" + reviewData + ", getReviews()=" + Arrays.toString(getReviews())
				+ ", toString()=" + super.toString() + ", getStatusMessage()=" + getStatusMessage()
				+ ", getStatusCode()=" + getStatusCode() 
				+ "]";
	}
	
	
}


