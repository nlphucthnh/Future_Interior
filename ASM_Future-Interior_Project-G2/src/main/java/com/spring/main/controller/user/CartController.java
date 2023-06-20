package com.spring.main.controller.user;

import java.util.List;
import java.util.*;
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

import com.spring.main.dao.ChiPhiDonHangDAO;
import com.spring.main.dao.DonHangChiTietDAO;
import com.spring.main.dao.DonHangDAO;
import com.spring.main.dao.GioHangDAO;
import com.spring.main.dao.PhanNhomLoaiDAO;
import com.spring.main.dao.SanPhamDAO;
import com.spring.main.dao.TaiKhoanDAO;
import com.spring.main.entity.ChiPhiDonHang;
import com.spring.main.entity.DonHang;
import com.spring.main.entity.DonHangChiTiet;
import com.spring.main.entity.GioHang;
import com.spring.main.entity.SanPham;
import com.spring.main.entity.TaiKhoan;
import com.spring.main.service.CartService;
import com.spring.main.service.SessionService;

@Controller
public class CartController {

	@Autowired
	CartService cart;

	@Autowired
	SanPhamDAO spDAO;

	@Autowired
	GioHangDAO ghDAO;

	@Autowired
	PhanNhomLoaiDAO pnlDao;

	@Autowired
	TaiKhoanDAO tkDAO;

	@Autowired
	DonHangDAO dhDAO;

	@Autowired
	DonHangChiTietDAO dhctDAO;

	@Autowired
	ChiPhiDonHangDAO chiPhiDonHangDAO;

	@Autowired
	SessionService session;

	private List<GioHang> ListCart = new ArrayList();
	public Double thanhtien = 0.0;

	@RequestMapping("/User/cart/add")
	public String addCart(@RequestParam(name = "idSanPham") String idSanPham,
			@RequestParam(name = "quantityProduct") int quantityProduct, Model model) {
		SanPham sanPhamAddCart = spDAO.findByIdSanPham(idSanPham);
		TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
		TaiKhoan taiKhoan2 = tkDAO.findByTenDangNhap(taiKhoan.getTenDangNhap());
		GioHang gioHangTest = ghDAO.findBySanPhamGHAndTaiKhoanGH(sanPhamAddCart, taiKhoan2);
		if (gioHangTest == null) {
			GioHang gioHang = new GioHang(0, quantityProduct, sanPhamAddCart, taiKhoan2);
			ghDAO.save(gioHang);
		} else {
			int soLuong = gioHangTest.getSoLuong() + quantityProduct;
			if (soLuong <= sanPhamAddCart.getSoLuong()) {
				gioHangTest.setSoLuong(soLuong);
				ghDAO.save(gioHangTest);
			}
		}
		model.addAttribute("onRegistered", true);
		model.addAttribute("TaiKhoanUser", taiKhoan);
		model.addAttribute("spitem", sanPhamAddCart);
		return "User-item-page";
	}

	@GetMapping("/User/cart")
	public String getCartPage(Model model) {
		TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
		List<GioHang> ListGioHangs = ghDAO.findByTaiKhoanGH(taiKhoan);
		model.addAttribute("TaiKhoanUser", taiKhoan);
		model.addAttribute("items", ListGioHangs);
		return "User-cart-page";
	}


	@ResponseBody
	@RequestMapping("/User/cart/json")
	public GioHang getDataJson(@RequestBody GioHang gioHang) {
		GioHang gioHang2 = ghDAO.findBySanPhamGHAndTaiKhoanGH(gioHang.getSanPhamGH(), gioHang.getTaiKhoanGH());
		gioHang2.setSoLuong(gioHang.getSoLuong());
		ghDAO.save(gioHang2);
		return gioHang2;
	}

	@ResponseBody
	@RequestMapping("/User/cart/jsonPush/idGioHang")
	public GioHang getDataJsonPushIdGioHang(@RequestParam(name = "idGioHang") int idGioHang) {
		GioHang gioHang2 = ghDAO.findByIdGioHang(idGioHang);
		ListCart.add(gioHang2);
		return gioHang2;
	}

	@ResponseBody
	@RequestMapping("/User/cart/jsonPop/idGioHang")
	public GioHang getDataJsonPopIdGioHang(@RequestParam(name = "idGioHang") int idGioHang) {
		GioHang gioHang2 = ghDAO.findByIdGioHang(idGioHang);
		for (GioHang gioHang : ListCart) {
			if (gioHang.getIdGioHang() == gioHang2.getIdGioHang()) {
				ListCart.remove(ListCart.indexOf(gioHang));
			}
		}
		return gioHang2;
	}

	@PostMapping("/User/card/order")
	public String addOrder() {
		DonHang donHang = new DonHang();
		TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
		TaiKhoan taiKhoan2 = tkDAO.findByTenDangNhap(taiKhoan.getTenDangNhap());
		donHang.setTaiKhoanMuaHang(taiKhoan2);
		DonHang donHang2 = dhDAO.save(donHang);
		session.set("idOrdering", donHang2.getIdDonHang());
		float subTotal = 0;
		float moneyShip = 30000;
		float Total = 0;
		for (GioHang gioHang : ListCart) {
			DonHangChiTiet donHangChiTiet = new DonHangChiTiet(0,gioHang.getSoLuong(),gioHang.getSanPhamGH(),donHang2);
			dhctDAO.save(donHangChiTiet);
			if(gioHang.getSanPhamGH().getKhuyenMai() != null){
				Float discount = (1 - gioHang.getSanPhamGH().getKhuyenMai().getPhamTramKhuyenMai()/100);
				subTotal += (gioHang.getSanPhamGH().getGiaSanPham() * discount)  *  gioHang.getSoLuong();
			}else {
				subTotal += gioHang.getSanPhamGH().getGiaSanPham() *  gioHang.getSoLuong();
			}
			System.out.println(subTotal);
		}
		Total = subTotal + moneyShip;
		ChiPhiDonHang chiPhiDonHang = new ChiPhiDonHang(0, subTotal, moneyShip, Total, donHang2);
		chiPhiDonHangDAO.save(chiPhiDonHang);
		ListCart.clear();
		return "redirect:/User/address";
	}

	@RequestMapping("/cart-page/update/{idGioHang}")
	public String update(@PathVariable("idGioHang") Integer idGioHang, @RequestParam("soLuong") Integer soLuong) {
		System.out.println("UpdateSoLuong-----idGioHang:" + idGioHang + "---soLuongUpdate:" + soLuong);
		ghDAO.updateSoLuong(soLuong, idGioHang);
		return "redirect:/cart-page";
	}

	@RequestMapping("/User/cart/delete/{idGioHang}")
	public String remove(@PathVariable("idGioHang") Integer id) {
		ghDAO.deleteById(id);
		return "redirect:/User/cart";
	}

}
