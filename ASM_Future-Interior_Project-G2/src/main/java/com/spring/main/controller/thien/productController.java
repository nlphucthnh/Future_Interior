package com.spring.main.controller.thien;

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

import org.apache.tiles.autotag.core.runtime.annotation.Parameter;
import org.hibernate.validator.constraints.Mod10Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.main.dao.DonHangChiTietDAO;
import com.spring.main.dao.DonHangDAO;
import com.spring.main.dao.PhanNhomLoaiDAO;
import com.spring.main.dao.SanPhamDAO;
import com.spring.main.dao.SanPhamKhuyenMaiDAO;
import com.spring.main.dao.TaiKhoanDAO;
import com.spring.main.dao.ThongTinTaiKhoanDAO;
import com.spring.main.entity.DonHang;
import com.spring.main.entity.DonHangChiTiet;
import com.spring.main.entity.MailInfo;
import com.spring.main.entity.PhanNhomLoai;
import com.spring.main.entity.SanPham;
import com.spring.main.entity.SanPhamKhuyenMai;
import com.spring.main.entity.TaiKhoan;
import com.spring.main.entity.ThongTinTaiKhoan;
import com.spring.main.service.MailerService;
import com.spring.main.service.SessionService;

import ch.qos.logback.core.util.DirectJson;
import jakarta.mail.internet.InternetAddress;
import jakarta.servlet.http.HttpSession;

@Controller
public class productController {

    @Autowired
    SanPhamDAO sanPhamDAO;

    @Autowired
    SanPhamKhuyenMaiDAO spkmDAO;

    @Autowired
    PhanNhomLoaiDAO pnlDao;

    @Autowired
    TaiKhoanDAO tkDAO;

    @Autowired
    DonHangDAO dhDAO;

    @Autowired
    DonHangChiTietDAO dhctDAO;

    @Autowired
    MailerService mailer;

    @Autowired
    ThongTinTaiKhoanDAO tttkDAO;

    @Autowired
    HttpSession sessions;

    @Autowired
    SessionService session;

    @GetMapping("/")
    public String index(Model model) {
        List<SanPham> phamList = sanPhamDAO.findAll();
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
        if (taiKhoan != null) {
            model.addAttribute("onRegistered", true);
            model.addAttribute("TaiKhoanUser", taiKhoan);
        } else {
            model.addAttribute("onRegistered", false);
        }
        model.addAttribute("products", phamList);
        model.addAttribute("onRegistered", false);
        var productsSale = spkmDAO.findAll();
        model.addAttribute("productsSales", productsSale);
        return "index";
    }

    @GetMapping("/product-page")
    public String getProduct(Model model, @RequestParam("p") Optional<Integer> p) {
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
        if (taiKhoan.getTenDangNhap() != null) {
            model.addAttribute("onRegistered", true);
            model.addAttribute("TaiKhoanUser", taiKhoan);
        } else {
            model.addAttribute("onRegistered", false);
        }
        var product = new SanPham();
        model.addAttribute("product", product);
        Pageable pageable = PageRequest.of(p.orElse(0), 12, Sort.by("tenSanPham").ascending());
        var products = sanPhamDAO.findAll(pageable);
        var numberOfPages = products.getTotalPages();
        model.addAttribute("currIndex", p.orElse(0));
        model.addAttribute("numberOfPages", numberOfPages);

        model.addAttribute("products", products);
        var productsSale = spkmDAO.findAll();
        model.addAttribute("productsSales", productsSale);
        return "product";
    }

    @GetMapping("/product-page/page")
    public String paginate(Model model, @RequestParam("p") Optional<Integer> p) {
        return this.getProduct(model, p);
    }

    @GetMapping("/product-page/edit/{idSanPham}")
    public String productItem(Model model, @PathVariable("idSanPham") String id) {
        SanPham spitem = sanPhamDAO.findById(id).get();
        model.addAttribute("spitem", spitem);
        var productsSale = spkmDAO.findAll();
        model.addAttribute("productsSales", productsSale);
        return "product-Item";
    }

    // product list-name
    @PostMapping("/product-list-page/{category}/name")
    public String productListPage(Model model, @PathVariable("category") String category,
            @RequestParam("selectName") String select) {
        model.addAttribute("title", category);
        if (select.equals("AZ")) {
            List<SanPham> prolists = sanPhamDAO.findByLoaiSanpham("%" + category + "%",
                    Sort.by(Direction.ASC, "tenSanPham"));
            model.addAttribute("prolists", prolists);
            model.addAttribute("messagename", "Lọc từ A - Z");
        } else {
            List<SanPham> prolists = sanPhamDAO.findByLoaiSanpham("%" + category + "%",
                    Sort.by(Direction.DESC, "tenSanPham"));
            model.addAttribute("prolists", prolists);
            model.addAttribute("messagename", "Lọc từ Z - A");
        }
        return "product-List";
    }

    @PostMapping("/product-list-page/{category}/price")
    public String productListPagePrice(Model model, @PathVariable("category") String category,
            @RequestParam("selectPrice") String selectPrice) {
        model.addAttribute("title", category);
        switch (selectPrice) {
            case "1":
                model.addAttribute("messages", "Lọc từ 0 đến 1.000.000");
                List<SanPham> prolists0_1 = sanPhamDAO.findByLoaiSanphamPrice("%" + category + "%",
                        Sort.by(Direction.ASC, "tenSanPham"), 0,
                        1000000);
                model.addAttribute("prolists", prolists0_1);
                break;
            case "2":
                model.addAttribute("messages", "Lọc từ 1.000.000 đến 5.000.000");
                List<SanPham> prolists1_5 = sanPhamDAO.findByLoaiSanphamPrice("%" + category + "%",
                        Sort.by(Direction.ASC, "tenSanPham"),
                        1000000,
                        5000000);
                model.addAttribute("prolists", prolists1_5);
                break;
            case "3":
                model.addAttribute("messages", "Lọc từ 5.000.000 đến 10.000.000");
                List<SanPham> prolists5_10 = sanPhamDAO.findByLoaiSanphamPrice("%" + category + "%",
                        Sort.by(Direction.ASC, "tenSanPham"),
                        5000000,
                        10000000);
                model.addAttribute("prolists", prolists5_10);
                break;
            case "4":
                model.addAttribute("messages", "Lọc từ 10.000.000 đến 15.000.000");
                List<SanPham> prolists10_15 = sanPhamDAO.findByLoaiSanphamPrice("%" + category + "%",
                        Sort.by(Direction.ASC, "tenSanPham"),
                        10000000,
                        15000000);
                model.addAttribute("prolists", prolists10_15);
                break;
            case "5":
                model.addAttribute("messages", "Lọc từ 15.000.000 đến 20.000.000");
                List<SanPham> prolists15_20 = sanPhamDAO.findByLoaiSanphamPrice("%" + category + "%",
                        Sort.by(Direction.ASC, "tenSanPham"),
                        15000000,
                        20000000);
                model.addAttribute("prolists", prolists15_20);
                break;
            case "6":
                model.addAttribute("messages", "Lọc từ 20.000.000 Trở lên");
                List<SanPham> prolists20_ = sanPhamDAO.findByLoaiSanphamPrice("%" + category + "%",
                        Sort.by(Direction.ASC, "tenSanPham"),
                        2000000,
                        10000000);
                model.addAttribute("prolists", prolists20_);
                break;
            default:
                model.addAttribute("messages", "Lỗi luôn");
                break;
        }

        return "product-List";
    }

    @GetMapping("/product-list-page/{category}")
    public String productListPage(Model model, @PathVariable("category") String category) {
        model.addAttribute("title", category);
        List<SanPham> prolists = sanPhamDAO.findByLoaiSanpham("%" + category + "%",
                Sort.by(Direction.ASC, "tenSanPham"));
        model.addAttribute("prolists", prolists);
        return "product-List";
    }

    /// person
    @GetMapping("/person")
    public String person(Model model) {
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
        if (taiKhoan.getTenDangNhap() != null) {
            model.addAttribute("onRegistered", true);
            model.addAttribute("TaiKhoanUser", taiKhoan);
        } else {
            model.addAttribute("onRegistered", false);
        }
        List<DonHangChiTiet> tkmuahang = dhctDAO.findByTaiKhoan(taiKhoan.getTenDangNhap());
        model.addAttribute("donhangct", tkmuahang);
        return "person";
    }

    @Transactional
    @PostMapping("/person")
    public String personedit(Model model, @RequestParam("fullName") String fullName,
            @RequestParam("gender") boolean gender, @RequestParam("sdt") String phone) {
        // var ss = sessions.getAttribute ("AccoutUser");
        // System.out.println(ss);
        if (phone.length() != 10) {
            model.addAttribute("erroPhone", "Số điện thoại phải là 10 số");
        } else {
            tttkDAO.updateThongTinTaiKhoan(fullName, gender, phone, "thienlv");
        }
        TaiKhoan people = tkDAO.findByTenDangNhap("thienlv");
        model.addAttribute("people", people);
        List<DonHangChiTiet> tkmuahang = dhctDAO.findByTaiKhoan("thienlv");
        model.addAttribute("donhangct", tkmuahang);
        return "person";
    }

    // doi mk
    @GetMapping("/changePassword")
    public String changepass(Model model) {
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
        model.addAttribute("TaiKhoanUser", taiKhoan);
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String change(Model model,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmNewPassword") String confirmNewPassword) {
        // List<TaiKhoan> change = tkDAO.findByTenDangNhapLike("thienlc");
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
        if (taiKhoan.getTenDangNhap() != null) {
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
                return "person";
            }
        } else {
            model.addAttribute("message", "Nhập mật khẩu cũ sai");
        }

        return "changePassword";
    }

    // // quen mat khau ?
    @GetMapping("/quenmatkhau")
    public String quenmatkhau(Model model) {
        model.addAttribute("mailInfo", new MailInfo());

        return "quenmatkhau";
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

    @PostMapping("/quenmatkhau")
    public String resetPassword(Model model, @RequestParam("forgot") String email) {
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
        TaiKhoan tk = tkDAO.findByTenDangNhap(taiKhoan.getTenDangNhap());
        model.addAttribute("username", taiKhoan.getTenDangNhap());
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        if (tk.getEmail().equals(email)) {
            try {
                generatedOTP = generateOTP(); // Lưu trữ mã OTP đã tạo

                String subject = "Lấy Lại Mật Khẩu";
                String body = "<p>Chào bạn, Cảm ơn bạn đã ủng hộ Shop</p>"
                        + "<p>Dưới đây là mã OTP của bạn.</p>"
                        + "<p>Mã này được tạo vào lúc: " + formattedDateTime + "</p>"
                        + "<p>Mã OTP của bạn là: " + generatedOTP + "</p>"
                        + "<p>Mail này dược tạo tự động, Vui lòng không trả lời. </p>";
                mailer.send(email, subject, body);

                model.addAttribute("success", "Mã sẽ gửi về mail của bạn trong giây lát");

                return "redirect:/confirm-otp";
            } catch (Exception e) {
                System.out.println("Lỗi khi gửi email: " + e);
            }
        } else {
            model.addAttribute("message", "Bạn đã nhập sai email");
        }
        return "quenmatkhau";
    }

    @GetMapping("/confirm-otp")
    public String confirm() {

        return "confirm-otp";
    }

    @PostMapping("/confirm-otp")
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

                return "redirect:/resetpassword";
            } else if (!otp.equals(generatedOTP)) {
                model.addAttribute("messager", "Nhập lại mật khẩu không đúng");
                System.out.println("sai");
                return "redirect:/confirm-otp";
            } else {
                // Mã OTP không hợp lệ hoặc đã hết hạn
                model.addAttribute("messager", "Mã OTP đã hết hạn");

                System.out.println("Hết hạn");
                return "redirect:/confirm-otp";
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ khi chuyển đổi ngày
            model.addAttribute("messager", "Lỗi chuyển đổi ngày");
            System.out.println(e + "Lỗi luôn rồi");
            return "redirect:/confirm-otp";
        }
    }

    @GetMapping("/resetpassword")
    public String restpassword() {
        return "resetpassword";
    }

    @Transactional
    @PostMapping("/resetpassword")
    public String passwordNew(Model model, @RequestParam("passwordNew") String passwordnewm,
            @RequestParam("rePasswordNew") String repasswordnew) {
        // Kiểm tra độ dài mật khẩu mới
        if (passwordnewm.length() < 4 || passwordnewm.length() > 20) {
            model.addAttribute("error", "Mật khẩu phải từ 4 - 20 ký tự");
            return "resetpassword"; // Trả về trang resetpassword với thông báo lỗi
        } else

        // Kiểm tra độ dài mật khẩu nhập lại
        if (repasswordnew.length() < 4 || repasswordnew.length() > 20) {
            model.addAttribute("error1", "Mật khẩu phải từ 4 - 20 ký tự");
            return "resetpassword"; // Trả về trang resetpassword với thông báo lỗi
        } else

        // Kiểm tra mật khẩu mới và mật khẩu nhập lại
        if (!passwordnewm.equals(repasswordnew)) {
            model.addAttribute("error1", "Mật khẩu mới và mật khẩu nhập lại không khớp.");
            return "resetpassword"; // Trả về trang resetpassword với thông báo lỗi
        } else {
            tkDAO.updatePassword(repasswordnew, "thienlv");
            model.addAttribute("sussuc", "Tạo mật khẩu thành công");
        }

        return "redirect:/login-page"; // Điều hướng đến trang thành công sau khi đặt lại mật khẩu thành công
    }

    // dang xuat
    @GetMapping("/dangxuat")
    public String dangxuat() {
        session.remove("TaiKhoanUser");
        return "redirect:/login-page";
    }

    @Transactional
    @PostMapping("/upload")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile avatar) {
        if (!avatar.isEmpty()) {
            try {
                String fileName = avatar.getOriginalFilename();
                tttkDAO.updateAvatar(fileName, "thienlv");
            } catch (Exception e) {
                System.out.println(e + " Lỗi rồi");
            }
        } else {

        }
        return "redirect:/person";
    }

}
