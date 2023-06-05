package com.spring.main.controller.thien;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.main.dao.SanPhamDAO;
import com.spring.main.dao.SanPhamKhuyenMaiDAO;
import com.spring.main.entity.SanPham;
import com.spring.main.entity.SanPhamKhuyenMai;


@Controller
public class productController {
    @Autowired
    SanPhamDAO spDAO;
    
    @Autowired
    SanPhamKhuyenMaiDAO spkmDAO;
    

    @GetMapping("/product-page")
    public String getProduct(Model model,@RequestParam("p") Optional<Integer> p) {
        
        var product = new SanPham();
        model.addAttribute("product", product);
        Pageable pageable = PageRequest.of(p.orElse(0), 12, Sort.by("tenSanPham").ascending());
        var  products= spDAO.findAll(pageable);
        var numberOfPages = products.getTotalPages();
        model.addAttribute("currIndex", p.orElse(0));
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("products", products);

        var  productsSale= spkmDAO.findAll();
        model.addAttribute("productsSales", productsSale);
        
         return "product";
    }

    @GetMapping("/product-page/page")
    public String paginate(Model model,@RequestParam("p") Optional<Integer> p){
        return this.getProduct(model, p);
    }
    @GetMapping("/product-page/edit/{idSanPham}")
    public String productItem(Model model, @PathVariable("idSanPham") String id) {
        SanPham sp = spDAO.findById(id).get();
        model.addAttribute("item", sp);
        return "product";
    }


}
