package com.spring.main.controller.user;

import java.util.Locale.Category;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.main.dao.TaiKhoanDAO;
import com.spring.main.entity.TaiKhoan;

import jakarta.validation.Valid;

@Controller
public class USSignInController {
    @Autowired
    TaiKhoanDAO taiKhoanDAO;

    // @RequestMapping("/sign-up-page")
    // public String getSiginPage(Model model) {
    // TaiKhoan item = new TaiKhoan();
    // // model.addAttribute("item", item); // item buộc lên form
    // return "dangky";
    // }

    @GetMapping("/sign-up-page")
    public String form(@ModelAttribute("sv") TaiKhoan taiKhoan, Model model) {
        TaiKhoan tk = new TaiKhoan();
        model.addAttribute("student", tk);
        return "dangky";
    }

    @PostMapping("/student/save")
    public String save(@Valid @ModelAttribute("student") TaiKhoan taiKhoan, BindingResult result, Model model) {
        boolean duplicateUsername = false;
        // kiểm tra có lỗi hay không
        if (result.hasErrors()) {
            model.addAttribute("message", "Vui lòng sửa các lỗi sau");
            // if (taiKhoan.getMatKhau() !=) {

            // }
        } else {
            // Kiểm tra tên đăng nhập trùng lặp
            if (taiKhoanDAO.existsByTenDangNhap(taiKhoan.getTenDangNhap())) {
                duplicateUsername = true;
            } else {
                taiKhoanDAO.save(taiKhoan);
                model.addAttribute("message", "Chúc mừng, bạn đã đăng ký thành công");
            }
        }

        model.addAttribute("duplicateUsername", duplicateUsername);
        // model.addAttribute("student", taiKhoan);

        return "dangky";
    }

    // @RequestMapping("/category/create")
    // public String create(Category item) {
    // dao.save(item);
    // return "redirect:/category/index";
    // }

}
