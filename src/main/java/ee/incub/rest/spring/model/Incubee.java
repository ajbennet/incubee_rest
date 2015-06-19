package ee.incub.rest.spring.model;

import java.util.Arrays;



public class Incubee {
	private String company_name;
	private String company_url;
	private String logo_url;
	private String high_concept;
	private String description;
	private String twitter_url;
	private String video_url;
	private String founder;
	private String location;
	private String id;
	private String contact_email;
	private String[] images;
	private String video;
	private boolean funding;
	private String project_status;
	private String field;
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
	public String getLogo_url() {
		return logo_url;
	}
	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
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
	public String getId() {
		return id;
	}
	public void setId(String uuid) {
		this.id = uuid;
	}
	
	public String getContact_email() {
		return contact_email;
	}
	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}
	public String[] getImages() {
		return images;
	}
	public void setImages(String[] images) {
		this.images = images;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public boolean isFunding() {
		return funding;
	}
	public void setFunding(boolean funding) {
		this.funding = funding;
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
	@Override
	public String toString() {
		return "Incubee [company_name=" + company_name + ", company_url="
				+ company_url + ", logo_url=" + logo_url + ", high_concept="
				+ high_concept + ", description=" + description
				+ ", twitter_url=" + twitter_url + ", video_url=" + video_url
				+ ", founder=" + founder + ", location=" + location + ", uuid="
				+ id + ", contact_email="
				+ contact_email + ", images=" + Arrays.toString(images)
				+ ", video=" + video + ", funding=" + funding
				+ ", project_status=" + project_status + ", field=" + field
				+ "]";
	}
	
}
