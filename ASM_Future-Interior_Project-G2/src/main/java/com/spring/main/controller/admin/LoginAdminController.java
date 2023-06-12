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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@RequestMapping("/Manager/login/json/{id}")
	public TaiKhoan getJsonData(@PathVariable("id") String username){
		TaiKhoan taiKhoan = taiKhoanDAO.findByTenDangNhap(username);
		return taiKhoan;
	}

	// đăng nhập
	@PostMapping("/Manager/login")
	public String ManageLoginPage( @RequestParam(name = "tenDangNhap" , defaultValue = "") String un ,@RequestParam(name = "matKhau", defaultValue = "") String pw ,@RequestParam(name = "remember", defaultValue = "false") Boolean remember, Model model) {

		//
		// if (result.hasErrors()) {
		// 	model.addAttribute("message", "Vui lòng nhập đúng thông tin!");
		// 	System.out.println(result.getErrorCount());

		// 	return "Manager-login-page";
		// }
		if ("".equals(un.trim())) {
			model.addAttribute("message", "Tên đăng nhập không được để trống!");
			return "Manager-login-page";
		} else if ("".equals(pw.trim())) {
			model.addAttribute("message", "Mật khẩu không được để trống!");
			return "Manager-login-page";
		}
		List<TaiKhoan> list = new ArrayList<>();
		TaiKhoan taikhoan = taiKhoanDAO.findById(un).get();
		// Chưa bắt sai tenDangNhap
		System.out.println("taikhoan == " + taikhoan);
		if (taiKhoanDAO.existsById(un) == false || !pw.equals(taikhoan.getMatKhau())) {
			model.addAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
			System.out.println("Sai tên đăng nhập hoặc mật khẩu");
			return "Manager-login-page";

		} else if (!taikhoan.isTrangThai()) {
			model.addAttribute("message", "Tài khoản chưa được kích hoạt!");
			System.out.println("Tài khoản chưa được kích hoạt");
			return "Manager-login-page";

		}

		// đăng nhập thành công
		if (un.equals(taikhoan.getTenDangNhap()) && pw.equals(taikhoan.getMatKhau()) && taikhoan.isTrangThai() == true
				&& taikhoan.isVaiTro() == true) {

			session.set("tenDangNhap", taikhoan);
			session.set("isLogin", true);
			session.set("isVaiTro", true);
			// lưu thông tin tài khoản và mật khẩu vào Cookie
			if (remember == true) {
				cookieService.add("tenDangNhap", un, 10);
				cookieService.add("matKhau", pw, 10);
				model.addAttribute("message", "Đăng nhập thành công!");
				// return "redirect:/Manager/blog";
			} else {
				cookieService.remove("tenDangNhap");
				cookieService.remove("matKhau");
				model.addAttribute("message", "Đăng nhập thành công!");

				// return "redirect:/Manager/blog";
			}
			System.out.println("Đã chạy qua đây");
			return "redirect:/Manager/blog";
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
