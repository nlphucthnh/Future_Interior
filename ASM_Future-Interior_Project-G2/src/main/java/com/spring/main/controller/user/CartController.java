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

	@RequestMapping("/cart-page")
	public String getCartPage(Model model) {
		model.addAttribute("cart", cart);
		model.addAttribute("countcart", cart.getCount());

		var productsSale = spkmDAO.findAll();
		model.addAttribute("productsSales", productsSale);

		TaiKhoan tk = tkDAO.findById("thao").get();

		List<GioHang> phamList = ghDAO.findByTaiKhoanGH(tk);
		model.addAttribute("items", phamList);
		return "cart";
	}

	@PostMapping("/cart-page/add")
	public String addToCart(@Param("soLuong") Integer soLuong, @Param("idSanPham") String idSanPham, Model model,
			@Param("slTonKho") Integer slTonKho) {
		System.out.println(soLuong + "-----" + idSanPham + "-----------------" + slTonKho);

		if (soLuong <= slTonKho) {
			// Add to cart
			SanPham spitem = spDAO.findById(idSanPham).get();
			model.addAttribute("spitem", spitem);
			var productsSale = spkmDAO.findAll();
			model.addAttribute("productsSales", productsSale);

			TaiKhoan tenDangNhap = tkDAO.findById("thao").get();

			List<GioHang> phamList = ghDAO.findByTaiKhoanGH(tenDangNhap);
			model.addAttribute("items", phamList);

			Integer sl = cart.add(idSanPham, soLuong, tenDangNhap);
			return "redirect:/cart-page";
		} else {
			System.out.println("Mua quá số lượng");
			
		}

	}

	@RequestMapping("/cart-page/update/{idGioHang}")
	public String update(@PathVariable("idGioHang") Integer idGioHang, @RequestParam("soLuong") Integer soLuong) {
		System.out.println(idGioHang + "---" + soLuong);
		cart.update(idGioHang, soLuong);
		return "redirect:/cart-page";
	}

	@RequestMapping("/cart-page/remove/{idGioHang}")
	public String remove(@PathVariable("idGioHang") Integer id) {

		ghDAO.deleteById(id);

		return "redirect:/cart-page";
	}

}
