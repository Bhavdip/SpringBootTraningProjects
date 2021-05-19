package training.spring.boot.mobileapp.util;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class Utilities {
	
	public String generateRandomId() {
		return UUID.randomUUID().toString();
	}

}

