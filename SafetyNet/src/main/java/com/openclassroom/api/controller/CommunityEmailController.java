package com.openclassroom.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunityEmailController {

	@GetMapping("/test")
	public String saycoucou() {
		return "coucou";
	}
}
