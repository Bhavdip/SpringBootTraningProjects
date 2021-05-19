package trainging.spring.boot.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import trainging.spring.boot.exception.UserServiceException;
import trainging.spring.boot.model.request.UpdateUserDetailsRequestModel;
import trainging.spring.boot.model.request.UserDetailsRequestModel;
import trainging.spring.boot.model.response.UserRest;
import trainging.spring.boot.service.imp.UserServiceImp;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	public UserServiceImp userServiceImp;
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<UserRest>> getUsers(@RequestParam(value = "page", defaultValue = "1", required = false) int page, @RequestParam(value = "limit", defaultValue = "50") int limit,  @RequestParam(value = "sort",  defaultValue = "asc",  required = false) String sort) {
		ArrayList<UserRest> entityList = userServiceImp.getAllUsers();
		return new ResponseEntity<List<UserRest>>(entityList, HttpStatus.OK);
	}

	
	@GetMapping(path="/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
		UserRest resultUser = userServiceImp.getUser(userId);
		if(resultUser != null) {
			return new ResponseEntity<UserRest>(resultUser, HttpStatus.OK);
		}
		return new ResponseEntity<UserRest>(HttpStatus.NO_CONTENT);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetailsRequestModel) {
		UserRest nwUser = userServiceImp.createUser(userDetailsRequestModel);
		return new ResponseEntity<UserRest>(nwUser, HttpStatus.OK);
	}
	
	@PutMapping(path= "/{userId}",consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<UserRest> updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequestModel userDetailsRequestModel) {
		UserRest updateRest = userServiceImp.updateUser(userId, userDetailsRequestModel);
		if(updateRest != null) {
			return new ResponseEntity<UserRest>(updateRest, HttpStatus.OK);
		}
		return new ResponseEntity<UserRest>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> deleteUser(@PathVariable String id) {
		int result = userServiceImp.deleteUserById(id);
		if(result == -1) {
			throw new UserServiceException("Requested UserId not found in data set");
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
