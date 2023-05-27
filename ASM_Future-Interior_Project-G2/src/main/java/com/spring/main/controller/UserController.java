package com.spring.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	@GetMapping("/home-page")
	public String getHomePage() {

		return "home";
	}

	@GetMapping("/about-page")
	public String getAboutPage() {
		return "about";
	}

	@GetMapping("/contact-page")
	public String getContactPage() {
		return "contact";
	}
	@GetMapping("/product-page")
	public String getProductPage() {
		return "product";
	}

	@GetMapping("/products-list-page")
	public String getProductList() {
		return "product-list";
	}
	@GetMapping("/product-item-page")
	public String getProductItem() {
		return "product-item";
	}
	@GetMapping("/cart-page")
	public String getCartPage() {
		return "cart";
	}
	
	@GetMapping("/adreess-page")
	public String getAddressPage() {
		return "adreess";
	}
	@GetMapping("/pay-page")
	public String getPayPage() {
		return "pay";
	}

	@GetMapping("/demo")
	public String getDemo() {
		return "demo";
	}

}
