package ee.incub.rest.spring.model.http.v010;


public class AdhocIncubeeRequest {
	private String name;
	private String email_id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	@Override
	public String toString() {
		return "AdhocIncubeeRequest [name=" + name + ", email_id=" + email_id + "]";
	}
	
}
