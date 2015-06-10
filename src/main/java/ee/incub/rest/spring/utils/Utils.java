package ee.incub.rest.spring.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ee.incub.rest.spring.model.Incubee;
import ee.incub.rest.spring.model.IncubeeRequest;
import ee.incub.rest.spring.model.IncubeeResponse;

public class Utils {
	
	public static Incubee fromIncubeeRequest(IncubeeRequest request, String[] images, String uuid){
		if (request == null){
			return null;
		}else {
			Incubee incubee = new Incubee();
			incubee.setCompanyname(request.getCompanyname());
			incubee.setCompanyurl(request.getCompanyurl());
			incubee.setDescription(request.getDescription());
			incubee.setHighconcept(request.getHighconcept());
			incubee.setLogourl(request.getLogourl());
			incubee.setLocation(request.getLocation());
			incubee.setTwitterurl(request.getTwitterurl());
			incubee.setFounder(request.getFounder());
			incubee.setUUID(uuid);
			incubee.setContactemail(request.getContactemail());
			incubee.setUsername(request.getUsername());
			if (images!=null ){
				String[] tempImages = new String[images.length];
				for (int i = 0; i < images.length; i++) {
					tempImages[i]=Constants.S3_IMAGE_URL+images[i];
				}
				incubee.setImages(tempImages);
			}
			return incubee;	
		}
	}
	
	public static IncubeeResponse fromIncubee(Incubee request){
		if (request == null){
			return null;
		}else {
			IncubeeResponse incubee = new IncubeeResponse();
			incubee.setCompanyname(request.getCompanyname());
			incubee.setCompanyurl(request.getCompanyurl());
			incubee.setDescription(request.getDescription());
			incubee.setHighconcept(request.getHighconcept());
			incubee.setLogourl(request.getLogourl());
			incubee.setLocation(request.getLocation());
			incubee.setTwitterurl(request.getTwitterurl());
			incubee.setFounder(request.getFounder());
			incubee.setContactEmail(request.getContactemail());
			incubee.setImages(request.getImages());
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
