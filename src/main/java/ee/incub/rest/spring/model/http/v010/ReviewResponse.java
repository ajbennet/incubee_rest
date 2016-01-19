package ee.incub.rest.spring.model.http.v010;


public class ReviewResponse {
	private String statusMessage;
	private String statusCode;
	public final static String SUCCESS = "REV_1000";
	public final static String REVIEW_FAILED = "REV_1003";
	public final static String BAD_REQUEST = "REV_1004";
	public final static String TOKEN_NOT_FOUND = "REV_1099";
	
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
}
