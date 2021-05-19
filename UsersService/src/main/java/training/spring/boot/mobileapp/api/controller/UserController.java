package training.spring.boot.mobileapp.api.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import training.spring.boot.mobileapp.api.users.service.impl.UserServiceImp;
import training.spring.boot.mobileapp.model.CreateUserReponseModel;
import training.spring.boot.mobileapp.model.CreateUserRequestModel;
import training.spring.boot.mobileapp.model.UserResponseModel;
import training.spring.boot.mobileapp.model.shared.UserDto;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private UserServiceImp mUserServiceImp;
	
	@GetMapping("/status/check/v2")
	public String status() {
		System.out.println("[UserController]=>status");
		return "I am working on port: [" + environment.getProperty("local.server.port") + "]" + "and secret token is: [" + environment.getProperty("token.secret") + "]";
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CreateUserReponseModel> createUser(@RequestBody CreateUserRequestModel createUserRequestModel) {
		//ModelMapper help to generate UserDto object and send it to users service 
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto requestUserDto = modelMapper.map(createUserRequestModel, UserDto.class);
		UserDto createdUserDto = mUserServiceImp.createUser(requestUserDto);
		
		//map createdUserDto to CreateUserReponseModel for exclude some filed value
		CreateUserReponseModel responseModel = modelMapper.map(createdUserDto, CreateUserReponseModel.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
	}


	@GetMapping(value = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserResponseModel> getUser(@PathVariable String userId) {
		UserDto userDto = mUserServiceImp.getUserByUserId(userId);
		//Map to Response Entity 
		UserResponseModel userResponseModel = new ModelMapper().map(userDto, UserResponseModel.class);
		return ResponseEntity.status(HttpStatus.OK).body(userResponseModel);
	}
	

}