package com.spring.main.controller.user;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.main.dao.SanPhamDAO;
import com.spring.main.entity.SanPham;
import com.spring.main.entity.TaiKhoan;
import com.spring.main.service.SessionService;

@Controller
public class ProductController {
    @Autowired
    SanPhamDAO sanPhamDAO;

    @Autowired
    SessionService session;

    @GetMapping("/User/product")
    public String getProduct(Model model, @RequestParam("p") Optional<Integer> p) {
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
        if (taiKhoan != null) {
            model.addAttribute("onRegistered", true);
            model.addAttribute("TaiKhoanUser", taiKhoan);
        } else {
            model.addAttribute("onRegistered", false);
        }
        var product = new SanPham();
        model.addAttribute("product", product);
        Pageable pageable = PageRequest.of(p.orElse(0),8, Sort.by("tenSanPham").ascending());
        var products = sanPhamDAO.findAll(pageable);
        var numberOfPages = products.getTotalPages();
        model.addAttribute("currIndex", p.orElse(0));
        model.addAttribute("numberOfPages", numberOfPages);

        model.addAttribute("products", products);
        // var productsSale = spkmDAO.findAll();
        // model.addAttribute("productsSales", productsSale);
        return "User-product-page";
    }

    @GetMapping("/User/product/page")
    public String paginate(Model model, @RequestParam("p") Optional<Integer> p) {
        return this.getProduct(model, p);
    }

    @GetMapping("/User/product/edit/{idSanPham}")
    public String productItem(Model model, @PathVariable("idSanPham") String id) {
        SanPham spitem = sanPhamDAO.findById(id).get();
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
        if (taiKhoan != null) {
            model.addAttribute("onRegistered", true);
            model.addAttribute("TaiKhoanUser", taiKhoan);
        } else {
            model.addAttribute("onRegistered", false);
        }
        model.addAttribute("spitem", spitem);
        return "User-item-page";
    }



    // product list-name
    @PostMapping("/User/product/list/{category}/name")
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
        return "User-list-page";
    }

    @PostMapping("/User/product/list/{category}/price")
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

        return "User-list-page";
    }

    @GetMapping("/User/product/list/{category}")
    public String productListPage(Model model, @PathVariable("category") String category) {
        model.addAttribute("title", category);
        List<SanPham> prolists = sanPhamDAO.findByLoaiSanpham("%" + category + "%",
                Sort.by(Direction.ASC, "tenSanPham"));     
        model.addAttribute("prolists", prolists);
        return "User-list-page";
    }

}
