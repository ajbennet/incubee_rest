package ee.incub.rest.spring.controllers;


import ee.incub.rest.spring.model.http.Token;

public class GoogleVerificationController {

	public static boolean verifyToken(String token) {
		/*GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
				transport, jsonFactory).setAudience(Arrays.asList(Constants.CLIENT_ID))
				.build();

		// (Receive idTokenString by HTTPS POST)

		GoogleIdToken idToken = verifier.verify(idTokenString);
		if (idToken != null) {
			Payload payload = idToken.getPayload();
			if (payload.getHostedDomain().equals(Constants.APPS_DOMAIN_NAME)
			// If multiple clients access the backend server:
			//		&& Arrays.asList(ANDROID_CLIENT_ID, IOS_CLIENT_ID)
							.contains(payload.getAuthorizedParty())) {
				System.out.println("User ID: " + payload.getSubject());
			} else {
				System.out.println("Invalid ID token.");
			}
		} else {
			System.out.println("Invalid ID token.");
		}*/
		return true;
	}
	
	public static void main(){
		new GoogleVerificationController()
		//.verifyToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IjI4Yzk2MzAxYTVmMDY2OTM4OTVmZmRkMjE0OTBhNGRhMjQwOThmMGMifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwic3ViIjoiMTE0NTIxNzQ3Nzk3ODU0Mjc1MzUzIiwiYXpwIjoiMTA3OTIxODM2OTc1My0zZmc5c291NDBrZHJqYjVoc2ZubTBvbzlqajBkb2s5YS5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsImVtYWlsIjoiYWJpQGluY3ViLmVlIiwiYXRfaGFzaCI6Ijd2UjFRbXU2SEtRbGU5NWd6aTExTFEiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXVkIjoiMTA3OTIxODM2OTc1My0zZmc5c291NDBrZHJqYjVoc2ZubTBvbzlqajBkb2s5YS5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsImhkIjoiaW5jdWIuZWUiLCJpYXQiOjE0MzM5MjI3NTQsImV4cCI6MTQzMzkyNjM1NCwibmFtZSI6IkFiaW5hdGhhYiBKb2huIFJveSBCZW5uZXQiLCJnaXZlbl9uYW1lIjoiQWJpbmF0aGFiIEpvaG4gUm95IiwiZmFtaWx5X25hbWUiOiJCZW5uZXQiLCJsb2NhbGUiOiJlbiJ9.I6mFmzKZ75-N25AgsFAaf0fgpPZVEhit5In4n5OyXP7ZfGOVsCQ8w_rc4iupMbpJZJPY5nwRjUz8yjsIrzZ90i31hwfBoI4Blxaqd-3r2NxBjDC9fHZR01aGCmLvqJDaoGGwLqCTGbBjqc3Px-SM46SLKha7Ct0Lh6ofNQWI1oU");
		;
	}
}
