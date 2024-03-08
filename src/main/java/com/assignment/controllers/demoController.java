package com.assignment.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demoController {

	@GetMapping("/greet")
	public String greet()
	{
		return "Hello everyone";
	}
}
