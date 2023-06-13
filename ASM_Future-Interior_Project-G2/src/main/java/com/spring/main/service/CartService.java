package com.spring.main.service;

import java.util.Collection;

import com.spring.main.entity.GioHang;
import com.spring.main.entity.TaiKhoan;

public interface CartService {
	Integer add(String masp, Integer soLuong, TaiKhoan taiKhoanGH);

	void remove(String id);

	GioHang update(Integer id, int qty);

	void clear();

	int getCount();

	double getAmount();
}
