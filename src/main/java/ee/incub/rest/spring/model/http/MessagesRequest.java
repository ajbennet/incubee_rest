package ee.incub.rest.spring.model.http;

import java.sql.Date;

public class MessagesRequest {
	private Date time;
	private String status;
	private String nickname;
	private String from;
	private String fromName;
	private String to;
	private String body;
	private String type;
	private String dir;
	private int lattitude;
	private int longitude;
	private String media;
}
