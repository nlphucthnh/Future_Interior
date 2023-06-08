package com.spring.main.controller.admin;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.main.entity.TaiKhoan;
import com.spring.main.service.CookieService;
import com.spring.main.service.ParamService;
import com.spring.main.service.SessionService;
import com.spring.main.service.TaiKhoanService;

import com.spring.main.dao.TaiKhoanDAO;

import jakarta.validation.Valid;

@Controller
public class LoginAdminController {

	@Autowired
	TaiKhoanDAO dao;

	
	/*
	 * @Autowired TaiKhoanService accountService;
	 */
	
	@Autowired
	CookieService cookieService;

	
	@Autowired
	ParamService paramService;

	/*
	 * @Autowired MailerService mailer;
	 */
	@Autowired
	SessionService session;
	
	@PostMapping("/Manager/login")
	public String ManageLoginPage(@Valid @ModelAttribute TaiKhoan tai_khoan, @RequestParam("ten_dang_nhap") String ten_dang_nhap,
			@RequestParam("mat_khau") String mat_khau, @RequestParam(name = "remember", defaultValue = "true") Boolean remember)
			throws NoSuchAlgorithmException {
		TaiKhoan TaiKhoan = dao.findById(ten_dang_nhap).get();
		if (ten_dang_nhap.equals(TaiKhoan.getTen_dang_nhap()) && mat_khau.equals(TaiKhoan.getMat_khau())) {
			session.set("ten_dang_nhap", TaiKhoan);
			session.set("isLogin", true);
			if (remember == true) {
				cookieService.add("ten_dang_nhap", ten_dang_nhap, 10);
				cookieService.add("mat_khau",mat_khau, 10);
			} else {
				cookieService.remove("ten_dang_nhap");
				cookieService.remove("mat_khau");
			}
			if (TaiKhoan.isVai_tro() == true) {
				session.set("isVaiTro", true);
				return "redirect:/Manager/blog";
			} else {
				session.set("isVaiTro", false);
			}
			return "redirect:/Manager/login";
		} else {
			session.set("isLogin", false);
			return "redirect:/Manager/login";
		}
	}
}
