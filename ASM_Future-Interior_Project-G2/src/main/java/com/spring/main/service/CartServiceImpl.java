package com.spring.main.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.spring.main.dao.GioHangDAO;
import com.spring.main.dao.SanPhamDAO;
import com.spring.main.entity.GioHang;
import com.spring.main.entity.SanPham;
import com.spring.main.entity.TaiKhoan;
import com.spring.main.service.CartService;

@SessionScope
@Service
public class CartServiceImpl implements CartService {

	Map<Integer, GioHang> map = new HashMap<>();
	ArrayList<GioHang> list = new ArrayList<GioHang>();

	@Autowired
	SanPhamDAO dao;

	@Autowired
	GioHangDAO ghdao;

	@Override
	public Integer add(String masp, Integer soLuong, TaiKhoan taiKhoanGH) {
		Integer addQty = soLuong;

		SanPham sp = dao.findById(masp).get();

		// GioHang cart = ghdao.findByTaiKhoanGHAndSanPham(taiKhoanGH,sp);
		// if (cart != null) {
		// addQty = cart.getSoLuong()+soLuong;
		// cart.setSoLuong(addQty);
		// }else {}

		GioHang cart = new GioHang();
		cart.setTaiKhoanGH(taiKhoanGH);
		cart.setSanPhamGH(sp);
		cart.setSoLuong(soLuong);
		ghdao.save(cart);

		return addQty;

	}

	@Override
	public void remove(Integer id) {

	}

	@Override
	public GioHang update(Integer idGioHang, int soLuong) {

		map.get(idGioHang).setSoLuong(soLuong);
		
		return map.get(idGioHang);
	}

	@Override
	public void clear() {

	}

	/**
	 * Lấy tất cả các mặt hàng trong giỏ
	 */
	@Override
	public Collection<GioHang> getGioHangs() {
		return map.values();
	}

	/**
	 * Lấy tổng số lượng các mặt hàng trong giỏ
	 */
	@Override
	public int getCount() {

		return map.size();

	}

	/**
	 * Lấy tổng số tiền tất cả các mặt hàng trong giỏ
	 */
	public double getAmount() {
		double amount = 0;
		for (GioHang gh : map.values()) {
			amount += gh.getSanPhamGH().getGiaSanPham() * gh.getSoLuong();
		}
		return amount;
	}

}
