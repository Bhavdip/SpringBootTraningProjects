package trainging.spring.boot.util;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class Utilites {
	
	public String generateRandomId() {
		return UUID.randomUUID().toString();
	}

}
