package ee.incub.rest.spring.controllers;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ee.incub.rest.spring.aws.adaptors.MessagesDynamoDB;
import ee.incub.rest.spring.aws.adaptors.UserDynamoDB;
import ee.incub.rest.spring.model.db.Message;
import ee.incub.rest.spring.model.http.IncubeeResponse;
import ee.incub.rest.spring.model.http.MessageRequest;
import ee.incub.rest.spring.model.http.MessageResponse;
import ee.incub.rest.spring.model.http.Token;
import ee.incub.rest.spring.utils.Utils;

/**
 * Handles requests for the application Messages Requests
 */
@Controller
public class MessagesController {

	private static final Logger logger = LoggerFactory
			.getLogger(MessagesController.class);

	@RequestMapping(value = "/msg", method = RequestMethod.POST)
	public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest messageRequest
			,@RequestHeader("token") String token
			//,@PathVariable String eid
			) 
			{
		logger.info("Message Object" + messageRequest);
		
		if (token == null || !GoogleVerificationController.verifyToken(token)) {
			MessageResponse messageResponse = new MessageResponse();
			messageResponse.setStatusMessage("Token not found");
			messageResponse.setStatusCode(MessageResponse.TOKEN_NOT_FOUND);
			return new ResponseEntity<MessageResponse>(messageResponse,
					HttpStatus.UNAUTHORIZED);
		}
		String mid = Utils.generateID("MSG_", 15);
		Message inMessage = Utils.inboundMsgfromMsgRequest(messageRequest, mid);
		Message outMessage = Utils.outboundMsgfromMsgRequest(messageRequest, mid);
		try {
			MessagesDynamoDB.loadMessage(inMessage);
			MessagesDynamoDB.loadMessage(outMessage);
		} catch (Exception e) {
			logger.error("Error Saving Message : " + e.getMessage(), e);
			e.printStackTrace();
			MessageResponse messageResponse = new MessageResponse();
			messageResponse.setStatusMessage("Saving Message failed");
			messageResponse.setStatusCode(MessageResponse.SEND_FAILED);
			return new ResponseEntity<MessageResponse>(messageResponse,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		MessageResponse messageResponse = new MessageResponse();
		messageResponse.setStatusMessage("Success");
		messageResponse.setStatusCode(MessageResponse.SUCCESS);
		messageResponse.setMessage(inMessage);
		return new ResponseEntity<MessageResponse>(messageResponse,
				HttpStatus.OK);
	}
	@RequestMapping(value = "/msg/{mid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<MessageResponse> getForId(@PathVariable("mid") String mid, @RequestParam("eid") String eid) {
		logger.info("Recieved GetMesssafe for mId: " + mid + " for eid :" + eid);
		try {
			MessageResponse messageResponse = new MessageResponse();
			messageResponse.setStatusMessage("Success");
			messageResponse.setStatusCode(MessageResponse.SUCCESS);
			messageResponse.setMessage( MessagesDynamoDB.getMessageForMessageID(mid, eid));
			return new ResponseEntity<MessageResponse>(messageResponse,
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception getMessage for Id: " + mid + " for eid :" + eid,e);
			MessageResponse messageResponse = new MessageResponse();
			messageResponse.setStatusMessage("Ger Message failed");
			messageResponse.setStatusCode(MessageResponse.GET_FAILED);
			return new ResponseEntity<MessageResponse>(messageResponse,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
