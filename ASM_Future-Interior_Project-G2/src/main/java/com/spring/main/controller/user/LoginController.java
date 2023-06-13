package com.spring.main.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.main.dao.TaiKhoanDAO;
import com.spring.main.entity.TaiKhoan;
import com.spring.main.service.CookieService;
import com.spring.main.service.ParamService;
import com.spring.main.service.SessionService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@Controller
public class LoginController {

    @Autowired
    TaiKhoanDAO taiKhoanDAO;

    @Autowired
    CookieService cookieService;

    @Autowired
    ParamService paramService;

    @Autowired
    SessionService session;

    @GetMapping("/login-page")
    public String form(@ModelAttribute("sv") TaiKhoan TaiKhoan, Model model) {
        return "dangnhap";
    }

    @ResponseBody
    @RequestMapping("/login-page/json/data")
    public TaiKhoan getData(@RequestParam(name = "tenDangNhap") String tenDangNhap) {
        System.out.println(tenDangNhap);
        TaiKhoan taiKhoan = taiKhoanDAO.findByTenDangNhap(tenDangNhap);
        return taiKhoan;
    }

    @PostMapping("/login-page/save")
    public String ManageLoginPage(@RequestParam(name = "tenDangNhap") String un,
            @RequestParam(name = "matKhau") String pw, Model model,@RequestParam(name = "remember" , defaultValue = "false") Boolean remember) {
    	List<TaiKhoan> list = new ArrayList<>();
		TaiKhoan taikhoan = taiKhoanDAO.findById(un).get();
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
    				return "redirect:/home-page";
    			} else {
    				session.set("isLogin", false);
    				session.set("isVaiTro", false);
    				model.addAttribute("message", "Đăng nhập thất bại!");

    			}
    	
        return "dangnhap";
    }

}
