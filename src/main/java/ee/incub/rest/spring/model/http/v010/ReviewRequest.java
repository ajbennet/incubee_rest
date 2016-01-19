package ee.incub.rest.spring.model.http.v010;

public class ReviewRequest {
	
	String incubee_id;
	String title;
	String description;
	int rating;
	String user_id;
	int meeting;
	int status;
	
	
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
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getMeeting() {
		return meeting;
	}
	public void setMeeting(int meeting) {
		this.meeting = meeting;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((incubee_id == null) ? 0 : incubee_id.hashCode());
		result = prime * result + meeting;
		result = prime * result + rating;
		result = prime * result + status;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewRequest other = (ReviewRequest) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (incubee_id == null) {
			if (other.incubee_id != null)
				return false;
		} else if (!incubee_id.equals(other.incubee_id))
			return false;
		if (meeting != other.meeting)
			return false;
		if (rating != other.rating)
			return false;
		if (status != other.status)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ReviewRequest [incubee_id=" + incubee_id + ", title=" + title
				+ ", description=" + description + ", rating=" + rating
				+ ", user_id=" + user_id + ", meeting=" + meeting + ", status="
				+ status + "]";
	}
	
}
