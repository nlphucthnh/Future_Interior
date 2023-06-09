package com.spring.main.controller.admin;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.main.entity.TaiKhoan;
import com.spring.main.service.CookieService;
import com.spring.main.service.ParamService;
import com.spring.main.service.SessionService;
import com.spring.main.entity.*;
import com.spring.main.dao.TaiKhoanDAO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginAdminController {
	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

	@Autowired
	ServletContext context;
	
	@Autowired
	TaiKhoanDAO dao;
	
	@Autowired
	CookieService cookieService;

	
	@Autowired
	ParamService paramService;

	/*
	 * @Autowired MailerService mailer;
	 */
	@Autowired
	SessionService session;
	
	
	// đăng nhập
	@PostMapping("/Manager/login")
	public String ManageLoginPage(@Validated @ModelAttribute("tai_khoan") TaiKhoan tai_khoan, Model model, BindingResult errors, @RequestParam("ten_dang_nhap") String ten_dang_nhap,
			@RequestParam("mat_khau") String mat_khau, @RequestParam(name = "remember", defaultValue = "false") Boolean remember)
			throws NoSuchAlgorithmException {
	
		// bắt null tên đăng nhập và mật khẩu
		if("".equals(ten_dang_nhap.trim())){
			model.addAttribute("message","Tên đăng nhập không được bỏ trống");
			return "redirect:/Manager/login";
		}else if("".equals(mat_khau.trim())) {
			model.addAttribute("message","Mật khẩu không được bỏ trống");
			return "redirect:/Manager/login";
		}
		
		TaiKhoan taikhoan = dao.findById(ten_dang_nhap).get();
		
		if(taikhoan == null || !mat_khau.equals(taikhoan.getMat_khau())) {
			model.addAttribute("message", "Sai tên đăng nhập hoặc mật khẩu");
			return "redirect:/Manager/login";
		}else if(!taikhoan.isTrang_thai()) {
			model.addAttribute("message", "Tài khoản chưa được kích hoạt");
			return "redirect:/Manager/login";
		}

		// đăng nhập thành công
		if (ten_dang_nhap.equals(taikhoan.getTen_dang_nhap()) && mat_khau.equals(taikhoan.getMat_khau()) && taikhoan.isTrang_thai()==true) {
			
			session.set("ten_dang_nhap", taikhoan);
			session.set("isLogin", true);
			
			// lưu thông tin tài khoản và mật khẩu vào Cookie
			if (remember == true) {
				cookieService.add("ten_dang_nhap", ten_dang_nhap, 10);
				cookieService.add("mat_khau", mat_khau, 10);
				model.addAttribute("ten_dang_nhap", taikhoan);
			} else {
				cookieService.remove("ten_dang_nhap");
				cookieService.remove("mat_khau");
			}
			
			// vai trò admin mới được đăng nhập
			if (taikhoan.isVai_tro() == true) {
				session.set("isVaiTro", true);
				model.addAttribute("ten_dang_nhap", taikhoan);
				model.addAttribute("message", "Đăng nhập thành công!");
				
				return "redirect:/Manager/blog";
			} else {
				session.set("isVaiTro", false);
			}
			model.addAttribute("message", "Đăng nhập thất bại!");
			return "redirect:/Manager/login";
		
		} else {
			session.set("isLogin", false);
			model.addAttribute("message", "Đăng nhập thất bại!");
			return "redirect:/Manager/login";
		}
	}
	
	// đăng xuất 
	@RequestMapping("/Manager/logout")
	public String logout() {
		session.remove("ten_dang_nhap");
		session.remove("isLogin");
		session.remove("isAdmin");
		cookieService.remove("ten_dang_nhap");
		cookieService.remove("mat_khau");
		return "redirect:/Manager/login";
	}
}
