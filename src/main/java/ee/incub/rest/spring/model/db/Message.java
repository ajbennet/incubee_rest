package ee.incub.rest.spring.model.db;

import java.util.Date;

public class Message {
	public static final String NEW = "NEW";
	public static final String READ = "REA";
	public static final String RECIPIENT_READ = "RRE";
	public static final String RECIPIENT_RECEIVED = "RRV";
	public static final String INBOUND ="I";
	public static final String OUTBOUND = "O";
	public static final String USER_MSG = "USR";
	public static final String INC_MSG = "INC";
	
	private String mid;
	private String to;
	private String eid;
	private Date time;
	private Date stime;
	private String status;
	private String name;
	private String body;
	private String type;
	private String dir;
	private int lattitude;
	private int longitude;
	private String media;
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Date getStime() {
		return stime;
	}
	public void setStime(Date stime) {
		this.stime = stime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public int getLattitude() {
		return lattitude;
	}
	public void setLattitude(int lattitude) {
		this.lattitude = lattitude;
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
	@Override
	public String toString() {
		return "Message [mid=" + mid + ", to=" + to + ", eid=" + eid
				+ ", time=" + time + ", stime=" + stime + ", status=" + status
				+ ", name=" + name + ", body=" + body + ", type=" + type
				+ ", dir=" + dir + ", lattitude=" + lattitude + ", longitude="
				+ longitude + ", media=" + media + "]";
	}
	
}
