package com.spring.main.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	@Autowired
	SanPhamDAO dao;

	@Autowired
	GioHangDAO ghdao;

	@Override
	public Integer add(String masp, Integer soLuong, TaiKhoan taiKhoanGH) {
		Integer addQty = soLuong;

		SanPham sp = dao.findById(masp).get();

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
	public void remove(String id) {

	}

	@Override
	public GioHang update(Integer id, int qty) {
		GioHang item = map.get(id);
		item.setSoLuong(qty);
		return item;
	}

	@Override
	public void clear() {

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
