package com.spring.main.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.spring.main.dao.GioHangDAO;
import com.spring.main.dao.SanPhamDAO;
import com.spring.main.entity.GioHang;
import com.spring.main.entity.SanPham;
import com.spring.main.entity.TaiKhoan;

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
	public Integer add(String idSanPham, Integer soLuong, TaiKhoan taiKhoanGH) {
		Integer addQty = soLuong;

		SanPham sp = dao.findById(idSanPham).get();

		// GioHang cart = ghdao.findByTaiKhoanGH(taiKhoanGH,sp);

		// if (cart != null) {
		// addQty = cart.getSoluong()+soluong;
		// cart.setSoluong(addQty);
		// }else {
		GioHang cart = new GioHang();
		cart.setTaiKhoanGH(taiKhoanGH);
		cart.setSanPhamGH(sp);
		cart.setSoLuong(soLuong);

		ghdao.save(cart);

		return addQty;

	}

	@Override
	public void remove(Integer id) {
		map.remove(id);
	}

	@Override
	public GioHang update(Integer idGioHang, int soLuong) {
		GioHang item = map.get(idGioHang);
		System.out.println(item);
		item.setSoLuong(soLuong);
		return item;
	}

	@Override
	public void clear() {
		map.clear();
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
		return map.values().stream().mapToInt(item -> item.getSoLuong()).sum();

	}

	/**
	 * Lấy tổng số tiền tất cả các mặt hàng trong giỏ
	 */
	@Override
	public double getAmount() {
		return map.values().stream().mapToDouble(item -> item.getSanPhamGH().getGiaSanPham() * item.getSoLuong()).sum();
	}

}
