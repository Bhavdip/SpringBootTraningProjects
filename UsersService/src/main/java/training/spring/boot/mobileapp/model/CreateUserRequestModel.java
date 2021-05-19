package training.spring.boot.mobileapp.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {

	@NotNull(message = "First name should not empty")
	@Size(min=2, message = "Name should be grater than 2 chanracters")
	private String firstName;
	@NotNull(message = "Last name should not empty")
	private String lastName;
	@Email(message = "Email can not be empty")
	private String email;
	@NotNull(message = "Password can not be empty")
	@Size(min=8, max=16, message = "Password must be equal or grater than 8 chanracters and less then 16 characters")
	private String password;
	
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
}
