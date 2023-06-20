package com.spring.main.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.main.entity.TaiKhoan;
import com.spring.main.service.SessionService;

import jakarta.servlet.http.HttpSession;



@Controller
public class UserController {
	
@Autowired
SessionService session;

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

	@GetMapping("/404")
	public String get404(){
		return "Message-404-page";
	}

	@GetMapping("/")
	public String getIndex(Model model) {
		TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
		if(taiKhoan != null){
			model.addAttribute("onRegistered", true);
			model.addAttribute("TaiKhoanUser", taiKhoan);
		}else {
			model.addAttribute("onRegistered", false);
		}
		return "index";
	}
	
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


	@GetMapping("/User/about")
	public String getAboutPage(Model model) {
		TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
		if(taiKhoan != null){
			model.addAttribute("onRegistered", true);
			model.addAttribute("TaiKhoanUser", taiKhoan);
		}else {
			model.addAttribute("onRegistered", false);
		}
		return "User-about-page";
	}

}
