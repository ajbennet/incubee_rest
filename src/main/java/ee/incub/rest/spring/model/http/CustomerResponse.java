package ee.incub.rest.spring.model.http;

import java.util.Arrays;

public class CustomerResponse {
		private String statusMessage;
		private String statusCode;
		private String[] incubeeList;
		
		public final static String SUCCESS = "CUS_1000";
		public final static String SERVER_FAILURE = "CUS_1003";
		public final static String NO_CUSTOMERS_YET="CUS_1004";
		public final static String TOKEN_NOT_FOUND = "CUS_1099";
		
		
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
		public String[] getIncubeeList() {
			return incubeeList;
		}
		public void setIncubeeList(String[] incubeeList) {
			this.incubeeList = incubeeList;
		}
		@Override
		public String toString() {
			return "CustomerResponse [statusMessage=" + statusMessage
					+ ", statusCode=" + statusCode + ", incubeeList="
					+ Arrays.toString(incubeeList) + "]";
		}
		
}
