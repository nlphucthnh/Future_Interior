package com.spring.main.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.GioHang;
import com.spring.main.entity.TaiKhoan;

public interface GioHangDAO extends JpaRepository<GioHang, String> {

	List<GioHang> findByTaiKhoanGH(TaiKhoan taiKhoanGH);
}
