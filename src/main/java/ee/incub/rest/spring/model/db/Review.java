package ee.incub.rest.spring.model.db;

import java.util.Date;

public class Review {
	
	String incubee_id;
	String title;
	String description;
	int rating;
	String user_id;
	String meeting;
	String status;
	Date date;
	int replies;
	int views;
	int likes;
	int dislikes;
	String review_id;
	
	public String getReview_id() {
		return review_id;
	}
	public void setReview_id(String review_id) {
		this.review_id = review_id;
	}
	public static final String INPERSON_MEET ="PER";
	public static final String TELEPHONIC_MEET = "TEL";
	public static final String INTERESTED = "INT";
	public static final String INVESTED = "INV";
	public static final String PASSED = "PAS";
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMeeting() {
		return meeting;
	}
	public void setMeeting(String meeting) {
		this.meeting = meeting;
	}
	
	public int getReplies() {
		return replies;
	}
	public void setReplies(int replies) {
		this.replies = replies;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getDislikes() {
		return dislikes;
	}
	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
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
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Review [incubee_id=" + incubee_id + ", title=" + title + ", description=" + description + ", rating="
				+ rating + ", user_id=" + user_id + ", meeting=" + meeting + ", status=" + status + ", date=" + date
				+ ", replies=" + replies + ", views=" + views + ", likes=" + likes + ", dislikes=" + dislikes
				+ ", review_id=" + review_id + "]";
	}
	
	
}
