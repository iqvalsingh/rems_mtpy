/**
 * 
 */
package com.mytangibleproperty.rems.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.mytangibleproperty.rems.bo.City;
import com.mytangibleproperty.rems.bo.Country;
import com.mytangibleproperty.rems.bo.Enquiry;
import com.mytangibleproperty.rems.bo.Feedback;
import com.mytangibleproperty.rems.bo.Property;
import com.mytangibleproperty.rems.bo.PropertyType;
import com.mytangibleproperty.rems.bo.State;
import com.mytangibleproperty.rems.bo.User;
import com.mytangibleproperty.rems.services.CityService;
import com.mytangibleproperty.rems.services.CountryService;
import com.mytangibleproperty.rems.services.EnquiryService;
import com.mytangibleproperty.rems.services.FeedbackService;
import com.mytangibleproperty.rems.services.PropertyService;
import com.mytangibleproperty.rems.services.PropertyTypeService;
import com.mytangibleproperty.rems.services.StateService;
import com.mytangibleproperty.rems.services.UserService;

import lombok.extern.log4j.Log4j2;

/**
 * @author Satbir Kaur
 *
 */
@Log4j2
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Autowired
	PropertyTypeService propertyTypeService;

	@Autowired
	CountryService countryService;

	@Autowired
	StateService stateService;

	@Autowired
	CityService cityService;

	@Autowired
	PropertyService propertyService;

	@Autowired
	FeedbackService feedbackService;

	@Autowired
	EnquiryService enquiryService;

	@PostMapping(value = "/login.html")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		logger.info("login");
		
		String email = (String) request.getParameter("email");
		String passwordInput = (String) request.getParameter("password");

		String password = passwordInput;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// Log
		}
		md.update(passwordInput.getBytes());
		BigInteger hash = new BigInteger(1, md.digest());
		password = hash.toString(16);
		while (password.length() < 32) { // 40 for SHA-1
			password = "0" + password;
		}

		Map<String, Object> loginMap = new HashMap<>();
		loginMap.put("EMAIL", email);
		loginMap.put("PASSWORD", password);
		List<User> userList = userService.getUsers(loginMap);
		if (!CollectionUtils.isEmpty(userList)) {
			request.getSession().setAttribute("user", userList.get(0));
		} else {
			return new ModelAndView("redirect:/index.html?e=INVALID_CREDS");
		}

		return new ModelAndView("redirect:/index.html");
	}

	@GetMapping(value = "/logout.html")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("user");
		ModelAndView model = new ModelAndView("redirect:/index.html");
		return model;
	}

	@GetMapping(value = "/forgot-password.html")
	public ModelAndView forgotPassword(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("forgot-password");
		return model;
	}

	@PostMapping(value = "/reset-password.html")
	public ModelAndView resetPassword(HttpServletRequest request, HttpServletResponse response) {
		String email = (String) request.getParameter("email");
		String mobileNumber = (String) request.getParameter("mobilenumber");
		Map<String, Object> loginMap = new HashMap<>();
		loginMap.put("EMAIL", email);
		loginMap.put("MOBILENUMBER", mobileNumber);
		List<User> userList = userService.getUsers(loginMap);
		ModelAndView model = new ModelAndView("reset-password");
		if (!CollectionUtils.isEmpty(userList)) {
			model.addObject("user", userList.get(0));
		} else {
			model = new ModelAndView("forgot-password");
			model.addObject("message", "Invalid Details. Please try again.");
		}
		return model;
	}

	@PostMapping(value = "/saveNewPassword.html")
	public ModelAndView saveNewPassword(HttpServletRequest request, HttpServletResponse response) {
		String newPassword = (String) request.getParameter("newpassword");
		String confirmPassword = (String) request.getParameter("confirmpassword");
		if (newPassword != null && confirmPassword != null && newPassword.equals(confirmPassword)) {
			String userId = (String) request.getParameter("userId");
			User user = userService.getUserById(Integer.parseInt(userId));
			String password = newPassword;
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				// Log
			}
			md.update(newPassword.getBytes());
			BigInteger hash = new BigInteger(1, md.digest());
			password = hash.toString(16);
			while (password.length() < 32) { // 40 for SHA-1
				password = "0" + password;
			}
			user.setPassword(password);
			userService.updateUser(user);
			return new ModelAndView("redirect:/login.html");
		} else {
			// Passwords did not match
			return new ModelAndView("redirect:/reset-password.html");
		}
	}

	@PostMapping(value = "/signup.html")
	public ModelAndView signup(HttpServletRequest request, HttpServletResponse response) {
		String fullName = (String) request.getParameter("fullname");
		String email = (String) request.getParameter("email");
		String mobileNumber = (String) request.getParameter("mobilenumber");
		String passwordInput = (String) request.getParameter("password");
		String usertype = (String) request.getParameter("usertype");

		Map<String, Object> loginMap = new HashMap<>();
		loginMap.put("EMAIL", email);
		List<User> userList = userService.getUsers(loginMap);
		if (!CollectionUtils.isEmpty(userList)) {
			// Email already exists
		} else {
			Date now = new Date();
			User user = new User();
			user.setFullName(fullName);
			user.setEmail(email);
			user.setMobileNumber(Long.parseLong(mobileNumber));
			user.setCreationDate(now);
			user.setUpdationDate(now);
			user.setUserType(Integer.parseInt(usertype));

			String password = passwordInput;
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				// Log
			}
			md.update(passwordInput.getBytes());
			BigInteger hash = new BigInteger(1, md.digest());
			password = hash.toString(16);
			while (password.length() < 32) { // 40 for SHA-1
				password = "0" + password;
			}
			user.setPassword(password);
			userService.addUser(user);
		}
		return new ModelAndView("index");
	}

	@GetMapping(value = "/change-password.html")
	public ModelAndView changePassword(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView model = new ModelAndView("change-password");
		model.addObject("user", user);
		return model;
	}

	@PostMapping(value = "/updatePassword.html")
	public ModelAndView updatePassword(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		String currentPassword = (String) request.getParameter("currentpassword");
		String newPassword = (String) request.getParameter("newpassword");
		String confirmPassword = (String) request.getParameter("confirmpassword");

		String tempCurrentPassword = currentPassword;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// Log
		}
		md.update(currentPassword.getBytes());
		BigInteger hash = new BigInteger(1, md.digest());
		tempCurrentPassword = hash.toString(16);
		while (tempCurrentPassword.length() < 32) { // 40 for SHA-1
			tempCurrentPassword = "0" + tempCurrentPassword;
		}
		ModelAndView model = new ModelAndView("change-password");
		if (!tempCurrentPassword.equals(user.getPassword())) {
			model.addObject("message", "Your current password is wrong");
			return model;
		}

		if (newPassword != null && confirmPassword != null && newPassword.equals(confirmPassword)) {
			String password = newPassword;
			md.update(newPassword.getBytes());
			password = hash.toString(16);
			while (password.length() < 32) { // 40 for SHA-1
				password = "0" + password;
			}
			user.setPassword(password);
			userService.updateUser(user);
			model.addObject("message", "Your password successully changed");
		}
		return model;
	}

	@GetMapping(value = "/add-property.html")
	public ModelAndView addPropertyPage(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("redirect:/index.html?e=USER_NOT_LOGGED_IN");
		}
		ModelAndView model = new ModelAndView("add-property");
		List<Country> countryList = countryService.getAllCountry();
		model.addObject("countryList", countryList);
		List<State> stateList = stateService.getAllState();
		model.addObject("stateList", stateList);
		List<City> cityList = cityService.getAllCity();
		model.addObject("cityList", cityList);
		List<PropertyType> propertyTypeList = propertyTypeService.getAllPropertyType();
		model.addObject("propertyTypeList", propertyTypeList);
		model.addObject("propertyForm", new Property());
		return model;
	}

	private void fileUpload(InputStream inputStream, String path, String propertyImageName) throws IOException {
//		File file = new File(path + "rems//propertyimages//");
		File file;
		if (path.contains("ROOT")) {
			file = new File(path + "images//html//propertyimages//"); // Unix box.
		} else {
			//file = new File(path + "rems//src//main//webapp//html//propertyimages//");
			file = new File(path + "images//html//propertyimages//");
		}
		File imageFile = new File(file, propertyImageName);
		OutputStream outputStream = new FileOutputStream(imageFile);
		int bytesRead = 0;
		byte[] dataBytes = new byte[4 * 1024];
		while ((bytesRead = inputStream.read(dataBytes)) != -1) {
			outputStream.write(dataBytes, 0, bytesRead);
			outputStream.flush();
		}
		outputStream.close();
	}

	@PostMapping(value = "/saveProperty.html", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ModelAndView saveProperty(@ModelAttribute Property property, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		User user = (User) request.getSession().getAttribute("user");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> imageMap = multipartRequest.getFileMap();
		// property.setId(-1);
		property.setUser(user);
		Random randomNum = new Random();
		property.setDisplayPropertyId(100000000 + randomNum.nextInt(900000000));
		property.setFeaturedImage(new Date().getTime()+1+imageMap.get("uploadedFeaturedImage").getOriginalFilename());
		property.setGalleryImage1(new Date().getTime()+2+imageMap.get("uploadedGalleryImage1").getOriginalFilename());
		property.setGalleryImage2(new Date().getTime()+3+imageMap.get("uploadedGalleryImage2").getOriginalFilename());
		property.setGalleryImage3(new Date().getTime()+4+imageMap.get("uploadedGalleryImage3").getOriginalFilename());
		property.setGalleryImage4(new Date().getTime()+5+imageMap.get("uploadedGalleryImage4").getOriginalFilename());
		property.setGalleryImage5(new Date().getTime()+6+imageMap.get("uploadedGalleryImage5").getOriginalFilename());
		property.setListingDate(new Date());

		String path = this.getClass().getClassLoader().getResource("").getPath();
		String fullPath = URLDecoder.decode(path, "UTF-8");
//		String	pathArr[]	= fullPath.split("/rems/");
		System.out.println("fullPath>>>>>>>>>>.." + fullPath);
		int i = fullPath.lastIndexOf("rems/");
		if (i < 0) {
			i = fullPath.lastIndexOf("WEB-INF/"); // Unix box.
		}
		String pathArr[] = { fullPath.substring(0, i), fullPath.substring(i) };
		fullPath = pathArr[0];
		System.out.println("fullPath 2>>> "+fullPath);

		fileUpload(imageMap.get("uploadedFeaturedImage").getInputStream(), fullPath, property.getFeaturedImage());
		fileUpload(imageMap.get("uploadedGalleryImage1").getInputStream(), fullPath, property.getGalleryImage1());
		fileUpload(imageMap.get("uploadedGalleryImage2").getInputStream(), fullPath, property.getGalleryImage2());
		fileUpload(imageMap.get("uploadedGalleryImage3").getInputStream(), fullPath, property.getGalleryImage3());
		fileUpload(imageMap.get("uploadedGalleryImage4").getInputStream(), fullPath, property.getGalleryImage4());
		fileUpload(imageMap.get("uploadedGalleryImage5").getInputStream(), fullPath, property.getGalleryImage5());
		propertyService.saveProperty(property);

		return new ModelAndView("redirect:/properties-grid.html");
	}

	@PostMapping(value = "/saveReview.html")
	public ModelAndView saveReview(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		String propertyId = (String) request.getParameter("propertyId");
		String reviewcomment = (String) request.getParameter("reviewcomment");

		ModelAndView model = new ModelAndView("redirect:/single-property-detail.html?propertyId=" + propertyId);
		if (user == null) {
			request.getSession().setAttribute("issue", "ISSUE-USER-NOT-LOGGED-IN");
			return model;
		}
		Property property = new Property();
		property.setPropertyId(Integer.parseInt(propertyId));
		Feedback feedback = new Feedback();
		feedback.setUserRemark(reviewcomment);
		feedback.setUser(user);
		feedback.setProperty(property);
		feedback.setPostingDate(new Date());

		feedbackService.saveFeedback(feedback);

		return model;
	}

	@GetMapping(value = "/user-profile.html")
	public ModelAndView userProfile(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView model = new ModelAndView("user-profile");
		model.addObject("user", user);
		return model;
	}

	@PostMapping(value = "/updateUserProfile.html")
	public ModelAndView updateUserProfile(@ModelAttribute User user, HttpServletRequest request,
			HttpServletResponse response) {
		User sessionUser = (User) request.getSession().getAttribute("user");
		sessionUser.setFullName(user.getFullName());
		sessionUser.setMobileNumber(user.getMobileNumber());
		sessionUser.setAboutMe(user.getAboutMe());
		sessionUser.setUpdationDate(new Date());
		userService.updateUser(sessionUser);
		request.getSession().setAttribute("user", sessionUser);
		return new ModelAndView("redirect:/user-profile.html");
	}

	@GetMapping(value = "/enquiries.html")
	public ModelAndView enquiries(HttpServletRequest request, HttpServletResponse response) {
		String status = (String) request.getParameter("status");
		User user = (User) request.getSession().getAttribute("user");
		Map<String, Object> criteria = new HashMap<>();
		criteria.put("USERID", user.getUserId());
		ModelAndView model = null;
		if (status.equals("Received")) {
			criteria.put("STATUS", null);
			model = new ModelAndView("enquiry");
		} else if (status.equals("Answer")) {
			criteria.put("STATUS", "Answer");
			model = new ModelAndView("ansenquiry");
		} else {
			model = new ModelAndView("enquiry-status");
		}
		List<Enquiry> enquiryList = enquiryService.getEnquiryByCriteria(criteria);
		model.addObject("enquiryList", enquiryList);
		return model;
	}

	@GetMapping(value = "/view-enquiry-detail.html")
	public ModelAndView enquiryDetails(HttpServletRequest request, HttpServletResponse response) {
		int eqnuiryId = Integer.parseInt((String) request.getParameter("eqnuiryId"));
		Enquiry enquiry = enquiryService.getEnquiryById(eqnuiryId);
		ModelAndView model = new ModelAndView("view-enquiry-detail");
		model.addObject("enquiry", enquiry);
		return model;
	}

	@GetMapping(value = "/my-properties.html")
	public ModelAndView propertiesListing(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");

		ModelAndView model = new ModelAndView("my-properties");
		List<Property> propertyList = null;
		Map<String, Object> criteria = new HashMap<>();
		criteria.put("USERID", user.getUserId());
		propertyList = propertyService.getPropertyByCriteria(criteria);
		model.addObject("propertyList", propertyList);
		return model;
	}

	@GetMapping(value = "/edit-property.html")
	public ModelAndView editPropertyPage(HttpServletRequest request, HttpServletResponse response) {
		String propertyId = (String) request.getParameter("propertyId");
		Property property = propertyService.getPropertyById(Integer.parseInt(propertyId));
		ModelAndView model = new ModelAndView("edit-property");
		List<Country> countryList = countryService.getAllCountry();
		model.addObject("countryList", countryList);
		List<State> stateList = stateService.getAllState();
		model.addObject("stateList", stateList);
		List<City> cityList = cityService.getAllCity();
		model.addObject("cityList", cityList);
		List<PropertyType> propertyTypeList = propertyTypeService.getAllPropertyType();
		model.addObject("propertyTypeList", propertyTypeList);
		model.addObject("property", property);

		// model.addObject("property", property);
		return model;
	}

	@GetMapping(value = "/changeimage.html")
	public ModelAndView changePropertyImage(HttpServletRequest request, HttpServletResponse response) {
		String propertyId = (String) request.getParameter("propertyId");
		String imageName = (String) request.getParameter("imageName");
		Property property = propertyService.getPropertyById(Integer.parseInt(propertyId));
		ModelAndView model = new ModelAndView("changeimage");
		model.addObject("propertyId", propertyId);
		model.addObject("propertyTitle", property.getPropertyTitle());
		String propertyImage = null;
		switch (imageName) {
		case "featuredImage":
			propertyImage = property.getFeaturedImage();
			break;
		case "galleryImage1":
			propertyImage = property.getGalleryImage1();
			break;
		case "galleryImage2":
			propertyImage = property.getGalleryImage2();
			break;
		case "galleryImage3":
			propertyImage = property.getGalleryImage3();
			break;
		case "galleryImage4":
			propertyImage = property.getGalleryImage4();
			break;
		case "galleryImage5":
			propertyImage = property.getGalleryImage5();
			break;
		}
		model.addObject("propertyImage", propertyImage);
		model.addObject("imageName", imageName);

		return model;
	}

	@PostMapping(value = "/saveImage.html", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ModelAndView saveImage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String propertyId = (String) request.getParameter("propertyId");
		String imageName = (String) request.getParameter("imageName");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> imageMap = multipartRequest.getFileMap();
		Property property = propertyService.getPropertyById(Integer.parseInt(propertyId));

		String path = this.getClass().getClassLoader().getResource("").getPath();
		String fullPath = URLDecoder.decode(path, "UTF-8");
//		String	pathArr[]	= fullPath.split("/rems/");
		int i = fullPath.lastIndexOf("rems/");
		String pathArr[] = { fullPath.substring(0, i), fullPath.substring(i) };
		fullPath = pathArr[0];

		fileUpload(imageMap.get("image").getInputStream(), fullPath, imageMap.get("image").getOriginalFilename());

		switch (imageName) {
		case "featuredImage":
			property.setFeaturedImage(imageMap.get("image").getOriginalFilename());
			break;
		case "galleryImage1":
			property.setGalleryImage1(imageMap.get("image").getOriginalFilename());
			break;
		case "galleryImage2":
			property.setGalleryImage2(imageMap.get("image").getOriginalFilename());
			break;
		case "galleryImage3":
			property.setGalleryImage3(imageMap.get("image").getOriginalFilename());
			break;
		case "galleryImage4":
			property.setGalleryImage4(imageMap.get("image").getOriginalFilename());
			break;
		case "galleryImage5":
			property.setGalleryImage5(imageMap.get("image").getOriginalFilename());
			break;
		}
		propertyService.updateProperty(property);

		return new ModelAndView("redirect:/changeimage.html?imageName=" + imageName + "&propertyId=" + propertyId);
	}

}
