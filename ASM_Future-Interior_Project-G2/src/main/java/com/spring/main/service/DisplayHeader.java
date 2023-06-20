package com.spring.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.main.dao.TaiKhoanDAO;
import com.spring.main.entity.TaiKhoan;

import jakarta.servlet.http.HttpSession;

@Service
public class DisplayHeader {
	@Autowired
	SessionService session;
	@Autowired
	CookieService cookieService;
	@Autowired
	HttpSession sessions;
	@Autowired
	TaiKhoanDAO tkdao;

	public void displayHeader(Model model) {
		TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
		if (cookieService.getValue("tenDangNhap", "") != "") { // Cookie tồn tại
			String tenDangNhap = cookieService.getValue("tenDangNhap", ""); // Lấy username từ ck
			taiKhoan = tkdao.findByTenDangNhap(tenDangNhap); // Tìm người dùng có username như trong ck
			// Gán người dùng đó lên thanh header
			model.addAttribute("usernameHeader", taiKhoan.getTenDangNhap());
			model.addAttribute("emailHeader", taiKhoan.getEmail());
		}
		if (taiKhoan != null) { // Người dùng đã đăng nhập --> header thành
			model.addAttribute("onRegistered", true);
			model.addAttribute("TaiKhoanUser", taiKhoan);
			// Người dùng trong cookie ko null
			model.addAttribute("usernameHeader", taiKhoan.getTenDangNhap());
			model.addAttribute("emailHeader", taiKhoan.getEmail());
			model.addAttribute("username", taiKhoan.getTenDangNhap());
		} else {
			model.addAttribute("onRegistered", false);
		}
	}

	public TaiKhoan getNguoiDung() {
		TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
		if (cookieService.getValue("tenDangNhap", "") != "") { // Cookie tồn tại
			String tenDangNhap = cookieService.getValue("tenDangNhap", ""); // Lấy username từ ck
			taiKhoan = tkdao.findByTenDangNhap(tenDangNhap); // Tìm người dùng có username như trong ck
		}
		return taiKhoan;
	}
}
