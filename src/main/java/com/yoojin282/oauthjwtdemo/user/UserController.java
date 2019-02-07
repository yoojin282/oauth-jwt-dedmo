package com.yoojin282.oauthjwtdemo.user;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/users/me")
	public Principal me(Principal principal) {
		return principal;
	}
}
