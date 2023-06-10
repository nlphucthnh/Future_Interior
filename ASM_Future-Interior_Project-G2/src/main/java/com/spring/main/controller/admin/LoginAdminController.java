package com.spring.main.controller.admin;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
	public String ManageLoginPage (@Valid @ModelAttribute("TaiKhoan") TaiKhoan TaiKhoan, Model model, @RequestParam(name = "remember", defaultValue = "false") Boolean remember, BindingResult errors) {
		
//		
		if(errors.hasErrors()){
			model.addAttribute("message", "Vui lòng nhập đúng thông tin!");
			return "Manager/login";
        }
		
		String un = paramService.getString("tenDangNhap", "");
		String pw = paramService.getString("matKhau", "");
		
		if("".equals(un.trim())) {
			model.addAttribute("message", "Tên đăng nhập không được để trống!");
			return "Manager-login-page";
		}else if("".equals(pw.trim())){
			model.addAttribute("message", "Mật khẩu không được để trống!");
			return "Manager-login-page";
		}
		List<TaiKhoan> list = new ArrayList<>();
		TaiKhoan taikhoan = dao.findById(un).get();
//		Chưa bắt sai tenDangNhap
		System.out.println("TAIKHOAN == "+TaiKhoan);
		System.out.println("taikhoan == "+taikhoan);
				if(!un.equals(taikhoan.getTenDangNhap()) || !pw.equals(taikhoan.getMatKhau())) {
					model.addAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
					System.out.println("Sai tên đăng nhập hoặc mật khẩu");
					System.out.println(TaiKhoan);
					return "Manager-login-page";
					
				}else if(!taikhoan.isTrangThai()) {
					model.addAttribute("message", "Tài khoản chưa được kích hoạt!");
					System.out.println("Tài khoản chưa được kích hoạt");
					return "Manager-login-page";
					
				}
				
		
	   
				// đăng nhập thành công
				if (un.equals(taikhoan.getTenDangNhap()) && pw.equals(taikhoan.getMatKhau()) && taikhoan.isTrangThai()==true && taikhoan.isVaiTro()==true) {
					
					session.set("tenDangNhap", taikhoan);
					session.set("isLogin", true);
					session.set("isVaiTro", true);
					// lưu thông tin tài khoản và mật khẩu vào Cookie
					if (remember == true) {
						cookieService.add("tenDangNhap", un, 10);
						cookieService.add("matKhau", pw, 10);
						model.addAttribute("message", "Đăng nhập thành công!");
						//return "redirect:/Manager/blog";
					} else {
						
						cookieService.remove("tenDangNhap");
						cookieService.remove("matKhau");
						model.addAttribute("message", "Đăng nhập thành công!");
						
						//return "redirect:/Manager/blog";
					}
					
					return "Manager-blog-page";
				} else {
					session.set("isLogin", false);
					session.set("isVaiTro", false);
					model.addAttribute("message", "Đăng nhập thất bại!");
					
				}
				
				return "Manager-login-page";
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
