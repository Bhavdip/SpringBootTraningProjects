package trainging.spring.boot.service;

import java.util.ArrayList;

import trainging.spring.boot.model.request.UpdateUserDetailsRequestModel;
import trainging.spring.boot.model.request.UserDetailsRequestModel;
import trainging.spring.boot.model.response.UserRest;

public interface UserService {

	ArrayList<UserRest> getAllUsers();
	
	UserRest createUser(UserDetailsRequestModel userDetailsRequestModel);
	
	UserRest getUser(String userId);
	
	UserRest updateUser(String userId, UpdateUserDetailsRequestModel userDetailsRequestModel);
	
	int deleteUserById(String userId);
}
