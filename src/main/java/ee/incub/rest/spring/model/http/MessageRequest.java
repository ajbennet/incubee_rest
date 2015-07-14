package ee.incub.rest.spring.model.http;

public class MessageRequest {

	private String from;
	private String fromName;
	private String to;
	private String body;
	private int longitude;
	private int latitude;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "MessageRequest [from=" + from + ", fromName=" + fromName
				+ ", to=" + to + ", body=" + body + ", longitude=" + longitude
				+ ", latitude=" + latitude + "]";
	}

}
