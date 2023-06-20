package com.spring.main.controller.user;

import java.util.Locale.Category;
import java.util.Objects;

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

import com.spring.main.dao.TaiKhoanDAO;
import com.spring.main.entity.TaiKhoan;
import com.spring.main.service.DisplayHeader;
import com.spring.main.service.SessionService;

import jakarta.validation.Valid;

@Controller
// @Validated
public class USSignInController {
    @Autowired
    TaiKhoanDAO taiKhoanDAO;
    @Autowired
    SessionService ss;

    @GetMapping("/sign-up-page")
    public String form(@ModelAttribute("sv") TaiKhoan taiKhoan, Model model) {
        TaiKhoan tk = new TaiKhoan();
        model.addAttribute("taiKhoan", tk);
        return "dangky";
    }

    @PostMapping("/sign-up-page/save")
    public String save(@RequestParam(name = "confirmpw") String confirmpw,
            @Valid @ModelAttribute("taiKhoan") TaiKhoan taiKhoan, BindingResult result, Model model) {
        // kiểm tra có lỗi hay không
        if (result.hasErrors()) { // trường hợp bắt những lỗi null, trống
            model.addAttribute("message", "Vui lòng sửa các lỗi sau");
            return "dangky";
            // RegexValidator
        }

        // String tenDangNhap = taiKhoan.getTenDangNhap();

        if (taiKhoanDAO.existsByTenDangNhap(taiKhoan.getTenDangNhap())) {
            model.addAttribute("duplicateUsername", true);
            return "dangky"; // [a-zA-Z0-9]{5,10}

        } else if (!taiKhoan.getTenDangNhap().matches("[a-zA-Z0-9]{5,10}")) {
            model.addAttribute("tenDangNhapError", "Tên đăng nhập không hợp lệ");
            return "dangky"; // ^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$
        } else if (!taiKhoan.getMatKhau().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
             
            model.addAttribute("matKhauError", "Mật khẩu không hợp lệ");
            System.out.println("eles if 111" + taiKhoan.getMatKhau());
            return "dangky";
        } else if (!taiKhoan.getMatKhau().equals(confirmpw)) {// trường hợp này mình bắt lỗi xác nhận với mật khẩu
            System.out.println(taiKhoan.getMatKhau());
            System.out.println(confirmpw);
            model.addAttribute("confirnMessage", true); // tính hiệu để bật thông báo
            return "dangky";
        } else { // nếu không lọt vao các trường hợp trên thì tài khoản dăng ký hợp lệ nên thông
                 // báo đăng ký thành công.
            ss.remove("TaiKhoanUser");
            ss.set("TaiKhoanUser", taiKhoan);
            taiKhoanDAO.save(taiKhoan);
            model.addAttribute("sucess", true);//
            return "redirect:/home-page";

        }
    }

}
