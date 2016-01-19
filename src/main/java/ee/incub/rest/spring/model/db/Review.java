package ee.incub.rest.spring.model.db;

import java.util.Date;

public class Review {
	
	String incubee_id;
	String title;
	String description;
	int rating;
	String user_id;
	int meeting;
	int status;
	Date last_replied_date;
	int replies;
	int views;
	int likes;
	int dislikes;
	
	
	
	
	public Date getLast_replied_date() {
		return last_replied_date;
	}
	public void setLast_replied_date(Date last_replied_date) {
		this.last_replied_date = last_replied_date;
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
	
}
