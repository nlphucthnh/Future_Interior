package com.spring.main.dao;

import java.util.List;

import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.main.entity.SanPhamKhuyenMai;

public interface SanPhamKhuyenMaiDAO extends JpaRepository<SanPhamKhuyenMai, String> {
    // @Query("SELECT d.khuyenMaiSPKM.phamTramKhuyenMai FROM SanPhamKhuyenMai d ")
    // List<SanPhamKhuyenMai> getInventoryByCategory();
}   
