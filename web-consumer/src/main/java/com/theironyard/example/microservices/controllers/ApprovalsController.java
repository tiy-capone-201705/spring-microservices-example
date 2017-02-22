package com.theironyard.example.microservices.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/approvals")
public class ApprovalsController {
	@RequestMapping(method=RequestMethod.GET)
	public String dashboard(Model model) {
		return "approvals/index";
	}
}
