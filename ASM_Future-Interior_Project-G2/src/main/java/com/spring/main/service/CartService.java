package com.spring.main.service;

import java.util.Collection;

import com.spring.main.entity.GioHang;
import com.spring.main.entity.TaiKhoan;

public interface CartService {
	Integer add(String idSanPham, Integer soLuong, TaiKhoan taiKhoanGH);

	void remove(Integer id);

	GioHang update(Integer idGioHang, int soLuong);

	void clear();

	Collection<GioHang> getGioHangs();

	int getCount();

	double getAmount();
}
