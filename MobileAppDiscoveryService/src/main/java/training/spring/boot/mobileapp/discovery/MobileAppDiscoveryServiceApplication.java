package training.spring.boot.mobileapp.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MobileAppDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileAppDiscoveryServiceApplication.class, args);
	}

}
