package ee.incub.rest.spring.model.http.v010;

import java.util.List;

public class AdhocIncubeeResponse extends BaseResponse{
	private List<AdhocIncubee> incubeeList;
	
	public final static String SUCCESS = "ADH_1000";
	public final static String ADHOC_INCUBEE_CREATION_FAILED = "ADH_1003";
	public final static String BAD_REQUEST = "ADH_1004";
	public final static String TOKEN_NOT_FOUND = "ADH_1099";
	public final static String GET_FAILED = "ADH_1005";
	
	public List<AdhocIncubee> getIncubeeList() {
		return incubeeList;
	}
	public void setIncubeeList(List<AdhocIncubee> incubeeList) {
		this.incubeeList = incubeeList;
	}
	@Override
	public String toString() {
		return "AdhocIncubeeResponse [incubeeList=" + incubeeList + ", getStatusMessage()=" + getStatusMessage()
				+ ", getStatusCode()=" + getStatusCode() + "]";
	}

}
