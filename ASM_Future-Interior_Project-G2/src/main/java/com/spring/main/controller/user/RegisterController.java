package com.spring.main.controller.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale.Category;
import java.util.Objects;
import java.util.Random;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.main.dao.TaiKhoanDAO;
import com.spring.main.dao.ThongTinTaiKhoanDAO;
import com.spring.main.entity.TaiKhoan;
import com.spring.main.entity.ThongTinTaiKhoan;
import com.spring.main.service.MailerService;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.validation.Valid;

@Controller
// @Validated
public class RegisterController {
    @Autowired
    TaiKhoanDAO taiKhoanDAO;

    @Autowired
    ThongTinTaiKhoanDAO thongTinTaiKhoanDAO;

    @Autowired
    MailerService mailer;
    private String verifiCode;
    private TaiKhoan taiKhoanSave = new TaiKhoan();

    @GetMapping("/User/register")
    public String form(Model model) {
        TaiKhoan tk = new TaiKhoan();
        model.addAttribute("taiKhoan", tk);
        return "User-register-page";
    }

    private String generateOTP() {
        int otpLength = 6;
        String otpCharacters = "0123456789";
        Random random = new Random();

        StringBuilder otp = new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            int index = random.nextInt(otpCharacters.length());
            otp.append(otpCharacters.charAt(index));
        }
        return otp.toString();
    }

    @GetMapping("/User/verification")
    public String getConfimAccount() {
        return "User-verification-page";
    }

    @ResponseBody
    @RequestMapping("/User/verification/json")
    public String getverificationCode() {
        return verifiCode;
    }

    @RequestMapping("/User/verification/handle")
    public String handleConfimAccount(@RequestParam(name = "verificationCode") String verificationCode) {
        if (verificationCode.equals(verifiCode)) {
            TaiKhoan taiKhoanDataBase = taiKhoanDAO.save(taiKhoanSave);
            ThongTinTaiKhoan thongTinTaiKhoan = new ThongTinTaiKhoan();
            thongTinTaiKhoan.setTaiKhoanTTTK(taiKhoanDataBase);
            thongTinTaiKhoanDAO.save(thongTinTaiKhoan);
            taiKhoanSave = new TaiKhoan();
            return "redirect:/User/login";
        }

        return "redirect:/User/verification";
    }

    @PostMapping("/User/register/save")
    public String save(@RequestParam(name = "confirmpw") String confirmpw,
            @Valid @ModelAttribute("taiKhoan") TaiKhoan taiKhoan, BindingResult result, Model model) throws MessagingException {
        // kiểm tra có lỗi hay không
        if (result.hasErrors()) { // trường hợp bắt những lỗi null, trống
            model.addAttribute("message", "Vui lòng sửa các lỗi sau");
            return "User-register-page";
            // RegexValidator
        }
        if (taiKhoanDAO.existsByTenDangNhap(taiKhoan.getTenDangNhap())) {
            model.addAttribute("duplicateUsername", true);
            return "User-register-page"; // [a-zA-Z0-9]{5,10}
        }else if (taiKhoanDAO.findByEmail(taiKhoan.getEmail()) != null) {
            model.addAttribute("emailMessage", true);
            return "User-register-page";
        } else if (!taiKhoan.getTenDangNhap().matches("[a-zA-Z0-9]{5,20}")) {
            model.addAttribute("tenDangNhapError", "Tên đăng nhập không hợp lệ");
            return "User-register-page"; // ^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$
        } else if (!taiKhoan.getMatKhau().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$")) {
            model.addAttribute("matKhauError", "Mật khẩu không hợp lệ");
            System.out.println("eles if 111" + taiKhoan.getMatKhau());
            return "User-register-page";
        } else if (!taiKhoan.getMatKhau().equals(confirmpw)) {// trường hợp này mình bắt lỗi xác nhận với mật khẩu
            model.addAttribute("confirnMessage", true); // tính hiệu để bật thông báo
            return "User-register-page";
        } else { // nếu không lọt vao các trường hợp trên thì tài khoản dăng ký hợp lệ nên thông
                 // báo đăng ký thành công.
            taiKhoanSave = taiKhoan;
            verifiCode = generateOTP();
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            String subject = "Lấy Lại Mật Khẩu";
            String body = "<p>Chào bạn, Cảm ơn bạn đã ủng hộ Shop</p>"
                    + "<p>Dưới đây là mã OTP của bạn.</p>"
                    + "<p>Mã này được tạo vào lúc: " + formattedDateTime + "</p>"
                    + "<p>Mã Verification của bạn là: " + verifiCode + "</p>"
                    + "<p>Mã này dược tạo tự động, Vui lòng không trả lời. </p>";
            mailer.send(taiKhoan.getEmail(), subject, body);
            return "redirect:/User/verification";

        }
    }

}
