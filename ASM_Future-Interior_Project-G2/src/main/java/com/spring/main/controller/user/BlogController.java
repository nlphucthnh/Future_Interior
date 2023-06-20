package com.spring.main.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.main.dao.BaiDangDAO;
import com.spring.main.entity.BaiDang;
import com.spring.main.service.DisplayHeader;

@Controller
public class BlogController {

    @Autowired
    BaiDangDAO baiDangDAO;
    @Autowired
    DisplayHeader displayHeader;

    @RequestMapping("/blog-page")
    public String blogPage(Model model, @RequestParam("p") Optional<Integer> p) {
        // List<BaiDang> list = new ArrayList<>();
        // List<BaiDang> baiDang = baiDangDAO.findAll();
        // for (BaiDang baiDang2 : baiDang) {
        // System.out.println(baiDang2.getTieuDeBaiDang());
        // }
        displayHeader.displayHeader(model); // hiển thị người dùng trên header
        Pageable pageable = PageRequest.of(0, 9);
        pageable = PageRequest.of(p.orElse(0), 9);
        Page<BaiDang> page = baiDangDAO.findAll(pageable);
        model.addAttribute("page", page);
        return "/blogcopy";
    }

    // @RequestMapping("/blog-page/navigation")
    // public String paginate(Model model, @RequestParam("p") Optional<Integer> p) {
    //     Pageable pageable = PageRequest.of(p.orElse(0), 9);
    //     System.out.println("halaoahduad"+p);
    //     Page<BaiDang> page = baiDangDAO.findAll(pageable);
    //     model.addAttribute("page", page);
    //     return "/blogcopy";
    // }

    // @RequestMapping("/product/page")
    // public String paginate(Model model, @RequestParam("p") Optional<Integer> p) {
    // Pageable pageable = PageRequest.of(p.orElse(0), 5);
    // Page<Product> page = dao.findAll(pageable);
    // model.addAttribute("page", page);
    // return "page";
    // }

}
