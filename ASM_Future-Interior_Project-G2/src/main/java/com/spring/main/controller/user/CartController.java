package com.spring.main.controller.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.main.dao.DonHangChiTietDAO;
import com.spring.main.dao.DonHangDAO;
import com.spring.main.dao.PhanNhomLoaiDAO;
import com.spring.main.dao.SanPhamDAO;
import com.spring.main.dao.SanPhamKhuyenMaiDAO;
import com.spring.main.dao.TaiKhoanDAO;
import com.spring.main.entity.SanPham;
import com.spring.main.service.CartService;

@Controller
public class CartController {

	@Autowired
	CartService cart;

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
    //Impl Item
//    @RequestMapping("/cart-page")
//	public String cartProduct(Model model) {
//    	List<SanPham> phamList = spDAO.findAll();
//    	model.addAttribute("products", phamList);
//	
//		model.addAttribute("cart", cart);
//		model.addAttribute("countcart", cart.getCount());
//		return "cart";
//	}
//
//	@RequestMapping("/cart-page/edit/{idSanPham}")
//	public String add(@PathVariable("idSanPham") String id) {
//		cart.add(id);
//		return "redirect:/cart";
//	}
    
    
//	Add SanPham nhưng kh có count

	@GetMapping("/cart-page")
	public String getCartPage(Model model) {
		List<SanPham> phamList = spDAO.findAll();
        model.addAttribute("products", phamList);
        return "cart";
	}
	
	 @GetMapping("/cart-page/edit/{idSanPham}")
	    public String add(Model model,@PathVariable("idSanPham") String id) {
		 SanPham spitem = spDAO.findById(id).get();
	        model.addAttribute("spitem", spitem);
	        var productsSale = spkmDAO.findAll();
	        model.addAttribute("productsSales", productsSale);
	        
	        var products = spDAO.findById(id).get();
	        model.addAttribute("products", products);
	       // cart.add(id);
		   return "cart";
	    }
}
