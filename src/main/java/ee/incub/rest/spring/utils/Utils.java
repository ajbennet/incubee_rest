package ee.incub.rest.spring.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ee.incub.rest.spring.model.Incubee;
import ee.incub.rest.spring.model.IncubeeRequest;
import ee.incub.rest.spring.model.IncubeeResponse;

public class Utils {
	
	public static Incubee fromIncubeeRequest(IncubeeRequest request, String[] images, String video, String uuid){
		if (request == null){
			return null;
		}else {
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
			if (images!=null ){
				String[] tempImages = new String[images.length];
				for (int i = 0; i < images.length; i++) {
					tempImages[i]=Constants.S3_IMAGE_URL+images[i];
				}
				incubee.setImages(tempImages);
			}
			incubee.setVideo(Constants.S3_IMAGE_URL+video);
			return incubee;	
		}
	}
	
	public static IncubeeResponse fromIncubee(Incubee request){
		if (request == null){
			return null;
		}else {
			IncubeeResponse incubee = new IncubeeResponse();
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
	
	public static List<IncubeeResponse> fromIncubeeList(List<Incubee> request){
		if (request == null){
			return null;
		}else {
			List<IncubeeResponse> returnList= new ArrayList<IncubeeResponse>();
			for (Iterator<Incubee> iterator = request.iterator(); iterator.hasNext();) {
				Incubee incubee = (Incubee) iterator.next();
				returnList.add(fromIncubee(incubee));
			}
			return returnList;
		}
	}
	
}
