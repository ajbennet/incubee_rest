package ee.incub.rest.spring.model.http;

import java.util.Arrays;

import ee.incub.rest.spring.model.db.Message;

public class AllMessagesResponse {
	private String statusMessage;
	private String statusCode;
	private Message[] messages;
	public final static String SUCCESS = "MSGS_1000";
	public final static String SEND_FAILED = "MSGS_1003";
	public final static String GET_FAILED = "MSGS_1004";
	public final static String TOKEN_NOT_FOUND = "MSGS_1099";
	
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
	public Message[] getMessages() {
		return messages;
	}
	public void setMessages(Message[] message) {
		this.messages = message;
	}
	@Override
	public String toString() {
		return "AllMessagesResponse [statusMessage=" + statusMessage
				+ ", statusCode=" + statusCode + ", message="
				+ Arrays.toString(messages) + "]";
	}
	
}
