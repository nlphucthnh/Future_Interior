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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.main.dao.BaiDangDAO;
import com.spring.main.entity.BaiDang;
import com.spring.main.entity.TaiKhoan;
import com.spring.main.service.SessionService;

import jakarta.mail.Session;

@Controller
public class BlogController {

    @Autowired
    BaiDangDAO baiDangDAO;

    @Autowired
    SessionService session;

    @GetMapping("/User/blog")
    public String blogPage(Model model, @RequestParam("p") Optional<Integer> p) {
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
		if(taiKhoan != null){
			model.addAttribute("onRegistered", true);
			model.addAttribute("TaiKhoanUser", taiKhoan);
		}else {
			model.addAttribute("onRegistered", false);
		}
        Pageable pageable = PageRequest.of(0, 9);
        pageable = PageRequest.of(p.orElse(0), 9);
        Page<BaiDang> page = baiDangDAO.findAll(pageable);
        model.addAttribute("page", page);
        return "User-blog-page";
    }

    
    @GetMapping("/User/blog/{idBaiDang}")
    public String blogPage(@PathVariable("idBaiDang") int idBaiDang  ,Model model) {
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
		if(taiKhoan != null){
			model.addAttribute("onRegistered", true);
			model.addAttribute("TaiKhoanUser", taiKhoan);
		}else {
			model.addAttribute("onRegistered", false);
		}
        BaiDang baiDang = baiDangDAO.findByIdBaiDang(idBaiDang);
        int view = baiDang.getLuotXemBaiDang();
        baiDang.setLuotXemBaiDang(++view);
        baiDangDAO.save(baiDang);
        model.addAttribute("BaiDang",baiDang);        
        return "User-blog-item-page";
    }

}
