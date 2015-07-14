package ee.incub.rest.spring.model.http;

import java.util.Map;

public class LoginResponse {
	private String statusMessage;
	private String statusCode;
	private Map<String, Object> servicedata;
	public final static String SUCCESS = "LOG_1000";
	public final static String USER_NOT_FOUND = "LOG_1003";
	public final static String TOKEN_NOT_FOUND = "LOG_1099";
	
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
	public Map<String, Object> getServicedata() {
		return servicedata;
	}
	public void setServicedata(Map<String, Object> servicedata) {
		this.servicedata = servicedata;
	}
	
}
