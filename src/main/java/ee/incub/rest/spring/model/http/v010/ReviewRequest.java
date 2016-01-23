package ee.incub.rest.spring.model.http.v010;

public class ReviewRequest {
	
	String incubee_id;
	String title;
	String description;
	int rating;
	String meeting;
	String status;
	
	
	public String getMeeting() {
		return meeting;
	}
	public void setMeeting(String meeting) {
		this.meeting = meeting;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIncubee_id() {
		return incubee_id;
	}
	public void setIncubee_id(String incubee_id) {
		this.incubee_id = incubee_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "ReviewRequest [incubee_id=" + incubee_id + ", title=" + title
				+ ", description=" + description + ", rating=" + rating
				+ ", meeting=" + meeting + ", status="
				+ status + "]";
	}
	
}
