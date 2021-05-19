package training.spring.boot.mobileapp.api.users.service.impl;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import training.spring.boot.mobileapp.api.users.service.UserService;
import training.spring.boot.mobileapp.data.UserEntity;
import training.spring.boot.mobileapp.data.UserRepository;
import training.spring.boot.mobileapp.model.shared.UserDto;
import training.spring.boot.mobileapp.util.Utilities;

@Service
public class UserServiceImp implements UserService {
	
	private Utilities mUtilities;
	
	/**
	 * Repository help to perform basic CURD operation
	 */
	private UserRepository mUserRepository;
	
	private BCryptPasswordEncoder mBCryptPasswordEncoder;
	
	public UserServiceImp() {
	}

	@Autowired
	public UserServiceImp(Utilities utilities, UserRepository repository, BCryptPasswordEncoder cryptPasswordEncoder) {
		mUtilities = utilities;
		mUserRepository = repository;
		mBCryptPasswordEncoder = cryptPasswordEncoder;
	}
	
	@Override
	public UserDto createUser(UserDto userDetails) {
		
		// we have to generate the random Id
		userDetails.setUserId(mUtilities.generateRandomId());
		
		// Encrypt the password and store into in memory database
		userDetails.setEncryptedPassword(mBCryptPasswordEncoder.encode(userDetails.getPassword()));
		
		// Use ModelMapper to map UserDto => UserEntity
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		
		// Persist User Entity into database
		mUserRepository.save(userEntity);
	
		//return UserDto object from userEntity to controller
		UserDto nwUserDetails = modelMapper.map(userEntity, UserDto.class);
		
		return nwUserDetails;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = mUserRepository.findByEmail(username);
		if(userEntity == null) throw new UsernameNotFoundException(username);
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity = mUserRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		UserDto userDetails = new ModelMapper().map(userEntity, UserDto.class);
		return userDetails;
	}

}
