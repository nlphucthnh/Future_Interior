package com.spring.main.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.main.entity.GioHang;
import com.spring.main.entity.SanPham;
import com.spring.main.entity.TaiKhoan;

public interface GioHangDAO extends JpaRepository<GioHang, Integer> {

	List<GioHang> findByTaiKhoanGH(TaiKhoan taiKhoanGH);
    
}
