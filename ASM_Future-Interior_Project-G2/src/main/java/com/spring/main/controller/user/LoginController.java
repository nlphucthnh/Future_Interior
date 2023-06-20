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
import com.spring.main.service.DisplayHeader;
import com.spring.main.service.ParamService;
import com.spring.main.service.SessionService;
import com.spring.main.validation.Login;

import jakarta.servlet.http.HttpSession;
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
    HttpSession sessions;

    @Autowired
    SessionService session;

    // @GetMapping("/login-page")
    // public String form(@ModelAttribute("sv") TaiKhoan TaiKhoan, Model model) {
    //     return "dangnhap";
    // }

    @ResponseBody
    @RequestMapping("/login-page/json/data")
    public TaiKhoan getData(@RequestParam(name = "tenDangNhap") String tenDangNhap) {
        System.out.println(tenDangNhap);
        TaiKhoan taiKhoan = taiKhoanDAO.findByTenDangNhap(tenDangNhap);
        return taiKhoan;
    }

    @RequestMapping("/logout")
    public String logout() {
        sessions.removeAttribute("TaiKhoanUser");
        cookieService.remove("tenDangNhap");
        return "redirect:/home-page";
    }

    @GetMapping("/login-page")
    public String login(Model model, @ModelAttribute("login") Login login) {
        return "dangnhap";
    }

    @PostMapping("/login-page")
    public String ManageLoginPage(@Valid @ModelAttribute("login") Login login,
            BindingResult result, Model model) {
        // Lấy tên đăng nhập
        TaiKhoan taiKhoandataBase = taiKhoanDAO.findByTenDangNhap(login.getTenDangNhap());
        boolean rm = paramService.getBoolean("remember", false);
        //Tên đăng nhập rỗng
        if (taiKhoandataBase == null) { // tức nó không có trong database
            model.addAttribute("MessageWarning", true); // tính hiệu để thông báo tên đăng nhập không tồn tại.
        } else { //Tên đăng nhập ko rỗng
            // check mật khẩu
            if (login.getTenDangNhap().equals(taiKhoandataBase.getTenDangNhap())
                    && login.getMatKhau().equals(taiKhoandataBase.getMatKhau()) && taiKhoandataBase.isTrangThai()) { // &&
                                                                                                              // taiKhoandataBase.isTrangThai()==true
                session.set("TaiKhoanUser", taiKhoandataBase);
                System.out.println("đã ====");
                sessions.setAttribute("AccoutUser", taiKhoandataBase);
                if (rm) {
                    cookieService.add("tenDangNhap", login.getTenDangNhap(), 10);
                } else {
                    cookieService.remove("tenDangNhap");
                }
                return "redirect:/home-page";
            } else {
                model.addAttribute("message", "Tài khoản hoặc mật khẩu không chính xác.");
            }
        }
        return "dangnhap";
    }


}
