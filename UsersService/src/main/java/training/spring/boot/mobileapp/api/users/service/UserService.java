package training.spring.boot.mobileapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import training.spring.boot.mobileapp.model.shared.UserDto;

public interface UserService extends UserDetailsService{
	UserDto createUser(UserDto user);
	UserDto getUserDetailsByEmail(String email);
	UserDto getUserByUserId(String userId);
}
