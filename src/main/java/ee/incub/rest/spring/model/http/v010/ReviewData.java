package ee.incub.rest.spring.model.http.v010;

public class ReviewData{
	private int noOfRatings;
	private double averageRating;
	private int[] noOfStars = new int[5];
	public int getNoOfRatings() {
		return noOfRatings;
	}
	public void setNoOfRatings(int noOfRatings) {
		this.noOfRatings = noOfRatings;
	}
	public double getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
	public int[] getNoOfStars() {
		return noOfStars;
	}
	public void setNoOfStars(int[] noOfStars) {
		this.noOfStars = noOfStars;
	}
	
}
