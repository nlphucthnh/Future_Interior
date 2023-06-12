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
    public String ManageLoginPage(@RequestParam(name = "tenDangNhap") String tenDangNhapForm,
            @RequestParam(name = "matKhau") String matKhauForm, Model model) {
        TaiKhoan taiKhoandataBase = taiKhoanDAO.findByTenDangNhap(tenDangNhapForm);
        System.out.println(tenDangNhapForm);
        System.out.println(taiKhoandataBase);
        boolean rm = paramService.getBoolean("remember", false);
        if (taiKhoandataBase == null) { // tức nó không có trong database
            model.addAttribute("MessageWarning", true); // tính hiện để thông báo tên đăng nhập không tồn tại.
        } else {
            if (tenDangNhapForm.equals(taiKhoandataBase.getTenDangNhap())
                    && matKhauForm.equals(taiKhoandataBase.getMatKhau()) && taiKhoandataBase.isTrangThai()) { // && taiKhoandataBase.isTrangThai()==true
                // Account accs = new Account(un, pw);
                session.set("tenDangNhap", tenDangNhapForm);
                session.set("matKhau", matKhauForm);
                if (rm) {
                    cookieService.add("tenDangNhap", tenDangNhapForm, 10);
                    cookieService.add("matKhau", matKhauForm, 10);
                    System.out.println("dang nhap thanh cong elseeee");

                } else {
                    cookieService.remove("tenDangNhap");
                    cookieService.remove("matKhau");
                    System.out.println("dang nhap thanh cong");
                }
                // model.addAttribute("cookie", cookieService.getValue("user", ""));
            }
            return "redirect:/home-page";
        }
        // if(!taikhoan.isTrangThai()) {
        // model.addAttribute("message", "Tài khoản chưa được kích hoạt!");
        // System.out.println("Tài khoản chưa được kích hoạt");
        // return "dangnhap";

        // }
        return "dangnhap";
    }

}
