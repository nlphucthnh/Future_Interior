package com.spring.main.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.main.entity.DonHangChiTiet;

public interface DonHangChiTietDAO extends JpaRepository<DonHangChiTiet, String> {
    @Query(value = "Select d from DonHangChiTiet d where d.donHangDHCT.taiKhoanMuaHang.tenDangNhap Like ?1")
    List<DonHangChiTiet> findByTaiKhoan(String taiKhoan);

}
