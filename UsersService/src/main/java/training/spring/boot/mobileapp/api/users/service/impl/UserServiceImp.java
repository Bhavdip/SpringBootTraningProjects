package training.spring.boot.mobileapp.api.users.service.impl;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import training.spring.boot.mobileapp.api.users.service.UserService;
import training.spring.boot.mobileapp.data.UserEntity;
import training.spring.boot.mobileapp.data.UserRepository;
import training.spring.boot.mobileapp.model.AlbumReponseModel;
import training.spring.boot.mobileapp.model.shared.UserDto;
import training.spring.boot.mobileapp.util.Utilities;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
	
	private Utilities mUtilities;
	
	/**
	 * Repository help to perform basic CURD operation
	 */
	private UserRepository mUserRepository;
	
	private BCryptPasswordEncoder mBCryptPasswordEncoder;
	
	private RestTemplate mRestTemplate;
	
	private Environment mEnvironment;
	

	public UserServiceImp() {
	}

	@Autowired
	public UserServiceImp(
			Utilities utilities, 
			UserRepository repository, 
			BCryptPasswordEncoder cryptPasswordEncoder,
			RestTemplate restTemplate,
			Environment environment) {
		
		mUtilities = utilities;
		mUserRepository = repository;
		mBCryptPasswordEncoder = cryptPasswordEncoder;
		mRestTemplate = restTemplate;
		mEnvironment = environment;
		
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

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = mUserRepository.findByUserId(userId);
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		UserDto userDetails = new ModelMapper().map(userEntity, UserDto.class);

		//Prepare Albums-WS
		String  albumsURL = String.format(mEnvironment.getProperty("albums.users.url"), userDetails.getUserId());
		
		//Communicate with Albums-WS
		ResponseEntity<List<AlbumReponseModel>> albumeResponseList  =  mRestTemplate.exchange(albumsURL, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumReponseModel>>() {});
		
		//Response of Albums-WS 
		List<AlbumReponseModel> resultAlbumList = albumeResponseList.getBody();
		
		//Save result album to UserDto PoJo
		userDetails.setAlbums(resultAlbumList);
		
		return userDetails;
	}
}
