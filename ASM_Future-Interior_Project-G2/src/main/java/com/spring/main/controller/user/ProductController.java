package com.spring.main.controller.user;

import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.main.dao.DonHangChiTietDAO;
import com.spring.main.dao.DonHangDAO;
import com.spring.main.dao.PhanNhomLoaiDAO;
import com.spring.main.dao.SanPhamDAO;
import com.spring.main.dao.SanPhamKhuyenMaiDAO;
import com.spring.main.dao.TaiKhoanDAO;
import com.spring.main.entity.DonHang;
import com.spring.main.entity.DonHangChiTiet;
import com.spring.main.entity.MailInfo;
import com.spring.main.entity.PhanNhomLoai;
import com.spring.main.entity.SanPham;
import com.spring.main.entity.SanPhamKhuyenMai;
import com.spring.main.entity.TaiKhoan;
import com.spring.main.service.CartService;
import com.spring.main.service.MailerService;

import ch.qos.logback.core.util.DirectJson;
import jakarta.mail.internet.InternetAddress;

@Controller
public class ProductController {
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
    CartService cart;
    
    @GetMapping("/")
    public String index(Model model) {
        List<SanPham> phamList = sanPhamDAO.findAll();
        System.out.println(phamList);
        model.addAttribute("products", phamList);
        model.addAttribute("onRegistered", false);
        return "index";
    }

    @GetMapping("/product-page")
    public String getProduct(Model model, @RequestParam("p") Optional<Integer> p) {

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
        model.addAttribute("countcart", cart.getCount());
        return "product-Item";
    }

    // product list-name
    @PostMapping("/product-list-page/{category}/name")
    public String productListPage(Model model, @PathVariable("category") String category,
            @RequestParam("selectName") String select) {

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

        List<SanPham> prolists = sanPhamDAO.findByLoaiSanpham("%" + category + "%", Sort.by(Direction.ASC, "tenSanPham"));
        model.addAttribute("prolists", prolists);
        return "product-List";
    }

    /// person

    @GetMapping("/person")
    public String person(Model model) {
        // List<TaiKhoan> people = tkDAO.findByTenDangNhap1("thienlv");
        // model.addAttribute("people", people);
        List<DonHangChiTiet> tkmuahang = dhctDAO.findByTaiKhoan("thienlv");
        model.addAttribute("donhangct", tkmuahang);
        return "person";
    }

    // // doi mk
    // @GetMapping("/changePassword")
    // public String changepass(Model model) {
    //     return "changePassword";
    // }

    // @PostMapping("/changePassword")
    // public String change(Model model,
    //         @RequestParam("currentPassword") String currentPassword,
    //         @RequestParam("newPassword") String newPassword,
    //         @RequestParam("confirmNewPassword") String confirmNewPassword) {

    //     List<TaiKhoan> change = tkDAO.findByTenDangNhap1("thienlv");
    //     model.addAttribute("username", "thienlv");
    //     boolean usernameExists = false;
    //     for (TaiKhoan tk : change) {
    //         if (tk.getMatKhau().equals(currentPassword)) {
    //             usernameExists = true;
    //             break;
    //         }
    //     }
    //     if (usernameExists) {
    //         if (newPassword.length() < 4 || newPassword.length() > 20) {
    //             model.addAttribute("length", "Mật khẩu từ 4 - 20 ký tự");
    //         } else if (!newPassword.equals(confirmNewPassword)) {
    //             model.addAttribute("message1", "Nhập lại mật khẩu không đúng");
    //         } else {
    //             TaiKhoan updatedTaiKhoan = change.get(0);
    //             // Cập nhật mật khẩu mới trong đối tượng TaiKhoan
    //             updatedTaiKhoan.setMatKhau(newPassword);
    //             // Lưu thay đổi vào CSDL
    //             // System.out.println(updatedTaiKhoan);
    //             tkDAO.save(updatedTaiKhoan);
    //             model.addAttribute("success", "Đổi mật khẩu thành công");
    //         }
    //     } else {
    //         model.addAttribute("message", "Nhập mật khẩu cũ sai");
    //     }

    //     return "changePassword";
    // }

    // // quen mat khau ?
    // @GetMapping("/quenmatkhau")
    // public String quenmatkhau(Model model) {
    //     model.addAttribute("mailInfo", new MailInfo());
    //     return "quenmatkhau";
    // }

    // @PostMapping("/quenmatkhau")
    // public String resetpassword(Model model, @RequestParam("forgot") String email) {
    //     List<TaiKhoan> change = tkDAO.findByTenDangNhap1("thienlv");
    //     model.addAttribute("username", "thienlv");

    //     boolean emailExists = false;
    //     for (TaiKhoan tk : change) {
    //         if (tk.getEmail().equals(email)) {
    //             emailExists = true;
    //             break;
    //         }
    //     }
    //     if (emailExists) {
    //         try {

    //             String subject = "Lấy Lại Mật Khẩu";
    //             String body = "<p>Chào bạn,</p>"
    //                     + "<p>Dưới đây là mật khẩu của bạn.</p>"
    //                     + "<p>Mật khẩu của bạn là: </p>"
    //                     + "<br>"
    //                     + "<p>Cảm ơn bạn đã ủng hộ shop </p>";

    //             mailer.send(email, subject, body);

    //             model.addAttribute("success", "Mật khẩu sẽ gửi về mail của bạn trong giây lát");

    //         } catch (Exception e) {
    //             System.out.println("Lỗi khi gửi email: " + e);
    //         }

    //     } else {
    //         model.addAttribute("message", "Bạn đã nhập sai email");
    //     }

    //     return "quenmatkhau";
    // }




}
