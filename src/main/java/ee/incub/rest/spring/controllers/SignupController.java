package ee.incub.rest.spring.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ee.incub.rest.spring.aws.adaptors.DynamoDBAdaptor;
import ee.incub.rest.spring.aws.adaptors.S3Adaptor;
import ee.incub.rest.spring.model.Incubee;
import ee.incub.rest.spring.model.IncubeeRequest;
import ee.incub.rest.spring.model.IncubeeResponse;
import ee.incub.rest.spring.model.Token;
import ee.incub.rest.spring.utils.Utils;

/**
 * Handles requests for the application file upload requests
 */
@Controller
public class SignupController {

	private static final Logger logger = LoggerFactory
			.getLogger(SignupController.class);

	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFileHandler(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="
						+ serverFile.getAbsolutePath());

				return "You successfully uploaded file=" + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name
					+ " because the file was empty.";
		}
	}

	/**
	 * Upload multiple file using Spring Controller
	 */
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadMultipleFileHandler(@RequestParam("name") String[] names,
			@RequestParam("file") MultipartFile[] files) {

		if (files.length != names.length)
			return "Mandatory information missing";

		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="
						+ serverFile.getAbsolutePath());

				message = message + "You successfully uploaded file=" + name
						+ "<br />";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		}
		return message;
	}
	
	/**
	 * Upload multiple file using Spring Controller
	 */
	@RequestMapping(value = "/uploadmyfile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFiles(@RequestParam(value="name",required=false) String name,
			@RequestParam("images") MultipartFile[] files) {


		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String tempName = name + i;
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + tempName);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="
						+ serverFile.getAbsolutePath());

				message = message + "You successfully uploaded file=" + tempName
						+ "<br />";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		}
		return message;
	}
	
	@RequestMapping(value="/handle", method=RequestMethod.POST)
	@ResponseBody
	public String handleRequest(IncubeeRequest incubee) {
	    logger.info("Incubee Object" + incubee);
	    Token token = null;
	    
	    if (incubee.getToken()!=null){
	    	try {
				token = new ObjectMapper().readValue(incubee.getToken(), Token.class);
			} catch (JsonParseException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
	    }
	    if (token ==null || GoogleVerificationController.verifyToken(token.getToken())){
	    	return "Please Sign in with Google";
	    }
	    String uuid = "inc_"+UUID.randomUUID().toString();
	    String[] keyList = null;
	    if (incubee.getImages()!=null){
	    	S3Adaptor adaptor = new S3Adaptor();
	    	MultipartFile[] fileList = incubee.getImages();
	    	keyList = new String[fileList.length];
	    	for (int i = 0; i < fileList.length; i++) {
				try {
					keyList[i]=adaptor.uploadFile(fileList[i]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("S3 Upload Exception :" + e);
				}
			}
	    }
	    DynamoDBAdaptor.loadIncubee(Utils.fromIncubeeRequest(incubee, keyList, uuid));
	    return "OK";
	}
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	@ResponseBody
	public List<IncubeeResponse> getAll() {
		return Utils.fromIncubeeList(DynamoDBAdaptor.getAllIncubees());
	}
}
