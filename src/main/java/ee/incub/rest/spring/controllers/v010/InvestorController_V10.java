package ee.incub.rest.spring.controllers.v010;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ee.incub.rest.spring.aws.adaptors.UserDynamoDB;
import ee.incub.rest.spring.model.db.User;
import ee.incub.rest.spring.utils.Constants;
import ee.incub.rest.spring.utils.GoogleVerificationController;
import ee.incub.rest.spring.utils.Utils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Handles requests for the application file upload requests
 */
@Controller
public class InvestorController_V10 {

	private static final Logger logger = LoggerFactory
			.getLogger(InvestorController_V10.class);

	@RequestMapping(value = "/v1.0/invite/{emailId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> invite(
			@RequestParam("uid") String uid,
			@PathVariable("emailId") String emailId,
			@RequestHeader("token") String token) {
		logger.info("Invite founder request initiated by " + emailId + " : "
				+ uid);

		if (token == null || !GoogleVerificationController.verifyToken(token)) {
			return new ResponseEntity<String>("Please sign-in with Google",
					HttpStatus.UNAUTHORIZED);
		}
		try {
			User user = UserDynamoDB.getUser(uid);
			if (user!=null){
				
				String email_token = Utils.generateID("inv", 15);
				sendEmail(emailId,"Alison", email_token);
				return new ResponseEntity<String>(
						"{  \"statusMessage\":\"Success\","
								+ "\"statusCode\":\"INV_1000\"}", HttpStatus.OK);
			}else
				return new ResponseEntity<String>(
						"{  \"statusMessage\":\"User not found\","
								+ "\"statusCode\":\"INV_1003\"}",
						HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(
					"{  \"statusMessage\":\"Success\","
							+ "\"statusCode\":\"INV_1003\"}",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	private void sendEmail(String emailId, String investor, String token){
		
		final String username = Constants.EMAIL_USERNAME;
		final String password = Constants.EMAIL_PASSWORD;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("support@incub.ee"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(emailId));
			message.setSubject( "Invitation from " + investor );
			
			BodyPart body = new MimeBodyPart();
			 
            Configuration cfg = new Configuration();
            cfg.setClassForTemplateLoading(InvestorController_V10.class, "/");

            Template template = cfg.getTemplate("email_template.ftl");
            Map<String, String> rootMap = new HashMap<String, String>();
            rootMap.put("investor", investor);
            rootMap.put("token", token);
            Writer out = new StringWriter();
            template.process(rootMap, out);
 
            /* you can add html tags in your text to decorate it. */
            body.setContent(out.toString(), "text/html");
 
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);
 
            message.setContent(multipart, "text/html");
			
			Transport.send(message);
			logger.info("Email sent to :" + emailId + " from: " + investor);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
