package trainging.spring.boot.model.response;

import java.util.Date;

public class ErrorMessage {

	private Date timeStemp;
	
	private String errorMessage;
	
	public ErrorMessage() {
	}
	
	public ErrorMessage(Date timeStemp, String errorMessage) {
		this.timeStemp = timeStemp;
		this.errorMessage = errorMessage;
	}

	public Date getTimeStemp() {
		return timeStemp;
	}

	public void setTimeStemp(Date timeStemp) {
		this.timeStemp = timeStemp;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
