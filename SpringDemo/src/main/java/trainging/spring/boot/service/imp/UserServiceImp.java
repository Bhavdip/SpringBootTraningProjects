package trainging.spring.boot.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainging.spring.boot.model.request.UpdateUserDetailsRequestModel;
import trainging.spring.boot.model.request.UserDetailsRequestModel;
import trainging.spring.boot.model.response.UserRest;
import trainging.spring.boot.service.UserService;
import trainging.spring.boot.util.Utilites;

@Service
public class UserServiceImp implements UserService {
	
	// For this example we are storing user info temporary in HashMap and retrieve it from HashMap 
	private HashMap<String, UserRest> usersDataSet;
	
	@Autowired
	private Utilites utilites;
	
	public UserServiceImp() {
	}

	public UserServiceImp(Utilites utilites) {
		this.utilites = utilites;
	}

	@Override
	public ArrayList<UserRest> getAllUsers() {
		if(usersDataSet != null && !usersDataSet.isEmpty()) {
			ArrayList<UserRest> entityList = new ArrayList<UserRest>(usersDataSet.values());
			return entityList;
		}
		return new ArrayList<UserRest>();
	}

	@Override
	public UserRest createUser(UserDetailsRequestModel userDetailsRequestModel) {
		// if usersDataSet is empty then we we instantiate it 
		if(usersDataSet == null || usersDataSet.size() == 0) {
			usersDataSet = new HashMap<>();
		}
		String userUUID = utilites.generateRandomId();
		UserRest nwUser = new UserRest();
		nwUser.setUserId(userUUID);
		nwUser.setFirstName(userDetailsRequestModel.getFirstName());
		nwUser.setLastName(userDetailsRequestModel.getLastName());
		nwUser.setEmail(userDetailsRequestModel.getEmail());
		nwUser.setPassword(userDetailsRequestModel.getPassword());
		
		// story in local HashMap
		usersDataSet.put(userUUID, nwUser);

		return nwUser;
	}

	@Override
	public UserRest getUser(String userId) {
		if(usersDataSet != null || usersDataSet.size() > 0) {
			UserRest resultUser = usersDataSet.get(userId);
			return resultUser;
		}
		return null;
	}

	@Override
	public UserRest updateUser(String userId, UpdateUserDetailsRequestModel userDetailsRequestModel) {
		if(usersDataSet != null && usersDataSet.containsKey(userId)) {
			UserRest updateRest = usersDataSet.get(userId);
			updateRest.setFirstName(userDetailsRequestModel.getFirstName());
			updateRest.setLastName(userDetailsRequestModel.getLastName());
			updateRest.setEmail(userDetailsRequestModel.getEmail());
			usersDataSet.put(userId, updateRest);

			return updateRest;
		}
		return null;
	}

	@Override
	public int deleteUserById(String userId) {
		if(usersDataSet != null && usersDataSet.containsKey(userId)) {
			usersDataSet.remove(userId);
			return 0;
		}
		return -1;
	}

}
