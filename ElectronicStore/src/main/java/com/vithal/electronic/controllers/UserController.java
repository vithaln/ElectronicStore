package com.vithal.electronic.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vithal.electronic.dtos.PagebleResponse;
import com.vithal.electronic.dtos.UserDto;
import com.vithal.electronic.payload.ApiResponseMesg;
import com.vithal.electronic.payload.ImageResponse;
import com.vithal.electronic.services.FileService;
import com.vithal.electronic.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private FileService fileService;
	
	
	@Value("${user.profile.imageName}")
	private String imageUploadPath;
	
	// create users
	@PostMapping("/")
	public ResponseEntity<UserDto> saveUsers(@Valid @RequestBody UserDto dto) {

		UserDto createUser = service.createUser(dto);
		return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);
	}

	// get All users
	/*
	 * @GetMapping("/") public ResponseEntity<List<UserDto>> fetchAllUsers(
	 * 
	 * @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int
	 * pageNumber,
	 * 
	 * @RequestParam(value = "pageSize",defaultValue = "2",required = false) int
	 * pageSize,
	 * 
	 * @RequestParam(value = "sortBy",defaultValue = "name",required = false) String
	 * sortBy,
	 * 
	 * @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String
	 * sortDir
	 * 
	 * 
	 * ) { List<UserDto> allUsers =
	 * service.getAllUsers(pageNumber,pageSize,sortBy,sortDir); return new
	 * ResponseEntity<List<UserDto>>(allUsers, HttpStatus.OK); }
	 */
	
	@GetMapping("/")
	public ResponseEntity<PagebleResponse<UserDto>> fetchAllUsers(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "2",required = false) int pageSize,
			@RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir

			
			) {
		PagebleResponse<UserDto> allUsers = service.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PagebleResponse<UserDto>>(allUsers, HttpStatus.OK);
	}

	// get single user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> fetchSingleUserByUserId(@PathVariable String userId) {

		UserDto userDto = service.getUserById(userId);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}

	// update user by id
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUserById(@Valid @RequestBody UserDto dto, @PathVariable String userId) {

		UserDto updateUserByUserId = service.updateUserByUserId(dto, userId);
		return new ResponseEntity<UserDto>(updateUserByUserId, HttpStatus.OK);
	}

	// get user by email
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> fetchUserByEmail(@PathVariable String email) {
		UserDto userByEmail = service.getUserByEmail(email);
		return new ResponseEntity<UserDto>(userByEmail, HttpStatus.OK);
	}

	// get user by search name
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<UserDto>> fetchUsersByNameConatingkayword(@PathVariable String keyword) {

		List<UserDto> userByKeyWord = service.getUserByKeyWord(keyword);
		return new ResponseEntity<List<UserDto>>(userByKeyWord, HttpStatus.OK);
	}

	// delete user by Id
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMesg> deleteUsersById(@PathVariable String userId) {

		ApiResponseMesg apiResponseMesg = ApiResponseMesg.builder().message("User deleted successfully!!").success(true)
				.status(HttpStatus.OK).build();
		service.delteUserByUserId(userId);
		return new ResponseEntity<ApiResponseMesg>(apiResponseMesg, HttpStatus.OK);
	}
	
	//upload image
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadImage(
			@PathVariable String userId,
			@RequestParam("userImage") MultipartFile image
			
			) throws IOException{
		
		String imageName = fileService.uploadFile(image, imageUploadPath);
		
		UserDto user = service.getUserById(userId);
		user.setImageName(imageName);
		UserDto updateUserByUserId = service.updateUserByUserId(user, userId);
		
		ImageResponse imageResponse = ImageResponse.builder()
		.imageName(imageName)
		.success(true)
		.message("Image has been uploaded successfully..")
		.status(HttpStatus.CREATED).build();
		
				return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);
		
		
	}
	
	
	//serve image
	@GetMapping(value = "/image/{userId}")
	public void fetchImage(@PathVariable String userId,HttpServletResponse response) throws IOException {
		
		UserDto user = service.getUserById(userId);
		log.info("User Image {} ",user.getImageName());
		
		InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
	log.info("Image resousource: {}",resource);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
}
