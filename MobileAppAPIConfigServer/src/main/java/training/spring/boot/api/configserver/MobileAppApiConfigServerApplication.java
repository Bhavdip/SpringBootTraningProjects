package training.spring.boot.api.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class MobileAppApiConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileAppApiConfigServerApplication.class, args);
	}

}
