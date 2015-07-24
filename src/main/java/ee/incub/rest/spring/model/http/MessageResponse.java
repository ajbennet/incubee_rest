package ee.incub.rest.spring.model.http;

import ee.incub.rest.spring.model.db.Message;

public class MessageResponse {
	private String statusMessage;
	private String statusCode;
	private Message message;
	public final static String SUCCESS = "MSG_1000";
	public final static String SEND_FAILED = "MSG_1003";
	public final static String GET_FAILED = "MSG_1004";
	public final static String TOKEN_NOT_FOUND = "MSG_1099";
	
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
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
}
