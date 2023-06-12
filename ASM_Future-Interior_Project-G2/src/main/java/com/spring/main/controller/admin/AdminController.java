package com.spring.main.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	
//	---------------- GET URL MANAGER PAGE ----------------//
	
	// @GetMapping("/Manager/login")
	// public String ManageLoginPage() {
	// 	return "Manager-login-page";
	// }
	
	
//	@GetMapping("/Manager/blog")
//	public String ManageBlogPage() {
//		return "Manager-blog-page";
//	}
	
	@GetMapping("/Manager/revenue")
	public String ManageRevunuePage() {
		return "Manager-revenue-page";
	}
	
	@GetMapping("/Manager/order")
	public String ManageOrderPage() {
		return "Manager-order-page";
	}
	
	@GetMapping("/Manager/product")
	public String ManageProductPage() {
		return "Manager-product-page";
	}
	
	
//	@GetMapping("/Manager/filter")
//	public String ManageFilterPage() {
//		return "Manager-filter-page";
//	}
//	
//	@GetMapping("/Manager/account")
//	public String ManageAccountPage() {
//		return "Manager-account-page";
//	}
	
	
}
