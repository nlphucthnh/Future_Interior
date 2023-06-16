 package com.spring.main.controller.user;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.main.bean.MailInfo;
import com.spring.main.entity.TaiKhoan;

@Controller
public class UserController {

@Autowired
HttpSession sessions;
	
// 	@GetMapping("/login-page")
// 	public String getLoginPage() {
// 		return "dangnhap";
// 	}
	
// 	@GetMapping("/sign-up-page")
// 	public String getSiginPage() {
// 		return "dangky";
// 	}

	

	
	@GetMapping("/home-page")
	public String getHomePage(Model model) {
		TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
		if(taiKhoan != null){
			model.addAttribute("onRegistered", true);
			model.addAttribute("TaiKhoanUser", taiKhoan);
		}else {
			model.addAttribute("onRegistered", false);
		}
		return "index";
	}
	
	// @GetMapping("/blog-page")
	// public String getBlogPage() {
	// 	return "blog";
	// }
	
	@GetMapping("/blogs-item-page")
	public String getBlogItemPage(Model model) {
		TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
		if(taiKhoan != null){
			model.addAttribute("onRegistered", true);
			model.addAttribute("TaiKhoanUser", taiKhoan);
		}else {
			model.addAttribute("onRegistered", false);
		}
		return "blog-item";
	}
// 	@GetMapping("/about-page")
// 	public String getAboutPage() {
// 		return "about";
// 	}

	@GetMapping("/about-page")
	public String getAboutPage(Model model) {
		TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
		if(taiKhoan != null){
			model.addAttribute("onRegistered", true);
			model.addAttribute("TaiKhoanUser", taiKhoan);
		}else {
			model.addAttribute("onRegistered", false);
		}
		return "about";
	}

//	@GetMapping("/cart-page")
//	public String getCartPage() {
//		return "cart";
//	}

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
