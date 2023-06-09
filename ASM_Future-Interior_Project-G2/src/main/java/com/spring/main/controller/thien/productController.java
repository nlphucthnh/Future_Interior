package com.spring.main.controller.thien;

import java.util.List;
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
import com.spring.main.entity.PhanNhomLoai;
import com.spring.main.entity.SanPham;
import com.spring.main.entity.SanPhamKhuyenMai;
import com.spring.main.entity.TaiKhoan;

import ch.qos.logback.core.util.DirectJson;

@Controller
public class productController {
    @Autowired
    SanPhamDAO spDAO;

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

    @GetMapping("/")
    public String index(Model model) {
        List<SanPham> phamList = spDAO.findAll();
        model.addAttribute("products", phamList);
        return "index";
    }

    @GetMapping("/product-page")
    public String getProduct(Model model, @RequestParam("p") Optional<Integer> p) {

        var product = new SanPham();
        model.addAttribute("product", product);
        Pageable pageable = PageRequest.of(p.orElse(0), 12, Sort.by("tenSanPham").ascending());
        var products = spDAO.findAll(pageable);
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
        SanPham spitem = spDAO.findById(id).get();
        model.addAttribute("spitem", spitem);
        var productsSale = spkmDAO.findAll();
        model.addAttribute("productsSales", productsSale);
        return "product-Item";
    }

    // product list-name
    @PostMapping("/product-list-page/{category}/name")
    public String productListPage(Model model, @PathVariable("category") String category,
            @RequestParam("selectName") String select) {

        if (select.equals("AZ")) {
            List<SanPham> prolists = spDAO.findByLoaiSanpham("%" + category + "%",
                    Sort.by(Direction.ASC, "tenSanPham"));
            model.addAttribute("prolists", prolists);
            model.addAttribute("messagename", "Lọc từ A - Z");
        } else {
            List<SanPham> prolists = spDAO.findByLoaiSanpham("%" + category + "%",
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
                List<SanPham> prolists0_1 = spDAO.findByLoaiSanphamPrice("%" + category + "%",
                        Sort.by(Direction.ASC, "tenSanPham"), 0,
                        1000000);
                model.addAttribute("prolists", prolists0_1);
                break;
            case "2":
                model.addAttribute("messages", "Lọc từ 1.000.000 đến 5.000.000");
                List<SanPham> prolists1_5 = spDAO.findByLoaiSanphamPrice("%" + category + "%",
                        Sort.by(Direction.ASC, "tenSanPham"),
                        1000000,
                        5000000);
                model.addAttribute("prolists", prolists1_5);
                break;
            case "3":
                model.addAttribute("messages", "Lọc từ 5.000.000 đến 10.000.000");
                List<SanPham> prolists5_10 = spDAO.findByLoaiSanphamPrice("%" + category + "%",
                        Sort.by(Direction.ASC, "tenSanPham"),
                        5000000,
                        10000000);
                model.addAttribute("prolists", prolists5_10);
                break;
            case "4":
                model.addAttribute("messages", "Lọc từ 10.000.000 đến 15.000.000");
                List<SanPham> prolists10_15 = spDAO.findByLoaiSanphamPrice("%" + category + "%",
                        Sort.by(Direction.ASC, "tenSanPham"),
                        10000000,
                        15000000);
                model.addAttribute("prolists", prolists10_15);
                break;
            case "5":
                model.addAttribute("messages", "Lọc từ 15.000.000 đến 20.000.000");
                List<SanPham> prolists15_20 = spDAO.findByLoaiSanphamPrice("%" + category + "%",
                        Sort.by(Direction.ASC, "tenSanPham"),
                        15000000,
                        20000000);
                model.addAttribute("prolists", prolists15_20);
                break;
            case "6":
                model.addAttribute("messages", "Lọc từ 20.000.000 Trở lên");
                List<SanPham> prolists20_ = spDAO.findByLoaiSanphamPrice("%" + category + "%",
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

        List<SanPham> prolists = spDAO.findByLoaiSanpham("%" + category + "%", Sort.by(Direction.ASC, "tenSanPham"));
        model.addAttribute("prolists", prolists);
        return "product-List";
    }

    /// person

    @GetMapping("/person")
    public String person(Model model) {
        List<TaiKhoan> people = tkDAO.findByTenDangNhap("thienlv");
        model.addAttribute("people", people);
        List<DonHangChiTiet> tkmuahang = dhctDAO.findByTaiKhoan("thienlv");
        model.addAttribute("donhangct", tkmuahang);
        return "person";
    }

}
