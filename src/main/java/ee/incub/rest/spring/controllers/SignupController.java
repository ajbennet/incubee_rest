package ee.incub.rest.spring.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ee.incub.rest.spring.aws.adaptors.DynamoDBAdaptor;
import ee.incub.rest.spring.aws.adaptors.S3Adaptor;
import ee.incub.rest.spring.model.IncubeeRequest;
import ee.incub.rest.spring.model.IncubeeResponse;
import ee.incub.rest.spring.model.LoginResponse;
import ee.incub.rest.spring.model.Token;
import ee.incub.rest.spring.model.User;
import ee.incub.rest.spring.utils.Utils;

/**
 * Handles requests for the application file upload requests
 */
@Controller
public class SignupController {

	private static final Logger logger = LoggerFactory
			.getLogger(SignupController.class);


	@RequestMapping(value = "/handle", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> handleRequest(IncubeeRequest incubee) {
		logger.info("Incubee Object" + incubee);
		Token token = null;

		if (incubee.getToken() != null) {
			try {
				token = new ObjectMapper().readValue(incubee.getToken(),
						Token.class);
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

		if (token == null || !GoogleVerificationController.verifyToken(token)) {
			return new ResponseEntity<String>("Please sign-in with Google",
					HttpStatus.UNAUTHORIZED);
		}
		String uuid = "inc_" + UUID.randomUUID().toString();
		User user = DynamoDBAdaptor.getUserForHandle(token.getId());
		if (user == null) {
			//creating new incubee & user
			createOrUpdateIncubee(incubee, uuid);
			createUser(token, uuid);
			return new ResponseEntity<String>("User & Startup Created", HttpStatus.CREATED);
		} else {
			createOrUpdateIncubee(incubee, uuid);
			return new ResponseEntity<String>("Incubee Updated", HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public List<IncubeeResponse> getAll() {
		return Utils.fromIncubeeList(DynamoDBAdaptor.getAllIncubees());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public IncubeeResponse getForId(@PathVariable("id") String id) {
		logger.info("Recieved getIncubee for Id: " + id);
		return Utils.fromIncubee(DynamoDBAdaptor.getIncubee(id));
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<LoginResponse> login(@RequestBody final Token token) {
		// String incubee_id= null;
		logger.info("Recieved Login Request with token : " + token);
		LoginResponse response = new LoginResponse();
		// retreive the user with the id.
		if (token != null && token.getId() != null) {
			User user = DynamoDBAdaptor.getUser(token.getId());
			if (user == null) {
				// create user
				// /boolean createdUser = DynamoDBAdaptor.createUser(token);
				response.setStatusCode(LoginResponse.USER_NOT_FOUND);
				response.setStatusMessage("User not found");
				return new ResponseEntity<LoginResponse>(response,
						HttpStatus.NOT_FOUND);
			}
			else {
				//Incubee incubee = DynamoDBAdaptor.getIncubee(user.getCompany_id());
				response.setStatusCode(LoginResponse.SUCCESS);
				response.setStatusMessage("Success");
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("company_id", user.getCompany_id());
				response.setServicedata(map);
				//return new ResponseEntity<String>("{\"company_id\":\""+user.getCompany_id()+"\"}", HttpStatus.OK);
				return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
			}
		}
		// DynamoDBAdaptor.getIncubee(incubee_id);
		response.setStatusCode(LoginResponse.TOKEN_NOT_FOUND);
		response.setStatusMessage("Token not found");
		return new ResponseEntity<LoginResponse>(response, HttpStatus.BAD_REQUEST);
	}

	private boolean createOrUpdateIncubee(IncubeeRequest incubee, String uuid) {
		boolean isCreated = false;
		logger.info("Creating new Incubee : " + incubee + " with id :" + uuid);
		String[] keyList = null;
		if (incubee.getImages() != null) {
			S3Adaptor adaptor = new S3Adaptor();
			MultipartFile[] fileList = incubee.getImages();
			keyList = new String[fileList.length];
			for (int i = 0; i < fileList.length; i++) {
				try {
					if(!fileList[i].isEmpty())
						keyList[i] = adaptor.uploadFile(fileList[i], "img");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("S3 Upload Exception :" + e);
				}
			}
		}
		String video = null;
		if (incubee.getVideo() != null) {
			S3Adaptor adaptor = new S3Adaptor();
			MultipartFile file = incubee.getVideo();
			if (file != null) {
				try {
					if(!file.isEmpty())
						video = adaptor.uploadFile(file, "vid");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("S3 Upload Exception :" + e);
				}
			}
		}
		if(incubee.getId()!=null){
			DynamoDBAdaptor.updateIncubee(Utils.fromIncubeeRequest(incubee, keyList,
				video, incubee.getId()));
		} else{
			DynamoDBAdaptor.loadIncubee(Utils.fromIncubeeRequest(incubee, keyList,
				video, uuid));
		}
		// check if user already has a company associated to his account.
		isCreated = true;
		logger.info("Incubee Created : " + incubee.getCompany_name());
		return isCreated;
	}

	private boolean createUser(Token token, String company_id) {
		if (token != null && company_id != null) {
			logger.info("Creating User with : " + token + " for company :"
					+ company_id);
			User user = new User();
			String user_id = "usr_" + UUID.randomUUID().toString();
			user.setId(user_id);
			user.setCompany_id(company_id);
			user.setEmail(token.getEmail());
			user.setImage_url(token.getImage_url());
			user.setName(token.getName());
			user.setToken(token.getToken());
			user.setHandle_id(token.getId());
			user.setLogin_type("google");
			DynamoDBAdaptor.createUser(user);
			logger.info("Created User : " + user_id);
			return true;
		} else {
			logger.error("Created User Failed - No Token or company_id  ");
			return false;
		}
	}
	
	
	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(
			@RequestParam("name") String name,
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
	public @ResponseBody String uploadMultipleFileHandler(
			@RequestParam("name") String[] names,
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
	public @ResponseBody String uploadFiles(
			@RequestParam(value = "name", required = false) String name,
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

				message = message + "You successfully uploaded file="
						+ tempName + "<br />";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		}
		return message;
	}
}
