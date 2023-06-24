package com.spring.main.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.main.entity.TaiKhoan;
import com.spring.main.service.CookieService;
import com.spring.main.service.ParamService;
import com.spring.main.service.SessionService;

import com.spring.main.dao.TaiKhoanDAO;

@Controller
public class LoginManagerController {

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@Autowired
	CookieService cookieService;

	@Autowired
	ParamService paramService;

	/*
	 * @Autowired MailerService mailer;
	 */
	@Autowired
	SessionService session;

	@GetMapping("/Manager/login")
	public String ManageLoginPage() {
		return "Manager-login-page";
	}

	@ResponseBody
	@RequestMapping("/Manager/login/json")
	public TaiKhoan getJsonData(@RequestParam(name = "username") String username){
		TaiKhoan taiKhoan = taiKhoanDAO.findByTenDangNhap(username);
		return taiKhoan;
	}

	// đăng nhập
	@PostMapping("/Manager/login")
	public String ManageLoginPage( @RequestParam(name = "tenDangNhap" , defaultValue = "") String un ,@RequestParam(name = "matKhau", defaultValue = "") String pw ,@RequestParam(name = "remember", defaultValue = "false") Boolean remember, Model model) {
		if ("".equals(un.trim())) {
			model.addAttribute("message", "Tên đăng nhập không được để trống!");
			return "Manager-login-page";
		} else if ("".equals(pw.trim())) {
			model.addAttribute("message", "Mật khẩu không được để trống!");
			return "Manager-login-page";
		}
		
		TaiKhoan taikhoan = taiKhoanDAO.findByTenDangNhap(un);
		if (taiKhoanDAO.existsById(un) == false || !pw.equals(taikhoan.getMatKhau())) {
			model.addAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
			System.out.println("Sai tên đăng nhập hoặc mật khẩu");
			return "Manager-login-page";

		} else if (!taikhoan.isTrangThai()) {
			model.addAttribute("message", "Tài khoản chưa được kích hoạt!");
			System.out.println("Tài khoản chưa được kích hoạt");
			return "Manager-login-page";

		}
		System.out.println("Đã qua đây 01");

		// đăng nhập thành công
		if (un.equals(taikhoan.getTenDangNhap()) && pw.equals(taikhoan.getMatKhau()) && taikhoan.isTrangThai() == true
				&& taikhoan.isVaiTro() == true) {
			session.set("Admin", taikhoan);
			session.set("isLogin", true);
			session.set("isVaiTro", true);
			// lưu thông tin tài khoản và mật khẩu vào Cookie
			if (remember == true) {
				cookieService.add("tenDangNhap", un, 10);
				cookieService.add("matKhau", pw, 10);
				model.addAttribute("message", "Đăng nhập thành công!");
			} else {
				cookieService.remove("tenDangNhap");
				cookieService.remove("matKhau");
				model.addAttribute("message", "Đăng nhập thành công!");
			}
			System.out.println("Đã chạy qua đây");
			return "redirect:/Manager/blog";
		} else {
			session.set("isLogin", false);
			session.set("isVaiTro", false);
			model.addAttribute("message", "Đăng nhập thất bại!");

		}
		System.out.println("Đã qua đây 02");
		return "Manager-login-page";
	}

	// đăng xuất
	@RequestMapping("/Manager/logout")
	public String logout() {
		session.remove("Admin");
		session.remove("isLogin");
		session.remove("isVaiTro");
		cookieService.remove("ten_dang_nhap");
		cookieService.remove("mat_khau");
		return "redirect:/Manager/login";
	}
}
