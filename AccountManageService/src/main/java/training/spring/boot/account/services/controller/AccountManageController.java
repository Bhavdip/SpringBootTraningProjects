package training.spring.boot.account.services.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountManageController {

	@GetMapping("/status/check")
	public String getAccountManagement() {
		return "Account Service Up and Running";
	}
}
