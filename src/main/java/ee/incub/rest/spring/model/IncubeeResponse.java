package ee.incub.rest.spring.model;


public class IncubeeResponse {
	private String companyname;
	private String companyurl;
	private String logourl;
	private String highconcept;
	private String description;
	private String twitterurl;
	private String videourl;
	private String founder;
	private String location;
	private String contactEmail;
	private String[] images;
	
	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanyurl() {
		return companyurl;
	}

	public void setCompanyurl(String companyurl) {
		this.companyurl = companyurl;
	}

	public String getLogourl() {
		return logourl;
	}

	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}

	public String getHighconcept() {
		return highconcept;
	}

	public void setHighconcept(String highconcept) {
		this.highconcept = highconcept;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTwitterurl() {
		return twitterurl;
	}

	public void setTwitterurl(String twitterurl) {
		this.twitterurl = twitterurl;
	}

	public String getVideourl() {
		return videourl;
	}

	public void setVideourl(String videourl) {
		this.videourl = videourl;
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


	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	
	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @param contactEmail the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	
}
