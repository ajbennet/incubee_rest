package ee.incub.rest.spring.model.db;

public class User {
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogin_type() {
		return login_type;
	}
	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * @return the handle_id
	 */
	public String getHandle_id() {
		return handle_id;
	}
	/**
	 * @param handle_id the handle_id to set
	 */
	public void setHandle_id(String handle_id) {
		this.handle_id = handle_id;
	}

	@Override
	public String toString() {
		return "User [handle_id=" + handle_id + ", id=" + id + ", login_type="
				+ login_type + ", token=" + token + ", user_type=" + user_type
				+ ", company_id=" + company_id + ", image_url=" + image_url
				+ ", email=" + email + ", name=" + name + "]";
	}

	private String handle_id;
	private String id;
	private String login_type;
	private String token;
	private String user_type;
	private String company_id;
	private String image_url;
	private String email;
	private String name;
	
}
