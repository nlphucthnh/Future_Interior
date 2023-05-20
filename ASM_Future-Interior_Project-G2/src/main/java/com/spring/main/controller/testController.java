package com.spring.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class testController {
	
	
	@RequestMapping(value = "/product/list", method= RequestMethod.GET)
	public String getAboutPage() {
		return "productlist";
	}
}
