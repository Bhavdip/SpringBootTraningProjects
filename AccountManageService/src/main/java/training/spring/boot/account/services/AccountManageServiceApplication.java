package training.spring.boot.account.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AccountManageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountManageServiceApplication.class, args);
	}

}
