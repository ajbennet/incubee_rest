package ee.incub.rest.spring.model.http;


public class MessageRequest {
	private String eid;
	private String name;
	private String to;
	private String body;
	private int latitude;
	private int longitude;
	private String media;
	private String type;
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "MessageRequest [eid=" + eid + ", name=" + name + ", to=" + to
				+ ", body=" + body + ", latitude=" + latitude + ", longitude="
				+ longitude + ", media=" + media + ", type=" + type + "]";
	}
	
}
