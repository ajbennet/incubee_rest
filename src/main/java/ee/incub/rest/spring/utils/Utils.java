package ee.incub.rest.spring.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import ee.incub.rest.spring.model.db.Incubee;
import ee.incub.rest.spring.model.db.Message;
import ee.incub.rest.spring.model.db.Review;
import ee.incub.rest.spring.model.http.IncubeeRequest;
import ee.incub.rest.spring.model.http.IncubeeResponse;
import ee.incub.rest.spring.model.http.MessageRequest;
import ee.incub.rest.spring.model.http.v010.AdhocIncubee;
import ee.incub.rest.spring.model.http.v010.ReviewData;
import ee.incub.rest.spring.model.http.v010.ReviewRequest;

public class Utils {
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	public static ReviewData getReviewData(Review[] reviews){
		if (reviews == null || reviews.length<=0){
			return null;
		}
		ReviewData reviewData = new ReviewData();
		int sum =0;
		for (int i = 0; i < reviews.length; i++) {
			sum =sum+ reviews[i].getRating();
			int[] noOfStars = reviewData.getNoOfStars();
			int starIndex = reviews[i].getRating()>0?reviews[i].getRating()-1:0;
			noOfStars[starIndex]++;
			reviewData.setNoOfStars(noOfStars);					
		}
		reviewData.setAverageRating(sum/reviews.length);
		reviewData.setNoOfRatings(reviews.length);
		return reviewData;
	}
	public static Incubee fromIncubeeRequest(IncubeeRequest request,
			String[] images, String video, String uuid) {
		if (request == null) {
			return null;
		} else {
			Incubee incubee = new Incubee();
			incubee.setCompany_name(request.getCompany_name());
			incubee.setCompany_url(request.getCompany_url());
			incubee.setDescription(request.getDescription());
			incubee.setHigh_concept(request.getHigh_concept());
			incubee.setLogo_url(request.getLogourl());
			incubee.setLocation(request.getLocation());
			incubee.setTwitter_url(request.getTwitter_url());
			incubee.setFounder(request.getFounder());
			incubee.setId(uuid);
			incubee.setContact_email(request.getContact_email());
			incubee.setField(request.getField());
			incubee.setFunding(request.isFunding());
			incubee.setProject_status(request.getProject_status());
			if (images != null) {
				String[] tempImages = new String[images.length];
				for (int i = 0; i < images.length; i++) {
					if (images[i] != null)
						tempImages[i] = Constants.S3_IMAGE_URL + images[i];
				}
				incubee.setImages(tempImages);
			}

			incubee.setVideo((video != null ? Constants.S3_IMAGE_URL + video
					: null));
			return incubee;
		}
	}

	public static IncubeeResponse fromIncubee(Incubee request) {
		if (request == null) {
			return null;
		} else {
			IncubeeResponse incubee = new IncubeeResponse();
			incubee.setId(request.getId());
			incubee.setCompany_name(request.getCompany_name());
			incubee.setCompany_url(request.getCompany_url());
			incubee.setDescription(request.getDescription());
			incubee.setHigh_concept(request.getHigh_concept());
			incubee.setLogo_url(request.getLogo_url());
			incubee.setLocation(request.getLocation());
			incubee.setTwitter_url(request.getTwitter_url());
			incubee.setFounder(request.getFounder());
			incubee.setContact_email(request.getContact_email());
			incubee.setImages(request.getImages());
			incubee.setVideo_url(request.getVideo_url());
			incubee.setVideo(request.getVideo());
			incubee.setProject_status(request.getProject_status());
			incubee.setFunding(request.isFunding());
			incubee.setField(request.getField());
			return incubee;
		}
	}
	
	public static IncubeeResponse fromIncubee(AdhocIncubee request) {
		if (request == null) {
			return null;
		} else {
			IncubeeResponse incubee = new IncubeeResponse();
			incubee.setId(request.getId());
			incubee.setCompany_name(request.getName());
			incubee.setContact_email(request.getEmail_id());
			return incubee;
		}
	}

	public static List<IncubeeResponse> fromIncubeeList(List<Incubee> request) {
		if (request == null) {
			return null;
		} else {
			List<IncubeeResponse> returnList = new ArrayList<IncubeeResponse>();
			for (Iterator<Incubee> iterator = request.iterator(); iterator
					.hasNext();) {
				Incubee incubee = (Incubee) iterator.next();
				returnList.add(fromIncubee(incubee));
			}
			return returnList;
		}
	}

	public static Incubee fromItem(Map<String, AttributeValue> item) {
		if (item != null) {
			Incubee incubee = new Incubee();

			incubee.setCompany_name(item.get("company_name") != null ? item
					.get("company_name").getS() : null);
			incubee.setCompany_url(item.get("company_url") != null ? item.get(
					"company_url").getS() : null);
			incubee.setLogo_url(item.get("logo_url") != null ? item.get(
					"logo_url").getS() : null);
			incubee.setDescription(item.get("description") != null ? item.get(
					"description").getS() : null);
			incubee.setFounder(item.get("founder") != null ? item
					.get("founder").getS() : null);
			incubee.setHigh_concept(item.get("high_concept") != null ? item
					.get("high_concept").getS() : null);
			incubee.setId(item.get("id") != null ? item.get("id").getS() : null);
			incubee.setContact_email(item.get("contact_email") != null ? item
					.get("contact_email").getS() : null);
			incubee.setLocation(item.get("location") != null ? item.get(
					"location").getS() : null);
			incubee.setTwitter_url(item.get("twitter_url") != null ? item.get(
					"twitter_url").getS() : null);
			incubee.setVideo_url(item.get("video_url") != null ? item.get(
					"video_url").getS() : null);
			incubee.setVideo(item.get("video") != null ? item.get("video")
					.getS() : null);
			List<String> photos = item.get("photos") != null ? item.get(
					"photos").getSS() : null;
			if (photos != null) {
				String[] photosStr = new String[photos.size()];
				incubee.setImages(photos.toArray(photosStr));
			}
			incubee.setFunding(item.get("funding") != null ? item
					.get("funding").getBOOL() : null);
			incubee.setField(item.get("field") != null ? item.get("field")
					.getS() : null);
			incubee.setProject_status(item.get("project_status") != null ? item
					.get("project_status").getS() : null);

			logger.debug("Incubee: " + item.toString());
			return incubee;
		}
		return null;
	}
	
	public static AdhocIncubee adhocIncubeeFromItem(Map<String, AttributeValue> item) {
		if (item != null) {
			AdhocIncubee incubee = new AdhocIncubee();

			incubee.setName(item.get("name") != null ? item
					.get("name").getS() : null);
			incubee.setEmail_id(item.get("email_id") != null ? item.get(
					"email_id").getS() : null);
			incubee.setId(item.get("id") != null ? item.get("id").getS() : null);
			incubee.setCreated_by_id(item.get("created_by_id") != null ? item
					.get("created_by_id").getS() : null);
			logger.debug("AdhocIncubee: " + item.toString());
			return incubee;
		}
		return null;
	}
	
	public static AdhocIncubee adhocIncubeeFromItem(Item item) {
		if (item != null) {
			AdhocIncubee incubee = new AdhocIncubee();

			incubee.setName(item.getString("name") != null ? item
					.getString("name") : null);
			incubee.setEmail_id(item.getString("email_id") != null ? item.getString(
					"email_id") : null);
			incubee.setId(item.get("id") != null ? item.getString("id") : null);
			incubee.setCreated_by_id(item.getString("created_by_id") != null ? item
					.getString("created_by_id") : null);
			logger.debug("AdhocIncubee: " + item.toString());
			return incubee;
		}
		return null;
	}


	public static Message messageFromItem(Item item) {
		if (item != null) {
			Message message = new Message();
			message.setMid(item.getString("mid") != null ? item
					.getString("mid") : null);
			message.setBody(item.getString("body") != null ? item
					.getString("body") : null);
			message.setDir(item.getString("dir") != null ? item
					.getString("dir") : null);
			message.setEid(item.getString("eid") != null ? item
					.getString("eid") : null);
			message.setLattitude(item.getInt("lattitude"));
			message.setLongitude(item.getInt("longitude"));
			message.setMedia(item.getString("media") != null ? item
					.getString("media") : null);
			message.setName(item.getString("name") != null ? item
					.getString("name") : null);
			message.setStatus(item.getString("status") != null ? item
					.getString("status") : null);
			try {
				message.setStime(item.getString("stime") != null ? dateFormatter
						.parse(item.getString("stime")) : null);
			} catch (ParseException e) {

				logger.error("Error Parsing date : " + item.getString("stime"),
						e);
				message.setStime(null);
			}
			try {
				message.setTime(item.getString("updated_time") != null ? dateFormatter
						.parse(item.getString("updated_time")) : null);
			} catch (ParseException e) {

				logger.error("Error Parsing date : " + item.getString("updated_time"),
						e);
				message.setTime(null);
			}

			message.setTo(item.getString("to") != null ? item
					.getString("to") : null);
			message.setType(item.getString("type") != null ? item
					.getString("type") : null);
			return message;
		}
		return null;
	}
	
	public static Review reviewFromItem(Item item) {
		if (item != null) {
			Review review = new Review();
			review.setIncubee_id(item.getString("incubee_id") != null ? item
					.getString("incubee_id") : null);
			review.setDescription(item.getString("description") != null ? item
					.getString("description") : null);
			review.setTitle(item.getString("title") != null ? item
					.getString("title") : null);
			review.setRating(item.getInt("rating"));
			review.setLikes(item.isPresent("likes")?item.getInt("likes"):0);
			review.setDislikes(item.isPresent("dislikes")?item.getInt("dislikes"):0);
			review.setReplies(item.isPresent("replies")?item.getInt("replies"):0);
			review.setViews(item.isPresent("views")?item.getInt("views"):0);
			review.setStatus(item.getString("review_status") != null ? item
					.getString("review_status") : null);
			review.setMeeting(item.getString("meeting") != null ? item
							.getString("meeting") : null);
			review.setReview_id(item.getString("review_id")!=null ? item
							.getString("review_id"):null);
			try {
				review.setDate(item.getString("date") != null ? dateFormatter
						.parse(item.getString("date")) : null);
			} catch (ParseException e) {

				logger.error("Error Parsing date : " + item.getString("stime"),
						e);
				review.setDate(null);
			}

		
			review.setUser_id(item.getString("user_id") != null ? item
					.getString("user_id") : null);
			return review;
		}
		return null;
	}
	
	public static Incubee fromItem(Item item) {
		if (item != null) {
			Incubee incubee = new Incubee();

			incubee.setCompany_name(item.getString("company_name") != null ? item
					.getString("company_name") : null);
			incubee.setCompany_url(item.getString("company_url") != null ? item
					.getString("company_url") : null);
			incubee.setLogo_url(item.getString("logo_url") != null ? item
					.getString("logo_url") : null);
			incubee.setDescription(item.getString("description") != null ? item
					.getString("description") : null);
			incubee.setFounder(item.getString("founder") != null ? item
					.getString("founder") : null);
			incubee.setHigh_concept(item.getString("high_concept") != null ? item
					.getString("high_concept") : null);
			incubee.setId(item.getString("id") != null ? item.getString("id")
					: null);
			incubee.setContact_email(item.getString("contact_email") != null ? item
					.getString("contact_email") : null);
			incubee.setLocation(item.getString("location") != null ? item
					.getString("location") : null);
			incubee.setTwitter_url(item.getString("twitter_url") != null ? item
					.getString("twitter_url") : null);
			incubee.setVideo_url(item.getString("video_url") != null ? item
					.getString("video_url") : null);
			incubee.setVideo(item.getString("video") != null ? item
					.getString("video") : null);
			Set<String> photos = item.getStringSet("photos") != null ? item
					.getStringSet("photos") : null;
			if (photos != null) {
				String[] photosStr = new String[photos.size()];
				incubee.setImages(photos.toArray(photosStr));
			}
			incubee.setFunding(item.getBoolean("funding") ? item
					.getBoolean("funding") : false);
			incubee.setField(item.getString("field") != null ? item
					.getString("field") : null);
			incubee.setProject_status(item.getString("project_status") != null ? item
					.getString("project_status") : null);

			logger.debug("Incubee: " + item.toString());
			return incubee;
		}
		return null;
	}
	
	

	public static Message outboundMsgfromMsgRequest(
			MessageRequest messageRequest, String mid) {
		Message message = new Message();
		message.setMid(mid);
		message.setEid(messageRequest.getEid());
		message.setName(messageRequest.getName());
		message.setBody(messageRequest.getBody());
		message.setDir(Message.OUTBOUND);
		message.setLattitude(messageRequest.getLatitude());
		message.setLongitude(messageRequest.getLongitude());
		message.setTime(new Date());
		message.setStime(new Date());
		message.setMedia(messageRequest.getMedia());
		message.setStatus(Message.NEW);
		message.setTo(messageRequest.getTo());
		message.setType(messageRequest.getType());
		return message;
	}

	public static Message inboundMsgfromMsgRequest(
			MessageRequest messageRequest, String mid) {
		Message message = new Message();
		message.setMid(mid);
		// swapping the UID and To fields
		message.setEid(messageRequest.getTo());
		message.setTo(messageRequest.getEid());
		message.setName(messageRequest.getName());
		message.setBody(messageRequest.getBody());
		message.setDir(Message.INBOUND);
		message.setLattitude(messageRequest.getLatitude());
		message.setLongitude(messageRequest.getLongitude());
		message.setTime(new Date());
		message.setStime(new Date());
		message.setMedia(messageRequest.getMedia());
		message.setStatus(Message.NEW);
		message.setType(messageRequest.getType());
		return message;
	}
	
	public static Review reviewFromReviewRequest(
			ReviewRequest reviewRequest, String uid, String uuid) {
		Review review = new Review();
		review.setIncubee_id(reviewRequest.getIncubee_id());
		review.setUser_id(uid);
		review.setTitle(reviewRequest.getTitle());
		review.setDescription(reviewRequest.getDescription());
		review.setRating(reviewRequest.getRating());
		review.setDate(new Date());
		review.setMeeting(reviewRequest.getMeeting());
		review.setStatus(reviewRequest.getStatus());
		review.setLikes(0);
		review.setDislikes(0);
		review.setViews(1);
		review.setReplies(0);
		review.setReview_id(uuid);
		return review;
	}


	public static String setUpdateExpression(boolean isfirst, String string) {
		if (isfirst)
			string = " " + string;
		else
			string = "," + string;
		return string;
	}

	public static final String generateID(String prefix, int size) {
		return prefix.toLowerCase()
				+ RandomStringUtils.randomAlphanumeric(size).toLowerCase();
	}
}
