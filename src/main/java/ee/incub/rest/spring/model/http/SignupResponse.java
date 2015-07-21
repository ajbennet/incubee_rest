package ee.incub.rest.spring.model.http;

public class SignupResponse {
		private String statusMessage;
		private String statusCode;
		
		public final static String SUCCESS = "SIGN_1000";
		public final static String SERVER_FAILURE = "SIGN_1003";
		public final static String USER_ALREADY_FOUND = "SIGN_1004";
		public final static String TOKEN_NOT_FOUND = "SIGN_1099";
		
		
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
		@Override
		public String toString() {
			return "SignupResponse [statusMessage=" + statusMessage
					+ ", statusCode=" + statusCode + "]";
		}

		
}
