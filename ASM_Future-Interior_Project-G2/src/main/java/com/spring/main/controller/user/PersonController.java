package com.spring.main.controller.user;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.main.bean.MailInfo;
import com.spring.main.dao.DonHangChiTietDAO;
import com.spring.main.dao.TaiKhoanDAO;
import com.spring.main.dao.ThongTinTaiKhoanDAO;
import com.spring.main.entity.DonHangChiTiet;
import com.spring.main.entity.TaiKhoan;
import com.spring.main.entity.ThongTinTaiKhoan;
import com.spring.main.service.MailerService;
import com.spring.main.service.SessionService;

import ch.qos.logback.core.util.DirectJson;
import jakarta.mail.internet.InternetAddress;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;


@Controller
public class PersonController {

    @Autowired
    SessionService session;

    @Autowired
    DonHangChiTietDAO dhctDAO;

    @Autowired
    ThongTinTaiKhoanDAO tttkDAO;

    @Autowired
    MailerService mailer;

    @Autowired
    TaiKhoanDAO tkDAO;

    @GetMapping("/User/person")
    public String person(Model model) {
        TaiKhoan tk = (TaiKhoan) session.get("TaiKhoanUser");
        TaiKhoan taiKhoan = tkDAO.findByTenDangNhap(tk.getTenDangNhap());
       
        if (taiKhoan != null) {
            model.addAttribute("onRegistered", true);
            model.addAttribute("TaiKhoanUser", taiKhoan);
        } else {
            model.addAttribute("onRegistered", false);
        }
        System.out.println("Đã qua đây");
        List<DonHangChiTiet> tkmuahang = dhctDAO.findByTaiKhoan(taiKhoan.getTenDangNhap());
        model.addAttribute("donhangct", tkmuahang);
        return "User-person-page";
    }


    public void reloadSessonUser(TaiKhoan taiKhoan){
        session.remove("TaiKhoanUser");
        session.set("TaiKhoanUser", taiKhoan);
    }

    @Transactional
    @PostMapping("/User/person/handle")
    public String personedit(Model model, @RequestParam("fullName") String fullName,
            @RequestParam("gender") boolean gender, @RequestParam("sdt") String phone) {
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
        if (taiKhoan != null) {
            model.addAttribute("onRegistered", true);
            model.addAttribute("TaiKhoanUser", taiKhoan);
        } else {
            model.addAttribute("onRegistered", false);
        }
        if (phone.length() != 10) {
            model.addAttribute("erroPhone", "Số điện thoại phải là 10 số");
        } else {
            tttkDAO.updateThongTinTaiKhoan(fullName, gender, phone, taiKhoan.getTenDangNhap());
        }
        List<DonHangChiTiet> tkmuahang = dhctDAO.findByTaiKhoan(taiKhoan.getTenDangNhap());
        model.addAttribute("donhangct", tkmuahang);
        return "redirect:/User/person";
    }

    // doi mk
    @GetMapping("/User/changePassword")
    public String changepass(Model model) {
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
        model.addAttribute("TaiKhoanUser", taiKhoan);
        return "User-change-page";
    }

    @PostMapping("/User/changePassword")
    public String change(Model model,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmNewPassword") String confirmNewPassword) {
        // List<TaiKhoan> change = tkDAO.findByTenDangNhapLike("thienlc");
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
        if (taiKhoan != null) {
            model.addAttribute("TaiKhoanUser", taiKhoan);
        }
        if (taiKhoan.getMatKhau().equals(currentPassword)) {
            if (newPassword.length() < 4 || newPassword.length() > 20) {
                model.addAttribute("length", "Mật khẩu từ 4 - 20 ký tự");
            } else if (taiKhoan.getMatKhau().equals(confirmNewPassword)) {
                model.addAttribute("length", "Mật khẩu đã chùng với mật khẩu củ");
            } else if (!newPassword.equals(confirmNewPassword)) {
                model.addAttribute("message1", "Nhập lại mật khẩu không đúng");
            } else {

                TaiKhoan updatedTaiKhoan = tkDAO.findByTenDangNhap(taiKhoan.getTenDangNhap());
                // Cập nhật mật khẩu mới trong đối tượng TaiKhoan
                updatedTaiKhoan.setMatKhau(newPassword);
                // Lưu thay đổi vào CSDL
                tkDAO.save(updatedTaiKhoan);
                model.addAttribute("success", "Đổi mật khẩu thành công");
                return "redirect:/User/person";
            }
        } else {
            model.addAttribute("message", "Nhập mật khẩu cũ sai");
        }

        return "redirect:/User/changePassword";
    }

    // // quen mat khau ?
    @GetMapping("/User/forgotPass")
    public String quenmatkhau(Model model) {
        model.addAttribute("mailInfo", new MailInfo());

        return "User-forgot-page";
    }

    private String generatedOTP; // Lưu trữ mã OTP đã tạo

    private String generateOTP() {
        int otpLength = 4;
        String otpCharacters = "0123456789";
        Random random = new Random();

        StringBuilder otp = new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            int index = random.nextInt(otpCharacters.length());
            otp.append(otpCharacters.charAt(index));
        }
        return otp.toString();
    }

    @PostMapping("/User/forgotPass")
    public String resetPassword(Model model, @RequestParam("forgot") String email) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        List<TaiKhoan> TaiKhoan = tkDAO.findAll();
        boolean falg = false;
        for (TaiKhoan taiKhoan2 : TaiKhoan) {
            if (taiKhoan2.getEmail().equals(email)) {
                falg = true;
                session.set("TaiKhoanfogot", taiKhoan2);
                break;
            }
        }

        if (falg) {
           try {
                generatedOTP = generateOTP(); // Lưu trữ mã OTP đã tạo

                String subject = "Lấy Lại Mật Khẩu";
                String body = "<html>"
                        + "<body>"
                        + "<div style=\"text-align: center;\">"
                        + "<img src=\"../img/Logowebsite.png\" alt=\"Logo\" style=\"width: 200px; height: auto; margin-bottom: 20px;\">"
                        + "<p style=\"font-size: 18px;\">Chào bạn, Cảm ơn bạn đã ủng hộ Shop</p>"
                        + "<p style=\"font-size: 18px;\">Dưới đây là mã OTP của bạn.</p>"
                        + "<p style=\"font-size: 18px;\">Mã này được tạo vào lúc: " + formattedDateTime + "</p>"
                        + "<p style=\"font-size: 24px; color: #FF0000; font-weight: bold;\">Mã OTP của bạn là: "
                        + generatedOTP + "</p>"
                        + "<p style=\"font-size: 18px;\">Mail này được tạo tự động, Vui lòng không trả lời.</p>"
                        + "</div>"
                        + "</body>"
                        + "</html>";
                mailer.send(email, subject, body);

           

                model.addAttribute("success", "Mã sẽ gửi về mail của bạn trong giây lát");

                return "redirect:/User/confirm";
            } catch (Exception e) {
                System.out.println("Lỗi khi gửi email: " + e);
            }
        } else {
            model.addAttribute("message", "Bạn đã nhập sai email");
        }
        return "User-forgot-page";
    }

    @GetMapping("/User/confirm")
    public String confirm() {
        return "User-confim-page";
    }

    @PostMapping("/User/confirm")
    public String confirmOTP(Model model, @RequestParam("otp") String otp) {

        try {
            // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date otpCreationTime = new Date(); // Thời gian tạo mã OTP
            Date otpExpiry = new Date(otpCreationTime.getTime() + TimeUnit.MINUTES.toMillis(1)); // Thời gian hết hạn
                                                                                                 // sau 1 phút

            if (otp.equals(generatedOTP) && new Date().before(otpExpiry)) {
                // Mã OTP hợp lệ và vẫn trong thời gian hiệu lực
                System.out.println("Thanhcong");
                model.addAttribute("messager", "Xác thực mã OTP thành công");

                return "redirect:/User/repass";
            } else if (!otp.equals(generatedOTP)) {
                model.addAttribute("messager", "Nhập lại mật khẩu không đúng");
                System.out.println("sai");
                return "redirect:/User/confirm";
            } else {
                // Mã OTP không hợp lệ hoặc đã hết hạn
                model.addAttribute("messager", "Mã OTP đã hết hạn");

                System.out.println("Hết hạn");
                return "redirect:/User/confirm";
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ khi chuyển đổi ngày
            model.addAttribute("messager", "Lỗi chuyển đổi ngày");
            System.out.println(e + "Lỗi luôn rồi");
            return "redirect:/User/confirm";
        }
    }

    @GetMapping("User/repass")
    public String restpassword() {
        return "User-respass-page";
    }

    @Transactional
    @PostMapping("User/repass")
    public String passwordNew(Model model, @RequestParam("passwordNew") String passwordnewm,
            @RequestParam("rePasswordNew") String repasswordnew) {
        // Kiểm tra độ dài mật khẩu mới
        if (passwordnewm.length() < 4 || passwordnewm.length() > 20) {
            model.addAttribute("error", "Mật khẩu phải từ 4 - 20 ký tự");
            return "/User-respass-page"; // Trả về trang resetpassword với thông báo lỗi
        } else

        // Kiểm tra độ dài mật khẩu nhập lại
        if (repasswordnew.length() < 4 || repasswordnew.length() > 20) {
            model.addAttribute("error1", "Mật khẩu phải từ 4 - 20 ký tự");
            return "/User-respass-page"; // Trả về trang resetpassword với thông báo lỗi
        } else

        // Kiểm tra mật khẩu mới và mật khẩu nhập lại
        if (!passwordnewm.equals(repasswordnew)) {
            model.addAttribute("error1", "Mật khẩu mới và mật khẩu nhập lại không khớp.");
            return "/User-respass-page"; // Trả về trang resetpassword với thông báo lỗi
        } else {
            TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanfogot");
            tkDAO.updatePassword(repasswordnew, taiKhoan.getTenDangNhap());
            model.addAttribute("sussuc", "Tạo mật khẩu thành công");
        }

        return "redirect:/User/login"; // Điều hướng đến trang thành công sau khi đặt lại mật khẩu thành công
    }

    // dang xuat
    @GetMapping("/User/logout")
    public String dangxuat() {
        session.remove("TaiKhoanUser");
        return "redirect:/User/login";
    }

    @PostMapping("/User/person/avata")
    public String uploadAvatar(@RequestParam(name = "urlImg")String urlImg ) {
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
        ThongTinTaiKhoan thongTinTaiKhoan = tttkDAO.findByTaiKhoanTTTK(taiKhoan);
        thongTinTaiKhoan.setAnhDaiDien(urlImg);
        taiKhoan.setThongTinTaiKhoan(thongTinTaiKhoan);
        tttkDAO.save(thongTinTaiKhoan);
        TaiKhoan taiKhoan2 = tkDAO.save(taiKhoan);
        reloadSessonUser(taiKhoan2);
        return "redirect:/User/person";
    }

}
