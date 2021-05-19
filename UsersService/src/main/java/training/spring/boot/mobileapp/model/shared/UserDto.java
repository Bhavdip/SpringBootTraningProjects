package training.spring.boot.mobileapp.model.shared;

import java.io.Serializable;
import java.util.List;

import training.spring.boot.mobileapp.model.AlbumReponseModel;

public class UserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7789257769565110986L;
	
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String encryptedPassword;
	private List<AlbumReponseModel> albums;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	public List<AlbumReponseModel> getAlbums() {
		return albums;
	}
	public void setAlbums(List<AlbumReponseModel> albums) {
		this.albums = albums;
	}
	
}
