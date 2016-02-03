package ee.incub.rest.spring.model.http.v010;

public class AdhocIncubee {
	
	private String id;
	private String email_id;
	private String name;
	private String created_by_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreated_by_id() {
		return created_by_id;
	}
	public void setCreated_by_id(String created_by_id) {
		this.created_by_id = created_by_id;
	}
	@Override
	public String toString() {
		return "AdhocIncubee [id=" + id + ", email_id=" + email_id + ", name=" + name + ", created_by_id="
				+ created_by_id + "]";
	}

}
