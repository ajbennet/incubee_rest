package ee.incub.rest.spring.model;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class IncubeeRequest {
	private String company_name;
	private String company_url;
	private String logourl;
	private String high_concept;
	private String description;
	private String twitter_url;
	private String video_url;
	private String founder;
	private String location;
	private String contact_email;
	private User user;
	private String project_status;
	private String field;
	private boolean funding;

	private MultipartFile[] images;
	private MultipartFile video;
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_url() {
		return company_url;
	}
	public void setCompany_url(String company_url) {
		this.company_url = company_url;
	}
	public String getLogourl() {
		return logourl;
	}
	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}
	public String getHigh_concept() {
		return high_concept;
	}
	public void setHigh_concept(String high_concept) {
		this.high_concept = high_concept;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTwitter_url() {
		return twitter_url;
	}
	public void setTwitter_url(String twitter_url) {
		this.twitter_url = twitter_url;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public String getFounder() {
		return founder;
	}
	public void setFounder(String founder) {
		this.founder = founder;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContact_email() {
		return contact_email;
	}
	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getProject_status() {
		return project_status;
	}
	public void setProject_status(String project_status) {
		this.project_status = project_status;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public boolean isFunding() {
		return funding;
	}
	public void setFunding(boolean funding) {
		this.funding = funding;
	}
	public MultipartFile[] getImages() {
		return images;
	}
	public void setImages(MultipartFile[] images) {
		this.images = images;
	}
	public MultipartFile getVideo() {
		return video;
	}
	public void setVideo(MultipartFile video) {
		this.video = video;
	}
	@Override
	public String toString() {
		return "IncubeeRequest [company_name=" + company_name
				+ ", company_url=" + company_url + ", logourl=" + logourl
				+ ", high_concept=" + high_concept + ", description="
				+ description + ", twitter_url=" + twitter_url + ", video_url="
				+ video_url + ", founder=" + founder + ", location=" + location
				+ ", contact_email=" + contact_email + ", user=" + user
				+ ", project_status=" + project_status + ", field=" + field
				+ ", funding=" + funding + ", images="
				+ Arrays.toString(images) + ", video=" + video + "]";
	}
	
	
}
