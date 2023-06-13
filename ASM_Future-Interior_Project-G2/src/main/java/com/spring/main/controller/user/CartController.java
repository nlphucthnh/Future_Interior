package com.spring.main.controller.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.main.dao.DonHangChiTietDAO;
import com.spring.main.dao.DonHangDAO;
import com.spring.main.dao.GioHangDAO;
import com.spring.main.dao.PhanNhomLoaiDAO;
import com.spring.main.dao.SanPhamDAO;
import com.spring.main.dao.SanPhamKhuyenMaiDAO;
import com.spring.main.dao.TaiKhoanDAO;
import com.spring.main.entity.GioHang;
import com.spring.main.entity.SanPham;
import com.spring.main.entity.TaiKhoan;
import com.spring.main.service.CartService;

@Controller
public class CartController {

	@Autowired
	CartService cart;

	@Autowired
	SanPhamDAO spDAO;

	@Autowired
	GioHangDAO ghDAO;

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
	// Impl Item
	// @RequestMapping("/cart-page")
	// public String cartProduct(Model model) {
	// List<SanPham> phamList = spDAO.findAll();
	// model.addAttribute("products", phamList);
	//
	// model.addAttribute("cart", cart);
	// model.addAttribute("countcart", cart.getCount());
	// return "cart";
	// }
	//
	// @RequestMapping("/cart-page/edit/{idSanPham}")
	// public String add(@PathVariable("idSanPham") String id) {
	// cart.add(id);
	// return "redirect:/cart";
	// }

	// Add SanPham nhưng kh có count

	@RequestMapping("/cart-page")
	public String getCartPage(Model model) {
		var productsSale = spkmDAO.findAll();
		model.addAttribute("productsSales", productsSale);

		TaiKhoan tk = tkDAO.findById("thao").get();

		List<GioHang> phamList = ghDAO.findByTaiKhoanGH(tk);
		model.addAttribute("items", phamList);
		return "cart";
	}

	@RequestMapping("/cart-page/edit/{idSanPham}")
	public String add(Model model,
			@PathVariable("idSanPham") String id) {

		SanPham spitem = spDAO.findById(id).get();
		model.addAttribute("spitem", spitem);
		var productsSale = spkmDAO.findAll();
		model.addAttribute("productsSales", productsSale);

		TaiKhoan tk = tkDAO.findById("thao").get();

		List<GioHang> phamList = ghDAO.findByTaiKhoanGH(tk);
		model.addAttribute("items", phamList);

		Integer sl = cart.add(id, 2, tk);

		return "redirect:/cart-page";
	}

	@GetMapping("/cart-page/remove/{idGioHang}")
	public String remove(@PathVariable("idGioHang") String id) {

		ghDAO.deleteById(id);

		return "redirect:/cart-page";
	}

	@RequestMapping("/cart/update/{idGioHang}")
	public String update(@PathVariable("idGioHang") int id,
			@RequestParam("soLuong") int qty) {
		cart.update(id, qty);
		return "redirect:/cart-page";
	}

	@ResponseBody
	@PostMapping("/cart/item/update")
	public GioHang updateCardItem(@RequestBody GioHang item) {
		GioHang updatedItem = cart.update(item.getIdGioHang(), item.getSoLuong());
		return updatedItem;
	}

}
