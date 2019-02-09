package com.yoojin282.oauthjwtdemo.user;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/users/me")
	public UserDTO.Response me(Authentication auth) {
		
		return new UserDTO.Response(auth.getName(), auth.getAuthorities());
	}
}
