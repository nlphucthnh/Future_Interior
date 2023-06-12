package com.spring.main.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.main.entity.SanPham;
import java.util.List;


public interface SanPhamDAO extends JpaRepository<SanPham, String> {

    @Query(value = "SELECT p FROM SanPham p where p.phanNhomLoai.tenPhanLoaiNhom LIKE %?1%")
    List<SanPham> findByLoaiSanpham (String p, Sort sort);

    @Query(value = "SELECT p FROM SanPham p WHERE p.phanNhomLoai.tenPhanLoaiNhom LIKE ?1 AND p.giaSanPham BETWEEN ?2 AND ?3")
    List<SanPham> findByLoaiSanphamPrice(String p, Sort sort, int min, int max);
}
