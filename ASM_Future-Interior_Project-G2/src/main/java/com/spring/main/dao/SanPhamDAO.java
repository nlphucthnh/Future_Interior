package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.SanPham;
import java.util.List;


public interface SanPhamDAO extends JpaRepository<SanPham, String>{
    List<SanPham> findByidPhanNhomLoaiContaining(Integer phanNhomLoai);
}
