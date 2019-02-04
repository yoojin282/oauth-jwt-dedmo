package com.yoojin282.oauthjwtdemo.helllo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello")
	@ResponseStatus(code=HttpStatus.OK)
	public String hello() {
		return "hello";
	}
	
	@GetMapping("/imgroot")
	public String imGroot() {
		return "imgroot";		
	}
}
