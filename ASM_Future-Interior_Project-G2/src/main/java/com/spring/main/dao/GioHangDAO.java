package com.spring.main.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.spring.main.entity.GioHang;
import com.spring.main.entity.SanPham;
import com.spring.main.entity.TaiKhoan;

import jakarta.transaction.Transactional;

public interface GioHangDAO extends JpaRepository<GioHang, Integer> {

	List<GioHang> findByTaiKhoanGH(TaiKhoan taiKhoanGH);

	GioHang findByIdGioHang(int idGioHang);

	GioHang findBySanPhamGHAndTaiKhoanGH (SanPham sanPhamGH,TaiKhoan taiKhoanGH);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("Update GioHang gh set gh.soLuong = ?1 where gh.idGioHang = ?2")
	public void updateSoLuong(Integer soLuong, Integer idGioHang);
}
